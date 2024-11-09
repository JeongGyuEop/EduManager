package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
	public Map<String, String> userCheck(String login_id, String login_pass) {
		
		Map<String, String> userInfo = new HashMap<String, String>();
		
		try {
			con = ds.getConnection();
			
			String sql = "select * from user where user_id=?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, login_id);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) { // 입력한 아이디로 조회된 결과가 있을 경우
	            // 입력된 비밀번호와 DB 비밀번호 비교
	            if (login_pass.equals(rs.getString("user_pw"))) {
	                userInfo.put("role", rs.getString("role"));
	                userInfo.put("name", rs.getString("user_name"));
	                userInfo.put("check", "1"); // 아이디와 비밀번호 일치 시 "1" 반환
	            } else {
	                userInfo.put("check", "0"); // 비밀번호 불일치 시 "0" 반환
	            }
	        } else {
	            userInfo.put("check", "-1"); // 아이디가 DB에 없는 경우 "-1" 반환
	        }
			
		} catch (Exception e) {
			System.out.println("MemberDAO의 userCheck메소드 오류 :  " + e);
			e.printStackTrace();
		}finally {
			closeResource();
		}
		
		return userInfo; //MemberService(부장)에게 결과 반환 
	}

	



}// MemberDAO 클래스