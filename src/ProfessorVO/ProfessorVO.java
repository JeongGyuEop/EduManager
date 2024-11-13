package ProfessorVO;

import java.sql.Date;

public class ProfessorVO {

	//user테이블
	private String user_id;  //교수아이디(사번)
	private String user_pw;   //교수비밀번호(전화번호)
	private String user_name;   //교수이름
	private Date brithDate;  //생년월일
	private String gender;  //성별
	private String address ;  //주소
	private String phone;    //전화번호
	private String email;  //이메일
	private String role; // "학생","교수","관리자" 중 하나

	//professor_info 테이블
	private String professor_id;  //교수사번
	private String  majorcode;    //학과번호
	private Date   employDate ;  //고용일
	
	
	//기본생성자
	public ProfessorVO() {}

	// date 뺀 생성자
	public ProfessorVO(String user_id, String user_pw, String user_name, String gender, String address, String phone,
			String email, String role, String professor_id, String majorcode) {
		super();
		this.user_id = user_id;
		this.user_pw = user_pw;
		this.user_name = user_name;
		this.gender = gender;
		this.address = address;
		this.phone = phone;
		this.email = email;
		this.role = role;
		this.professor_id = professor_id;
		this.majorcode = majorcode;
	}

	
	//전체 생성자
	public ProfessorVO(String user_id, String user_pw, String user_name, Date brithDate, String gender, String address,
			String phone, String email, String role, String professor_id, String majorcode, Date employDate) {
		super();
		this.user_id = user_id;
		this.user_pw = user_pw;
		this.user_name = user_name;
		this.brithDate = brithDate;
		this.gender = gender;
		this.address = address;
		this.phone = phone;
		this.email = email;
		this.role = role;
		this.professor_id = professor_id;
		this.majorcode = majorcode;
		this.employDate = employDate;
	}

	
// getter, setter 메소드	
	
	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_pw() {
		return user_pw;
	}

	public void setUser_pw(String user_pw) {
		this.user_pw = user_pw;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public Date getBrithDate() {
		return brithDate;
	}

	public void setBrithDate(Date brithDate) {
		this.brithDate = brithDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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

	public Date getEmployDate() {
		return employDate;
	}

	public void setEmployDate(Date employDate) {
		this.employDate = employDate;
	}


	


}

	
	
	