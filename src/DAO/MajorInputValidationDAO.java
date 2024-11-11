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
	// 유효성 검사 결과
	private static final int SUCCESS = 1;
	private static final int FAILURE = 0;
	private static final int NONE = -1;
	private static final int EXISTS = -2;
	// 1 = 성공, 0 =실패, -1 = 없음, -2 = 있음
	
	int validationResult = NONE;

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
		// db 연동
		try {
			con = ds.getConnection();
			sql = "SELECT majorname FROM majorinformation WHERE majorname = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, newMajorName);
			rs = pstmt.executeQuery();
			// 중복된 이름이 존재할 경우
			if (rs.next()) {
				validationResult = EXISTS;
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

	public int majorSearchValidationCode(String editMajorCode) {
		try {
			con = ds.getConnection();
			sql = "SELECT majorcode FROM majorinformation WHERE majorcode = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, String.format("%02d", Integer.parseInt(editMajorCode)));
			rs = pstmt.executeQuery();
			// 중복된 이름이 존재할 경우
			if (rs.next()) {
				validationResult = EXISTS;
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
	public int majorSearchValidationName(String editMajorName) {
		try {
			con = ds.getConnection();
			sql = "SELECT majorname FROM majorinformation WHERE majorname = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, editMajorName);
			rs = pstmt.executeQuery();
			// 중복된 이름이 존재할 경우
			if (rs.next()) {
				validationResult = EXISTS;
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
