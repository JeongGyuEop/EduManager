package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class SubmissionDAO {

	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	DataSource ds;
		
	//컨넥션풀 얻는 생성자
	public SubmissionDAO() {
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

	//----------
	// 학생이 제출한 과제를 DB에 등록하기 위한 함수
	public int saveSubmission(String assignmentId, String studentId) {

		 int submissionId = 0;

		    try {
		        con = ds.getConnection();

		        // SQL 쿼리 실행
		        String sql = "INSERT INTO submission (assignment_id, student_id) VALUES (?, ?)";
		        pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

		        pstmt.setInt(1, Integer.parseInt(assignmentId)); // `INT` 타입
		        pstmt.setString(2, studentId); // `VARCHAR` 타입

		        pstmt.executeUpdate();

		        // 자동 생성된 키(submission_id) 가져오기
		        ResultSet rs = pstmt.getGeneratedKeys();
		        if (rs.next()) {
		            submissionId = rs.getInt(1);
		        }
		    } catch (Exception e) {
		        System.out.println("SubmissionDAO의 saveSubmission 메소드에서 오류");
		        e.printStackTrace();
		    } finally {
		        closeResource();
		    }

		    return submissionId;
	}

	//----------
	// 학생이 제출한 파일을 관리하기 위해 DB 연결
	public int saveFile(int submissionId, String filePath, String originalName) {
		
		int result = 0;
		
		String query = "INSERT INTO submission_file (submission_id, file_path, original_name) VALUES (?, ?, ?)";

	    try {

	        con = ds.getConnection();
	    	
	        pstmt = con.prepareStatement(query);
	        pstmt.setInt(1, submissionId);   // 제출 ID (submission 테이블의 FK)
	        pstmt.setString(2, filePath);    // 파일 저장 경로
	        pstmt.setString(3, originalName); // 원본 파일 이름
	        
	        result = pstmt.executeUpdate();
	        
	        return result;
	        
	    } catch (Exception e) {
	        System.out.println("SubmissionDAO의 saveFile 메소드에서 오류");
	        e.printStackTrace();
	    } finally {
	        closeResource();
	    }
	    
	    return result;
		
	}
	
}
