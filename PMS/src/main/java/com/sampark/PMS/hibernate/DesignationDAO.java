package com.sampark.PMS.hibernate;

import java.util.List;

import com.sampark.PMS.dto.Designation;
import com.sampark.PMS.object.RequestObject;
import com.sampark.PMS.object.ResponseObject;

public interface DesignationDAO {

	ResponseObject saveDesignation(RequestObject object);
	List<Designation> getAllDesignationList();
	ResponseObject deleteDesignation(Integer designationId);

}
