package com.sampark.PMS.hibernate;

import java.util.List;

import com.sampark.PMS.dto.EmployeeDynamicFormDetails;
import com.sampark.PMS.object.ResponseObject;

public interface EmployeeDynamicFormDetailsDAO {
	List<EmployeeDynamicFormDetails> getAllEmployeeDynamicFormList();
	ResponseObject deleteColumn(Integer id, Integer appraisalYearId);
	ResponseObject saveColumn(String description, String name, Integer id, Integer appraisalYearId);
	ResponseObject activateColumn(Integer appraisalYearId, Integer id);
	List<EmployeeDynamicFormDetails> getCurrentEmployeeDynamicFormList(Integer appraisalYearId);

}
