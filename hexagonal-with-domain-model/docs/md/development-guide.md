# 개발 가이드

## 아키텍처
- 헥사고날 아키텍처
- 도메인 모델 패턴

## 계층
- Domain
- Application
- Adapter

## 프로젝트 패키지 구조

```
project-root/
 ├── domain/                  # 도메인 모델을 담당하는 패키지
 ├── adapter/                 # 애플리케이션 포트/인터페이스 구현체(Primary, Secondary)
 │    ├── webapi/             # 웹 기술 기반 액터와의 연결 (Primary)
 │    ├── persistence/        # 데이터베이스 연동 (Secondary)
 │    ├── integration/        # 외부 시스템 통합 기능 (Mail, Slack 등)
 │    └── security/           # 도메인과 무관한 보안 관련 어댑터
 └── application/             # 인터페이스 집합
      ├── provided/           # 기능 제공 인터페이스 (Lollipop)
      └── required/           # 기능 요구 인터페이스 (Socket)
```

| 패키지 | 설명 |
|--------|------|
| **domain** | 도메인 모델을 정의하고, 비즈니스 로직의 핵심을 담당 |
| **adapter** | `application`에서 정의한 포트와 인터페이스를 구현하는 계층, Primary/Secondary로 구분 |
| **adapter/webapi** | 웹 기술(HTTP, WebSocket 등)을 이용하는 액터와 연결하는 어댑터 (Primary) |
| **adapter/persistence** | 데이터베이스와 연동하는 어댑터 (Secondary) |
| **adapter/integration** | 외부 시스템과의 통합 기능을 모아둔 어댑터 (예: 메일, Slack) |
| **adapter/security** | 인증·인가 등 보안 관련 어댑터 (도메인과 직접적인 연관 없음) |
| **application** | 시스템의 유즈케이스를 위한 인터페이스 집합 |
| **application/provided** | 외부로 기능을 제공하는 인터페이스 (예: Lollipop) |
| **application/required** | 외부로부터 필요한 기능을 요구하는 인터페이스 (예: Socket) |
