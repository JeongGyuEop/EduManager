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

        //-------------
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
                        var row = '<tr data-id="' + assignment.assignmentId + '">' +
				                        '<td class="editable editable-title">' + assignment.title + '</td>' +
				                        '<td class="editable editable-dueDate">' + assignment.dueDate + '</td>' +
				                        '<td class="editable editable-description">' + assignment.description + '</td>' +
				                        '<td>' +
				                            '<button class="btn btn-sm btn-primary edit-btn">수정</button>' +
				                            '<button class="btn btn-sm btn-success complete-btn" style="display:none;">완료</button>' +
				                            '<button class="btn btn-sm btn-secondary cancel-btn" style="display:none;">취소</button>' +
				                            '<button class="btn btn-sm btn-danger delete-btn">삭제</button>' +
				                            '<button class="btn btn-sm btn-success view-btn">제출물 보기</button>' +
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
    
    //-------------
 	// 수정 버튼 클릭 시
    $(document).on('click', '.edit-btn', function () {
        var row = $(this).closest('tr'); // 현재 행 가져오기

        // 기존 값 저장 (data 속성에 저장)
        row.find('.editable').each(function () {
            var cell = $(this);
            cell.data('original-value', cell.text()); // 기존 값을 저장
            var value = cell.text();

            // 마감일은 date picker로 변환
            if (cell.hasClass('editable-dueDate')) {
                cell.html('<input type="date" class="form-control" value="' + value + '">');
            } else {
                cell.html('<input type="text" class="form-control" value="' + value + '">');
            }
        });

        // 모든 버튼 숨기기
        row.find('.edit-btn, .delete-btn, .view-btn').hide();

        // 완료/취소 버튼 표시
        row.find('.complete-btn, .cancel-btn').show();
    });
    

    //-------------
    // 완료 버튼 클릭 시
    $(document).on('click', '.complete-btn', function () {
        var row = $(this).closest('tr'); // 현재 행 가져오기
        var assignmentId = row.data('id'); // 데이터 ID
        var title = row.find('.editable-title input').val();
        var dueDate = row.find('.editable-dueDate input').val();
        var description = row.find('.editable-description input').val();

        // AJAX로 수정 요청 보내기
        $.ajax({
            url: '<%= contextPath %>/assign/updateAssignment.do',
            method: 'POST',
            data: {
            	courseId: '<%= course_id %>',
                assignmentId: assignmentId,
                title: title,
                dueDate: dueDate,
                description: description
            },
            success: function (response) {
                if (response === 'success') {
                    alert('수정이 완료되었습니다.');

                    // 입력 필드를 다시 텍스트로 변경
                    row.find('.editable-title').html(title);
                    row.find('.editable-dueDate').html(dueDate);
                    row.find('.editable-description').html(description);

                    // 완료/취소 버튼 숨기기
                    row.find('.complete-btn, .cancel-btn').hide();

                    // 수정, 삭제, 제출물확인 버튼 다시 표시
                    row.find('.edit-btn, .delete-btn, .view-btn').show();
                } else {
                    alert('수정에 실패했습니다. 다시 시도해주세요.');
                }
            },
            error: function (error) {
                alert('수정 요청 중 오류가 발생했습니다.');
                console.error(error);
            }
        });
    });


    //-------------
    // 취소 버튼 클릭 시
    $(document).on('click', '.cancel-btn', function () {
        var row = $(this).closest('tr'); // 현재 행 가져오기

        // 저장된 기존 값으로 복원
        row.find('.editable').each(function () {
            var cell = $(this);
            var originalValue = cell.data('original-value'); // 저장된 기존 값
            cell.html(originalValue); // 기존 값으로 복원
        });

        // 완료/취소 버튼 숨기기
        row.find('.complete-btn, .cancel-btn').hide();

        // 기본 버튼 다시 표시
        row.find('.edit-btn, .delete-btn, .view-btn').show();
    });
 

    //-------------
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
        
        <!-- 수정 모달 -->
<div class="modal fade" id="editAssignmentModal" tabindex="-1" aria-labelledby="editAssignmentModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="editAssignmentModalLabel">과제 수정</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="editAssignmentForm">
                    <input type="hidden" id="editAssignmentId">
                    <div class="mb-3">
                        <label for="editTitle" class="form-label">제목:</label>
                        <input type="text" id="editTitle" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label for="editDescription" class="form-label">설명:</label>
                        <textarea id="editDescription" class="form-control" rows="3" required></textarea>
                    </div>
                    <div class="mb-3">
                        <label for="editDueDate" class="form-label">마감일:</label>
                        <input type="date" id="editDueDate" class="form-control" required>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                <button type="button" class="btn btn-primary" id="saveEditAssignment">저장</button>
            </div>
        </div>
    </div>
</div>
        
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
