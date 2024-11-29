<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String contextPath = (String)request.getContextPath(); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>수강신청 기간 설정</title>
    <!-- Bootstrap CSS 연결 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f9f9f9; /* 배경색 설정 */
        }
        .form-container {
            max-width: 400px; /* 폼의 최대 너비 설정 */
            margin: 40px auto; /* 중앙 정렬 */
            padding: 20px;
            background-color: #ffffff; /* 폼 배경색 */
            border-radius: 8px; /* 둥근 모서리 */
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); /* 그림자 효과 */
        }
        .form-label {
            font-size: 14px; /* 라벨 글자 크기 조정 */
        }
        .form-control {
            font-size: 14px; /* 입력 필드 글자 크기 조정 */
        }
        .btn {
            font-size: 14px; /* 버튼 글자 크기 조정 */
            padding: 10px; /* 버튼 패딩 조정 */
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="form-container">
            <h2 class="text-center mb-4">수강신청 기간 설정</h2>
            <form action="<%=contextPath %>/classroom/setEnrollmentPeriod.do" method="post">
                <!-- 시작 날짜 -->
                <div class="mb-3">
                    <label for="start_date" class="form-label">시작 날짜</label>
                    <input type="datetime-local" name="start_date" id="start_date" class="form-control" required>
                </div>
                <!-- 종료 날짜 -->
                <div class="mb-3">
                    <label for="end_date" class="form-label">종료 날짜</label>
                    <input type="datetime-local" name="end_date" id="end_date" class="form-control" required>
                </div>
                <!-- 설명 -->
                <div class="mb-3">
                    <label for="description" class="form-label">설명</label>
                    <textarea name="description" id="description" rows="3" class="form-control" placeholder="설명을 입력하세요."></textarea>
                </div>
                <!-- 버튼 -->
                <button type="submit" class="btn btn-primary w-100">설정</button>
            </form>
        </div>
    </div>
    <!-- Bootstrap JS 연결 (옵션, 팝업 등 활성화 필요 시) -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
