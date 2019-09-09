package com.sampark.PMS;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sampark.PMS.dto.Department;
import com.sampark.PMS.object.RequestObject;
import com.sampark.PMS.object.ResponseObject;
import com.sampark.PMS.util.CommonUtils;
import com.sampark.PMS.util.DbUtils;

/**
 * Handles requests for the application home page.
 */
@Controller
public class DepartmentController {

	private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);
	 private static ResourceBundle messages = ResourceBundle.getBundle("messages");
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	 
	 @RequestMapping(value = "/department-list", method = RequestMethod.GET)
		public String departmentList(Locale locale, Model model) {
			logMessage("department-list", "");
			return "PMS/pages/department/department-list";
		}
	 @CrossOrigin
	 @RequestMapping(value = "/get-all-departments", method = RequestMethod.POST)
		@ResponseBody
		public List<Department> getAllDepartment() {
			logger.info("getAllDepartment - ");
			return DbUtils.getAllDepartmentList();
		}
	 
	 @RequestMapping(value = "/save-department", method = RequestMethod.POST)
	 @ResponseBody
		public ResponseObject saveDepartment(@RequestBody RequestObject object) {
			logger.info("save-department "+ object);
			return DbUtils.saveDepartment(object);
		}
	 @RequestMapping(value = "/delete-department", method = RequestMethod.POST)
	 @ResponseBody
		public ResponseObject deleteDepartment(@RequestBody RequestObject object) {
			logger.info("delete-department "+ object);
			return DbUtils.deleteDepartment(object.getId());
		}
	 private void logMessage(String action, String data) {
			try {
				logger.debug("[" + action + "] by [" + CommonUtils.getCurrentUserName() + "] " + data);
			} catch (Exception e) {
				logger.debug("Exception while logging action [" + action + "] message [" + data);
			}
		}
}
