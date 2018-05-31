package netty.codecs;

import netty.data.RequestData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class RequestDataEncoder extends MessageToByteEncoder<RequestData> {
  @Override
  protected void encode(ChannelHandlerContext ctx, RequestData msg, ByteBuf out) throws Exception {
    out.writeInt(msg.getValue());
    out.writeInt(msg.getName().length());
    out.writeCharSequence(msg.getName(), Codecs.CHARSET);
  }
}
