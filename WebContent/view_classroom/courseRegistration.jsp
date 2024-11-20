<%@page import="Vo.StudentVo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Vo.CourseVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String contextPath = request.getContextPath();	
	ArrayList<CourseVo> courseList = (ArrayList<CourseVo>) session.getAttribute("courseList");

%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>교수 강의 목록</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css"
        rel="stylesheet" crossorigin="anonymous">
    <script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
    <style>
        .btn-green {
            background-color: #4CAF50;
            color: white;
        }

        .btn-green:hover {
            background-color: #45a049;
        }

        
    </style>
</head>
<body class="bg-light">
	<main class="container my-5">
		<h2 class="text-center mb-4">학생 목록</h2>
		<div class="card shadow-sm">
            <div class="card-body">
                <table class="table table-bordered table-hover text-center align-middle">
                    <thead class="table-success">
                        <tr>
                            <th scope="col">선 택</th>
                            <th scope="col">과목 코드</th>
                            <th scope="col">과목 이름</th>
                            <th scope="col">교수명</th>
                            <th scope="col">강의실</th>
                            <th scope="col">수 강</th>
                            <th scope="col">취 소</th>
                            
                        </tr>
                    </thead>
                    <tbody>
                        <%
                        for (CourseVo course : courseList) {
                        	
                        %>
                        <tr>
							<td><input type="checkbox"></td>
							<td><%=course.getCourse_id() %></td>
                            <td><%=course.getCourse_name() %></td>
                            <td><%=course.getProfessor_id() %></td>
                            <td><%=course.getRoom_id() %></td>
                             <td>
                                <button id="" 
                                        class="btn btn-green register-btn" 
                                        onclick="">수강</button>
                            </td>
                             <td>
                                <button id="" 
                                        class="btn btn-green register-btn" 
                                        onclick="">취소</button>
                            </td>
                        </tr>
                        <%
                        }
                        %>
                    </tbody>
                </table>
            </div>
        </div>
    </main>
    <script type="text/javascript">
        

    </script>
</body>
</html>