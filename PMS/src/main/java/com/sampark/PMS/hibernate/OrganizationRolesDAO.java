package com.sampark.PMS.hibernate;

import java.util.List;

import com.sampark.PMS.dto.OrganizationRoles;
import com.sampark.PMS.object.RequestObject;
import com.sampark.PMS.object.ResponseObject;

public interface OrganizationRolesDAO {

	List<OrganizationRoles> getAllOrganizationRolesList(Integer departmentId);
	ResponseObject deleteOrganizationRoles(Integer organizationRolesId, Integer departmentId);

	ResponseObject saveOrganizationRoles(RequestObject object);

}
