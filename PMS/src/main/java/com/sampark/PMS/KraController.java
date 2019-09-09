package com.sampark.PMS;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.UUID;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sampark.PMS.object.KraNewSections;
import com.sampark.PMS.object.KraSections;
import com.sampark.PMS.object.RequestObject;
import com.sampark.PMS.object.ResponseObject;
import com.sampark.PMS.util.CommonUtils;
import com.sampark.PMS.util.DbUtils;

/**
 * Handles requests for the application home page.
 */
@Controller
public class KraController {

	private static final Logger logger = LoggerFactory.getLogger(KraController.class);
	private static ResourceBundle messages = ResourceBundle.getBundle("messages");

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	
	@RequestMapping(value = "/employee-kra", method = RequestMethod.GET)
	public String employeeKRA(Locale locale, Model model) {
		logMessage("employee-kra", "");
		return "PMS/pages/KRA/employee-kra";
	}

	@RequestMapping(value = "/employee-newKra", method = RequestMethod.GET)
	public String employeeNewKRA(Locale locale, Model model) {
		logMessage("employee-newKra", "");
		return "PMS/pages/KRA/employee-newKra";
	}
	
	@RequestMapping(value = "/employee-oldKra", method = RequestMethod.GET)
	public String employeeOldKRA(Locale locale, Model model) {
		logMessage("employee-oldKra", "");
		return "PMS/pages/KRA/employee-oldKra";
	}
	
	@RequestMapping(value = "/employee-kra-list", method = RequestMethod.GET)
	public String employeeKRAList(Locale locale, Model model) {
		logMessage("employee-kra-list", "");
		return "PMS/pages/KRA/employee-kra-list";
	}

	
	@RequestMapping(value = "/add-employee-kra-details", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject addEmployeeKraDetails( @RequestParam("kraSections") String kraSections,@RequestParam("list") List<Integer> list,
		@RequestParam("type") String type, @RequestParam(value="appraisalYearId",required=false)Integer appraisalYearId, @RequestParam("fileList") MultipartFile[] fileList,HttpServletResponse response) {
		Integer fileSize = null;
		Integer index = 0;
		fileSize = fileList.length;
		RequestObject res = new RequestObject();
		res.setAppraisalYearId(appraisalYearId);
		try
		{
		KraSections jsonObject = new ObjectMapper().readValue(kraSections, KraSections.class);
		logger.info("add-employee-kra-details " + jsonObject);
		for(int i =0;i< jsonObject.getSectionAList().size();i++)
		{
			if (fileSize > 0 && jsonObject.getSectionAList().get(i).getFileName() != null) {
				MultipartFile file = fileList[index];
				System.out.println("file.getOriginalFilename() \n "+ file.getOriginalFilename());
				if(jsonObject.getSectionAList().get(i).getFileName().equals(file.getOriginalFilename()))
				{
				try {
					byte[] bytes = file.getBytes();
					String filePath = PMSConstants.DOC_BASE_PATH;
					// Creating the directory to store file
					File dir = new File(filePath);
					if (!dir.exists())
						dir.mkdirs();
					// Create the file on server
					String name = UUID.randomUUID().toString()
							+ file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
					File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();
					System.out.println("Server File Location=" + serverFile.getAbsolutePath()
							+ "You successfully uploaded file=" + "<br />");
					
					jsonObject.getSectionAList().get(i).setFileName("/PMS/images?image="+name+"&folder=document&type=download");
					
					
					fileSize--;
					index++;
				} catch (Exception e) {
					System.out.println("Exception" + e);
				}
			} 
			}
		}
		for(int i =0;i< jsonObject.getSectionBList().size();i++)
		{
			if (fileSize > 0 && jsonObject.getSectionBList().get(i).getFileName() != null) {
				MultipartFile file = fileList[index];
				if(jsonObject.getSectionBList().get(i).getFileName().equals(file.getOriginalFilename()))
				{
				try {
					byte[] bytes = file.getBytes();
					String filePath = PMSConstants.DOC_BASE_PATH;
					// Creating the directory to store file
					File dir = new File(filePath);
					if (!dir.exists())
						dir.mkdirs();
					// Create the file on server
					String name = UUID.randomUUID().toString()
							+ file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
					File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();
					System.out.println("Server File Location=" + serverFile.getAbsolutePath()
							+ "You successfully uploaded file=" + "<br />");
					jsonObject.getSectionBList().get(i).setFileName("/PMS/images?image="+name+"&folder=document&type=download");
					fileSize--;
					index++;
				} catch (Exception e) {
					System.out.println("Exception" + e);
				}
			} 
			}
		}
		for(int i =0;i< jsonObject.getSectionCList().size();i++)
		{
			if (fileSize > 0 && jsonObject.getSectionCList().get(i).getFileName() != null) {
				MultipartFile file = fileList[index];
				if(jsonObject.getSectionCList().get(i).getFileName().equals(file.getOriginalFilename()))
				{
				try {
					byte[] bytes = file.getBytes();
					String filePath = PMSConstants.DOC_BASE_PATH;
					// Creating the directory to store file
					File dir = new File(filePath);
					if (!dir.exists())
						dir.mkdirs();
					// Create the file on server
					String name = UUID.randomUUID().toString()
							+ file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
					File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();
					System.out.println("Server File Location=" + serverFile.getAbsolutePath()
							+ "You successfully uploaded file=" + "<br />");
					jsonObject.getSectionCList().get(i).setFileName("/PMS/images?image="+name+"&folder=document&type=download");
					fileSize--;
					index++;
				} catch (Exception e) {
					System.out.println("Exception" + e);
				}
			} 
			}
		}
		for(int i =0;i< jsonObject.getSectionDList().size();i++)
		{
			if (fileSize > 0 && jsonObject.getSectionDList().get(i).getFileName() != null) {
				MultipartFile file = fileList[index];
				if(jsonObject.getSectionDList().get(i).getFileName().equals(file.getOriginalFilename()))
				{
				try {
					byte[] bytes = file.getBytes();
					String filePath = PMSConstants.DOC_BASE_PATH;
					// Creating the directory to store file
					File dir = new File(filePath);
					if (!dir.exists())
						dir.mkdirs();
					// Create the file on server
					String name = UUID.randomUUID().toString()
							+ file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
					File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();
					System.out.println("Server File Location=" + serverFile.getAbsolutePath()
							+ "You successfully uploaded file=" + "<br />");
					jsonObject.getSectionDList().get(i).setFileName("/PMS/images?image="+name+"&folder=document&type=download");
					fileSize--;
					index++;
				} catch (Exception e) {
					System.out.println("Exception" + e);
				}
			} 
			}
		}
		res.setKraDetails(jsonObject);
		res.setType(type);
		res.setList(list);
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
		ResponseObject result = DbUtils.addKraDetails(res, CommonUtils.getCurrentUserName(),PMSConstants.USER_EMPLOYEE);
		return result;
	}
	
	@RequestMapping(value = "/add-employee-new-kra-details", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject addEmployeeNewKraDetails( @RequestParam("kraSections") String kraSections,@RequestParam("list") List<Integer> list,
		@RequestParam("type") String type,@RequestParam("fileList") MultipartFile[] fileList,HttpServletResponse response) {
		Integer fileSize = null;
		Integer index = 0;
		fileSize = fileList.length;
		RequestObject res = new RequestObject();
		try
		{
		KraNewSections jsonObject = new ObjectMapper().readValue(kraSections, KraNewSections.class);
		logger.info("add-employee-new-kra-details " + jsonObject);
		for(int i =0;i< jsonObject.getSectionAList().size();i++)
		{
			if (fileSize > 0 && jsonObject.getSectionAList().get(i).getFileName() != null) {
				MultipartFile file = fileList[index];
				System.out.println("file.getOriginalFilename() \n "+ file.getOriginalFilename());
				if(jsonObject.getSectionAList().get(i).getFileName().equals(file.getOriginalFilename()))
				{
				try {
					byte[] bytes = file.getBytes();
					String filePath = PMSConstants.DOC_BASE_PATH;
					// Creating the directory to store file
					File dir = new File(filePath);
					if (!dir.exists())
						dir.mkdirs();
					// Create the file on server
					String name = UUID.randomUUID().toString()
							+ file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
					File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();
					System.out.println("Server File Location=" + serverFile.getAbsolutePath()
							+ "You successfully uploaded file=" + "<br />");
					
					jsonObject.getSectionAList().get(i).setFileName("/PMS/images?image="+name+"&folder=document&type=download");
					fileSize--;
					index++;
				} catch (Exception e) {
					System.out.println("Exception" + e);
				}
			} 
			}
		}
		for(int i =0;i< jsonObject.getSectionBList().size();i++)
		{
			if (fileSize > 0 && jsonObject.getSectionBList().get(i).getFileName() != null) {
				MultipartFile file = fileList[index];
				if(jsonObject.getSectionBList().get(i).getFileName().equals(file.getOriginalFilename()))
				{
				try {
					byte[] bytes = file.getBytes();
					String filePath = PMSConstants.DOC_BASE_PATH;
					// Creating the directory to store file
					File dir = new File(filePath);
					if (!dir.exists())
						dir.mkdirs();
					// Create the file on server
					String name = UUID.randomUUID().toString()
							+ file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
					File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();
					System.out.println("Server File Location=" + serverFile.getAbsolutePath()
							+ "You successfully uploaded file=" + "<br />");
					jsonObject.getSectionBList().get(i).setFileName("/PMS/images?image="+name+"&folder=document&type=download");
					fileSize--;
					index++;
				} catch (Exception e) {
					System.out.println("Exception" + e);
				}
			} 
			}
		}
		for(int i =0;i< jsonObject.getSectionCList().size();i++)
		{
			if (fileSize > 0 && jsonObject.getSectionCList().get(i).getFileName() != null) {
				MultipartFile file = fileList[index];
				if(jsonObject.getSectionCList().get(i).getFileName().equals(file.getOriginalFilename()))
				{
				try {
					byte[] bytes = file.getBytes();
					String filePath = PMSConstants.DOC_BASE_PATH;
					// Creating the directory to store file
					File dir = new File(filePath);
					if (!dir.exists())
						dir.mkdirs();
					// Create the file on server
					String name = UUID.randomUUID().toString()
							+ file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
					File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();
					System.out.println("Server File Location=" + serverFile.getAbsolutePath()
							+ "You successfully uploaded file=" + "<br />");
					jsonObject.getSectionCList().get(i).setFileName("/PMS/images?image="+name+"&folder=document&type=download");
					fileSize--;
					index++;
				} catch (Exception e) {
					System.out.println("Exception" + e);
				}
			} 
			}
		}
		for(int i =0;i< jsonObject.getSectionDList().size();i++)
		{
			if (fileSize > 0 && jsonObject.getSectionDList().get(i).getFileName() != null) {
				MultipartFile file = fileList[index];
				if(jsonObject.getSectionDList().get(i).getFileName().equals(file.getOriginalFilename()))
				{
				try {
					byte[] bytes = file.getBytes();
					String filePath = PMSConstants.DOC_BASE_PATH;
					// Creating the directory to store file
					File dir = new File(filePath);
					if (!dir.exists())
						dir.mkdirs();
					// Create the file on server
					String name = UUID.randomUUID().toString()
							+ file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
					File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();
					System.out.println("Server File Location=" + serverFile.getAbsolutePath()
							+ "You successfully uploaded file=" + "<br />");
					jsonObject.getSectionDList().get(i).setFileName("/PMS/images?image="+name+"&folder=document&type=download");
					fileSize--;
					index++;
				} catch (Exception e) {
					System.out.println("Exception" + e);
				}
			} 
			}
		}
		res.setKraNewDetails(jsonObject);
		res.setType(type);
		res.setList(list);
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
		ResponseObject result = DbUtils.addNewKraDetails(res, CommonUtils.getCurrentUserName(),PMSConstants.USER_EMPLOYEE);
		return result;
	}
	
	
	@RequestMapping(value = "/copyOldKRAtoNewKRA", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject copyOldKRAtoNewKRA(@RequestBody RequestObject object) {
		logger.info("copyOldKRAtoNewKRA" + object);
		return DbUtils.copyOldKRAtoNewKRA(object);
	}
	
	
	@RequestMapping(value = "/add-manager-kra-details", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject addManagerKraDetails(@RequestBody RequestObject object) {
		logger.info("add-manager-kra-details " + object);
		ResponseObject result = new ResponseObject();
		if(object.getKraNewDetails() != null)
		{
			result = DbUtils.addNewKraDetails(object,object.getEmpCode(),object.getName());
		}
		else {
	    result = DbUtils.addKraDetails(object,object.getEmpCode(),object.getName());
		}
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
