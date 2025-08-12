# 엔티티 정의 문서

## 엔티티( Entity )
- 도메인 안에 있는 대상이나 개념
- **고유한 식별자**를 통해 개별적으로 구분된다.
- Life Cycle을 가진다 -> 시간의 흐름에 따라 상태가 변경될 수 있다.

### 회원 ( Member )
_Entity_
#### 속성
- `email`: 이메일 - ID
- `nickname`: 닉네임
- `passwordHash`: 해싱된 비밀번호
- `status`: 회원 상태
#### 행위
- `constructor()` -> 회원 생성: email, nickname, passwordHash, status
- `activate()` -> 가입을 완료한다
- `deactivate()` ->  탈퇴한다
#### 규칙
- 회원 생성 후 상태는 가입 대기
- 일정 조건을 만족하면, 가입 완료가 된다.
- 가입 대기 상태(PENDING)에서만 가입 완료가 될 수 있다.
- 가입 완료 상태에서는 탈퇴할 수 있다.
#### 회원 상태( Member Status )
_Enum_
##### 상수
- `PENDING`: 가입 대기
- `ACTIVE`: 활성 상태( 가입 완료 )
- `DEACTIVATE`: 비활성 상태( 탈퇴, 논리적 삭제 )