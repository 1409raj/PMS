package com.sampark.PMS.hibernate;

import java.util.List;

import com.sampark.PMS.dto.CareerAspirationsDetails;
import com.sampark.PMS.object.ResponseObject;

public interface CareerAspirationsDAO {

	ResponseObject saveCareerAspirationsDetails(CareerAspirationsDetails object, String empCode, String userType);

	List<CareerAspirationsDetails> getCurrentEmployeeCareerAspirationDetails(String empCode,Integer appraisalYearId);

	List<Integer> getValidatedCareerAspirationDetails(String empCode, Integer activeAppraisalYearID,String managerType);

}
