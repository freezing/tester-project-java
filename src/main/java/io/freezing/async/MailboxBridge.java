package io.freezing.async;

public class MailboxBridge {
  private final Mailbox mailbox;
  private long id;

  public static MailboxBridge create(Mailbox mailbox) {
    return new MailboxBridge(mailbox);
  }

  private MailboxBridge(Mailbox mailbox) {
    this.mailbox = mailbox;
  }

  public void runOnce(long epoch) {
    mailbox.enqueue(() -> {
      for (long i = 0; i < epoch; i++) {
        id += Math.abs((long) Math.sqrt(epoch) + epoch);
        // NOTE(nikola): Not sure if Java compiler is smart to optimize if variable is not used,
        // therefore to ensure it's not doing some optimization and the above loop is actually
        // happening, do some std output that will never happen.
        if (id < 0) {
          System.out.println("Test");
        }
      }
    });
  }
}
