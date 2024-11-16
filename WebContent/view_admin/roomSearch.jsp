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
        body {
            background-color: #f4f6f9;
        }

        .table-container {
            max-width: 1000px;
            margin: 50px auto;
            background-color: #ffffff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0px 6px 12px rgba(0, 0, 0, 0.1);
        }

        .header-title {
            text-align: center;
            font-weight: bold;
            font-size: 2em;
            margin-bottom: 30px;
            color: #333;
        }

        .table thead th {
            background-color: #e9ecef;
            color: #495057;
            text-align: center;
        }

        .table tbody tr:hover {
            background-color: #f8f9fa;
        }

        .btn-edit, .btn-complete, .btn-delete {
            font-size: 0.9rem;
            padding: 6px 12px;
            border-radius: 5px;
        }

        .btn-edit {
            background-color: #17a2b8;
            color: #ffffff;
            border: none;
        }

        .btn-edit:hover {
            background-color: #138496;
        }

        .btn-complete {
            background-color: #28a745;
            color: #ffffff;
            border: none;
        }

        .btn-complete:hover {
            background-color: #218838;
        }

        .btn-delete {
            background-color: #dc3545;
            color: #ffffff;
            border: none;
        }

        .btn-delete:hover {
            background-color: #c82333;
        }

        .form-check {
            display: flex;
            align-items: center;
            gap: 8px;
        }

        .form-check-label {
            margin-bottom: 0;
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
                    <th scope="col">수정</th>
                    <th scope="col">삭제</th>
                </tr>
            </thead>
            <tbody>
                <%
                    ArrayList<ClassroomVo> courseList = (ArrayList<ClassroomVo>) request.getAttribute("roomList");

                    if (courseList != null && !courseList.isEmpty()) {
                        for (ClassroomVo classroom : courseList) {
                %>
                <tr id="row-<%= classroom.getRoom_id() %>">
                    <td class="text-center"><%= classroom.getRoom_id() %></td>
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
                        <button class="btn btn-edit" id="edit-btn-<%= classroom.getRoom_id() %>" onclick="enableEdit('<%= classroom.getRoom_id() %>')">수정</button>
                        <button class="btn btn-complete" id="complete-btn-<%= classroom.getRoom_id() %>" onclick="updateRoom('<%= classroom.getRoom_id() %>')" style="display: none;">완료</button>
                    </td>
                    <td class="text-center">
                        <button class="btn btn-delete" onclick="deleteRoom('<%= classroom.getRoom_id() %>')">삭제</button>
                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="5" class="text-center">조회할 강의실 정보가 없습니다.</td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>

    <script>
        function enableEdit(roomId) {
            $("#capacity-" + roomId).prop("disabled", false);
            $("#equipment-options-" + roomId).show();
            $("#equipment-display-" + roomId).hide();
            $("#edit-btn-" + roomId).hide();
            $("#complete-btn-" + roomId).show();
        }

        function updateRoom(roomId) {
            var capacity = $("#capacity-" + roomId).val();
            var equipment = [];
            $("#equipment-options-" + roomId + " input:checked").each(function() {
                equipment.push($(this).val());
            });

            $.ajax({
                url: "<%=contextPath%>/classroom/updateRoom.do",
                method: "POST",
                data: {
                    room_id: roomId,
                    capacity: capacity,
                    equipment: equipment.join(",")
                },
                success: function(response) {
                    if (response === "success") {
                        alert("강의실 정보가 수정되었습니다.");
                        $("#capacity-" + roomId).prop("disabled", true);
                        $("#equipment-options-" + roomId).hide();
                        $("#equipment-display-" + roomId).text(equipment.join(", ")).show();
                        $("#edit-btn-" + roomId).show();
                        $("#complete-btn-" + roomId).hide();
                    } else {
                        alert("수정 실패");
                    }
                },
                error: function() {
                    alert("오류 발생");
                }
            });
        }

        function deleteRoom(roomId) {
            if (!confirm("강의실을 삭제하시겠습니까?")) return;

            $.ajax({
                url: "<%=contextPath%>/classroom/deleteRoom.do",
                method: "POST",
                data: { room_id: roomId },
                success: function(response) {
                    if (response === "success") {
                        alert("강의실이 삭제되었습니다.");
                        $("#row-" + roomId).remove();
                    } else {
                        alert("삭제 실패");
                    }
                },
                error: function() {
                    alert("오류 발생");
                }
            });
        }
    </script>
</body>
</html>