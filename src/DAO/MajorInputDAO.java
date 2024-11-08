package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MajorInputDAO {
	private static final int success = 1;
	private static final int failure = 0;
	
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	DataSource ds;
	// sql 준비
	String sql = null;
	// db 연결
	public MajorInputDAO() {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/edumanager");
		} catch (Exception e) {
			System.out.println("커넥션 풀을 얻는 데 실패했습니다: " + e.toString());
		}
	}
	// 자원해제 메소드
	private void closeDatabaseResources(PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (Exception e) {
            System.out.println("ResultSet 자원 해제 중 오류 발생: " + e.toString());
        }

        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (Exception e) {
            System.out.println("PreparedStatement 자원 해제 중 오류 발생: " + e.toString());
        }
    }
    
	public int majorInput(String newMajorName, String newMajorTel) {
		// 반환 값 설정
		int addResult = failure;
		// db 연동
		try {
		con = ds.getConnection();
        sql = "insert into majorinformation (majorname, majortel) values (?, ?)";
        pstmt = con.prepareStatement(sql);
        pstmt.setString(1, newMajorName);
        pstmt.setString(2, newMajorTel);
        addResult = pstmt.executeUpdate();
        
		} catch (SQLException e) {
		    System.out.println("SQL 오류 발생: " + e.getMessage());
		    e.printStackTrace();
		} finally {
			closeDatabaseResources(pstmt, rs);
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println("Connection 자원 해제 중 오류 발생: " + e.getMessage());
            }
        }
		// 값 반환
		return addResult;
	}
}
