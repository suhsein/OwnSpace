# OwnSpace

![Alt text](https://ownspacebucket.s3.ap-northeast-2.amazonaws.com/apple-icon-120x120.png)

#### In my OwnSpace, All in OwnSpace

## Table of Contents

1. [👩‍🏫프로젝트 소개](#프로젝트-소개)
1. [⚙️기술 스택](#기술-스택)
1. [⌛개발 기간](#개발-기간)
1. [🛢ERD](#ERD)
1. [🛠️Trouble Shooting](#Touble-shooting)
1. [💼Takeaway](#Takeaway)

## 💻프로젝트 소개

#### OwnSpace는 사용자가 나만의 공간에서 나만의 기록을 남길 수 있도록, 여러 기능을 제공하는 웹사이트입니다.

#### 사진 게시판, 일상 게시판, 지도, 달력으로 구성되어 있습니다.

<br>

> ### 갤러리

| 갤러리 게시판 동작 시연                                                                                                                                |
|----------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://github.com/suhsein/OwnSpace/assets/76998096/7ccce8af-0058-4d59-921f-932713f60c89" style="width=240px; height:auto;"></img> |

1. ***AWS S3 bucket***을 이용하여 업로드한 사진을 저장할 수 있도록 했습니다.
1. ***Javascript***로 사용자에게 ***첨부파일 목록***을 보여줄 수 있도록 했습니다.
1. 첨부파일 목록에는 각 첨부파일명과, 삭제 버튼이 담깁니다.
1. 새로운 게시물을 작성할 때는 첨부파일 목록을 수정할 때 ***DataTransfer***를 통해 input files도 같이 수정할 수 있도록 했습니다.
1. 게시물 수정 시, 기존 파일 중 삭제된 파일들의 목록을 컨트롤러에서 ***@RequestParam***으로 받을 수 있도록 했습니다.
1. ***Masonry 레이아웃***으로 사진 목록을 한 눈에 볼 수 있습니다.
1. 리포지토리에서 ***Entity Manager***를 주입받아서 메서드를 ***직접 구현***했습니다.

---

> ### 지도

1. ***Kakao 지도 API***를 이용하여 지도를 보여줄 수 있도록 했습니다.
1. ***Javascript***로 키워드로 장소 검색을 하는 코드를 작성했습니다.
1. 지도에 검색 결과 장소가 마크 될 수 있도록 했습니다.
1. 검색 목록 ***페이지네이션***을 하고, 열거나 닫을 수 있도록 했습니다.
1. 검색 결과 장소의 장소명, 주소, 전화번호를 복사할 수 있도록 했습니다.
1. 리포지토리에서 ***Entity Manager***를 주입받아서 메서드를 ***직접 구현***했습니다.

---

> ### 일상

> 게시판

1. 리포지토리에서 ***JPA Repository***를 상속받아 기본 메서드를 사용했습니다. 필요에 따라서 리포지토리 내에 메서드를 생성하고, 구체적인 쿼리가 필요한 경우
   ***@Query로 jpql 쿼리문을 작성***했습니다.
1. ***페이징 처리***를 했습니다.
    2. 글목록의 시작과 끝, 이전, 다음 페이지로의 이동을 용이하게 했습니다.
    2. 검색 화면에서도 ***페이징***으로 글목록을 보여줍니다.
1. ***Native Query에서 lag, lead***로 현재 게시물에서 이전 게시물과 다음 게시물을 보여주고 이동할 수 있도록 했습니다.
1. ***검색 메서드를 구현***하여 검색코드와 키워드로 검색을 할 수 있도록 했습니다.
1. 검색 결과에서 ***Javascript***로 ***키워드를 하이라이트*** 처리 할 수 있도록 했습니다.

> 댓글

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

## 🛠️Trouble Shooting

## 💼Takeaway

