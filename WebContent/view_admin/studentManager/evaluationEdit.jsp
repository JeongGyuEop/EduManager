<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String contextPath = request.getContextPath();
    String studentId = (String) session.getAttribute("student_id"); // 세션에서 학생 ID 가져오기
    String evaluationId = request.getParameter("evaluationId"); // 수정할 평가 ID 가져오기

    // 로그인 체크
    if (studentId == null) {
        response.sendRedirect(contextPath + "/login.jsp");
    }

    // 강의 정보 및 평가 데이터 처리
    String courseId = (String) request.getAttribute("courseId");
    String courseName = (String) request.getAttribute("courseName");
    Integer rating = (Integer) request.getAttribute("rating");
    String comments = (String) request.getAttribute("comments");

    courseId = courseId != null ? courseId : "";
    courseName = courseName != null ? courseName : "";
    comments = comments != null ? comments : "";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>강의 평가 수정</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
    <div class="container mt-5">
        <h2 class="mb-4">강의 평가 수정</h2>
        
        <%-- 에러 메시지 표시 --%>
        <%
            String errorMessage = (String) request.getAttribute("errorMessage");
            if (errorMessage != null) {
        %>
        <div class="alert alert-danger" role="alert">
            <%= errorMessage %>
        </div>
        <%
            }
        %>
        
        <form action="<%= contextPath %>/student/evaluationUpdate.do" method="post">
            <!-- 평가 ID (숨김 필드) -->
            <input type="hidden" name="evaluationId" value="<%= evaluationId %>">
            
            <!-- 학생 정보 -->
            <div class="mb-3">
                <label for="studentId" class="form-label">학생 ID</label>
                <input type="text" class="form-control" id="studentId" name="student_id" value="<%= studentId %>" readonly>
            </div>

            <!-- 강의 정보 -->
            <div class="mb-3">
                <label for="courseId" class="form-label">강의 ID</label>
                <input type="text" class="form-control" id="courseId" name="course_id" value="<%= courseId %>" readonly>
            </div>
            <div class="mb-3">
                <label for="courseName" class="form-label">강의 이름</label>
                <input type="text" class="form-control" id="courseName" name="course_name" value="<%= courseName %>" readonly>
            </div>
            
            <!-- 평점 -->
            <div class="mb-3">
                <label for="rating" class="form-label">평점</label>
                <select class="form-select" id="rating" name="rating" required>
                    <option value="1" <%= ("1".equals(rating + "")) ? "selected" : "" %>>1 - 매우 나쁨</option>
                    <option value="2" <%= ("2".equals(rating + "")) ? "selected" : "" %>>2 - 나쁨</option>
                    <option value="3" <%= ("3".equals(rating + "")) ? "selected" : "" %>>3 - 보통</option>
                    <option value="4" <%= ("4".equals(rating + "")) ? "selected" : "" %>>4 - 좋음</option>
                    <option value="5" <%= ("5".equals(rating + "")) ? "selected" : "" %>>5 - 매우 좋음</option>
                </select>
            </div>

            <!-- 평가 내용 -->
            <div class="mb-3">
                <label for="comments" class="form-label">평가 내용</label>
                <textarea class="form-control" id="comments" name="comments" rows="4" required><%= comments %></textarea>
            </div>

            <!-- 수정 및 취소 버튼 -->
            <div class="d-flex justify-content-between">
                <button type="submit" class="btn btn-primary">수정하기</button>
                <a href="<%= contextPath %>/student/evaluationList.do" class="btn btn-secondary">취소</a>
            </div>
        </form>
    </div>

    <!-- 수정 확인 스크립트 -->
    <script>
        document.querySelector('form').addEventListener('submit', function(event) {
            if (!confirm("평가를 수정하시겠습니까?")) {
                event.preventDefault();
            }
        });
    </script>
</body>
</html>
