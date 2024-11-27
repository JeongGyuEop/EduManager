<%@page import="Vo.BookPostReplyVo"%>
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
List<BookPostReplyVo> replies = (List<BookPostReplyVo>) request.getAttribute("replies");

//replies 리스트 내용 출력
if (replies != null && !replies.isEmpty()) {
	System.out.println("Replies List Contents:");
	for (BookPostReplyVo reply : replies) {
		System.out.println(reply); // toString() 메소드가 구현되어 있어야 합니다.

		// 또는 개별 필드를 출력하고 싶다면
		/*
		System.out.println("Reply ID: " + reply.getReplyId());
		System.out.println("User ID: " + reply.getUserId());
		System.out.println("Content: " + reply.getReplyContent());
		System.out.println("Reply Time: " + reply.getReplyTimeAt());
		System.out.println("---------------------------");
		*/
	}
} else {
	System.out.println("Replies list is null or empty.");
}

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
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(document).on('click', '.reply-update', function(event) {
		event.preventDefault();
		
		// 클릭된 버튼이 속한 form 요소의 값들을 저장합니다.
        var formElement = $(this).closest('form');
		
		console.log(formElement.find('input[name="replyId"]'),"");
		
        var replyId = formElement.find('input[name="replyId"]').val();
        var postId = formElement.find('input[name="postId"]').val();
        var replyContent = formElement.next('tr').find('#transeform').text();
        
        // 확인용 알림창
        alert("Reply ID: " + replyId + "\nPost ID: " + postId + "\nReply Content: " + replyContent);

        // 이후 로직에 저장된 값을 사용할 수 있습니다.
        console.log("Reply ID: ", replyId);
        console.log("Post ID: ", postId);
        console.log("Reply Content: ", replyContent);
		
		// 클릭된 버튼이 속한 form 요소를 삭제합니다.
        $(this).closest('form').remove();
        // 그 form 내부에 포함된 tr도 삭제합니다.
        $(this).closest('tr').remove();
	});
</script>
</head>
<body>
	<div>
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
			</thead>
			<tbody>
				<tr>
					<td colspan="4"><label for="preview-image">이미지 미리보기</label>
						<div id="preview" style="display: flex; flex-wrap: wrap;">
							<%
							if (images != null && !images.isEmpty()) {
							%>
							<%
							for (BookImage image : images) {
							%>
							<img src="<%=request.getContextPath() + image.getImage_path()%>"
								style="width: 178px; height: 178px; margin: 2px;" />
							<%
							}
							%>
							<%
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
					<td>
						<form action="<%=contextPath%>/Book/bookpostupdate.bo"
							method="get">
							<input type="hidden" value="<%=postId%>" name="postId"> <input
								type="submit" value="수정하기">
						</form>
					</td>
					<td>
						<form action="<%=contextPath%>/Book/bookpostdelete.do">
							<input type="hidden" value="<%=postId%>" name="postId"> <input
								type="submit" value="삭제하기">
						</form>
					</td>
					<%
					}
					%>
					<td>
						<!-- 목록 -->
						<form action="<%=contextPath%>/Book/bookpostboard.bo">
							<input type="hidden" value="/view_student/booktradingboard.jsp"
								name="center"> <input type="submit" value="목록">
						</form>
					</td>
				</tr>
			</tfoot>
		</table>
	</div>
	<!-- 댓글 섹션 -->
	<div id="replySection">
		<h4>댓글</h4>
		<div id="replyList">
			<c:if test="${not empty replies}">
				<div class="replies-section">
					<h4>댓글 목록</h4>
					<!-- replies 리스트를 반복 -->
					<c:forEach var="reply" items="${replies}">
						<table border="1" cellspacing="0" cellpadding="8">
							<tr>
								<td>${reply.userId}</td>
								<td>${reply.replytimeAt}</td>
							</tr>
							<form action="<%=contextPath%>/Book/bookpostreplyDelete.do"
								method="get" class="reply-update-form">
								<input type="hidden" value="${reply.replyId}" name="replyId">
								<input type="hidden" value="<%=postId%>" name="postId">
								<tr>
									<td id="transeform" colspan="3">${reply.replyContent}</td>
									<td>
										<button class="reply-update">수정</button> <input type="submit"
										value="삭제">
									</td>
								</tr>
							</form>
						</table>
					</c:forEach>
				</div>
			</c:if>
		</div>
		<!-- 댓글 입력 폼 -->
		<form action="<%=contextPath%>/Book/bookpostreply.do" method="post">
			<input type="hidden" name="postId" value="<%=postId%>"> <input
				type="text" name="userId" value="<%=userId%>" readonly>
			<textarea name="replyContent" placeholder="댓글 입력" required></textarea>
			<input type="submit" value="댓글 등록">
		</form>
	</div>
</body>
</html>
