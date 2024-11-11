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
	// 1 = 성공, 0 =실패, -1 = 없음, -2 = 있음

	MajorInputDAO majorInputDAO;
	MajorInputValidationDAO majorInputValidationDAO;

	public MajorInputService() {
		majorInputDAO = new MajorInputDAO();
		majorInputValidationDAO = new MajorInputValidationDAO();
	}

	public int majorInput(HttpServletRequest request) {
		String newMajorName = request.getParameter("MajorNameInput");
		String newMajorTel = request.getParameter("MajorTelInput");

		// 占쏙옙占쏙옙占쏙옙 占쏙옙효占쏙옙 占싯삼옙
		// 占쏙옙占쏙옙 占쏙옙占쏙옙占� 占쏙옙占� -1占쏙옙 占쏙옙환
		if (newMajorName == null || newMajorName.trim().isEmpty()) {
			return NONE;
		}
		if (newMajorTel == null || newMajorTel.trim().isEmpty()) {
			return NONE;
		}

		// 같은 이름이 있을 경우
		if (majorInputValidationDAO.majorInputValidation(newMajorName) == EXISTS) {
			return EXISTS;
		}
		// 占쌩곤옙 占쌜억옙
		return majorInputDAO.majorInput(newMajorName, newMajorTel);
	}

	public ArrayList<MajorVO> searchMajor(HttpServletRequest request) {
		return majorInputDAO.searchMajor(request);
	}

	public int editMajor(HttpServletRequest request) {
		String editMajorCode = request.getParameter("majorCode");
		String editMajorName = request.getParameter("majorName");
		String editMajorTel = request.getParameter("majorTel");
		// 데이터 유효성 검사
		// 값이 비었을 경우 deleteMajor를 실행
		if (editMajorName == null || editMajorName.trim().isEmpty()) {
	        return majorInputDAO.deleteMajor(editMajorCode);
	    }
		if (majorInputValidationDAO.majorSearchValidationCode(editMajorCode) == EXISTS) {
		    if (majorInputValidationDAO.majorSearchValidationName(editMajorName) != EXISTS) {
		        // 학과 코드가 존재하고, 학과 이름이 중복되지 않은 경우: 수정 작업을 수행하고 결과 반환
		        return majorInputDAO.editMajor(editMajorCode, editMajorName, editMajorTel);
		    } else if (majorInputValidationDAO.majorSearchValidationName(editMajorName) == EXISTS) {
		        // 학과 이름이 이미 존재하는 경우: 중복을 나타내는 EXISTS 반환
		        return EXISTS;
		    }
		}
		// 학과 코드가 존재하지 않는 경우 또는 위의 모든 조건에 맞지 않는 경우: FAILURE 반환
		return FAILURE;
	}
}
