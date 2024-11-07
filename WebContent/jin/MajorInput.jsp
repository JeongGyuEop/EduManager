<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%
	String contextPath = request.getContextPath();
	System.out.println(contextPath);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>New Major Input Page</title>
<script type="text/javascript">
	window.onload = function() {
		// cotroller에서 받는 값
		var addResult = parseInt("${addResult}", 10);

		if (addResult === -1) {
			alert("학과 이름 및 번호를 입력해 주세요.");
		} else if (addResult === 0) {
			alert("데이터베이스에 추가하는 데 실패했습니다. 다시 시도해 주세요.");
		} else if (addResult === 1) {
			alert("학과가 성공적으로 추가되었습니다.");
		}
	};
</script>
</head>
<body>
	<form action="<%=contextPath %>/DMI/MajorInput.do" method="get"
		onsubmit="return validateForm()">
		<label for="MajorNameInput">신규 학과명:</label> <input type="text"
			id="MajorNameInput" name="MajorNameInput" placeholder="**학과"
			required="required"> <label for="MajorTelInput">학과
			사무실 전화번호:</label> <input type="tel" id="MajorTelInput" name="MajorTelInput"
			placeholder="02-123-1234">
			
			
		<button type="submit">제출</button>
	</form>
	<!-- <script>
		// 전화번호 유효성 검사
		function validateForm() {
			const telNumber = document.getElementById("MajorTelInput").value;
			const telRegex = /^\d{2,3}-\d{3,4}-\d{4}$/;

			if (!telRegex.test(telNumber)) {
				alert("유효한 전화번호 형식을 입력해 주세요. 예: 02-123-1234");
				return false;
			}
			return true;
		}
	</script> -->

</body>
</html>
