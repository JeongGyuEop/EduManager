package Service;

import java.sql.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import VO.StudentUserVO;
import DAO.StudentUserDAO;

public class StudentUserService {

    private StudentUserDAO studentUserDAO;

    public StudentUserService() {
        studentUserDAO = new StudentUserDAO();
    }

    // 학생 등록 메서드
    public int registerStudent(HttpServletRequest request) {
        // user 테이블 관련 필드
        String user_id = request.getParameter("user_id");
        String user_pw = request.getParameter("user_pw");
        String user_name = request.getParameter("user_name");
        Date birthDate = Date.valueOf(request.getParameter("birthDate"));
        String gender = request.getParameter("gender");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String role = request.getParameter("role");

        // student_info 테이블 관련 필드
        String student_id = request.getParameter("student_id");
        String majorcode = request.getParameter("majorcode");
        int grade = Integer.parseInt(request.getParameter("grade"));
        Date admission_date = Date.valueOf(request.getParameter("admission_date"));
        String status = request.getParameter("status");

        
        StudentUserVO student = new StudentUserVO(user_id, user_pw, user_name, birthDate, gender, address, phone, email, role, student_id, majorcode, grade, admission_date, status); 
         
        // DAO 메서드 호출하여 데이터 삽입
        int isSuccess = studentUserDAO.insertStudent(student);
        return isSuccess;  // 등록 성공 시 1 반환, 실패 시 0 반환
    }

    // 전체 학생 목록 조회 메서드
    public List<StudentUserVO> getAllStudents() {
        return studentUserDAO.getAllStudents();
    }

    // 특정 학생 정보 조회 메서드
    public StudentUserVO getStudentById(String userId) {
        return studentUserDAO.getStudentById(userId);
    }
    
    //학생 정보 수정 메서드
    public boolean updateStudent(HttpServletRequest request) {
        // request에서 수정할 필드 값들을 가져옴
        String userId = request.getParameter("user_id");
        String userPw = request.getParameter("user_pw");
        String userName = request.getParameter("user_name");
        Date birthDate = Date.valueOf(request.getParameter("birthDate"));
        String gender = request.getParameter("gender");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String role = request.getParameter("role");
        String studentId = request.getParameter("student_id");
        String majorcode = request.getParameter("majorcode");
        int grade = Integer.parseInt(request.getParameter("grade"));
        Date admissionDate = Date.valueOf(request.getParameter("admission_date"));
        String status = request.getParameter("status");

        // VO 객체에 값 설정
        StudentUserVO student = new StudentUserVO(userId, userPw, userName, birthDate, gender, address, phone, email, role, studentId, majorcode, grade, admissionDate, status);
        
        // DAO 메서드 호출하여 업데이트 수행
        return studentUserDAO.updateStudent(student);
    }
        // 학생 정보 삭제 메서드 추가
        public boolean deleteStudent(String studentId) {
            return studentUserDAO.deleteStudent(studentId);
    }
}

