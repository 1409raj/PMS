package com.sampark.PMS;

import java.util.List;
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

import com.sampark.PMS.dto.AppraisalYear;
import com.sampark.PMS.object.RequestObject;
import com.sampark.PMS.object.ResponseObject;
import com.sampark.PMS.util.CommonUtils;
import com.sampark.PMS.util.DbUtils;

/**
 * Handles requests for the application home page.
 */
@Controller
public class AppraisalYearController {

	private static final Logger logger = LoggerFactory.getLogger(AppraisalYearController.class);
	private static ResourceBundle messages = ResourceBundle.getBundle("messages");
	
	@RequestMapping(value = "/get-all-appraisal-year-details", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject getAppraisalYearDetails() {
		logger.info("get-all-appraisal-year-details");
		return DbUtils.getAppraisalYearDetails();
	}
	
	@RequestMapping(value = "/over_all_rating", method = RequestMethod.GET)
	public String overAllRating(Locale locale, Model model) {
		logMessage("over_all_rating", "");
		return "PMS/pages/appraisal/over_all_rating";
	}
	@RequestMapping(value = "/team_over_all_rating", method = RequestMethod.GET)
	public String teamOverAllRating(Locale locale, Model model) {
		logMessage("team_over_all_rating", "");
		return "PMS/pages/appraisal/team_over_all_rating";
	}
	
	@RequestMapping(value = "/team-appraisal", method = RequestMethod.GET)
	public String teamLevelAppraisal(Locale locale, Model model) {
		logMessage("team-appraisal", "");
		return "PMS/pages/appraisal/team-appraisal";
	}
	

	@RequestMapping(value = "/firstlevel-appraisal", method = RequestMethod.GET)
	public String firstlevelAppraisal(Locale locale, Model model) {
		logMessage("firstlevel-appraisal", "");
		return "PMS/pages/appraisal/firstlevel-appraisal";
	}
	
	@RequestMapping(value = "/secondlevel-appraisal", method = RequestMethod.GET)
	public String secondlevelAppraisal(Locale locale, Model model) {
		logMessage("secondlevel-appraisal", "");
		return "PMS/pages/appraisal/secondlevel-appraisal";
	}
	
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	@RequestMapping(value = "/get-appraisal-configurator-details", method = RequestMethod.POST)
//	@ResponseBody
//	public ResponseObject getAppraisalConfiguratorDetails() {
//		logger.info("get-appraisal-configurator-details");
//		ResponseObject res = new ResponseObject();
//		List list = new ArrayList();
//		list.add(DbUtils.getAllDepartmentList());
//		list.add(DbUtils.getAllDesignationList());
//		res.setObject(list);
//		res.setInteger(PMSConstants.STATUS_SUCCESS);
//		res.setString("Success");
//		return res;
//	}
	
	@RequestMapping(value = "/appraisal_years-list", method = RequestMethod.GET)
	public String appraisalyearList(Locale locale, Model model) {
		logMessage("appraisal_years-list", "");
		return "PMS/pages/appraisal/appraisal_years-list";
	}
	private void logMessage(String action, String data) {
		try {
			logger.debug("[" + action + "] by [" + CommonUtils.getCurrentUserName() + "] " + data);
		} catch (Exception e) {
			logger.debug("Exception while logging action [" + action + "] message [" + data);
		}
	}
	@RequestMapping(value = "/get-appraisal-years-list", method = RequestMethod.POST)
	@ResponseBody
	public List<AppraisalYear> getAppraisalYearList() {
		logger.info("get-appraisal-year-list - ");
		return DbUtils.getAppraisalYearList();
	}

 @RequestMapping(value = "/save-appraisal-year", method = RequestMethod.POST)
 @ResponseBody
	public ResponseObject saveAppraisalYear(@RequestBody RequestObject object) {
		logger.info("save-appraisal-year "+ object);
		return DbUtils.saveAppraisalYear(object);
	}
 @RequestMapping(value = "/update-application-appraisal-year", method = RequestMethod.POST)
 @ResponseBody
	public ResponseObject updateApplicationAppraisalYear(@RequestBody RequestObject object) {
		logger.info("update-application-appraisal-year "+ object.getList());
		return DbUtils.updateApplicationAppraisalYear(object.getId(),object.getList());
	}
 @RequestMapping(value = "/delete-appraisal-year", method = RequestMethod.POST)
 @ResponseBody
	public ResponseObject deleteAppraisalYear(@RequestBody RequestObject object) {
		logger.info("delete-appraisal-year "+ object);
		return DbUtils.deleteAppraisalYear(object.getId());
	}
}
