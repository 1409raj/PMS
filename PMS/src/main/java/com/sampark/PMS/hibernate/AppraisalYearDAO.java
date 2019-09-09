package com.sampark.PMS.hibernate;

import java.util.List;

import com.sampark.PMS.dto.AppraisalYear;
import com.sampark.PMS.object.RequestObject;
import com.sampark.PMS.object.ResponseObject;

public interface AppraisalYearDAO {

	List<AppraisalYear> getAppraisalYearList();

	ResponseObject saveAppraisalYear(RequestObject object);

	ResponseObject deleteAppraisalYear(Integer id);

	ResponseObject updateApplicationAppraisalYear(Integer id, List<Integer> list);

	Integer getActiveAppraisalYearId();

	Integer getPrevioueYearId();

}
