package Vo;

public class CourseVo {
    private String course_id;
    private String course_name;
    private String professor_id;
    private String majorcode;
    private String room_id;
    private ClassroomVo classroom; // ClassroomVo 객체 포함

    public CourseVo() { } // 기본 생성자

    // 모든 필드를 포함하는 생성자
    public CourseVo(String course_id, String course_name, String professor_id, String majorcode, String room_id, ClassroomVo classroom) {
        super();
        this.course_id = course_id;
        this.course_name = course_name;
        this.professor_id = professor_id;
        this.majorcode = majorcode;
        this.room_id = room_id;
        this.classroom = classroom;
    }

    // getter, setter
    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getProfessor_id() {
        return professor_id;
    }

    public void setProfessor_id(String professor_id) {
        this.professor_id = professor_id;
    }

    public String getMajorcode() {
        return majorcode;
    }

    public void setMajorcode(String majorcode) {
        this.majorcode = majorcode;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public ClassroomVo getClassroom() {
        return classroom;
    }

    public void setClassroom(ClassroomVo classroom) {
        this.classroom = classroom;
    }
}