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
import com.sampark.PMS.dto.EmployeePromotions;
import com.sampark.PMS.object.RequestObject;
import com.sampark.PMS.object.ResponseObject;
import com.sampark.PMS.util.CommonUtils;
import com.sampark.PMS.util.DbUtils;

public class EmployeePromotionsDAOImpl implements EmployeePromotionsDAO {

	private static final Logger logger = LoggerFactory.getLogger(EmployeePromotionsDAOImpl.class);
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public ResponseObject saveEmployeePromotiondetails(EmployeePromotions object) {
		ResponseObject responseObject = new ResponseObject();
		Session session = sessionFactory.openSession();
		Transaction tx =session.beginTransaction();
		try {
			EmployeePromotions employeePromotions = new EmployeePromotions();
			if(object.getId() == null)
			{	
				object.setStatus(PMSConstants.STATUS_ACTIVE);
			    object.setCreatedOn(new Date());
			    object.setCreatedBy(CommonUtils.getCurrentUserName());
			    object.setFirstLevelCheck(PMSConstants.STATUS_ACTIVE);
			    object.setFirstLevelManagerId(CommonUtils.getCurrentUserName());
			    object.setSecondLevelManagerId(CommonUtils.getSecondLevelManagerId(object.getEmpCode()));
			    object.setFirstLevelSuperiorCommentsDate(new Date());
			    session.persist(object);
			}
			else
			{
				if(object.getType().equals(PMSConstants.FIRST_LEVEL_EMPLOYEE))
				{
				employeePromotions = (EmployeePromotions) session.get(EmployeePromotions.class, object.getId());
				employeePromotions.setRecommendedDesignation(object.getRecommendedDesignation());
				employeePromotions.setSpecificAchievements(object.getSpecificAchievements());
				employeePromotions.setExpectations(object.getExpectations());
				employeePromotions.setPromotionImpact(object.getPromotionImpact());
				employeePromotions.setJobResponsibility(object.getJobResponsibility());
				employeePromotions.setDepartmentLevel(object.getDepartmentLevel());
				employeePromotions.setOrganisationLevel(object.getOrganisationLevel());
				employeePromotions.setAdditionalTraining(object.getAdditionalTraining());
				employeePromotions.setNextFiveYears(object.getNextFiveYears());
				employeePromotions.setFirstLevelSuperiorComments(object.getFirstLevelSuperiorComments());
				employeePromotions.setFirstLevelCheck(PMSConstants.STATUS_ACTIVE);
				employeePromotions.setFirstLevelSuperiorCommentsDate(new Date());
				employeePromotions.setStatus(PMSConstants.STATUS_ACTIVE);
				}
				if(object.getType().equals(PMSConstants.SECOND_LEVEL_EMPLOYEE))
				{
				employeePromotions = (EmployeePromotions) session.get(EmployeePromotions.class, object.getId());
				employeePromotions.setRecommendedDesignation(object.getRecommendedDesignation());
				employeePromotions.setSpecificAchievements(object.getSpecificAchievements());
				employeePromotions.setExpectations(object.getExpectations());
				employeePromotions.setPromotionImpact(object.getPromotionImpact());
				employeePromotions.setJobResponsibility(object.getJobResponsibility());
				employeePromotions.setDepartmentLevel(object.getDepartmentLevel());
				employeePromotions.setOrganisationLevel(object.getOrganisationLevel());
				employeePromotions.setAdditionalTraining(object.getAdditionalTraining());
				employeePromotions.setNextFiveYears(object.getNextFiveYears());
				employeePromotions.setFirstLevelSuperiorComments(object.getFirstLevelSuperiorComments());
				employeePromotions.setSecondLevelSuperiorComments(object.getSecondLevelSuperiorComments());
				employeePromotions.setAppraisalYearId(object.getAppraisalYearId());
				employeePromotions.setSecondLevelCheck(PMSConstants.STATUS_ACTIVE);
				employeePromotions.setSecondLevelSuperiorCommentsDate(new Date());
				}
				if(object.getType().equals(PMSConstants.ROLE_HR_HEAD))
				{
					employeePromotions = (EmployeePromotions) session.get(EmployeePromotions.class, object.getId());
					employeePromotions.setRecommendedDesignation(object.getRecommendedDesignation());
					employeePromotions.setSpecificAchievements(object.getSpecificAchievements());
					employeePromotions.setExpectations(object.getExpectations());
					employeePromotions.setPromotionImpact(object.getPromotionImpact());
					employeePromotions.setJobResponsibility(object.getJobResponsibility());
					employeePromotions.setDepartmentLevel(object.getDepartmentLevel());
					employeePromotions.setOrganisationLevel(object.getOrganisationLevel());
					employeePromotions.setAdditionalTraining(object.getAdditionalTraining());
					employeePromotions.setNextFiveYears(object.getNextFiveYears());
					employeePromotions.setFirstLevelSuperiorComments(object.getFirstLevelSuperiorComments());
					employeePromotions.setSecondLevelSuperiorComments(object.getSecondLevelSuperiorComments());
					employeePromotions.setHrComments(object.getHrComments());
					employeePromotions.setHrCommentsDate(object.getHrCommentsDate());
					employeePromotions.setHrCheck(PMSConstants.STATUS_ACTIVE);
				}
				if(object.getType().equals(PMSConstants.ROLE_CEO_AND_ED))
				{
					employeePromotions = (EmployeePromotions) session.get(EmployeePromotions.class, object.getId());
					employeePromotions.setRecommendedDesignation(object.getRecommendedDesignation());
					employeePromotions.setSpecificAchievements(object.getSpecificAchievements());
					employeePromotions.setExpectations(object.getExpectations());
					employeePromotions.setPromotionImpact(object.getPromotionImpact());
					employeePromotions.setJobResponsibility(object.getJobResponsibility());
					employeePromotions.setDepartmentLevel(object.getDepartmentLevel());
					employeePromotions.setOrganisationLevel(object.getOrganisationLevel());
					employeePromotions.setAdditionalTraining(object.getAdditionalTraining());
					employeePromotions.setNextFiveYears(object.getNextFiveYears());
					employeePromotions.setFirstLevelSuperiorComments(object.getFirstLevelSuperiorComments());
					employeePromotions.setSecondLevelSuperiorComments(object.getSecondLevelSuperiorComments());
					employeePromotions.setApprovedByComments(object.getApprovedByComments());
					employeePromotions.setApprovedByDate(new Date());
					employeePromotions.setCeoCheck(PMSConstants.STATUS_ACTIVE);
				}
				session.update(employeePromotions);
			}
			tx.commit();
			responseObject.setInteger(PMSConstants.STATUS_SUCCESS);
			responseObject.setObject(DbUtils.getEmployeePromotionDetails(object.getAppraisalYearId(),object.getEmpCode()));
			responseObject.setString("Successfully saved");
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			logger.error("Exception while adding new EmployeePromotion Details ... " + e);
			responseObject.setInteger(PMSConstants.STATUS_FAILED);
			responseObject.setString("Exception while adding new EmployeePromotion Details ... " + e);
			responseObject.setObject(e);
		}finally {
			session.flush();
			session.close();
		}
		return responseObject;
	}

	@Override
	public List<EmployeePromotions> getCurrentEmployeePromotionDetails(Integer appraisalYearId, String empCode) {
		Session session = this.sessionFactory.openSession();
		List<EmployeePromotions> list = new ArrayList<EmployeePromotions>();
		try {
			Criteria criteria = session.createCriteria(EmployeePromotions.class);
			criteria.add(Restrictions.eq("appraisalYearId", appraisalYearId));
			criteria.add(Restrictions.eq("empCode",empCode));
			list = criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception while getting EmployeePromotions List... " + e);
		} finally {
			session.flush();
			session.close();
		}
		return list;
	}

	@Override
	public ResponseObject rejectEmployeePromotiondetails(RequestObject object) {
		ResponseObject responseObject = new ResponseObject();
		Session session = sessionFactory.openSession();
		Transaction tx =session.beginTransaction();
		try {
			EmployeePromotions employeePromotions = new EmployeePromotions();
		    if(object.getType().equals(PMSConstants.SECOND_LEVEL_EMPLOYEE))
			{
			employeePromotions = (EmployeePromotions) session.get(EmployeePromotions.class, object.getId());
			employeePromotions.setSecondLevelSuperiorComments(null);
			employeePromotions.setFirstLevelCheck(PMSConstants.STATUS_DELETED);
			employeePromotions.setStatus(PMSConstants.STATUS_DELETED);
			}
			if(object.getType().equals(PMSConstants.USER_ADMIN))
			{
			employeePromotions = (EmployeePromotions) session.get(EmployeePromotions.class, object.getId());
			employeePromotions.setSecondLevelSuperiorComments(null);
			employeePromotions.setHrComments(null);
			employeePromotions.setApprovedByComments(null);
			employeePromotions.setSecondLevelCheck(PMSConstants.STATUS_DELETED);
			employeePromotions.setHrCheck(PMSConstants.STATUS_DELETED);
			}
			session.update(employeePromotions);
			tx.commit();
			responseObject.setInteger(PMSConstants.STATUS_SUCCESS);
			responseObject.setString("Successfully saved");
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			logger.error("Exception while Rejecting EmployeePromotion Details ... " + e);
			responseObject.setInteger(PMSConstants.STATUS_FAILED);
			responseObject.setString("Exception while Rejecting EmployeePromotion Details ... " + e);
			responseObject.setObject(e);
		}finally {
			session.flush();
			session.close();
		}
		return responseObject;
	}

}
