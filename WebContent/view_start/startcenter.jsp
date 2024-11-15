<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
    request.setCharacterEncoding("UTF-8");
    String contextPath = request.getContextPath();
%>
    
<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bootstrap demo</title>
    <!-- Font Awesome 아이콘 추가 -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    
    
    <!-- 달력관련 -->
    <link href="https://cdn.jsdelivr.net/npm/fullcalendar@5.11.3/main.min.css" rel="stylesheet">
	<script src="https://cdn.jsdelivr.net/npm/fullcalendar@5.11.3/main.min.js"></script>

    
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
  	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
  	
	<link href="<%=contextPath %>/css/startpage.css" rel="stylesheet">

  </head>
  <body>
  <script>
    let slideIndex = 0;
    const images = [
        "<%= contextPath %>/img/BackGround/poster1.png",
        "<%= contextPath %>/img/BackGround/poster2.png",
        "<%= contextPath %>/img/BackGround/poster3.png"
    ];
    
    let autoSlideTimer; // 자동 슬라이드 타이머
    const autoSlideDelay = 3000; // 자동 슬라이드 시간 간격 (3초)

    function showSlide() {
        document.getElementById("sliderImage").src = images[slideIndex];
    }
    
    function nextSlide() {
        slideIndex = (slideIndex + 1) % images.length;
        showSlide();
    }

    function prevSlide() {
        slideIndex = (slideIndex - 1 + images.length) % images.length;
        showSlide();
    }

    // 자동 슬라이드를 시작하는 함수
    function startAutoSlide() {
        autoSlideTimer = setInterval(nextSlide, autoSlideDelay);
    }

    // 자동 슬라이드를 리셋하는 함수
    function resetAutoSlide() {
        clearInterval(autoSlideTimer); // 기존 타이머 초기화
        startAutoSlide(); // 자동 슬라이드 재시작
    }

    // 페이지 로드 시 자동 슬라이드 시작
    startAutoSlide();
    </script>
</head>
<body>

	<!-- 배경 이미지 -->
    <div class="background" ></div>
    
	<div class="container" >
	
	    <div class="row align-items-stretch">
	    
			<!-- 슬라이드 이미지 영역 -->
			<div class="col-md-8">
			    <!-- 고정된 크기 설정 (높이를 줄여서 세로 크기 조정) -->
			    <div class="p-5 bg-body-tertiary rounded-3" style="height: 450px;">
			        <div id="imageCarousel" class="carousel slide h-100" data-bs-ride="carousel" style="height: 100%;">
			            <div class="carousel-inner h-100">
			                <!-- 첫 번째 슬라이드 -->
			                <div class="carousel-item active h-100">
			                    <img src="<%= contextPath %>/img/background/poster1.png" class="d-block w-100 h-100 object-fit-cover" alt="Poster 1">
			                </div>
			                <!-- 두 번째 슬라이드 -->
			                <div class="carousel-item h-100">
			                    <img src="<%= contextPath %>/img/background/poster2.png" class="d-block w-100 h-100 object-fit-cover" alt="Poster 2">
			                </div>
			                <!-- 세 번째 슬라이드 -->
			                <div class="carousel-item h-100">
			                    <img src="<%= contextPath %>/img/background/poster3.png" class="d-block w-100 h-100 object-fit-cover" alt="Poster 3">
			                </div>
			            </div>
			            <!-- 이전/다음 버튼 -->
			            <button class="carousel-control-prev" type="button" data-bs-target="#imageCarousel" data-bs-slide="prev">
			                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
			                <span class="visually-hidden">Previous</span>
			            </button>
			            <button class="carousel-control-next" type="button" data-bs-target="#imageCarousel" data-bs-slide="next">
			                <span class="carousel-control-next-icon" aria-hidden="true"></span>
			                <span class="visually-hidden">Next</span>
			            </button>
			        </div>
			    </div>
			</div>

	        <!-- 로그인 박스 -->
	        <div class="col-md-4">
	            <div class="h-100 p-5 bg-light border rounded-3 d-flex flex-column justify-content-center">
	                <h2>로그인</h2>
	                <form action="<%=contextPath%>/member/login.do" method="post">
	                    <div class="mb-3 d-flex align-items-center">
	                        <label for="username" class="form-label me-2" style="width: 80px;">아이디</label>
	                        <input type="text" class="form-control" name="id" placeholder="아이디 입력">
	                    </div>
	                    <div class="mb-3 d-flex align-items-center">
	                        <label for="password" class="form-label me-2" style="width: 80px;">비밀번호</label>
	                        <input type="password" class="form-control" name="pass" placeholder="비밀번호 입력">
	                    </div>
	                    <button type="submit" class="btn btn-primary w-100">로그인</button>
	                </form>
	            </div>
	        </div>
	        
	    </div>
    
		<div class="row align-items-md-stretch mt-4"> <!-- 행 전체에 하단 여백 추가 -->
		    <!-- 학사 일정 영역 -->
		    <div class="col-md-7" style="margin-bottom: 10px;"> <!-- 학사 일정 상자에 하단 여백 추가 -->
		        <div class="h-100 p-5 bg-body-tertiary rounded-3">
		            <h2>학사 일정</h2>
		            <!-- FullCalendar 달력 삽입 -->
		            <div id="calendar"></div>
		        </div>
		    </div>
		    <div class="col-md-5" style="margin-bottom: 10px;"> <!-- 공지 사항 상자에 하단 여백 추가 -->
		        <div class="h-100 p-5 bg-body-tertiary border rounded-3">
		            <h2>공지 사항</h2>
		            <div>여기에는 공지사항이 들어갈 영역입니다.</div>
		        </div>
		    </div>
		</div>
    
</div>

  <script>
        document.addEventListener('DOMContentLoaded', function() {
            var calendarEl = document.getElementById('calendar');
            var calendar = new FullCalendar.Calendar(calendarEl, {
                initialView: 'dayGridMonth', // 월간 달력
                headerToolbar: {
                    left: 'prev,next today',
                    center: 'title',
                    right: 'dayGridMonth,dayGridWeek'
                },
                events: [
                    { title: '학사 일정 예시', start: '2024-11-10', end: '2024-11-12' },
                    { title: '중간고사', start: '2024-11-15', end: '2024-11-16' }
                ],
                // 
                height: 'auto', // 달력 높이를 고정된 값으로 설정
                width: 'auto',
                locale: 'ko' // 한국어 설정
            });
            calendar.render();
        });
    </script>
</body>
</html>
