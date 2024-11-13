<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

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
</head>
<body>

</body>
</html>