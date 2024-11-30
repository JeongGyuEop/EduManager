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
