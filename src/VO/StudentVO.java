package VO;

import java.sql.Date;

public class StudentVO {
	private String id;
	private String pw;
	private String deptId;
    private String studentName;
    private String birthDate;
    private String gender;
    private String address;
    private String phone;
    private String email;
    private String studentId;
    private int grade;
    private int age;
    private String admissionDate;



    // 생성자
    public StudentVO(String id, String pw , String deptId, String studentName, String birthDate, String gender,
                     String address, String phone, String email, String studentId,
                     int grade, int age, String admissionDate) {
        this.id = id;
        this.pw = pw;
    	this.deptId = deptId;
        this.studentName = studentName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.studentId = studentId;
        this.grade = grade;
        this.age = age;
        this.admissionDate = admissionDate;
    }

    // Getter와 Setter
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getPw() { return pw; }
    public void setPw(String pw) {this.pw = pw;}
    
    
    public String getDeptId() { return deptId; }
    public void setDeptId(String deptId) { this.deptId = deptId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getBirthDate() { return birthDate; }
    public void setBirthDate(String birthDate) { this.birthDate = birthDate; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public int getGrade() { return grade; }
    public void setGrade(int grade) { this.grade = grade; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    
    public String getAdmissionDate() { return admissionDate; }
    public void setAdmissionDate(String admissionDate) { this.admissionDate = admissionDate; }
}
