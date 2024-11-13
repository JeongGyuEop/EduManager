package professorService;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ProfessorDAO.ProfessorDao;
import ProfessorVO.ProfessorVO;




public class ProfessorService {

	
		ProfessorDao professorDao;
		
	
		public ProfessorService() {
			professorDao = new ProfessorDao();
		}

		
		//교수등록
		  public int serviceInsertProsess(ProfessorVO vo) {
		  	  
			  return professorDao.insertProfessor(vo);  
		  }
		  
		
		    
		 // 전체 교수 조회 서비스
		    public List<ProfessorVO> getProfessors(String prof_id, String majorCode) {
		    	
     
		       
		    	// 교수 조회 처리 Dao시킴
		        return professorDao.getProfessorList(prof_id, majorCode);
		    }
		    
		    

		    
		    
		 // 교수 정보 수정
		    public boolean updateProfessor(ProfessorVO professor) {
		    			    
		        return professorDao.updateProfessor(professor);
		    }

		    
		    //교수 삭제
			public boolean deleteProfessor(String professorId) {
				
				 //삭제에 성공하면 "삭제성공" 반환 실패하면 "삭제실패" 반환 
				return professorDao.deleteProfessor(professorId);
				
			}


				
				
		    
} 
		
		    
		   


		 

		
	

