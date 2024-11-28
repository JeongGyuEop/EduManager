<%@page import="java.net.URLEncoder"%>
<%@page import="Vo.BoardVo"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
    request.setCharacterEncoding("UTF-8");
    String contextPath = request.getContextPath();
    String key = (String)request.getAttribute("key");
    String word = (String)request.getAttribute("word");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>공지사항</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f8f9fa;
            margin: 0;
            padding: 0;
            line-height: 1.6;
        }

        .table-container {
            margin: 20px auto;
            padding: 20px;
            background-color: #fff;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
        }

        .table th {
            background-color: #198754;
            color: white;
        }

        .table-hover tbody tr:hover {
            background-color: #e9ecef;
        }

        .pagination a {
            color: #198754;
            text-decoration: none;
        }

        .pagination a:hover {
            background-color: #198754;
            color: white;
        }

        .search-bar {
            display: flex;
            justify-content: center;
            align-items: center;
            margin-bottom: 20px;
        }

        .search-bar select, .search-bar input {
            margin-right: 10px;
        }

        .search-bar .btn {
            display: inline-flex !important; /* 버튼 내부 요소를 가로 정렬 */
            align-items: center !important; /* 텍스트를 세로 중앙 정렬 */
            justify-content: center !important; /* 텍스트를 가로 중앙 정렬 */
            white-space: nowrap !important; /* 텍스트 줄바꿈 방지 */
            height: auto !important; /* 버튼 높이를 내용에 맞게 조정 */
            padding: 0.375rem 0.75rem !important; /* 버튼 내부 여백 조정 */
            font-size: 1rem !important; /* 버튼 폰트 크기 설정 */
            line-height: normal !important; /* 텍스트 높이 조정 */
            text-align: center !important; /* 텍스트 정렬 */
        }
    </style>

    <script>
        function fnSearch() {
            var word = document.getElementById("word").value;
            if (!word) {
                alert("검색어를 입력하세요.");
                document.getElementById("word").focus();
                return false;
            }
            return true;
        }

        function fnRead(val) {
            document.frmRead.action = "<%=contextPath%>/Board/read.bo";
            document.frmRead.notice_id.value = val;
            document.frmRead.submit();
        }
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

        ArrayList<BoardVo> list = (ArrayList<BoardVo>) request.getAttribute("list");
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
    %>

    <!-- 글 상세 조회용 폼 -->
    <form name="frmRead">
        <input type="hidden" name="notice_id">
        <input type="hidden" name="nowPage" value="<%=nowPage%>">
        <input type="hidden" name="nowBlock" value="<%=nowBlock%>">
    </form>

    <div class="container mt-4">
        <h1 class="text-center text-success">공지사항</h1>

        <!-- 검색 영역 -->
        <div class="search-bar">
            <form action="<%=contextPath%>/Board/searchlist.bo" method="post" name="frmSearch" class="d-flex" onsubmit="return fnSearch();">
                <select name="key" class="form-select w-auto me-2">
                    <option value="titleContent">제목 + 내용</option>
                    <option value="name">작성자</option>
                </select>
                <input type="text" name="word" id="word" class="form-control" placeholder="검색어를 입력하세요">
                <button type="submit" class="btn btn-primary">검색</button>
            </form>
            <button id="newContent" onclick="location.href='<%=contextPath%>/Board/write.bo?nowPage=<%=nowPage%>&nowBlock=<%=nowBlock%>'"
                class="btn btn-success ms-3">새 글쓰기</button>
        </div>

        <!-- 게시판 테이블 -->
        <table class="table table-bordered table-hover">
            <thead class="table-success">
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
                    <td colspan="5" class="text-center text-muted">등록된 글이 없습니다.</td>
                </tr>
                <% } else {
                    for (int i = beginPerPage; i < (beginPerPage + numPerPage); i++) {
                        if (i == totalRecord) break;
                        BoardVo vo = list.get(i);
                %>
                <tr>
                    <td><%=vo.getNotice_id()%></td>
                    <td>
                        <div class="d-flex align-items-center">
                            <% if (vo.getB_level() > 0) { %>
                                <img src="<%=contextPath%>/common/notice/images/level.gif" width="<%=vo.getB_level() * 10%>" height="15" alt="level">
                                <img src="<%=contextPath%>/common/notice/images/re.gif" alt="reply">
                            <% } %>
                            <a href="javascript:fnRead('<%=vo.getNotice_id()%>')" class="ms-2 text-decoration-none text-primary"><%=vo.getTitle()%></a>
                        </div>
                    </td>
                    <td><%=vo.getContent()%></td>
                    <td><%=vo.getUserName().getUser_name()%></td>
                    <td><%=vo.getCreated_date()%></td>
                </tr>
                <% } } %>
            </tbody>
        </table>

        <!-- 페이지네이션 -->
        <nav class="d-flex justify-content-center mt-4">
            <ul class="pagination">
                <% 
                    String searchParams = "";
                    if (key != null && word != null) {
                        searchParams = "&key=" + key + "&word=" + URLEncoder.encode(word, "UTF-8");
                    }
                    if (totalRecord != 0) {
                        if (nowBlock > 0) { 
                %>
                <li class="page-item">
                    <a class="page-link" href="<%=contextPath%>/Board/list.bo?center=/view_admin/noticeManage.jsp&nowBlock=<%=nowBlock - 1%>&nowPage=<%=((nowBlock - 1) * pagePerBlock)%><%=searchParams%>">◀ 이전</a>
                </li>
                <% } 
                    for (int i = 0; i < pagePerBlock; i++) {
                        int pageNum = (nowBlock * pagePerBlock) + i + 1;
                        if (pageNum > totalPage) break; 
                %>
                <li class="page-item">
                    <a class="page-link" href="<%=contextPath%>/Board/list.bo?center=/view_admin/noticeManage.jsp&nowBlock=<%=nowBlock%>&nowPage=<%=pageNum - 1%><%=searchParams%>"><%=pageNum%></a>
                </li>
                <% } 
                    if (totalBlock > nowBlock + 1) { 
                %>
                <li class="page-item">
                    <a class="page-link" href="<%=contextPath%>/Board/list.bo?center=/view_admin/noticeManage.jsp&nowBlock=<%=nowBlock + 1%>&nowPage=<%=(nowBlock + 1) * pagePerBlock%><%=searchParams%>">▶ 다음</a>
                </li>
                <% } } %>
            </ul>
        </nav>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
