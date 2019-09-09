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

import com.sampark.PMS.dto.BehaviouralCompetence;
import com.sampark.PMS.object.RequestObject;
import com.sampark.PMS.object.ResponseObject;
import com.sampark.PMS.util.CommonUtils;
import com.sampark.PMS.util.DbUtils;

/**
 * Handles requests for the application home page.
 */
@Controller
public class BehaviouralCompetenceController {

	private static final Logger logger = LoggerFactory.getLogger(BehaviouralCompetenceController.class);
	private static ResourceBundle messages = ResourceBundle.getBundle("messages");

	@RequestMapping(value = "/behavioral_comptence-list", method = RequestMethod.GET)
	public String behavioralComptenceList(Locale locale, Model model) {
		logMessage("behavioral_comptence-list", "");
		return "PMS/pages/behavioral_comptence/behavioral_comptence-list";
	}
//	@RequestMapping(value = "/manager_behavioral_comptence", method = RequestMethod.GET)
//	public String managerBehavioralComptence(Locale locale, Model model) {
//		logMessage("manager_behavioral_comptence", "");
//		return "PMS/pages/behavioral_comptence/manager_behavioral_comptence";
//	}
	
	@RequestMapping(value = "/behavioral_comptence", method = RequestMethod.GET)
	public String behavioralComptence(Locale locale, Model model) {
		logMessage("behavioral_comptence", "");
		return "PMS/pages/behavioral_comptence/behavioral_comptence";
	}

	 @RequestMapping(value = "/save-behavioral_comptence", method = RequestMethod.POST)
	 @ResponseBody
		public ResponseObject saveBehavioralComptence(@RequestBody RequestObject object) {
			logger.info("save-behavioral_comptence "+ object);
			return DbUtils.saveBehavioralComptence(object);
		}
	 @RequestMapping(value = "/delete-behavioral_comptence", method = RequestMethod.POST)
	 @ResponseBody
		public ResponseObject deleteBehavioralComptence(@RequestBody RequestObject object) {
			logger.info("delete-behavioral_comptence "+ object);
			return DbUtils.deleteBehavioralComptence(object.getId(),object.getAppraisalYearId(),object.getType());
		}
	 
	 @RequestMapping(value = "/behavioral_data_carry_forward", method = RequestMethod.POST)
	 @ResponseBody
		public ResponseObject behavioralDataCarryForward(@RequestBody RequestObject object) {
			logger.info("behavioral_data_carry_forward "+ object);
			return DbUtils.behavioralDataCarryForward(object.getAppraisalYearId(),object.getType());
		}
	 
	@RequestMapping(value = "/add-employee-behavioral-comptence-details", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject addEmployeeBehavioralComptenceDetails(@RequestBody RequestObject object) {
		logger.info("add-employee-behavioral-comptence-details" + object);
		return DbUtils.addBehavioralComptenceDetails(object, CommonUtils.getCurrentUserName(),PMSConstants.USER_EMPLOYEE);
	}
	
	@RequestMapping(value = "/add-manager-behavioral-comptence-details", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject addManagerBehavioralComptenceDetails(@RequestBody RequestObject object) {
		logger.info("add-manager-behavioral-comptence-details" + object);
		return DbUtils.addBehavioralComptenceDetails(object, object.getEmpCode(),object.getName());
	}

	@RequestMapping(value = "/get-behavioral-competence-list", method = RequestMethod.POST)
	public @ResponseBody List<BehaviouralCompetence> behavioralCompetenceList(@RequestBody RequestObject object) {
		logger.info("get-behavioral-competence-list");
		return DbUtils.getCurrentBehavioralCompetenceList(object.getId(),object.getType());

	}

	private void logMessage(String action, String data) {
		try {
			logger.debug("[" + action + "] by [" + CommonUtils.getCurrentUserName() + "] " + data);
		} catch (Exception e) {
			logger.debug("Exception while logging action [" + action + "] message [" + data);
		}
	}
}
