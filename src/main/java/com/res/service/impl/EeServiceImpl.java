package com.res.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.filter.ElementFilter;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.res.bean.AgencyTypeBean;
import com.res.bean.BlockBean;
import com.res.bean.DistrictBean;
import com.res.bean.DivisionBean;
import com.res.bean.EmbDto;
import com.res.bean.GramPanchayatBean;
import com.res.bean.KmlFilePoints;
import com.res.bean.LineDepartmentBean;
import com.res.bean.MeasurementDto;
import com.res.bean.OfficeBean;
import com.res.bean.OfficeTypeBean;
import com.res.bean.PhysicalStageTypeBean;
import com.res.bean.UserBean;
import com.res.bean.VillageBean;
import com.res.bean.WorkAgreementBean;
import com.res.bean.WorkBean;
import com.res.bean.WorkSubTypeBean;
import com.res.bean.WorkTemplateBean;
import com.res.bean.WorkTypeBean;
import com.res.bean.chapterDesciptionDto;
import com.res.constants.RESConstants;
import com.res.entity.AccountHead;
import com.res.entity.AdministrationSanction;
import com.res.entity.AdministrationSanctionType;
import com.res.entity.AgencyType;
import com.res.entity.Block;
import com.res.entity.Chapter;
import com.res.entity.Contractor;
import com.res.entity.Designation;
import com.res.entity.District;
import com.res.entity.Division;
import com.res.entity.DocumentUpload;
import com.res.entity.GramPanchayat;
import com.res.entity.IssuingAuthority;
import com.res.entity.Item;
import com.res.entity.LineDepartment;
import com.res.entity.Measurement;
import com.res.entity.Office;
import com.res.entity.OfficeType;
import com.res.entity.PhysicalStageType;
import com.res.entity.RequestStatus;
import com.res.entity.SchemeSanctionedUnderProgramme;
import com.res.entity.TechnicalSanction;
import com.res.entity.TechnicalSanctionType;
import com.res.entity.TechnicalStatus;
import com.res.entity.Users;
import com.res.entity.Village;
import com.res.entity.Work;
import com.res.entity.WorkEstimation;
import com.res.entity.WorkEstimationItems;
import com.res.entity.WorkLegacyIdGeneration;
import com.res.entity.WorkNature;
import com.res.entity.WorkRequisitionIdGeneration;
import com.res.entity.WorkStatus;
import com.res.entity.WorkSubType;
import com.res.entity.WorkType;
import com.res.entity.workEmb;
import com.res.exception.RESBusinessException;
import com.res.json.WorkAgreementJson;
import com.res.json.WorkJson;
import com.res.repository.AdministrationSanctionRepository;
import com.res.repository.BlockRepository;
import com.res.repository.DesignationRepository;
import com.res.repository.DistrictRepository;
import com.res.repository.DocumentRepository;
import com.res.repository.GramPanchayatRepository;
import com.res.repository.ItemRepository;
import com.res.repository.MeasurementRepository;
import com.res.repository.OfficeRepository;
import com.res.repository.OfficeTypeRepository;
import com.res.repository.RoleRepository;
import com.res.repository.TechnicalSanctionRepository;
import com.res.repository.UserRepository;
import com.res.repository.VillageRepository;
import com.res.repository.WorkEstimationItemsRepository;
import com.res.repository.WorkEstimationRepository;
import com.res.repository.WorkLegacyIdGenerationRepository;
import com.res.repository.WorkRepository;
import com.res.repository.WorkRequisitionIdGenerationRepository;
import com.res.repository.workEmbRepository;
import com.res.response.ResponseObject;
import com.res.service.EeService;
import com.res.service.UserService;
import com.res.util.RESUtil;


import antlr.StringUtils;

@Service
public class EeServiceImpl implements EeService {

	public static final Logger logger = LoggerFactory
			.getLogger(EeServiceImpl.class);

	@Autowired
	private DistrictRepository districtRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private workEmbRepository embRepository;

	@Autowired
	private WorkEstimationItemsRepository workEstimationItemsRepository;

	@Autowired
	private DesignationRepository designationRepository;

	@Autowired
	private WorkRepository workRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private OfficeTypeRepository officeTypeRepository;

	@Autowired
	private TechnicalSanctionRepository technicalSanctionRepository;

	@Autowired
	private AdministrationSanctionRepository administrationSanctionRepository;

	@Autowired
	private DocumentRepository documentRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private VillageRepository villageRepository;

	@Autowired
	private OfficeRepository officeRepository;

	@Autowired
	private GramPanchayatRepository gramPanchayatRepository;


	@Autowired
	private workEmbRepository workEmbRepository;

	@Autowired
	private BlockRepository blockRepository;

	@Autowired
	private WorkRequisitionIdGenerationRepository workRequisitionIdGenerationRepository;

	@Autowired
	private WorkLegacyIdGenerationRepository workLegacyIdGenerationRepository;

	@Autowired
	private MeasurementRepository measurementRepository;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private WorkEstimationRepository workEstimationRepository;

	@Value("${document.root}")
	private String documentRootPath;

	@Value("${document.work}")
	private String workDocumentPath;

	@Value("${document.technical}")
	private String workTechSanctionDocumentPath;

	@Value("${document.administration}")
	private String workAdminSanctionDocumentPath;

	@Value("${document.workRequisition}")
	private String workRequisitionPath;

	public String getDocumentRootPath() {
		return documentRootPath;
	}

	@Value("${document.WorkMasterKmlFilePath}")
	private String workMasterKmlFilePath;

	public void setDocumentRootPath(String documentRootPath) {
		this.documentRootPath = documentRootPath;
	}

	public String getWorkDocumentPath() {
		return workDocumentPath;
	}

	public void setWorkDocumentPath(String workDocumentPath) {
		this.workDocumentPath = workDocumentPath;
	}

	public String getWorkTechSanctionDocumentPath() {
		return workTechSanctionDocumentPath;
	}

	public void setWorkTechSanctionDocumentPath(
			String workTechSanctionDocumentPath) {
		this.workTechSanctionDocumentPath = workTechSanctionDocumentPath;
	}

	public String getWorkAdminSanctionDocumentPath() {
		return workAdminSanctionDocumentPath;
	}

	public void setWorkAdminSanctionDocumentPath(
			String workAdminSanctionDocumentPath) {
		this.workAdminSanctionDocumentPath = workAdminSanctionDocumentPath;
	}

	public String getWorkRequisitionPath() {
		return workRequisitionPath;
	}

	public void setWorkRequisitionPath(String workRequisitionPath) {
		this.workRequisitionPath = workRequisitionPath;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String addWork(WorkBean bean) {
		try {
			if (bean != null) {
				/*
				 * if (bean.getAdministrationSanctionFile() != null) {
				 * DocumentUpload documentUpload = uploadWorkDocument( "AsDocs",
				 * bean.getAdministrationSanctionFile(), bean.getDocumentDesc(),
				 * RESConstants.ADMINISTRATION_SANCTION_DOC);
				 * documentRepository.save(documentUpload); //
				 * legalDao.saveOrUpdate(legalDocumentUpload); //
				 * legalCaseHearing.setLegalDocumentUpload(new
				 * LegalDocumentUpload( //
				 * legalDocumentUpload.getDocumentId())); }
				 */
				Work entity = new Work();
				TechnicalSanction technicalSanctionEntity = new TechnicalSanction();
				AdministrationSanction administrationSanctionEntity = new AdministrationSanction();
				if (bean.getAdministrationSanctionBean()
						.getAdministrationSanctionFile() != null) {
					DocumentUpload documentUpload = RESUtil
							.uploadAsWorkDocument(documentRootPath
									+ workAdminSanctionDocumentPath, "blank",
									bean.getAdministrationSanctionBean()
											.getAdministrationSanctionFile(),
									null, "blank");
					documentRepository.save(documentUpload);
					administrationSanctionEntity
							.setDocumentUpload(documentUpload);
					// entity.setAdministrationSanctionFile(documentUpload);
					// legalDao.saveOrUpdate(legalDocumentUpload);
					// legalCaseHearing.setLegalDocumentUpload(new
					// LegalDocumentUpload(
					// legalDocumentUpload.getDocumentId()));
				}

				if (bean.getWorkStatusBean() != null) {
					if (bean.getAgencyTypeBean().getAgencyTypeId() == (long) 1
							&& bean.getWorkStatusBean().getWorkStatusid() != (long) 1
							&& bean.getWorkStatusBean().getWorkStatusid() != (long) 3
							&& bean.getWorkStatusBean().getWorkStatusid() != (long) 5)

					{
						if (bean.getAgreementCopyFile() != null) {
							DocumentUpload documentUpload = RESUtil
									.uploadAgreementDocument(documentRootPath
											+ workDocumentPath, "blank",
											bean.getAgreementCopyFile(), null,
											"blank");
							documentRepository.save(documentUpload);
							entity.setAgreementCopy(documentUpload);
							// legalDao.saveOrUpdate(legalDocumentUpload);
							// legalCaseHearing.setLegalDocumentUpload(new
							// LegalDocumentUpload(
							// legalDocumentUpload.getDocumentId()));
						}
					}
				}
				if (bean.getTechnicalSanctionBean() != null) {
					if (bean.getTechnicalSanctionBean()
							.getLatestDrawingCopyFile() != null) {
						DocumentUpload documentUpload = RESUtil
								.uploadDrawingDocument(documentRootPath
										+ workTechSanctionDocumentPath,
										"blank", bean
												.getTechnicalSanctionBean()
												.getLatestDrawingCopyFile(),
										null, "blank");
						documentRepository.save(documentUpload);
						technicalSanctionEntity
								.setDocumentUploadDrawing(documentUpload);
						// legalDao.saveOrUpdate(legalDocumentUpload);
						// legalCaseHearing.setLegalDocumentUpload(new
						// LegalDocumentUpload(
						// legalDocumentUpload.getDocumentId()));
					}

					if (bean.getTechnicalSanctionBean().getEstimateFile() != null) {
						DocumentUpload documentUpload = RESUtil
								.uploadEstimationDocument(documentRootPath
										+ workTechSanctionDocumentPath,
										"blank", bean
												.getTechnicalSanctionBean()
												.getEstimateFile(),
										null,
										"blank");
						documentRepository.save(documentUpload);
						technicalSanctionEntity
								.setDocumentUploadEstimate(documentUpload);
						// legalDao.saveOrUpdate(legalDocumentUpload);
						// legalCaseHearing.setLegalDocumentUpload(new
						// LegalDocumentUpload(
						// legalDocumentUpload.getDocumentId()));
					}

					if (bean.getTechnicalSanctionBean()
							.getTechnicalSanctionFile() != null) {
						DocumentUpload documentUpload = RESUtil
								.uploadTsWorkDocument(documentRootPath
										+ workTechSanctionDocumentPath,
										"blank", bean
												.getTechnicalSanctionBean()
												.getTechnicalSanctionFile(),
										null, "blank");
						documentRepository.save(documentUpload);
						technicalSanctionEntity
								.setDocumentUploadTechnical(documentUpload);
						// legalDao.saveOrUpdate(legalDocumentUpload);
						// legalCaseHearing.setLegalDocumentUpload(new
						// LegalDocumentUpload(
						// legalDocumentUpload.getDocumentId()));
					}
				}

				// Legacy Work ID Generation

				String lastWord = bean.getExecutiveEngineerOfficeName()
						.substring(
								bean.getExecutiveEngineerOfficeName()
										.lastIndexOf(",") + 1);
				if (bean.getAgencyTypeBean().getAgencyTypeId() == 1) {
					lastWord = "LD_" + lastWord + "_RC";
				} else if (bean.getAgencyTypeBean().getAgencyTypeId() == 2) {
					lastWord = "LD_" + lastWord + "_GP";
				} else if (bean.getAgencyTypeBean().getAgencyTypeId() == 3) {
					lastWord = "LD_" + lastWord + "_RD";
				}

				WorkLegacyIdGeneration workLegacyIdGeneration = workLegacyIdGenerationRepository
						.findByDivisionAgency(lastWord);
				if (workLegacyIdGeneration == null) {
					entity.setWorkRequisitionNo(lastWord + "_1");
					persistFirstLegacyIdGeneration(lastWord, 1);
				} else {
					int newCount = workLegacyIdGeneration.getCounter() + 1;
					workLegacyIdGeneration.setCounter(newCount);
					workLegacyIdGenerationRepository.save(workLegacyIdGeneration);
					entity.setWorkRequisitionNo(lastWord + "_" + newCount);

					/*
					 * WorkLegacyIdGeneration workLegacyIdGeneration = workLegacyIdGenerationList
					 * .get(workLegacyIdGenerationList.size() - 1);
					 * int count = workLegacyIdGeneration.getCounter();
					 * persistNextLegacyIdGeneration(lastWord, count);
					 */

				}

				convertWorkBeanToEntity(entity, bean);
				if (null != bean.getAdministrationSanctionBean() && null != bean.getAdministrationSanctionBean()
						.getAdministrationSanctionDate()) {
					entity.setFinancialYear(RESUtil.getFinacialYearFromDate(RESUtil
							.convertStringToDate(bean.getAdministrationSanctionBean()
									.getAdministrationSanctionDate())));
				} else {
					entity.setFinancialYear("AS Pending");
				}
				entity.setBillingFlag((short) 0);
				workRepository.save(entity);

				convertWorkBeanToTechnicalSanctionEntity(
						technicalSanctionEntity, entity, bean);
				technicalSanctionRepository.save(technicalSanctionEntity);

				convertWorkBeanToAdministrationSanctionEntity(
						administrationSanctionEntity, entity, bean);
				administrationSanctionRepository
						.save(administrationSanctionEntity);
			}
			return null;
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return RESConstants.ERROR_SAVING_DATA;
		}
	}

	private Work convertWorkBeanToEntity(Work entity, WorkBean bean)
			throws RESBusinessException {

		entity.setWorkName(bean.getWorkName());
		if (bean.getWorkTypeBean()
				.getWorkTypeId() != null) {
			entity.setWorkTypeId(new WorkType(bean.getWorkTypeBean()
					.getWorkTypeId()));
		}

		if (bean.getWorkNatureId() != null)
			entity.setWorkNatureId(new WorkNature(bean.getWorkNatureId()));

		if (null != bean.getSchemeTypeId())
			entity.setSchemeTypeId(new SchemeSanctionedUnderProgramme(bean.getSchemeTypeId()));

		if (null != bean.getWorkSubTypeBean()
				&& null != bean.getWorkSubTypeBean().getWorkSubTypeId()) {
			entity.setWorkSubTypeId(new WorkSubType(bean.getWorkSubTypeBean()
					.getWorkSubTypeId()));
		}
		entity.setDistance(bean.getDistance());
		entity.setCompleteddistance(bean.getCompleteddistance());

		entity.setLineDepartmentId(new LineDepartment(bean
				.getLineDepartmentBean().getLineDepartmentId()));

		entity.setAccountHead(new AccountHead(bean.getAccountHeadId()));

		entity.setAgencyTypeId(new AgencyType(bean.getAgencyTypeBean()
				.getAgencyTypeId()));
		if (bean.getAgencyTypeBean().getAgencyTypeId() == 1
				&& null != bean.getContractorBean()
				&& null != bean.getContractorBean().getId()) {
			entity.setContractor(new Contractor(bean.getContractorBean()
					.getId()));
		}
		/*
		 * if(bean.getAgencyTypeBean().getAgencyTypeId()==2){
		 * if(bean.getAgencyName()!=null)
		 * entity.setAgencyName(bean.getAgencyName()); }
		 */
		if (bean.getEstimatedCostString() != null)
			entity.setEstimatedCost(new BigDecimal(bean
					.getEstimatedCostString()));
		entity.setTotalCost(new BigDecimal(bean.getTotalCostString()));
		entity.setTotalExpenditureTill31March2018(new BigDecimal(bean
				.getTotalExpenditureTill31March2018String()));

		entity.setTotalExpenditureOnContingencyTill31March2018(bean
				.getTotalExpenditureOnContingencyTill31March2018());

		if (bean.getPhysicalStageTypeBean() != null)
			entity.setPhysicalStageType(new PhysicalStageType(bean
					.getPhysicalStageTypeBean().getPhysicalStageId()));
		entity.setTentativeCompletionDate(RESUtil.convertStringToDate(bean
				.getTentativeCompletionDateString()));
		entity.setTotalAmountRecievedTill31march2018(new BigDecimal(bean
				.getTotalAmountRecievedTill31March2018String()));

		/*
		 * entity.setTenderedRateSign(bean.getTenderedRateSign());
		 * entity.setTenderedRatePer(bean.getTenderedRatePer());
		 */
		entity.setDistrict(new District(bean.getDistrict().getDistrictId()));
		entity.setBlock(new Block(bean.getBlock().getBlockId()));
		if (bean.getGramPanchayatBean() != null)
			entity.setGramPanchayat(new GramPanchayat(bean
					.getGramPanchayatBean().getGramPanchayatId()));
		if (bean.getVillageBean() != null)
			entity.setVillage(new Village(bean.getVillageBean().getVillageId()));
		entity.setWorkLocationLatitude(bean.getWorkLocationLatitude());
		entity.setWorkLocationLongitude(bean.getWorkLocationLongitude());
		if (bean.getLocationGeometery() != null
				&& !bean.getLocationGeometery().isEmpty())
			entity.setLocationGeometery(bean.getLocationGeometery());
		entity.setExecutiveEngineerOffice(new Office(bean.getUserBean()
				.getOfficeBean().getId()));
		entity.setSuperintendingEngineerOffice(new Office(bean.getUserBean()
				.getOfficeBean().getParentOffice().getId()));
		entity.setChiefEngineerOffice(new Office(bean.getUserBean()
				.getOfficeBean().getChiefEngineerOfficeId()));
		entity.setLocationAddress(bean.getLocationAddress());
		if (bean.getClientIp() != null)
			entity.setClientIp(bean.getClientIp());
		if (bean.getAssistantEngineer() != null)
			entity.setAssistantEngineer(new Users(bean.getAssistantEngineer()
					.getId()));
		if (bean.getSubEngineer() != null)
			entity.setSubEngineer(new Users(bean.getSubEngineer().getId()));

		if (bean.getSubDivisionOfficerId() != null)
			entity.setSubDivisionalOfficer(new Users(bean.getSubDivisionOfficerId()));
		if (bean.getWorkStatusBean() != null)
			entity.setWorkStatusId(new WorkStatus(bean.getWorkStatusBean()
					.getWorkStatusid()));

		/* entity.setStatus(RESConstants.STATUS_ACTIVE); */
		/* entity.setStatus(bean.getStatus()); */

		if (bean.getWorkRequestStatusId() == 1) {
			entity.setWorkRequestStatusId(new RequestStatus(bean
					.getWorkRequestStatusId()));
		} else {
			if (bean.getAgencyTypeBean().getAgencyTypeId() == 1) {
				entity.setWorkRequestStatusId(new RequestStatus(
						RESConstants.REQUEST_STATUS_WORK_AGREEMENT_DONE_FWD_FOR_BILLING_INSPECTION_ID));
			} else if (bean.getAgencyTypeBean().getAgencyTypeId() == 2
					|| bean.getAgencyTypeBean().getAgencyTypeId() == 3) {
				entity.setWorkRequestStatusId(new RequestStatus(
						RESConstants.REQUEST_STATUS_AS_RECEIVED_AND_FWD_FOR_WORK_ORDERTENDER_DETAILS));
			}

		}

		entity.setIsLegacy((short) 1);
		entity.setRemarks(bean.getRemarks());

		if (bean.getProbableAmountOfWork() != null) {
			entity.setProbableAmountOfWork(bean.getProbableAmountOfWork());
		}

		if (bean.getWorkStatusBean() != null) {
			/*
			 * if (bean.getAgencyTypeBean().getAgencyTypeId() == 1
			 * && bean.getWorkStatusBean().getWorkStatusid() != 1
			 * && bean.getWorkStatusBean().getWorkStatusid() != 3
			 * && bean.getWorkStatusBean().getWorkStatusid() != 5) {
			 */
			if (bean.getAgencyTypeBean().getAgencyTypeId() == 1) {
				if (bean.getLineDepartmentBean() != null) {
					// if (bean.getLineDepartmentBean().getLineDepartmentId() != 28) {
					if (bean.getAgreementDateString() != null)
						entity.setAgreementDate(RESUtil
								.convertStringToDate(bean
										.getAgreementDateString()));
					entity.setAgreementNumber(bean.getAgreementNumber());
					// }
				}
				entity.setTenderedRateSign(bean.getTenderedRateSign());
				entity.setTenderedRatePer(bean.getTenderedRatePer() != null ? bean
						.getTenderedRatePer() : BigDecimal.ZERO);
				entity.setPacAmount(bean.getPacAmount());
				entity.setTenderCost(bean.getTenderCost());

			}
		}
		return entity;
	}

	private TechnicalSanction convertWorkBeanToTechnicalSanctionEntity(
			TechnicalSanction technicalSanctionEntity, Work entity,
			WorkBean bean) throws RESBusinessException {
		if (bean.getTechnicalSanctionBean() != null) {
			if (bean.getTechnicalSanctionBean().getTechnicalSanctionTypeBean() != null
					&& bean.getTechnicalSanctionBean()
							.getTechnicalSanctionTypeBean()
							.getTechnicalSanctionTypeId() != null)
				technicalSanctionEntity
						.setTechnicalSanctionType(new TechnicalSanctionType(
								bean.getTechnicalSanctionBean()
										.getTechnicalSanctionTypeBean()
										.getTechnicalSanctionTypeId()));
			if (bean.getTechnicalSanctionBean() != null
					&& bean.getTechnicalSanctionBean().getTechnicalSanctionNo() != null)
				technicalSanctionEntity.setTechnicalSanctionNo(bean
						.getTechnicalSanctionBean().getTechnicalSanctionNo());
			if (bean.getTechnicalSanctionBean() != null
					&& bean.getTechnicalSanctionBean()
							.getTechnicalSanctionDate() != null)
				technicalSanctionEntity.setTechnicalSanctionDate(RESUtil
						.convertStringToDate(bean.getTechnicalSanctionBean()
								.getTechnicalSanctionDate()));
			if (bean.getEstimatedCostString() != null)
				technicalSanctionEntity
						.setTechnicalSanctionAmount(new BigDecimal(bean
								.getEstimatedCostString()));
			if (bean.getTsAuthorityName() != null)
				technicalSanctionEntity.setTsAuthorityName(bean
						.getTsAuthorityName());
			if (bean.getTsIssuingAuthorityId() != null)
				technicalSanctionEntity.setTsIssuingAuthority(new Designation(
						bean.getTsIssuingAuthorityId()));
		}
		technicalSanctionEntity.setWork(entity);

		return technicalSanctionEntity;

	}

	private void convertWorkBeanToTechnicalSanctionEntityForRevised(
			TechnicalSanction technicalSanctionEntity, Work entity,
			WorkBean bean) throws RESBusinessException {

		technicalSanctionEntity.setTechnicalSanctionType(new TechnicalSanctionType((long) 2));

		technicalSanctionEntity.setTechnicalSanctionNo(bean.getTechnicalSanctionNo());
		if (bean.getTechnicalSanctionDate() != null)
			technicalSanctionEntity.setTechnicalSanctionDate(RESUtil
					.convertStringToDate(bean
							.getTechnicalSanctionDate()));

		technicalSanctionEntity
				.setTechnicalSanctionAmount(new BigDecimal(bean
						.getEstimatedCostString()));

		technicalSanctionEntity.setTsAuthorityName(bean
				.getTsAuthorityName());

		technicalSanctionEntity.setTsIssuingAuthority(new Designation(
				bean.getTsIssuingAuthorityId()));
		technicalSanctionEntity.setParentId(new TechnicalSanction(bean.getTsParentId()));

		technicalSanctionEntity.setWork(entity);

	}

	private void convertWorkBeanToAdministrationSanctionEntity(
			AdministrationSanction administrationSanctionEntity, Work entity,
			WorkBean bean) throws RESBusinessException {
		administrationSanctionEntity
				.setAdministrationSanctionType(new AdministrationSanctionType(
						bean.getAdministrationSanctionBean()
								.getAdministrationSanctionTypeBean()
								.getAdministrationSanctionTypeId()));
		administrationSanctionEntity.setAdministrativeSanctionNo(bean
				.getAdministrationSanctionBean().getAdministrationSanctionNo());
		administrationSanctionEntity.setAdministrativeSanctionDate(RESUtil
				.convertStringToDate(bean.getAdministrationSanctionBean()
						.getAdministrationSanctionDate()));
		/*
		 * if(entity.getAgencyTypeId().getAgencyTypeId()==1){
		 * administrationSanctionEntity.setIssuingAuthority(new
		 * IssuingAuthority(
		 * bean.getLineDepartmentBean().getLineDepartmentId())); }
		 */
		if (entity.getAgencyTypeId().getAgencyTypeId() == 2) {
			administrationSanctionEntity
					.setIssuingAuthority(new IssuingAuthority(bean
							.getAdministrationSanctionBean()
							.getIssuingAuthorityBean().getIssuingAuthorityId()));
		}
		administrationSanctionEntity.setWork(entity);
		administrationSanctionEntity
				.setAdministrativeSanctionAmount(new BigDecimal(bean
						.getTotalCostString()));
		administrationSanctionEntity.setStatus(RESConstants.STATUS_ACTIVE);
		administrationSanctionEntity.setAsAuthorityName(bean
				.getAsAuthorityName());
		administrationSanctionEntity.setContingencyAmount(bean
				.getContingencyAmount());
	}

	private void convertWorkBeanToAdministrationSanctionEntityForRevised(
			AdministrationSanction administrationSanctionEntity, Work entity,
			WorkBean bean) throws RESBusinessException {
		administrationSanctionEntity
				.setAdministrationSanctionType(new AdministrationSanctionType((long) 2));
		administrationSanctionEntity.setAdministrativeSanctionNo(bean.getAdministrationSanctionNo());
		administrationSanctionEntity.setAdministrativeSanctionDate(RESUtil
				.convertStringToDate(bean.getAdministrationSanctionDate()));
		/*
		 * if(entity.getAgencyTypeId().getAgencyTypeId()==1){
		 * administrationSanctionEntity.setIssuingAuthority(new
		 * IssuingAuthority(
		 * bean.getLineDepartmentBean().getLineDepartmentId())); }
		 */
		if (entity.getAgencyTypeId().getAgencyTypeId() == 2) {
			administrationSanctionEntity
					.setIssuingAuthority(new IssuingAuthority(bean
							.getAdministrationSanctionBean()
							.getIssuingAuthorityBean().getIssuingAuthorityId()));
		}
		administrationSanctionEntity.setWork(entity);
		administrationSanctionEntity
				.setAdministrativeSanctionAmount(new BigDecimal(bean
						.getTotalCostString()));
		administrationSanctionEntity.setStatus(RESConstants.STATUS_ACTIVE);
		administrationSanctionEntity.setAsAuthorityName(bean
				.getAsAuthorityName());
		administrationSanctionEntity.setContingencyAmount(bean
				.getContingencyAmount());
		administrationSanctionEntity.setParentId(new AdministrationSanction(bean.getAsParentId()));
	}

	/*
	 * @Override public WorkJson getAllWorks(Pageable pageable) { WorkJson
	 * workJson = null; String[] statusArr = null;
	 * if(StringUtils.isEmpty(status)){ statusArr = new
	 * String[]{RESConstants.STATUS_ACTIVE, RESConstants.STATUS_INACTIVE,
	 * RESConstants.STATUS_PENDING}; }else{ statusArr = new String[]{status}; }
	 * try{ Page<Work> works = null;
	 * 
	 * if(!StringUtils.isEmpty(searchBoxVal) &&
	 * (!StringUtils.isEmpty(designation) || !StringUtils.isEmpty(status)))
	 * 
	 * users = userRepository.
	 * findByNameContainingOrEmailIdContainingAndDesignationAndStatusInAndIsOICNotNull
	 * (pageable, searchBoxVal, searchBoxVal, designation, statusArr); else
	 * if(!StringUtils.isEmpty(searchBoxVal)) users = userRepository.
	 * findByNameContainingOrEmailIdContainingAndStatusNotAndIsOICNotNull
	 * (pageable, searchBoxVal, searchBoxVal, RESConstants.STATUS_DELETED); else
	 * if(!StringUtils.isEmpty(designation) || !StringUtils.isEmpty(status))
	 * users =
	 * userRepository.findByDesignationAndStatusInAndIsOICNotNull(pageable,
	 * designation, statusArr); else users =
	 * userRepository.findByStatusNotAndIsOICNotNull(pageable,
	 * RESConstants.STATUS_DELETED);
	 * 
	 * works = workRepository.findAll(pageable);
	 * 
	 * if(works!=null){ List<Work> entityList = works.getContent();
	 * List<WorkBean> beanList = new ArrayList<>(); if(entityList!= null &&
	 * !entityList.isEmpty()){
	 * 
	 * int index = pageable.getPageNumber()*pageable.getPageSize(); for(Work
	 * work : entityList){
	 * 
	 * WorkBean bean = convertWorkEntityToBean(work); bean.setIndex(++index);
	 * beanList.add(bean); } } workJson = new WorkJson();
	 * workJson.setiTotalDisplayRecords(works.getTotalElements()); //
	 * workJson.setiTotalRecords
	 * (workRepository.countByStatusNotAndIsOICNotNull(RESConstants
	 * .STATUS_DELETED)); workJson.setiTotalRecords(workRepository.count());
	 * workJson.setAaData(beanList); } return workJson; } catch (Exception e) {
	 * logger.error("An exception occurred.", e); return workJson; } }
	 */
	private WorkBean convertWorkEntityToBean(Work entity) {
		WorkBean bean = new WorkBean();
		if (entity != null) {
			bean.setWorkId(entity.getId());
			bean.setWorkName(entity.getWorkName());
			bean.setWorkTypeId(entity.getWorkTypeId().getWorkTypeId());

			if (entity.getWorkNatureId() != null)
				bean.setWorkNatureId(entity.getWorkNatureId().getWorkNatureId());

			if (null != bean.getSchemeTypeId())
				bean.setSchemeTypeId(entity.getSchemeTypeId().getId());

			if (entity.getWorkSubTypeId() != null)
				bean.setWorkSubTypeId(entity.getWorkSubTypeId()
						.getWorkSubTypeId());
			bean.setLineDepartmentId(entity.getLineDepartmentId()
					.getLineDepartmentId());
			// bean.setWorkTypeBean(convertWorkTypeEntityToBean(entity.getWorkTypeId()));
			// bean.setWorkSubTypeBean(convertWorkSubTypeEntityToBean(entity.getWorkSubTypeId()));
			// bean.setLineDepartmentBean(convertLineDepartmentEntityToBean(entity.getLineDepartmentId()));
			bean.setEstimatedCostString(String.valueOf(entity
					.getEstimatedCost()));
			bean.setTotalCostString(String.valueOf(entity.getTotalCost()));
			if (entity.getAccountHead() != null) {
				bean.setAccountHeadId(entity.getAccountHead().getId());
				bean.setAccountHeadName(entity.getAccountHead()
						.getAccountHeadNameE());
			}
			bean.setAgencyTypeId(entity.getAgencyTypeId().getAgencyTypeId());
			// bean.setAgencyTypeBean(convertAgencyTypeEntityToBean(entity.getAgencyTypeId()));
			if (entity.getAgencyName() != null) {
				bean.setAgencyName(entity.getAgencyName());
			} else {
				bean.setAgencyName(" - ");
			}
			bean.setTotalExpenditureTill31March2018String(String.valueOf(entity
					.getTotalExpenditureTill31March2018()));
			if (entity.getPhysicalStageType() != null) {
				bean.setPhysicalStageId(entity.getPhysicalStageType().getPhysicalStageId());
			} else {
				bean.setPhysicalStageId(null);
			}
			bean.setAgreementDateString(RESUtil.convertDateToString(entity
					.getAgreementDate()));
			bean.setTenderedRateSign(entity.getTenderedRateSign());
			bean.setTenderedRatePer(entity.getTenderedRatePer());

			if (entity.getAssistantEngineer() != null)
				bean.setAssistantEngineerId(entity.getAssistantEngineer()
						.getId());
			// bean.setSubEngineerId(entity.getSubEngineer().getId());
			if (entity.getExecutiveEngineerOffice() != null) {
				bean.setExecutiveEngineerOfficeId(entity
						.getExecutiveEngineerOffice().getId());
				bean.setExecutiveEngineerOfficeName(entity
						.getExecutiveEngineerOffice().getOfficeName());
			}

			if (entity.getSubDivisionalOfficer() != null) {
				bean.setSubDivisionOfficerId(entity
						.getSubDivisionalOfficer().getId());
				bean.setSubDivisionOfficerName(entity
						.getSubDivisionalOfficer().getOffice().getOfficeName());
			}
			// bean.setPhysicalStageTypeBean(convertPhysicalStageTypeEntityToBean(entity.getPhysicalStageType()));
			// bean.setExecuticeEngineerOffice(convertOfficeEntityToBean(entity.getExecutiveEngineerOffice()));
			// bean.setAssistantEngineer(convertUserEntityToBean(entity.getAssistantEngineer()));
			// bean.setSubEngineer(convertUserEntityToBean(entity.getSubEngineer()));
			if (entity.getDistrict() != null)
				bean.setDistrictId(entity.getDistrict().getDistrictId());
			bean.setBlockId(entity.getBlock().getBlockId());
			// bean.setDistrict(convertDistrictEntityToBean(entity.getDistrict()));
			// bean.setBlock(convertBlockEntityToBean(entity.getBlock()));
			bean.setLocationGeometery(entity.getLocationGeometery());
			bean.setWorkLocationLatitude(entity.getWorkLocationLatitude());
			bean.setWorkLocationLongitude(entity.getWorkLocationLongitude());
			bean.setClientIp(entity.getClientIp());
			bean.setApprovedBy(entity.getApprovedBy());
			bean.setStatus(entity.getStatus());

			bean.setTenderedRateSign(entity.getTenderedRateSign());
			bean.setTenderedRatePer(entity.getTenderedRatePer());
		}
		return bean;

	}

	private WorkBean convertWorkEntityToBean(Work entity,
			TechnicalSanction entityTechnical,
			AdministrationSanction entityAdministration) {
		WorkBean bean = new WorkBean();
		if (entity != null) {
			bean.setWorkId(entity.getId());
			bean.setWorkName(entity.getWorkName());
			bean.setWorkTypeId(entity.getWorkTypeId().getWorkTypeId());

			if (entity.getWorkNatureId() != null)
				bean.setWorkNatureId(entity.getWorkNatureId().getWorkNatureId());

			if (null != bean.getSchemeTypeId())
				bean.setSchemeTypeId(entity.getSchemeTypeId().getId());

			bean.setWorkSubTypeId(entity.getWorkSubTypeId().getWorkSubTypeId());
			bean.setLineDepartmentId(entity.getLineDepartmentId()
					.getLineDepartmentId());
			bean.setWorkStatusId(entity.getWorkStatusId().getId());
			// bean.setWorkTypeBean(convertWorkTypeEntityToBean(entity.getWorkTypeId()));
			// bean.setWorkSubTypeBean(convertWorkSubTypeEntityToBean(entity.getWorkSubTypeId()));
			// bean.setLineDepartmentBean(convertLineDepartmentEntityToBean(entity.getLineDepartmentId()));
			bean.setEstimatedCostString(String.valueOf(entity
					.getEstimatedCost()));
			bean.setTotalCostString(String.valueOf(entity.getTotalCost()));
			if (entity.getAccountHead() != null) {
				bean.setAccountHeadId(entity.getAccountHead().getId());
				bean.setAccountHeadName(entity.getAccountHead()
						.getAccountHeadNameE());
			}
			bean.setAgencyTypeId(entity.getAgencyTypeId().getAgencyTypeId());
			// bean.setAgencyTypeBean(convertAgencyTypeEntityToBean(entity.getAgencyTypeId()));
			bean.setAgencyName(entity.getAgencyName());
			if (entity.getContractor() != null)
				bean.setContractorId(entity.getContractor().getId());
			bean.setTotalExpenditureTill31March2018String(String.valueOf(entity
					.getTotalExpenditureTill31March2018()));
			if (entity.getPhysicalStageType() != null)
				bean.setPhysicalStageId(entity.getPhysicalStageType()
						.getPhysicalStageId());
			bean.setTentativeCompletionDateString(RESUtil
					.convertDateToString(entity.getTentativeCompletionDate()));
			bean.setTotalAmountRecievedTill31March2018String(String
					.valueOf(entity.getTotalAmountRecievedTill31march2018()));

			bean.setAgreementDateString(RESUtil.convertDateToString(entity
					.getAgreementDate()));
			bean.setTenderedRateSign(entity.getTenderedRateSign());
			bean.setTenderedRatePer(entity.getTenderedRatePer());
			if (entity.getAssistantEngineer() != null) {
				bean.setAssistantEngineerId(entity.getAssistantEngineer()
						.getId());
			} else {
				bean.setAssistantEngineerId(null);
			}
			if (entity.getSubEngineer() != null) {
				bean.setSubEngineerId(entity.getSubEngineer().getId());
			} else {
				bean.setSubEngineerId(null);
			}
			// bean.setSubEngineerId(entity.getSubEngineer().getId());
			if (entity.getExecutiveEngineerOffice() != null) {
				bean.setExecutiveEngineerOfficeId(entity
						.getExecutiveEngineerOffice().getId());
				bean.setExecutiveEngineerOfficeName(entity
						.getExecutiveEngineerOffice().getOfficeName());
			}

			if (entity.getSubDivisionalOfficer() != null) {
				bean.setSubDivisionOfficerId(entity
						.getSubDivisionalOfficer().getId());
				bean.setSubDivisionOfficerName(entity
						.getSubDivisionalOfficer().getOffice().getOfficeName());
			}
			// bean.setPhysicalStageTypeBean(convertPhysicalStageTypeEntityToBean(entity.getPhysicalStageType()));
			// bean.setExecuticeEngineerOffice(convertOfficeEntityToBean(entity.getExecutiveEngineerOffice()));
			// bean.setAssistantEngineer(convertUserEntityToBean(entity.getAssistantEngineer()));
			// bean.setSubEngineer(convertUserEntityToBean(entity.getSubEngineer()));
			bean.setDistrictId(entity.getDistrict().getDistrictId());
			bean.setDistrictName(entity.getDistrict().getDistrictName());
			bean.setBlockId(entity.getBlock().getBlockId());
			if (entity.getGramPanchayat() != null)
				bean.setGramPanchayatId(entity.getGramPanchayat()
						.getGramPanchayatId());
			if (entity.getVillage() != null)
				bean.setVillageId(entity.getVillage().getId());
			// bean.setDistrict(convertDistrictEntityToBean(entity.getDistrict()));
			// bean.setBlock(convertBlockEntityToBean(entity.getBlock()));
			bean.setLocationAddress(entity.getLocationAddress());
			bean.setLocationGeometery(entity.getLocationGeometery());
			bean.setWorkLocationLatitude(entity.getWorkLocationLatitude());
			bean.setWorkLocationLongitude(entity.getWorkLocationLongitude());
			bean.setClientIp(entity.getClientIp());
			bean.setApprovedBy(entity.getApprovedBy());
			bean.setStatus(entity.getStatus());
			if (entityTechnical != null) {
				bean.setTechnicalSanctionTypeId(entityTechnical
						.getTechnicalSanctionType()
						.getTechnicalSanctionTypeId());
				bean.setTechnicalSanctionNo(entityTechnical
						.getTechnicalSanctionNo());
				bean.setTechnicalSanctionDate(RESUtil
						.convertDateToString(entityTechnical
								.getTechnicalSanctionDate()));
			}
			if (entityAdministration != null) {
				bean.setAdministrationSanctionTypeId(entityAdministration
						.getAdministrationSanctionType()
						.getAdministrationSanctionTypeId());
				bean.setAdministrationSanctionNo(entityAdministration
						.getAdministrativeSanctionNo());
				bean.setAdministrationSanctionDate(RESUtil
						.convertDateToString(entityAdministration
								.getAdministrativeSanctionDate()));
				if (entityAdministration.getIssuingAuthority() != null)
					bean.setIssuingAuthorityId(entityAdministration
							.getIssuingAuthority().getIssuingAuthorityId());
			}
		}
		return bean;
	}

	private OfficeBean convertOfficeEntityToBean(Office entity) {
		OfficeBean bean = new OfficeBean();
		if (entity != null) {
			bean.setId(entity.getId());
			bean.setOfficeName(entity.getOfficeName());
			bean.setOfficeNameH(entity.getOfficeNameH());
			bean.setEnabled(entity.getEnabled());
			bean.setOfficeType(convertOfficeTypeEntityToBean(entity
					.getOfficeType()));
			bean.setOic(convertUserEntityToBean(entity.getOic()));
			bean.setParentOffice(convertOfficeEntityToBean(entity
					.getParentOffice()));
			bean.setDistrict(convertDistrictEntityToBean(districtRepository
					.findByDistrictCodeAndEnabled(entity.getDistrictCode(),
							(short) 1)));
			bean.setDivision(convertDivisionEntityToBean(entity.getDivision()));
		}
		return bean;
	}

	private BlockBean convertBlockEntityToBean(Block entity) {

		BlockBean bean = new BlockBean();
		if (entity != null) {
			bean.setBlockId(entity.getBlockId());
			bean.setBlockName(entity.getBlockName());
			bean.setBlockNameH(entity.getBlockNameH());
			bean.setEnabled(entity.getEnabled());
			bean.setDistrictCode(entity.getDistrictCode());
			bean.setBlockCode(entity.getBlockCode());

		}
		return bean;

	}

	private DivisionBean convertDivisionEntityToBean(Division entity) {

		DivisionBean bean = new DivisionBean();
		if (entity != null) {
			bean.setId(entity.getId());
			bean.setDivisionName(entity.getDivisionName());
			bean.setDivisionNameH(entity.getDivisionNameH());
			bean.setEnabled(entity.getEnabled());
		}
		return bean;
	}

	private DistrictBean convertDistrictEntityToBean(District entity) {

		DistrictBean bean = new DistrictBean();
		if (entity != null) {
			bean.setDistrictId(entity.getDistrictId());
			bean.setDistrictCode(entity.getDistrictCode());
			bean.setDistrictName(entity.getDistrictName());
			bean.setDistrictNameH(entity.getDistrictNameH());
			bean.setEnabled(entity.getEnabled());
		}
		return bean;
	}

	private UserBean convertUserEntityToBean(Users entity) {

		UserBean bean = new UserBean();
		if (entity != null) {
			bean.setId(entity.getId());
			bean.setName(entity.getName());
		}
		return bean;

	}

	private OfficeTypeBean convertOfficeTypeEntityToBean(OfficeType entity) {
		OfficeTypeBean bean = new OfficeTypeBean();
		if (entity != null) {
			bean.setId(entity.getId());
			bean.setOfficeType(entity.getOfficeType());
			bean.setOfficeTypeH(entity.getOfficeTypeH());
			bean.setEnabled(entity.getEnabled());
		}
		return bean;
	}

	private PhysicalStageTypeBean convertPhysicalStageTypeEntityToBean(
			PhysicalStageType entity) {
		PhysicalStageTypeBean bean = new PhysicalStageTypeBean();
		if (entity != null) {
			bean.setPhysicalStageId(entity.getPhysicalStageId());
			bean.setPhysicalStageNameE(entity.getPhysicalStageNameE());
			bean.setPhysicalStageNameH(entity.getPhysicalStageNameH());
			bean.setEnabled(entity.getEnabled());
			bean.setOrder(entity.getOrder());
		}
		return bean;
	}

	private AgencyTypeBean convertAgencyTypeEntityToBean(AgencyType entity) {
		AgencyTypeBean bean = new AgencyTypeBean();
		if (entity != null) {
			bean.setAgencyTypeId(entity.getAgencyTypeId());
			bean.setAgencyTypeNameE(entity.getAgencyTypeNameE());
			bean.setAgencyTypeNameH(entity.getAgencyTypeNameH());
			bean.setEnabled(entity.getEnabled());
		}
		return bean;
	}

	private LineDepartmentBean convertLineDepartmentEntityToBean(
			LineDepartment entity) {
		LineDepartmentBean bean = new LineDepartmentBean();
		if (entity != null) {
			bean.setLineDepartmentId(bean.getLineDepartmentId());
			bean.setLineDepartmentNameE(bean.getLineDepartmentNameE());
			bean.setLineDepartmentNameH(bean.getLineDepartmentNameH());
			bean.setEnabled(bean.getEnabled());
		}
		return bean;
	}

	private WorkSubTypeBean convertWorkSubTypeEntityToBean(WorkSubType entity) {
		WorkSubTypeBean bean = new WorkSubTypeBean();

		if (entity != null) {
			bean.setWorkSubTypeId(entity.getWorkSubTypeId());
			bean.setWorkSubTypeNameE(entity.getWorkSubTypeNameE());
			bean.setWorkSubTypeNameH(entity.getWorkSubTypeNameH());
			bean.setEnabled(entity.getEnabled());
			bean.setWorkType(new WorkTypeBean(entity.getWorkType()
					.getWorkTypeId()));
		}

		return bean;
	}

	private WorkTypeBean convertWorkTypeEntityToBean(WorkType entity) {
		WorkTypeBean bean = new WorkTypeBean();
		if (entity != null) {
			bean.setWorkTypeId(entity.getWorkTypeId());
			bean.setWorkTypeNameE(entity.getWorkTypeNameE());
			bean.setWorkTypeNameH(entity.getWorkTypeNameH());
			bean.setEnabled(entity.getEnabled());
		}
		return bean;
	}

	@Override
	public String deleteWork(Long id) {
		try {
			Work entity = workRepository.findOne(id);
			if (entity != null) {
				entity.setStatus(RESConstants.STATUS_DELETED);
				workRepository.save(entity);
			}
			TechnicalSanction entity2 = technicalSanctionRepository
					.findByWork(entity);

			/*
			 * if (entity2 != null) {
			 * entity2.setStatus(RESConstants.STATUS_DELETED);
			 * technicalSanctionRepository.save(entity2);
			 * }
			 */
			/*
			 * AdministrationSanction entity3 =
			 * administrationSanctionRepository.findByWork(entity);
			 */
			AdministrationSanction entity3 = administrationSanctionRepository.findAllASByWork(entity).get(0);
			if (entity3 != null) {
				entity3.setStatus(RESConstants.STATUS_DELETED);
				administrationSanctionRepository.save(entity3);
			}
			return null;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return RESConstants.ERROR_DELETING_DATA;
		}
	}

	/*
	 * @Override public WorkBean fetchWorkDetails(Long id) { try{ Work entity =
	 * workRepository.findOne(id); TechnicalSanction entityTechnical =
	 * technicalSanctionRepository.findByWork(entity); AdministrationSanction
	 * entityAdministration =
	 * administrationSanctionRepository.findByWork(entity); return
	 * convertWorkEntityToBean(entity,entityTechnical,entityAdministration); }
	 * catch (Exception e) { logger.error("An exception occurred.", e); return
	 * null; } }
	 */

	/**
	 * CR-RESOWMS/CR/1-1
	 * Work Transfer Module-Transfer Work to Other Office
	 * 
	 * @param workBean
	 * @return String
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String editWork(WorkBean workBean) {

		try {
			Work entity = workRepository.findOne(workBean.getWorkId());
			// TechnicalSanction technicalSanctionEntity =
			// technicalSanctionRepository.findByWork(entity);
			TechnicalSanction technicalSanctionEntity = null;
			List<TechnicalSanction> findAllTSByWork = technicalSanctionRepository.findAllTSByWork(entity);
			if (null != findAllTSByWork && findAllTSByWork.size() > 0) {
				technicalSanctionEntity = (TechnicalSanction) findAllTSByWork.get(0);
			}
			if (null == technicalSanctionEntity) {
				technicalSanctionEntity = new TechnicalSanction();
			}
			/*
			 * AdministrationSanction administrationSanctionEntity =
			 * administrationSanctionRepository.findByWork(entity);
			 */

			AdministrationSanction administrationSanctionEntity = administrationSanctionRepository
					.findAllASByWork(entity).get(0);

			if (null == administrationSanctionEntity) {
				administrationSanctionEntity = new AdministrationSanction();
			}

			if (workBean.getAdministrationSanctionFile() != null) {
				DocumentUpload documentUpload = RESUtil.uploadAsWorkDocument(
						documentRootPath + workAdminSanctionDocumentPath,
						"blank", workBean.getAdministrationSanctionFile(),
						null, "blank");
				documentRepository.save(documentUpload);
				administrationSanctionEntity.setDocumentUpload(documentUpload);
				// entity.setAdministrationSanctionFile(documentUpload);
				// legalDao.saveOrUpdate(legalDocumentUpload);
				// legalCaseHearing.setLegalDocumentUpload(new
				// LegalDocumentUpload(
				// legalDocumentUpload.getDocumentId()));
			}

			if (workBean.getAgencyTypeId() == 1
					&& workBean.getWorkStatusId() != 1
					&& workBean.getWorkStatusId() != 3
					&& workBean.getWorkStatusId() != 5) {
				if (workBean.getAgreementCopyFile() != null) {
					DocumentUpload documentUpload = RESUtil
							.uploadAgreementDocument(documentRootPath
									+ workDocumentPath, "blank",
									workBean.getAgreementCopyFile(), null,
									"blank");
					documentRepository.save(documentUpload);
					entity.setAgreementCopy(documentUpload);
					// legalDao.saveOrUpdate(legalDocumentUpload);
					// legalCaseHearing.setLegalDocumentUpload(new
					// LegalDocumentUpload(
					// legalDocumentUpload.getDocumentId()));
				}
			}

			if (workBean.getLatestDrawingCopyFile() != null) {
				DocumentUpload documentUpload = RESUtil.uploadDrawingDocument(
						documentRootPath + workTechSanctionDocumentPath,
						"blank", workBean.getLatestDrawingCopyFile(), null,
						"blank");
				documentRepository.save(documentUpload);
				technicalSanctionEntity
						.setDocumentUploadDrawing(documentUpload);
				// legalDao.saveOrUpdate(legalDocumentUpload);
				// legalCaseHearing.setLegalDocumentUpload(new
				// LegalDocumentUpload(
				// legalDocumentUpload.getDocumentId()));
			}

			if (workBean.getEstimateFile() != null) {
				DocumentUpload documentUpload = RESUtil
						.uploadEstimationDocument(documentRootPath
								+ workTechSanctionDocumentPath, "blank",
								workBean.getEstimateFile(), null, "blank");
				documentRepository.save(documentUpload);
				technicalSanctionEntity
						.setDocumentUploadEstimate(documentUpload);
				// legalDao.saveOrUpdate(legalDocumentUpload);
				// legalCaseHearing.setLegalDocumentUpload(new
				// LegalDocumentUpload(
				// legalDocumentUpload.getDocumentId()));
			}

			if (workBean.getTechnicalSanctionFile() != null) {
				DocumentUpload documentUpload = RESUtil.uploadTsWorkDocument(
						documentRootPath + workTechSanctionDocumentPath,
						"blank", workBean.getTechnicalSanctionFile(), null,
						"blank");
				documentRepository.save(documentUpload);
				technicalSanctionEntity
						.setDocumentUploadTechnical(documentUpload);
				// legalDao.saveOrUpdate(legalDocumentUpload);
				// legalCaseHearing.setLegalDocumentUpload(new
				// LegalDocumentUpload(
				// legalDocumentUpload.getDocumentId()));
			}

			// Legacy Work ID Generation

			String lastWord = workBean.getExecutiveEngineerOfficeName()
					.substring(
							workBean.getExecutiveEngineerOfficeName()
									.lastIndexOf(",") + 1);

			int index = entity.getWorkRequisitionNo().lastIndexOf('_');
			String oldRequisition = entity.getWorkRequisitionNo().substring(0, index);

			WorkLegacyIdGeneration workLegacyIdGeneration = null;

			if (workBean.getAgencyTypeId() == 1) {
				workLegacyIdGeneration = workLegacyIdGenerationRepository
						.findByDivisionAgency("LD_" + lastWord + "_RC");
			} else if (workBean.getAgencyTypeId() == 2) {
				workLegacyIdGeneration = workLegacyIdGenerationRepository
						.findByDivisionAgency("LD_" + lastWord + "_GP");
			} else if (workBean.getAgencyTypeId() == 3) {
				workLegacyIdGeneration = workLegacyIdGenerationRepository
						.findByDivisionAgency("LD_" + lastWord + "_RD");
			}

			if (workLegacyIdGeneration == null) {
				entity.setWorkRequisitionNo(lastWord + "_1");
				persistFirstLegacyIdGeneration(lastWord, 1);
			} else {
				if (!oldRequisition.equals(workLegacyIdGeneration.getDivisionAgency())) {

					int newCount = workLegacyIdGeneration.getCounter() + 1;
					workLegacyIdGeneration.setCounter(newCount);
					workLegacyIdGenerationRepository.save(workLegacyIdGeneration);

					if (workBean.getAgencyTypeId() == 1) {
						entity.setWorkRequisitionNo("LD_" + lastWord + "_RC" + "_" + newCount);
					} else if (workBean.getAgencyTypeId() == 2) {
						entity.setWorkRequisitionNo("LD_" + lastWord + "_GP" + "_" + newCount);
					} else if (workBean.getAgencyTypeId() == 3) {
						entity.setWorkRequisitionNo("LD_" + lastWord + "_RD" + "_" + newCount);
					}
					WorkLegacyIdGeneration workLegacyIdGenerationOld = workLegacyIdGenerationRepository
							.findByDivisionAgency(oldRequisition);

					int newCountTwo = workLegacyIdGeneration.getCounter() - 1;
					workLegacyIdGenerationOld.setCounter(newCountTwo);
					workLegacyIdGenerationRepository.save(workLegacyIdGenerationOld);
				}
			}

			convertWorkBeanToEntityDuringEdit(entity, workBean);
			workRepository.save(entity);

			convertWorkBeanToTechnicalSanctionEntityDuringEdit(
					technicalSanctionEntity, workBean);
			technicalSanctionRepository.save(technicalSanctionEntity);

			convertWorkBeanToAdministrationSanctionEntityDuringEdit(
					administrationSanctionEntity, workBean);
			administrationSanctionRepository.save(administrationSanctionEntity);

			return null;

		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return RESConstants.ERROR_SAVING_DATA;
		}

	}

	/*
	 * @Override
	 * 
	 * @Transactional(rollbackFor = Exception.class)
	 * public String editWorkRevise(WorkBean workBean) {
	 * 
	 * try {
	 * Work entity = workRepository.findOne(workBean.getWorkId());
	 * 
	 * TechnicalSanction technicalSanctionEntity=new TechnicalSanction();
	 * 
	 * AdministrationSanction administrationSanctionEntity =
	 * administrationSanctionRepository.findByWork(entity);
	 * 
	 * 
	 * AdministrationSanction administrationSanctionEntity=new
	 * AdministrationSanction();
	 * 
	 * 
	 * if (workBean.getAdministrationSanctionFile() != null) {
	 * DocumentUpload documentUpload = RESUtil.uploadAsWorkDocument(
	 * documentRootPath + workAdminSanctionDocumentPath,
	 * "blank", workBean.getAdministrationSanctionFile(),
	 * null, "blank");
	 * documentRepository.save(documentUpload);
	 * administrationSanctionEntity.setDocumentUpload(documentUpload);
	 * // entity.setAdministrationSanctionFile(documentUpload);
	 * // legalDao.saveOrUpdate(legalDocumentUpload);
	 * // legalCaseHearing.setLegalDocumentUpload(new
	 * // LegalDocumentUpload(
	 * // legalDocumentUpload.getDocumentId()));
	 * }
	 * 
	 * if (workBean.getAgencyTypeId() == 1
	 * && workBean.getWorkStatusId() != 1
	 * && workBean.getWorkStatusId() != 3
	 * && workBean.getWorkStatusId() != 5) {
	 * if (workBean.getAgreementCopyFile() != null) {
	 * DocumentUpload documentUpload = RESUtil
	 * .uploadAgreementDocument(documentRootPath
	 * + workDocumentPath, "blank",
	 * workBean.getAgreementCopyFile(), null,
	 * "blank");
	 * documentRepository.save(documentUpload);
	 * entity.setAgreementCopy(documentUpload);
	 * // legalDao.saveOrUpdate(legalDocumentUpload);
	 * // legalCaseHearing.setLegalDocumentUpload(new
	 * // LegalDocumentUpload(
	 * // legalDocumentUpload.getDocumentId()));
	 * }
	 * }
	 * 
	 * if (workBean.getLatestDrawingCopyFile() != null) {
	 * DocumentUpload documentUpload = RESUtil.uploadDrawingDocument(
	 * documentRootPath + workTechSanctionDocumentPath,
	 * "blank", workBean.getLatestDrawingCopyFile(), null,
	 * "blank");
	 * documentRepository.save(documentUpload);
	 * technicalSanctionEntity
	 * .setDocumentUploadDrawing(documentUpload);
	 * // legalDao.saveOrUpdate(legalDocumentUpload);
	 * // legalCaseHearing.setLegalDocumentUpload(new
	 * // LegalDocumentUpload(
	 * // legalDocumentUpload.getDocumentId()));
	 * }
	 * 
	 * if (workBean.getEstimateFile() != null) {
	 * DocumentUpload documentUpload = RESUtil
	 * .uploadEstimationDocument(documentRootPath
	 * + workTechSanctionDocumentPath, "blank",
	 * workBean.getEstimateFile(), null, "blank");
	 * documentRepository.save(documentUpload);
	 * technicalSanctionEntity
	 * .setDocumentUploadEstimate(documentUpload);
	 * // legalDao.saveOrUpdate(legalDocumentUpload);
	 * // legalCaseHearing.setLegalDocumentUpload(new
	 * // LegalDocumentUpload(
	 * // legalDocumentUpload.getDocumentId()));
	 * }
	 * 
	 * if (workBean.getTechnicalSanctionFile() != null) {
	 * DocumentUpload documentUpload = RESUtil.uploadTsWorkDocument(
	 * documentRootPath + workTechSanctionDocumentPath,
	 * "blank", workBean.getTechnicalSanctionFile(), null,
	 * "blank");
	 * documentRepository.save(documentUpload);
	 * technicalSanctionEntity
	 * .setDocumentUploadTechnical(documentUpload);
	 * // legalDao.saveOrUpdate(legalDocumentUpload);
	 * // legalCaseHearing.setLegalDocumentUpload(new
	 * // LegalDocumentUpload(
	 * // legalDocumentUpload.getDocumentId()));
	 * }
	 * 
	 * 
	 * // Legacy Work ID Generation
	 * 
	 * 
	 * String lastWord = workBean.getExecutiveEngineerOfficeName()
	 * .substring(
	 * workBean.getExecutiveEngineerOfficeName()
	 * .lastIndexOf(",") + 1);
	 * 
	 * int index=entity.getWorkRequisitionNo().lastIndexOf('_');
	 * String oldRequisition = entity.getWorkRequisitionNo().substring(0,index);
	 * 
	 * 
	 * 
	 * WorkLegacyIdGeneration workLegacyIdGeneration = null;
	 * 
	 * if (workBean.getAgencyTypeId() == 1) {
	 * workLegacyIdGeneration =
	 * workLegacyIdGenerationRepository.findByDivisionAgency("LD_"+lastWord+"_RC");
	 * } else if (workBean.getAgencyTypeId() == 2) {
	 * workLegacyIdGeneration =
	 * workLegacyIdGenerationRepository.findByDivisionAgency("LD_"+lastWord+"_GP");
	 * } else if (workBean.getAgencyTypeId() == 3) {
	 * workLegacyIdGeneration =
	 * workLegacyIdGenerationRepository.findByDivisionAgency("LD_"+lastWord+"_RD");
	 * }
	 * 
	 * 
	 * 
	 * 
	 * if (workLegacyIdGeneration==null) {
	 * entity.setWorkRequisitionNo(lastWord + "_1");
	 * persistFirstLegacyIdGeneration(lastWord, 1);
	 * } else {
	 * if(!oldRequisition.equals(workLegacyIdGeneration.getDivisionAgency()))
	 * {
	 * 
	 * 
	 * int newCount = workLegacyIdGeneration.getCounter()+1;
	 * workLegacyIdGeneration.setCounter(newCount);
	 * workLegacyIdGenerationRepository.save(workLegacyIdGeneration);
	 * 
	 * 
	 * 
	 * if (workBean.getAgencyTypeId() == 1) {
	 * entity.setWorkRequisitionNo("LD_"+lastWord+"_RC" + "_" + newCount);
	 * } else if (workBean.getAgencyTypeId() == 2) {
	 * entity.setWorkRequisitionNo("LD_"+lastWord+"_GP" + "_" + newCount);
	 * } else if (workBean.getAgencyTypeId() == 3) {
	 * entity.setWorkRequisitionNo("LD_"+lastWord+"_RD" + "_" + newCount);
	 * }
	 * 
	 * 
	 * 
	 * WorkLegacyIdGeneration workLegacyIdGenerationOld
	 * =workLegacyIdGenerationRepository.findByDivisionAgency(oldRequisition);
	 * 
	 * int newCountTwo = workLegacyIdGeneration.getCounter()-1;
	 * workLegacyIdGenerationOld.setCounter(newCountTwo);
	 * workLegacyIdGenerationRepository.save(workLegacyIdGenerationOld);
	 * 
	 * 
	 * 
	 * }
	 * 
	 * 
	 * 
	 * 
	 * }
	 * 
	 * convertWorkBeanToEntityDuringEdit(entity, workBean);
	 * entity.setFinancialYear("AS Pending");
	 * entity.setIsLegacyRevise((short) 1);
	 * workRepository.save(entity);
	 * 
	 * convertWorkBeanToTechnicalSanctionEntityForRevised(
	 * technicalSanctionEntity, entity, workBean);
	 * TechnicalSanction save =
	 * technicalSanctionRepository.save(technicalSanctionEntity);
	 * 
	 * 
	 * convertWorkBeanToAdministrationSanctionEntityForRevised(
	 * administrationSanctionEntity, entity, workBean);
	 * administrationSanctionEntity.setTechnicalSanction(save);
	 * administrationSanctionRepository
	 * .save(administrationSanctionEntity);
	 * 
	 * 
	 * 
	 * 
	 * 
	 * return null;
	 * 
	 * } catch (Exception e) {
	 * logger.error("An exception occurred.", e);
	 * return RESConstants.ERROR_SAVING_DATA;
	 * }
	 * 
	 * }
	 */

	private void convertWorkBeanToEntityDuringEdit(Work entity,
			WorkBean workBean) throws RESBusinessException {

		entity.setWorkName(workBean.getWorkName());
		entity.setWorkTypeId(new WorkType(workBean.getWorkTypeId()));
		entity.setWorkNatureId(new WorkNature(workBean.getWorkNatureId()));

		if (null != workBean.getSchemeTypeId())
			entity.setSchemeTypeId(new SchemeSanctionedUnderProgramme(workBean.getSchemeTypeId()));

		if (workBean.getWorkSubTypeId() != null)
			entity.setWorkSubTypeId(new WorkSubType(workBean.getWorkSubTypeId()));
		entity.setLineDepartmentId(new LineDepartment(workBean
				.getLineDepartmentId()));
		entity.setAccountHead(new AccountHead(workBean.getAccountHeadId()));
		entity.setAgencyTypeId(new AgencyType(workBean.getAgencyTypeId()));

		entity.setDistance(workBean.getDistance());
		entity.setCompleteddistance(workBean.getCompleteddistance());

		if (workBean.getAgencyTypeId() == 1
				&& null != workBean.getContractorId()) {
			entity.setContractor(new Contractor(workBean.getContractorId()));
		} else {
			entity.setContractor(null);
		}
		/*
		 * if(workBean.getAgencyTypeId()==2){ if(workBean.getAgencyName()!=null)
		 * entity.setAgencyName(workBean.getAgencyName()); }
		 */

		if (workBean.getEstimatedCostString() != null)
			entity.setEstimatedCost(new BigDecimal(workBean
					.getEstimatedCostString()));
		entity.setTotalCost(new BigDecimal(workBean.getTotalCostString()));

		entity.setTotalExpenditureTill31March2018(new BigDecimal(workBean
				.getTotalExpenditureTill31March2018String()));

		entity.setTotalExpenditureOnContingencyTill31March2018(workBean
				.getTotalExpenditureOnContingencyTill31March2018());

		entity.setWorkStatusId(new WorkStatus(workBean.getWorkStatusId()));

		if (workBean.getPhysicalStageId() != null)
			entity.setPhysicalStageType(new PhysicalStageType(workBean
					.getPhysicalStageId()));
		entity.setTentativeCompletionDate(RESUtil.convertStringToDate(workBean
				.getTentativeCompletionDateString()));
		entity.setTotalAmountRecievedTill31march2018(new BigDecimal(workBean
				.getTotalAmountRecievedTill31March2018String()));

		/*
		 * entity.setTenderedRateSign(workBean.getTenderedRateSign());
		 * entity.setTenderedRatePer(workBean.getTenderedRatePer());
		 */

		entity.setDistrict(new District(workBean.getDistrictId()));
		entity.setBlock(new Block(workBean.getBlockId()));
		if (workBean.getGramPanchayatId() != null)
			entity.setGramPanchayat(new GramPanchayat(workBean
					.getGramPanchayatId()));
		if (workBean.getVillageId() != null)
			entity.setVillage(new Village(workBean.getVillageId()));
		entity.setLocationAddress(workBean.getLocationAddress());
		entity.setWorkLocationLatitude(workBean.getWorkLocationLatitude());
		entity.setWorkLocationLongitude(workBean.getWorkLocationLongitude());
		if (workBean.getLocationGeometery() != null
				&& !workBean.getLocationGeometery().isEmpty())
			entity.setLocationGeometery(workBean.getLocationGeometery());

		if (workBean.getClientIp() != null)
			entity.setClientIp(workBean.getClientIp());

		/*
		 * entity.setExecutiveEngineerOffice(new
		 * Office(workBean.getExecutiveEngineerOfficeId()));
		 */
		if (workBean.getAssistantEngineerId() != null)
			entity.setAssistantEngineer(new Users(workBean
					.getAssistantEngineerId()));
		if (workBean.getSubEngineerId() != null)
			entity.setSubEngineer(new Users(workBean.getSubEngineerId()));

		if (workBean.getSubDivisionOfficerId() != null)
			entity.setSubDivisionalOfficer(new Users(workBean.getSubDivisionOfficerId()));
		/* entity.setStatus(RESConstants.STATUS_ACTIVE); */
		/* entity.setStatus(workBean.getStatus()); */
		/*
		 * entity.setWorkRequestStatusId(new RequestStatus(workBean
		 * .getWorkRequestStatusId()));
		 */

		if (workBean.getSpecificFieldsEditFlag() == null) {
			if (workBean.getWorkRequestStatusId() == 1) {
				entity.setWorkRequestStatusId(new RequestStatus(workBean
						.getWorkRequestStatusId()));
			} else {
				if (workBean.getAgencyTypeId() == 1) {
					entity.setWorkRequestStatusId(new RequestStatus(
							RESConstants.REQUEST_STATUS_WORK_AGREEMENT_DONE_FWD_FOR_BILLING_INSPECTION_ID));
				} else if (workBean.getAgencyTypeId() == 2
						|| workBean.getAgencyTypeId() == 3) {
					entity.setWorkRequestStatusId(new RequestStatus(
							RESConstants.REQUEST_STATUS_AS_RECEIVED_AND_FWD_FOR_WORK_ORDERTENDER_DETAILS));
				}
			}
		} else {
			if (workBean.getIsEstimationRevised() != null) {
				entity.setIsEstimationRevised(workBean.getIsEstimationRevised());
				entity.setCompetentAuthName(workBean.getCompetentAuthName());
				entity.setCompetentAuthDesig(workBean.getCompetentAuthDesig());
			} else {
				entity.setIsEstimationRevised(null);
				entity.setCompetentAuthName(null);
				entity.setCompetentAuthDesig(null);
			}
		}

		entity.setLetterNo(workBean.getLetterNo());
		entity.setRevisedLetterNo(workBean.getRevisedLetterNo());
		if (workBean.getLetterNoDate() != null)
			entity.setLetterNoDate(RESUtil.convertStringToDate(workBean.getLetterNoDate()));
		entity.setRevisedAsAmt(workBean.getRevisedAsAmt());
		entity.setRevisedTsAmt(workBean.getRevisedTsAmt());

		/*
		 * if (workBean.getAgencyTypeId() == 1 && workBean.getWorkStatusId() != 1
		 * && workBean.getWorkStatusId() != 3
		 * && workBean.getWorkStatusId() != 5) {
		 */
		if (workBean.getAgencyTypeId() == 1) {
			if (workBean.getLineDepartmentId() != 28) {
				if (workBean.getAgreementDateString() != null)
					entity.setAgreementDate(RESUtil
							.convertStringToDate(workBean
									.getAgreementDateString()));
				entity.setAgreementNumber(workBean.getAgreementNumber());
			} else {
				if (workBean.getAgreementDateString() != null) {
					entity.setAgreementDate(RESUtil
							.convertStringToDate(workBean
									.getAgreementDateString()));
				} else {
					entity.setAgreementDate(null);
				}
				if (workBean.getAgreementNumber() != null) {
					entity.setAgreementNumber(workBean.getAgreementNumber());
				} else {
					entity.setAgreementNumber(null);
				}
			}

			entity.setTenderedRateSign(workBean.getTenderedRateSign());
			entity.setTenderedRatePer(workBean.getTenderedRatePer() != null ? workBean
					.getTenderedRatePer() : BigDecimal.ZERO);
			entity.setPacAmount(workBean.getPacAmount());
			entity.setTenderCost(workBean.getTenderCost());
		}
		/*
		 * else if (workBean.getLineDepartmentId() != 28) {
		 * if (workBean.getAgreementDateString() != null) {
		 * entity.setAgreementDate(RESUtil.convertStringToDate(workBean
		 * .getAgreementDateString()));
		 * } else {
		 * entity.setAgreementDate(null);
		 * }
		 * if (workBean.getAgreementNumber() != null) {
		 * entity.setAgreementNumber(workBean.getAgreementNumber());
		 * } else {
		 * entity.setAgreementNumber(null);
		 * }
		 * }
		 */
		else {
			if (workBean.getAgreementDateString() != null) {
				entity.setAgreementDate(RESUtil.convertStringToDate(workBean
						.getAgreementDateString()));
			} else {
				entity.setAgreementDate(null);
			}
			if (workBean.getAgreementNumber() != null) {
				entity.setAgreementNumber(workBean.getAgreementNumber());
			} else {
				entity.setAgreementNumber(null);
			}
			entity.setTenderedRateSign(workBean.getTenderedRateSign());
			entity.setTenderedRatePer(workBean.getTenderedRatePer() != null ? workBean
					.getTenderedRatePer() : BigDecimal.ZERO);
			entity.setPacAmount(workBean.getPacAmount());
			entity.setTenderCost(workBean.getTenderCost());
			// entity.setAgreementDate(null);
			// entity.setAgreementNumber(null);
			// entity.setTenderedRateSign(null);
			// entity.setTenderedRatePer(null);
			// entity.setPacAmount(null);
			// entity.setTenderCost(null);

		}

		if (workBean.getProbableAmountOfWork() != null)
			entity.setProbableAmountOfWork(workBean.getProbableAmountOfWork());

		entity.setRemarks(workBean.getRemarks());
	}

	private void convertWorkBeanToTechnicalSanctionEntityDuringEdit(
			TechnicalSanction technicalSanctionEntity, WorkBean workBean)
			throws RESBusinessException {
		if (workBean.getIsEstimationRevised() != null && workBean.getIsEstimationRevised() == 1) {
			if (workBean.getTechnicalSanctionTypeId() != null) {
				technicalSanctionEntity
						.setTechnicalSanctionType(new TechnicalSanctionType(RESConstants.TS_TYPE_STATUS_REVISED));
				technicalSanctionEntity.setTechnicalStatus(new TechnicalStatus(RESConstants.TS_STATUS_DISPATCHED));
			}
		} else if (workBean.getTechnicalSanctionTypeId() != null) {
			technicalSanctionEntity
					.setTechnicalSanctionType(new TechnicalSanctionType(
							workBean.getTechnicalSanctionTypeId()));
		}

		if (workBean.getTechnicalSanctionNo() != null)
			technicalSanctionEntity.setTechnicalSanctionNo(workBean
					.getTechnicalSanctionNo());
		if (workBean.getTechnicalSanctionDate() != null)
			technicalSanctionEntity.setTechnicalSanctionDate(RESUtil
					.convertStringToDate(workBean.getTechnicalSanctionDate()));
		if (workBean.getEstimatedCostString() != null)
			technicalSanctionEntity.setTechnicalSanctionAmount(new BigDecimal(
					workBean.getEstimatedCostString()));
		if (workBean.getTsAuthorityName() != null)
			technicalSanctionEntity.setTsAuthorityName(workBean
					.getTsAuthorityName());
		if (workBean.getTsIssuingAuthorityId() != null)
			technicalSanctionEntity.setTsIssuingAuthority(new Designation(
					workBean.getTsIssuingAuthorityId()));

	}

	private void convertWorkBeanToAdministrationSanctionEntityDuringEdit(
			AdministrationSanction administrationSanctionEntity,
			WorkBean workBean) throws RESBusinessException {
		administrationSanctionEntity
				.setAdministrationSanctionType(new AdministrationSanctionType(
						workBean.getAdministrationSanctionTypeId()));
		administrationSanctionEntity.setAdministrativeSanctionNo(workBean
				.getAdministrationSanctionNo());
		administrationSanctionEntity.setAdministrativeSanctionDate(RESUtil
				.convertStringToDate(workBean.getAdministrationSanctionDate()));
		if (workBean.getIssuingAuthorityId() != null)
			administrationSanctionEntity
					.setIssuingAuthority(new IssuingAuthority(workBean
							.getIssuingAuthorityId()));
		administrationSanctionEntity
				.setAdministrativeSanctionAmount(new BigDecimal(workBean
						.getTotalCostString()));
		administrationSanctionEntity.setAsAuthorityName(workBean
				.getAsAuthorityName());
		administrationSanctionEntity.setContingencyAmount(workBean
				.getContingencyAmount());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String addRequisitionWork(WorkBean bean) {
		try {
			if (bean != null) {
				Work entity = new Work();

				if (bean.getLineDepartmentFile() != null) {
					DocumentUpload documentUpload = RESUtil.uploadDocument(
							documentRootPath + workRequisitionPath, "blank",
							bean.getLineDepartmentFile(), null, "blank");
					documentRepository.save(documentUpload);
					entity.setLineDepartmentFile(documentUpload);
				}

				// Generate Work Requisition Number

				String lastWord = bean.getExecutiveEngineerOfficeName()
						.substring(
								bean.getExecutiveEngineerOfficeName()
										.lastIndexOf(",") + 1);
				if (bean.getAgencyTypeId() == 1) {
					lastWord = lastWord + "_RC";
				} else if (bean.getAgencyTypeId() == 2) {
					lastWord = lastWord + "_GP";
				} else if (bean.getAgencyTypeId() == 3) {
					lastWord = lastWord + "_RD";
				}

				WorkRequisitionIdGeneration workRequisitionIdGeneration = workRequisitionIdGenerationRepository
						.findByDivisionAgency(lastWord);
				if (workRequisitionIdGeneration == null) {
					entity.setWorkRequisitionNo(lastWord + "_1");
					persistFirstRequestIdGeneration(lastWord, 1);
				} else {
					int newCount = workRequisitionIdGeneration.getCounter() + 1;
					workRequisitionIdGeneration.setCounter(newCount);
					workRequisitionIdGenerationRepository.save(workRequisitionIdGeneration);
					entity.setWorkRequisitionNo(lastWord + "_" + newCount);

					/*
					 * WorkLegacyIdGeneration workLegacyIdGeneration = workLegacyIdGenerationList
					 * .get(workLegacyIdGenerationList.size() - 1);
					 * int count = workLegacyIdGeneration.getCounter();
					 * persistNextLegacyIdGeneration(lastWord, count);
					 */

				}

				convertWorkBeanToEntityDuringRequisition(entity, bean);
				entity.setFinancialYear("AS Pending");
				entity.setBillingFlag((short) 0);
				workRepository.save(entity);

				// Handle KML file
				if (bean.getKmlFile() != null && !bean.getKmlFile().isEmpty()) {
					String fullDocumentPath = documentRootPath + workMasterKmlFilePath;
					String workIdentifier = (entity.getId() != null) ? "Work_" + entity.getId()
							: "Temp_" + System.currentTimeMillis();

					DocumentUpload documentUpload = RESUtil.uploadKMLDocumentForWorkLocation(fullDocumentPath,
							workIdentifier, bean.getKmlFile(), "Kml file upload for project", "blank");

					documentUpload = documentRepository.save(documentUpload);

					entity.setKmlFileUpload(documentUpload);
				}

				// Save again after KML file handling if needed
				entity = workRepository.save(entity);

			}
			return null;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return RESConstants.ERROR_SAVING_DATA;
		}
	}

	private void persistFirstRequestIdGeneration(String lastWord, int i) {
		WorkRequisitionIdGeneration workRequisitionIdGeneration = new WorkRequisitionIdGeneration();
		workRequisitionIdGeneration.setDivisionAgency(lastWord);
		workRequisitionIdGeneration.setCounter(1);
		workRequisitionIdGenerationRepository.save(workRequisitionIdGeneration);
		// TODO Auto-generated method stub

	}

	private void convertWorkBeanToEntityDuringRequisition(Work entity,
			WorkBean bean) throws RESBusinessException {

		entity.setWorkName(bean.getWorkName());
		entity.setWorkTypeId(new WorkType(bean.getWorkTypeId()));
		entity.setWorkNatureId(new WorkNature(bean.getWorkNatureId()));

		if (null != bean.getSchemeTypeId())
			entity.setSchemeTypeId(new SchemeSanctionedUnderProgramme(bean.getSchemeTypeId()));

		if (bean.getWorkSubTypeId() != null) {
			entity.setWorkSubTypeId(new WorkSubType(bean.getWorkSubTypeId()));
		} else {
			entity.setWorkSubTypeId(null);
		}

		entity.setDistance(bean.getDistance());

		entity.setLineDepartmentId(new LineDepartment(bean
				.getLineDepartmentId()));
		entity.setLetterNo(bean.getLetterNo());
		entity.setLetterDate(RESUtil.convertStringToDate(bean
				.getLetterDateString()));
		entity.setAccountHead(new AccountHead(bean.getAccountHeadId()));
		entity.setAgencyTypeId(new AgencyType(bean.getAgencyTypeId()));
		entity.setDistrict(new District(bean.getDistrictId()));

		if (bean.isKmlFileUpload() == true) {
			Block block = blockRepository.findByBlockCode(bean.getBlockId().toString());
			if (block != null) {

				entity.setBlock(new Block(block.getBlockId()));
			}
		} else {
			if (bean.getBlockId() != null)
				entity.setBlock(new Block(bean.getBlockId()));
		}

		if (bean.isKmlFileUpload() == true) {
			List<GramPanchayat> gpList = gramPanchayatRepository.findByGpCode(bean.getGramPanchayatId().toString());
			if (!gpList.isEmpty()) {
				GramPanchayat firstGp = gpList.get(0);
				entity.setGramPanchayat(new GramPanchayat(firstGp.getGramPanchayatId()));
			}

		} else {
			entity.setGramPanchayat(new GramPanchayat(bean.getGramPanchayatId()));
		}

		if (bean.isKmlFileUpload() == true) {
			Village village = villageRepository.findByVillageCode(bean.getVillageId().toString());
			entity.setVillage(new Village(village.getId()));

		} else {
			if (bean.getVillageId() != null)
				entity.setVillage(new Village(bean.getVillageId()));
		}

		entity.setWorkLocationLatitude(bean.getWorkLocationLatitude());
		entity.setWorkLocationLongitude(bean.getWorkLocationLongitude());
		entity.setLocationAddress(bean.getLocationAddress());

		if (bean.getOfficeId() != null)
			entity.setExecutiveEngineerOffice(new Office(bean.getOfficeId()));
		if (bean.getParentOfficeId() != null)
			entity.setSuperintendingEngineerOffice(new Office(bean
					.getParentOfficeId()));
		if (bean.getChiefOfficeId() != null)
			entity.setChiefEngineerOffice(new Office(bean.getChiefOfficeId()));

		if (bean.getClientIp() != null)
			entity.setClientIp(bean.getClientIp());

		if (bean.getAssistantEngineerId() != null)
			entity.setAssistantEngineer(new Users(bean.getAssistantEngineerId()));
		if (bean.getSubEngineerId() != null)
			entity.setSubEngineer(new Users(bean.getSubEngineerId()));

		if (bean.getSubDivisionOfficerId() != null)
			entity.setSubDivisionalOfficer(new Users(bean.getSubDivisionOfficerId()));
		/* entity.setStatus(RESConstants.STATUS_ACTIVE); */
		/* entity.setStatus(bean.getStatus()); */

		if (bean.getSpecificFieldsEditFlag() == null) {
			entity.setWorkRequestStatusId(new RequestStatus(bean.getWorkRequestStatusId()));
		}
		entity.setIsLegacy((short) 0);
		// setting work status as not started.
		entity.setWorkStatusId(new WorkStatus(RESConstants.STATUS_NOT_STARTED_ID));
		// end here

		if (bean.getRemarks() != null) {
			entity.setRemarks(bean.getRemarks());
		}

		/*
		 * if (bean.getContractorId() != null) {
		 * entity.setContractor(new Contractor(bean.getContractorId()));
		 * }
		 */

	}

	@Override
	public String editRequisitionWork(WorkBean workBean) {

		try {
			Work entity = workRepository.findOne(workBean.getWorkId());

			if (workBean.getLineDepartmentFile() != null) {
				DocumentUpload documentUpload = RESUtil.uploadDocument(
						documentRootPath + workRequisitionPath, "blank",
						workBean.getLineDepartmentFile(), null, "blank");
				documentRepository.save(documentUpload);
				entity.setLineDepartmentFile(documentUpload);
			}

			// Requisition Work ID Generation

			String lastWord = workBean.getExecutiveEngineerOfficeName()
					.substring(
							workBean.getExecutiveEngineerOfficeName()
									.lastIndexOf(",") + 1);

			int index = entity.getWorkRequisitionNo().lastIndexOf('_');
			String oldRequisition = entity.getWorkRequisitionNo().substring(0, index);

			WorkRequisitionIdGeneration workRequisitionIdGeneration = null;

			if (workBean.getAgencyTypeId() == 1) {
				workRequisitionIdGeneration = workRequisitionIdGenerationRepository
						.findByDivisionAgency(lastWord + "_RC");
			} else if (workBean.getAgencyTypeId() == 2) {
				workRequisitionIdGeneration = workRequisitionIdGenerationRepository
						.findByDivisionAgency(lastWord + "_GP");
			} else if (workBean.getAgencyTypeId() == 3) {
				workRequisitionIdGeneration = workRequisitionIdGenerationRepository
						.findByDivisionAgency(lastWord + "_RD");
			}

			if (workRequisitionIdGeneration == null) {
				entity.setWorkRequisitionNo(lastWord + "_1");
				persistFirstRequestIdGeneration(lastWord, 1);
			} else {
				if (!oldRequisition.equals(workRequisitionIdGeneration.getDivisionAgency())) {

					int newCount = workRequisitionIdGeneration.getCounter() + 1;
					workRequisitionIdGeneration.setCounter(newCount);
					workRequisitionIdGenerationRepository.save(workRequisitionIdGeneration);

					if (workBean.getAgencyTypeId() == 1) {
						entity.setWorkRequisitionNo(lastWord + "_RC" + "_" + newCount);
					} else if (workBean.getAgencyTypeId() == 2) {
						entity.setWorkRequisitionNo(lastWord + "_GP" + "_" + newCount);
					} else if (workBean.getAgencyTypeId() == 3) {
						entity.setWorkRequisitionNo(lastWord + "_RD" + "_" + newCount);
					}

					WorkRequisitionIdGeneration workRequisitionIdGenerationOld = workRequisitionIdGenerationRepository
							.findByDivisionAgency(oldRequisition);

					int newCountTwo = workRequisitionIdGeneration.getCounter() - 1;
					workRequisitionIdGenerationOld.setCounter(newCountTwo);
					workRequisitionIdGenerationRepository.save(workRequisitionIdGenerationOld);
				}
			}

			convertWorkBeanToEntityDuringRequisition(entity, workBean);

			/* convertWorkBeanToEntityDuringEdit(entity, workBean); */
			if (workBean.getSpecificFieldsEditFlag() == null) {
				entity.setFinancialYear("AS Pending");
			}

			workRepository.save(entity);

			return null;

		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return RESConstants.ERROR_SAVING_DATA;
		}

	}

	/*
	 * private void persistNextLegacyIdGeneration(String lastWord, Integer count) {
	 * // TODO Auto-generated method stub
	 * WorkLegacyIdGeneration workLegacyIdGeneration = new WorkLegacyIdGeneration();
	 * workLegacyIdGeneration.setDivisionAgency(lastWord);
	 * workLegacyIdGeneration.setCounter(count);
	 * workLegacyIdGenerationRepository.save(workLegacyIdGeneration);
	 * 
	 * }
	 */

	private void persistFirstLegacyIdGeneration(String lastWord, int i) {

		WorkLegacyIdGeneration workLegacyIdGeneration = new WorkLegacyIdGeneration();
		workLegacyIdGeneration.setDivisionAgency(lastWord);
		workLegacyIdGeneration.setCounter(1);
		workLegacyIdGenerationRepository.save(workLegacyIdGeneration);

	}

	@Override
	public List<KmlFilePoints> processKmlFile(KmlFilePoints bean) {
		MultipartFile file = bean.getKmlFile();
		List<KmlFilePoints> pointsList = new ArrayList<>();
		// System.err.println(bean.getProjectId());
		Set<GramPanchayatBean> beangp = new HashSet<>();
		Set<Long> gpId = new HashSet<>();
		User user = RESUtil.getUserDetail();

		UserBean fetchUserDetailsByUserName = userService.fetchUserDetailsByUserName(user.getUsername());

		for (Long i : gpId) {
			beangp.add(convertGramPanchayatEntityToBean(gramPanchayatRepository.findOne(i)));
		}
		if (beangp.iterator().hasNext()) {
			GramPanchayatBean next = beangp.iterator().next();
		}

		String gpcode = "";// next.getGramPanchayatCode() ;

		for (GramPanchayatBean b : beangp) {
			gpcode = b.getGramPanchayatId().toString();

		}

		try {
			// Build the SAXBuilder
			SAXBuilder saxBuilder = new SAXBuilder();

			// Get the InputStream from the MultipartFile
			InputStream inputStream = file.getInputStream();

			// Parse the KML file
			Document document = saxBuilder.build(inputStream);

			// Get the root element of the document (which is usually <kml>)
			Element rootElement = document.getRootElement();
			List<String> grampanchayatCodeList = new ArrayList<>();
			Iterator<Element> coordinatesIterator = rootElement.getDescendants(new ElementFilter("coordinates"));
			Iterator<Element> placemarkIterator = rootElement.getDescendants(new ElementFilter("Placemark")).iterator();
			if (placemarkIterator.hasNext()) {
				while (placemarkIterator.hasNext()) {

					Element firstPlacemark = placemarkIterator.next();

					Iterator<Element> descendants = firstPlacemark.getDescendants(new ElementFilter("coordinates"));

					while (descendants.hasNext()) {
						Element descendant = descendants.next();
						String[] split = descendant.getTextTrim().split(" ");
						System.err.println(split.length);
					}
					System.err.println("1");
					// Get ExtendedData properly
					Iterator<Element> extendedIterator = firstPlacemark
							.getDescendants(new ElementFilter("ExtendedData")).iterator();
					if (extendedIterator.hasNext()) {
						Element extendedData = extendedIterator.next();
						System.err.println("2");
						System.out.println("Children of ExtendedData:");
						for (Element child : extendedData.getChildren()) {
							System.out.println("Element name: " + child.getName());
						}

						// Retrieve SchemaData inside ExtendedData
						Iterator<Element> schemaIterator = extendedData.getDescendants(new ElementFilter("SchemaData"))
								.iterator();
						if (schemaIterator.hasNext()) {
							Element schemaData = schemaIterator.next();
							System.err.println("3");
							// Extract SimpleData elements System.out.println("Children of ExtendedData:");
							for (Element child : schemaData.getChildren()) {
								if (child.getAttributeValue("name").equals("lgd_gp_cd")) {
									grampanchayatCodeList.add(child.getText());
								}
								System.out.println(
										"Element name: " + child.getAttributeValue("name") + " - " + child.getText());
							}
							// List<Element> simpleDataList = schemaData.getChildren("SimpleData");

						} else {
							System.out.println("No SchemaData found.");
						}
					} else {
						System.out.println("No ExtendedData found.");
					}
				}
			}

			// System.err.println(grampanchayatCodeList + " - " + gpcode);

			// Create a list to store the coordinates elements
			List<Element> coordinatesElements = new ArrayList<>();
			boolean hasCoordinates = false;
			// Iterate over the coordinates and add them to the list
			while (coordinatesIterator.hasNext()) {
				Element coordinatesElement = coordinatesIterator.next();
				coordinatesElements.add(coordinatesElement);
			} // Process each <coordinates> element
			int index = 0;

			for (Element coordinatesElement : coordinatesElements) {
				String coordinatesText = coordinatesElement.getTextTrim();
				hasCoordinates = true;
				// Assuming coordinates are in "longitude,latitude,altitude" format
				if (coordinatesText.contains(" ")) {
					String[] coordinatesArray = coordinatesText.split(" ");
					if (coordinatesArray.length >= 1) {
						for (String s : coordinatesArray) {
							String[] split = s.split(",");
							String lat = split[1];
							String lon = split[0];
							KmlFilePoints point = new KmlFilePoints();
							point.setLongitude(Double.parseDouble(lon));
							point.setLattitude(Double.parseDouble(lat));
							point.setLocationName(index++ + "");
							pointsList.add(point);
						}
					}
				} else {
					String[] coordinatesArray = coordinatesText.split(",");
					if (coordinatesArray.length >= 1) {
						// Extract Longitude and Latitude
						String longitude = coordinatesArray[0];
						String latitude = coordinatesArray[1];

						// Create KmlFilePoints object and add it to the list
						KmlFilePoints point = new KmlFilePoints();

						if (latitude.contains(" ")) {
							String[] parts = latitude.split(" ");
							latitude = parts[0];
							longitude = parts[1];

						}
						point.setLongitude(Double.parseDouble(longitude));
						point.setLattitude(Double.parseDouble(latitude));
						pointsList.add(point);
					}
				}
			}
			if (!hasCoordinates) {
				Iterator<Element> longitudeElements = rootElement.getDescendants(new ElementFilter("longitude"));
				Iterator<Element> latitudeElements = rootElement.getDescendants(new ElementFilter("latitude"));

				if (longitudeElements.hasNext() && latitudeElements.hasNext()) {
					String longitudeText = longitudeElements.next().getTextTrim();
					String latitudeText = latitudeElements.next().getTextTrim();

					KmlFilePoints point = new KmlFilePoints();
					point.setLongitude(Double.parseDouble(longitudeText));
					point.setLattitude(Double.parseDouble(latitudeText));
					pointsList.add(point);
				}

			}
			if (pointsList.isEmpty()) {
				Iterator<Element> longitudeElements = rootElement.getDescendants(new ElementFilter("longitude"));
				Iterator<Element> latitudeElements = rootElement.getDescendants(new ElementFilter("latitude"));

				if (longitudeElements.hasNext() && latitudeElements.hasNext()) {
					String longitudeText = longitudeElements.next().getTextTrim();
					String latitudeText = latitudeElements.next().getTextTrim();

					KmlFilePoints point = new KmlFilePoints();
					point.setLongitude(Double.parseDouble(longitudeText));
					point.setLattitude(Double.parseDouble(latitudeText));
					pointsList.add(point);
				}
			}

		} catch (Exception e) {
			e.printStackTrace(); // Handle exceptions properly in your real code
		}

		pointsList.get(0).setGpCodeList(Arrays.asList(gpcode));
		return pointsList;
	}

	private GramPanchayatBean convertGramPanchayatEntityToBean(GramPanchayat entity) {

		GramPanchayatBean bean = new GramPanchayatBean();
		if (entity != null) {
			bean.setGramPanchayatId(entity.getGramPanchayatId());
			System.out.println("GramPanchayatId111" + bean.getGramPanchayatId());
			bean.setGpNameH(entity.getGpNameH());
			bean.setGpName(entity.getGpName());
			bean.setGpCode(entity.getGpCode());
			bean.setEnabled(entity.getEnabled());
			// System.out.println("GramPanchayatCode"+entity.getGramPanchayatCode());
		}
		return bean;
	}

	@Override
	public VillageBean fetchVillageByVCode(Long long1) {
		VillageBean bean = new VillageBean();

		try {
			Village entity = villageRepository.findOne(long1);

			Village vEntity = villageRepository.findByVillageCode(entity.getVillageCode());

			bean = convertVillageEntityToBean(vEntity);

		} catch (Exception e) {
			// TODO: handle exception
		}

		return bean;

	}

	@Override
	public List<GramPanchayatBean> fetchGramPanchayatByGPCode(Long gpCode) {
		List<GramPanchayatBean> beanl = new ArrayList<>();
		try {
			List<GramPanchayat> entityl = gramPanchayatRepository.findByGpCode(gpCode.toString());
			for (GramPanchayat entity : entityl) {
				GramPanchayatBean bean = new GramPanchayatBean();
				bean.setGpCode(entity.getGpCode());
				bean.setGramPanchayatId(entity.getGramPanchayatId());
				bean.setGpName(entity.getGpName());
				bean.setGpNameH(entity.getGpNameH());
				beanl.add(bean);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		return beanl;

	}

	public GramPanchayatBean fetchLgdGpCode(Long gpId) {
		try {
			GramPanchayat entity = gramPanchayatRepository.findOne(gpId);
			GramPanchayatBean bean = new GramPanchayatBean();
			bean.setGpCode(entity.getGpCode());
			bean.setGramPanchayatId(entity.getGramPanchayatId());
			bean.setGpName(entity.getGpName());
			bean.setGpNameH(entity.getGpNameH());
			return bean;

		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	private VillageBean convertVillageEntityToBean(Village entity) {

		VillageBean bean = new VillageBean();

		if (entity != null) {

			bean.setBlockCode(entity.getBlockCode());
			bean.setDistrictCode(entity.getDistrictCode());
			bean.setEnabled(RESConstants.ENABLED);
			bean.setGpCode(entity.getGpCode());
			bean.setVillageId(entity.getId());
			System.out.println(entity.getId());
			bean.setVillageName(entity.getVillageName());
			bean.setTehsilCode(entity.getTehsilCode());
			bean.setVillageCode(entity.getVillageCode());

		}
		return bean;

	}

	@Override
	public BlockBean fetchblockCode(Long blockId) {
		Block block = blockRepository.findOne(blockId);
		BlockBean bean = new BlockBean();
		bean.setBlockCode(block.getBlockCode());
		return bean;
	}

	@Override
	@Transactional
	public ResponseObject IssueEmbNumber(EmbDto dto, UserBean fetchLoggedInUserDetails) {
		ResponseObject object = new ResponseObject();

		try {

			Long workId = dto.getWorkid();

			workEmb entity = null;
			// Fetch or create new entity

			entity = embRepository.findByWorkId(workId);

			if (entity == null) {

				entity = new workEmb();
			}

			// Fetch required data
			Work work = workRepository.findOne(workId);
			Long villageId = work.getVillage().getId();
			String villageCode = villageRepository.findOne(villageId).getVillageCode();
			Users engineer = userRepository.findOne(dto.getEngineerId());

			// Prepare EMB number
			String embNumber = villageCode + "/" + workId;
			entity.setRemarks(dto.getRemarks());
			entity.setIpAddress(dto.getIpAddress());
			// Set values
			entity.setWorkId(workId);
			entity.setEngineerId(dto.getEngineerId());
			entity.setEmbNo(embNumber);

			// Update work status
			work.seteMbStatus(1L);
			Work save = workRepository.save(work);
			entity.seteMbIssueDate(new Date());
			// Save EMB
			embRepository.save(entity);

			object.setSuccessMessage(
					"e-MB No. - " + embNumber + " has been successfully issued for the work Id "
							+ workId + " to " + engineer.getUsername());

			return object;

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error while issuing e-MB");
		}

	}

	@Override
	public WorkAgreementJson fetchWorkListForEmb(Pageable pageable,
			String searchBoxVal, String loggedInUserRole, String username) {

		WorkAgreementJson workAgreementJson = null;

		if (searchBoxVal.isEmpty()) {
			searchBoxVal = null;
		}

		try {

			// Page<Work> work = null;

			Users entity = userRepository.findByUsernameAndStatus(username,
					RESConstants.STATUS_ACTIVE);
			long count = 0;
			int maxLimit = (pageable.getPageSize() * pageable.getPageNumber()) == 0 ? pageable.getPageSize()
					: (pageable.getPageSize() * pageable.getPageNumber());
			List<Object[]> entityList = null;
			if (loggedInUserRole.equals(RESConstants.ROLE_EE)) {// EE login
				entityList = workRepository
						.findPendingWorkAgrrementByExecutiveEngineerOfficeQuery(entity.getOffice().getId(),
								pageable.getOffset(), maxLimit);

				count = workRepository
						.findPendingWorkAgrrementByExecutiveEngineerOfficeCount(entity.getOffice().getId());
			}

			// if (work != null) {

			List<WorkAgreementBean> beanList = new ArrayList<>();
			if (entityList != null && !entityList.isEmpty()) {

				int index = pageable.getPageNumber() * pageable.getPageSize();

				for (Object[] objArr : entityList) {

					// WorkAgreementBean bean = convertWorkEntityToAgreementBean(element);
					WorkAgreementBean bean = new WorkAgreementBean();
					bean.setWorkId(Long.parseLong(objArr[0].toString()));
					bean.setWorkName((String) objArr[2]);
					bean.setWorkRequisitionNo((String) objArr[1]);
					bean.setWorkStatus((String) objArr[8]);
					bean.setExecutionAgency((String) objArr[4]);

					// WorkTender workTender = workTenderRepository.findByWorkId(work.getId());
					// WorkTender workTender =
					// (workTenderRepository.findByWorkIdOrderByCreatedDateDesc(work.getId()).size()>0?workTenderRepository.findByWorkIdOrderByCreatedDateDesc(work.getId()).get(0):null);
					if (objArr[5] != null) {
						bean.setTenderCost((BigDecimal) objArr[5]);
						bean.setContractorId(Long.parseLong(objArr[6].toString()));
						bean.setContractorName((String) objArr[7]);
					}
					if (objArr[13] != null)
						bean.setTenderId(Long.parseLong(objArr[13].toString()));

					// WorkAgreement workAgreement =
					// workAgreementRepository.findByWorkTender(workTender);
					if (objArr[10] != null) {
						bean.setAgreementDate(
								RESUtil.convertDateToStringWithFormat((Date) objArr[10], RESConstants.DATE_FORMAT));
						// bean.setWrittenOrderDate(RESUtil.convertDateToStringWithFormat(workAgreement.getWritten_order_date(),
						// RESConstants.DATE_FORMAT));
						bean.setTentativeCompletionDate(
								RESUtil.convertDateToStringWithFormat((Date) objArr[11], RESConstants.DATE_FORMAT));

					}
					if (objArr[9] != null)
						bean.setWorkAgreementStatusId(Long.parseLong(objArr[9].toString()));
					if (objArr[15] != null)
						bean.setWorkAgreementStatus((String) objArr[15]);
					if (objArr[14] != null)
						bean.setWorkAgreementId(Long.parseLong(objArr[14].toString()));
					if (objArr[12] != null)
						bean.setParentId(Long.parseLong(objArr[12].toString()));

					workEmb byWorkId = embRepository.findByWorkId(bean.getWorkId());

					if (byWorkId != null) {
						bean.setEmbNo(byWorkId.getEmbNo());

					}

					bean.setIndex(++index);
					beanList.add(bean);
				}

			}
			workAgreementJson = new WorkAgreementJson();
			workAgreementJson.setiTotalDisplayRecords(count);
			workAgreementJson.setiTotalRecords(count);
			workAgreementJson.setAaData(beanList);
			// }
			return workAgreementJson;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return workAgreementJson;
		}
	}

	@Override
	public List<Users> getAllEngineerOfficer(Long id) {
		List<Users> users = new ArrayList<>();
		try {
			Users one = userRepository.findOne(id);
			List<Designation> asList = Arrays.asList(new Designation(6L));

			List<Users> byDistrictAndDesignationIdIn = userRepository
					.findByOfficeAndDesignationAndStatus(one.getOffice(), asList, "Active");
			for (Users a : byDistrictAndDesignationIdIn) {
				Users u = new Users();
				u.setId(a.getId());
				// u.setFirstname(a.getFir);
				u.setEmailId(a.getEmailId());
				u.setUsername(a.getUsername());
				u.setMobileNo(a.getMobileNo());

				users.add(u);
			}
			return users;
		} catch (Exception e) {
			logger.info("Error comes in fething Engineers", e);
			return null;
		}
	}

	@Override
	public List<Office> getOfficeOfTSPerson(Long id, Long workId) {

		List<Office> list = new ArrayList<>();

		if (workId == null || id == null) {
			return list;
		}

		try {

			Work work = workRepository.findOne(workId);

			if (work == null) {
				return list;
			}

			String workName = work.getWorkName();

			Users user = userRepository.findOne(id);

			if (user == null) {
				return list;
			}

			Office office2 = officeRepository.findOne(user.getOffice().getId());

			Office office = new Office();

			if (office2 != null) {
				office.setOfficeName(office2.getOfficeName());
			}

			if (workName != null) {
				office.setOfficeNameH(workName);
			}

			list.add(office);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in getOfficeOfTSPerson", e);
		}

		return list;
	}

	@Override
	public WorkJson fetchWorkWithEmbIssuedForEnginner(UserBean user, Pageable pageable, String searchBoxVal,
			String ProjectId, String blockId, String grampanchayatId, String villageId) {

		WorkJson workjson = new WorkJson();
		try {
			Long project = null;
			if (ProjectId != null) {
				project = Long.parseLong(ProjectId);
			}
			Long block = null;
			if (blockId != null) {
				block = Long.parseLong(blockId);
			}
			Long grampanchayat = null;
			if (grampanchayatId != null) {
				grampanchayat = Long.parseLong(grampanchayatId);
			}
			Long village = null;
			if (villageId != null) {
				village = Long.parseLong(villageId);
			}

			Page<Work> work = null;

			Long id = user.getId();
			List<workEmb> embs = null;
			if (searchBoxVal != null && !searchBoxVal.isEmpty()) {
				embs = embRepository.findByEmbNoContaining(searchBoxVal);
			}
			List<workEmb> workids = embRepository.findAllByEngineerId(id);

			if (embs != null) {

			}
			work = workRepository.fetchWorkWithEmbIssuedEngineer(pageable,
					id, searchBoxVal);

			if (work != null) {
				List<Work> entityList = work.getContent();
				List<WorkBean> beanList = new ArrayList<>();
				if (entityList != null) {

					int index = pageable.getPageNumber() * pageable.getPageSize();
					for (Work proj : entityList) {
						// proj.setProjectName("TS");
						WorkBean bean = convertWorkEntityToBean(proj);
						workEmb byWorkId = embRepository.findByWorkId(proj.getId());

						if (byWorkId != null) {
							bean.setEmbNo(byWorkId.getEmbNo());
							bean.setEmbDate(byWorkId.geteMbIssueDate() + "");
						}
						bean.setIndex(++index);
						beanList.add(bean);
					}
				}
				workjson = new WorkJson();
				workjson.setiTotalDisplayRecords(work.getTotalElements());
				workjson.setiTotalRecords(workRepository.count());
				workjson.setAaData(beanList);
			}
			return workjson;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return workjson;
		}
	}

	private void populateWorkTemplateBeanFromEntity(WorkEstimationItems workEstimationItem,
			WorkTemplateBean workTemplateBean) {
		workTemplateBean.setId(workEstimationItem.getId());
		if (null != workEstimationItem.getAmoountMachinery()) {
			workTemplateBean.setAmoountMachinery(workEstimationItem.getAmoountMachinery().toString());
		}
		if (null != workEstimationItem.getAmount()) {
			workTemplateBean.setAmount(workEstimationItem.getAmount().toString());
		}
		if (null != workEstimationItem.getAmountExcavation()) {
			workTemplateBean.setAmountExcavation(workEstimationItem.getAmountExcavation().toString());
		}
		if (null != workEstimationItem.getAmountLooseningSoil()) {
			workTemplateBean.setAmountLooseningSoil(workEstimationItem.getAmountLooseningSoil().toString());
		}
		if (null != workEstimationItem.getAmountMaterial()) {
			workTemplateBean.setAmountMaterial(workEstimationItem.getAmountMaterial().toString());
		}

		// HEIGHT / DEPTH
		if (workEstimationItem.getHeightDepth() != null) {
			workTemplateBean.setMeasureHeightDepth(true);
			workTemplateBean.setHeightDepth(
					workEstimationItem.getHeightDepth()
							.stripTrailingZeros()
							.toPlainString());
		} else {
			workTemplateBean.setMeasureHeightDepth(true);
			workTemplateBean.setHeightDepth("1");
		}

		// WIDTH
		if (workEstimationItem.getWidth() != null) {
			workTemplateBean.setMeasureWidth(true);
			workTemplateBean.setWidth(
					workEstimationItem.getWidth()
							.stripTrailingZeros()
							.toPlainString());
		} else {
			workTemplateBean.setMeasureWidth(true);
			workTemplateBean.setWidth("1");
		}

		// LENGTH
		if (workEstimationItem.getLength() != null) {
			workTemplateBean.setMeasureLength(true);
			workTemplateBean.setLength(
					workEstimationItem.getLength()
							.stripTrailingZeros()
							.toPlainString());
		} else {
			workTemplateBean.setMeasureLength(true);
			workTemplateBean.setLength("1");
		}
		workTemplateBean.setId(workEstimationItem.getId());
		workTemplateBean.setItemDesc(workEstimationItem.getItemDesc());
		if (null != workEstimationItem.getAmountLabour()) {
			workTemplateBean.setLabourComponentValue(workEstimationItem.getAmountLabour().toString());
			workTemplateBean.setLabourComponent(true);
		}
		if (null != workEstimationItem.getNo()) {
			workTemplateBean.setNo(workEstimationItem.getNo().stripTrailingZeros().toPlainString());
		}
		if (null != workEstimationItem.getQuantity()) {
			workTemplateBean.setQuantity(workEstimationItem.getQuantity().stripTrailingZeros().toPlainString());
		}
		if (null != workEstimationItem.getRate()) {
			workTemplateBean.setRate(workEstimationItem.getRate().stripTrailingZeros().toPlainString());
		}
		if (null != workEstimationItem.getRateLabour()) {
			workTemplateBean.setRateLabour(workEstimationItem.getRateLabour().stripTrailingZeros().toPlainString());
		}
		workTemplateBean.setSorItemNo(workEstimationItem.getSorItemNo());
		workTemplateBean.setUnit(workEstimationItem.getUnit());
		workTemplateBean.setHasChild(workEstimationItem.getHasChild());
		if (null != workEstimationItem.getHasChild() && workEstimationItem.getHasChild()) {
			workTemplateBean.setParentItem(new WorkTemplateBean());
		}

		if (null != workEstimationItem.getUnit() && !workEstimationItem.getUnit().isEmpty()) {
			workTemplateBean.setUnitReadOnly(true);
		}
		if (null != workEstimationItem.getRate()) {
			workTemplateBean.setRateReadOnly(true);
		}
		if (null != workEstimationItem.getSorItemNo() && !workEstimationItem.getSorItemNo().isEmpty()) {
			workTemplateBean.setSorItemNoReadOnly(true);
		}
		if (null != workEstimationItem.getItemDesc() && !workEstimationItem.getItemDesc().isEmpty()) {
			workTemplateBean.setDescReadOnly(true);
		}
		workTemplateBean.setGroup(workEstimationItem.getGroup());
		workTemplateBean.setNew(false);
	}

	@Override
	public List<chapterDesciptionDto> loadItemChapterWise(String workId, UserBean fetchLoggedInUserDetails) {

		List<chapterDesciptionDto> beanlist = new ArrayList<>();

		Long workid = Long.parseLong(workId);
		Work one = workRepository.findOne(workid);
		WorkEstimation byWork = workEstimationRepository.findByWorkAndEnabled(one, true);

		List<WorkEstimationItems> workEstimationItems = workEstimationItemsRepository
				.findByWorkEstimationAndEnabled(byWork, true);

		if (workEstimationItems == null || workEstimationItems.isEmpty()) {
			return beanlist;
		}

		// 1️⃣ Convert entity → WorkTemplateBean
		List<WorkTemplateBean> workTemplateItems = new ArrayList<>();

		for (WorkEstimationItems entity : workEstimationItems) {
			WorkTemplateBean bean = new WorkTemplateBean();

			List<Measurement> mesuremtnlist = measurementRepository.findByEstimateSorIdOrderByIdDesc(entity.getId());

			if (mesuremtnlist != null && !mesuremtnlist.isEmpty()
					&& mesuremtnlist.get(0).getCreatedDate() != null) {

				Date createdDate = mesuremtnlist.get(0).getCreatedDate();

				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				String formattedDate = sdf.format(createdDate);

				bean.setLastMeasurementDate(formattedDate);

			} else {
				bean.setLastMeasurementDate("");
			}

			populateWorkTemplateBeanFromEntity(entity, bean);
			workTemplateItems.add(bean);
		}

		// 2️⃣ Collect all SOR Item Numbers
		String previous = null;

List<String> sorItemNos = new ArrayList<>();

for (WorkEstimationItems w : workEstimationItems) {
    String sor = w.getSorItemNo();

    if (sor != null) {
        previous = sor;
        sorItemNos.add(sor);
    } else if (previous != null) {
        sorItemNos.add(previous);
    }
}

List<Item> items = null;

if (!sorItemNos.isEmpty()) {
    items = itemRepository.findByItemNumberIn(sorItemNos);
}

             

		// 4️⃣ Build Map: sorItemNo → Chapter
		Map<String, Chapter> sorChapterMap = items.stream()
				.collect(Collectors.toMap(Item::getItemNumber, Item::getChapter, (a, b) -> a));

		// 5️⃣ Group WorkTemplateBeans by Chapter

		Map<Chapter, List<WorkTemplateBean>> chapterGroupMap = workTemplateItems.stream()
				.filter(bean -> bean.getSorItemNo() != null)
				.filter(bean -> sorChapterMap.containsKey(bean.getSorItemNo()))
				.collect(Collectors.groupingBy(bean -> sorChapterMap.get(bean.getSorItemNo())));

		// 6️⃣ Build final DTO list
		for (Map.Entry<Chapter, List<WorkTemplateBean>> entry : chapterGroupMap.entrySet()) {

			Chapter chapter = entry.getKey();

			chapterDesciptionDto dto = new chapterDesciptionDto();
			dto.setChapterId(chapter.getId());
			dto.setChapterName(chapter.getChapterName());
			dto.setItemList(entry.getValue());

			beanlist.add(dto);
		}

		beanlist.sort(Comparator.comparing(chapterDesciptionDto::getChapterName, String.CASE_INSENSITIVE_ORDER));

		return beanlist;
	}




	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseObject addMeasurement(UserBean fetchLoggedInUserDetails, MeasurementDto dto, String ip) {

		ResponseObject object = new ResponseObject();
		String uploadedFilePath = null;

		try {

			Measurement measurement = null;

			// ---------- Edit / New ----------
			if (dto.getMeasurementId() != null) {
				measurement = measurementRepository.findOne(dto.getMeasurementId());
			}
			if (measurement == null) {
				measurement = new Measurement();
			}

			// ---------- Work EMB ----------
			if (dto.getWorkId() != null) {
				workEmb byWorkId = workEmbRepository.findByWorkId(dto.getWorkId());
				if (byWorkId != null && byWorkId.getEmbNo() != null) {
					measurement.seteMbNo(byWorkId.getEmbNo().toString());
				}
				measurement.setWorkId(dto.getWorkId());
			}
			if (dto.getEstimateSorId() != null) {
				measurement.setEstimateSorId(dto.getEstimateSorId());
			}
			if (dto.getCalculatedAmount() != null) {
				measurement.setCalculatedAmount(dto.getCalculatedAmount());

			}

			if (dto.getPlaceDescription() != null) {
				measurement.setPlaceDescription(dto.getPlaceDescription());
			}

			measurement.setIpAddress(ip);
			if (dto.getSorItemNo() != null) {
				measurement.setSorItemNo(dto.getSorItemNo());
			}

			if (dto.getUnit() != null) {
				measurement.setUnit(dto.getUnit());
			}

			if (dto.getItemDesc() != null) {
				measurement.setItemDesc(dto.getItemDesc());
			}

			// ---------- BigDecimal fields ----------
			if (dto.getQuantity() != null) {
				measurement.setQuantity(new BigDecimal(dto.getQuantity()));
			}

			if (dto.getLength() != null) {
				measurement.setLength(new BigDecimal(dto.getLength()));
			}

			if (dto.getWidth() != null) {
				measurement.setWidth(new BigDecimal(dto.getWidth()));
			}

			if (dto.getHeightDepth() != null && !dto.getHeightDepth().trim().isEmpty()
					&& !"null".equalsIgnoreCase(dto.getHeightDepth().trim())) {

				measurement.setHeightDepth(new BigDecimal(dto.getHeightDepth().trim()));
			}

			// ---------- Previous Measurement ----------
			if (dto.getPreviousMeasurementL() != null) {
				measurement.setPreviousMeasurementL(dto.getPreviousMeasurementL());
			}
			if (dto.getPreviousMeasurementW() != null) {
				measurement.setPreviousMeasurementW(dto.getPreviousMeasurementW());
			}
			if (dto.getPreviousMeasurementT_T() != null) {
				measurement.setPreviousMeasurementT_T(dto.getPreviousMeasurementT_T());
			}
			if (dto.getPreviousMeasurementNo() != null) {
				measurement.setPreviousMeasurementNo(dto.getPreviousMeasurementNo());
			}
			if (dto.getCurrentMesurementNo() != null) {
				measurement.setCurrentMesurementNo(dto.getCurrentMesurementNo());
			}

			if (dto.getRemainingMeasurementNo() != null) {
				measurement.setRemainingMeasurementNo(dto.getRemainingMeasurementNo());
			}

			// ---------- Remaining Measurement ----------
			if (dto.getRemainingMeasurementL() != null) {
				measurement.setRemainingMeasurementL(dto.getRemainingMeasurementL());
			}
			if (dto.getRemainingMeasurementW() != null) {
				measurement.setRemainingMeasurementW(dto.getRemainingMeasurementW());
			}
			if (dto.getRemainingMeasurementT() != null) {
				measurement.setRemainingMeasurementT(dto.getRemainingMeasurementT());
			}

			// ---------- Current Measurement ----------
			if (dto.getCurrentMesurementL() != null && dto.getCurrentMesurementL() != 0) {
				measurement.setCurrentMesurementL(dto.getCurrentMesurementL());
			}
			if (dto.getCurrentMesurementW() != null && dto.getCurrentMesurementW() != 0) {
				measurement.setCurrentMesurementW(dto.getCurrentMesurementW());
			}
			if (dto.getCurrentMesurementT() != null && dto.getCurrentMesurementT() != 0) {
				measurement.setCurrentMesurementT(dto.getCurrentMesurementT());
			}

			if (dto.getRemarks() != null) {
				measurement.setRemarks(dto.getRemarks());
			}
			measurement.getAmoountMachinery();
			// ---------- Save Measurement ----------
			Measurement savedMeasurement = measurementRepository.save(measurement);

			// ---------- File Upload (Optional) ----------
			if (dto.getDocumentUpload() != null && !dto.getDocumentUpload().isEmpty()) {

				MultipartFile file = dto.getDocumentUpload();
				String geteMbNo = measurement.geteMbNo();
				geteMbNo = geteMbNo.replace("/", "");

				uploadedFilePath = saveFile(file, geteMbNo, savedMeasurement.getId());

				DocumentUpload documentUpload = new DocumentUpload();
				documentUpload.setDocumentName(file.getOriginalFilename());
				documentUpload.setDocumentDesc("Measurement File For Emb " + dto.geteMbNo());
				documentUpload.setDocumentUploadPath(uploadedFilePath);

				DocumentUpload savedDoc = documentRepository.save(documentUpload);

				if (savedDoc != null) {
					savedMeasurement.setDocumentId(savedDoc.getDocumentId());
					measurementRepository.save(savedMeasurement);
				}
			}

			object.setSuccessMessage("Saved successfully");

		} catch (Exception e) {

			// ---------- Manual rollback for file ----------
			if (uploadedFilePath != null) {
				try {
					Files.deleteIfExists(Paths.get(uploadedFilePath));
				} catch (Exception ex) {
					logger.error("Failed to delete file after rollback", ex);
				}
			}

			logger.error("Transaction failed", e);
			object.setErrorMessage("Failed to save measurement");
		}

		return object;
	}


	private String saveFile(MultipartFile file, String embNo, Long measurementId) throws Exception {

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
		String strDate = formatter.format(date);

		String documentsPath = documentRootPath + "EMB/";
		File dir = new File(documentsPath);

		if (!dir.exists()) {
			dir.mkdirs();
		}

		String fileExtension = "pdf";
		if (file.getOriginalFilename() != null) {
			String[] fileArr = file.getOriginalFilename().split("\\.");
			fileExtension = fileArr[fileArr.length - 1];
		}

		String fileName = "MeasurementDocument_" + strDate + "_" + embNo + "_" + measurementId + "." + fileExtension;

		File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);

		try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile))) {

			stream.write(file.getBytes());
		}

		return serverFile.getAbsolutePath();
	}

	@Override
	public List<Double> loadPreviousDataByEstimateId(UserBean user, Long estimateSorId) {
		List<Double> list = new ArrayList<>();

		try {

			List<Measurement> list2 = measurementRepository.findByEstimateSorIdOrderByIdDesc(estimateSorId);
			Double l = 0.0D;
			Double w = 0.0D;
			Double t = 0.0D;
			Double no = 0.0D;
			if (list2 != null && !list2.isEmpty()) {

				for (Measurement measurement : list2) {
					if (measurement.getCurrentMesurementL() != null) {
						l += measurement.getCurrentMesurementL();
					} else {
						measurement.setCurrentMesurementL(0.0D);
						l += measurement.getCurrentMesurementL();
					}
					if (measurement.getCurrentMesurementW() != null) {
						w += measurement.getCurrentMesurementW();
					} else {
						measurement.setCurrentMesurementW(0.0D);
						w += measurement.getCurrentMesurementW();
					}
					if (measurement.getCurrentMesurementT() != null) {
						t += measurement.getCurrentMesurementT();
					} else {
						measurement.setCurrentMesurementT(0.0D);
						t += measurement.getCurrentMesurementT();
					}

					if (measurement.getCurrentMesurementNo() != null) {
						no += measurement.getCurrentMesurementNo();
					} else {
						measurement.setCurrentMesurementNo(0.0D);
						no += measurement.getCurrentMesurementNo();
					}

				}

				list.add(l);
				list.add(w);
				list.add(t);
				list.add(no);
			} else {
				list.add(0.0D);
				list.add(0.0D);
				list.add(0.0D);
				list.add(0.0D);
			}

			return list;
		} catch (Exception e) {
			logger.info("some error Occured In Fetching Data", e);
			return list;
		}

	}

	@Override
	public List<Double> loadPreviousDataByEstimateIdInEdit(UserBean fetchLoggedInUserDetails, Long estimateSorId,
			Long mId) {
		List<Double> list = new ArrayList<>();

		try {

			List<Measurement> list2 = measurementRepository.findByEstimateSorIdOrderByIdDesc(estimateSorId);
			Double l = 0.0D;
			Double w = 0.0D;
			Double t = 0.0D;
			Double no = 0.0D;

			if (list2 != null && !list2.isEmpty()) {

				for (Measurement measurement : list2) {
					if (mId == measurement.getId()) {

					} else {
						if (measurement.getCurrentMesurementL() != null) {
							l += measurement.getCurrentMesurementL();
						} else {
							measurement.setCurrentMesurementL(0.0D);
							l += measurement.getCurrentMesurementL();
						}
						if (measurement.getCurrentMesurementW() != null) {
							w += measurement.getCurrentMesurementW();
						} else {
							measurement.setCurrentMesurementW(0.0D);
							w += measurement.getCurrentMesurementW();
						}
						if (measurement.getCurrentMesurementT() != null) {
							t += measurement.getCurrentMesurementT();
						} else {
							measurement.setCurrentMesurementT(0.0D);
							t += measurement.getCurrentMesurementT();
						}
						if (measurement.getCurrentMesurementNo() != null) {
							no += measurement.getCurrentMesurementNo();
						} else {
							measurement.setCurrentMesurementNo(0.0D);
							no += measurement.getCurrentMesurementNo();
						}
					}

				}

				list.add(l);
				list.add(w);
				list.add(t);
				list.add(no);
			} else {
				list.add(0.0D);
				list.add(0.0D);
				list.add(0.0D);
				list.add(0.0D);
			}

			return list;
		} catch (Exception e) {
			logger.info("some error Occured In Fetching Data", e);
			return list;
		}

	}


@Override
public List<MeasurementDto> LoadAllMeasurementListByEstimateSorId(UserBean fetchLoggedInUserDetails,
		Long estimateSorId) {
	List<MeasurementDto> beanist = new LinkedList<>();

	try {
		
		
		List<Measurement> list2 = measurementRepository.findByEstimateSorIdOrderByIdDesc(estimateSorId);

		Double quanti  = 0.0D;
		Double amounttotal = 0.0D;
		
		if (list2 != null && !list2.isEmpty()) {
			for (Measurement m : list2) {
				MeasurementDto bean = new MeasurementDto();

				bean.setSorItemNo(m.getSorItemNo());
				bean.setEstimateSorId(m.getEstimateSorId());
				bean.setDocId(m.getDocumentId());
				bean.seteMbNo(m.geteMbNo());
				bean.setIpAddress(m.getIpAddress());
				bean.setValuationDate(m.getModifiedDate() + "");
				if (m.getModifiedBy() != null) {
					bean.setMeasuredBy(userRepository.findOne(Long.parseLong(m.getModifiedBy())).getUsername());
				}
				bean.setUnit(m.getUnit());
				BigDecimal qty = new BigDecimal(m.getQuantity()+"");
				qty = qty.setScale(2, RoundingMode.HALF_UP);

				bean.setQuantity(qty.toString());
				WorkEstimationItems one = workEstimationItemsRepository.findOne(Integer.valueOf(m.getEstimateSorId()+""));
				BigDecimal amt = new BigDecimal(one.getAmount()+"");
				amt = amt.setScale(2, RoundingMode.HALF_UP);

				bean.setAmount(amt.toString());
				bean.setRate(one.getRate() + "");
				bean.setItemDesc(one.getItemDesc());
				bean.setEstimateSorId(estimateSorId);
				
				if(m.getCalculatedAmount()!=null) {
					bean.setCalculatedAmount(m.getCalculatedAmount());
					
				
					
				}
				
				
				if(m.getPlaceDescription()!=null) {
					bean.setPlaceDescription(m.getPlaceDescription());
				}
				if(m.getCalculatedAmount()!=null) {
					
					BigDecimal amt2 = new BigDecimal(m.getCalculatedAmount()+"");
					amt2 = amt2.setScale(2, RoundingMode.HALF_UP);

					//bean.setAmount(amt.toString());
					bean.setCalculatedAmount(amt2+"");
				}
				
				if(m.getPlaceDescription()!=null) {
					bean.setPlaceDescription(m.getPlaceDescription());
				}
				Double l = m.getCurrentMesurementL() != null ? m.getCurrentMesurementL() : 1d;
				Double t = m.getCurrentMesurementT() != null ? m.getCurrentMesurementT() : 1d;
				Double w = m.getCurrentMesurementW() != null ? m.getCurrentMesurementW() : 1d;

				Double multi = l * t * w;

				bean.setRemarks(m.getRemarks());
				bean.setNo(m.getNo()+"");
				bean.setLength(m.getLength() + "");
				bean.setWidth(m.getWidth() + "");
				bean.setHeightDepth(m.getHeightDepth() + "");
				bean.setRemainingMeasurementL(m.getRemainingMeasurementL());
				bean.setPreviousMeasurementL(m.getPreviousMeasurementL());
				bean.setRemainingMeasurementW(m.getRemainingMeasurementW());
				bean.setPreviousMeasurementW(m.getPreviousMeasurementW());
				bean.setRemainingMeasurementT(m.getRemainingMeasurementT());
				bean.setPreviousMeasurementT_T(m.getPreviousMeasurementT_T());
				if(m.getDscStatus() == (short)1) {
					bean.setDscStatus(false);
				}else {
					bean.setDscStatus(true);
				}
				
				
				if(m.getCurrentMesurementL() == null) {
					bean.setCurrentMesurementL(0.0D);
				}else {
					bean.setCurrentMesurementL(m.getCurrentMesurementL());
				}
				if(m.getCurrentMesurementW() == null) {
					bean.setCurrentMesurementW(0.0D);
				}else {
					bean.setCurrentMesurementW(m.getCurrentMesurementW());
				}
				if(m.getCurrentMesurementT() == null) {
					bean.setCurrentMesurementT(0.0D);
				}else {
					bean.setCurrentMesurementT(m.getCurrentMesurementT());
				}
				
				
				bean.setQuantity(multi+"");
				quanti +=  Double.parseDouble(bean.getQuantity());
				amounttotal += (bean.getCalculatedAmount() == null || bean.getCalculatedAmount().isEmpty())
				        ? 0d
				        : Double.parseDouble(bean.getCalculatedAmount());

				bean.setMeasurementId(m.getId());
				beanist.add(bean);
			}
			String formatted = new BigDecimal(amounttotal.toString())
			        .setScale(2, RoundingMode.HALF_UP)
			        .toString();
			String formatted2 = new BigDecimal(quanti.toString())
			        .setScale(2, RoundingMode.HALF_UP)
			        .toString();

			beanist.get(0).setAmountTotal(formatted);
			beanist.get(0).setQuantityTotal(formatted2);
		}

	} catch (Exception e) {
		logger.info("Error", e);
	}

	return beanist;
}








@Override
public ResponseObject  VerifyTheMesurementWithDSC(com.res.bean.DscSaveRequest req) {
	ResponseObject object = new ResponseObject();
	
	
	try {
		
		short a = 0;
		List<Measurement> list = measurementRepository.findByWorkIdAndDscStatus(req.getWorkId(),a);
		for(Measurement measuremnt : list ) {
			measuremnt.setDscStatus((short)1);
			measuremnt.setDscString(req.getSignedData());
			measurementRepository.save(measuremnt);
		}
		object.setSuccessMessage("Data Signed With DSC");
		
	} catch (Exception e) {
		logger.info("Some Error Occured During Saving Data",e);
		object.setErrorMessage("Data Cannot be Signed With DSC");
	}
	
	
	return object;
	
}






}
