package prosessVo;

import java.sql.Date;

public class ProsessVo {


	private String p_id;  //교수아이디(사번)
	private String p_pw;   //교수비밀번호(전화번호)
	private String p_Name;   //교수이름
	private Date p_brithDate;  //생년월일
	private String p_Gender;  //성별
	private String majorcode;  // 전공코드
	private String p_Address ;  //주소
	private String p_phone;    //전화번호
	private String p_email;  //이메일
	private int    p_profId;  // 사번
	private int    deptID ;    //학과번호
	private Date   p_employDate ;  //고용일
	
		
//기본생성자
public ProsessVo() {}


//(Date빼고)초기화할 생성자
public ProsessVo(String p_id, String p_pw, String p_Name, String p_Gender, String majorcode, String p_Address,
		String p_phone, String p_email, int p_profId, int deptID) {
	super();
	this.p_id = p_id;
	this.p_pw = p_pw;
	this.p_Name = p_Name;
	this.p_Gender = p_Gender;
	this.majorcode = majorcode;
	this.p_Address = p_Address;
	this.p_phone = p_phone;
	this.p_email = p_email;
	this.p_profId = p_profId;
	this.deptID = deptID;	
}


//전체 초기화할 생성자
public ProsessVo(String p_id, String p_pw, String p_Name, Date p_brithDate, String p_Gender, String majorcode,
		String p_Address, String p_phone, String p_email, int p_profId, int deptID, Date p_employDate) {
	super();
	this.p_id = p_id;
	this.p_pw = p_pw;
	this.p_Name = p_Name;
	this.p_brithDate = p_brithDate;
	this.p_Gender = p_Gender;
	this.majorcode = majorcode;
	this.p_Address = p_Address;
	this.p_phone = p_phone;
	this.p_email = p_email;
	this.p_profId = p_profId;
	this.deptID = deptID;
	this.p_employDate = p_employDate;
}



//getter, setter 메소드

public String getP_id() {
	return p_id;
}


public void setP_id(String p_id) {
	this.p_id = p_id;
}


public String getP_pw() {
	return p_pw;
}


public void setP_pw(String p_pw) {
	this.p_pw = p_pw;
}


public String getP_Name() {
	return p_Name;
}


public void setP_Name(String p_Name) {
	this.p_Name = p_Name;
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


public String getMajorcode() {
	return majorcode;
}


public void setMajorcode(String majorcode) {
	this.majorcode = majorcode;
}


public String getP_Address() {
	return p_Address;
}


public void setP_Address(String p_Address) {
	this.p_Address = p_Address;
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


public int getP_profId() {
	return p_profId;
}


public void setP_profId(int p_profId) {
	this.p_profId = p_profId;
}


public int getDeptID() {
	return deptID;
}


public void setDeptID(int deptID) {
	this.deptID = deptID;
}


public Date getP_employDate() {
	return p_employDate;
}


public void setP_employDate(Date p_employDate) {
	this.p_employDate = p_employDate;
}





}