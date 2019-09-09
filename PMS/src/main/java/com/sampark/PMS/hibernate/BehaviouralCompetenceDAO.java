package com.sampark.PMS.hibernate;

import java.util.List;

import com.sampark.PMS.dto.BehaviouralCompetence;
import com.sampark.PMS.dto.BehaviouralCompetenceDetails;
import com.sampark.PMS.dto.EmployeeBehaviouralCompetenceDetails;
import com.sampark.PMS.object.RequestObject;
import com.sampark.PMS.object.ResponseObject;

public interface BehaviouralCompetenceDAO {

	ResponseObject saveBehavioralComptenceDetails(RequestObject object, String empCode, String userType);
	List<BehaviouralCompetence> getCurrentBehavioralCompetenceList(Integer appraisalYearId, String type);
	ResponseObject saveBehavioralComptence(RequestObject object);
	ResponseObject deleteBehavioralComptence(Integer id, Integer appraisalYearId, String type);
	List<BehaviouralCompetenceDetails> getCurrentEmployeeBehavioralComptenceDetails(String empCode, Integer appraisalYearId);
	ResponseObject behavioralDataCarryForward(Integer appraisalYearId, String type);
	List<Integer> getValidatedBehaviouralCompetenceDetails(String empCode, Integer appraisalYearId, String managerType);
	List<EmployeeBehaviouralCompetenceDetails> getEmployeeBehaviouralCompetenceDetails(String empCode,
			Integer appraisalYearId);
}
