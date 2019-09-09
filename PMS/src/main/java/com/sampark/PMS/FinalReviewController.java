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

import com.sampark.PMS.dto.FinalReviewDetails;
import com.sampark.PMS.object.ResponseObject;
import com.sampark.PMS.util.CommonUtils;
import com.sampark.PMS.util.DbUtils;

/**
 * Handles requests for the application home page.
 */
@Controller
public class FinalReviewController {

	private static final Logger logger = LoggerFactory.getLogger(FinalReviewController.class);
	private static ResourceBundle messages = ResourceBundle.getBundle("messages");

	@RequestMapping(value = "/final_review", method = RequestMethod.GET)
	public String finalReview(Locale locale, Model model) {
		logMessage("final_review", "");
		return "PMS/pages/final_review/final_review";
	}

	@RequestMapping(value = "/add-finalReview-details", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject addFinalReviewDetails(@RequestBody FinalReviewDetails object) {
		logger.info("add-finalReview-details" + object);
		ResponseObject result = DbUtils.addFinalReviewDetails(object, CommonUtils.getCurrentUserName());
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
