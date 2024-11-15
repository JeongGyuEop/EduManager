<%@page import="Vo.StudentVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    request.setCharacterEncoding("utf-8");
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이페이지</title>

<style>
    .disabled-input {
        background-color: #f0f0f0;
        pointer-events: none;
    }
</style>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script>
    function validateForm() {
        var form = document.forms["myForm"];
        var currentPassword = form["current_pw"].value;
        var newPassword = form["user_pw"].value;
        var confirmPassword = form["confirm_pw"].value;
        var email = form["email"].value;

        if (newPassword == null || newPassword === "") {
            alert("비밀번호를 입력해주세요.");
            return false;
        }

        if (newPassword === currentPassword) {
            alert("현재 비밀번호와 새 비밀번호는 같을 수 없습니다.");
            return false;
        }

        if (newPassword !== confirmPassword) {
            alert("새 비밀번호와 확인 비밀번호가 일치하지 않습니다.");
            return false;
        }

        var emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (email == null || email === "" || !emailPattern.test(email)) {
            alert("올바른 이메일 형식을 입력해주세요.");
            return false;
        }
        return true;
    }

    function updateInfo() {
        if (!validateForm()) return false;

        $.ajax({
            type: "POST",
            url: "<%=contextPath%>/student/updateMyInfo.do",
            data: $("#myForm").serialize(),
            success: function(response) {
                if (response.trim() === "Success") {
                    alert("수정 완료되었습니다.");
                    disableInputs();
                } else {
                    alert("수정 실패: 입력 내용을 확인해주세요.");
                }
            },
            error: function() {
                alert("서버 요청 중 오류가 발생했습니다.");
            }
        });
        return false; // 폼 제출 막기
    }

    function disableInputs() {
        var inputs = document.querySelectorAll("input[type='text'], input[type='password'], input[type='email']");
        inputs.forEach(function(input) {
            input.classList.add("disabled-input");
            input.setAttribute("readonly", true);
        });
    }
</script>

<!-- 다음 주소 API 스크립트 추가 -->
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
    function sample4_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                var roadAddr = data.roadAddress;
                var extraRoadAddr = '';

                if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                    extraRoadAddr += data.bname;
                }
                if (data.buildingName !== '' && data.apartment === 'Y') {
                    extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                if (extraRoadAddr !== '') {
                    extraRoadAddr = ' (' + extraRoadAddr + ')';
                }

                document.getElementById('address').value = roadAddr + extraRoadAddr;
            }
        }).open();
    }
</script>

</head>
<body>
    <h2>학생 정보 수정</h2>
    <%
        StudentVo member = (StudentVo) request.getAttribute("member");
        if (member != null) { 
    %>
        <form id="myForm" name="myForm" onsubmit="return updateInfo();">
            <table>
                <tr><td><label>아 이 디</label></td>
                    <td><input type="text" name="user_id" value="<%= member.getUser_id() %>" readonly></td>
                </tr>
                <tr><td><label>이 름</label></td>
                    <td><input type="text" name="user_name" value="<%= member.getUser_name() %>" readonly></td>
                </tr>
                <tr><td><label>주 소</label></td>
                    <td>
                        <input type="text" name="address" id="address" value="<%= member.getAddress() %>" placeholder="우편번호로 검색 후 상세주소까지 입력">
                        <button type="button" onclick="sample4_execDaumPostcode()">주소 찾기</button>
                    </td>
                </tr>
                <tr><td><label>전 화 번 호</label></td>
                    <td><input type="text" name="phone" value="<%= member.getPhone() %>" placeholder="전화번호"></td>
                </tr>
                <tr><td><label>이 메 일</label></td>
                    <td><input type="text" name="email" value="<%= member.getEmail() %>" placeholder="이메일"></td>
                </tr>
                <tr><td><label>현 재 비 밀 번 호</label></td>
                    <td><input type="text" name="current_pw" value="<%= member.getUser_pw() %>" readonly></td>
                </tr>
                <tr><td><label>새 비 밀 번 호</label></td>
                    <td><input type="password" name="user_pw" placeholder="새 비밀번호"></td>
                </tr>
                <tr><td><label>새 비 밀 번 호 확 인</label></td>
                    <td><input type="password" name="confirm_pw" required placeholder="새 비밀번호 확인"></td>
                </tr>
                <tr>
                    <td colspan="2" style="text-align: center;">
                        <input type="submit" value="수정">
                    </td>
                </tr>
            </table>
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
