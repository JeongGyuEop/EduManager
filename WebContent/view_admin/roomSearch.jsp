<%@ page import="Vo.ClassroomVo" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.ArrayList" %>

<%
	request.setCharacterEncoding("UTF-8");
	String contextPath = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>강의실 목록</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <style>
        .table-container {
            max-width: 900px;
            margin: 0 auto;
            background-color: #f8f9fa;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
        }
        .table thead th {
            background-color: #d6e9c6;
            color: #333;
        }
        .table tbody tr:hover {
            background-color: #f1f1f1;
        }
        .btn-edit, .btn-complete {
            font-size: 0.9em;
            padding: 5px 10px;
        }
        .header-title {
            text-align: center;
            margin-bottom: 20px;
            font-weight: bold;
            font-size: 1.5em;
        }
    </style>
</head>
<body>
    <div class="table-container">
        <h2 class="header-title">강의실 목록</h2>

        <table class="table table-bordered table-hover">
            <thead>
                <tr>
                    <th scope="col">강의실 ID</th>
                    <th scope="col">수용 인원</th>
                    <th scope="col">장비</th>
                    <th scope="col" class="text-center">수정</th>
                </tr>
            </thead>
            <tbody>
                <%
                    ArrayList<ClassroomVo> courseList = (ArrayList<ClassroomVo>) request.getAttribute("roomList");
                    
                    if (courseList != null && !courseList.isEmpty()) {
                        for (ClassroomVo classroom : courseList) {
                %>
                <tr id="row-<%= classroom.getRoom_id() %>">
                    <td><%= classroom.getRoom_id() %></td>
                    <td>
                        <input type="number" class="form-control" id="capacity-<%= classroom.getRoom_id() %>" value="<%= classroom.getCapacity() %>" disabled>
                    </td>
                    <td>
                        <div id="equipment-options-<%= classroom.getRoom_id() %>" style="display: none;">
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="checkbox" id="equipment1-<%= classroom.getRoom_id() %>" value="프로젝터">
                                <label class="form-check-label" for="equipment1-<%= classroom.getRoom_id() %>">프로젝터</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="checkbox" id="equipment2-<%= classroom.getRoom_id() %>" value="화이트보드">
                                <label class="form-check-label" for="equipment2-<%= classroom.getRoom_id() %>">화이트보드</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="checkbox" id="equipment3-<%= classroom.getRoom_id() %>" value="실험 장비">
                                <label class="form-check-label" for="equipment3-<%= classroom.getRoom_id() %>">실험 장비</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="checkbox" id="equipment4-<%= classroom.getRoom_id() %>" value="컴퓨터실">
                                <label class="form-check-label" for="equipment4-<%= classroom.getRoom_id() %>">컴퓨터실</label>
                            </div>
                        </div>
                        <span id="equipment-display-<%= classroom.getRoom_id() %>"><%= classroom.getEquipment() %></span>
                    </td>
                    <td class="text-center">
                        <button class="btn btn-success btn-edit" id="edit-btn-<%= classroom.getRoom_id() %>" onclick="enableEdit('<%= classroom.getRoom_id() %>')">수정</button>
                        <button class="btn btn-primary btn-complete" id="complete-btn-<%= classroom.getRoom_id() %>" onclick="updateRoom('<%= classroom.getRoom_id() %>')" style="display: none;">수정 완료</button>
                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="4" class="text-center">조회할 강의실 정보가 없습니다.</td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>

    <script>
        function enableEdit(roomId) {
            // 수용 인원과 장비 옵션 활성화
            $("#capacity-" + roomId).prop("disabled", false);
            $("#equipment-options-" + roomId).show();
            $("#equipment-display-" + roomId).hide();

            // 버튼 변경
            $("#edit-btn-" + roomId).hide();
            $("#complete-btn-" + roomId).show();
        }

        function updateRoom(roomId) {
            var capacity = $("#capacity-" + roomId).val();
            
            // 선택된 장비들을 배열로 수집
            var equipment = [];
            $("#equipment-options-" + roomId + " input:checked").each(function() {
                equipment.push($(this).val());
            });

            $.ajax({
                url: "<%=contextPath%>/classroom/updateRoom.do",  // 서버에서 처리할 URL
                method: "POST",
                data: {
                    room_id: roomId,
                    capacity: capacity,
                    equipment: equipment.join(",")  // 배열을 문자열로 변환
                },
                success: function(response) {
                    if (response === "success") {
                        alert("강의실 정보가 성공적으로 수정되었습니다.");
                        $("#capacity-" + roomId).prop("disabled", true);
                        $("#equipment-options-" + roomId).hide();
                        $("#equipment-display-" + roomId).text(equipment.join(", ")).show();
                        $("#edit-btn-" + roomId).show();
                        $("#complete-btn-" + roomId).hide();
                    } else {
                        alert("수정에 실패했습니다. 다시 시도해 주세요.");
                    }
                },
                error: function(xhr, status, error) {
                    alert("수정 중 오류가 발생했습니다. 다시 시도해 주세요.");
                }
            });
        }
    </script>
</body>
</html>