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
import com.sampark.PMS.dto.EmployeeDynamicFormDetails;
import com.sampark.PMS.object.ResponseObject;
import com.sampark.PMS.util.CommonUtils;

public class EmployeeDynamicFormDetailsDAOImpl implements EmployeeDynamicFormDetailsDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeDynamicFormDetailsDAOImpl.class);
	private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeDynamicFormDetails> getAllEmployeeDynamicFormList() {
		Session session = this.sessionFactory.openSession();
		List<EmployeeDynamicFormDetails> list = new ArrayList<EmployeeDynamicFormDetails>();
		try {
			Criteria criteria = session.createCriteria(EmployeeDynamicFormDetails.class);
			list = criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception while getting EmployeeDynamicForm List ... " + e);
		} finally {
			session.flush();
			session.close();
		}
		return list;
	}

	@Override
	public ResponseObject saveColumn(String description, String name, Integer id,Integer appraisalYearId) {
		ResponseObject responseObject = new ResponseObject();
		Session session = this.sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			EmployeeDynamicFormDetails obj = (EmployeeDynamicFormDetails) session.get(EmployeeDynamicFormDetails.class, id);
//			obj.setStatus(PMSConstants.STATUS_ACTIVE);
			obj.setLabelName(name);
			obj.setAppraisalYearId(appraisalYearId);
			obj.setCreatedOn(new Date());
			obj.setModifiedBy(CommonUtils.getCurrentUserName());
			obj.setDescription(description);
			session.update(obj);
			tx.commit();
			responseObject.setInteger(PMSConstants.STATUS_SUCCESS);
			responseObject.setObject(getAllEmployeeDynamicFormList());
			responseObject.setString("Successfully updated");
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			responseObject.setInteger(PMSConstants.STATUS_FAILED);
			responseObject.setString("Exception while updating EmployeeDynamicFormDetails ... " + e);
			responseObject.setObject(e);
			logger.error("Exception while updating EmployeeDynamicFormDetails ... " + e);
		} finally {
			session.flush();
			session.close();
		}
		return responseObject;
	}

	@Override
	public ResponseObject deleteColumn(Integer id,Integer appraisalYearId) {
		ResponseObject responseObject = new ResponseObject();
		Session session = this.sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			EmployeeDynamicFormDetails obj = (EmployeeDynamicFormDetails) session.get(EmployeeDynamicFormDetails.class, id);
			obj.setStatus(PMSConstants.STATUS_DELETED);
			obj.setAppraisalYearId(PMSConstants.STATUS_DELETED);
			obj.setLabelName(obj.getName());
			obj.setDescription(obj.getName());
			session.update(obj);
			tx.commit();
			responseObject.setInteger(PMSConstants.STATUS_SUCCESS);
			responseObject.setObject(getAllEmployeeDynamicFormList());
			responseObject.setString("Successfully delete");
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			responseObject.setInteger(PMSConstants.STATUS_FAILED);
			responseObject.setString("Exception while deleting EmployeeDynamicFormDetails ... " + e);
			responseObject.setObject(e);
			logger.error("Exception while deleting EmployeeDynamicFormDetails ... " + e);
		} finally {
			session.flush();
			session.close();
		}
		return responseObject;
	}

	@Override
	public ResponseObject activateColumn(Integer appraisalYearId, Integer id) {
		ResponseObject responseObject = new ResponseObject();
		Session session = this.sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			EmployeeDynamicFormDetails obj = (EmployeeDynamicFormDetails) session.get(EmployeeDynamicFormDetails.class, id);
			obj.setStatus(PMSConstants.STATUS_ACTIVE);
			obj.setAppraisalYearId(appraisalYearId);
			obj.setModifiedOn(new Date());
			obj.setModifiedBy(CommonUtils.getCurrentUserName());
			session.update(obj);
			tx.commit();
			responseObject.setInteger(PMSConstants.STATUS_SUCCESS);
			responseObject.setObject(getAllEmployeeDynamicFormList());
			responseObject.setString("Successfully updated");
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			responseObject.setInteger(PMSConstants.STATUS_FAILED);
			responseObject.setString("Exception while activating EmployeeDynamicFormDetails ... " + e);
			responseObject.setObject(e);
			logger.error("Exception while activating EmployeeDynamicFormDetails ... " + e);
		} finally {
			session.flush();
			session.close();
		}
		return responseObject;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeDynamicFormDetails> getCurrentEmployeeDynamicFormList(Integer appraisalYearId) {
		Session session = this.sessionFactory.openSession();
		List<EmployeeDynamicFormDetails> list = new ArrayList<EmployeeDynamicFormDetails>();
		try {
			Criteria criteria = session.createCriteria(EmployeeDynamicFormDetails.class);
			criteria.add(Restrictions.eq("status", PMSConstants.STATUS_ACTIVE));
			criteria.add(Restrictions.le("appraisalYearId", appraisalYearId));
			list = criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception while getting Current EmployeeDynamicForm List ... " + e);
		} finally {
			session.flush();
			session.close();
		}
		return list;
	}
}
