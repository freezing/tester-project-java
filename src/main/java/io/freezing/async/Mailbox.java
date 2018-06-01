package io.freezing.async;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Mailbox {
  private final int id;
  private final ConcurrentLinkedQueue<Runnable> tasks;
  private volatile boolean idle = true;

  public Mailbox(int id) {
    this.id = id;
    this.tasks = new ConcurrentLinkedQueue<>();
  }

  public void enqueue(Runnable runnable) {
    tasks.add(runnable);
  }

  public void setIdle(boolean value) {
    idle = value;
  }

  public int id() {
    return id;
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
