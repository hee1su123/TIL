# 대규모 트래픽 처리
- 급격한 트래픽 변화에 대응하기 위한 트래픽 처리 방법들

## 로드 밸런싱(Load Balancing)
- 요청을 여러대의 서버로 나누어 부하를 분산시키는 방법
- 모든 요청이 로드 밸런서로 먼저 들어온 후 적합한 서버로 라우팅됨

## 오토 스케일링(Auto Scaling)
- 서버의 부하를 체크하여 서버를 생성(미리 만들어 놓은 이미지 사용)
- CPU, 메몰, 디스크, 네트워크 트래픽 등을 모니터링하며 서버 사이즈를 자동으로 조절

## Reference
- [AWS - Load Balancing](https://aws.amazon.com/ko/what-is/load-balancing/)
- [Samasung sds - Auto Scaling](https://www.samsungsds.com/kr/insights/auto_scaling.html#:~:text=%EC%98%A4%ED%86%A0%EC%8A%A4%EC%BC%80%EC%9D%BC%EB%A7%81(Auto%20Scaling)%EC%9D%80,%EB%A5%BC%20%EC%9E%90%EB%8F%99%EC%9C%BC%EB%A1%9C%20%EC%A1%B0%EC%A0%88%ED%95%A9%EB%8B%88%EB%8B%A4.)
- [블로그 - 대규모 트래픽 처리](https://blog.naver.com/PostView.nhn?isHttpsRedirect=true&blogId=seek316&logNo=222071746247&redirect=Dlog&widgetTypeCall=true&directAccess=false)