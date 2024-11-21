package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import Service.AssignmentService;
import Vo.AssignmentVo;
import Vo.CourseVo;
import Vo.EnrollmentVo;

@WebServlet("/assign/*")
public class AssignmentController extends HttpServlet {

	AssignmentService assignmentservice;
	
	@Override
	public void init() throws ServletException {
		assignmentservice = new AssignmentService();
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
	    
	    	case "/assignmentManage.bo": // 교수의 각 강의의 과제관리 화면을 보여주는 2단계 요청 주소를 받으면
	    		String course_id = request.getParameter("courseId");
	    		center = request.getParameter("center");
	    		
	    		request.setAttribute("classroomCenter", center);
	    		request.setAttribute("courseId", course_id);
	    		
	    		nextPage = "/view_classroom/classroom.jsp";
	    		
	    		break;
	    		
	    //==========================================================================================
	    	    
	    	case "/assignmentSearch.do": // 해당 과목의 과제를 조회하는 2단계 요청 주소를 받으면
	    		
	    		ArrayList<AssignmentVo> assignmentList = new ArrayList<AssignmentVo>();
	    		
	    		course_id = request.getParameter("courseId");
	    		assignmentList = assignmentservice.serviceAssignmentSearch(course_id);
	    		
	    		// JSON 응답 설정
	    	    response.setContentType("application/json; charset=UTF-8");
	    	    out = response.getWriter();
	    	    
	    	    JSONArray assignmentArray = new JSONArray();
	    	    if (assignmentList != null && !assignmentList.isEmpty()) {

	    	        // 강의 목록을 JSON 배열로 변환
	    	        for (AssignmentVo assignment : assignmentList) {
	    	            JSONObject assignmentJson = new JSONObject();
	    	            assignmentJson.put("assignmentId", assignment.getAssignmentId());
	    	            assignmentJson.put("courseId", assignment.getCourse().getCourse_id());
	    	            assignmentJson.put("title", assignment.getTitle());
	    	            assignmentJson.put("description", assignment.getDescription());
	    	            assignmentJson.put("dueDate", assignment.getDueDate().toString());
	    	            assignmentJson.put("createdDate", assignment.getCreatedDate().toString());
	    	            
	    	            // 배열에 추가
	    	            assignmentArray.add(assignmentJson);

	    	        }
	    	    }
	    	    // JSON 응답 반환
	    	    out.print(assignmentArray);
	    	    out.flush();
	    	    out.close();
	    	    return;
	    		
	    //==========================================================================================
	    		
	    	case "/createAssignment.do": // 해당 과목의 과제를 등록하는 2단계 요청 주소를 받으면
	    		
	    		course_id = request.getParameter("courseId");
	    		String title = request.getParameter("title");
	    		String description = request.getParameter("description");
	    		String dueDateString = request.getParameter("dueDate");
	    		
	    		Date dueDate = java.sql.Date.valueOf(dueDateString);
	            
	            AssignmentVo assignment = new AssignmentVo();
	            assignment.setTitle(title);
	            assignment.setDescription(description);
	            assignment.setDueDate(dueDate);

	            CourseVo course = new CourseVo();
	            course.setCourse_id(course_id);
	            assignment.setCourse(course);
	            
	            int result = assignmentservice.serviceCreateAssignment(assignment);
	    		
	            if(result == 1) {
				    response.sendRedirect(request.getContextPath() +"/assign/assignmentManage.bo?message="
				    		+ URLEncoder.encode("과제 등록이 완료되었습니다.", "UTF-8")
				    		+ "&courseId=" + course_id
				    		+ "&center=/view_classroom/assignment_submission/assignmentManage.jsp");
				    return;
				} else {
					// 실패 시 message 파라미터만 포함하여 리다이렉트
				    response.sendRedirect(request.getContextPath() +"/assign/assignmentManage.bo?message=" 
				                          + URLEncoder.encode("과제 등록에 실패했습니다. 다시 입력해 주세요.", "UTF-8") 
								    	  + "&courseId=" + course_id
				                          + "center=/view_classroom/assignment_submission/assignmentManage.jsp");
				    return;
				}
	            
	    //==========================================================================================
	    		
	    	case "/deleteAssignment.do": // 해당 과목의 과제를 삭제하는 2단계 요청주소를 받으면
	    		
	    		String assignment_id = request.getParameter("assignmentId");
	    		course_id = request.getParameter("courseId");
	    		
	    		int deleteAssignmentResult = assignmentservice.serviceDeleteAssignment(assignment_id);
	    		
	    		if(deleteAssignmentResult == 1) {
				    response.sendRedirect(request.getContextPath() +"/assign/assignmentManage.bo?message="
				    		+ URLEncoder.encode("과제가 삭제되었습니다.", "UTF-8") 
				    		+ "&courseId=" + course_id
				    		+ "&center=/view_classroom/assignment_submission/assignmentManage.jsp");
				    return;
				} else {
					// 실패 시 message 파라미터만 포함하여 리다이렉트
				    response.sendRedirect(request.getContextPath() +"/assign/assignmentManage.bo?message=" 
				                          + URLEncoder.encode("과제 삭제에 실패했습니다. 다시 입력해 주세요.", "UTF-8") 
								    	  + "&courseId=" + course_id
				                          + "center=/view_classroom/assignment_submission/assignmentManage.jsp");
				    return;
				}
	    		
	    //==========================================================================================
	    		
	    	default :
	    		break;
	    }
	    
	    // 디스패처 방식 포워딩(재요청)
	 	RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
	 	dispatch.forward(request, response);
	 		
	 } // doHandle 메소드
}
