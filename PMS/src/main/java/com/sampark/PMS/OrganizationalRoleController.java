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

import com.sampark.PMS.dto.OrganizationRoles;
import com.sampark.PMS.object.RequestObject;
import com.sampark.PMS.object.ResponseObject;
import com.sampark.PMS.util.CommonUtils;
import com.sampark.PMS.util.DbUtils;

/**
 * Handles requests for the application home page.
 */
@Controller
public class OrganizationalRoleController {

	private static final Logger logger = LoggerFactory.getLogger(OrganizationalRoleController.class);
	 private static ResourceBundle messages = ResourceBundle.getBundle("messages");
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	 @RequestMapping(value = "/organization_roles-list", method = RequestMethod.GET)
		public String organizationRolesList(Locale locale, Model model) {
			logMessage("organization_roles-list", "");
			return "PMS/pages/organizationRoles/organization_roles-list";
		}
	 
	@RequestMapping(value = "/get-all-organization-roles", method = RequestMethod.POST)
	@ResponseBody
	public List<OrganizationRoles> getAllOrganizationRolesList(@RequestBody Integer departmentId) {
		logger.info("get-all-Organization-roles - "+departmentId);
		return DbUtils.getAllOrganizationRolesList(departmentId);
	}
		
	 @RequestMapping(value = "/save-organization-roles", method = RequestMethod.POST)
	 @ResponseBody
		public ResponseObject saveOrganizationRoles(@RequestBody RequestObject object) {
			logger.info("save-organization-roles "+ object);
			return DbUtils.saveOrganizationRoles(object);
		}
	 
	 @RequestMapping(value = "/delete-organization-roles", method = RequestMethod.POST)
	 @ResponseBody
		public ResponseObject deleteOrganizationRoles(@RequestBody RequestObject object) {
			logger.info("delete-organization-roles "+ object);
			return DbUtils.deleteOrganizationRoles(object.getId(),object.getDepartmentId());
		}
	 
	 private void logMessage(String action, String data) {
			try {
				logger.debug("[" + action + "] by [" + CommonUtils.getCurrentUserName() + "] " + data);
			} catch (Exception e) {
				logger.debug("Exception while logging action [" + action + "] message [" + data);
			}
		}
}
