package Service;

import java.util.ArrayList;
import java.util.List;

import Dao.BoardDAO;
import Dao.MemberDAO;
import Vo.BoardVo;
import Vo.MemberVo;

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
    public void serviceReplyInsertBoard(String super_notice_id, String reply_writer, String reply_title, String reply_content, String reply_id) {
        boarddao.replyInsertBoard(super_notice_id, reply_writer, reply_title, reply_content, reply_id);
    }

    // 일정 이벤트 조회 서비스 (캘린더)
    public List<BoardVo> getEvents(String startDate, String endDate) throws Exception {
        return boarddao.getEvents(startDate, endDate);
    }
}
