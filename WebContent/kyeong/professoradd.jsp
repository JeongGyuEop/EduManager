<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>교수등록</title>

<%
request.setCharacterEncoding("UTF-8");
String contextPath = request.getContextPath();
%>


</head>
<body>

<h2> 교수등록 </h2>

<form name="" method="post" action="">

	
	<table border="1">
	
	<tr>
		<th bgcolor="#D3D3D3">사번</th>
		<td><input type="text" name="profId"></td>		
	</tr>
	<tr>
		<th bgcolor="#D3D3D3">이름</th>
		<td><input type="text" name="p_Name"></td>		
	</tr>
	<tr>
		<th bgcolor="#D3D3D3">생년월일</th>
		<td><input style="width:165px;" type="date" name="Age"></td>		
	</tr>
	<tr>
		<th bgcolor="#D3D3D3">성별</th>
		<td>
		<input type="radio" name="Gender" value="male">남성
		<input type="radio" name="Gender" value="female">여성
		</td>		
	</tr>
	<tr>
		<th bgcolor="#D3D3D3">주소</th>
		<td><input type="text" name="p_Address"></td>		
	</tr>
	<tr>
		<th bgcolor="#D3D3D3">전화번호</th>
		<td><input type="text" name="p_phone"></td>		
	</tr>
	<tr>
		<th bgcolor="#D3D3D3">학과번호</th>
		<td><input type="text" name="p_stunum"></td>		
	</tr>
	<tr>
		<th bgcolor="#D3D3D3">이메일</th>
		<td><input type="text" name="p_email"></td>		
	</tr>
	<tr>
		<th bgcolor="#D3D3D3">고용일</th>
		<td><input style="width:165px;" type="date" name="p_goyong"></td>		
	</tr>
	
	</table>
	
	<input type="submit" value="등록" onclick="">
	<input type="submit" value="수정" onclick="">
	
</form>

</body>
</html>