package com.res.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "measurement")
@Entity
public class Measurement extends Auditable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "e_Mb_No")
	private String eMbNo;
	
	@Column(name = "dsc_status")
	private Short dscStatus = 0;
	
	
	@Column(name ="dsc_string")
	private String dscString;
	@Column(name ="calculatedamount")
	private String calculatedAmount;
	
	@Column(name ="placedescription")
	private String placeDescription;
	
	
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
	public String getDscString() {
		return dscString;
	}
	public void setDscString(String dscString) {
		this.dscString = dscString;
	}
	public Short getDscStatus() {
		return dscStatus;
	}
	public void setDscStatus(Short dscStatus) {
		this.dscStatus = dscStatus;
	}
	@Column(name="ip_address")
	private String ipAddress;
	@Column(name = "work_Id")
	private  Long workId;
	@Column(name = "current_mesurement_l")
	private Double currentMesurementL;
	@Column(name = "current_mesurement_t")
	private Double currentMesurementT;
	
	@Column(name = "current_mesurement_w")
	private Double currentMesurementW;
	
	@Column(name = "estimatesorid")
	private Long estimateSorId;
	

	
;	public Long getEstimateSorId() {
		return estimateSorId;
	}
	public void setEstimateSorId(Long estimateSorId) {
		this.estimateSorId = estimateSorId;
	}
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
	@Column(name = "previous_Measurement_L")
	private Double previousMeasurementL;
	
	
	@Column(name = "remaining_measurement_l")
	private Double remainingMeasurementL;
	
	@Column(name = "previous_measurement_no")
	private Double previousMeasurementW;
	
	@Column(name = "current_mesurement_no")
	private Double currentMesurementNo;

	@Column(name = "remaining_measurement_no")
	private Double remainingMeasurementNo;
	
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
	@Column(name = "previous_measurement_w")
	private Double previousMeasurementNo;

	
	
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	@Column(name = "remaining_measurement_w")
	private Double remainingMeasurementW;
	
	
	@Column(name = "previous_measurement_t")
	private Double previousMeasurementT_T;
	
	
	@Column(name = "remaining_measurement_t")
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
	@Column(name = "sor_item_no")
	private String sorItemNo;

	@Column(name = "item_desc")
	private String itemDesc;

	@Column(name = "no")
	private BigDecimal no;

	@Column(name = "length")
	private BigDecimal length;

	@Column(name = "width")
	private BigDecimal width;

	@Column(name = "height_depth")
	private BigDecimal heightDepth;

	@Column(name = "quantity")
	private BigDecimal quantity;

	@Column(name = "unit")
	private String unit;

	@Column(name = "rate")
	private BigDecimal rate;

	@Column(name = "amount")
	private BigDecimal amount;

	@Column(name = "rate_labour")
	private BigDecimal rateLabour;

	@Column(name = "amount_labour")
	private BigDecimal amountLabour;

	@Column(name = "amount_material")
	private BigDecimal amountMaterial;

	@Column(name = "amoount_machinery")
	private BigDecimal amoountMachinery;

	@Column(name = "amount_loosening_soil")
	private BigDecimal amountLooseningSoil;

	@Column(name = "amount_excavation")
	private BigDecimal amountExcavation;

	@Column(name = "enabled")
	private Boolean enabled;

	@Column(name = "has_child")
	private Boolean hasChild;
	
	@Column(name = "is_group")
	private Boolean group;

	@JoinColumn(name = "work_estimation_id", referencedColumnName = "id")
	@ManyToOne
	private WorkEstimation workEstimation;
	
	
	
	
	
//	@JoinColumn(name = "template_group_id", referencedColumnName = "id")
//	@OneToOne
//	private TemplateGroup templateGroup;
	@Column(name = "template_group_id")
	Long templateGroupId;
	
	@Column(name = "template_sub_group_id")
	private Long templateSubGroupId;
	
	//new code to save data
	@Column(name = "work_template_id")
	Long workTemplateId;
	
	
	@Column(name = "document_Id")
	private Long documentId;
	//
	
	
	
	@Column(name = "remarks")
	private String remarks;
	public Long getDocumentId() {
		return documentId;
	}
	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}
	//new to save Index
	@Column(name = "item_index")
	private Integer itemIndex;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String geteMbNo() {
		return eMbNo;
	}
	public void seteMbNo(String eMbNo) {
		this.eMbNo = eMbNo;
	}
	public Long getWorkId() {
		return workId;
	}
	public void setWorkId(Long workId) {
		this.workId = workId;
	}

	public String getSorItemNo() {
		return sorItemNo;
	}
	public void setSorItemNo(String sorItemNo) {
		this.sorItemNo = sorItemNo;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public BigDecimal getNo() {
		return no;
	}
	public void setNo(BigDecimal no) {
		this.no = no;
	}
	public BigDecimal getLength() {
		return length;
	}
	public void setLength(BigDecimal length) {
		this.length = length;
	}
	public BigDecimal getWidth() {
		return width;
	}
	public void setWidth(BigDecimal width) {
		this.width = width;
	}
	public BigDecimal getHeightDepth() {
		return heightDepth;
	}
	public void setHeightDepth(BigDecimal heightDepth) {
		this.heightDepth = heightDepth;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getRateLabour() {
		return rateLabour;
	}
	public void setRateLabour(BigDecimal rateLabour) {
		this.rateLabour = rateLabour;
	}
	public BigDecimal getAmountLabour() {
		return amountLabour;
	}
	public void setAmountLabour(BigDecimal amountLabour) {
		this.amountLabour = amountLabour;
	}
	public BigDecimal getAmountMaterial() {
		return amountMaterial;
	}
	public void setAmountMaterial(BigDecimal amountMaterial) {
		this.amountMaterial = amountMaterial;
	}
	public BigDecimal getAmoountMachinery() {
		return amoountMachinery;
	}
	public void setAmoountMachinery(BigDecimal amoountMachinery) {
		this.amoountMachinery = amoountMachinery;
	}
	public BigDecimal getAmountLooseningSoil() {
		return amountLooseningSoil;
	}
	public void setAmountLooseningSoil(BigDecimal amountLooseningSoil) {
		this.amountLooseningSoil = amountLooseningSoil;
	}
	public BigDecimal getAmountExcavation() {
		return amountExcavation;
	}
	public void setAmountExcavation(BigDecimal amountExcavation) {
		this.amountExcavation = amountExcavation;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public Boolean getHasChild() {
		return hasChild;
	}
	public void setHasChild(Boolean hasChild) {
		this.hasChild = hasChild;
	}
	public Boolean getGroup() {
		return group;
	}
	public void setGroup(Boolean group) {
		this.group = group;
	}
	public WorkEstimation getWorkEstimation() {
		return workEstimation;
	}
	public void setWorkEstimation(WorkEstimation workEstimation) {
		this.workEstimation = workEstimation;
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
