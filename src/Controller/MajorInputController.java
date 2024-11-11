package Controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Service.MajorInputService;
import VO.MajorVO;

@WebServlet("/MI/*")
public class MajorInputController extends HttpServlet {
	private MajorInputService majorInputService;

	@Override
	public void init() throws ServletException {
		// 전공 입력 서비스 초기화
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
			// 검색 결과를 가져 옴
			ArrayList<MajorVO> searchList = majorInputService.searchMajor(request);
			// 검색이 성공하면 그대로 뿌려주고,
			request.setAttribute("searchList", searchList);
			
			nextPage = "/jin/EditDeleteMajor.jsp";
			
			break;
		case "/editMajor.do":
			
			int deleteResult = majorInputService.editMajor(request);
			if(deleteResult == 1) {
				request.setAttribute("deleteResult", deleteResult);
				nextPage = "/jin/EditDeleteMajor.jsp";
			} else if (deleteResult == -2) {
				request.setAttribute("deleteResult", deleteResult);
				nextPage = "/jin/EditDeleteMajor.jsp";
			}
			break;
		default:
			System.out.println("주소 소실");
			break;
		}
		RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
		dispatch.forward(request, response);
	}
}
