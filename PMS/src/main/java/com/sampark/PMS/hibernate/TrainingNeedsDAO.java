package com.sampark.PMS.hibernate;

import java.util.List;

import com.sampark.PMS.dto.TrainingNeeds;
import com.sampark.PMS.dto.TrainingNeedsDetails;
import com.sampark.PMS.object.RequestObject;
import com.sampark.PMS.object.ResponseObject;

public interface TrainingNeedsDAO {

	ResponseObject saveTrainingNeedsDetails(RequestObject object, String empCode, String userType);

//	List<TrainingNeeds> getTrainingNeedsList();

//	ResponseObject saveTrainingNeeds(RequestObject object);
//
//	ResponseObject deleteTrainingNeeds(Integer id);

//	ResponseObject activateTrainingNeeds(Integer id, Integer appraisalYearId);

	List<TrainingNeedsDetails> getCurrentEmployeeTrainingNeedsDetails(String empCode, Integer appraisalYearId);

	List<Integer> getValidatedTrainingNeedsDetails(String empCode, Integer activeAppraisalYearID, String managerType);

//	List<TrainingNeeds> getCurrentTrainingNeeds(Integer appraisalYearId);

	
}
