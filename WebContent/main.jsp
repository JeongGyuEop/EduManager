<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
	String contextPath = request.getContextPath();
%>

<!doctype html>
<html>

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>EveryOne</title>
	<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/style.css">
	<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/mobile.css"
		media="screen and (max-width : 568px)">
	<script type="text/javascript" src="<%=contextPath %>/js/mobile.js"></script>
</head>

<body>

	<c:set var="center" value="${requestScope.center}" />


	<c:if test="${center == null }">
		<c:set var="center" value="center.jsp" />
	</c:if>

	<center>
		<table width="100%" height="100%">
			<tr align="left">
				<td height="25%">
					<jsp:include page="top.jsp" />
				</td>
			</tr>
			<tr>
				<td height="50%" align="center">
					<jsp:include page="${center}" />
				</td>
			</tr>
			<tr>
				<td height="25%">
					<jsp:include page="footer.jsp" />
				</td>
			</tr>
		</table>
	</center>



</body>

</html>
