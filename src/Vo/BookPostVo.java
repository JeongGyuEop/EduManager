package Vo;

import java.util.List;
import java.sql.Timestamp;

public class BookPostVo {
    
    // 필드 선언
    private String userId;          // 유저 아이디
    private String title;           // 글 제목
    private String content;         // 글 내용
    private String major;           // 학과 태그
    private List<String> fileNames; // 업로드된 이미지 파일 목록
    private Timestamp createdAt;    // 생성일시

    // 기본 생성자
    public BookPostVo() {
    }

    // 모든 필드를 초기화하는 생성자
    public BookPostVo(String userId, String title, String content, String major, List<String> fileNames, Timestamp createdAt) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.major = major;
        this.fileNames = fileNames;
        this.createdAt = createdAt;
    }

    // 시간 제외
    public BookPostVo(String userId, String title, String content, String major, List<String> fileNames) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.major = major;
        this.fileNames = fileNames;
    }
    
    // Getter 및 Setter 메서드
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public List<String> getFileNames() {
        return fileNames;
    }

    public void setFileNames(List<String> fileNames) {
        this.fileNames = fileNames;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "BookPostVO{" +
                "userId='" + userId + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", major='" + major + '\'' +
                ", fileNames=" + fileNames +
                ", createdAt=" + createdAt +
                '}';
    }
}
