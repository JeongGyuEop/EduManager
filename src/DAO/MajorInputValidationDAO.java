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
	// sql �غ�
	String sql = null;
	// ��ȿ�� �˻� ���
	private static final int SUCCESS = 1;
	private static final int FAILURE = 0;
	private static final int NONE = -1;
	private static final int EXISTS = -2;
	// 1 = ����, 0 =����, -1 = ����, -2 = ����
	
	int validationResult = NONE;

	// db ����
	public MajorInputValidationDAO() {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/edumanager");
		} catch (Exception e) {
			System.out.println("Ŀ�ؼ� Ǯ�� ��� �� �����߽��ϴ�: " + e.toString());
		}
	}

	// �ڿ����� �޼ҵ�
	private void closeDatabaseResources(PreparedStatement pstmt, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
			System.out.println("ResultSet �ڿ� ���� �� ���� �߻�: " + e.toString());
		}

		try {
			if (pstmt != null) {
				pstmt.close();
			}
		} catch (Exception e) {
			System.out.println("PreparedStatement �ڿ� ���� �� ���� �߻�: " + e.toString());
		}
	}

	public int majorInputValidation(String newMajorName) {
		// db ����
		try {
			con = ds.getConnection();
			sql = "SELECT majorname FROM majorinformation WHERE majorname = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, newMajorName);
			rs = pstmt.executeQuery();
			// �ߺ��� �̸��� ������ ���
			if (rs.next()) {
				validationResult = EXISTS;
			}
		} catch (SQLException e) {
			System.out.println("SQL ���� �߻�: " + e.getMessage());
			e.printStackTrace();
		} finally {
			closeDatabaseResources(pstmt, rs);
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				System.out.println("Connection �ڿ� ���� �� ���� �߻�: " + e.getMessage());
			}
		}
		// �� ��ȯ
		return validationResult;
	}

	public int majorSearchValidationCode(String editMajorCode) {
		try {
			con = ds.getConnection();
			sql = "SELECT majorcode FROM majorinformation WHERE majorcode = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, String.format("%02d", Integer.parseInt(editMajorCode)));
			rs = pstmt.executeQuery();
			// �ߺ��� �̸��� ������ ���
			if (rs.next()) {
				validationResult = EXISTS;
			}
		} catch (SQLException e) {
			System.out.println("SQL ���� �߻�: " + e.getMessage());
			e.printStackTrace();
		} finally {
			closeDatabaseResources(pstmt, rs);
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				System.out.println("Connection �ڿ� ���� �� ���� �߻�: " + e.getMessage());
			}
		}
		// �� ��ȯ
		return validationResult;
	}
	public int majorSearchValidationName(String editMajorName) {
		try {
			con = ds.getConnection();
			sql = "SELECT majorname FROM majorinformation WHERE majorname = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, editMajorName);
			rs = pstmt.executeQuery();
			// �ߺ��� �̸��� ������ ���
			if (rs.next()) {
				validationResult = EXISTS;
			}
		} catch (SQLException e) {
			System.out.println("SQL ���� �߻�: " + e.getMessage());
			e.printStackTrace();
		} finally {
			closeDatabaseResources(pstmt, rs);
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				System.out.println("Connection �ڿ� ���� �� ���� �߻�: " + e.getMessage());
			}
		}
		// �� ��ȯ
		return validationResult;
	}
}
