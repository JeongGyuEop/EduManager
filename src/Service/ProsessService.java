package prosessService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import prosessDao.ProsessDao;
import prosessVo.ProsessVo;

public class ProsessService {

	
		ProsessDao prosessDao;
		
	
		public ProsessService() {
			prosessDao = new ProsessDao();
		}
		
		//회원가입 중앙화면 VIEW요청 
		public String serviceJoinName(HttpServletRequest request) {
			return request.getParameter("center");
		}
		
		
		//아이디 중복 체크 요청
		public boolean serviceOverLappedId(HttpServletRequest request) {
			//요청한 아이디 얻기 
			String id = request.getParameter("id");
			
			//사원(MemberDAO) 한테 시킴!!!!!!!!
			//입력한 아이디가 DB에 저장되어 있는 지 확인 하는 작업을 MmemberDAO의 메소드를 호출해서 명령
			return  prosessDao.overlappedId(id);
				    //true 또는 false를 반환 받아 다시 MemberController(사장)에 반환 
			
		}
		
		
		//회원등록(가입) 요청
		public void serviceInsertMember(HttpServletRequest request) {
			//요청한 값 얻기 (회원가입을 위해 입력했던 값들 request에서 얻기)
			String user_id = request.getParameter("id");
			String user_pass = request.getParameter("pass");
			String user_name = request.getParameter("name");
			int user_age = Integer.parseInt(request.getParameter("age"));
			String user_gender = request.getParameter("gender");
			
			String address1 = request.getParameter("address1");
			String address2 = request.getParameter("address2");
			String address3 = request.getParameter("address3");
			String address4 = request.getParameter("address4");
			String address5 = request.getParameter("address5");
			String user_address = address1 + address2 
					              + address3 + address4 + address5;
			
			String user_email = request.getParameter("email");
			String user_tel = request.getParameter("tel");
			String user_hp = request.getParameter("hp");
			
			prosessVo vo = new ProsessVo(user_id, 
									   user_pass, 
									   user_name, 
									   user_age, 
									   user_gender, 
									   user_address, 
									   user_email, 
									   user_tel, 
									   user_hp);	
			//사원아 니가 회원가입 해라 insert~~
			prosessDao.insertMember(vo);
			
			
			
		}
		
		//로그인을 하기 위해 아이디 비밀번호를 입력할수 잇는 중앙 화면 VIEW요청
		public  String serviceLoginMember() {
			
			return "members/login.jsp";
		}
		
		//로그인 요청
		public int serviceUserCheck(HttpServletRequest request) {
			
			//요청한 값 얻기 (로그인 요청시 입력한 아이디,비밀번호 request에서 얻기)
			String login_id = request.getParameter("id");
			String login_pass = request.getParameter("pass");
			
			//check 변수값이 1이면  입력한 아이디,비밀번호가 DB에 존재함
			
			//HttpSession메모리 얻기
			HttpSession session = request.getSession();
			//HttpSession메모리에 입력한 아이디 바인딩
			session.setAttribute("id", login_id);
			
				    
			return prosessDao.userCheck(login_id, login_pass);
		}
		
		
		//로그 아웃 요청
		public void serviceLogOut(HttpServletRequest request) {
			
			//기존에 생성했던 HttpSession객체 메모리 얻기
			HttpSession session_ = request.getSession();
			//세션에 저장된 아이디 제거
			session_.removeAttribute("id");
		}
		
	
}
