package Service;

import javax.servlet.http.HttpServletRequest;

import DAO.DepartmentInputDAO;

public class DepartmentInputService {
	DepartmentInputDAO departmentInputDAO;

	public DepartmentInputService() {
		departmentInputDAO = new DepartmentInputDAO();
	}
	// jsp로부터 받은 request, session, application 값을 풀어 설정하는 곳

	public void departmentInput(HttpServletRequest request) {
		String newDepartmentName = request.getParameter("DepartmentNameInput");
		departmentInputDAO.departmentInput(newDepartmentName);
	}
}
