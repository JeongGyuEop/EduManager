package Vo;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AssignmentVo {
    private int assignmentId;      // 과제 ID (기본 키)
    private CourseVo course;       // 강의 객체 (외래키 참조)
    private String title;          // 과제 제목
    private String description;    // 과제 설명
    private LocalDate dueDate;     // 제출 기한
    private LocalDateTime createdDate; // 생성일

    // 기본 생성자
    public AssignmentVo() {}

    // 매개변수 생성자
    public AssignmentVo(int assignmentId, CourseVo course, String title, String description, LocalDate dueDate, LocalDateTime createdDate) {
        this.assignmentId = assignmentId;
        this.course = course;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.createdDate = createdDate;
    }

    // Getter and Setter 메소드
    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public CourseVo getCourse() {
        return course;
    }

    public void setCourse(CourseVo course) {
        this.course = course;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
