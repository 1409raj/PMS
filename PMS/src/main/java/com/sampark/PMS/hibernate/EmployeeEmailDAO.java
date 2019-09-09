package com.sampark.PMS.hibernate;

import java.util.List;

import com.sampark.PMS.dto.EmployeeEmails;

public interface EmployeeEmailDAO {

	List<EmployeeEmails> getEmployeeEmailList();

	void updateEmployeeEmailDetails(EmployeeEmails employeeEmails);

}
