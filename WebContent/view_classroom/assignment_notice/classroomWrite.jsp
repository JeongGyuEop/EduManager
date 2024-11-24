<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	// JSP 내 서버 데이터 준비 (사용자 ID, 페이지 정보 등)
	request.setCharacterEncoding("UTF-8");
	String contextPath = request.getContextPath();
	String nowPage = (String)request.getAttribute("nowPage");
	String nowBlock = (String)request.getAttribute("nowBlock");
	String id = (String)session.getAttribute("id");
	String course_id = (String)session.getAttribute("course_id");
%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>공지사항 글쓰기</title>
<style>
    /* 페이지의 기본적인 배경 설정 */
    body {
        font-family: 'Arial', sans-serif; /* 가독성 좋은 폰트 설정 */
        background-color: #f4f6f9; /* 밝은 회색 배경으로 설정 */
        margin: 0; /* 페이지 기본 마진 제거 */
        padding: 0; /* 페이지 기본 패딩 제거 */
    }

    /* 제목 스타일 */
    h1 {
        text-align: center; /* 제목 중앙 정렬 */
        color: #333; /* 텍스트 색상을 짙은 회색으로 설정 */
        margin-bottom: 20px; /* 제목 아래 여백 추가 */
        font-size: 40px; /* 제목의 크기를 설정 */
        font-weight: bold; /* 제목을 굵게 설정 */
        display: inline-block; /* 제목 크기를 텍스트 크기에 맞게 조정 */
        padding-bottom: 10px; /* 제목 하단 여백 */
    }

    /* 컨텐츠 전체 영역 스타일 */
    .container {
        width: 70%; /* 컨테이너 너비를 화면의 70%로 설정 */
        margin: 0 auto; /* 화면 가운데 정렬 */
        padding: 20px; /* 컨테이너 내부 여백 추가 */
        background-color: #fff; /* 흰색 배경 */
        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); /* 컨테이너에 부드러운 그림자 추가 */
        border-radius: 10px; /* 모서리를 둥글게 설정 */
    }

    /* 테이블 스타일 */
    table {
        width: 100%; /* 테이블 너비를 컨테이너 크기에 맞춤 */
        margin-top: 20px; /* 테이블 상단 여백 추가 */
        border-collapse: collapse; /* 테이블 경계 중복 제거 */
        background-color: #fff; /* 흰색 배경 */
    }

    /* 테이블 셀 스타일 */
    th, td {
        padding: 15px; /* 셀 내부 여백 추가 */
        text-align: left; /* 셀 내부 텍스트 왼쪽 정렬 */
        font-size: 14px; /* 텍스트 크기 설정 */
    }

    /* 입력 필드 스타일 */
    td input[type="text"],
    td textarea {
        width: 100%; /* 입력 필드가 셀 크기를 채우도록 설정 */
        padding: 10px; /* 필드 내부 여백 추가 */
        margin: 5px 0; /* 입력 필드 간 간격 추가 */
        border: 1px solid #ced4da; /* 연한 회색 테두리 추가 */
        border-radius: 5px; /* 모서리를 둥글게 설정 */
        font-size: 14px; /* 텍스트 크기 설정 */
        color: #495057; /* 필드 내부 텍스트 색상 설정 */
    }

    /* 텍스트 에리어의 크기를 고정 */
    td textarea {
        resize: none; /* 사용자가 크기 조정 불가능하도록 설정 */
    }

    /* 읽기 전용 입력 필드 스타일 */
    td input[type="text"]:read-only {
        background-color: #e9ecef; /* 비활성화된 필드 배경을 연한 회색으로 설정 */
        color: #6c757d; /* 비활성화된 텍스트 색상 설정 */
    }

    /* 테이블 셀 테두리 스타일 */
    td {
        border-bottom: 1px solid #e9ecef; /* 셀 하단에 연한 회색 경계선 추가 */
    }

    /* 버튼 그룹 스타일 */
    .button-group {
        text-align: center; /* 버튼 중앙 정렬 */
        margin-top: 20px; /* 버튼 그룹 상단 여백 추가 */
    }

    /* 버튼 기본 스타일 */
    .button-group input[type="button"] {
        font-size: 16px; /* 버튼 텍스트 크기 설정 */
        font-weight: bold; /* 버튼 텍스트 굵게 설정 */
        padding: 12px 24px; /* 버튼 내부 여백 설정 */
        margin: 10px; /* 버튼 간 간격 추가 */
        border: none; /* 버튼 기본 테두리 제거 */
        border-radius: 5px; /* 모서리를 둥글게 설정 */
        cursor: pointer; /* 버튼 마우스 커서 변경 */
        transition: all 0.3s ease; /* 버튼 애니메이션 효과 추가 */
    }

    /* 등록 버튼 스타일 */
    .button-group input[type="button"]#registration1 {
        background-color: #007bff; /* 파란색 배경 */
        color: #fff; /* 흰색 텍스트 */
    }

    /* 등록 버튼 마우스 오버 스타일 */
    .button-group input[type="button"]#registration1:hover {
        background-color: #0056b3; /* 어두운 파란색 배경 */
    }

    /* 목록 버튼 스타일 */
    .button-group input[type="button"]#list {
        background-color: #6c757d; /* 회색 배경 */
        color: #fff; /* 흰색 텍스트 */
    }

    /* 목록 버튼 마우스 오버 스타일 */
    .button-group input[type="button"]#list:hover {
        background-color: #495057; /* 어두운 회색 배경 */
    }

    /* 결과 텍스트 스타일 */
    #resultInsert {
        text-align: center; /* 텍스트 중앙 정렬 */
        font-size: 16px; /* 텍스트 크기 설정 */
        font-weight: bold; /* 텍스트 굵게 설정 */
        margin-top: 10px; /* 텍스트 상단 여백 추가 */
    }

    /* 테이블의 라벨 스타일 */
    td:first-child {
        background-color: #f7f9fc; /* 연한 회색 배경 */
        font-weight: bold; /* 텍스트 굵게 설정 */
        color: #333; /* 진한 회색 텍스트 */
        text-align: center; /* 텍스트 중앙 정렬 */
        border-right: 1px solid #e9ecef; /* 셀 오른쪽 경계 추가 */
    }

    td:last-child {
        background-color: #fff; /* 흰색 배경 */
    }

    /* 모바일 반응형 스타일 */
    @media (max-width: 768px) {
        .container {
            width: 90%; /* 모바일에서는 컨테이너 너비를 늘림 */
        }

        h1 {
            font-size: 24px; /* 제목 크기를 줄임 */
        }

        th, td {
            font-size: 12px; /* 테이블 텍스트 크기 줄임 */
        }

        .button-group input[type="button"] {
            font-size: 14px; /* 버튼 텍스트 크기 줄임 */
            padding: 10px 20px; /* 버튼 내부 여백 줄임 */
        }
    }
</style>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
<h1>공지사항 글쓰기</h1>
<div class="container">
    <!-- 입력 폼 영역 -->
    <table>
        <tr>
            <td>작성자</td>
            <td>
                <input type="text" name="writer" value="<%=id %>" readonly />
            </td>
        </tr>
        <tr>
            <td>제목</td>
            <td>
                <input type="text" name="title" />
            </td>
        </tr>
        <tr>
            <td>내용</td>
            <td>
                <textarea name="content" rows="10"></textarea>
            </td>
        </tr>
    </table>
    <!-- 버튼 영역 -->
    <div class="button-group">
        <input type="button" id="registration1" value="등록">
        <input type="button" id="list" value="목록">
    </div>
    <div id="resultInsert"></div>
</div>
<script type="text/javascript">
    // 목록 버튼 클릭 이벤트
    $("#list").click(function(event) {
        event.preventDefault();
        location.href = "<%=contextPath%>/classroomboard/noticeList.bo?center=/view_classroom/assignment_notice/professorNotice.jsp&courseId=<%=course_id %>&nowPage=<%=nowPage%>&nowBlock=<%=nowBlock%>";
    });

    // 등록 버튼 클릭 이벤트
    $("#registration1").click(function(event) {
        event.preventDefault();

        let writer = $("input[name=writer]").val();
        let title = $("input[name=title]").val();
        let content = $("textarea[name=content]").val();

        $.ajax({
            url: "<%=contextPath%>/classroomboard/noticeWritePro.bo?courseId=<%=course_id%>",
            type: "post",
            async: true,
            data: { w: writer, t: title, c: content },
            dataType: "text",
            success: function(responseData) {
                if (responseData === "1") {
                    $("#resultInsert").text("글 작성 완료!").css("color", "green");
                    if (window.confirm("작성한 글을 조회해서 보기 위해 목록 페이지로 이동하시겠습니까?")) {
                        location.href = "<%=contextPath%>/classroomboard/noticeList.bo?center=/view_classroom/assignment_notice/professorNotice.jsp&courseId=<%=course_id %>&nowPage=<%=nowPage%>&nowBlock=<%=nowBlock%>";
                    }
                } else {
                    $("#resultInsert").text("글 작성 실패!").css("color", "red");
                }
            }
        });
    });
</script>
</body>
</html>
