<%@page import="org.apache.tomcat.util.log.SystemLogHandler"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
<%
	request.setCharacterEncoding("UTF-8");
	String contextPath = request.getContextPath();
	
	String message = null;
	
	String userId = (String) session.getAttribute("professor_id");
	String majorCode = (String) session.getAttribute("majorcode");
	
	System.out.println(majorCode);

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
    <h1>강의 등록</h1>

    <% if (message != null) { %>
        <p style="color: red;"><%= message %></p>
    <% } %>

    <form action="<%= request.getContextPath() %>/course/register" method="post">
        <label for="course_name">강의명:</label>
        <input type="text" name="course_name" id="course_name" required>

        <label for="majorcode">학과:</label>
        <select name="majorcode" id="majorcode" required>
            <option value="">학과 선택</option>
            <c:forEach var="major" items="${majors}">
                <option value="${major.majorcode}">${major.majorname}</option>
            </c:forEach>
        </select>

        <label for="room_id">강의실:</label>
        <input type="text" name="room_id" id="room_id" required>

        <label for="professor_id">교수 ID:</label>
        <input type="text" name="professor_id" id="professor_id" value="<%=userId %>" readonly>

        <button type="submit">강의 등록</button>
    </form>
</body>
</html>