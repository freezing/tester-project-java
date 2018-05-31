package benchmark.grpc.common;

import java.util.stream.Collectors;

public class ThreadHelpers {
  public static void printThreadStats() {
    System.out.println("# of active threads: " + Thread.activeCount());
    String threadNames = Thread.getAllStackTraces().keySet().stream()
        .map(Thread::getName)
        .sorted()
        .collect(Collectors.joining(", "));
    System.out.println(threadNames);
  }
}
