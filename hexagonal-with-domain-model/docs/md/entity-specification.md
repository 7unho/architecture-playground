# 엔티티 정의 문서

---

## 엔티티( Entity )
- 도메인 안에 있는 대상이나 개념
- **고유한 식별자**를 통해 개별적으로 구분된다.
- Life Cycle을 가진다 -> 시간의 흐름에 따라 상태가 변경될 수 있다.

---
## [ 회원 애그리거트 ]
### 회원 ( Member )
_Aggregate Root_
#### 속성
- `id`: `Long` 타입의 구분자
- `email`: [`EMAIL`] 이메일 - ID
- `nickname`: 닉네임
- `passwordHash`: 해싱된 비밀번호
- `status`: `MemberStatus` 회원 상태
- `detail`: `MemberDetail`과 **1:1** 관계
#### 행위
- `static register()`: 회원 생성, email, nickname, password, passwordHash, passwordEncoder, profileAddressGenerator
- `activate()`: 등록을 완료한다
- `deactivate()`:  탈퇴한다
- `verifyPassword()`: 비밀번호를 검증한다
- `changePassword()`: 비밀번호를 변경한다.
- `updateInfo()`: 회원의 닉네임, 프로필 주소, 자기 소개를 수정한다. 
#### 규칙
- 회원 생성 후 상태는 등록 대기
- 일정 조건을 만족하면, 등록 완료가 된다.
- 등록 대기 상태(PENDING)에서만 등록 완료가 될 수 있다.
- 등록 완료 상태에서는 탈퇴할 수 있다.
- 회원의 비밀번호는 해시를 만들어서 저장한다
- 프로필 주소는 중복을 허용하지 않는다.
- 기존 생성했던 프로필 주소를 제거할 수 있다.
### 회원 상세( Member Detail )
_Entity_
- `id`: `Long`
- `profile`: 프로필 주소, 모든 회원이 고유한 프로필 주소를 가져야 한다.
- `introduction`: 자기 소개
- `registeredAt`: 등록 일시
- `activatedAt`: 등록 완료 일시
- `deactivatedAt`: 탈퇴 일시
#### 행위
- `static create()`: 회원 등록. 현재 시간을 등록 일시로 저장한다. 랜덤 프로필 주소를 저장한다.
- `activate()`: 등록 완료와 관련된 작업 수행. 등록 완료 일시 저장
- `deactive()`: 탈퇴와 관련된 작업 수행. 탈퇴 완료 일시 저장
- `updateInfo()`: 상세 정보 수정

### 회원 상태( Member Status )
_Enum_
##### 상수
- `PENDING`: 등록 대기
- `ACTIVE`: 활성 상태( 등록 완료 )
- `DEACTIVATE`: 비활성 상태( 탈퇴, 논리적 삭제 )

##### DuplicateEmailException
_Exception_


### 비밀번호 인코더 ( PasswordEncoder )
_Domain Service_
#### 행위
- `encode()`: 비밀번호 암호화
- `matches()`: 일치 여부 확인

### 프로필 주소( Profile )
_Value Object_
#### 속성
- `address`: 프로필 주소

### 프로필 주소 제너레이터 ( ProfileAddressGenerator )
_Domain Service_
#### 행위
- `generate()`: 프로필 주소 생성

[End of 회원 애그리거트](#-회원-애그리거트-)

---

### 이메일 ( Email )
_Record(VO)_
#### 규칙
- 이메일 형식이 아니라면 예외를 던진다.
