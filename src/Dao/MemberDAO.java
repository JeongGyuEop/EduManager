package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	String query = null;
	
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
	
	//================================================================================================================== 
	//con = getConnection();때문에 새로운 메소드 추가함
	 private Connection getConnection() throws SQLException {
	        return ds.getConnection();
	    }
	//====================================================================================================================
	
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

	
	//=========================================================================================================
	// 학생 등록 
	 public int insertStudent(MemberVo studentUser) {
	        int result = 0;
	        try {
	            con = getConnection();
	            query = "INSERT INTO user (user_id, user_pw, user_name, birthDate, gender, address, phone, email, role) "
	                  + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	            pstmt = con.prepareStatement(query);
	            pstmt.setString(1, studentUser.getUser_id());
	            pstmt.setString(2, studentUser.getUser_pw());
	            pstmt.setString(3, studentUser.getUser_name());
	            pstmt.setDate(4, studentUser.getBirthDate());
	            pstmt.setString(5, studentUser.getGender());
	            pstmt.setString(6, studentUser.getAddress());
	            pstmt.setString(7, studentUser.getPhone());
	            pstmt.setString(8, studentUser.getEmail());
	            pstmt.setString(9, studentUser.getRole());
	            result = pstmt.executeUpdate();

	            query = "INSERT INTO student_info (student_id, user_id, majorcode, grade, admission_date, status) "
	                  + "VALUES (?, ?, ?, ?, ?, ?)";
	            pstmt = con.prepareStatement(query);
	            pstmt.setString(1, studentUser.getStudent_id());
	            pstmt.setString(2, studentUser.getUser_id());
	            pstmt.setString(3, studentUser.getMajorcode());
	            pstmt.setInt(4, studentUser.getGrade());
	            pstmt.setDate(5, studentUser.getAdmission_date());
	            pstmt.setString(6, studentUser.getStatus());
	            result = pstmt.executeUpdate();
	        } catch (SQLException e) {
	            System.err.println("학생등록오류: " + e.getMessage());
	            e.printStackTrace();
	        } finally {
	            closeResource();
	        }
	        return result;
	    }
	//=========================================================================================================
	 //전체 학생 조회
	    public List<MemberVo> getAllStudents() {
	        List<MemberVo> students = new ArrayList<>();
	        String query = "SELECT s.student_id, u.user_id, u.user_name, s.majorcode, s.grade, s.admission_date, s.status "
	                     + "FROM student_info s JOIN user u ON s.user_id = u.user_id";
	        try {
	            con = getConnection();
	            pstmt = con.prepareStatement(query);
	            rs = pstmt.executeQuery();

	            while (rs.next()) {
	            	MemberVo student = new MemberVo();
	                student.setStudent_id(rs.getString("student_id"));
	                student.setUser_id(rs.getString("user_id"));
	                student.setUser_name(rs.getString("user_name"));
	                student.setMajorcode(rs.getString("majorcode"));
	                student.setGrade(rs.getInt("grade"));
	                student.setAdmission_date(rs.getDate("admission_date"));
	                student.setStatus(rs.getString("status"));
	                students.add(student);
	            }
	        } catch (SQLException e) {
	            System.err.println("전체 학생 목록 조회 중 예외 발생: " + e.getMessage());
	            e.printStackTrace();
	        } finally {
	            closeResource();
	        }
	        return students;
	    }
	  //=========================================================================================================
	    // 특정 학생 검색해서 조회이긴한데 구현은 안됨. 하지만 있어도 상관없기에 일단 넣어둠 나중에 추가할 수 있으면 추가하면 될 듯
	    public MemberVo getStudentById(String userId) {
	        MemberVo student = null;
	        String query = "SELECT u.user_id, u.user_pw, u.user_name, u.birthDate, u.gender, u.address, u.phone, "
	                     + "u.email, u.role, s.student_id, s.majorcode, s.grade, s.admission_date, s.status "
	                     + "FROM user u JOIN student_info s ON u.user_id = s.user_id WHERE u.user_id = ?";
	        try {
	            con = getConnection();
	            pstmt = con.prepareStatement(query);
	            pstmt.setString(1, userId);
	            rs = pstmt.executeQuery();

	            if (rs.next()) {
	                student = new MemberVo();
	                student.setUser_id(rs.getString("user_id"));
	                student.setUser_pw(rs.getString("user_pw"));
	                student.setUser_name(rs.getString("user_name"));
	                student.setBirthDate(rs.getDate("birthDate"));
	                student.setGender(rs.getString("gender"));
	                student.setAddress(rs.getString("address"));
	                student.setPhone(rs.getString("phone"));
	                student.setEmail(rs.getString("email"));
	                student.setRole(rs.getString("role"));
	                student.setStudent_id(rs.getString("student_id"));
	                student.setMajorcode(rs.getString("majorcode"));
	                student.setGrade(rs.getInt("grade"));
	                student.setAdmission_date(rs.getDate("admission_date"));
	                student.setStatus(rs.getString("status"));
	            }
	        } catch (SQLException e) {
	            System.err.println("특정 학생 조회 중 예외 발생: " + e.getMessage());
	            e.printStackTrace();
	        } finally {
	            closeResource();
	        }
	        return student;
	    }
	  //=========================================================================================================
	    // 학생 정보 수정
	    public boolean updateStudent(MemberVo student) {
	        boolean isUpdated = false;
	        String query = "UPDATE user u JOIN student_info s ON u.user_id = s.user_id SET "
	                     + "u.user_pw = ?, u.user_name = ?, u.birthDate = ?, u.gender = ?, "
	                     + "u.address = ?, u.phone = ?, u.email = ?, u.role = ?, "
	                     + "s.grade = ?, s.admission_date = ?, s.status = ? "
	                     + "WHERE s.student_id = ?";
	        try {
	            con = getConnection();
	            pstmt = con.prepareStatement(query);
	            pstmt.setString(1, student.getUser_pw());
	            pstmt.setString(2, student.getUser_name());
	            pstmt.setDate(3, student.getBirthDate());
	            pstmt.setString(4, student.getGender());
	            pstmt.setString(5, student.getAddress());
	            pstmt.setString(6, student.getPhone());
	            pstmt.setString(7, student.getEmail());
	            pstmt.setString(8, student.getRole());
	            pstmt.setInt(9, student.getGrade());
	            pstmt.setDate(10, student.getAdmission_date());
	            pstmt.setString(11, student.getStatus());
	            pstmt.setString(12, student.getStudent_id());

	            int rowsUpdated = pstmt.executeUpdate();
	            isUpdated = rowsUpdated > 0;
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            closeResource();
	        }
	        return isUpdated;
	    }
	  //=========================================================================================================
	    // 학생 정보 삭제
	    public boolean deleteStudent(String studentId) {
	        boolean isDeleted = false;
	        String selectQuery = "SELECT user_id FROM student_info WHERE student_id = ?";
	        String deleteStudentInfoQuery = "DELETE FROM student_info WHERE student_id = ?";
	        String deleteUserQuery = "DELETE FROM user WHERE user_id = ?";

	        try {
	            con = getConnection();
	            con.setAutoCommit(false);
	            String userId = null;

	            try (PreparedStatement pstmtSelect = con.prepareStatement(selectQuery)) {
	                pstmtSelect.setString(1, studentId);
	                ResultSet rs = pstmtSelect.executeQuery();
	                if (rs.next()) {
	                    userId = rs.getString("user_id");
	                }
	            }

	            if (userId != null) {
	                try (PreparedStatement pstmtDeleteStudentInfo = con.prepareStatement(deleteStudentInfoQuery)) {
	                    pstmtDeleteStudentInfo.setString(1, studentId);
	                    pstmtDeleteStudentInfo.executeUpdate();
	                }
	                try (PreparedStatement pstmtDeleteUser = con.prepareStatement(deleteUserQuery)) {
	                    pstmtDeleteUser.setString(1, userId);
	                    pstmtDeleteUser.executeUpdate();
	                }
	                con.commit();
	                isDeleted = true;
	            }
	        } catch (SQLException e) {
	            System.err.println("학생 삭제 중 예외 발생: " + e.getMessage());
	            e.printStackTrace();
	            try {
	                con.rollback();
	            } catch (SQLException rollbackEx) {
	                rollbackEx.printStackTrace();
	            }
	        } finally {
	            closeResource();
	        }
	        return isDeleted;
	    }
	
	
	 }// MemberDAO 클래스