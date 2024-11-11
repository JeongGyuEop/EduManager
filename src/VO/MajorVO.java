package VO;

public class MajorVO {
	private String majorName;
	private String majorTel;
	private int majorCode;

	public MajorVO() {
	}

	// Constructor
	public MajorVO(String majorName, String majorTel, int majorCode) {
		this.majorName = majorName;
		this.majorTel = majorTel;
		this.majorCode = majorCode;
	}

	// Getters and Setters
	public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	public int getMajorCode() {
		return majorCode;
	}

	public void setMajorCode(int majorCode) {
		this.majorCode = majorCode;
	}

	public String getMajorTel() {
		return majorTel;
	}

	public void setMajorTel(String majorTel) {
		this.majorTel = majorTel;
	}
}
