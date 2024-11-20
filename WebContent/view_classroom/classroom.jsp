<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 

<%
	String contextPath = request.getContextPath();
	String role = (String) session.getAttribute("role");
	String profName = (String) session.getAttribute("name");

%>    

    
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>강의실 메인 페이지</title>
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
        <link href="<%=contextPath %>/css/classroom_styles.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script> <!-- jQuery 추가 -->
        <script>
        $(document).ready(function() {
            $.ajax({
                url: '<%=contextPath%>/classroom/courseNameSearch.do', // 실제 서버 URL 경로로 변경 필요
                method: 'GET',
                dataType: 'json',
                success: function(courseList) {
                	console.log(courseList);  // 클라이언트 측에서 받아진 데이터 확인
                	
                    if (courseList && courseList.length > 0) {
                        let courseIndex = 1;
                        courseList.forEach(course => {
                        	console.log(course.courseName);
                        	console.log(course);
                            let courseHtml = 
	                            '<a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#collapseCourse' + courseIndex + '" aria-expanded="false" aria-controls="collapseCourse' + courseIndex + '">' +
	                            	'<div class="sb-nav-link-icon"><i class="fas fa-columns"></i></div>' +
	                            	course.courseName + ' <!-- 강의 이름 표시 -->' +
	                            	'<div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>' +
	                            '</a>' +
	                            '<div class="collapse" id="collapseCourse' + courseIndex + '" aria-labelledby="headingCourse' + courseIndex + '" data-bs-parent="#sidenavAccordion">' +
	                            	'<nav class="sb-sidenav-menu-nested nav">' +
	                            		'<a class="nav-link" href="assignment.html">과제</a>' +
	                            		'<a class="nav-link" href="material.html">자료</a>' +
	                            	'</nav>' +
	                            '</div>';
                            $('#courseTargetElement').append(courseHtml);
                            console.log("Appended HTML: ", courseHtml); // 추가된 HTML을 콘솔에 출력

                            courseIndex++;
                        });
                    }
                },
                error: function(error) {
                    console.error('Error fetching course list:', error);
                }
            });
        });
        </script>
    </head>
    <body class="sb-nav-fixed">
        <nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
            <!-- Navbar Brand-->
            <a class="navbar-brand ps-3" href="index.html">강의실</a>
            <!-- Sidebar Toggle-->
            <button class="btn btn-link btn-sm order-1 order-lg-0 me-4 me-lg-0" id="sidebarToggle" href="#!"><i class="fas fa-bars"></i></button>
            <!-- Navbar Search-->
            
			<!-- Navbar-->
			    <ul class="navbar-nav ms-auto d-flex align-items-center">
			        <li class="nav-item">
			            <p class="text-white mb-0 me-3">반갑습니다. <%=profName %> <%=role %>님!</p>
			        </li>
			        <li class="nav-item dropdown">
			            <a class="nav-link dropdown-toggle text-white" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false"><i class="fas fa-user fa-fw"></i></a>
			            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
			                <li><a class="dropdown-item" href="<%=contextPath%>/member/main.bo?center=view_professor/professorHome.jsp">강의실 나가기</a></li>
			                <li><hr class="dropdown-divider" /></li>
			                <li><a class="dropdown-item" href="<%=contextPath%>/member/logout.me">로그아웃</a></li>
			            </ul>
			        </li>
			    </ul>
        </nav>
        <div id="layoutSidenav">
            <div id="layoutSidenav_nav">
                <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
                    <div class="sb-sidenav-menu">
                        <div class="nav">
 

					<%	if(role.equals("교수")) { %>
							
                        	<!-- 사이드바 수강관리 영역 -->
                            <div class="sb-sidenav-menu-heading">Course Manage</div>
                            <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#collapseLayouts" aria-expanded="false" aria-controls="collapseLayouts">
                                <div class="sb-nav-link-icon"><i class="fas fa-columns"></i></div>
                                수강 관리
                                <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                            </a>
                            <div class="collapse" id="collapseLayouts" aria-labelledby="headingOne" data-bs-parent="#sidenavAccordion">
                                <nav class="sb-sidenav-menu-nested nav">
                                    <a class="nav-link" href="<%=contextPath%>/classroom/course_register.bo?classroomCenter=/view_classroom/courseRegister.jsp">수강 등록</a>
                                    <a class="nav-link" href="<%=contextPath%>/classroom/course_search.bo?classroomCenter=/view_classroom/courseSearch.jsp">수강 조회(수정/삭제)</a>
                                </nav>
                            </div>
							
							<!-- 사이드바 나의 수업 영역 -->
							<div class="sb-sidenav-menu-heading">My Courses</div>
							<!-- AJAX로 동적 생성되는 강의 목록 -->
                            <div id="courseTargetElement"></div>
                            
                            <!-- 사이드바 성적 조회 영역 -->
                            <div class="sb-sidenav-menu-heading">SCORE</div>
                            <a class="nav-link" href="<%=contextPath%>/classroom/course_search.bo?classroomCenter=/view_classroom/courseList.jsp">
                                <div class="sb-nav-link-icon"><i class="fas fa-chart-area"></i></div>
                                성적 관리
                            </a>
                            <a class="nav-link" href="tables.html">
                                <div class="sb-nav-link-icon"><i class="fas fa-table"></i></div>
                                ?????
                            </a>
                            
					<%	} else { %>
                            <!-- 사이드바 수강신청 영역 -->
                            <div class="sb-sidenav-menu-heading">Course Registration</div>
                            <a class="nav-link" href="<%=contextPath%>/classroom/course_registration.bo?classroomCenter=/view_classroom/courseRegistration.jsp">
                                <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                                수강신청
                            </a>
                            
                            <!-- 사이드바 나의 수업 영역 -->
                            <div class="sb-sidenav-menu-heading">My Course</div>
                            <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#collapseLayouts" aria-expanded="false" aria-controls="collapseLayouts">
                                <div class="sb-nav-link-icon"><i class="fas fa-columns"></i></div>
                                ??????
                                <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                            </a>
                            <div class="collapse" id="collapseLayouts" aria-labelledby="headingOne" data-bs-parent="#sidenavAccordion">
                                <nav class="sb-sidenav-menu-nested nav">
                                    <a class="nav-link" href="layout-static.html">Static Navigation</a>
                                    <a class="nav-link" href="layout-sidenav-light.html">Light Sidenav</a>
                                </nav>
                            </div>
                            <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#collapsePages" aria-expanded="false" aria-controls="collapsePages">
                                <div class="sb-nav-link-icon"><i class="fas fa-book-open"></i></div>
                                Pages
                                <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                            </a>
                            <div class="collapse" id="collapsePages" aria-labelledby="headingTwo" data-bs-parent="#sidenavAccordion">
                                <nav class="sb-sidenav-menu-nested nav accordion" id="sidenavAccordionPages">
                                    <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#pagesCollapseAuth" aria-expanded="false" aria-controls="pagesCollapseAuth">
                                        Authentication
                                        <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                                    </a>
                                    <div class="collapse" id="pagesCollapseAuth" aria-labelledby="headingOne" data-bs-parent="#sidenavAccordionPages">
                                        <nav class="sb-sidenav-menu-nested nav">
                                            <a class="nav-link" href="login.html">Login</a>
                                            <a class="nav-link" href="register.html">Register</a>
                                            <a class="nav-link" href="password.html">Forgot Password</a>
                                        </nav>
                                    </div>
                                    <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#pagesCollapseError" aria-expanded="false" aria-controls="pagesCollapseError">
                                        Error
                                        <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                                    </a>
                                    <div class="collapse" id="pagesCollapseError" aria-labelledby="headingOne" data-bs-parent="#sidenavAccordionPages">
                                        <nav class="sb-sidenav-menu-nested nav">
                                            <a class="nav-link" href="401.html">401 Page</a>
                                            <a class="nav-link" href="404.html">404 Page</a>
                                            <a class="nav-link" href="500.html">500 Page</a>
                                        </nav>
                                    </div>
                                </nav>
                            </div>
                            
                            <!-- 사이드바 성적 조회 영역 -->
                            <div class="sb-sidenav-menu-heading">SCORE</div>
                            <a class="nav-link" href="<%=contextPath%>/classroom/grade_search.bo?classroomCenter=/view_classroom/gradeList.jsp">
                                <div class="sb-nav-link-icon"><i class="fas fa-chart-area"></i></div>
                                성적 조회
                            </a>
                            <a class="nav-link" href="tables.html">
                                <div class="sb-nav-link-icon"><i class="fas fa-table"></i></div>
                                Tables
                            </a>
                    <%	} %> 
                        </div>
                    </div>
                </nav>
            </div>
            
<%
		String classroomCenter = (String)request.getAttribute("classroomCenter");
%>
            <!-- 중앙 콘텐츠 영역 -->
            <div id="layoutSidenav_content">
                <jsp:include page="<%= classroomCenter %>"/>  
            </div>
            
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="../js/scripts.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js" crossorigin="anonymous"></script>
        <script src="../js/datatables-simple-demo.js"></script>
    </body>
</html>