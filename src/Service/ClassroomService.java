package Service;

import java.util.ArrayList;

import Dao.ClassroomDAO;
import Vo.ClassroomVo;
import Vo.CourseVo;
import Vo.StudentVo;

public class ClassroomService {

	ClassroomDAO classroomdao;
	
	public ClassroomService() {
		classroomdao = new ClassroomDAO();
	}
	
	//-------------(Major 관련 service가 있는지 확인하기)
	// 학과의 정보를 가져오기 위한 함수
	public String serviceGetMajor(String majorCode) {
		return classroomdao.getMajorNameInfo(majorCode);
	}

	//-------------
	// 강의실 정보를 모두 조회해서 가져오는 기능의 함수
	public ArrayList<ClassroomVo> serviceGetClassInfo() {
		return classroomdao.getClassroomAllInfo();
	}

	//-------------
	// 강의를 등록하기 위해 DAO를 호출하는 함수
	public int serviceRegisterInsertCourse(String course_name, String majorcode, String room_id, String professor_id) {
		return classroomdao.registerInsertCourse(course_name, majorcode, room_id, professor_id);
	}

	//-------------
	// 교수의 강의를 조회하기 위해 DAO를 호출하는 함수
	public ArrayList<CourseVo> serviceCourseSearch(String professor_id) {
		return classroomdao.courseSearch(professor_id);
	}

	//-------------
	// 교수의 강의를 삭제하기 위해 DAO를 호출하는 함수
	public int serviceDeleteCourse(String course_id) {
		return classroomdao.courseDelete(course_id);
	}

	//-------------
	// 교수의 강의를 수정하기 위해 DAO를 호출하는
	public int serviceUpdateCourse(String course_id, String course_name, String room_id) {
		return classroomdao.updateCourse(course_id, course_name, room_id);
	}

	// 교수의 강의를 수강하는 학생 조회
	public ArrayList<StudentVo> serviceStudentSearch(String course_id_) {
		return classroomdao.studentSearch(course_id_);
	}

	//성적 등록 
	public void serviceGradeInsert(String course_id_, String student_id, String total) {
		classroomdao.gradeInsert(course_id_, student_id, total);
	}

	public void serviceGradeUpdate(String student_id_, String total_) {
		classroomdao.gradeUpdate(student_id_, total_);
	}

	public ArrayList<StudentVo> serviceGradeSearch(String student_id_1) {
		return classroomdao.gradeSearch(student_id_1);
	}


}
