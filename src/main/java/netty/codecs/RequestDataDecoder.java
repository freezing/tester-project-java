package netty.codecs;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import netty.data.RequestData;
import java.util.List;

public class RequestDataDecoder extends ReplayingDecoder<RequestData> {
  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
    int value = in.readInt();
    int nameLength = in.readInt();
    String name = in.readCharSequence(nameLength, Codecs.CHARSET).toString();
    RequestData data = new RequestData(value, name);
    out.add(data);
  }
}
