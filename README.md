# EduManager

## 개요
EduManager는 교육 관리 시스템을 구현한 Java 기반 웹 애플리케이션입니다. 이 애플리케이션은 교육 기관의 학사 관리, 강의실 예약, 공지사항 관리, 학습 자료 업로드 및 다운로드와 같은 기능을 포함하고 있습니다.

## 목표
- MVC 구조를 상세히 이해하고 활용할 수 있다
- Java 언어를 능숙하게 사용할 수 있다
- 협업을 친숙하게 몸에 익힐 수 있다

## 구조
EduManager는 다음과 같은 주요 디렉토리 및 파일로 구성되어 있습니다:

  ### 1. JSP 파일
  bottom.jsp, index.jsp, main.jsp, top.jsp: 공통 레이아웃 및 메인 페이지 구성.
  common/: 공지사항, 캘린더, 북샵 등의 공용 뷰 제공.
  view_admin/: 관리자 관련 기능 (예: 전공 입력, 일정 관리, 교수 관리).
  view_classroom/: 강의실 관련 기능 (예: 강의 등록, 과제 제출, 성적 조회).
  view_student/: 학생 관련 기능 (예: 교재 거래 게시판, 학생 프로필 관리).
  ### 2. Java 클래스
  컨트롤러 (Controller)
  주요 요청을 처리하며 각 도메인별 로직을 관리 (예: AdminController, StudentController).
  DAO (Data Access Object)
  데이터베이스와의 상호작용을 처리 (예: AdminDAO, AssignmentDAO).
  Service
  비즈니스 로직을 캡슐화하여 컨트롤러와 DAO 사이의 연결을 담당 (예: AdminService, ClassroomService).
  VO (Value Object)
  데이터 전달 객체로, 데이터베이스 테이블과 매핑된 클래스 (예: AdminVo, SubmissionVo).
  ### 3. 설정 파일
  META-INF/context.xml: 데이터베이스 연결 설정.
  WEB-INF/web.xml: 애플리케이션 배포 설명자.
  config.properties: 추가 구성 정보를 포함.
  ### 4. 외부 라이브러리
  데이터 처리 및 기타 기능 제공:
    commons-fileupload, gson, json-simple: 데이터 변환 및 파일 업로드 지원.
    mysql-connector-java: MySQL 데이터베이스 연결.
    taglibs-standard: JSTL 지원.
  ### 5. 정적 자원
  CSS:
    css/ 디렉토리 내에 다양한 페이지 스타일 정의.
  JavaScript:
    js/ 디렉토리 내 스크립트 파일 포함.
  이미지:
    images/, img/ 디렉토리 내에 아이콘 및 배경 이미지 제공.
  기능
    관리자:
      학사 일정 관리, 전공 및 과목 입력, 교수 및 학생 계정 관리.
    학생:
      과제 제출, 강의 정보 조회, 성적 확인, 교재 거래.
    교수:
      강의 등록 및 관리, 공지사항 게시, 학생 평가.
    공통 기능:
      실시간 채팅, 공지사항 관리, 파일 업로드.

## 기술 스택
  ### 백엔드
  - Java (Servlet, JSP)
  - JDBC
  ### 프론트엔드
  - HTML
  - CSS
  - JavaScript
  - jQuery
  ### 데이터베이스
  - MySQL
  ### 서버
  - Apache Tomcat
  ### Library
  - Gson
  - JSoup
  - JSON-Simple
  - MySQL Connector
  ### 개발 및 협업 환경
  - IDE: Eclipse
  - 버전 관리: Git, GitHub
  - 문서 관리: Notion

## 설치 및 실행
1. Apache Tomcat 9 이상 설치.
2. EduManager.war 파일을 Tomcat의 webapps 디렉토리에 배치.
3. Tomcat 서버를 시작.
4. 브라우저에서 http://www.javatjoeun.kro.kr:8080/EduManager/member/main.bo 로 접속.

## 개발자
- Jeong Gyu Eop (Leader)
- Lee Jun Hui
- Kwon Yu Jin
