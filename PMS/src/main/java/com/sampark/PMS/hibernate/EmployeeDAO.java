package com.sampark.PMS.hibernate;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.sampark.PMS.dto.AppraisalCountDetails;
import com.sampark.PMS.dto.Employee;
import com.sampark.PMS.dto.EmployeeBasicDetails;
import com.sampark.PMS.dto.EmployeeCodeEmail;
import com.sampark.PMS.object.ResponseObject;

public interface EmployeeDAO {

	ResponseObject addEmployee(Employee object, Integer empId, String encryptedPassword, String userRandomPassword);
	@SuppressWarnings("rawtypes")
	List getAllEmployeeCodeList();
	ResponseObject getEmployeeAppraisalDetails(Integer appraisalYearId, String empCode);
	ResponseObject getAllEmployeeList();
	void addEmployeeBulk(List<Employee> object, HttpServletResponse response) throws IOException;
	ResponseObject deleteEmployee(Integer id);
	ResponseObject getCurrentEmployeeDetails(Integer empId);
	List<EmployeeCodeEmail> getAllEmployeeCode(Date eligibilityDate, Integer appraisalYearId);
	List<Integer> getAllEmployeeListId(Integer appraisalYearId, Date eligibilityDate);
	ResponseObject getAllSubEmployeeList(String empCode, String type, Integer appraisalYearId);
	Employee getCurrentEmployeeData(String empCode);
	List<EmployeeCodeEmail> getAllEmployeeAppraisalDetailsList(Integer appraisalYearId, Date eligibilityDate);
	
	@SuppressWarnings("rawtypes")
	List getSubEmployeeAppraisalCountDetails(String empCode, Integer appraisalYearId, String type, String stage);
	String getAppraiserName(String empCode);
	ResponseObject getEmployeePromotionDetails(Integer appraisalYearId, String empCode);
	String getEmployeeType(String empCode);
	ResponseObject getOverAllRatingSubEmployeeList(String empCode, String type, Integer appraisalYearId);
	
	ResponseObject getAllEmployeeListData();
	ResponseObject getAllDeletedEmployeeList();
	ResponseObject getAllActiveEmployeeListData();
	
	
	List<EmployeeBasicDetails> getEmployeeBasicDetails(Integer departmentId, Integer appraisalYearId, String empCode);
	ResponseObject getAllDetailsOfDeletedEmployeeList();
	ResponseObject getAllDetailsEmployeeList();
	
	
	

}
