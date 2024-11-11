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
	// sql 占쌔븝옙
	String sql = null;
	// 유효성 검사 결과
	private static final int SUCCESS = 1;
	private static final int FAILURE = 0;
	private static final int NONE = -1;
	private static final int EXISTS = -2;
	// 1 = 성공, 0 =실패, -1 = 없음, -2 = 있음
	
	int validationResult = NONE;

	// db 占쏙옙占쏙옙
	public MajorInputValidationDAO() {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/edumanager");
		} catch (Exception e) {
			System.out.println("커占쌔쇽옙 풀占쏙옙 占쏙옙占� 占쏙옙 占쏙옙占쏙옙占쌩쏙옙占싹댐옙: " + e.toString());
		}
	}

	// 占쌘울옙占쏙옙占쏙옙 占쌨소듸옙
	private void closeDatabaseResources(Connection con, PreparedStatement pstmt, ResultSet rs) {
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

        try {
            if (con != null) {
                con.close();
            }
        } catch (Exception e) {
            System.out.println("Connection 자원 해제 중 오류 발생: " + e.toString());
        }
    }

	public int majorInputValidation(String newMajorName) {
		// db 연동
		validationResult = NONE;
		try {
			con = ds.getConnection();
			sql = "SELECT majorname FROM majorinformation WHERE majorname = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, newMajorName);
			rs = pstmt.executeQuery();
			// 占쌩븝옙占쏙옙 占싱몌옙占쏙옙 占쏙옙占쏙옙占쏙옙 占쏙옙占�
			if (rs.next()) {
				validationResult = EXISTS;
			}
		} catch (SQLException e) {
            System.out.println("SQL 오류 발생: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeDatabaseResources(con, pstmt, rs);
        }
        // 값 반환
        return validationResult;
    }

	public int majorSearchValidationCode(String editMajorCode) {
		validationResult = NONE;
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
            closeDatabaseResources(con, pstmt, rs);
        }
        // 값 반환
        return validationResult;
    }
	public int majorSearchValidationName(String editMajorName) {
		validationResult = NONE;
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
            closeDatabaseResources(con, pstmt, rs);
        }
        // 값 반환
        return validationResult;
    }
}
