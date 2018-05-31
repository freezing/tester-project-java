package netty.server;

import benchmark.grpc.Transport;
import netty.Util;
import netty.codecs.RequestDataDecoder;
import netty.codecs.ResponseDataEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;

public class NettyServer {
  public static final int DEFAULT_PORT = 8080;
  public static final String DEFAULT_HOSTNAME = "localhost";

  private final int port;

  public NettyServer(int port) {
    this.port = port;
  }

  public static void main(String[] args) throws InterruptedException {
    int port = args.length > 0 ? Integer.parseInt(args[0]) : DEFAULT_PORT;
    new NettyServer(port).run();
  }

  public void run() throws InterruptedException {
    Transport transport = Transport.NETTY_NIO;
    EventLoopGroup bossGroup = Util.createEventLoopGroup(1, transport);
    EventLoopGroup workerGroup = Util.createEventLoopGroup(0, transport);
    try {
      ServerBootstrap serverBootstrap = new ServerBootstrap()
          .group(bossGroup, workerGroup)
          .channel(Util.getServerChannelClass(transport))
          .childHandler(new ChildHandler())
          .option(ChannelOption.SO_BACKLOG, 128)
          .childOption(ChannelOption.SO_KEEPALIVE, true);
      ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
      channelFuture.channel().closeFuture().sync();
    } finally {
      workerGroup.shutdownGracefully();
      bossGroup.shutdownGracefully();
    }
  }

  private static class ChildHandler extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
      channel.pipeline()
          .addLast(
              new RequestDataDecoder(),
              new ResponseDataEncoder(),
              new SimpleServerChannelHandler());
    }
  }
}
