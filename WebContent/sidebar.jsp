<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<% 
    request.setCharacterEncoding("UTF-8"); 
	String contextPath = request.getContextPath();
	
	String selectedMenu = (String) session.getAttribute("selectedMenu");
	System.out.println(selectedMenu);
	
	String userRole = (String) session.getAttribute("role"); // 사용자 역할 가져오기
%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
 if ("학생".equals(userRole)) { 
        // 학생 역할이면서 특정 메뉴가 선택된 경우 하위 메뉴 출력
        if ("홈".equals(selectedMenu)) {
%>
            <ul>
                <li><a href="#">강의실</a></li>
                <li><a href="<%= contextPath %>/myPage.jsp">마이페이지</a></li>
                <li><a href="<%= contextPath %>/notice.jsp">공지사항</a></li>
            </ul>
<%
        } else if ("강의실".equals(selectedMenu)) { // 수강신청, 과제제출, 성적조회
%>
		
       <!-- 
       		<ul>
                <li><a href="<%= contextPath %>/member/adminMain.bo?center=/view_admin/studentManage.jsp">수강신청</a></li>
                <li><a href="<%= contextPath %>/member/adminMain.bo?center=/view_admin/professorManage.jsp">과제제출</a></li>
                <li><a href="<%= contextPath %>/member/adminMain.bo?center=/view_admin/adminManage.jsp">성적조회</a></li>
            </ul>
		--> 
		
<%
        } else if ("마이페이지".equals(selectedMenu)) { // 개인 정보 수정
%>
            <ul>
                <li><a href="<%= contextPath %>/member/adminPage.bo?center=/view_admin/departmentManage.jsp">개인 정보 조회</a></li>
            </ul>
<%
        } else if ("공지사항".equals(selectedMenu)) { // 학사 일정 조회, 공지사항 확인
%>
            <ul>
                <li><a href="<%= contextPath %>/member/adminPage.bo?center=/view_admin/noticeManage.jsp">학사 일정</a></li>
                <li><a href="<%= contextPath %>/member/adminPage.bo?center=/view_admin/scheduleManage.jsp">공지사항</a></li>
            </ul>
<%
        }
    }else if ("교수".equals(userRole)) { 
        // 교수 역할이면서 특정 메뉴가 선택된 경우 하위 메뉴 출력
        if ("홈".equals(selectedMenu)) {
%>
            <ul>
                <li><a href="#">강의실</a></li>
                <li><a href="">강의 관리</a></li>
                <li><a href="">학생 관리</a></li>
                <li><a href="">공지사항</a></li>
            </ul>
<%
        } else if ("강의실".equals(selectedMenu)) { // 강의별(과제 제출, 공지 사항 등록, 일대일 채팅)
%>
	<!-- 
            <ul>
                <li><a href="">강의 개설</a></li>
                <li><a href="">과제 제출</a></li> (학생에게)
                <li><a href="">공지사항 등록</a></li>
            </ul>
	 -->
<%
        } else if ("강의 관리".equals(selectedMenu)) { // 강의 등록(개설), 강의 조회/수정/삭제
%>
            <ul>
                <li><a href="">강의 등록</a></li>
                <li><a href="">강의 조회</a></li>
            </ul>
<%
        } else if ("성적 관리".equals(selectedMenu)) { // 학생 성적 관리
%>
            <ul>
                <li><a href="">성적 관리</a></li>
            </ul>
<%
        } else if ("정보 관리".equals(selectedMenu)) {
%>
            <ul>
                <li><a href="">공지사항 관리</a></li>
                <li><a href="">학사일정 관리</a></li>
            </ul>
<%
        }
    } else if ("관리자".equals(userRole)) { 
        // 관리자 역할이면서 특정 메뉴가 선택된 경우 하위 메뉴 출력
        if ("홈".equals(selectedMenu)) {
%>
            <ul>
                <li><a href="<%= contextPath %>/member/adminPage.bo?center=/view_admin/studentManage.jsp">사용자 관리</a></li>
                <li><a href="<%= contextPath %>/member/adminPage.bo?center=/view_admin/departmentManage.jsp">학사 관리</a></li>
                <li><a href="<%= contextPath %>/member/adminMPage.bo?center=/view_admin/noticeManage.jsp">정보 관리</a></li>
            </ul>
<%
        } else if ("사용자 관리".equals(selectedMenu)) {
%>
            <ul>
             	<li><a href="<%= contextPath %>/menu/topside.do?center=/view_admin/studentManage.jsp&selectedMenu=사용자 관리">학생관리</a></li>
                <%-- <li><a href="<%= contextPath %>/member/adminPage.bo?center=/view_admin/studentManage.jsp&selectedMenu=사용자 관리">학생관리</a></li> --%>
                <li><a href="<%= contextPath %>/member/adminPage.bo?center=/view_admin/professorManage.jsp&selectedMenu=사용자 관리">교수관리</a></li>
                <li><a href="<%= contextPath %>/member/adminPage.bo?center=/view_admin/adminManage.jsp&selectedMenu=사용자 관리">관리자 관리</a></li>
            </ul>
<%
        } else if ("학사 관리".equals(selectedMenu)) {
%>
            <ul>
                <li><a href="<%= contextPath %>/member/adminPage.bo?center=/view_admin/departmentManage.jsp&selectedMenu=학사 관리">학과 관리</a></li>
                <li><a href="<%= contextPath %>/member/adminPage.bo?center=/view_admin/classroomManage.jsp&selectedMenu=학사 관리">강의실 관리</a></li>
            </ul>
<%
        } else if ("정보 관리".equals(selectedMenu)) {
%>
            <ul>
                <li><a href="<%= contextPath %>/member/adminPage.bo?center=/view_admin/noticeManage.jsp&selectedMenu=정보 관리">공지사항 관리</a></li>
                <li><a href="<%= contextPath %>/member/adminPage.bo?center=/view_admin/scheduleManage.jsp&selectedMenu=정보 관리">학사일정 관리</a></li>
            </ul>
<%
        }
    }
%>
</body>
</html>