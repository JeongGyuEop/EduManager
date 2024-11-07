<%@page import="prosessVo.ProsessVo"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>교수조회</title>

<%
request.setCharacterEncoding("UTF-8");
String contextPath = request.getContextPath();

List<ProsessVo> prosessList = (List<ProsessVo>)request.getAttribute("v");
%>



</head>
<body>

<h2> 교수 조회 </h2>


<form style="align:center">

	<table align="center" width="300">
		<tr>
			<td align ="center"  bgcolor="lightgray">사  번</td>
			<td><input type="text" name="profId" maxlength="50"></td>
		</tr>
		<tr>
			<td align ="center" bgcolor="lightgray">학과번호</td>
			<td><input type="text" name="p_stunum" maxlength="50"></td>
		</tr> 
	
	    <tr align="center">
	    	<td ></td>
	    	<td align="center">
	    	<input style="width:65px; height:24px;"  type="submit" value="조회">
	    	<input type="submit" value="전체조회">
	    	</td>
	    </tr>
	</table>    
	   	
	<br>
	
	<table align="center"  border="1">
		
			<tr align="center" >
				<th align="center">사번</th>
				<th align="center">이름</th>
				<th align="center">생년월일</th>
				<th align="center">성별</th>
				<th align="center">주소</th>
				<th align="center">전화번호</th>
				<th align="center">학과번호</th>
				<th align="center">이메일</th>
				<th align="center">고용일</th>
			</tr>
		
<%
		if(prosessList == null || prosessList.isEmpty()){
		%>
			<tr align="center">
				<td colspan ="9">조회된 교수가 없습니다.</td>
			</tr>
<%
} else{
	
		for(ProsessVo vo : prosessList){
%>			
	
			<tr align="center">
				<td align="center"><%=vo.getP_profId()%></td>
				<td align="center"><%=vo.getP_Name()%></td>
				<td align="center"><%=vo.getP_brithDate()%></td>
				<td align="center"><%=vo.getP_Gender()%></td>
				<td align="center"><%=vo.getP_Address()%></td>
				<td align="center"><%=vo.getP_phone()%></td>
				<td align="center"><%=vo.getDeptID()%></td>
				<td align="center"><%=vo.getP_email()%></td>
				<td align="center"><%=vo.getP_employDate()%></td>
			</tr>	
<%			
			}
		}
%>
		<tr align="right">
			<td colspan="8" align="right"><input type="reset" value="확인"></td>
		</tr>
	</table>
	
		
			

</form>

</body>
</html>