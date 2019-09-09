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

import com.sampark.PMS.dto.EmployeePromotions;
import com.sampark.PMS.object.RequestObject;
import com.sampark.PMS.object.ResponseObject;
import com.sampark.PMS.util.CommonUtils;
import com.sampark.PMS.util.DbUtils;

/**
 * Handles requests for the application home page.
 */
@Controller
public class EmployeePromotionController {

	private static final Logger logger = LoggerFactory.getLogger(EmployeePromotionController.class);
	 private static ResourceBundle messages = ResourceBundle.getBundle("messages");
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	 
	 @RequestMapping(value = "/employee-promotion", method = RequestMethod.GET)
		public String employeePromotion(Locale locale, Model model) {
			logMessage("employee-promotion", "");
			return "PMS/pages/promotion/employee-promotion";
		}
	 @RequestMapping(value = "/hrEmployeePromotion", method = RequestMethod.GET)
		public String hrEmployeePromotion(Locale locale, Model model) {
		   logMessage("hrEmployeePromotion", "");
			return "PMS/pages/promotion/hrEmployeePromotion";
		}
	 @RequestMapping(value = "/ceoEmployeePromotion", method = RequestMethod.GET)
		public String ceoEmployeePromotion(Locale locale, Model model) {
		    logMessage("ceoEmployeePromotion", "");
			return "PMS/pages/promotion/ceoEmployeePromotion";
		}
	 
//	 @RequestMapping(value = "/get-all-departments", method = RequestMethod.POST)
//		@ResponseBody
//		public List<Department> getAllDepartment() {
//			logger.info("getAllDepartment - ");
//			return DbUtils.getAllDepartmentList();
//		}
//	 
	 @RequestMapping(value = "/saveEmployeePromotiondetails", method = RequestMethod.POST)
	 @ResponseBody
		public ResponseObject saveEmployeePromotiondetails(@RequestBody EmployeePromotions object) {
			logger.info("saveEmployeePromotiondetails "+ object.toString());
			return DbUtils.saveEmployeePromotiondetails(object);
		}
	 @RequestMapping(value = "/rejectEmployeePromotiondetails", method = RequestMethod.POST)
	 @ResponseBody
		public ResponseObject rejectEmployeePromotiondetails(@RequestBody RequestObject object) {
			logger.info("rejectEmployeePromotiondetails ");
			return DbUtils.rejectEmployeePromotiondetails(object);
		}
//	 @RequestMapping(value = "/delete-department", method = RequestMethod.POST)
//	 @ResponseBody
//		public ResponseObject deleteDepartment(@RequestBody RequestObject object) {
//			logger.info("delete-department "+ object);
//			return DbUtils.deleteDepartment(object.getId());
//		}
	 private void logMessage(String action, String data) {
			try {
				logger.debug("[" + action + "] by [" + CommonUtils.getCurrentUserName() + "] " + data);
			} catch (Exception e) {
				logger.debug("Exception while logging action [" + action + "] message [" + data);
			}
		}
}
