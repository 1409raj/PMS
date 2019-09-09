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
import com.sampark.PMS.dto.Designation;
import com.sampark.PMS.object.RequestObject;
import com.sampark.PMS.object.ResponseObject;
import com.sampark.PMS.util.CommonUtils;

public class DesignationDAOImpl implements DesignationDAO{
	private static final Logger logger = LoggerFactory.getLogger(DesignationDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	@Override
	public ResponseObject saveDesignation(RequestObject res) {
		ResponseObject responseObject = new ResponseObject();
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Designation object = new Designation();
			if(res.getId() == null)
			{
			object.setCreatedOn(new Date());
			object.setCreatedBy(CommonUtils.getCurrentUserName());
			object.setName(res.getName());
			object.setDescription(res.getDescription());
			object.setStatus(PMSConstants.STATUS_ACTIVE);
			session.persist(object);
			}
			else
			{
				    object = (Designation) session.get(Designation.class, res.getId());
				    object.setName(res.getName());
					object.setDescription(res.getDescription());
				    object.setModifiedOn(new Date());
				    object.setModifiedBy(CommonUtils.getCurrentUserName());
					session.update(object);
			}
			tx.commit();
			responseObject.setInteger(PMSConstants.STATUS_SUCCESS);
			responseObject.setObject(getAllDesignationList());
			responseObject.setString("Successfully saved");
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			logger.error("Exception while adding new Designation ... " + e);
			responseObject.setInteger(PMSConstants.STATUS_FAILED);
			responseObject.setString("Exception while adding new Designation ... " + e);
			responseObject.setObject(e);
		}finally {
			session.flush();
			session.close();
		}
		return responseObject;
	}

	@Override
	public List<Designation> getAllDesignationList() {
		Session session = this.sessionFactory.openSession();
		List<Designation> list = new ArrayList<Designation>();
		try {
			Criteria criteria = session.createCriteria(Designation.class);
			criteria.add(Restrictions.eq("status", PMSConstants.STATUS_ACTIVE));
			list = criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception while getting all Designation ... " + e);
		} finally {
			session.flush();
			session.close();
		}
		return list;
	}
	@Override
	public ResponseObject deleteDesignation(Integer designationId) {
		ResponseObject responseObject = new ResponseObject();
		Session session = this.sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Designation designation = (Designation) session.get(Designation.class, designationId);
			designation.setStatus(PMSConstants.STATUS_DELETED);
			session.update(designation);
			tx.commit();
			responseObject.setInteger(PMSConstants.STATUS_SUCCESS);
			responseObject.setObject(getAllDesignationList());
			responseObject.setString("Successfully delete");
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			responseObject.setInteger(PMSConstants.STATUS_FAILED);
			responseObject.setString("Exception while deleting Designation ... " + e);
			responseObject.setObject(e);
			logger.error("Exception while deleting Designation ... " + e);
		} finally {
			session.flush();
			session.close();
		}
		return responseObject;
	}
}
