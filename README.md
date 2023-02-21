## 기능 요구사항

### 회원

```
member
- id
- account_id
- password
- nickname
- created_at
- modified_at
- withdrawal
- withdrawal_at
```

#### 회원 가입
- account_id, password, nickname을 입력받아 회원 가입한다.
- account_id, nickname은 고유해야 한다.
- password는 암호화 되어 저장해야 한다.

#### 로그인
- account_id, password를 입력하여 로그인한다.
- account_id로 member를 조회한다.
- 탈퇴한 회원일 경우 401 코드를 반환한다.
- 암호화된 member의 password와 입력된 password의 일치 여부를 확인한다.
- 일치하지 않을 경우 401 코드를 반환한다.
- 일치할 경우 JWT를 반환한다.

#### 탈퇴
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
- modified_at
```

#### TODO 작성
- 회원만 작성할 수 있다.
- title은 필수 값이다.
- TODO의 초기 상태는 '할 일' 이다.

#### TODO 조회
- 회원만 조회할 수 있다.
- 회원은 자신의 TODO만 조회할 수 있다.
- 가장 최근 TODO 1개 조회
- 전체 목록 조회

#### TODO 상태 변경
- 회원만 변경할 수 있다.
- 회원은 TODO의 상태를 변경할 수 있다.
- 상태는 할 일, 진행중, 완료됨 세 가지이다.
