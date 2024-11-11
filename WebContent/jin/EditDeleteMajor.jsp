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
    // 여러 이벤트를 연결하기 위해 jQuery 사용
    $(document).on("keydown blur", "input", function(event) {
        // 공통 동작 수행 함수 호출
        handleInputEvent(event, $(this));
    });
});

// 입력 이벤트를 처리하는 공통 함수
function handleInputEvent(event, $input) {
    if (event.type === "keydown" && event.key === "Enter") {
        // 엔터키가 눌렸을 때 업데이트 수행
        updateAllValues($input.closest("td")[0]);
    } else if (event.type === "blur") {
        // 포커스가 해제되었을 때에도 동일한 업데이트 수행
        updateAllValues($input.closest("td")[0]);
    }
}

function makeEditable(td) {
    // td의 기존 텍스트 값을 가져옵니다.
    var originalValue = td.innerText;

    // 새로운 input 엘리먼트를 생성합니다.
    var input = document.createElement("input");
    input.type = "text";
    input.value = originalValue;

    // td 내부를 비우고 input을 넣습니다.
    td.innerHTML = "";
    td.appendChild(input);

    // input에 포커스를 줍니다.
    input.focus();
}

function updateAllValues(td) {
    // 수정된 값을 가져옵니다.
    var input = td.querySelector("input");
    var updatedValue = input.value;

    // td 내부에 수정된 값만 남기고 input을 제거합니다.
    td.innerHTML = updatedValue;

    // 수정된 값을 테이블에서 가져옵니다.
    var row = td.parentElement;
    var majorCode = row.cells[0].innerText;
    var majorName = row.cells[1].innerText;
    var majorTel = row.cells[2].innerText;

    // 변경된 값을 가지고 서블릿 호출
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "<%=contextPath%>/MI/editMajor.do", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    // 학과 코드, 학과 이름, 전화 번호 전달
    var params = "majorCode=" + encodeURIComponent(majorCode)
            + "&majorName=" + encodeURIComponent(majorName) + "&majorTel="
            + encodeURIComponent(majorTel);

    xhr.send(params);

    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            console.log("Update successful");
            location.reload(); // 알림 후 페이지 새로 고침
        }
    };
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
