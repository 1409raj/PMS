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
import com.sampark.PMS.dto.TrainingNeeds;
import com.sampark.PMS.object.RequestObject;
import com.sampark.PMS.object.ResponseObject;
import com.sampark.PMS.util.CommonUtils;
import com.sampark.PMS.util.DbUtils;

/**
 * Handles requests for the application home page.
 */
@Controller
public class TrainingNeedsController {

	private static final Logger logger = LoggerFactory.getLogger(TrainingNeedsController.class);
	private static ResourceBundle messages = ResourceBundle.getBundle("messages");

	@RequestMapping(value = "/training_needs-list", method = RequestMethod.GET)
	public String trainingNeedsList(Locale locale, Model model) {
		logMessage("training_needs-list", "");
		return "PMS/pages/training_needs/training_needs-list";
	}
	
//	 @RequestMapping(value = "/save_training_needs", method = RequestMethod.POST)
//	 @ResponseBody
//		public ResponseObject saveTrainingNeeds(@RequestBody RequestObject object) {
//			logger.info("save-training_needs "+ object);
//			return DbUtils.saveTrainingNeeds(object);
//		}
//	@RequestMapping(value = "/activate_training_needs", method = RequestMethod.POST)
//	 @ResponseBody
//		public ResponseObject activateTrainingNeeds(@RequestBody RequestObject object) {
//			logger.info("activate_training_needs "+ object);
//			return DbUtils.activateTrainingNeeds(object.getId(),object.getAppraisalYearId());
//		}
//	 @RequestMapping(value = "/delete_training_needs", method = RequestMethod.POST)
//	 @ResponseBody
//		public ResponseObject deleteTrainingNeeds(@RequestBody RequestObject object) {
//			logger.info("delete-training_needs "+ object);
//			return DbUtils.deleteTrainingNeeds(object.getId());
//		}
	@RequestMapping(value = "/training_needs", method = RequestMethod.GET)
	public String trainingNeeds(Locale locale, Model model) {
		logMessage("training_needs", "");
		return "PMS/pages/training_needs/training_needs";
	}
//	@RequestMapping(value = "/get_training_needs_list", method = RequestMethod.POST)
//	public @ResponseBody List<TrainingNeeds> getTrainingNeedsList() {
//		logger.info("get_training_needs_list");
//		return DbUtils.getTrainingNeedsList();
//
//	}
	@RequestMapping(value = "/add-employee-trainingNeeds-details", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject addEmployeeTrainingNeedsDetails(@RequestBody RequestObject object) {
		logger.info("add-employee-trainingNeeds-details" + object.getAppraisalYearId());
		return DbUtils.addTrainingNeedsDetails(object, object.getEmpCode(),PMSConstants.USER_EMPLOYEE);
	}
	@RequestMapping(value = "/add-manager-trainingNeeds-details", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject addManagerTrainingNeedsDetails(@RequestBody RequestObject object) {
		logger.info("add-manager-trainingNeeds-details" + object);
		return DbUtils.addTrainingNeedsDetails(object,object.getEmpCode(),object.getName());
	}
	private void logMessage(String action, String data) {
		try {
			logger.debug("[" + action + "] by [" + CommonUtils.getCurrentUserName() + "] " + data);
		} catch (Exception e) {
			logger.debug("Exception while logging action [" + action + "] message [" + data);
		}
	}
}
