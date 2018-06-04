package grpc;

import benchmark.grpc.common.PayloadHelpers;
import io.freezing.benchmark.BenchmarkServiceGrpc.BenchmarkServiceImplBase;
import io.freezing.benchmark.Server.BenchmarkRequest;
import io.freezing.benchmark.Server.BenchmarkResponse;
import io.grpc.stub.ServerCallStreamObserver;
import io.grpc.stub.StreamObserver;
import java.util.concurrent.atomic.AtomicBoolean;

public class BenchmarkServiceImpl extends BenchmarkServiceImplBase {
  private static final boolean ENABLE_FLOW_CONTROL = false;

  public static BenchmarkServiceImpl create() {
    return new BenchmarkServiceImpl();
  }

  @Override
  public void unaryCall(BenchmarkRequest request,
      StreamObserver<BenchmarkResponse> responseObserver) {
    BenchmarkResponse response =
        PayloadHelpers.makeBenchmarkResponse(0, request.getServerPayloadSizeBytes());
    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }

  @Override
  public StreamObserver<BenchmarkRequest> streamingCall(
      StreamObserver<BenchmarkResponse> responseObserver) {
    final AtomicBoolean wasReady = new AtomicBoolean(false);
    final ServerCallStreamObserver<BenchmarkResponse> serverCallStreamObserver =
          (ServerCallStreamObserver<BenchmarkResponse>) responseObserver;
    if (ENABLE_FLOW_CONTROL) {
      serverCallStreamObserver.disableAutoInboundFlowControl();
      serverCallStreamObserver.setOnReadyHandler(() -> {
        if (serverCallStreamObserver.isReady() && wasReady.compareAndSet(false, true)) {
          serverCallStreamObserver.request(1);
        }
      });
    }

    return new StreamObserver<BenchmarkRequest>() {
      @Override
      public void onNext(BenchmarkRequest value) {
        // Do some work.
        int res = 0;
        for (int i = 0 ; i < value.getServerWorkIterations(); i++) {
          // Do nothing.
          res += i * i;
        }
        res = 1 + Math.abs(res);
        if (res > 0) {
          int processingRes = 0;
          for (int i = 0; i < value.getServerProcessingIterations(); i++) {
            processingRes += i * i;
          }
          processingRes = 1 + Math.abs(processingRes);
          if (processingRes > 0) {
            responseObserver.onNext(
                PayloadHelpers.makeBenchmarkResponse(value.getId(),
                    value.getServerPayloadSizeBytes()));
          } else {
            System.err.println("processingRes not > 0");
          }
        } else {
          System.err.println("Res not > 0");
        }

        if (ENABLE_FLOW_CONTROL) {
          if (serverCallStreamObserver.isReady()) {
            serverCallStreamObserver.request(1);
          } else {
            wasReady.set(false);
          }
        }
      }

      @Override
      public void onError(Throwable t) {
        responseObserver.onError(t);
      }

      @Override
      public void onCompleted() {
        responseObserver.onCompleted();
      }
    };
  }
}
