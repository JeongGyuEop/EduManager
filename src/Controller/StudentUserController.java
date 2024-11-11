package Controller;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Service.StudentUserService;
import VO.StudentUserVO;
import java.util.List;

@WebServlet("/studentUser/*")
public class StudentUserController extends HttpServlet {

    private StudentUserService studentUserService;

    @Override
    public void init() throws ServletException {
        studentUserService = new StudentUserService();
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

    protected void doHandle(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        
        String action = request.getPathInfo();
        System.out.println("요청한 2단계 주소: " + action);
        
        // 기본적으로 요청 재전송 경로 설정
        String nextPage = "/studentRegister.jsp";
        
            if (action.equals("/studentRegister.do")) {
                // 학생 등록 서비스 호출
                int result = studentUserService.registerStudent(request);
                request.setAttribute("result", result);
                
                if (result == 1) {
                    request.setAttribute("message", "학생 정보가 성공적으로 추가되었습니다.");
                } else {
                    request.setAttribute("message", "학생 정보를 추가하는 데 실패했습니다.");
                }
                nextPage = "/studentRegister.jsp";

            } else if (action.equals("/viewStudentList.do")) {
                
            	// 전체 학생 목록 조회
                List<StudentUserVO> students = studentUserService.getAllStudents();
                request.setAttribute("students", students);
                nextPage = "/viewStudentList.jsp"; // 전체 학생 목록을 표시할 JSP 페이지
                
            } else if (action.equals("/viewStudent.do")) {
                // 특정 학생 정보 조회
                String userId = request.getParameter("user_id");
                StudentUserVO student = studentUserService.getStudentById(userId);
                request.setAttribute("student", student);
                nextPage = "/viewStudent.jsp"; // 특정 학생 상세 정보를 표시할 JSP 페이지
            }else if (action.equals("/editStudent.do")) {
                String userId = request.getParameter("user_id");
                StudentUserVO student = studentUserService.getStudentById(userId);
                request.setAttribute("student", student);
                nextPage = "/editStudent.jsp";
                
            } else if (action.equals("/updateStudent.do")) {
                boolean isUpdated = studentUserService.updateStudent(request);
                String message;
                if (isUpdated) {
                    message = "학생 정보가 성공적으로 수정되었습니다.";
                } else {
                    message = "학생 정보 수정에 실패했습니다.";
                }
                response.sendRedirect(request.getContextPath() + "/studentUser/viewStudentList.do?message=" + URLEncoder.encode(message, "UTF-8"));
                return;
            }else if (action.equals("/deleteStudent.do")) {
                String studentId = request.getParameter("student_id");
                boolean isDeleted = studentUserService.deleteStudent(studentId);
                String message;
                if (isDeleted) {
                    message = "학생 정보가 성공적으로 삭제되었습니다.";
                } else {
                    message = "학생 정보 삭제에 실패했습니다.";
                }
                response.sendRedirect(request.getContextPath() + "/studentUser/viewStudentList.do?message=" + URLEncoder.encode(message, "UTF-8"));
                return;
            }
            

        // 결과 페이지로 포워드
        RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
        dispatch.forward(request, response);
    }
}
