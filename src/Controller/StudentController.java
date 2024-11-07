package Controller;

import DAO.StudentDAO;
import VO.StudentVO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

//StudentController.java
@WebServlet("/Student/*")
public class StudentController extends HttpServlet {

 private StudentDAO studentdao;

 @Override
 public void init() throws ServletException {
     // DataSource 설정 (예: context.xml에서 설정한 DataSource)
     DataSource ds = (DataSource) getServletContext().getAttribute("dataSource");
     studentdao = new StudentDAO(ds);  // DAO 객체 초기화
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

     // 웹브라우저로 출력할 출력 스트림 생성
     PrintWriter pw = response.getWriter();

     // 클라이언트가 요청한 2단계 주소 얻기
     String action = request.getPathInfo();
     System.out.println("요청한 2단계 주소: " + action);

     // 요청된 경로에 따라 처리
     if (action.equals("/register")) {
         // 학생 등록 페이지로 포워딩
         request.getRequestDispatcher("/studentRegister.jsp").forward(request, response);
     } else if (action.equals("/list")) {
         // 학생 목록 조회
         List<StudentVO> students = studentdao.getAllStudents();
         request.setAttribute("students", students);
         request.getRequestDispatcher("/studentList.jsp").forward(request, response);
     } else if (action.equals("/add")) {
         // 학생 등록 처리
         String id = request.getParameter("id");  // 학번 파라미터 받기
         String pw = request.getParameter("pw");  // 비밀번호 파라미터 받기
         String deptId = request.getParameter("deptId");
         String studentName = request.getParameter("studentName");
         String birthDate = request.getParameter("birthDate");
         String gender = request.getParameter("gender");
         String address = request.getParameter("address");
         String phone = request.getParameter("phone");
         String email = request.getParameter("email");
         String studentId = request.getParameter("studentId");
         int grade = Integer.parseInt(request.getParameter("grade"));
         int age = Integer.parseInt(request.getParameter("age"));  // 나이 파라미터 받기
         String admissionDate = request.getParameter("admissionDate");

         // StudentVO 객체 생성
         StudentVO student = new StudentVO(id, pw, deptId, studentName, birthDate, gender, address, phone, email, studentId, grade, age, admissionDate);

         // 학생 등록 처리
         boolean isRegistered = studentdao.insertStudent(student);

         if (isRegistered) {
             // 등록 성공 시 목록 페이지로 리다이렉트
             response.sendRedirect(request.getContextPath() + "/Student/list");
         } else {
             // 등록 실패 시 에러 메시지를 JSP에 전달
             request.setAttribute("errorMessage", "학생 등록에 실패했습니다.");
             request.getRequestDispatcher("/studentRegister.jsp").forward(request, response);
         }
     } else if (action.equals("/edit")) {
         // 학생 정보 수정 (기존의 데이터 가져오기)
         String studentId = request.getParameter("studentId");
         StudentVO student = studentdao.getStudentById(studentId);

         if (student != null) {
             request.setAttribute("student", student);
             request.getRequestDispatcher("/studentEdit.jsp").forward(request, response);
         } else {
             // 학생이 없으면 에러 페이지로 이동
             response.sendError(HttpServletResponse.SC_NOT_FOUND, "학생을 찾을 수 없습니다.");
         }
     } else if (action.equals("/update")) {
         // 학생 정보 수정 처리
         String id = request.getParameter("id");
         String pw = request.getParameter("pw");
         String deptId = request.getParameter("deptId");
         String studentName = request.getParameter("studentName");
         String birthDate = request.getParameter("birthDate");
         String gender = request.getParameter("gender");
         String address = request.getParameter("address");
         String phone = request.getParameter("phone");
         String email = request.getParameter("email");
         String studentId = request.getParameter("studentId");
         int grade = Integer.parseInt(request.getParameter("grade"));
         int age = Integer.parseInt(request.getParameter("age"));  // 나이 파라미터 받기
         String admissionDate = request.getParameter("admissionDate");

         StudentVO student = new StudentVO(id, pw, deptId, studentName, birthDate, gender, address, phone, email, studentId, grade, age, admissionDate);

         boolean isUpdated = studentdao.updateStudent(student);
         if (isUpdated) {
             // 수정 성공 시 학생 목록으로 리다이렉트
             response.sendRedirect(request.getContextPath() + "/Student/list");
         } else {
             // 수정 실패 시 에러 메시지 전달
             request.setAttribute("errorMessage", "학생 정보 수정에 실패했습니다.");
             request.getRequestDispatcher("/studentEdit.jsp").forward(request, response);
         }
     } else if (action.equals("/delete")) {
         // 학생 삭제 처리
         String studentId = request.getParameter("studentId");

         boolean isDeleted = studentdao.deleteStudent(studentId);
         if (isDeleted) {
             response.sendRedirect(request.getContextPath() + "/Student/list");
         } else {
             request.setAttribute("errorMessage", "학생 삭제에 실패했습니다.");
             request.getRequestDispatcher("/studentList.jsp").forward(request, response);
         }
     } else {
         // 잘못된 URL 접근 시 에러 처리
         response.sendError(HttpServletResponse.SC_NOT_FOUND);
     }
 }
}
