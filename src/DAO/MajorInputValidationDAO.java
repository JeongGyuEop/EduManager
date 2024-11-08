package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MajorInputValidationDAO {
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	DataSource ds;
	// sql 준비
	String sql = null;

	// db 연결
	public MajorInputValidationDAO() {
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

	public int majorInputValidation(String newMajorName) {
		// 반환 값 설정
		String majorName = null;
		int validationResult = 0;
		// db 연동
		try {
			con = ds.getConnection();
			sql = "SELECT majorname FROM majorinformation WHERE majorname = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, newMajorName);
			rs = pstmt.executeQuery();
			// 중복된 이름이 존재할 경우
			if (rs.next()) {
				validationResult = -2;
			}
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
		return validationResult;
	}
}
