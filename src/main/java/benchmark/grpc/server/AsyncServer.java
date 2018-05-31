package benchmark.grpc.server;

import benchmark.grpc.Transport;
import benchmark.grpc.common.ThreadHelpers;
import grpc.BenchmarkServiceImpl;
import io.grpc.Server;
import io.grpc.netty.NettyServerBuilder;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class AsyncServer {
  private final ServerConfiguration serverConfiguration;

  public AsyncServer(ServerConfiguration serverConfiguration) {
    this.serverConfiguration = serverConfiguration;
  }

  public static void main(String[] args) throws InterruptedException, IOException {
    SocketAddress socketAddress = new InetSocketAddress("localhost", 9999);
    Transport transport = Transport.NETTY_NIO;
    ServerConfiguration serverConfiguration =
        ServerConfiguration.createDefault(socketAddress, transport);
    new AsyncServer(serverConfiguration).run();
  }

  private void run() throws InterruptedException, IOException {
    final Server server = NettyServerBuilder
        .forAddress(serverConfiguration.socketAddress())
        .bossEventLoopGroup(serverConfiguration.bossEventLoopGroup())
        .workerEventLoopGroup(serverConfiguration.workerEventLoopGroup())
        .executor(serverConfiguration.executor())
        .channelType(serverConfiguration.channelType())
        .flowControlWindow(serverConfiguration.flowControlWindowSizeBytes())
        .maxConcurrentCallsPerConnection(serverConfiguration.maxConcurrentCallsPerConnection())
        .maxMessageSize(serverConfiguration.maxMessageSizeBytes())
        .fallbackHandlerRegistry(serverConfiguration.fallbackHandlerRegistry())
        .addService(BenchmarkServiceImpl.create(serverConfiguration.numProcessingThreads()))
        .build();

    server.start();
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
        try {
          System.out.println("QPS Server shutting down");
          ThreadHelpers.printThreadStats();
          server.shutdown();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }));
    server.awaitTermination();
  }
}
