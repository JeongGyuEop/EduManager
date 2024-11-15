package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;

import Service.MajorInputService;
import Vo.MajorVo;

@WebServlet("/MI/*")
public class MajorInputController extends HttpServlet {
	private MajorInputService majorInputService;

	@Override
	public void init() throws ServletException {
		// ���� �Է� ���� �ʱ�ȭ

		majorInputService = new MajorInputService();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doHandle(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doHandle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");

		String action = request.getPathInfo();

		String nextPage = null;

		switch (action) {
		case "/MajorInput.do":

			int addResult = majorInputService.majorInput(request);
			request.setAttribute("addResult", addResult);

			nextPage = "/jin/MajorInput.jsp";

			break;

		case "/searchMajor.do":
			// �˻� ����� ���� ��
			ArrayList<MajorVo> searchList = majorInputService.searchMajor(request);
			// �˻��� �����ϸ� �״�� �ѷ��ְ�,
			request.setAttribute("searchList", searchList);

			nextPage = "/jin/EditDeleteMajor.jsp";

			break;
		case "/editMajor.do":
			int deleteResult = majorInputService.editMajorService(request);
			System.out.println("editMajor.do" + deleteResult);
			request.setAttribute("deleteResult", deleteResult);
			nextPage = "/jin/EditDeleteMajor.jsp";
			break;
		case "/fetchMajorData":
			JSONArray majorData;
			try {
				majorData = majorInputService.fetchMajorService();
				response.setContentType("application/json; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.print(majorData.toString());
			} catch (Exception e) {
				e.printStackTrace();
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				PrintWriter out = response.getWriter();
				out.println("<tr><td colspan='3'>�����͸� �ҷ����� ���� ������ �߻��߽��ϴ�.</td></tr>");
			}
			return;
		default:
			System.out.println("�ּ� �ҽ�");

			break;
		}
		RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
		dispatch.forward(request, response);
	}
}
