package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import Vo.BoardVo;

public class BoardDAO {

    Connection con;
    PreparedStatement pstmt;
    ResultSet rs;
    DataSource ds;

    // 생성자: 커넥션 풀을 초기화합니다.
    public BoardDAO() {
        try {
            Context ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/edumanager");
        } catch (Exception e) {
            System.out.println("커넥션풀 얻기 실패 : " + e.toString());
        }
    }

    // 자원 해제: 사용한 Connection, PreparedStatement, ResultSet을 해제합니다.
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

    // 모든 게시글을 조회하여 반환하는 메소드
    public ArrayList<BoardVo> boardListAll() {
        String sql = "SELECT * FROM notice ORDER BY b_group ASC";
        ArrayList<BoardVo> list = new ArrayList<>();

        try {
            con = ds.getConnection();
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            // 조회된 결과를 반복하여 리스트에 추가
            while (rs.next()) {
                BoardVo vo = new BoardVo(
                    rs.getInt("notice_id"),
                    rs.getInt("b_group"),
                    rs.getInt("b_level"),
                    rs.getString("author_id"),
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getDate("created_date")
                );
                list.add(vo);
            }
        } catch (Exception e) {
            System.out.println("BoardDAO의 boardListAll메소드에서 오류 ");
            e.printStackTrace();
        } finally {
            closeResource();
        }
        return list;
    }

    // 검색 조건에 따라 게시글 목록을 조회하는 메소드
    public ArrayList<BoardVo> boardList(String key, String word) {
        String sql;
        ArrayList<BoardVo> list = new ArrayList<>();

        // 검색어가 있는 경우 조건에 따라 SQL 설정
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

            // SQL에 파라미터 설정
            if (!word.equals("")) {
                if (key.equals("titleContent")) {
                    pstmt.setString(1, "%" + word + "%");
                    pstmt.setString(2, "%" + word + "%");
                } else {
                    pstmt.setString(1, "%" + word + "%");
                }
            }

            rs = pstmt.executeQuery();

            // 조회된 결과를 반복하여 리스트에 추가
            while (rs.next()) {
                BoardVo vo = new BoardVo(
                    rs.getInt("notice_id"),
                    rs.getInt("b_group"),
                    rs.getInt("b_level"),
                    rs.getString("author_id"),
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getDate("created_date")
                );
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

    // 새 게시글을 추가하는 메소드
    public int insertBoard(BoardVo vo) {
        int result = 0;
        String sql;

        try {
            con = ds.getConnection();

            // 기존 글들의 그룹 값을 증가시킴
            sql = "UPDATE notice SET b_group = b_group + 1";
            pstmt = con.prepareStatement(sql);
            pstmt.executeUpdate();

            // 새로운 글을 삽입
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

    // 특정 글 번호로 게시글을 조회하는 메소드
    public BoardVo boardRead(String notice_id) {
        BoardVo vo = null;
        String sql = "SELECT * FROM notice WHERE notice_id=?";

        try {
            con = ds.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(notice_id));
            rs = pstmt.executeQuery();

            if (rs.next()) {
                vo = new BoardVo(
                    rs.getInt("notice_id"),
                    rs.getInt("b_group"),
                    rs.getInt("b_level"),
                    rs.getString("author_id"),
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getDate("created_date")
                );
            }
        } catch (Exception e) {
            System.out.println("BoardDAO의 boardRead메소드에서 오류");
            e.printStackTrace();
        } finally {
            closeResource();
        }
        return vo;
    }

    // 수정시 입력한 글 정보를 DB에 UPDATE(열의 값을 수정)하는 메소드
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

    // 삭제할 글번호를 매개변수로 받아 글(레코드)삭제후 삭제에 성공하면 "삭제성공" 반환 실패하면 "삭제실패" 반환 하는 메소드
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

    // 답글을 추가하는 메소드
    public void replyInsertBoard(String super_notice_id, String reply_writer, String reply_title, String reply_content, String reply_id) {
        String sql;
        try {
            con = ds.getConnection();

            // 부모 글의 그룹과 레벨을 조회
            sql = "SELECT b_group, b_level FROM notice WHERE notice_id=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(super_notice_id));
            rs = pstmt.executeQuery();
            rs.next();
            int b_group = rs.getInt("b_group");
            int b_level = rs.getInt("b_level");

            // b_group 업데이트
            sql = "UPDATE notice SET b_group = b_group + 1 WHERE b_group > ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, b_group);
            pstmt.executeUpdate();

            // 답글을 삽입
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

    // 특정 기간 내의 이벤트를 조회하는 메소드
    public List<BoardVo> getEvents(String startDate, String endDate) {
        List<BoardVo> events = new ArrayList<>();
        String sql = "SELECT schedule_id, event_name, description, start_date, end_date FROM academic_schedule WHERE start_date <= ? AND end_date >= ?";

        try {
            con = ds.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, endDate);
            pstmt.setString(2, startDate);
            rs = pstmt.executeQuery();

            // 조회된 결과를 반복하여 리스트에 추가
            while (rs.next()) {
                BoardVo event = new BoardVo();
                event.setNotice_id(rs.getInt("schedule_id"));
                event.setEvent_name(rs.getString("event_name"));
                event.setDescription(rs.getString("description"));
                event.setStart_date(rs.getDate("start_date"));
                event.setEnd_date(rs.getDate("end_date"));
                events.add(event);
            }
        } catch (Exception e) {
            System.out.println("BoardDAO의 getEvents메소드에서 오류");
            e.printStackTrace();
        } finally {
            closeResource();
        }
        return events;
    }
}
