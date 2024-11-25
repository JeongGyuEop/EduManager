package Controller;

import java.io.IOException;
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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.*;

@WebServlet("/Book/*")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 1, // 1MB
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 50    // 50MB
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
			nextPage = "/Book/booktradingboard.bo?&center=/view_student/booktradingboard.jsp"; // ?center=/view_student/booktradingboard.jsp";

			break;

		// 게시글 검색
		case "/booksearchlist.bo":

			// 키워드로 게시글 검색 기능
			String key = request.getParameter("key");
			String word = request.getParameter("word");
			bookBoardList = bookPostservice.serviceBookKeyWord(key, word);
			request.setAttribute("bookBoardList", bookBoardList);
			request.setAttribute("center", "view_student/booktradingboard.jsp");
			nextPage = "/main.jsp";
			break;

		// 게시판상세보기
		case "/bookread.bo":

			BookPostVo bookPost = bookPostservice.serviceBookPost(request);
			majorInfo = bookPostservice.majorInfo();

			request.setAttribute("center", "/view_student/booktradingread.jsp");
			request.setAttribute("bookPost", bookPost);
			request.setAttribute("majorInfo", majorInfo);

			nextPage = "/main.jsp";

			break;

		// 게시글 삭제
		case "/bookpostdelete.do":
			
			
			
			
			
			
			

			// 게시글 수정 버튼
		case "/bookpostupdate.bo":
			System.out.println("Controller : " + request.getParameter("postId"));
			BookPostVo bookPost1 = bookPostservice.serviceBookPost(request);
			majorInfo = bookPostservice.majorInfo();

			request.setAttribute("center", "/view_student/bookpostupdate.jsp");
			request.setAttribute("bookPost", bookPost1);
			request.setAttribute("majorInfo", majorInfo);

			nextPage = "/main.jsp";

			break;

		case "/bookpostupdate.do": // 글 수정
			// 요청 인코딩 설정
			request.setCharacterEncoding("UTF-8");

			// 폼 필드 값들을 저장할 변수 선언
			String userIdUpdate = null;
			String postIdUpdate = null;
			String postTitleUpdate = null;
			String postContentUpdate = null;
			String majorTagUpdate = null;

			// BookPostVo 객체 및 이미지 리스트 생성
			BookPostVo bookPostVoUpdate = new BookPostVo();
			bookPostVoUpdate.setImages(new ArrayList<BookPostVo.BookImage>());

			// 파일 업로드 처리를 위해 파트들을 가져옵니다.
			Collection<Part> partsUpdate = request.getParts();

			for (Part part : partsUpdate) {
				String fieldName = part.getName();
				if (part.getContentType() == null) {
					// 이것은 폼 필드입니다.
					String value = readParameterValue(part);
					System.out.println("폼 필드 - " + fieldName + ": " + value); // 디버깅 로그
					switch (fieldName) {
					case "userId":
						userIdUpdate = value;
						bookPostVoUpdate.setUserId(userIdUpdate);
						break;
					case "postId":
						postIdUpdate = value;
						if (postIdUpdate != null && !postIdUpdate.isEmpty()) {
							try {
								bookPostVoUpdate.setPostId(Integer.parseInt(postIdUpdate));
							} catch (NumberFormatException e) {
								System.out.println("postId 형식이 올바르지 않습니다: " + postIdUpdate);
								e.printStackTrace();
								// 적절한 오류 처리
							}
						} else {
							System.out.println("postId가 null이거나 비어있습니다.");
						}
						break;
					case "postTitle":
						postTitleUpdate = value;
						bookPostVoUpdate.setPostTitle(postTitleUpdate);
						break;
					case "postContent":
						postContentUpdate = value;
						bookPostVoUpdate.setPostContent(postContentUpdate);
						break;
					case "majorTag":
						majorTagUpdate = value;
						bookPostVoUpdate.setMajorTag(majorTagUpdate);
						break;
					}
				} else {
					// 이것은 파일 파트입니다.
					if (fieldName.equals("image") && part.getSize() > 0) {
						String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
						System.out.println("파일 이름: " + fileName);
						System.out.println("파일 크기: " + part.getSize());
						System.out.println("컨텐츠 타입: " + part.getContentType());

						// 이미지 객체 생성 및 파일명 설정
						BookPostVo.BookImage bookImage = new BookPostVo.BookImage();
						bookImage.setFileName(fileName); // 실제 파일명

						// 이미지 리스트에 추가
						bookPostVoUpdate.getImages().add(bookImage);
					}
				}
			}

			// 폼 필드 값 출력
			System.out.println("userId: " + userIdUpdate);
			System.out.println("postId: " + postIdUpdate);
			System.out.println("postTitle: " + postTitleUpdate);
			System.out.println("postContent: " + postContentUpdate);
			System.out.println("majorTag: " + majorTagUpdate);

			// 필수 필드 확인
			if (userIdUpdate == null || postIdUpdate == null || postTitleUpdate == null || postContentUpdate == null
					|| majorTagUpdate == null) {
				System.out.println("필수 필드 중 하나 이상이 누락되었습니다.");
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "필수 필드가 누락되었습니다.");
				return;
			}
			// 글 수정 form으로부터 글 제목이 있을 경우에 실행
			try {
				// 서비스 호출
				int result = bookPostservice.bookPostUpdateService(request);
				// 결과 처리 및 메시지 설정
				if (result == 1) {
					request.setAttribute("message", "게시글이 성공적으로 수정되었습니다.");
				} else {
					request.setAttribute("message", "게시글 수정에 실패했습니다. 다시 시도해주세요.");
				}
			} catch (Exception e) {
				// 예외 발생 시 에러 메시지 설정
				e.printStackTrace();
				request.setAttribute("message", "게시글 수정 중 문제가 발생했습니다.");
			}
			// nextPage 지정
			nextPage = "/Book/booktradingboard.bo?&center=/view_student/booktradingboard.jsp";

			break;

		// 댓글 입력
		case "replypro.do":

			// 중고 책 거래 -------------------------------------------------------------------------------------------------------------------

		default:
			break;
		}

		// 다음 페이지로 포워딩
		if (nextPage != null) {
			RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
			dispatch.forward(request, response);
		} else {
			System.out.println("nextPage가 null입니다. 요청을 처리하지 못했습니다.");
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "페이지를 찾을 수 없습니다.");
		}
	}

	// 폼 필드의 값을 읽어오는 유틸리티 메서드
	private String readParameterValue(Part part) throws IOException {
		InputStream inputStream = part.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
		StringBuilder value = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			value.append(line);
		}
		return value.toString();
	}
}
