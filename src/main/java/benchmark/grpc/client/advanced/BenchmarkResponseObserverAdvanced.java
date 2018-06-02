package benchmark.grpc.client.advanced;

import benchmark.grpc.common.MetricsHelper;
import com.google.common.util.concurrent.SettableFuture;
import io.freezing.benchmark.Server.BenchmarkRequest;
import io.freezing.benchmark.Server.BenchmarkResponse;
import io.grpc.stub.StreamObserver;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.HdrHistogram.Histogram;

/**
 * Sends a request to the server as soon as it receives the response to the previous request.
 * It assumes that there is an in-flight request already in progress.
 */
public class BenchmarkResponseObserverAdvanced implements StreamObserver<BenchmarkResponse> {
  private final SettableFuture<Histogram> histogramFuture;
  private final Histogram histogram;
  private final int targetOutstandingRpcsPerStream;
  private final BenchmarkRequest benchmarkRequest;
  private final long benchmarkFinishTimeNanos;
  private final Map<Integer, Long> callTimestamps;
  // Cannot be set in the constructor because it's created before the request stream.
  private StreamObserver<BenchmarkRequest> requestStream;
  private int nextRequestId;
  private int currentOutstandingRpcs;
  private boolean isCompleted;
  private final Executor ex = Executors.newSingleThreadExecutor();

  public BenchmarkResponseObserverAdvanced(
      int targetOutstandingRpcsPerStream,
      SettableFuture<Histogram> histogramFuture,
      BenchmarkRequest benchmarkRequest,
      long benchmarkFinishTimeNanos) {
    this.targetOutstandingRpcsPerStream = targetOutstandingRpcsPerStream;
    this.histogramFuture = histogramFuture;
    this.histogram = MetricsHelper.newHistogram();
    this.benchmarkRequest = benchmarkRequest;
    this.benchmarkFinishTimeNanos = benchmarkFinishTimeNanos;
    this.callTimestamps = new HashMap<>();
    this.currentOutstandingRpcs = 0;
    this.nextRequestId = 0;
    this.isCompleted = false;
  }

  public void setRequestStream(
      StreamObserver<BenchmarkRequest> requestStream) {
    this.requestStream = requestStream;
  }

  @Override
  public void onNext(BenchmarkResponse value) {
    long nowNanos = System.nanoTime();
    long startNanos = callTimestamps.get(value.getId());
    long latencyNanos = nowNanos - startNanos;
    // NOTE(nikola): Measure in microseconds.
    histogram.recordValue(latencyNanos / 1000L);

    if (nowNanos >= benchmarkFinishTimeNanos) {
      if (!isCompleted) {
        isCompleted = true;
        ex.execute(() -> {
          requestStream.onCompleted();
        });
      }
      return;
    }
    currentOutstandingRpcs--;
    sendRequest();
//    while (currentOutstandingRpcs < targetOutstandingRpcsPerStream) {
//      sendRequest();
//    }
  }

  @Override
  public void onError(Throwable t) {
    t.printStackTrace();
    histogramFuture.setException(t);
  }

  @Override
  public void onCompleted() {
    histogramFuture.set(histogram);
  }

  public void start() {
    sendRequest();
    sendRequest();
  }

  private void sendRequest() {
    long timestampNano = System.nanoTime();
    BenchmarkRequest request = nextRequest();
    callTimestamps.put(request.getId(), timestampNano);
    currentOutstandingRpcs++;
    ex.execute(() -> requestStream.onNext(request));
  }

  private BenchmarkRequest nextRequest() {
    return benchmarkRequest.toBuilder()
        .setId(nextRequestId++)
        .build();
  }
}
