# Spring Boot Sample

## 🚀 주요 기능

- **인증 시스템**: JWT 기반 로그인/회원가입
- **토큰 관리**: Access Token & Refresh Token 시스템
- **사용자 관리**: 사용자 CRUD 및 프로필 관리
- **데이터베이스**: PostgreSQL + JPA + Kotlin JDSL
- **로깅**: Logback 기반 구조화된 로깅
- **API 문서**: Swagger/OpenAPI 자동 생성
- **환경 관리**: 다중 환경 설정 (local/dev/prod)

## 🛠 기술 스택

- **Framework**: Spring Boot 3.4.3
- **Language**: Kotlin
- **Database**: PostgreSQL
- **ORM**: JPA
- **Authentication**: Spring Security 6.x + JWT
- **Validation**: spring-validator
- **Documentation**: Swagger/OpenAPI
- **Logging**: Logback + Daily Rotate File

## 📋 요구사항

- JDK 21 이상
- PostgreSQL 14.0 이상

## 🚀 빠른 시작

### 1. 저장소 클론

```bash
git clone <repository-url>
cd spring-kotlin-sample
```

### 2. 의존성 설치

```bash
# 의존성 설치
./gradlew clean build

# 실행 권한이 없는 경우
chmod +x ./gradlew

# lint 오류
./gradlew ktlintFormat
```

### 3. 환경 변수 설정

```bash
# 환경 변수 파일 생성
sh ./scripts/generate-env.sh
```

### 4. 데이터베이스 마이그레이션

```bash
# 프로젝트 실행 시 flyway로 자동 마이그레이션
```

## 📁 프로젝트 구조

```
spring-kotlin-sample/
src
├── main
│   ├── kotlin
│   │   └── demo
│   │       └── springkotlinsample
│   │           ├── auth
│   │           │   ├── auth
│   │           │   │   ├── controller
│   │           │   │   ├── dto
│   │           │   │   └── service
│   │           │   └── token
│   │           │       ├── controller
│   │           │       ├── domain
│   │           │       ├── dto
│   │           │       ├── mapper
│   │           │       ├── persistence
│   │           │       │   └── repository
│   │           │       ├── repository
│   │           │       └── service
│   │           ├── common
│   │           │   ├── base
│   │           │   ├── converter
│   │           │   ├── exception
│   │           │   ├── jpa
│   │           │   ├── logging
│   │           │   ├── pageable
│   │           │   ├── security
│   │           │   │   ├── exception
│   │           │   │   ├── filter
│   │           │   │   └── principal
│   │           │   └── swagger
│   │           └── user
│   │               ├── user
│   │               │   ├── controller
│   │               │   ├── domain
│   │               │   ├── dto
│   │               │   ├── enumeration
│   │               │   ├── mapper
│   │               │   ├── persistence
│   │               │   │   └── repository
│   │               │   ├── repository
│   │               │   └── service
│   │               └── userrole
│   │                   ├── domain
│   │                   ├── enumeration
│   │                   ├── mapper
│   │                   ├── persistence
│   │                   └── repository
│   └── resources
│       └── db
│           └── migration
└── test
    └── kotlin
        └── demo
            └── springkotlinsample
```

## 🔧 환경 변수

### 필수 환경 변수

```env
# 서버 설정
PORT=8080

API_PREFIX=api
API_VERSION=v1

DATABASE_URL=jdbc:postgresql://localhost:5432/springboot
DATABASE_USERNAME=username
DATABASE_PASSWORD=password

LOGGING_SQL_SHOW=true
LOGGING_SQL_LEVEL=trace

JWT_SECRET_KEY=your-secret-key
JWT_HEADER=Authorization
JWT_PREFIX="Bearer "
JWT_ISSUER=sbk
JWT_ACCESS_EXPIRES_IN=3600
JWT_REFRESH_EXPIRES_IN=604800

```

## 📚 API 문서

애플리케이션 실행 후 다음 URL에서 API 문서를 확인할 수 있습니다:

- **Swagger UI**: `http://localhost:8080/api/v1/swagger-ui/index.html`

## 🔐 인증 시스템

### JWT 토큰 구조

```text
// Access Token
{
    userId: number,
    type: 'ac',
    roles: '...'
}

// Refresh Token
{
    userId: number,
    type: 're',
    roles: '...'
}
```

## 🗄 데이터베이스

### 주요 모델

- **User**: 사용자 정보
- **UserRole**: 사용자 Role 정보
- **Token**: 리프레시 토큰 관리

## 🔍 로깅

### 로그 레벨

- **개발 환경**: `debug`
- **프로덕션 환경**: `info`

### 로그 파일 구조

```
logs/
├── app/           # 애플리케이션 로그
└── error/         # 에러 로그
```

## 🏗 아키텍처

- **Controller**: API 엔드포인트
- **Domain**: 도메인
- **Dto**: 요청, 응답 정보 전달
- **Mapper**: Dto <-> Domain 간 변환
- **Persistence**: 데이터베이스 통신
- **Repository**: 영속성 로직 캡슐화
- **Service**: 비즈니스 로직
