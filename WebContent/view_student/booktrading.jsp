<%@ page import="Vo.BookPostVo"%>
<%@ page import="Vo.MemberVo"%>
<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	request.setCharacterEncoding("utf-8");
	String contextPath = request.getContextPath();

	MemberVo memberVo = new MemberVo();
	String userId = (String) session.getAttribute("id");


	String nowPage = (String) request.getAttribute("nowPage");
	String nowBlock = (String) request.getAttribute("nowBlock");

	List<BookPostVo> majorInfo = (List<BookPostVo>) request.getAttribute("majorInfo");
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>판매 또는 구매 글 등록</title>

 <script type="text/javascript">
        window.onload = function () {
            // JSP에서 메시지 값을 JavaScript 변수로 가져오기
            var message = "<%= request.getAttribute("message") %>";
            if (message && message !== "null") {
                alert(message); // 메시지가 있을 경우에만 alert 창 표시
            }
        };
    </script>
    
</head>
<body>

	<form action="<%=contextPath%>/Book/bookpostupdate.do" method="post"
		enctype="multipart/form-data">
		<!-- 작성자 정보 가져온 뒤 readonly -->
		<!-- 작성일은 DAO에서 처리 -->
<!-- 	<input type="hidden" name="userId" value="<%=userId%>" readonly>  -->	
		<input type="text" name="userId" value="<%=userId%>">

		<table>
			<thead>
				<tr>
					<td><label for="postTitle">글 제목:</label> <input type="text"
						id="postTitle" name="postTitle" required></td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><label for="preview-image">이미지 미리보기</label>
						<div id="preview" style="display: flex; flex-wrap: wrap;"></div></td>
				</tr>
				<tr>
					<td><label for="postContent">내용:</label> <textarea
							id="postContent" name="postContent" rows="5" cols="50" required></textarea></td>
				</tr>
			</tbody>
			<tfoot>
				<tr>
					<td><label for="majorTag">학과 태그:</label> <select id="majorTag"
						name="majorTag">
							<option value="일반 중고책 거래">일반 중고책 거래</option>
							<c:forEach var="major" items="${majorInfo}">
								<option value="${major.majorTag}">${major.majorTag}</option>
							</c:forEach>
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
