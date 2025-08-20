package utils;

import java.util.concurrent.atomic.AtomicLong;

public final class IdGenerator {
    private static final AtomicLong ACC_ID = new AtomicLong(10001);
    private static final AtomicLong CUST_ID = new AtomicLong(5001);

    public static long nextAccountId() { return ACC_ID.getAndIncrement(); }
    public static long nextCustomerId() { return CUST_ID.getAndIncrement(); }

    public static String makeAccountNumber(long id) { return "AC" + id; }

    private IdGenerator() {}
}
