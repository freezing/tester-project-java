package io.freezing.async;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MailboxTester {
  private final OrderedExecutor orderedExecutor;
  private final List<MailboxBridge> mailboxes;
  private final Executor processingExecutor;
  private final Random random = new Random();

  static MailboxTester create(int throughput, int paralelism, int numActors) {
    OrderedExecutor orderedExecutor = OrderedExecutor.create(throughput, paralelism);
    List<MailboxBridge> mailboxes = new ArrayList<>();
    for (int i = 0; i < numActors; i++) {
      Mailbox mailbox = orderedExecutor.registerMailbox();
      MailboxBridge mb = MailboxBridge.create(mailbox);
      mailboxes.add(mb);
    }
    orderedExecutor.start();
    return new MailboxTester(orderedExecutor, mailboxes,
        Executors.newSingleThreadExecutor());
  }

  public MailboxTester(OrderedExecutor orderedExecutor, List<MailboxBridge> mailboxes,
      Executor processingExecutor) {
    this.orderedExecutor = orderedExecutor;
    this.mailboxes = mailboxes;
    this.processingExecutor = processingExecutor;
  }

  public void run() {
    int epoch = 0;
    while (true) {
      epoch++;
      final int currentEpoch = epoch;
//      List<MailboxBridge> picked = selectRandomMailboxes();
      mailboxes.forEach(bridge -> bridge.runOnce(currentEpoch));
    }
  }

  private List<MailboxBridge> selectRandomMailboxes() {
    int[] indexes = new int[mailboxes.size()];
    for (int i = 0; i < indexes.length; i++) {
      indexes[i] = i;
    }

    for (int i = 1; i < indexes.length; i++) {
      int j = random.nextInt(i);
      int tmp = indexes[i];
      indexes[i] = indexes[j];
      indexes[j] = tmp;
    }

    List<MailboxBridge> picked = new ArrayList<>();
    int pickSize = random.nextInt(indexes.length);
    for (int i = 0; i < pickSize; i++) {
      picked.add(mailboxes.get(indexes[i]));
    }
    return picked;
  }

  public static void main(String[] args) {
    MailboxTester.create(10, 5, 100).run();
  }
}
