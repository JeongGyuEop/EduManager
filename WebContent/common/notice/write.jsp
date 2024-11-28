<%@page import="Vo.BoardVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("UTF-8");
	String contextPath = request.getContextPath();

	// list.jsp 화면에서 페이지 번호 클릭 시
	String nowPage = (String) request.getAttribute("nowPage");
	String nowBlock = (String) request.getAttribute("nowBlock");

	String id = (String) session.getAttribute("id");
	String name = (String) session.getAttribute("name");
%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">
<style>
    /* 컨테이너 스타일 */
    .form-container {
        max-width: 800px;
        margin: 50px auto;
        padding: 20px;
        background: #fff;
        border-radius: 8px;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    }

    /* 페이지 제목 */
    .page-title {
        text-align: center;
        font-size: 24px;
        font-weight: bold;
        margin-bottom: 30px;
    }

    /* 버튼 그룹 정렬 */
    .btn-group {
        display: flex;
        justify-content: flex-end;
        gap: 10px;
        margin-top: 20px;
    }

    /* 입력 필드 스타일 */
    .form-control[readonly] {
        background-color: #e9ecef;
        cursor: not-allowed;
    }

    #pwInput {
        color: red;
        font-weight: bold;
        text-align: center;
        margin-top: 10px;
    }
</style>
</head>
<body class="bg-light">
<div class="container form-container">
    <h1 class="page-title">글 작성 화면</h1>
    <form>
        <div class="mb-3 row">
            <label for="writer" class="col-sm-2 col-form-label text-end"><strong>작성자</strong></label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="writer" value="<%=name %>" readonly>
                <input type="hidden" name="writer" value="<%=id %>" />
            </div>
        </div>
        <div class="mb-3 row">
            <label for="title" class="col-sm-2 col-form-label text-end"><strong>제목</strong></label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="title" name="title">
            </div>
        </div>
        <div class="mb-3 row">
            <label for="content" class="col-sm-2 col-form-label text-end"><strong>내용</strong></label>
            <div class="col-sm-10">
                <textarea class="form-control" id="content" name="content" rows="10"></textarea>
            </div>
        </div>
        <div class="btn-group">
            <input type="button" id="registration1" value="등록" class="btn btn-primary">
            <input type="button" id="list" value="목록" class="btn btn-secondary">
        </div>
        <p id="pwInput"></p>
    </form>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
    // 목록 버튼 클릭 이벤트
    $("#list").click(function(event){
        event.preventDefault(); // 기본 동작 차단
        location.href="<%=contextPath%>/Board/list.bo?center=/view_admin/noticeManage.jsp&nowPage=<%=nowPage%>&nowBlock=<%=nowBlock%>";
    });

    // 등록 버튼 클릭 이벤트
    $("#registration1").click(function(event){
        event.preventDefault();

        let writer = $("input[name=writer]").val(); // 작성자
        let title = $("input[name=title]").val(); // 제목
        let content = $("textarea[name=content]").val(); // 내용

        if (!title || !content) {
            $("#pwInput").text("제목과 내용을 모두 입력해주세요.");
            return;
        }

        // Ajax를 통해 서버에 데이터 전송
        $.ajax({
            url: "<%=contextPath%>/Board/writePro.bo",
            type: "post",
            async: true,
            data: { w: writer, t: title, c: content },
            dataType: "text",
            success: function(responseData) {
                if (responseData === "1") {
                    $("#pwInput").text("글 작성 완료!").css("color", "green");

                    let result = window.confirm("작성한 글을 조회하시겠습니까?");
                    if (result) {
                        location.href = "<%=contextPath%>/Board/list.bo?center=/view_admin/noticeManage.jsp";
                    }
                } else {
                    $("#pwInput").text("글 작성 실패!").css("color", "red");
                }
            }
        });
    });
</script>
</body>
</html>
