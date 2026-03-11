package com.res.entity;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "emb_registeration")
@Entity
public class workEmb  extends Auditable{
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "emb_id")
	private Long embId;

	@Column(name = "work_Id")
	private Long workId;
	
	@Column(name = "emb_No")
	private String embNo;

	@Column(name = "engineer_Id")
	private Long engineerId;
	
	@Column(name = "remarks")
	private String Remarks;
	
	
	@Column(name="ip_address")
	private String ipAddress;
	
	@Column(name = "e_Mb_Issue_Date")
	private Date eMbIssueDate;
	
	
	
	public Date geteMbIssueDate() {
		return eMbIssueDate;
	}

	public void seteMbIssueDate(Date eMbIssueDate) {
		this.eMbIssueDate = eMbIssueDate;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getRemarks() {
		return Remarks;
	}

	public void setRemarks(String remarks) {
		Remarks = remarks;
	}

	public Long getEngineerId() {
		return engineerId;
	}

	public void setEngineerId(Long engineerId) {
		this.engineerId = engineerId;
	}

	public Long getEmbId() {
		return embId;
	}

	public void setEmbId(Long embId) {
		this.embId = embId;
	}

	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}

	public String getEmbNo() {
		return embNo;
	}

	public void setEmbNo(String embNo) {
		this.embNo = embNo;
	}

	
	
	
	
}
