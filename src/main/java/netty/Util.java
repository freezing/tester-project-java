package netty;

import benchmark.grpc.Transport;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.kqueue.KQueueEventLoopGroup;
import io.netty.channel.kqueue.KQueueServerSocketChannel;
import io.netty.channel.kqueue.KQueueSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Util {
  public static EventLoopGroup createEventLoopGroup(int nThreads, Transport transport) {
    switch (transport) {
      case NETTY_NIO:
        return new NioEventLoopGroup(nThreads);
      case KQUEUE:
        return new KQueueEventLoopGroup(nThreads);
      default:
        throw new UnknownTransportException(transport);
    }
  }

  public static Class<? extends ServerChannel> getServerChannelClass(Transport transport) {
    switch (transport) {
      case NETTY_NIO:
        return NioServerSocketChannel.class;
      case KQUEUE:
        return KQueueServerSocketChannel.class;
      default:
        throw new UnknownTransportException(transport);
    }
  }

  public static Class<? extends SocketChannel> getClientChannelClass(Transport transport) {
    switch (transport) {
      case NETTY_NIO:
        return NioSocketChannel.class;
      case KQUEUE:
        return KQueueSocketChannel.class;
      default:
        throw new UnknownTransportException(transport);
    }
  }

  private static class UnknownTransportException extends RuntimeException {
    UnknownTransportException(Transport transport) {
      super(String.format("Unknown tranport: %s", transport));
    }
  }
}
