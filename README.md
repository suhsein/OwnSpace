# OwnSpace

![Alt text](https://ownspacebucket.s3.ap-northeast-2.amazonaws.com/apple-icon-120x120.png)

#### In my OwnSpace, All in OwnSpace

## Table of Contents

1. [👩‍🏫프로젝트 소개](#프로젝트-소개)
1. [⚙️기술 스택](#%EF%B8%8F기술-스택)
1. [⌛개발 기간](#개발-기간)
1. [🎈EC2와 RDS로 임시배포 해보기](#EC2와-RDS로-임시배포-해보기)
1. [🛢ERD](#ERD)
1. [📁프로젝트 구조](#프로젝트-구조)
1. [🛠️Trouble Shooting](#%EF%B8%8Ftrouble-shooting)
1. [💼Takeaway](#Takeaway)

## 💻프로젝트 소개

#### OwnSpace는 사용자가 나만의 공간에서 나만의 기록을 남길 수 있도록, 여러 기능을 제공하는 웹사이트입니다.

#### 사진 게시판, 일상 게시판, 지도, 달력으로 구성되어 있습니다.

<br>

> ### 게시판 메인
| 게시판 메인 |
|----------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://github.com/suhsein/OwnSpace/assets/76998096/7d3fb11b-658b-4db7-9e59-4b5e99c26781" style="width=240px; height:auto;"></img> |

1. ***부트스트랩 card***로 구현했습니다. 오버레이 된 이미지 위에 프로젝트 설명을 보여줍니다.
1. 앞, 뒷면 클래스를 만들어서 absolute로 위치를 겹쳐놓고, css로 hover 시에 rotateY(180deg)가 실행되도록 했습니다.

> ### 메뉴 설명 모달
| 메뉴 설명 모달 동작 시연|
| -----|
| <img src="https://github.com/suhsein/OwnSpace/assets/76998096/bd92f1f6-3cd3-4182-8c48-f7316196bb06" style="width=240px; height:auto;"></img> |

1. 각 메뉴마다 물음표 버튼을 누르면 모달창을 띄울 수 있도록 ***부트스트랩 modal***로 구현했습니다.

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
   1. ***Configuration*** 파일을 만들어서 초기 설정을 하고 Amazon S3 Client를 생성하도록 했습니다.
   1. 이미지를 버킷에 저장할 때 기존 파일명 앞에 ***UUID***를 붙여서 고유한 저장 파일명을 가질 수 있도록 했습니다.
   1. 이미지 업로드 시에, ***DB에서도*** aws_s3 라는 테이블 내에 이미지의 ***기존 파일명과 s3 key, 경로, 사진게시물 id를 저장***하도록 했습니다.
   1. 이미지 삭제 시에, S3 버킷에서 삭제하기 전에 aws_s3 테이블에서 먼저 ***해당 이미지 정보를 삭제***할 수 있도록 했습니다. 
1. ***Javascript***로 사용자에게 ***첨부파일 목록***을 보여줄 수 있도록 했습니다.
1. 첨부파일 목록에는 각 첨부파일명과, 삭제 버튼이 담깁니다.
1. 게시물 작성 시, 첨부파일 목록을 수정할 때 ***DataTransfer***를 통해 input files도 같이 수정할 수 있도록 했습니다.
1. 게시물 수정 시, 기존 파일 중 삭제된 파일들의 목록을 컨트롤러에서 ***@RequestParam***으로 받을 수 있도록 했습니다.
1. ***Masonry 레이아웃***으로 사진 목록을 한 눈에 볼 수 있습니다.

| AWS S3 버킷 저장 예시 |
|---|
| <img src="https://github.com/suhsein/OwnSpace/assets/76998096/ff6f53d1-bea5-4562-a8b4-0d8df6b0a04a" style="width=200px; height:auto;"></img> |

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
> ### 캘린더
| 캘린더 사용법 동작 시연 |
|----------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://github.com/suhsein/OwnSpace/assets/76998096/c0ee0966-5382-4422-a84a-00d871810fdf" style="width=240px; height:auto;"></img> |

1. 테이블을 이용해서 캘린더 틀을 생성했습니다.
1. 매개변수로 year, month 정보를 받은 후, ***LocalDate***로 해당 달력의 첫째날 정보를 받아옵니다. 그리고 List에 ***주별로 날짜 데이터***를 담아서 리턴하는 함수를 구현했습니다. 
1. 타임리프의 ***th:each***로 넣어준 데이터를 td에 넣어줬습니다.
1. 기본적으로 ***현재 년도의 현재 달력***을 보여줍니다.
1. 이전 달 혹은 다음 달 버튼을 클릭하는 경우 ***PostMapping*** 됩니다.
   1. ***request Param***으로 이전 달 혹은 다음 달 정보를 받고, ***redirectAttribute에 flashAttribute***로 year, month 정보를 넣습니다. 
   1. 캘린더로 리다이렉트 됩니다. (***PRG 패턴***) => ***설정한 date 달력***을 보여줍니다.
1. 달력의 날짜에 일정이 있는 경우, ***일정 갯수***를 보여줍니다.
1. 달력의 날짜를 누르면 일정등록과 일정목록 메뉴가 ***네비게이션 바***로 보여집니다.
1. 일정 등록 시 지도에서 ***키워드로 장소를 검색***할 수 있습니다. 장소 클릭시 폼의 장소 input에 장소명이 자동으로 입력됩니다.
1. 일정 목록에서 일정 제목, 장소, 시간, 상세설명을 볼 수 있습니다.
1. Enum으로 일정의 상태를 저장했습니다. 완료 버튼을 누르면 상태가 완료로 변합니다.
1. delete 버튼을 누르면 일정이 ***DB에서 삭제***됩니다.
---

## ⚙️기술 스택

<div align="center">
<img alt="HTML5" src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white">  <img alt="CSS3" src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white">  <img alt="BootStrap" src="https://img.shields.io/badge/BootStrap-7952B3?style=for-the-badge&logo=BootStrap&logoColor=white"><br>
<img alt="Thymeleaf" src="https://img.shields.io/badge/thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white">  <img alt="JS" src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black">  <img alt="kakao" src="https://img.shields.io/badge/kakao-FFCD00?style=for-the-badge&logo=kakao&logoColor=black">  <img alt="jQuery" src="https://img.shields.io/badge/jquery-0769AD?style=for-the-badge&logo=jquery&logoColor=white"><br>
<img alt="Java" src="https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white">  <img alt="Spring" src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=Spring&logoColor=white">  <img alt="SpringBoot" src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=Spring Boot&logoColor=white">  <img alt="MySQL" src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=black"><br>
<img alt="AWS" src="https://img.shields.io/badge/Amazon AWS-f7f7f7?style=for-the-badge&logo=Amazon AWS&logoColor=f89400">  <img alt="AWSS3Bucket" src="https://img.shields.io/badge/Amazon S3-569A31?style=for-the-badge&logo=Amazon S3&logoColor=f89400">  <img alt="GitHub" src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
</div>

## ⌛개발 기간

### 2024.01.23 ~ 2024.02.29 (5주)

## 🎈EC2와 RDS로 임시배포 해보기
과금 문제로 지속적인 배포는 불가능 하지만, 학습을 위해 aws의 EC2와 RDS를 사용해서 임시배포를 해보았습니다.<br>
다음과 같이 성공적으로 동작하는 모습을 확인할 수 있습니다.
| 임시 배포 일상 게시판 동작 화면 | 임시 배포 지도 게시판 동작 화면  |
|--------------------------------------------------------------------|--------------------------------------------------------------------------|
| <img src="https://github.com/suhsein/OwnSpace/assets/76998096/f2016308-9bce-43d2-8ac4-81ec03270beb" style="width=240px; height:auto;"></img> | <img src="https://github.com/suhsein/OwnSpace/assets/76998096/33305b65-2ae1-41eb-bff3-470b4b38c74f" style="width=240px; height:auto;"></img> |

임시배포와 관련한 내용은 아래 링크된 이슈에 자세히 기술해놓았습니다.<br>
<a href="https://github.com/suhsein/OwnSpace/issues/50">임시배포 과정 및 Trouble Shooting</a>

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
> 프로젝트를 진행하면서 어려웠던 부분, 막혔던 부분과 해결 방법에 대해서 기술했습니다.
* 정보를 입력받기
   * 폼을 사용할 때는 반드시 getmapping 부분에서 model에 해당하는 빈 폼에 대한 정보를 담아줘야한다. (객체이든, dto이든)<br>
     form 객체를 받을 때는 @ModelAttribute로 받을 수 있다.
      * 이때 반드시 html 문서 내에서 form 객체의 각 필드를 name으로 설정해줘야한다.
      * 타임리프를 사용하는 경우, form의 객체를 th:object로 설정할 수 있다. form 객체의 각 필드들의 name, id, value를 th:field를 통해 한 번에 설정할 수 있다.
   * Post에서 폼을 사용하지 않는 경우에는 @RequestParam으로 값을 받을 수 있고, 필수값이 아니라면 required = false로 설정한다.
      * @RequestParam의 value에는 html 태그의 name 값을 입력해주면 된다.
* Post 요청 시, 반드시 Post -> Redirection -> Get으로 return하여 새로고침을 할 때 같은 제출이 여러번 일어나지 않도록 해야한다.
* 에러 메세지 커스텀 시에 application.properties(혹은 yml)의 spring.messages.basename에 설정 파일명 등록 반드시 필요.
* 리다이렉트 시에는 리다이렉트 어트리뷰트로 추가해야함. 추가적으로, 경로의 파라미터가 아닌, 모델에 값을 담고 싶다면, addattribute 대신 addflashattribute 사용하기.
* 지도가 지도 메뉴에서는 잘 보이는데 캘린더의 모달창에서는 안보임. 아마 모달창이 생성되기 전에 지도가 생성되면서 width를 못잡는 문제인듯 하다.
   * => 모달 띄울때 setTimeout으로 지도 생성하도록 수정하기.
* MaxUploadSizeExceededException: Maximum upload size exceeded] 파일 업로드 시에 용량 따로 설정하지 않으면 약 1MB가 기본용량으로 설정됨.
   * => application.properties(혹은 yml)에 설정 필요.
* 생성자의 매개변수가 많은 경우 가독성이 안좋고, 순서를 지켜야하는 문제가 발생한다.
   * => @Builder 어노테이션을 붙여 빌더패턴으로 만든다.
* Github에 Aws 액세스 키 노출 문제 발생
   * 새로운 액세스 키를 인텔리제이에서 환경변수로 설정해주었고 aws 측에서 부여한 AWSCompromisedKeyQuarantineV2 정책을 삭제했다.
   * 추가적으로 Aws에서 오는 메일들에 응답하면서 후속조치를 해줘야했다.
      * 노출된 액세스 키는 정지 시키고, 새로운 액세스 키를 만든 후에 손상된 액세스 키를 삭제한다.
      * Cloud Trail로 비정상적인 동작이 발생하는지 감시했다. (요금이 꽤 많이 발생하므로, 가능한 빨리 중지한다.)
* S3 버킷 사용 참고
   * https://kim-jong-hyun.tistory.com/78
* 사진 업로드, 수정, 삭제 기능 참고
   * https://non-stop.tistory.com/540?category=1085804 
* DB에서 각 도메인들에 대한 table을 생성하려고 했었다. 허나 photo 라는 도메인의 경우, List<AwsS3> 타입의 필드를 가지고 있었다. AwsS3는 내가 만든 커스텀 객체이고, 이를 심지어 리스트로 저장한다.
  * => RDB를 사용하면서도 객체지향적인 설계를 잃지 않기 위해서는 JPA 공부의 필요성을 느껴서 잠시 프로젝트 만들기를 중단하고 JPA 공부를 했다.
* EntityManager를 사용하는 경우에, getSingleResult() 함수에서 null이 발생하면, null을 반환하지 않고 exception을 던진다.
   * => getResultList()는 null을 담을 수 있다. getResultList()를 실행시키고 stream().findAny()로 값을 받아왔다.
* Java, HTML, MySQL 간의 타입 차이
   * HTML의 input type="time"을 받을 때에, HTML에서 자바로, 자바에서 MySQL로 데이터를 넘기며 형변환이 필요했다.
   * input type="time"을 DTO에서 String으로 받아서, 자바에서 LocalTime으로 변환해줬다. LocalTime 타입 필드는 MySQL에서는 Time으로 변환되어 저장된다.
* 타임리프에서의 Enum 타입 비교 => <T(전체 패키지 경로.EnumClass).EnumType명>과 비교한다. 
* 이미지 편집 시에 순환 참조 문제 Could not write JSON: Infinite recursion
   * photo => aws_s3 // 1대다 매핑
   * 세가지 해결 방식
      1. @JsonIgnore<br>
         @OneToMany 부분에 @JsonIgnore 붙여주기 해당 프로퍼티 값을 null로 만들어주며, 데이터가 들어가는 것을 막아준다. 
      1. @JsonManagedReference와 @JsonBackReference<br>
         @JsonManagedReference가 걸려있는 클래스에서는 해당 부분을 직렬화시키며, @JsonBackReference가 걸려있는 클래스에서는 직렬화를 막는다.
      1. DTO 사용<br>
         엔티티 자체를 응답하려다보니 이슈들이 발생함. 베스트는 DTO를 만들어서 엔티티를 보호하고, 필요한 정보들만 넘겨주도록 한다.
   * DTO를 사용하는 방법으로 해결했다.
* 테이블 생성 시에 특별한 문제가 없는 것 같은데 오류가 발생하는 경우, 필드명이나 테이블명이 DB의 예약어와 겹치지는 않는지 확인해야 한다.
   * https://dev.mysql.com/doc/refman/8.0/en/keywords.html
* 개행문자의 저장과 보여주기
  * textarea에서 엔터(개행)은 DB에는 반영되어서 \r\n으로 저장됨
  * 그러나 DB에서 가져온 데이터를 보여줄 때, \r\n을 <br>로 변환해야한다.
  * 두가지 해결방식   
     * \r\n를 <br>로 변환하는 서비스를 만들고, utext로 출력하기.(이스케이프 문자를 그대로 보여주고 싶은 경우에는, 언이스케이프문으로 출력되므로 문제가 된다.)
     * pre 태그로 출력하기.(\r\n을 그대로 출력해준다. 그러나 폰트가 시스템 폰트로 설정되므로, css로 수정해줘야한다.)
* 페이지네이션 참고
   * https://wikidocs.net/162028
* 댓글, 답글 들여쓰기 기능
   * 댓글 리스트를 보여줄 때, 답글은 계층별로 들여쓰기해서 보여주고 싶었다.
   * 혼자 구현하기는 어려워서 참고 자료를 많이 찾아봤는데, 대부분 도메인에 댓글 깊이와 댓글 순서를 가지도록 했다. 그리고 이를 정렬해서 뷰로 보낸다.
   * 이 과정이 너무 복잡하고, 이미 댓글 도메인에는 부모 댓글과 자식 댓글 리스트를 가지고 있기 때문에, 이를 활용하고자 했다.
   * 프래그먼트의 재귀적 호출
      * 타임리프 nested fragment로 처리하기로 했다.
      * 참고 => https://www.youtube.com/watch?v=W9rg4LitaHQ 
      * th:replace를 쓰게 되면, 현재 프래그먼트를 호출한 부모 프래그먼트에서 적용시켰던 속성이 아닌, 제일 먼저 프래그먼트를 호출했던 곳의 속성이 적용되므로, 결국 스택 오버플로우가 터진다.
      * th:include 와 th:replace의 동작 원리가 다르기 때문이라고 한다. th:include는 deprecated 됐으므로, th:insert를 쓰면 된다.
      * 참고 => https://stackoverflow.com/questions/47032018/threplace-doesnt-work-well-with-thwith-in-thymeleaf
* 원댓글 삭제와 답글 삭제
   * 재귀적으로 거슬러 올라가서 삭제할 수 있는 부모를 반환한다 -> 없으면 자기자신을 반환한다. 삭제할 수 있는 가장 상위의 댓글은 orphanRemoval이 true이므로 삭제가 가능하고, 연쇄적으로 자식 댓글들도 삭제된다.
   * 참고 => https://kukekyakya.tistory.com/9?category=1022639
* dailyPages=Page 1 of 0 containing UNKNOWN instances 에러 발생
   * -> dailyPages.totalPages로 거르면 된다.
* Github 사용법
  * 10mb 넘는 파일은 첨부할 수 없다.
  * 리드미에 첨부하는 gif 파일 생성법
   1. Window+G로 녹화 후에, ClipChamp로 소리 제거 + 2배속 편집해준다.
   1. 동영상 압축 사이트 => https://www.veed.io/tools/video-compressor?locale=ko-KR&source=%2Ftools%2Fvideo-compressor%2Fgif-compressor
   1. gif 변환 사이트 => https://ezgif.com/video-to-gif
   1. Github issues에 첨부하여, 파일 경로를 복사해서 리드미에서 사용한다.
* Github 커밋 내역에서 파일 영구삭제 하는 방법
   1. git filter-branch --index-filter "git rm -rf --cached --ignore-unmatch [지우고 싶은 특정 파일의 경로]" HEAD
   1. git push origin --force --all
   1. 참고 => https://velog.io/@1ong1ong/Git에서-파일-완전-삭제하기
* IDE에서는 자동으로 처리해줬으나 오류였던 것
   1. 컨트롤러에서 뷰 경로를 리턴할 때, 맨 앞에 / 붙이면 안됨.
   1. 타임리프 프래그먼트 처리 시에도 ~{} 안의 경로도 맨 앞에 / 붙이면 안됨.
   1. application.yml 혹은 application.properties에서 방언 설정 해줘야함
* 임시배포 후 개방 ip 주소로 접근해야 하는데, 프라이빗 ip 주소로 접근하려고 했던 실수를 했다.
* 임시배포 과정에서 환경변수 설정 시, 전역으로 설정해줘야한다.
 
> 개선해야할 점
* 로그인 후 이전 페이지로 돌아가기와 같은 구현을 하기 위해서 스프링 시큐리티 공부가 필요하다.
* 패키지 순환 참조에 대한 이해가 부족한 것 같다.
* 인터페이스를 적극적으로 도입하여, 중복 메서드들에 대해서 오버라이딩하여 구현할 수 있도록 하면 좋을 것 같다.
* 마이페이지를 만들어서 프로필 사진을 바꾸거나 닉네임을 바꿀 수 있도록 하면 좋을 것 같다.
* 로그인과 회원가입 부분에서 API를 사용하면 좋을 것 같다.
* JPA에 대한 이론적인 공부가 좀 더 필요할 것 같다.
* 동적 쿼리와 관련하여 Querydsl 공부가 필요하다.
* 데이터베이스에 이모지가 저장되지 않는 문제도 개선이 필요하다. 

## 💼Takeaway
인프런의 스프링 강의를 들으며 스프링 MVC에 대해서 이론적으로 이해하고, 간단한 프로젝트를 따라하며 공부를 했었다.<br>
이번에는 나의 프로젝트를 만들면서 왜 이런 기능이 존재하고 이렇게 구현해야하는지 복기해볼 수 있었다.<br>
컨트롤러에서는 모델에 attribute를 설정해주고 뷰를 리턴하거나 리다이렉트 하는 간단한 기능만을 담당하게 했다.<br>
뷰에서는 타임리프를 사용해서 순수 HTML 구조를 유지할 수 있도록 했다. 또한 부트스트랩을 사용해서 비교적 간단하게 css 처리를 할 수 있도록 했다.<br>
도메인을 직접 구상하고 구현하는 과정에서 도메인에서 필요한 필드들을 추가하고 삭제해보며 이런 홈페이지를 만들 때에 도메인의 기본적인 틀을 잡을 수 있었다.<br>
예를 들면 게시판의 게시물 도메인에 대해서는 작성자와, 작성일자, 최종수정일자를 포함시키는 것과 같은 기본적인 틀 말이다.<br>
프로젝트 내에서 각각의 기능과 책임을 명확하게 분리해야함을 계속 고려하며 구현했다.<br>
패키지 구조를 컨트롤러, 도메인, 리포지토리, 서비스로 분리하고, 각각의 패키지 내에서 메뉴별로 다시 패키지를 분리해주었다.<br> 
DTO클래스를 어떤 패키지 아래에 두어야할지는 계속 고려해야할 사항인 것 같다.<br>
일대다, 다대일, 일대일 매핑을 위해서 JPA를 사용했다. 프로젝트 진행 중 JPA에 대해서 간단하게만 공부하고 사용해보았었다.<br>
트랜잭션 내에서 진행되는 서비스에 대해서 생각해보고 구현할 수 있었다.<br>
공부를 위해 Daily 메뉴의 리포지토리는 JPA Repository를 상속하여 만들었고, 나머지 메뉴의 리포지토리는 Entity Manager를 주입시켜 직접 구현했다.<br>
몇몇 메서드에 대해서는 jpql 쿼리를 직접 짜야했는데, sql과의 유사성 덕분에 비교적 쉽게 작성할 수 있었다.<br>
Aws Translate, Textract 기능을 사용해서 프로젝트를 만든 적이 있었는데, 이번에는 S3를 사용해서 프로젝트를 만들어보았다.<br>
파일은 스토리지에, 메타데이터는 DB에 저장하며 S3 사용법을 익힐 수 있었다.<br>
추가적으로 Aws의 EC2와 RDS를 이용해서 임시배포 과정을 진행해보았다.<br>
인스턴스를 생성하고, 원격 DB에 연결하고, SSH로 원격 인스턴스에 연결해 빌드한 파일을 실행시켜서 임시배포 사이트를 실행하는 전 과정에 대해서 학습할 수 있었다.
