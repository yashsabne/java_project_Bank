package utils;

import bank.Transaction;
import bank.TransactionType;
import bank.accounts.Account;
import bank.accounts.SavingsAccount;
import bank.accounts.CurrentAccount;

import java.io.*;
import java.nio.file.*;
import java.time.Instant;
import java.util.*;

public final class FileStore {
    private final Path accountsPath;
    private final Path txPath;

    public FileStore(String dataDir) {
        this.accountsPath = Paths.get(dataDir, "accounts.db");
        this.txPath = Paths.get(dataDir, "transactions.db");
        try {
            Files.createDirectories(Paths.get(dataDir));
            if (!Files.exists(accountsPath)) Files.createFile(accountsPath);
            if (!Files.exists(txPath)) Files.createFile(txPath);
        } catch (IOException e) { throw new RuntimeException(e); }
    }

    // persist accounts
    public void saveAccounts(Collection<Account> accounts) {
        try (BufferedWriter bw = Files.newBufferedWriter(accountsPath)) {
            for (Account a : accounts) {
                String line = String.join("|",
                        a.getType(),
                        String.valueOf(a.getAccountId()),
                        String.valueOf(a.getCustomerId()),
                        a.getAccountNumber(),
                        String.valueOf(a.getBalance())
                );
                bw.write(line); bw.newLine();
            }
        } catch (IOException e) { throw new RuntimeException(e); }
    }

    public List<Account> loadAccounts() {
        List<Account> list = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(accountsPath)) {
            String ln;
            while ((ln = br.readLine()) != null) {
                if (ln.isBlank()) continue;
                String[] p = ln.split("\\|");
                String type = p[0];
                long accId = Long.parseLong(p[1]);
                long custId = Long.parseLong(p[2]);
                String accNo = p[3];
                double bal = Double.parseDouble(p[4]);

                if ("SAVINGS".equals(type)) {
                    list.add(new SavingsAccount(accId, custId, accNo, bal, 0.035));
                } else {
                    list.add(new CurrentAccount(accId, custId, accNo, bal, 2000.0));
                }
            }
        } catch (IOException e) { throw new RuntimeException(e); }
        return list;
    }

    // persist transactions
    public void appendTransactions(List<Transaction> txs) {
        try (BufferedWriter bw = Files.newBufferedWriter(txPath, StandardOpenOption.APPEND)) {
            for (Transaction t : txs) {
                String line = String.join("|",
                        String.valueOf(t.id),
                        String.valueOf(t.accountId),
                        t.type.name(),
                        String.valueOf(t.amount),
                        String.valueOf(t.timestamp.toEpochMilli())
                );
                bw.write(line); bw.newLine();
            }
        } catch (IOException e) { throw new RuntimeException(e); }
    }

    public List<Transaction> loadTransactions() {
        List<Transaction> list = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(txPath)) {
            String ln;
            while ((ln = br.readLine()) != null) {
                if (ln.isBlank()) continue;
                String[] p = ln.split("\\|");
                long id = Long.parseLong(p[0]);
                long accId = Long.parseLong(p[1]);
                TransactionType type = TransactionType.valueOf(p[2]);
                double amount = Double.parseDouble(p[3]);
                Instant ts = Instant.ofEpochMilli(Long.parseLong(p[4]));
                // replay-only: lightweight object (we won’t expose id/timestamp publicly here)
                list.add(Transaction.replay(id, accId, type, amount, "replayed", ts));

            }
        } catch (IOException e) { throw new RuntimeException(e); }
        return list;
    }
}
