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

import com.sampark.PMS.dto.Roles;
import com.sampark.PMS.object.RequestObject;
import com.sampark.PMS.object.ResponseObject;
import com.sampark.PMS.util.CommonUtils;
import com.sampark.PMS.util.DbUtils;

/**
 * Handles requests for the application home page.
 */
@Controller
public class ApplicationRoleController {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationRoleController.class);
	private static ResourceBundle messages = ResourceBundle.getBundle("messages");

	/**
	 * Simply selects the home view to render by returning its name.
	 */

	@RequestMapping(value = "/application_roles-list", method = RequestMethod.GET)
	public String applicationRoles(Locale locale, Model model) {
		logMessage("application_roles-list", "");
		return "PMS/pages/applicationRoles/application_roles-list";
	}

	@RequestMapping(value = "/get-all-applicationRoles-list", method = RequestMethod.POST)
	@ResponseBody
	public List<Roles> getAllApplicationRolesList() {
		logger.info("get-all-applicationRoles-list-");
		return DbUtils.getAllApplicationRolesList();
	}

	@RequestMapping(value = "/save-applicationRoles", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject saveApplicationRole(@RequestBody RequestObject object) {
		logger.info("save-applicationRoles" + object);
		return DbUtils.saveApplicationRoles(object);
	}

	@RequestMapping(value = "/delete-applicationRoles", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject deleteApplicationRoles(@RequestBody RequestObject object) {
		logger.info("delete-applicationRoles " + object);
		return DbUtils.deleteApplicationRoles(object.getId());
	}

	private void logMessage(String action, String data) {
		try {
			logger.debug("[" + action + "] by [" + CommonUtils.getCurrentUserName() + "] " + data);
		} catch (Exception e) {
			logger.debug("Exception while logging action [" + action + "] message [" + data);
		}
	}
}
