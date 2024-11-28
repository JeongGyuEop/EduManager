<%@page import="java.net.URLEncoder"%>
<%@page import="Vo.ClassroomBoardVo"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String contextPath = request.getContextPath();
	String course_id = (String)request.getAttribute("course_id");
	String role_ = (String)session.getAttribute("role");
	String key = (String)request.getAttribute("key");
	String word = (String)request.getAttribute("word");

%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>교수 공지사항</title>
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
    function fnSearch() {
        var word = document.getElementById("word").value;

        if (word == null || word == "") {
            alert("검색어를 입력하세요.");
            document.getElementById("word").focus();
            return false;
        } else {
            document.frmSearch.submit();
        }
    }

    function fnRead(val) {
        document.frmRead.action = "<%=contextPath%>/classroomboard/noticeRead.bo";
        document.frmRead.notice_id.value = val;
        document.frmRead.submit();
    }

    $(document).ready(function () {
        var role = "<%=role_ %>";
        if (role === "교수") {
            $("#newContent").css("visibility", "visible");
        } else {
            $("#newContent").css("visibility", "hidden");
        }
    });
</script>
</head>
<body>

<%
    int totalRecord = 0;
    int numPerPage = 5;
    int pagePerBlock = 3;
    int totalPage = 0;
    int totalBlock = 0;
    int nowPage = 0;
    int nowBlock = 0;
    int beginPerPage = 0;

    ArrayList<ClassroomBoardVo> list = (ArrayList<ClassroomBoardVo>)request.getAttribute("list");
    totalRecord = list.size();

    if (request.getAttribute("nowPage") != null) {
        nowPage = Integer.parseInt(request.getAttribute("nowPage").toString());
    }

    beginPerPage = nowPage * numPerPage;
    totalPage = (int) Math.ceil((double) totalRecord / numPerPage);
    totalBlock = (int) Math.ceil((double) totalPage / pagePerBlock);

    if (request.getAttribute("nowBlock") != null) {
        nowBlock = Integer.parseInt(request.getAttribute("nowBlock").toString());
    }

    String id = (String) session.getAttribute("id");
%>

<form name="frmRead">
    <input type="hidden" name="center" value="/view_classroom/assignment_notice/classroomRead.jsp">
    <input type="hidden" name="notice_id">
    <input type="hidden" name="nowPage" value="<%=nowPage%>">
    <input type="hidden" name="nowBlock" value="<%=nowBlock%>">
    <input type="hidden" name="course_id" value="<%=course_id%>">
</form>

<div class="search-bar">
    <!-- 검색창을 가운데 정렬 -->
    <form action="<%=contextPath%>/classroomboard/searchlist.bo" method="post" name="frmSearch" onsubmit="fnSearch(); return false;">
        <select name="key">
            <option value="titleContent">제목 + 내용</option>
            <option value="name">작성자</option>
        </select>
        <input type="text" name="word" id="word">
        <input type="submit" value="검색">
        <input type="hidden" name="course_id" value="<%=course_id %>" >
        
    </form>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <button id="newContent" onclick="location.href='<%=contextPath%>/classroomboard/noticeWrite.bo?center=/view_classroom/assignment_notice/classroomWrite.jsp&nowPage=<%=nowPage%>&nowBlock=<%=nowBlock%>&course_id=<%=course_id%>'">새 글쓰기</button>
</div>

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
        <%
            if (list.isEmpty()) {
        %>
        <tr>
            <td colspan="5">등록된 글이 없습니다.</td>
        </tr>
        <%
            } else {
                for (int i = beginPerPage; i < (beginPerPage + numPerPage); i++) {
                    if (i == totalRecord) {
                        break;
                    }

                    ClassroomBoardVo vo = list.get(i);
        %>
        <tr>
            <td><%=vo.getNotice_id()%></td>
            <td>
                <div class="reply-indent" >
                 <%
					int width = 0; //답변글에 대한 이미지level.gif의 들여 쓰기 너비width값 

					if(vo.getB_level() > 0){//답변글들		
						width = vo.getB_level() * 10;
				%>
					<img src="<%=contextPath%>/common/notice/images/level.gif" 
							 width="<%=width%>" height="15">
							 
					<img src="<%=contextPath%>/common/notice/images/re.gif">
				<%	} %>
                    <a href="javascript:fnRead('<%=vo.getNotice_id()%>')"><%=vo.getTitle()%></a>
                </div>
            </td>
            <td><%=vo.getContent()%></td>
            <td><%=vo.getUserName().getUser_name()%></td>
            <td><%=vo.getCreated_date()%></td>
        </tr>
        <%
                }
            }
        %>
    </tbody>
</table>

<div class="pagination">
    <%
	    
	    String searchParams = "";
	    if (key != null && word != null) {
	        searchParams = "&key=" + key + "&word=" + URLEncoder.encode(word, "UTF-8");
	    }

        if (totalRecord != 0) { // 조회된 글이 있을 경우만 페이징 처리
            // 이전 블록 링크
            if (nowBlock > 0) {
    %>
    <a href="<%=contextPath%>/classroomboard/noticeList.bo?center=/view_classroom/assignment_notice/professorNotice.jsp&nowBlock=<%=nowBlock - 1%>&nowPage=<%=((nowBlock - 1) * pagePerBlock)%>&courseId=<%=course_id%><%=searchParams%>">
        ◀ 이전 <%=pagePerBlock%>개
    </a>
    <%
            }
            
            // 페이지 번호 반복 출력
            for (int i = 0; i < pagePerBlock; i++) {
                int pageNum = (nowBlock * pagePerBlock) + i + 1;
                if (pageNum > totalPage) break; // 페이지 번호 초과 시 중단
    %>
    <a href="<%=contextPath%>/classroomboard/noticeList.bo?center=/view_classroom/assignment_notice/professorNotice.jsp&nowBlock=<%=nowBlock%>&nowPage=<%=pageNum - 1%>&courseId=<%=course_id%><%=searchParams%>">
        <%=pageNum%>
    </a>
    <%
            }
            
            // 다음 블록 링크
            if (totalBlock > nowBlock + 1) {
    %>
    <a href="<%=contextPath%>/classroomboard/noticeList.bo?center=/view_classroom/assignment_notice/professorNotice.jsp&nowBlock=<%=nowBlock + 1%>&nowPage=<%=(nowBlock + 1) * pagePerBlock%>&courseId=<%=course_id%>">
        ▶ 다음 <%=pagePerBlock%>개
    </a>
    <%
            }
        }
    %>
</div>
</body>
</html>
