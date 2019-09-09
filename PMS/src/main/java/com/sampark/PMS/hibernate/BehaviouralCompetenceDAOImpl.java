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
import com.sampark.PMS.dto.BehaviouralCompetence;
import com.sampark.PMS.dto.BehaviouralCompetenceDetails;
import com.sampark.PMS.dto.Department;
import com.sampark.PMS.dto.EmployeeBehaviouralCompetenceDetails;
import com.sampark.PMS.dto.EmployeeKRADetails;
import com.sampark.PMS.object.RequestObject;
import com.sampark.PMS.object.ResponseObject;
import com.sampark.PMS.util.CommonUtils;

public class BehaviouralCompetenceDAOImpl implements BehaviouralCompetenceDAO {

	private static final Logger logger = LoggerFactory.getLogger(CommonDAOImpl.class);
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public ResponseObject saveBehavioralComptenceDetails(RequestObject object, String empCode,String userType) {
		ResponseObject responseObject = new ResponseObject();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			for (BehaviouralCompetence obj : object.getBehaviouralCompetence()) {
				BehaviouralCompetenceDetails bcd = new BehaviouralCompetenceDetails();
				if(obj.getBehaviouralCompetenceDetailsId() != null)
				{
					bcd = (BehaviouralCompetenceDetails) session.get(BehaviouralCompetenceDetails.class, obj.getBehaviouralCompetenceDetailsId());
					bcd.setEmpCode(empCode);
					bcd.setAppraisalYearId(obj.getAppraisalYearId());
					bcd.setComments(obj.getComments());
					bcd.setModifiedBy(CommonUtils.getCurrentUserName());
					bcd.setModifiedOn(new Date());
					bcd.setBehaviouralDetailsId(obj.getId());
					bcd.setMidYearSelfRating(obj.getMidYearSelfRating());
					bcd.setFinalYearSelfRating(obj.getFinalYearSelfRating());
					responseObject.setString("Successfully saved");
//					bcd.setIsValidatedByEmployee(PMSConstants.STATUS_DELETED);
//					if (object.getType().equals(PMSConstants.BUTTON_SUBMIT)
//							&& userType.equals(PMSConstants.USER_EMPLOYEE)) {
//						bcd.setIsValidatedByEmployee(PMSConstants.STATUS_ACTIVE);
//					}
//					if (object.getType().equals(PMSConstants.BUTTON_SUBMIT)
//							&& userType.equals(PMSConstants.FIRST_LEVEL_MANAGER)) {
//						bcd.setIsValidatedByFirstLevel(PMSConstants.STATUS_ACTIVE);
//					}
					if (object.getType().equals(PMSConstants.BUTTON_SUBMIT)
							&& userType.equals(PMSConstants.USER_EMPLOYEE)) {
						bcd.setIsValidatedByEmployee(PMSConstants.STATUS_ACTIVE);
						responseObject.setString("Successfully submitted");
					}
					if (object.getType().equals(PMSConstants.BUTTON_SUBMIT)
							&& userType.equals(PMSConstants.FIRST_LEVEL_MANAGER)) {
						bcd.setIsValidatedByFirstLevel(PMSConstants.STATUS_ACTIVE);
						responseObject.setString("Successfully submitted");
					}
					if (object.getType().equals(PMSConstants.BUTTON_SUBMIT)
							&& userType.equals(PMSConstants.SECOND_LEVEL_MANAGER)) {
						bcd.setIsValidatedBySecondLevel(PMSConstants.STATUS_ACTIVE);
						responseObject.setString("Successfully submitted");
					}
					if(userType.equals(PMSConstants.FIRST_LEVEL_MANAGER))
					{
						bcd.setMidYearAssessorRating(obj.getMidYearAssessorRating());
						bcd.setFinalYearAssessorRating(obj.getFinalYearAssessorRating());
					}
					if(userType.equals(PMSConstants.SECOND_LEVEL_MANAGER))
					{
						if(bcd.getMidYearAssessorRating() != obj.getMidYearAssessorRating() && bcd.getMidYearAssessorRating() !=null)
						{
							bcd.setMidYearHighlights(PMSConstants.STATUS_ACTIVE);
						}
						bcd.setMidYearAssessorRating(obj.getMidYearAssessorRating());
						if(bcd.getFinalYearAssessorRating() != obj.getFinalYearAssessorRating() && bcd.getFinalYearAssessorRating() != null)
						{
							bcd.setFinalYearHighlights(PMSConstants.STATUS_ACTIVE);
						}
						bcd.setFinalYearAssessorRating(obj.getFinalYearAssessorRating());
					}
					session.update(bcd);
				}
				else
				{
					bcd.setEmpCode(empCode);
					bcd.setAppraisalYearId(obj.getAppraisalYearId());
					bcd.setComments(obj.getComments());
					bcd.setCreatedBy(CommonUtils.getCurrentUserName());
					bcd.setCreatedOn(new Date());
					bcd.setBehaviouralDetailsId(obj.getId());
					bcd.setMidYearSelfRating(obj.getMidYearSelfRating());
					bcd.setMidYearAssessorRating(obj.getMidYearAssessorRating());
					bcd.setFinalYearSelfRating(obj.getFinalYearSelfRating());
					bcd.setFinalYearAssessorRating(obj.getFinalYearAssessorRating());
					bcd.setStatus(PMSConstants.STATUS_ACTIVE);
					bcd.setIsValidatedByEmployee(PMSConstants.STATUS_DELETED);
					bcd.setIsValidatedByFirstLevel(PMSConstants.STATUS_DELETED);
					bcd.setIsValidatedBySecondLevel(PMSConstants.STATUS_DELETED);
					responseObject.setString("Successfully saved");
					if (object.getType().equals(PMSConstants.BUTTON_SUBMIT)) {
						bcd.setIsValidatedByEmployee(PMSConstants.STATUS_ACTIVE);
						responseObject.setString("Successfully submitted");
					}
					session.persist(bcd);
				}		
			}
			tx.commit();
//			responseObject.setObject(getCurrentEmployeeBehavioralComptenceDetails(empCode,CommonUtils.getActiveAppraisalYearId()));
			responseObject.setInteger(PMSConstants.STATUS_SUCCESS);
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			logger.error("Exception while adding new Behavioural Competence Details ... " + e);
			responseObject.setInteger(PMSConstants.STATUS_FAILED);
			responseObject.setString("Exception while adding new Behavioural Competence Details ... " + e);
			responseObject.setObject(e);
		}finally {
			session.flush();
			session.close();
		}
		return responseObject;

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BehaviouralCompetence> getCurrentBehavioralCompetenceList(Integer appraisalYearId,String type) {
		Session session = this.sessionFactory.openSession();
		List<BehaviouralCompetence> list = new ArrayList<BehaviouralCompetence>();
		try {
			Criteria criteria = session.createCriteria(BehaviouralCompetence.class);
			criteria.add(Restrictions.eq("status", PMSConstants.STATUS_ACTIVE));
			criteria.add(Restrictions.eq("appraisalYearId", appraisalYearId));
			criteria.add(Restrictions.eq("type", type));
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
	public ResponseObject saveBehavioralComptence(RequestObject res) {
		ResponseObject responseObject = new ResponseObject();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			BehaviouralCompetence object = new BehaviouralCompetence();
			if(res.getId() == null)
			{	
				object.setStatus(PMSConstants.STATUS_ACTIVE);
			    object.setCreatedOn(new Date());
			    object.setCreatedBy(CommonUtils.getCurrentUserName());
			    object.setName(res.getName());
			    object.setType(res.getType());
			    object.setAppraisalYearId(res.getAppraisalYearId());
			    object.setWeightage(res.getWeightage());
			    object.setDescription(res.getDescription());
			    session.persist(object);
			}
			else
			{
			    object = (BehaviouralCompetence) session.get(BehaviouralCompetence.class, res.getId());
			    object.setModifiedOn(new Date());
			    object.setModifiedBy(CommonUtils.getCurrentUserName());
			    object.setName(res.getName());
			    object.setWeightage(res.getWeightage());
			    object.setDescription(res.getDescription());
				session.update(object);
			}
			tx.commit();
			responseObject.setInteger(PMSConstants.STATUS_SUCCESS);
			responseObject.setObject(getCurrentBehavioralCompetenceList(res.getAppraisalYearId(),res.getType()));
			responseObject.setString("Successfully saved");
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			logger.error("Exception while adding new BehaviouralCompetence ... " + e);
			responseObject.setInteger(PMSConstants.STATUS_FAILED);
			responseObject.setString("Exception while adding new BehaviouralCompetence ... " + e);
			responseObject.setObject(e);
		}finally {
			session.flush();
			session.close();
		}
		return responseObject;
	}

	@Override
	public ResponseObject deleteBehavioralComptence(Integer id, Integer appraisalYearId,String type) {
		ResponseObject responseObject = new ResponseObject();
		Session session = this.sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			BehaviouralCompetence object = (BehaviouralCompetence) session.get(BehaviouralCompetence.class, id);
			object.setStatus(PMSConstants.STATUS_DELETED);
			object.setAppraisalYearId(PMSConstants.STATUS_DELETED);
			session.update(object);
			tx.commit();
			responseObject.setInteger(PMSConstants.STATUS_SUCCESS);
			responseObject.setObject(getCurrentBehavioralCompetenceList(appraisalYearId,type));
			responseObject.setString("Successfully deleted");
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			responseObject.setInteger(PMSConstants.STATUS_FAILED);
			responseObject.setString("Exception while deleting BehaviouralCompetence ... " + e);
			responseObject.setObject(e);
		} finally {
			session.flush();
			session.close();
		}
		return responseObject;
	}

	@Override
	public List<BehaviouralCompetenceDetails> getCurrentEmployeeBehavioralComptenceDetails(String empCode,Integer appraisalYearId) {
		Session session = this.sessionFactory.openSession();
		List<BehaviouralCompetenceDetails> list = new ArrayList<BehaviouralCompetenceDetails>();
		try {
			Criteria criteria = session.createCriteria(BehaviouralCompetenceDetails.class);
			criteria.add(Restrictions.eq("status", PMSConstants.STATUS_ACTIVE));
			criteria.add(Restrictions.eq("empCode", empCode));
			criteria.add(Restrictions.eq("appraisalYearId", appraisalYearId));
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
	public ResponseObject behavioralDataCarryForward(Integer appraisalYearId,String type) {
		  ResponseObject responseObject = new ResponseObject();
		  Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
		  try {
			for (BehaviouralCompetence obj : getPreviousYearBehaviouralCompetence(appraisalYearId)) {
				BehaviouralCompetence object = new BehaviouralCompetence();
				object.setAppraisalYearId(appraisalYearId);
				object.setType(obj.getType());
				object.setCreatedOn(new Date());
				object.setCreatedBy(CommonUtils.getCurrentUserName());
				object.setName(obj.getName());
				object.setDescription(obj.getDescription());
				object.setWeightage(obj.getWeightage());
				object.setStatus(PMSConstants.STATUS_ACTIVE);
				session.persist(object);
			}
			tx.commit();
			responseObject.setInteger(PMSConstants.STATUS_SUCCESS);
			responseObject.setObject(getCurrentBehavioralCompetenceList(appraisalYearId,type));
			responseObject.setString("Successfully saved");
		}catch (HibernateException e) {	
			e.printStackTrace();
			if (tx != null)
				tx.rollback();
			logger.error("Exception while adding new BehaviouralCompetenceDetails ... " + e);
			responseObject.setInteger(PMSConstants.STATUS_FAILED);
			responseObject.setString("Exception while adding new BehaviouralCompetenceDetails ... " + e);
			responseObject.setObject(e);
		} finally {
			session.flush();
			session.close();
		}
		return responseObject;
	}

	private List<BehaviouralCompetence> getPreviousYearBehaviouralCompetence(Integer appraisalYearId) {
		Session session = this.sessionFactory.openSession();
		List<BehaviouralCompetence> list = new ArrayList<BehaviouralCompetence>();
		try {
			String sql = "from BehaviouralCompetence where appraisalYearId = (select max(appraisalYearId) from BehaviouralCompetence) and status =:status";
			Query query = session.createQuery(sql);
			query.setParameter("status", PMSConstants.STATUS_ACTIVE);
			list = query.list();	
		} catch (HibernateException e) {	
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
		return list;
	}

	@Override
	public List<Integer> getValidatedBehaviouralCompetenceDetails(String empCode, Integer appraisalYearId,
			String managerType) {
		Session session = this.sessionFactory.openSession();
		List<Integer> list = new ArrayList<Integer>();
		try {
			String sql = "select id from BehaviouralCompetenceDetails where"
					+ " appraisalYearId =:appraisalYearId ";
			if(managerType.equals(PMSConstants.FIRST_LEVEL_MANAGER))
			{
				sql = sql + " and isValidatedByFirstLevel =:isValidatedByFirstLevel ";		
			}
			if(empCode!=PMSConstants.NULL_VALUE)
			{
				sql = sql + " and empCode =:empCode";
			}
			if(managerType.equals(PMSConstants.SECOND_LEVEL_MANAGER))
			{
				sql = sql + " and isValidatedBySecondLevel =:isValidatedBySecondLevel ";
			}
			Query query = session.createQuery(sql);
			if(managerType.equals(PMSConstants.FIRST_LEVEL_MANAGER))
			{
				query.setParameter("isValidatedByFirstLevel", PMSConstants.STATUS_ACTIVE);	
			}
			if(managerType.equals(PMSConstants.SECOND_LEVEL_MANAGER))
			{
				query.setParameter("isValidatedBySecondLevel", PMSConstants.STATUS_ACTIVE);
			}
			if(empCode!=PMSConstants.NULL_VALUE)
			{
				query.setParameter("empCode", empCode);
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

	@Override
	public List<EmployeeBehaviouralCompetenceDetails> getEmployeeBehaviouralCompetenceDetails(String empCode,
			Integer appraisalYearId) {
		Session session = this.sessionFactory.openSession();
		List<EmployeeBehaviouralCompetenceDetails> employeeBehaviouralCompetenceDetails = new ArrayList<EmployeeBehaviouralCompetenceDetails>();
		try {
			String sql ="select bcd.ID as bcdId, bcd.MID_YEAR_ASSESSOR_RATING as midYearAssessorRating,bc.WEIGHTAGE as weightage, "
					+ " bcd.FINAL_YEAR_ASSESSOR_RATING as finalYearAssessorRating "
					+ " from behavioural_competence_details as bcd, behavioural_competence as bc where bc.ID = bcd.BEHAVIOURAL_DETAILS_ID and "
					+ " bcd.APPRAISAL_YEAR_ID =:APPRAISAL_YEAR_ID and bc.APPRAISAL_YEAR_ID = bcd.APPRAISAL_YEAR_ID and bcd.EMP_CODE =:EMP_CODE ";
			Query query = session.createSQLQuery(sql).addEntity(EmployeeBehaviouralCompetenceDetails.class);
			query.setParameter("EMP_CODE", empCode);
			query.setParameter("APPRAISAL_YEAR_ID", appraisalYearId);
			employeeBehaviouralCompetenceDetails = query.list();	
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception while getting getEmployeeBehaviouralCompetenceDetails List" + e);
		} finally {
			session.flush();
			session.close();
		}
		return employeeBehaviouralCompetenceDetails;
	}


}
