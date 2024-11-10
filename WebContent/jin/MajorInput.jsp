<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	System.out.println(contextPath);
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
			setInterval(loadMajorData, 5000); // 5초마다 loadMajorData 함수 실행
		};
		//
		function inputResult() {
			// controller에서 받는 값 처리
			var addResult = parseInt("${addResult}", 10);

			if (isNaN(addResult)) {
				return; // addResult가 없을 때는 함수를 종료함
			}

			if (addResult === -1) {
				alert("학과 이름 및 번호를 입력해 주세요.");
			} else if (addResult === 0) {
				alert("데이터베이스에 추가하는 데 실패했습니다. 다시 시도해 주세요.");
			} else if (addResult === 1) {
				alert("학과가 성공적으로 추가되었습니다.");
			} else if (addResult === -2) {
				alert("이미 존재하는 학과명입니다.");
			}
		}

		// AJAX를 사용해 학과 데이터를 로드하는 함수
		let isLoading = false;

		function loadMajorData() {
			if (isLoading) return; // 이미 로딩 중이라면 요청하지 않음
			isLoading = true;

			// 1. XMLHttpRequest 객체 생성
			var xhr = new XMLHttpRequest();
			var contextPath = "<%=request.getContextPath()%>";

			// 2. 요청 설정 (GET 방식으로 fetchMajorData.jsp에 요청을 보냄)
			xhr.open("GET", contextPath + "/fetchMajorData", true);

			// 3. 요청의 상태가 변경될 때마다 실행될 콜백 함수 정의
			xhr.onreadystatechange = function () {
				if (xhr.readyState == 4) {
					isLoading = false; // 요청이 완료되면 isLoading 상태 해제
					if (xhr.status == 200) {
						// 응답받은 데이터를 테이블의 <tbody> 부분에 삽입
						let data = JSON.parse(xhr.responseText);

						// 테이블의 <tbody> 부분을 비우고 새 데이터 삽입
						let tbody = document.querySelector("#major-table tbody");
						tbody.innerHTML = ""; // 기존 데이터를 비움

						// JSON 데이터를 이용해 새로운 행을 추가
						data.forEach(function (major) {
							let row = tbody.insertRow();
							row.insertCell(0).innerText = major.majorcode;
							row.insertCell(1).innerText = major.majorname;
							row.insertCell(2).innerText = major.majortel;

							// 수정 및 삭제 버튼이 들어갈 4번째 셀 생성
							let actionsCell = row.insertCell(3);
							actionsCell.innerHTML = `
		                        <button onclick="editMajor('${major.majorcode}')">수정</button>
		                        <button onclick="deleteMajor('${major.majorcode}')">삭제</button>
		                    `;
						});
						// 총 학과 수 업데이트
						const totalMajor = document.querySelector("#major-table tbody").children.length;
						// 문자열 연결 방식으로 학과 수 표시
						if (totalMajor > 0) {
							document.querySelector("#major-table tfoot").innerHTML = "<tr><th colspan='4'>**대학교에는 총 " + totalMajor + "개의 학과가 있습니다!</th></tr>";
						} else {
							document.querySelector("#major-table tfoot").innerHTML = "<tr><th colspan='4'>학과 정보가 없습니다!</th></tr>";
						}
					} else {
						console.error("데이터 로드 중 오류 발생: " + xhr.status);
					}
				}
			};
			xhr.send(); // 서버로 요청 전송
		}
	</script>
</head>

<body>
	<div>
		<form action="<%=contextPath%>/DMI/MajorInput.do" method="get" onsubmit="return validateForm()">
			<label for="MajorNameInput">신규 학과명:</label> <input type="text" id="MajorNameInput" name="MajorNameInput"
				placeholder="**학과" required="required"> <label for="MajorTelInput">학과
				사무실 전화번호:</label> <input type="tel" id="MajorTelInput" name="MajorTelInput" placeholder="02-123-1234">

			<button type="submit">제출</button>
		</form>
	</div>
	<div>
		<h3>학과 정보</h3>
		<table id="major-table">
			<thead>
				<tr>
					<th>학과 코드</th>
					<th>학과명</th>
					<th>학과 사무실 전화번호</th>
					<th>수정 및 삭제</th>
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

	</script>
</body>

</html>
