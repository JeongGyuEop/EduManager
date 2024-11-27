<%@page import="Vo.SubmissionVo"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    request.setCharacterEncoding("UTF-8");

    // submissions 리스트 가져오기
    List<SubmissionVo> submissions = (List<SubmissionVo>) request.getAttribute("submissions");
%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>학생 제출물 확인</title>

    <!-- 부트스트랩 CSS CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container mt-5 p-4 bg-white rounded shadow">
        <!-- 제목 -->
        <h2 class="text-center mb-4 text-primary">과제 제출 확인</h2>
        <p class="text-secondary"><strong>과제 제목:</strong> ${assignmentTitle}</p>

        <!-- 제출물 테이블 -->
        <table class="table table-striped table-hover text-center">
            <thead class="table-success">
                <tr>
                    <th scope="col">학생 이름</th>
                    <th scope="col">제출일</th>
<!--                     
                    <th scope="col">점수</th>
                    <th scope="col">피드백</th>
-->   
                    <th scope="col">파일</th>
                </tr>
            </thead>
            <tbody>
                <%
                    if (submissions != null && !submissions.isEmpty()) {
                        for (SubmissionVo submission : submissions) {
                %>
                    <tr>
                        <td><%= submission.getStudent().getUser_name() %></td>
                        <td><%= submission.getSubmittedDate() %></td>
<%--                         
                        <td><%= submission.getGrade() %></td>
                        <td><%= submission.getFeedback() %></td>
--%>                    
                        <td>
                            <a href="<%= request.getContextPath() %>/submit/downloadAssignment.do?fileId=<%= submission.getFileId() %>" 
                               class="btn btn-success btn-sm">
                                <%= submission.getOriginalName() %>
                            </a>
                        </td>
                    </tr>
                <%
                        }
                    } else {
                %>
                    <tr>
                        <td colspan="6" class="text-center text-muted">등록된 제출물이 없습니다.</td>
                    </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>

    <!-- 부트스트랩 JS & Popper.js CDN -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.7/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
</body>
</html>
