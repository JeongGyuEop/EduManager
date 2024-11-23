package Controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import Service.SubmissionService;

@WebServlet("/submit/*")
public class SubmissionController extends HttpServlet {


	SubmissionService submissionservice;
	
	@Override
	public void init() throws ServletException {
		submissionservice = new SubmissionService();
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
	    
	    	case "/submitAssignmentPage.bo": // 학생의 과제를 제출하는 화면을 보여주는 2단계 요청 주소를 받으면
	    		
	    		String assignment_id = request.getParameter("assignmentId");
	    		String assignment_title = request.getParameter("assignmentTitle");
	    		center = "/view_classroom/assignment_submission/submitAssignment.jsp";
	    		
	    		request.setAttribute("classroomCenter", center);
	    		request.setAttribute("assignmentId", assignment_id);
	    		request.setAttribute("assignment_title", assignment_title);
	    		
	    		nextPage = "/view_classroom/classroom.jsp";
	    		
	    		break;
	    		
	    //==========================================================================================
	    	    
	    	case "/uploadAssignment.do": //  2단계 요청 주소를 받으면
	    		
	    		String uploadPath = getServletContext().getRealPath("") + File.separator + "submitUploads";
	    		File uploadDir = new File(uploadPath);

	    		if (!uploadDir.exists()) {
	    			uploadDir.mkdir(); // 업로드 디렉토리 생성
	    		}
	    	    
	    		int maxSize = 1024 * 1024 * 1024;
	    		
	    		MultipartRequest multipartRequest = new MultipartRequest(request, uploadPath, maxSize, "UTF-8", new DefaultFileRenamePolicy());
	    		
	    		String assignmentId = multipartRequest.getParameter("assignmentId"); 
	    		String assignmentTitle = multipartRequest.getParameter("assignmentTitle");
	    	    System.out.println("assignmentId: " + assignmentId); // 값 확인
	    		String studentId = (String)session.getAttribute("student_id"); // 학생 ID
	    		        
	    		// submission 테이블에 데이터 저장
	    		int submissionId = submissionservice.serviceSaveSubmission(assignmentId, studentId);
	    		
	    		// 실제 업로드 하기 전의  파일업로드를 하기 위해 jsp에서 선택했던 원본파일명 얻기
	    		String original_name = multipartRequest.getOriginalFileName("assignmentFile");
	    		
	    		// 실제 업로드폴더(실제 서버 업로드폴더경로)에  업로드된 실제 파일명 얻기 
	    		String file_path = multipartRequest.getFilesystemName("assignmentFile");
	    		
	    		// 업로드하기위해 선택한 파일의 원본이름과, 실제업로드한 파일 이름을 DB에 File테이블에 INSERT
	    		int result = submissionservice.serviceSaveFile(submissionId, file_path, original_name);

	    		if(result == 1) {
				    response.sendRedirect(request.getContextPath() +"/submit/submitAssignmentPage.bo?message="
				    		+ URLEncoder.encode("과제 제출이 완료되었습니다.", "UTF-8")
				    		+ "&assignmentId=" + assignmentId
				    		+ "&assignmentTitle=" + assignmentTitle);
				    return;
				} else {
					// 실패 시 message 파라미터만 포함하여 리다이렉트
					response.sendRedirect(request.getContextPath() +"/submit/submitAssignmentPage.bo?message="
				    		+ URLEncoder.encode("과제 제출에 실패했습니다.", "UTF-8")
				    		+ "&assignmentId=" + assignmentId
				    		+ "&assignmentTitle=" + assignmentTitle);
				    return;
				}
	    		
	    //==========================================================================================
	    		
	    	case "/createAssignment.do": //  2단계 요청 주소를 받으면
	    		
	    		break;
	    		
	    //==========================================================================================
	    		
	    	case "/deleteAssignment.do": //  2단계 요청주소를 받으면
	    		
	    		break;
	    		
	    //==========================================================================================
	    		
	    	case "/updateAssignment.do": // 
	    		
	    		break;
	    		
	    //==========================================================================================
	    		
	    	default :
	    		break;
	    }
	    
	    // 디스패처 방식 포워딩(재요청)
	 	RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
	 	dispatch.forward(request, response);
	 		
	 } // doHandle 메소드
	
	
}
