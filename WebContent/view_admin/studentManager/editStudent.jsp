<%@page import="Vo.StudentVo"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>학생 정보 수정</title>
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
<h2>학생 정보 수정</h2>

<%
    // request에서 학생 정보를 가져오기
    StudentVo student = (StudentVo) request.getAttribute("student");
%>

<form action="${pageContext.request.contextPath}/student/updateStudent.do" method="post">

    <table border="1" style="width: 50%; margin: auto;">
        <tr>
            <td><label for="student_id">학 생 I D:</label></td>
            <td><input type="text" id="student_id" name="student_id" value="<%= student.getStudent_id() %>" readonly></td>
        </tr>
        <tr>
            <td><label for="user_id">사 용 자 I D:</label></td>
            <td><input type="text" id="user_id" name="user_id" value="<%= student.getUser_id() %>" readonly></td>
        </tr>
        <tr>
            <td><label for="user_pw">비밀번호:</label></td>
            <td><input type="password" id="user_pw" name="user_pw" value="<%= student.getUser_pw() %>"></td>
        </tr>
        <tr>
            <td><label for="user_name">이름:</label></td>
            <td><input type="text" id="user_name" name="user_name" value="<%= student.getUser_name() %>"></td>
        </tr>
        <tr>
            <td><label for="birthDate">생년월일:</label></td>
            <td><input type="date" id="birthDate" name="birthDate" value="<%= student.getBirthDate() %>"></td>
        </tr>
        <tr>
    		<td><label for="gender">성별:</label></td>
    		<td>
        		<select id="gender" name="gender" required>
            		<option value="남" <%= "남".equals(student.getGender()) ? "selected" : "" %>>남</option>
            		<option value="여" <%= "여".equals(student.getGender()) ? "selected" : "" %>>여</option>
       			 </select>
    		</td>
		</tr>
        <tr>
            <td><label for="address">주소:</label></td>
            <td>
                <div style="display: flex; align-items: center;">
                    <input type="text" id="address" name="address" value="<%= student.getAddress() %>" placeholder="우편번호로 찾으신 후 상세주소도 이어서 기입해주세요" style="flex: 1;">
                    <input type="button" onclick="sample4_execDaumPostcode()" value="우편번호 찾기" style="margin-left: 5px;">
                </div>
            </td>
        </tr>
      	<!-- 		<td><label for="address">주소:</label></td> -->
         <!--   <td><input type="text" id="address" name="address" value="<%= student.getAddress() %>"></td> -->
        <tr>
            <td><label for="phone">전화번호:</label></td>
            <td><input type="text" id="phone" name="phone" value="<%= student.getPhone() %>"></td>
        </tr>
        <tr>
            <td><label for="email">이메일:</label></td>
            <td><input type="email" id="email" name="email" value="<%= student.getEmail() %>"></td>
        </tr>
        <tr>
    		<td><label for="role">역할:</label></td>
    			<td>
       				<select id="role" name="role" required>
            			<option value="학생" <%= "학생".equals(student.getRole()) ? "selected" : "" %>>학생</option>
            			<option value="교수" <%= "교수".equals(student.getRole()) ? "selected" : "" %>>교수</option>
            			<option value="관리자" <%= "관리자".equals(student.getRole()) ? "selected" : "" %>>관리자</option>
        			</select>
    			</td>
		</tr>
        
        <tr>
            <td><label for="majorcode">학 과 번 호:</label></td>
            <td><input type="text" id="majorcode" name="majorcode" value="<%= student.getMajorcode() %>" readonly></td>
        </tr>
        <tr>
            <td><label for="grade">학 년:</label></td>
            <td><input type="number" id="grade" name="grade"  min="1" max="4" value="<%= student.getGrade() %>" required></td>
        </tr>
        <tr>
            <td><label for="admission_date">입 학 일:</label></td>
            <td><input type="date" id="admission_date" name="admission_date" value="<%= student.getAdmission_date() %>"></td>
        </tr>
        <tr>
    		<td><label for="status">상 태:</label></td>
    			<td>
        			<select id="status" name="status" required>
            			<option value="재학" <%= "재학".equals(student.getStatus()) ? "selected" : "" %>>재학</option>
            			<option value="휴학" <%= "휴학".equals(student.getStatus()) ? "selected" : "" %>>휴학</option>
            			<option value="졸업" <%= "졸업".equals(student.getStatus()) ? "selected" : "" %>>졸업</option>
            			<option value="자퇴" <%= "자퇴".equals(student.getStatus()) ? "selected" : "" %>>자퇴</option>
        			</select>
    		</td>
		</tr>
        
        <tr>
            <td colspan="2" style="text-align: center;">
                <input type="submit" value="수정 완료">
            </td>
        </tr>
    </table>
</form>

</body>
</html>
