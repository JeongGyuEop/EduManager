package DAO;

import VO.StudentVO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class StudentDAO {
    
	private Connection con;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private DataSource ds;


  //컨넥션풀 얻는 생성자
  	public StudentDAO() {
  		try {
  			Context ctx = new InitialContext();
  			ds = (DataSource)ctx.lookup("java:/comp/env/jdbc/jspbeginner");
  			
  		}catch(Exception e) {
  			System.out.println("커넥션풀 얻기 실패 : " + e.toString());
  		}
  	}
  	//자원해제(Connection, PreparedStatment, ResultSet)기능의 메소드 
  	private void closeResource() throws Exception {
  		if(con != null) { con.close(); }
  		if(pstmt != null) { pstmt.close(); }
  		if(rs != null) { rs.close(); }
  	}
  	
  	
  	
  	
  	
    // DataSource를 통해 Connection 초기화
    public StudentDAO(DataSource ds) {
        this.ds = ds;
    }

    
    
    // Connection 가져오기
    private Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

  
    
    // 학생 정보 삽입 메서드
    public boolean insertStudent(StudentVO student) {
        String query = "INSERT INTO students (deptId, studentName, birthDate, gender, address, phone, email, studentId, grade, admissionDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            con = getConnection();
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, student.getDeptId());
            pstmt.setString(2, student.getStudentName());
            pstmt.setDate(3, Date.valueOf(student.getBirthDate()));
            pstmt.setString(4, student.getGender());
            pstmt.setString(5, student.getAddress());
            pstmt.setString(6, student.getPhone());
            pstmt.setString(7, student.getEmail());
            pstmt.setString(8, student.getStudentId());
            pstmt.setInt(9, student.getGrade());
            pstmt.setDate(10, Date.valueOf(student.getAdmissionDate()));
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return false;
    }

    // 모든 학생 조회 메서드
    public List<StudentVO> getAllStudents() {
        List<StudentVO> students = new ArrayList<>();
        String query = "SELECT * FROM students";
        try {
            con = getConnection();
            pstmt = con.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                StudentVO student = new StudentVO(
                    rs.getString("id"),
                    rs.getString("pw"),
                    rs.getString("deptId"),
                    rs.getString("studentName"),
                    rs.getString("birthDate"),
                    rs.getString("gender"),
                    rs.getString("address"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getString("studentId"),
                    rs.getInt("grade"),
                    rs.getInt("age"),
                    rs.getString("admissionDate")
                );
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return students;
    }

    // 특정 학번으로 학생 정보 조회
    public StudentVO getStudentById(String studentId) {
        String query = "SELECT * FROM students WHERE studentId = ?";
        try {
            
        	con = getConnection();
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, studentId);
            rs = pstmt.executeQuery();
           
            if (rs.next()) {
                return new StudentVO(
                    rs.getString("id"),
                    rs.getString("pw"),
                    rs.getString("deptId"),
                    rs.getString("studentName"),
                    rs.getString("birthDate"),
                    rs.getString("gender"),
                    rs.getString("address"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getString("studentId"),
                    rs.getInt("grade"),
                    rs.getInt("age"),
                    rs.getString("admissionDate")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return null;
    }

    // 학생 정보 업데이트 메서드
    public boolean updateStudent(StudentVO student) {
        String query = "UPDATE students SET deptId = ?, "
        		+ "studentName = ?, "
        		+ "birthDate = ?, "
        		+ "gender = ?, "
        		+ "address = ?, "
        		+ "phone = ?, "
        		+ "email = ?, "
        		+ "grade = ?, "
        		+ "admissionDate = ? "
        		+ "WHERE studentId = ?";
        try {
            
        	con = getConnection();
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, student.getDeptId());
            pstmt.setString(2, student.getStudentName());
            pstmt.setDate(3, Date.valueOf(student.getBirthDate()));
            pstmt.setString(4, student.getGender());
            pstmt.setString(5, student.getAddress());
            pstmt.setString(6, student.getPhone());
            pstmt.setString(7, student.getEmail());
            pstmt.setInt(8, student.getGrade());
            pstmt.setDate(9, Date.valueOf(student.getAdmissionDate()));
            pstmt.setString(10, student.getStudentId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return false;
    }

    // 학생 정보 삭제 메서드
    public boolean deleteStudent(String studentId) {
        String query = "DELETE FROM students WHERE studentId = ?";
        
        try {
            con = getConnection();
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, studentId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return false;
    }

    // 자원 해제 메서드
    private void closeResources() {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

