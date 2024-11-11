<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%////
	request.setCharacterEncoding("UTF-8");
	String contextPath = request.getContextPath();
%>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Insert title here</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
		integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
		crossorigin="anonymous"></script>

</head>

<body>
	<header class="p-1">
		<div class="container">
			<div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
				<a href="/" class="d-flex align-items-center mb-2 mb-lg-0 link-body-emphasis text-decoration-none">
					<svg class="bi me-2" width="40" height="32" role="img" aria-label="Bootstrap">
						<use xlink:href="#bootstrap"></use>
					</svg>
				</a>

				<ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
					<li><a href="#" class="nav-link px-2">홈</a></li>
					<li><a href="#" class="nav-link px-2">정보1</a></li>
					<li><a href="#" class="nav-link px-2">정보2</a></li>
					<li><a href="#" class="nav-link px-2">커뮤니티</a></li>
					<li><a href="#" class="nav-link px-2">공지사항</a></li>
				</ul>

				<form class="col-12 col-lg-auto mb-3 mb-lg-0 me-lg-3" role="search">
					<input type="search" class="form-control form-control-dark" placeholder="Search..."
						aria-label="Search">
				</form>

				<%
		//Session내장객체 메모리 영역에 session값 얻기
		String userId = (String)session.getAttribute("userId");
		String userName = (String)session.getAttribute("userName");

		//Session에 값이 저장되어 있지 않으면?
		if(userId == null){
%>
				<div class="text-end">
					<button type="button" class="btn btn-outline-light me-2"
						onclick="location.href='<%=contextPath%>/member/login.me'">로그인</button>
					<button type="button" class="btn btn-warning">회원가입</button>
				</div>
				<%
		}else{
%>
				<div class="text-end">
					환영합니다. &nbsp;&nbsp;<b><%=userName%></b> 님!&nbsp;&nbsp;
					<button type="button" class="btn btn-primary"
						onclick="location.href='<%=contextPath%>/member/logout.me'">로그아웃</button>
				</div>
				<%
		}
%>

			</div>
		</div>
	</header>

</body>

</html>
