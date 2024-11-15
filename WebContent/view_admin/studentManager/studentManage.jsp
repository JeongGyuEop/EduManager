<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    request.setCharacterEncoding("UTF-8");
    String contextPath = request.getContextPath();
    System.out.println(contextPath);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>학 생 등 록</title>
<style>
    table {
        border-collapse: collapse;
        width: 50%;
        margin: auto;
    }
    table, th, td {
        border: 1px solid black;
        padding: 8px;
        text-align: left;
    }
    th, td {
        padding: 10px;
    }
</style>
<script>
window.onload = function() {
    result();
};

function result(){
	
	var result = parseInt("${result}", 10);
    
    if (isNaN(result)) {
        return; // addResult가 없을 때는 함수를 종료함
    }

    /* if (result === -1) {
        alert("학과 이름 및 번호를 입력해 주세요.");
    } else */ if (result === 0) {
        alert("데이터베이스에 추가하는 데 실패했습니다. 다시 시도해 주세요.");
    } else if (result === 1) {
        alert("학생 정보가 성공적으로 추가되었습니다.");
    }/*  else if (result === -2) {
        alert("이미 존재하는 학과명입니다.");
    } */
}
</script>

<!-- 다음 주소 API 스크립트 추가 -->
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
    function sample4_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                var roadAddr = data.roadAddress; // 도로명 주소
                var extraRoadAddr = ''; // 참고 항목

                // 법정동명과 건물명 추가
                if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                    extraRoadAddr += data.bname;
                }
                if (data.buildingName !== '' && data.apartment === 'Y') {
                    extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                if (extraRoadAddr !== '') {
                    extraRoadAddr = ' (' + extraRoadAddr + ')';
                }

                // 최종 주소를 하나의 필드에 입력
                document.getElementById('address').value = roadAddr + extraRoadAddr;
            }
        }).open();
    }
</script>


</head>
<body>
    <h2>학 생 등 록</h2>
    
    <!-- 전체 학생 조회 버튼 추가 -->
    <form action="${pageContext.request.contextPath}/student/viewStudentList.do" method="get" style="text-align: center; margin-bottom: 20px;">
        <input type="submit" value="전체 학생 조회">
    </form>
        
        <form action="<%=contextPath %>/student/studentRegister.do" method="post">
        <table>
            <tr>
                <td><label for="student_id">학 생  I D:</label></td>
                <td><input type="text" id="student_id" name="student_id" required placeholder="정해진 양식의 학생ID 입력 필요"></td>
            </tr>
            <tr>
                <td><label for="user_id">사 용 자 I D:</label></td>
                <td><input type="text" id="user_id" name="user_id" required placeholder="정해진 양식의 사용자ID 입력 필요"></td>
            </tr>
            <tr>
                <td><label for="user_pw">비 밀 번 호:</label></td>
                <td><input type="password" id="user_pw" name="user_pw" required placeholder="비밀번호"></td>
            </tr>
            <tr>
                <td><label for="user_name">이 름:</label></td>
                <td><input type="text" id="user_name" name="user_name" required placeholder="이름"></td>
            </tr>
            <tr>
                <td><label for="birthDate">생 년 월 일:</label></td>
                <td><input type="date" id="birthDate" name="birthDate" required placeholder="생 년 월 일"></td>
            </tr>
            <tr>
                <td><label for="gender">성 별:</label></td>
                <td>
                    <select id="gender" name="gender" required>
                        <option value="남">남</option>
                        <option value="여">여</option>
                    </select>
                </td>
            </tr>
            <tr>
    		<td><label for="address">주 소:</label></td>
    			<td>
       			 	<div style="display: flex; align-items: center;">
            			<input type="text" id="address" name="address" placeholder="우편번호로 검색 후 상세주소까지 입력할 것" required style="flex: 1;">
            			<input type="button" onclick="sample4_execDaumPostcode()" value="우편번호 찾기" style="margin-left: 5px;">
        			</div>
    			</td>
			</tr>
            <tr>
                <td><label for="phone">전 화 번 호:</label></td>
                <td><input type="text" id="phone" name="phone" required placeholder="-없이 숫자만 입력"></td>
            </tr>
            <tr>
                <td><label for="email">이 메 일:</label></td>
                <td><input type="email" id="email" name="email" required placeholder="examle@naver.com"></td>
            </tr>
            <tr>
            	<input type="hidden" id="role" name="role" value="학생">
            
            </tr>
            <tr>
                <td><label for="majorcode">학 과 번 호:</label></td>
                <td><input type="text" id="majorcode" name="majorcode" required></td>
            </tr>
            <tr>
                <td><label for="grade">학 년:</label></td>
                <td><input type="number" id="grade" name="grade" min="1" max="4" required></td>
            </tr>
            <tr>
                <td><label for="admission_date">입 학 일:</label></td>
                <td><input type="date" id="admission_date" name="admission_date" required></td>
            </tr>
            <tr>
                <td><label for="status">상 태:</label></td>
                <td>
                    <select id="status" name="status" required>
                        <option value="재학" selected>재학</option>
                        <option value="휴학">휴학</option>
                        <option value="졸업">졸업</option>
                        <option value="자퇴">자퇴</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td colspan="2" style="text-align: center;">
                    <input type="submit" value="등록">
                </td>
            </tr>
        </table>
    </form>
</body>
</html>

