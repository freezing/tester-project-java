package benchmark.grpc.common;

import com.google.protobuf.ByteString;
import io.freezing.benchmark.Server.BenchmarkRequest;
import io.freezing.benchmark.Server.BenchmarkResponse;
import java.util.Random;

public class PayloadHelpers {
  public static BenchmarkRequest makeBenchmarkRequest(
      int clientPayloadSizeBytes, int serverPayloadSizeBytes) {
    return BenchmarkRequest.newBuilder()
        .setServerPayloadSizeBytes(serverPayloadSizeBytes)
        .setPayload(makeRandomPayload(clientPayloadSizeBytes))
        .build();
  }

  public static BenchmarkResponse makeBenchmarkResponse(int payloadSizeBytes) {
    return BenchmarkResponse.newBuilder()
        .setPayload(PayloadHelpers.makeRandomPayload(payloadSizeBytes))
        .build();
  }

  public static ByteString makeRandomPayload(int payloadSizeBytes) {
    Random random = new Random();
    return makeRandomPayload(payloadSizeBytes, random);
  }

  public static ByteString makeRandomPayload(int payloadSizeBytes, Random random) {
    byte[] payloadBytes = new byte[payloadSizeBytes];
    random.nextBytes(payloadBytes);
    return ByteString.copyFrom(payloadBytes);
  }
}
