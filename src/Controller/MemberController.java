package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


//import Dao.CarDAO;
import Service.MemberService;
import Service.MenuItemService;
import Vo.MemberVo;
//import Vo.CarConfirmVo;
//import Vo.CarListVo;
//import Vo.CarOrderVo;

// 사장 ...

// MVC 중에서 C 역할

// /member/join.me?center=members/join.jsp

@WebServlet("/member/*")
public class MemberController extends HttpServlet {

	// 부장
	MemberService memberservice;

	@Override
	public void init() throws ServletException {
		memberservice = new MemberService();
	}

	// doGet doPost 메소드 오버라이딩
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

	protected void doHandle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8"); // MIME TYPE 설정

		// 웹브라우저로 출력할 출력 스트림 생성
		PrintWriter out = null;

		// 조건에 따라 포워딩 또는 보여줄 VIEW 주소 경로를 저장할 변수
		String nextPage = null;

		// 재요청할 경로 주소를 저장할 변수
		String center = null;

		String action = request.getPathInfo(); // 2단계 요청주소
		System.out.println("요청한 2단계 주소: " + action);

		switch (action) {
		case "/main.bo": // CarMain.jsp 메인화면 2단계 요청 주소를 받으면

			nextPage = "/main.jsp";

			break;

		// ===========================================================================================

		case "/login.do": // 로그인 요청을 한 2단계 요청주소 일 때

			// 부장에게 시킴
			// check 변수의 값이 1 이면 아이디, 비밀번호가 DB에 존재한다.
			// 0 이면 아이디만 DB에 존재한다.
			// -1 이면 아이디 DB에 존재하지 않음
			Map<String, String> userInfo = memberservice.serviceUserCheck(request);

			String check = userInfo.get("check"); // check 값 확인

			out = response.getWriter();

			// check 값에 따라 로그인 결과 처리
			if (check == "0") { // 아이디 맞음, 비밀번호 틀림
				out.println("<script>");
				out.println("window.alert('비밀번호가 틀립니다!');");
				out.println("history.go(-1);");
				out.println("</script>");

				return; // doHandle 메소드를 빠져나가기 위함

			} else if (check == "-1") { // 아이디 틀림
				out.println("<script>");
				out.println("window.alert('아이디가 존재하지 않습니다!');");
				out.println("history.go(-1);");
				out.println("</script>");

				return; // doHandle 메소드를 빠져나가기 위함
			}

			// 로그인 성공 (check 값이 "1"인 경우)
			String role = userInfo.get("role");

			if (role.equals("학생")) {
				// 재요청할 전체 메인화면 주소를 저장
				center = "/view_student/studentHome.jsp";
			} else if (role.equals("교수")) {
				// 재요청할 전체 메인화면 주소를 저장
				center = "/view_professor/professorHome.jsp";
			} else if (role.equals("관리자")) {
				// 재요청할 전체 메인화면 주소를 저장
				center = "/view_admin/adminHome.jsp";
			}

			// MenuItemService를 사용하여 역할에 맞는 메뉴 HTML 생성
			MenuItemService menuService = new MenuItemService();
			String contextPath = request.getContextPath();

			// MenuItemService.java (서비스)를 호출하여 top메뉴와 sidebar에 표시될 요소를 반환받는다.
			String menuHtml = menuService.generateMenuHtml(role, contextPath);

			System.out.println(contextPath); // EduManager
			System.out.println(menuHtml);

			request.setAttribute("center", center);
			session.setAttribute("menuHtml", menuHtml); // 생성된 menuHtml 설정

			nextPage = "/main.jsp";

			break;

		// =========================================================================================

		case "/adminPage.bo": // 로그인 요청을 한 2단계 요청주소 일 때

			center = request.getParameter("center");

			session = request.getSession();
			String selectedMenu = request.getParameter("selectedMenu");
			if (selectedMenu != null) {
				session.setAttribute("selectedMenu", selectedMenu);
			}

			request.setAttribute("center", center);
			nextPage = "/main.jsp";

			break;

		// =========================================================================================
		case "/logout.me": // 로그아웃 요청을 한 2단계 요청주소 일 때

			// 부장 시키자
			// - 로그아웃 화면을 보여주기 위해 session 영역에 저장된 아이디를 제거
			memberservice.serviceLogOut(request);

			out = response.getWriter();
			// 세션 가져오기
			session = request.getSession(false); // 세션이 없으면 null 반환
			if (session == null) {
				out.println("<html><body>");
				out.println("<script>");
				out.println("alert('로그아웃이 완료되었습니다.');");
				out.println("window.location.href = '/EduManager/main.jsp';");
				out.println("</script>");
				out.println("</body></html>");

				out.close();

				return;
			}

			out.println("<html><body>");
			out.println("<script>");
			out.println("alert('로그아웃에 실패하였습니다. 다시 시도해주세요.');");
			out.println("</script>");
			out.println("</body></html>");

			break;

		// ==============================================================================

		case "/studentRegister.do":
			// 학생 등록 서비스 호출
			int result = memberservice.registerStudent(request);
			// request.setAttribute("result", result);

			if (result == 1) {
				request.setAttribute("message", "학생 정보가 성공적으로 추가되었습니다.");
			} else {
				request.setAttribute("message", "학생 정보를 추가하는 데 실패했습니다.");
			}

			center = "/view_admin/studentManager/viewStudentList.jsp";

			request.setAttribute("center", center);
			nextPage = "/main.jsp";
			break;

		// ==========================================================================================

		case "/viewStudentList.do": // 전체 학생 조회
			List<MemberVo> students = memberservice.getAllStudents();
			request.setAttribute("students", students);
			nextPage = "/view_admin/studentManager/viewStudentList.jsp";
			break;

		// ==========================================================================================

		case "/studentManage.bo": // 전체 학생 조회

			center = request.getParameter("center");

			request.setAttribute("center", center);

			nextPage = "/main.jsp";
			break;

		// ==========================================================================================

		case "/editStudent.do": // 학생 정보 수정

			String userId = request.getParameter("user_id");
			MemberVo student = memberservice.getStudentById(userId);
			request.setAttribute("student", student);
			nextPage = "/view_admin/studentManager/editStudent.jsp";

			break;

		// ==========================================================================================
		case "/viewStudent.do":
			userId = request.getParameter("user_id");
			student = memberservice.getStudentById(userId);
			request.setAttribute("student", student);
			nextPage = "/view_admin/studentManager/viewStudent.jsp";
			break;
		// ==========================================================================================
		case "/updateStudent.do": // 수정했을때 보여주는 메세지
			boolean isUpdated = memberservice.updateStudent(request);
			String updateMessage = isUpdated ? "학생 정보가 성공적으로 수정되었습니다." : "학생 정보 수정에 실패했습니다.";
			response.sendRedirect(request.getContextPath() + "/member/viewStudentList.do?message="
					+ URLEncoder.encode(updateMessage, "UTF-8"));
			return;
		// ==========================================================================================
		case "/deleteStudent.do": // 학생 정보 삭제
			String studentId = request.getParameter("student_id");
			boolean isDeleted = memberservice.deleteStudent(studentId);
			String deleteMessage = isDeleted ? "학생 정보가 성공적으로 삭제되었습니다." : "학생 정보 삭제에 실패했습니다.";
			response.sendRedirect(request.getContextPath() + "/member/viewStudentList.do?message="
					+ URLEncoder.encode(deleteMessage, "UTF-8"));
			return;

		// ==========================================================================================
		
			// MemberController.java
		case "/updateMyInfo.do":
			 boolean isMyInfoUpdated = memberservice.updateMyInfo(request);
		        String myInfoMessage = isMyInfoUpdated ? "정보가 성공적으로 수정되었습니다." : "권한이 없거나 수정에 실패했습니다.";
		        response.sendRedirect(request.getContextPath() + "/member/myPage.do?message=" + URLEncoder.encode(myInfoMessage, "UTF-8"));
		        return;

	
		default:
			break;
		}

		// 디스패처 방식 포워딩(재요청)
		RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
		dispatch.forward(request, response);

	} // doHandle 메소드
}
