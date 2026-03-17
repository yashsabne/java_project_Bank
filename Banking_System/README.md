---

# 📄 README.md (upload to GitHub)

Here’s a **professional README.md** you can drop into your project root:

```markdown
# 🏦 Java Banking System

A modular **Banking System** implemented in **pure Java** (without frameworks).  
The project demonstrates strong **Object-Oriented Programming (OOP)** principles, **Collections**, **File I/O**, and even a lightweight **REST API** using Java's built-in `HttpServer`.

---

## 🚀 Features
- Create **Savings** and **Current** accounts
- Deposit, Withdraw, and Transfer money (with validation)
- Interest calculation for savings accounts
- Overdraft handling for current accounts
- Transaction history (persistent across runs)
- File-based storage for accounts & transactions
- Admin can create/delete accounts
- JSON REST API (no Spring Boot needed):
  - `GET /accounts` → list all accounts
  - `POST /transfer` → transfer between accounts

---

## 🛠️ Technologies Used
- **Java 17+**
- **OOP Principles:** Abstraction, Inheritance, Polymorphism, Encapsulation
- **Collections Framework:** `HashMap`, `ArrayList`, `Comparator`
- **File Handling:** `BufferedReader`, `BufferedWriter`, `Paths`
- **Concurrency:** `AtomicLong` for unique IDs
- **Networking (optional):** `HttpServer` for REST API

---

## 📂 Project Structure
```

BankingSystem/
│── src/
│   ├── Main.java
│   ├── bank/
│   │   ├── Bank.java
│   │   ├── Transaction.java
│   │   ├── TransactionType.java
│   │   ├── accounts/
│   │       ├── Account.java
│   │       ├── SavingsAccount.java
│   │       ├── CurrentAccount.java
│   ├── people/
│   │   ├── Person.java
│   │   ├── Customer.java
│   │   ├── Admin.java
│   ├── utils/
│   │   ├── FileStore.java
│   │   ├── IdGenerator.java
│   │   ├── Json.java
│   ├── web/
│       ├── WebServer.java
│── data/
│   ├── accounts.db
│   ├── transactions.db

````

---

## ⚡ How to Run

### Compile
```bash
javac -d out src\Main.java src\bank\*.java src\bank\accounts\*.java src\people\*.java src\utils\*.java src\web\*.java
````

### Run CLI mode

```bash
java -cp out Main
```

### Run Web API mode

```bash
java -cp out Main --web
```

#### Example API calls

* `GET http://localhost:8080/accounts`
* `POST http://localhost:8080/transfer`
  Body: `from=10001&to=10002&amount=500`

---

## 📊 Example Output

```
=== Accounts (desc by balance) ===
SAVINGS{id=10001, number=AC10001, balance=5800.0}
CURRENT{id=10002, number=AC10002, balance=2200.0}
```

---

## 🧠 Learning Objectives

* How to structure a **modular Java project** with packages
* Applying all **OOP principles** in a real-world system
* Using **Collections** effectively for account/transaction management
* Implementing **persistence** with File I/O
* Building a **REST API** without frameworks (lightweight server)

---

## 📌 Future Improvements

* Add user authentication (username/password)
* Support for multiple currencies
* GUI or web frontend
* Advanced reporting (top accounts, interest reports)

---

## 👤 Author

**Yash Navnath Sabne**
B.Tech (ECE) @ NIT Surat | Full-stack & Java Developer
[LinkedIn](https://www.linkedin.com/in/yash-sabne-77239b287/) | [GitHub](https://github.com/yashsabne)

```
 
