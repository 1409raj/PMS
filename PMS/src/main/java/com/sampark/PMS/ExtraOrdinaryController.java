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

import com.sampark.PMS.dto.ExtraOrdinary;
import com.sampark.PMS.object.RequestObject;
import com.sampark.PMS.object.ResponseObject;
import com.sampark.PMS.util.CommonUtils;
import com.sampark.PMS.util.DbUtils;

/**
 * Handles requests for the application home page.
 */
@Controller
public class ExtraOrdinaryController {

	private static final Logger logger = LoggerFactory.getLogger(ExtraOrdinaryController.class);
	private static ResourceBundle messages = ResourceBundle.getBundle("messages");

	/**
	 * Simply selects the home view to render by returning its name.
	 */

	@RequestMapping(value = "/extra_ordinary-list", method = RequestMethod.GET)
	public String extraOrdinaryList(Locale locale, Model model) {
		logMessage("extra_ordinary-list", "");
		return "PMS/pages/extra_ordinary/extra_ordinary-list";
	}

	@RequestMapping(value = "/extra_ordinary", method = RequestMethod.GET)
	public String extraOrdinary(Locale locale, Model model) {
		logMessage("extra_ordinary", "");
		return "PMS/pages/extra_ordinary/extra_ordinary";
	}
	 @RequestMapping(value = "/get_extra_ordinary_list", method = RequestMethod.POST)
		@ResponseBody
		public List<ExtraOrdinary> getAllExtraOrdinary() {
			logger.info("extra_ordinary - ");
			return DbUtils.getAllExtraOrdinary();
		}
	 
	 @RequestMapping(value = "/save_extra_ordinary", method = RequestMethod.POST)
	 @ResponseBody
		public ResponseObject saveExtraOrdinary(@RequestBody RequestObject object) {
			logger.info("save-extra_ordinary "+ object);
			return DbUtils.saveExtraOrdinary(object);
		}
	 @RequestMapping(value = "/activate_extra_ordinary", method = RequestMethod.POST)
	 @ResponseBody
		public ResponseObject activateExtraOrdinary(@RequestBody RequestObject object) {
			logger.info("activate-extra_ordinary "+ object);
			return DbUtils.activateExtraOrdinary(object.getId(),object.getAppraisalYearId());
		}
	 @RequestMapping(value = "/delete_extra_ordinary", method = RequestMethod.POST)
	 @ResponseBody
		public ResponseObject deleteExtraOrdinary(@RequestBody RequestObject object) {
			logger.info("delete-extra_ordinary "+ object);
			return DbUtils.deleteExtraOrdinary(object.getId());
		}

	@RequestMapping(value = "/add-employee-extraOrdinary-details", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject addEmployeeExtraOrdinaryDetails(@RequestBody RequestObject object) {
		logger.info("add-employee-extraOrdinary-details" + object);
		ResponseObject result = DbUtils.addExtraOrdinaryDetails(object, object.getEmpCode(),PMSConstants.USER_EMPLOYEE);
		return result;
	}
	@RequestMapping(value = "/add-manager-extraOrdinary-details", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject addManagerExtraOrdinaryDetails(@RequestBody RequestObject object) {
		logger.info("add-manager-extraOrdinary-details" + object);
		ResponseObject result = DbUtils.addExtraOrdinaryDetails(object,object.getEmpCode(),object.getName());
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
