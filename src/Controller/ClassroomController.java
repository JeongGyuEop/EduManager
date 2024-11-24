package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import javax.el.ListELResolver;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import Service.ClassroomService;
import Service.MenuItemService;
import Service.StudentService;
import Vo.BoardVo;
import Vo.ClassroomVo;
import Vo.CourseVo;
import Vo.EnrollmentVo;
import Vo.StudentVo;


@WebServlet("/classroom/*")
public class ClassroomController extends HttpServlet {
	
	ClassroomService classroomservice;
	
	@Override
	public void init() throws ServletException {
		classroomservice = new ClassroomService();
	}
	
	// doGet doPost 메소드 오버라이딩
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doHandle(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doHandle(request, response);
	}
	
	protected void doHandle(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

        HttpSession session = request.getSession();
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8"); // MIME TYPE 설정
		
		String contextPath = request.getContextPath();
		
		//웹브라우저로 출력할 출력 스트림 생성
	    PrintWriter out = null;
	      
	    // 조건에 따라 포워딩 또는 보여줄 VIEW 주소 경로를 저장할 변수
	    String nextPage = null;
	    
	    // 재요청할 경로 주소를 저장할 변수
	    String center = null;
		
	    String action = request.getPathInfo(); // 2단계 요청주소
	    System.out.println("요청한 2단계 주소: " + action);
	    
	    
	    switch(action) {

		//==========================================================================================
	    
	    	case "/classroom.bo": // 학생 계정의 강의실 화면 2단계 요청 주소를 받으면
	    		
	    		center = request.getParameter("classroomCenter");
	    		
	    		request.setAttribute("classroomCenter", center);
	    		
				nextPage = "/view_classroom/classroom.jsp";
				
				break;
				
		//==========================================================================================
				
	    	case "/course_register.bo": // 수강관리 화면을 보여주는 요청을 받으면
	    		
	    		center = request.getParameter("classroomCenter");
	    		
	    		String majorcode = (String)session.getAttribute("majorcode");
	    		
	    		// 학과 이름 조회
	    		String majorName = classroomservice.serviceGetMajor(majorcode);
	    		System.out.println(majorName);
	    		
	    		

	    	    ArrayList<ClassroomVo> list = null;
	    		// 강의실 정보 조회
	    		list = classroomservice.serviceGetClassInfo();
	    		
//				로그인할 때 session 값으로 저장한 값
//	    		session.setAttribute("role", userInfo.get("role"));
//	    		session.setAttribute("name", userInfo.get("name"));
//	    		session.setAttribute("id", login_id);
	    		
	    		request.setAttribute("majorname", majorName);
	    		request.setAttribute("rooms", list);
	    		request.setAttribute("classroomCenter", center);
	    		
				nextPage = "/view_classroom/classroom.jsp";
				
				break;
				
		//==========================================================================================
			
			// 강의 등록을 했을 때 전달 받는 2단계 경로 
	    	case "/course_register.do":
	    		
	    		String course_name = request.getParameter("course_name");
	    		majorcode = (String) session.getAttribute("majorcode");
	    		String room_id = request.getParameter("room_id");
	    		String professor_id = request.getParameter("professor_id");
	    		
	    		int result = classroomservice.serviceRegisterInsertCourse(course_name, majorcode, room_id, professor_id); 
	    		
	    		if(result == 1) {
				    response.sendRedirect(contextPath +"/classroom/course_search.bo?message="
				    		+ URLEncoder.encode("강의 등록이 완료되었습니다.", "UTF-8") + "&classroomCenter=/view_classroom/courseSearch.jsp");
				    return;
				} else {
					// 실패 시 message 파라미터만 포함하여 리다이렉트
				    response.sendRedirect(contextPath +"/classroom/course_register.bo?message=" 
				                          + URLEncoder.encode("강의 등록에 실패했습니다. 다시 입력해 주세요.", "UTF-8") + "&classroomCenter=/view_classroom/courseRegister.jsp");
				    return;
				}
	    		
	    		
		//==========================================================================================
			    
	    	case "/course_search.bo": // 교수 강의 조회 화면 2단계 요청 주소를 받으면
	    		
	    		ArrayList<CourseVo> courseList = new ArrayList<CourseVo>();
	    		
	    		professor_id = (String) session.getAttribute("professor_id");
	    		
	    		courseList = classroomservice.serviceCourseSearch(professor_id);
	    		
	    		center = request.getParameter("classroomCenter");
	    		
	    		request.setAttribute("courseList", courseList);
	    		request.setAttribute("classroomCenter", center);
	    		
				nextPage = "/view_classroom/classroom.jsp";
				
				break;
						
		//==========================================================================================
			    
	    	case "/courseNameSearch.do": // 교수 강의 조회 화면 2단계 요청 주소를 받으면
	    		
	    		ArrayList<CourseVo> courseListAjax = new ArrayList<CourseVo>();
	    		
	    		professor_id = (String) session.getAttribute("professor_id");
	    		
	    		courseListAjax = classroomservice.serviceCourseSearch(professor_id);
	    		
	    		// JSON 응답 설정
	    	    response.setContentType("application/json; charset=UTF-8");
	    	    out = response.getWriter();
	    	    
	    	    JSONArray courseArray = new JSONArray();
	    	    if (courseListAjax != null && !courseListAjax.isEmpty()) {

	    	        // 강의 목록을 JSON 배열로 변환
	    	        for (CourseVo course : courseListAjax) {
	    	            JSONObject courseJson = new JSONObject();
	    	            courseJson.put("courseName", course.getCourse_name()); // 강의 이름 추가
	    	            courseJson.put("courseId", course.getCourse_id()); // 강의 아이디 추가
	    	            courseArray.add(courseJson); // 배열에 추가
	    	        }
	    	    }
	    	    // JSON 응답 반환
	    	    out.print(courseArray);
	    	    out.flush();
	    	    out.close();
	    	    return;
				
		//==========================================================================================
				
	    	case "/updateCourse.do":
	    		// 요청으로부터 파라미터 받기
	            String course_id = request.getParameter("courseId");
	            course_name = request.getParameter("courseName");
	            room_id = request.getParameter("classroomId");
//	    		majorcode = (String) session.getAttribute("majorcode");
//	    		professor_id = (String) session.getAttribute("professor_id");
	    		
	    		int courseUpdateResult = classroomservice.serviceUpdateCourse(course_id, course_name, room_id);
	    		
	    		if(courseUpdateResult == 1) {

	    	        out = response.getWriter();
	    	        out.print("강의 수정 완료");
	    	        out.flush();
	    	        return;
	    		}
	    		
	    		break;
	    		
	    //==========================================================================================
				
	    	// 로그인된 교수의 강의실의 목록을 조회해서 가져온다.
	    	case "/getClassroomList.do":
	    	
	    		majorcode = (String)session.getAttribute("majorcode");

	    	    ArrayList<ClassroomVo> classroomList = null;
	    		// 강의실 정보 조회
	    		classroomList = classroomservice.serviceGetClassInfo();
	    		
	    		if(classroomList != null) {
	    			// JSON 배열로 변환
	    	        JSONArray jsonArray = new JSONArray();
	    	        for (ClassroomVo room : classroomList) {
	    	            JSONObject jsonObject = new JSONObject();
	    	            jsonObject.put("room_id", room.getRoom_id());
	    	            jsonObject.put("capacity", room.getCapacity());
	    	            jsonObject.put("equipment", room.getEquipment());
	    	            jsonArray.add(jsonObject);
	    	        }
	    	        
	    	        out = response.getWriter();
	    	        out.print(jsonArray.toString());
	    	        out.flush();
	    	        return;
	    		}
	    		
	    		break;
	    		
	    //==========================================================================================
				
	    	case "/deleteCourse.do":
	    		
	    		course_id = request.getParameter("id");
	    		System.out.println(course_id);
	    		
	    		int deleteResult = classroomservice.serviceDeleteCourse(course_id); 

	    		if(deleteResult == 1) {
				    response.sendRedirect(contextPath +"/classroom/course_search.bo?message="
				    		+ URLEncoder.encode("강의가 정상적으로 삭제되었습니다.", "UTF-8") + "&classroomCenter=/view_classroom/courseSearch.jsp");
				    return;
				} else {
					// 실패 시 message 파라미터만 포함하여 리다이렉트
				    response.sendRedirect(contextPath +"/classroom/course_search.bo?message=" 
				                      + URLEncoder.encode("강의 삭제에 실패했습니다.", "UTF-8") + "&classroomCenter=/view_classroom/courseSearch.jsp");
				    return;
				}
				
	    //==========================================================================================
				
	    	case "/roomRegister.bo":
	    		
	    		center = request.getParameter("center");
	    		
	    		request.setAttribute("center", center);
	    		
				nextPage = "/main.jsp";
	    		
	    		break;	
	    		
	    //==========================================================================================
				
	    	case "/roomRegister.do":
	    		
	    		room_id = (String) request.getParameter("room_id");
	    		String capacity = (String) request.getParameter("capacity");
	    		String[] equipment = request.getParameterValues("equipment[]");
	    		
	    		result = classroomservice.serviceRoomRegister(room_id, capacity, equipment);
	    		
	    		if(result == 1) {
				    response.sendRedirect(contextPath +"/classroom/roomSearch.bo?message="
				    		+ URLEncoder.encode("강의실이 정상적으로 등록되었습니다.", "UTF-8") + "&center=/view_admin/roomSearch.jsp");
				    return;
				} else {
					// 실패 시 message 파라미터만 포함하여 리다이렉트
				    response.sendRedirect(contextPath +"/classroom/roomRegister.bo?message=" 
				                      + URLEncoder.encode("강의실 등록에 실패했습니다. 다시 입력하세요.", "UTF-8") + "&center=/view_admin/roomRegister.jsp");
				    return;
				}
	    		
	    		
	    //==========================================================================================
			    
	    	case "/roomSearch.bo": // 강의실 조회 화면 2단계 요청 주소를 받으면
	    		
	    		ArrayList<ClassroomVo> roomList = new ArrayList<ClassroomVo>();
	    		
	    		roomList = classroomservice.serviceGetClassInfo();
	    		
	    		center = request.getParameter("center");
	    		
	    		request.setAttribute("roomList", roomList);
	    		request.setAttribute("center", center);
	    		
				nextPage = "/main.jsp";
				
				break;
	    		
		//==========================================================================================
				
	    	case "/updateRoom.do":
	    		
	    		room_id = (String)request.getParameter("room_id");
	    		capacity = (String) request.getParameter("capacity");
	    		String room_equipment = (String)request.getParameter("equipment");
	    		
	    		result = classroomservice.serviceUpdateRoom(room_id, capacity, room_equipment);
	    		
	    		if(result == 1) {
	    	        out = response.getWriter();
	    	        out.write("success");
	    	        out.flush();
	    	        return;
	    		}
	    		
	    		break;
	    		
	    //==========================================================================================
				
	    	case "/deleteRoom.do":
	    		
	    		room_id = request.getParameter("room_id");
	    		
	    		result = classroomservice.serviceDeleteRoom(room_id); 
	    		
	    	    out = response.getWriter();
	    		if(result == 1) {
	    	        out.write("success");
	    	        out.flush();
	    	        return;
	    		} else {
	    	        out.write("non-success");
	    	        out.flush();
	    	        return;
	    		}
	    		
		//==========================================================================================
				
	    	case "/student_search.bo": //강의명 클릭시 조회될 학생목록
	    		
	    		ArrayList<StudentVo> studentList = new ArrayList<StudentVo>();
	    		
	    		String course_id_ = (String) request.getParameter("course_id");	    
	    		
	    		session.setAttribute("course_id", course_id_);
	    		
	    		studentList = classroomservice.serviceStudentSearch(course_id_);
	    		
	    		center = request.getParameter("classroomCenter");
	    		
	    		session.setAttribute("studentList", studentList);
	    		request.setAttribute("classroomCenter", center);
	    		
				nextPage = "/view_classroom/classroom.jsp";
	    		
	    		break;
	    		
	    //==========================================================================================

	    	case "/grade_register.do": // 교수가 성적 등록
	    		
	    		studentList = (ArrayList<StudentVo>) session.getAttribute("studentList");
	    		
	    		session.setAttribute("studentList", studentList);
	    		
	    		course_id_ = (String) session.getAttribute("course_id");
	    		String student_id = (String) request.getParameter("student_id");
	    		String total = (String)request.getParameter("total");
	    		String midtest_score = (String) request.getParameter("midtest");
	    		String finaltest_score = (String) request.getParameter("finaltest");
	    		String assignment_score = (String) request.getParameter("assignment");
	    		
	    		
	    		session.setAttribute("total", total);
	    		center = request.getParameter("classroomCenter");
	    		
	    		request.setAttribute("classroomCenter", center);
	    		
	    		nextPage = "/view_classroom/classroom.jsp";
	    		
	    		boolean isGradeExists = classroomservice.isGradeExists(course_id_, student_id);
	    		
	    		if(isGradeExists) {// 이미 해당 학생의 성적이 등록되어 있는 경우
	    			 response.getWriter().write("이미 성적이 등록된 학생입니다.");
	    			 return;
	    		} else {

		    		classroomservice.serviceGradeInsert(course_id_, student_id, total, midtest_score, finaltest_score, assignment_score);
		    		response.getWriter().write("성적이 성공적으로 등록되었습니다."); // 성공 메시지 반환
		    		return;
	    		}
	    		
	    		
   	    //==========================================================================================
		
	    	case "/grade_search.bo": // 학생이 성적 조회
	    		
	    		ArrayList<StudentVo> studentList_ = new ArrayList<StudentVo>();
	    			  
	    		String student_id_1 = (String) session.getAttribute("student_id");
	    		
	    		studentList_ = classroomservice.serviceGradeSearch(student_id_1);
	    		
	    		center = request.getParameter("classroomCenter");
	    		
	    		session.setAttribute("studentList", studentList_);
	    		request.setAttribute("classroomCenter", center);
	    		
				nextPage = "/view_classroom/classroom.jsp";
	    		
	    		
	    		break;
	    
  	    //==========================================================================================
	    		
	    	case "/grade_update.do": //교수가 성적 수정
	    		
	    		studentList = (ArrayList<StudentVo>) session.getAttribute("studentList");
	    		
	    		session.setAttribute("studentList", studentList);
	    		
	    		course_id_ = (String) session.getAttribute("course_id");
	    		student_id = (String) request.getParameter("student_id");
	    		total = (String)request.getParameter("total");
	    		midtest_score = (String) request.getParameter("midtest");
	    		finaltest_score = (String) request.getParameter("finaltest");
	    		assignment_score = (String) request.getParameter("assignment");
	    		
	    		session.setAttribute("total", total);
	    		
	    		center = request.getParameter("classroomCenter");
	    		
	    		request.setAttribute("classroomCenter", center);
	    		
	    		nextPage = "/view_classroom/classroom.jsp";
	    		
	    	
	    		
	    		// 입력값 검증
	    	    try {
	    	    	int midtest = Integer.parseInt(midtest_score);
		    		int finaltest = Integer.parseInt(finaltest_score);
		    		int assignment = Integer.parseInt(assignment_score);

	    	        // 점수 범위 확인 (0~100)
	    	        if (midtest < 0 || midtest > 100 || finaltest < 0 || finaltest > 100 || assignment < 0 || assignment > 100) {
	    	            response.getWriter().write("점수는 0에서 100 사이여야 합니다.");
	    	            return;
	    	        }
	    	        classroomservice.serviceGradeUpdate(course_id_, student_id, total, midtest_score, finaltest_score, assignment_score );
	    	        response.getWriter().write("성적이 성공적으로 수정되었습니다.");
	    	        return;
	    	    } catch (NumberFormatException e) {
	    	        // 숫자가 아닌 값이 입력된 경우
	    	        response.getWriter().write("유효한 숫자를 입력해주세요.");
	    	    }
	    		
	    		
	    		
	    		break;
	    		
	    //==========================================================================================
	    		
	    	case "/grade_delete.do": //교수가 성적 삭제
	    		
	    		studentList = (ArrayList<StudentVo>) session.getAttribute("studentList");
	    		
	    		session.setAttribute("studentList", studentList);
	    		
	    		course_id_ = (String) session.getAttribute("course_id");
	    		student_id = (String) request.getParameter("student_id");
	    		
	    		System.out.println(course_id_);
	    		System.out.println(student_id);
	    		
	    		center = request.getParameter("classroomCenter");
	    		
	    		request.setAttribute("classroomCenter", center);
	    		
	    		nextPage = "/view_classroom/classroom.jsp";
	    		
	    	      
		       	classroomservice.serviceGradeDelete(course_id_, student_id);
		       	
		        response.getWriter().write("성적이 성공적으로 삭제되었습니다.");
		        return; 
	    		
	    //==========================================================================================
		
	    	case "/course_submit.bo": //수강신청 전체 과목 조회
	    		
	    		ArrayList<CourseVo> courseList1 = new ArrayList<CourseVo>();
	    		ArrayList<CourseVo> courseList2 = new ArrayList<CourseVo>();
	 
	    		String studentId = (String)session.getAttribute("student_id");
	    		
	    		courseList1 = classroomservice.serviceCourseList(studentId);
	    		courseList2 = classroomservice.serviceCourseSelect(studentId);
	    		System.out.println(courseList2);
	    		
	    		center = request.getParameter("classroomCenter");
	    		
	    		session.setAttribute("courseList", courseList1);
	    		session.setAttribute("courseList2", courseList2);
	    		
	    		request.setAttribute("classroomCenter", center);
	    		
				nextPage = "/view_classroom/classroom.jsp";
	    		
	    		
	    		break;
		        
		//==========================================================================================
			    
	    	case "/studentCourseSearch.do": // 학생이 수강하는 과목을 조회해서 가져오는 2단계 요청주소
	    		
	    		ArrayList<EnrollmentVo> studentCourseList = new ArrayList<EnrollmentVo>();
	    		
	    		student_id = (String) session.getAttribute("student_id");
	    		
	    		studentCourseList = classroomservice.serviceStudentCourseSearch(student_id);
	    		
	    		// JSON 응답 설정
	    	    response.setContentType("application/json; charset=UTF-8");
	    	    out = response.getWriter();
	    	    
	    	    courseArray = new JSONArray();
	    	    if (studentCourseList != null && !studentCourseList.isEmpty()) {

	    	        // 강의 목록을 JSON 배열로 변환
	    	        for (EnrollmentVo enrollment : studentCourseList) {
	    	            JSONObject courseJson = new JSONObject();
	    	            // EnrollmentVo의 CourseVo에서 강의 정보 추출
	    	            CourseVo course = enrollment.getCourse(); // EnrollmentVo에서 CourseVo를 가져옴

	    	            if (course != null) {
	    	                courseJson.put("courseId", course.getCourse_id());   // 강의 ID 추가
	    	                courseJson.put("courseName", course.getCourse_name()); // 강의 이름 추가
	    	            }
	    	            
	    	            // 배열에 추가
	    	            courseArray.add(courseJson);

	    	        }
	    	    }
	    	    // JSON 응답 반환
	    	    out.print(courseArray);
	    	    out.flush();
	    	    out.close();
	    	    return;
	    	    
		//==========================================================================================
		        
	    	case "/courseInsert.do": //수강 신청
	    		
	    		String courseId = request.getParameter("courseId");
	    		studentId = (String)session.getAttribute("student_id");
	    		System.out.println(courseId);
	    		System.out.println(studentId);
	    		int isInsert = classroomservice.serviceCourseInsert(courseId, studentId);
	    		
	    		nextPage = "/view_classroom/classroom.jsp";
	    		
	    		if(isInsert > 0) {
	    			response.getWriter().write("Success");
	    			return;
	    		}else {
	    			response.getWriter().write("Fail");
	    			return;
	    		}
	    		
		        
	    //==========================================================================================
	    		
	    	case "/courseDelete.do": //수강 취소
	    		
	    		courseId = request.getParameter("courseId");
	    		studentId = (String)session.getAttribute("student_id");
	    		System.out.println(courseId);
	    		System.out.println(studentId);
	    		
	    		int isDelete = classroomservice.serviceCourseDelete(courseId, studentId);
	    		
	    		nextPage = "/view_classroom/classroom.jsp";
	    		
	    		if(isDelete > 0) {
	    			response.getWriter().write("Success");
	    			return;
	    		}else {
	    			response.getWriter().write("Fail");
	    			return;
	    		}
	    					
	    //==========================================================================================
    		
	    	default:
	    		break;
	    }
		
		// 디스패처 방식 포워딩(재요청)
		RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
		dispatch.forward(request, response);
		
	} // doHandle 메소드

}
