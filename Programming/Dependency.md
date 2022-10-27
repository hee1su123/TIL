# 의존성(Dependency)
- 객체 간의 협력
- 파라미터나 리턴값 또는 지역변수 등으로 다른 객체를 참조하는 것

## 표현
- "의존성이 있다"
- "의존한다"
- "A는 B에 대한 의존성이 있다" -> B 가 변하면 A 도 변해야 함

## 예시
- Service 클래스
```Java
public class MemberService {
    private final MemberRepository repository;

    -- 생략 --
}
```
- Repository 클래스
```Java
public class MemberRepository {
    -- 생략 --
}
```
- MemberService 는 DB 접근을 위해 MemeberRepository 를 지역변수로 가지고 참조하므로
    - "MemberService 는 MemberRepository 에 의존한다"
    - "MemberService 는 MemberRepository 에 대한 의존성이 있다"