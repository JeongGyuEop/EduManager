package src.VO;

public class MajorVO {
	private String MajorName;
	private int MajorCode;

	// Constructor
	public MajorVO(String majorName, int majorCode) {
		this.MajorName = majorName;
		this.MajorCode = majorCode;
	}

	// Getters and Setters (optional)
	public String getMajorName() {
		return MajorName;
	}

	public void setMajorName(String majorName) {
		MajorName = majorName;
	}

	public int getMajorCode() {
		return MajorCode;
	}

	public void setMajorCode(int majorCode) {
		MajorCode = majorCode;
	}
}
