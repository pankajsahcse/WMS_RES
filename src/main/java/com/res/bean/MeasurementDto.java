package com.res.bean;

import java.util.List;

import javax.persistence.Column;

import org.springframework.web.multipart.MultipartFile;

import com.res.entity.DocumentUpload;

public class MeasurementDto {

	private Long workId;
	
	public Long getWorkId() {
		return workId;
	}

	private String calculatedAmount;
	
	//private String rate;
	
	private String placeDescription;
	
	

	
	private Double currentMesurementNo;

	
	private Double remainingMeasurementNo;
	
	
	private Double previousMeasurementNo;


	
	
	public Double getCurrentMesurementNo() {
		return currentMesurementNo;
	}

	public void setCurrentMesurementNo(Double currentMesurementNo) {
		this.currentMesurementNo = currentMesurementNo;
	}

	public Double getRemainingMeasurementNo() {
		return remainingMeasurementNo;
	}

	public void setRemainingMeasurementNo(Double remainingMeasurementNo) {
		this.remainingMeasurementNo = remainingMeasurementNo;
	}

	public Double getPreviousMeasurementNo() {
		return previousMeasurementNo;
	}

	public void setPreviousMeasurementNo(Double previousMeasurementNo) {
		this.previousMeasurementNo = previousMeasurementNo;
	}

	public String getCalculatedAmount() {
		return calculatedAmount;
	}

	public void setCalculatedAmount(String calculatedAmount) {
		this.calculatedAmount = calculatedAmount;
	}

	public String getPlaceDescription() {
		return placeDescription;
	}

	public void setPlaceDescription(String placeDescription) {
		this.placeDescription = placeDescription;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}
	private Boolean dscStatus;
	
	private String dscString;
	
	private String rateTotal;
	
	private String quantityTotal;
	
	private String amountTotal;
	
	
	
	
	
	public String getRateTotal() {
		return rateTotal;
	}

	public void setRateTotal(String rateTotal) {
		this.rateTotal = rateTotal;
	}

	public String getQuantityTotal() {
		return quantityTotal;
	}

	public void setQuantityTotal(String quantityTotal) {
		this.quantityTotal = quantityTotal;
	}

	public String getAmountTotal() {
		return amountTotal;
	}

	public void setAmountTotal(String amountTotal) {
		this.amountTotal = amountTotal;
	}

	public Boolean getDscStatus() {
		return dscStatus;
	}

	public void setDscStatus(Boolean dscStatus) {
		this.dscStatus = dscStatus;
	}

	public String getDscString() {
		return dscString;
	}

	public void setDscString(String dscString) {
		this.dscString = dscString;
	}
	private Long estimateSorId ;
	
	private Long docId;
	
	
	

	public Long getDocId() {
		return docId;
	}

	public void setDocId(Long docId) {
		this.docId = docId;
	}

	public Long getEstimateSorId() {
		return estimateSorId;
	}

	public void setEstimateSorId(Long estimateSorId) {
		this.estimateSorId = estimateSorId;
	}


	private Long measurementId;
	
	
	private Double previousMeasurementL;
	
	
private Double currentMesurementL;
	
	private Double currentMesurementT;
	
	
	private Double currentMesurementW;
	
	
	
	
	public Double getCurrentMesurementL() {
		return currentMesurementL;
	}

	public void setCurrentMesurementL(Double currentMesurementL) {
		this.currentMesurementL = currentMesurementL;
	}

	public Double getCurrentMesurementT() {
		return currentMesurementT;
	}

	public void setCurrentMesurementT(Double currentMesurementT) {
		this.currentMesurementT = currentMesurementT;
	}

	public Double getCurrentMesurementW() {
		return currentMesurementW;
	}

	public void setCurrentMesurementW(Double currentMesurementW) {
		this.currentMesurementW = currentMesurementW;
	}

	private Double remainingMeasurementL;
	

	private Double previousMeasurementW;
	
	
	
	private Double remainingMeasurementW;
	
	
	
	private Double previousMeasurementT_T;
	
	
	
	private Double remainingMeasurementT;
	
	
	
	
	public Double getPreviousMeasurementL() {
		return previousMeasurementL;
	}

	public void setPreviousMeasurementL(Double previousMeasurementL) {
		this.previousMeasurementL = previousMeasurementL;
	}

	public Double getRemainingMeasurementL() {
		return remainingMeasurementL;
	}

	public void setRemainingMeasurementL(Double remainingMeasurementL) {
		this.remainingMeasurementL = remainingMeasurementL;
	}

	public Double getPreviousMeasurementW() {
		return previousMeasurementW;
	}

	public void setPreviousMeasurementW(Double previousMeasurementW) {
		this.previousMeasurementW = previousMeasurementW;
	}

	public Double getRemainingMeasurementW() {
		return remainingMeasurementW;
	}

	public void setRemainingMeasurementW(Double remainingMeasurementW) {
		this.remainingMeasurementW = remainingMeasurementW;
	}

	public Double getPreviousMeasurementT_T() {
		return previousMeasurementT_T;
	}

	public void setPreviousMeasurementT_T(Double previousMeasurementT_T) {
		this.previousMeasurementT_T = previousMeasurementT_T;
	}

	public Double getRemainingMeasurementT() {
		return remainingMeasurementT;
	}

	public void setRemainingMeasurementT(Double remainingMeasurementT) {
		this.remainingMeasurementT = remainingMeasurementT;
	}

	public Long getMeasurementId() {
		return measurementId;
	}

	public void setMeasurementId(Long measurementId) {
		this.measurementId = measurementId;
	}

	private String sorItemNo;
	
	private Long workEstimateSorid;

	private String eMbNo;
	
	private MultipartFile documentUpload;
	
	private  String measuredBy;
	
	private String ipAddress;
	
	private String  valuationDate;
	
	
	
	
	public String getMeasuredBy() {
		return measuredBy;
	}

	public void setMeasuredBy(String measuredBy) {
		this.measuredBy = measuredBy;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getValuationDate() {
		return valuationDate;
	}

	public void setValuationDate(String valuationDate) {
		this.valuationDate = valuationDate;
	}

	public MultipartFile getDocumentUpload() {
		return documentUpload;
	}

	public void setDocumentUpload(MultipartFile documentUpload) {
		this.documentUpload = documentUpload;
	}

	private String sorType;

	private String itemDesc;

	private Boolean measureLength;

	private Boolean measureWidth;

	private Boolean measureHeightDepth;

	private String unit;
	//not found 
	private String  itemUnit;
	private Boolean labourComponent;

	private Float sequenceNo;

	private Boolean hasChild;

	private Boolean enabled;

	private Boolean group;

	private WorkTemplateBean parentItem;

	private List<WorkTemplateBean> childItems;

	private Long workTypeId;

	private Long standardTemplateTypeId;

	private String no;

	private String length;

	private String width;

	private String heightDepth;

	private String quantity;

	private String rate;
	private String labourComponentValue;
	private String amount;
	private String rateLabour;
	private String amountMaterial;
	private String amoountMachinery;
	private String amountLooseningSoil;
	private String amountExcavation;

	private boolean readOnly;

	private Integer serialNo;

	private Integer cumulativeChildsCount;
	
	private Integer childsCount;

	private boolean isNew;

	private boolean isRateReadOnly;

	private boolean isUnitReadOnly;

	private boolean isSorItemNoReadOnly;

	private boolean isDescReadOnly;
	
	private boolean isLeafNode;

	private Integer index;
	private Integer parentIndex;
	private Short templateType;
	private String sorOtherTypeName;
	//new 
	private Long templateGroupId;
	//new code to save data
	private Long templateSubGroupId;
	
	private Long workTemplateId;
	
	//remarks
	private String remarks;
	
	private Integer itemIndex;

	public String getSorItemNo() {
		return sorItemNo;
	}

	public void setSorItemNo(String sorItemNo) {
		this.sorItemNo = sorItemNo;
	}

	public Long getWorkEstimateSorid() {
		return workEstimateSorid;
	}

	public void setWorkEstimateSorid(Long workEstimateSorid) {
		this.workEstimateSorid = workEstimateSorid;
	}

	public String geteMbNo() {
		return eMbNo;
	}

	public void seteMbNo(String eMbNo) {
		this.eMbNo = eMbNo;
	}

	public String getSorType() {
		return sorType;
	}

	public void setSorType(String sorType) {
		this.sorType = sorType;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public Boolean getMeasureLength() {
		return measureLength;
	}

	public void setMeasureLength(Boolean measureLength) {
		this.measureLength = measureLength;
	}

	public Boolean getMeasureWidth() {
		return measureWidth;
	}

	public void setMeasureWidth(Boolean measureWidth) {
		this.measureWidth = measureWidth;
	}

	public Boolean getMeasureHeightDepth() {
		return measureHeightDepth;
	}

	public void setMeasureHeightDepth(Boolean measureHeightDepth) {
		this.measureHeightDepth = measureHeightDepth;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getItemUnit() {
		return itemUnit;
	}

	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}

	public Boolean getLabourComponent() {
		return labourComponent;
	}

	public void setLabourComponent(Boolean labourComponent) {
		this.labourComponent = labourComponent;
	}

	public Float getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(Float sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	public Boolean getHasChild() {
		return hasChild;
	}

	public void setHasChild(Boolean hasChild) {
		this.hasChild = hasChild;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getGroup() {
		return group;
	}

	public void setGroup(Boolean group) {
		this.group = group;
	}

	public WorkTemplateBean getParentItem() {
		return parentItem;
	}

	public void setParentItem(WorkTemplateBean parentItem) {
		this.parentItem = parentItem;
	}

	public List<WorkTemplateBean> getChildItems() {
		return childItems;
	}

	public void setChildItems(List<WorkTemplateBean> childItems) {
		this.childItems = childItems;
	}

	public Long getWorkTypeId() {
		return workTypeId;
	}

	public void setWorkTypeId(Long workTypeId) {
		this.workTypeId = workTypeId;
	}

	public Long getStandardTemplateTypeId() {
		return standardTemplateTypeId;
	}

	public void setStandardTemplateTypeId(Long standardTemplateTypeId) {
		this.standardTemplateTypeId = standardTemplateTypeId;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeightDepth() {
		return heightDepth;
	}

	public void setHeightDepth(String heightDepth) {
		this.heightDepth = heightDepth;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getLabourComponentValue() {
		return labourComponentValue;
	}

	public void setLabourComponentValue(String labourComponentValue) {
		this.labourComponentValue = labourComponentValue;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getRateLabour() {
		return rateLabour;
	}

	public void setRateLabour(String rateLabour) {
		this.rateLabour = rateLabour;
	}

	public String getAmountMaterial() {
		return amountMaterial;
	}

	public void setAmountMaterial(String amountMaterial) {
		this.amountMaterial = amountMaterial;
	}

	public String getAmoountMachinery() {
		return amoountMachinery;
	}

	public void setAmoountMachinery(String amoountMachinery) {
		this.amoountMachinery = amoountMachinery;
	}

	public String getAmountLooseningSoil() {
		return amountLooseningSoil;
	}

	public void setAmountLooseningSoil(String amountLooseningSoil) {
		this.amountLooseningSoil = amountLooseningSoil;
	}

	public String getAmountExcavation() {
		return amountExcavation;
	}

	public void setAmountExcavation(String amountExcavation) {
		this.amountExcavation = amountExcavation;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public Integer getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}

	public Integer getCumulativeChildsCount() {
		return cumulativeChildsCount;
	}

	public void setCumulativeChildsCount(Integer cumulativeChildsCount) {
		this.cumulativeChildsCount = cumulativeChildsCount;
	}

	public Integer getChildsCount() {
		return childsCount;
	}

	public void setChildsCount(Integer childsCount) {
		this.childsCount = childsCount;
	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	public boolean isRateReadOnly() {
		return isRateReadOnly;
	}

	public void setRateReadOnly(boolean isRateReadOnly) {
		this.isRateReadOnly = isRateReadOnly;
	}

	public boolean isUnitReadOnly() {
		return isUnitReadOnly;
	}

	public void setUnitReadOnly(boolean isUnitReadOnly) {
		this.isUnitReadOnly = isUnitReadOnly;
	}

	public boolean isSorItemNoReadOnly() {
		return isSorItemNoReadOnly;
	}

	public void setSorItemNoReadOnly(boolean isSorItemNoReadOnly) {
		this.isSorItemNoReadOnly = isSorItemNoReadOnly;
	}

	public boolean isDescReadOnly() {
		return isDescReadOnly;
	}

	public void setDescReadOnly(boolean isDescReadOnly) {
		this.isDescReadOnly = isDescReadOnly;
	}

	public boolean isLeafNode() {
		return isLeafNode;
	}

	public void setLeafNode(boolean isLeafNode) {
		this.isLeafNode = isLeafNode;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public Integer getParentIndex() {
		return parentIndex;
	}

	public void setParentIndex(Integer parentIndex) {
		this.parentIndex = parentIndex;
	}

	public Short getTemplateType() {
		return templateType;
	}

	public void setTemplateType(Short templateType) {
		this.templateType = templateType;
	}

	public String getSorOtherTypeName() {
		return sorOtherTypeName;
	}

	public void setSorOtherTypeName(String sorOtherTypeName) {
		this.sorOtherTypeName = sorOtherTypeName;
	}

	public Long getTemplateGroupId() {
		return templateGroupId;
	}

	public void setTemplateGroupId(Long templateGroupId) {
		this.templateGroupId = templateGroupId;
	}

	public Long getTemplateSubGroupId() {
		return templateSubGroupId;
	}

	public void setTemplateSubGroupId(Long templateSubGroupId) {
		this.templateSubGroupId = templateSubGroupId;
	}

	public Long getWorkTemplateId() {
		return workTemplateId;
	}

	public void setWorkTemplateId(Long workTemplateId) {
		this.workTemplateId = workTemplateId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getItemIndex() {
		return itemIndex;
	}

	public void setItemIndex(Integer itemIndex) {
		this.itemIndex = itemIndex;
	}
	
	
	
	
}
