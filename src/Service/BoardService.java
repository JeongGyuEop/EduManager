package Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import Dao.BoardDAO;
import Dao.MemberDAO;
import Vo.BoardVo;
import Vo.BookPostVo;
import Vo.MemberVo;
import Vo.ScheduleVo;

public class BoardService {

	BoardDAO boarddao;
	MemberDAO memberdao;

	public BoardService() {
		boarddao = new BoardDAO();
		memberdao = new MemberDAO();
	}

	// 특정 회원 정보 조회 서비스
	public MemberVo serviceMemberOne(String reply_id_) {
		return memberdao.memberOne(reply_id_);
	}

	// 게시글 작성 서비스
	public int serviceInsertBoard(BoardVo vo) {
		return boarddao.insertBoard(vo);
	}

	// 전체 게시글 목록 조회 서비스
	public ArrayList serviceBoardList() {
		return boarddao.boardListAll();
	}

	// 키워드 검색을 통한 게시글 목록 조회 서비스
	public ArrayList serviceBoardKeyWord(String key, String word) {
		return boarddao.boardList(key, word);
	}

	// 특정 게시글 읽기 서비스
	public BoardVo serviceBoardRead(String notice_id) {
		return boarddao.boardRead(notice_id);
	}

	// 게시글 수정 서비스
	public int serviceUpdateBoard(String notice_id_2, String title_, String content_) {
		return boarddao.updateBoard(notice_id_2, title_, content_);
	}

	// 게시글 삭제 서비스
	public String serviceDeleteBoard(String delete_idx) {
		return boarddao.deleteBoard(delete_idx);
	}

	// 답글 작성 서비스
	public void serviceReplyInsertBoard(String super_notice_id, String reply_writer, String reply_title,
			String reply_content, String reply_id) {
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

	public int bookPostUploadService(HttpServletRequest request) { // 업로드를 위해 사용될 정보만 가져옵니다.
		String userId = request.getParameter("userId"); // 유저 아이디, hidden을 통해 받아왔습니다.
		String postTitle = request.getParameter("postTitle"); // 글 제목
		String postContent = request.getParameter("postContent"); // 글 내용
		String majorTag = request.getParameter("majorTag"); // 책과 관련된 학과명
		ArrayList<BookPostVo.BookImage> bookImages = new ArrayList<>(); // BookImage 객체 리스트를 저장하는 변수
	    try {
	        Collection<Part> images = request.getParts(); // 이미지 정보 저장
	        for (Part image : images) {
	            if (image.getName().equals("image") && image.getSize() > 0) {
	                // 파일 이름 가져오기
	                String fileName = Paths.get(image.getSubmittedFileName()).getFileName().toString();
	                // BookImage 객체 생성 후 리스트에 추가
	                BookPostVo.BookImage bookImage = new BookPostVo.BookImage();
	                bookImage.setFileName(fileName);
	                bookImages.add(bookImage);
	            }
	        }
	    } catch (IOException | ServletException e) {
	        e.printStackTrace();
	    }
		
	    // BookPostVO 객체 생성 및 데이터 설정
	    BookPostVo bookPostVo = new BookPostVo();
	    bookPostVo.setUserId(userId);
	    bookPostVo.setPostTitle(postTitle);
	    bookPostVo.setPostContent(postContent);
	    bookPostVo.setMajorTag(majorTag);
	    bookPostVo.setImages(bookImages); // 이미지 리스트 설정

	    // DAO 호출하여 데이터베이스에 저장
	    int result = boarddao.bookPostUpload(bookPostVo);

	    return result;
	}
}
