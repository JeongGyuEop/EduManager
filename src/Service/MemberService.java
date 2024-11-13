package Service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import Dao.MemberDAO;
import Vo.MemberVo;

// 부장
// - 단위 기능 별로 메소드를 만들어서 그 기능을 처리하는 클래스
public class MemberService {

	// MemberDAO 객체의 주소를 저장할 참조변수
	MemberDAO memberDao;

	// 기본 생성자 - 위 memberDao변수에 new MemberDAO() 객체를 만들어서 저장하는 역할
	public MemberService() {
		memberDao = new MemberDAO();
	}

	// 로그인 요청
	public Map<String, String> serviceUserCheck(HttpServletRequest request) {

		// 요청한 값 얻기(로그인 요청시 입력한 아이디, 비밀번호 request에서 얻기)
		String login_id = request.getParameter("id");
		String login_pass = request.getParameter("pass");

		Map<String, String> userInfo = memberDao.userCheck(login_id, login_pass);

		// HttpSession메모리 얻기
		HttpSession session = request.getSession();

		// check 값이 "1"인 경우에만 role과 name을 세션에 저장
		if ("1".equals(userInfo.get("check"))) {
			// HttpSession메모리에 입력한 아이디 바인딩
			session.setAttribute("role", userInfo.get("role"));
			session.setAttribute("name", userInfo.get("name"));
			session.setAttribute("id", login_id);
		}

		return userInfo;
	}

	// -------
	// 로그아웃 요청
	public void serviceLogOut(HttpServletRequest request) {

		// 기존에 생성했던 HttpSession 객체 메모리 얻기
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute("userId");
			session.removeAttribute("userName");
			session.invalidate(); // 세션 무효화

		}

	}

	// ===================================================================================
	// 전체 학생 목록 조회 메서드
	public List<MemberVo> getAllStudents() {
		return memberDao.getAllStudents();
	}

	// ===================================================================================
	// 특정 학생 정보 조회 메서드
	public MemberVo getStudentById(String userId) {
		return memberDao.getStudentById(userId);
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
		MemberVo student = new MemberVo(user_id, user_pw, user_name, birthDate, gender, address, phone, email, role,
				student_id, majorcode, grade, admission_date, status);

		// DAO 메서드 호출하여 데이터 삽입
		return memberDao.insertStudent(student);
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
		MemberVo student = new MemberVo(userId, userPw, userName, birthDate, gender, address, phone, email, role,
				studentId, majorcode, grade, admissionDate, status);

		// DAO 메서드 호출하여 업데이트 수행
		return memberDao.updateStudent(student);
	}

	// ===================================================================================
	// 학생 정보 삭제 메서드
	public boolean deleteStudent(String studentId) {
		return memberDao.deleteStudent(studentId);
	}
	
	//============================================================================
	// 학생이 본인 정보를 업데이트를 하는 메서드
	
	public boolean updateMyInfo(HttpServletRequest request) {
	    HttpSession session = request.getSession();
	    String sessionUserId = (String) session.getAttribute("id"); // 세션에서 사용자 ID 가져오기
	    String role = (String) session.getAttribute("role"); // 세션에서 사용자 역할 가져오기

	    // 로그인된 학생 본인만 수정할 수 있도록 제한
	    String userId = request.getParameter("user_id");
	    if (!"학생".equals(role) || !sessionUserId.equals(userId)) {
	        // 권한이 없거나 다른 학생의 정보를 수정하려고 할 때 false 반환
	        return false;
	    }

	    // 수정할 필드 값 가져오기
	    String userPw = request.getParameter("user_pw");
	    //==
	    if (userPw == null || userPw.trim().isEmpty()) {
	        // 비밀번호가 비어 있는 경우 false 반환
	        return false;
	    }
	    //==
	    String phone = request.getParameter("phone");
	    String email = request.getParameter("email");
	    String address = request.getParameter("address");

	    // VO 객체에 필요한 값 설정
	    MemberVo student = new MemberVo();
	    student.setUser_id(userId);
	    student.setUser_pw(userPw);
	    student.setPhone(phone);
	    student.setEmail(email);
	    student.setAddress(address);

	    // DAO 메서드를 호출하여 본인 정보 업데이트
	    return memberDao.updateStudentInfo(student);
	}

	
	
}
