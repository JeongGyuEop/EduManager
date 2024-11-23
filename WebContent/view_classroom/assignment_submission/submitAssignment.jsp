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
                        <th>과목</th>
                        <th>파일명</th>
                        <th>제출일</th>
                        <th>다운로드</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- 업로드된 파일 리스트 -->
                    <tr>
                        <td>컴퓨터 과학 기초</td>
                        <td>example_assignment.pdf</td>
                        <td>2024-11-23</td>
                        <td><a href="<%=contextPath%>/downloadAssignment.do?fileId=1" class="btn btn-sm btn-success"><i class="fas fa-download"></i> 다운로드</a></td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>
