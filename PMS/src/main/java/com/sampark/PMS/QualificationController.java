package com.sampark.PMS;
//package com.sampark.PMS;
//
//import java.util.List;
//import java.util.Locale;
//import java.util.ResourceBundle;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.sampark.PMS.dto.Qualification;
//import com.sampark.PMS.object.RequestObject;
//import com.sampark.PMS.object.ResponseObject;
//import com.sampark.PMS.util.CommonUtils;
//import com.sampark.PMS.util.DbUtils;
//
///**
// * Handles requests for the application home page.
// */
//@Controller
//public class QualificationController {
//
//	private static final Logger logger = LoggerFactory.getLogger(QualificationController.class);
//	 private static ResourceBundle messages = ResourceBundle.getBundle("messages");
//	/**
//	 * Simply selects the home view to render by returning its name.
//	 */
//	 @RequestMapping(value = "/qualification-list", method = RequestMethod.GET)
//		public String qualificationList(Locale locale, Model model) {
//			logMessage("qualification-list", "");
//			return "PMS/pages/qualification/qualification-list";
//		}
//	 
//	 @RequestMapping(value = "/get-all-qualifications", method = RequestMethod.POST)
//		@ResponseBody
//		public List<Qualification> getAllQualifications() {
//			logger.info("getAllQualifications - ");
//			return DbUtils.getAllQualificationList();
//		}
//	 @RequestMapping(value = "/save-qualification", method = RequestMethod.POST)
//	 @ResponseBody
//		public ResponseObject saveQualification(@RequestBody RequestObject object) {
//			logger.info("save-qualification"+ object);
//			return DbUtils.saveQualification(object);
//		}
//	 @RequestMapping(value = "/delete-qualification", method = RequestMethod.POST)
//	 @ResponseBody
//		public ResponseObject deleteQualification(@RequestBody RequestObject object) {
//			logger.info("delete-qualification "+ object);
//			return DbUtils.deleteQualification(object.getId());
//		}
//	 private void logMessage(String action, String data) {
//			try {
//				logger.debug("[" + action + "] by [" + CommonUtils.getCurrentUserName() + "] " + data);
//			} catch (Exception e) {
//				logger.debug("Exception while logging action [" + action + "] message [" + data);
//			}
//		}
//}
