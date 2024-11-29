<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Vo.StudentVo" %>
<%
    String contextPath = request.getContextPath();
    String studentId = (String) session.getAttribute("id"); // 세션에서 로그인한 학생 ID 가져오기
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>강의 평가 등록</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
    <div class="container mt-5">
        <h2 class="mb-4">강의 평가 등록</h2>
        <form action="<%=contextPath%>/student/evaluationSubmit.do" method="post">
            <!-- 학생 정보 -->
            <div class="mb-3">
                <label for="studentId" class="form-label">학생 ID</label>
                <input type="text" class="form-control" id="studentId" name="student_id" value="<%=studentId%>" readonly>
            </div>

            <!-- 강의 선택 -->
            <div class="mb-3">
                <label for="courseId" class="form-label">강의 ID</label>
                <select class="form-select" id="courseId" name="course_id" required>
                    <option value="">강의를 선택하세요</option>
                    <%-- 강의 목록 동적 생성 --%>
                    <%
                        List<StudentVo> courseList = (List<StudentVo>) request.getAttribute("courseList");
                        if (courseList.size() != 0) {
                            for (StudentVo course : courseList) {
                    %>
                    <option value="<%=course.getCourseId()%>">
                        <%=course.getCourseId()%> - <%=course.getCourse_name()%>
                    </option>
                    <%
                            }
                        }
                    %>
                </select>
            </div>

            <!-- 평점 -->
            <div class="mb-3">
                <label for="rating" class="form-label">평점</label>
                <select class="form-select" id="rating" name="rating" required>
                    <option value="">평점을 선택하세요</option>
                    <option value="1">1 - 매우 나쁨</option>
                    <option value="2">2 - 나쁨</option>
                    <option value="3">3 - 보통</option>
                    <option value="4">4 - 좋음</option>
                    <option value="5">5 - 매우 좋음</option>
                </select>
            </div>

            <!-- 평가 내용 -->
            <div class="mb-3">
                <label for="comments" class="form-label">평가 내용</label>
                <textarea class="form-control" id="comments" name="comments" rows="4" placeholder="평가 내용을 입력하세요" required></textarea>
            </div>

            <!-- 등록 및 조회 버튼 -->
            <div class="d-flex justify-content-between">
                <button type="submit" class="btn btn-primary">등록하기</button>
                <a href="<%=contextPath%>/student/evaluationList.do" class="btn btn-secondary">바로 조회하기</a>
            </div>
        </form>
    </div>
</body>
</html>