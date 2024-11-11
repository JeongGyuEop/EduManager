package Dao;

import Vo.StudentUserVO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class StudentUserDAO {

    Connection con;
    PreparedStatement pstmt;
    ResultSet rs;
    DataSource ds;
    
    String query = null;
    
    public StudentUserDAO() {
        try {
            Context ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/edumanager");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
    
    private void closeResources() {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (con != null) con.close();
        } catch (SQLException e) {
        	System.err.println("자원해제 오류:" + e.getMessage());
            e.printStackTrace();
        }
    }

    // 학생 등록 메소드
    public int insertStudent(StudentUserVO studentUser) {
    	int result = 0;
        
        try {
        	con = getConnection();
            query = "INSERT INTO user ("
            		+ "user_id,"
            		+ "user_pw ,"
            		+ "user_name ,"
            		+ "birthDate ,"
            		+ "gender ,"
            		+ "address,"
            		+ "phone,"
            		+ "email,"
            		+ "role) "
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
            pstmt.close();  // user 테이블에 삽입 후 pstmt 닫기
           
            // student_info 테이블에 데이터 삽입
            query = "INSERT INTO student_info ("
            		+ "student_id, "
            		+ "user_id, "
            		+ "majorcode, "
            		+ "grade, "
            		+ "admission_date, "
            		+ "status) "
            		+ "VALUES (?, ?, ?, ?, ?, ?)";
            
            pstmt = con.prepareStatement(query);
            
            pstmt.setString(1, studentUser.getStudent_id());
            pstmt.setString(2, studentUser.getUser_id());  // user 테이블의 user_id와 연계
            pstmt.setString(3, studentUser.getMajorcode());
            pstmt.setInt(4, studentUser.getGrade());
            pstmt.setDate(5, studentUser.getAdmission_date());
            pstmt.setString(6, studentUser.getStatus());
            
            result = pstmt.executeUpdate();
            
            
        } catch (SQLException e) {
        	System.err.println("학생등록오류" + e.getMessage());
            e.printStackTrace();
            
        } finally {
            closeResources(); //자원해제
            
        }
        return result; //성공시 1반환 아니면 0
    }
     // 전체 학생 목록 조회 메서드
        public List<StudentUserVO> getAllStudents() {
            List<StudentUserVO> students = new ArrayList<>();
            String query = "SELECT s.student_id, u.user_id, u.user_name, s.majorcode, s.grade, s.admission_date, s.status "
                         + "FROM student_info s JOIN user u ON s.user_id = u.user_id";

            try {
                con = getConnection();  // DB 연결
                pstmt = con.prepareStatement(query);
                rs = pstmt.executeQuery();

                // 조회 결과를 StudentUserVO 객체에 저장 후 List에 추가
                while (rs.next()) {
                    StudentUserVO student = new StudentUserVO();
                    student.setStudent_id(rs.getString("student_id"));
                    student.setUser_id(rs.getString("user_id"));
                    student.setUser_name(rs.getString("user_name"));
                    student.setMajorcode(rs.getString("majorcode"));
                    student.setGrade(rs.getInt("grade"));
                    student.setAdmission_date(rs.getDate("admission_date"));
                    student.setStatus(rs.getString("status"));
                    students.add(student);  // 조회된 학생 정보를 리스트에 추가
                }
            } catch (SQLException e) {
                System.err.println("전체 학생 목록 조회 중 예외 발생: " + e.getMessage());
                e.printStackTrace();
            } finally {
                closeResources();  // 자원 해제
            }
            return students;  // 학생 목록 반환
        }

       
        
        // 수정 메서드
            public boolean updateStudent(StudentUserVO student) {
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
                    closeResources();
                }

                return isUpdated;
        
    }
            // 삭제 메서드
            public boolean deleteStudent(String studentId) {
                boolean isDeleted = false;
                String selectQuery = "SELECT user_id FROM student_info WHERE student_id = ?";
                String deleteStudentInfoQuery = "DELETE FROM student_info WHERE student_id = ?";
                String deleteUserQuery = "DELETE FROM user WHERE user_id = ?";

                try (Connection con = getConnection()) {
                    con.setAutoCommit(false); // 트랜잭션 시작

                    // student_info에서 user_id 조회
                    String userId = null;
                    try (PreparedStatement pstmtSelect = con.prepareStatement(selectQuery)) {
                        pstmtSelect.setString(1, studentId);
                        ResultSet rs = pstmtSelect.executeQuery();
                        if (rs.next()) {
                            userId = rs.getString("user_id");
                        }
                    }

                    if (userId != null) {
                        // student_info 테이블의 데이터 삭제
                        try (PreparedStatement pstmtDeleteStudentInfo = con.prepareStatement(deleteStudentInfoQuery)) {
                            pstmtDeleteStudentInfo.setString(1, studentId);
                            pstmtDeleteStudentInfo.executeUpdate();
                        }

                        // user 테이블의 연관된 데이터 삭제
                        try (PreparedStatement pstmtDeleteUser = con.prepareStatement(deleteUserQuery)) {
                            pstmtDeleteUser.setString(1, userId);
                            pstmtDeleteUser.executeUpdate();
                        }

                        con.commit(); // 성공 시 커밋
                        isDeleted = true;
                    }
                } catch (SQLException e) {
                    System.err.println("학생 삭제 중 예외 발생: " + e.getMessage());
                    e.printStackTrace();
                    try {
                        con.rollback(); // 오류 시 롤백
                    } catch (SQLException rollbackEx) {
                        rollbackEx.printStackTrace();
                    }
                } finally {
                    closeResources(); // 자원 해제
                }
                return isDeleted;
            }


    
}

