package io.freezing.async;

import com.google.common.util.concurrent.UncaughtExceptionHandlers;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import javax.annotation.concurrent.GuardedBy;

public class OrderedExecutor {
  private static final long DISPATCHER_LOOP_SLEEP_TIME_IF_NO_EVENTS_MILLISECONDS = 10;

  private final HashMap<Integer, Mailbox> mailboxes;
  private final HashMap<Integer, Mailbox> toAddMailboxes;
  private final Set<Integer> toRemoveMailboxes;
  private final int throughput;
  private final Executor dispatcherExecutor;
  private final ExecutorService workerPool;

  private volatile boolean toAddMailboxesPresent;
  private volatile boolean toRemoveMailboxesPresent;

  @GuardedBy("this")
  private int nextMailboxId;

  public static OrderedExecutor create(int throughput, int paralelism) {
    return new OrderedExecutor(throughput,
        Executors.newSingleThreadExecutor(),
        new ForkJoinPool(paralelism, ForkJoinPool.defaultForkJoinWorkerThreadFactory,
            UncaughtExceptionHandlers.systemExit(),
            /* async */ true));
  }

  private OrderedExecutor(int throughput,
      Executor dispatcherExecutor,
      ExecutorService workerPool) {
    this.throughput = throughput;
    this.workerPool = workerPool;
    this.dispatcherExecutor = dispatcherExecutor;
    this.mailboxes = new HashMap<>();
    this.toAddMailboxes = new HashMap<>();
    this.toRemoveMailboxes = new HashSet<>();
    this.nextMailboxId = 0;
    this.toAddMailboxesPresent = false;
    this.toRemoveMailboxesPresent = false;
  }

  public synchronized Mailbox registerMailbox() {
    Mailbox mailbox = new Mailbox(nextMailboxId++);
    toAddMailboxes.put(mailbox.id(), mailbox);
    toAddMailboxesPresent = true;
    return mailbox;
  }

  public synchronized void unregisterMailbox(Mailbox mailbox) {
    toRemoveMailboxes.add(mailbox.id());
    toRemoveMailboxesPresent = true;
  }

  public void start() {
    dispatcherExecutor.execute(this::run);
  }

  private void run() {
    while (true) {
      boolean doneSomeWork = false;
      for (Mailbox mailbox : mailboxes.values()) {
        doneSomeWork |= tryScheduleMailbox(mailbox);
      }
      doneSomeWork |= tryRegisterPendingMailboxes();
      doneSomeWork |= tryUnregisterPendingMailboxes();

      if (!doneSomeWork) {
        try {
          Thread.sleep(DISPATCHER_LOOP_SLEEP_TIME_IF_NO_EVENTS_MILLISECONDS);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  private boolean tryScheduleMailbox(Mailbox mailbox) {
    if (!mailbox.isIdle() || mailbox.isEmpty()) {
      return false;
    }
    mailbox.setIdle(false);
    workerPool.execute(() -> {
      for (int i = 0; i < throughput; i++) {
        Runnable runnable = mailbox.poll();
        if (runnable == null) {
          break;
        }
        runnable.run();
      }
      mailbox.setIdle(true);
    });
    return true;
  }

  private boolean tryRegisterPendingMailboxes() {
    if (!toAddMailboxesPresent) {
      return false;
    }
    synchronized (this) {
      for (Mailbox mailbox : toAddMailboxes.values()) {
        mailboxes.put(mailbox.id(), mailbox);
      }
      toAddMailboxes.clear();
    }
    toAddMailboxesPresent = false;
    return true;
  }

  private boolean tryUnregisterPendingMailboxes() {
    if (!toRemoveMailboxesPresent) {
      return false;
    }
    synchronized (this) {
      for (int mailboxId : toRemoveMailboxes) {
        mailboxes.remove(mailboxId);
      }
      toRemoveMailboxes.clear();
    }
    return true;
  }
}
