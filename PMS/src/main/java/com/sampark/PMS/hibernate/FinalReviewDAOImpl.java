package com.sampark.PMS.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sampark.PMS.PMSConstants;
import com.sampark.PMS.dto.FinalReviewDetails;
import com.sampark.PMS.object.ResponseObject;

public class FinalReviewDAOImpl implements FinalReviewDAO {

	private static final Logger logger = LoggerFactory.getLogger(FinalReviewDAOImpl.class);
	private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	@Override
	public ResponseObject saveFinalReviewDetails(FinalReviewDetails object, String empId) {
		ResponseObject responseObject = new ResponseObject();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
//			object.setEmpId(empId);
//			object.setAppraisalYear(new Date());
			session.persist(object);
			tx.commit();
			responseObject.setInteger(PMSConstants.STATUS_SUCCESS);
			responseObject.setString("Successfully saved");
		}catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			responseObject.setInteger(PMSConstants.STATUS_FAILED);
			responseObject.setString("Exception while adding new FinalReviewDetails ... " + e);
			responseObject.setObject(e);
			logger.error("Exception while adding new FinalReviewDetails ... " + e);
		}finally {
			session.flush();
			session.close();
		}
		return responseObject;
	}
	
}
