package Service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import Dao.StudentDAO;
import Vo.StudentVo;

// 부장
// - 단위 기능 별로 메소드를 만들어서 그 기능을 처리하는 클래스
public class StudentService {

	// MemberDAO 객체의 주소를 저장할 참조변수
	StudentDAO studentDao;

	// 기본 생성자 - 위 memberDao변수에 new MemberDAO() 객체를 만들어서 저장하는 역할
	public StudentService() {
		studentDao = new StudentDAO();
	}

	
	// ===================================================================================
	// 전체 학생 목록 조회 메서드
	public List<StudentVo> getAllStudents() {
		return studentDao.getAllStudents();
	}

	// ===================================================================================
	// 특정 학생 정보 조회 메서드
	public StudentVo getStudentById(String userId) {
		return studentDao.getStudentById(userId);
	}

	// ===================================================================================
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

		// VO 객체 생성 및 설정
		StudentVo student = new StudentVo(user_id, user_pw, user_name, birthDate, gender, address, phone, email, role,
				student_id, majorcode, grade, admission_date, status);

		// DAO 메서드 호출하여 데이터 삽입
		return studentDao.insertStudent(student);
	}

	// ===================================================================================
	// 학생 정보 수정 메서드
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
		StudentVo student = new StudentVo(userId, userPw, userName, birthDate, gender, address, phone, email, role,
				studentId, majorcode, grade, admissionDate, status);

		// DAO 메서드 호출하여 업데이트 수행
		return studentDao.updateStudent(student);
	}

	// ===================================================================================
	// 학생 정보 삭제 메서드
	public boolean deleteStudent(String studentId) {
		return studentDao.deleteStudent(studentId);
	}
}
