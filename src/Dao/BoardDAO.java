package Dao;

import java.awt.PageAttributes.OrientationRequestedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import Vo.BoardVo;
import Vo.ScheduleVo;
import Vo.StudentVo;

public class BoardDAO {

	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	DataSource ds;

	public BoardDAO() {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/edumanager"); // 데이터베이스 커넥션 풀 초기화
		} catch (Exception e) {
			System.out.println("커넥션풀 얻기 실패 : " + e.toString());
		}
	}

	// 자원 해제 메서드
	private void closeResource() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 모든 게시글 목록 조회 메서드
	public ArrayList<BoardVo> boardListAll() {
		String sql = "SELECT * FROM notice ORDER BY b_group ASC";
		ArrayList<BoardVo> list = new ArrayList<>();

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				BoardVo vo = new BoardVo(rs.getInt("notice_id"), rs.getInt("b_group"), rs.getInt("b_level"),
						rs.getString("author_id"), rs.getString("title"), rs.getString("content"),
						rs.getDate("created_date"));
				list.add(vo);
			}
		} catch (Exception e) {
			System.out.println("BoardDAO의 boardListAll메소드에서 오류");
			e.printStackTrace();
		} finally {
			closeResource();
		}
		return list;
	}

	// 특정 키워드를 통한 게시글 목록 조회 메서드
	public ArrayList<BoardVo> boardList(String key, String word) {
		String sql;
		ArrayList<BoardVo> list = new ArrayList<>();

		if (!word.equals("")) {
			if (key.equals("titleContent")) {
				sql = "SELECT * FROM notice WHERE title LIKE ? OR content LIKE ? ORDER BY b_group ASC";
			} else {
				sql = "SELECT * FROM notice WHERE author_id LIKE ? ORDER BY b_group ASC";
			}
		} else {
			sql = "SELECT * FROM notice ORDER BY b_group ASC";
		}

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);

			if (!word.equals("")) {
				if (key.equals("titleContent")) {
					pstmt.setString(1, "%" + word + "%");
					pstmt.setString(2, "%" + word + "%");
				} else {
					pstmt.setString(1, "%" + word + "%");
				}
			}

			rs = pstmt.executeQuery();

			while (rs.next()) {
				BoardVo vo = new BoardVo(rs.getInt("notice_id"), rs.getInt("b_group"), rs.getInt("b_level"),
						rs.getString("author_id"), rs.getString("title"), rs.getString("content"),
						rs.getDate("created_date"));
				list.add(vo);
			}
		} catch (Exception e) {
			System.out.println("BoardDAO의 boardList메소드에서 오류");
			e.printStackTrace();
		} finally {
			closeResource();
		}
		return list;
	}

	// 게시글 삽입 메서드
	public int insertBoard(BoardVo vo) {
		int result = 0;
		String sql;

		try {
			con = ds.getConnection();
			sql = "UPDATE notice SET b_group = b_group + 1"; // 그룹 값을 업데이트하여 게시글 정렬 순서 유지
			pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate();

			sql = "INSERT INTO notice (title, content, created_date, author_id, b_group, b_level) VALUES (?, ?, NOW(), ?, 0, 0)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setString(3, vo.getAuthor_id());

			result = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("BoardDAO의 insertBoard메소드에서 오류");
			e.printStackTrace();
		} finally {
			closeResource();
		}
		return result;
	}

	// 특정 게시글 읽기 메서드
	public BoardVo boardRead(String notice_id) {
		BoardVo vo = null;
		String sql = "SELECT * FROM notice WHERE notice_id=?";

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(notice_id));
			rs = pstmt.executeQuery();

			if (rs.next()) {
				vo = new BoardVo(rs.getInt("notice_id"), rs.getInt("b_group"), rs.getInt("b_level"),
						rs.getString("author_id"), rs.getString("title"), rs.getString("content"),
						rs.getDate("created_date"));
			}
		} catch (Exception e) {
			System.out.println("BoardDAO의 boardRead메소드에서 오류");
			e.printStackTrace();
		} finally {
			closeResource();
		}
		return vo;
	}

	// 게시글 수정 메서드
	public int updateBoard(String notice_id, String title, String content) {
		int result = 0;
		String sql = "UPDATE notice SET title=?, content=? WHERE notice_id=?";

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setInt(3, Integer.parseInt(notice_id));

			result = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("BoardDAO의 updateBoard메소드에서 오류");
			e.printStackTrace();
		} finally {
			closeResource();
		}
		return result;
	}

	// 게시글 삭제 메서드
	public String deleteBoard(String delete_idx) {
		String result;
		String sql = "DELETE FROM notice WHERE notice_id=?";

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(delete_idx));
			int val = pstmt.executeUpdate();

			result = (val == 1) ? "삭제성공" : "삭제실패";
		} catch (Exception e) {
			System.out.println("BoardDAO의 deleteBoard메소드에서 오류");
			e.printStackTrace();
			result = "삭제실패";
		} finally {
			closeResource();
		}
		return result;
	}

	// 답글 삽입 메서드
	public void replyInsertBoard(String super_notice_id, String reply_writer, String reply_title, String reply_content,
			String reply_id) {
		String sql;
		try {
			con = ds.getConnection();
			sql = "SELECT b_group, b_level FROM notice WHERE notice_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(super_notice_id));
			rs = pstmt.executeQuery();
			rs.next();
			int b_group = rs.getInt("b_group");
			int b_level = rs.getInt("b_level");

			sql = "UPDATE notice SET b_group = b_group + 1 WHERE b_group > ?"; // 그룹 업데이트하여 정렬 유지
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, b_group);
			pstmt.executeUpdate();

			sql = "INSERT INTO notice (title, content, created_date, author_id, b_group, b_level) VALUES (?, ?, NOW(), ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, reply_title);
			pstmt.setString(2, reply_content);
			pstmt.setString(3, reply_id);
			pstmt.setInt(4, b_group + 1);
			pstmt.setInt(5, b_level + 1);
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("BoardDAO의 replyInsertBoard메소드에서 오류");
			e.printStackTrace();
		} finally {
			closeResource();
		}
	}

	// 월별 일정 조회 메서드
	public List<ScheduleVo> getEventsByMonth(String year, String month) {
		List<ScheduleVo> events = new ArrayList<>();
		String EndDay = String.valueOf(YearMonth.of(Integer.parseInt(year), Integer.parseInt(month)).lengthOfMonth());
		String monthStart = year + "-" + String.format("%02d", Integer.parseInt(month)) + "-01";
		String monthEnd = year + "-" + String.format("%02d", Integer.parseInt(month)) + "-" + EndDay;

		String sql = "SELECT * FROM academic_schedule WHERE start_date <= ? AND end_date >= ?";
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, monthEnd);
			pstmt.setString(2, monthStart);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ScheduleVo event = new ScheduleVo();
				event.setSchedule_id(rs.getInt("schedule_id"));
				event.setEvent_name(rs.getString("event_name"));
				event.setDescription(rs.getString("description"));
				event.setStart_date(rs.getDate("start_date"));
				event.setEnd_date(rs.getDate("end_date"));
				events.add(event);
			}
		} catch (SQLException e) {
			System.out.println("BoardDAO의 getEventsByMonth 메소드에서 오류");
			e.printStackTrace();
		} finally {
			closeResource();
		}
		return events;
	}

	// 특정 기간의 일정 조회 메서드
	public List<ScheduleVo> getEvents(String startDate, String endDate) {
		List<ScheduleVo> events = new ArrayList<>();
		String sql = "SELECT * FROM academic_schedule WHERE start_date <= ? AND end_date >= ?";
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);

			if (startDate == null || endDate == null || startDate.length() < 10 || endDate.length() < 10) {
				throw new IllegalArgumentException("유효하지 않은 날짜 파라미터.");
			}
			String start = startDate.substring(0, 10);
			String end = endDate.substring(0, 10);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			sdf.setLenient(false);
			try {
				sdf.parse(start);
				sdf.parse(end);
			} catch (ParseException e) {
				throw new IllegalArgumentException("유효하지 않은 날짜 형식: " + startDate + ", " + endDate);
			}

			pstmt.setString(1, end);
			pstmt.setString(2, start);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ScheduleVo event = new ScheduleVo(
		                rs.getInt("schedule_id"),
		                rs.getString("event_name"),
		                rs.getString("description"),
		                rs.getDate("start_date"),
		                rs.getDate("end_date")
		            );
				events.add(event);
			}
		} catch (SQLException e) {
			System.out.println("BoardDAO의 getEvents 메소드에서 오류");
			throw new RuntimeException("데이터베이스 오류", e);
		} catch (IllegalArgumentException e) {
			System.out.println("BoardDAO의 getEvents 메소드에서 유효성 검증 실패: " + e.getMessage());
			throw e;
		} finally {
			closeResource();
		}
//		System.out.println(events);
		return events;
	}

	public boolean isValidSchedule(String eventName, String startDate, String endDate, String description) {
        // 간단한 유효성 검사 로직을 구현
        if (eventName == null || eventName.isEmpty()) {
            return false;
        }
        if (startDate == null || startDate.isEmpty()) {
            return false;
        }
        if (endDate == null || endDate.isEmpty()) {
            return false;
        }
        if (description == null || description.isEmpty()) {
            return false;
        }
        return true;
    }

    public void insertSchedule(ScheduleVo schedule) {
        String sql = "INSERT INTO academic_schedule (event_name, start_date, end_date, description) VALUES (?, ?, ?, ?)";
        try {
            con = ds.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, schedule.getEvent_name());
            pstmt.setDate(2, schedule.getStart_date());
            pstmt.setDate(3, schedule.getEnd_date());
            pstmt.setString(4, schedule.getDescription());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("BoardDAO의 insertSchedule메소드에서 오류");
            e.printStackTrace();
            throw new RuntimeException("일정을 추가하는 중 오류가 발생했습니다.");
        } finally {
            closeResource();
        }
    }
    
    public void updateSchedule(ScheduleVo schedule) {
        String sql = "UPDATE academic_schedule SET event_name = ?, start_date = ?, end_date = ?, description = ? WHERE schedule_id = ?";
        try {
            con = ds.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, schedule.getEvent_name());
            pstmt.setDate(2, schedule.getStart_date());
            pstmt.setDate(3, schedule.getEnd_date());
            pstmt.setString(4, schedule.getDescription());
            pstmt.setInt(5, schedule.getSchedule_id());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("BoardDAO의 updateSchedule메소드에서 오류");
            e.printStackTrace();
            throw new RuntimeException("일정을 수정하는 중 오류가 발생했습니다.");
        } finally {
            closeResource();
        }
    }

    public void deleteSchedule(int scheduleId) {
        String sql = "DELETE FROM academic_schedule WHERE schedule_id = ?";
        try {
            con = ds.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, scheduleId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("BoardDAO의 deleteSchedule메소드에서 오류");
            e.printStackTrace();
            throw new RuntimeException("일정을 삭제하는 중 오류가 발생했습니다.");
        } finally {
            closeResource();
        }
    }
    
//----------- 중고 책 거래 ------------------------------------------------------    

public int bookPostupload(String titlel, String contentl, String majorl) {
	
	int result = 0;
	
	
	

	
	return result;
	
}
   
   
    
//----------- 중고 책 거래 ------------------------------------------------------    
    
}
