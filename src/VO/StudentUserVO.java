package VO;

import java.sql.Date;

public class StudentUserVO {
    // user 테이블 관련 필드 (부모 테이블)
    private String user_id;      // 사용자 ID
    private String user_pw;      // 비밀번호
    private String user_name;    // 이름
    private Date birthDate;      // 생년월일
    private String gender;       // 성별
    private String address;      // 주소
    private String phone;        // 전화번호
    private String email;        // 이메일
    private String role;         // 역할 (학생, 교수, 관리자)

    // student_info 테이블 관련 필드 (자식 테이블)
    private String student_id;   // 학번
    private String majorcode;    // 학과 코드 (외래 키)
    private int grade;           // 학년
    private Date admission_date; // 입학일
    private String status;       // 상태 (재학, 휴학, 졸업, 자퇴)

    // 기본 생성자
    public StudentUserVO() {}
    
    
//    public StudentUserVO(String user_id, String user_pw, String user_name, String gender, String address,
//			String phone, String email, String role, String student_id, String majorcode, int grade, String status) {
//		super();
//		this.user_id = user_id;
//		this.user_pw = user_pw;
//		this.user_name = user_name;
//		this.gender = gender;
//		this.address = address;
//		this.phone = phone;
//		this.email = email;
//		this.role = role;
//		this.student_id = student_id;
//		this.majorcode = majorcode;
//		this.grade = grade;
//		this.status = status;
//	}

    
	public StudentUserVO(String user_id, String user_pw, String user_name, Date birthDate, String gender,
			String address, String phone, String email, String role, String student_id, String majorcode, int grade,
			Date admission_date, String status) {
		super();
		this.user_id = user_id;
		this.user_pw = user_pw;
		this.user_name = user_name;
		this.birthDate = birthDate;
		this.gender = gender;
		this.address = address;
		this.phone = phone;
		this.email = email;
		this.role = role;
		this.student_id = student_id;
		this.majorcode = majorcode;
		this.grade = grade;
		this.admission_date = admission_date;
		this.status = status;
	}



	// Getters and Setters for user fields
    public String getUser_id() { return user_id; }
    public void setUser_id(String user_id) { this.user_id = user_id; }

    public String getUser_pw() { return user_pw; }
    public void setUser_pw(String user_pw) { this.user_pw = user_pw; }

    public String getUser_name() { return user_name; }
    public void setUser_name(String user_name) { this.user_name = user_name; }

    public Date getBirthDate() { return birthDate; }
    public void setBirthDate(Date birthDate) { this.birthDate = birthDate; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    // Getters and Setters for student_info fields
    public String getStudent_id() { return student_id; }
    public void setStudent_id(String student_id) { this.student_id = student_id; }

    public String getMajorcode() { return majorcode; }
    public void setMajorcode(String majorcode) { this.majorcode = majorcode; }

    public int getGrade() { return grade; }
    public void setGrade(int grade) { this.grade = grade; }

    public Date getAdmission_date() { return admission_date; }
    public void setAdmission_date(Date admission_date) { this.admission_date = admission_date; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

