<%@page import="ProfessorVO.ProfessorVO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>교수 조회</title>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  
<%
request.setCharacterEncoding("UTF-8");
String contextPath = request.getContextPath();
List<ProfessorVO> professorList = (List<ProfessorVO>) request.getAttribute("professor");
%>

<style>
    * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
    }
    body {
        font-family: Arial, sans-serif;
        background-color: #f4f4f9;
        color: #333;
        height: 100%;
        display: flex;
        flex-direction: column;
        align-items: center;
        padding: 20px;
    }
    h2 {
        text-align: center;
        color: #5a5a5a;
        margin-top: 20px;
    }
    .container {
        width: 100%;
        max-width: 1200px;
        margin: 20px auto;
    }
    .form-container, .table-container {
        margin-top: 20px;
        width: 100%;
    }
    .form-container form {
        display: flex;
        justify-content: center;
        gap: 15px;
        margin-bottom: 20px;
        flex-wrap: wrap;
    }
    .form-container input[type="text"], .form-container input[type="submit"] {
        padding: 8px;
        margin: 5px;
        border: 1px solid #ccc;
        border-radius: 4px;
    }
    .form-container input[type="text"] {
        width: 250px;
    }
    .form-container input[type="submit"] {
        background-color: #4CAF50;
        color: white;
        cursor: pointer;
    }
    .form-container input[type="submit"]:hover {
        background-color: #45a049;
    }
    .message {
        text-align: center;
        color: red;
        margin-top: 10px;
    }
    .table-container {
        width: 100%;
        margin-top: 20px;
    }
    table {
        width: 100%;
        border-collapse: collapse;
    }
    th, td {
        padding: 12px;
        text-align: center;
        border: 1px solid #ddd;
    }
    th {
        background-color: #f2f2f2;
        color: #555;
    }
    td {
        background-color: #fff;
    }
    tr:nth-child(even) td {
        background-color: #f7f7f7;
    }
    .action-btn {
        color: #007BFF;
        text-decoration: none;
        padding: 5px;
        border: 1px solid #007BFF;
        border-radius: 4px;
    }
    .action-btn:hover {
        background-color: #007BFF;
        color: white;
    }
    .disabled {
        background-color: #f2f2f2;
        color: #aaa;
        pointer-events: none;
    }
    .enabled {
        background-color: white;
        color: black;
        pointer-events: auto;
    }

    .form-container input[type="submit"]:focus,
    .table-container .action-btn:focus {
        outline: none;
    }
</style>


</head>
<body>

<div class="container">
    <h2>교수 조회</h2>


    <div class="form-container">
        <form action="<%=contextPath%>/prosess/professorquiry.do" class="form" method="get">
            <div>
                <label for="professor_id">사번</label>
                <input type="text" id="professor_id" name="professor_id" maxlength="50" required="required">
            </div>
            <div>
                <label for="majorcode">학과번호</label>
                <input type="text" id="majorcode" name="majorcode" maxlength="50" required="required">
            </div>
            <div>
                <input type="submit"  value="조회">
            </div>
        </form>

        <form action="<%=contextPath%>/prosess/professorquiry.do" class="form" method="get">
            <div>
                <input type="submit"  value="전체조회">
            </div>
        </form>
    </div>

    <div class="table-container">
        <table>
            <thead>
                <tr>
                    <th>사번</th>
                    <th>이름</th>
                    <th>학과번호</th>
                    <th>생년월일</th>
                    <th>성별</th>
                    <th>주소</th>
                    <th>전화번호</th>
                    <th>이메일</th>
                    <th>고용일</th>
                    <th>정보수정</th>
                    <th>삭제</th>
                </tr>
            </thead>
           <tbody>
           
<% if (professorList != null && !professorList.isEmpty()) { %>
    <% for (ProfessorVO vo : professorList) { %>
			<tr id="professor-row-<%= vo.getProfessor_id() %>">
			    <td><input type="text" name="professor_id" class="professor-id" value="<%= vo.getProfessor_id() %>" readonly /></td>
			    <td><input type="text" name="user_name" value="<%= vo.getUser_name() %>" disabled /></td>
			    <td><input type="text" name="majorcode" value="<%= vo.getMajorcode() %>" disabled /></td>
			    <td><input type="date" name="birthDate" value="<%= vo.getBrithDate() %>" disabled /></td>
			    <td>
			        <select name="gender" disabled>
			            <option value="M" <%= vo.getGender().equals("M") ? "selected" : "" %>>Male</option>
			            <option value="F" <%= vo.getGender().equals("F") ? "selected" : "" %>>Female</option>
			        </select>
			    </td>
			    <td><input type="text" name="address" value="<%= vo.getAddress() %>" disabled /></td>
			    <td><input type="text" name="phone" value="<%= vo.getPhone() %>" disabled /></td>
			    <td><input type="email" name="email" value="<%= vo.getEmail() %>" disabled /></td>
			    <td><input type="date" name="employDate" value="<%= vo.getEmployDate() %>" disabled /></td>
			    <td><a href="#" class="edit-btn">정보수정</a></td>
			   <td><a href="#" onclick="deleteProfessor('<%= vo.getProfessor_id() %>'); return false;">삭제</a></td>
			   <!--  <td><a href="#" onclick="return ProDelete();">삭제</a></td> -->
			</tr>

    <% } %>
<% } else { %>
    <tr>
        <td colspan="12">조회된 교수가 없습니다.</td>
    </tr>
<% } %>
</tbody>
        </table>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>

// 교수 수정
    $(document).ready(function() {
        // "정보수정" 버튼을 클릭했을 때 해당 행의 입력 필드를 활성화
        $(".edit-btn").on("click", function(event) {
        	event.preventDefault();
            var row = $(this).closest('tr'); // 해당 행을 찾기
            row.find('input, select').prop('disabled', false); // 해당 행의 모든 input, select 활성화
            $(this).text("저장"); // "정보수정"을 "저장"으로 변경

            // "저장"을 클릭했을 때 수정된 내용을 서버로 보낼 수 있도록 설정
            $(this).off("click").on("click", function() {

            	event.preventDefault();
                // 해당 행의 입력값들을 가져옴
                var professorId = row.find('input[name="professor_id"]').val();
                var userName = row.find('input[name="user_name"]').val();
                var majorCode = row.find('input[name="majorcode"]').val();
                var birthDate = row.find('input[name="birthDate"]').val();
                var gender = row.find('select[name="gender"]').val();
                var address = row.find('input[name="address"]').val();
                var phone = row.find('input[name="phone"]').val();
                var email = row.find('input[name="email"]').val();
                var employDate = row.find('input[name="employDate"]').val();

                // AJAX 요청을 보냄
                $.ajax({
                    url: '<%=contextPath%>/prosess/updateProfessor.do', // 서블릿의 URL 매핑
                    type: 'POST',
                    data: {
                        professor_id: professorId,
                        user_name: userName,
                        majorcode: majorCode,
                        birthDate: birthDate,
                        gender: gender,
                        address: address,
                        phone: phone,
                        email: email,
                        employDate: employDate
                    },
                    success: function(response) {
                    	
                    	if (response==="수정 완료") {
                    		alert("수정이 완료되었습니다.");
                            // 수정이 완료되면 버튼을 다시 "정보수정"으로 바꿈
                            $(this).text("정보수정");
                            row.find('input, select').prop('disabled', true); // 입력 필드를 다시 비활성화
						}else{
							alert("수정 실패");
						}                    
                    },
                    error: function(xhr, status, error) {
                        alert("수정 실패: " + error);
                    }
                });
            });
        });
    });
    
    //교수삭제
   function deleteProfessor(professorId) {
    if (confirm("정말로 삭제하시겠습니까?")) { // 사용자에게 삭제 확인
       
    	$.ajax({
            url: '<%=contextPath%>/prosess/deleteProfessor.do', // 요청 URL
            method: 'POST',                   // 요청 방식 (삭제 시 POST 요청)
            data: { professor_id: professorId },         // 서버에 보낼 데이터
            success: function(response) {
            	
                if (response.success) {
                    alert("삭제되었습니다.");
                    location.reload(); // 페이지 새로고침
                } else {
                    alert("삭제에 실패했습니다.");
                }
            },
            error: function() {
                alert("오류가 발생했습니다. 다시 시도해 주세요.");
            }
        });
    }
}

</script>



</body>
</html>