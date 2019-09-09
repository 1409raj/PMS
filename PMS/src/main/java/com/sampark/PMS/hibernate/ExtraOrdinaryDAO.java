package com.sampark.PMS.hibernate;

import java.util.List;
import com.sampark.PMS.dto.EmployeeExtraOrdinaryDetails;
import com.sampark.PMS.dto.ExtraOrdinary;
import com.sampark.PMS.dto.ExtraOrdinaryDetails;
import com.sampark.PMS.object.RequestObject;
import com.sampark.PMS.object.ResponseObject;

public interface ExtraOrdinaryDAO {

	ResponseObject saveExtraOrdinaryDetails(RequestObject object, String empCode, String userType);

	List<ExtraOrdinary> getAllExtraOrdinary();

	ResponseObject saveExtraOrdinary(RequestObject object);

	ResponseObject deleteExtraOrdinary(Integer id);
	
	ResponseObject activateExtraOrdinary(Integer id, Integer appraisalYearId);

	List<ExtraOrdinary> getCurrentExtraOrdinary(Integer appraisalYearId);

	List<ExtraOrdinaryDetails> getCurrentEmployeeExtraOrdinaryDetails(Integer appraisalYearId, String empCode);

	List<Integer> getValidatedExtraOrdinaryDetails(String empCode, Integer appraisalYearId, String managerType);

	List<EmployeeExtraOrdinaryDetails> getEmployeeExtraOrdinaryDetails(String empCode, Integer appraisalYearId);

	

}
