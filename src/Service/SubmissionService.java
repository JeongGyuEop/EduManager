package Service;

import Dao.SubmissionDAO;

public class SubmissionService {
	
	SubmissionDAO submissiondao;
	
	public SubmissionService() {
		submissiondao = new SubmissionDAO();
	}

	//----------
	// 학생이 제출한 파일을 저장하기 위해 DAO를 호출하는 함수
	public int serviceSaveSubmission(String assignmentId, String studentId ) {
		return submissiondao.saveSubmission(assignmentId, studentId);
	}

	//----------
	// 학생이 제출한 파일의 정보를 관리하기 위해 DAO를 호출
	public int serviceSaveFile(int submissionId, String filePath, String originalName) {
		return submissiondao.saveFile(submissionId, filePath, originalName);
	}
	
	
	
	
}
