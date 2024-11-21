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

import Dao.BoardDAO;
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

	public int bookPostUploadService(HttpServletRequest request) {
		// 1. 폼 데이터 추출
		String userId = request.getParameter("userId"); // 유저 아이디, hidden을 통해 받아왔습니다.
		String postTitle = request.getParameter("postTitle"); // 글 제목
		String postContent = request.getParameter("postContent"); // 글 내용
		String majorTag = request.getParameter("majorTag"); // 책과 관련된 학과명

		System.out.println(request.getParameter("userId"));
		System.out.println(request.getParameter("postTitle"));
		System.out.println(request.getParameter("postContent"));
		System.out.println(request.getParameter("majorTag"));
		// 2. 이미지 파일 수집
		ArrayList<BookPostVo.BookImage> bookImages = new ArrayList<>(); // BookImage 객체 리스트를 저장하는 변수
		try {
			Collection<Part> parts = request.getParts(); // 이미지 정보 저장
			for (Part part : parts) {
				if (part.getName().equals("image") && part.getSize() > 0) {
					// 파일 이름 가져오기
					String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
					// BookImage 객체 생성 후 리스트에 추가
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

		// 4. DAO 호출하여 데이터베이스에 저장하고 imagePath 리스트 반환
		List<String> imagePaths = bookPostDAO.bookPostUpload(bookPostVo);

		if (imagePaths.isEmpty()) {
			return 0; // 데이터베이스 저장 실패
		}

		// 5. 이미지 파일을 실제 파일 시스템에 저장
		int postId = bookPostVo.getPostId();
		String uploadDirPath = "C:/Users/602/EduManagerImage"; // 서버에 실제 저장할 디렉토리 경로
		String postImageDirPath = uploadDirPath + File.separator + postId;
		// 디렉토리가 없으면 만들기
		File postImageDir = new File(postImageDirPath);
		if (!postImageDir.exists()) {
			postImageDir.mkdirs();
		}

		// 이미지 파일 저장
		try {
			Collection<Part> parts = request.getParts();
			for (Part part : parts) {
				if (part.getName().equals("image") && part.getSize() > 0) {
					String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
					// 해당 이미지의 uniqueFileName 찾기
					String uniqueFileName = null;
					for (String path : imagePaths) {
						if (path.contains("/" + postId + "/" + fileName + "_")) {
							uniqueFileName = path.substring(path.lastIndexOf("/") + 1);
							break;
						}
					}
					if (uniqueFileName != null) {
						String filePath = postImageDirPath + File.separator + uniqueFileName;
						part.write(filePath);
					}
				}
			}
		} catch (IOException | ServletException e) {
			e.printStackTrace();
			return 0; // 파일 저장 실패 시 0 반환
		}

		return 1; // 성공 시 1 반환
	}
}
