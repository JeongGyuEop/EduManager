<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>판매 또는 구매 글 등록</title>
</head>
<body>
	<form action="#" method="get">
		<input type="hidden" value="">
		<!-- 작성자 정보 가져온 뒤 readonly -->
		<!-- 작성일은 dao에서 처리 -->
		<table>
			<thead>
				<tr>
					<td><label>글 제목</label> <input type="text"></td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>이미지</td>
				</tr>
				<tr>
					<td id="imginsert"></td>
				</tr>
				<tr>
					<td>내용</td>
				</tr>
				<tr>
					<td><input type="textarea"></td>
				</tr>
			</tbody>
			<tfoot>
				<tr>
					<td>학과 태그</td>
				</tr>
				<tr>
					<td><select id="majorinsert">
							<option value="일반 중고책 거래">
					</select></td>
				</tr>
				<tr>
					<td>
						<button>이미지 추가</button>
					</td>
				</tr>
				<tr>
					<td>
						<button>등록</button>
					</td>
				</tr>
			</tfoot>
		</table>
	</form>
</body>
</html>