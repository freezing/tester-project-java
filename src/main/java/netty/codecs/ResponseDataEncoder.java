package netty.codecs;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import netty.data.ResponseData;

public class ResponseDataEncoder extends MessageToByteEncoder<ResponseData> {
  @Override
  protected void encode(ChannelHandlerContext ctx, ResponseData msg, ByteBuf out) throws Exception {
    out.writeInt(msg.getValue());
  }
}
