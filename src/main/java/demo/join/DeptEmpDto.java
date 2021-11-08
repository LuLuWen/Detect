package demo.join;

public class DeptEmpDto {
	private String empDept;
	private String empName;
	private String empEmail;
	private String empAddress;

	public DeptEmpDto(String empDept, String empName, String empEmail, String empAddress) {
		this.empDept = empDept;
		this.empName = empName;
		this.empEmail = empEmail;
		this.empAddress = empAddress;
	}

	//getters and setters

	@Override
	public String toString() {
		return "DeptEmpDto [empDept=" + empDept + ", empName=" + empName + ", empEmail=" + empEmail + ", empAddress="
				+ empAddress + "]";
	}
}
