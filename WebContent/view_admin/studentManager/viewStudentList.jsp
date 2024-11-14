<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Vo.StudentVo" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>전체 학생 목록</title>
      	<script>
    		function confirmDelete() {
        		// confirm 창을 띄워서 사용자가 '네'를 누르면 true, '아니오'를 누르면 false 반환
        		return confirm("정말로 삭제하시겠습니까?");
    									}
    		// 삭제 결과 메시지 표시
            window.onload = function() {
                const urlParams = new URLSearchParams(window.location.search);
                if (urlParams.get('message')) {
                    alert(urlParams.get('message'));
                }
            };
    	</script>
</head>
<body>
<h2>전체 학생 목록</h2>

<form action="${pageContext.request.contextPath}/student/viewStudentList.do" method="get" style="text-align: center; margin-bottom: 20px;">
    <input type="submit" value="전체 조회">
</form>



<table border="1" style="width: 80%; margin: auto; text-align: center;">
    <tr>
        <th>학번</th>
        <th>사용자 ID</th>
        <th>이름</th>
        <th>전공 코드</th>
        <th>학년</th>
        <th>입학일</th>
        <th>상태</th>
        <th>상세보기</th>
        <th>수정</th>
        <th>삭제</th>
    </tr>

    <%
        List<StudentVo> students = (List<StudentVo>) request.getAttribute("students");
        if (students != null && !students.isEmpty()) {
            for (StudentVo student : students) {
    %>
                <tr>
                    <td><%= student.getStudent_id() %></td>
                    <td><%= student.getUser_id() %></td>
                    <td><%= student.getUser_name() %></td>
                    <td><%= student.getMajorcode() %></td>
                    <td><%= student.getGrade() %></td>
                    <td><%= student.getAdmission_date() %></td>
                    <td><%= student.getStatus() %></td>
                    <td><a href="${pageContext.request.contextPath}/student/viewStudent.do?user_id=<%= student.getUser_id() %>">상세보기</a></td>
					<td><a href="${pageContext.request.contextPath}/student/editStudent.do?user_id=<%= student.getUser_id() %>">수정</a></td>
					<td><a href="${pageContext.request.contextPath}/student/deleteStudent.do?student_id=<%= student.getStudent_id() %>" onclick="return confirmDelete();">삭제</a></td>
                    
                </tr>
    <%
            }
        } else {
    %>
            <tr>
                <td colspan="10">학생 정보가 없습니다.</td>
            </tr>
    <%
        }
    %>
</table>
</body>
</html>
