<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>판매 또는 구매 글 등록</title>
</head>
<body>
	<form action="submitPost.jsp" method="post"
		enctype="multipart/form-data">
		<!-- 작성자 정보 가져온 뒤 readonly -->
		<!-- 작성일은 DAO에서 처리 -->
		<input type="hidden" name="author" value="${author}" readonly>
		<table>
			<thead>
				<tr>
					<td><label for="title">글 제목:</label> <input type="text"
						id="title" name="title" required></td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><label for="image">이미지:</label> <input type="file"
						id="image" name="image" accept="image/*"></td>
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
					<td>
						<button type="submit">등록</button>
					</td>
				</tr>
			</tfoot>
		</table>
	</form>
</body>
</html>
