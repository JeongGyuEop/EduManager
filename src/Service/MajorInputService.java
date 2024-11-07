package Service;

import javax.servlet.http.HttpServletRequest;

import DAO.MajorInputDAO;

public class MajorInputService {
	MajorInputDAO majorInputDAO;

	public MajorInputService() {
		majorInputDAO = new MajorInputDAO();
	}
	// jsp�κ��� ���� request, session, application ���� Ǯ�� ����
	// ��ȿ���� üũ�Ͽ� dao �۾� ������ ����

	public int majorInput(HttpServletRequest request) {
		String newMajorName = request.getParameter("MajorNameInput");
		String newMajorTel = request.getParameter("MajorTelInput");
		
	    // ������ ��ȿ�� �˻�
		// ���� ����� ��� -1�� ��ȯ
	    if (newMajorName == null || newMajorName.trim().isEmpty()) {
	        return -1;
	    }

	    if (newMajorTel == null || newMajorTel.trim().isEmpty()) {
	        return -1;
	    }
		// �߰��� �������� ��� 1�� ��ȯ
	    // �߰��� �������� ��� 0�� ��ȯ
		return majorInputDAO.majorInput(newMajorName, newMajorTel);
	}
}
