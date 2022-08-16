## 문제 발생
STOMP Disconnect 의 경우 Front-end 에서 헤더에 값을 넣어서 Disconnect 를 실행 하면 누가 Disconnect  를 실행 했는지 알 수 있지만, 강제 종료된 경우 유저정보를 알 수 없다.

## 발생 원인
일반적인 STOMP Disconnect 의 경우 Front-end 에서 헤더에 값을 넣어서 Disconnect 를 실행 하면 누가 Disconnect  를 실행 했는지 알 수 있지만, 강제 종료된 경우 유저정보를 알 수 없다.

## 해결 방안
```C
@Component
@Slf4j
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {

    private final AuthTokenProvider tokenProvider;
    private final ChatMessageService2 chatMessageService2;

    // websocket 요청 시 실행
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        // Subscribe 시
        if (StompCommand.CONNECT == accessor.getCommand()) {

            log.info("나는 StompCommand.CONNECT");
            log.info(message.getHeaders().toString());

            String tokenStr = accessor.getFirstNativeHeader("Authorization");
            AuthToken token = tokenProvider.convertAuthToken(tokenStr);
            token.validate();
            String email = token.getTokenClaims().getSubject();

            log.info("나는 websoket 에서 받는 토큰이다 : " + token.getToken());
            log.info("내 이메일은 " + email + "이다");
            log.info(message.getHeaders().toString());

        } else if (StompCommand.SUBSCRIBE == accessor.getCommand()) {

            log.info("나는 StompCommand.SUBSCRIBE");
            log.info(message.getHeaders().toString());

            // sessionId, userId 맵핑을 위해 user 필요
            String tokenStr = accessor.getFirstNativeHeader("Authorization");
            AuthToken token = tokenProvider.convertAuthToken(tokenStr);
            token.validate();
            String email = token.getTokenClaims().getSubject();

            String roomId = chatMessageService2.getRoomId(Optional.ofNullable((String) message.getHeaders().get("simpDestination")));
            String sessionId = (String) message.getHeaders().get("simpSessionId");
            // 채팅방 사람 수 ++
            chatMessageService2.plusParticipantCount(roomId);
            // sessionId 와 participant(user와 roomId로 찾을 수 있음) 맵핑 진행
            chatMessageService2.mapSessionAndParticipant(email, roomId, sessionId);

        } else if (StompCommand.DISCONNECT == accessor.getCommand()) {

            // disconnect 시 sessionId 정보
            String sessionId = (String) message.getHeaders().get("simpSessionId");
            // sessionId 로 맵핑된 participant 알 수 있음. 해당 participant 의 exitTime 변경
            String roomId = chatMessageService2.exitParticipant(sessionId);
            // participant 로 ChatRoom 의 roomId 알 수 있음. 해당 roomId 로 채팅방 사람 수 --
            if (roomId != null){
                log.info("나는 StompCommand.DISCONNECT");
                log.info(message.getHeaders().toString());
                log.info("sessionId : " + sessionId);
                log.info("roomId : " + roomId);
                chatMessageService2.minusParticipantCount(roomId);
            }

        }
        return message;
    }
}
```
- Interceptor 을 이용하여 presend 라는 함수를 Override 한다.
- presend 함수 내에서 Connect, Subscribe, Disconnect 등의 요청이 왔을 때를 if 문을 사용하여 알 수 있다.
- Subscribe 요청 시 sessionId 와 participantId 를 Redis 캐싱을 이용하여 맵핑을 해놓는다.
- 강제 Disconnect 시 유일하게 알 수 있는 정보가 Disconnect 된 sessionId 이다. Redis 캐시에 저장되어 있는 맵핑 정보를 이용해 누가 Disconnect 되었는지 확인 한다.

## 결론
- 강제 Disconnect 되는 경우 유일하게 알 수 있는 정보는 Disconnect 된 세션의 아이디 이다. 해당 세션이 처음 Connect 또는 Subscribe 되었을때 미리 유저 또는 참여자의 아이디와 맵핑 해 놓은 정보를 갖고 있으면, 누가 Disconnect 되었는지 알 수 있다.
- 웹소켓이 연결 되었다 끊기는 과정은 매우 빈번하게 일어나며, 영속적일 필요가 없는 정보이다. 따라서 RDBMS 를 사용하지 않고 Redis 를 사용하여 정보를 저장한다.

## Reference
[WebSocket Disconnection 감지](https://hyeon9mak.github.io/web-socket-disconnection-detection/)
