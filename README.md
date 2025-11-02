# HW1: Calculator in the Cloud

## 개요
본 프로젝트는 Java Socket 프로그래밍을 이용하여 구현한 **클라이언트-서버 기반 계산기 애플리케이션**이다.  
클라이언트는 사용자의 연산 요청을 서버로 전송하고, 서버는 **사칙연산 수행 및 예외 처리 후 결과를 응답**한다.

---

## 프로젝트 구조

```
HW1_SRC/
 ├─ CN/
 │   ├─ CalculatorClient.java
 │   ├─ CalculatorServer.java
 │   └─ CalculatorTask.java
 └─ server_info.dat
```

**CN 패키지** 내부에서 Client, Server, Task(Worker)가 동작한다.

---

## 실행 방법

### 1) 컴파일
```bash
javac CN/*.java
```

### 2) 서버 실행
```bash
java CN.CalculatorServer
```

### 3) 클라이언트 실행 (새 터미널에서)
```bash
java CN.CalculatorClient
```

---

## 서버-클라이언트 통신 프로토콜 (ASCII 기반)

| 구분 | 형식 | 설명 | 예시 |
|---|---|---|---|
| 요청(Request) | `OP A B` | 연산자 + 피연산자 2개 | `ADD 10 20` |
| 성공 응답 | `OK value` | 계산 성공 결과 | `OK 30` |
| 오류 응답 | `ERR CODE` | 오류 코드 반환 | `ERR DIV_BY_ZERO` |

### 지원 연산자
| 연산자 | 의미 |
|---|---|
| ADD | 덧셈 |
| SUB | 뺄셈 |
| MUL | 곱셈 |
| DIV | 나눗셈 (0 나누기 예외 처리 포함) |

### 오류 코드
| 코드 | 의미 |
|---|---|
| DIV_BY_ZERO | 0으로 나누기 발생 |
| BAD_OPERATOR | 미지원 연산자 |
| BAD_ARITY | 인자 개수 부족 / 초과 |
| BAD_NUMBER | 숫자 변환 불가 |
| MALFORMED | 형식 오류 |

---

## server_info.dat 설정 예시
```
server.address=localhost
server.port=1234
```
없을 경우 기본값 (localhost:1234) 자동 적용.

---

## 동작 예시 (터미널)

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


