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
    <style>
        /* 전체 배경 및 top-section 스타일 */
        .top-section {
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 10px 20px;
            background-color: #f8f9fa;
            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
        }

        /* 로고 스타일 */
        .top-section .logo img {
            max-width: 60px;
        }

        /* 타이틀 및 설명 텍스트 스타일 */
        .top-section .title {
            text-align: center;
        }

        .top-section .title h1 {
            font-size: 1.5rem;
            font-weight: bold;
            color: #007bff;
            margin: 0;
            display: flex;
            align-items: center;
        }

        .top-section .title h1 i {
            margin-right: 8px;
        }

        .top-section .title p {
            margin: 5px 0 0;
            font-size: 0.9rem;
            color: #6c757d;
        }

        /* 검색창 스타일 */
        .top-section .search-bar {
            display: flex;
            align-items: center;
            background-color: white;
            border-radius: 50px;
            padding: 5px 10px;
            box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.1);
        }

        .top-section .search-bar input {
            border: none;
            outline: none;
            width: 160px;
            font-size: 0.9rem;
            border-radius: 50px;
            padding-left: 10px;
        }

        .top-section .search-bar button {
            border: none;
            background-color: #007bff;
            color: white;
            padding: 5px 12px;
            font-size: 0.9rem;
            border-radius: 50px;
            margin-left: 5px;
        }
    </style>
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
