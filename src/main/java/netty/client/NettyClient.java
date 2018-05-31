package netty.client;

import benchmark.grpc.Transport;
import netty.Util;
import netty.codecs.RequestDataEncoder;
import netty.codecs.ResponseDataDecoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import netty.server.NettyServer;

public class NettyClient {
  private final String hostname;
  private final int port;

  public NettyClient(String hostname, int port) {
    this.hostname = hostname;
    this.port = port;
  }

  public static void main(String[] args) throws InterruptedException {
    int port = args.length > 0 ? Integer.parseInt(args[0]) : NettyServer.DEFAULT_PORT;
    String hostname = args.length > 1 ? args[1] : NettyServer.DEFAULT_HOSTNAME;
    new NettyClient(hostname, port).run();
  }

  public void run() throws InterruptedException {
    Transport transport = Transport.NETTY_NIO;
    EventLoopGroup workerGroup = Util.createEventLoopGroup(0, transport);
    try {
      Bootstrap bootstrap = new Bootstrap()
          .group(workerGroup)
          .channel(Util.getClientChannelClass(transport))
          .option(ChannelOption.SO_KEEPALIVE, true)
          .handler(new Handler());
      ChannelFuture channelFuture = bootstrap.connect(hostname, port);
      channelFuture.sync();
      channelFuture.channel().closeFuture().sync();
    } finally {
      workerGroup.shutdownGracefully();
    }
  }

  private static class Handler extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
      channel.pipeline()
          .addLast(
              new RequestDataEncoder(),
              new ResponseDataDecoder(),
              SimpleClientChannelHandler.create());
    }
  }
}
