<%@page import="Vo.BoardVo"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>학사 일정 조회</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/calendarCSS.css">
<script type="text/javascript">
    window.onload = function() {
        var message = "<%= request.getAttribute("message") %>";
		if (message == "삭제 성공" || message == "삭제 실패") {
			alert(message);
		}
	};
</script>
<script>
	// 전체 선택 체크박스 기능
	function toggleSelectAll(source) {
		var checkboxes = document.getElementsByName('schedule_ids');
		for (var i = 0, n = checkboxes.length; i < n; i++) {
			checkboxes[i].checked = source.checked;
		}
	}

	// 삭제 버튼 클릭 시 선택된 일정의 ID를 삭제 폼에 추가
	function handleDelete() {
		var selectedIds = [];
		var checkboxes = document.getElementsByName('schedule_ids');
		for (var i = 0; i < checkboxes.length; i++) {
			if (checkboxes[i].checked) {
				selectedIds.push(checkboxes[i].value);
			}
		}

		if (selectedIds.length === 0) {
			alert('삭제할 일정을 선택해주세요.');
			return false;
		}

		var scheduleIdsInput = document.createElement('input');
		scheduleIdsInput.type = 'hidden';
		scheduleIdsInput.name = 'schedule_ids';
		scheduleIdsInput.value = selectedIds.join(',');
		document.getElementById('deleteForm').appendChild(scheduleIdsInput);

		return confirm('선택한 일정을 삭제하시겠습니까?');
	}
</script>
</head>
<body>
	<h2>학사 일정 조회</h2>

	<!-- 메시지 표시 블록 -->
	<%
		String msg = request.getParameter("msg");
	%>
	<%
		if (msg != null && !msg.isEmpty()) {
	%>
	<div class="message <%=msg.contains("성공") ? "" : "error"%>"><%=msg%></div>
	<%
		}
	%>

	<!-- 조회 폼 -->
	<div class="search-form">
		<form
			action="<%=request.getContextPath()%>/Board/boardCalendarSearch.bo"
			method="get">
			<label for="year">연도:</label> <select name="year" id="year">
				<%
					int selectedYear = (request.getAttribute("selectedYear") != null)
							? Integer.parseInt(request.getAttribute("selectedYear").toString())
							: -1;
					for (int y = 2020; y <= 2030; y++) {
				%>
				<option value="<%=y%>" <%=(y == selectedYear) ? "selected" : ""%>><%=y%></option>
				<%
					}
				%>
			</select> <label for="month">월:</label> <select name="month" id="month">
				<%
					int selectedMonth = (request.getAttribute("selectedMonth") != null)
							? Integer.parseInt(request.getAttribute("selectedMonth").toString())
							: -1;
					for (int m = 1; m <= 12; m++) {
				%>
				<option value="<%=m%>" <%=(m == selectedMonth) ? "selected" : ""%>><%=m%></option>
				<%
					}
				%>
			</select>


			<button type="submit">조회</button>
		</form>
	</div>

	<!-- 삭제 폼 -->
	<div class="delete-form">
		<form id="deleteForm"
			action="<%=request.getContextPath()%>/Board/deleteSchedules.bo"
			method="post" onsubmit="return handleDelete()">
			<%
				if (request.getAttribute("schedules") != null) {
			%>
			<table>
				<thead>
					<tr>
						<th><input type="checkbox" id="selectAll"
							onclick="toggleSelectAll(this)"></th>
						<th>일정 ID</th>
						<th>일정 이름</th>
						<th>설명</th>
						<th>시작 날짜</th>
						<th>종료 날짜</th>
					</tr>
				</thead>
				<tbody>
					<%
						List<BoardVo> schedules = (List<BoardVo>) request.getAttribute("schedules");
							for (BoardVo schedule : schedules) {
					%>
					<tr>
						<td><input type="checkbox" name="schedule_ids"
							value="<%=schedule.getSchedule_id()%>"></td>
						<td><%=schedule.getSchedule_id()%></td>
						<td><%=schedule.getEvent_name()%></td>
						<td><%=schedule.getDescription()%></td>
						<td><%=new java.text.SimpleDateFormat("yyyy-MM-dd").format(schedule.getStart_date())%></td>
						<td><%=new java.text.SimpleDateFormat("yyyy-MM-dd").format(schedule.getEnd_date())%></td>
					</tr>
					<%
						}
					%>
				</tbody>
			</table>
			<button type="submit" class="delete-button">삭제</button>
			<%
				} else {
			%>
			<p>선택한 기간에 해당하는 학사 일정이 없습니다.</p>
			<%
				}
			%>
		</form>
	</div>
</body>
</html>
