package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import VO.MajorVO;

public class MajorInputDAO {
    private static final int SUCCESS = 1;
    private static final int FAILURE = 0;

    DataSource ds;

    // db ����
    public MajorInputDAO() {
        try {
            Context ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/edumanager");
        } catch (Exception e) {
            System.out.println("Ŀ�ؼ� Ǯ�� ��� �� �����߽��ϴ�: " + e.toString());
        }
    }

    // �ڿ����� �޼ҵ� (Connection ����)
    private void closeDatabaseResources(Connection con, PreparedStatement pstmt, ResultSet rs) {
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

    public int majorInput(String newMajorName, String newMajorTel) {
        int addResult = FAILURE;
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = ds.getConnection();
            String sql = "insert into majorinformation (majorname, majortel) values (?, ?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, newMajorName);
            pstmt.setString(2, newMajorTel);
            addResult = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL ���� �߻�: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeDatabaseResources(con, pstmt, null);
        }

        return addResult;
    }

    public ArrayList<MajorVO> searchMajor(HttpServletRequest request) {
        ArrayList<MajorVO> searchList = new ArrayList<>();
        String searchKeyWord = request.getParameter("searchMajor");

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = ds.getConnection();

            // Ű���尡 �������� Ȯ��
            boolean isNumeric = false;
            try {
                Integer.parseInt(searchKeyWord);
                isNumeric = true;
            } catch (NumberFormatException e) {
                isNumeric = false;
            }

            // ������ ���� ������ ���� ���� �ۼ�
            if (isNumeric) {
                // ���ڶ�� majorcode�� �˻�
                String sql = "SELECT * FROM majorinformation WHERE majorcode = ?";
                pstmt = con.prepareStatement(sql);
                pstmt.setInt(1, Integer.parseInt(searchKeyWord));
            } else {
                // ���ڶ�� majorname���� LIKE �˻�
                String sql = "SELECT * FROM majorinformation WHERE majorname LIKE ?";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, "%" + searchKeyWord + "%");
            }

            rs = pstmt.executeQuery();

            while (rs.next()) {
                MajorVO vo = new MajorVO();
                vo.setMajorCode(rs.getInt("majorcode"));
                vo.setMajorName(rs.getString("majorname"));
                vo.setMajorTel(rs.getString("majortel"));
                searchList.add(vo);
            }
        } catch (SQLException e) {
            System.out.println("SQL ���� �߻�: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeDatabaseResources(con, pstmt, rs);
        }

        return searchList;
    }

    public int editMajor(String editMajorCode, String editMajorName, String editMajorTel) {
        int editResult = FAILURE;
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = ds.getConnection();
            String sql = "UPDATE majorinformation SET majorname=?, majortel=? WHERE majorcode=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, editMajorName);
            pstmt.setString(2, editMajorTel);
            pstmt.setString(3, String.format("%02d", Integer.parseInt(editMajorCode)));
            editResult = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL ���� �߻�: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeDatabaseResources(con, pstmt, null);
        }
        return editResult;
    }

	public int deleteMajor(String editMajorCode) {
		int deleteResult = FAILURE;
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = ds.getConnection();
            String sql = "DELETE FROM majorinformation WHERE majorcode = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, String.format("%02d", Integer.parseInt(editMajorCode)));
            deleteResult = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL ���� �߻�: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeDatabaseResources(con, pstmt, null);
        }
        return deleteResult;
    }
}
