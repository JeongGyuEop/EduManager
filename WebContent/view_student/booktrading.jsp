
<%@page import="Vo.MemberVo"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
	String contextPath = request.getContextPath();
	MemberVo memberVo = new MemberVo();
	String user_id = memberVo.getUser_id();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>판매 또는 구매 글 등록</title>
</head>

<% 
    String message = (String) request.getAttribute("message"); 
    if (message != null) {
%>
        <p><%= message %></p>
<% 
    } 
%>
<body>
	<form action="<%=contextPath%>/Board/bookPostUpload.bo" method="get"
		enctype="multipart/form-data">
		<!-- 작성자 정보 가져온 뒤 readonly -->
		<!-- 작성일은 DAO에서 처리 -->
		<input type="hidden" name="user_id" value="${user_id}" readonly>
		<table>
			<thead>
				<tr>
					<td><label for="title">글 제목:</label> 
					<input type="text" id="title" name="title" required></td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><label for="preview-image">이미지 미리보기</label>
						<div id="preview" style="display: flex; flex-wrap: wrap;"></div></td>
				</tr>
				<tr>
					<td><label for="content">내용:</label> <textarea id="content"
							name="content" rows="5" cols="50" required></textarea></td>
				</tr>
			</tbody>
			<tfoot>
				<tr>
					<td><label for="major">학과 태그:</label> <select id="major"
						name="major">
							<option value="일반 중고책 거래">일반 중고책 거래</option>
							<!-- 추가적인 옵션을 여기에 넣으세요 -->
					</select></td>
				</tr>
				<tr>
					<td><label for="image">이미지:</label><input type="file"
						id="image" name="image" accept="image/*"
						onchange="previewImages(event)" multiple></td>
				</tr>
				<tr>
					<td>
						<button type="submit">등록</button>
					</td>
				</tr>
			</tfoot>
		</table>
	</form>
	<script>
 		function previewImages(event) {
			const files = event.target.files;

			// 미리보기 영역을 초기화합니다.
			const preview = document.getElementById('preview');
			preview.innerHTML = ""; // 기존 미리보기를 초기화

			// 파일이 5개를 초과하면 처리하지 않음
			if (files.length > 5) {
				alert("최대 5개의 이미지만 업로드할 수 있습니다.");
				event.target.value = ""; // 파일 선택 초기화
				return;
			}

			// 선택한 파일들을 순회하며 미리보기 생성
			for (let i = 0; i < files.length; i++) {
				if (files[i]) {
					const reader = new FileReader();

					reader.onload = function(e) {
						// 각 이미지 미리보기를 위한 <img> 태그 생성
						const img = document.createElement("img");
						img.src = e.target.result;
						img.style.width = "178px"; // 이미지의 가로 크기
						img.style.height = "178px"; // 이미지의 세로 크기
						img.style.margin = "2px"; // 이미지 간의 간격
						preview.appendChild(img);
					};

					reader.readAsDataURL(files[i]);
				}
			}
		} 
	</script>

</body>
</html>
