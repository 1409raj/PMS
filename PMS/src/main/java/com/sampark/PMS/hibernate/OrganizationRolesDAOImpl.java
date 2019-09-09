package com.sampark.PMS.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sampark.PMS.PMSConstants;
import com.sampark.PMS.dto.OrganizationRoles;
import com.sampark.PMS.object.RequestObject;
import com.sampark.PMS.object.ResponseObject;
import com.sampark.PMS.util.CommonUtils;

public class OrganizationRolesDAOImpl implements OrganizationRolesDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(OrganizationRolesDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrganizationRoles> getAllOrganizationRolesList(Integer departmentId) {
		Session session = this.sessionFactory.openSession();
		List<OrganizationRoles> list = new ArrayList<OrganizationRoles>();
		try {
			Criteria criteria = session.createCriteria(OrganizationRoles.class);
			criteria.add(Restrictions.eq("status", PMSConstants.STATUS_ACTIVE));
			criteria.add(Restrictions.eq("departmentId", departmentId));
			list = criteria.list();
		} catch (HibernateException e) {
		   e.printStackTrace();
		   logger.error("Exception while getting All OrganizationRoles List ... " + e);
		} finally {
			session.flush();
			session.close();
		}
		return list;
	}

	@Override
	public ResponseObject saveOrganizationRoles(RequestObject res) {
		ResponseObject responseObject = new ResponseObject();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
//			String roleName = res.getName().replace("ROLE_","");
//			roleName = roleName.replace("Role_","");
//			roleName = roleName.replace("role_","");
			OrganizationRoles object  = new OrganizationRoles();
			if(res.getId() == null)
			{
			object.setCreatedOn(new Date());
			object.setCreatedBy(CommonUtils.getCurrentUserName());
//			object.setName("ROLE_"+ roleName.replaceAll(" ", "_"));
			object.setName(res.getName());
			object.setDescription(res.getDescription());
			object.setDepartmentId(res.getDepartmentId());
			object.setStatus(PMSConstants.STATUS_ACTIVE);
			session.persist(object);
			}
			else
			{
				object = (OrganizationRoles) session.get(OrganizationRoles.class, res.getId());
//				object.setName("ROLE_"+ roleName.replaceAll(" ", "_"));
				object.setName(res.getName());
				object.setDescription(res.getDescription());
				object.setModifiedOn(new Date());
				object.setModifiedBy(CommonUtils.getCurrentUserName());
				session.update(object);
			}
			tx.commit();
			responseObject.setInteger(PMSConstants.STATUS_SUCCESS);
			responseObject.setObject(getAllOrganizationRolesList(res.getDepartmentId()));
			responseObject.setString("Successfully saved");
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			responseObject.setInteger(PMSConstants.STATUS_FAILED);
			responseObject.setString("Exception while adding new OrganizationRoles ... " + e);
			responseObject.setObject(e);
			logger.error("Exception while adding new OrganizationRoles ... " + e);
		}finally {
			session.flush();
			session.close();
		}
		return responseObject;
	}

	@Override
	public ResponseObject deleteOrganizationRoles(Integer organizationRolesId, Integer departmentId) {
		ResponseObject responseObject = new ResponseObject();
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			OrganizationRoles organizationRoles = (OrganizationRoles) session.get(OrganizationRoles.class, organizationRolesId);
			organizationRoles.setStatus(PMSConstants.STATUS_DELETED);
			session.update(organizationRoles);
			tx.commit();
			responseObject.setInteger(PMSConstants.STATUS_SUCCESS);
			responseObject.setObject(getAllOrganizationRolesList(departmentId));
			responseObject.setString("Successfully delete");
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			responseObject.setInteger(PMSConstants.STATUS_FAILED);
			responseObject.setString("Exception while deleting OrganizationRoles ... " + e);
			responseObject.setObject(e);
			logger.error("Exception while deleting OrganizationRoles ... " + e);
		} finally {
			session.flush();
			session.close();
		}
		return responseObject;
	}

}
