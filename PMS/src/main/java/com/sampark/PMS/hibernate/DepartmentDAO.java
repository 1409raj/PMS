package com.sampark.PMS.hibernate;

import java.util.List;

import com.sampark.PMS.dto.Department;
import com.sampark.PMS.dto.DepartmentName;
import com.sampark.PMS.object.RequestObject;
import com.sampark.PMS.object.ResponseObject;

public interface DepartmentDAO {

	ResponseObject saveDepartment(RequestObject object);
	List<Department> getAllDepartmentList();
	ResponseObject deleteDepartment(Integer departmentId);
	List<DepartmentName> getAllDepartmentIdList();


}
