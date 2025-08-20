package bank;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;

public class Transaction {
    private static final AtomicLong SEQ = new AtomicLong(1);

    public final long id;
    public final long accountId;
    public final TransactionType type;
    public final double amount;
    public final String note;
    public final Instant timestamp;

    // Private constructor so we control creation
    private Transaction(long id, long accountId, TransactionType type, double amount, String note, Instant timestamp) {
        this.id = id;
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
        this.note = note;
        this.timestamp = timestamp;
    }

    // ✅ Factory for NEW transactions
    public static Transaction of(long accountId, TransactionType type, double amount, String note) {
        return new Transaction(SEQ.getAndIncrement(), accountId, type, amount, note, Instant.now());
    }

    // ✅ Factory for REPLAY (from file store)
    public static Transaction replay(long id, long accountId, TransactionType type, double amount, String note, Instant timestamp) {
        return new Transaction(id, accountId, type, amount, note, timestamp);
    }

    @Override
    public String toString() {
        return "Transaction{id=" + id +
                ", accId=" + accountId +
                ", type=" + type +
                ", amount=" + amount +
                ", note='" + note + '\'' +
                ", ts=" + timestamp + '}';
    }
}
