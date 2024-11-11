package professorService;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import professorDao.ProfessorDao;
import professorVo.ProfessorVo;



public class ProfessorService {

	
		ProfessorDao prosessDao;
		
	
		public ProfessorService() {
			prosessDao = new ProfessorDao();
		}

		
		//교수등록
		  public void serviceInsertProsess(ProfessorVo vo) {
		  	  
			 prosessDao.insertProfessor(vo);  
		  }
		  
		  
		  // 교수 등록(가입) 요청
		    public int registerProfessor(ProfessorVo vo) {
		    	
		        // 교수 등록 처리
		        int result = prosessDao.insertProfessor(vo);
		       
		        return result;  // insert가 성공적으로 실행되면 1 이상의 값이 리턴됨
		    }
		
/*		
		//교수 조회

		    public List<ProfessorVo> professorService(String profId, String majorcode) throws SQLException {
		        List<ProfessorVo> professorList = null;

		        try {
		            professorList = ProfessorDao.getProfessorList(profId, majorcode);
		            if (professorList == null || professorList.isEmpty()) {
		               
		                System.out.println("교수를 조회할 수 없습니다.");
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		            
		            throw new SQLException("professorService에서 오류", e);
		        }

		        return professorList;
		    } */
		    
}

		 

		
	

