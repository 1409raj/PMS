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
import com.sampark.PMS.dto.CareerAspirationsDetails;
import com.sampark.PMS.object.ResponseObject;

public class CareerAspirationsDAOImpl implements CareerAspirationsDAO {

	private static final Logger logger = LoggerFactory.getLogger(CommonDAOImpl.class);
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public ResponseObject saveCareerAspirationsDetails(CareerAspirationsDetails object, String empCode,
			String userType) {
		ResponseObject responseObject = new ResponseObject();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			if (object.getId() == null) {
				object.setCreatedBy(empCode);
				object.setCreatedOn(new Date());
				object.setStatus(PMSConstants.STATUS_ACTIVE);
				object.setMidYearCommentsStatus(PMSConstants.STATUS_DELETED);
				object.setMidYearCommentsStatusFirstLevel(PMSConstants.STATUS_DELETED);
				object.setMidYearCommentsStatusSecondLevel(PMSConstants.STATUS_DELETED);
				object.setIsValidatedByEmployee(PMSConstants.STATUS_DELETED);
				object.setIsValidatedByFirstLevel(PMSConstants.STATUS_DELETED);
				object.setIsValidatedBySecondLevel(PMSConstants.STATUS_DELETED);
				object.setMidYearCommentsStatus(PMSConstants.STATUS_DELETED);
				object.setMidYearCommentsStatusFirstLevel(PMSConstants.STATUS_DELETED);
				object.setMidYearCommentsStatusSecondLevel(PMSConstants.STATUS_DELETED);
				object.setYearEndCommentsStatus(PMSConstants.STATUS_DELETED);
				object.setYearEndCommentsStatusFirstLevel(PMSConstants.STATUS_DELETED);
				object.setYearEndCommentsStatusSecondLevel(PMSConstants.STATUS_DELETED);
				responseObject.setString("Successfully saved");
				if (object.getType().equals(PMSConstants.BUTTON_SUBMIT)) {
					object.setIsValidatedByEmployee(PMSConstants.STATUS_ACTIVE);
					responseObject.setString("Successfully submitted");
				}
				session.persist(object);
			} else {
				CareerAspirationsDetails obj = (CareerAspirationsDetails) session.get(CareerAspirationsDetails.class,object.getId());
				obj.setModifiedOn(new Date());
				obj.setModifiedBy(empCode);
				obj.setInitializationComments(object.getInitializationComments());
				obj.setMidYearComments(object.getMidYearComments());
				obj.setYearEndComments(object.getYearEndComments());
				responseObject.setString("Successfully saved");
				if (userType.equals(PMSConstants.FIRST_LEVEL_MANAGER) || userType.equals(PMSConstants.SECOND_LEVEL_MANAGER)) {
					obj.setInitializationManagerReview(object.getInitializationManagerReview());
					obj.setMidYearCommentsManagerReview(object.getMidYearCommentsManagerReview());
					obj.setYearEndCommentsManagerReview(object.getYearEndCommentsManagerReview());
				}
				if (object.getType().equals(PMSConstants.BUTTON_SUBMIT)
						&& userType.equals(PMSConstants.USER_EMPLOYEE)) {
					obj.setIsValidatedByEmployee(PMSConstants.STATUS_ACTIVE);
					if (object.getMidYearComments() != null && !object.getMidYearComments().isEmpty()) {
						obj.setMidYearCommentsStatus(PMSConstants.STATUS_ACTIVE);
					}
					if (object.getYearEndComments() != null && !object.getYearEndComments().isEmpty()) {
						obj.setYearEndCommentsStatus(PMSConstants.STATUS_ACTIVE);
					}
					responseObject.setString("Successfully submitted");
				}
				if (object.getType().equals(PMSConstants.BUTTON_SUBMIT)
						&& userType.equals(PMSConstants.FIRST_LEVEL_MANAGER)) {
					obj.setIsValidatedByFirstLevel(PMSConstants.STATUS_ACTIVE);
					if (object.getMidYearComments() != null && !object.getMidYearComments().isEmpty()) {
						obj.setMidYearCommentsStatusFirstLevel(PMSConstants.STATUS_ACTIVE);
					}
					if (object.getYearEndComments() != null && !object.getYearEndComments().isEmpty()) {
						obj.setYearEndCommentsStatusFirstLevel(PMSConstants.STATUS_ACTIVE);
					}
					responseObject.setString("Successfully submitted");
				}
				if (object.getType().equals(PMSConstants.BUTTON_SUBMIT)
						&& userType.equals(PMSConstants.SECOND_LEVEL_MANAGER)) {
					obj.setIsValidatedBySecondLevel(PMSConstants.STATUS_ACTIVE);
					if (object.getMidYearComments() != null && !object.getMidYearComments().isEmpty()) {
						obj.setMidYearCommentsStatusSecondLevel(PMSConstants.STATUS_ACTIVE);
					}
					if (object.getYearEndComments() != null && !object.getYearEndComments().isEmpty()) {
						obj.setYearEndCommentsStatusSecondLevel(PMSConstants.STATUS_ACTIVE);
					}
					responseObject.setString("Successfully submitted");
				}
				session.update(obj);
			}
			tx.commit();
			responseObject.setObject(getCurrentEmployeeCareerAspirationDetails(empCode, object.getAppraisalYearId()));
			responseObject.setInteger(PMSConstants.STATUS_SUCCESS);
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			logger.error("Exception while adding new Career Aspirations Details ... " + e);
			responseObject.setInteger(PMSConstants.STATUS_FAILED);
			responseObject.setString("Exception while adding new Career Aspirations Details ... " + e);
			responseObject.setObject(e);
		} finally {
			session.flush();
			session.close();
		}
		return responseObject;
	}

	@Override
	public List<CareerAspirationsDetails> getCurrentEmployeeCareerAspirationDetails(String empCode,
			Integer appraisalYearId) {
		Session session = this.sessionFactory.openSession();
		List<CareerAspirationsDetails> list = new ArrayList<CareerAspirationsDetails>();
		try {
			Criteria criteria = session.createCriteria(CareerAspirationsDetails.class);
			criteria.add(Restrictions.eq("status", PMSConstants.STATUS_ACTIVE));
			criteria.add(Restrictions.eq("empCode", empCode));
			criteria.add(Restrictions.eq("appraisalYearId", appraisalYearId));
			list = criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception while getting CareerAspirationsDetails List... " + e);
		} finally {
			session.flush();
			session.close();
		}
		return list;
	}

	@Override
	public List<Integer> getValidatedCareerAspirationDetails(String empCode, Integer appraisalYearId,
			String managerType) {
		Session session = this.sessionFactory.openSession();
		List<Integer> list = new ArrayList<Integer>();
		try {
			String sql = "select id from CareerAspirationsDetails where" + " appraisalYearId =:appraisalYearId ";
			if (empCode != PMSConstants.NULL_VALUE) {
				sql = sql + " and empCode =:empCode";
			}
			if (managerType.equals(PMSConstants.FIRST_LEVEL_MANAGER)) {
				sql = sql + " and isValidatedByFirstLevel =:isValidatedByFirstLevel ";
			}
			if (managerType.equals(PMSConstants.SECOND_LEVEL_MANAGER)) {
				sql = sql + " and isValidatedBySecondLevel =:isValidatedBySecondLevel ";
			}
			Query query = session.createQuery(sql);
			if (managerType.equals(PMSConstants.FIRST_LEVEL_MANAGER)) {
				query.setParameter("isValidatedByFirstLevel", PMSConstants.STATUS_ACTIVE);
			}
			if (empCode != PMSConstants.NULL_VALUE) {
				query.setParameter("empCode", empCode);
			}
			if (managerType.equals(PMSConstants.SECOND_LEVEL_MANAGER)) {
				query.setParameter("isValidatedBySecondLevel", PMSConstants.STATUS_ACTIVE);

			}

			query.setParameter("appraisalYearId", appraisalYearId);
			list = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
		return list;
	}
}
