<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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

// 이미지 리스트 생성
List<BookImage> displayImages = new ArrayList<>();
if (images != null) {
	for (int i = 0; i < images.size() && i < 5; i++) {
		displayImages.add(images.get(i));

	}
}
// 프로퍼티 파일 로드
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
<title>상세확인</title>



</head>
<body>
	<<<<<<< HEAD
	<form action="<%=contextPath%>/Book/bookPostUpload.do" method="post"
		enctype="multipart/form-data">
		<input type="hidden" name="userId" value="<%=userId%>">
		<table>
			<thead>
				<tr>

					<td><label for="postTitle">글 제목:</label><%=postTitle%></td>
				</tr>
				<tr>
					<td><label for="postUserId">작성자:</label><%=postUserId%></td>
					<td><label for="createdAt">작성일:</label><%=createdAt%></td>
				</tr>
				</tr>

			</thead>
			<tbody>
				<tr>
					<td colspan="4"><label for="preview-image">이미지 미리보기</label>
						<div id="preview" style="display: flex; flex-wrap: wrap;">
							<%
							if (images != null && !images.isEmpty()) {
								for (BookImage image : images) {
									String imagePath = image.getImage_path();
							%>
							<img src="<%=request.getContextPath() + imagePath%>"
								style="width: 178px; height: 178px; margin: 2px;" />
							<%
							}
							} else {
							%>
							<p>이미지가 없습니다.</p>
							<%
							}
							%>
						</div></td>
				</tr>

				<tr>

					<td colspan="4"><label for="postContent">내용:</label><%=postContent%></td>

				</tr>
				<tr>
					<td colspan="4" bgcolor="#f5f5f5" style="text-align: left">
						<p id="textInput"></p>
					</td>
				</tr>
			</tbody>
			<tfoot>
				<tr>

					<td><label for="majorTag">학과 태그:</label><%=majorTag%></td>


					<!-- if(userId == postUserId { -->

					<%--수정하기 --%>
					<td><input type="button" id="update" value="수정"></td>
					<%--삭제하기 --%>
					<td><input type="button" id="delete"
						onclick="javascript:deletePro('<%=postId%>');" value="삭제"></td>

					<!-- } -->

					<%--목록 --%>
					<td><input type="button" id="list" value="목록" /></td>
				</tr>
			</tfoot>
		</table>
	</form>


	<!-- 댓글 섹션 -->
	<div id="commentsSection" style="margin-top: 20px;">
		<h3>댓글</h3>

		<!-- 댓글 목록 -->
		<div id="commentList">
			<c:forEach var="comment" items="${commentList}">
				<div>
					<strong>${comment.author}</strong>
					<p>${comment.content}</p>
					<span>${comment.createdAt}</span>
				</div>
			</c:forEach>
			<c:if test="${empty commentList}">
				<p>댓글이 없습니다.</p>
			</c:if>
		</div>

		<!-- 댓글 입력 폼 -->
		<form action="${pageContext.request.contextPath}/Book/postDetail.do"
			method="post">
			<input type="hidden" name="postId" value="${postDetail.postId}">
			<input type="hidden" name="createdAt" value="${postDetail.createdAt}">
			<input type="text" name="author" placeholder="작성자" required>
			<textarea name="commentContent" placeholder="댓글 입력" required></textarea>
			<button type="submit">등록</button>
		</form>
	</div>



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


	<input type="hidden" name="userId" value="<%=userId%>">
	<table>
		<thead>
			<tr>
				<td><label for="postTitle">글 제목:</label><%=postTitle%></td>
			</tr>
			<tr>
				<td><label for="postUserId">작성자:</label><%=postUserId%></td>
				<td><label for="createdAt">작성일:</label><%=createdAt%></td>
			</tr>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td colspan="4"><label for="preview-image">이미지 미리보기</label>
					<div id="preview" style="display: flex; flex-wrap: wrap;">
						<%
						if (images != null && !images.isEmpty()) {
							for (BookImage image : images) {
								String imagePath = image.getImage_path();
						%>
						<img src="<%=request.getContextPath() + imagePath%>"
							style="width: 178px; height: 178px; margin: 2px;" />
						<%
						}
						} else {
						%>
						<p>이미지가 없습니다.</p>
						<%
						}
						%>
					</div></td>
			</tr>

			<tr>
				<td colspan="4"><label for="postContent">내용:</label><%=postContent%></td>
			</tr>
			<tr>
				<td colspan="4" bgcolor="#f5f5f5" style="text-align: left">
					<p id="textInput"></p>
				</td>
			</tr>
		</tbody>
		<tfoot>
			<tr>
				<td><label for="majorTag">학과 태그:</label><%=majorTag%></td>
				<%
				if (userId != null && userId.equals(postUserId)) {
				%>
				<!-- 작성자가 동일할 경우에만 수정/삭제 버튼 표시 -->
				<td><form action="<%=contextPath%>/Book/bookpostupdate.bo"
						method="get">
						<input type="hidden" value="<%=postId%>" name="postId"><input
							type="submit" value="수정하기">
					</form></td>
				<td><form action="<%=contextPath%>/Book/bookpostdelete.do">
						<input type="hidden" value="<%=postId%>" name="postId"><input
							type="submit" value="삭제하기">
					</form></td>
				<%
				}
				%>
				<%--목록 --%>
				<td><form action="<%=contextPath%>/Book/bookpostboard.bo">
						<input type="hidden" value="/view_student/booktradingboard.jsp"
							name="center"><input type="submit" value="목록">
					</form></td>
			</tr>
		</tfoot>
	</table>

</body>
</html>
