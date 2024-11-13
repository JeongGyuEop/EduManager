<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Vo.MemberVo" %>
<html>
<head>
    <title>학생 상세 정보</title>
</head>
<body>
<h2>학생 상세 정보</h2>

<%
MemberVo student = (MemberVo)request.getAttribute("student");
    if (student == null) {
%>
        <p style="color: red;">학생 정보를 불러오는 데 문제가 발생했습니다.</p>
         <a href="${pageContext.request.contextPath}/member/viewStudentList.do">목록으로 돌아가기</a>
<%
    } else {
%>
    <table border="1"  style="width: 60%; margin: auto;">
        <tr><td>학번</td><td><%= student.getStudent_id() %></td></tr>
        <tr><td>사용자 ID</td><td><%= student.getUser_id() %></td></tr>
        <tr><td>이름</td><td><%= student.getUser_name() %></td></tr>
        <tr><td>생년월일</td><td><%= student.getBirthDate() %></td></tr>
        <tr><td>성별</td><td><%= student.getGender() %></td></tr>
        <tr><td>주소</td><td><%= student.getAddress() %></td></tr>
        <tr><td>전화번호</td><td><%= student.getPhone() %></td></tr>
        <tr><td>이메일</td><td><%= student.getEmail() %></td></tr>
        <tr><td>역할</td><td><%= student.getRole() %></td></tr>
        <tr><td>전공 코드</td><td><%= student.getMajorcode() %></td></tr>
        <tr><td>학년</td><td><%= student.getGrade() %></td></tr>
        <tr><td>입학일</td><td><%= student.getAdmission_date() %></td></tr>
        <tr><td>상태</td><td><%= student.getStatus() %></td></tr>
    </table>
    
<%
    }
%>

</body>
</html>
