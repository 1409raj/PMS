package com.sampark.PMS.hibernate;

import java.util.List;

import com.sampark.PMS.dto.EmployeePromotions;
import com.sampark.PMS.object.RequestObject;
import com.sampark.PMS.object.ResponseObject;

public interface EmployeePromotionsDAO {

	ResponseObject saveEmployeePromotiondetails(EmployeePromotions object);

	List<EmployeePromotions> getCurrentEmployeePromotionDetails(Integer appraisalYearId, String empCode);

	ResponseObject rejectEmployeePromotiondetails(RequestObject object);

}
