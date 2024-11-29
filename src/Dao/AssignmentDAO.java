package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import Vo.AssignmentVo;
import Vo.CourseVo;
import Vo.StudentVo;
import Vo.SubmissionVo;

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
                assignment.setDueDate(rs.getDate("due_date"));
                assignment.setCreatedDate(rs.getTimestamp("created_date"));
                
                // AssignmentVo 리스트에 추가
                assignmentList.add(assignment);
            }

    		return assignmentList;
    		
		} catch (Exception e) {
			System.out.println("AssignmentDAO의 assignmentSearch메소드에서 오류 ");
			e.printStackTrace();
		} finally {
			closeResource(); // 자원 해제
		}
		
		return assignmentList;
	}

	//------------
	// 교수가 자신의 각 강의에 과제를 DB에 등록하기 위해 호출하는 함수 
	public int createAssignment(AssignmentVo assignmentVo) {
		
		int result = 0;
		
		try {
			
			con = ds.getConnection();
			
			// SQL 쿼리 실행
			String sql = "INSERT INTO assignment (course_id, title, description, due_date, created_date) "
	                   + "VALUES (?, ?, ?, ?, ?)";
            
            pstmt = con.prepareStatement(sql);
            
            // PreparedStatement에 값 설정
            pstmt.setString(1, assignmentVo.getCourse().getCourse_id());
            pstmt.setString(2, assignmentVo.getTitle());
            pstmt.setString(3, assignmentVo.getDescription());
            pstmt.setDate(4, assignmentVo.getDueDate()); // java.sql.Date 설정
            pstmt.setTimestamp(5, new Timestamp(System.currentTimeMillis())); // 현재 시간 설정
            result = pstmt.executeUpdate();
            
    		return result;
    		
		} catch (Exception e) {
			System.out.println("AssignmentDAO의 createAssignment메소드에서 오류 ");
			e.printStackTrace();
		} finally {
			closeResource(); // 자원 해제
		}
		
		
		return result;
	}

	//-----------
	// 교수가 자신의 각 강의에 등록된 과제를 DB에서 삭제하기 위해 호출되는 함수
	public int deleteAssignment(String assignment_id) {
		
		int result = 0;
		
		try {
			
			con = ds.getConnection();
			
			// SQL 쿼리 실행
			String sql = "DELETE FROM assignment WHERE assignment_id=?";
            
            pstmt = con.prepareStatement(sql);
            
            // PreparedStatement에 값 설정
            pstmt.setString(1, assignment_id);
            result = pstmt.executeUpdate();
            
    		return result;
    		
		} catch (Exception e) {
			System.out.println("AssignmentDAO의 deleteAssignment메소드에서 오류 ");
			e.printStackTrace();
		} finally {
			closeResource(); // 자원 해제
		}

		return 0;
	}

	//-----------
	// 교수가 자신의 각 강의에 등록된 과제를 DB에 수정요청을 위해 호출되는 함수
	public int updateAssignment(AssignmentVo assignmentVo) {
		int result = 0;
		
		try {

			con = ds.getConnection();
			
			// SQL 쿼리 실행
			String sql = "UPDATE assignment "
						+ "SET title=?, description=?, due_date=? "
						+ "WHERE assignment_id=?";
            
            pstmt = con.prepareStatement(sql);
            
            // PreparedStatement에 값 설정
            pstmt.setString(1, assignmentVo.getTitle());
            pstmt.setString(2, assignmentVo.getDescription());
            pstmt.setDate(3, assignmentVo.getDueDate());
            pstmt.setInt(4, assignmentVo.getAssignmentId());
            result = pstmt.executeUpdate();
            
    		return result;
    		
		} catch (Exception e) {
			System.out.println("AssignmentDAO의 updateAssignment메소드에서 오류 ");
			e.printStackTrace();
		} finally {
			closeResource(); // 자원 해제
		}

		return result;
	}
	
	//----------
	// 교수가 학생들이 제출한 과제를 조회하기 위해 DB에 연결하는 함수
	public List<SubmissionVo> getSubmission(String assignmentId) {
	    String sql = "SELECT s.submission_id, s.assignment_id, s.student_id, si.user_id, u.user_name, " +
	                 "s.submitted_date, s.feedback, s.grade, sf.file_id, sf.file_name, sf.original_name " +
	                 "FROM submission s " +
	                 "LEFT JOIN student_info si ON s.student_id = si.student_id " +
	                 "LEFT JOIN user u ON si.user_id = u.user_id " +
	                 "LEFT JOIN submission_file sf ON s.submission_id = sf.submission_id " +
	                 "WHERE s.assignment_id = ?";
	    
	    List<SubmissionVo> submissions = new ArrayList<SubmissionVo>();
	    
	    try {
	        con = ds.getConnection();
	        pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, assignmentId);
	        rs = pstmt.executeQuery();
	        
	        while (rs.next()) {
	            // SubmissionVo 객체 생성 및 설정
	            SubmissionVo submission = new SubmissionVo();
	            submission.setSubmissionId(rs.getInt("submission_id"));
	            
	            // StudentVo 객체 생성 및 설정
	            StudentVo student = new StudentVo();
	            student.setStudent_id(rs.getString("student_id")); // student_id 설정
	            student.setUser_name(rs.getString("user_name")); // user_name 설정
	            
	            // AssignmentVo 객체 생성 (필요 시)
	            AssignmentVo assignment = new AssignmentVo();
	            assignment.setAssignmentId(rs.getInt("assignment_id"));
	            
	            submission.setAssignment(assignment);
	            submission.setStudent(student);
	            submission.setSubmittedDate(rs.getTimestamp("submitted_date"));
	            submission.setFeedback(rs.getString("feedback"));
	            submission.setGrade(rs.getInt("grade"));
	            submission.setFileId(rs.getInt("file_id"));
	            submission.setFileName(rs.getString("file_name"));
	            submission.setOriginalName(rs.getString("original_name"));

	            submissions.add(submission);
	        }
	    } catch (Exception e) {
	        System.out.println("AssignmentDAO의 getSubmission 메소드에서 오류");
	        e.printStackTrace();
	    }
	    
	    return submissions;
	}


}
