package com.sampark.PMS.hibernate;

import java.util.List;

import com.sampark.PMS.dto.Parameters;
import com.sampark.PMS.object.RequestObject;
import com.sampark.PMS.object.ResponseObject;
public interface CommonDAO {
	
	String getApplicationVersion();

	Integer getNextId(String sequenceName) throws Exception;
	
//	ResponseObject getLoggedUserDetails(String empCode);

//	Long getCurrentUserSubEmployeeCount(String empCode);
	Long getCurrentUserSubEmployeeCount(String empCode, String superiorLevel);

	ResponseObject getAllTeamMembers(String empCode, String type, Integer appraisalYearId);

	List<Parameters> getAllParameters();

	ResponseObject getAllParametersData(Integer id);

	Integer getCurrentUserId(String empCode);

	ResponseObject isUserFirstTime(String empCode, Integer appraisalYearId);

	ResponseObject changeUserPassword(String currentPassword, String password);

	ResponseObject forgetPassword(String email);

	ResponseObject resetPassword(String password, String token, String email);

	String getCurrentUserRoleId(String empCode);

	Integer getEmployeeAppraisalYearDetails(Integer activeAppraisalYearId, String empCode);

	String getFirstLevelManagerId(String empCode);

	String getSecondLevelManagerId(String empCode);

	ResponseObject getEmployeeOverAllRating(Integer appraisalYearId, String empCode);

	String getEmployeeName(String currentUserName);

	boolean isCurrentEmployeeHead(String currentUserName, String organizationRole);

	
	
}
