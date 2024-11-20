package Controller;

import java.io.IOException;
import java.io.PrintWriter;
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
	    		System.out.println(course_id);
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
	    		
	    	default :
	    		break;
	    }
	    
	    // 디스패처 방식 포워딩(재요청)
	 	RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
	 	dispatch.forward(request, response);
	 		
	 } // doHandle 메소드
}
