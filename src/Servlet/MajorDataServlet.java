package Servlet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/fetchMajorData")
public class MajorDataServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT majorcode, majorname, majortel FROM majorinformation";

		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		try {
			// �����ͺ��̽� ���� ���� (JNDI ���)
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/edumanager");
			con = ds.getConnection();

			// SQL ���� ����
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			// �������� �а� ������ HTML �������� ���
			while (rs.next()) {
				out.println("<tr>");
				out.println("<td>" + rs.getString("majorcode") + "</td>");
				out.println("<td>" + rs.getString("majorname") + "</td>");
				out.println("<td>" + rs.getString("majortel") + "</td>");
				out.println("</tr>");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			out.println("<tr><td colspan='3'>�����͸� �ҷ����� �� ������ �߻��߽��ϴ�.</td></tr>");
		} finally {
			// �ڿ� ����
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			if (con != null)
				try {
					con.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
		}
	}
}
