package io.freezing.async;

import com.google.common.util.concurrent.UncaughtExceptionHandlers;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class OrderedExecutor {
  private final List<Mailbox> mailboxes;
  private final int throughput;
  private final Executor executor;
  private final ForkJoinPool forkJoinPool;

  public static OrderedExecutor create(int throughput, int paralelism) {
    return new OrderedExecutor(throughput, paralelism,
        Executors.newSingleThreadExecutor());
  }

  private OrderedExecutor(int throughput, int paralelism, Executor executor) {
    this.throughput = throughput;
    this.mailboxes = new ArrayList<>();
    this.forkJoinPool = new ForkJoinPool(paralelism, ForkJoinPool.defaultForkJoinWorkerThreadFactory,
        UncaughtExceptionHandlers.systemExit(), true);
    this.executor = Executors.newSingleThreadExecutor();
  }

  public void registerMailbox(Mailbox mailbox) {
    mailboxes.add(mailbox);
  }

  public void start() {
    executor.execute(this::scheduleMailboxes);
  }

  private void scheduleMailboxes() {
    while (true) {
      boolean scheduledAtLeastOne = false;
      for (Mailbox mailbox : mailboxes) {
        if (mailbox.isIdle() && !mailbox.isEmpty()) {
          scheduleMailbox(mailbox);
          scheduledAtLeastOne = true;
        }
      }
      if (!scheduledAtLeastOne) {
        try {
          Thread.sleep(10);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  private void scheduleMailbox(Mailbox mailbox) {
    mailbox.setIdle(false);
    forkJoinPool.execute(() -> {
      for (long i = 0; i < throughput; i++) {
        Runnable runnable = mailbox.poll();
        if (runnable == null) {
          break;
        }
        runnable.run();
      }
      mailbox.setIdle(true);
    });
  }
}
