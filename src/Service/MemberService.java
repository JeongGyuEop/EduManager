package Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import Dao.MemberDAO;
import Vo.MemberVo;

// - 부장

//단위 기능별로 메소드를 만들어서 그 기능을 처리하는 클래스
public class MemberService {
	
	//MemberDAO객체의 주소를 저장할 참조변수
	MemberDAO memberDao;
	
	//생성자 - 위 memberDao변수에  new MemberDAO()객체를 만들어서 저장하는 역할
	public MemberService() {
		memberDao = new MemberDAO();
	}

	//회원가입 중앙화면 VIEW 요청
	public String serviceJoinName(HttpServletRequest request) {
		//  members/join.jsp 중앙화면 뷰 주소를 얻어 MemberController로 반환
		return request.getParameter("center");
	}

	//로그인을 하기 위해 아이디 비밀번호를 입력할 수 있는 중앙 화면 VIEW요청
	public String serviceLoginMember() {
		
		return "members/login.jsp";
	}

	//로그인 요청 
	public int serviceUserCheck(HttpServletRequest request) {
		
		//요청한 값 얻기(로그인 요청시 입력한 아이디, 비밀번호 request에서 얻기)
		String login_id = request.getParameter("id");
		String login_pass = request.getParameter("pw");
		
		//check 변수값이 1이면 입력한 아이디, 비밀번호가 DB에 존재함 
		
		//HttpSession메모리 얻기
		HttpSession session = request.getSession();
		//HttpSession메모리에 입력한 아이디 바인딩
		session.setAttribute("id", login_id);
	
		return memberDao.userCheck(login_id, login_pass);
	}	
	
	//로그아웃 요청
	public void serviceLogOut(HttpServletRequest request) {
		
		//기존에 생성했던 HttpSession객체 메모리 얻기
		HttpSession session_ = request.getSession();
		//세션에 저장된 아이디 제거
		session_.removeAttribute("id");
		
		
	}
	
	
}//MemberService
