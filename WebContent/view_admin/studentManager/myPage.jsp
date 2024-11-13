<%@page import="Vo.StudentVo"%>
<%@page import="Vo.MemberVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이페이지</title>

<script>
    function validateForm() {
        var form = document.forms["myForm"];
        var currentPassword = form["current_pw"].value; // 현재 비밀번호 필드
        var newPassword = form["user_pw"].value; // 새 비밀번호 필드
        var email = form["email"].value; // 이메일 필드
        
        
        // 새 비밀번호가 비어 있는지 확인
        if (newPassword == null || newPassword === "") {
            alert("비밀번호를 입력해주세요.");
            return false; // 폼 전송을 막음
        }

        // 현재 비밀번호와 새 비밀번호가 같은지 확인
        if (newPassword === currentPassword) {
            alert("현재 비밀번호와 새 비밀번호는 같을 수 없습니다.");
            return false; // 폼 전송을 막음
        }
        
     // 이메일 형식 검증 (기본적인 형식 검사 정규식)
        var emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (email == null || email === "" || !emailPattern.test(email)) {
            alert("올바른 이메일 형식을 입력해주세요.");
            return false; // 폼 전송을 막음
        }


        return true; // 모든 검증을 통과한 경우 폼 제출 허용
    }
</script>
</head>
<body>
    <h2>학생 정보 수정</h2>
    <%
        // Controller에서 전달된 사용자 정보 가져오기
        StudentVo member = (StudentVo) request.getAttribute("member");
        if (member != null) { 
    %>
        <form name="myForm" action="/student/updateMyInfo.do" method="post" onsubmit="return validateForm();">
            <label>아이디</label>
            <input type="text" name="user_id" value="<%= member.getUser_id() %>" readonly>
            <br>
            <label>이름</label>
            <input type="text" name="user_name" value="<%= member.getUser_name() %>" readonly>
            <br>
            <label>주소</label>
            <input type="text" name="address" value="<%= member.getAddress() %>">
            <br>
            <label>전화번호</label>
            <input type="text" name="phone" value="<%= member.getPhone() %>">
            <br>
            <label>이메일</label>
            <input type="text" name="email" value="<%= member.getEmail() %>">
            <br>
            <label>현재 비밀번호</label>
            <input type="text" name="current_pw" value="<%= member.getUser_pw() %>" readonly>
            <br>
            <label>새 비밀번호</label>
            <input type="password" name="user_pw">
            <br>
            <input type="submit" value="수정">
        </form>
    <%
        } else { 
    %>
        <p>회원 정보를 불러올 수 없습니다.</p>
    <%
        }
    %>
</body>
</html>
