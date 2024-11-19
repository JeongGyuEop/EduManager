<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>EditDeleteMajor</title>
    <!-- jQuery 최신 버전 추가 -->
    <script src="https://code.jquery.com/jquery-latest.min.js"
        crossorigin="anonymous"></script>
    <!-- 외부 CSS 파일 연결 -->
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/css/majorCSS.css">
    
    <script type="text/javascript">
    function handleInputEvent(event, $input) {
        var td = $input.closest("td")[0];
        if (event.type === "keydown" && (event.key === "Enter" || event.which === 13 || event.keyCode === 13)) {
            // 엔터키가 눌렸을 때 업데이트 수행
            event.stopPropagation(); // 이벤트 전파 중지
            event.preventDefault();  // 기본 동작 방지
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
    
        // td.cellIndex 값을 확인
        console.log("td.cellIndex:", td.cellIndex);
    
        // 유효성 검사 필요 여부 결정
        if (td.cellIndex === 2) { // 전화번호 칸이라면
            if (!validateForm(updatedValue)) {
                // 유효하지 않으면 원래 값으로 복원
                td.innerHTML = originalValue;
                td.setAttribute("onclick", "makeEditable(this)"); // onclick 핸들러 복원
                return;
            }
        }
    
        // 유효하면 td에 값을 업데이트
        td.innerHTML = updatedValue;
        td.setAttribute("onclick", "makeEditable(this)"); // onclick 핸들러 복원
    
        // 데이터 수집
        var row = td.parentElement;
        var majorCode = row.cells[0].innerText.trim();  // 학과 코드
        var majorName = row.cells[1].innerText.trim();  // 학과 이름
        var majorTel = row.cells[2].innerText.trim();   // 학과 전화번호
    
        // jQuery AJAX 요청 보내기
        $.ajax({
            type: "POST",
            url: "<%=contextPath%>/major/editMajor.do",
            data: {
                majorCode: majorCode,
                majorName: majorName,
                majorTel: majorTel
            },
            success: function(response) {
                console.log("Update successful");
                alert("수정 또는 삭제에 성공했습니다.");
                location.reload();
            },
            error: function(xhr, status, error) {
                console.error("Update failed. Status: " + xhr.status);
                alert("업데이트에 실패했습니다. 다시 시도해 주세요.");
                // 업데이트 실패 시 원래 값으로 되돌림
                td.innerHTML = originalValue;
                td.setAttribute("onclick", "makeEditable(this)"); // onclick 핸들러 복원
            }
        });
    }
    
    function validateForm(telNumber) {
        telNumber = telNumber.trim();
        console.log("validateForm called with telNumber:", telNumber);
        const telRegex = /^\d{2,3}-\d{3,4}-\d{4}$/;
    
        if (!telRegex.test(telNumber)) {
            alert("유효한 전화번호 형식을 입력해 주세요. 예: 02-123-1234");
            return false;
        }
        return true;
    }
    
    function makeEditable(td) {
        var originalValue = td.getAttribute("data-original-value");
        if (originalValue === null) {
            originalValue = td.textContent.trim(); // td.textContent 사용
            td.setAttribute("data-original-value", originalValue);
        }
    
        var input = document.createElement("input");
        input.type = "text";
        input.value = originalValue;
    
        td.innerHTML = "";
        td.appendChild(input);
    
        // 이벤트 핸들러 직접 추가
        $(input).on("keydown blur", function(event) {
            handleInputEvent(event, $(this));
        });
    
        input.focus();
    }
    </script>
</head>
<body>
    <h3>수정 및 삭제할 학과 이름 또는 학과 번호를 입력해주세요.</h3>
    <form class="form-container" action="<%=contextPath%>/major/searchMajor.do" method="get">
        <label for="searchMajor">학과 이름 또는 학과 번호:</label>
        <input type="text"
            id="searchMajor" name="searchMajor" placeholder="학과 이름 또는 번호를 입력하세요">
        <input type="submit" value="검색">
    </form>

    <h4>검색 결과</h4>
    <p>수정 : 학과 이름 또는 전화 번호 클릭</p>
    <p>삭제 : 학과 이름을 공백으로 두면 해당 학과를 삭제</p>
    <table class="major-table">
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
