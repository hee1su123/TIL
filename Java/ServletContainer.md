# Servlet Container
- Web Container라고 불리기도 함
- 서블릿의 생성, 실행, 파괴를 담당
- 서블릿과 웹서버가 통신할 수 있게 연결고리 역할 수행
- 대표적으로 Tomcat 이 가장 많이 사용됨

## 요청과 응답 과정
- 웹서버가 HTTP 요청을 받는다
- 웹서버는 요청을 서블릿 컨테이너로 전달합니다.
- 서블릿이 컨테이너에 없다면, 서블릿을 동적으로 검색하여 컨테이너의 주소 공간에 로드한다
- 컨테이너가 서블릿의 init() 메소드를 호출하면, 서블릿이 초기화된다 : 서블릿이 처음 로드됬을 때 한번만 호출
- 컨테이너가 서블릿의 service() 메소드를 호출하여 HTTP 요청을 처리한다.
- 웹서버는 동적으로 생성된 결과를 올바른 위치에 반환한다.

## Reference
- [Wikipedia - 'Web Container'](https://en.wikipedia.org/wiki/Web_container)
- [인프런 - '토비의 스프링 부트 - 이해와 원리'](https://www.inflearn.com/course/%ED%86%A0%EB%B9%84-%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-%EC%9D%B4%ED%95%B4%EC%99%80%EC%9B%90%EB%A6%AC/dashboard)
- [블로그 - '서블릿 컨테이너'](https://velog.io/@han_been/%EC%84%9C%EB%B8%94%EB%A6%BF-%EC%BB%A8%ED%85%8C%EC%9D%B4%EB%84%88Servlet-Container-%EB%9E%80)