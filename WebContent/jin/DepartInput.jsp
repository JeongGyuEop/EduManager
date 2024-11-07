<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>New Depart Input Page</title>
</head>
<body>
	<form action="${contextPath}/DMI/DepartmentInput.do" method="get">
		<label for="DepartmentInput">신규 학부명:</label> <input type="text"
			id="DepartmentNameInput" name="DepartmentNameInput">
		<button type="submit">제출</button>
	</form>
</body>
</html>
