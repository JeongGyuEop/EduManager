package Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Collection;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import Dao.BookPostDAO;
import Dao.MemberDAO;
import Vo.BookPostVo;
import Vo.CommentVo;

public class BookPostService {

	BookPostDAO bookPostDAO;
	MemberDAO memberdao;

	public BookPostService() {
		bookPostDAO = new BookPostDAO();
		memberdao = new MemberDAO();
	}

//-------------- 중고 책 거래 ----------------------------------------------------------------------------------------------	

	// 글 등록
	public int bookPostUploadService(HttpServletRequest request) {
	    // 1. 폼 데이터 추출
	    String userId = request.getParameter("userId"); // 유저 아이디
	    String postTitle = request.getParameter("postTitle"); // 글 제목
	    String postContent = request.getParameter("postContent"); // 글 내용
	    String majorTag = request.getParameter("majorTag"); // 학과 태그

	    // 2. 이미지 파일 수집
	    ArrayList<BookPostVo.BookImage> bookImages = new ArrayList<>();
	    List<Part> fileParts = new ArrayList<>(); // 파일 파트를 저장할 리스트

	    try {
	        Collection<Part> parts = request.getParts();
	        for (Part part : parts) {
	            if (part.getName().equals("image") && part.getSize() > 0) {
	                fileParts.add(part); // 파일 파트 저장

	                String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
	                BookPostVo.BookImage bookImage = new BookPostVo.BookImage();
	                bookImage.setFileName(fileName);
	                bookImages.add(bookImage);
	            }
	        }
	    } catch (IOException | ServletException e) {
	        e.printStackTrace();
	        return 0; // 실패 시 0 반환
	    }

	    // 3. BookPostVo 객체 생성 및 데이터 설정
	    BookPostVo bookPostVo = new BookPostVo();
	    bookPostVo.setUserId(userId);
	    bookPostVo.setPostTitle(postTitle);
	    bookPostVo.setPostContent(postContent);
	    bookPostVo.setMajorTag(majorTag);
	    bookPostVo.setImages(bookImages); // 이미지 리스트 설정

	    // 4. DAO 호출하여 데이터베이스에 저장
	    bookPostDAO.bookPostUpload(bookPostVo);

	    // 5. 프로퍼티 파일 로드
	    Properties properties = new Properties();
	    try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
	        if (input == null) {
	            System.out.println("프로퍼티 파일을 찾을 수 없습니다.");
	            return 0;
	        }
	        properties.load(input);
	    } catch (IOException ex) {
	        ex.printStackTrace();
	        return 0;
	    }

	    // 6. 업로드 디렉토리 경로 설정
	    String uploadDir = properties.getProperty("upload.dir");
	    if (uploadDir == null || uploadDir.isEmpty()) {
	        System.out.println("업로드 디렉토리가 설정되지 않았습니다.");
	        return 0;
	    }

	    String applicationPath = request.getServletContext().getRealPath("");
	    String uploadDirPath = applicationPath + File.separator + uploadDir + File.separator + bookPostVo.getPostId();
	    System.out.println("이미지 업로드 경로: " + uploadDirPath);

	    // 7. 디렉토리 생성
	    File postImageDir = new File(uploadDirPath);
	    if (!postImageDir.exists()) {
	        boolean dirCreated = postImageDir.mkdirs();
	        if (!dirCreated) {
	            System.out.println("디렉토리 생성 실패");
	            return 0; // 디렉토리 생성 실패 시 0 반환
	        }
	    }

	    // 8. 이미지 파일 저장
	    try {
	        int index = 0;
	        for (Part part : fileParts) {
	            String uniqueFileName = bookPostVo.getImages().get(index).getFileName();
	            String filePath = postImageDir + File.separator + uniqueFileName;
	            part.write(filePath);
	            index++;
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	        return 0; // 파일 저장 실패 시 0 반환
	    }

	    return 1; // 성공 시 1 반환
	}

	//모든 글조회
	public List<BookPostVo> serviceBoardbooklist() {
		return bookPostDAO.booklistboard();
	}
	
	//글 내용 조회
	public BookPostVo serviceBookPost(HttpServletRequest request) {
		int postId = Integer.parseInt(request.getParameter("postId"));
		return bookPostDAO.bookPost(postId);
	}

//학과정보 받아오기
	public List<BookPostVo> majorInfo() {
		List<BookPostVo> majorInfo = bookPostDAO.majorInfo();

		return majorInfo;
	}
	
	// 키워드 검색을 통한 게시글 목록 조회 서비스
		public List<BookPostVo> serviceBookKeyWord(String key, String word) {
			return bookPostDAO.bookserchList(key, word);
		}


		//상세보기 수정을 위한 글번호 얻기
		public List<BookPostVo> getPostById(int postId) throws SQLException {
		  
			return bookPostDAO.getPostById(postId);
		}


		
		
		
		 public BookPostVo getPostDetail(int postId) {
		        return bookPostDAO.getPostDetail(postId);
		    }

		    public List<CommentVo> getComments(int postId) {
		        return bookPostDAO.getComments(postId);
		    }

		    public void addComment(CommentVo comment) {
		        bookPostDAO.addComment(comment);
		    }
		
		
		

		

}