package benchmark.grpc.client.advanced;

import benchmark.grpc.Transport;
import com.google.common.util.concurrent.MoreExecutors;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import java.net.SocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import netty.Util;

public class ClientConfigurationAdvanced {
  private final SocketAddress socketAddress;
  private final Executor executor;
  private final int numChannels;
  private final int flowControlWindowSizeBytes;
  private final EventLoopGroup eventLoopGroup;
  private final Class<? extends SocketChannel> channelType;
  private final int warmupDurationSeconds;
  private final int benchmarkDurationSeconds;
  private final int streamsPerChannel;
  private final int outsandingRpcsPerStream;
  private final int serverPayloadSizeBytes;
  private final int clientPayloadSizeBytes;
  private final int serverWorkIterations;
  private final int serverProcessingIterations;

  public ClientConfigurationAdvanced(SocketAddress socketAddress, Executor executor, int numChannels,
      int streamsPerChannel, int outstandingRpcsPerStream, int flowControlWindowSizeBytes,
      EventLoopGroup eventLoopGroup,
      Class<? extends SocketChannel> channelType, int warmupDurationSeconds,
      int benchmarkDurationSeconds, int serverPayloadSizeBytes, int clientPayloadSizeBytes,
      int serverWorkIterations, int serverProcessingIterations) {
    this.socketAddress = socketAddress;
    this.executor = executor;
    this.numChannels = numChannels;
    this.streamsPerChannel = streamsPerChannel;
    this.outsandingRpcsPerStream = outstandingRpcsPerStream;
    this.flowControlWindowSizeBytes = flowControlWindowSizeBytes;
    this.eventLoopGroup = eventLoopGroup;
    this.channelType = channelType;
    this.warmupDurationSeconds = warmupDurationSeconds;
    this.benchmarkDurationSeconds = benchmarkDurationSeconds;
    this.serverPayloadSizeBytes = serverPayloadSizeBytes;
    this.clientPayloadSizeBytes = clientPayloadSizeBytes;
    this.serverWorkIterations = serverWorkIterations;
    this.serverProcessingIterations = serverProcessingIterations;
  }

  public SocketAddress serverAddress() {
    return socketAddress;
  }

  public Executor executor() {
    return executor;
  }

  public int numChannels() {
    return numChannels;
  }

  public int streamsPerChannel() {
    return streamsPerChannel;
  }

  public int outsandingRpcsPerStream() {
    return outsandingRpcsPerStream;
  }

  public int flowControlWindowSizeBytes() {
    return flowControlWindowSizeBytes;
  }

  public EventLoopGroup eventLoopGroup() {
    return eventLoopGroup;
  }

  public Class<? extends SocketChannel> channelType() {
    return channelType;
  }

  public int warmupDurationSeconds() {
    return warmupDurationSeconds;
  }

  public int benchmarkDurationSeconds() {
    return benchmarkDurationSeconds;
  }

  public int serverPayloadSizeBytes() {
    return serverPayloadSizeBytes;
  }

  public int clientPayloadSizeBytes() {
    return clientPayloadSizeBytes;
  }

  public int serverWorkIterations() {
    return serverWorkIterations;
  }

  public int serverProcessingIterations() {
    return serverProcessingIterations;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    sb
        .append("Client configuration\n")
        .append(String.format("  # channels: %d\n", numChannels))
        .append(String.format("  # streams per channel: %d\n", streamsPerChannel))
        .append(String.format("  # Outstanding RPCs per stream: %d\n", outsandingRpcsPerStream));

    return sb.toString();
  }

  public static ClientConfigurationAdvanced createDefault(
      SocketAddress socketAddress, Transport transport) {
    return new ClientConfigurationAdvanced(
        socketAddress,
//        Executors.newFixedThreadPool(2),
//        MoreExecutors.directExecutor(),
        new ForkJoinPool(3),
        /* numChannels */ 4,
        /* streamsPerChannel */ 4,
        /* outstandingRpcsPerStream */ 300,
        /* flowControlWindowSizeBytes */ 4 * 1024 * 1024, // 4MB
        Util.createEventLoopGroup(4, transport),
        Util.getClientChannelClass(transport),
        /* warmupDurationSeconds */ 5,
        /* benchmarkDurationSeconds */ 20,
        /* serverPayloadSizeBytes */ 2000,
        /* clientPayloadSizeBytes */ 100,
        /* serverWorkIterations */ 1000,
        /* serverProcessingIterations */ 1000);
  }
}
