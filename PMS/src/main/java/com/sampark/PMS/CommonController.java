package com.sampark.PMS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sampark.PMS.dto.EmployeeDynamicFormDetails;
import com.sampark.PMS.dto.Parameters;
import com.sampark.PMS.object.RequestObject;
import com.sampark.PMS.object.ResponseObject;
import com.sampark.PMS.util.CommonUtils;
import com.sampark.PMS.util.DbUtils;

/**
 * Handles requests for the application home page.
 */
@Controller
public class CommonController {

	private static final Logger logger = LoggerFactory.getLogger(CommonController.class);
	private static ResourceBundle messages = ResourceBundle.getBundle("messages");

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/get-all-basic-details", method = RequestMethod.POST)
	@ResponseBody
	public Map getAllBasicDetails() {
		logger.info("getAllBasicDetails - ");
		Map map = new HashMap();
		map.put("departmentList", DbUtils.getAllDepartmentList());
		map.put("designationList", DbUtils.getAllDesignationList());
//		map.put("qualificationList", DbUtils.getAllQualificationList());
		map.put("employeeList", DbUtils.getAllEmployeeCodeList());
		map.put("applicationRoleList", DbUtils.getAllApplicationRolesList());
		return map;
	}
	
	
	@RequestMapping(value = "/isUserFirstTimeLogin", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject isUserFirstTimeLogin(@RequestBody RequestObject object) {
		logger.info("isUserFirstTimeLogin" + object.getEmpCode());
		return DbUtils.isUserFirstTime(object.getEmpCode(),object.getAppraisalYearId());
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/getDashBoardDetails", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject getDashBoardDetails(@RequestBody RequestObject object) {
		logger.info("getDashBoardDetails ---->" + object.getType());
		List list = new ArrayList();
		ResponseObject res = new ResponseObject();
//			list.add(DbUtils.getUserAppraisalDetails(object.getEmpCode(),
//					object.getAppraisalYearId()));
			list.add(DbUtils.getSubEmployeeAppraisalCountDetails(object.getEmpCode(),object.getAppraisalYearId(),object.getType(),object.getStage()));
			res.setObject(list);
			return res;
		}
	
	
	@RequestMapping(value = "/getEmployeeOverAllRating", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject getEmployeeOverAllRating(@RequestBody RequestObject object) {
		logger.info("getEmployeeOverAllRating ---->" + object.getEmpCode());
			return DbUtils.getEmployeeOverAllRating(object.getAppraisalYearId(),object.getEmpCode());
		}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/getHRDashBoardDetails", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject getHRDashBoardDetails(@RequestBody RequestObject object) {
		logger.info("getHRDashBoardDetails ---->" + object.getType());
		List list = new ArrayList();
		ResponseObject res = new ResponseObject();
			list.add(DbUtils.getOverAllAppraisalCountDetails(object.getAppraisalYearId(),object.getType()).get(0));
			list.add(DbUtils.getOverAllDepartmentAppraisalCountDetails(object.getAppraisalYearId(),object.getType()));
			res.setObject(list);
			return res;
		}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getAllDepartmentHRDashBoardDetails", method = RequestMethod.POST)
	@ResponseBody
	public List getAllDepartmentHRDashBoardDetails(@RequestBody RequestObject object) {
		logger.info("getAllDepartmentHRDashBoardDetails");
        return DbUtils.getAllDepartmentHRDashBoardDetails(object.getAppraisalYearId());
	}
	
	
	@RequestMapping(value = "/getDetailsOverAllRating", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject getDetailsOverAllRating(@RequestBody RequestObject object) {
		logger.info("getDetailsOverAllRating ---->" + object.getEmpCode());
			return DbUtils.getUserAppraisalDetails(object.getEmpCode(),object.getAppraisalYearId(),"EMPLOYEE");
		}
	
	
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/getDetails", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject getDetails(@RequestBody RequestObject object) {
		logger.info("getDetails ---->" + object.getType());
		List list = new ArrayList();
		ResponseObject res = new ResponseObject();
		/* fetch Appraisal Details */
		switch (object.getType()) {
		case "EmployeeKra":
			list.add(DbUtils.getUserAppraisalDetails(object.getEmpCode(),
					object.getAppraisalYearId(),object.getType()));
			list.add(DbUtils.getCurrentEmployeeKRADetails(object.getEmpCode(),
					object.getAppraisalYearId(),object.getKraScreen()));
			break;
		case "EmployeeOldKra":
			list.add(DbUtils.getUserAppraisalDetails(object.getEmpCode(),
					object.getAppraisalYearId(),object.getType()));
			list.add(DbUtils.getCurrentEmployeeOldKRADetails(object.getEmpCode(),
					object.getAppraisalYearId()));
			break;
		case "EmployeeNewKra":
			list.add(DbUtils.getUserAppraisalDetails(object.getEmpCode(),
					object.getAppraisalYearId(),object.getType()));
			list.add(DbUtils.getCurrentEmployeeNewKRADetails(object.getEmpCode(),
					object.getAppraisalYearId()));
			break;
		case "ExtraOrdinary":
			list.add(DbUtils.getUserAppraisalDetails(object.getEmpCode(),
					object.getAppraisalYearId(),object.getType()));
			/* fetch Current Year Extra Ordinary */
			// list.add(DbUtils.getCurrentExtraOrdinary(object.getAppraisalYearId()));
			/* fetch Current Employee Extra Ordinary Details */
			list.add(DbUtils.getCurrentEmployeeExtraOrdinaryDetails(object.getAppraisalYearId(),
					object.getEmpCode()));
			break;
		case "BehavioralComptence":
			list.add(DbUtils.getUserAppraisalDetails(object.getEmpCode(),
					object.getAppraisalYearId(),object.getType()));
			String type = null;
			if (CommonUtils.getEmployeeType(object.getEmpCode()).equals("NON-HOD")) {
				type ="EMPLOYEE";
			} else {
				type ="HOD";
			}                                       
			list.add(DbUtils.getCurrentBehavioralCompetenceList(object.getAppraisalYearId(),type));
			list.add(DbUtils.getCurrentEmployeeBehavioralComptenceDetails(object.getEmpCode(),
					object.getAppraisalYearId()));
			break;
		case "TrainingNeeds":
			// list.add(DbUtils.getLoggedUserAppraisalDetails(CommonUtils.getCurrentUserName(),object.getAppraisalYearId()));
			/* fetch Current Year Training Needs */
			// list.add(DbUtils.getCurrentTrainingNeeds(object.getAppraisalYearId()));
			/* fetch Current Employee Training Needs Details */
			list.add(DbUtils.getUserAppraisalDetails(object.getEmpCode(),
					object.getAppraisalYearId(),object.getType()));
			list.add(DbUtils.getCurrentEmployeeTrainingNeedsDetails(object.getEmpCode(),
					object.getAppraisalYearId()));
			break;
		case "CareerAspiration":
			list.add(DbUtils.getUserAppraisalDetails(object.getEmpCode(),
					object.getAppraisalYearId(),object.getType()));
			list.add(DbUtils.getCurrentEmployeeCareerAspirationDetails(object.getEmpCode(),
					object.getAppraisalYearId()));
			break;
		}
		res.setObject(list);
		return res;
	}
	
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@RequestMapping(value = "/getNewKRADetails", method = RequestMethod.POST)
//	@ResponseBody
//	public ResponseObject getNewKRADetails(@RequestBody RequestObject object) {
//		logger.info("getNewKRADetails ---->" + object.getType());
//		List list = new ArrayList();
//		ResponseObject res = new ResponseObject();
//		list.add(DbUtils.getUserAppraisalDetails(object.getEmpCode(),object.getAppraisalYearId(),object.getType()));
//		list.add(DbUtils.getNewKRADetails(object.getEmpCode(),object.getAppraisalYearId()));
//		res.setObject(list);
//		return res;
//	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/getAllDepartmentWiseKRAList", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject getAllDepartmentWiseKRAList(@RequestBody RequestObject object) {
		logger.info("getAllDepartmentWiseKRAList ---->" + object.getDepartmentId());
		List list = new ArrayList();
		ResponseObject res = new ResponseObject();
		list.add(DbUtils.getAllDepartmentWiseKRAList(object.getDepartmentId(),object.getAppraisalYearId(),object.getEmpCode()));
		res.setObject(list);
		return res;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/get-manager-Details", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject getManagerDetails(@RequestBody RequestObject object) {
		logger.info("get-manager-Details ---->" + object.getType());
		List list = new ArrayList();
		ResponseObject res = new ResponseObject();
		switch (object.getType()) {
//		case "DashBoard":
//			list.add(DbUtils.getUserAppraisalDetails(object.getEmpCode(),
//					object.getAppraisalYearId()));
//		break;
		case "EmployeeKra":
			list.add(DbUtils.getUserAppraisalDetails(object.getEmpCode(),
					object.getAppraisalYearId(),object.getType()));
			list.add(DbUtils.getCurrentEmployeeKRADetails(object.getEmpCode(),
					object.getAppraisalYearId(),object.getKraScreen()));
			break;
		case "EmployeeOldKra":
			list.add(DbUtils.getUserAppraisalDetails(object.getEmpCode(),
					object.getAppraisalYearId(),object.getType()));
			list.add(DbUtils.getCurrentEmployeeOldKRADetails(object.getEmpCode(),
					object.getAppraisalYearId()));
			break;
		case "ExtraOrdinary":
			list.add(DbUtils.getUserAppraisalDetails(object.getEmpCode(),
					object.getAppraisalYearId(),object.getType()));
			/* fetch Current Year Extra Ordinary */
			// list.add(DbUtils.getCurrentExtraOrdinary(object.getAppraisalYearId()));
			/* fetch Current Employee Extra Ordinary Details */
			list.add(DbUtils.getCurrentEmployeeExtraOrdinaryDetails(object.getAppraisalYearId(),
					object.getEmpCode()));
			break;
		case "BehavioralComptence":
			list.add(DbUtils.getUserAppraisalDetails(object.getEmpCode(),
					object.getAppraisalYearId(),object.getType()));
			String type = null;
			if (DbUtils.getCurrentUserRoleId(object.getEmpCode()).equals(PMSConstants.USER_HOD)) {
				type =PMSConstants.USER_HOD;
			} else {
				type =PMSConstants.USER_EMPLOYEE;
			}

			list.add(DbUtils.getCurrentBehavioralCompetenceList(object.getAppraisalYearId(),type));
			list.add(DbUtils.getCurrentEmployeeBehavioralComptenceDetails(object.getEmpCode(),
					object.getAppraisalYearId()));
			break;
		case "TrainingNeeds":
			// list.add(DbUtils.getLoggedUserAppraisalDetails(CommonUtils.getCurrentUserName(),object.getAppraisalYearId()));
			/* fetch Current Year Training Needs */
			// list.add(DbUtils.getCurrentTrainingNeeds(object.getAppraisalYearId()));
			/* fetch Current Employee Training Needs Details */
			list.add(DbUtils.getCurrentEmployeeTrainingNeedsDetails(object.getEmpCode(),
					object.getAppraisalYearId()));
			break;
		case "CareerAspiration":
			list.add(DbUtils.getCurrentEmployeeCareerAspirationDetails(object.getEmpCode(),
					object.getAppraisalYearId()));
			break;
		}
		res.setObject(list);
		return res;
	}
	

	// @RequestMapping(value = "/save-parameter", method = RequestMethod.POST)
	// @ResponseBody
	// public ResponseObject saveParameter(@RequestBody RequestObject object) {
	// logger.info("save-parameter "+ object);
	// ResponseObject res = new ResponseObject();
	// switch(object.getTypeId()){
	// case 1: res =
	// DbUtils.saveDepartment(object.getId(),object.getName(),object.getDescription());break;
	// case 2: res =
	// DbUtils.saveDesignation(object.getId(),object.getName(),object.getDescription());break;
	// case 3: res =
	// DbUtils.saveQualifcation(object.getId(),object.getName(),object.getDescription());break;
	// case 4: res
	// =DbUtils.saveOrganizationRoles(object.getId(),object.getName(),object.getDepartmentId(),object.getDescription());break;
	// case 5: res
	// =DbUtils.saveApplicationRoles(object.getId(),object.getName(),object.getDescription());break;
	// }
	// return res;
	// }

	// @RequestMapping(value = "/delete-parameter", method = RequestMethod.POST)
	// @ResponseBody
	// public ResponseObject deleteParameter(@RequestBody RequestObject object)
	// {
	// logger.info("delete-parameter "+ object);
	// ResponseObject res = new ResponseObject();
	// switch(object.getTypeId()){
	// case 1: res = DbUtils.deleteDepartment(object.getId());break;
	// case 2: res = DbUtils.deleteDesignation(object.getId());break;
	// case 3: res = DbUtils.deleteQualifcation(object.getId());break;
	// case 4: res
	// =DbUtils.deleteOrganizationRoles(object.getId(),object.getDepartmentId());break;
	// case 5: res =DbUtils.deleteApplicationRoles(object.getId());break;
	// }
	// return res;
	// }

	@RequestMapping(value = "/get-all-parameter-data", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject getAllParametersData(@RequestBody Integer id) {
		logger.info("get-all-parameter-data - " + id);
		return DbUtils.getAllParametersData(id);
	}

	@RequestMapping(value = "/get-all-parameters", method = RequestMethod.POST)
	@ResponseBody
	public List<Parameters> getAllParameters() {
		logger.info("get-all-parameter- ");
		return DbUtils.getAllParameters();
	}

	@RequestMapping(value = "/get-employee-dynamic-form-details", method = RequestMethod.POST)
	@ResponseBody
	public List<EmployeeDynamicFormDetails> getAllEmployeeDynamicFormList() {
		logger.info("get-employee-dynamic-form-details - ");
		return DbUtils.getAllEmployeeDynamicFormList();
	}

	@RequestMapping(value = "/get_Current_employee_dynamic_form", method = RequestMethod.POST)
	@ResponseBody
	public List<EmployeeDynamicFormDetails> getCurrentEmployeeDynamicFormList(@RequestBody Integer appraisalYearId) {
		logger.info("get_Current_employee_dynamic_form - " + appraisalYearId);
		return DbUtils.getCurrentEmployeeDynamicFormList(appraisalYearId);
	}

	// @RequestMapping(value = "/get-all-data", method = RequestMethod.POST)
	// @ResponseBody
	// public ResponseObject getAllParameters(@RequestBody RequestObject object)
	// {
	// logger.info("get-all-data - ");
	// ResponseObject res = new ResponseObject();
	// switch(object.getType()){
	// case "Parameter" : res.setObject(DbUtils.getAllParameters());break;
	// case "employeeDynamicFormDetails" :
	// res.setObject(DbUtils.getEmployeeDynamicFormDetails());break;
	//// case 3: res = DbUtils.deleteQualifcation(object.getId());break;
	//// case 4: res
	// =DbUtils.deleteOrganizationRoles(object.getId(),object.getDepartmentId());break;
	//// case 5: res =DbUtils.deleteApplicationRoles(object.getId());break;
	// }
	// return res;
	// }

//	@RequestMapping(value = "/get-loggedUser-Details", method = RequestMethod.POST)
//	@ResponseBody
//	public ResponseObject getLoggedUserDetails() {
//		logger.info("get-loggedUser-Details - ");
//		return DbUtils.getLoggedUserDetails(CommonUtils.getCurrentUserName());
//	}

}
