import bank.Bank;
import bank.accounts.Account;
import people.Customer;
import utils.FileStore;
import web.WebServer;

public class Main {
    public static void main(String[] args) throws Exception {
        FileStore store = new FileStore("data");
        Bank bank = new Bank(store);

        // Seed demo data (run once; in real use, check if accounts exist)
        Customer c1 = bank.registerCustomer("Yash Sabne", "yash@example.com");
        Customer c2 = bank.registerCustomer("Deepak", "deepak@example.com");

        Account a1 = bank.openSavings(c1.getId(), 5000, 0.035);
        Account a2 = bank.openCurrent(c2.getId(), 2000, 2000);

        bank.deposit(a1.getAccountId(), 1500);
        bank.withdraw(a2.getAccountId(), 500);
        bank.transfer(a1.getAccountId(), a2.getAccountId(), 700);

        System.out.println("=== Accounts (desc by balance) ===");
        bank.listAccounts().forEach(System.out::println);

        // Optional mini-API
        if (args.length > 0 && args[0].equals("--web")) {
            new WebServer(bank).start(8080);
        }
    }
}
