package Service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import DAO.MajorInputDAO;
import DAO.MajorInputValidationDAO;
import VO.MajorVO;

public class MajorInputService {
	private static final int SUCCESS = 1;
	private static final int FAILURE = 0;
	private static final int NONE = -1;
	private static final int EXISTS = -2;
	// 1 = ����, 0 =����, -1 = ����, -2 = ����

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
			return NONE;
		}
		if (newMajorTel == null || newMajorTel.trim().isEmpty()) {
			return NONE;
		}
		// ���� �̸��� ���� ���
		if (majorInputValidationDAO.majorInputValidation(newMajorName) == EXISTS) {
			return EXISTS;
		}
		// �߰� �۾�
		return majorInputDAO.majorInput(newMajorName, newMajorTel);
	}

	public ArrayList<MajorVO> searchMajor(HttpServletRequest request) {
		return majorInputDAO.searchMajor(request);
	}

	public int editMajor(HttpServletRequest request) {
		String editMajorCode = request.getParameter("majorCode");
		String editMajorName = request.getParameter("majorName");
		String editMajorTel = request.getParameter("majorTel");
		// ������ ��ȿ�� �˻�
		// ���� ����� ��� deleteMajor�� ����
		if (editMajorName == null || editMajorName.trim().isEmpty()) {
	        return majorInputDAO.deleteMajor(editMajorCode);
	    }
		if (majorInputValidationDAO.majorSearchValidationCode(editMajorCode) == EXISTS) {
		    if (majorInputValidationDAO.majorSearchValidationName(editMajorName) != EXISTS) {
		        // �а� �ڵ尡 �����ϰ�, �а� �̸��� �ߺ����� ���� ���: ���� �۾��� �����ϰ� ��� ��ȯ
		        return majorInputDAO.editMajor(editMajorCode, editMajorName, editMajorTel);
		    } else if (majorInputValidationDAO.majorSearchValidationName(editMajorName) == EXISTS) {
		        // �а� �̸��� �̹� �����ϴ� ���: �ߺ��� ��Ÿ���� EXISTS ��ȯ
		        return EXISTS;
		    }
		}
		// �а� �ڵ尡 �������� �ʴ� ��� �Ǵ� ���� ��� ���ǿ� ���� �ʴ� ���: FAILURE ��ȯ
		return FAILURE;
	}
}
