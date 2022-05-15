# JWT (Json Web Token)

Authorization (인가, 권한부여) 기능. 유저가 로그인 되고 나면, 이후 해당 유저가 보내는 모든 요청(request) 시 HTTP Headers 에 JWT가 포함된다. 해당 토큰으로 허가된 routes, services,resources에 접근이 가능하게 된다. JWT는 수정 가능성에 대한 보호는 되어 있지만 누구나 읽을 수 있는 정보이므로 비밀 정보는 담아서는 안된다.

## JWT Structure
- Header : Type / Algorithm
- Payload
    - Registered claims : 필수는 아니지만 권장됨. iss(이슈), exp(만료시간), sub(주제), aud(audience) 등
    - Public claims : JWT를 사용하는 사람들이 정의 가능. 다만 충돌을 피하기 위해 IANA JSON Web Token Registry 에 정의 되어 있거나 URI 으로 정의 되어 있어야함.
    - Private claims : 두 파티간 정보 공유에 사용하기 위한 클레임.
- Signature : 암호화된 header, 암호화된 payload, secret, algorithm 으로 이루어짐.


```C
Header
{
    "alg":"HS256",
    "typ":"JWT"
}
Payload
{
    "sub":"123456789",
    "name":"Heesu",
    "admin":"true"
}
Signature
HMACSHA256(
  base64UrlEncode(header) + "." +
  base64UrlEncode(payload),
  secret)
```