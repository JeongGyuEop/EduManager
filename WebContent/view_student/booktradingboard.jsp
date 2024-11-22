<%@page import="Vo.BookPostVo"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String contextPath = request.getContextPath(); 
	String userId = (String) session.getAttribute("id");
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/MVCBoard/style.css"/>
</head>
<body>
	<form action="<%=contextPath%>/Book/bookPostUpload.bo" method="get">
		<input type="hidden" value="<%=userId%>" name="userId"> <input
			type="submit" value="글 쓰기">
	</form>

</body>
</html>
