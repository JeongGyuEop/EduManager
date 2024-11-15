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
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">


<!-- 달력관련 -->
<link
	href="https://cdn.jsdelivr.net/npm/fullcalendar@5.11.3/main.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/fullcalendar@5.11.3/main.min.js"></script>


<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
	crossorigin="anonymous"></script>

<link href="<%=contextPath%>/css/startpage.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

</head>
<body>
	<!-- 학사 일정 영역 -->
	<div class="row align-items-md-stretch mt-4">
		<div class="col-md-7" style="margin-bottom: 10px;">
			<!-- 학사 일정 상자에 하단 여백 추가 -->
			<div class="h-100 p-5 bg-body-tertiary rounded-3">
				<h2>학사 일정</h2>
				<!-- FullCalendar 달력 삽입 -->
				<div id="calendar"></div>
			</div>
		</div>
	</div>

	<script>
		document.addEventListener("DOMContentLoaded", function() {
			// FullCalendar 설정
			var calendarEl = document.getElementById('calendar');
			var calendar = new FullCalendar.Calendar(calendarEl, {
				initialView : 'dayGridMonth', // 월간 달력
				headerToolbar : {
					left : 'prev,next today',
					center : 'title',
					right : 'dayGridMonth,dayGridWeek'
				},
				// 서버로부터 일정 데이터를 가져오기 위해 이벤트 소스를 설정합니다.
				events : function(info, successCallback, failureCallback) {
					// AJAX를 이용해 서버에서 일정 데이터를 가져옵니다.
					$.ajax({
						url : '/api/getAcademicSchedules',
						method : 'GET',
						data : {
							start : info.startStr, // 요청한 달력의 시작 날짜 (YYYY-MM-DD 형식)
							end : info.endStr
						// 요청한 달력의 끝 날짜 (YYYY-MM-DD 형식)
						},
						success : function(data) {
							successCallback(data); // 서버로부터 받은 데이터를 FullCalendar에 전달합니다.
						},
						error : function() {
							failureCallback();
						}
					});
				},
				height : 'auto',
				locale : 'ko' // 한국어 설정
			});
			calendar.render();
		});
	</script>
</body>
</html>
