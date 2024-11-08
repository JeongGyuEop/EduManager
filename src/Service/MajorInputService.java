package Service;

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

		// 데이터 유효성 검사
		// 값이 비었을 경우 -1을 반환
		if (newMajorName == null || newMajorName.trim().isEmpty()) {
			return errorEmptyField;
		}
		if (newMajorTel == null || newMajorTel.trim().isEmpty()) {
			return errorEmptyField;
		}
		// 같은 이름이 있을 경우
		if (majorInputValidationDAO.majorInputValidation(newMajorName) == errorDuplicateName) {
			return errorDuplicateName;
		}
		// 추가 작업
		return majorInputDAO.majorInput(newMajorName, newMajorTel);
	}
}
