package com.sampark.PMS;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Locale;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sampark.PMS.object.ResponseObject;
import com.sampark.PMS.util.CommonUtils;

/**
 * Handles requests for the application home page.
 */
@Controller
public class ChangeLogoController {

	private static final Logger logger = LoggerFactory.getLogger(ChangeLogoController.class);
	private static ResourceBundle messages = ResourceBundle.getBundle("messages");

	@RequestMapping(value = "/change-logo", method = RequestMethod.GET)
	public String changeLogo(Locale locale, Model model) {
		logMessage("change-logo", "");
		return "PMS/pages/logo/change-logo";
	}
	
	@RequestMapping(value = "/changeLogo", method = RequestMethod.POST)
	public @ResponseBody ResponseObject uploadFileHandler(@RequestParam("file") MultipartFile file) {
		ResponseObject object = new ResponseObject();
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				// Creating the directory to store file
				String filePath = PMSConstants.IMAGE_BASE_PATH;
				File dir = new File(filePath);
				if (!dir.exists())
					dir.mkdirs();
				// Create the file on server
				String name = "logo"
						+ file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
				File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				logger.info("Server File Location=" + serverFile.getAbsolutePath());
				object.setString("You successfully changed the logo=" + name);
				object.setInteger(PMSConstants.STATUS_SUCCESS);
			} catch (Exception e) {
				object.setString("You failed to upload "+ " => " + e.getMessage());
				object.setInteger(PMSConstants.STATUS_FAILED);
			}
		} else {
			object.setString( "You failed to upload " + " because the file was empty.");
			object.setInteger(PMSConstants.STATUS_FAILED);
		}
		return object;
	}

	private void logMessage(String action, String data) {
		try {
			logger.debug("[" + action + "] by [" + CommonUtils.getCurrentUserName() + "] " + data);
		} catch (Exception e) {
			logger.debug("Exception while logging action [" + action + "] message [" + data);
		}
	}
}
