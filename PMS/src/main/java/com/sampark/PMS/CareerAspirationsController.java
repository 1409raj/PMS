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

import com.sampark.PMS.dto.CareerAspirationsDetails;
import com.sampark.PMS.object.ResponseObject;
import com.sampark.PMS.util.CommonUtils;
import com.sampark.PMS.util.DbUtils;

/**
 * Handles requests for the application home page.
 */
@Controller
public class CareerAspirationsController {

	private static final Logger logger = LoggerFactory.getLogger(CareerAspirationsController.class);
	private static ResourceBundle messages = ResourceBundle.getBundle("messages");

	@RequestMapping(value = "/career_aspiration", method = RequestMethod.GET)
	public String carreeAspiration(Locale locale, Model model) {
		logMessage("career_aspiration", "");
		return "PMS/pages/career_aspiration/career_aspiration";
	}

	@RequestMapping(value = "/add-employee-career_aspirations-details", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject addEmployeeCareerAspirationsDetails(@RequestBody CareerAspirationsDetails object) {
		logger.info("add-employee-career_aspirations-details" + object);
		ResponseObject result = DbUtils.addCareerAspirationsDetails(object,object.getEmpCode(),PMSConstants.USER_EMPLOYEE);
		return result;
	}
	@RequestMapping(value = "/add-manager-career_aspirations-details", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject addManagerCareerAspirationsDetails(@RequestBody CareerAspirationsDetails object) {
		logger.info("add-manager-career_aspirations-details" + object);
		ResponseObject result = DbUtils.addCareerAspirationsDetails(object,object.getEmpCode(),object.getName());
		return result;
	}
	private void logMessage(String action, String data) {
		try {
			logger.debug("[" + action + "] by [" + CommonUtils.getCurrentUserName() + "] " + data);
		} catch (Exception e) {
			logger.debug("Exception while logging action [" + action + "] message [" + data);
		}
	}
}
