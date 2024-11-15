<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="Dao.BoardDAO"%>
<%@page import="Vo.ScheduleVo"%>
<%
	String contextPath = request.getContextPath();
%>
<html>
<head>
<title>일정 관리 페이지</title>
<script>
        function enableEdit(row) {
            const cells = row.getElementsByTagName("td");
            const originalValues = [];

            // 임시로 값을 저장하고 input 태그로 변환
            for (let i = 1; i < cells.length - 1; i++) {
                const value = cells[i].innerText;
                originalValues.push(value);
                cells[i].innerHTML = '';
                if (i === 4) {
                    // For content field
                    const textarea = document.createElement('textarea');
                    textarea.value = value;
                    cells[i].appendChild(textarea);
                } else if (i === 2 || i === 3) {
                    // For start date and end date fields
                    const input = document.createElement('input');
                    input.type = 'date';
                    input.value = value;
                    cells[i].appendChild(input);
                } else {
                    // For other fields like title
                    const input = document.createElement('input');
                    input.type = 'text';
                    input.value = value;
                    cells[i].appendChild(input);
                }
            }

            // Change the '수정' link to '저장'
            const editLink = cells[cells.length - 1].getElementsByTagName("a")[0];
            editLink.innerText = "저장";
            editLink.href = "javascript:void(0);";
            editLink.onclick = function () { saveEdit(row, originalValues); };
        }

        function saveEdit(row, originalValues) {
            const cells = row.getElementsByTagName("td");
            const scheduleId = row.querySelector("input[type='checkbox']").value;
            const updatedData = {
                schedule_id: scheduleId,
                title: cells[1].getElementsByTagName("input")[0].value,
                startDate: cells[2].getElementsByTagName("input")[0].value,
                endDate: cells[3].getElementsByTagName("input")[0].value,
                content: cells[4].getElementsByTagName("textarea")[0].value
            };

            // **현재 선택된 달 정보를 가져옵니다.**
            const currentMonth = document.getElementById('month').value;

            // 폼 생성 및 데이터 추가
            const form = document.createElement('form');
            form.method = 'post';
            form.action = '<%=contextPath%>/Board/updateSchedule';
		for ( const key in updatedData) {
			if (updatedData.hasOwnProperty(key)) {
				const hiddenField = document.createElement('input');
				hiddenField.type = 'hidden';
				hiddenField.name = key;
				hiddenField.value = updatedData[key];
				form.appendChild(hiddenField);
			}
		}

		// **현재 선택된 달 정보를 폼에 추가합니다.**
		const monthField = document.createElement('input');
		monthField.type = 'hidden';
		monthField.name = 'month';
		monthField.value = currentMonth;
		form.appendChild(monthField);

		document.body.appendChild(form);
		form.submit();
	}
</script>
</head>
<body>
	<h2>일정 관리</h2>

	<!-- 일정 추가 폼 -->
	<form id="scheduleForm" action="<%out.print(contextPath);%>/Board/addSchedule"
		method="post">
		<label for="title">일정 제목:</label> <input type="text" id="title"
			name="title" required> <br> <label for="startDate">시작
			일자:</label> <input type="date" id="startDate" name="startDate" required>
		<br> <label for="endDate">마침 일자:</label> <input type="date"
			id="endDate" name="endDate" required> <br> <label
			for="content">일정 내용:</label>
		<textarea id="content" name="content" required></textarea>
		<br> <input type="submit" value="추가">
	</form>

	<h3>일정 목록</h3>
	<!-- 조회할 달 선택 폼 -->
	<form action="<%=contextPath%>/Board/viewSchedule" method="get">
		<label for="month">조회할 달:</label> <input type="month" id="month"
			name="month" required
			value="<%=request.getParameter("month") != null ? request.getParameter("month") : ""%>">
		<input type="submit" value="조회">
	</form>

	<form action="<%out.print(contextPath);%>/Board/deleteSchedule"
		method="post">
		<input type="hidden" name="month"
			value="<%=request.getParameter("month") != null ? request.getParameter("month") : ""%>">
		<table border="1">
			<thead>
				<tr>
					<th>선택</th>
					<th>일정 제목</th>
					<th>시작 일자</th>
					<th>마침 일자</th>
					<th>일정 내용</th>
					<th>수정</th>
				</tr>
			</thead>
			<tbody>
				<%
					List<ScheduleVo> scheduleList = (List<ScheduleVo>) request.getAttribute("scheduleList");
					if (scheduleList != null) {
						for (ScheduleVo schedule : scheduleList) {
				%>
				<tr>
					<td><input type="checkbox" name="deleteIds"
						value="<%=schedule.getSchedule_id()%>"></td>
					<td><%=schedule.getEvent_name()%></td>
					<td><%=schedule.getStart_date()%></td>
					<td><%=schedule.getEnd_date()%></td>
					<td><%=schedule.getDescription()%></td>
					<td><a href="javascript:void(0);"
						onclick="enableEdit(this.parentElement.parentElement);">수정</a></td>
				</tr>
				<%
					}
					}
				%>
			</tbody>
		</table>
		<input type="button" value="선택된 일정 삭제" onclick="deleteSchedules();">
	</form>

	<script>
		document
				.getElementById('scheduleForm')
				.addEventListener(
						'submit',
						function(event) {
							const title = document.getElementById('title').value
									.trim();
							const startDate = document
									.getElementById('startDate').value;
							const endDate = document.getElementById('endDate').value;

							if (!title || !startDate || !endDate) {
								alert('일정 제목, 시작 일자 및 마침 일자는 필수 입력 사항입니다.');
								event.preventDefault(); // 폼 제출 취소
							}
						});
		function deleteSchedules() {
		    // 선택된 체크박스 가져오기
		    const checkboxes = document.querySelectorAll('input[name="deleteIds"]:checked');
		    const deleteIds = Array.from(checkboxes).map(cb => cb.value);

		    if (deleteIds.length === 0) {
		        alert('삭제할 일정이 선택되지 않았습니다.');
		        return;
		    }

		    // 삭제 확인
		    if (!confirm('선택된 일정을 삭제하시겠습니까?')) {
		        return;
		    }

		    // 현재 선택된 달 정보 가져오기
		    const currentMonth = document.getElementById('month').value;

		    // 서버로 전송할 데이터 준비 (URLSearchParams 사용)
		    const params = new URLSearchParams();
		    deleteIds.forEach(id => params.append('deleteIds', id));
		    params.append('month', currentMonth);

		    // AJAX 요청 보내기
		    fetch('<%=contextPath%>/Board/deleteSchedule', {
		        method: 'POST',
		        headers: {
		            'Content-Type': 'application/x-www-form-urlencoded'
		        },
		        body: params.toString()
		    })
		    .then(response => {
		        if (response.ok) {
		        	window.location.href = "<%=contextPath%>/Board/viewSchedule?month=" + encodeURIComponent(currentMonth);
		        } else {
		            alert('일정 삭제 중 오류가 발생했습니다.');
		        }
		    })
		    .catch(error => {
		        console.error('Error:', error);
		        alert('일정 삭제 중 오류가 발생했습니다.');
		    });
		}
	</script>
</body>
</html>
