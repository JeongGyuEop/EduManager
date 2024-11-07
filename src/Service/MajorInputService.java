package Service;

import javax.servlet.http.HttpServletRequest;

import DAO.MajorInputDAO;

public class MajorInputService {
	MajorInputDAO majorInputDAO;

	public MajorInputService() {
		majorInputDAO = new MajorInputDAO();
	}
	// jsp로부터 받은 request, session, application 값을 풀어 설정
	// 유효성을 체크하여 dao 작업 유무를 결정

	public int majorInput(HttpServletRequest request) {
		String newMajorName = request.getParameter("MajorNameInput");
		String newMajorTel = request.getParameter("MajorTelInput");
		
	    // 데이터 유효성 검사
		// 값이 비었을 경우 -1을 반환
	    if (newMajorName == null || newMajorName.trim().isEmpty()) {
	        return -1;
	    }

	    if (newMajorTel == null || newMajorTel.trim().isEmpty()) {
	        return -1;
	    }
		// 추가에 성공했을 경우 1을 반환
	    // 추가에 실패했을 경우 0을 반환
		return majorInputDAO.majorInput(newMajorName, newMajorTel);
	}
}
