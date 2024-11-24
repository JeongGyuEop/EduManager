package Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import Dao.BookPostDAO;
import Dao.MemberDAO;
import Vo.BookPostVo;

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
	    try {
	        Collection<Part> parts = request.getParts();
	        for (Part part : parts) {
	            if (part.getName().equals("image") && part.getSize() > 0) {
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

	    // 5. 이미지 파일을 실제 파일 시스템에 저장
	    int postId = bookPostVo.getPostId();
	    String uploadDirPath = "C:/Users/602/EduManagerImage"; // 서버에 실제 저장할 디렉토리 경로
	    String postImageDirPath = uploadDirPath + File.separator + postId;
	    // 디렉토리가 없으면 생성
	    File postImageDir = new File(postImageDirPath);
	    if (!postImageDir.exists()) {
	        postImageDir.mkdirs();
	    }

	    // 이미지 파일 저장
	    try {
	        Collection<Part> parts = request.getParts();
	        int index = 0;
	        for (Part part : parts) {
	            if (part.getName().equals("image") && part.getSize() > 0) {
	                // VO에서 uniqueFileName 가져오기
	                String uniqueFileName = bookPostVo.getImages().get(index).getUniqueFileName();
	                String filePath = postImageDirPath + File.separator + uniqueFileName;
	                part.write(filePath);
	                index++;
	            }
	        }
	    } catch (IOException | ServletException e) {
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
}

