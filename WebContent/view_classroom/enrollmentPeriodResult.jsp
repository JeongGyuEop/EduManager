<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String contextPath = (String)request.getContextPath(); %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>수강신청 기간 설정 결과</title>
</head>
<body>
    <h2>${message}</h2>

    <!-- 설정된 수강신청 기간 표시 -->
    <c:if test="${not empty startDate}">
        <p>설정된 수강신청 기간:</p>
        <ul>
            <li><strong>시작 날짜:</strong> ${startDate}</li>
            <li><strong>종료 날짜:</strong> ${endDate}</li>
            <li><strong>설명:</strong> ${description}</li>
        </ul>
    </c:if>

    <!-- 다시 설정하기 버튼 -->
    <a href="<%=contextPath %>/classroom/enrollmentPeriodPage.bo">다시 설정하기</a>
</body>
</html>
