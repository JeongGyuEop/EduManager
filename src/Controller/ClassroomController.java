package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.el.ListELResolver;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Service.ClassroomService;


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
	    
	    ArrayList list = null;
	    
	    switch(action) {

		//==========================================================================================
	    
	    	case "/classroom.bo": // 학생 계정의 강의실 화면 2단계 요청 주소를 받으면
	    		
	    		center = request.getParameter("classroomCenter");
	    		
	    		request.setAttribute("classroomCenter", center);
	    		
				nextPage = "/view_classroom/classroom.jsp";
				
				break;
				
		//==========================================================================================
				
	    	case "/course_register.bo": // 수강관리 화면을 보여주는 요청을 받으면
	    		
	    		center = "/view_classroom/courseRegister.jsp";
	    		
	    		String majorCode = (String)session.getAttribute("majorcode");
	    		
	    		// 학과 이름 조회
	    		String majorName = classroomservice.serviceGetMajor(majorCode);
	    		System.out.println(majorName);
	    		
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
	    		String majorcode = (String) session.getAttribute("majorcode");
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
				                          + URLEncoder.encode("강의 등록에 실패했습니다. 다시 입력해 주세요.", "UTF-8"));
				    return;
				}
	    		
	    		
		//==========================================================================================
			    
	    	case "/course_search.bo": // 교수 강의 조회 화면 2단계 요청 주소를 받으면
	    		
	    		majorCode = (String)session.getAttribute("majorcode");
	    		
//	    		list = classroomservice.
	    		
	    		center = request.getParameter("classroomCenter");
	    		
	    		request.setAttribute("classroomCenter", center);
	    		
				nextPage = "/view_classroom/classroom.jsp";
				
				break;
						
				
		//==========================================================================================
				
	    	default:
	    		break;
	    }
		
		// 디스패처 방식 포워딩(재요청)
		RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
		dispatch.forward(request, response);
		
	} // doHandle 메소드

}
