package Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import Dao.BookPostDAO;
import Service.BookPostService;
import Vo.BookPostVo;

@WebServlet("/Book/*")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, // 1MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class BookPostController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	BookPostService bookPostservice;
	BookPostDAO bookPostDAO;

	@Override
	public void init() throws ServletException {
		super.init();
		bookPostDAO = new BookPostDAO();
		bookPostservice = new BookPostService();
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

	// 모든 요청을 처리하는 메인 메서드
	protected void doHandle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");

		String nextPage = null;
		String center = null;
		String action = request.getPathInfo();

		System.out.println("2단계 요청 주소 : " + action);

		// 액션에 따라 분기 처리
		switch (action) {
// 중고 책 거래 -------------------------------------------------------------------------------------------------------------------

		case "/booktradingboard.bo": // 글 조회 메서드

			List<BookPostVo> bookBoardList = bookPostservice.serviceBoardbooklist();
			String nowPage = request.getParameter("nowPage");
			String nowBlock = request.getParameter("nowBlock");			
			
			request.setAttribute("message", request.getAttribute("message"));
			
			center = request.getParameter("center");

			request.setAttribute("center", center);
			
			request.setAttribute("bookBoardList", bookBoardList);
			request.setAttribute("nowPage", nowPage);
			request.setAttribute("nowBlock", nowBlock);

			nextPage = "/main.jsp";


			break;

		case "/bookPostUpload.bo": // 글 등록하러 가기
			// 학과 정보를 받아옵니다.
			List<BookPostVo> majorInfo = bookPostservice.majorInfo();

			center = "/view_student/booktrading.jsp";
			
			request.setAttribute("center", center);
			request.setAttribute("majorInfo", majorInfo);
			request.setAttribute("userId", request.getParameter("userId"));
			
			nextPage = "/main.jsp";

			break;

		case "/bookPostUpload.do": // 글 등록

			// 글 등록 form으로부터 글 제목이 있을 경우에 실행
			try {
				// 서비스 호출
				int result = bookPostservice.bookPostUploadService(request);
				// 결과 처리 및 메시지 설정
				if (result == 1) {
					request.setAttribute("message", "게시글이 성공적으로 등록되었습니다.");
				} else {
					request.setAttribute("message", "게시글 등록에 실패했습니다. 다시 시도해주세요.");
				}
			} catch (Exception e) {
				// 예외 발생 시 에러 메시지 설정
				e.printStackTrace();
				request.setAttribute("message", "게시글 등록 중 문제가 발생했습니다.");
			}
			// nextPage 지정
			nextPage = "/Book/booktradingboard.bo?&center=/view_student/booktradingboard.jsp";  //?center=/view_student/booktradingboard.jsp";

			break;

			
			//게시글 검색
		case "/booksearchlist.bo":	

			// 키워드로 게시글 검색 기능
						String key = request.getParameter("key");
						String word = request.getParameter("word");
						bookBoardList = bookPostservice.serviceBookKeyWord(key, word);
						request.setAttribute("bookBoardList", bookBoardList);
						request.setAttribute("center", "view_student/booktradingboard.jsp");
						nextPage = "/main.jsp";
						break;
			
			
			
			//게시판읽기
		case "/bookread.bo":	
			
			
			
// 중고 책 거래 -------------------------------------------------------------------------------------------------------------------

		default:
			break;
		}

		// 다음 페이지로 포워딩
		RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
		dispatch.forward(request, response);
	}
}
