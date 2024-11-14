package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import Vo.BoardVo;



//MVC중에서 M을 얻기 위한 클래스 

//DB와 연결하여 비즈니스로직 처리하는 클래스 
//사원 
public class BoardDAO {
	
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	DataSource ds;
	
	//컨넥션풀 얻는 생성자
	public BoardDAO() {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource)ctx.lookup("java:/comp/env/jdbc/edumanager");
			
		}catch(Exception e) {
			System.out.println("커넥션풀 얻기 실패 : " + e.toString());
		}
	}
	//자원해제(Connection, PreparedStatment, ResultSet)기능의 메소드 
	private void closeResource() {
		try {
				if(con != null) { con.close(); }
				if(pstmt != null) { pstmt.close(); }
				if(rs != null) { rs.close(); }
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//현재 DB의 board테이블에 저장된 모든 게시글들 조회
	public ArrayList boardListAll() {
		
		String sql = null;
		
		ArrayList list = new ArrayList();
		
		try {
			con = ds.getConnection();//DB연결
			sql = "select * from notice order by b_group asc";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			//조회된 ResultSet의 정보를 레코드 단위로 얻어서 
			//BoardVo객체에 레코드 정보를 반복해서 저장하고 
			//BoardVo객체들을 ArrayList배열에 반복해서  추가
			while(rs.next()) {
				BoardVo vo = new BoardVo(rs.getInt("notice_id"),
										 rs.getInt("b_group"),
										 rs.getInt("b_level"),
										 rs.getString("author_id"),
										 rs.getString("title"), 
										 rs.getString("content"), 
										 rs.getDate("created_date"));
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
	
	//선택한 option의 value값(검색기준열의값)과 입력한 검색어 단어가 포함된 글목록 조회
	public ArrayList boardList(String key, String word) {
		
		String sql = null;
		
		ArrayList list = new ArrayList();
		
		//검색어를 입력했다면?
		if(!word.equals("")) {
			
			//검색기준열의 값  제목+내용 선택했다면?
			if(key.equals("titleContent")) {
				
				sql = "select * from notice "
					+ "where title like  '%"+word+"%' "
					+ "OR content like '%"+word+"%' "
					+ "order by b_group asc";
				
			}else {//검색기준열의 값 작성자 선택했다면?
				
				sql = "select * from notice "
				    + " where author_id like '%"+word+"%' "
				    + "order by b_group asc";
			} 
					
		}else {//검색어를 입력하지 않았다면?
			//모든 글의 열목록 조회
			sql = "select * from notice "
					+ "order by b_group asc";
			
		}
		
		try {
			con = ds.getConnection();//DB연결
			
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			//조회된 ResultSet의 정보를 레코드 단위로 얻어서 
			//BoardVo객체에 레코드 정보를 반복해서 저장하고 
			//BoardVo객체들을 ArrayList배열에 반복해서  추가
			while(rs.next()) {
				BoardVo vo = new BoardVo(rs.getInt("notice_id"),
						 rs.getInt("b_group"),
						 rs.getInt("b_level"),
						 rs.getString("author_id"),
						 rs.getString("title"), 
						 rs.getString("content"), 
						 rs.getDate("created_date"));
				list.add(vo);			
			}			
			
			
		} catch (Exception e) {
			System.out.println("BoardDAO의 boardList메소드 ");
			e.printStackTrace();
		} finally {
			closeResource();
		}
		
		return list; //BoardService부장에게 리턴(보고)	
	}
	
	//입력한 새글 정보를 DB의 board테이블에 추가insert 시키는 기능의 메소드
	public int insertBoard(BoardVo vo) {
		
		int result = 0;
		String sql = null;
		
		try {
			  con = ds.getConnection(); //DB연결
			  
			  //주글 insert 규칙2. 두번째 글부터 추가되는 글들의 pos(b_group)를 1증가 시킨다.
			  sql = "update notice set b_group = b_group + 1";
			  pstmt = con.prepareStatement(sql);
			  pstmt.executeUpdate();
			  
			  sql = "INSERT INTO notice (title, content, created_date, author_id, b_group, b_level)"
			           + " VALUES (?, ?, NOW(), ?, 0, 0)";
			  
			  pstmt = con.prepareStatement(sql);
			  pstmt.setString(1, vo.getTitle());
			  pstmt.setString(2, vo.getContent());
			  pstmt.setString(3, vo.getAuthor_id());
			  
			  result = pstmt.executeUpdate();//insert 성공하면 1 반환 실패하면 0반환
			  
		}catch(Exception e) {
			System.out.println("BoardDAO의 insertBoard메소드");
			e.printStackTrace();
		}finally {
			closeResource();
		}
		
		return result; // 1  또는 0을 반환(리턴) BoardService에게 
	}

	//글번호를 이용해 글하나 조회
	public BoardVo boardRead(String notice_id) {
		
		BoardVo vo = null;
		String sql = null;
		
		try {
			con = ds.getConnection();
			sql = "select * from notice where notice_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,  Integer.parseInt(notice_id));
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				//ResultSet에서 조회된 레코드의 모든 열값을 얻어
				//BoardVo객체 생성후 각변수에 저장
				vo = new BoardVo(rs.getInt("notice_id"),
						 rs.getInt("b_group"),
						 rs.getInt("b_level"),
						 rs.getString("author_id"),
						 rs.getString("title"), 
						 rs.getString("content"), 
						 rs.getDate("created_date"));
			}
		} catch (Exception e) {
			System.out.println("BoardDAO의 boardRead메소드");
			e.printStackTrace();
		} finally {
			closeResource();
		}
		
		return vo;
		
	}
	
	//수정시 입력한 글 정보를 DB에 UPDATE(열의 값을 수정)하는 메소드 
	public int updateBoard(String notice_id_2, 
						   String title_, 
						   String content_) {
		int result = 0;
		
		try {
			//DB연결
			con = ds.getConnection();
			//Board테이블의 b_idx열의 값이 매개변수로 전달받은 수정할 글번호이면
			//글번호가 저장된 레코드의 b_email열의 값, b_title열의 값, b_content열의 값을
			//우리가 수정시 입력한 값들로 변경해라(UPDATE)
			String sql = "update notice set "
					   + "title=?, content=? "
					   + "where notice_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, title_);
			pstmt.setString(2, content_);
			pstmt.setInt(3, Integer.parseInt(notice_id_2));
				
			result = pstmt.executeUpdate();//UPDATE성공하면 성공한 레코드 갯수 1반환
								 			//UPDAte실패하면 실패했으므로 레코드 갯수 0 반환

			
		} catch (Exception e) {
			System.out.println("BoardDAO의 updateBoard메소드");
			e.printStackTrace();
		} finally {
			//커넥션 객체 커넥션풀로 반납 
			//Resultset객체 사용후 제거
			//PreparedStatement객체 사용후 제거 
			closeResource();
		}
		
		return result; //1또는 0 반환 
	}
	
	//삭제할 글번호를 매개변수로 받아 글(레코드)삭제후 
	//삭제에 성공하면 "삭제성공" 반환 실패하면 "삭제실패" 반환 하는 메소드
	public String deleteBoard(String delete_notice_id) {
		
		//"삭제성공" 또는 "삭제실패" 메세지 저장할 변수
		String result = null;
		
		try {
			con = ds.getConnection();
			//Board테이블의 b_idx열(글번호가 저장되는 열)의 값이 매개변수로 전달받은 글번호와 같으면?
			//글번호가 저장된 행(글 하나의 정보 레코드)삭제 
			String sql = "DELETE FROM notice";
				   sql += " WHERE notice_id=?";
							/*
							DELETE FROM 테이블 명
							WHERE 조건
							*/
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(delete_notice_id));
			
			int val = pstmt.executeUpdate();
			
			if(val == 1)  result = "삭제성공";
			else result = "삭제실패";
			
		} catch (Exception e) {
			System.out.println("BoardDAO의 deleteBoard메소드");
			e.printStackTrace();
		} finally {
			closeResource();
		}
		return result;
	}
	
//작성한 답변들을 DB의 Board테이블에 INSERT
//규칙1. 답변글을 추가하는 주글(부모글)의
//      b_group열의 값보다 큰 값이 저장된 다른주글의 b_grop열의 값을 1증가시킨다
	
//규칙2. 추가(INSERT)하는 답변글은 주글(부모들)의 b_grop열값을 가져와 +1을 해서 insert한다
//규칙3. 추가(INSERT)하는 답변글은 주글(부모들)의 b_level열값을 가져와 +1을 해서 insert한다
public void replyInsertBoard(String super_notice_id, String reply_writer, String reply_title, String reply_content, String reply_id) {
	String sql = null;
	try {
	    con = ds.getConnection();

	    // 부모 글 정보 조회
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

	    // 답변글 삽입
	    sql = "insert into notice(title, content, created_date, author_id, b_group, b_level)"
	          + " values(?,?,NOW(),?,?,?)";
	    pstmt = con.prepareStatement(sql);
	    pstmt.setString(1, reply_title);
	    pstmt.setString(2, reply_content);
	    pstmt.setString(3, reply_id);
	    pstmt.setInt(4, b_group + 1);
	    pstmt.setInt(5, b_level + 1);
	    pstmt.executeUpdate();

	    

		} catch (Exception e) {
			System.out.println("BoardDAO의 replyInsertBoard메소드");
			e.printStackTrace();
		} finally {
		    closeResource();
		}
	
	}

}