<%@page import="Vo.MemberVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	request.setCharacterEncoding("utf-8");
	String contextPath = request.getContextPath();
	String userId = (String) session.getAttribute("id");
	System.out.println("booktradingboard userId check : " + userId);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>제목을 입력하세요</title>
</head>
<body>
	<form action="<%=contextPath%>/Board/bookPostUpload.bo" method="get">
		<input type="hidden" value="<%=userId%>" name="userId"> <input
			type="submit" value="글 쓰기">
	</form>
</body>
</html>
