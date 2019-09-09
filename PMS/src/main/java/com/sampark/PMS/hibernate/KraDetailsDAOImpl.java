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
import com.sampark.PMS.dto.EmployeeBasicDetails;
import com.sampark.PMS.dto.EmployeeKRAData;
import com.sampark.PMS.dto.EmployeeKRADetails;
import com.sampark.PMS.dto.KraDetails;
import com.sampark.PMS.dto.KraNew;
import com.sampark.PMS.object.RequestObject;
import com.sampark.PMS.object.ResponseObject;
import com.sampark.PMS.util.CommonUtils;
import com.sampark.PMS.util.DbUtils;

public class KraDetailsDAOImpl implements KraDetailsDAO {

	private static final Logger logger = LoggerFactory.getLogger(KraDetailsDAOImpl.class);
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public ResponseObject addKraDetails(RequestObject object, String empCode, String userType) {
		ResponseObject responseObject = new ResponseObject();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<Integer> previousKraId = object.getList();
		try {
			//Integer activeAppraisalYearId = CommonUtils.getActiveAppraisalYearId();
			Integer activeAppraisalYearId =null;
			if(object.getAppraisalYearId()!=null)
			{
				activeAppraisalYearId=object.getAppraisalYearId();
			}
			else
			{
				activeAppraisalYearId=CommonUtils.getActiveAppraisalYearId();
			}
			for (KraDetails kra : object.getKraDetails().getSectionAList()) {
				if (kra.getId() == null) {
					kra.setEmpCode(empCode);
					kra.setCreatedBy(empCode);
					kra.setCreatedOn(new Date());
					kra.setAppraisalYearId(activeAppraisalYearId);
					kra.setStatus(PMSConstants.STATUS_ACTIVE);
					kra.setIsValidatedByEmployee(PMSConstants.STATUS_DELETED);
					kra.setIsValidatedByFirstLevel(PMSConstants.STATUS_DELETED);
					kra.setIsValidatedBySecondLevel(PMSConstants.STATUS_DELETED);
					responseObject.setString("Successfully saved");
					if (object.getType().equals(PMSConstants.BUTTON_SUBMIT) && userType.equals(PMSConstants.USER_EMPLOYEE)) {
						kra.setIsValidatedByEmployee(PMSConstants.STATUS_ACTIVE);
						responseObject.setString("Successfully submitted");
					}
					if(object.getType().equals(PMSConstants.BUTTON_SUBMIT) && userType.equals(PMSConstants.FIRST_LEVEL_MANAGER))
					{
						kra.setIsValidatedByEmployee(PMSConstants.STATUS_ACTIVE);
						kra.setIsValidatedByFirstLevel(PMSConstants.STATUS_ACTIVE);
						responseObject.setString("Successfully submitted");
					}
					if(object.getType().equals(PMSConstants.BUTTON_SUBMIT) && userType.equals(PMSConstants.SECOND_LEVEL_MANAGER))
					{
						kra.setIsValidatedByEmployee(PMSConstants.STATUS_ACTIVE);
						kra.setIsValidatedByFirstLevel(PMSConstants.STATUS_ACTIVE);
						kra.setIsValidatedBySecondLevel(PMSConstants.STATUS_ACTIVE);
						responseObject.setString("Successfully submitted");
					}
					
					session.persist(kra);
				} else {
					KraDetails obj = (KraDetails) session.get(KraDetails.class, kra.getId());
					obj.setMidYearAchievement(kra.getMidYearAchievement());
					obj.setFinalYearAchievement(kra.getFinalYearAchievement());
					obj.setAchievementDate(kra.getAchievementDate());
					obj.setSmartGoal(kra.getSmartGoal());
					obj.setTarget(kra.getTarget());
					obj.setWeightage(kra.getWeightage());
					obj.setMidYearSelfRating(kra.getMidYearSelfRating());
					obj.setFinalYearSelfRating(kra.getFinalYearSelfRating());
					obj.setModifiedBy(empCode);
					obj.setFileName(kra.getFileName());
					obj.setModifiedOn(new Date());
					responseObject.setString("Successfully saved");
					if(userType.equals(PMSConstants.FIRST_LEVEL_MANAGER))
					{
						obj.setMidYearAppraisarRating(kra.getMidYearAppraisarRating());
						obj.setFinalYearAppraisarRating(kra.getFinalYearAppraisarRating());
					}
					if(userType.equals(PMSConstants.SECOND_LEVEL_MANAGER))
					{
						if(obj.getMidYearAppraisarRating() != kra.getMidYearAppraisarRating() && obj.getMidYearAppraisarRating() !=null)
						{
							obj.setMidYearHighlights(PMSConstants.STATUS_ACTIVE);
						}
						obj.setMidYearAppraisarRating(kra.getMidYearAppraisarRating());
						if(obj.getFinalYearAppraisarRating() != kra.getFinalYearAppraisarRating() && obj.getFinalYearAppraisarRating() != null)
						{
							obj.setFinalYearHighlights(PMSConstants.STATUS_ACTIVE);
						}
						obj.setFinalYearAppraisarRating(kra.getFinalYearAppraisarRating());
					}
					if(userType.equals(PMSConstants.FIRST_LEVEL_MANAGER) || userType.equals(PMSConstants.SECOND_LEVEL_MANAGER))
					{
						obj.setMidYearAssessmentRemarks(kra.getMidYearAssessmentRemarks());
						obj.setRemarks(kra.getRemarks());
					}
					// obj.setIsValidatedByEmployee(PMSConstants.STATUS_DELETED);
					if (object.getType().equals(PMSConstants.BUTTON_SUBMIT) 
							&& userType.equals(PMSConstants.USER_EMPLOYEE)) {
						obj.setIsValidatedByEmployee(PMSConstants.STATUS_ACTIVE);
						responseObject.setString("Successfully submitted");
					}
					if (object.getType().equals(PMSConstants.BUTTON_SUBMIT)
							&& userType.equals(PMSConstants.FIRST_LEVEL_MANAGER)) {
						obj.setIsValidatedByFirstLevel(PMSConstants.STATUS_ACTIVE);
						responseObject.setString("Successfully submitted");
					}
					if (object.getType().equals(PMSConstants.BUTTON_SUBMIT)
							&& userType.equals(PMSConstants.SECOND_LEVEL_MANAGER)) {
						obj.setIsValidatedBySecondLevel(PMSConstants.STATUS_ACTIVE);
						responseObject.setString("Successfully submitted");
					}
					session.update(obj);
					if (previousKraId.size() > 0) {
						previousKraId.remove(new Integer(kra.getId()));
					}
				}
			}

			for (KraDetails kra : object.getKraDetails().getSectionBList()) {
				if (kra.getId() == null) {
					kra.setEmpCode(empCode);
					kra.setCreatedBy(empCode);
					kra.setCreatedOn(new Date());
					kra.setAppraisalYearId(activeAppraisalYearId);
					kra.setStatus(PMSConstants.STATUS_ACTIVE);
					kra.setIsValidatedByEmployee(PMSConstants.STATUS_DELETED);
					kra.setIsValidatedByFirstLevel(PMSConstants.STATUS_DELETED);
					kra.setIsValidatedBySecondLevel(PMSConstants.STATUS_DELETED);
					responseObject.setString("Successfully saved");
					if (object.getType().equals(PMSConstants.BUTTON_SUBMIT) && userType.equals(PMSConstants.USER_EMPLOYEE)) {
						kra.setIsValidatedByEmployee(PMSConstants.STATUS_ACTIVE);
						responseObject.setString("Successfully submitted");
					}
					if(object.getType().equals(PMSConstants.BUTTON_SUBMIT) && userType.equals(PMSConstants.FIRST_LEVEL_MANAGER))
					{
						kra.setIsValidatedByEmployee(PMSConstants.STATUS_ACTIVE);
						kra.setIsValidatedByFirstLevel(PMSConstants.STATUS_ACTIVE);
						responseObject.setString("Successfully submitted");
					}
					if(object.getType().equals(PMSConstants.BUTTON_SUBMIT) && userType.equals(PMSConstants.SECOND_LEVEL_MANAGER))
					{
						kra.setIsValidatedByEmployee(PMSConstants.STATUS_ACTIVE);
						kra.setIsValidatedByFirstLevel(PMSConstants.STATUS_ACTIVE);
						kra.setIsValidatedBySecondLevel(PMSConstants.STATUS_ACTIVE);
						responseObject.setString("Successfully submitted");
					}
					session.persist(kra);
				} else {
					KraDetails obj = (KraDetails) session.get(KraDetails.class, kra.getId());
					obj.setMidYearAchievement(kra.getMidYearAchievement());
					obj.setFinalYearAchievement(kra.getFinalYearAchievement());
					obj.setAchievementDate(kra.getAchievementDate());
					obj.setSmartGoal(kra.getSmartGoal());
					obj.setTarget(kra.getTarget());
					obj.setWeightage(kra.getWeightage());
					obj.setMidYearSelfRating(kra.getMidYearSelfRating());
					obj.setFinalYearSelfRating(kra.getFinalYearSelfRating());
					obj.setModifiedBy(empCode);
					obj.setModifiedOn(new Date());
					obj.setFileName(kra.getFileName());
					responseObject.setString("Successfully saved");
					if(userType.equals(PMSConstants.FIRST_LEVEL_MANAGER) || userType.equals(PMSConstants.SECOND_LEVEL_MANAGER))
					{
						obj.setMidYearAssessmentRemarks(kra.getMidYearAssessmentRemarks());
						obj.setRemarks(kra.getRemarks());
						
					}
					if(userType.equals(PMSConstants.FIRST_LEVEL_MANAGER))
					{
						obj.setMidYearAppraisarRating(kra.getMidYearAppraisarRating());
						obj.setFinalYearAppraisarRating(kra.getFinalYearAppraisarRating());
					}
					if(userType.equals(PMSConstants.SECOND_LEVEL_MANAGER))
					{
						if(obj.getMidYearAppraisarRating() != kra.getMidYearAppraisarRating() && obj.getMidYearAppraisarRating() !=null)
						{
							obj.setMidYearHighlights(PMSConstants.STATUS_ACTIVE);
						}
						obj.setMidYearAppraisarRating(kra.getMidYearAppraisarRating());
						if(obj.getFinalYearAppraisarRating() != kra.getFinalYearAppraisarRating() && obj.getFinalYearAppraisarRating() != null)
						{
							obj.setFinalYearHighlights(PMSConstants.STATUS_ACTIVE);
						}
						obj.setFinalYearAppraisarRating(kra.getFinalYearAppraisarRating());
					}
					if (object.getType().equals(PMSConstants.BUTTON_SUBMIT)
							&& userType.equals(PMSConstants.USER_EMPLOYEE)) {
						obj.setIsValidatedByEmployee(PMSConstants.STATUS_ACTIVE);
						responseObject.setString("Successfully submitted");
					}
					if (object.getType().equals(PMSConstants.BUTTON_SUBMIT)
							&& userType.equals(PMSConstants.FIRST_LEVEL_MANAGER)) {
						obj.setIsValidatedByFirstLevel(PMSConstants.STATUS_ACTIVE);
						responseObject.setString("Successfully submitted");
					}
					if (object.getType().equals(PMSConstants.BUTTON_SUBMIT)
							&& userType.equals(PMSConstants.SECOND_LEVEL_MANAGER)) {
						obj.setIsValidatedBySecondLevel(PMSConstants.STATUS_ACTIVE);
						responseObject.setString("Successfully submitted");
					}
					session.update(obj);
					if (previousKraId.size() > 0) {
						previousKraId.remove(new Integer(kra.getId()));
					}
				}
			}
			for (KraDetails kra : object.getKraDetails().getSectionCList()) {
				if (kra.getId() == null) {
					kra.setEmpCode(empCode);
					kra.setCreatedBy(empCode);
					kra.setCreatedOn(new Date());
					kra.setAppraisalYearId(activeAppraisalYearId);
					kra.setStatus(PMSConstants.STATUS_ACTIVE);
					kra.setIsValidatedByEmployee(PMSConstants.STATUS_DELETED);
					kra.setIsValidatedByFirstLevel(PMSConstants.STATUS_DELETED);
					kra.setIsValidatedBySecondLevel(PMSConstants.STATUS_DELETED);
					responseObject.setString("Successfully saved");
					if (object.getType().equals(PMSConstants.BUTTON_SUBMIT) && userType.equals(PMSConstants.USER_EMPLOYEE)) {
						kra.setIsValidatedByEmployee(PMSConstants.STATUS_ACTIVE);
						responseObject.setString("Successfully submitted");
					}
					if(object.getType().equals(PMSConstants.BUTTON_SUBMIT) && userType.equals(PMSConstants.FIRST_LEVEL_MANAGER))
					{
						kra.setIsValidatedByEmployee(PMSConstants.STATUS_ACTIVE);
						kra.setIsValidatedByFirstLevel(PMSConstants.STATUS_ACTIVE);
						responseObject.setString("Successfully submitted");
					}
					if(object.getType().equals(PMSConstants.BUTTON_SUBMIT) && userType.equals(PMSConstants.SECOND_LEVEL_MANAGER))
					{
						kra.setIsValidatedByEmployee(PMSConstants.STATUS_ACTIVE);
						kra.setIsValidatedByFirstLevel(PMSConstants.STATUS_ACTIVE);
						kra.setIsValidatedBySecondLevel(PMSConstants.STATUS_ACTIVE);
						responseObject.setString("Successfully submitted");
					}
					session.persist(kra);
				} else {
					KraDetails obj = (KraDetails) session.get(KraDetails.class, kra.getId());
					obj.setMidYearAchievement(kra.getMidYearAchievement());
					obj.setFinalYearAchievement(kra.getFinalYearAchievement());
					obj.setAchievementDate(kra.getAchievementDate());
					obj.setSmartGoal(kra.getSmartGoal());
					obj.setTarget(kra.getTarget());
					obj.setWeightage(kra.getWeightage());
					obj.setMidYearSelfRating(kra.getMidYearSelfRating());
					obj.setFinalYearSelfRating(kra.getFinalYearSelfRating());
					obj.setModifiedBy(empCode);
					obj.setModifiedOn(new Date());
					obj.setFileName(kra.getFileName());
					responseObject.setString("Successfully saved");
					if(userType.equals(PMSConstants.FIRST_LEVEL_MANAGER) || userType.equals(PMSConstants.SECOND_LEVEL_MANAGER))
					{
						obj.setMidYearAssessmentRemarks(kra.getMidYearAssessmentRemarks());
						obj.setRemarks(kra.getRemarks());
						
					}
					if(userType.equals(PMSConstants.FIRST_LEVEL_MANAGER))
					{
						obj.setMidYearAppraisarRating(kra.getMidYearAppraisarRating());
						obj.setFinalYearAppraisarRating(kra.getFinalYearAppraisarRating());
					}
					if(userType.equals(PMSConstants.SECOND_LEVEL_MANAGER))
					{
						if(obj.getMidYearAppraisarRating() != kra.getMidYearAppraisarRating() && obj.getMidYearAppraisarRating() !=null)
						{
							obj.setMidYearHighlights(PMSConstants.STATUS_ACTIVE);
						}
						obj.setMidYearAppraisarRating(kra.getMidYearAppraisarRating());
						if(obj.getFinalYearAppraisarRating() != kra.getFinalYearAppraisarRating() && obj.getFinalYearAppraisarRating() != null)
						{
							obj.setFinalYearHighlights(PMSConstants.STATUS_ACTIVE);
						}
						obj.setFinalYearAppraisarRating(kra.getFinalYearAppraisarRating());
					}
					if (object.getType().equals(PMSConstants.BUTTON_SUBMIT)
							&& userType.equals(PMSConstants.USER_EMPLOYEE)) {
						obj.setIsValidatedByEmployee(PMSConstants.STATUS_ACTIVE);
						responseObject.setString("Successfully submitted");
					}
					if (object.getType().equals(PMSConstants.BUTTON_SUBMIT)
							&& userType.equals(PMSConstants.FIRST_LEVEL_MANAGER)) {
						obj.setIsValidatedByFirstLevel(PMSConstants.STATUS_ACTIVE);
						responseObject.setString("Successfully submitted");
					}
					if (object.getType().equals(PMSConstants.BUTTON_SUBMIT)
							&& userType.equals(PMSConstants.SECOND_LEVEL_MANAGER)) {
						obj.setIsValidatedBySecondLevel(PMSConstants.STATUS_ACTIVE);
						responseObject.setString("Successfully submitted");
					}
					session.update(obj);
					if (previousKraId.size() > 0) {
						previousKraId.remove(new Integer(kra.getId()));
					}
				}
			}
			for (KraDetails kra : object.getKraDetails().getSectionDList()) {
				if (kra.getId() == null) {
					kra.setEmpCode(empCode);
					kra.setCreatedBy(empCode);
					kra.setCreatedOn(new Date());
					kra.setAppraisalYearId(activeAppraisalYearId);
					kra.setStatus(PMSConstants.STATUS_ACTIVE);
					kra.setIsValidatedByEmployee(PMSConstants.STATUS_DELETED);
					kra.setIsValidatedByFirstLevel(PMSConstants.STATUS_DELETED);
					kra.setIsValidatedBySecondLevel(PMSConstants.STATUS_DELETED);
					responseObject.setString("Successfully saved");
					if (object.getType().equals(PMSConstants.BUTTON_SUBMIT) && userType.equals(PMSConstants.USER_EMPLOYEE)) {
						responseObject.setString("Successfully submitted");
						kra.setIsValidatedByEmployee(PMSConstants.STATUS_ACTIVE);
						responseObject.setString("Successfully submitted");
					}
					if(object.getType().equals(PMSConstants.BUTTON_SUBMIT) && userType.equals(PMSConstants.FIRST_LEVEL_MANAGER))
					{
						responseObject.setString("Successfully submitted");
						kra.setIsValidatedByEmployee(PMSConstants.STATUS_ACTIVE);
						kra.setIsValidatedByFirstLevel(PMSConstants.STATUS_ACTIVE);
						responseObject.setString("Successfully submitted");
					}
					if(object.getType().equals(PMSConstants.BUTTON_SUBMIT) && userType.equals(PMSConstants.SECOND_LEVEL_MANAGER))
					{
						responseObject.setString("Successfully submitted");
						kra.setIsValidatedByEmployee(PMSConstants.STATUS_ACTIVE);
						kra.setIsValidatedByFirstLevel(PMSConstants.STATUS_ACTIVE);
						kra.setIsValidatedBySecondLevel(PMSConstants.STATUS_ACTIVE);
						responseObject.setString("Successfully submitted");
					}
					
					session.persist(kra);
				} else {
					KraDetails obj = (KraDetails) session.get(KraDetails.class, kra.getId());
					obj.setMidYearAchievement(kra.getMidYearAchievement());
					obj.setFinalYearAchievement(kra.getFinalYearAchievement());
					obj.setAchievementDate(kra.getAchievementDate());
					obj.setSmartGoal(kra.getSmartGoal());
					obj.setTarget(kra.getTarget());
					obj.setFileName(kra.getFileName());
					obj.setWeightage(kra.getWeightage());
					obj.setMidYearSelfRating(kra.getMidYearSelfRating());
					obj.setFinalYearSelfRating(kra.getFinalYearSelfRating());
					obj.setModifiedBy(empCode);
					obj.setModifiedOn(new Date());
					responseObject.setString("Successfully saved");
//					obj.setMidYearAppraisarRating(kra.getMidYearAppraisarRating());
//					obj.setFinalYearAppraisarRating(kra.getFinalYearAppraisarRating());
					if(userType.equals(PMSConstants.FIRST_LEVEL_MANAGER) || userType.equals(PMSConstants.SECOND_LEVEL_MANAGER))
					{
						obj.setMidYearAssessmentRemarks(kra.getMidYearAssessmentRemarks());
						obj.setRemarks(kra.getRemarks());
						
					}
					if(userType.equals(PMSConstants.FIRST_LEVEL_MANAGER))
					{
						obj.setMidYearAppraisarRating(kra.getMidYearAppraisarRating());
						obj.setFinalYearAppraisarRating(kra.getFinalYearAppraisarRating());
					}
					if(userType.equals(PMSConstants.SECOND_LEVEL_MANAGER))
					{
						if(obj.getMidYearAppraisarRating() != kra.getMidYearAppraisarRating() && obj.getMidYearAppraisarRating() !=null)
						{
							obj.setMidYearHighlights(PMSConstants.STATUS_ACTIVE);
						}
						obj.setMidYearAppraisarRating(kra.getMidYearAppraisarRating());
						if(obj.getFinalYearAppraisarRating() != kra.getFinalYearAppraisarRating() && obj.getFinalYearAppraisarRating() != null)
						{
							obj.setFinalYearHighlights(PMSConstants.STATUS_ACTIVE);
						}
						obj.setFinalYearAppraisarRating(kra.getFinalYearAppraisarRating());
					}
					if (object.getType().equals(PMSConstants.BUTTON_SUBMIT)
							&& userType.equals(PMSConstants.USER_EMPLOYEE)) {
						responseObject.setString("Successfully submitted");
						obj.setIsValidatedByEmployee(PMSConstants.STATUS_ACTIVE);
					}
					if (object.getType().equals(PMSConstants.BUTTON_SUBMIT)
							&& userType.equals(PMSConstants.FIRST_LEVEL_MANAGER)) {
						responseObject.setString("Successfully submitted");
						obj.setIsValidatedByFirstLevel(PMSConstants.STATUS_ACTIVE);
					}
					if (object.getType().equals(PMSConstants.BUTTON_SUBMIT)
							&& userType.equals(PMSConstants.SECOND_LEVEL_MANAGER)) {
						responseObject.setString("Successfully submitted");
						obj.setIsValidatedBySecondLevel(PMSConstants.STATUS_ACTIVE);
					}
					session.update(obj);
					if (previousKraId.size() > 0) {
						previousKraId.remove(new Integer(kra.getId()));
					}
				}
			}
			for (int i = 0; i < previousKraId.size(); i++) {
				KraDetails obj = (KraDetails) session.get(KraDetails.class, previousKraId.get(i));
				session.delete(obj);
			}
			tx.commit();
			responseObject.setInteger(PMSConstants.STATUS_SUCCESS);
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			responseObject.setInteger(PMSConstants.STATUS_FAILED);
			responseObject.setString("Exception while adding new Kra ... " + e);
			responseObject.setObject(e);
			logger.error("Exception while adding new Kra ... " + e);
		} finally {
			session.flush();
			session.close();
		}
		return responseObject;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseObject getCurrentEmployeeKRADetails(String empCode, Integer appraisalYearId,String kraScreen) {
		Session session = this.sessionFactory.openSession();
		ResponseObject res = new ResponseObject();
		try {
//			List<KraNew> kraNewtest = new ArrayList<KraNew>();
//			List<KraDetails> kraDetails = new ArrayList<KraDetails>();
//			Criteria criteriaTest = session.createCriteria(KraNew.class);
//			criteriaTest.add(Restrictions.eq("status", PMSConstants.STATUS_ACTIVE));
//			criteriaTest.add(Restrictions.eq("appraisalYearId", appraisalYearId));
//			criteriaTest.add(Restrictions.eq("empCode", empCode));
//			kraNewtest = criteriaTest.list();
//			if(kraScreen != null && kraNewtest.size() > 0)
//			{
//			if(kraScreen.equals(PMSConstants.KRA_SCREEN))
//			{
//				
//				Criteria criteria = session.createCriteria(KraDetails.class);
//				criteria.add(Restrictions.eq("status", PMSConstants.STATUS_ACTIVE));
//				criteria.add(Restrictions.eq("appraisalYearId", appraisalYearId));
//				criteria.add(Restrictions.eq("empCode", empCode));
//				kraDetails = criteria.list();
//				for (KraDetails kraO : kraDetails){
//						KraNew obj = new KraNew();
//						obj.setId(kraO.getId());
//						obj.setEmpCode(kraO.getEmpCode());
//						obj.setCreatedBy(kraO.getEmpCode());
//						obj.setAppraisalYearId(kraO.getAppraisalYearId());
//						obj.setSectionName(kraO.getSectionName());
//						obj.setSmartGoal(kraO.getSmartGoal());
//						obj.setTarget(kraO.getTarget());
//						obj.setAchievementDate(kraO.getAchievementDate());
//						obj.setWeightage(kraO.getWeightage());
//						obj.setMidYearAchievement(kraO.getMidYearAchievement());
//						obj.setMidYearSelfRating(kraO.getMidYearSelfRating());
//						obj.setMidYearAppraisarRating(kraO.getMidYearAppraisarRating());
//						obj.setMidYearAssessmentRemarks(kraO.getMidYearAssessmentRemarks());
//						obj.setKraScreen(PMSConstants.OLD_KRA);
//						kraNewtest.add(obj);
//				}
//			}
//			}
//			if(kraNewtest.size() == 0)
//			{
			Criteria criteria = session.createCriteria(KraDetails.class);
			criteria.add(Restrictions.eq("status", PMSConstants.STATUS_ACTIVE));
			criteria.add(Restrictions.eq("appraisalYearId", appraisalYearId));
			criteria.add(Restrictions.eq("empCode", empCode));
			res.setObject(criteria.list());
			res.setInteger(PMSConstants.STATUS_SUCCESS);
			res.setString("OK");
//			}
//			if(kraNewtest.size() > 0)
//			{
//				res.setObject(kraNewtest);
//				res.setInteger(PMSConstants.STATUS_SUCCESS);
//				res.setString("OK");
//			}
			
		} catch (Exception e) {
			e.printStackTrace();
			res.setObject(e);
			res.setInteger(PMSConstants.STATUS_FAILED);
			res.setString("Excepting will getting CurrentEmployeeKRADetails." + e);
			logger.error("Excepting will getting CurrentEmployeeKRADetails. " + e);
		} finally {
			session.flush();
			session.close();
		}
		return res;
	}

	@Override
	public List<EmployeeKRAData> getValidatedKRADetails(String empCode, Integer appraisalYearId,String managerType) {
		Session session = this.sessionFactory.openSession();
		List<EmployeeKRAData> list = new ArrayList<EmployeeKRAData>();
		try {
			String sql = "select ID as id,KRA_TYPE as kraType from kra_new where"
					+ " APPRAISAL_YEAR_ID =:APPRAISAL_YEAR_ID ";
			if(empCode!=PMSConstants.NULL_VALUE)
			{
				sql = sql + " and EMP_CODE =:EMP_CODE";
			}
			if(managerType.equals(PMSConstants.FIRST_LEVEL_MANAGER))
			{
				sql = sql + " and ISVALIDATEDBY_FIRSTLEVEL =:ISVALIDATEDBY_FIRSTLEVEL";		
			}
			if(managerType.equals(PMSConstants.SECOND_LEVEL_MANAGER))
			{
				sql = sql + " and ISVALIDATEDBY_SECONDLEVEL =:ISVALIDATEDBY_SECONDLEVEL";
			}
			Query query = session.createSQLQuery(sql).addEntity(EmployeeKRAData.class);
			if(managerType.equals(PMSConstants.FIRST_LEVEL_MANAGER))
			{
				query.setParameter("ISVALIDATEDBY_FIRSTLEVEL", PMSConstants.STATUS_ACTIVE);	
			}
			if(managerType.equals(PMSConstants.SECOND_LEVEL_MANAGER))
			{
				query.setParameter("ISVALIDATEDBY_SECONDLEVEL", PMSConstants.STATUS_ACTIVE);
			}
			if(empCode!=PMSConstants.NULL_VALUE)
			{
				query.setParameter("EMP_CODE", empCode);
			}
			query.setParameter("APPRAISAL_YEAR_ID", appraisalYearId);
//			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			list = query.list();
			if(list.size() == 0)
			{
				String sql1 = "select ID as id,KRA_TYPE as kraType from kra_details where"
						+ " APPRAISAL_YEAR_ID =:APPRAISAL_YEAR_ID ";
				if(empCode!=PMSConstants.NULL_VALUE)
				{
					sql1 = sql1 + " and EMP_CODE =:EMP_CODE";
				}
				if(managerType.equals(PMSConstants.FIRST_LEVEL_MANAGER))
				{
					sql1 = sql1 + " and ISVALIDATEDBY_FIRSTLEVEL =:ISVALIDATEDBY_FIRSTLEVEL";			
				}
				if(managerType.equals(PMSConstants.SECOND_LEVEL_MANAGER))
				{
					sql1 = sql1 + " and ISVALIDATEDBY_SECONDLEVEL =:ISVALIDATEDBY_SECONDLEVEL";
				}
				Query query1 = session.createSQLQuery(sql1).addEntity(EmployeeKRAData.class);
				if(managerType.equals(PMSConstants.FIRST_LEVEL_MANAGER))
				{
					query1.setParameter("ISVALIDATEDBY_FIRSTLEVEL", PMSConstants.STATUS_ACTIVE);	
				}
				if(managerType.equals(PMSConstants.SECOND_LEVEL_MANAGER))
				{
					query1.setParameter("ISVALIDATEDBY_SECONDLEVEL", PMSConstants.STATUS_ACTIVE);
				}
				if(empCode!=PMSConstants.NULL_VALUE)
				{
					query1.setParameter("EMP_CODE", empCode);
				}
				query1.setParameter("APPRAISAL_YEAR_ID", appraisalYearId);
//				query1.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
				list = query1.list();
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseObject forwardEmployeeKRADATAToCurrentYear(String empCode,Integer appraisalYearId) {
		Integer previousYearId = DbUtils.getPrevioueYearId();
		ResponseObject res = getCurrentEmployeeKRADetails(empCode,previousYearId,null);
		ResponseObject responseObject = new ResponseObject();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<KraDetails> oldKRAData = (List<KraDetails>) res.getObject();
		Integer kraCount = 0;
		try {
			for (KraDetails kra : oldKRAData) {
					KraDetails obj = new KraDetails();
					obj.setEmpCode(empCode);
					obj.setCreatedBy(empCode);
					obj.setCreatedOn(new Date());
					obj.setAppraisalYearId(appraisalYearId);
					obj.setStatus(PMSConstants.STATUS_ACTIVE);
					obj.setIsValidatedByEmployee(PMSConstants.STATUS_DELETED);
					obj.setIsValidatedByFirstLevel(PMSConstants.STATUS_DELETED);
					obj.setIsValidatedBySecondLevel(PMSConstants.STATUS_DELETED);
					obj.setSectionName(kra.getSectionName());
					obj.setAchievementDate(kra.getAchievementDate());
					obj.setSmartGoal(kra.getSmartGoal());
					obj.setTarget(kra.getTarget());
					obj.setWeightage(kra.getWeightage());
					session.persist(obj);
					kraCount++;
			}
			tx.commit();
			responseObject.setInteger(PMSConstants.STATUS_SUCCESS);
			if(kraCount > 0)
			{
				responseObject.setString("Successfully saved");	
				responseObject.setObject(getCurrentEmployeeKRADetails(empCode,appraisalYearId,null));
			}
			else{
				responseObject.setString("No previous year data to carry forward");
			}
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			responseObject.setInteger(PMSConstants.STATUS_FAILED);
			responseObject.setString("Exception while adding new Kra ... " + e);
			responseObject.setObject(e);
			logger.error("Exception while adding new Kra ... " + e);
		} finally {
			session.flush();
			session.close();
		}
		return responseObject;
	}

	@Override
	public List<EmployeeKRADetails> getEmployeeKRADetails(String empCode, Integer appraisalYearId) {
		Session session = this.sessionFactory.openSession();
		List<EmployeeKRADetails> employeeKRADetails = new ArrayList<EmployeeKRADetails>();
		try {
			String sql ="select kra.ID as kraId, kra.WEIGHTAGE as weightage,kra.MID_YEAR_APPRAISAR_RATING as midYearAppraisarRating,"
					+ " kra.FINAL_YEAR_APPRAISAR_RATING as finalYearAppraisarRating "
					+ " from kra_details as kra where "
					+ " kra.APPRAISAL_YEAR_ID =:APPRAISAL_YEAR_ID and kra.EMP_CODE =:EMP_CODE ";
			Query query = session.createSQLQuery(sql).addEntity(EmployeeKRADetails.class);
			query.setParameter("EMP_CODE", empCode);
			query.setParameter("APPRAISAL_YEAR_ID", appraisalYearId);
			employeeKRADetails	= query.list();	
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception while getting getEmployeeKRADetails List" + e);
		} finally {
			session.flush();
			session.close();
		}
		return employeeKRADetails;
	}

	@Override
	public ResponseObject getAllDepartmentWiseKRAList(Integer departmentId, Integer appraisalYearId,String empCode) {
		Session session = this.sessionFactory.openSession();
		ResponseObject res = new ResponseObject();
		try {
			List<EmployeeBasicDetails> list = DbUtils.getEmployeeBasicDetails(departmentId,appraisalYearId,empCode);
			List<List<KraDetails>> kraList = new ArrayList<List<KraDetails>>();
			for(int i=0;i<list.size();i++)
			{
			List<KraDetails> kraListInner = new ArrayList<KraDetails>();
			Criteria criteria = session.createCriteria(KraDetails.class);
			criteria.add(Restrictions.eq("status", PMSConstants.STATUS_ACTIVE));
			criteria.add(Restrictions.eq("appraisalYearId", appraisalYearId));
			criteria.add(Restrictions.eq("empCode", list.get(i).getEmpCode()));
			kraListInner = criteria.list();
			for(int j =0;j<kraListInner.size();j++)
			{
				kraListInner.get(j).setEmpName(list.get(i).getEmpName());
				kraListInner.get(j).setDepartmentName(list.get(i).getDepartmentName());
				kraListInner.get(j).setDesignationName(list.get(i).getDesignationName());
			}
			if(kraListInner.size() == 0)
			{
				KraDetails empKraDetails = new KraDetails();
				empKraDetails.setEmpName(list.get(i).getEmpName());
				empKraDetails.setDepartmentName(list.get(i).getDepartmentName());
				empKraDetails.setDesignationName(list.get(i).getDesignationName());
				kraListInner.add(empKraDetails);
			}
			kraList.add(kraListInner);
			}
			logger.info("kraListInner"+kraList);
			res.setObject(kraList);
			res.setInteger(PMSConstants.STATUS_SUCCESS);
			res.setString("OK");
		} catch (HibernateException e) {
			e.printStackTrace();
			res.setObject(e);
			res.setInteger(PMSConstants.STATUS_FAILED);
			res.setString("Excepting will getting getAllDepartmentWiseKRAList." + e);
			logger.error("Excepting will getting getAllDepartmentWiseKRAList. " + e);
		} finally {
			session.flush();
			session.close();
		}
		return res;
	}

//	@Override
//	public ResponseObject getNewKRADetails(String empCode, Integer appraisalYearId) {
//		Session session = this.sessionFactory.openSession();
//		ResponseObject res = new ResponseObject();
//		try {
//			Criteria criteria = session.createCriteria(KraNew.class);
//			criteria.add(Restrictions.eq("status", PMSConstants.STATUS_ACTIVE));
//			criteria.add(Restrictions.eq("appraisalYearId", appraisalYearId));
//			criteria.add(Restrictions.eq("empCode", empCode));
//			res.setObject(criteria.list());
//			res.setInteger(PMSConstants.STATUS_SUCCESS);
//			res.setString("OK");
//		} catch (HibernateException e) {
//			e.printStackTrace();
//			res.setObject(e);
//			res.setInteger(PMSConstants.STATUS_FAILED);
//			res.setString("Excepting will getting getNewKRADetails." + e);
//			logger.error("Excepting will getting getNewKRADetails. " + e);
//		} finally {
//			session.flush();
//			session.close();
//		}
//		return res;
//	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseObject addNewKraDetails(RequestObject object, String empCode, String userType) {
			ResponseObject responseObject = new ResponseObject();
			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			List<Integer> previousKraId = object.getList();
			try {
				Integer activeAppraisalYearId = CommonUtils.getActiveAppraisalYearId();
				for (KraNew kra : object.getKraNewDetails().getSectionAList()) {
					if (kra.getId() == null) {
						kra.setEmpCode(empCode);
						kra.setCreatedBy(empCode);
						kra.setKraType(PMSConstants.NEW_KRA);
						kra.setCreatedOn(new Date());
						kra.setAppraisalYearId(activeAppraisalYearId);
						kra.setStatus(PMSConstants.STATUS_ACTIVE);
						kra.setIsValidatedByEmployee(PMSConstants.STATUS_DELETED);
						kra.setIsValidatedByFirstLevel(PMSConstants.STATUS_DELETED);
						kra.setIsValidatedBySecondLevel(PMSConstants.STATUS_DELETED);
						responseObject.setString("Successfully saved");
						if (object.getType().equals(PMSConstants.BUTTON_SUBMIT) && userType.equals(PMSConstants.USER_EMPLOYEE)) {
							kra.setIsValidatedByEmployee(PMSConstants.STATUS_ACTIVE);
							responseObject.setString("Successfully submitted");
						}
						if(object.getType().equals(PMSConstants.BUTTON_SUBMIT) && userType.equals(PMSConstants.FIRST_LEVEL_MANAGER))
						{
							kra.setIsValidatedByEmployee(PMSConstants.STATUS_ACTIVE);
							kra.setIsValidatedByFirstLevel(PMSConstants.STATUS_ACTIVE);
							responseObject.setString("Successfully submitted");
						}
						if(object.getType().equals(PMSConstants.BUTTON_SUBMIT) && userType.equals(PMSConstants.SECOND_LEVEL_MANAGER))
						{
							kra.setIsValidatedByEmployee(PMSConstants.STATUS_ACTIVE);
							kra.setIsValidatedByFirstLevel(PMSConstants.STATUS_ACTIVE);
							kra.setIsValidatedBySecondLevel(PMSConstants.STATUS_ACTIVE);
							responseObject.setString("Successfully submitted");
						}				
						session.persist(kra);
					} else {
						KraNew obj = (KraNew) session.get(KraNew.class, kra.getId());
						obj.setMidYearAchievement(kra.getMidYearAchievement());
						obj.setFinalYearAchievement(kra.getFinalYearAchievement());
						obj.setAchievementDate(kra.getAchievementDate());
						obj.setSmartGoal(kra.getSmartGoal());
						obj.setTarget(kra.getTarget());
						obj.setWeightage(kra.getWeightage());
						obj.setMidYearSelfRating(kra.getMidYearSelfRating());
						obj.setFinalYearSelfRating(kra.getFinalYearSelfRating());
						obj.setModifiedBy(empCode);
						obj.setKraType(PMSConstants.NEW_KRA);
						obj.setFileName(kra.getFileName());
						obj.setModifiedOn(new Date());
						responseObject.setString("Successfully saved");
						if(userType.equals(PMSConstants.FIRST_LEVEL_MANAGER))
						{
							obj.setMidYearAppraisarRating(kra.getMidYearAppraisarRating());
							obj.setFinalYearAppraisarRating(kra.getFinalYearAppraisarRating());
						}
						if(userType.equals(PMSConstants.SECOND_LEVEL_MANAGER))
						{
							if(obj.getMidYearAppraisarRating() != kra.getMidYearAppraisarRating() && obj.getMidYearAppraisarRating() !=null)
							{
								obj.setMidYearHighlights(PMSConstants.STATUS_ACTIVE);
							}
							obj.setMidYearAppraisarRating(kra.getMidYearAppraisarRating());
							if(obj.getFinalYearAppraisarRating() != kra.getFinalYearAppraisarRating() && obj.getFinalYearAppraisarRating() != null)
							{
								obj.setFinalYearHighlights(PMSConstants.STATUS_ACTIVE);
							}
							obj.setFinalYearAppraisarRating(kra.getFinalYearAppraisarRating());
						}
						if(userType.equals(PMSConstants.FIRST_LEVEL_MANAGER) || userType.equals(PMSConstants.SECOND_LEVEL_MANAGER))
						{
							obj.setMidYearAssessmentRemarks(kra.getMidYearAssessmentRemarks());
							obj.setRemarks(kra.getRemarks());
						}
						// obj.setIsValidatedByEmployee(PMSConstants.STATUS_DELETED);
						if (object.getType().equals(PMSConstants.BUTTON_SUBMIT) 
								&& userType.equals(PMSConstants.USER_EMPLOYEE)) {
							obj.setIsValidatedByEmployee(PMSConstants.STATUS_ACTIVE);
							responseObject.setString("Successfully submitted");
						}
						if (object.getType().equals(PMSConstants.BUTTON_SUBMIT)
								&& userType.equals(PMSConstants.FIRST_LEVEL_MANAGER)) {
							obj.setIsValidatedByFirstLevel(PMSConstants.STATUS_ACTIVE);
							responseObject.setString("Successfully submitted");
						}
						if (object.getType().equals(PMSConstants.BUTTON_SUBMIT)
								&& userType.equals(PMSConstants.SECOND_LEVEL_MANAGER)) {
							obj.setIsValidatedBySecondLevel(PMSConstants.STATUS_ACTIVE);
							responseObject.setString("Successfully submitted");
						}
						session.update(obj);
						if (previousKraId.size() > 0) {
							previousKraId.remove(new Integer(kra.getId()));
						}
					}
				}

				for (KraNew kra : object.getKraNewDetails().getSectionBList()) {
					if (kra.getId() == null) {
						kra.setEmpCode(empCode);
						kra.setCreatedBy(empCode);
						kra.setCreatedOn(new Date());
						kra.setKraType(PMSConstants.NEW_KRA);
						kra.setAppraisalYearId(activeAppraisalYearId);
						kra.setStatus(PMSConstants.STATUS_ACTIVE);
						kra.setIsValidatedByEmployee(PMSConstants.STATUS_DELETED);
						kra.setIsValidatedByFirstLevel(PMSConstants.STATUS_DELETED);
						kra.setIsValidatedBySecondLevel(PMSConstants.STATUS_DELETED);
						responseObject.setString("Successfully saved");
						if (object.getType().equals(PMSConstants.BUTTON_SUBMIT) && userType.equals(PMSConstants.USER_EMPLOYEE)) {
							kra.setIsValidatedByEmployee(PMSConstants.STATUS_ACTIVE);
							responseObject.setString("Successfully submitted");
						}
						if(object.getType().equals(PMSConstants.BUTTON_SUBMIT) && userType.equals(PMSConstants.FIRST_LEVEL_MANAGER))
						{
							kra.setIsValidatedByEmployee(PMSConstants.STATUS_ACTIVE);
							kra.setIsValidatedByFirstLevel(PMSConstants.STATUS_ACTIVE);
							responseObject.setString("Successfully submitted");
						}
						if(object.getType().equals(PMSConstants.BUTTON_SUBMIT) && userType.equals(PMSConstants.SECOND_LEVEL_MANAGER))
						{
							kra.setIsValidatedByEmployee(PMSConstants.STATUS_ACTIVE);
							kra.setIsValidatedByFirstLevel(PMSConstants.STATUS_ACTIVE);
							kra.setIsValidatedBySecondLevel(PMSConstants.STATUS_ACTIVE);
							responseObject.setString("Successfully submitted");
						}
						session.persist(kra);
					} else {
						KraNew obj = (KraNew) session.get(KraNew.class, kra.getId());
						obj.setKraType(PMSConstants.NEW_KRA);
						obj.setMidYearAchievement(kra.getMidYearAchievement());
						obj.setFinalYearAchievement(kra.getFinalYearAchievement());
						obj.setAchievementDate(kra.getAchievementDate());
						obj.setSmartGoal(kra.getSmartGoal());
						obj.setTarget(kra.getTarget());
						obj.setWeightage(kra.getWeightage());
						obj.setMidYearSelfRating(kra.getMidYearSelfRating());
						obj.setFinalYearSelfRating(kra.getFinalYearSelfRating());
						obj.setModifiedBy(empCode);
						obj.setModifiedOn(new Date());
						obj.setFileName(kra.getFileName());
						responseObject.setString("Successfully saved");
						if(userType.equals(PMSConstants.FIRST_LEVEL_MANAGER) || userType.equals(PMSConstants.SECOND_LEVEL_MANAGER))
						{
							obj.setMidYearAssessmentRemarks(kra.getMidYearAssessmentRemarks());
							obj.setRemarks(kra.getRemarks());
							
						}
						if(userType.equals(PMSConstants.FIRST_LEVEL_MANAGER))
						{
							obj.setMidYearAppraisarRating(kra.getMidYearAppraisarRating());
							obj.setFinalYearAppraisarRating(kra.getFinalYearAppraisarRating());
						}
						if(userType.equals(PMSConstants.SECOND_LEVEL_MANAGER))
						{
							if(obj.getMidYearAppraisarRating() != kra.getMidYearAppraisarRating() && obj.getMidYearAppraisarRating() !=null)
							{
								obj.setMidYearHighlights(PMSConstants.STATUS_ACTIVE);
							}
							obj.setMidYearAppraisarRating(kra.getMidYearAppraisarRating());
							if(obj.getFinalYearAppraisarRating() != kra.getFinalYearAppraisarRating() && obj.getFinalYearAppraisarRating() != null)
							{
								obj.setFinalYearHighlights(PMSConstants.STATUS_ACTIVE);
							}
							obj.setFinalYearAppraisarRating(kra.getFinalYearAppraisarRating());
						}
						if (object.getType().equals(PMSConstants.BUTTON_SUBMIT)
								&& userType.equals(PMSConstants.USER_EMPLOYEE)) {
							obj.setIsValidatedByEmployee(PMSConstants.STATUS_ACTIVE);
							responseObject.setString("Successfully submitted");
						}
						if (object.getType().equals(PMSConstants.BUTTON_SUBMIT)
								&& userType.equals(PMSConstants.FIRST_LEVEL_MANAGER)) {
							obj.setIsValidatedByFirstLevel(PMSConstants.STATUS_ACTIVE);
							responseObject.setString("Successfully submitted");
						}
						if (object.getType().equals(PMSConstants.BUTTON_SUBMIT)
								&& userType.equals(PMSConstants.SECOND_LEVEL_MANAGER)) {
							obj.setIsValidatedBySecondLevel(PMSConstants.STATUS_ACTIVE);
							responseObject.setString("Successfully submitted");
						}
						session.update(obj);
						if (previousKraId.size() > 0) {
							previousKraId.remove(new Integer(kra.getId()));
						}
					}
				}
				for (KraNew kra : object.getKraNewDetails().getSectionCList()) {
					if (kra.getId() == null) {
						kra.setEmpCode(empCode);
						kra.setCreatedBy(empCode);
						kra.setCreatedOn(new Date());
						kra.setKraType(PMSConstants.NEW_KRA);
						kra.setAppraisalYearId(activeAppraisalYearId);
						kra.setStatus(PMSConstants.STATUS_ACTIVE);
						kra.setIsValidatedByEmployee(PMSConstants.STATUS_DELETED);
						kra.setIsValidatedByFirstLevel(PMSConstants.STATUS_DELETED);
						kra.setIsValidatedBySecondLevel(PMSConstants.STATUS_DELETED);
						responseObject.setString("Successfully saved");
						if (object.getType().equals(PMSConstants.BUTTON_SUBMIT) && userType.equals(PMSConstants.USER_EMPLOYEE)) {
							kra.setIsValidatedByEmployee(PMSConstants.STATUS_ACTIVE);
							responseObject.setString("Successfully submitted");
						}
						if(object.getType().equals(PMSConstants.BUTTON_SUBMIT) && userType.equals(PMSConstants.FIRST_LEVEL_MANAGER))
						{
							kra.setIsValidatedByEmployee(PMSConstants.STATUS_ACTIVE);
							kra.setIsValidatedByFirstLevel(PMSConstants.STATUS_ACTIVE);
							responseObject.setString("Successfully submitted");
						}
						if(object.getType().equals(PMSConstants.BUTTON_SUBMIT) && userType.equals(PMSConstants.SECOND_LEVEL_MANAGER))
						{
							kra.setIsValidatedByEmployee(PMSConstants.STATUS_ACTIVE);
							kra.setIsValidatedByFirstLevel(PMSConstants.STATUS_ACTIVE);
							kra.setIsValidatedBySecondLevel(PMSConstants.STATUS_ACTIVE);
							responseObject.setString("Successfully submitted");
						}
						session.persist(kra);
					} else {
						KraNew obj = (KraNew) session.get(KraNew.class, kra.getId());
						obj.setMidYearAchievement(kra.getMidYearAchievement());
						obj.setKraType(PMSConstants.NEW_KRA);
						obj.setFinalYearAchievement(kra.getFinalYearAchievement());
						obj.setAchievementDate(kra.getAchievementDate());
						obj.setSmartGoal(kra.getSmartGoal());
						obj.setTarget(kra.getTarget());
						obj.setWeightage(kra.getWeightage());
						obj.setMidYearSelfRating(kra.getMidYearSelfRating());
						obj.setFinalYearSelfRating(kra.getFinalYearSelfRating());
						obj.setModifiedBy(empCode);
						obj.setModifiedOn(new Date());
						obj.setFileName(kra.getFileName());
						responseObject.setString("Successfully saved");
						if(userType.equals(PMSConstants.FIRST_LEVEL_MANAGER) || userType.equals(PMSConstants.SECOND_LEVEL_MANAGER))
						{
							obj.setMidYearAssessmentRemarks(kra.getMidYearAssessmentRemarks());
							obj.setRemarks(kra.getRemarks());
							
						}
						if(userType.equals(PMSConstants.FIRST_LEVEL_MANAGER))
						{
							obj.setMidYearAppraisarRating(kra.getMidYearAppraisarRating());
							obj.setFinalYearAppraisarRating(kra.getFinalYearAppraisarRating());
						}
						if(userType.equals(PMSConstants.SECOND_LEVEL_MANAGER))
						{
							if(obj.getMidYearAppraisarRating() != kra.getMidYearAppraisarRating() && obj.getMidYearAppraisarRating() !=null)
							{
								obj.setMidYearHighlights(PMSConstants.STATUS_ACTIVE);
							}
							obj.setMidYearAppraisarRating(kra.getMidYearAppraisarRating());
							if(obj.getFinalYearAppraisarRating() != kra.getFinalYearAppraisarRating() && obj.getFinalYearAppraisarRating() != null)
							{
								obj.setFinalYearHighlights(PMSConstants.STATUS_ACTIVE);
							}
							obj.setFinalYearAppraisarRating(kra.getFinalYearAppraisarRating());
						}
						if (object.getType().equals(PMSConstants.BUTTON_SUBMIT)
								&& userType.equals(PMSConstants.USER_EMPLOYEE)) {
							obj.setIsValidatedByEmployee(PMSConstants.STATUS_ACTIVE);
							responseObject.setString("Successfully submitted");
						}
						if (object.getType().equals(PMSConstants.BUTTON_SUBMIT)
								&& userType.equals(PMSConstants.FIRST_LEVEL_MANAGER)) {
							obj.setIsValidatedByFirstLevel(PMSConstants.STATUS_ACTIVE);
							responseObject.setString("Successfully submitted");
						}
						if (object.getType().equals(PMSConstants.BUTTON_SUBMIT)
								&& userType.equals(PMSConstants.SECOND_LEVEL_MANAGER)) {
							obj.setIsValidatedBySecondLevel(PMSConstants.STATUS_ACTIVE);
							responseObject.setString("Successfully submitted");
						}
						session.update(obj);
						if (previousKraId.size() > 0) {
							previousKraId.remove(new Integer(kra.getId()));
						}
					}
				}
				for (KraNew kra : object.getKraNewDetails().getSectionDList()) {
					if (kra.getId() == null) {
						kra.setEmpCode(empCode);
						kra.setCreatedBy(empCode);
						kra.setCreatedOn(new Date());
						kra.setKraType(PMSConstants.NEW_KRA);
						kra.setAppraisalYearId(activeAppraisalYearId);
						kra.setStatus(PMSConstants.STATUS_ACTIVE);
						kra.setIsValidatedByEmployee(PMSConstants.STATUS_DELETED);
						kra.setIsValidatedByFirstLevel(PMSConstants.STATUS_DELETED);
						kra.setIsValidatedBySecondLevel(PMSConstants.STATUS_DELETED);
						responseObject.setString("Successfully saved");
						if (object.getType().equals(PMSConstants.BUTTON_SUBMIT) && userType.equals(PMSConstants.USER_EMPLOYEE)) {
							responseObject.setString("Successfully submitted");
							kra.setIsValidatedByEmployee(PMSConstants.STATUS_ACTIVE);
							responseObject.setString("Successfully submitted");
						}
						if(object.getType().equals(PMSConstants.BUTTON_SUBMIT) && userType.equals(PMSConstants.FIRST_LEVEL_MANAGER))
						{
							responseObject.setString("Successfully submitted");
							kra.setIsValidatedByEmployee(PMSConstants.STATUS_ACTIVE);
							kra.setIsValidatedByFirstLevel(PMSConstants.STATUS_ACTIVE);
							responseObject.setString("Successfully submitted");
						}
						if(object.getType().equals(PMSConstants.BUTTON_SUBMIT) && userType.equals(PMSConstants.SECOND_LEVEL_MANAGER))
						{
							responseObject.setString("Successfully submitted");
							kra.setIsValidatedByEmployee(PMSConstants.STATUS_ACTIVE);
							kra.setIsValidatedByFirstLevel(PMSConstants.STATUS_ACTIVE);
							kra.setIsValidatedBySecondLevel(PMSConstants.STATUS_ACTIVE);
							responseObject.setString("Successfully submitted");
						}
						
						session.persist(kra);
					} else {
						KraNew obj = (KraNew) session.get(KraNew.class, kra.getId());
						obj.setMidYearAchievement(kra.getMidYearAchievement());
						obj.setKraType(PMSConstants.NEW_KRA);
						obj.setFinalYearAchievement(kra.getFinalYearAchievement());
						obj.setAchievementDate(kra.getAchievementDate());
						obj.setSmartGoal(kra.getSmartGoal());
						obj.setTarget(kra.getTarget());
						obj.setFileName(kra.getFileName());
						obj.setWeightage(kra.getWeightage());
						obj.setMidYearSelfRating(kra.getMidYearSelfRating());
						obj.setFinalYearSelfRating(kra.getFinalYearSelfRating());
						obj.setModifiedBy(empCode);
						obj.setModifiedOn(new Date());
						responseObject.setString("Successfully saved");
//						obj.setMidYearAppraisarRating(kra.getMidYearAppraisarRating());
//						obj.setFinalYearAppraisarRating(kra.getFinalYearAppraisarRating());
						if(userType.equals(PMSConstants.FIRST_LEVEL_MANAGER) || userType.equals(PMSConstants.SECOND_LEVEL_MANAGER))
						{
							obj.setMidYearAssessmentRemarks(kra.getMidYearAssessmentRemarks());
							obj.setRemarks(kra.getRemarks());
							
						}
						if(userType.equals(PMSConstants.FIRST_LEVEL_MANAGER))
						{
							obj.setMidYearAppraisarRating(kra.getMidYearAppraisarRating());
							obj.setFinalYearAppraisarRating(kra.getFinalYearAppraisarRating());
						}
						if(userType.equals(PMSConstants.SECOND_LEVEL_MANAGER))
						{
							if(obj.getMidYearAppraisarRating() != kra.getMidYearAppraisarRating() && obj.getMidYearAppraisarRating() !=null)
							{
								obj.setMidYearHighlights(PMSConstants.STATUS_ACTIVE);
							}
							obj.setMidYearAppraisarRating(kra.getMidYearAppraisarRating());
							if(obj.getFinalYearAppraisarRating() != kra.getFinalYearAppraisarRating() && obj.getFinalYearAppraisarRating() != null)
							{
								obj.setFinalYearHighlights(PMSConstants.STATUS_ACTIVE);
							}
							obj.setFinalYearAppraisarRating(kra.getFinalYearAppraisarRating());
						}
						if (object.getType().equals(PMSConstants.BUTTON_SUBMIT)
								&& userType.equals(PMSConstants.USER_EMPLOYEE)) {
							responseObject.setString("Successfully submitted");
							obj.setIsValidatedByEmployee(PMSConstants.STATUS_ACTIVE);
						}
						if (object.getType().equals(PMSConstants.BUTTON_SUBMIT)
								&& userType.equals(PMSConstants.FIRST_LEVEL_MANAGER)) {
							responseObject.setString("Successfully submitted");
							obj.setIsValidatedByFirstLevel(PMSConstants.STATUS_ACTIVE);
						}
						if (object.getType().equals(PMSConstants.BUTTON_SUBMIT)
								&& userType.equals(PMSConstants.SECOND_LEVEL_MANAGER)) {
							responseObject.setString("Successfully submitted");
							obj.setIsValidatedBySecondLevel(PMSConstants.STATUS_ACTIVE);
						}
						session.update(obj);
						if (previousKraId.size() > 0) {
							previousKraId.remove(new Integer(kra.getId()));
						}
					}
				}
				for (int i = 0; i < previousKraId.size(); i++) {
					KraNew obj = (KraNew) session.get(KraNew.class, previousKraId.get(i));
					session.delete(obj);
				}
//				String sql = "Update AppraisalYearDetails set firstManagerNewKRARejection =:firstManagerNewKRARejection where appraisalYearId =:appraisalYearId and empCode =:empCode";
//				Query query = session.createQuery(sql);
//				query.setParameter("appraisalYearId", activeAppraisalYearId);
//				query.setParameter("firstManagerNewKRARejection", PMSConstants.STATUS_DELETED);
//				query.setParameter("empCode", empCode);
//				query.executeUpdate();
				tx.commit();
				responseObject.setObject((List<KraNew>)getCurrentEmployeeNewKRADetails(empCode,activeAppraisalYearId).getObject());
				responseObject.setInteger(PMSConstants.STATUS_SUCCESS);
			} catch (Exception e) {
				if (tx != null)
					tx.rollback();
				e.printStackTrace();
				responseObject.setInteger(PMSConstants.STATUS_FAILED);
				responseObject.setString("Exception while adding new Kra ... " + e);
				responseObject.setObject(e);
				logger.error("Exception while adding new Kra ... " + e);
			} finally {
				session.flush();
				session.close();
			}
			return responseObject;
		}

	@Override
	public ResponseObject getCurrentEmployeeOldKRADetails(String empCode, Integer appraisalYearId) {
		Session session = this.sessionFactory.openSession();
		ResponseObject res = new ResponseObject();
		try {
			Criteria criteria = session.createCriteria(KraDetails.class);
			criteria.add(Restrictions.eq("status", PMSConstants.STATUS_ACTIVE));
			criteria.add(Restrictions.eq("appraisalYearId", appraisalYearId));
			criteria.add(Restrictions.eq("empCode", empCode));
			res.setObject(criteria.list());
			res.setInteger(PMSConstants.STATUS_SUCCESS);
			res.setString("OK");
		} catch (HibernateException e) {
			e.printStackTrace();
			res.setObject(e);
			res.setInteger(PMSConstants.STATUS_FAILED);
			res.setString("Excepting will getting getCurrentEmployeeOldKRADetails. " + e);
			logger.error("Excepting will getting getCurrentEmployeeOldKRADetails. " + e);
		} finally {
			session.flush();
			session.close();
		}
		return res;
	}

	@Override
	public ResponseObject getCurrentEmployeeNewKRADetails(String empCode, Integer appraisalYearId) {
		Session session = this.sessionFactory.openSession();
		ResponseObject res = new ResponseObject();
		try {
			Criteria criteriaTest = session.createCriteria(KraNew.class);
			criteriaTest.add(Restrictions.eq("status", PMSConstants.STATUS_ACTIVE));
			criteriaTest.add(Restrictions.eq("appraisalYearId", appraisalYearId));
			criteriaTest.add(Restrictions.eq("empCode", empCode));
			res.setObject(criteriaTest.list());
			res.setInteger(PMSConstants.STATUS_SUCCESS);
			res.setString("OK");
		} catch (HibernateException e) {
			e.printStackTrace();
			res.setObject(e);
			res.setInteger(PMSConstants.STATUS_FAILED);
			res.setString("Excepting will getting CurrentEmployeeKRADetails." + e);
			logger.error("Excepting will getting CurrentEmployeeKRADetails. " + e);
		} finally {
			session.flush();
			session.close();
		}
		return res;
	}

	@Override
	public List<Integer> getValidatedNewKRADetails(String empCode, Integer appraisalYearId, String managerType) {
		Session session = this.sessionFactory.openSession();
		List<Integer> list = new ArrayList<Integer>();
		try {
			String sql = "select id from KraNew where"
					+ " appraisalYearId =:appraisalYearId ";
			if(empCode!=PMSConstants.NULL_VALUE)
			{
				sql = sql + " and empCode =:empCode";
			}
			if(managerType.equals(PMSConstants.FIRST_LEVEL_MANAGER))
			{
				sql = sql + " and isValidatedByFirstLevel =:isValidatedByFirstLevel";		
			}
			if(managerType.equals(PMSConstants.SECOND_LEVEL_MANAGER))
			{
				sql = sql + " and isValidatedBySecondLevel =:isValidatedBySecondLevel";
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

	@SuppressWarnings("unchecked")
	@Override
	public ResponseObject copyOldKRAtoNewKRA(RequestObject object) {
		ResponseObject res = getCurrentEmployeeOldKRADetails(object.getEmpCode(),object.getAppraisalYearId());
		ResponseObject responseObject = new ResponseObject();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<KraDetails> oldKRAData = (List<KraDetails>) res.getObject();
		try {
			for (KraDetails kra : oldKRAData){
					KraNew obj = new KraNew();
					obj.setEmpCode(object.getEmpCode());
					obj.setCreatedBy(object.getEmpCode());
					obj.setCreatedOn(new Date());
//					obj.setMidYearAchievement(kra.getMidYearAchievement());
//					obj.setMidYearSelfRating(kra.getMidYearSelfRating());
//					obj.setMidYearAppraisarRating(kra.getMidYearAppraisarRating());
//					obj.setMidYearAssessmentRemarks(kra.getMidYearAssessmentRemarks());
					obj.setAppraisalYearId(object.getAppraisalYearId());
					obj.setStatus(PMSConstants.STATUS_ACTIVE);
					obj.setIsValidatedByEmployee(PMSConstants.STATUS_DELETED);
					obj.setIsValidatedByFirstLevel(PMSConstants.STATUS_DELETED);
					obj.setIsValidatedBySecondLevel(PMSConstants.STATUS_DELETED);
					obj.setSectionName(kra.getSectionName());
					obj.setAchievementDate(kra.getAchievementDate());
					obj.setSmartGoal(kra.getSmartGoal());
					obj.setTarget(kra.getTarget());
					obj.setWeightage(kra.getWeightage());
					session.persist(obj);
			}
			tx.commit();
			responseObject.setInteger(PMSConstants.STATUS_SUCCESS);
			responseObject.setString("Successfully saved");	
			responseObject.setObject((List<KraNew>) getCurrentEmployeeNewKRADetails(object.getEmpCode(),object.getAppraisalYearId()).getObject());
			} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			responseObject.setInteger(PMSConstants.STATUS_FAILED);
			responseObject.setString("Exception while adding old Kra into new kra ... " + e);
			responseObject.setObject(e);
			logger.error("Exception while adding old Kra into new kra ... " + e);
		} finally {
			session.flush();
			session.close();
		}
		return responseObject;
	}
}
