package Service;

import javax.servlet.http.HttpServletRequest;

import DAO.DepartmentInputDAO;

public class DepartmentInputService {
	DepartmentInputDAO departmentInputDAO;

	public DepartmentInputService() {
		departmentInputDAO = new DepartmentInputDAO();
	}
	// jsp�κ��� ���� request, session, application ���� Ǯ�� �����ϴ� ��

	public void departmentInput(HttpServletRequest request) {
		String newDepartmentName = request.getParameter("DepartmentNameInput");
		departmentInputDAO.departmentInput(newDepartmentName);
	}
}
