<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% 
    String message = (String) request.getAttribute("message");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	
	
	<script type="text/javascript">
	window.onload() {
		function writecheck();
	}// 이 부분에서 메세지 팝업
	
	function writecheck(${message}){
		if(message != null ||){
			alert(message);
		}
	}
	</script>
</body>
</html>