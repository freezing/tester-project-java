package benchmark.grpc.client;

import benchmark.grpc.common.MetricsHelper;
import com.google.common.util.concurrent.SettableFuture;
import io.freezing.benchmark.Server.BenchmarkRequest;
import io.freezing.benchmark.Server.BenchmarkResponse;
import io.grpc.stub.StreamObserver;
import org.HdrHistogram.Histogram;

/**
 * Sends a request to the server as soon as it receives the response to the previous request.
 * It assumes that there is an in-flight request already in progress.
 */
public class BenchmarkResponseObserver implements StreamObserver<BenchmarkResponse> {
  private final SettableFuture<Histogram> histogramFuture;
  private final Histogram histogram;
  private final BenchmarkRequest benchmarkRequest;
  private final long benchmarkFinishTimeNanos;
  // This is always populated because there is an in-flight request when this class is instantiated.
  private long lastCallNanos;
  // Cannot be set in the constructor because it's created before the request stream.
  private StreamObserver<BenchmarkRequest> requestStream;
  private boolean isCompleted;

  public BenchmarkResponseObserver(
      SettableFuture<Histogram> histogramFuture,
      BenchmarkRequest benchmarkRequest,
      long lastCallNanos,
      long benchmarkFinishTimeNanos) {
    this.histogramFuture = histogramFuture;
    this.histogram = MetricsHelper.newHistogram();
    this.benchmarkRequest = benchmarkRequest;
    this.lastCallNanos = lastCallNanos;
    this.benchmarkFinishTimeNanos = benchmarkFinishTimeNanos;
  }

  public void setRequestStream(
      StreamObserver<BenchmarkRequest> requestStream) {
    this.requestStream = requestStream;
  }

  @Override
  public void onNext(BenchmarkResponse value) {
    long nowNanos = System.nanoTime();
    long latencyNanos = nowNanos - lastCallNanos;
    // NOTE(nikola): Measure in microseconds.
    histogram.recordValue(latencyNanos / 1000L);
    lastCallNanos = nowNanos;

    if (nowNanos >= benchmarkFinishTimeNanos) {
      if (!isCompleted) {
        isCompleted = true;
        requestStream.onCompleted();
      }
      return;
    }
    requestStream.onNext(benchmarkRequest);
  }

  @Override
  public void onError(Throwable t) {
    t.printStackTrace();
    histogramFuture.setException(t);
  }

  @Override
  public void onCompleted() {
    histogramFuture.set(histogram);
  }
}
