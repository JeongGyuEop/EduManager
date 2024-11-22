<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="Vo.ClassroomVo"%>

<%
request.setCharacterEncoding("UTF-8");
String contextPath = request.getContextPath();

String majorName = (String) request.getAttribute("majorname");
ArrayList<ClassroomVo> rooms = (ArrayList<ClassroomVo>) request.getAttribute("rooms");
if (rooms == null) {
	rooms = new ArrayList<>(); // rooms가 null일 경우 빈 ArrayList로 초기화
}
String userId = (String) session.getAttribute("professor_id");
%>

<%
    String message = request.getParameter("message");
    if (message != null) {
%>
    <script>
        alert('<%= message %>'); // 메시지를 알림으로 표시
    </script>
<%
    }
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>강의 등록</title>
<link href="<%=contextPath %>/css/classroom_style.css" rel="stylesheet" />
</head>
<body>
			<main>
				<div class="container-fluid px-4">
					<h1 class="mt-4">수강 등록</h1>
					<ol class="breadcrumb mb-4">
						<li class="breadcrumb-item active">교수님의 강의를 등록해 주세요!</li>
					</ol>
					<div class="form-container">
						<h1>강의 등록</h1>
						<form action="<%=contextPath%>/classroom/course_register.do" method="post">
							<div class="form-group">
								<label for="course_name">강의명:</label> <input type="text"
									name="course_name" id="course_name" required>
							</div>

							<div class="form-group">
								<label for="majorname">학과:</label> <input type="text"
									name="majorname" id="majorname" value="<%=majorName%>" readonly>
							</div>

							<div class="form-group">
								<label for="room_id">강의실:</label> <select name="room_id"
									id="room_id" required>
									<option value="">강의실</option>
									<%
									for (ClassroomVo room : rooms) {
										String roomId = room.getRoom_id();
										int capacity = room.getCapacity();
										String equipment = room.getEquipment();
									%>
									<option value="<%=roomId%>"><%=roomId%> (<%=capacity%>명,
										<%=equipment%>)
									</option>
									<%
									}
									%>
								</select>
							</div>

							<div class="form-group">
								<label for="professor_id">교수 ID:</label> <input type="text"
									name="professor_id" id="professor_id" value="<%=userId%>"
									readonly>
							</div>

							<button type="submit">강의 등록</button>
						</form>
					</div>
				</div>
			</main>
</body>
</html>