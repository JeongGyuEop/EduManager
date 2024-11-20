package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import Vo.AssignmentVo;
import Vo.CourseVo;

public class AssignmentDAO {
		
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	DataSource ds;
		
	//컨넥션풀 얻는 생성자
	public AssignmentDAO() {
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
		
	//---------
	// 교수의 해당하는 강의의 과제를 DB에서 조회해 가져오기 위한 함수
	public ArrayList<AssignmentVo> assignmentSearch(String course_id) {

		ArrayList<AssignmentVo> assignmentList = new ArrayList<AssignmentVo>();
		
		try {
			
			con = ds.getConnection();
			
			// SQL 쿼리 실행
            String query = "SELECT * FROM assignment WHERE course_id=?";
            
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, course_id);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
            	 // CourseVo 객체 생성 및 값 설정
                CourseVo course = new CourseVo();
                course.setCourse_id(rs.getString("course_id"));
				
                // AssignmentVo 객체 생성 및 CourseVo 설정
                AssignmentVo assignment = new AssignmentVo();
                assignment.setCourse(course); 
                assignment.setAssignmentId(rs.getInt("assignment_id"));
                assignment.setTitle(rs.getString("title"));
                assignment.setDescription(rs.getString("description"));
                assignment.setDueDate(rs.getDate("due_date").toLocalDate());
                assignment.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
                
                // AssignmentVo 리스트에 추가
                assignmentList.add(assignment);
            }

    		return assignmentList;
    		
		} catch (Exception e) {
			System.out.println("ClassroomDAO의 courseSearch메소드에서 오류 ");
			e.printStackTrace();
		} finally {
			closeResource(); // 자원 해제
		}
		
		return assignmentList;
	}

}
