# Vmmem 이 점유하는 메모리가 너무 큰 문제 발생
- 작업 관리자로 확인 시 Vmmem 이란 프로그램이 메모리를 너무 많이 점유하는 문제가 발생

## 원인
- wsl2 의 사용과 Docker Desktop 의 사용이 Vmmem 의 메모리 사용량을 크게 증가 시킨다고 한다
- 리눅스에서 파일 액세스할 때, 리눅스 OS는 그 정보를 캐시로 사용하기 위해 메모리에 보존한다. 이는 메모리가 부족해 더 이상 보존할 수 없을 때까지 반복된다.
- WSL2는 Linux의 메모리 사용량에 따라 사용 메모리 크기를 동적으로 증가/감소시킨다.
	- 리눅스에서 파일 액세스의 정보를 WSL2에서 배당받은 메모리에 보존한다.
	- 이는 파일 열람을 할 때마다 매번 발생한다.
	- 리눅스의 배당받은 메모리가 한계에 달한다.
	- WSL2가 메모리를 추가 할당한다.
	- 리눅스가 배당받은 메모리가 한계에 달한다.
	- 무한 악순환

## 해결 방안
- WSL2 메모리 할당 제한
	- C:/Users/사용자이름 의 위치에 .wslconfig 파일을 생성 후 다음과 같이 작성
```
[wsl2]
memory=4GB
swap=0
```
- Docker Desktop 을 사용할때만 켜놓는다

## 결론
- 근본적인 해결책은 아직 나오지 않은 듯 하다. 깃허브 이슈도 닫히지 않고 계속 열려있다.

## Reference
- [블로그 - 'WSL2 기반 docker 사용시 vmmem 프로세스 메모리 소모량 줄이기'](https://meaownworld.tistory.com/160)
- [블로그 - 'Vmmem 점유율 문제'](https://blog.naver.com/PostView.naver?blogId=sharedrecord&logNo=222595122678&parentCategoryNo=&categoryNo=49&viewDate=&isShowPopularPosts=true&from=search)
- [Github issue - 'WSL2 consumes massive amounts of RAM and doesn't return it'](https://github.com/microsoft/WSL/issues/4166)