package netty.codecs;

import netty.data.ResponseData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import java.util.List;

public class ResponseDataDecoder extends ReplayingDecoder<ResponseData> {
  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
    int value = in.readInt();
    ResponseData responseData = new ResponseData(value);
    out.add(responseData);
  }
}
