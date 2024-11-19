<%@page import="java.util.ArrayList"%>
<%@page import="Vo.StudentVo"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	StudentVo svo = new StudentVo();
	String contextPath = request.getContextPath();
	String studentId = (String) session.getAttribute("student_id"); // 세션에서 학생 ID 가져오기
	List<StudentVo> evaluations = (List<StudentVo>) request.getAttribute("evaluations");
	//변수선언
	String courseId = null;
	String courseName = null;
	int rating = 0;
	String comments = null;
	//변수저장
	if (evaluations != null) {
	    for (StudentVo evaluation : evaluations) {
	        courseId = evaluation.getCourseId(); // 강의 ID
	        courseName = evaluation.getCourse_name(); // 강의 이름
	        rating = Integer.parseInt(evaluation.getRating()); // 평점
	        comments = evaluation.getComments(); // 평가 내용
	    }
	} else {
	    out.println("Evaluations 데이터가 없습니다.");
	}
	System.out.println(rating);
	if (studentId == null) {
		response.sendRedirect(contextPath + "/login.jsp");
	}

	List<StudentVo> evaluationList = (List<StudentVo>) request.getAttribute("evaluations");
	if (evaluationList == null) {
		evaluationList = new ArrayList<>();
	}
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>강의 평가 조회</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
	<div class="container mt-5">
		<h2 class="mb-4">내 강의 평가 조회</h2>

		<!-- 등록 버튼 -->
		<div class="mb-3">
			<a href="<%=contextPath%>/student/evaluationRegister.do"
				class="btn btn-primary">강의 평가 등록하기</a>
		</div>

		<!-- 강의 평가 리스트 -->
		<table class="table table-bordered">
			<thead class="table-dark">
				<tr>
					<th>강의 ID</th>
					<th>강의 이름</th>
					<th>평점</th>
					<th>내용</th>

					<th>수정</th>
					<th>삭제</th>
				</tr>
			</thead>
			<tbody>
				<%
					if (!evaluationList.isEmpty()) {
						for (StudentVo evaluation : evaluationList) {
				%>
				<tr>
					<td><%=evaluation.getCourseId()%></td>
					<td><%=evaluation.getCourse_name()%></td>
					<td><%=evaluation.getRating()%></td>
					<td><%=evaluation.getComments()%></td>

					<td>
						<form action="<%=contextPath%>/student/evaluationEdit.do"
							method="post" style="display: inline;">

							<input type="hidden" name="course_id"
								value="<%=courseId%>"> <input
								type="hidden" name="course_name"
								value="<%=courseName%>"> <input
								type="hidden" name="rating"
								value="<%=rating%>"> <input
								type="hidden" name="comments"
								value="<%=comments%>">
							<button type="submit" class="btn btn-warning btn-sm">수정</button>
						</form>
					</td>
					<td>
						<form action="<%=contextPath%>/student/evaluationDelete.do"
							method="post" style="display: inline;">
							<input type="hidden" name="evaluationId"
								value="<%=evaluation.getEvaluationId()%>">
							<button type="submit" class="btn btn-danger btn-sm">삭제</button>
						</form>
					</td>
				</tr>
				<%
					}
					} else {
				%>
				<tr>
					<td colspan="6" class="text-center">작성된 강의 평가가 없습니다.</td>
				</tr>
				<%
					}
				%>
			</tbody>
		</table>
	</div>

	<!-- 삭제 확인 팝업 -->
	<script>
        document.querySelectorAll('form[action$="evaluationDelete.do"]').forEach(form => {
            form.addEventListener('submit', function(event) {
                if (!confirm("정말로 삭제하시겠습니까?")) {
                    event.preventDefault();
                }
            });
        });
    </script>
</body>
</html>
