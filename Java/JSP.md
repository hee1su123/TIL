# JSP : Java Server Page
- HTML 코드에 Java 코드를 넣어 동적 웹페이지를 생성하는 웹 애플리케이션 도구, 기술을 의미
- Server side 기술이다
- Servlet 기술의 상위 버전이다
- runtime 중 .jsp 파일은 Jsp Container(Servlet Container) 에 의해 Java Servlet (.java 파일)로 변환된 후 컴파일 된다

## Servlet Container & Jsp Container
- Jsp 컨테이너는 Servlet 컨테이너의 기능을 포함하고 있다
- Jsp 가 처음 등장했을 때, Servlet 컨테이너는 Jsp 를 변환하는 기능을 Servlet 컨테이너가 제공하지 않았다. 따라서 Jsp 컨테이너만 Jsp 를 Servlet 으로 변환하는 기능을 수행했다. 하지만 요즘은 Servlet 컨테이너도 해당 기능이 추가되어 둘을 구분하는 것은 의미가 없다고 한다(Stackoverflow 내용으로 100% 정확한지는 모르겠음..)
- 또다른 블로그에서는 Jsp 컨테이너는 Jsp를 서블릿으로 변환하고 컴파일 하는 기능만 담당하며 객체 생성과 요청 수행은 서블릿 컨테이너가 담당한다고 한다

## Reference
- [Wikipedia - 'JSP'](https://en.wikipedia.org/wiki/Jakarta_Server_Pages)
- [GeeksforGeeks - 'JSP'](https://www.geeksforgeeks.org/introduction-to-jsp/)
- [블로그 - 'JSP'](https://javacpro.tistory.com/43)
- [블로그 - 'Jsp Container & Servlet Container'](https://m.blog.naver.com/PostView.naver?isHttpsRedirect=true&blogId=zag001&logNo=221558401301)
- [Stackoverflow - 'Jsp container vs Servlet container'](https://stackoverflow.com/questions/10680332/jsp-container-vs-servlet-container)
- [Stackoverflow - 'Jsp and Servlet Container same?'](https://stackoverflow.com/questions/2113934/are-jsp-and-servlet-container-same?noredirect=1&lq=1)