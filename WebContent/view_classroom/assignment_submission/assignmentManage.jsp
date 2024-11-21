<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
    request.setCharacterEncoding("UTF-8");
    String contextPath = request.getContextPath();
    String course_id = (String) request.getParameter("courseId");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>과제 관리</title>
<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script> <!-- jQuery 추가 -->
<%
    String message = request.getParameter("message");
    if (message != null) {
%>
        <script>
            alert('<%= message %>'); // 메시지를 알림으로 표시
        </script>
<%
    }
%>
<script>
    $(document).ready(function() {
        // 과제 조회 탭에서 AJAX로 데이터 불러오기
        $.ajax({
            url: '<%= contextPath %>/assign/assignmentSearch.do',
            method: 'GET',
            data: { courseId: '<%= course_id %>' },
            dataType: 'json',
            success: function(assignmentList) {
                var tbody = $('#assignmentTable tbody');
                tbody.empty(); // 테이블 본문 초기화

                if (assignmentList && assignmentList.length > 0) {
                    assignmentList.forEach(function(assignment) {
                        var row = '<tr>' +
                                    '<td>' + assignment.title + '</td>' +
                                    '<td>' + assignment.dueDate + '</td>' +
                                    '<td>' + assignment.description + '</td>' +
                                    '<td>' +
                                        '<a href="editAssignment.jsp?assignmentId=' + assignment.assignmentId + '" class="btn btn-sm btn-primary">수정</a> ' +
                                        '<a href="#" class="btn btn-sm btn-danger" onclick="confirmDelete(' + assignment.assignmentId + ')">삭제</a> ' +
                                        '<a href="viewSubmissions.jsp?assignmentId=' + assignment.assignmentId + '" class="btn btn-sm btn-success">제출물 보기</a>' +
                                    '</td>' +
                                  '</tr>';
                        tbody.append(row);
                    });
                } else {
                    var emptyRow = '<tr><td colspan="4">등록된 과제가 없습니다.</td></tr>';
                    tbody.append(emptyRow);
                }
            },
            error: function(error) {
                console.error('Error fetching course list:', error);
            }
        });
    });
    
 	// 과제 삭제 확인 함수
    function confirmDelete(assignmentId) {
        if (confirm("삭제하시겠습니까?")) {
            // 사용자가 확인을 누르면 삭제 요청 실행
            window.location.href = "<%= contextPath %>/assign/deleteAssignment.do?assignmentId=" + assignmentId + "&courseId=" + '<%= course_id %>';
        }
    }
</script>
</head>
<body>
<div class="container mt-4">
    <h2 class="text-center mb-4">과제 관리</h2>
    
    <!-- 탭 -->
    <ul class="nav nav-tabs" id="assignmentTabs" role="tablist">
        <li class="nav-item">
            <a class="nav-link active" id="assignment-list-tab" data-bs-toggle="tab" href="#assignment-list" role="tab" aria-controls="assignment-list" aria-selected="true">과제 조회</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" id="assignment-create-tab" data-bs-toggle="tab" href="#assignment-create" role="tab" aria-controls="assignment-create" aria-selected="false">과제 등록</a>
        </li>
    </ul>

    <!-- 탭 내용 -->
    <div class="tab-content mt-4" id="assignmentTabsContent">
        <!-- 과제 조회 -->
        <div class="tab-pane fade show active" id="assignment-list" role="tabpanel" aria-labelledby="assignment-list-tab">
            <table id="assignmentTable" class="table table-bordered text-center">
                <thead class="table-success">
                    <tr>
                        <th>과제 제목</th>
                        <th>마감일</th>
                        <th>설명</th>
                        <th>관리</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- AJAX로 데이터가 동적으로 추가됨 -->
                </tbody>
            </table>
        </div>

        <!-- 과제 등록 -->
        <div class="tab-pane fade" id="assignment-create" role="tabpanel" aria-labelledby="assignment-create-tab">
            <form method="post" action="<%= contextPath %>/assign/createAssignment.do" class="p-4 border rounded">
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
                <input type="hidden" name="courseId" value="<%= course_id %>">
                <div class="text-center">
                    <button type="submit" class="btn btn-primary">등록</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
