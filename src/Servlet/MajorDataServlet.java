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
			// 데이터베이스 연결 설정 (JNDI 사용)
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/edumanager");
			con = ds.getConnection();

			// SQL 쿼리 실행
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			// 응답으로 학과 정보를 HTML 형식으로 출력
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
			out.println("<tr><td colspan='3'>데이터를 불러오는 중 오류가 발생했습니다.</td></tr>");
		} finally {
			// 자원 해제
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
