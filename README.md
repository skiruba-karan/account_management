# README
# 🏦 Banking System API

A comprehensive Spring Boot project simulating a real-world **Banking Backend System**, built with clean architecture, SOLID principles, DTO-based design, and layered structure.  
This system covers account operations, fund transfers, monthly statements, loan processing, admin dashboard analytics, and multiple financial calculators.

This project also focuses on **transaction safety**, **data consistency**, **strategy-based business logic**, and **extensibility**.

---

# ✨ Features

## 1️⃣ Account Management

- Create new accounts  
- Deposit funds  
- Withdraw funds  
- Fetch account details  
- View all accounts  

### ✔ Endpoints
POST /accounts
GET /accounts/{id}
POST /accounts/{id}/deposit
POST /accounts/{id}/withdraw
GET /accounts
---

## 2️⃣ Fund Transfers

- Transfer funds between two accounts  
- Validates sufficient balance  
- Prevents transfer to same account  
- Uses `@Transactional` to ensure atomic debit/credit  

### ✔ Endpoint
POST /accounts/transfer
---

## 3️⃣ Transaction History

- Logs every **deposit**, **withdrawal**, and **transfer**  
- Fetches last N transactions for any account  

### ✔ Endpoint
GET /accounts/{id}/transactions
---

## 4️⃣ Simple Interest Calculator

- Computes simple interest  
- Returns interest + total payable amount  
- Validates all input fields  

### ✔ Endpoint
POST /calculateInterest
---

## 5️⃣ Loan Eligibility Checker

Evaluates if a user qualifies for a loan based on:

- Age  
- Annual income  
- Credit score  
- Existing loan amount  

Applies rules:
- Min age: 21  
- Income > 3,00,000  
- Credit score ≥ 700  
- Loan-to-income ratio < 40%

### ✔ Endpoint
POST /loanEligibility
---

## 6️⃣ Fixed Deposit (FD) Calculator — Compound Interest

- Calculates maturity using **compound interest**  
- Supports **premature withdrawal**  
- Applies **Penalty Strategy Pattern** (extensible)  
- Clean separation of business rules  

### ✔ Endpoint
POST /fixedDeposit
---

## 7️⃣ Monthly Account Statement

Generates monthly statement including:

- Opening balance  
- Total deposits  
- Total withdrawals  
- Closing balance  
- Month summary  

Additional features:

- Export statement as **CSV**  
- Export statement as **PDF**  
- Optional: Email delivery  

### ✔ Endpoint
GET /statement/{accountId}?month=MM&year=YYYY
---

## 8️⃣ Admin Dashboard Analytics

Admin reporting system using **JPQL aggregation** + optional caching.

Includes:

- Total customers  
- Total account deposits  
- Top-performing accounts (> ₹1,00,000)  
- Loan summary: total active loans & total loan amount  
- Full consolidated report  

### ✔ Endpoints
GET /admin/totalCustomers
GET /admin/totalDeposits
GET /admin/topAccounts
GET /admin/loanSummary
GET /admin/fullReport
---

## 9️⃣ Loan Management Module

Handles the loan lifecycle:

- Create a loan  
- Fetch loan details  
- Fetch all loans or filter by account  
- Close a loan  

### ✔ Endpoints
POST /loans
GET /loans
GET /loans/{loanId}
PUT /loans/{loanId}/close
---

# ⚙️ Tech Stack

| Component | Technology |
|----------|------------|
| Backend | Spring Boot 3.x |
| Language | Java 17 |
| Database | MySQL / H2 |
| ORM | Spring Data JPA |
| Validation | Jakarta Bean Validation |
| Build Tool | Maven |
| Testing | JUnit 5 |
| API Docs (Optional) | Swagger / Springdoc |

---

# 🧠 Architecture

This project follows a **clean layered architecture**:

src/main/java/com/example/bankingsystem/
├── controller/ → REST endpoints
├── service/ → Business logic
├── repository/ → JPA repositories
├── dto/ → Request/Response models
├── model/ → Entity classes
├── util/ → Exporters, strategy classes
├── exception/ → Global exception handling
└── config/ → App configs (Swagger, caching)
---

# 🧱 SOLID Principles Applied

- **Single Responsibility**  
  Every class handles one job only (e.g., StatementService only calculates statements).

- **Open/Closed**  
  New penalty rules, admin reports, and statement formats can be added **without modifying existing logic**.

- **Liskov Substitution**  
  Services are interface-based — implementations can be replaced anytime.

- **Interface Segregation**  
  Clean and focused service interfaces (LoanService, StatementService, AdminDashboardService).

- **Dependency Inversion**  
  Controllers depend on abstractions, not concrete classes.

---

# 🛠 Setup Instructions

### 1. Clone the Repository
```bash
git clone <repository_link>
cd banking-system-api
```
### 2. Configure MySQL
application.properties:

```properties

spring.datasource.url=jdbc:mysql://localhost:3306/bankdb
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```
### 3. Run the Application

```bash
mvn spring-boot:run

Runs on:
http://localhost:8080
```

### ⭐ Highlights
- Production-level architecture

- DTO-based clean API communication

- Transaction-safe fund transfers

- Aggregation reports via JPQL

- Strategy Pattern for penalty logic

- CSV/PDF export utilities

- Extendable service-driven design

🪪 License
This project is for educational and demonstration purposes.
