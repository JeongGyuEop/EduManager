<%@page import="java.io.IOException"%>
<%@page import="java.io.FileNotFoundException"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.util.Properties"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Vo.BookPostVo.BookImage"%>
<%@page import="java.sql.Timestamp"%>
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

// vo객체 반환
BookPostVo bookPost = (BookPostVo) request.getAttribute("bookPost");
List<BookPostVo> majorInfo = (List<BookPostVo>) request.getAttribute("majorInfo");
if (bookPost == null) {
	// 객체가 없을 경우 처리
	out.println("게시글 정보를 불러올 수 없습니다.");
	return;
}

// vo 해제
int postId = bookPost.getPostId();
String postUserId = bookPost.getUserId();
String postTitle = bookPost.getPostTitle();
String postContent = bookPost.getPostContent();
String majorTag = bookPost.getMajorTag();
Timestamp createdAt = bookPost.getCreatedAt();
List<BookImage> images = bookPost.getImages();

//이미지 리스트 생성
List<BookImage> displayImages = new ArrayList<>();
if (images != null) {
	for (int i = 0; i < images.size() && i < 5; i++) {
		displayImages.add(images.get(i));
	}
}
//프로퍼티 파일 로드
Properties properties = new Properties();
try (InputStream input = application.getResourceAsStream("/WEB-INF/classes/config.properties")) {
	if (input == null) {
		throw new FileNotFoundException("프로퍼티 파일을 찾을 수 없습니다.");
	}
	properties.load(input);
} catch (IOException ex) {
	ex.printStackTrace();
}

String uploadDir = properties.getProperty("upload.dir");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 수정</title>
</head>
<body>
	<form action="<%=contextPath%>/Book/bookpostupdate.do" method="post" enctype="multipart/form-data">
		<input type="hidden" name="postId" value="<%=postId%>">
		<input type="hidden" name="userId" value="<%=userId%>">
		<table>
			<thead>
				<tr>
					<td>
						<label for="postTitle">글 제목:</label>
						<input type="text" id="postTitle" name="postTitle" value="<%=postTitle%>" required>
					</td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>
						<label for="preview-image">이미지 미리보기</label>
						<div id="preview" style="display: flex; flex-wrap: wrap;">
							<% if (images != null && !images.isEmpty()) { 
								for (BookImage image : images) {
									String imagePath = image.getImage_path();
							%>
							<img src="<%=request.getContextPath() + imagePath%>" style="width: 178px; height: 178px; margin: 2px;" />
							<% } 
							   } else { %>
							<p>이미지가 없습니다.</p>
							<% } %>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<label for="postContent">내용:</label>
						<textarea id="postContent" name="postContent" rows="5" cols="50" required><%=postContent%></textarea>
					</td>
				</tr>
			</tbody>
			<tfoot>
				<tr>
					<td>
						<label for="majorTag">학과 태그:</label>
						<select id="majorTag" name="majorTag">
							<option value="<%=majorTag%>"><%=majorTag%></option>
							<option value="일반 중고책 거래">일반 중고책 거래</option>
							<c:forEach var="major" items="${majorInfo}">
								<option value="${major.majorTag}">${major.majorTag}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<label for="image">이미지:</label>
						<input type="file" id="imageInput" name="image" accept="image/*" multiple>
					</td>
				</tr>
				<tr>
					<td>
						<input type="submit" value="수정하기">
						<button type="button" onclick="history.back();">취소</button>
					</td>
				</tr>
			</tfoot>
		</table>
	</form>
</body>
<script type="text/javascript">
	document.addEventListener('DOMContentLoaded', function() {
		const imageInput = document.getElementById('imageInput');
		const previewContainer = document.getElementById('preview');

		imageInput.addEventListener('change', function() {
			// 기존 미리보기 제거
			previewContainer.innerHTML = '';

			const files = imageInput.files;

			if (files.length === 0) {
				const noImageText = document.createElement('p');
				noImageText.textContent = '이미지가 없습니다.';
				previewContainer.appendChild(noImageText);
				return;
			}

			// 선택된 파일들을 순회하며 미리보기 생성
			for (let i = 0; i < files.length; i++) {
				const file = files[i];

				// 이미지 파일인지 확인
				if (!file.type.startsWith('image/')) {
					continue;
				}

				const reader = new FileReader();

				reader.onload = function(e) {
					const img = document.createElement('img');
					img.src = e.target.result;
					img.style.width = '178px';
					img.style.height = '178px';
					img.style.margin = '2px';
					previewContainer.appendChild(img);
				};

				reader.readAsDataURL(file);
			}
		});
	});
</script>
</html>
