package bank;

import bank.accounts.*;
import people.Customer;
import utils.FileStore;
import utils.IdGenerator;

import java.util.*;
import java.util.stream.Collectors;

public class Bank {
    private final Map<Long, Account> accounts = new HashMap<>();
    private final Map<Long, Customer> customers = new HashMap<>();
    private final FileStore store;

    public Bank(FileStore store) {
        this.store = store;
        // load from disk
        for (Account a : store.loadAccounts()) {
            accounts.put(a.getAccountId(), a);
        }
    }

    public Customer registerCustomer(String name, String email) {
        long id = IdGenerator.nextCustomerId();
        Customer c = new Customer(id, name, email);
        customers.put(id, c);
        return c;
    }

    public Account openSavings(long customerId, double opening, double rate) {
        long id = IdGenerator.nextAccountId();
        Account a = new SavingsAccount(id, customerId, IdGenerator.makeAccountNumber(id), opening, rate);
        accounts.put(id, a);
        persist();
        return a;
    }

    public Account openCurrent(long customerId, double opening, double odLimit) {
        long id = IdGenerator.nextAccountId();
        Account a = new CurrentAccount(id, customerId, IdGenerator.makeAccountNumber(id), opening, odLimit);
        accounts.put(id, a);
        persist();
        return a;
    }

    public Optional<Account> findById(long accountId) { return Optional.ofNullable(accounts.get(accountId)); }

    public List<Account> listAccounts() {
        return accounts.values().stream()
                .sorted(Comparator.comparingDouble(Account::getBalance).reversed())
                .collect(Collectors.toList());
    }

    public void deposit(long accId, double amount) {
        Account a = accounts.get(accId); if (a == null) throw new NoSuchElementException("Account not found");
        a.deposit(amount);
        store.appendTransactions(Collections.singletonList(a.getTransactions().get(a.getTransactions().size()-1)));
        persist();
    }

    public void withdraw(long accId, double amount) {
        Account a = accounts.get(accId); if (a == null) throw new NoSuchElementException("Account not found");
        a.withdraw(amount);
        store.appendTransactions(Collections.singletonList(a.getTransactions().get(a.getTransactions().size()-1)));
        persist();
    }

    public void transfer(long fromId, long toId, double amount) {
        if (fromId == toId) throw new IllegalArgumentException("Same accounts");
        Account from = accounts.get(fromId); Account to = accounts.get(toId);
        if (from == null || to == null) throw new NoSuchElementException("Account not found");

        from.withdraw(amount);
        from.getTransactions().add(Transaction.of(from.getAccountId(), TransactionType.TRANSFER_OUT, amount, "Transfer out"));
        to.deposit(amount);
        to.getTransactions().add(Transaction.of(to.getAccountId(), TransactionType.TRANSFER_IN, amount, "Transfer in"));

        List<Transaction> pair = Arrays.asList(
                from.getTransactions().get(from.getTransactions().size()-1),
                to.getTransactions().get(to.getTransactions().size()-1)
        );
        store.appendTransactions(pair);
        persist();
    }

    private void persist() { store.saveAccounts(accounts.values()); }
}
