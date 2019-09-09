package com.sampark.PMS.hibernate;
//package com.sampark.PMS.hibernate;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import org.hibernate.Criteria;
//import org.hibernate.HibernateException;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//import org.hibernate.criterion.Restrictions;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import com.sampark.PMS.PMSConstants;
//import com.sampark.PMS.dto.Qualification;
//import com.sampark.PMS.object.RequestObject;
//import com.sampark.PMS.object.ResponseObject;
//import com.sampark.PMS.util.CommonUtils;
//
//public class QualificationDAOImpl implements QualificationDAO {
//	
//	private static final Logger logger = LoggerFactory.getLogger(QualificationDAOImpl.class);
//	private SessionFactory sessionFactory;
//
//	public void setSessionFactory(SessionFactory sessionFactory) {
//		this.sessionFactory = sessionFactory;
//	}
//	
//	@Override
//	public ResponseObject saveQualification(RequestObject res) {
//		ResponseObject responseObject = new ResponseObject();
//		Session session = sessionFactory.openSession();
//		Transaction tx = session.beginTransaction();
//		try {
//			Qualification object = new Qualification();	
//			if(res.getId() == null)
//			{
//				object.setCreatedOn(new Date());
//				object.setCreatedBy(CommonUtils.getCurrentUserName());
//				object.setName(res.getName());
//				object.setDescription(res.getDescription());
//				object.setStatus(PMSConstants.STATUS_ACTIVE);
//				session.persist(object);
//			}
//			else
//			{
//				object = (Qualification) session.get(Qualification.class, res.getId());
//				object.setName(res.getName());
//				object.setDescription(res.getDescription());
//			    object.setModifiedOn(new Date());
//			    object.setModifiedBy(CommonUtils.getCurrentUserName());
//				session.update(object);
//			}
//			tx.commit();
//			responseObject.setInteger(PMSConstants.STATUS_SUCCESS);
//			responseObject.setObject(getAllQualificationList());
//			responseObject.setString("Successfully saved");
//		} catch (HibernateException e) {
//			if (tx != null)
//				tx.rollback();
//			e.printStackTrace();
//			responseObject.setInteger(PMSConstants.STATUS_FAILED);
//			responseObject.setString("Exception while adding new Qualification ... " + e);
//			responseObject.setObject(e);
//			logger.error("Exception while adding new Qualification ... " + e);
//		} finally {
//			session.close();
//		}
//		return responseObject;
//	}
//	
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Qualification> getAllQualificationList() {
//		Session session = this.sessionFactory.openSession();
//		List<Qualification> list = new ArrayList<Qualification>();
//		try {
//			Criteria criteria = session.createCriteria(Qualification.class);
//			criteria.add(Restrictions.eq("status", PMSConstants.STATUS_ACTIVE));
//			list = criteria.list();
//		} catch (HibernateException e) {
//		 e.printStackTrace();
//		 logger.error("Exception while getting Qualification list... " + e);
//		} finally {
//			session.close();
//		}
//		return list;
//	}
//	
//	@Override
//	public ResponseObject deleteQualification(Integer qualificationId) {
//		ResponseObject responseObject = new ResponseObject();
//		Session session = this.sessionFactory.openSession();
//		Transaction tx =  session.beginTransaction();
//		try {
//			Qualification qualification = (Qualification) session.get(Qualification.class, qualificationId);
//			qualification.setStatus(PMSConstants.STATUS_DELETED);
//			session.update(qualification);
//			tx.commit();
//			responseObject.setInteger(PMSConstants.STATUS_SUCCESS);
//			responseObject.setObject(getAllQualificationList());
//			responseObject.setString("Successfully delete");
//		} catch (HibernateException e) {
//			if (tx != null)
//				tx.rollback();
//			e.printStackTrace();
//			responseObject.setInteger(PMSConstants.STATUS_FAILED);
//			responseObject.setString("Exception while deleting Qualification ... " + e);
//			responseObject.setObject(e);
//			logger.error("Exception while deleting Qualification ... " + e);
//		} finally {
//			session.close();
//		}
//		return responseObject;
//	}
//}
