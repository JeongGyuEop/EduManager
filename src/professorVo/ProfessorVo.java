package professorVo;

import java.sql.Date;

public class ProfessorVo {

	
	private String user_id;  //교수아이디(사번)
	private String user_pw;   //교수비밀번호(전화번호)
	private String professor_id;  //교수사번
	private String p_name;   //교수이름
	private Date p_brithDate;  //생년월일
	private String p_Gender;  //성별
	private String p_address ;  //주소
	private String p_phone;    //전화번호
	private String p_email;  //이메일
	private String  majorcode;    //학과번호
	private Date   p_employDate ;  //고용일
	private String role; // "학생","교수","관리자" 중 하나


	
	//기본생성자
	public ProfessorVo() {}
	
	
	//조회 생성자
	public ProfessorVo(String professor_id, String p_name, Date p_brithDate, String p_Gender, String p_address,
			String p_phone, String p_email, String majorcode, Date p_employDate) {
		super();
		this.professor_id = professor_id;
		this.p_name = p_name;
		this.p_brithDate = p_brithDate;
		this.p_Gender = p_Gender;
		this.p_address = p_address;
		this.p_phone = p_phone;
		this.p_email = p_email;
		this.majorcode = majorcode;
		this.p_employDate = p_employDate;
	}

	


// 전체 생성자
	public ProfessorVo(String user_id, String user_pw, String professor_id, String p_name, Date p_brithDate,
			String p_Gender, String p_address, String p_phone, String p_email, String majorcode, Date p_employDate,
			String role) {
		super();
		this.user_id = user_id;
		this.user_pw = user_pw;
		this.professor_id = professor_id;
		this.p_name = p_name;
		this.p_brithDate = p_brithDate;
		this.p_Gender = p_Gender;
		this.p_address = p_address;
		this.p_phone = p_phone;
		this.p_email = p_email;
		this.majorcode = majorcode;
		this.p_employDate = p_employDate;
		this.role = role;
	}




//getter, setter
	
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



	public String getProfessor_id() {
		return professor_id;
	}



	public void setProfessor_id(String professor_id) {
		this.professor_id = professor_id;
	}



	public String getP_name() {
		return p_name;
	}



	public void setP_name(String p_name) {
		this.p_name = p_name;
	}



	public Date getP_brithDate() {
		return p_brithDate;
	}



	public void setP_brithDate(Date p_brithDate) {
		this.p_brithDate = p_brithDate;
	}



	public String getP_Gender() {
		return p_Gender;
	}



	public void setP_Gender(String p_Gender) {
		this.p_Gender = p_Gender;
	}



	public String getP_address() {
		return p_address;
	}



	public void setP_address(String p_address) {
		this.p_address = p_address;
	}



	public String getP_phone() {
		return p_phone;
	}



	public void setP_phone(String p_phone) {
		this.p_phone = p_phone;
	}



	public String getP_email() {
		return p_email;
	}



	public void setP_email(String p_email) {
		this.p_email = p_email;
	}



	public String getMajorcode() {
		return majorcode;
	}



	public void setMajorcode(String majorcode) {
		this.majorcode = majorcode;
	}



	public Date getP_employDate() {
		return p_employDate;
	}



	public void setP_employDate(Date p_employDate) {
		this.p_employDate = p_employDate;
	}



	public String getRole() {
		return role;
	}



	public void setRole(String role) {
		this.role = role;
	}
	
	
	

	
}
