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
import com.sampark.PMS.dto.EmployeeKRADetails;
import com.sampark.PMS.dto.TrainingNeeds;
import com.sampark.PMS.dto.TrainingNeedsDetails;
import com.sampark.PMS.object.RequestObject;
import com.sampark.PMS.object.ResponseObject;
import com.sampark.PMS.util.CommonUtils;

public class TrainingNeedsDAOImpl implements TrainingNeedsDAO {

	private static final Logger logger = LoggerFactory.getLogger(TrainingNeedsDAOImpl.class);
	private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	@Override
	public ResponseObject saveTrainingNeedsDetails(RequestObject object, String empCode,String userType) {
		ResponseObject responseObject = new ResponseObject();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
			List<Integer> trainingNeedsDetailsOldIdList = object.getList();
			try {
				for (TrainingNeedsDetails res : object.getTrainingNeedsDetails()) {
					if(res.getId() == null)
					{
						res.setEmpCode(empCode);
						res.setCreatedOn(new Date());
						res.setCreatedBy(empCode);
					//	res.setAppraisalYearId(CommonUtils.getActiveAppraisalYearId());
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
						TrainingNeedsDetails obj = (TrainingNeedsDetails) session.get(TrainingNeedsDetails.class, res.getId());
						obj.setModifiedBy(CommonUtils.getCurrentUserName());
						obj.setModifiedOn(new Date());
						obj.setManHours(res.getManHours());
						obj.setTrainingTopic(res.getTrainingTopic());
						obj.setTrainingReasons(res.getTrainingReasons());
						obj.setRemarks(res.getRemarks());
						obj.setApprovedReject(res.getApprovedReject());
						obj.setApprovedReject(res.getApprovedReject());
						responseObject.setString("Successfully saved");
//						obj.setIsValidatedByEmployee(PMSConstants.STATUS_DELETED);
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
						if(trainingNeedsDetailsOldIdList.size() > 0)
						{
						trainingNeedsDetailsOldIdList.remove(new Integer(res.getId()));
						}
					}					
				}
				for(int i=0;i<trainingNeedsDetailsOldIdList.size();i++)
				{
					TrainingNeedsDetails obj = (TrainingNeedsDetails) session.get(TrainingNeedsDetails.class, trainingNeedsDetailsOldIdList.get(i));
					session.delete(obj);
				}
				tx.commit();
				responseObject.setObject(getCurrentEmployeeTrainingNeedsDetails(empCode,object.getAppraisalYearId()));
				responseObject.setInteger(PMSConstants.STATUS_SUCCESS);
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			logger.error("Exception while adding new TrainingNeedsDetails ... " + e);
			responseObject.setInteger(PMSConstants.STATUS_FAILED);
			responseObject.setString("Exception while adding new TrainingNeedsDetails ... " + e);
			responseObject.setObject(e);
		}finally {
			session.flush();
			session.close();
		}
		return responseObject;
		
		
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<TrainingNeeds> getTrainingNeedsList() {
//		Session session = this.sessionFactory.openSession();
//		List<TrainingNeeds> list = new ArrayList<TrainingNeeds>();
//		try {
//			Criteria criteria = session.createCriteria(TrainingNeeds.class);
//			list = criteria.list();
//		} catch (HibernateException e) {
//			e.printStackTrace();
//			logger.error("Exception while getting TrainingNeeds List ... " + e);
//		} finally {
//			session.flush();
//			session.close();
//		}
//		return list;
//	}

//	@Override
//	public ResponseObject saveTrainingNeeds(RequestObject res) {
//		ResponseObject responseObject = new ResponseObject();
//		Session session = sessionFactory.openSession();
//		Transaction tx = session.beginTransaction();
//		try {
//			TrainingNeeds object  = (TrainingNeeds) session.get(TrainingNeeds.class, res.getId());
//			    object.setCreatedOn(new Date());
//			    object.setModifiedBy(CommonUtils.getCurrentUserName());
//			    object.setLabelName(res.getName());
////			    object.setStatus(PMSConstants.STATUS_ACTIVE);
//			    object.setDescription(res.getDescription());
//				session.update(object);
//			tx.commit();
//			responseObject.setInteger(PMSConstants.STATUS_SUCCESS);
//			responseObject.setObject(getTrainingNeedsList());
//			responseObject.setString("Successfully saved");
//		} catch (HibernateException e) {
//			if (tx != null)
//				tx.rollback();
//			e.printStackTrace();
//			responseObject.setInteger(PMSConstants.STATUS_FAILED);
//			responseObject.setString("Exception while adding new TrainingNeeds ... " + e);
//			responseObject.setObject(e);
//			logger.error("Exception while adding new TrainingNeeds ... " + e);
//		}finally {
//			session.flush();
//			session.close();
//		}
//		return responseObject;
//	}

//	@Override
//	public ResponseObject deleteTrainingNeeds(Integer id) {
//		ResponseObject responseObject = new ResponseObject();
//		Session session = this.sessionFactory.openSession();
//		Transaction tx = session.beginTransaction();
//		try {
//			TrainingNeeds object = (TrainingNeeds) session.get(TrainingNeeds.class, id);
//			object.setStatus(PMSConstants.STATUS_DELETED);
//			object.setAppraisalYearId(PMSConstants.STATUS_DELETED);
//			object.setLabelName(object.getName());
//			object.setDescription(object.getName());
//			session.update(object);
//			tx.commit();
//			responseObject.setInteger(PMSConstants.STATUS_SUCCESS);
//			responseObject.setObject(getTrainingNeedsList());
//			responseObject.setString("Successfully deleted");
//		} catch (HibernateException e) {
//			if (tx != null)
//				tx.rollback();
//			e.printStackTrace();
//			responseObject.setInteger(PMSConstants.STATUS_FAILED);
//			responseObject.setString("Exception while deleting TrainingNeeds ... " + e);
//			responseObject.setObject(e);
//			logger.error("Exception while deleting TrainingNeeds ... " + e);
//		} finally {
//			session.flush();
//			session.close();
//		}
//		return responseObject;
//	}

	@Override
	public List<TrainingNeedsDetails> getCurrentEmployeeTrainingNeedsDetails(String empCode, Integer appraisalYearId) {
		Session session = this.sessionFactory.openSession();
		List<TrainingNeedsDetails> list = new ArrayList<TrainingNeedsDetails>();
		try {
			String sql ="select tn.id as id,tn.empCode as empCode,tn.appraisalYearId as appraisalYearId,tn.trainingTopic as trainingTopic,"
					+ " tn.trainingReasons as trainingReasons,tn.isValidatedByEmployee as isValidatedByEmployee,tn.isValidatedByFirstLevel as isValidatedByFirstLevel,"
					+ " tn.isValidatedBySecondLevel as isValidatedBySecondLevel,"
					+ " tn.manHours as manHours,tn.approvedReject as approvedReject,tn.remarks as remarks,"
					+ " dp.name as departmentName,ds.name as designationName,"
					+ " emp.empName as empName from TrainingNeedsDetails as tn,Employee as emp,Designation as ds,Department as dp"
					+ " where tn.appraisalYearId =:appraisalYearId and "
					+ " tn.empCode = emp.empCode and dp.id = emp.departmentId and ds.id = emp.designationId";
			if(!empCode.equals("ALL"))
			{
				sql = sql + " and emp.empCode =:empCode";
			}
			Query query = session.createQuery(sql);
			if(!empCode.equals("ALL"))
			{
			query.setParameter("empCode", empCode);
			}
			query.setParameter("appraisalYearId", appraisalYearId);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			list = query.list();	
			
//			Criteria criteria = session.createCriteria(TrainingNeedsDetails.class);
//			criteria.add(Restrictions.eq("status", PMSConstants.STATUS_ACTIVE));
			
//			criteria.add(Restrictions.eq("appraisalYearId", appraisalYearId));
//			list = criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception while getting current TrainingNeedsDetails ... " + e);
		} finally {
			session.flush();
			session.close();
		}
		return list;
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<TrainingNeeds> getCurrentTrainingNeeds(Integer appraisalYearId) {
//		Session session = this.sessionFactory.openSession();
//		List<TrainingNeeds> list = new ArrayList<TrainingNeeds>();
//		try {
//			Criteria criteria = session.createCriteria(TrainingNeeds.class);
//			criteria.add(Restrictions.eq("status", PMSConstants.STATUS_ACTIVE));
//			criteria.add(Restrictions.eq("appraisalYearId", appraisalYearId));
//			list = criteria.list();
//		} catch (HibernateException e) {
//			e.printStackTrace();
//			logger.error("Exception while getting current TrainingNeeds ... " + e);
//		} finally {
//			session.close();
//		}
//		return list;
//	}

//	@Override
//	public ResponseObject activateTrainingNeeds(Integer id, Integer appraisalYearId) {
//		ResponseObject responseObject = new ResponseObject();
//		Session session = sessionFactory.openSession();
//		Transaction tx = session.beginTransaction();
//		try {
//			TrainingNeeds object  = (TrainingNeeds) session.get(TrainingNeeds.class,id);
//			    object.setModifiedOn(new Date());
//			    object.setAppraisalYearId(appraisalYearId);
//			    object.setModifiedBy(CommonUtils.getCurrentUserName());
//			    object.setStatus(PMSConstants.STATUS_ACTIVE);
//				session.update(object);
//			tx.commit();
//			responseObject.setInteger(PMSConstants.STATUS_SUCCESS);
//			responseObject.setObject(getTrainingNeedsList());
//			responseObject.setString("Successfully saved");
//		} catch (HibernateException e) {
//			if (tx != null)
//				tx.rollback();
//			e.printStackTrace();
//			responseObject.setInteger(PMSConstants.STATUS_FAILED);
//			responseObject.setString("Exception while activating new ExtraOrdinary ... " + e);
//			responseObject.setObject(e);
//			logger.error("Exception while activating new ExtraOrdinary ... " + e);
//		}finally {
//			session.flush();
//			session.close();
//		}
//		return responseObject;
//	}

	@Override
	public List<Integer> getValidatedTrainingNeedsDetails(String empCode, Integer appraisalYearId,
			String managerType) {
		Session session = this.sessionFactory.openSession();
		List<Integer> list = new ArrayList<Integer>();
		try {
			String sql = "select id from TrainingNeedsDetails where"
					+ " appraisalYearId =:appraisalYearId ";
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
}
