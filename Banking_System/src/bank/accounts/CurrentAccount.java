package bank.accounts;

public class CurrentAccount extends Account {
    private final double overdraftLimit; // allowed negative balance

    public CurrentAccount(long id, long customerId, String accNo, double opening, double overdraftLimit) {
        super(id, customerId, accNo, opening);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        if (balance - amount < -overdraftLimit) throw new IllegalStateException("Overdraft limit exceeded");
        balance -= amount;
        transactions.add(bank.Transaction.of(accountId, bank.TransactionType.WITHDRAW, amount, "Withdraw (OD allowed)"));
    }

    @Override public String getType() { return "CURRENT"; }
}
