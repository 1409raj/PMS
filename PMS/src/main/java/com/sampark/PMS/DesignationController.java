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

import com.sampark.PMS.dto.Designation;
import com.sampark.PMS.object.RequestObject;
import com.sampark.PMS.object.ResponseObject;
import com.sampark.PMS.util.CommonUtils;
import com.sampark.PMS.util.DbUtils;

/**
 * Handles requests for the application home page.
 */
@Controller
public class DesignationController {

	private static final Logger logger = LoggerFactory.getLogger(DesignationController.class);
	 private static ResourceBundle messages = ResourceBundle.getBundle("messages");
	/**
	 * Simply selects the home view to render by returning its name.
	 */ 
	 
	 @RequestMapping(value = "/designation-list", method = RequestMethod.GET)
		public String designationList(Locale locale, Model model) {
			logMessage("designation-list", "");
			return "PMS/pages/designation/designation-list";
		}

	 @RequestMapping(value = "/get-all-designations", method = RequestMethod.POST)
		@ResponseBody
		public List<Designation> getAllDesignations() {
			logger.info("getAllDesignations - ");
			return DbUtils.getAllDesignationList();
		}
	
	 @RequestMapping(value = "/save-designation", method = RequestMethod.POST)
	 @ResponseBody
		public ResponseObject saveDesignation(@RequestBody RequestObject object) {
			logger.info("save-designation "+ object);
			return DbUtils.saveDesignation(object);
		}
	 @RequestMapping(value = "/delete-designation", method = RequestMethod.POST)
	 @ResponseBody
		public ResponseObject deleteDesignation(@RequestBody RequestObject object) {
			logger.info("delete-designation "+ object);
			return DbUtils.deleteDesignation(object.getId());
		}
	 private void logMessage(String action, String data) {
			try {
				logger.debug("[" + action + "] by [" + CommonUtils.getCurrentUserName() + "] " + data);
			} catch (Exception e) {
				logger.debug("Exception while logging action [" + action + "] message [" + data);
			}
		}
}
