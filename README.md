# OwnSpace
![Alt text](https://ownspacebucket.s3.ap-northeast-2.amazonaws.com/apple-icon-120x120.png)
#### In my OwnSpace, All in OwnSpace

## Table of Contents
1. [👩‍🏫프로젝트 소개](#프로젝트-소개)
1. [⚙️기술 스택](#기술-스택)
1. [⌛개발 기간](#개발-기간)
1. [🛠️Trouble Shooting](#Touble-shooting)
1. [💼Takeaway](#Takeaway)  

## 💻프로젝트 소개

#### OwnSpace는 사용자가 나만의 공간에서 나만의 기록을 남길 수 있도록, 여러 기능을 제공하는 웹사이트입니다.  
#### 사진 게시판, 일상 게시판, 지도, 달력으로 구성되어 있습니다.  
<br>

> ### 갤러리

  1. ***AWS S3 bucket***을 이용하여 업로드한 사진을 저장할 수 있도록 했습니다.  
  1. Javascript로 사용자에게 첨부파일 목록을 보여줄 수 있도록 했습니다.  
  1. 첨부파일 목록에는 각 첨부파일명과, 삭제 버튼이 담깁니다.  
  1. 새로운 게시물을 작성할 때는 첨부파일 목록을 수정할 때 ***DataTransfer***를 통해 input files도 같이 수정할 수 있도록 했습니다.  
  1. 게시물 수정 시, 기존 파일을 삭제할 때 html에서 삭제된 파일들의 name을 동일하게 설정하여, 컨트롤러에서 ***@RequestParam***으로 받을 수 있도록 했습니다.  
  1. ***Masonry 레이아웃***으로 사진 목록을 한 눈에 볼 수 있습니다.

<br>
    
> ### 갤러리 사용방법

#### 갤러리 메인
1. 목록에서 게시된 사진과 제목을 볼 수 있습니다.
2. 게시물의 첨부파일 목록 중 첫번째 첨부파일이 썸네일로 보여집니다.
       
#### 글쓰기
* 글쓰기 버튼을 눌러서 글을 쓸 수 있습니다.
  1.  게시물에는 무조건 하나 이상의 사진을 첨부해야합니다.
  1.  사진 첨부 시 첨부 사진 목록이 표시됩니다.
  1.  첨부 사진 목록에서 삭제 버튼을 눌러서 첨부한 사진을 삭제할 수 있습니다.

#### 수정과 삭제
* 게시물의 수정과 삭제가 가능합니다.
  1. 게시물 수정 시에도 하나 이상의 사진이 남아있어야합니다.
  1. 기존에 첨부했던 사진을 삭제하거나 새로운 사진을 첨부할 수 있습니다.

---

> ### 지도

  1. ***Kakao 지도 API***를 이용하여 지도를 보여줄 수 있도록 했습니다.   
  1. Javascript로 키워드로 장소 검색을 하는 코드를 작성했습니다.
  1. 지도에 검색 결과 장소가 마크 될 수 있도록 했습니다. 
  1. 검색 목록 페이지네이션을 하고, 열거나 닫을 수 있도록 했습니다.
  1. 검색 결과 장소의 장소명, 주소, 전화번호를 복사할 수 있도록 했습니다.

<br>
    
> ### 지도 사용방법

#### 지도
1. 지도에 키워드를 입력합니다
    * 예시) 전주 미용실, 전주 맛집
1. 해당하는 장소들이 왼쪽 목록에 표시됩니다.
1. 목록의 장소명, 주소, 전화번호 클릭 시, 클립보드에 복사됩니다.
1. 목록의 장소 혹은 지도에 마킹된 장소 위에 커서를 두면 장소명을 볼 수 있습니다.

---

## ⚙️기술 스택
<img alt="HTML5" src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white"> <img alt="CSS3" src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white"> 
<img alt="JS" src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black">
<img alt="jQuery" src="https://img.shields.io/badge/jquery-0769AD?style=for-the-badge&logo=jquery&logoColor=white">
<img alt="Java" src="https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white">
<img alt="Spring" src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=Spring&logoColor=white">
<img alt="SpringBoot" src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=Spring Boot&logoColor=white">  
<img alt="AWS" src="https://img.shields.io/badge/Amazon AWS-f7f7f7?style=for-the-badge&logo=Amazon AWS&logoColor=f89400">
<img alt="AWSS3Bucket" src="https://img.shields.io/badge/Amazon S3-569A31?style=for-the-badge&logo=Amazon S3&logoColor=f89400">
<img alt="GitHub" src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">

## ⌛개발 기간

## 🛠️Trouble Shooting


## 💼Takeaway

