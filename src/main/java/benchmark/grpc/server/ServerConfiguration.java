package benchmark.grpc.server;

import benchmark.grpc.Transport;
import com.google.common.util.concurrent.MoreExecutors;
import io.grpc.HandlerRegistry;
import io.grpc.netty.NettyChannelBuilder;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import java.net.SocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import netty.Util;

public class ServerConfiguration {
  private final SocketAddress socketAddress;
  private final EventLoopGroup bossEventLoopGroup;
  private final EventLoopGroup workerEventLoopGroup;
  private final Executor executor;
  private final int flowControlWindowSizeBytes;
  private final int maxConcurrentCallsPerConnection;
  private final int maxMessageSizeBytes;
  private final Class<? extends ServerChannel> channelType;
  private final HandlerRegistry fallbackHandlerRegistry;

  public ServerConfiguration(SocketAddress socketAddress,
      EventLoopGroup bossEventLoopGroup, EventLoopGroup workerEventLoopGroup,
      Executor executor, int flowControlWindowSizeBytes, int maxConcurrentCallsPerConnection,
      int maxMessageSizeBytes,
      Class<? extends ServerChannel> channelType, HandlerRegistry fallbackHandlerRegistry) {
    this.socketAddress = socketAddress;
    this.bossEventLoopGroup = bossEventLoopGroup;
    this.workerEventLoopGroup = workerEventLoopGroup;
    this.executor = executor;
    this.flowControlWindowSizeBytes = flowControlWindowSizeBytes;
    this.maxConcurrentCallsPerConnection = maxConcurrentCallsPerConnection;
    this.maxMessageSizeBytes = maxMessageSizeBytes;
    this.channelType = channelType;
    this.fallbackHandlerRegistry = fallbackHandlerRegistry;
  }

  public SocketAddress socketAddress() {
    return socketAddress;
  }

  public EventLoopGroup bossEventLoopGroup() {
    return bossEventLoopGroup;
  }

  public EventLoopGroup workerEventLoopGroup() {
    return workerEventLoopGroup;
  }

  public Executor executor() {
    return executor;
  }

  public int flowControlWindowSizeBytes() {
    return flowControlWindowSizeBytes;
  }

  public int maxConcurrentCallsPerConnection() {
    return maxConcurrentCallsPerConnection;
  }

  public int maxMessageSizeBytes() {
    return maxMessageSizeBytes;
  }

  public Class<? extends ServerChannel> channelType() {
    return channelType;
  }

  public HandlerRegistry fallbackHandlerRegistry() {
    return fallbackHandlerRegistry;
  }

  public static ServerConfiguration createDefault(
      SocketAddress socketAddress, Transport transport) {
    return new ServerConfiguration(
        socketAddress,
        /* bossEventLoopGroup */ Util.createEventLoopGroup(1, transport),
        /* workerEventLoopGroup */ Util.createEventLoopGroup(4, transport),
//        Executors.newFixedThreadPool(5),
//        MoreExecutors.directExecutor(),
        new ForkJoinPool(4),
        NettyChannelBuilder.DEFAULT_FLOW_CONTROL_WINDOW,
        Integer.MAX_VALUE,
        /* maxMessageSizeBytes */ 4 * 1024 * 1024, // 4MB
        Util.getServerChannelClass(transport),
        /* fallbackHandlerRegistry */ null);
  }
}
