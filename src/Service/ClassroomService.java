package Service;

import java.util.ArrayList;

import Dao.ClassroomDAO;

public class ClassroomService {

	ClassroomDAO classroomdao;
	
	public ClassroomService() {
		classroomdao = new ClassroomDAO()
;	}
	
	//-------------(Major 관련 service가 있는지 확인하기)
	// 학과의 정보를 가져오기 위한 함수
	public String serviceGetMajor(String majorCode) {
		return classroomdao.getMajorNameInfo(majorCode);
	}

	// 강의실 정보를 모두 조회해서 가져오는 기능의 함수
	public ArrayList serviceGetClassInfo() {
		return classroomdao.getClassroomAllInfo();
	}

}
