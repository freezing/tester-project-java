package io.freezing.async;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Mailbox {
  private final ConcurrentLinkedQueue<Runnable> tasks;
  private volatile boolean idle = true;

  public Mailbox() {
    this.tasks = new ConcurrentLinkedQueue<>();
  }

  public void enqueue(Runnable runnable) {
    tasks.add(runnable);
  }

  public void setIdle(boolean value) {
    idle = value;
  }

  public boolean isIdle() {
    return idle;
  }

  public Runnable poll() {
    return tasks.poll();
  }

  public boolean isEmpty() {
    return tasks.isEmpty();
  }
}
