<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="Vo.BookPostVo"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	request.setCharacterEncoding("UTF-8");
	String contextPath = request.getContextPath();

	//페이징 처리를 위한 변수 선언

	int totalRecord = 0;//board테이블에 저장된  조회된 총 글의 갯수 저장  *
	int numPerPage = 5; //한 페이지 번호당 조회해서 보여줄 글 목록 개수 저장
	int pagePerBlock = 3; //한 블럭당 묶여질 페이지 번호 갯수 저장
	//예)   1   2    3   <-  한블럭으로 묶음 

	int totalPage = 0; // 총 페이지 번호 갯수(총페이지 갯수) *
	int totalBlock = 0; // 총 페이지 번호 갯수에 따른 총블럭 갯수  *
	int nowPage = 0; //현재 클라이언트 화면에 보여지고 있는 페이지가 위치한 번호 저장
	//요약 : 아래 쪽 페이지번호 1 2  3  중에서 클릭한  현재 페이지번호 저장
	//*

	int nowBlock = 0; //클릭한 페이지번호가 속한 블럭위치 번호 저장  *

	int beginPerPage = 0; //각 페이지마다 
	//조회되어 보여지는 시작 행의  index위치 번호
	//(가장 위의 조회된 레코드 행의 시작 index위치번호) 저장  
	//*

	// id 구하기
	String userId = (String) session.getAttribute("id");

	// BoardController에서 request에 바인딩 한 ArrayList배열을 꺼내옵니다
	// 조회된 글목록 정보 얻기 
	List<BookPostVo> bookBoardList = (List<BookPostVo>) request.getAttribute("bookBoardList");

	//조회된 글 총 갯수 
	totalRecord = bookBoardList.size();

	//게시판 아래쪽 페이지 번호 중 하나를 클릭했다면?
	if (request.getAttribute("nowPage") != null) {
		//클릭한 페이지번호를 얻어 저장
		nowPage = Integer.parseInt(request.getAttribute("nowPage").toString());
	}

	//각 페이지에 보여질 시작글번호 구하기
	beginPerPage = nowPage * numPerPage;
	//자유게시판 메뉴 클릭 또는 아래 하단의 페이지번호 중 1페이지 번호 클릭시!!!
	//0      *     5       =   0 index

	//아래 하단의 페이지번호 중 2페이지 번호 클릭시!!! 
	//1      *     5       =    5 index

	//각 페이지마다 
	//조회되어 보여지는 시작 행의  index위치 번호
	//(가장 위의 조회된 레코드 행의 시작 index위치번호) 저장  

	//글이 몇개 인지에 따른 총 페이지 번호 갯수 구하기
	//총 페이지번호 갯수 = 총 글의 갯수 / 한 페이지당 보여질 글목록 갯수 
	totalPage = (int) Math.ceil((double) totalRecord / numPerPage);
	//33.0         /     5
	//			6.6
	//       7.0
	//       7
	//총 페이지 번호 갯수에 따른 총 블럭 갯수 구하기 
	totalBlock = (int) Math.ceil((double) totalPage / pagePerBlock);
	//	        7.0  /  한 블럭당 묶여질 페이지 번호 갯수  3
	//	     2.333333333333333
	//     3.0
	//     3
	//게시판 아래쪽에 클릭한 페이지 번호가 속한 불럭 위치 번호 구하기
	if (request.getAttribute("nowBlock") != null) {

		//BoardController에서 request에 바인딩된 값을 다시 얻어 저장 
		nowBlock = Integer.parseInt(request.getAttribute("nowBlock").toString());
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>목록조회</title>
<link rel="stylesheet" type="text/css" href="/MVCBoard/style.css" />

<script type="text/javascript">

    // 아래의 검색어를 입력하지 않고  검색버튼을 눌렀을때
    // 검색어 입력하지 않으면  검색어를 입력하세요!  체크 하는 함수 
	function fnSearch(){
    	
    	//입력한 검색어 얻기 
		var word = document.getElementById("word").value;
		
    	//검색어를 입력하지 않았다면?
		if(word == null || word == ""){
			//검색어 입력 메세지창 띄우기 
			alert("검색어를 입력하세요.");
			//검색어를 입력 하는 <input>에 강제 포커스를 주어 검색어를 입력하게 유도함.
			document.getElementById("word").focus();
			
			//<form>의 action속성에 작성된 BoardController서버페이지 요청을 차단 
			return false;
		}
		else{//검색어를 입력했다면
			
			//<form>을 선택해서 가져와 action속성에 작성된 주소를 이용해
			//BoardController로 입력한 검색어에 관한 글목록 조회 요청을 함 
			document.frmSearch.submit();
		}
	}
	
    //조회된 화면에서  글제목 하나를 클릭했을때  글번호를 매개변수로 받아서
    //아래에 작성된 <form>를 이용해 글번호에 해당되는 글 하나의 정보를 조회 요청!
    function fnRead(val){
<<<<<<< HEAD
    	document.frmRead.action="<%=contextPath%>/view_student/booktradingread.jsp";
		document.frmRead.notice_id.value = val;
=======
    	var values = val.split(",");
        var postId = values[0];
    	
    	document.frmRead.action="<%=contextPath%>/Book/bookread.bo";
    	
		document.frmRead.postId.value = postId;
	    
>>>>>>> ef10ca62b5222e7b5fb5063d644940ee2df320fd
		document.frmRead.submit();//<form> 을 이용해 요청
	}
</script>

</head>
<body>
	<%--글제목 하나를 클릭했을떄 BoardController 글 하나 조회 요청하기 위한 폼
	위 자바스크립트 function fnRead함수에서 사용하는 <form>
 --%>
	<form name="frmRead">
		<input type="hidden" name="postId">
	</form>
	<table width="97%" border="0" cellspacing="0" cellpadding="0">

		<tr>
			<td colspan="3" valign="top">
				<div align="center">
					<table width="95%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td colspan="4" style="height: 19px">&nbsp;</td>
						</tr>
						<tr>
							<td colspan="4">
								<table border="0" width="100%" cellpadding="2" cellspacing="0">
									<tr align="center" bgcolor="#D0D0D0" height="120%">
										<td align="left">번호</td>
										<td align="left">제목</td>
										<td align="left">작성자</td>
										<td align="left">학과태그</td>
										<td align="left">날짜</td>
									</tr>
									<%
										// 리스트가 비어 있지 않으면 반복문을 통해 출력
										if (bookBoardList != null && !bookBoardList.isEmpty()) {
											int start = beginPerPage;
											int end = Math.min(beginPerPage + numPerPage, totalRecord);
											for (int i = start; i < end; i++) {
												BookPostVo listinput = bookBoardList.get(i);
									%>
									<tr>
										<!-- 번호 -->
										<td align="left"><%=listinput.getPostId()%></td>
										<!-- 제목 (링크 클릭 시 fnRead 함수 호출) -->
										<td><a
											href="javascript:fnRead('<%=listinput.getPostId()%>')"><%=listinput.getPostTitle()%></a></td>
										<!-- 작성자 -->
										<td align="left"><%=listinput.getUserId()%></td>
										<!-- 학과태그 -->
										<td align="left"><%=listinput.getMajorTag()%></td>
										<!-- 날짜 -->
										<td align="left"><%=listinput.getCreatedAt()%></td>
									</tr>
									<%
										}
										} else {
									%>
									<tr align="center">
										<td colspan="5">등록된 글이 없습니다.</td>
									</tr>
									<%
										}
									%>
								</table>
							</td>
						</tr>
						<tr>
							<td colspan="4">&nbsp;</td>
						</tr>
						<tr>
							<td colspan="4">&nbsp;</td>
						</tr>
						<tr>
							<form action="<%=contextPath%>/Book/booksearchlist.bo"
								method="post" name="frmSearch"
								onsubmit="fnSearch(); return false;">
								<td colspan="2">
									<div align="right">
										<select name="key">
											<option value="titleContent">제목 + 내용</option>
											<option value="name">작성자</option>
										</select>
									</div>
								</td>
								<td width="26%">
									<div align="center">
										&nbsp; <input type="text" name="word" id="word" /> <input
											type="submit" value="검색" />
									</div>
								</td>
							</form>
						</tr>
						<%-- 새 글쓰기 버튼 이미지 --%>
						<td width="38%" style="text-align: left">



							<form action="<%=contextPath%>/Book/bookPostUpload.bo"
								method="post">
								<input type="hidden" value="<%=userId%>" name="userId">
								<input type="submit" value="글 쓰기">
							</form>

						</td>
						<tr>
							<td colspan="4">&nbsp;</td>
						</tr>
					</table>
				</div>
			</td>
		</tr>
		<tr align="center">
			<td colspan="3" align="center">Go To Page <%
				if (totalRecord != 0) {//DB의 board테이블에서 조회된 글이 있다면?

					if (nowBlock > 0) {
			%> <a
				href="<%=contextPath%>/Book/booktradingboard.bo?nowBlock=<%=nowBlock - 1%>&nowPage=<%=((nowBlock - 1) * pagePerBlock)%>&center=/view_student/booktradingboard.jsp">
					◀ 이전 <%=pagePerBlock%>개
			</a> <%
 	}

 		//페이지 번호를 반복해서 3개씩 보여 주자 
 		for (int i = 0; i < pagePerBlock; i++) {
 			int pageNumber = (nowBlock * pagePerBlock) + i + 1;
 			if (pageNumber > totalPage) {
 				break;
 			}
 %> &nbsp;&nbsp; <a
				href="<%=contextPath%>/Book/booktradingboard.bo?nowBlock=<%=nowBlock%>&nowPage=<%=(nowBlock * pagePerBlock) + i%>&center=/view_student/booktradingboard.jsp">
					<%=(nowBlock * pagePerBlock) + i + 1%>
			</a> &nbsp;&nbsp; <%
 	} //for 닫기

 		if (totalBlock > nowBlock + 1) {
 %> <a
				href="<%=contextPath%>/Book/booktradingboard.bo?nowBlock=<%=nowBlock + 1%>&nowPage=<%=(nowBlock + 1) * pagePerBlock%>&center=/view_student/booktradingboard.jsp">
					▶ 다음 <%=pagePerBlock%>개
			</a> <%
 	}

 	} //바깥쪽 if닫기
 %>
			</td>
		</tr>
	</table>
</body>
</html>



