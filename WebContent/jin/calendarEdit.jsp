<%@page import="Vo.ScheduleVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
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

            // Send updated data to the server (AJAX or form submission)
            // Example of form submission
            const form = document.createElement('form');
            form.method = 'post';
            form.action = '<% out.print(contextPath); %>/Board/updateSchedule';
            for (const key in updatedData) {
                if (updatedData.hasOwnProperty(key)) {
                    const hiddenField = document.createElement('input');
                    hiddenField.type = 'hidden';
                    hiddenField.name = key;
                    hiddenField.value = updatedData[key];
                    form.appendChild(hiddenField);
                }
            }
            document.body.appendChild(form);
            form.submit();
        }
    </script>
</head>
<body>
    <h2>일정 관리</h2>

    <!-- 일정 추가 폼 -->
    <form action="<% out.print(contextPath); %>/Board/addSchedule" method="post">
        <label for="title">일정 제목:</label>
        <input type="text" id="title" name="title" required>
        <br>
        <label for="startDate">시작 일자:</label>
        <input type="date" id="startDate" name="startDate" required>
        <br>
        <label for="endDate">마침 일자:</label>
        <input type="date" id="endDate" name="endDate" required>
        <br>
        <label for="content">일정 내용:</label>
        <textarea id="content" name="content" required></textarea>
        <br>
        <input type="submit" value="추가">
    </form>

    <h3>일정 목록</h3>
    <!-- 조회할 달 선택 폼 -->
    <form action="<% out.print(contextPath); %>/Board/viewSchedule" method="get">
        <label for="month">조회할 달:</label>
        <input type="month" id="month" name="month" required>
        <input type="submit" value="조회">
    </form>

    <form action="<% out.print(contextPath); %>/Board/deleteSchedule" method="post">
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
                        <td><input type="checkbox" name="deleteIds" value="<%= schedule.getSchedule_id() %>"></td>
                        <td><%= schedule.getEvent_name() %></td>
                        <td><%= schedule.getStart_date() %></td>
                        <td><%= schedule.getEnd_date() %></td>
                        <td><%= schedule.getDescription() %></td>
                        <td><a href="javascript:void(0);" onclick="enableEdit(this.parentElement.parentElement);">수정</a></td>
                    </tr>
                <%
                        }
                    }
                %>
            </tbody>
        </table>
        <input type="submit" value="선택된 일정 삭제">
    </form>
</body>
</html>
