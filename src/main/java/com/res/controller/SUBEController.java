package com.res.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.res.bean.DscSaveRequest;
import com.res.bean.KmlFilePoints;
import com.res.bean.LatLngForestResponse;
import com.res.bean.MeasurementDto;
import com.res.bean.UserBean;
import com.res.bean.WorkBean;
import com.res.bean.chapterDesciptionDto;
import com.res.constants.RESConstants;
import com.res.entity.Users;
import com.res.json.WorkJson;
import com.res.repository.MeasurementRepository;
import com.res.response.ResponseObject;
import com.res.service.AdminService;
import com.res.service.CommonService;
import com.res.service.EeService;
import com.res.service.UserService;
import com.res.util.RESUtil;


@RestController
@RequestMapping("/sube/*")
public class SUBEController extends BaseController {

	public static final Logger logger = LoggerFactory.getLogger(SUBEController.class);

	private User user;

	@Autowired
	private UserService userService;

	@Autowired
	private AdminService adminService;

	@Autowired
	private EeService eeService;

	@Autowired
	private CommonService commonService;

	@Autowired
	private MeasurementRepository measurementRepository;

	RestTemplate restTemplate = new RestTemplate();

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView viewHome(HttpServletRequest request, Model model) {

		Locale locale = (Locale) request.getSession().getAttribute(
				SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);

		String language = request.getParameter("lang");

		if (null == locale) {
			request.getSession().setAttribute(
					SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,
					new Locale("en"));
		} else if (!StringUtils.isEmpty(language)
				&& language.equals(RESConstants.LOCALE_HI)) {

			request.getSession().setAttribute(
					SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,
					new Locale("hi"));
		} else if (!StringUtils.isEmpty(language)
				&& language.equals(RESConstants.LOCALE_EN)) {
			request.getSession().setAttribute(
					SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,
					new Locale("en"));
		}

		Locale updatedLocale = (Locale) request.getSession().getAttribute(
				SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
		model.addAttribute("language", updatedLocale.toString());

		user = RESUtil.getUserDetail();
		ModelAndView modelAndView = new ModelAndView("sube/subeHome");

		if (user != null) {
			logger.info(
					"User - " + user.getUsername() + ", Role - " + user.getAuthorities() + " - Displaying home page");
			// Users userEntity = userService.findByUserName(user.getUsername());
			modelAndView.addObject("loggedInUserName", user.getUsername());

			/*
			 * HttpSession httpSession = request.getSession(false);
			 * 
			 * String roleName =
			 * (String)httpSession.getAttribute(RESConstants.LOGGED_IN_USER_ROLE);
			 */
			/*
			 * Set<Role> roles = userEntity.getRoles();
			 * if(roles!= null && !roles.isEmpty()){
			 * List<Role> roleList = new ArrayList<Role>(roles);
			 * roleName = roleList.get(0).getRoleName();
			 * }
			 */
			UserBean userBean = fetchLoggedInUserDetails(request);
			modelAndView.addObject("roleName", userBean.getLoggedInUserRole());
			modelAndView.addObject("isOIC", userBean.getIsOICString());
		}
		return modelAndView;
	}

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView viewDashboard(HttpServletRequest request) {

		user = RESUtil.getUserDetail();
		logger.info("User - " + user.getUsername() + ", Role - " + user.getAuthorities() + " - Displaying dashboard");
		ModelAndView modelAndView = new ModelAndView("sube/dashboard");
		return modelAndView;

	}

	/*
	 * @RequestMapping(value = "/fetchRatesList", method = RequestMethod.GET,
	 * produces = "application/json;charset=UTF-8")
	 * public String fetchRatesList(HttpServletRequest request) {
	 * 
	 * user = RESUtil.getUserDetail();
	 * logger.info("User - " + user.getUsername() + ", Role - " +
	 * user.getAuthorities() + " - Fetching Rates List");
	 * String sSortCol = request.getParameter("iSortCol_0");
	 * String sSortDir = request.getParameter("sSortDir_0");
	 * String sColName = request.getParameter("mDataProp_" + sSortCol);
	 * 
	 * // Fetch the page number from client
	 * Integer pageNumber = 0;
	 * 
	 * // Fetch search parameter
	 * String searchParameter = request.getParameter("sSearch");
	 * 
	 * // Fetch Page display length
	 * Integer pageDisplayLength = Integer.valueOf(request
	 * .getParameter("iDisplayLength"));
	 * 
	 * if (null != request.getParameter("iDisplayStart")) {
	 * pageNumber = (Integer
	 * .valueOf(request.getParameter("iDisplayStart")) / pageDisplayLength);
	 * }
	 * 
	 * Sort sort = null;
	 * if(sColName!=null){
	 * if(StringUtils.equals("asc", sSortDir)){
	 * sort = new Sort(new Sort.Order(Direction.ASC, sColName));
	 * }else{
	 * sort = new Sort(new Sort.Order(Direction.DESC, sColName));
	 * }
	 * }else{
	 * sort = new Sort(new Sort.Order(Direction.ASC, "effectiveFrom"));//default
	 * sorting
	 * }
	 * 
	 * Pageable pageable = new PageRequest(pageNumber, pageDisplayLength, sort);
	 * 
	 * RatesJson ratesJson = adminService.getAllRates(pageable, searchParameter);
	 * 
	 * Gson gson = new GsonBuilder().setPrettyPrinting().create();
	 * String json = gson.toJson(ratesJson);
	 * 
	 * return json;
	 * }
	 * 
	 * @RequestMapping(value = "/addRatesForm", method = RequestMethod.GET)
	 * public ModelAndView viewAddRatesForm(HttpServletRequest request) {
	 * 
	 * user = RESUtil.getUserDetail();
	 * logger.info("User - " + user.getUsername() + ", Role - " +
	 * user.getAuthorities() + " - Displaying Add Rates Form");
	 * ModelAndView modelAndView = new ModelAndView("admin/addRatesForm");
	 * return modelAndView;
	 * }
	 * 
	 * @RequestMapping(value = "/addRates", method = RequestMethod.POST)
	 * public ResponseObject addRates(@RequestBody RatesBean ratesBean,
	 * HttpServletRequest request) throws Exception {
	 * 
	 * user = RESUtil.getUserDetail();
	 * logger.info("User - " + user.getUsername() + ", Role - " +
	 * user.getAuthorities() + " - Adding Rates data");
	 * ResponseObject response = new ResponseObject();
	 * 
	 * String errorMsg = adminService.addRates(ratesBean);
	 * if(errorMsg!=null){
	 * response.setErrorMessage(errorMsg);
	 * logger.error("User - " + user.getUsername() + ", Role - " +
	 * user.getAuthorities() + " - " + errorMsg);
	 * }else{
	 * response.setSuccessMessage("Rates added successfully!");
	 * logger.info("User - " + user.getUsername() + ", Role - " +
	 * user.getAuthorities() + " - Rates added successfully!");
	 * }
	 * return response;
	 * }
	 * 
	 * @RequestMapping(value = "/editRatesForm/{id}", method = RequestMethod.GET)
	 * public ModelAndView viewEditRatesForm(
	 * 
	 * @PathVariable String id, HttpServletRequest request) {
	 * 
	 * user = RESUtil.getUserDetail();
	 * logger.info("User - " + user.getUsername() + ", Role - " +
	 * user.getAuthorities() + " - Displaying Edit Rates Form");
	 * ModelAndView modelAndView = new ModelAndView("admin/editRatesForm");
	 * return modelAndView;
	 * }
	 * 
	 * @RequestMapping(value = "fetchRatesDetails/{id}", method = RequestMethod.GET)
	 * public RatesBean fetchRatesDetails(@PathVariable Long id,
	 * HttpServletRequest request) {
	 * 
	 * user = RESUtil.getUserDetail();
	 * logger.info("User - " + user.getUsername() + ", Role - " +
	 * user.getAuthorities() + " - Fetching Rates data");
	 * return adminService.fetchRatesDetails(id);
	 * }
	 * 
	 * @RequestMapping(value = "/editRates", method = RequestMethod.POST)
	 * public ResponseObject editRates(@RequestBody RatesBean ratesBean,
	 * HttpServletRequest request) throws Exception {
	 * 
	 * user = RESUtil.getUserDetail();
	 * logger.info("User - " + user.getUsername() + ", Role - " +
	 * user.getAuthorities() + " - Updating Rates data");
	 * ResponseObject response = new ResponseObject();
	 * 
	 * String errorMsg = adminService.editRates(ratesBean);
	 * 
	 * if(errorMsg!=null){
	 * response.setErrorMessage(errorMsg);
	 * logger.error("User - " + user.getUsername() + ", Role - " +
	 * user.getAuthorities() + " - " + errorMsg);
	 * }else{
	 * response.setSuccessMessage("Rates updated successfully!");
	 * logger.info("User - " + user.getUsername() + ", Role - " +
	 * user.getAuthorities() + " - Rates updated successfully!");
	 * }
	 * return response;
	 * }
	 */

	/*
	 * @RequestMapping(value = "/deleteRates/{id}", method = RequestMethod.GET)
	 * public ResponseObject deleteRates(
	 * 
	 * @PathVariable Long id, HttpServletRequest request) {
	 * 
	 * user = RESUtil.getUserDetail();
	 * logger.info("User - " + user.getUsername() + ", Role - " +
	 * user.getAuthorities() + " - Deleting Rates");
	 * ResponseObject response = new ResponseObject();
	 * 
	 * String errorMsg = adminService.deleteRates(id);
	 * 
	 * if(errorMsg!=null){
	 * response.setErrorMessage(errorMsg);
	 * logger.error("User - " + user.getUsername() + ", Role - " +
	 * user.getAuthorities() + " - " + errorMsg);
	 * }else{
	 * response.setSuccessMessage("Rates deleted successfully!");
	 * logger.info("User - " + user.getUsername() + ", Role - " +
	 * user.getAuthorities() + " - Rates deleted successfully!");
	 * }
	 * return response;
	 * }
	 */

	@RequestMapping(value = "/addLegacyDataMapping", method = RequestMethod.GET)
	public ModelAndView addLegacyDataMapping(HttpServletRequest request) {
		user = RESUtil.getUserDetail();
		logger.info("User - " + user.getUsername() + ", Role - " + user.getAuthorities()
				+ " - Displaying Add Legacy Data Form");

		ModelAndView modelAndView = new ModelAndView("ee/addLegacyDataForm");
		Users userEntity = userService.findByUserName(user.getUsername());
		modelAndView.addObject("officeId", userEntity.getOffice().getId());
		modelAndView.addObject("officeName", userEntity.getOffice().getOfficeName());
		return modelAndView;
	}

	/*
	 * @RequestMapping(value = "/manageLegacyDataMapping", method =
	 * RequestMethod.GET)
	 * public ModelAndView manageLegacyDataMapping(HttpServletRequest request) {
	 * 
	 * user = RESUtil.getUserDetail();
	 * boolean hasRole = false;
	 * for (GrantedAuthority authority : user.getAuthorities()) {
	 * hasRole = authority.getAuthority().equals(RESConstants.ROLE_EE);
	 * if (hasRole) {
	 * ModelAndView modelAndView = new ModelAndView(
	 * "ee/manageLegacyData");
	 * }
	 * hasRole = authority.getAuthority().equals(RESConstants.ROLE_ADMIN);
	 * if (hasRole) {
	 * ModelAndView modelAndView = new ModelAndView(
	 * "ee/manageLegacyData");
	 * }
	 * }
	 * HttpSession httpSession = request.getSession(false);
	 * String role =
	 * (String)httpSession.getAttribute(RESConstants.LOGGED_IN_USER_ROLE);
	 * ModelAndView modelAndView = null;
	 * 
	 * if(role.equals(RESConstants.ROLE_EE)){
	 * logger.info("User - " + user.getUsername() + ", Role - " +
	 * user.getAuthorities() + " - Displaying Manage Legacy Data page");
	 * modelAndView = new ModelAndView(
	 * "ee/manageLegacyData");
	 * }
	 * 
	 * if(role.equals(RESConstants.ROLE_ADMIN)){
	 * logger.info("User - " + user.getUsername() + ", Role - " +
	 * user.getAuthorities() + " - Displaying WorkWise Report");
	 * modelAndView = new ModelAndView(
	 * "common/viewWorkReport");
	 * }
	 * return modelAndView;
	 * }
	 */

	/*
	 * @RequestMapping(value = "/addWork", method = RequestMethod.POST)
	 * 
	 * @ResponseBody
	 * public ResponseObject addWork(
	 * WorkBean workBean,
	 * HttpServletRequest request) throws Exception {
	 * 
	 * String remoteIpAddr = request.getHeader("X-Forwarded-For");
	 * if (remoteIpAddr == null || "".equals(remoteIpAddr)) {
	 * remoteIpAddr = request.getRemoteAddr();
	 * }
	 * workBean.setClientIp(remoteIpAddr);
	 * 
	 * user = RESUtil.getUserDetail();
	 * logger.info("User - " + user.getUsername() + ", Role - " +
	 * user.getAuthorities() + " - Adding Legacy data");
	 * ResponseObject response = new ResponseObject();
	 * 
	 * String errorMsg = eeService.addWork(workBean);
	 * if(errorMsg!=null){
	 * response.setErrorMessage(errorMsg);
	 * logger.error("User - " + user.getUsername() + ", Role - " +
	 * user.getAuthorities() + " - " + errorMsg);
	 * }else{
	 * response.setSuccessMessage("Work added successfully!");
	 * logger.info("User - " + user.getUsername() + ", Role - " +
	 * user.getAuthorities() + " - Work added successfully!");
	 * }
	 * return response;
	 * }
	 */

	@RequestMapping(value = "/deleteWork/{id}", method = RequestMethod.GET)
	public ResponseObject deleteWork(
			@PathVariable Long id, HttpServletRequest request) {

		user = RESUtil.getUserDetail();
		logger.info("User - " + user.getUsername() + ", Role - " + user.getAuthorities() + " - Deleting Work");
		ResponseObject response = new ResponseObject();

		String errorMsg = eeService.deleteWork(id);

		if (errorMsg != null) {
			response.setErrorMessage(errorMsg);
			logger.error("User - " + user.getUsername() + ", Role - " + user.getAuthorities() + " - " + errorMsg);
		} else {
			response.setSuccessMessage("Work deleted successfully!");
			logger.info("User - " + user.getUsername() + ", Role - " + user.getAuthorities()
					+ " - Work deleted successfully!");
		}
		return response;
	}

	@RequestMapping(value = "/editLegacyDataMapping/{id}", method = RequestMethod.GET)
	public ModelAndView viewEditUserForm(
			@PathVariable String id, HttpServletRequest request) {

		user = RESUtil.getUserDetail();
		logger.info("User - " + user.getUsername() + ", Role - " + user.getAuthorities()
				+ " - Displaying Edit Legacy Form");
		ModelAndView modelAndView = new ModelAndView("ee/editLegacyDataForm");
		return modelAndView;
	}

	/*
	 * @RequestMapping(value = "fetchWorkDetails/{id}", method = RequestMethod.GET)
	 * public WorkBean fetchWorkDetails(@PathVariable Long id,
	 * HttpServletRequest request) {
	 * 
	 * user = RESUtil.getUserDetail();
	 * logger.info("User - " + user.getUsername() + ", Role - " +
	 * user.getAuthorities() + " - Fetching Work data");
	 * return eeService.fetchWorkDetails(id);
	 * }
	 */

	/*
	 * @RequestMapping(value = "/editWork", method = RequestMethod.POST)
	 * public ResponseObject editWork(@RequestBody WorkBean workBean,
	 * HttpServletRequest request) throws Exception {
	 * 
	 * String remoteIpAddr = request.getHeader("X-Forwarded-For");
	 * if (remoteIpAddr == null || "".equals(remoteIpAddr)) {
	 * remoteIpAddr = request.getRemoteAddr();
	 * }
	 * workBean.setClientIp(remoteIpAddr);
	 * 
	 * 
	 * user = RESUtil.getUserDetail();
	 * logger.info("User - " + user.getUsername() + ", Role - " +
	 * user.getAuthorities() + " - Updating Work data");
	 * ResponseObject response = new ResponseObject();
	 * 
	 * String errorMsg = eeService.editWork(workBean);
	 * 
	 * if(errorMsg!=null){
	 * response.setErrorMessage(errorMsg);
	 * logger.error("User - " + user.getUsername() + ", Role - " +
	 * user.getAuthorities() + " - " + errorMsg);
	 * }else{
	 * response.setSuccessMessage("Work updated successfully!");
	 * logger.info("User - " + user.getUsername() + ", Role - " +
	 * user.getAuthorities() + " - Work updated successfully!");
	 * }
	 * return response;
	 * }
	 */

	@RequestMapping(value = "/editWork", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject editWork(WorkBean workBean,
			HttpServletRequest request) throws Exception {

		String remoteIpAddr = request.getHeader("X-Forwarded-For");
		if (remoteIpAddr == null || "".equals(remoteIpAddr)) {
			remoteIpAddr = request.getRemoteAddr();
		}
		workBean.setClientIp(remoteIpAddr);

		user = RESUtil.getUserDetail();
		logger.info("User - " + user.getUsername() + ", Role - " + user.getAuthorities() + " - Updating Work data");
		ResponseObject response = new ResponseObject();

		String errorMsg = eeService.editWork(workBean);

		if (errorMsg != null) {
			response.setErrorMessage(errorMsg);
			logger.error("User - " + user.getUsername() + ", Role - " + user.getAuthorities() + " - " + errorMsg);
		} else {
			response.setSuccessMessage("Work updated successfully!");
			logger.info("User - " + user.getUsername() + ", Role - " + user.getAuthorities()
					+ " - Work updated successfully!");
		}
		return response;
	}

	/*
	 * @RequestMapping(value = "/viewEeReportsMapping", method = RequestMethod.GET)
	 * public ModelAndView viewEeReports(HttpServletRequest request) {
	 * user = RESUtil.getUserDetail();
	 * logger.info("User - " + user.getUsername() + ", Role - " +
	 * user.getAuthorities() + " - Displaying Executive Offices Report of Work");
	 * ModelAndView modelAndView = new ModelAndView(
	 * "common/viewExecutiveWorkReport");
	 * return modelAndView;
	 * }
	 */

	/*
	 * @RequestMapping(value = "/fetchExecutiveOfficeWorkReport", method =
	 * RequestMethod.GET, produces = "application/json;charset=UTF-8")
	 * public String fetchExecutiveOfficeWorkReport(HttpServletRequest request) {
	 * 
	 * user = RESUtil.getUserDetail();
	 * logger.info("User - " + user.getUsername() + ", Role - " +
	 * user.getAuthorities() + " - Fetching Executive Office Work Report");
	 * 
	 * String searchBoxVal = request.getParameter("searchBoxVal");
	 * String designation = request.getParameter("designation");
	 * String status = request.getParameter("status");
	 * 
	 * String sSortCol = request.getParameter("iSortCol_0");
	 * String sSortDir = request.getParameter("sSortDir_0");
	 * String sColName = request.getParameter("mDataProp_" + sSortCol);
	 * 
	 * // Fetch the page number from client
	 * Integer pageNumber = 0;
	 * 
	 * // Fetch search parameter
	 * //String searchParameter = request.getParameter("sSearch");
	 * 
	 * // Fetch Page display length
	 * Integer pageDisplayLength = Integer.valueOf(request
	 * .getParameter("iDisplayLength"));
	 * if (null != request.getParameter("iDisplayStart")) {
	 * pageNumber = (Integer
	 * .valueOf(request.getParameter("iDisplayStart")) / pageDisplayLength);
	 * }
	 * Sort sort = null;
	 * if(sColName!=null){
	 * if(StringUtils.equals("asc", sSortDir)){
	 * sort = new Sort(new Sort.Order(Direction.ASC, sColName));
	 * }else{
	 * sort = new Sort(new Sort.Order(Direction.DESC, sColName));
	 * }
	 * }else{
	 * sort = new Sort(new Sort.Order(Direction.DESC, "id"));//default sorting
	 * }
	 * Pageable pageable = new PageRequest(pageNumber, pageDisplayLength, sort);
	 * 
	 * // UserJson userJson = userService.getAllUsers(pageable, searchBoxVal,
	 * designation, status);
	 * 
	 * ExecutiveWorkJson workJson = eeService.getAllExecutiveWorks(pageable);
	 * Gson gson = new GsonBuilder().setPrettyPrinting().create();
	 * String json = gson.toJson(workJson);
	 * return json;
	 * }
	 */

	@RequestMapping(value = "/addWorkRequisitionDataMapping", method = RequestMethod.GET)
	public ModelAndView addWorkRequisitionDataMapping(HttpServletRequest request) {
		user = RESUtil.getUserDetail();
		logger.info("User - " + user.getUsername() + ", Role - " + user.getAuthorities()
				+ " - Displaying Add Work Requisition Data Form");

		ModelAndView modelAndView = new ModelAndView("sube/addWorkRequisitionDataForm");
		Users userEntity = userService.findByUserName(user.getUsername());
		modelAndView.addObject("officeId", userEntity.getOffice().getId());
		modelAndView.addObject("officeName", userEntity.getOffice().getOfficeName());
		return modelAndView;
	}

	@RequestMapping(value = "/addRequisitionWork", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject addRequisitionWork(
			WorkBean workBean,
			HttpServletRequest request) throws Exception {

		String remoteIpAddr = request.getHeader("X-Forwarded-For");
		if (remoteIpAddr == null || "".equals(remoteIpAddr)) {
			remoteIpAddr = request.getRemoteAddr();
		}
		workBean.setClientIp(remoteIpAddr);

		String isKmlFile = request.getParameter("isKmlFile");
		if (isKmlFile != null && !isKmlFile.isEmpty()) {
			workBean.setKmlFileUpload(true);
		}

		user = RESUtil.getUserDetail();
		logger.info("User - " + user.getUsername() + ", Role - " + user.getAuthorities()
				+ " - Adding Requisition Work data");
		ResponseObject response = new ResponseObject();

		String errorMsg = eeService.addRequisitionWork(workBean);
		if (errorMsg != null) {
			response.setErrorMessage(errorMsg);
			logger.error("User - " + user.getUsername() + ", Role - " + user.getAuthorities() + " - " + errorMsg);
		} else {
			if (workBean.getWorkRequestStatusId() == 2) {
				response.setSuccessMessage("Work Requisition Submitted successfully!");
				logger.info("User - " + user.getUsername() + ", Role - " + user.getAuthorities()
						+ " - Work Requisition Submitted successfully!");
			} else {
				response.setSuccessMessage("Work Requisition Saved as Draft successfully!");
				logger.info("User - " + user.getUsername() + ", Role - " + user.getAuthorities()
						+ " - Work Requisition Saved as Draft successfully!");
			}
		}
		return response;
	}

	@RequestMapping(value = "/editWorkRequisitionDataMapping/{id}", method = RequestMethod.GET)
	public ModelAndView viewEditWorkRequisitionForm(
			@PathVariable String id, HttpServletRequest request) {

		user = RESUtil.getUserDetail();
		logger.info("User - " + user.getUsername() + ", Role - " + user.getAuthorities()
				+ " - Displaying Edit Work Requisition Form");
		ModelAndView modelAndView = new ModelAndView("sube/editWorkRequisitionDataForm");
		return modelAndView;
	}

	@RequestMapping(value = "/editRequisitionWork", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject editRequisitionWork(WorkBean workBean,
			@RequestPart(value = "kmlFile", required = false) MultipartFile kmlFile,
			HttpServletRequest request) throws Exception {

		String remoteIpAddr = request.getHeader("X-Forwarded-For");
		if (remoteIpAddr == null || "".equals(remoteIpAddr)) {
			remoteIpAddr = request.getRemoteAddr();
		}
		workBean.setClientIp(remoteIpAddr);

		user = RESUtil.getUserDetail();
		logger.info("User - " + user.getUsername() + ", Role - " + user.getAuthorities() + " - Updating Work data");
		ResponseObject response = new ResponseObject();

		String errorMsg = eeService.editRequisitionWork(workBean);

		if (errorMsg != null) {
			response.setErrorMessage(errorMsg);
			logger.error("User - " + user.getUsername() + ", Role - " + user.getAuthorities() + " - " + errorMsg);
		} else {

			if (workBean.getWorkRequestStatusId() == 2) {
				response.setSuccessMessage("Work Requisition Submitted successfully!");
				logger.info("User - " + user.getUsername() + ", Role - " + user.getAuthorities()
						+ " - Work Requisition Submitted successfully!");
			} else {
				response.setSuccessMessage("Work Requisition Saved as Draft successfully!");
				logger.info("User - " + user.getUsername() + ", Role - " + user.getAuthorities()
						+ " - Work Requisition Saved as Draft successfully!");
			}

			/*
			 * response.setSuccessMessage("Work updated successfully!");
			 * logger.info("User - " + user.getUsername() + ", Role - " +
			 * user.getAuthorities() + " - Work Requisition updated successfully!");
			 */
		}
		return response;
	}

	@PostMapping(value = "processKmlFile", consumes = { "multipart/form-data" })
	public List<KmlFilePoints> ProcessKmlFile(KmlFilePoints Bean, HttpServletRequest request) {
		logger.info("Processing Kml File");

		return eeService.processKmlFile(Bean);

	}

	@PostMapping("getLatLng/{templat}/{templong}/")
	public ResponseEntity<String> getLatLng(@PathVariable BigDecimal templat, @PathVariable BigDecimal templong)
			throws IOException {
		System.out.printf("templat: %.6f, templong: %.6f%n", templat, templong);
		String apiUrl = "https://geoportal.mp.gov.in/adminunitservice/RestServiceImpl.svc/getAdminUnitsByLatLon";
		String requestBody = String.format("{\"lat\": \"%s\", \"lon\": \"%s\"}", templat, templong);

		// Set up the HTTP request
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

		// Call the external API
		ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, requestEntity, String.class);
		// System.out.println("response"+response.getBody());

		return response;

	}

	@PostMapping("getLatLngforest/{templat}/{templong}")
	public ResponseEntity<LatLngForestResponse> getLatLngforestarea(@PathVariable double templat,
			@PathVariable double templong) throws IOException {
		String apiUrl = "https://geoportal.mp.gov.in/MPGeo_API/api/RangeBoundary/getRangeBoundaryByLatLng";
		String requestBody = String.format("{\"lat\": \"%s\", \"lng\": \"%s\"}", templat, templong);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

		// Map the response directly to LatLngForestResponse
		ResponseEntity<LatLngForestResponse> response = restTemplate.postForEntity(apiUrl, requestEntity,
				LatLngForestResponse.class);

		return ResponseEntity.ok(response.getBody());

	}

	@RequestMapping(value = "/viewIssuedEmb", method = RequestMethod.GET)
	public ModelAndView viewIssuedEmb(HttpServletRequest request) {

		user = RESUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying issueEMBNumber Form", user.getUsername(),
				user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("ee/viewIssuedEmb");

		return modelAndView;
	}

	@RequestMapping(value = "/fetchWorkWithEmbIssuedForEngineer", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String fetchWorkWithEmbIssued(HttpServletRequest request) {

		user = RESUtil.getUserDetail();
		logger.info("User - {}, Role - {} - fetch Work List", user.getUsername(), user.getAuthorities());
		String searchBoxVal = request.getParameter("searchBoxVal");
		String projectId = request.getParameter("projectName");
		String blockId = request.getParameter("blockName");
		String grampanchayatId = request.getParameter("grampanchayatId");

		String villageId = request.getParameter("villageId");

		String sSortCol = request.getParameter("iSortCol_0");
		String sSortDir = request.getParameter("sSortDir_0");
		String sColName = request.getParameter("mDataProp_" + sSortCol);

		UserBean user = fetchLoggedInUserDetails(request);
		// Fetch the page number from client
		Integer pageNumber = 0;

		Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));

		if (null != request.getParameter("iDisplayStart")) {
			pageNumber = (Integer.valueOf(request.getParameter("iDisplayStart")) / pageDisplayLength);
		}

		Sort sort = null;
		if (sColName != null) {
			if (StringUtils.equals("asc", sSortDir)) {
				sort = new Sort(new Sort.Order(Direction.ASC, sColName));
			} else {
				sort = new Sort(new Sort.Order(Direction.DESC, sColName));
			}
		} else {
			sort = new Sort(new Sort.Order(Direction.DESC, "id"));// default sorting
		}

		Pageable pageable = new PageRequest(pageNumber, pageDisplayLength, sort);

		// WorkCreationJson workjson = workService.getAllWork(pageable, searchBoxVal);
		WorkJson workjson = eeService.fetchWorkWithEmbIssuedForEnginner(user, pageable,
				!StringUtils.isEmpty(searchBoxVal) ? searchBoxVal : null,
				!StringUtils.isEmpty(projectId) ? projectId : null, !StringUtils.isEmpty(blockId) ? blockId : null,
				!StringUtils.isEmpty(grampanchayatId) ? grampanchayatId : null,
				!StringUtils.isEmpty(villageId) ? villageId : null

		);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(workjson);

		return json;
	}

	@RequestMapping(value = "/takeMeasurement/{workId}", method = RequestMethod.GET)
	public ModelAndView takeMeasurement(@PathVariable Long workId, HttpServletRequest request) {

		user = RESUtil.getUserDetail();
		// logger.info(
		// "User - " + user.getUsername() + ", Role - " + user.getAuthorities() + " -
		// Displaying View Bills page");

		ModelAndView modelAndView = new ModelAndView("ae/TakeMeasurement");
		UserBean userDetail = userService.fetchUserDetailsByUserName(user.getUsername());
		String loggedInUserRole = userDetail.getLoggedInUserRole();
		modelAndView.addObject("loggedInUserRole", loggedInUserRole);
		return modelAndView;
	}

	@GetMapping(value = "/fetchItemByChapterForEstimate/{workId}")
	public List<chapterDesciptionDto> loadItemChapterWise(@PathVariable String workId, HttpServletRequest request) {
		user = RESUtil.getUserDetail();
		return eeService.loadItemChapterWise(workId, fetchLoggedInUserDetails(request));
	}

	@GetMapping(value = "/loadPreviousDataByEstimateIdInEdit/{estimateSorId}/{mId}")
	public List<Double> loadPreviousDataByEstimateIdInEdit(HttpServletRequest request,
			@PathVariable("estimateSorId") Long estimateSorId, @PathVariable("mId") Long mId) {
		return eeService.loadPreviousDataByEstimateIdInEdit(fetchLoggedInUserDetails(request), estimateSorId, mId);
	}

	@PostMapping(value = "/addMeasurementForThisEmb", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseObject addMeasurementForThisEmb(HttpServletRequest request, @ModelAttribute MeasurementDto dto) {
		return eeService.addMeasurement(fetchLoggedInUserDetails(request), dto, getClientIp(request));
	}

	@GetMapping(value = "/loadPreviousDataByEstimateId/{estimateSorId}")
	public List<Double> loadPreviousDataByEstimateId(HttpServletRequest request,
			@PathVariable("estimateSorId") Long estimateSorId) {
		return eeService.loadPreviousDataByEstimateId(fetchLoggedInUserDetails(request), estimateSorId);
	}

	private static String getClientIp(HttpServletRequest request) {

		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		return ip;
	}
	
	
	@GetMapping(value = "/loadMeasurementByEsorId/{estimateSorId}" )
	public List<MeasurementDto> LoadAllMeasurementListByEstimateSorId(HttpServletRequest request,@PathVariable("estimateSorId") Long estimateSorId){
		return eeService.LoadAllMeasurementListByEstimateSorId(fetchLoggedInUserDetails(request), estimateSorId);
		
	}
	
	@RequestMapping(value = "/deleteMeasurementById/{id}", method = RequestMethod.POST)
@ResponseBody
public ResponseObject deleteMeasurementById(@PathVariable Long id, HttpServletRequest request) {

    ResponseObject responseObject = new ResponseObject();

    try {

        measurementRepository.delete(id);
   
        responseObject.setSuccessMessage("Measurement deleted successfully");

    } catch (Exception e) {

        logger.error("Error deleting measurement: ", e);

        responseObject.setErrorMessage("Error deleting measurement");

    }
    return responseObject;
}

@GetMapping(value = "/downloadMeasurementFile/{documentId}")
	public void downloadDocument(@PathVariable String documentId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" downloadDocument = documentId" + documentId);
		
		
		String fileName = commonService.fetchDownloadFileNameEMB(Long.parseLong(documentId));
		if (fileName != null) {
			File file = new File(fileName);
			if (file.exists()) {
				InputStream is = null;
				OutputStream os = null;
				try {
					is = new FileInputStream(file);
					os = response.getOutputStream();

					// Set response headers
					response.setContentType("application/octet-stream");
					response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

					// Read and write file content
					byte[] buffer = new byte[1024];
					int len;
					while ((len = is.read(buffer)) != -1) {
						os.write(buffer, 0, len);
					}
					os.flush();
				} catch (IOException e) {
					logger.error("Error while downloading document with ID: " + documentId, e);
					response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to download file.");
				} finally {
					if (os != null) {
						try {
							os.close();
						} catch (IOException e) {
							logger.warn("Failed to close output stream", e);
						}
					}
					if (is != null) {
						try {
							is.close();
						} catch (IOException e) {
							logger.warn("Failed to close input stream", e);
						}
					}
				}
			} else {
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found.");
			}
		} else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid document ID or file name.");
		}
		// obj.downloadDocument(documentId.substring(0, documentId.length()-4), request,
		// response);
	}
	
	@GetMapping(value = "/getMeasurementSummaryByWorkId/{workId}")
	public Map<String, Object> getMeasurementSummaryByWorkId(@PathVariable("workId") Long workId) {
		return eeService.getMeasurementSummaryByWorkId(workId);
	}

	
	@RequestMapping(value="generateDscVerificationPDF", method=RequestMethod.POST)
	public ResponseObject generateDscVerificationPDF() {

	    ResponseObject response = new ResponseObject();

	    try {

	        // 🔹 create PDF in memoryimport java.io.ByteArrayOutputStream;
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();

	        Document document = new Document();
	        PdfWriter.getInstance(document, baos);

	        document.open();
	        document.add(new Paragraph("DSC Verification"));
	        document.close();

	        byte[] pdfBytes = baos.toByteArray();
	        String base64 = Base64.getEncoder().encodeToString(pdfBytes);

	        // 🔹 server datetime (must match signer format)
	        String serverTime = LocalDateTime.now()
	                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a"));


	        response.setUnsignedBase64PDF(base64);
	        response.setServerDateTime(serverTime);
	        response.setServerDateTime(RESUtil.convertDateToString(new Date()));
	        System.out.println("Generated in-memory PDF base64 length: " + base64.length());
	        System.out.println("Server time: " + serverTime);

	    } catch (Exception e) {
	        e.printStackTrace();
	        response.setErrorMessage(e.getMessage());
	    }

	    return response;
	}

	
	@RequestMapping(value="saveMeasurementWithDsc", method=RequestMethod.POST)
	@ResponseBody
	public ResponseObject saveMeasurementWithDsc(@RequestBody DscSaveRequest req,HttpServletRequest request){

	    ResponseObject res = new ResponseObject();

	    try{

	        if(req.getSignedData() == null || req.getSignedData().isEmpty()){
	            res.setErrorMessage("DSC data missing");
	            return res;
	        }

	        Long workId = req.getWorkId();
	        System.out.println("WorkId = " + workId);

	        res = eeService.VerifyTheMesurementWithDSC(req);
	        


	        

	    }catch(Exception e){
	        e.printStackTrace();
	        res.setErrorMessage(e.getMessage());
	    }

	    return res;
	}
	
}
