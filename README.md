## Projects
- Java 11 (corretto)
- Spring Boot 2.7.8
- Spring Security
- Spring Data Jpa
- H2 Database
- JUnit 5

## App Start
```./gradlew bootRun```

## API 사용 방법

### 회원가입
- URI: POST http://127.0.0.1:8080/members
- Request Body
```json
{
  "accountId": "tester",
  "password": "tester1234",
  "nickname": "tester"
}
```

### 로그인
- URI: POST http://127.0.0.1:8080/login
- Request Body:
```json
{
  "accountId": "tester",
  "password": "tester1234"
}
```
- Response Header
  - key: token 
  - value: [JWT]

### 회원 탈퇴
- URI: POST http://127.0.0.1:8080/members/withdrawal
- Request Header:
  - key: Authorization
  - value: Bearer [JWT]
- Request Body:
```json
{
    "password": "tester1234"
}
```

### TODO 작성
- URI: POST http://127.0.0.1:8080/todos
- Request Header:
  - key: Authorization
  - value: Bearer [JWT]
- Request Body:
```json
{
  "title": "title test",
  "content": "content test"
}
```

### TODO 상태 변경
- URI: PATCH http://127.0.0.1:8080/todos/{id}/status/{status}
  - id: todo id
  - status: TO_DO, IN_PROGRESS, DONE
- Request Header:
  - key: Authorization
  - value: Bearer [JWT]

### 가장 최근 TODO 단건 조회
- URI: GET http://127.0.0.1:8080/todos/latest
- Request Header:
  - key: Authorization
  - value: Bearer [JWT]

### TODO 목록 조회
- URI: GET http://127.0.0.1:8080/todos?page=0&size=5&sort=createdAt,desc
  - page: 페이지 번호
  - size: 가져올 데이터 개수
  - sort: 정렬 기준 및 방법
- Request Header:
  - key: Authorization
  - value: Bearer [JWT]

## 기능 요구사항
### 회원

```
member
- id
- account_id
- password
- nickname
- created_at
- updated_at
- withdrawal
- withdrawal_at
```

#### 회원 가입 (완)
- account_id, password, nickname을 입력받아 회원 가입한다.
- account_id, nickname은 고유해야 한다.
- password는 암호화 되어 저장해야 한다.

#### 로그인 (완)
- account_id, password를 입력하여 로그인한다.
- account_id로 member를 조회한다.
- 탈퇴한 회원일 경우 401 코드를 반환한다.
- 암호화된 member의 password와 입력된 password의 일치 여부를 확인한다.
- 일치하지 않을 경우 401 코드를 반환한다.
- 일치할 경우 JWT를 반환한다.

#### 탈퇴 (완)
- 인가된 사용자만 호출할 수 있다.
- password를 입력 받는다.
- 탈퇴할 회원의 password와 입력된 password의 일치 여부를 확인한다.
- member의 withdrawal을 true로 변경하고 withdrawal_at을 현재 시간으로 변경한다.
- 탈퇴가 완료되면 JWT를 제거한다.

### TODO List

```
todo
- id
- member_id
- title
- content
- status
- created_at
- updated_at
```

#### TODO 작성 (완)
- 회원만 작성할 수 있다.
- title은 필수 값이다.
- TODO의 초기 상태는 '할 일' 이다.

#### TODO 조회 (완)
- 회원만 조회할 수 있다.
- 회원은 자신의 TODO만 조회할 수 있다.
- 가장 최근 TODO 1개 조회
- 전체 목록 조회

#### TODO 상태 변경 (완)
- 회원만 변경할 수 있다.
- 회원은 TODO의 상태를 변경할 수 있다.
- 상태는 할 일, 진행중, 완료됨 세 가지이다.
