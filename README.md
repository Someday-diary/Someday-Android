# 오늘하루 Android

<img src="https://minhyukji.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F1a20263c-9b3f-4b81-82ea-90ce22943b85%2F%EC%A0%9C%EB%AA%A9_%EC%97%86%EC%9D%8C.png?table=block&id=0b1fc9e8-f1a0-48e9-ba30-fbc087e41bb0&spaceId=3a51694b-1104-441b-bbfc-7003d445c3f2&width=2000&userId=&cache=v2">

<br>

# 👨‍💻 개발자

|<img src="https://avatars.githubusercontent.com/u/63153516?v=4" width="100"/>|<img src="https://avatars.githubusercontent.com/u/74233781?v=4" width="100"/>
|:--:|:--:|
|[지민혁](https://github.com/MinHyukJi1226)|[강동현](https://github.com/DuBu-DongHyeon)|

<br>

# 📝 기능 소개

- 일기를 간단하게 작성
    - 매일 쓸 수 있는 일기는 태그와 함께 저장할 수 있습니다. 그날의 일기를 태그로 요약해보세요
- 앱을 원하는 색으로
    - 사람마다 원하는 색이 다를 수 있습니다. 테마 설정을 통해 자신이 원하는 스타일로 일기 앱을 이용할 수 있습니다.
- 검색은 간편하게
    - 일기와 함께 태그를 작성했다면, 그 태그에 맞게 검색할 수 있습니다. 또한, 같은 태그로 이루어진 일기들을 모아 볼 수 있습니다.
- 잠금으로 안전하게
    - 사용자의 프라이버시는 중요합니다. 서버를 통해 암호화를 저장되는 것은 물론, 설정화면에서 제공되는 앱 잠금을 통해 일기를 안전하게 보호하세요.

<br>

# 🛠️ 사용 기술

| 내용 | 기술 |
|---|:---:|
| 언어 | Kotlin |
|아키텍처| MVVM |
|비동기 처리| RxJava |
| API 통신 | Retrofit2 |
| DI | Koin |
| Jetpack | Navigation, DataBinding, Room, LiveData, ViewModel, Biometric |

<br>

# 📁 파일구조

```
─base
├─di
│  └─application
├─model
│  ├─db
│  ├─network
│  │  ├─dao
│  │  ├─dto
│  │  │  ├─request
│  │  │  │  ├─diary
│  │  │  │  └─user
│  │  │  └─response
│  │  └─util
│  │      ├─Constants
│  │      ├─converter
│  │      └─Enum
│  ├─repository
│  └─sharedpreferences
├─view
│  ├─activity
│  ├─adapter
│  │  ├─data
│  │  └─listener
│  ├─decoration
│  ├─fragment
│  └─util
│      └─bindingAdapter
│          └─theme
└─viewModel
```
