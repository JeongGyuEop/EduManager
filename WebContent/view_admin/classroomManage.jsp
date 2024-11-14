<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>강의실 등록</title>
    <!-- Bootstrap CSS -->
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome for icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        body {
            background-color: #f0f2f5;
            font-family: 'Arial', sans-serif;
        }
        #custom-container {
            max-width: 600px;
            background-color: #ffffff;
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0px 8px 16px rgba(0, 0, 0, 0.15);
            margin-top: 50px;
        }
        .form-group label {
            font-weight: bold;
            color: #333;
        }
        .form-check-label {
            margin-left: 5px;
        }
        .btn-primary {
            background: linear-gradient(45deg, #4a90e2, #567bdb);
            border: none;
            padding: 12px;
            font-size: 18px;
            font-weight: bold;
            border-radius: 8px;
        }
        .btn-primary:hover {
            background: linear-gradient(45deg, #3a78c2, #466abd);
        }
        .form-icon {
            color: #4a90e2;
            margin-right: 8px;
        }
        .header-title {
            font-size: 24px;
            font-weight: bold;
            color: #4a90e2;
            text-align: center;
            margin-bottom: 30px;
        }
        .form-check-inline {
            display: inline-flex;
            align-items: center;
            margin-right: 15px;
        }
        
    </style>
</head>
<body>
    <div class="container" id="custom-container">
        <h2 class="header-title"><i class="fas fa-chalkboard"></i> 강의실 등록</h2>
        
        <form action="registerClassroom" method="post">
            <div class="form-group">
                <label for="roomId"><i class="fas fa-door-open form-icon"></i>강의실 ID</label>
                <input type="text" class="form-control" id="roomId" name="room_id" required placeholder="예: R101">
            </div>

            <div class="form-group">
                <label for="capacity"><i class="fas fa-users form-icon"></i>수용 인원</label>
                <input type="number" class="form-control" id="capacity" name="capacity" required placeholder="최대 수용 인원">
            </div>

            <div class="form-group">
                <label><i class="fas fa-tools form-icon"></i>장비</label>
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="checkbox" id="equipment1" name="equipment[]" value="프로젝터">
                            <label class="form-check-label" for="equipment1">프로젝터</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="checkbox" id="equipment2" name="equipment[]" value="화이트보드">
                            <label class="form-check-label" for="equipment2">화이트보드</label>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="checkbox" id="equipment4" name="equipment[]" value="실험 장비">
                            <label class="form-check-label" for="equipment4">실험 장비</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="checkbox" id="equipment3" name="equipment[]" value="컴퓨터실">
                            <label class="form-check-label" for="equipment3">컴퓨터실</label>
                        </div>
                    </div>
                </div>
            </div>

            <button type="submit" class="btn btn-primary btn-block"><i class="fas fa-save"></i> 등록</button>
        </form>
    </div>


    <!-- Bootstrap JS, Popper.js, and jQuery -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>