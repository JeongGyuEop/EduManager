
<%@page import="Vo.BoardVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
 
<%
	request.setCharacterEncoding("UTF-8");
	String contextPath = request.getContextPath();
	
	//조회한 글정보 얻기
	BoardVo vo = (BoardVo)request.getAttribute("vo");
	String author_id = vo.getAuthor_id();//조회한 글을 작성한 사람
	String title = vo.getTitle();//조회한 글제목
	String content = vo.getContent().replace("/r/n", "<br>");//조회한 글 내용
	
	String notice_id = (String)request.getAttribute("notice_id");
	String nowPage = (String)request.getAttribute("nowPage");
	String nowBlock = (String)request.getAttribute("nowBlock");
	
	
	String id = (String)session.getAttribute("id");
	String role_ = (String)session.getAttribute("role");
%>		


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>글 수정 화면</title>
</head>
<body>
	<table width="80%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td colspan="3">
				<div align="center">
					<table width="95%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="20" colspan="3"></td>
						</tr>
						<tr>
							<td height="327" colspan="3" valign="top">
								<div align="center">
									<table width="95%" height="373" border="0" cellpadding="0" cellspacing="1" class="border1">
										<tr>
											<td width="13%" height="29" bgcolor="#e4e4e4" class="text2">
												<div align="center">작 성 자</div>
											</td>
											<td width="34%" bgcolor="#f5f5f5" style="text-align: left">
												&nbsp;&nbsp; <input type="text" name="writer" id="writer" value="<%=author_id%>" disabled>
											</td>
											
										</tr>
										<tr>
											<td height="31" bgcolor="#e4e4e4" class="text2">
												<div align="center">제&nbsp;&nbsp;&nbsp; &nbsp; 목</div>
											</td>
											<td colspan="3" bgcolor="#f5f5f5" style="text-align: left">
												&nbsp;&nbsp; <input type="text" name="title" id="title" value="<%=title%>">
											</td>
										</tr>
										<tr>
											<td height="245" bgcolor="#e4e4e4" class="text2">
												<div align="center">내 &nbsp;&nbsp; 용</div>
											</td>
											<td colspan="3" bgcolor="#f5f5f5" style="text-align: left; vertical-align: top;">
												&nbsp; <textarea rows="20" cols="100" name="content" id="content"><%=content%></textarea>
											</td>
										</tr>
										<tr>
											<td bgcolor="#e4e4e4" class="text2">
												<div align="center"></div>
											</td>
											<td colspan="2" bgcolor="#f5f5f5" style="text-align: left">
												<input type="hidden" name="role" id="role" value="<%=role_%>" />
											</td>
											<td colspan="2" bgcolor="#f5f5f5" style="text-align: left">
												<p id="pwInput"></p>
											</td>
										</tr>
									</table>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="3">&nbsp;</td>
						</tr>
						<tr>
							<td style="width: 48%">
								<div align="right" id="menuButton" >
								<%--수정하기 --%>	
									<input type="button" id="update" value="수정" style="visibility:visible;"/>&nbsp;&nbsp; 
								<%--삭제하기 --%>	
									<input type="button" id="delete" onclick="javascript:deletePro('<%=notice_id%>');" value="삭제" style="visibility:visible;"/>&nbsp;&nbsp; 
								<%--답변달기 --%>
									<input type="button" id="reply" value="답변" style="visibility:visible;"/>&nbsp;&nbsp;
								</div>
							</td>
							<td width="10%">
								<div align="center">
									<%--목록 이미지 버튼 --%>
									<input type="button" id="list" value="목록" />&nbsp;
								</div>
							</td>
							<td width="42%"></td>
						</tr>
						<tr>
							<td colspan="3" style="height: 19px">&nbsp;</td>
						</tr>
					</table>
				</div>
			</td>
		</tr>
	</table>
	
	<%-- 답변 버튼을 클릭했을때 답변을 작성할수 있는 화면 요청! --%>
	<form id="replyForm"  action="<%=contextPath%>/Board/reply.do">
	
		<%--주글 의 글번호 전달 --%>
		<input type="hidden" name="notice_id" 
							 value="<%=notice_id%>" 
							 id="notice_id">
		<%--답변글을 작성하는 사람의 로그인된 아이디를 전달--%>					 
		<input type="hidden" name="id" value="<%=id%>">					 
	
	</form>
	<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
	<script>
		//답변 버튼을 선택해서 가져와 클릭이벤트 등록
		//-> 답변 버튼을 클릭했을때
		$("#reply").on("click", function(){
			
			//<form id="replyForm"></form>한쌍을 선택해서 
			//답변글을 작성하는 화면 요청!
			$("#replyForm").submit();
			
		});
	
	
	
	
		//삭제 <input type="img" onclick>를 클릭하면
	  	//호출되는 함수 
		function deletePro(notice_id){
			
		 	let result	 = window.confirm("정말로 글을 삭제하시겠어요?");
							//[확인][취소] 버튼이 보이는 한번 물어보는 팝업창을 띄워
							//[확인]버튼을 누르면 true반환
							//[취소]버튼을 누르면 false반환
		 	
			if(result==true){//[확인]버튼 누름 //글삭제한다는 의미
				
				$.ajax({
					url:"<%=contextPath%>/Board/deleteBoard.do",
					type:"post",
					data:{  notice_id : notice_id  },
					dataType:"text",
					success:function(data){
									//"삭제성공" 또는 "삭제실패"
						if(data=="삭제성공"){
							alert("삭제성공");
							
							//수정시 입력할수 있는 <input>2개 , <textarea>1개 비활성화
							document.getElementById("writer").disabled = true;
					 		document.getElementById("title").disabled = true;
					 		document.getElementById("content").disabled = true;	
					 		
					 		//id속성이 list인 "목록"<input type="image">를 선택해
					 		//click이벤트를 강제로 발생시키게 하여
					 		//글목록조회 재요청을 하여 보여주자 
				 		//$("#list").trigger("click");
					 		
					 		//글삭제된 후  2 초 휴식한 뒤에 글 목록 조회 요청 강제로 하자 
					 		setTimeout(function(){
					 			
					 			//강제로 "목록" <input> 클릭이벤트 발생 
					 			$("#list").trigger("click");
					 			
					 		}, 2000);
					 				
							
						}else{//"삭제실패"
							
							$("#pwInput").text("삭제실패!").css("color","red");
							document.getElementById("writer").disabled = false;
					 		document.getElementById("title").disabled = false;
					 		document.getElementById("content").disabled = false;
							
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
			let author_id = $("#author_id").val();
			let title = $("#title").val();
			let content = $("#content").val();
			let notice_id = $("#notice_id").val(); //글번호 얻기 
			
			$.ajax({
					url:"<%=contextPath%>/Board/updateBoard.do",
					type:"post",
					async:true,
					data:{          
						   author_id : author_id,
						   title : title,
						   content : content,
						   notice_id : notice_id
					},
					dataType:"text",
					success:function(data){
								    //"수정성공" or "수정실패"
						//수정에 성공하면
						if(data === "수정성공"){
							//<p id="pwInput"></p>요소를 선택해서
							//텍스트노드 자리에 "수정성공" 메세지 녹색으로 보여주자
							$("#pwInput").html("<strong>수정성공</strong>")
										 .css("color","green");
							
							//수정시 입력할수 있는 <input>2개 , <textarea>1개 비활성화
							document.getElementById("writer").disabled = true;
					 		document.getElementById("title").disabled = true;
					 		document.getElementById("content").disabled = true;
					 		
						}else{//수정에 실패하면
							//<p id="pwInput"></p>요소를 선택해서
							//텍스트노드 자리에 "수정실패" 메세지 빨간색으로 보여주자
							$("#pwInput").html("<strong>수정 실패</strong>")
										 .css("color","red");
							
							//수정시 입력할수 있는 <input>2개 , <textarea>1개 활성화
							document.getElementById("writer").disabled = false;
					 		document.getElementById("title").disabled = false;
					 		document.getElementById("content").disabled = false;
						}
								    
						
					},
					error : function(){
						alert("수정 요청시 비동기 통신 장애");
					}	
			});
		});
	
		
		// 관리자인지 확인하여 수정,삭제 버튼 제목,내용 입력창 활성화 , 비활성화 
		 $(document).ready(function() {
	            // role 값이 관리자일 때만 수정 및 삭제 버튼을 보이게 설정
	            const role = "<%= role_ %>";
	            
	            if (role === "관리자") {
	                $("#update").css("visibility", "visible");
	                $("#delete").css("visibility", "visible");
	                $("#reply").css("visibility", "visible");
	            } else {
	                $("#update").css("visibility", "hidden");
	                $("#delete").css("visibility", "hidden");
	                $("#reply").css("visibility", "hidden");

	                // 제목과 내용 입력창 비활성화
	                $("#title").prop("disabled", true);
	                $("#content").prop("disabled", true);
	            }
	        });
		 
		
// 목록 버튼 클릭시 role 값 비교하여 각자에 맞는 jsp로 넘어감 
	     $(document).ready(function () {
	         // 조건에 따라 URL 설정
	         const role = "<%= role_ %>";
	         const contextPath = "<%= contextPath %>";
	         const nowBlock = "<%= nowBlock %>";
	         const nowPage = "<%= nowPage %>";
	         
	         // 목록 버튼 클릭 이벤트 등록
	         $("#list").on("click", function () {
	             let url;
	             
	             if (role === "관리자") {
	                 url = "<%=contextPath%>/Board/list.bo?center=/view_admin/noticeManage.jsp&nowBlock=<%=nowBlock%>&nowPage=<%=nowPage%>";
	             } else {
	                 url = "<%=contextPath%>/Board/list.bo?center=/common/notice/list.jsp&nowBlock=<%=nowBlock%>&nowPage=<%=nowPage%>";
	             } 
	             
	             // 설정된 URL로 이동
	             window.location.href = url;
	         });
	     });
	</script>
	
	
</body>
</html>
















