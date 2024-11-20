package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import Dao.BoardDAO;
import Service.BoardService;
import Service.MenuItemService;
import Vo.BoardVo;
import Vo.MemberVo;
import Vo.ScheduleVo;

@WebServlet("/Board/*")
@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024 * 2, // 2MB => 2mb보다 작은 경우 메모리에 저장, 큰 경우 디스크에 저장
	    maxFileSize = 1024 * 1024 * 10,      // 10MB - 개별 파일 크기 제한
	    maxRequestSize = 1024 * 1024 * 50    // 50MB - 전체 파일 크기 제한
	)
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
		try {
			doHandle(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			doHandle(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// JSON 문자열 내 특수 문자를 이스케이프 처리하는 메서드
	private String escapeJson(String str) {
		if (str == null) {
			return "";
		}
		return str.replace("\\", "\\\\").replace("\"", "\\\"").replace("/", "\\/").replace("\b", "\\b")
				.replace("\f", "\\f").replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
	}

	// 모든 요청을 처리하는 메인 메서드
	protected void doHandle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, Exception {
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

		String startDate = null;
		String endDate = null;
		String month = null;

		int result = 0;
		
		System.out.println("2단계 요청 주소 : " + action);

		// 액션에 따라 분기 처리
		switch (action) {
		case "/list.bo":
			// 게시판 목록을 가져오는 기능
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
			// 키워드로 게시글 검색 기능
			String key = request.getParameter("key");
			String word = request.getParameter("word");
			list = boardservice.serviceBoardKeyWord(key, word);
			request.setAttribute("list", list);
			request.setAttribute("center", "common/notice/list.jsp");
			nextPage = "/main.jsp";
			break;

		case "/write.bo":
			// 게시글 작성 페이지로 이동
			String memberid = (String) session.getAttribute("id");
			MemberVo membervo = boardservice.serviceMemberOne(memberid);
			request.setAttribute("center", "common/notice/write.jsp");
			request.setAttribute("nowPage", request.getParameter("nowPage"));
			request.setAttribute("nowBlock", request.getParameter("nowBlock"));
			nextPage = "/main.jsp";
			break;

		case "/writePro.bo":
			// 게시글 작성 후 저장 기능
			String writer = request.getParameter("w");
			String title = request.getParameter("t");
			String content = request.getParameter("c");
			vo = new BoardVo();
			vo.setAuthor_id(writer);
			vo.setTitle(title);
			vo.setContent(content);
			result = boardservice.serviceInsertBoard(vo);
			String go = String.valueOf(result);
			out.print(go);
			return;

		case "/read.bo":
			// 게시글 읽기 기능
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
			// 게시글 수정 기능
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
			// 게시글 삭제 기능
			String delete_idx = request.getParameter("notice_id");
			String result__ = boardservice.serviceDeleteBoard(delete_idx);
			out.write(result__);
			return;

		case "/reply.do":
			// 게시글에 대한 답글 작성 페이지로 이동
			String notice_id_ = request.getParameter("notice_id");
			String reply_id_ = request.getParameter("id");
			MemberVo reply_vo = boardservice.serviceMemberOne(reply_id_);
			request.setAttribute("notice_id", notice_id_);
			request.setAttribute("vo", reply_vo);
			request.setAttribute("center", "common/notice/reply.jsp");
			nextPage = "/main.jsp";
			break;

		case "/replyPro.do":
			// 답글 작성 후 저장 기능
			String super_notice_id = request.getParameter("super_notice_id");
			String reply_writer = request.getParameter("writer");
			String reply_title = request.getParameter("title");
			String reply_content = request.getParameter("content");
			String reply_id = (String) session.getAttribute("id");
			boardservice.serviceReplyInsertBoard(super_notice_id, reply_writer, reply_title, reply_content, reply_id);
			nextPage = "/Board/list.bo?center=/view_admin/noticeManage.jsp&nowBlock=<%=nowBlock%>&nowPage=<%=(nowBlock * pagePerBlock)";
			break;

		case "/boardCalendar.bo":

			center = "/common/calendar.jsp";

			request.setAttribute("center", center);

			nextPage = "/main.jsp";

			break;

		case "/boardCalendar.do":
			// 일정 데이터를 JSON 형식으로 응답하는 기능
			startDate = request.getParameter("start");
			endDate = request.getParameter("end");

			try {
				List<ScheduleVo> eventList = boardservice.getEvents(startDate, endDate);
				Gson gson = new Gson();
				List<JsonObject> jsonEvents = new ArrayList<>();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				for (ScheduleVo event : eventList) {
					JsonObject jsonEvent = new JsonObject();
					jsonEvent.addProperty("title", event.getEvent_name());
					jsonEvent.addProperty("start", dateFormat.format(event.getStart_date()));
					jsonEvent.addProperty("end", dateFormat.format(event.getEnd_date()));
					jsonEvent.addProperty("description", event.getDescription());
					jsonEvents.add(jsonEvent);
				}
				String jsonResponse = gson.toJson(jsonEvents);
				response.setContentType("application/json;charset=UTF-8");
				out.print(jsonResponse);
				out.flush();
			} catch (IllegalArgumentException e) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				JsonObject error = new JsonObject();
				error.addProperty("error", e.getMessage());
				out.write(error.toString());
			} catch (RuntimeException e) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				JsonObject error = new JsonObject();
				error.addProperty("error", "서버 내부 오류가 발생했습니다.");
				out.write(error.toString());
				e.printStackTrace();
			}
			return;

		case "/viewSchedule.bo":
			month = request.getParameter("month");
			if (month != null && !month.isEmpty()) {
				boardservice.processViewSchedule(request);
			}

			center = request.getParameter("center");
			request.setAttribute("center", center);

			nextPage = "/main.jsp";

			break;

		case "/addSchedule":
			boardservice.addSchedule(request);
			startDate = request.getParameter("startDate");
			month = startDate.substring(0, 7);
			response.sendRedirect(request.getContextPath()
					+ "/Board/viewSchedule.bo?center=/view_admin/calendarEdit.jsp&month=" + month);
			return;
		case "/updateSchedule":
			boardservice.updateSchedule(request);
			month = request.getParameter("month");
			response.sendRedirect(
					request.getContextPath() + "/Board/viewSchedule.bo?center=/view_admin/calendarEdit.jsp&month="
							+ URLEncoder.encode(month, "UTF-8"));
			return;
		case "/deleteSchedule":
			boardservice.deleteSchedule(request);
			month = request.getParameter("month");
			response.sendRedirect(
					request.getContextPath() + "/Board/viewSchedule.bo?center=/view_admin/calendarEdit.jsp&month="
							+ URLEncoder.encode(month, "UTF-8"));
			return;

// 중고 책 거래 -------------------------------------------------------------------------------------------------------------------
			
		case "/bookPostUpload.bo": // 여기서는 간단한 로직만 처리합니다.
			result = boardservice.bookPostUploadService(request); // 저장 메서드 라인
			if (result == 1) {
				// 저장에 성공하면 성공 메시지 반환
			} else {
				// 저장에 실패하면 실패 메시지 반환
			}
			// 조회 메서드 라인
			// 조회 결과값 저장
			// 반환 받은 메시지 request에 저장
			// center 및 nextPage 지정
			
			break;
			
			
// 중고 책 거래 -------------------------------------------------------------------------------------------------------------------
			
		default:
			break;
		}

		// 다음 페이지로 포워딩
		RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
		dispatch.forward(request, response);
	}
}
