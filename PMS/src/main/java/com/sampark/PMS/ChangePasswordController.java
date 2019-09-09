package com.sampark.PMS;

import java.util.Locale;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sampark.PMS.object.RequestObject;
import com.sampark.PMS.object.ResponseObject;
import com.sampark.PMS.util.CommonUtils;
import com.sampark.PMS.util.DbUtils;
import com.sampark.PMS.util.ResetPasswordMail;

/**
 * Handles requests for the application home page.
 */
@Controller
public class ChangePasswordController {

	private static final Logger logger = LoggerFactory.getLogger(ChangePasswordController.class);
	private static ResourceBundle messages = ResourceBundle.getBundle("messages");
	
	@RequestMapping(value = "/change_password", method = RequestMethod.GET)
	public String changePassword(Locale locale, Model model) {
		logMessage("change_password", "");
		return "PMS/pages/changePassword/change_password";
	}
	
	@RequestMapping(value = "/reset_password", method = RequestMethod.GET)
	public String resetPassword(Locale locale, Model model,@RequestParam ("token") String token) {
		logMessage("reset_password", "");
		model.addAttribute("token", token);
		model.addAttribute("imagebasePath", PMSConstants.BASE_URL + "/images?image=logo.jpg&folder=images");
		return "PMS/pages/changePassword/reset_password";
	}
	
	 @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	 @ResponseBody
		public ResponseObject resetPassword(@RequestBody RequestObject object) {
			logger.info("resetPassword "+ object);
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			return DbUtils.resetPassword(passwordEncoder.encode(object.getPassword()),object.getToken(),object.getEmail());
		}
	 
	 @RequestMapping(value = "/forgetPassword", method = RequestMethod.POST)
	 @ResponseBody
		public ResponseObject forgetPassword(@RequestBody String email) {
			logger.info("forgetPassword "+ email);
			ResponseObject object = DbUtils.forgetPassword(email);
			if(object.getInteger()==1)
			{
				String url ="https://pms-hfe.com/PMS/reset_password?token="+object.getObject();
//				String url ="http://localhost:8080/PMS/reset_password?token="+object.getObject();
				ResetPasswordMail.send(email,url);
			}
			return object;
		}
	
	 @RequestMapping(value = "/changeUserPassword", method = RequestMethod.POST)
	 @ResponseBody
		public ResponseObject changeUserPassword(@RequestBody RequestObject object) {
			logger.info("changeUserPassword "+ object);
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			return DbUtils.changeUserPassword(object.getCurrentPassword(),passwordEncoder.encode(object.getPassword()));
		}
	 
	private void logMessage(String action, String data) {
		try {
			logger.debug("[" + action + "] by [" + CommonUtils.getCurrentUserName() + "] " + data);
		} catch (Exception e) {
			logger.debug("Exception while logging action [" + action + "] message [" + data);
		}
	}
}
