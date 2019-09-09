package com.sampark.PMS;

import java.util.Locale;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sampark.PMS.object.RequestObject;
import com.sampark.PMS.object.ResponseObject;
import com.sampark.PMS.util.CommonUtils;
import com.sampark.PMS.util.DbUtils;

/**
 * Handles requests for the application home page.
 */
@Controller
public class AppraisalYearDetailsController {

	private static final Logger logger = LoggerFactory.getLogger(AppraisalYearDetailsController.class);
	private static ResourceBundle messages = ResourceBundle.getBundle("messages");

	@RequestMapping(value = "/appraisal-cycle", method = RequestMethod.GET)
	public String appraisalCycle(Locale locale, Model model) {
		logMessage("appraisal-cycle", "");
		return "PMS/pages/appraisal/appraisal-cycle";
	}
	
	@RequestMapping(value = "/team_rating", method = RequestMethod.GET)
	public String teamRating(Locale locale, Model model) {
		logMessage("team_rating", "");
		return "PMS/pages/appraisal/team_rating";
	}
	
	
	@RequestMapping(value = "/teamRating", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject teamRating(@RequestBody RequestObject object) {
		logger.info("teamRating API");
        return DbUtils.teamRating(object);
	}
	
	@RequestMapping(value = "/employeeAcknowledgement", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject employeeAcknowledgement(@RequestBody RequestObject object) {
		logger.info("employeeAcknowledgement");
        return DbUtils.employeeAcknowledgement(object.getEmpCode(),object.getAppraisalYearId());
	}
	
	@RequestMapping(value = "/getAppraisalPendingEmployeeDetails", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject getAppraisalPendingEmployeeDetails(@RequestBody RequestObject object) {
		logger.info("getAppraisalPendingEmployeeDetails");
        return DbUtils.getAppraisalPendingEmployeeDetails(object.getEmpCode(),object.getAppraisalYearId(),object.getName(),object.getType());
	}
	@RequestMapping(value = "/getALLAppraisalPendingEmployeeDetails", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject getALLAppraisalPendingEmployeeDetails(@RequestBody RequestObject object) {
		logger.info("getALLAppraisalPendingEmployeeDetails");
        return DbUtils.getALLAppraisalPendingEmployeeDetails(object.getAppraisalYearId(),object.getType(),object.getId());
	}
	
	
	@RequestMapping(value = "/carryForwardEmployeeKRADATAToCurrentYear", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject carryForwardEmployeeKRADATAToCurrentYear(@RequestBody RequestObject object) {
		logger.info("carryForwardEmployeeKRADATAToCurrentYear");
        return DbUtils.carryForwardEmployeeKRADATAToCurrentYear(object);
	}
	
	@RequestMapping(value = "/getAppraisalStatus", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject getAppraisalStatus(@RequestBody RequestObject object) {
		logger.info("getAppraisalStatus ---->" + object.getType());
		return DbUtils.getUserAppraisalDetails(object.getEmpCode(),object.getAppraisalYearId(),object.getType());
	}
	
	@RequestMapping(value = "/updateEmployeeAppraisal", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject updateEmployeeAppraisal(@RequestBody RequestObject object) {
		logger.info("updateEmployeeAppraisal");
        return DbUtils.updateEmployeeAppraisal(object.getStatus(),object.getId(),object.getAppraisalYearId());
	}
	@RequestMapping(value = "/get-all-employeeAppraisalList", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject getEmployeeAppraisalList(@RequestBody Integer appraisalYearId) {
		logger.info("get-all-employeeAppraisalList - " + appraisalYearId);
		return DbUtils.getEmployeeAppraisalList(appraisalYearId);
	}
	
	@RequestMapping(value = "/sendToManager", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject sendToManager(@RequestBody RequestObject object) {
		logger.info("sendToManager");
        return DbUtils.sendToManager(object.getName(),object.getType(),object.getEmpCode(),object.getAppraisalYearId());
	}
	
	@RequestMapping(value = "/view-appraisal", method = RequestMethod.GET)
	public String viewAppraisal(Locale locale, Model model) {
		logMessage("view-appraisal", "");
		return "PMS/pages/appraisal/view-appraisal";
	}

	@RequestMapping(value = "/appraisal-cycle-start", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject appraisalCycleStart(@RequestBody RequestObject object) {
		logger.info("appraisal-cycle-start");
        return DbUtils.appraisalCycleStart(object);
	}
	
	@RequestMapping(value = "/changeEmployeeAppraisalStage", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject changeEmployeeAppraisalStage(@RequestBody RequestObject object) {
		logger.info("changeEmployeeAppraisalStage");
        return DbUtils.changeEmployeeAppraisalStage(object);
	}
	
//	@RequestMapping(value = "/view-subEmployee-appraisal", method = RequestMethod.GET)
//	public String viewSubEmployeeAppraisal(Locale locale, Model model) {
//		logMessage("view-subEmployee-appraisal", "");
//		return "PMS/pages/appraisal/view-subEmployee-appraisal";
//	}

//	@RequestMapping(value = "/appraisal-year-kra-submission", method = RequestMethod.POST)
//	@ResponseBody
//	public ResponseObject appraisalYearKraSubmission(@RequestBody RequestObject object) {
//		logger.info("appraisal-year-kra-submission");
//		return DbUtils.appraisalYearKraSubmission(object.getAppraisalYearId());
//	}

//	@RequestMapping(value = "/appraisal-year-mid-year-submission", method = RequestMethod.POST)
//	@ResponseBody
//	public ResponseObject appraisalYearMidYearSubmission(@RequestBody RequestObject object) {
//		logger.info("appraisal-year-mid-year-submission");
//		return DbUtils.appraisalYearMidYearSubmission(object.getAppraisalYearId());
//	}

//	@RequestMapping(value = "/appraisal-year-final-year-submission", method = RequestMethod.POST)
//	@ResponseBody
//	public ResponseObject appraisalYearFinalYearSubmission(@RequestBody RequestObject object) {
//		logger.info("appraisal-year-final-year-submission");
//		return DbUtils.appraisalYearFinalYearSubmission(object.getAppraisalYearId());
//	}

//	@RequestMapping(value = "/get-appraisal-cycle-details", method = RequestMethod.POST)
//	@ResponseBody
//	public ResponseObject getAppraisalCycleDetails(@RequestBody RequestObject object) {
//		logger.info("get-appraisal-cycle-details");
//		return DbUtils.getAppraisalCycleDetails(object.getAppraisalYearId());
//	}

	private void logMessage(String action, String data) {
		try {
			logger.debug("[" + action + "] by [" + CommonUtils.getCurrentUserName() + "] " + data);
		} catch (Exception e) {
			logger.debug("Exception while logging action [" + action + "] message [" + data);
		}
	}
}
