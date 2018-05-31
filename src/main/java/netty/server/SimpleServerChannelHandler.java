package netty.server;

import netty.data.RequestData;
import netty.data.ResponseData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class SimpleServerChannelHandler extends ChannelInboundHandlerAdapter {
  private ByteBuf tmp;

  private int receivedCount;

  @Override
  public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
    System.out.println("Handler added");
    tmp = ctx.alloc().buffer(4);
  }

  @Override
  public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
    System.out.println("Handler removed");
    tmp.release();
    tmp = null;
    ctx.close();
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    RequestData requestData = (RequestData) msg;
    ResponseData responseData = new ResponseData(requestData.getValue());
    ctx.writeAndFlush(responseData);
    debugLog();
  }

  private void debugLog() {
    receivedCount++;
    if (receivedCount % 100000 == 0) {
      System.out.println(String.format("Received: %,d", receivedCount));
    }
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    cause.printStackTrace();
    ctx.close();
  }
}
