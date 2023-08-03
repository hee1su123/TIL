# Feign Client
- Netflix 에서 개발된 Http Client Binder
- 인터페이스 생성 후 annotation 으로 선언만 하면 사용 가능

## 사용 방법
build.gradle 의존성 추가
```groovy
implementation group: 'org.springframework.cloud', name: 'spring-cloud-starter-openfeign', version: '3.1.1'
```

메인 함수 클래스에 ```@EnableFeignClients```어노테이션 추가
```Java
@SpringBootApplication
@EnableFeignClients
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
```

Client 작성
```Java
@FeignClient("names")
public interface StoreClient {
    @RequestMapping(method = Request.GET, value = "/stores")
    List<Store> getStores();
}
```

Service 작성
```Java
@Service
public class StoreService {
    @Autowired
    private StoreClient client;

    public List<Store> getStore() {
        return client.getStores();
    }
}
```

## Configuration
기본적으로 설정되어 있는 것 외에 사용 되는 경우
- 권한, 보안 등을 위한 헤더 설정. 'RequestInterceptor' 를 사용
- HTTP Proxy
- 로깅, 캐싱 등을 활성화/비활성화


## Reference
- [Spring docs](https://docs.spring.io/spring-cloud-openfeign/docs/current/reference/html/)
- [우아한 기술블로그 - '우아한 feign 적용기'](https://techblog.woowahan.com/2630/)
- [블로그 - 'Feign Client 기본적인 사용법'](https://wildeveloperetrain.tistory.com/172)