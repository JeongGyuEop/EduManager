<%@page import="java.net.URLDecoder"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Vo.CourseVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%

String contextPath = request.getContextPath();
ArrayList<CourseVo> courseList = (ArrayList<CourseVo>) request.getAttribute("courseList");

String profName = (String) session.getAttribute("name");

%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>교수가 강의하는 수강 목록</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>

<style>
    .btn-green {
        background-color: #4CAF50; /* 초록색 */
        color: white;
    }
    .btn-green:hover {
        background-color: #45a049; /* 호버 효과 */
    }
</style>

<%
	String message = (String) request.getAttribute("message");
	if (message != null) {
    	message = URLDecoder.decode(message, "UTF-8");
%>
        <script>
            alert('<%= message %>'); // 메시지를 알림으로 표시
        </script>
<%
    }
%>


<script>
function enableEdit(courseId) {

    // jQuery를 사용하여 강의명 셀을 input 필드로 변경
    const courseNameCell = $('#courseName_' + courseId);
    
    // 요소가 undefined가 아닌지 확인하고 텍스트 가져오기
    const courseName = courseNameCell.text() ? courseNameCell.text().trim() : "";
    courseNameCell.html('<input type="text" id="editInput_' + courseId + '" class="form-control" value="' + courseName + '">');
    
    // 강의실 셀을 선택 가능하도록 변경
    const classroomCell = $('#classroom_' + courseId);
    classroomCell.html('<select id="classroomSelect_' + courseId + '" class="form-select"></select>');

    // DB에서 강의실 목록을 가져와서 select 태그에 추가
    $.ajax({
        url: '<%=contextPath%>/classroom/getClassroomList.do', // 서버에서 강의실 목록을 제공하는 엔드포인트
        type: 'post',
        dataType: 'json',
        success: function(classrooms) {
            const classroomSelect = $('#classroomSelect_' + courseId);
            classroomSelect.empty(); // 기존 옵션 제거

            // 강의실 목록을 select 옵션으로 추가
            classrooms.forEach(function(room) {
                const option = $('<option></option>')
                    .val(room.room_id)
                    .text(room.room_id + ' (' + room.capacity + '명 / ' + room.equipment + ')');
                classroomSelect.append(option);
            });
        },
        error: function(xhr, status, error) {
            console.error("강의실 목록을 가져오는 데 실패했습니다:", error);
        }
    });
    
 	// 수정 버튼을 숨기고, 수정 완료 버튼을 표시
    $('#editBtn_' + courseId).hide();
    $('#saveBtn_' + courseId).show();
    
}

function saveEdit(courseId) {

    // 수정된 강의명과 선택된 강의실 가져오기
    const newCourseName = $('#editInput_' + courseId).val();
    const selectedClassroom = $('#classroomSelect_' + courseId).val();

    // 서버로 저장 요청을 보내려면 Ajax 호출 추가
    $.ajax({
        url: '<%=contextPath%>/classroom/updateCourse.do',
        type: 'POST',
        data: {
            courseId: courseId,
            courseName: newCourseName,
            classroomId: selectedClassroom
        },
        success: function(response) {
        	if (response.status === "success") {
        		
	            // 성공적으로 저장된 후, UI 업데이트
	            $('#courseName_' + courseId).text(newCourseName);
	            $('#classroom_' + courseId).text($('#classroomSelect_' + courseId + ' option:selected').text());
	
	            // 버튼 상태를 원래대로 복구
	            $('#editBtn_' + courseId).show();
	            $('#saveBtn_' + courseId).hide();
	
	            alert("강의 수정 완료!");
	            
        	} else {
                alert("강의 수정에 실패했습니다.");
            }
        },
        error: function(xhr, status, error) {
            alert("강의 수정 처리중 오류!");
        }
    });
}

// 삭제 버튼 클릭 이벤트로 폼을 제출
function submitDeleteForm(courseId) {
    if (confirm("삭제하시겠습니까?")) {
        document.getElementById("deleteForm_" + courseId).submit();
    }
}
</script>


</head>
<body class="bg-light">
	<main class="container my-5">
		<h2 class="text-center mb-4"><%=profName %> 교수님 수강 강의 목록</h2>
		<div class="card shadow-sm">
			<div class="card-body">
				<table class="table table-bordered table-hover text-center align-middle">
					<thead class="table-success">
						<tr>
							<th scope="col">과목 ID</th>
							<th scope="col">과목 이름</th>
							<th scope="col">강의실(수용인원 / 장비)</th>
							<th scope="col">수정</th>
							<th scope="col">삭제</th>
						</tr>
					</thead>
					<tbody>
						<%
						for (CourseVo course : courseList) {
						%>
						<tr id="row_<%= course.getCourse_id() %>">
							<td><%= course.getCourse_id() %></td>
                            <td id="courseName_<%= course.getCourse_id() %>"><%= course.getCourse_name() %></td>
							<td id="classroom_<%= course.getCourse_id() %>">
								<%= course.getClassroom().getRoom_id() %> (<%= course.getClassroom().getCapacity() %> / <%= course.getClassroom().getEquipment() %>)
							</td>
							<td>
								<button id="editBtn_<%= course.getCourse_id() %>" class="btn btn-green" onclick="enableEdit('<%= course.getCourse_id() %>')">수정</button>
								<button id="saveBtn_<%= course.getCourse_id() %>" class="btn btn-green" style="display:none;" onclick="saveEdit('<%= course.getCourse_id() %>')">수정 완료</button>
							</td>
							<td>
								<form id="deleteForm_<%= course.getCourse_id() %>" action="<%=contextPath%>/classroom/deleteCourse.do" method="POST">
								        <input type="hidden" name="id" value="<%= course.getCourse_id() %>">
								        <input type="hidden" name="classroomCenter" value="/view_classroom/courseSearch.jsp">
								        <button type="button" class="btn btn-green" onclick="submitDeleteForm('<%= course.getCourse_id() %>')">삭제</button>
							    </form>
							</td>
						</tr>
						<%
						}
						%>
					</tbody>
				</table>
			</div>
		</div>
		
		
	</main>
</body>
</html>