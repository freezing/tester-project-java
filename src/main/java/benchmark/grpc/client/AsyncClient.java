package benchmark.grpc.client;

import benchmark.grpc.Transport;
import benchmark.grpc.common.MetricsHelper;
import benchmark.grpc.common.PayloadHelpers;
import benchmark.grpc.common.ThreadHelpers;
import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.SettableFuture;
import io.freezing.benchmark.BenchmarkServiceGrpc;
import io.freezing.benchmark.BenchmarkServiceGrpc.BenchmarkServiceStub;
import io.freezing.benchmark.Server.BenchmarkRequest;
import io.grpc.ManagedChannel;
import io.grpc.netty.NettyChannelBuilder;
import io.grpc.stub.StreamObserver;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import org.HdrHistogram.Histogram;

public class AsyncClient {
  private final ClientConfiguration configuration;

  public AsyncClient(ClientConfiguration configuration) {
    this.configuration = configuration;
  }

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    Transport transport = Transport.NETTY_NIO;
    SocketAddress serverAddress = new InetSocketAddress("localhost", 9999);
    ClientConfiguration clientConfiguration =
        ClientConfiguration.createDefault(serverAddress, transport);
    new AsyncClient(clientConfiguration).run();
  }

  private void run() throws ExecutionException, InterruptedException {
    List<ManagedChannel> channels = makeManagedChannels(configuration);
    BenchmarkRequest request = PayloadHelpers.makeBenchmarkRequest(
        configuration.clientPayloadSizeBytes(),
        configuration.serverPayloadSizeBytes());
    doWarmup(request, channels, configuration.warmupDurationSeconds());
    System.out.println("Warmup finished.");

    long benchmarkStartNanos = System.nanoTime();
    ListenableFuture<Histogram> histogramFuture = doBenchmark(request, channels,
        configuration.benchmarkDurationSeconds());
    Histogram histogram = histogramFuture.get();
    long elapsedTimeNanos = System.nanoTime() - benchmarkStartNanos;
    ThreadHelpers.printThreadStats();
    System.out.println();
    printStats(histogram, elapsedTimeNanos);
  }

  private void doWarmup(BenchmarkRequest request, List<ManagedChannel> channels,
      int durationSeconds) {
    // TODO: Create await that returns Present.
    try {
      doBenchmark(request, channels, durationSeconds).get();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }
    System.gc();
  }

  private ListenableFuture<Histogram> doBenchmark(
      BenchmarkRequest request, List<ManagedChannel> channels, int durationSeconds) {
    long benchmarkFinishTimeNanos = System.nanoTime() + TimeUnit.SECONDS.toNanos(durationSeconds);

    ImmutableList<ListenableFuture<Histogram>> histogramFutures = channels.stream()
        .map(channel ->
            doRpcs(channel, configuration.rpcsPerChannel(), request, benchmarkFinishTimeNanos))
        .collect(ImmutableList.toImmutableList());
    return Futures.transform(Futures.allAsList(histogramFutures),
        this::mergeHistograms,
        MoreExecutors.directExecutor());
  }

  private ListenableFuture<Histogram> doRpcs(ManagedChannel channel,
      int rpcsPerChannel, BenchmarkRequest request, long benchmarkFinishTimeNanos) {
    ImmutableList.Builder<ListenableFuture<Histogram>> histogramBuilderFutures =
        ImmutableList.builder();
    for (int i = 0; i < rpcsPerChannel; i++) {
      // TODO: Check request type and introduce unary calls.
      ListenableFuture<Histogram> histogramFuture =
          doStreamingCall(channel, request, benchmarkFinishTimeNanos);
      histogramBuilderFutures.add(histogramFuture);
    }
    return Futures.transform(Futures.allAsList(histogramBuilderFutures.build()),
        this::mergeHistograms,
        MoreExecutors.directExecutor());
  }

  private ListenableFuture<Histogram> doStreamingCall(
      ManagedChannel channel, BenchmarkRequest request, long benchmarkFinishTimeNanos) {
    long nowNanos = System.nanoTime();

    SettableFuture<Histogram> histogramFuture = SettableFuture.create();
    BenchmarkServiceStub stub = BenchmarkServiceGrpc.newStub(channel);
    BenchmarkResponseObserver responseObserver = new BenchmarkResponseObserver(
        histogramFuture,
        request,
        /* lastCallNanos */ nowNanos,
        benchmarkFinishTimeNanos);
    StreamObserver<BenchmarkRequest> requestStream =
        stub.streamingCall(responseObserver);
    responseObserver.setRequestStream(requestStream);
    requestStream.onNext(request);
    return histogramFuture;
  }

  private Histogram mergeHistograms(List<Histogram> histograms) {
    Histogram merged = MetricsHelper.newHistogram();
    histograms.forEach(histogram -> histogram.allValues().forEach(histogramEntry -> {
      long latency = histogramEntry.getValueIteratedTo();
      long count = histogramEntry.getCountAtValueIteratedTo();
      merged.recordValueWithCount(latency, count);
    }));
    return merged;
  }

  private List<ManagedChannel> makeManagedChannels(ClientConfiguration configuration) {
    List<ManagedChannel> channels = new ArrayList<>();
    for (int i = 0; i < configuration.numChannels(); i++) {
      ManagedChannel channel = NettyChannelBuilder
          .forAddress(configuration.serverAddress())
          .eventLoopGroup(configuration.eventLoopGroup())
          .executor(configuration.executor())
          .channelType(configuration.channelType())
          .flowControlWindow(configuration.flowControlWindowSizeBytes())
          .usePlaintext()
          .build();
      channels.add(channel);
    }
    return channels;
  }

  private void printStats(Histogram histogram, long elapsedTimeNanos) {
    long latency50 = histogram.getValueAtPercentile(50);
    long latency90 = histogram.getValueAtPercentile(90);
    long latency95 = histogram.getValueAtPercentile(95);
    long latency99 = histogram.getValueAtPercentile(99);
    long latency999 = histogram.getValueAtPercentile(99.9);
    long latencyMax = histogram.getValueAtPercentile(100);
    long queriesPerSecond = histogram.getTotalCount() * 1000000000L / elapsedTimeNanos;

    StringBuilder values = new StringBuilder();
    values
        .append("50%ile Latency (in micros):     ").append(String.format("%,d", latency50)).append('\n')
        .append("90%ile Latency (in micros):     ").append(String.format("%,d", latency90)).append('\n')
        .append("95%ile Latency (in micros):     ").append(String.format("%,d", latency95)).append('\n')
        .append("99%ile Latency (in micros):     ").append(String.format("%,d", latency99)).append('\n')
        .append("99.9%ile Latency (in micros):   ").append(String.format("%,d", latency999)).append('\n')
        .append("Maximum Latency (in micros):    ").append(String.format("%,d", latencyMax)).append('\n')
        .append("QPS:                            ").append(String.format("%,d", queriesPerSecond)).append('\n');
    System.out.println(values);
  }
}
