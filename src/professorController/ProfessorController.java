package professorController;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import professorService.ProfessorService;
import professorVo.ProfessorVo;

@WebServlet("/prosess/*")
public class ProfessorController extends HttpServlet {

	// .. 사장

	// MVC중에서 C의 역할

	// 부장
	ProfessorService prosessservice;

	@Override
	public void init() throws ServletException {

		prosessservice = new ProfessorService();
	}

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

// 핸들러		
	protected void doHandle(HttpServletRequest request, 
				  				HttpServletResponse response) 
				  						throws ServletException, IOException {
			
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=utf-8");
			//웹브라우저로 출력할 출력 스트림 생성
			PrintWriter out = response.getWriter();
		
			
			//조건에 따라서 포워딩 또는 보여줄 VIEW주소 경로를 저장할 변수
			String nextPage = null;
			
			//요청한 중앙화면 뷰 주소를 저장할 변수
			String center = null;
			
			
			String action = request.getPathInfo();//2단계 요청주소 
			System.out.println("요청한 2단계 주소 : " + action);
			
			
			switch(action) {
			
				case "/professor.do":
					
					int result = 0;
					
					//요청 파라미터 가져오기
					String user_id  = request.getParameter("user_id");
					String user_pw  = request.getParameter("user_pw");
					String professor_id = request.getParameter("professor_id");
					String p_name = request.getParameter("p_name");
					String p_birthDate = request.getParameter("p_birthDate");				
			        String p_gender = request.getParameter("p_gender");
			        
					String address1 = request.getParameter("address1");
					String address2 = request.getParameter("address2");
					String address3 = request.getParameter("address3");
					String address4 = request.getParameter("address4");
					String address5 = request.getParameter("address5");
					String p_address = address1 + address2 
							              + address3 + address4 + address5;
					
					String p_phone = request.getParameter("p_phone");
					String professorMajorCode  = request.getParameter("majorcode");
					String p_email = request.getParameter("p_email");
					String p_employDate = request.getParameter("p_employDate");
			        String role = request.getParameter("role");
			
			        
			        // professorVo 객체 생성
					ProfessorVo vo = new ProfessorVo();
							vo.setUser_id(user_id);
					        vo.setUser_pw(user_pw);
					        vo.setP_name(p_name);
					        vo.setP_brithDate(Date.valueOf(p_birthDate));
					        vo.setP_Gender(p_gender);
					        vo.setP_address(p_address);
					        vo.setP_phone(p_phone);
					        vo.setP_email(p_email);
					        vo.setProfessor_id(professor_id);
					        vo.setMajorcode(professorMajorCode );
					        vo.setP_employDate(Date.valueOf(p_employDate));
					        vo.setRole("교수");	
					        
					        result = prosessservice.registerProfessor(vo);
					        //prosessservice.registerProfessor(request);
					
					      //출력 스트림 PrintWriter객체 생성
							out = response.getWriter();
							if(result == 1) {
							out.print("<script>");
							out.print(" alert('등록되었습니다.'); ");
							out.print(" location.href='"+request.getContextPath()
														+"/kyeong/professoradd.jsp'");
							out.print("</script>");
							} else {out.print("<script>");
							out.print(" alert('등록실패.'); ");
							out.print(" location.href='"+request.getContextPath()
														+"/kyeong/professoradd.jsp'");
							out.print("</script>");
							}
							
					     
					        nextPage = "/professorinquiry.jsp"; 
					        
					        break;
					        
		    	/*				
				// 교수 조회	        
				case "/professorquiry.do":
				    String profId = request.getParameter("profId");
				    String majorcode = request.getParameter("majorcode");
	
				    List<ProfessorVo> professorList = null;
				    try {
				        professorList = prosessservice.professorService(profId, majorcode);
				        
				        // Handle the case when no professors are found
				        if (professorList == null || professorList.isEmpty()) {
				            request.setAttribute("message", "교수 정보를 찾을 수 없습니다.");
				        } else {
				            request.setAttribute("v", professorList);
				        }
				    } catch (SQLException e) {
				        e.printStackTrace();
				        request.setAttribute("error", "교수 정보를 조회하는 중 오류가 발생했습니다.");
				    }
	
				    nextPage = "/professorinquiry.jsp";  // JSP page for showing results
				    break;
	*/			        
			/*				
							 case "/update.do":
							  	// 1. professor_id 받기
							        String professorId = request.getParameter("professor_id");
							
							        // 2. DB에서 교수 정보 조회
							        ProfessorDao professorDao = new ProfessorDao();
							        ProfessorVo professor = null;
							        try {
							            professor = professorDao.getProfessorById(professorId); // 해당 교수의 정보를 DB에서 조회
							        } catch (SQLException e) {
							            e.printStackTrace();
							        }
							
							        // 3. 교수 정보 수정 폼에 전달
							        request.setAttribute("professor", professor);
							        RequestDispatcher dispatcher = request.getRequestDispatcher("/professorUpdateForm.jsp");
							        dispatcher.forward(request, response);
							    }
							    
							     // 1. 수정된 교수 정보 받기
							        String professorId = request.getParameter("professor_id");
							        String pName = request.getParameter("p_name");
							        String pBirthDate = request.getParameter("p_brithDate");
							        String pGender = request.getParameter("p_gender");
							        String pAddress = request.getParameter("p_address");
							        String pPhone = request.getParameter("p_phone");
							        String pEmail = request.getParameter("p_email");
							        String pEmployDate = request.getParameter("p_employDate");
							
							        // 2. ProfessorVo 객체에 수정된 정보 설정
							        ProfessorVo updatedProfessor = new ProfessorVo();
							        updatedProfessor.setProfessor_id(professorId);
							        updatedProfessor.setP_name(pName);
							        updatedProfessor.setP_brithDate(Date.valueOf(pBirthDate));
							        updatedProfessor.setP_Gender(pGender);
							        updatedProfessor.setP_address(pAddress);
							        updatedProfessor.setP_phone(pPhone);
							        updatedProfessor.setP_email(pEmail);
							        updatedProfessor.setP_employDate(Date.valueOf(pEmployDate));
							
							        // 3. DB에서 정보 수정
							        ProfessorDao professorDao = new ProfessorDao();
							        try {
							            professorDao.updateProfessor(updatedProfessor);  // DB에 수정된 정보 업데이트
							            response.sendRedirect("/professorinquiry.jsp");  // 수정 후 교수 목록 페이지로 리다이렉트
							        } catch (SQLException e) {
							            e.printStackTrace();
							            response.sendRedirect("/errorPage.jsp");  // 에러 페이지로 리다이렉트
							        }
							    }
							    
					    
					    
			    
			    
			    
			  -----------------------------------------------------------------------------------------------------------  
//						     예약한 정보 수정하기위해 예약한 아이디로 예약정보 조회요청!	
						case "/update.do":
							
							//<a href="${contextPath}/Car/update.do?orderid=${vo.orderid}&carimg=${vo.carimg}&memberpass=${requestScope.memberpass}&memberphone=${requestScope.memberphone}">
							   //예약수정
							//</a>			
									//요청한 값 얻기
									int orderid = Integer.parseInt(request.getParameter("orderid"));
									String carimg = request.getParameter("carimg");
									String memberphone = request.getParameter("memberphone");
									String memberpass = request.getParameter("memberpass");
									
									//예약 아이디를 이용해 예약한 정보를 DB에서 조회하기 위해
									//CarDAO객체의 getOneOrder메소드 호출할때 
									//매개변수로 orderid를 전달 하여 조회 해 오자
									CarConfirmVo vo = cardao.getOneOrder(orderid);
												 vo.setCarimg(carimg);
											
									//중앙화면 VIEW("CarConfirmUpdate.jsp")에 조회된 예약정보를 보여주기 위해
									//일단~~~ request내장객체 영역에 CarConfirmVO객체 바인딩
									request.setAttribute("vo", vo);
									
									request.setAttribute("memberphone", memberphone);
									request.setAttribute("memberpass", memberpass);
									
									request.setAttribute("center", "CarConfirmUpdate.jsp");//중앙 VIEW
									
									
									nextPage = "/CarMain.jsp";
									
								
								//입력한 정보를 DB에 UPDATE수정 해주세요!
						case "/updatePro.do";
									
									//수정을 위해 입력한 정보들은 request내장객체 메모리에 저장되어 있으므로
									//DB에 UPDATE시키기 위해 CarDAO객체의 carOrderUpdate메소드 호출할때
									//매개변수로 request객체 메모리의 주소를 전달해 UPDATE시키자
									int result = cardao.carOrderUpdate(request);
										
									int orderid = Integer.parseInt(request.getParameter("orderid"));
									String memberphone = request.getParameter("memberphone");
									String carimg = request.getParameter("carimg");
									
									
									if(result == 1) {
										pw.print("<script>");
										pw.print(" alert('예약 정보가 수정 되었습니다.'); ");
										pw.print(" location.href='"+ request.getContextPath() +"/Car/update.do?orderid="+orderid+"&carimg="+carimg+"&memberphone="+memberphone+"'");	
										pw.print("</script>");
										
										return;
									}else {
										
										pw.print("<script>");
										pw.print(" alert('예약정보 수정 실패'); ");
										pw.print(" history.back(); ");
										pw.print("</script>");
										
										return;
									}
				----------------------------------------------------------------------------------------------			
								
								//예약 (취소)삭제 요청 을 받았다면
						case "/delete.do"
									
									 // 1. professor_id 받기
			        String professorId = request.getParameter("professor_id");

			        // 2. DB에서 교수 정보 삭제
			        ProfessorDao professorDao = new ProfessorDao();
			        try {
			            professorDao.deleteProfessor(professorId);  // 해당 교수 삭제
			            response.sendRedirect("/professorinquiry.jsp");  // 삭제 후 교수 목록 페이지로 리다이렉트
			        } catch (SQLException e) {
			            e.printStackTrace();
			            response.sendRedirect("/errorPage.jsp");  // 에러 페이지로 리다이렉트
			        }
									
				----------------------------------------------------------------------------------------------------				
									//응답할 값 마련 
									//예약정보를 삭제(취소)하기 위해 CarDAO객체의 OrderDelete메소드 호출할때
									//매개변수로 삭제할 예약아이디와 입력한 비밀번호 전달하여 DB에서 DELETE시키자
									//삭제에 성공하면 OrderDelete 메소드의 반환값은 
									//삭제에 성공한 레코드 개수 1을 반환받고
									//실패하면 0을 반환 받습니다.	
									int result = cardao.OrderDelete(orderid, memberpass);
									
									if(result == 1) {
										pw.print("<script>");
										pw.print(" alert('교수 정보가 삭제 되었습니다.'); ");
										pw.print(" location.href='"+ request.getContextPath() +"/Car/CarReserveConfirm.do?memberphone="+memberphone+"&memberpass="+memberpass+"';");	
										pw.print("</script>");
										
										return;
									}else {
										
										pw.print("<script>");
										pw.print(" alert('교수정보 삭제 실패'); ");
										pw.print(" history.back(); ");
										pw.print("</script>");
										
										return;
									}	
				*/			

				  default: 
					  break;
				 
			}
	
			//디스패처 방식 포워딩(재요청)
			RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
			dispatch.forward(request, response);
		
		}// doHandle메소드
}
