# security
## process
회원가입
<br/>1) 회원가입 -> 인증번호 인증 -> 회원가입 완료
<br/>2) 인증번호 Redis로 관리, 3분 후 삭제 되도록 TTL 적용
<br/><br/>로그인
<br/>1) 액세스 토큰 만료 시간 1시간
<br/>2) 리프레시 토큰 만료 시간 7일 Redis로 관리, 7일 후 삭제 되도록 TTL 적용
<br/><br/>토큰 만료
<br/>1) 액세스 토큰 만료시 토큰 재발급 API 호출
<br/>2) 리프레시 토큰 만료시 다시 로그인 하여 토큰 발급
## API
회원가입
```
POST http://localhost:8080/signup
{
    "email": "test@naver.com",
    "password": "1234",
    "name" : "테스터",
}
```
```
인증번호가 발송되었습니다. 인증 제한시간은 3분 입니다.
```
<br/>인증번호 인증
```
POST http://localhost:8080/signup/verify
{
    "email": "test@naver.com",
    "number": "897632"
}
```
```
200 인증이 완료되었습니다.
400 인증번호가 다릅니다.
403 인증 제한시간을 초과하였습니다.
```
<br/>로그인
```
POST http://localhost:8080/signin
{
    "email": "test@naver.com",
    "password": "1234"
}
```
```
{
    "memberId": 1,
    "email": "test@naver.com",
    "accessToken": "액세스토큰",
    "refreshToken": "리프레시토큰"
}
```
<br/>액세스 토큰 재발급
```
POST http://localhost:8080/jwt/refresh
{
    "memberId": 1,
    "refreshToken": "리프레시토큰"
}
```
```
{
    "memberId": 1,
    "email": "test@naver.com",
    "accessToken": "액세스토큰",
    "refreshToken": "리프레시토큰"
}
```
