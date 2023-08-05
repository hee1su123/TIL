# Git Flow 전략
- Vincent Dreiessen 이 사용한 상호 협력 방법론

## Branch 종류
- master: 기준이 되는 브랜치로 제품을 배포하는 브랜치 입니다.
- develop: 개발 브랜치로 개발자들이 이 브랜치를 기준으로 각자 작업한 기능들을 합(Merge)칩니다.
- feature: 단위 기능을 개발하는 브랜치로 기능 개발이 완료되면 develop 브랜치에 합칩니다.
- release: 배포를 위해 master 브랜치로 보내기 전에 먼저 QA(품질검사)를 하기위한 브랜치 입니다.
- hotfix: master 브랜치로 배포를 했는데 버그가 생겼을 떄 긴급 수정하는 브랜치 입니다.

## Git flow 방법
- ![Git-flow image](../Image/gitflow.png)
- master 에서 시작
- master 에서 develop 브랜치 생성
- 기능 구현 필요 시 feature 브랜치를 하나 생성하여 개발 진행
- 기능 구현 완료 시 검토 후 develop 브랜치에 Merge
- 모든 개발 완료 후 develop 브랜치에서 release 브랜치 생성
- release 브랜치에서 QA(품질 검사) 를 진행
- QA 통과 후 release 브랜치를 develop 과 master 로 Merge


## Reference
- [우아한 기술블로그 - '우린 Git-flow를 사용하고 있어요'](https://techblog.woowahan.com/2553/)
- [블로그 - 'Git-flow 이해하기'](https://ux.stories.pe.kr/183)