# README
# Banking System API

A personal Spring Boot project simulating a basic **banking system** with account management, fund transfers, transaction history, and an interest calculator.
This project is designed to practice **REST API development**, **transaction handling**, and **data consistency** using Spring Boot and MySQL.

---

## Features

### Account Management

* Create new accounts
* Deposit and withdraw funds
* View account details and balance

### Fund Transfers

* Transfer funds between accounts
* Validates sufficient balance and prevents self-transfer
* Uses `@Transactional` for atomic updates

### Transaction History

* Logs all deposits, withdrawals, and transfers
* Fetch last N transactions per account

### Interest Calculator

* Calculates **simple interest** and **total payable amount**
* Formula: `(Principal * Rate * Time) / 100`
* Input validation for positive numbers

---

## Tech Stack

| Component      | Technology               |
| -------------- | ------------------------ |
| **Backend**    | Spring Boot 3.x          |
| **Language**   | Java 17                  |
| **Database**   | MySQL / H2 (for testing) |
| **ORM**        | Spring Data JPA          |
| **Validation** | Jakarta Bean Validation  |
| **Testing**    | JUnit 5                  |
| **Build Tool** | Maven                    |

---

## API Endpoints

### Account Management

| Method | Endpoint                  | Description          |
| ------ | ------------------------- | -------------------- |
| `POST` | `/accounts`               | Create a new account |
| `GET`  | `/accounts/{id}`          | Get account details  |
| `POST` | `/accounts/{id}/deposit`  | Deposit funds        |
| `POST` | `/accounts/{id}/withdraw` | Withdraw funds       |

**Example Request**

```json
{
  "name": "Simon",
  "initialDeposit": 10000
}
```

**Response**

```json
{
  "accountID": 101,
  "balance": 10000
}
```

---

### Fund Transfer

| Method | Endpoint             | Description                     |
| ------ | -------------------- | ------------------------------- |
| `POST` | `/accounts/transfer` | Transfer money between accounts |

**Example Request**

```json
{
  "fromAccount": 101,
  "toAccount": 102,
  "amount": 1500
}
```

**Response**

```json
{
  "message": "Transfer Successful",
  "fromAccount": 101,
  "toAccount": 102,
  "amountTransferred": 1500,
  "remainingBalance": 8500
}
```

---

### Transaction History

| Method | Endpoint                      | Description                                 |
| ------ | ----------------------------- | ------------------------------------------- |
| `GET`  | `/accounts/{id}/transactions` | Retrieve last N transactions for an account |

**Example Response**

```json
[
  { "type": "DEPOSIT", "amount": 1000, "date": "2025-10-24" },
  { "type": "WITHDRAW", "amount": 100, "date": "2025-10-25" },
  { "type": "TRANSFER_OUT", "amount": 1500, "date": "2025-10-25" }
]
```

---

### Interest Calculator

| Method | Endpoint                     | Description                                |
| ------ | ---------------------------- | ------------------------------------------ |
| `POST` | `/api/v1/interest/calculate` | Calculate simple interest and total amount |

**Example Request**

```json
{
  "principal": 10000,
  "rate": 6.5,
  "time": 2
}
```

**Example Response**

```json
{
  "Interest": 1300,
  "TotalAmount": 11300,
  "Message": "Calculation successful"
}
```

---

## Setup Instructions

### Clone the Repository

```bash
git clone https://github.com/<your-username>/banking-system-api.git
cd banking-system-api
```

### Configure Database (MySQL)

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bankdb
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

(For quick testing, you can switch to H2 in-memory DB.)

---

### Build & Run the Application

```bash
mvn clean install
mvn spring-boot:run
```

App runs at:
👉 [http://localhost:8080](http://localhost:8080)

---

## 🧪 Testing

Run unit tests:

```bash
mvn test
```

Includes:

* Interest calculation tests
* Fund transfer validation
* Deposit/withdrawal tests
* Transaction history integrity

---

## 📂 Project Structure

```
src/
 ├── main/java/com/example/bankingsystem/
 │   ├── controller/       # REST Controllers
 │   ├── service/          # Business Logic
 │   ├── dto/              # Data Transfer Objects
 │   ├── model/            # Entity Models
 │   ├── repository/       # JPA Repositories
 │   └── exception/        # Global Exception Handling
 └── test/java/...         # JUnit Test Cases
```

---

## Highlights

* Clean architecture with layered design
* DTOs for clean API data exchange
* Validation and custom exceptions
* Transaction-safe fund transfers
* Reusable business logic services

---


## 🪪 License

This project is for educational and demonstration purposes.
