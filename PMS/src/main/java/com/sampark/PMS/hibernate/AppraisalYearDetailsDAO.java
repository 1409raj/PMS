package com.sampark.PMS.hibernate;

import java.util.List;

import com.sampark.PMS.dto.AppraisalCountDetails;
import com.sampark.PMS.object.RequestObject;
import com.sampark.PMS.object.ResponseObject;

public interface AppraisalYearDetailsDAO {

	ResponseObject getAppraisalYearDetails();

//	ResponseObject appraisalYearKraSubmission(Integer appraisalYearId);

//	ResponseObject getAppraisalCycleDetails(Integer appraisalYearId);

//	ResponseObject appraisalYearMidYearSubmission(Integer appraisalYearId);
//
//	ResponseObject appraisalYearFinalYearSubmission(Integer appraisalYearId);

	ResponseObject appraisalCycleStart(RequestObject object);

	ResponseObject updateEmployeeAppraisal(Integer status, Integer appraisalYearDetailsId, Integer appraisalYearId);

	ResponseObject getEmployeeAppraisalList(Integer appraisalYearId);

	ResponseObject sendToManager(String managerType,String buttonType, String empCode, Integer appraisalYearId);

	ResponseObject getUserAppraisalDetails(String empCode, Integer appraisalYearId, String type);

	ResponseObject employeeAcknowledgement(String empCode, Integer appraisalYearId);

	ResponseObject getAppraisalPendingEmployeeDetails(String empCode, Integer appraisalYearId, String managerType,
			String type);

	@SuppressWarnings("rawtypes")
	List getOverAllAppraisalCountDetails(Integer appraisalYearId, String appraisalStage);

	@SuppressWarnings("rawtypes")
	List getOverAllDepartmentAppraisalCountDetails(Integer appraisalYearId, String appraisalStage);

	ResponseObject getALLAppraisalPendingEmployeeDetails(Integer appraisalYearId, String type, Integer departmentId);

	@SuppressWarnings("rawtypes")
	List getAllDepartmentHRDashBoardDetails(Integer appraisalYearId);

	ResponseObject getSelectedAppraisalYearDetails(String appraisalStage);

	Integer isUserAcknowledged(String empCode, Integer appraisalYearId);

	ResponseObject changeEmployeeAppraisalStage(RequestObject object);

	ResponseObject carryForwardEmployeeKRADATAToCurrentYear(RequestObject object);

	ResponseObject employeeApprovalProcess(RequestObject object);

	ResponseObject teamRating(RequestObject object);
}
