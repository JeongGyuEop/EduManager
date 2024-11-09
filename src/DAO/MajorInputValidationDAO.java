package src.DAO;

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
		// ��ȯ �� ����
		String majorName = null;
		int validationResult = 0;
		// db ����
		try {
			con = ds.getConnection();
			sql = "SELECT majorname FROM majorinformation WHERE majorname = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, newMajorName);
			rs = pstmt.executeQuery();
			// �ߺ��� �̸��� ������ ���
			if (rs.next()) {
				validationResult = -2;
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
