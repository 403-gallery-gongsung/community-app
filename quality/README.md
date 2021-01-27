## Coding Convention

Java 언어는 [Google Coding Convention](https://google.github.io/styleguide/javaguide.html)을 따른다. <br>
js 언어는 [Airbnb Coding Convention](https://github.com/airbnb/javascript)을 따른다.

## Commit Rule
git commit template 이용한다. 

```bash
$ git config --glbal commit.template $(ProjectPath)/community-app/quality/gitmessage.txt
```

커밋 메시지의 라벨 리스트는 아래와 같다.
```
feat : 새로운 기능에 대한 커밋
fix : 버그 수정에 대한 커밋
build : 빌드 관련 파일 수정에 대한 커
chore : 그 외 자잘한 수정에 대한 커밋
ci : CI관련 설정 수정에 대한 커밋
docs : 문서 수정에 대한 커밋
style : 코드 스타일 혹은 포맷 등에 관한 커밋
refactor :  코드 리팩토링에 대한 커밋
test : 테스트 코드 수정에 대한 커밋
```

## Branch Rule
브랜치는 이슈를 바탕으로 생성하며, 네이밍은 아래 규칙을 따른다. <br>
`Epic`/`Issue-Number`/`Description`

각 브랜치는 해당 이슈에 대한 개발을 모두 완료하면 `develop` 브랜치를 base로 하여 Pull Request & Merge 수행한다.

## Pull Request Rule
Pull Request는 아래 템플릿 형식을 사용한다. <br>
zenhub을 이용해 issue에 connect 해둔다. 
```
#### 요약
...

#### 상세 수정내용
...

#### 참고
...
```

## Packaging Rule
Module 우선 Packaging 방식을 우선적으로 사용한다.

## Board rule
1. BackLog: 진행해야 하는 이슈이지만, 아직 진행 계획은 수립되지 않은 상태
2. To Do: 아직 진행하지 않은 이슈이며, 진행 계획은 수립된 상태
3. In Progress: 현재 진행중인 이슈
4. Review/QA: `develop` 브랜치에 반영되어 있고, 리뷰 및 테스트 중인 이슈
5. Done: 리뷰 및 테스트를 마친 이슈
6. Closed: `main` 브랜치에 반영된 이슈
