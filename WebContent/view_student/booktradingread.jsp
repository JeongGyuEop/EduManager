
<%@page import="Vo.CommentVo"%>
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

	 BookPostVo postDetail = (BookPostVo) request.getAttribute("postDetail");
	 CommentVo comment = (CommentVo) request.getAttribute("commentList");
%>

<%

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
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상세확인</title>


 <%-- <script type="text/javascript">
        window.onload = function () {
            // JSP에서 메시지 값을 JavaScript 변수로 가져오기
            var message = "<%= request.getAttribute("message") %>";
            if (message && message !== "null") {
                alert(message); // 메시지가 있을 경우에만 alert 창 표시
            }
        };
        

        document.addEventListener("DOMContentLoaded", function () {
            // 메시지 알림 처리
            var message = "<%= request.getAttribute("message") %>";
            if (message && message !== "null") {
                alert(message);
            } 
    </script> --%>
    

</head>
<body>
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
			<!-- <tr>
					<td colspan="4"><label for="preview-image">이미지 미리보기</label>
						<div id="preview" style="display: flex; flex-wrap: wrap;"></div></td>
				</tr> -->
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
						<%--수정하기 --%>	
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
    <form action="${pageContext.request.contextPath}/Book/postDetail.do" method="post">
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
		
		
	<%--	
		//삭제 <input type="img" onclick>를 클릭하면
	  	//호출되는 함수 
		function deletePro(userId){
			
		 	let result	 = window.confirm("정말로 글을 삭제하시겠어요?");
							//[확인][취소] 버튼이 보이는 한번 물어보는 팝업창을 띄워
							//[확인]버튼을 누르면 true반환
							//[취소]버튼을 누르면 false반환
		 	
			if(result==true){//[확인]버튼 누름 //글삭제한다는 의미
				
				$.ajax({
					url:"<%=contextPath%>/Book/deleteBoard.do",
					type:"post",
					data:{  userId : userId  },
					dataType:"text",
					success:function(data){
									//"삭제성공" 또는 "삭제실패"
						if(data=="삭제성공"){
							alert("삭제성공");
							
							//수정시 입력할수 있는 <input>3개 , <textarea>1개 비활성화
							document.getElementById("postTitle").disabled = true;
					 		document.getElementById("stuname").disabled = true;
					 		document.getElementById("stutime").disabled = true;	
					 		document.getElementById("postContent").disabled = true;	
					 		
					 		
					 		//글삭제된 후  2 초 휴식한 뒤에 글 목록 조회 요청 강제로 하자 
					 		setTimeout(function(){
					 			
					 			//강제로 "목록" <input> 클릭이벤트 발생 
					 			$("#list").trigger("click");
					 			
					 		}, 2000);
					 				
							
						}else{//"삭제실패"
							
							$("#textInput").text("삭제실패!").css("color","red");
							document.getElementById("postTitle").disabled = false;
					 		document.getElementById("stuname").disabled = false;
					 		document.getElementById("stutime").disabled = false;
					 		document.getElementById("postContent").disabled = false;
							
						}
					},
					error:function(){  alert('삭제 요청 비동기 통신 장애');  }
					
				});
		 		
				
		 	}else{//[취소]버튼을 누름
		 		
		 		return false;
		 	}		
				
		}
	
	
	
		//글 수정내용을 모두 입려하고 수정 이미지 버튼을 클릭했을때
		$("#update").click(function(){			
			//수정시 입력한 이메일, 글제목, 글내용
			let author_id = $("#userId").val();
			let title = $("#postTitle").val();
			let content = $("#postContent").val();
			let notice_id = $("#notice_id").val(); //글번호 얻기 
			
			$.ajax({
					url:"<%=contextPath%>/Book/updateBoard.do",
					type:"post",
					async:true,
					data:{          
						   userId : userId,
						   postTitle : postTitle,
						   postContent : postContent,
						   post_id : post_id
					},
					dataType:"text",
					success:function(data){
								    //"수정성공" or "수정실패"
						//수정에 성공하면
						if(data === "수정성공"){
							//<p id="pwInput"></p>요소를 선택해서
							//텍스트노드 자리에 "수정성공" 메세지 녹색으로 보여주자
							$("#textInput").html("<strong>수정성공</strong>")
										 .css("color","green");
							
							//수정시 입력할수 있는 <input>3개 , <textarea>1개 비활성화
							document.getElementById("postTitle").disabled = true;
					 		document.getElementById("stuname").disabled = true;
					 		document.getElementById("stutime").disabled = true;	
					 		document.getElementById("postContent").disabled = true;	
					 		
						}else{//수정에 실패하면
							//<p id="pwInput"></p>요소를 선택해서
							//텍스트노드 자리에 "수정실패" 메세지 빨간색으로 보여주자
							$("#textInput").html("<strong>수정 실패</strong>")
										 .css("color","red");
							
							//수정시 입력할수 있는 <input>3개 , <textarea>1개 활성화
							document.getElementById("postTitle").disabled = true;
					 		document.getElementById("stuname").disabled = true;
					 		document.getElementById("stutime").disabled = true;	
					 		document.getElementById("postContent").disabled = true;	
						}
								    
						
					},
					error : function(){
						alert("수정 요청시 비동기 통신 장애");
					}	
			});
		});
		

--%>





<%-- 		
		//댓글등록
		$.ajax({
    url: "<%=contextPath%>/Book/replypro.do",
    type: "POST",
    data: { postId: postId, author: author, content: content },
    success: function(response) {
        console.log("댓글 등록 성공:", response);
        location.reload(); // 성공 시 페이지 새로고침
    },
    error: function(error) {
        console.log("댓글 등록 실패:", error);
    }
}); --%>

		
		
		
		
		
		
	</script>


</body>
</html>
