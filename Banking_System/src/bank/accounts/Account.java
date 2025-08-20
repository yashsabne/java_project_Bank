package bank.accounts;

import bank.Transaction;
import bank.TransactionType;
import java.util.ArrayList;
import java.util.List;

public abstract class Account {
    protected final long accountId;
    protected final long customerId;
    protected String accountNumber; // human-friendly
    protected double balance;
    protected final List<Transaction> transactions = new ArrayList<>();

    protected Account(long accountId, long customerId, String accountNumber, double openingBalance) {
        this.accountId = accountId;
        this.customerId = customerId;
        this.accountNumber = accountNumber;
        this.balance = openingBalance;
    }

    public long getAccountId() { return accountId; }
    public long getCustomerId() { return customerId; }
    public String getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }
    public List<Transaction> getTransactions() { return transactions; }

    public void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        balance += amount;
        transactions.add(Transaction.of(accountId, TransactionType.DEPOSIT, amount, "Deposit"));
    }

    public void withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        if (balance < amount) throw new IllegalStateException("Insufficient funds");
        balance -= amount;
        transactions.add(Transaction.of(accountId, TransactionType.WITHDRAW, amount, "Withdraw"));
    }

    public abstract String getType();

    @Override public String toString() {
        return getType()+"{id="+accountId+", number="+accountNumber+", balance="+balance+"}";
    }
}
