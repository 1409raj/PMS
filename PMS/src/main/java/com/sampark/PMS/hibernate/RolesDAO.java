package com.sampark.PMS.hibernate;

import java.util.List;

import com.sampark.PMS.dto.Roles;
import com.sampark.PMS.object.RequestObject;
import com.sampark.PMS.object.ResponseObject;

public interface RolesDAO {

	List<Roles> getAllApplicationRolesList();

	ResponseObject saveApplicationRoles(RequestObject object);

	ResponseObject deleteApplicationRoles(Integer rolesId);

}
