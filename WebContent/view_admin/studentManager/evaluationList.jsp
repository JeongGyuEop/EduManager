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
    <title>강의 평가 조회</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
    <div class="container mt-5">
        <h2 class="mb-4">내 강의 평가 조회</h2>

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
                <%-- 서버에서 강의 평가 데이터를 가져와 반복 출력 --%>
                <%
                    List<StudentVo> evaluationList = (List<StudentVo>) request.getAttribute("evaluations"); // 컨트롤러에서 설정한 "evaluations" 속성 가져오기
                    if (evaluationList != null && !evaluationList.isEmpty()) { // 리스트가 null이 아니고 비어있지 않을 때
                        for (StudentVo evaluation : evaluationList) {
                %>
                <tr>
                    <td><%= evaluation.getCourseId() %></td>
                    <td><%= evaluation.getCourse_name() %></td>
                    <td><%= evaluation.getRating() %></td>
                    <td><%= evaluation.getComments() %></td>
                    <td>
                        <form action="<%= contextPath %>/student/evaluationEdit.do" method="get" style="display:inline;">
                            <input type="hidden" name="evaluation_id" value="<%= evaluation.getEvaluationId() %>">
                            <button type="submit" class="btn btn-warning btn-sm">수정</button>
                        </form>
                    </td>
                    <td>
                        <form action="<%= contextPath %>/student/evaluationDelete.do" method="post" style="display:inline;">
                            <input type="hidden" name="evaluation_id" value="<%= evaluation.getEvaluationId() %>">
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

        <!-- 돌아가기 버튼 -->
        <a href="<%= contextPath %>/student/evaluationRegister.do" class="btn btn-secondary">강의 평가 등록하기</a>
    </div>
</body>
</html>