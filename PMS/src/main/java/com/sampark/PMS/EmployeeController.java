package com.sampark.PMS;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.UUID;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sampark.PMS.dto.Employee;
import com.sampark.PMS.object.RequestObject;
import com.sampark.PMS.object.ResponseObject;
import com.sampark.PMS.util.CommonUtils;
import com.sampark.PMS.util.DbUtils;
import com.sampark.PMS.util.RandomPassword;

/**
 * Handles requests for the application home page.
 */
@Controller
public class EmployeeController {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	private static ResourceBundle messages = ResourceBundle.getBundle("messages");

	@RequestMapping(value = "/employee-list", method = RequestMethod.GET)
	public String employeeList(Locale locale, Model model) {
		logMessage("employee-list", "");
		return "PMS/pages/employee/employee-list";
	}
	
	
	@RequestMapping(value = "/employeeApprovalProcess", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject employeeApprovalProcess(@RequestBody RequestObject object) {
		logger.info("employeeApprovalProcess - ");
		return DbUtils.employeeApprovalProcess(object);
	}
	
	@RequestMapping(value = "/get-all-team-members", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject getAllTeamMembers(@RequestBody RequestObject object) {
		logger.info("get-all-team-members - ");
		return DbUtils.getAllTeamMembers(object.getEmpCode(),object.getType(),object.getAppraisalYearId());
	}
		
	@RequestMapping(value = "/employee-appraisal-list", method = RequestMethod.GET)
	public String employeeAppraisalList(Locale locale, Model model) {
		logMessage("employee-appraisal-list", "");
		return "PMS/pages/employee/employee-appraisal-list";
	}
	@RequestMapping(value = "/add-employee", method = RequestMethod.GET)
	public String addEmployee(Locale locale, Model model) {
		logMessage("add-employee", "");
		return "PMS/pages/employee/add-employee";
	}
	
	@RequestMapping(value = "/modify-employee", method = RequestMethod.GET)
	public String modifyEmployee(Locale locale, Model model) {
		logMessage("modify-employee", "");
		return "PMS/pages/employee/modify-employee";
	}

	@RequestMapping(value = "/employee-form-configuration", method = RequestMethod.GET)
	public String employeeFormConfiguration(Locale locale, Model model) {
		logMessage("employee-form-configuration", "");
		return "PMS/pages/employee/employee-form-configuration";
	}

	@RequestMapping(value = "/employee-bulk", method = RequestMethod.GET)
	public String bulkEmployee(Locale locale, Model model) {
		logMessage("employee-bulk", "");
		return "PMS/pages/employee/employee-bulk";
	}
	
	@RequestMapping(value = "/view-employee", method = RequestMethod.GET)
	public String viewEmployee(Locale locale, Model model) {
		logMessage("view-employee", "");
		return "PMS/pages/employee/view-employee";
	}

	@RequestMapping(value = "/save-column", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject saveColumn(@RequestBody RequestObject object) {
		logger.info("save-column " + object.getDescription(), object.getName(), object.getId());
		return DbUtils.saveColumn(object.getDescription(), object.getName(), object.getId(),
				object.getAppraisalYearId());
	}

	@RequestMapping(value = "/activate_column", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject activateColumn(@RequestBody RequestObject object) {
		logger.info("activate_column " + object.getAppraisalYearId(), object.getId());
		return DbUtils.activateColumn(object.getAppraisalYearId(), object.getId());
	}

	@RequestMapping(value = "/delete-column", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject deleteColumn(@RequestBody RequestObject object) {
		logger.info("delete-column " + object.getId());
		return DbUtils.deleteColumn(object.getId(), object.getAppraisalYearId());
	}
	

	@RequestMapping(value = "/delete-employee", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject deleteEmployee(@RequestBody RequestObject object) {
		logger.info("delete-employee " + object.getId());
		return DbUtils.deleteEmployee(object.getId());
	}

	@RequestMapping(value = "/add-new-employee", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject saveQuestionPaper(
			@RequestParam(value = "empId", required = false) Integer empId,
			@RequestParam(value = "empName", required = false) String empName,
			@RequestParam(value = "empCode", required = false) String empCode,
			@RequestParam(value = "empType", required = false) String empType,
			@RequestParam(value = "company", required = false) String company,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "dateOfJoining", required = false) Date dateOfJoining,
			@RequestParam(value = "dateOfBirth", required = false) Date dateOfBirth,
			@RequestParam(value = "jobDescription", required = false) String jobDescription,
			@RequestParam(value = "departmentId", required = false) Integer departmentId,
			@RequestParam(value = "organizationRoleId", required = false) Integer organizationRoleId,
			@RequestParam(value = "roleId", required = false) Integer roleId,
			@RequestParam(value = "designationId", required = false) Integer designationId,
			@RequestParam(value = "qualification", required = false) String qualification,
			@RequestParam(value = "mobile", required = false) String mobile,
			@RequestParam(value = "location", required = false) String location,
			@RequestParam(value = "firstLevelSuperiorEmpId", required = false) String firstLevelSuperiorEmpId,
			@RequestParam(value = "secondLevelSuperiorEmpId", required = false) String secondLevelSuperiorEmpId,
//			@RequestParam(value = "firstLevelSuperiorName", required = false) String firstLevelSuperiorName,
//			@RequestParam(value = "secondLevelSuperiorName", required = false) String secondLevelSuperiorName,
//			@RequestParam(value = "appraisalYearId", required = false) Integer appraisalYearId,
			@RequestParam(value = "fields", required = false) List<String> list, //dynamic Form Fields
			@RequestParam(value=  "file1" , required = false) MultipartFile file)
			throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		logger.info("add-new-employee - ");
		String name = null;
		ResponseObject result = new ResponseObject();
		if(file!= null)
		{
		try {

			byte[] bytes = file.getBytes();
			String filePath = PMSConstants.DOC_BASE_PATH;
			// Creating the directory to store file
			File dir = new File(filePath);
			if (!dir.exists())
				dir.mkdirs();
			// Create the file on server
			name = UUID.randomUUID().toString()
					+ file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
			File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
			stream.write(bytes);
			stream.close();
			System.out.println("Server File Location=" + serverFile.getAbsolutePath()
					+ "You successfully uploaded file=" + "<br />");
		} catch (Exception e) {
			System.out.println("Exception" + e);
		}
		}
		
		Employee object = new Employee();
		object.setEmpCode(empCode);
		object.setRoleId(roleId);
		object.setEmpName(empName);
		object.setEmail(email);
		object.setDateOfJoining(dateOfJoining);
		object.setJobDescription(jobDescription);
		object.setDepartmentId(departmentId);
		object.setOrganizationRoleId(organizationRoleId);
		object.setApplicationRoleId(roleId);
		object.setDesignationId(designationId);
		object.setQualification(qualification);
		object.setMobile(mobile);
		object.setCompany(company);
		object.setDateOfBirth(dateOfBirth);
		object.setEmpType(empType);
		object.setLocation(location);
		object.setFirstLevelSuperiorEmpId(firstLevelSuperiorEmpId);
		object.setSecondLevelSuperiorEmpId(secondLevelSuperiorEmpId);
		object.setDocument("/pms/images?image="+name+"&folder=document");
		if(list!= null)
		{
		for(int i=0;i<list.size();i++)
		{	
		switch(i)
		{
		    case 0 : object.setField1(list.get(i).toString());
		    break; 
		    case 1 :object.setField2(list.get(i).toString());
		    break; 
		    case 2 :object.setField3(list.get(i).toString());
			break; 
		    case 3 :object.setField4(list.get(i).toString());
			break; 
		    case 4 :object.setField5(list.get(i).toString());
			break; 
		    case 5 :object.setField6(list.get(i).toString());
			break;
		    case 6 :object.setField7(list.get(i).toString());
			break;
		    case 7 :object.setField8(list.get(i).toString());
			break;
		    case 8 :object.setField9(list.get(i).toString());
			break;
		    case 9 :object.setField10(list.get(i).toString());
			break;
		   
			
		}
		}
		}
		String userRandomPassword = RandomPassword.geek_Password(10).toString();
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		result = DbUtils.addEmployee(object,empId, passwordEncoder.encode(userRandomPassword),userRandomPassword);
		return result;
	}

	@RequestMapping(value = "/add-employee-bulk", method = RequestMethod.POST)
	@ResponseBody
	public void saveEmployee(@RequestParam("file1") MultipartFile file,HttpServletResponse response)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, ParseException, IOException {
		logger.info("add-employee-bulk" + file);
		BufferedReader br;
		List<Employee> result = new ArrayList<Employee>();
	
		try {
			String line;
			InputStream is = file.getInputStream();
			br = new BufferedReader(new InputStreamReader(is));
			boolean check = true;
			String[] employees;
			while ((line = br.readLine()) != null) {
				if (check == true) {
					employees = line.split(",");
					check = false;
				} else {
					Employee employee =  new Employee();
					employees = line.split(",");
					System.out.println("\n String Bulk array Testinggggggggg ------>"+employees[1]);
					employee.setEmpCode(employees[0]);
					employee.setEmpName(employees[1]);
					employee.setMobile(employees[2]);
					employee.setEmail(employees[3]);
					DateFormat formatter;
					Date date1;
					formatter = new SimpleDateFormat("dd-MMM-yy");
					date1 = formatter.parse(employees[4]);
//					Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse();
					employee.setDateOfJoining(date1);
					employee.setJobDescription(employees[5]);
					employee.setDepartmentId(Integer.valueOf(employees[6]));
					employee.setOrganizationRoleId(Integer.valueOf(employees[7]));
					employee.setRoleId(Integer.valueOf(employees[8]));
					employee.setDesignationId(Integer.valueOf(employees[9]));
					employee.setQualification(employees[10]);
					employee.setLocation(employees[11]);
					employee.setFirstLevelSuperiorEmpId(employees[12]);
					employee.setSecondLevelSuperiorEmpId(employees[13]);
					employee.setEmpType(employees[14]);
//					employee.setJobDescription(employees[15]);
					employee.setCompany(employees[16]);
					employee.setDocument(PMSConstants.BLANK_DOC);
//					employee.setSecondLevelSuperiorName(employees[15]);
//					employee.setAppraisalYearId(Integer.valueOf(employees[14]));
					employee.setStatus(PMSConstants.STATUS_ACTIVE);
					employee.setCreatedOn(new Date());
					result.add(employee);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	     DbUtils.addEmployeeBulk(result,response);
	}
	
	@RequestMapping(value = "/get_current_employee_details", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject getCurrentEmployeeDetails(@RequestBody Integer empId) {
		logger.info("get_current_employee_details - " + empId);
		return DbUtils.getCurrentEmployeeDetails(empId);
	}
	
	@RequestMapping(value = "/getBasicEmployeeDetails", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject getBasicEmployeeDetails(@RequestBody RequestObject object) {
		logger.info("getBasicEmployeeDetails - " + object.getEmpCode());
		return DbUtils.getCurrentEmployeeDetails(DbUtils.getCurrentUserId(object.getEmpCode()));
	}
	
	
	
	@RequestMapping(value = "/getAllEmployeeListData", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject getAllEmployeeListData() {
		logger.info("getAllEmployeeListData - ");
		return DbUtils.getAllEmployeeListData();
	}
	
	@RequestMapping(value = "/getAllDeletedEmployeeList", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject getAllDeletedEmployeeList() {
		logger.info("getAllDeletedEmployeeList - ");
		return DbUtils.getAllDeletedEmployeeList();
	}
	
	@RequestMapping(value = "/getAllActiveEmployeeList", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject getAllActiveEmployeeList() {
		logger.info("getAllDeletedEmployeeList - ");
		return DbUtils.getAllActiveEmployeeListData();
	}
	
	
	@CrossOrigin(origins="*", allowedHeaders = "*")
	@RequestMapping(value = "/get-all-employeeList", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject getAllEmployeeList() {
		logger.info("get-all-employeeList - ");
		return DbUtils.getAllEmployeeList();
	}
	
	@CrossOrigin(origins="*", allowedHeaders = "*")
	@RequestMapping(value = "/get-all-deleted-employeeList", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject getAllDetailsOfDeletedEmployeeList() {
		logger.info("get-all-deleted-employeeList");
		return DbUtils.getAllDetailsOfDeletedEmployeeList();
	}
	
	
	
	@CrossOrigin(origins="*", allowedHeaders = "*")
	@RequestMapping(value = "/get-alldetails-employeeList", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject getAllDetailsEmployeeList() {
		logger.info("get-alldetails-employeeList - ");
		return DbUtils.getAllDetailsEmployeeList();
	}
	

	@RequestMapping(value = "/get-all-subEmployeeList", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject getAllSubEmployeeList(@RequestBody RequestObject object) {
		logger.info("get-all-subEmployeeList - "+ object.getEmpCode());
		return DbUtils.getAllSubEmployeeList(object.getEmpCode(),object.getType(),object.getAppraisalYearId());
	}
	
	@RequestMapping(value = "/get-overAllRating-subEmployeeList", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject getOverAllRatingSubEmployeeList(@RequestBody RequestObject object) {
		logger.info("get-overAllRating-subEmployeeList - "+ object.getEmpCode());
		return DbUtils.getOverAllRatingSubEmployeeList(object.getEmpCode(),object.getType(),object.getAppraisalYearId());
	}
	
	@RequestMapping(value = "/get-employee-appraisal-details", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject getEmployeeAppraisalDetails(@RequestBody RequestObject object) {
		logger.info("get-employee-appraisal-details - "+ object.getAppraisalYearId());
		return DbUtils.getEmployeeAppraisalDetails(object.getAppraisalYearId(),object.getEmpCode());
	}
	@RequestMapping(value = "/getEmployeePromotionDetails", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject getEmployeePromotionDetails(@RequestBody RequestObject object) {
		logger.info("getEmployeePromotionDetails - "+ object.getAppraisalYearId());
		return DbUtils.getEmployeePromotionDetails(object.getAppraisalYearId(),object.getEmpCode());
	}
	private void logMessage(String action, String data) {
		try {
			logger.debug("[" + action + "] by [" + CommonUtils.getCurrentUserName() + "] " + data);
		} catch (Exception e) {
			logger.debug("Exception while logging action [" + action + "] message [" + data);
		}
	}
}
