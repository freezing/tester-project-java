package netty.client;

import netty.data.RequestData;
import netty.data.ResponseData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.HdrHistogram.Histogram;

public class SimpleClientChannelHandler extends ChannelInboundHandlerAdapter {
  // The histogram can record values between 1 microsecond and 1 min.
  public static final long HISTOGRAM_MAX_VALUE = 60000000L;

  // Value quantization will be no more than 1%. See the README of HdrHistogram for more details.
  public static final int HISTOGRAM_PRECISION = 2;

  private static final int MAX_ITERATIONS = 1000000;
  private static final int BATCH_SIZE = Math.min(MAX_ITERATIONS, 10);
  private static final int WARMUP_ITERATIONS = MAX_ITERATIONS / 10;

  private final Executor executor;
  private final Histogram histogram;
  private final ConcurrentHashMap<Integer, Long> requestTimestampsNanos;

  private long startTimeNanos;

  private int sentCount;
  private volatile int receivedCount;

  public static SimpleClientChannelHandler create() {
    return new SimpleClientChannelHandler(
        Executors.newSingleThreadExecutor(),
        new Histogram(HISTOGRAM_MAX_VALUE, HISTOGRAM_PRECISION));
  }

  private SimpleClientChannelHandler(Executor executor, Histogram histogram) {
    this.executor = executor;
    this.histogram = histogram;
    this.requestTimestampsNanos = new ConcurrentHashMap<>();
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    executor.execute(() -> runLoadGenerator(ctx));
  }

  private void runLoadGenerator(ChannelHandlerContext ctx) {
    startMeasure();
    int currentBatch = 0;
    while (sentCount < MAX_ITERATIONS) {
      currentBatch++;
      RequestData requestData = makeRequest(sentCount);
      if (currentBatch % BATCH_SIZE == 0) {
        ctx.writeAndFlush(requestData);
      } else {
        ctx.write(requestData);
      }
      requestTimestampsNanos.put(sentCount, System.nanoTime());
      sentCount++;
    }
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) {
    ResponseData responseData = (ResponseData) msg;
    if (!requestTimestampsNanos.containsKey(responseData.getValue())) {
      System.out.println("Can't find: " + responseData.getValue());
      return;
    }
    long sentTimeNanos = requestTimestampsNanos.get(responseData.getValue());
    long latencyNanos = System.nanoTime() - sentTimeNanos;
    histogram.recordValue(latencyNanos / 1000L);
    receivedCount++;
    debugLog();

    if (receivedCount >= sentCount) {
      ctx.close();
      endMeasure();
    }
  }

  private void debugLog() {
    if (receivedCount % 100000 == 0) {
      System.out.println(String.format("Received: %d", receivedCount));
    }
  }

  private void startMeasure() {
    startTimeNanos = System.nanoTime();
  }

  private void endMeasure() {
    System.out.println(System.currentTimeMillis());
    long endTimeNanos = System.nanoTime();
    long elapsedTimeNanos = endTimeNanos - startTimeNanos;
    printStats(histogram, elapsedTimeNanos);
  }

  private void printStats(Histogram histogram, long elapsedTimeNanos) {
    long latency50 = histogram.getValueAtPercentile(50);
    long latency90 = histogram.getValueAtPercentile(90);
    long latency95 = histogram.getValueAtPercentile(95);
    long latency99 = histogram.getValueAtPercentile(99);
    long latency999 = histogram.getValueAtPercentile(99.9);
    long latencyMax = histogram.getValueAtPercentile(100);
    long queriesPerSecond = histogram.getTotalCount() * 1000000000L / elapsedTimeNanos;

    StringBuilder values = new StringBuilder();
    values
        .append("50%ile Latency (in micros):     ").append(String.format("%,d", latency50)).append('\n')
        .append("90%ile Latency (in micros):     ").append(String.format("%,d", latency90)).append('\n')
        .append("95%ile Latency (in micros):     ").append(String.format("%,d", latency95)).append('\n')
        .append("99%ile Latency (in micros):     ").append(String.format("%,d", latency99)).append('\n')
        .append("99.9%ile Latency (in micros):   ").append(String.format("%,d", latency999)).append('\n')
        .append("Maximum Latency (in micros):    ").append(String.format("%,d", latencyMax)).append('\n')
        .append("QPS:                            ").append(String.format("%,d", queriesPerSecond)).append('\n');
    System.out.println(values);
  }

  private static RequestData makeRequest(int currentIteration) {
    return new RequestData(currentIteration, String.format("request-number-%d", currentIteration));
  }
}
