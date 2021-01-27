## Coding Convention

Java 언어는 [Google Coding Convention](https://google.github.io/styleguide/javaguide.html)을 따른다. <br>
js 언어는 [Airbnb Coding Convention](https://github.com/airbnb/javascript)을 따른다.

## Commit Rule
git commit template 이용한다. 

```bash
$ git config --glbal commit.template $(ProjectPath)/community-app/quality/gitmessage.txt
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
