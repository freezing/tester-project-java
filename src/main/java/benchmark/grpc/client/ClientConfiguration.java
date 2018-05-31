package benchmark.grpc.client;

import benchmark.grpc.Transport;
import com.google.common.util.concurrent.MoreExecutors;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import java.net.SocketAddress;
import java.util.concurrent.Executor;
import netty.Util;

public class ClientConfiguration {
  private final SocketAddress socketAddress;
  private final Executor executor;
  private final int numChannels;
  private final int flowControlWindowSizeBytes;
  private final EventLoopGroup eventLoopGroup;
  private final Class<? extends SocketChannel> channelType;
  // TODO: Add RpcType
  private final int warmupDurationSeconds;
  private final int benchmarkDurationSeconds;
  private final int rpcsPerChannel;
  private final int serverPayloadSizeBytes;
  private final int clientPayloadSizeBytes;
  private final int serverWorkIterations;
  private final int serverProcessingIterations;

  public ClientConfiguration(SocketAddress socketAddress, Executor executor, int numChannels,
      int rpcsPerChannel, int flowControlWindowSizeBytes,
      EventLoopGroup eventLoopGroup,
      Class<? extends SocketChannel> channelType, int warmupDurationSeconds,
      int benchmarkDurationSeconds, int serverPayloadSizeBytes, int clientPayloadSizeBytes,
      int serverWorkIterations, int serverProcessingIterations) {
    this.socketAddress = socketAddress;
    this.executor = executor;
    this.numChannels = numChannels;
    this.rpcsPerChannel = rpcsPerChannel;
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

  public int rpcsPerChannel() {
    return rpcsPerChannel;
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

  public static ClientConfiguration createDefault(
      SocketAddress socketAddress, Transport transport) {
    return new ClientConfiguration(
        socketAddress,
        MoreExecutors.directExecutor(),
//        Executors.newFixedThreadPool(4),
//        new ForkJoinPool(2),
        /* numChannels */ 4,
        /* rpcsPerChannel */ 500,
        /* flowControlWindowSizeBytes */ 4 * 1024 * 1024, // 1MB
        Util.createEventLoopGroup(8, transport),
        Util.getClientChannelClass(transport),
        /* warmupDurationSeconds */ 5,
        /* benchmarkDurationSeconds */ 20,
        /* serverPayloadSizeBytes */ 100,
        /* clientPayloadSizeBytes */ 100,
        /* serverWorkIterations */ 1000,
        /* serverProcessingIterations */ 1000);
  }
}
