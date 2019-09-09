package com.sampark.PMS.hibernate;

import com.sampark.PMS.dto.FinalReviewDetails;
import com.sampark.PMS.object.ResponseObject;

public interface FinalReviewDAO {

	ResponseObject saveFinalReviewDetails(FinalReviewDetails object, String empId);

}
