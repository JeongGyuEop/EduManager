package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DepartmentInputDAO {
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	DataSource ds;
	
	String sql = null;

	public DepartmentInputDAO() {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
		} catch (Exception e) {
			System.out.println("Ŀ�ؼ� Ǯ�� ��� �� �����߽��ϴ�: " + e.toString());
		}
	}

	private void closeDatabaseResources() {
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
	    
	    try {
	        if (con != null) {
	            con.close();
	        }
	    } catch (Exception e) {
	        System.out.println("Connection �ڿ� ���� �� ���� �߻�: " + e.toString());
	    }
	}
    
	public void departmentInput(String newDepartmentName) {
		
		try {
		con = ds.getConnection();
        sql = "";
        pstmt = con.prepareStatement(sql);
        pstmt.setString(1, newDepartmentName);
        rs = pstmt.executeQuery();
		} catch (SQLException e) {
		    System.out.println("SQL ���� �߻�: " + e.getMessage());
		    e.printStackTrace();
		} finally {
			closeDatabaseResources();
		}
	}
}
