package Service;

import java.util.ArrayList;

import Dao.AssignmentDAO;
import Vo.AssignmentVo;

public class AssignmentService {
	
	AssignmentDAO assignmentdao;
	
	public AssignmentService() {
		assignmentdao = new AssignmentDAO();
	}

	//----------
	// 교수의 각 강의에서 등록된 과제들을 조회하기 위해 DAO 호출
	public ArrayList<AssignmentVo> serviceAssignmentSearch(String course_id) {
		return assignmentdao.assignmentSearch(course_id);
	}

}
