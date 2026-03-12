package com.res.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.res.bean.BlockBean;
import com.res.bean.DscSaveRequest;
import com.res.bean.EmbDto;
import com.res.bean.GramPanchayatBean;
import com.res.bean.KmlFilePoints;
import com.res.bean.MeasurementDto;
import com.res.bean.UserBean;
import com.res.bean.VillageBean;
import com.res.bean.WorkBean;
import com.res.bean.chapterDesciptionDto;
import com.res.entity.Office;
import com.res.entity.Users;
import com.res.json.WorkAgreementJson;
import com.res.json.WorkJson;
import com.res.response.ResponseObject;


public interface EeService {

	String addWork(WorkBean workBean);

//	WorkJson getAllWorks(Pageable pageable);
	
	String deleteWork(Long id);
	
	//WorkBean fetchWorkDetails(Long id);

	/** CR-RESOWMS/CR/1-1
	 * Work Transfer Module-Transfer Work to Other Office
	 * @param workBean
	 * @return String
	 */
	String editWork(WorkBean workBean);
	
	/*String editWorkRevise(WorkBean workBean);*/
	
	String addRequisitionWork(WorkBean workBean);
	
	String editRequisitionWork(WorkBean workBean);

	List<KmlFilePoints> processKmlFile(KmlFilePoints bean);

	VillageBean fetchVillageByVCode(Long long1);

	List<GramPanchayatBean> fetchGramPanchayatByGPCode(Long gpCode);

	GramPanchayatBean fetchLgdGpCode(Long gpId);

	BlockBean fetchblockCode(Long blockId);

    ResponseObject IssueEmbNumber(EmbDto dto, UserBean fetchLoggedInUserDetails);

	WorkAgreementJson fetchWorkListForEmb(Pageable pageable, String searchBoxVal, String role, String username);

	List<Users> getAllEngineerOfficer(Long id);

	List<Office> getOfficeOfTSPerson(Long id, Long workId);

    WorkJson fetchWorkWithEmbIssuedForEnginner(UserBean user, Pageable pageable, String searchBoxVal,
			String ProjectId, String blockId, String grampanchayatId, String villageId);

	List<chapterDesciptionDto> loadItemChapterWise(String workId, UserBean fetchLoggedInUserDetails);

    List<Double> loadPreviousDataByEstimateIdInEdit(UserBean fetchLoggedInUserDetails, Long estimateSorId, Long mId);

    ResponseObject addMeasurement(UserBean fetchLoggedInUserDetails, MeasurementDto dto, String clientIp);

    List<Double> loadPreviousDataByEstimateId(UserBean fetchLoggedInUserDetails, Long estimateSorId);

	List<MeasurementDto> LoadAllMeasurementListByEstimateSorId(UserBean fetchLoggedInUserDetails, Long estimateSorId);

	ResponseObject VerifyTheMesurementWithDSC(DscSaveRequest req);


	
}
