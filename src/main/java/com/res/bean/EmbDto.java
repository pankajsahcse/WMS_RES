package com.res.bean;

public class EmbDto {

	private Long workid;
	
	private Long engineerId;
	
	private Long OfficeId;
	
	private String workName;

	private String remarks;
	
	private String ipAddress;
	
	
	
	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Long getWorkid() {
		return workid;
	}

	public void setWorkid(Long workid) {
		this.workid = workid;
	}

	public Long getEngineerId() {
		return engineerId;
	}

	public void setEngineerId(Long engineerId) {
		this.engineerId = engineerId;
	}

	public Long getOfficeId() {
		return OfficeId;
	}

	public void setOfficeId(Long officeId) {
		OfficeId = officeId;
	}

	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}
	
	
	
}
