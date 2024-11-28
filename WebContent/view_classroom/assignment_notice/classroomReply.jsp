<%@page import="Vo.MemberVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
 
<%
	request.setCharacterEncoding("UTF-8");
	String contextPath = request.getContextPath();
	
	//부모글의 글 번호를 전달받아
	//DB로부터 부모글의 b_group열 값과, b_level열값을 조회 해야 합니다. 
	//그래서 받아 온 것입니다.
	String notice_id = (String)request.getAttribute("notice_id");
	MemberVo vo = (MemberVo)request.getAttribute("vo");
	 
	String course_id = (String)request.getAttribute("course_id"); 
	String id = (String)session.getAttribute("id");
	if(id == null){//로그인 하지 않았을경우
%>		
	<script>	
		alert("로그인을 하셔야 작성하실수 있습니다.");
		history.back();
	</script>
<%
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>답변글 작성 화면</title>

<!-- 부트스트랩 CSS 추가 -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">

<style>
    /* 전체 컨테이너 스타일 */
    .container {
        max-width: 800px;
        margin: 50px auto;
        padding: 20px;
        background: #ffffff;
        border-radius: 10px;
        box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
    }

    /* 제목 스타일 */
    #t {
        text-align: center;
        font-size: 24px;
        font-weight: bold;
        margin-bottom: 20px;
        color: #007bff;
    }

    /* 테이블 스타일 */
    table {
        width: 100%;
    }

    .form-control:read-only {
        background-color: #e9ecef;
        color: #495057;
    }

    /* 버튼 그룹 스타일 */
    .button-group {
        display: flex;
        justify-content: center;
        gap: 20px;
        margin-top: 20px;
    }

    .btn-custom {
        width: 150px; /* 버튼의 동일한 너비 설정 */
        height: 45px; /* 버튼의 동일한 높이 설정 */
        font-size: 16px;
        font-weight: bold;
    }
</style>
</head>
<body class="bg-light">
	<div class="container">
		<p id="t">답변글 작성 화면</p>
	
		<form action="<%=contextPath%>/classroomboard/replyPro.do?courseId=<%=course_id %>" method="post" 
			onsubmit="return check();">
			
			<%--답변글(자식글)을 DB에 Insert하기 위해 주글(부모글)의 글번호를 같이 전달 합니다. --%>
			<input type="hidden" name="super_notice_id" value="<%=notice_id%>">
			
			<%--답변글을 작성하는 사람의 ID를 전달합니다. --%>
			<input type="hidden" name="id" value="<%=id%>">		
				
			<table class="table">
				<tr>
					<td class="text-end"><strong>작성자</strong></td>
					<td>
						<input type="text" class="form-control" name="writer" id="writer" value="<%=vo.getUser_name() %>" readonly>
					</td>
				</tr>
				<tr>
					<td class="text-end"><strong>제목</strong></td>
					<td>
						<input type="text" class="form-control" name="title" id="title">
					</td>
				</tr>
				<tr>
					<td class="text-end"><strong>내용</strong></td>
					<td>
						<textarea class="form-control" rows="10" name="content" id="content"></textarea>
					</td>
				</tr>
			</table>

			<!-- 버튼 그룹 -->
			<div class="button-group">
				<input type="submit" id="reply" value="답변" class="btn btn-primary btn-custom">
				<input type="button" id="list" value="목록" class="btn btn-secondary btn-custom" 
					onclick="location.href='<%=contextPath%>/classroomboard/noticeList.bo?center=/view_classroom/assignment_notice/professorNotice.jsp&nowBlock=0&nowPage=0&courseId=<%=course_id%>'">
			</div>
		</form>	
	</div>

	<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>

	<script type="text/javascript">
		// 입력값 확인 함수
		function check(){
			var writer = $("input[name='writer']").val();
			var title = $("input[name='title']").val();
			var content = $("textarea[name='content']").val();
			
			if( writer == "" || title == "" || content == "" ) {
				alert("모든 필드를 입력해 주세요.");
				return false;
			}
			return true;
		}
	</script>
</body>
</html>
