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
import com.sampark.PMS.dto.EmployeeEmails;
import com.sampark.PMS.util.CommonUtils;

public class EmployeeEmailDAOImpl implements EmployeeEmailDAO {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeEmailDAOImpl.class);
	private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    
	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeEmails> getEmployeeEmailList() {
		Session session = this.sessionFactory.openSession();
		List<EmployeeEmails> list = new ArrayList<EmployeeEmails>();
		try {
			Criteria criteria = session.createCriteria(EmployeeEmails.class);
			//criteria.add(Restrictions.eq("appraisalYearId", CommonUtils.getActiveAppraisalYearId()));
			criteria.add(Restrictions.eq("status", PMSConstants.STATUS_PENDING));
			list = criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			System.out.println("Exception " + e);
			logger.error("Exception while getting Employee Email List ... " + e);
		} finally {
			session.flush();
			session.close();
		}
		return list;
	}
	
	

	@Override
	public void updateEmployeeEmailDetails(EmployeeEmails employeeEmails) {
		Session session = sessionFactory.openSession();
		Transaction tx =session.beginTransaction();
		try {
			EmployeeEmails object = (EmployeeEmails) session.get(EmployeeEmails.class, employeeEmails.getId());
			object.setStatus(PMSConstants.STATUS_ACTIVE);
			object.setDeliveryStatus("Succesfully Delivered");
			session.update(object);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			logger.error("Exception while adding new Department ... " + e);
			
		}finally {
			session.flush();
			session.close();
		}
	}
}
