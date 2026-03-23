package com.res.bean;

public class DscSaveRequest {
    private Long workId;
    private String signedData;

    public Long getWorkId() { return workId; }
    public void setWorkId(Long workId) { this.workId = workId; }

    public String getSignedData() { return signedData; }
    public void setSignedData(String signedData) { this.signedData = signedData; }
}

