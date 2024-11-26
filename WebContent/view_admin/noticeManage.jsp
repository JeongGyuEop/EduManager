
<%@page import="java.net.URLEncoder"%>
<%@page import="Vo.BoardVo"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String contextPath = request.getContextPath();

   	String key = (String)request.getAttribute("key");
   	String word = (String)request.getAttribute("word");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>

<style>
    /* 전체 페이지 스타일 */
    body {
        font-family: 'Arial', sans-serif; /* 기본 폰트 설정 */
        background-color: #f8f9fa; /* 밝은 회색 배경 */
        margin: 0;
        padding: 20px; /* 페이지 내부 여백 */
        line-height: 1.6; /* 텍스트 줄 간격 */
    }

    /* 제목 스타일 */
    h1, h2 {
        text-align: center; /* 제목을 가운데 정렬 */
        color: #343a40; /* 짙은 회색 텍스트 */
        margin: 20px 0; /* 위아래 여백 */
        font-size: 24px; /* 폰트 크기 */
        font-weight: bold; /* 굵은 글씨 */
    }

    /* 테이블 스타일 */
    table {
        width: 95%; /* 테이블 너비 */
        margin: 20px auto; /* 상하 여백, 가운데 정렬 */
        border-collapse: collapse; /* 테이블 셀 간격 제거 */
        background-color: white; /* 테이블 배경 */
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); /* 그림자 효과 */
        border-radius: 5px; /* 모서리를 둥글게 */
    }

    th, td {
        padding: 12px 15px; /* 셀 내부 여백 */
        text-align: center; /* 텍스트 가운데 정렬 */
        border-bottom: 1px solid #dee2e6; /* 아래쪽 테두리 */
    }

    th {
        background-color: #198754; /* 초록색 배경 */
        color: white; /* 흰색 텍스트 */
        font-size: 16px; /* 큰 텍스트 */
        font-weight: bold; /* 굵은 글씨 */
    }

    td {
        font-size: 14px; /* 일반 텍스트 크기 */
        color: #495057; /* 짙은 회색 */
    }

    tr:nth-child(even) {
        background-color: #f8f9fa; /* 짝수 행 배경 */
    }

    tr:hover {
        background-color: #e9ecef; /* 마우스 오버 시 배경색 */
    }

    /* 검색 영역 스타일 */
    .search-bar {
        display: flex; /* 플렉스 레이아웃 */
        justify-content: center; /* 가운데 정렬 */
        align-items: center; /* 세로 정렬 */
        margin: 20px auto; /* 여백 */
        width: 70%; /* 검색 영역 너비 */
    }

    .search-bar select,
    .search-bar input[type="text"] {
        font-size: 14px; /* 입력 필드 텍스트 크기 */
        padding: 8px; /* 필드 내부 여백 */
        margin-right: 10px; /* 필드 간 간격 */
        border: 1px solid #ced4da; /* 필드 테두리 색상 */
        border-radius: 5px; /* 모서리를 둥글게 */
    }

    .search-bar input[type="submit"],
    .search-bar button {
        background-color: #007bff; /* 파란색 버튼 */
        color: white; /* 흰색 텍스트 */
        font-size: 14px; /* 버튼 텍스트 크기 */
        padding: 8px 16px; /* 버튼 내부 여백 */
        border: none; /* 버튼 테두리 제거 */
        border-radius: 5px; /* 모서리를 둥글게 */
        cursor: pointer; /* 클릭 가능 커서 */
    }

    .search-bar input[type="submit"]:hover,
    .search-bar button:hover {
        background-color: #0056b3; /* 버튼 호버 시 색상 */
    }

    /* 페이지네이션 스타일 */
    .pagination {
        display: flex; /* 플렉스 레이아웃 */
        justify-content: center; /* 가운데 정렬 */
        margin: 20px auto; /* 여백 */
    }

    .pagination a {
        font-size: 14px; /* 텍스트 크기 */
        color: #198754; /* 초록색 */
        text-decoration: none; /* 밑줄 제거 */
        padding: 8px 12px; /* 링크 내부 여백 */
        margin: 0 5px; /* 링크 간 간격 */
        border: 1px solid #dee2e6; /* 테두리 색상 */
        border-radius: 5px; /* 모서리를 둥글게 */
        background-color: #ffffff; /* 흰색 배경 */
    }

    .pagination a:hover {
        background-color: #198754; /* 호버 시 초록색 배경 */
        color: white; /* 흰색 텍스트 */
    }

    /* 답글 들여쓰기 스타일 */
    .reply-indent {
        display: flex; /* 플렉스 레이아웃 */
        align-items: center; /* 세로 정렬 */
    }

    /* 버튼 스타일 */
    button,
    input[type="button"] {
        background-color: #007bff; /* 파란색 배경 */
        color: white; /* 흰색 텍스트 */
        font-size: 14px; /* 텍스트 크기 */
        font-weight: bold; /* 굵은 글씨 */
        padding: 10px 20px; /* 버튼 내부 여백 */
        border: none; /* 테두리 제거 */
        border-radius: 5px; /* 모서리를 둥글게 */
        cursor: pointer; /* 클릭 가능 커서 */
    }

    button:hover,
    input[type="button"]:hover {
        background-color: #0056b3; /* 호버 시 색상 */
    }

    /* 읽기 전용 필드 스타일 */
    input[readonly] {
        background-color: #e9ecef; /* 연한 회색 배경 */
        color: #6c757d; /* 짙은 회색 텍스트 */
    }

    /* 에러 메시지 스타일 */
    #pwInput {
        color: red; /* 빨간색 텍스트 */
        text-align: center; /* 가운데 정렬 */
        margin-top: 10px; /* 위쪽 여백 */
        font-weight: bold; /* 굵은 글씨 */
    }
</style>


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
    	document.frmRead.action="<%=contextPath%>/Board/read.bo";
		document.frmRead.notice_id.value = val;
		document.frmRead.submit();//<form> 을 이용해 요청
	}
</script>
</head>
<body>
	<%
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

		//BoardController에서 request에 바인딩 한 ArrayList배열을 꺼내옵니다
		//조회된 글목록 정보 얻기 
		ArrayList<BoardVo> list = (ArrayList<BoardVo>) request.getAttribute("list");

		//조회된 글 총 갯수 
		totalRecord = list.size();

		//게시판 아래쪽 페이지 번호 중 하나를 클릭했다면?
		if (request.getAttribute("nowPage") != null) {
			//클릭한 페이지번호를 얻어 저장
			nowPage = Integer.parseInt(request.getAttribute("nowPage").toString());
		}

		//각 페이지에 보여질 시작글번호 구하기
		beginPerPage = nowPage * numPerPage;
		//자유게시판 메뉴 클릭 또는 아래 하단의 페이지번호 중 1페이지 번호 클릭시!!!
		// 0      *     5       =   0 index

		//아래 하단의 페이지번호 중 2페이지 번호 클릭시!!! 
		// 1      *     5       =    5 index

		//각 페이지마다 
		//조회되어 보여지는 시작 행의  index위치 번호
		//(가장 위의 조회된 레코드 행의 시작 index위치번호) 저장  

		//글이 몇개 인지에 따른 총 페이지 번호 갯수 구하기
		//총 페이지번호 갯수 = 총 글의 갯수 / 한 페이지당 보여질 글목록 갯수 
		totalPage = (int) Math.ceil((double) totalRecord / numPerPage);
		//33.0         /     5
		//			6.6
		//         7.0
		//         7
		//총 페이지 번호 갯수에 따른 총 블럭 갯수 구하기 
		totalBlock = (int) Math.ceil((double) totalPage / pagePerBlock);
		//	        7.0  /  한 블럭당 묶여질 페이지 번호 갯수  3
		//	     2.333333333333333
		//       3.0
		//       3
		//게시판 아래쪽에 클릭한 페이지 번호가 속한 불럭 위치 번호 구하기
		if (request.getAttribute("nowBlock") != null) {

			//BoardController에서 request에 바인딩된 값을 다시 얻어 저장 
			nowBlock = Integer.parseInt(request.getAttribute("nowBlock").toString());
		}

		String id = (String) session.getAttribute("id");
	%>


	<%--글제목 하나를 클릭했을떄 BoardController 글 하나 조회 요청하기 위한 폼
	위 자바스크립트 function fnRead함수에서 사용하는 <form>
 --%>
	<form name="frmRead">
		<input type="hidden" name="notice_id"> 
		<input type="hidden" name="nowPage" value="<%=nowPage%>"> 
		<input type="hidden" name="nowBlock" value="<%=nowBlock%>">
	</form>


	<!-- 게시판 테이블 -->
	<table>
	    <thead>
	        <tr>
	            <th>번호</th>
	            <th>제목</th>
	            <th>내용</th>
	            <th>작성자</th>
	            <th>날짜</th>
	        </tr>
	    </thead>
	    <tbody>
	        <% if (list.isEmpty()) { %>
	        <tr>
	            <td colspan="5">등록된 글이 없습니다.</td>
	        </tr>
	        <% } else {
	            for (int i = beginPerPage; i < (beginPerPage + numPerPage); i++) {
	                if (i == totalRecord) break;
	                BoardVo vo = list.get(i);
	        %>
	        <tr>
	            <td><%=vo.getNotice_id()%></td>
	            <td>
	                <div class="reply-indent">
	                   <%
						    int width = 0; // 답변글에 대한 이미지 들여쓰기 너비값
						    if (vo.getB_level() > 0) { // 답글인 경우
						        width = vo.getB_level() * 10; // 들여쓰기 너비 계산
						%>
						    <img src="<%=contextPath%>/common/notice/images/level.gif" width="<%=width%>" height="15">
						    <img src="<%=contextPath%>/common/notice/images/re.gif">
						<% } %>
	
	                    <a href="javascript:fnRead('<%=vo.getNotice_id()%>')"><%=vo.getTitle()%></a>
	                </div>
	            </td>
	            <td><%=vo.getContent()%></td>
	            <td><%=vo.getUserName().getUser_name()%></td>
	            <td><%=vo.getCreated_date()%></td>
	        </tr>
	        <% } } %>
	    </tbody>
	</table>
	
	<!-- 검색 영역 -->
	<div class="search-bar">
	    <form action="<%=contextPath%>/Board/searchlist.bo" method="post" name="frmSearch" onsubmit="fnSearch(); return false;">
	        <select name="key">
	            <option value="titleContent">제목 + 내용</option>
	            <option value="name">작성자</option>
	        </select>
	        <input type="text" name="word" id="word">
	        <input type="submit" value="검색">
	    </form>
	    &nbsp;&nbsp;&nbsp;&nbsp;
	    <button id="newContent" onclick="location.href='<%=contextPath%>/Board/write.bo?nowPage=<%=nowPage%>&nowBlock=<%=nowBlock%>'">새 글쓰기</button>
	</div>

	<!-- 페이지네이션 -->
	<div class="pagination">
	    <% 
	    String searchParams = "";
	    if (key != null && word != null) {
	        searchParams = "&key=" + key + "&word=" + URLEncoder.encode(word, "UTF-8");
	    }	
	    if (totalRecord != 0) {
	        if (nowBlock > 0) { %>
	    <a href="<%=contextPath%>/Board/list.bo?center=/view_admin/noticeManage.jsp&nowBlock=<%=nowBlock - 1%>&nowPage=<%=((nowBlock - 1) * pagePerBlock)%><%=searchParams%>">◀ 이전</a>
	    <% }
	        for (int i = 0; i < pagePerBlock; i++) {
	            int pageNum = (nowBlock * pagePerBlock) + i + 1;
	            if (pageNum > totalPage) break; %>
	    <a href="<%=contextPath%>/Board/list.bo?center=/view_admin/noticeManage.jsp&nowBlock=<%=nowBlock%>&nowPage=<%=pageNum - 1%><%=searchParams%>"><%=pageNum%></a>
	    <% }
	        if (totalBlock > nowBlock + 1) { %>
	    <a href="<%=contextPath%>/Board/list.bo?center=/view_admin/noticeManage.jsp&nowBlock=<%=nowBlock + 1%>&nowPage=<%=(nowBlock + 1) * pagePerBlock%><%=searchParams%>">▶ 다음</a>
	    <% } } %>
	</div>


</body>
</html>












