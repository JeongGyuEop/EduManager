package src.Service;

import javax.servlet.http.HttpServletRequest;

import DAO.MajorInputDAO;
import DAO.MajorInputValidationDAO;

public class MajorInputService {
	private static final int errorEmptyField = -1;
	private static final int errorDuplicateName = -2;

	MajorInputDAO majorInputDAO;
	MajorInputValidationDAO majorInputValidationDAO;

	public MajorInputService() {
		majorInputDAO = new MajorInputDAO();
		majorInputValidationDAO = new MajorInputValidationDAO();
	}

	public int majorInput(HttpServletRequest request) {
		String newMajorName = request.getParameter("MajorNameInput");
		String newMajorTel = request.getParameter("MajorTelInput");

		// ������ ��ȿ�� �˻�
		// ���� ����� ��� -1�� ��ȯ
		if (newMajorName == null || newMajorName.trim().isEmpty()) {
			return errorEmptyField;
		}
		if (newMajorTel == null || newMajorTel.trim().isEmpty()) {
			return errorEmptyField;
		}
		// ���� �̸��� ���� ���
		if (majorInputValidationDAO.majorInputValidation(newMajorName) == errorDuplicateName) {
			return errorDuplicateName;
		}
		// �߰� �۾�
		return majorInputDAO.majorInput(newMajorName, newMajorTel);
	}
}
