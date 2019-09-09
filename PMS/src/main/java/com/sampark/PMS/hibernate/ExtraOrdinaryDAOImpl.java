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
import com.sampark.PMS.dto.EmployeeBehaviouralCompetenceDetails;
import com.sampark.PMS.dto.EmployeeExtraOrdinaryDetails;
import com.sampark.PMS.dto.ExtraOrdinary;
import com.sampark.PMS.dto.ExtraOrdinaryDetails;
import com.sampark.PMS.object.RequestObject;
import com.sampark.PMS.object.ResponseObject;
import com.sampark.PMS.util.CommonUtils;

public class ExtraOrdinaryDAOImpl implements ExtraOrdinaryDAO {

	private static final Logger logger = LoggerFactory.getLogger(ExtraOrdinaryDAOImpl.class);
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public ResponseObject saveExtraOrdinaryDetails(RequestObject object, String empCode,String userType) {
		ResponseObject responseObject = new ResponseObject();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<Integer> extraOrdinaryOldIdList = object.getList();
		try {
			for (ExtraOrdinaryDetails res : object.getExtraOrdinaryDetails()) {
				if(res.getId() == null)
				{
					res.setEmpCode(empCode);
					res.setCreatedOn(new Date());
					res.setCreatedBy(empCode);
					res.setAppraisalYearId(object.getAppraisalYearId());
					res.setStatus(PMSConstants.STATUS_ACTIVE);
					res.setIsValidatedByEmployee(PMSConstants.STATUS_DELETED);
					res.setIsValidatedByFirstLevel(PMSConstants.STATUS_DELETED);
					res.setIsValidatedBySecondLevel(PMSConstants.STATUS_DELETED);
					responseObject.setString("Successfully saved");
					if (object.getType().equals(PMSConstants.BUTTON_SUBMIT)) {
						res.setIsValidatedByEmployee(PMSConstants.STATUS_ACTIVE);
						responseObject.setString("Successfully submitted");
					}
					session.persist(res);
				}
				else
				{
					ExtraOrdinaryDetails obj = (ExtraOrdinaryDetails) session.get(ExtraOrdinaryDetails.class, res.getId());
					obj.setContributions(res.getContributions());
					obj.setContributionDetails(res.getContributionDetails());
					obj.setWeightage(res.getWeightage());
					obj.setFinalYearSelfRating(res.getFinalYearSelfRating());
					obj.setRemarks(res.getRemarks());
					obj.setModifiedBy(empCode);
					obj.setModifiedOn(new Date());
					responseObject.setString("Successfully saved");
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
					
					if(userType.equals(PMSConstants.FIRST_LEVEL_MANAGER))
					{
						obj.setFinalYearAppraisarRating(res.getFinalYearAppraisarRating());
						
					}
					if(userType.equals(PMSConstants.SECOND_LEVEL_MANAGER))
					{
						if(obj.getFinalYearAppraisarRating() != res.getFinalYearAppraisarRating() && obj.getFinalYearAppraisarRating() != null)
						{
							obj.setFinalYearHighlights(PMSConstants.STATUS_ACTIVE);
						}
						obj.setFinalYearAppraisarRating(res.getFinalYearAppraisarRating());
					}
					
					
					session.update(obj);
					if(extraOrdinaryOldIdList.size() > 0)
					{
					extraOrdinaryOldIdList.remove(new Integer(res.getId()));
					}
				}
				
				// Set Dynamic Column Data
//				for(int i=0;i<res.getChild().size();i++)
//				{
//					switch(i)
//					{
//					case 0:obj.setField1(res.getChild().get(i).getTextData());
//					break;
//					case 1:obj.setField2(res.getChild().get(i).getTextData());
//					break;
//					case 2:obj.setField3(res.getChild().get(i).getTextData());
//					break;
//					case 3:obj.setField4(res.getChild().get(i).getTextData());
//					break;
//					case 4:obj.setField5(res.getChild().get(i).getTextData());
//					break;
//					}
//				}
				
				
			}
			for(int i=0;i<extraOrdinaryOldIdList.size();i++)
			{
				ExtraOrdinaryDetails obj = (ExtraOrdinaryDetails) session.get(ExtraOrdinaryDetails.class, extraOrdinaryOldIdList.get(i));
				session.delete(obj);
			}
			tx.commit();
			responseObject.setInteger(PMSConstants.STATUS_SUCCESS);
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			responseObject.setInteger(PMSConstants.STATUS_FAILED);
			responseObject.setString("Exception while adding new ExtraOrdinaryDetails ... " + e);
			responseObject.setObject(e);
			logger.error("Exception while adding new ExtraOrdinaryDetails ... " + e);
		} finally {
			session.flush();
			session.close();
		}
		return responseObject;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExtraOrdinary> getAllExtraOrdinary() {
		Session session = this.sessionFactory.openSession();
		List<ExtraOrdinary> list = new ArrayList<ExtraOrdinary>();
		try {
			Criteria criteria = session.createCriteria(ExtraOrdinary.class);
			list = criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception while getting ExtraOrdinary ... " + e);
		} finally {
			session.flush();
			session.close();
		}
		return list;
	}

	@Override
	public ResponseObject saveExtraOrdinary(RequestObject res) {
		ResponseObject responseObject = new ResponseObject();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			ExtraOrdinary object = (ExtraOrdinary) session.get(ExtraOrdinary.class, res.getId());
			object.setCreatedOn(new Date());
			object.setModifiedBy(CommonUtils.getCurrentUserName());
			object.setLabelName(res.getName());
			object.setAppraisalYearId(res.getAppraisalYearId());
			object.setDescription(res.getDescription());
			session.update(object);
			tx.commit();
			responseObject.setInteger(PMSConstants.STATUS_SUCCESS);
			responseObject.setObject(getAllExtraOrdinary());
			responseObject.setString("Successfully saved");
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			responseObject.setInteger(PMSConstants.STATUS_FAILED);
			responseObject.setString("Exception while adding new ExtraOrdinary ... " + e);
			responseObject.setObject(e);
			logger.error("Exception while adding new ExtraOrdinary ... " + e);
		} finally {
			session.flush();
			session.close();
		}
		return responseObject;
	}

	@Override
	public ResponseObject deleteExtraOrdinary(Integer id) {
		ResponseObject responseObject = new ResponseObject();
		Session session = this.sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			ExtraOrdinary object = (ExtraOrdinary) session.get(ExtraOrdinary.class, id);
			object.setStatus(PMSConstants.STATUS_DELETED);
			object.setAppraisalYearId(PMSConstants.STATUS_DELETED);
			object.setLabelName(object.getName());
			object.setDescription(object.getName());
			session.update(object);
			tx.commit();
			responseObject.setInteger(PMSConstants.STATUS_SUCCESS);
			responseObject.setObject(getAllExtraOrdinary());
			responseObject.setString("Successfully deleted");
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			responseObject.setInteger(PMSConstants.STATUS_FAILED);
			responseObject.setString("Exception while deleting ExtraOrdinary ... " + e);
			responseObject.setObject(e);
			logger.error("Exception while deleting ExtraOrdinary ... " + e);
		} finally {
			session.flush();
			session.close();
		}
		return responseObject;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExtraOrdinary> getCurrentExtraOrdinary(Integer appraisalYearId) {
		Session session = this.sessionFactory.openSession();
		List<ExtraOrdinary> list = new ArrayList<ExtraOrdinary>();
		try {
			Criteria criteria = session.createCriteria(ExtraOrdinary.class);
			criteria.add(Restrictions.eq("status", PMSConstants.STATUS_ACTIVE));
			criteria.add(Restrictions.eq("appraisalYearId", appraisalYearId));
			list = criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception while getting current ExtraOrdinary ... " + e);
		} finally {
			session.flush();
			session.close();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExtraOrdinaryDetails> getCurrentEmployeeExtraOrdinaryDetails(Integer appraisalYearId, String empCode) {
		Session session = this.sessionFactory.openSession();
		List<ExtraOrdinaryDetails> list = new ArrayList<ExtraOrdinaryDetails>();
		try {
			Criteria criteria = session.createCriteria(ExtraOrdinaryDetails.class);
			criteria.add(Restrictions.eq("status", PMSConstants.STATUS_ACTIVE));
			criteria.add(Restrictions.eq("empCode", empCode));
			criteria.add(Restrictions.eq("appraisalYearId", appraisalYearId));
			list = criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception while getting current EmployeeExtraOrdinaryDetails ... " + e);
		} finally {
			session.flush();
			session.close();
		}
		return list;
	}

	@Override
	public ResponseObject activateExtraOrdinary(Integer id, Integer appraisalYearId) {
		ResponseObject responseObject = new ResponseObject();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			ExtraOrdinary object = (ExtraOrdinary) session.get(ExtraOrdinary.class, id);
			object.setModifiedOn(new Date());
			object.setAppraisalYearId(appraisalYearId);
			object.setModifiedBy(CommonUtils.getCurrentUserName());
			object.setStatus(PMSConstants.STATUS_ACTIVE);
			session.update(object);
			tx.commit();
			responseObject.setInteger(PMSConstants.STATUS_SUCCESS);
			responseObject.setObject(getAllExtraOrdinary());
			responseObject.setString("Successfully activated");
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			responseObject.setInteger(PMSConstants.STATUS_FAILED);
			responseObject.setString("Exception while activating ExtraOrdinary ... " + e);
			responseObject.setObject(e);
			logger.error("Exception while activating ExtraOrdinary ... " + e);
		} finally {
			session.flush();
			session.close();
		}
		return responseObject;
	}

	@Override
	public List<Integer> getValidatedExtraOrdinaryDetails(String empCode, Integer appraisalYearId, String managerType) {
		Session session = this.sessionFactory.openSession();
		List<Integer> list = new ArrayList<Integer>();
		try {
			String sql = "select id from ExtraOrdinaryDetails where"
					+ " appraisalYearId =:appraisalYearId  ";
			if(managerType.equals(PMSConstants.FIRST_LEVEL_MANAGER))
			{
				sql = sql + " and isValidatedByFirstLevel =:isValidatedByFirstLevel";		
			}
			if(empCode!=PMSConstants.NULL_VALUE)
			{
				sql = sql + " and empCode =:empCode";
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

	@Override
	public List<EmployeeExtraOrdinaryDetails> getEmployeeExtraOrdinaryDetails(String empCode, Integer appraisalYearId) {
		Session session = this.sessionFactory.openSession();
		List<EmployeeExtraOrdinaryDetails> employeeExtraOrdinaryDetails = new ArrayList<EmployeeExtraOrdinaryDetails>();
		try {
			String sql ="select ex.ID as exId, ex.WEIGHTAGE as weightage,"
					+ " ex.FINAL_YEAR_APPRAISAR_RATING as finalYearAppraisarRating "
					+ " from extra_ordinary_details as ex where "
					+ " ex.APPRAISAL_YEAR_ID =:APPRAISAL_YEAR_ID and ex.EMP_CODE =:EMP_CODE ";
			Query query = session.createSQLQuery(sql).addEntity(EmployeeExtraOrdinaryDetails.class);
			query.setParameter("EMP_CODE", empCode);
			query.setParameter("APPRAISAL_YEAR_ID", appraisalYearId);
			employeeExtraOrdinaryDetails = query.list();	
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception while getting employeeExtraOrdinaryDetails List" + e);
		} finally {
			session.flush();
			session.close();
		}
		return employeeExtraOrdinaryDetails;
	}

}
