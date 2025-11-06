---

# HW1: Calculator in the Cloud

## Overview

This project is a **client-server based calculator application** implemented using Java Socket programming.
The client sends arithmetic operation requests to the server, and the server **performs the computation with proper exception handling** and returns the result.

---

## Project Structure

```
HW1_SRC/
 ├─ CN/
 │   ├─ CalculatorClient.java
 │   ├─ CalculatorServer.java
 │   └─ CalculatorTask.java
 └─ server_info.dat
```

All components (Client, Server, Worker Task) operate inside the **CN** package.

---

## How to Run

### 1) Compile

```bash
javac CN/*.java
```

### 2) Start the Server

```bash
java CN.CalculatorServer
```

### 3) Run the Client (in a separate terminal)

```bash
java CN.CalculatorClient
```

---

## Server-Client Communication Protocol (ASCII-based)

| Type             | Format     | Description             | Example           |
| ---------------- | ---------- | ----------------------- | ----------------- |
| Request          | `OP A B`   | Operator + two operands | `ADD 10 20`       |
| Success Response | `OK value` | Successful result       | `OK 30`           |
| Error Response   | `ERR CODE` | Returns an error code   | `ERR DIV_BY_ZERO` |

### Supported Operators

| Operator | Meaning                                 |
| -------- | --------------------------------------- |
| ADD      | Addition                                |
| SUB      | Subtraction                             |
| MUL      | Multiplication                          |
| DIV      | Division (with zero-division detection) |

### Error Codes

| Code         | Description                       |
| ------------ | --------------------------------- |
| DIV_BY_ZERO  | Division by zero occurred         |
| BAD_OPERATOR | Unsupported operator              |
| BAD_ARITY    | Invalid number of arguments       |
| BAD_NUMBER   | Failed to parse operand as number |
| MALFORMED    | Malformed request format          |

---

## Example server_info.dat

```
server.address=localhost
server.port=1234
```

If omitted, defaults to `localhost:1234`.

---

## Example Run (Terminal Output)

```
[Client] Connecting to localhost:1234
[Server] OK READY
> ADD 10 20
[Server] OK 30
> DIV 10 0
[Server] ERR DIV_BY_ZERO
> QUIT
[Server] OK BYE
```

---
