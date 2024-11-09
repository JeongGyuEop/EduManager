<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<% 
    request.setCharacterEncoding("UTF-8"); 
    String contextPath = request.getContextPath();

	//Session내장객체 메모리 영역에 session값 얻기 (학생인지, 교수인지, 관리자인지)
	String userRole = (String)session.getAttribute("role");
	String userName = (String)session.getAttribute("name");
	
	// 역할에 따른 메뉴 항목과 링크 설정
    Map<String, String> menuItems = new LinkedHashMap<String, String>(); // LinkedHashMap<>() -> 순서를 보장하기 위해 사용
    menuItems.put("홈", contextPath + "/home.jsp");

    if ("학생".equals(userRole)) {
        menuItems.put("강의실", contextPath + "/lectureRoom.jsp"); // 예
        menuItems.put("마이페이지", contextPath + "/myPage.jsp");
        menuItems.put("공지사항", contextPath + "/notice.jsp");
    } else if ("교수".equals(userRole)) {
        menuItems.put("강의 관리", contextPath + "/lectureManagement.jsp");
        menuItems.put("학생 관리", contextPath + "/studentManagement.jsp");
        menuItems.put("공지사항", contextPath + "/professorNotice.jsp");
    } else { // 관리자
        menuItems.put("사용자 관리", contextPath + "/adminCon/userManagement.bo?center=/admin/userManagement.jsp"); // 여기까지 진행.
        menuItems.put("학사 관리", contextPath + "/academicManagement.jsp");
        menuItems.put("정보 관리", contextPath + "/infoManagement.jsp");
    }
    
 	// 선택된 메뉴 추적 변수 설정
    String selectedMenu = request.getParameter("selectedMenu");
    if (selectedMenu == null) {
        selectedMenu = "홈"; // 기본값을 '홈'으로 설정
    }
	
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Top Section</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%= contextPath %>/css/starttop.css">
    <style>
	    /* 요소 간 간격 조정 */
		.navbar-nav .nav-item {
		    margin: 0 70px; /* 요소 간 간격 설정 */
		}
		
		.navbar {
		    justify-content: center; /* 네비게이션 바 전체 중앙 정렬 */
		}

    </style>
</head>
<body>

    <!-- 네비게이션 바 -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark d-flex flex-column align-items-center">
        <div class="container-fluid d-flex justify-content-between align-items-center w-100">
            <!-- 사이드바 버튼 -->
            <button class="btn btn-outline-light" type="button" data-bs-toggle="offcanvas" data-bs-target="#sidebar" aria-controls="sidebar">
                <i class="fas fa-bars"></i>
            </button>
            <!-- 중앙 제목 -->
            <a class="navbar-brand mx-auto text-center" href="#" style="position: absolute; left: 50%; transform: translateX(-50%);">
                <i class="fas fa-graduation-cap"></i> 학사 지원 프로그램
            </a>
            <!-- 환영 메시지 -->
            <div class="navbar-text">
				반갑습니다. &nbsp;&nbsp;<b><%=userName%></b> <%=userRole %>님!&nbsp;&nbsp;
          		<button type="button" class="btn btn-primary" onclick="location.href='<%=contextPath%>/member/logout.me'">로그아웃</button>
        	</div>
        </div>
        
        <!-- 아래 줄 바꿈된 메뉴 항목들, 간격 조정 -->
        <div class="container-fluid d-flex justify-content-center mt-2">
		    <ul class="navbar-nav d-flex flex-row justify-content-center"> <!-- 이 부분에 justify-content-center 추가 -->
<%
												// 키-값 쌍을 포함하는 Set을 반환 -> 예) ["강의", contextPath + "/lectureRoom.jsp"]
			for(Map.Entry<String, String> entry : menuItems.entrySet()) {
				
				// 현재 메뉴가 선택된 메뉴인지 확인하고 세션에 저장
		        String menuKey = entry.getKey();
		        if (menuKey.equals(selectedMenu)) {
		            session.setAttribute("selectedMenu", selectedMenu);
		        }
%>
				<li class="nav-item">
        			<a class="nav-link <%= entry.getKey().equals(selectedMenu) ? "active" : "" %>"  href="<%=entry.getValue()%>">
        				<%= entry.getKey() %>
        			</a>
    			</li>
<%			
			}
%>
		    </ul>
		</div>

    </nav>

    <!-- 사이드바 토글 -->
    <div class="offcanvas offcanvas-start" tabindex="-1" id="sidebar" aria-labelledby="sidebarLabel">
        <div class="offcanvas-header">
            <h5 class="offcanvas-title" id="sidebarLabel">메뉴</h5>
            <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
        </div>
        <div class="offcanvas-body">
            <ul class="list-group">
    			<jsp:include page="sidebar.jsp" />
            </ul>
        </div>
    </div>

    <!-- Font Awesome and Bootstrap JS -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/js/all.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
