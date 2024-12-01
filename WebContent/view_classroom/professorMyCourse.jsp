<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>강의실 환영 페이지</title>
    <!-- 부트스트랩 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
    <style>
        .welcome-container {
            margin-top: 50px;
            text-align: center;
            color: #333;
        }
        .welcome-icon {
            font-size: 50px;
            color: #4CAF50;
            margin-bottom: 20px;
        }
        .welcome-text {
            font-size: 1.5rem;
            font-weight: bold;
            margin-bottom: 10px;
        }
        .info-text {
            font-size: 1rem;
            color: #555;
        }
        .action-buttons {
            margin-top: 30px;
        }
        .action-buttons .btn {
            margin: 5px;
        }
    </style>
</head>
<body>
    <div class="container welcome-container">
        <i class="fas fa-chalkboard-teacher welcome-icon"></i>
        <p class="welcome-text">강의실에 오신 것을 환영합니다, 이순god 교수님!</p>
        <p class="info-text">지금 강의실에서 관리할 강의와 학생 정보를 확인하거나 새로운 공지사항을 등록해보세요.</p>
        
        <div class="action-buttons">
            <a href="#" class="btn btn-primary"><i class="fas fa-book"></i> 강의 목록 보기</a>
            <a href="#" class="btn btn-success"><i class="fas fa-plus-circle"></i> 새 강의 추가</a>
            <a href="#" class="btn btn-warning"><i class="fas fa-bullhorn"></i> 공지사항 등록</a>
        </div>
    </div>

    <!-- 부트스트랩 JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
