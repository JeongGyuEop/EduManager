<%@page import="Vo.MemberVo"%>
<%@page import="Vo.BoardVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
 
<%
	request.setCharacterEncoding("UTF-8");
	String contextPath = request.getContextPath();
	String name = (String)session.getAttribute("name");
	
	//부모글의 글 번호를 전달받아
	//DB로부터 부모글의 b_group열 값과, b_level열값을 조회 해야 합니다. 
	//그래서 받아 온 것입니다.
	String notice_id = (String)request.getAttribute("notice_id");
	MemberVo vo = (MemberVo)request.getAttribute("vo");
	 
	String id = (String)session.getAttribute("id");
	if(id == null){//로그인 하지 않았을경우
%>		
	<script>	
		alert("로그인을 하셔야 작성하실 수 있습니다.");
		history.back();
	</script>
<%
	}
%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>답변글 작성 화면</title>
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
    <h1 class="page-title">답변글 작성 화면</h1>
    <form action="<%=contextPath%>/Board/replyPro.do" method="post" onsubmit="return check();">
        <input type="hidden" name="super_notice_id" value="<%=notice_id%>">
        <input type="hidden" name="id" value="<%=id%>">

        <div class="mb-3 row">
            <label for="writer" class="col-sm-2 col-form-label text-end"><strong>작성자</strong></label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="writer" name="writer" value="<%=name %>" readonly>
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
            <input type="submit" id="reply" value="답변" class="btn btn-primary">
            <input type="button" id="list" value="목록" class="btn btn-secondary"
                   onclick="location.href='<%=contextPath%>/Board/list.bo?center=/view_admin/noticeManage.jsp&nowBlock=0&nowPage=0'">
        </div>
    </form>
    <p id="pwInput"></p>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
    function check() {
        var writer = $("input[name='writer']").val();
        var title = $("input[name='title']").val();
        var content = $("textarea[name='content']").val();
        
        if (writer === "" || title === "" || content === "") {
            $("#pwInput").text("모두 작성해 주세요.");
            return false;
        }
        return true;
    }
</script>
</body>
</html>
