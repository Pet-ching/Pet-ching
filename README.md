# Petching
✔ 반려동물 주인과 펫시터를 1:1로 매칭해주는 플랫폼 서비스입니다.<br>
📽 시연 영상 : https://www.youtube.com/watch?v=9caRKdyAJHk <br>
📋 노션 페이지 : https://2cleanwater.notion.site/Pet-ching-2d0aeb3f61764f64be6da393b780507d

<br>

## 1. 제작 기간 & 참여 인원
- 2022.04.14~2022.04.28
- 6명

<br>

## 2. 기술 스택
- Java 11
- Spring Boot 2.6.6
- Spring Web MVC 5.3.19
- Spring Security 5.6.2
- Spring data JPA 2.6.3
- MySQL 8.0.27
- Thymeleaf 2.6.6

<br>

## 3. 주요 기능
|기능|내용|
|--|--|
|지역별 펫시터 목록 조회 서비스|등록된 펫시터 정보를 제공합니다. 원하는 지역에서 활동하는 펫시터를 검색을 통해 찾을 수 있습니다.|
|펫시터 상세보기 서비스|펫시터의 활동 범위를 카카오 맵으로 표현합니다. 돌봄 환경을 이미지 슬라이드로 확인할 수 있습니다. 또한 이용 가능 서비스, 이용 가능 요일, 이용 요금 정보를 제공합니다.|
|채팅 서비스|펫시터에게 1:1 채팅을 통해 문의할 수 있습니다. 채팅 내용을 DB에 저장되며 마이페이지에서 채팅방을 관리할 수 있습니다.|
|예약 서비스|펫시터가 예약을 승인하기 전까지 예약을 취소할 수 있습니다. 펫시터는 예약 승인, 거절이 가능합니다.|
|커뮤니티 서비스|게시판에 글을 등록, 삭제, 수정, 조회할 수 있습니다. 게시글에 댓글을 달 수 있습니다.|
|고객센터 서비스|자주 찾는 질문을 조회할 수 있습니다. 문의글을 카테고리별로 항목화하여 등록할 수 있습니다. 문의글 수정, 삭제가 가능합니다. 파일 업로드가 가능합니다.|
|회원관리 서비스|회원 가입, 탈퇴가 가능합니다. 이메일을 통해 로그인하며 회원가입 시 이메일 중복을 검사합니다. 비밀번호는 암호화 후 저장됩니다. 회원 정보 수정이 가능합니다. 반려동물 정보를 등록할 수 있습니다. 펫시터로 활동하는 경우 돌봄 환경을 등록할 수 있습니다.|

<br>

## 4. 프로젝트 아키텍처
![화면 캡처 2022-05-01 002256](https://user-images.githubusercontent.com/65762496/166111758-9647d44e-eeae-4559-b28e-397a53e6fa89.png)

<br>

## 5. 분석 패키지 구조도 
![분석패키지구조도](https://user-images.githubusercontent.com/65762496/166111849-381540a5-3f54-48e7-af15-874fc04c9690.png)

<br>

## 6. 개체-관계 모델(ERD)
![화면 캡처 2022-05-01 003209](https://user-images.githubusercontent.com/65762496/166112107-e469986b-4cfa-44ec-b65f-807d8afeca21.png)

<br>

## 7. 개발 팀 소개
|이름|역할|
|--|--|
|[박승연](https://github.com/seungyeonpark)|- 웹 소켓을 이용한 채팅 기능 구현 <br>- 예약 관리 기능 구현 <br> - 반려동물 정보 관리 기능 구현 <br>- 펫시터 돌봄환경 관리 기능 구현 <br>- 회원정보 관리 기능 구현|
|[이정수](https://github.com/2cleanwater)|- 펫시터 상세 정보 서비스 구현 <br>- 예약 등록 기능 구현 <br>- 카카오 맵 API 서비스 구현|
|[설태혁](https://github.com/seoltaehyeok)|- spring security를 사용해 로그인, 회원가입 구현 <br>- 로그인, 회원가입 제약 조건 구현 <br>- 회원 탈퇴 구현 <br>- 펫시터-보호자 매칭 로직 및 페이지 구현|
|[김선화](https://github.com/hhhhhsh)|- spring security를 사용해 로그인 보안 설계 구현 <br>- 회원가입 제약 조건 구현 <br>- index 페이지 구현|
|[이정운](https://github.com/wjddns0882)|- 커뮤니티 게시판 등록, 조회, 수정, 삭제 <br>- 커뮤니티 게시판 댓글 구현 <br>- 문서 작업|
|[김귀영](https://github.com/Onlwu)|- 고객센터 자주 찾는 질문 페이지 구현 <br>- 고객센터 1:1 문의 서비스 구현 <br>- 1:1 문의 페이지 관리자, 유저 권한 설정 <br>- 1:1 문의 페이지 파일 업로드, 다운로드|

