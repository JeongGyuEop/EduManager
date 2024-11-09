<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
        
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<% 
	request.setCharacterEncoding("UTF-8"); 
	String contextPath = request.getContextPath();	
%>
    
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Top Section</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
	<link rel="stylesheet" href="<%= contextPath %>/css/starttop.css">


</head>
<body>
    <div class="top-section" style="margin-bottom:130px;">
        <!-- 로고 -->
        <div class="logo">
            <img src="<%=contextPath%>/img/background/uni_logo.png" alt="University Logo">
        </div>

        <!-- 타이틀 및 설명 -->
        <div class="title">
            <h1><i class="fas fa-graduation-cap"></i> 학사 지원 프로그램</h1>
            <p>학생과 교수님을 위한 통합 학사 관리 시스템을 통해 강의 관리, 성적 조회, 공지사항 확인 등을 한곳에서 편리하게 이용하세요.</p>
        </div>

        <!-- 검색 기능 -->
        <div class="search-bar">
            <input type="text" placeholder="검색어를 입력하세요" aria-label="Search">
            <button type="submit"><i class="fas fa-search"></i> 검색</button>
        </div>
    </div>

    <!-- Font Awesome and Bootstrap JS -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/js/all.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
