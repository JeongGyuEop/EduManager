package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Dao.BoardDAO;
import Service.BoardService;
import Service.MenuItemService;
import Vo.BoardVo;
import Vo.MemberVo;

@WebServlet("/Board/*")
public class BoardController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    BoardService boardservice;
    private BoardDAO boardDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        boardDAO = new BoardDAO();
        boardservice = new BoardService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doHandle(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doHandle(request, response);
    }

    private String escapeJson(String str) {
        if (str == null) {
            return "";
        }
        return str.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("/", "\\/")
                  .replace("\b", "\\b")
                  .replace("\f", "\\f")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }

    protected void doHandle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        HttpSession session = request.getSession();
        PrintWriter out = response.getWriter();

        String nextPage = null;
        String center = null;
        String action = request.getPathInfo();

        ArrayList list = null;
        BoardVo vo = null;

        switch (action) {
            case "/list.bo":
                list = boardservice.serviceBoardList();
                String nowPage = request.getParameter("nowPage");
                String nowBlock = request.getParameter("nowBlock");
                MenuItemService menuService = new MenuItemService();
                String contextPath = request.getContextPath();
                String role = (String) session.getAttribute("role");
                String menuHtml = menuService.generateMenuHtml(role, contextPath);
                center = request.getParameter("center");
                request.setAttribute("center", center);
                request.setAttribute("list", list);
                request.setAttribute("nowPage", nowPage);
                request.setAttribute("nowBlock", nowBlock);
                nextPage = "/main.jsp";
                break;

            case "/searchlist.bo":
                String key = request.getParameter("key");
                String word = request.getParameter("word");
                list = boardservice.serviceBoardKeyWord(key, word);
                request.setAttribute("list", list);
                request.setAttribute("center", "common/notice/list.jsp");
                nextPage = "/main.jsp";
                break;

            case "/write.bo":
                String memberid = (String) session.getAttribute("id");
                MemberVo membervo = boardservice.serviceMemberOne(memberid);
                request.setAttribute("center", "common/notice/write.jsp");
                request.setAttribute("nowPage", request.getParameter("nowPage"));
                request.setAttribute("nowBlock", request.getParameter("nowBlock"));
                nextPage = "/main.jsp";
                break;

            case "/writePro.bo":
                String writer = request.getParameter("w");
                String title = request.getParameter("t");
                String content = request.getParameter("c");
                vo = new BoardVo();
                vo.setAuthor_id(writer);
                vo.setTitle(title);
                vo.setContent(content);
                int result = boardservice.serviceInsertBoard(vo);
                String go = String.valueOf(result);
                out.print(go);
                return;

            case "/read.bo":
                String notice_id = request.getParameter("notice_id");
                String nowPage_ = request.getParameter("nowPage");
                String nowBlock_ = request.getParameter("nowBlock");
                vo = boardservice.serviceBoardRead(notice_id);
                request.setAttribute("center", "common/notice/read.jsp");
                request.setAttribute("vo", vo);
                request.setAttribute("nowPage", nowPage_);
                request.setAttribute("nowBlock", nowBlock_);
                request.setAttribute("notice_id", notice_id);
                nextPage = "/main.jsp";
                break;

            case "/updateBoard.do":
                String notice_id_2 = request.getParameter("notice_id");
                String title_ = request.getParameter("title");
                String content_ = request.getParameter("content");
                int result_ = boardservice.serviceUpdateBoard(notice_id_2, title_, content_);
                if (result_ == 1) {
                    out.write("수정성공");
                } else {
                    out.write("수정실패");
                }
                return;

            case "/deleteBoard.do":
                String delete_idx = request.getParameter("notice_id");
                String result__ = boardservice.serviceDeleteBoard(delete_idx);
                out.write(result__);
                return;

            case "/reply.do":
                String notice_id_ = request.getParameter("notice_id");
                String reply_id_ = request.getParameter("id");
                MemberVo reply_vo = boardservice.serviceMemberOne(reply_id_);
                request.setAttribute("notice_id", notice_id_);
                request.setAttribute("vo", reply_vo);
                request.setAttribute("center", "common/notice/reply.jsp");
                nextPage = "/main.jsp";
                break;

            case "/replyPro.do":
                String super_notice_id = request.getParameter("super_notice_id");
                String reply_writer = request.getParameter("writer");
                String reply_title = request.getParameter("title");
                String reply_content = request.getParameter("content");
                String reply_id = (String) session.getAttribute("id");
                boardservice.serviceReplyInsertBoard(super_notice_id, reply_writer, reply_title, reply_content, reply_id);
                nextPage = "/Board/list.bo?center=/view_admin/noticeManage.jsp&nowBlock=<%=nowBlock%>&nowPage=<%=(nowBlock * pagePerBlock)";
                break;

            case "/boardCalendar.bo":
                // 캘린더 이벤트를 JSON 형식으로 가져와 데이터를 클라이언트에 반환합니다.
                String startDate = request.getParameter("start");
                String endDate = request.getParameter("end");
                List<BoardVo> eventList = boardDAO.getEvents(startDate, endDate);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                StringBuilder json = new StringBuilder();
                json.append("[");
                for (int i = 0; i < eventList.size(); i++) {
                    BoardVo event = eventList.get(i);
                    json.append("{");
                    json.append("\"title\":\"").append(escapeJson(event.getEvent_name())).append("\",");
                    json.append("\"start\":\"").append(escapeJson(dateFormat.format(event.getStart_date()))).append("\",");
                    json.append("\"end\":\"").append(escapeJson(dateFormat.format(event.getEnd_date()))).append("\"");
                    json.append("}");
                    if (i != eventList.size() - 1) {
                        json.append(",");
                    }
                }
                json.append("]");
                response.setContentType("application/json;charset=UTF-8");
                out.print(json.toString());
                out.flush();
                return;

            default:
                break;
        }

        RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
        dispatch.forward(request, response);
    }
}
