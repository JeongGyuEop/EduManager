package Controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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

import Dao.BookPostDAO;
import Service.BookPostService;
import Vo.BookPostVo;
import Vo.CommentVo;

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
			nextPage = "/Book/booktradingboard.bo?&center=/view_student/booktradingboard.jsp";

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
			
			
			
			//게시판상세보기
		case "/bookread.bo":
			

			BookPostVo bookPost = bookPostservice.serviceBookPost(request);
			majorInfo = bookPostservice.majorInfo();
			
			request.setAttribute("center", "/view_student/booktradingread.jsp");
			request.setAttribute("bookPost", bookPost);
			request.setAttribute("majorInfo", majorInfo);

			nextPage = "/main.jsp";
			
			break;

			
			
			
			// 댓글 작성 처리 및 게시글 상세보기
		case "/postDetail.do":
		    try {
		        // 게시글 상세보기
		        int postId = Integer.parseInt(request.getParameter("postId"));
		        BookPostVo postDetail = bookPostservice.getPostDetail(postId);
		        List<CommentVo> commentList = bookPostservice.getComments(postId);
		        
		        // 댓글 작성 처리 (POST 방식으로 받은 댓글 내용)
		        if ("POST".equalsIgnoreCase(request.getMethod())) {
		            String author = request.getParameter("author");
		            String content = request.getParameter("commentContent");

		            // 댓글 작성
		            Timestamp createdAt = Timestamp.valueOf(LocalDateTime.now());
		            CommentVo newComment = new CommentVo();
		            newComment.setPostId(postId);
		            newComment.setAuthor(author);
		            newComment.setContent(content);
		            newComment.setCreatedAt(createdAt);

		            // 댓글 추가
		            bookPostservice.addComment(newComment);

		            // 댓글 추가 후 리로드(리디렉션 대신 동일 페이지로 갱신)
		            commentList = bookPostservice.getComments(postId);
		        }

		        // 요청 속성에 게시글 정보와 댓글 리스트 설정
		        request.setAttribute("postDetail", postDetail);
		        request.setAttribute("commentList", commentList);

		        // 상세보기 페이지로 이동

		        nextPage = "/Book/postDetail.do?&center=/view_student/booktradingread.jsp";
		       // nextPage = "/view_student/booktradingread.jsp"; // 상세보기 페이지
		    } catch (NumberFormatException e) {
		        // postId가 유효하지 않으면 오류 처리
		        request.setAttribute("errorMessage", "Invalid postId parameter.");
		        return;
		    }
		    break;

			
			
			
			
			/*
			   // 댓글 게시글 상세보기
        case "/postDetail.do":
            try {
                // 게시글 상세보기
                int postId = Integer.parseInt(request.getParameter("postId"));
                BookPostVo postDetail = bookPostservice.getPostDetail(postId);
                List<CommentVo> commentList = bookPostservice.getComments(postId);

                
                // 요청 속성에 게시글 정보와 댓글 리스트 설정
                request.setAttribute("postDetail", postDetail);
                request.setAttribute("commentList", commentList);
                

                // 상세보기 페이지로 이동
                nextPage = "/view_student/booktradingread.jsp"; // 상세보기 페이지

            } catch (NumberFormatException e) {
                // postId가 유효하지 않으면 오류 처리
                request.setAttribute("errorMessage", "Invalid postId parameter.");
 //               nextPage = "/error.jsp"; // 오류 페이지로 리디렉션
                return;
            }
            break;

        // 댓글 작성 처리
        case "/replypro.do":
            try {
                // 댓글 작성 처리
                int postId_ = Integer.parseInt(request.getParameter("postId"));
                String author = request.getParameter("author");
                String content = request.getParameter("commentContent");
                
                BookPostVo postDetail = (BookPostVo) request.getAttribute("postDetail");
                Timestamp createdAt = Timestamp.valueOf(LocalDateTime.now());

                // 새로운 댓글 객체 생성
                CommentVo newComment = new CommentVo();
                newComment.setPostId(postId_);
                newComment.setAuthor(author);
                newComment.setContent(content);
                newComment.setCreatedAt(createdAt);

                // 댓글 추가
                bookPostservice.addComment(newComment);

                // 댓글 작성 후 상세보기 페이지로 리다이렉트
                response.sendRedirect(request.getContextPath() + "/Book/postDetail.do?postId=" + postId_);
                return;

            } catch (NumberFormatException e) {
                // postId가 유효하지 않으면 오류 처리
                request.setAttribute("errorMessage", "Invalid postId parameter.");
                return;
            }
      //      break;
*/
  

			

	    	
			//게시글 삭제
	//	case "/deleteBoard.do":
			
			
			//게시글 수정
	//	case "updateBoard.do":
				
			
			
// 중고 책 거래 -------------------------------------------------------------------------------------------------------------------

		default:
			break;
		}

		// 다음 페이지로 포워딩
		RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
		dispatch.forward(request, response);
	}
}
