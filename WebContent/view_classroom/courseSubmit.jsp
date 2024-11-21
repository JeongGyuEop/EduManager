<%@page import="Vo.StudentVo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Vo.CourseVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    String contextPath = request.getContextPath();
    ArrayList<CourseVo> courseList = (ArrayList<CourseVo>) session.getAttribute("courseList");
    ArrayList<CourseVo> courseList2 = (ArrayList<CourseVo>) session.getAttribute("courseList2");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>교수 강의 목록</title>
    <link
        href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css"
        rel="stylesheet" crossorigin="anonymous">
    <script type="text/javascript"
        src="http://code.jquery.com/jquery-latest.min.js"></script>
    <style>
        .btn-green {
            background-color: #4CAF50;
            color: white;
        }

        .btn-green:hover {
            background-color: #45a049;
        }

        .btn-danger:hover {
            background-color: #d9534f;
        }
    </style>
</head>
<body class="bg-light">
    <main class="container my-5">
        <div class="card shadow-sm">
            <h2 class="text-center mb-4">수강 목록</h2>
            <div class="card-body">
                <table class="table table-bordered table-hover text-center align-middle">
                    <thead class="table-success">
                        <tr>
                            <th scope="col">과목 코드</th>
                            <th scope="col">과목 이름</th>
                            <th scope="col">교수명</th>
                            <th scope="col">강의실</th>
                            <th scope="col">수 강</th>
                        </tr>
                    </thead>
                    <tbody id="courseTableBody">
                        <% for (CourseVo course : courseList) { %>
                        <tr>
                            <td><%=course.getCourse_id()%></td>
                            <td><%=course.getCourse_name()%></td>
                            <td><%=course.getProfessor_name().getUser_name()%></td>
                            <td><%=course.getRoom_id()%></td>
                            <td>
                                <!-- 수강 버튼 -->
                                <button class="btn btn-green register-btn" onclick="moveToApply(this)">수강</button>
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>
        <br>
        <hr>
        <br>
        <div class="card shadow-sm">
            <h2 class="text-center mb-4">신청 목록</h2>
            <div class="card-body">
                <table class="table table-bordered table-hover text-center align-middle">
                    <thead class="table-success">
                        <tr>
                            <th scope="col">과목 코드</th>
                            <th scope="col">과목 이름</th>
                            <th scope="col">교수명</th>
                            <th scope="col">강의실</th>
                            <th scope="col">취 소</th>
                        </tr>
                    </thead>
                    <tbody id="applyTableBody">
                  		<% for (CourseVo course1 : courseList2) { %>
                        <tr>
                            <td><%=course1.getCourse_id()%></td>
                            <td><%=course1.getCourse_name()%></td>
                            <td><%=course1.getProfessor_name().getUser_name()%></td>
                            <td><%=course1.getRoom_id()%></td>
                            <td>
                                <!-- 취소 버튼 -->
                                <button class="btn btn-danger cancel-btn" onclick="moveToCourse(this)">취소</button>
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </main>

    <script type="text/javascript">
        // 수강 버튼 클릭 시 호출되는 함수
        function moveToApply(button) {
            const row = button.closest("tr"); // 버튼이 포함된 <tr> 요소 가져오기
            const courseId = row.cells[0].innerText; // <tr>의 첫 번째 셀 값(course_id) 가져오기
            
            document.getElementById("applyTableBody").appendChild(row); // 신청 목록으로 행 이동
            
            button.textContent = "취소"; // 버튼 텍스트를 "취소"로 변경
            button.className = "btn btn-danger cancel-btn"; // 버튼 스타일 변경
            button.setAttribute("onclick", "moveToCourse(this)"); // 클릭 이벤트를 취소 처리로 변경
            
            sendDataToController(courseId, "courseInsert.do"); // 수강 등록 요청
        }

        // 취소 버튼 클릭 시 호출되는 함수
        function moveToCourse(button) {
            const row = button.closest("tr"); // 버튼이 포함된 <tr> 요소 가져오기
            const courseId = row.cells[0].innerText; // <tr>의 첫 번째 셀 값(course_id) 가져오기
            
            document.getElementById("courseTableBody").appendChild(row); // 수강 목록으로 행 이동
            
            button.textContent = "수강"; // 버튼 텍스트를 "수강"으로 변경
            button.className = "btn btn-green register-btn"; // 버튼 스타일 변경
            button.setAttribute("onclick", "moveToApply(this)"); // 클릭 이벤트를 수강 처리로 변경
            
            sendDataToController(courseId, "courseDelete.do"); // 수강 취소 요청
        }

        // Controller와 AJAX로 데이터를 주고받는 함수
        function sendDataToController(courseId, action) {
	
        	var url = "<%=contextPath%>/classroom/" + action + "?classroomCenter=/view_classroom/courseSubmit.jsp";
        	
            $.ajax({
                url: url, // 요청 보낼 URL
                type: "POST", // HTTP 메서드
                data: {
                    courseId: courseId // 전송할 course_id
                },
                success: function (response) {
                	if (response === "Success") {
                        alert("성공!"); // 성공 알림
                    } else {
                        alert("실패!"); // 실패 알림
                    }
                },
                
            });
        }
    </script>
</body>
</html>
