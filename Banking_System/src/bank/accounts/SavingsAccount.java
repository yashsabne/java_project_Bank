package bank.accounts;

public class SavingsAccount extends Account {
    private final double interestRate; // e.g., 0.035 (3.5%)

    public SavingsAccount(long id, long customerId, String accNo, double opening, double interestRate) {
        super(id, customerId, accNo, opening);
        this.interestRate = interestRate;
    }

    public void applyMonthlyInterest() {
        double interest = balance * (interestRate / 12.0);
        if (interest > 0) deposit(interest);
    }

    @Override public String getType() { return "SAVINGS"; }
}
