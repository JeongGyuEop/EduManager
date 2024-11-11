package professorDao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import professorVo.ProfessorVo;

public class ProfessorDao {

	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	DataSource ds;

	//컨넥션풀 얻는 생성자
		public ProfessorDao() {
			try {
				Context ctx = new InitialContext();
				ds = (DataSource)ctx.lookup("java:/comp/env/jdbc/edumanager");
				
			}catch(Exception e) {
				System.out.println("커넥션풀 얻기 실패 : " + e.toString());
			}
		}
		
	// 자원해제(Connection, PreparedStatment, ResultSet)기능의 메소드
	private void closeResource() {
		try {
			if (rs != null) {rs.close();}
			if (pstmt != null) {pstmt.close();}
			if (con != null) {con.close();}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	// 새 회원 추가
		 public int insertProfessor(ProfessorVo vo) {
		        
			 int result = 0;  // 기본값은 실패(false)
			 
		      
		        try {
		        	
		        con = ds.getConnection();
		        	
		        con.setAutoCommit(false); 
		        	
		        pstmt = con.prepareStatement( "INSERT INTO user (user_id, user_pw, user_name, birthDate, gender, "
		        								+ "address, phone, email, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");    	        	
		        	
	             pstmt.setString(1, vo.getUser_id());
	             pstmt.setString(2, vo.getUser_pw());
	             pstmt.setString(3, vo.getP_name());
	             pstmt.setDate(4, vo.getP_brithDate());
	             pstmt.setString(5, vo.getP_Gender());
	             pstmt.setString(6, vo.getP_address());
	             pstmt.setString(7, vo.getP_phone());
	             pstmt.setString(8, vo.getP_email());
	             pstmt.setString(9, "교수");

	             result = pstmt.executeUpdate();
	             
	             
		         pstmt = con.prepareStatement( "INSERT INTO professor_info (professor_id, user_id, majorcode, "
									+ "employment_date) VALUES (?, ?, ?, ?)");
		           
	         // 두 번째 INSERT 문 (professor_info 테이블)
	             pstmt.setString(1, vo.getProfessor_id());
	             pstmt.setString(2, vo.getUser_id());
	             pstmt.setString(3, vo.getMajorcode());
	             pstmt.setDate(4, vo.getP_employDate());

	             result += pstmt.executeUpdate();
		                 
	             // 모든 SQL문이 성공하면 커밋
	              con.commit();
	             
	           
	             
		        } catch (SQLException e) {
		            System.out.println("ProsessDao의 insertProfessor 메소드에서 오류 발생");
		            e.printStackTrace();
		            try {
		                if (con != null) {
		                    con.rollback();  // Rollback transaction on error
		                }
		            } catch (SQLException rollbackEx) {
		                rollbackEx.printStackTrace();
		            }
		        } finally {
		        	closeResource();
		        }
				return result;

		    }

 /*
	 // 교수 조회
		 public List<ProfessorVo> getProfessorList(String profId, String majorcode) throws SQLException {
			   
			 List<ProfessorVo> professorList = new ArrayList<>();

			    // SQL query with filtering conditions based on profId and majorcode
			    String query = "SELECT p.professor_id, u.user_name, u.birthDate, u.gender, u.address, "
			            + "u.phone, p.majorcode, u.email, p.employment_date "
			            + "FROM user u "
			            + "JOIN professor_info p ON u.user_id = p.user_id "
			            + "WHERE 1=1 ";

			    // Adding filtering conditions based on input parameters
			    if (profId != null && !profId.trim().isEmpty()) {
			        query += "AND p.professor_id = ? ";
			    }
			    if (majorcode != null && !majorcode.trim().isEmpty()) {
			        query += "AND p.majorcode = ? ";
			    }

			    // Prepare statement
			    PreparedStatement pstmt = con.prepareStatement(query);

			    int index = 1;

			    // Set the parameters based on the conditions
			    if (profId != null && !profId.trim().isEmpty()) {
			        pstmt.setString(index++, profId);
			    }
			    if (majorcode != null && !majorcode.trim().isEmpty()) {
			        pstmt.setString(index++, majorcode);
			    }

			    ResultSet rs = pstmt.executeQuery();

			    while (rs.next()) {
			        ProfessorVo vo = new ProfessorVo();
			        vo.setProfessor_id(rs.getString("professor_id"));
			        vo.setP_name(rs.getString("user_name"));
			        vo.setP_brithDate(rs.getDate("birthDate"));
			        vo.setP_Gender(rs.getString("gender"));
			        vo.setP_address(rs.getString("address"));
			        vo.setP_phone(rs.getString("phone"));
			        vo.setMajorcode(rs.getString("majorcode"));
			        vo.setP_email(rs.getString("email"));
			        vo.setP_employDate(rs.getDate("employment_date"));
			        
			        professorList.add(vo);
			    }

			    rs.close();
			    pstmt.close();
			    return professorList;
			}
	
		 
		 
	 // 교수 정보 수정 메서드
	    public void updateProfessor(ProfessorVo professor) throws SQLException {
	        String query = "UPDATE professor_info SET majorcode = ?, employment_date = ? WHERE professor_id = ?";
	        
	        con = ds.getConnection();
	       
	       PreparedStatement pstmt = con.prepareStatement(query);
	            pstmt.setString(1, professor.getMajorcode());
	            pstmt.setDate(2, professor.getP_employDate());
	            pstmt.setString(3, professor.getProfessor_id());
	            pstmt.executeUpdate();
	    }
	 
	 
	 
	//교수 정보 삭제 메서드
	 public void deleteProfessor(String professorId) throws SQLException {
	     
		 String query = "DELETE FROM professor_info WHERE professor_id = ?";

	     con = ds.getConnection(); 
	     
	     pstmt = con.prepareStatement(query);
	         pstmt.setString(1, professorId);
	         
	         pstmt.executeUpdate();
	     
	 }
	 
	 */
	 
	}

