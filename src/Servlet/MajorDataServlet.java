package Servlet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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

		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();

		try {
			// 占쏙옙占쏙옙占싶븝옙占싱쏙옙 占쏙옙占쏙옙 占쏙옙占쏙옙 (JNDI 占쏙옙占�)
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/edumanager");
			con = ds.getConnection();

			// SQL 占쏙옙占쏙옙 占쏙옙占쏙옙
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			// 占쏙옙占쏙옙占쏙옙占쏙옙 占싻곤옙 占쏙옙占쏙옙占쏙옙 HTML 占쏙옙占쏙옙占쏙옙占쏙옙 占쏙옙占�
			JSONArray jsonArray = new JSONArray();
			while (rs.next()) {

			    JSONObject jsonObject = new JSONObject();
			    jsonObject.put("majorcode", rs.getString("majorcode"));
			    jsonObject.put("majorname", rs.getString("majorname"));
			    jsonObject.put("majortel", rs.getString("majortel"));
			    
			    jsonArray.add(jsonObject);
			}
			out.print(jsonArray.toString());

		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			out.println("<tr><td colspan='3'>占쏙옙占쏙옙占싶몌옙 占쌀뤄옙占쏙옙占쏙옙 占쏙옙 占쏙옙占쏙옙占쏙옙 占쌩삼옙占쌩쏙옙占싹댐옙.</td></tr>");
		} finally {
			// 占쌘울옙 占쏙옙占쏙옙
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
