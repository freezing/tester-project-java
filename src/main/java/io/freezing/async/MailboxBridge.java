package io.freezing.async;

import com.google.common.base.Preconditions;

public class MailboxBridge {
  private final Mailbox mailbox;
  private long id;

  public static MailboxBridge create() {
    return new MailboxBridge(new Mailbox());
  }

  private MailboxBridge(Mailbox mailbox) {
    this.mailbox = mailbox;
  }

  public Mailbox getMailbox() {
    return mailbox;
  }

  public void runOnce(long epoch) {
    mailbox.enqueue(() -> {
//    if (id + 1 != epoch) {
//      System.err.println("Not in order. ID = " + id + "  EPOCH = " + epoch);
//    }
      for (long i = 0; i < epoch; i++) {
        id += Math.abs((long) Math.sqrt(epoch) + epoch);
        if (id < 0) {
          System.out.println("Test");
        }
      }
    });
  }
}
