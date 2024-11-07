package VO;

public class DeptMajorVO {
    private String DeptName, MajorName;
    private int DeptCode, MajorCode;

    // Constructor
    public DeptMajorVO(String deptName, String majorName, int deptCode, int majorCode) {
        this.DeptName = deptName;
        this.MajorName = majorName;
        this.DeptCode = deptCode;
        this.MajorCode = majorCode;
    }

    // Getters and Setters (optional)
    public String getDeptName() {
        return DeptName;
    }

    public void setDeptName(String deptName) {
        DeptName = deptName;
    }

    public String getMajorName() {
        return MajorName;
    }

    public void setMajorName(String majorName) {
        MajorName = majorName;
    }

    public int getDeptCode() {
        return DeptCode;
    }

    public void setDeptCode(int deptCode) {
        DeptCode = deptCode;
    }

    public int getMajorCode() {
        return MajorCode;
    }

    public void setMajorCode(int majorCode) {
        MajorCode = majorCode;
    }
}
