package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import Vo.BookPostVo;

public class BookPostDAO {

	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	DataSource ds;

	public BookPostDAO() {
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

	// ===============================================================================
	// 중고책 거래=======================================================================
	// ===============================================================================

	public List<String> bookPostUpload(BookPostVo bookPostVo) {
		String sqlInsertPost = "INSERT INTO book_post (user_id, post_title, post_content, major_tag, created_at) VALUES (?, ?, ?, ?, NOW())";
		String sqlInsertImage = "INSERT INTO book_image (post_id, file_name, image_path) VALUES (?, ?, ?)";
		List<String> imagePaths = new ArrayList<>();

		try {
			con = ds.getConnection();
			con.setAutoCommit(false); // 트랜잭션 시작

			// 1. book_post 테이블에 게시글 저장
			pstmt = con.prepareStatement(sqlInsertPost, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, bookPostVo.getUserId());
			pstmt.setString(2, bookPostVo.getPostTitle());
			pstmt.setString(3, bookPostVo.getPostContent());
			pstmt.setString(4, bookPostVo.getMajorTag());
			pstmt.executeUpdate();

			// 2. 생성된 post_id 가져오기
			rs = pstmt.getGeneratedKeys();
			int postId = 0;
			if (rs.next()) {
				postId = rs.getInt(1);
				bookPostVo.setPostId(postId);
			} else {
				throw new SQLException("게시글 삽입 실패, 게시글 ID를 가져올 수 없습니다.");
			}

			// 자원 정리 후 새 쿼리 준비
			pstmt.close();
			rs.close();

			// 3. book_image 테이블에 이미지 정보 저장
			pstmt = con.prepareStatement(sqlInsertImage);
			for (BookPostVo.BookImage image : bookPostVo.getImages()) {
				String fileName = image.getFileName();
				String uploadTime = String.valueOf(System.currentTimeMillis()); // 현재 시간을 밀리초로 가져와서 문자열로 변환
				String imagePath = "/images/" + postId + "/" + fileName + "_" + uploadTime;

				pstmt.setInt(1, postId);
				pstmt.setString(2, fileName);
				pstmt.setString(3, imagePath);
				pstmt.executeUpdate();

				imagePaths.add(imagePath);
			}

			con.commit(); // 트랜잭션 커밋
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				if (con != null) {
					con.rollback(); // 오류 발생 시 트랜잭션 롤백
				}
			} catch (SQLException rollbackEx) {
				rollbackEx.printStackTrace();
			}
			imagePaths.clear(); // 실패 시 빈 리스트 반환
		} finally {
			closeResource();
			try {
				if (con != null) {
					con.setAutoCommit(true); // 기본 자동 커밋 모드로 복구
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return imagePaths;
	}

	// ===============================================================================
	// 중고책 거래=======================================================================
	// ===============================================================================

}
