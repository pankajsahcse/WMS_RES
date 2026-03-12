package com.res.response;

import java.util.List;

public class ResponseObject {

	private String errorMessage;
	private String successMessage;
	private long id;
	private String number;
	private List<String> errorMsgList;
	private String role;

	public List<String> getErrorMsgList() {
		return errorMsgList;
	}

	public void setErrorMsgList(List<String> errorMsgList) {
		this.errorMsgList = errorMsgList;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getSuccessMessage() {
		return successMessage;
	}

	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	private String unsignedBase64PDF;
	private String serverDateTime;
	
	
	public String getUnsignedBase64PDF() {
		return unsignedBase64PDF;
	}

	public void setUnsignedBase64PDF(String unsignedBase64PDF) {
		this.unsignedBase64PDF = unsignedBase64PDF;
	}

	public String getServerDateTime() {
		return serverDateTime;
	}

	public void setServerDateTime(String serverDateTime) {
		this.serverDateTime = serverDateTime;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
