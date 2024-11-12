<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
%>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>New Major Input Page</title>
<script type="text/javascript">
        window.onload = function () {
            inputResult();
            loadMajorData(); // 페이지 로드 시 학과 데이터 로드
            setInterval(loadMajorData, 7800);
        };

        // controller에서 받는 값 처리 함수
        function inputResult() {
            var addResult = parseInt("${addResult}", 10);
            if (isNaN(addResult)) return;

            switch (addResult) {
                case -1:
                    alert("학과 이름 및 번호를 입력해 주세요.");
                    break;
                case 0:
                    alert("데이터베이스에 추가하는 데 실패했습니다. 다시 시도해 주세요.");
                    break;
                case 1:
                    alert("학과가 성공적으로 추가되었습니다.");
                    break;
                case -2:
                    alert("이미 존재하는 학과명입니다.");
                    break;
            }
        }

        // AJAX를 사용해 학과 데이터를 로드하는 함수
        let isLoading = false;
        function loadMajorData() {
            if (isLoading) return;
            isLoading = true;

            var xhr = new XMLHttpRequest();
            var contextPath = "<%=request.getContextPath()%>";

		xhr.open("GET", contextPath + "/MI/fetchMajorData", true);
		xhr.onreadystatechange = function() {
			if (xhr.readyState == 4) {
				isLoading = false;
				if (xhr.status == 200) {
					let data = JSON.parse(xhr.responseText);
		            console.log(data); // 서버에서 받아온 데이터 출력
					updateMajorTable(data);
				} else {
					console.error("데이터 로드 중 오류 발생: " + xhr.status);
				}
			}
		};
		xhr.send();
	}
    	function updateMajorTable(data) {
    		let tbody = document.querySelector("#major-table tbody");
    		tbody.innerHTML = "";

    		data.forEach(function(major) {
    			let row = tbody.insertRow();
    			let majorcode = major.majorcode;
    			row.insertCell(0).innerText = majorcode;
    			row.insertCell(1).innerText = major.majorname;
    			row.insertCell(2).innerText = major.majortel;
    		});
    		updateMajorCount(tbody.querySelectorAll("tr").length);
    	}
    	function updateMajorCount(totalMajor) {
    		const tfoot = document.querySelector("#major-table tfoot");
    		if (totalMajor > 0) {
    			tfoot.innerHTML = "<tr><th colspan='4'>조은대학교에는 총 " + totalMajor + "개의 학과가 있습니다!</th></tr>";
    		} else {
    			tfoot.innerHTML = `<tr><th colspan='4'>학과 정보가 없습니다!</th></tr>`;
    		}
    	}
</script>
</head>

<body>
	<div>
		<form action="<%=contextPath%>/MI/MajorInput.do" method="get"
			onsubmit="return validateForm()">
			<label for="MajorNameInput">신규 학과명:</label> <input type="text"
				id="MajorNameInput" name="MajorNameInput" placeholder="**학과"
				required="required"> <label for="MajorTelInput">학과
				사무실 전화번호:</label> <input type="tel" id="MajorTelInput" name="MajorTelInput"
				placeholder="02-123-1234">
			<button type="submit">제출</button>
		</form>
	</div>
	<div
		style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
		<div>
			<h3>학과 정보</h3>
		</div>
		<div>
			<a href="<%=contextPath%>/jin/EditDeleteMajor.jsp"
				onclick="openCustomPopup(this.href); return false;">수정 및 삭제</a>
		</div>
	</div>
	<div>
		<table id="major-table">
			<thead>
				<tr>
					<th>학과 코드</th>
					<th>학과명</th>
					<th>학과 사무실 전화번호</th>
				</tr>
			</thead>
			<tbody></tbody>
			<tfoot></tfoot>
		</table>
	</div>

	<script>
		// 전화번호 유효성 검사
		function validateForm() {
			const telNumber = document.getElementById("MajorTelInput").value;
			const telRegex = /^\d{2,3}-\d{3,4}-\d{4}$/;

			if (!telRegex.test(telNumber)) {
				alert("유효한 전화번호 형식을 입력해 주세요. 예: 02-123-1234");
				return false;
			}
			return true;
		}
		function openCustomPopup(url) {
			// 팝업창을 띄울 때, 크기와 설정을 세밀히 조정합니다.
			const popupOptions = [ 'width=440', // 팝업 너비
			'height=700', // 팝업 높이
			'toolbar=no', // 툴바 표시 여부 (없음)
			'menubar=no', // 메뉴바 표시 여부 (없음)
			'scrollbars=yes', // 스크롤바 표시 (있음)
			'resizable=no', // 창 크기 조절 가능 여부 (없음)
			'location=yes', // 주소 표시 여부 (있음)
			'status=no' // 상태바 표시 여부 (없음)
			].join(',');

			// 팝업창 열기
			window.open(url, 'editMajorPopup', popupOptions);
		}
	</script>
</body>

</html>
