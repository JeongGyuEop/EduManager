<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>EditDeleteMajor</title>
<!-- jQuery 최신 버전 추가 -->
<script src="https://code.jquery.com/jquery-latest.min.js" crossorigin="anonymous"></script>
<script type="text/javascript">
$(document).ready(function () {
    // 모든 input에 대해 keydown과 blur 이벤트 핸들러 설정
    $(document).on("keydown blur", "input", function(event) {
        handleInputEvent(event, $(this));
    });
});

// 입력 이벤트를 처리하는 공통 함수
function handleInputEvent(event, $input) {
    var td = $input.closest("td")[0];
    if (event.type === "keydown" && event.key === "Enter") {
        // 엔터키가 눌렸을 때 업데이트 수행
        updateAllValues(td);
    } else if (event.type === "blur") {
        // 포커스가 해제되었을 때에도 동일한 업데이트 수행
        updateAllValues(td);
    }
}

function updateAllValues(td) {
    var input = td.querySelector("input");
    if (!input) {
        console.error("Input element not found");
        return;
    }

    var updatedValue = input.value.trim();
    var originalValue = td.getAttribute("data-original-value");

    // 유효성 검사 필요 여부 결정
    if (td.cellIndex === 2) { // 전화번호 칸이라면
        if (!validateForm(updatedValue)) {
            // 유효하지 않으면 원래 값으로 복원
            td.innerHTML = originalValue;
            return;
        }
    }

    // 유효하면 td에 값을 업데이트
    td.innerHTML = updatedValue;

    // 데이터 수집
    var row = td.parentElement;
    var majorCode = row.cells[0].innerText.trim();  // 학과 코드
    var majorName = row.cells[1].innerText.trim();  // 학과 이름
    var majorTel = row.cells[2].innerText.trim();   // 학과 전화번호

    // XHR 요청 보내기
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "<%=contextPath%>/MI/editMajor.do", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    // 학과 코드, 학과 이름, 전화 번호 전달
    var params = "majorCode=" + encodeURIComponent(majorCode)
               + "&majorName=" + encodeURIComponent(majorName)
               + "&majorTel=" + encodeURIComponent(majorTel);
    console.log(params);
    xhr.send(params);

    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                console.log("Update successful");
                location.reload();
            } else {
                console.error("Update failed. Status: " + xhr.status);
                alert("업데이트에 실패했습니다. 다시 시도해 주세요.");
                // 업데이트 실패 시 원래 값으로 되돌림
                td.innerHTML = originalValue;
            }
        }
    };
}

function validateForm(telNumber) {
    const telRegex = /^\d{2,3}-\d{3,4}-\d{4}$/;

    if (!telRegex.test(telNumber)) {
        alert("유효한 전화번호 형식을 입력해 주세요. 예: 02-123-1234");
        return false;
    }
    return true;
}

function makeEditable(td) {
    var originalValue = td.innerText.trim();

    var input = document.createElement("input");
    input.type = "text";
    input.value = originalValue;

    td.innerHTML = "";
    td.appendChild(input);
    td.setAttribute("data-original-value", originalValue); // 원래 값을 저장해 둠

    input.focus();
}

</script>
</head>
<body>
    <h3>수정 및 삭제할 학과 이름 또는 학과 번호를 입력해주세요.</h3>
    <form action="<%=contextPath%>/MI/searchMajor.do" method="get">
        <label for="searchMajor">학과 이름 또는 학과 번호:</label>
        <input type="text" id="searchMajor" name="searchMajor" placeholder="학과 이름 또는 번호를 입력하세요">
        <input type="submit" value="검색">
    </form>

    <h4>검색 결과</h4>
    <p>수정 : 학과 이름 또는 전화 번호 클릭</p>
    <p>삭제 : 학과 이름을 공백으로 두면 해당 학과를 삭제</p>
    <table border="1">
        <tr>
            <th>학과 코드</th>
            <th>학과 이름</th>
            <th>학과 전화번호</th>
        </tr>
        <c:forEach var="major" items="${searchList}">
            <tr>
                <td>${major.majorCode}</td>
                <td onclick="makeEditable(this)">${major.majorName}</td>
                <td onclick="makeEditable(this)">${major.majorTel}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
