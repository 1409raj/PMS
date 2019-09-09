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
import com.sampark.PMS.dto.Roles;
import com.sampark.PMS.object.RequestObject;
import com.sampark.PMS.object.ResponseObject;
import com.sampark.PMS.util.CommonUtils;

public class RolesDAOImpl implements RolesDAO {

	private static final Logger logger = LoggerFactory.getLogger(RolesDAOImpl.class);
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Roles> getAllApplicationRolesList() {
		Session session = this.sessionFactory.openSession();
		List<Roles> list = new ArrayList<Roles>();
		try {
			Criteria criteria = session.createCriteria(Roles.class);
			criteria.add(Restrictions.eq("status", PMSConstants.STATUS_ACTIVE));
			criteria.add(Restrictions.ne("name", "ROLE_ADMIN"));
			list = criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception while getting ApplicationRolesList ... " + e);
		} finally {
			session.flush();
			session.close();
		}
		return list;

	}

	@Override
	public ResponseObject saveApplicationRoles(RequestObject res) {
		ResponseObject responseObject = new ResponseObject();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Roles object = new Roles();
			String roleName = res.getName().replace("ROLE_", "");
			roleName = roleName.replace("Role_", "");
			roleName = roleName.replace("role_", "");
			if (res.getId() == null) {
				object.setName("ROLE_" + roleName.replaceAll(" ", "_").toUpperCase());
				object.setDescription(res.getDescription());
				object.setStatus(PMSConstants.STATUS_ACTIVE);
				object.setCreatedOn(new Date());
				object.setCreatedBy(CommonUtils.getCurrentUserName());
				session.persist(object);
			} else {
				object = (Roles) session.get(Roles.class, res.getId());
				object.setName("ROLE_" + roleName.replaceAll(" ", "_").toUpperCase());
				object.setDescription(res.getDescription());
				object.setModifiedOn(new Date());
				object.setModifiedBy(CommonUtils.getCurrentUserName());
				session.update(object);
			}
			tx.commit();
			responseObject.setInteger(PMSConstants.STATUS_SUCCESS);
			responseObject.setObject(getAllApplicationRolesList());
			responseObject.setString("Successfully saved");
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			responseObject.setInteger(PMSConstants.STATUS_FAILED);
			responseObject.setString("Exception while adding new Roles ... " + e);
			responseObject.setObject(e);
			logger.error("Exception while adding new Roles ... " + e);
		} finally {
			session.flush();
			session.close();
		}
		return responseObject;
	}

	@Override
	public ResponseObject deleteApplicationRoles(Integer rolesId) {
		ResponseObject responseObject = new ResponseObject();
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Roles role = (Roles) session.get(Roles.class, rolesId);
			role.setStatus(PMSConstants.STATUS_DELETED);
			session.update(role);
			tx.commit();
			responseObject.setInteger(PMSConstants.STATUS_SUCCESS);
			responseObject.setObject(getAllApplicationRolesList());
			responseObject.setString("Successfully delete");
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			responseObject.setInteger(PMSConstants.STATUS_FAILED);
			responseObject.setString("Exception while deleting Roles ... " + e);
			responseObject.setObject(e);
			logger.error("Exception while deleting Roles ... " + e);
		} finally {
			session.flush();
			session.close();
		}
		return responseObject;
	}

}
