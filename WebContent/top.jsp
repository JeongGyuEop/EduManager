<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%
	request.setCharacterEncoding("UTF-8");
	String contextPath = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
 <meta name="viewport" content="width=device-width, initial-scale=1">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

</head>
<body>
<header class="p-1">
    <div class="container">
      	<div class="top-nav" style="display: flex; align-items: center; justify-content: space-between; padding: 10px 20px; background-color: #f8f9fa; border-bottom: 1px solid #ddd;">
        <div class="logo">
            <img src="img/university_logo.png" alt="University Logo" style="width: 50px;">
        </div>
        <div class="menu" style="display: flex; gap: 20px;">
            <a href="#" style="text-decoration: none; color: #333; font-weight: bold;">홈</a>
            <a href="#" style="text-decoration: none; color: #333; font-weight: bold;">MY</a>
            <a href="#" style="text-decoration: none; color: #333; font-weight: bold;">수업</a>
            <a href="#" style="text-decoration: none; color: #333; font-weight: bold;">수강신청</a>
            <a href="#" style="text-decoration: none; color: #333; font-weight: bold;">성적</a>
            <a href="#" style="text-decoration: none; color: #333; font-weight: bold;">학사정보</a>
        </div>

<%
		//Session내장객체 메모리 영역에 session값 얻기 (학생인지, 교수인지, 관리자인지)
		String userId = (String)session.getAttribute("userId");
		String userName = (String)session.getAttribute("userName");
		
		//Session저장된 값이 학생이면?
		if(userId.equals("학생")){
			// 위의 a 태그들 중에서 가장 첫번째 값(홈)을 선택해서 그에 맞는 사이드바 값들을 보여준다.
%>
		    <div class="side-nav" style="width: 200px; padding: 20px; background-color: #f8f9fa; border-right: 1px solid #ddd; height: 100vh;">
		        <h4 style="font-weight: bold; color: #333; border-bottom: 2px solid #007bff; padding-bottom: 5px;">수강신청</h4>
		        <div class="nav-links" style="margin-top: 20px; display: flex; flex-direction: column; gap: 10px;">
		            <a href="#" style="text-decoration: none; color: #007bff; font-weight: bold;">강의 시간표 조회</a>
		            <a href="#" style="text-decoration: none; color: #007bff; font-weight: bold;" class="active">예비 수강 신청</a>
		            <a href="#" style="text-decoration: none; color: #007bff; font-weight: bold;">수강 신청</a>
		            <a href="#" style="text-decoration: none; color: #007bff; font-weight: bold;">수강 신청 내역 조회</a>
		        </div>
		    </div>

<%
		//Session저장된 값이 교수이면?
		}else if(userId.equals("교수")){
%>	
			
<%			
		//Session저장된 값이 관리자이면?
		} else {
%>
	
<%		
		}
%>        
			<div class="text-end">
				환영합니다. &nbsp;&nbsp;<b><%=userName%></b> <%=userId %>님!&nbsp;&nbsp;
          		<button type="button" class="btn btn-primary" onclick="location.href='<%=contextPath%>/member/logout.me'">로그아웃</button>
        	</div>
        
      	</div>
     </div>
  </header>

</body>
</html>