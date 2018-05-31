package benchmark.grpc.common;

import org.HdrHistogram.Histogram;

public class MetricsHelper {// The histogram can record values between 1 microsecond and 1 min.
  public static final long HISTOGRAM_MAX_VALUE = 60000000L;

  // Value quantization will be no more than 1%. See the README of HdrHistogram for more details.
  public static final int HISTOGRAM_PRECISION = 2;

  public static Histogram newHistogram() {
    return newHistogram(HISTOGRAM_MAX_VALUE, HISTOGRAM_PRECISION);
  }

  public static Histogram newHistogram(long histogramMaxValue, int histogramPrecision) {
    return new Histogram(histogramMaxValue, histogramPrecision);
  }
}
