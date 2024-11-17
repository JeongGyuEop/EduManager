<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	String contextPath = request.getContextPath();
%>

<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>EveryOne</title>
<%-- <link rel="stylesheet" type="text/css"
	href="<%=contextPath%>/css/style.css">
<link rel="stylesheet" type="text/css"
	href="<%=contextPath%>/css/mobile.css"
	media="screen and (max-width : 568px)">
<script type="text/javascript" src="<%=contextPath%>/js/mobile.js"></script> --%>

<style>
html, body {
	margin: 0;
	padding: 0;
	height: 100%;
}

.full-height-table {
	width: 100%;
	height: 100%;
}
</style>
</head>
<body>

	<c:set var="center" value="${requestScope.center}" />
	<c:set var="top" value="${requestScope.top}" />


	<%-- 메인페이지의 top 영역 (보류) - /view_start/starttop.jsp
	
		<c:if test="${top == null }">		
			<c:set var="top" value="/view_start/starttop.jsp"/>
		</c:if> 
	--%>

	<c:if test="${center == null }">
		<c:set var="center" value="/view_start/startcenter.jsp" />
	</c:if>

	<center>
		<table class="full-height-table">
			<tr align="left">
				<td height="10%"><jsp:include page="top.jsp" /></td>
			</tr>
			<tr>
				<td height="70%" align="center"><jsp:include page="${center}" />
				</td>
			</tr>
			<tr align="left">
				<td height="20%"><jsp:include page="bottom.jsp" /></td>
			</tr>
		</table>
	</center>



</body>
</html>