package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import Vo.ClassroomVo;


public class ClassroomDAO {


	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	DataSource ds;
	
	//컨넥션풀 얻는 생성자
	public ClassroomDAO() {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource)ctx.lookup("java:/comp/env/jdbc/edumanager");
			
		}catch(Exception e) {
			System.out.println("커넥션풀 얻기 실패 : " + e.toString());
		}
	}
	
	//자원해제(Connection, PreparedStatment, ResultSet)기능의 메소드 
	private void closeResource() {
		try {
				if(con != null) { con.close(); }
				if(pstmt != null) { pstmt.close(); }
				if(rs != null) { rs.close(); }
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 현재 DB의 majorInformation테이블에 저장된 값들 중 majorCode와 일치하는 열 조회
	public String getMajorNameInfo(String majorCode) {
		
		String result = null;
		
		try {
			
			con = ds.getConnection();
			
			String sql = "select * from majorinformation where majorcode=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, majorCode);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getString("majorname");
			}
			
		} catch (Exception e) {
			System.out.println("ClassroomDAO의 getMajorInfo메소드에서 오류 ");
			e.printStackTrace();
		} finally {
			closeResource();
		}
		
		return result;
	}

	//------------
	// DB의 classroom 테이블에 저장된 모든 열 조회
	public ArrayList getClassroomAllInfo() {
		
		ArrayList list = new ArrayList();
		ClassroomVo vo;
		
		try {
			
			con = ds.getConnection();
			
			String sql = "select * from classroom";
			
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				vo = new ClassroomVo(rs.getString("room_id"),
						 rs.getInt("capacity"),
						 rs.getString("equipment"));
				
				list.add(vo);
			}
			
		} catch (Exception e) {
			System.out.println("ClassroomDAO의 getClassroomAllInfo메소드에서 오류 ");
			e.printStackTrace();
		} finally {
			closeResource(); // 자원 해제
		}
		
		return list;
	}

	
	//------------
	// DB의 classroom 테이블에 교수가 등록한 강의를 저장
	public int registerInsertCourse(String course_name, String majorcode, String room_id, String professor_id) {

		int result = 0;
		String sql = null;
		
		try {
			
			con = ds.getConnection();
			
			sql = "INSERT INTO course (course_name, professor_id, majorcode, room_id) VALUES (?, ?, ?, ?)";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, course_name);
            pstmt.setString(2, professor_id);
            pstmt.setString(3, majorcode);
            pstmt.setString(4, room_id);
            
			result = pstmt.executeUpdate();
			
			return result;
			
		} catch (Exception e) {
			System.out.println("ClassroomDAO의 registerInsertCourse메소드에서 오류 ");
			e.printStackTrace();
		} finally {
			closeResource(); // 자원 해제
		}
		
		return result;
	}

}
