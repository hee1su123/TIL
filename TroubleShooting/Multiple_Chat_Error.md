## 문제 발생
Redis Pub/Sub 이 적용된 웹소켓 채팅에서 채팅 내용이 DB 에 두번 저장되는 현상이 발생
## 해결 과정(생각 했지만 틀린 과정)
1. 당시 nginx 를 이용한 무중단 배포를 위해 서버가 2개 띄어져 있는 상태였다. 사용하는 서버는 하나였지만 두개의 서버가 서로 다른 포트에 실행되는 중이었고, 두 서버 모두 같은 localhost:6379 로 Redis가 연동되어 있었다.

chat.redis.RedisRepository
```C
public class RedisRepository {
---생략---

    @PostConstruct
    private void init() {
        topicMap = new HashMap<>();
        List<ChatRoom> roomList = chatRoomRepository.findAll();
        for (ChatRoom c : roomList) {
            ChannelTopic topic = ChannelTopic.of(c.getRoomId());
            redisMessageListenerContainer.addMessageListener(redisSub, topic);
            topicMap.put(c.getRoomId(), topic);
        }
    }

---생략---
}
```
- 위의 코드에서 @PostConstruct 는 스프링이 빌드 되면서 실행되는 메서드라는 의미인데, 서버가 두대이니 init() 함수가 두번 실행되어 ChannelTopic 이 두개 생성되어, 채팅 메세지가 두번 클라이언트에게 도착하여 생기는 오류라고 생각했다.
### 틀린 트러블 슈팅인 이유
- ChannelTopic 이 두개 생기는 것은 맞지만, 하나는 8081 서버에 또다른 하나는 8082 서버에 생기는 것은 자연스러운 현상이다. 처음부터 ```topicMap = new HashMap<>()``` 으로 선언 했기 때문에 Redis 의 HashOperation 으로 저장한 것이 아닌 in memory 형태여서 서로 다른 서버끼리는 해당 ChannelTopic 을 공유 할 수 없다. 따라서 각 서버마다 ChannelTopic 은 생성해 주는 것이 맞다. 
- ChannelTopic 이 두개여도 서로 다른 서버에 존재하며, 한명의 클라이언트는 하나의 서버에 접속해 있으므로 결국 웹소켓을 통해 받는 메세지 자체는 한개이다.  
-> 이 이유가 결론적으로 틀리게 되었는데, 이 때는 프론트에서 채팅 메세지를 띄어주기 위해 계속해서 DB 를 호출하는 방식으로 구현하고 있는지 몰랐다.

## 발생 원인

chat.redis.ReSub
```C
public class RedisSub implements MessageListener {

    ---생략---

    @Override
    public void onMessage(Message message, byte[] pattern) {
        ---생략---
        chatMessageService.save(chatMessage);
        messageSendingOperations.convertAndSend(---생략---);
    }

}
```
- 위의 코드에서 onMessage 는 메세지 Publish 진행 시 그 메세지 정보를 받는 기능을 한다. 이 메서드는 두개의 서버 8081, 8082 둘다 존재하고 있었고 이 메서드 중간 부분에서 메세지를 DB 에 저장하다 보니 두개의 메세지가 저장되고 있었다. 만약 서버가 3개였다면 3개의 메세지가 저장되었을 것이다.

## 오류 해결
chat.service.ChatMessageService
```C
public class ChatMessageService {

    ---생략---

    public void sendChatMessage(ChatMessageRequestDto message, String email) {

        ---생략---
        this.saveChatMessage(message);
        redisPub.publish(redisRepository.getTopic(room.getRoomId()), message);
    }

    ---생략---

}
```
- 위의 ```redisPub.publish()``` 함수는 실행 시 ```redisTemplate.convertAndSend()``` 함수를 실행하여 ```onMessage()``` 함수로 메세지를 보낸다. 기존에 메세지를 저장하던 위치에서 위의 메서드로 위치를 바꿔주어 DB 에 메세지가 두번 저장되는 것을 해결했다.

## 결론
- 서버가 여러대 늘어나게 되면서 고민해야 할 내용이 더 많아 지는 것 같다.
- Redis 의 Publish 를 어느 서버에서 접속하여 진행하든, 모든 서버의 onMessage 로 해당 메세지가 전송된다. 같은 Redis 서버를 이용하기 때문인 것으로 추정된다.


## 궁금한점
- ChannelTopic 은 같은 ```String name``` 으로 생성하면 같은 값이 나오나?