package com.sampark.PMS.hibernate;

import java.util.List;

import com.sampark.PMS.dto.EmployeeKRAData;
import com.sampark.PMS.dto.EmployeeKRADetails;
import com.sampark.PMS.object.RequestObject;
import com.sampark.PMS.object.ResponseObject;

public interface KraDetailsDAO {

	ResponseObject addKraDetails(RequestObject object, String empCode, String userType);
	ResponseObject getCurrentEmployeeKRADetails(String empCode, Integer appraisalYearId, String kraScreen);
	List<EmployeeKRAData> getValidatedKRADetails(String empCode, Integer appraisalYearId, String managerType);
	ResponseObject forwardEmployeeKRADATAToCurrentYear(String empCode, Integer appraisalYearId);
	List<EmployeeKRADetails> getEmployeeKRADetails(String empCode, Integer appraisalYearId);
	ResponseObject getAllDepartmentWiseKRAList(Integer departmentId, Integer appraisalYearId, String empCode);
//	ResponseObject getNewKRADetails(String empCode, Integer appraisalYearId);
	ResponseObject addNewKraDetails(RequestObject object, String empCode, String userType);
	ResponseObject getCurrentEmployeeOldKRADetails(String empCode, Integer appraisalYearId);
	ResponseObject getCurrentEmployeeNewKRADetails(String empCode, Integer appraisalYearId);
	List<Integer> getValidatedNewKRADetails(String empCode, Integer appraisalYearId, String managerType);
	ResponseObject copyOldKRAtoNewKRA(RequestObject object);
	

}
