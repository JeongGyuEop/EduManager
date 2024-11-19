package Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import Dao.BoardDAO;
import Dao.MemberDAO;
import Vo.BoardVo;
import Vo.MemberVo;
import Vo.ScheduleVo;

public class BoardService {

	BoardDAO boarddao;
	MemberDAO memberdao;

	public BoardService() {
		boarddao = new BoardDAO();
		memberdao = new MemberDAO();
	}
	
	//회원 아이디를 매개변수로 받아서 회원 한명을 조회 후 반환하는 기능의 메소드
		public MemberVo serviceMemberOne(String reply_id_) {
			return memberdao.memberOne(reply_id_);
		}
		
		//작성한 새글 정보 하나를 DB의 board테이블에 추가(INSERT)기능의 메소드
		public int serviceInsertBoard(BoardVo vo) {
			 return  boarddao.insertBoard(vo); //1 또는 0을 반환 받아 다시 반환(리턴)
		}
		
		
		//DB의 board테이블에 저장되어 있는 모든 글목록을 조회하는 기능의 메소드
		public ArrayList serviceBoardList() {
			
			return boarddao.boardListAll();
		}
		
		
		//검색기준값과 입력한 검색어를 포함하고 있는 글목록을 조회하는 기능의 메소드
		public ArrayList serviceBoardKeyWord(String key, String word) {
			
			return boarddao.boardList(key,word);//BoardController사장에게 리턴(보고)
		}
		
		//글번호(b_idx)를 이용해 조회 명령하는 기능의 메소드
		 public BoardVo serviceBoardRead(String notice_id) {
			 
			 return boarddao.boardRead(notice_id);
		 }
		
		// 글 수정 요청 UPDATE 하기 위한 메소드 호출!
		public int serviceUpdateBoard(String notice_id_2, String title_, String content_) {
			       //수정에 성공하면 1을 반환 실패하면 0을 반환
			return boarddao.updateBoard(notice_id_2, title_, content_);
		}

		// 글 삭제 요청 DELETE 하기 위한 메소드 호출!
		public String serviceDeleteBoard(String delete_notice_id) {
			
				   //삭제에 성공하면 "삭제성공" 반환 실패하면 "삭제실패" 반환 
			return boarddao.deleteBoard(delete_notice_id);
		}

		//DB의 Board테이블에 입력한 답변글 추가 하기 위해 호출!
		public void serviceReplyInsertBoard(String super_notice_id, String reply_writer, String reply_title, String reply_content, String reply_id) {
			boarddao.replyInsertBoard(super_notice_id, reply_writer, reply_title, reply_content, reply_id);
		}

	// 일정 이벤트 조회 서비스 (캘린더)
	public List<ScheduleVo> getEvents(String startDate, String endDate) throws Exception {
		return boarddao.getEvents(startDate, endDate);
	}
	
	public void processViewSchedule(HttpServletRequest request) {
        String month = request.getParameter("month");
        if (month != null && !month.isEmpty()) {
            String[] parts = month.split("-");
            if (parts.length == 2) {
                String year = parts[0];
                String monthPart = parts[1];
                List<ScheduleVo> scheduleList = boarddao.getEventsByMonth(year, monthPart);
                request.setAttribute("scheduleList", scheduleList);
            }
        }
    }
	
	public void addSchedule(HttpServletRequest request) {
		String title = request.getParameter("title");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String content = request.getParameter("content");

		if (boarddao.isValidSchedule(title, startDate, endDate, content)) {
			ScheduleVo newSchedule = new ScheduleVo();
			newSchedule.setEvent_name(title);
			newSchedule.setStart_date(Date.valueOf(startDate));
			newSchedule.setEnd_date(Date.valueOf(endDate));
			newSchedule.setDescription(content);

			boarddao.insertSchedule(newSchedule);
		} else {
			throw new IllegalArgumentException("일정 데이터가 유효하지 않습니다.");
		}
	}

	public void updateSchedule(HttpServletRequest request) {
		int scheduleId = Integer.parseInt(request.getParameter("schedule_id"));
		String title = request.getParameter("title");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String content = request.getParameter("content");

		if (boarddao.isValidSchedule(title, startDate, endDate, content)) {
			ScheduleVo updatedSchedule = new ScheduleVo();
			updatedSchedule.setSchedule_id(scheduleId);
			updatedSchedule.setEvent_name(title);
			updatedSchedule.setStart_date(Date.valueOf(startDate));
			updatedSchedule.setEnd_date(Date.valueOf(endDate));
			updatedSchedule.setDescription(content);

			boarddao.updateSchedule(updatedSchedule);
		} else {
			throw new IllegalArgumentException("일정 데이터가 유효하지 않습니다.");
		}
	}

	public void deleteSchedule(HttpServletRequest request) {
		String[] deleteIds = request.getParameterValues("deleteIds");
		if (deleteIds != null) {
			for (String id : deleteIds) {
				int scheduleId = Integer.parseInt(id);
				boarddao.deleteSchedule(scheduleId);
			}
		} else {
			throw new IllegalArgumentException("삭제할 일정이 선택되지 않았습니다.");
		}
	}
}
