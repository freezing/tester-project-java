package benchmark.grpc.client.tmp;

import benchmark.grpc.common.PayloadHelpers;
import io.freezing.benchmark.BenchmarkServiceGrpc;
import io.freezing.benchmark.BenchmarkServiceGrpc.BenchmarkServiceStub;
import io.freezing.benchmark.Server.BenchmarkRequest;
import io.freezing.benchmark.Server.BenchmarkResponse;
import io.grpc.ManagedChannel;
import io.grpc.netty.NettyChannelBuilder;
import io.grpc.stub.StreamObserver;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.CountDownLatch;

public class TmpClient {
  void run() throws InterruptedException {
    final BenchmarkRequest request = PayloadHelpers.makeBenchmarkRequest(100, 100);

    CountDownLatch latch = new CountDownLatch(1);
    SocketAddress serverAddress = new InetSocketAddress("localhost", 9999);
    ManagedChannel channel = NettyChannelBuilder.forAddress(serverAddress)
        .usePlaintext()
        .build();
    BenchmarkServiceStub stub = BenchmarkServiceGrpc.newStub(channel);

    StreamObserver<BenchmarkRequest> requestStream = stub.streamingCall(
        new StreamObserver<BenchmarkResponse>() {
          @Override
          public void onNext(BenchmarkResponse value) {
            System.out.println(value);
          }

          @Override
          public void onError(Throwable t) {
            t.printStackTrace();
            latch.countDown();
          }

          @Override
          public void onCompleted() {
            System.out.println("Completed");
            latch.countDown();
          }
        });
    requestStream.onNext(request);
    requestStream.onNext(request);
    requestStream.onCompleted();
    latch.await();
  }

  public static void main(String[] args) throws InterruptedException {
    new TmpClient().run();
  }
}
