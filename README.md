# OwnSpace

![Alt text](https://ownspacebucket.s3.ap-northeast-2.amazonaws.com/apple-icon-120x120.png)

#### In my OwnSpace, All in OwnSpace

## Table of Contents

1. [👩‍🏫프로젝트 소개](#프로젝트-소개)
1. [⚙️기술 스택](#기술-스택)
1. [⌛개발 기간](#개발-기간)
1. [🛢ERD](#ERD)
1. [📁프로젝트 구조](#프로젝트-구조)
1. [🛠️Trouble Shooting](#Touble-shooting)
1. [💼Takeaway](#Takeaway)

## 💻프로젝트 소개

#### OwnSpace는 사용자가 나만의 공간에서 나만의 기록을 남길 수 있도록, 여러 기능을 제공하는 웹사이트입니다.

#### 사진 게시판, 일상 게시판, 지도, 달력으로 구성되어 있습니다.

<br>

> ### 회원가입과 로그인

| 회원가입 동작 시연| 로그인 동작 시연 |
|-------------------------------------------------|---------------------------------------------------------------------------------------------|
| <img src="https://github.com/suhsein/OwnSpace/assets/76998096/55799a5c-5d0b-4469-b255-5ae09eb539ea" style="width=240px; height:auto;"></img> | <img src="https://github.com/suhsein/OwnSpace/assets/76998096/e0cea09a-08f0-4fbe-b2ad-cc438951b035" style="width=240px; height:auto;"></img> |

1. 세션에 로그인 된 멤버가 있는 경우, 인덱스 페이지로 리다이렉트 됩니다.
1. ***에러 코드와 에러 메세지를 저장***하는 errors.properties 파일을 두고, application.yml에서 spring.messages.basename에 해당 파일을 지정했습니다.
1. Member 도메인과 회원가입용 dto를 분리했습니다. 회원가입용 dto에서 멤버변수들에 ***검증용 어노테이션***들을 붙였습니다.(ex. @NotBlank)
1. 컨트롤러에서 ***@Validated***를 통해서 ***필드에러를 검증***할 수 있도록 했습니다. ***글로벌 에러***는 ***BindingResult의 reject 메서드***로 에러 코드를 넣어주었습니다. 
1. 뷰에서 ***타임리프***에서 ***th:object***로 폼의 dto를 지정하고, ***th:field***로 dto의 필드들을 지정한 후, ***th:errorclass***로 해당 필드에 에러가 있을 경우 클래스를 추가하도록 했습니다. 
1. 에러 메세지를 출력하는 태그에서는 ***th:errors로*** 에러가 발생하는 필드명을 넣어주었습니다. 해당 필드에 에러가 발생하면 태그가 보이고, ***메세지 리졸버***에 의해 지정해둔 에러 코드의 에러 메세지가 출력됩니다. 
1. 필드에러가 아닌 경우, ***글로벌 에러를 검증***하여 에러메세지를 출력할 수 있도록 했습니다.
1. 회원가입 시 ***데이터베이스***에 같은 아이디 혹은 이메일로 가입된 회원이 있는지 확인합니다.
1. 로그인 시 비밀번호가 ***데이터베이스***에 저장된 해당 회원의 비밀번호와 같은지 확인합니다.

---

> ### 갤러리

| 갤러리 게시판 동작 시연|
|----------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://github.com/suhsein/OwnSpace/assets/76998096/7ccce8af-0058-4d59-921f-932713f60c89" style="width=240px; height:auto;"></img> |

1. 리포지토리에서 ***Entity Manager***를 주입받아서 메서드를 ***직접 구현***했습니다.
1. ***AWS S3 bucket***을 업로드된 사진들을 저장하는 스토리지로 사용했습니다.
1. ***Javascript***로 사용자에게 ***첨부파일 목록***을 보여줄 수 있도록 했습니다.
1. 첨부파일 목록에는 각 첨부파일명과, 삭제 버튼이 담깁니다.
1. 새로운 게시물을 작성할 때는 첨부파일 목록을 수정할 때 ***DataTransfer***를 통해 input files도 같이 수정할 수 있도록 했습니다.
1. 게시물 수정 시, 기존 파일 중 삭제된 파일들의 목록을 컨트롤러에서 ***@RequestParam***으로 받을 수 있도록 했습니다.
1. ***Masonry 레이아웃***으로 사진 목록을 한 눈에 볼 수 있습니다.

---

> ### 지도

| 지도 동작 시연|
|----------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://github.com/suhsein/OwnSpace/assets/76998096/38de997d-4f7f-4f9c-9b63-e6a24ee7165e" style="width=240px; height:auto;"></img> |

1. 리포지토리에서 ***Entity Manager***를 주입받아서 메서드를 ***직접 구현***했습니다.
1. ***Kakao 지도 API***를 이용하여 지도를 보여줄 수 있도록 했습니다.
1. ***Javascript***로 키워드로 장소 검색을 하는 코드를 작성했습니다.
1. 지도에 검색 결과 장소가 마크 될 수 있도록 했습니다.
1. 검색 목록 ***페이지네이션***을 하고, 열거나 닫을 수 있도록 했습니다.
1. 검색 결과 ***장소의 장소명, 주소, 전화번호를 복사***할 수 있도록 했습니다.

---

> ### 일상

> 게시판

| 일상 게시판 글쓰기, 검색 동작 시연 | 일상 게시판 페이징, 수정, 삭제 동작 시연|
|-----------------------------------------------------------------|-----------------------------------------------------------------------------|
| <img src="https://github.com/suhsein/OwnSpace/assets/76998096/39b1f11f-5bcd-4098-98a4-c0e2122d1c77" style="width=240px; height:auto;"></img> | <img src="https://github.com/suhsein/OwnSpace/assets/76998096/5889b0ac-eecd-4ebb-b7ad-67051b13a30b" style="width=240px; height:auto;"></img> |

1. 리포지토리에서 ***JPA Repository를 상속***받아 기본 메서드를 사용했습니다. 필요에 따라서 리포지토리 내에 메서드를 생성하고,  
   구체적인 쿼리가 필요한 경우 ***@Query로 jpql 쿼리문을 작성***했습니다.
1. ***타임리프***에서 th:each를 사용해 게시물 목록을 보여줍니다. 각 게시물의 번호, 제목, 작성자, 날짜, 댓글수, 조회수가 보여집니다.
1. ***스프링의 Page와 Pageable 클래스***를 사용하여 ***페이징 처리***를 했습니다.
    1. 글목록의 시작과 끝, 이전, 다음 페이지로의 이동을 용이하게 했습니다.
    1. 검색 화면에서도 ***페이징***으로 글목록을 보여줍니다.
1. ***Native Query에서 lag, lead***(jpql에서 제공되지 않음)를 사용해 현재 게시물에서 이전 게시물과 다음 게시물을 보여주고 이동할 수 있도록 했습니다.
1. ***검색 메서드를 구현***하여 검색코드와 키워드로 검색을 할 수 있도록 했습니다.
1. 검색 결과에서 ***Javascript***로 ***키워드를 하이라이트*** 처리 할 수 있도록 했습니다.


> 댓글

| 일상 게시판 게시물 이동, 댓글, 답글 동작 시연|
|----------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://github.com/suhsein/OwnSpace/assets/76998096/52c909f3-6f4d-406a-b26d-c12774f5dc91" style="width=240px; height:auto;"></img> |

1. ***댓글 기능을 구현***하여 댓글을 달고 삭제할 수 있도록 했습니다.
1. 댓글 도메인은 ***셀프참조***를 통해 ***부모 댓글(1:N)*** 과 ***자식 리스트(N:1)*** 로 매핑했습니다.
1. 답글 버튼으로 답글을 달 수 있도록 하였고, 부모 댓글에 대비해 들여쓰기 처리해서 보여줍니다.
1. 답글의 뷰 처리는 ***타임리프의 프래그먼트***를 이용하여 ***재귀적***으로 보여줄 수 있도록 했습니다.
1. 댓글 삭제
    1. 원댓글에 답글이 달려있는 경우, 원댓글의 ***상태만 DELETE***로 변경하도록 했습니다.
    1. 원댓글에 답글이 없는 경우, ***상태가 DELETE인 가장 상위의 댓글을 찾아서(없다면 원댓글을) DB에서 삭제***합니다. ***OrphanRemoval을 true***로 해놓았기 때문에, 자손
       댓글은 연쇄적으로 삭제됩니다.

---

## ⚙️기술 스택

<div align="center">
<img alt="HTML5" src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white">  <img alt="CSS3" src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white">  <img alt="Thymeleaf" src="https://img.shields.io/badge/thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white">  <img alt="JS" src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black">  <img alt="jQuery" src="https://img.shields.io/badge/jquery-0769AD?style=for-the-badge&logo=jquery&logoColor=white">

<img alt="Java" src="https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white">  <img alt="Spring" src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=Spring&logoColor=white">  <img alt="SpringBoot" src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=Spring Boot&logoColor=white">  <img alt="MySQL" src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=black">  
  <img alt="kakao" src="https://img.shields.io/badge/kakao-FFCD00?style=for-the-badge&logo=kakao&logoColor=black">  <img alt="AWS" src="https://img.shields.io/badge/Amazon AWS-f7f7f7?style=for-the-badge&logo=Amazon AWS&logoColor=f89400">  <img alt="AWSS3Bucket" src="https://img.shields.io/badge/Amazon S3-569A31?style=for-the-badge&logo=Amazon S3&logoColor=f89400">  <img alt="GitHub" src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
</div>

## ⌛개발 기간

### 2024.01.23 ~ 2024.02.29 (5주)

## 🛢ERD

<img src="https://github.com/suhsein/OwnSpace/assets/76998096/16d9597d-29f9-40ba-8368-0d82db9b5bb4" width="70%"/>

## 📁프로젝트 구조
```
📦 
├─ .gitignore
├─ README.md
├─ build.gradle
├─ gradle
│  └─ wrapper
│     ├─ gradle-wrapper.jar
│     └─ gradle-wrapper.properties
├─ gradlew
├─ gradlew.bat
├─ settings.gradle
└─ src
   ├─ main
   │  ├─ java
   │  │  └─ com
   │  │     └─ suhsein
   │  │        └─ ownspace
   │  │           ├─ OwnSpaceApplication.java
   │  │           ├─ configuration
   │  │           │  └─ S3Config.java
   │  │           ├─ controller
   │  │           │  ├─ HomeController.java
   │  │           │  ├─ calendar
   │  │           │  │  ├─ CalendarController.java
   │  │           │  │  ├─ ToDoController.java
   │  │           │  │  └─ dto
   │  │           │  │     └─ ToDoDto.java
   │  │           │  ├─ daily
   │  │           │  │  ├─ CommentController.java
   │  │           │  │  ├─ DailyController.java
   │  │           │  │  ├─ DailySearchController.java
   │  │           │  │  └─ dto
   │  │           │  │     ├─ CommentDto.java
   │  │           │  │     ├─ DailyDto.java
   │  │           │  │     └─ DailySearchDto.java
   │  │           │  ├─ gallery
   │  │           │  │  ├─ GalleryController.java
   │  │           │  │  └─ dto
   │  │           │  │     ├─ AwsS3Dto.java
   │  │           │  │     └─ PhotoDto.java
   │  │           │  ├─ map
   │  │           │  │  └─ MapController.java
   │  │           │  └─ members
   │  │           │     ├─ LoginController.java
   │  │           │     └─ SignUpController.java
   │  │           ├─ domain
   │  │           │  ├─ calendar
   │  │           │  │  ├─ MyDate.java
   │  │           │  │  ├─ ToDo.java
   │  │           │  │  └─ ToDoStatus.java
   │  │           │  ├─ daily
   │  │           │  │  ├─ Comment.java
   │  │           │  │  ├─ CommentStatus.java
   │  │           │  │  ├─ Daily.java
   │  │           │  │  ├─ SearchCode.java
   │  │           │  │  ├─ SearchCodeName.java
   │  │           │  │  └─ SearchCodes.java
   │  │           │  ├─ gallery
   │  │           │  │  └─ Photo.java
   │  │           │  ├─ members
   │  │           │  │  └─ Member.java
   │  │           │  └─ s3
   │  │           │     └─ AwsS3.java
   │  │           ├─ repository
   │  │           │  ├─ calendar
   │  │           │  │  └─ ToDoRepository.java
   │  │           │  ├─ daily
   │  │           │  │  ├─ CommentRepository.java
   │  │           │  │  └─ DailyRepository.java
   │  │           │  ├─ gallery
   │  │           │  │  └─ PhotoRepository.java
   │  │           │  ├─ members
   │  │           │  │  └─ MemberRepository.java
   │  │           │  └─ s3
   │  │           │     └─ AwsS3Repository.java
   │  │           └─ service
   │  │              ├─ CheckLogin.java
   │  │              ├─ calendar
   │  │              │  ├─ CalendarService.java
   │  │              │  ├─ ConvertTimeService.java
   │  │              │  ├─ ToDoService.java
   │  │              │  ├─ WeekDayDto.java
   │  │              │  ├─ WeekDto.java
   │  │              │  └─ YearMonthDto.java
   │  │              ├─ daily
   │  │              │  ├─ CommentService.java
   │  │              │  └─ DailyService.java
   │  │              ├─ gallery
   │  │              │  └─ PhotoService.java
   │  │              ├─ members
   │  │              │  ├─ MemberService.java
   │  │              │  └─ dto
   │  │              │     ├─ MemberDto.java
   │  │              │     └─ MemberSaveDto.java
   │  │              └─ s3
   │  │                 └─ AwsS3Service.java
   │  └─ resources
   │     ├─ application.yml
   │     ├─ errors.properties
   │     ├─ static
   │     │  ├─ cloud.jpg
   │     │  ├─ css
   │     │  │  ├─ bootstrap.min.css
   │     │  │  ├─ calendar-map.css
   │     │  │  ├─ custom.css
   │     │  │  ├─ gallery.css
   │     │  │  └─ map.css
   │     │  ├─ favicon.ico
   │     │  ├─ js
   │     │  │  ├─ bootstrap.bundle.js
   │     │  │  ├─ bootstrap.bundle.js.map
   │     │  │  ├─ calendarMapService.js
   │     │  │  ├─ commentBoxService.js
   │     │  │  ├─ dailyHighlightService.js
   │     │  │  ├─ mapService.js
   │     │  │  ├─ passwordService.js
   │     │  │  └─ utils.js
   │     │  └─ masonry-layout.html
   │     └─ templates
   │        ├─ alert
   │        │  ├─ back.html
   │        │  ├─ current.html
   │        │  └─ redirect.html
   │        ├─ calendar
   │        │  ├─ calendar.html
   │        │  ├─ to-do-list.html
   │        │  └─ to-do.html
   │        ├─ daily
   │        │  ├─ add-post.html
   │        │  ├─ daily-view.html
   │        │  ├─ daily.html
   │        │  ├─ edit-comment.html
   │        │  └─ edit-post.html
   │        ├─ error
   │        │  ├─ 404.html
   │        │  └─ 500.html
   │        ├─ fragment
   │        │  ├─ comment-list.html
   │        │  ├─ daily-content.html
   │        │  ├─ edit-comment-list.html
   │        │  ├─ footer.html
   │        │  ├─ head.html
   │        │  ├─ info-description.html
   │        │  ├─ js-modules.html
   │        │  ├─ modals.html
   │        │  ├─ navigation.html
   │        │  └─ to-do-nav.html
   │        ├─ gallery
   │        │  ├─ add-photo.html
   │        │  ├─ edit-photo.html
   │        │  ├─ gallery.html
   │        │  └─ photo-view.html
   │        ├─ index.html
   │        ├─ map
   │        │  └─ map.html
   │        └─ members
   │           ├─ login.html
   │           └─ sign-up.html
   └─ test
      └─ java
         └─ com
            └─ suhsein
               └─ ownspace
                  ├─ DateTest.java
                  └─ OwnSpaceApplicationTests.java
```
©generated by [Project Tree Generator](https://woochanleee.github.io/project-tree-generator)

## 🛠️Trouble Shooting

## 💼Takeaway

