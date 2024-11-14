<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="utf-8">
    <title>학사 일정</title>
    <!-- Bootstrap CSS 추가 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- FullCalendar CSS 추가 -->
    <link href="https://cdn.jsdelivr.net/npm/fullcalendar@5.11.3/main.min.css" rel="stylesheet">
    <!-- Bootstrap JS 추가 -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <!-- FullCalendar JS 추가 -->
    <script src="https://cdn.jsdelivr.net/npm/fullcalendar@5.11.3/main.min.js"></script>
    <!-- FullCalendar 한국어 로케일 설정을 위한 추가 스크립트 -->
    <script src="https://cdn.jsdelivr.net/npm/fullcalendar@5.11.3/locales-all.min.js"></script>
    <!-- jQuery 라이브러리 추가 (AJAX 요청을 위해 사용) -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>

<body>
    <div class="container mt-5">
        <div class="h-100 p-5 bg-body-tertiary rounded-3">
            <h2>학사 일정</h2>
            <!-- FullCalendar가 표시될 div 요소 -->
            <div id="calendar"></div>
        </div>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            // 캘린더가 삽입될 요소를 가져옴
            const calendarEl = document.getElementById('calendar');
            
            // FullCalendar 인스턴스를 생성
            const calendar = new FullCalendar.Calendar(calendarEl, {
                initialView: 'dayGridMonth', // 초기 화면을 월간 달력으로 설정
                headerToolbar: { // 캘린더 헤더 툴바 설정
                    left: 'prev,next today', // 왼쪽에 이전, 다음, 오늘 버튼 배치
                    center: 'title', // 중앙에 타이틀 배치
                    right: 'dayGridMonth,dayGridWeek' // 오른쪽에 월간, 주간 보기 버튼 배치
                },
                locale: 'ko', // 한국어 로케일 설정
                events: function(fetchInfo, successCallback, failureCallback) {
                    // 이벤트 데이터를 서버에서 가져오는 AJAX 요청
                    $.ajax({
                    	url: '<%=request.getContextPath()%>/Board/boardCalendar.bo',
                        type: 'GET', // 요청 방식은 GET
                        dataType: 'json', // 반환 데이터 타입은 JSON
                        data: {
                            start: fetchInfo.startStr, // 요청 시작 날짜
                            end: fetchInfo.endStr, // 요청 종료 날짜
                            action: 'getEvents' // 요청 액션명 지정
                        },
                        success: function(response) {
                            successCallback(response); // 성공 시 이벤트 데이터를 캘린더에 전달
                        },
                        error: function() {
                            failureCallback(); // 실패 시 콜백 함수 호출
                        }
                    });
                },
                eventClick: function(info) {
                    // 이벤트 클릭 시 알림 창으로 일정 정보 표시
                    alert('일정: ' + info.event.title + '\n설명: ' + info.event.extendedProps.description);
                },
                height: 'auto' // 캘린더 높이를 자동으로 설정
            });
            
            // 캘린더 렌더링
            calendar.render();
        });
    </script>
</body>

</html>
