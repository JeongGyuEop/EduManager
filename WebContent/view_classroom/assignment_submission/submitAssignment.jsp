<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	request.setCharacterEncoding("UTF-8");

    String contextPath = request.getContextPath();
	String assignment_title = (String)request.getAttribute("assignment_title");
	String assignmentId = (String)request.getAttribute("assignmentId");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>과제 제출</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
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
		const submitButton = $('#assignmentUploadForm button[type="submit"]'); // 제출 버튼
	    $.ajax({
	        url: '<%=contextPath%>/submit/getSubmittedAssignments.do',
	        method: 'GET',
	        data: {
	        	assignmentId: '<%=assignmentId%>'
	        },
	        dataType: 'json',
	        success: function(response) {
	        	if (response) { // 응답 객체가 존재할 경우
	        		
	                submitButton.prop('disabled', true); // 제출 버튼 비활성화
	        		
	                // 다운로드 버튼을 클릭했을 때 전달할 URL
	        		const downloadUrl = '<%=contextPath%>/submit/downloadAssignment.do?fileName=' 
                        + encodeURIComponent(response.fileName) 
                        + '&originalName=' + encodeURIComponent(response.originalName);
	        		
	                $('#submittedAssignments').append(
	                    '<tr>' +
	                        '<td>' + '<%=assignment_title%>' + '</td>' +
	                        '<td>' + response.originalName + '</td>' +
	                        '<td>' + response.submittedDate + '</td>' +
	                        '<td><a href="' + downloadUrl + '">다운로드</a></td>' +
	                        '<td><button class="delete-file-btn" data-file-id="' + response.fileId + '">삭제</button></td>' +
	                    '</tr>'
	                );
	            } else {
	                submitButton.prop('disabled', false); // 제출 버튼 활성화
	                $('#submittedAssignments').append('<tr><td colspan="3">제출된 과제가 없습니다.</td></tr>');
	            }
	        },
	        error: function() {
	            alert('제출된 과제를 불러오는 중 오류가 발생했습니다.');
	        }
	    });
	});
	
	$(document).on('click', '.delete-file-btn', function() {
		const submitButton = $('#assignmentUploadForm button[type="submit"]'); // 제출 버튼
	    const fileId = $(this).data('file-id');
	    const row = $(this).closest('tr');

	    $.ajax({
	        url: '<%=contextPath%>/submit/deleteFile.do',
	        method: 'POST',
	        data: { fileId: fileId },
	        success: function(response) {
                submitButton.prop('disabled', false); // 제출 버튼 활성화
	            alert(response);
	            row.remove(); // 삭제된 행을 테이블에서 제거
	            $('#submittedAssignments').append('<tr><td colspan="3">제출된 과제가 없습니다.</td></tr>');
	        },
	        error: function(xhr) {
	            if (xhr.status === 403) {
	                alert("파일 삭제 권한이 없습니다.");
	            } else {
	                alert("파일 삭제에 실패했습니다.");
	            }
	        }
	    });
	});
	
</script>

</head>
<body>
    <div class="container">
        <h1 class="mt-4">과제 제출 </h1>
        <form id="assignmentUploadForm" method="post" action="<%=contextPath%>/submit/uploadAssignment.do" enctype="multipart/form-data">
		    <div class="form-group">
		        <label for="courseId">과제명</label>
		        <input id="assignmentTitle" name="assignmentTitle" class="form-control" value="<%= assignment_title != null ? assignment_title : "과제명이 없습니다." %>" readonly>
		    </div>
		    <div class="form-group">
		        <label for="assignmentFile">파일 선택</label>
		        <input type="file" id="assignmentFile" name="assignmentFile" class="form-control" required />
		    </div>
		    <input type="hidden" name="assignmentId" value="<%= assignmentId %>"> <!-- Hidden 필드로 assignmentId 전달 -->
		    <input type="hidden" name="assignmentTitle" value="<%= assignment_title %>"> <!-- Hidden 필드로 assignmentId 전달 -->
		    <button type="submit" class="btn btn-primary mt-3">
		        <i class="fas fa-upload"></i> 제출
		    </button>
		</form>


        <hr />

        <h2>제출된 과제</h2>
        <div id="uploadedAssignments">
            <table class="table">
                <thead>
                    <tr>
                        <th>과제명</th>
                        <th>파일명</th>
                        <th>제출일</th>
                        <th>다운로드</th>
                        <th>관리</th>
                    </tr>
                </thead>
                <tbody id="submittedAssignments">
			        <!-- Ajax 결과가 여기 추가됩니다. -->
			    </tbody>
            </table>
        </div>
    </div>
</body>
</html>
