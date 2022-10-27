## Framework vs Library
![Framework&Library](../Image/Framework%26Library.jpg)
### Framework
- Framework 뼈대, 기반구조를 뜻하며 애플리케이션의 개발을 더 수월하게 해주기 위해 필수적 코드, 알고리즘, DB 연동 등과 같은 기능들을 제공해주는 소프트웨어 환경이다. 이렇게 제공된 뼈대 위에 개발자가 코드를 작성해 애플리케이션을 완성시킨다
- 객체 지향적인 개발을 하며 통합성, 일관성 부족에 관한 문제를 해결해준다
- 제어의 역전 개념이 적용된 대표적인 기술

### Library
- 단순 활용 가능한 도구들의 집합이다. 개발자의 코드에서 호출해서 사용 가능하다

### Framework 와 Library 차이
- 둘의 차이는 제어의 흐름에 대한 권한이 어디에 있는지에 있다. 프레임워크는 전체적인 제어의 흐름에 대한 권한이 자신에게 있으며, 개발자가 작성한 코드를 가져다 쓰는 것으로 표현이 가능하다. 반면 라이브러리는 개발자의 코드가 전체적인 흐름을 주도하며 라이브러리를 호출하여 사용한다.
- 라이브러리를 사용하는 애플리케이션 코드는 애플리케이션 흐름을 직접 제어합니다.  
단지 동작하는 중에 필요한 기능이 있을 때 능동적으로 라이브러리를 사용할 뿐입니다. 
반면에 프레임워크는 거꾸로 애플리케이션 코드가 프레임워크에 의해 사용되는 것입니다

### 참고해볼만한 내용
[의존성 주입](../Spring/DI.md)

### Reference
[프레임워크와 라이브러리](https://webclub.tistory.com/458)  
[프레임워크와 라이브러리](https://m.blog.naver.com/dktmrorl/222121510562)