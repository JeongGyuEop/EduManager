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
import Service.StudentService;
import Service.MenuItemService;
import Vo.StudentVo;
//import Vo.CarConfirmVo;
//import Vo.CarListVo;
//import Vo.CarOrderVo;

// 사장 ...

// MVC 중에서 C 역할

// /member/join.me?center=members/join.jsp

@WebServlet("/student/*")
public class StudentController extends HttpServlet {

	// 부장
	StudentService studentservice;

	@Override
	public void init() throws ServletException {
		studentservice = new StudentService();
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

		// ==============================================================================

		case "/studentRegister.do":
			// 학생 등록 서비스 호출
			int result = studentservice.registerStudent(request);
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
			List<StudentVo> students = studentservice.getAllStudents();
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
			StudentVo student = studentservice.getStudentById(userId);
			request.setAttribute("student", student);
			nextPage = "/view_admin/studentManager/editStudent.jsp";

			break;

		// ==========================================================================================
		case "/viewStudent.do":
			userId = request.getParameter("user_id");
			student = studentservice.getStudentById(userId);
			request.setAttribute("student", student);
			nextPage = "/view_admin/studentManager/viewStudent.jsp";
			break;
		// ==========================================================================================
		case "/updateStudent.do": // 수정했을때 보여주는 메세지
			boolean isUpdated = studentservice.updateStudent(request);
			String updateMessage = isUpdated ? "학생 정보가 성공적으로 수정되었습니다." : "학생 정보 수정에 실패했습니다.";
			response.sendRedirect(request.getContextPath() + "/student/viewStudentList.do?message="
					+ URLEncoder.encode(updateMessage, "UTF-8"));
			return;
		// ==========================================================================================
		case "/deleteStudent.do": // 학생 정보 삭제
			String studentId = request.getParameter("student_id");
			boolean isDeleted = studentservice.deleteStudent(studentId);
			String deleteMessage = isDeleted ? "학생 정보가 성공적으로 삭제되었습니다." : "학생 정보 삭제에 실패했습니다.";
			response.sendRedirect(request.getContextPath() + "/student/viewStudentList.do?message="
					+ URLEncoder.encode(deleteMessage, "UTF-8"));
			return;

		// ==========================================================================================
		default:
			break;
		}

		// 디스패처 방식 포워딩(재요청)
		RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
		dispatch.forward(request, response);

	} // doHandle 메소드
}
