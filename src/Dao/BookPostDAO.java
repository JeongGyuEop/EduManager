package Dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import Vo.BoardVo;
import Vo.BookPostVo;
import Vo.BookPostVo.BookImage;
import Vo.CommentVo;

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
            if (rs != null) rs.close();
        } catch (SQLException e) { e.printStackTrace(); }
        try {
            if (pstmt != null) pstmt.close();
        } catch (SQLException e) { e.printStackTrace(); }
        try {
            if (con != null) con.close();
        } catch (SQLException e) { e.printStackTrace(); }
    }

	// ===============================================================================
	// 중고책 거래=======================================================================
	// ===============================================================================

	// 학과 정보 받아오기
	public List<BookPostVo> majorInfo() {

		List<BookPostVo> majorInfo = new ArrayList<BookPostVo>();
		String sqlMajorInfo = "SELECT majorname FROM majorinformation";

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sqlMajorInfo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String major = rs.getString("majorname");
				BookPostVo majorTag = new BookPostVo(major);
				majorInfo.add(majorTag);
			}
		} catch (Exception e) {
			System.out.println("BookPostDAO의 majorInfo메소드에서 오류");
			e.printStackTrace();
		} finally {
			closeResource();
		}
		return majorInfo;
	}


	public void bookPostUpload(BookPostVo bookPostVo) {
        String sqlInsertPost = "INSERT INTO book_post (user_id, post_title, post_content, major_tag, created_at) VALUES (?, ?, ?, ?, NOW())";
        String sqlInsertImage = "INSERT INTO book_image (post_id, file_name, image_path) VALUES (?, ?, ?)";


        // 프로퍼티 파일 로드
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new FileNotFoundException("프로퍼티 파일을 찾을 수 없습니다.");
            }
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }

        // 업로드 디렉토리 설정
        String uploadDir = properties.getProperty("upload.dir");
        if (uploadDir == null || uploadDir.isEmpty()) {
            throw new IllegalArgumentException("업로드 디렉토리가 설정되지 않았습니다.");
        }

        try {
            // 데이터베이스 연결 및 트랜잭션 시작
            con = ds.getConnection();
            con.setAutoCommit(false);

            // 1. book_post 테이블에 게시글 저장
            pstmt = con.prepareStatement(sqlInsertPost, pstmt.RETURN_GENERATED_KEYS);
            pstmt.setString(1, bookPostVo.getUserId());
            pstmt.setString(2, bookPostVo.getPostTitle());
            pstmt.setString(3, bookPostVo.getPostContent());
            pstmt.setString(4, bookPostVo.getMajorTag());
            pstmt.executeUpdate();

            // 생성된 post_id 가져오기
            rs = pstmt.getGeneratedKeys();
            int postId = 0;
            if (rs.next()) {
                postId = rs.getInt(1);
                bookPostVo.setPostId(postId); // VO 객체에 postId 설정
            } else {
                throw new SQLException("게시글 삽입 실패, 게시글 ID를 가져올 수 없습니다.");
            }

            // 2. book_image 테이블에 이미지 정보 저장
            pstmt = con.prepareStatement(sqlInsertImage);
            for (BookPostVo.BookImage image : bookPostVo.getImages()) {
                String fileName = image.getFileName();
                String uploadTime = String.valueOf(System.currentTimeMillis());
                String uniqueFileName = uploadTime + "_" + fileName;
                String imagePath = uploadDir + "/" + postId + "/" + uniqueFileName;

                // VO의 BookImage 객체에 uniqueFileName과 imagePath 설정
                image.setFileName(uniqueFileName); // 파일명을 uniqueFileName으로 변경
                image.setImage_path(imagePath);

                pstmt.setInt(1, postId);
                pstmt.setString(2, uniqueFileName);
                pstmt.setString(3, imagePath);
                pstmt.executeUpdate();
            }

            // 트랜잭션 커밋
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (con != null) {
                    con.rollback(); // 오류 발생 시 트랜잭션 롤백
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        } finally {
            try {
                if (con != null && !con.getAutoCommit()) {
                    con.setAutoCommit(true); // 자동 커밋 모드 복구
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                // 리소스 해제
                closeResource();
            }
        }
    }

	// 모든 게시글 조회
	public List<BookPostVo> booklistboard() {

		String sqlbooklist = "SELECT bp.post_id, bp.user_id, bp.post_title, bp.major_tag, bp.created_at FROM book_post bp ORDER BY bp.post_id DESC";
		List<BookPostVo> bookBoardList = new ArrayList<BookPostVo>();

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sqlbooklist);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int postId = rs.getInt("post_id");
				String userId = rs.getString("user_id");
				String postTitle = rs.getString("post_title");
				String majorTag = rs.getString("major_tag");
				Timestamp createdAt = rs.getTimestamp("created_at");
				// 게시물 정보 생성
				BookPostVo BoardList = new BookPostVo(postId, userId, postTitle, majorTag, createdAt);
				bookBoardList.add(BoardList);
			}
		} catch (Exception e) {
			System.out.println("BookDAO의 booklistboard 메소드 오류");
			e.printStackTrace();
		} finally {
			closeResource(); // 리소스 정리
		}

		return bookBoardList;
	}

	// content 포함 조회
	public BookPostVo bookPost(int postId) {

		String sqlbookpost = "SELECT bp.post_id, bp.user_id, bp.post_title, bp.post_content, bp.major_tag, bp.created_at, GROUP_CONCAT(bi.file_name) AS file_names, GROUP_CONCAT(bi.image_path) AS image_paths FROM book_post bp LEFT JOIN book_image bi ON bp.post_id = bi.post_id WHERE bp.post_id = ? GROUP BY bp.post_id;";

		BookPostVo bookPost = new BookPostVo();

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sqlbookpost);
			pstmt.setInt(1, postId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				postId = Integer.parseInt(rs.getString("post_id"));
				String userId = rs.getString("user_id");
				String postTitle = rs.getString("post_title");
				String postContent = rs.getString("post_content");
				String majorTag = rs.getString("major_tag");
				Timestamp createdAt = rs.getTimestamp("created_at");
				String fileNameList = rs.getString("file_names");
				String image_pathList = rs.getString("image_paths");

				List<BookImage> images = new ArrayList<>();

				if (fileNameList != null && image_pathList != null) {
					String[] fileNames = fileNameList.split(",");
					String[] imagePaths = image_pathList.split(",");

					for (int i = 0; i < fileNames.length; i++) {
						String fileName = fileNames[i].trim();
						String imagePath = imagePaths[i].trim();
						BookImage image = new BookImage(fileName, imagePath);
						images.add(image);
					}
				}
				// BookPostVo 객체를 생성합니다.
				bookPost = new BookPostVo(postId, userId, postTitle, postContent, majorTag, createdAt, images);
			}
		} catch (Exception e) {
			System.out.println("BookDAO의 booklistboard 메소드 오류");
			e.printStackTrace();
		} finally {
			closeResource(); // 리소스 정리
		}

		return bookPost;
	}

	// 검색
	public List<BookPostVo> bookserchList(String key, String word) {

		String sqlbooklist = null;
		ArrayList<BookPostVo> bookBoardList = new ArrayList<>();

		if (!word.equals("")) {
			if (key.equals("titleContent")) {
				sqlbooklist = "SELECT * FROM book_post WHERE post_title LIKE ? OR post_content LIKE ? ORDER BY post_id DESC";
			} else {
				sqlbooklist = "SELECT * FROM book_post WHERE user_id LIKE ? ORDER BY post_id DESC";
			}
		} else {
			sqlbooklist = "SELECT * FROM book_post ORDER BY post_id DESC";
		}

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sqlbooklist);

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
				int postId = rs.getInt("post_id");
				String userId = rs.getString("user_id");
				String postTitle = rs.getString("post_title");
				String majorTag = rs.getString("major_tag");
				Timestamp createdAt = rs.getTimestamp("created_at");
				// 게시물 정보 생성
				BookPostVo BoardList = new BookPostVo(postId, userId, postTitle, majorTag, createdAt);
				bookBoardList.add(BoardList);
			}
		} catch (Exception e) {
			System.out.println("bookpostDAO의 bookserchList메소드에서 오류");
			e.printStackTrace();
		} finally {
			closeResource();
		}
		return bookBoardList;
	}
	
	

	 // 게시글 읽기 (Read)
	public List<BookPostVo> getPostById(int postId) throws SQLException {
	    String sql = "SELECT * FROM book_post WHERE post_id = ?";
		List<BookPostVo> bookBoardList = new ArrayList<BookPostVo>();

	    try {
	        // 데이터베이스 연결
	        con = ds.getConnection(); 
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, postId); // 쿼리에 ID 설정
	        rs = pstmt.executeQuery();

	        // 결과 처리
	        if (rs.next()) { // 단일 결과만 처리
	            int postId_ = rs.getInt("post_id");
	            String userId = rs.getString("user_id");
	            String postTitle = rs.getString("post_title");
	            String postContent = rs.getString("post_content");
	            String majorTag = rs.getString("major_tag");
	            Timestamp createdAt = rs.getTimestamp("created_at");

	            // BookPostVo 객체 생성
	            bookBoardList = (List<BookPostVo>) new BookPostVo(postId_, userId, postTitle, postContent, majorTag, createdAt);
	        }
	    } catch (Exception e) {
	        System.out.println("BookPostDAO의 getPostById 메서드에서 오류 발생");
	        e.printStackTrace();
	    } finally {
	        closeResource(); // 자원 정리 (Connection, PreparedStatement, ResultSet 닫기)
	    }

	    return bookBoardList; // 조회된 게시글 반환 (없으면 null 반환)
	}

	
	
	//댓글
	public BookPostVo getPostDetail(int postId) {
        BookPostVo post = null;
        String sql = "SELECT * FROM book_post WHERE post_id = ?";

        try (Connection con =  ds.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, postId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                post = new BookPostVo();
                post.setPostId(rs.getInt("post_id"));
                post.setUserId(rs.getString("user_id"));
                post.setPostTitle(rs.getString("post_title"));
                post.setPostContent(rs.getString("post_content"));
                post.setMajorTag(rs.getString("major_tag"));
                post.setCreatedAt(rs.getTimestamp("created_at"));
            }
        } catch (Exception e) {
        	 System.out.println("BookPostDAO의 getPostDetail 메서드에서 오류 발생");
            e.printStackTrace();
        }finally {
			closeResource();
		}
        return post;
    }

    public List<CommentVo> getComments(int postId) {
        List<CommentVo> comments = new ArrayList<>();
        String sql = "SELECT * FROM comments WHERE post_id = ? ORDER BY created_at ASC";

        try (Connection con = ds.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, postId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                CommentVo comment = new CommentVo();
                comment.setCommentId(rs.getInt("comment_id"));
                comment.setPostId(rs.getInt("post_id"));
                comment.setAuthor(rs.getString("author"));
                comment.setContent(rs.getString("content"));
                comment.setCreatedAt(rs.getTimestamp("created_at"));
                comments.add(comment);
            }
        } catch (Exception e) {
        	System.out.println("BookPostDAO의 getComments 메서드에서 오류 발생");
            e.printStackTrace();
        }finally {
			closeResource();
		}
        return comments;
    }

    public void addComment(CommentVo comment) {
        
    	
    	String sql = "INSERT INTO comments (post_id, author, content, created_at) VALUES (?, ?, ?, now())";

        
        try {
        	Connection con = ds.getConnection();
        	con.setAutoCommit(false); // 트랜잭션 비활성화
             pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, comment.getPostId());
            pstmt.setString(2, comment.getAuthor());
            pstmt.setString(3, comment.getContent());
            pstmt.executeUpdate();
            
            con.commit(); // 커밋 호출
            
            System.out.println("Post ID: " + comment.getPostId());
            System.out.println("Author: " + comment.getAuthor());
            System.out.println("Content: " + comment.getContent());
            
            
        } catch (Exception e) {
        	System.out.println("BookPostDAO의 addComment 메서드에서 오류 발생");
            e.printStackTrace();
        }finally {
			closeResource();
		}
    }

	
	
	
	
	
	
	
	

    
    
    /*
      // 게시글 수정 (Update)
    public boolean updatePost(BookPostVo post) throws SQLException {
        String sql = "UPDATE book_posts SET post_title = ?, post_content = ?, major_tag = ? WHERE post_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, post.getPostTitle());
            stmt.setString(2, post.getPostContent());
            stmt.setString(3, post.getMajorTag());
            stmt.setInt(4, post.getPostId());
            return stmt.executeUpdate() > 0; // 1 이상이면 수정 성공
        }
    }

    // 게시글 삭제 (Delete)
    public boolean deletePost(int postId) throws SQLException {
        String sql = "DELETE FROM book_posts WHERE post_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, postId);
            return stmt.executeUpdate() > 0; // 1 이상이면 삭제 성공
        }
    }

     */
	
	// ===============================================================================
	// 중고책 거래=======================================================================
	// ===============================================================================

}
