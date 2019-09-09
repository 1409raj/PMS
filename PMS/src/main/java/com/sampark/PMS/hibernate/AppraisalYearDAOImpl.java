package com.sampark.PMS.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sampark.PMS.PMSConstants;
import com.sampark.PMS.dto.AppraisalYear;
import com.sampark.PMS.object.RequestObject;
import com.sampark.PMS.object.ResponseObject;
import com.sampark.PMS.util.CommonUtils;

public class AppraisalYearDAOImpl implements AppraisalYearDAO {

	private static final Logger logger = LoggerFactory.getLogger(AppraisalYearDetailsDAOImpl.class);
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<AppraisalYear> getAppraisalYearList() {
		Session session = this.sessionFactory.openSession();
		List<AppraisalYear> list = new ArrayList<AppraisalYear>();
		try {
			Criteria criteria = session.createCriteria(AppraisalYear.class);
			criteria.add(Restrictions.ge("status", PMSConstants.STATUS_ACTIVE));
			list = criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
			
		}
		return list;
	}

	@Override
	public ResponseObject saveAppraisalYear(RequestObject res) {
		ResponseObject responseObject = new ResponseObject();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			AppraisalYear object = new AppraisalYear();
			if(res.getId() == null)
			{	
				object.setStatus(PMSConstants.STATUS_PENDING);
			    object.setCreatedOn(new Date());
			    object.setCreatedBy(CommonUtils.getCurrentUserName());
			    object.setName(res.getName());
			    session.persist(object);
			}
			else
			{
				object = (AppraisalYear) session.get(AppraisalYear.class, res.getId());
			    object.setModifiedOn(new Date());
			    object.setModifiedBy(CommonUtils.getCurrentUserName());
			    object.setName(res.getName());
			    session.update(object);
			}
			tx.commit();
			responseObject.setInteger(PMSConstants.STATUS_SUCCESS);
			responseObject.setObject(getAppraisalYearList());
			responseObject.setString("Successfully saved");
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			logger.error("Exception while adding new AppraisalYear ... " + e);
			responseObject.setInteger(PMSConstants.STATUS_FAILED);
			responseObject.setString("Exception while adding new AppraisalYear ... " + e);
			responseObject.setObject(e);
		}finally {
			session.flush();
			session.close();
		}
		return responseObject;
	}

//	private boolean isYearActive() {
//		Session session = this.sessionFactory.openSession();
//		Transaction tx = session.beginTransaction();
//		Long count = null;
//		try {
//			String sql = "from AppraisalYear where status =:status";
//			Query query = session.createQuery(sql);
//			query.setParameter("status", 1);
//		    count = (Long) query.uniqueResult();
//			tx.commit();
//		} catch (HibernateException e) {
//			if (tx != null)
//				tx.rollback();
//			e.printStackTrace();
//		} finally {
//			session.close();
//		}
//		return count;
//		return false;
//	}

	@Override
	public ResponseObject deleteAppraisalYear(Integer id) {
		ResponseObject responseObject = new ResponseObject();
		Session session = this.sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			AppraisalYear object = (AppraisalYear) session.get(AppraisalYear.class, id);
			object.setStatus(PMSConstants.STATUS_DELETED);
			session.update(object);
			tx.commit();
			responseObject.setInteger(PMSConstants.STATUS_SUCCESS);
			responseObject.setObject(getAppraisalYearList());
			responseObject.setString("Successfully delete");
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			responseObject.setInteger(PMSConstants.STATUS_FAILED);
			responseObject.setString("Exception while deleting AppraisalYear ... " + e);
			responseObject.setObject(e);
		} finally {
			session.flush();
			session.close();
		}
		return responseObject;
	}

	@Override
	public ResponseObject updateApplicationAppraisalYear(Integer id, List<Integer> list) {
		ResponseObject responseObject = new ResponseObject();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			for(int i=0;i<list.size();i++)
			{
				AppraisalYear object = (AppraisalYear) session.get(AppraisalYear.class, list.get(i));
				if(id == list.get(i))
				{
				object.setStatus(PMSConstants.STATUS_ACTIVE);
				session.update(object);
				break;
				}
				else
				{
				object.setStatus(PMSConstants.STATUS_DELETED);	
				}
				session.update(object);
			}
			tx.commit();
			responseObject.setInteger(PMSConstants.STATUS_SUCCESS);
			responseObject.setObject(getAppraisalYearList());
			responseObject.setString("Successfully saved");
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			logger.error("Exception while updating application AppraisalYear ... " + e);
			responseObject.setInteger(PMSConstants.STATUS_FAILED);
			responseObject.setString("Exception while updating application AppraisalYear ... " + e);
			responseObject.setObject(e);
		}finally {
			session.flush();
			session.close();
		}
		return responseObject;
	}

	@Override
	public Integer getActiveAppraisalYearId() {
		Session session = this.sessionFactory.openSession();
		List<Integer> list = new ArrayList<Integer>();
		try {
			String sql = "select id from AppraisalYear where status =:status";
			Query query = session.createQuery(sql);
			query.setParameter("status", PMSConstants.STATUS_ACTIVE);
			list = query.list();
		} catch (HibernateException e) {
		e.printStackTrace();
		System.out.println(e);
		logger.error("Exception while getting All Current Appraisal Year Id" + e);
		} finally {
			session.flush();
			session.close();
		}
		return list.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer getPrevioueYearId() {
		Session session = this.sessionFactory.openSession();
		List<AppraisalYear> list = new ArrayList<AppraisalYear>();
		try {
			Criteria criteria = session.createCriteria(AppraisalYear.class);
			criteria.add(Restrictions.eq("status", PMSConstants.STATUS_DELETED));
			list = criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
		return list.get(list.size()-1).getId();
	}
}
