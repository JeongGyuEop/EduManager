<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	request.setCharacterEncoding("UTF-8");
	String contextPath = request.getContextPath();
	
	String course_id = (String)request.getParameter("courseId");
	
%>
    
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>과제 목록</title>
<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script> <!-- jQuery 추가 -->
<script>
	$(document).ready(function() {
        $.ajax({
            url: '<%=contextPath%>/assign/assignmentSearch.do', 
            method: 'GET',
            data: { courseId: '<%= course_id %>' }, 
            dataType: 'json',
            success: function(assignmentList) {
                // 과제 목록을 동적으로 테이블에 추가
                var tbody = $('table tbody');
                tbody.empty(); // 테이블 본문 초기화

                if (assignmentList && assignmentList.length > 0) {
                    // 과제가 있을 경우 각 과제를 테이블에 추가
                    assignmentList.forEach(function(assignment) {
                        var row = '<tr>' +
                                    '<td>' + assignment.title + '</td>' +
                                    '<td>' + assignment.dueDate + '</td>' +
                                    '<td>' + assignment.description + '</td>' +
                                    '<td>' +
                                        '<a href="editAssignment.jsp?assignmentId=' + assignment.assignmentId + '" class="btn btn-sm btn-primary">수정</a> ' +
                                        '<a href="deleteAssignment.do?assignmentId=' + assignment.assignmentId + '" class="btn btn-sm btn-danger">삭제</a> ' +
                                        '<a href="viewSubmissions.jsp?assignmentId=' + assignment.assignmentId + '" class="btn btn-sm btn-success">제출물 보기</a>' +
                                    '</td>' +
                                  '</tr>';
                        tbody.append(row);
                    });
                } else {
                    // 과제가 없을 경우 메시지 추가
                    var emptyRow = '<tr><td colspan="4">등록된 과제가 없습니다.</td></tr>';
                    tbody.append(emptyRow);
                }
            },
            error: function(error) {
                console.error('Error fetching course list:', error);
            }
        });
	});
</script>
</head>
<body>
<div class="container mt-4">
    <!-- 과제 목록 -->
    <h2 class="text-center mb-4">과제 목록</h2>
    <table class="table table-bordered text-center">
        <thead class="table-success">
            <tr>
                <th>과제 제목</th>
                <th>마감일</th>
                <th>설명</th>
                <th>관리</th>
            </tr>
        </thead>
        <tbody>
            
        </tbody>
    </table>

    <!-- 과제 등록 -->
    <h2 class="text-center my-4">새 과제 등록</h2>
    <form method="post" action="createAssignment.do" class="p-4 border rounded">
        <div class="mb-3">
            <label for="title" class="form-label">제목:</label>
            <input type="text" name="title" id="title" class="form-control" required>
        </div>
        <div class="mb-3">
            <label for="description" class="form-label">설명:</label>
            <textarea name="description" id="description" class="form-control" rows="3" required></textarea>
        </div>
        <div class="mb-3">
            <label for="dueDate" class="form-label">마감일:</label>
            <input type="date" name="dueDate" id="dueDate" class="form-control" required>
        </div>
        <input type="hidden" name="courseId" value="${selectedCourseId}">
        <div class="text-center">
            <button type="submit" class="btn btn-primary">등록</button>
        </div>
    </form>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
