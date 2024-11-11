package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;


import Vo.MemberVo;

//MVC 중에서 M을 얻기 위한 클래스 

//DB와 연결하여 비즈니스로직 처리하는 클래스
public class MemberDAO {
	
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	DataSource ds;
	
	//커넥션 풀 얻는 생성자 
	public MemberDAO() {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource)ctx.lookup("java:/comp/env/jdbc/edumanager");
		} catch (Exception e) {
			System.out.println("커넥션풀 얻기 실패 : "+ e);
		} 
				
	}//생성자 닫음

	//자원해제(Connection, PreparedStatment, ResultSet) 기능의 메소드
	private void closeResource(){
		try {
			if(con != null) {con.close();}
			if(pstmt != null) {pstmt.close();}
			if(rs != null) {rs.close();}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	//로그인 요청시 입력한 아이디, 비밀번호가 DB의 member테이블에 있는지 확인
	public String userCheck(String login_id, String login_pass) {
		
		String check = null;
		
		try {
			con = ds.getConnection();
			
			String sql = "select * from user where user_id=?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, login_id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {//입력한 아이디로 조회한 행이 있으면?(아이디가 DB에 있으면?)
				//입력한 비밀번호와 조회된 비밀전호를 비교해서 있으면?(비밀번호가 있으면?)
				if(login_pass.equals(rs.getString("user_pw"))) {
					return rs.getString("role");					
				}else {//입력한 아이디는 존재하나 비밀번호가 없으면?
					check = "0";
				}
				
			}else {//입력한 아이디가 DB의 테이블에 없다. (아이디가 DB에 없으면?)
				check = "-1";
			}
			
		} catch (Exception e) {
			System.out.println("MemberDAO의 userCheck메소드 오류 :  " + e);
			e.printStackTrace();
		}finally {
			closeResource();
		}
		
		return check; //MemberService(부장)에게 결과 반환 
	}

	



}// MemberDAO 클래스
