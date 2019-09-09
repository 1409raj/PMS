package com.sampark.PMS.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sampark.PMS.PMSConstants;
import com.sampark.PMS.dto.AppraisalYear;
import com.sampark.PMS.dto.AppraisalYearDetails;
import com.sampark.PMS.dto.BehaviouralCompetenceDetails;
import com.sampark.PMS.dto.CareerAspirationsDetails;
import com.sampark.PMS.dto.Department;
import com.sampark.PMS.dto.DepartmentFullAppraisalDetails;
import com.sampark.PMS.dto.DepartmentInPlanningAppraisalDetails;
import com.sampark.PMS.dto.DepartmentInProcessAppraisalDetails;
import com.sampark.PMS.dto.DepartmentInReviewAppraisalDetails;
import com.sampark.PMS.dto.DepartmentName;
import com.sampark.PMS.dto.Designation;
import com.sampark.PMS.dto.Employee;
import com.sampark.PMS.dto.EmployeeAppraisalDetails;
import com.sampark.PMS.dto.EmployeeBehaviouralCompetenceDetails;
import com.sampark.PMS.dto.EmployeeCodeEmail;
import com.sampark.PMS.dto.EmployeeEmails;
import com.sampark.PMS.dto.EmployeeExtraOrdinaryDetails;
import com.sampark.PMS.dto.EmployeeKRAData;
import com.sampark.PMS.dto.EmployeeKRADetails;
import com.sampark.PMS.dto.EmployeeOverAllCalculationDetails;
import com.sampark.PMS.dto.ExtraOrdinaryDetails;
import com.sampark.PMS.dto.KraDetails;
import com.sampark.PMS.dto.KraNew;
import com.sampark.PMS.dto.OverAllCalculation;
import com.sampark.PMS.dto.TermClass;
import com.sampark.PMS.dto.TrainingNeedsDetails;
import com.sampark.PMS.object.RequestObject;
import com.sampark.PMS.object.ResponseObject;
import com.sampark.PMS.util.CommonUtils;
import com.sampark.PMS.util.DbUtils;

public class AppraisalYearDetailsDAOImpl implements AppraisalYearDetailsDAO {

	private static final Logger logger = LoggerFactory.getLogger(AppraisalYearDetailsDAOImpl.class);
	private static ResourceBundle messages = ResourceBundle.getBundle("messages");
	private SessionFactory sessionFactory;
	private double calculationAverage = 0.0;
//	private Integer kraSectionBCalculationAverage = 0;
//	private Integer kraSectionCCalculationAverage = 0;
//	private Integer kraSectionDCalculationAverage = 0;
	private Integer sectionWeightage = 0;
//	private Integer sectionBWeightage = 0;
//	private Integer sectionCWeightage = 0;
//	private Integer sectionDWeightage = 0;
//	private Integer employeeExtraOrdinaryWeightage = 0;
//	private Integer employeeExtraOrdinaryTotalWeightageRating = 0;
//	private Integer BCWeightage = 0;
//	private Integer BCCalculationAverage = 0;
//	private Integer employeeOverAllKRARatingDataTotalWeightage = 0;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResponseObject getAppraisalYearDetails() {
		Session session = this.sessionFactory.openSession();
		ResponseObject res = new ResponseObject();
		List liData = new ArrayList();
		List<AppraisalYear> list = new ArrayList<AppraisalYear>();
		try {
			String sql = "from AppraisalYear";
			Query query = session.createQuery(sql);
			list = query.list();
			liData.add(list);
			res = getCurrentYear();
			if (res.getInteger() == 1) {
				liData.add(res.getObject());
				res.setObject(liData);
				res.setString("Success");
				res.setInteger(PMSConstants.STATUS_SUCCESS);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			res.setInteger(PMSConstants.STATUS_FAILED);
			res.setString("Error while getting Appraisal Year.");
		} finally {
			session.flush();
			session.close();
		}
		return res;
	}

	private ResponseObject getCurrentYear() {
		Session session = this.sessionFactory.openSession();
		ResponseObject res = new ResponseObject();
		try {
			String sql = "from AppraisalYear where status =:status";
			Query query = session.createQuery(sql);
			query.setParameter("status", PMSConstants.STATUS_ACTIVE);
			res.setObject(query.uniqueResult());
			res.setString("Success");
			res.setInteger(PMSConstants.STATUS_SUCCESS);
		} catch (HibernateException e) {
			e.printStackTrace();
			res.setInteger(PMSConstants.STATUS_FAILED);
			res.setObject(e);
			res.setString("Error while getting Appraisal Year.");
		} finally {
			session.flush();
			session.close();
		}
		return res;
	}

	@Override
	public ResponseObject getUserAppraisalDetails(String empCode, Integer appraisalYearId,String type) {
		Session session = this.sessionFactory.openSession();
		ResponseObject res = new ResponseObject();
		TermClass term = getAllTerms(empCode, appraisalYearId);
		try {
			String sql = "select ay.id as id,"
					+ " ay.firstManagerGoalApproval as firstManagerGoalApproval,ay.secondLevelIsvisibleCheck as secondLevelIsvisibleCheck, "
					+ " ay.firstManagerMidYearApproval as firstManagerMidYearApproval, ay.firstManagerYearEndAssessmentApproval as firstManagerYearEndAssessmentApproval, "
					+ " ay.employeeApproval as employeeApproval, ay.employeeMidYearApproval as employeeMidYearApproval, ay.initializationYear as initializationYear ,ay.midYear as midYear,ay.kraForwardCheck as kraForwardCheck, "
					+ " (select emp1.empName from Employee as emp1 where emp1.empCode = emp.firstLevelSuperiorEmpId) as firstLevelSuperiorName,"
					+ " ay.employeeIsvisible as employeeIsvisible,ay.firstLevelIsvisible as firstLevelIsvisible, "
					+ " ay.secondLevelIsvisible as secondLevelIsvisible,ay.finalYear as finalYear "
					+ " from AppraisalYearDetails as ay,Employee as emp where ay.empCode =:empCode and emp.empCode = ay.empCode "
					+ " and ay.appraisalYearId =:appraisalYearId ";
//			and ay.status =:status
			if(!type.equals(PMSConstants.USER_EMPLOYEE))
			{
			if (term.getInitializationYear() != null) {
				if (term.getInitializationYear() == 1)
					sql = sql + " and ay.initializationEndDate >=:initializationEndDate";
			}
			if (term.getMidYear() != null) {
				if (term.getMidYear() == 1)
					sql = sql + " and ay.midYearEndDate >=:midYearEndDate";
			}
			if (term.getFinalYear() != null) {
				if (term.getFinalYear() == 1)
					sql = sql + " and ay.finalYearEndDate >=:finalYearEndDate ";
			}
			}
			Query query = session.createQuery(sql);
			query.setParameter("empCode", empCode);
			query.setParameter("appraisalYearId", appraisalYearId);
//			query.setParameter("status", PMSConstants.STATUS_ACTIVE);
			if(!type.equals(PMSConstants.USER_EMPLOYEE))
			{
			if (term.getInitializationYear() != null) {
				if (term.getInitializationYear() == 1)
					query.setParameter("initializationEndDate", new Date());
			}
			if (term.getMidYear() != null) {
				if (term.getMidYear() == 1)
					query.setParameter("midYearEndDate", new Date());
			}
			if (term.getFinalYear() != null) {
				if (term.getFinalYear() == 1)
					query.setParameter("finalYearEndDate", new Date());
			}
			}
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			res.setObject(query.uniqueResult());
			res.setInteger(PMSConstants.STATUS_SUCCESS);
			
		} catch (HibernateException e) {
			e.printStackTrace();
			res.setObject(e);
			res.setInteger(PMSConstants.STATUS_FAILED);
			res.setString("Excepting will getting LoggedUserAppraisalDetails." + e);
			logger.error("Excepting will getting LoggedUserAppraisalDetails. " + e);
		} finally {
			session.flush();
			session.close();
		}
		return res;
	}

	private TermClass getAllTerms(String empCode, Integer appraisalYearId) {
		Session session = this.sessionFactory.openSession();
		TermClass term = new TermClass();
		try {
			String sql = "select id as id, INITIALIZATION_YEAR as initializationYear,MID_YEAR as midYear,FINAL_YEAR as finalYear"
					+ " from appraisal_year_details  where APPRAISAL_YEAR_ID =:APPRAISAL_YEAR_ID and STATUS =:STATUS and EMP_CODE =:EMP_CODE";
			Query query = session.createSQLQuery(sql).addEntity(TermClass.class);
			query.setParameter("APPRAISAL_YEAR_ID", appraisalYearId);
			query.setParameter("EMP_CODE", empCode);
			query.setParameter("STATUS", PMSConstants.STATUS_ACTIVE);
			if (query.list().size() > 0) {
				term = (TermClass) query.list().get(0);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Excepting will getting all term. " + e);
		} finally {
			session.flush();
			session.close();
		}
		return term;
	}



	@SuppressWarnings("rawtypes")
	private Integer approveEmployeeMidYearSubmissionDetails(List list, Integer appraisalYearId) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		ResponseObject res = new ResponseObject();
		Integer count = 0;
		Integer check = 0;
		try {
			for (int i = 0; i < list.size(); i++) {
				String sql = "Update AppraisalYearDetails set midYear =:midYear where appraisalYearId =:appraisalYearId and empCode =:empCode";
				Query query = session.createQuery(sql);
				query.setParameter("appraisalYearId", appraisalYearId);
				query.setParameter("midYear", 1);
				query.setParameter("empCode", list.get(i));
				query.executeUpdate();
				if (i % 50 == 0) {
					// Same as the JDBC batch size
					// flush a batch of inserts and release memory:
					session.flush();
					session.clear();
					count++;
				} else {
					if ((list.size() - (50 * count)) < 50) {
						session.flush();
						session.clear();

					}
				}
				check++;
			}
			tx.commit();
			if (check == list.size()) {
				// res.setObject(getAppraisalCycleDetails(appraisalYearId).getObject());
				res.setInteger(PMSConstants.STATUS_SUCCESS);
				res.setString("Success");
			}
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			res.setInteger(PMSConstants.STATUS_FAILED);
			res.setString("Error while Mid Year Submission");
		} finally {
			session.flush();
			session.close();
		}
		return count;
	}

	// @Override
	// public ResponseObject appraisalYearFinalYearSubmission(Integer
	// appraisalYearId) {
	// Session session = this.sessionFactory.openSession();
	// ResponseObject res = new ResponseObject();
	// List list = new ArrayList();
	// try {
	// String sql = "select empCode from AppraisalYearDetails where
	// appraisalYearId =:appraisalYearId";
	// Query query = session.createQuery(sql);
	// query.setParameter("appraisalYearId", appraisalYearId);
	// list = query.list();
	// if (approveEmployeeFinalYearSubmissionDetails(list, appraisalYearId) ==
	// 1) {
	// // res.setObject(getAppraisalCycleDetails(appraisalYearId).getObject());
	// res.setInteger(PMSConstants.STATUS_SUCCESS);
	// res.setString("Success");
	// }
	// } catch (HibernateException e) {
	// e.printStackTrace();
	// res.setInteger(PMSConstants.STATUS_FAILED);
	// res.setString("Error while Final Year KRA Submission");
	// } finally {
	// session.close();
	// }
	// return res;
	//
	// }

	@SuppressWarnings({ "rawtypes", "unused" })
	private Integer approveEmployeeFinalYearSubmissionDetails(List list, Integer appraisalYearId) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		ResponseObject res = new ResponseObject();
		Integer count = 0;
		Integer check = 0;
		try {
			for (int i = 0; i < list.size(); i++) {
				String sql = "Update AppraisalYearDetails set finalYear =:finalYear where appraisalYearId =:appraisalYearId and empCode =:empCode";
				Query query = session.createQuery(sql);
				query.setParameter("appraisalYearId", appraisalYearId);
				query.setParameter("finalYear", 1);
				query.setParameter("empCode", list.get(i));
				query.executeUpdate();
				if (i % 50 == 0) {
					// Same as the JDBC batch size
					// flush a batch of inserts and release memory:
					session.flush();
					session.clear();
					count++;
				} else {
					if ((list.size() - (50 * count)) < 50) {
						session.flush();
						session.clear();

					}
				}
				check++;
			}
			tx.commit();
			if (check == list.size()) {
				// res.setObject(getAppraisalCycleDetails(appraisalYearId).getObject());
				res.setInteger(PMSConstants.STATUS_SUCCESS);
				res.setString("Success");
			}
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			res.setInteger(PMSConstants.STATUS_FAILED);
			res.setString("Error while Final Year Submission");
		} finally {
			session.flush();
			session.close();
		}
		return count;
	}

	@Override
	public ResponseObject appraisalCycleStart(RequestObject res) {
		ResponseObject responseObject = new ResponseObject();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			if (res.getId() == 0) {
				List<EmployeeCodeEmail> employeeCodeList = DbUtils.getAllEmployeeCode(res.getEligibility(),
						res.getAppraisalYearId());
				for (EmployeeCodeEmail obj : employeeCodeList) {
					AppraisalYearDetails object = new AppraisalYearDetails();
					object.setCreatedOn(new Date());
					object.setCreatedBy(CommonUtils.getCurrentUserName());
					object.setAppraisalYearId(res.getAppraisalYearId());
					object.setEmpCode(obj.getEmpCode());
					object.setEmail(obj.getEmail());
					object.setInitializationYear(PMSConstants.STATUS_ACTIVE);
					object.setMidYear(PMSConstants.STATUS_DELETED);
					object.setFinalYear(PMSConstants.STATUS_DELETED);
					object.setInitializationStartDate(res.getStartDate());
					object.setInitializationEndDate(res.getEndDate());
					object.setEmployeeIsvisible(PMSConstants.STATUS_DELETED);
					object.setFirstLevelIsvisible(PMSConstants.STATUS_DELETED);
					object.setSecondLevelIsvisible(PMSConstants.STATUS_DELETED);
					object.setSecondLevelIsvisibleCheck(PMSConstants.STATUS_DELETED);
					object.setStatus(PMSConstants.STATUS_ACTIVE);
					object.setAcknowledgementCheck(PMSConstants.STATUS_DELETED);
					EmployeeEmails empEmail = new EmployeeEmails();
					// empEmail.setEmpCode(object.getEmpCode());
					empEmail.setSendTo(object.getEmail());
					empEmail.setSendFrom(PMSConstants.SEND_FORM);
					empEmail.setAppraisalYearId(res.getAppraisalYearId());
					empEmail.setEmailType(PMSConstants.EMPLOYEE_APPRAISAL);
					empEmail.setCreatedOn(new Date());
					empEmail.setStatus(PMSConstants.STATUS_PENDING);
					empEmail.setEmployeeName(obj.getEmpName());
					empEmail.setEmailSubject(res.getEmailSubject());
					empEmail.setEmailContent(res.getEmailContent());
					session.persist(empEmail);
					session.persist(object);
				}
				responseObject.setString("Employees appraisal started.");
				responseObject.setObject(employeeCodeList);
				responseObject.setInteger(PMSConstants.STATUS_ACTIVE);
			} else if (res.getId() == 1) {
				List<EmployeeCodeEmail> employeeDetailsList = DbUtils
						.getAllEmployeeAppraisalDetailsList(res.getAppraisalYearId(), res.getEligibility());
				List<Integer> employeeIDList = DbUtils.getAllEmployeeListId(res.getAppraisalYearId(),res.getEligibility());
				for (Integer ID : employeeIDList) {
					AppraisalYearDetails object = (AppraisalYearDetails) session.get(AppraisalYearDetails.class, ID);
					object.setInitializationYear(PMSConstants.STATUS_DELETED);
					object.setMidYear(PMSConstants.STATUS_ACTIVE);
					object.setFinalYear(PMSConstants.STATUS_DELETED);
					object.setMidYearStartDate(res.getStartDate());
					object.setMidYearEndDate(res.getEndDate());
					object.setModifiedOn(new Date());
					object.setModifiedBy(CommonUtils.getCurrentUserName());
					object.setEmployeeIsvisible(PMSConstants.STATUS_ACTIVE);
					object.setFirstLevelIsvisible(PMSConstants.STATUS_DELETED);
					object.setSecondLevelIsvisible(PMSConstants.STATUS_DELETED);
					object.setSecondLevelIsvisibleCheck(PMSConstants.STATUS_DELETED);

					EmployeeEmails empEmail = new EmployeeEmails();
					// empEmail.setEmpCode(object.getEmpCode());
					empEmail.setSendTo(object.getEmail());
					empEmail.setSendFrom(PMSConstants.SEND_FORM);
					empEmail.setAppraisalYearId(res.getAppraisalYearId());
					empEmail.setEmailType(PMSConstants.EMPLOYEE_APPRAISAL);
					empEmail.setCreatedOn(new Date());
					empEmail.setStatus(PMSConstants.STATUS_PENDING);
					empEmail.setEmailSubject(res.getEmailSubject());
					empEmail.setEmailContent(res.getEmailContent());
					session.persist(empEmail);
					session.update(object);
				}
				List<EmployeeKRAData> kralist = DbUtils.getValidatedKRADetails(PMSConstants.NULL_VALUE,
						res.getAppraisalYearId(), PMSConstants.APPRAISAL_MID_YEAR);
				for (int i = 0; i < kralist.size(); i++) {
					KraDetails kra = (KraDetails) session.get(KraDetails.class, kralist.get(i).getId());
					kra.setIsValidatedByFirstLevel(PMSConstants.STATUS_DELETED);
					kra.setIsValidatedByEmployee(PMSConstants.STATUS_DELETED);
					kra.setIsValidatedBySecondLevel(PMSConstants.STATUS_DELETED);
					session.update(kra);
				}
				// List<Integer> trainingNeedsDetailsIdList =
				// DbUtils.getValidatedTrainingNeedsDetails(PMSConstants.NULL_VALUE,
				// res.getAppraisalYearId() ,PMSConstants.APPRAISAL_MID_YEAR);
				// for(int i= 0;i<trainingNeedsDetailsIdList.size();i++)
				// {
				// TrainingNeedsDetails tnd = (TrainingNeedsDetails)
				// session.get(TrainingNeedsDetails.class,
				// trainingNeedsDetailsIdList.get(i));
				// tnd.setIsValidatedByEmployee(PMSConstants.STATUS_DELETED);
				// tnd.setIsValidatedByFirstLevel(PMSConstants.STATUS_DELETED);
				// tnd.setIsValidatedBySecondLevel(PMSConstants.STATUS_DELETED);
				// session.update(tnd);
				// }
				responseObject.setObject(employeeDetailsList);
				responseObject.setInteger(PMSConstants.STATUS_ACTIVE);
				responseObject.setString("Employees Mid Year appraisal started.");
			} else {
				List<EmployeeCodeEmail> employeeDetailsList = DbUtils
						.getAllEmployeeAppraisalDetailsList(res.getAppraisalYearId(), res.getEligibility());
				List<Integer> employeeIDList = DbUtils.getAllEmployeeListId(res.getAppraisalYearId(),res.getEligibility());
				for (Integer ID : employeeIDList) {
					AppraisalYearDetails object = new AppraisalYearDetails();
					object = (AppraisalYearDetails) session.get(AppraisalYearDetails.class, ID);
					object.setInitializationYear(PMSConstants.STATUS_DELETED);
					object.setMidYear(PMSConstants.STATUS_DELETED);
					object.setFinalYear(PMSConstants.STATUS_ACTIVE);
					object.setFinalYearStartDate(res.getStartDate());
					object.setFinalYearEndDate(res.getEndDate());
					object.setModifiedOn(new Date());
					object.setModifiedBy(CommonUtils.getCurrentUserName());
					object.setEmployeeIsvisible(PMSConstants.STATUS_ACTIVE);
					object.setFirstLevelIsvisible(PMSConstants.STATUS_DELETED);
					object.setSecondLevelIsvisible(PMSConstants.STATUS_DELETED);
					object.setSecondLevelIsvisibleCheck(PMSConstants.STATUS_DELETED);

					EmployeeEmails empEmail = new EmployeeEmails();
					// empEmail.setEmpCode(object.getEmpCode());
					empEmail.setSendTo(object.getEmail());
					empEmail.setSendFrom(PMSConstants.SEND_FORM);
					empEmail.setAppraisalYearId(res.getAppraisalYearId());
					empEmail.setEmailType(PMSConstants.EMPLOYEE_APPRAISAL);
					empEmail.setCreatedOn(new Date());
					empEmail.setStatus(PMSConstants.STATUS_PENDING);
					empEmail.setEmailSubject(res.getEmailSubject());
					empEmail.setEmailContent(res.getEmailContent());
					session.persist(empEmail);
					session.update(object);
				}
				List<EmployeeKRAData> kralist = DbUtils.getValidatedKRADetails(PMSConstants.NULL_VALUE,
						res.getAppraisalYearId(), PMSConstants.APPRAISAL_FINAL_YEAR);
				List<Integer> behaviouralCompetenceDetailsIdList = DbUtils.getValidatedBehaviouralCompetenceDetails(
						PMSConstants.NULL_VALUE, res.getAppraisalYearId(), PMSConstants.APPRAISAL_FINAL_YEAR);
				for (int i = 0; i < behaviouralCompetenceDetailsIdList.size(); i++) {
					BehaviouralCompetenceDetails bcd = (BehaviouralCompetenceDetails) session
							.get(BehaviouralCompetenceDetails.class, behaviouralCompetenceDetailsIdList.get(i));
					bcd.setIsValidatedByEmployee(PMSConstants.STATUS_DELETED);
					bcd.setIsValidatedByFirstLevel(PMSConstants.STATUS_DELETED);
					bcd.setIsValidatedBySecondLevel(PMSConstants.STATUS_DELETED);
					session.update(bcd);
				}
				if(kralist.size() > 0)
				{
					if(kralist.get(0).getKraType() == null)	
					{
				for (int i = 0; i < kralist.size(); i++) {
					KraDetails kra = (KraDetails) session.get(KraDetails.class, kralist.get(i).getId());
					kra.setIsValidatedByEmployee(PMSConstants.STATUS_DELETED);
					kra.setIsValidatedByFirstLevel(PMSConstants.STATUS_DELETED);
					kra.setIsValidatedBySecondLevel(PMSConstants.STATUS_DELETED);
					session.update(kra);
				}
					}
				if(kralist.get(0).getKraType() !=null)	
				{
				for (int i = 0; i < kralist.size(); i++) {
					KraNew kranew = (KraNew) session.get(KraNew.class, kralist.get(i).getId());
					kranew.setIsValidatedByEmployee(PMSConstants.STATUS_DELETED);
					kranew.setIsValidatedByFirstLevel(PMSConstants.STATUS_DELETED);
					kranew.setIsValidatedBySecondLevel(PMSConstants.STATUS_DELETED);
					session.update(kranew);
				}	
				}
				}
				responseObject.setObject(employeeDetailsList);
				responseObject.setInteger(PMSConstants.STATUS_SUCCESS);
				responseObject.setString("Employees Final Year appraisal started.");
			}
			tx.commit();
//			responseObject.setInteger(PMSConstants.STATUS_SUCCESS);
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			logger.error("Exception while adding new Designation ... " + e);
			responseObject.setInteger(PMSConstants.STATUS_FAILED);
			responseObject.setString("Exception while adding new Designation ... " + e);
			responseObject.setObject(e);
		} finally {
			session.flush();
			session.close();
		}
		return responseObject;
	}

	@Override
	public ResponseObject getEmployeeAppraisalList(Integer appraisalYearId) {
		Session session = this.sessionFactory.openSession();
		ResponseObject res = new ResponseObject();
		try {
			String sql = "select ayd.id as appraisalYearDetailsId,ayd.status as appraisalYearDetailsStatus, "
					+ " (select emp1.empName from Employee as emp1 where emp1.empCode = emp.firstLevelSuperiorEmpId) as firstLevelSuperiorName, "
					+ "  (select emp2.empName from Employee as emp2 where emp2.empCode = emp.secondLevelSuperiorEmpId) as secondLevelSuperiorName, "
					+ " emp.id as id, emp.empName as empName,emp.empCode as empCode,emp.status as status,"
					+ " dep.name as departmentName, des.name as designationName"
					+ " from Employee as emp,Department as dep,Designation as des, AppraisalYearDetails as ayd "
					+ " where emp.departmentId = dep.id"
					+ " and emp.designationId = des.id  and ayd.appraisalYearId =:appraisalYearId and ayd.empCode = emp.empCode";
			Query query = session.createQuery(sql);
			// and emp.status =:status
			// query.setParameter("status", PMSConstants.STATUS_ACTIVE);
			query.setParameter("appraisalYearId", appraisalYearId);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			res.setObject(query.list());
			res.setInteger(PMSConstants.STATUS_SUCCESS);
			res.setString("Success");
		} catch (HibernateException e) {
			e.printStackTrace();
			res.setObject(e);
			res.setInteger(PMSConstants.STATUS_FAILED);
			res.setString("Exception while getting All Employee Appraisal List " + e);
			logger.error("Exception while getting All Employee Appraisal List" + e);
		} finally {
			session.flush();
			session.close();
		}
		return res;
	}

	@Override
	public ResponseObject updateEmployeeAppraisal(Integer status, Integer appraisalYearDetailsId,
			Integer appraisalYearId) {
		ResponseObject responseObject = new ResponseObject();
		Session session = this.sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			AppraisalYearDetails object = (AppraisalYearDetails) session.get(AppraisalYearDetails.class,
					appraisalYearDetailsId);
			if (status >= 1) {
				object.setStatus(PMSConstants.STATUS_DELETED);
			} else {
				object.setStatus(PMSConstants.STATUS_ACTIVE);
			}
			session.update(object);
			tx.commit();
			responseObject.setInteger(PMSConstants.STATUS_SUCCESS);
			responseObject.setObject(getEmployeeAppraisalList(appraisalYearId).getObject());
			responseObject.setString("Successfully saved");
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			responseObject.setInteger(PMSConstants.STATUS_FAILED);
			responseObject.setString("Exception while activating and deactivating AppraisalYearDetails ... " + e);
			responseObject.setObject(e);
			logger.error("Exception while activating and deactivating AppraisalYearDetails ... " + e);
		} finally {
			session.flush();
			session.close();
		}
		return responseObject;
	}

	@Override
	public ResponseObject sendToManager(String userType, String buttonType, String empCode,Integer activeAppraisalYearID) {
		ResponseObject responseObject = new ResponseObject();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		boolean checkStatus = false;
		try {
			Employee employee = CommonUtils.getCurrentEmployeeData(empCode);
			Integer appraisalYearId = CommonUtils
					.getEmployeeAppraisalYearDetails(activeAppraisalYearID, empCode);
			AppraisalYearDetails object = (AppraisalYearDetails) session.get(AppraisalYearDetails.class,
					appraisalYearId);
			if (userType.equals(PMSConstants.FIRST_LEVEL_EMPLOYEE)) {
				object.setEmployeeIsvisible(PMSConstants.STATUS_DELETED);
				object.setFirstLevelIsvisible(PMSConstants.STATUS_ACTIVE);
				if (object.getInitializationYear() == 1) {
					object.setFirstManagerGoalApproval(PMSConstants.STATUS_DELETED);
					EmployeeEmails empEmail = new EmployeeEmails();
					// empEmail.setEmpCode(object.getEmpCode());
					empEmail.setSendTo(employee.getEmail());
					empEmail.setSendFrom(PMSConstants.SEND_FORM);
					empEmail.setAppraisalYearId(object.getAppraisalYearId());
					// empEmail.setEmailType(PMSConstants.EMPLOYEE_APPRAISAL_APPROVAL);
					empEmail.setCreatedOn(new Date());
					empEmail.setEmployeeName(employee.getEmpName());
					empEmail.setStatus(PMSConstants.STATUS_PENDING);
					empEmail.setEmailSubject(messages.getString("email.goal.setting.initialization.subject"));
					empEmail.setEmailContent("Dear " + employee.getEmpName() + "<br><br>"
							+ "You have submitted goals to manager for his review and feedback." + "<br><br>"
							+ "URL <a href='https://pms-hfe.com/PMS/login'>Click here to login PMS tool</a>"
							+ "<br><br>" + "Best Regards" + "<br>" + "Team HR");
					session.persist(empEmail);

					Employee firstLevelManagerDetails = CommonUtils
							.getCurrentEmployeeData(employee.getFirstLevelSuperiorEmpId());

					EmployeeEmails managerEmail = new EmployeeEmails();
					// managerEmail.setEmpCode(object.getEmpCode());
					managerEmail.setSendTo(firstLevelManagerDetails.getEmail());
					managerEmail.setSendFrom(PMSConstants.SEND_FORM);
					managerEmail.setAppraisalYearId(object.getAppraisalYearId());
					// managerEmail.setEmailType(PMSConstants.MANAGER_APPRAISAL_APPROVAL);
					managerEmail.setCreatedOn(new Date());
					managerEmail.setEmployeeName(employee.getEmpName());
					managerEmail.setStatus(PMSConstants.STATUS_PENDING);
					managerEmail.setEmailSubject(messages.getString("email.goal.setting.initialization.subject"));
					managerEmail
							.setEmailContent("Dear Manager" + "<br><br>" + "Your team member " + employee.getEmpName()
									+ " has submitted goals, please log into the PMS system to complete this action."
									+ "<br><br>"
									+ "URL <a href='https://pms-hfe.com/PMS/login'>Click here to login PMS tool</a>"
									+ "<br><br>" + "Best Regards" + "<br>" + "Team HR");
					session.persist(managerEmail);

				}
				if (object.getMidYear() == 1) {
					object.setFirstManagerMidYearApproval(PMSConstants.STATUS_DELETED);

					EmployeeEmails empEmail = new EmployeeEmails();
					// empEmail.setEmpCode(object.getEmpCode());
					empEmail.setSendTo(employee.getEmail());
					empEmail.setSendFrom(PMSConstants.SEND_FORM);
					empEmail.setAppraisalYearId(object.getAppraisalYearId());
					// empEmail.setEmailType(PMSConstants.EMPLOYEE_APPRAISAL_APPROVAL);
					empEmail.setCreatedOn(new Date());
					empEmail.setEmployeeName(employee.getEmpName());
					empEmail.setStatus(PMSConstants.STATUS_PENDING);
					empEmail.setEmailSubject(messages.getString("email.mid.year.review.subject"));
					empEmail.setEmailContent("Dear " + employee.getEmpName() + "<br><br>"
							+ "You have submitted mid term assessment to manager for his review.." + "<br><br>"
							+ "URL <a href='https://pms-hfe.com/PMS/login'>Click here to login PMS tool</a>"
							+ "<br><br>" + "Best Regards" + "<br>" + "Team HR");
					session.persist(empEmail);

					Employee firstLevelManagerDetails = CommonUtils
							.getCurrentEmployeeData(employee.getFirstLevelSuperiorEmpId());

					EmployeeEmails managerEmail = new EmployeeEmails();
					// managerEmail.setEmpCode(object.getEmpCode());
					managerEmail.setSendTo(firstLevelManagerDetails.getEmail());
					managerEmail.setSendFrom(PMSConstants.SEND_FORM);
					managerEmail.setAppraisalYearId(object.getAppraisalYearId());
					// managerEmail.setEmailType(PMSConstants.MANAGER_APPRAISAL_APPROVAL);
					managerEmail.setCreatedOn(new Date());
					managerEmail.setEmployeeName(employee.getEmpName());
					managerEmail.setStatus(PMSConstants.STATUS_PENDING);
					managerEmail.setEmailSubject(messages.getString("email.mid.year.review.subject"));
					managerEmail
							.setEmailContent("Dear Manager" + "<br><br>" + "Your team member " + employee.getEmpName()
									+ " has submitted mid-term assessment, please log into the PMS system to complete this action."
									+ "<br><br>"
									+ "URL <a href='https://pms-hfe.com/PMS/login'>Click here to login PMS tool</a>"
									+ "<br><br>" + "Best Regards" + "<br>" + "Team HR");
					session.persist(managerEmail);

				}
				if (object.getFinalYear() == 1) {
					object.setFirstManagerYearEndAssessmentApproval(PMSConstants.STATUS_DELETED);
					EmployeeEmails empEmail = new EmployeeEmails();
					// empEmail.setEmpCode(object.getEmpCode());
					empEmail.setSendTo(employee.getEmail());
					empEmail.setSendFrom(PMSConstants.SEND_FORM);
					empEmail.setAppraisalYearId(object.getAppraisalYearId());
					// empEmail.setEmailType(PMSConstants.EMPLOYEE_APPRAISAL_APPROVAL);
					empEmail.setCreatedOn(new Date());
					empEmail.setEmployeeName(employee.getEmpName());
					empEmail.setStatus(PMSConstants.STATUS_PENDING);
					empEmail.setEmailSubject(messages.getString("email.final.year.review.subject"));
					empEmail.setEmailContent("Dear " + employee.getEmpName() + "<br><br>"
							+ "You have submitted year-end assessment to manager for his review." + "<br><br>"
							+ "URL <a href='https://pms-hfe.com/PMS/login'>Click here to login PMS tool</a>"
							+ "<br><br>" + "Best Regards" + "<br>" + "Team HR");
					session.persist(empEmail);

					Employee firstLevelManagerDetails = CommonUtils
							.getCurrentEmployeeData(employee.getFirstLevelSuperiorEmpId());

					EmployeeEmails managerEmail = new EmployeeEmails();
					// managerEmail.setEmpCode(object.getEmpCode());
					managerEmail.setSendTo(firstLevelManagerDetails.getEmail());
					managerEmail.setSendFrom(PMSConstants.SEND_FORM);
					managerEmail.setAppraisalYearId(object.getAppraisalYearId());
					managerEmail.setEmailType(PMSConstants.MANAGER_APPRAISAL_APPROVAL);
					managerEmail.setCreatedOn(new Date());
					managerEmail.setEmployeeName(employee.getEmpName());
					managerEmail.setStatus(PMSConstants.STATUS_PENDING);
					managerEmail.setEmailSubject(messages.getString("email.final.year.review.subject"));
					managerEmail
							.setEmailContent("Dear Manager" + "<br><br>" + "Your team member " + employee.getEmpName()
									+ " has submitted year end assessment, please log into the PMS system to complete this action."
									+ "<br><br>"
									+ "URL <a href='https://pms-hfe.com/PMS/login'>Click here to login PMS tool</a>"
									+ "<br><br>" + "Best Regards" + "<br>" + "Team HR");
					session.persist(managerEmail);
				}
				responseObject.setString("Successfully send to manager");
			}
			if (userType.equals(PMSConstants.FIRST_LEVEL_MANAGER) && buttonType.equals(PMSConstants.BUTTON_APPROVED)) {
				object.setEmployeeIsvisible(PMSConstants.STATUS_DELETED);
				object.setFirstLevelIsvisible(PMSConstants.STATUS_DELETED);
				object.setSecondLevelIsvisible(PMSConstants.STATUS_ACTIVE);

				Employee firstLevelManagerDetails = CommonUtils
						.getCurrentEmployeeData(employee.getFirstLevelSuperiorEmpId());
				if (object.getInitializationYear() == 1) {
					object.setFirstManagerGoalApproval(PMSConstants.STATUS_ACTIVE);

					EmployeeEmails empEmail = new EmployeeEmails();
					// empEmail.setEmpCode(object.getEmpCode());
					empEmail.setSendTo(firstLevelManagerDetails.getEmail());
					empEmail.setSendFrom(PMSConstants.SEND_FORM);
					empEmail.setAppraisalYearId(object.getAppraisalYearId());
					// empEmail.setEmailType(PMSConstants.FIRST_LEVEL_MANAGER_APPRAISAL_APPROVAL);
					empEmail.setCreatedOn(new Date());
					empEmail.setEmployeeName(employee.getEmpName());
					empEmail.setStatus(PMSConstants.STATUS_PENDING);
					empEmail.setEmailSubject(messages.getString("email.goal.setting.initialization.subject"));
					empEmail.setEmailContent("Dear Manager" + "<br><br>" + "You have approved " + employee.getEmpName()
							+ " goals for Performance Appraisal." + "<br><br>"
							+ "URL <a href='https://pms-hfe.com/PMS/login'>Click here to login PMS tool</a>"
							+ "<br><br>" + "Best Regards" + "<br>" + "Team HR");
					session.persist(empEmail);

					Employee secondLevelManagerDetails = CommonUtils
							.getCurrentEmployeeData(employee.getSecondLevelSuperiorEmpId());

					EmployeeEmails managerEmail = new EmployeeEmails();
					// managerEmail.setEmpCode(object.getEmpCode());
					managerEmail.setSendTo(secondLevelManagerDetails.getEmail());
					managerEmail.setSendFrom(PMSConstants.SEND_FORM);
					managerEmail.setAppraisalYearId(object.getAppraisalYearId());
					// managerEmail.setEmailType(PMSConstants.SECOND_LEVEL_MANAGER_APPRAISAL_APPROVAL);
					managerEmail.setCreatedOn(new Date());
					managerEmail.setEmployeeName(employee.getEmpName());
					managerEmail.setStatus(PMSConstants.STATUS_PENDING);
					managerEmail.setEmailSubject(messages.getString("email.goal.setting.initialization.subject"));
					managerEmail
							.setEmailContent("Dear Manager" + "<br><br>" + "Your team member " + employee.getEmpName()
									+ " goals are approved by manager, please log into the PMS system to complete this action."
									+ "<br><br>"
									+ "URL <a href='https://pms-hfe.com/PMS/login'>Click here to login PMS tool</a>"
									+ "<br><br>" + "Best Regards" + "<br>" + "Team HR");
					session.persist(managerEmail);
				}
				if (object.getMidYear() == 1) {
					object.setFirstManagerMidYearApproval(PMSConstants.STATUS_ACTIVE);

					EmployeeEmails empEmail = new EmployeeEmails();
					// empEmail.setEmpCode(object.getEmpCode());
					empEmail.setSendTo(firstLevelManagerDetails.getEmail());
					empEmail.setSendFrom(PMSConstants.SEND_FORM);
					empEmail.setAppraisalYearId(object.getAppraisalYearId());
					// empEmail.setEmailType(PMSConstants.FIRST_LEVEL_MANAGER_APPRAISAL_APPROVAL);
					empEmail.setCreatedOn(new Date());
					empEmail.setEmployeeName(employee.getEmpName());
					empEmail.setStatus(PMSConstants.STATUS_PENDING);
					empEmail.setEmailSubject(messages.getString("email.mid.year.review.subject"));
					empEmail.setEmailContent("Dear Manager" + "<br><br>" + "You have approved " + employee.getEmpName()
							+ " mid-year assessment." + "<br><br>"
							+ "URL <a href='https://pms-hfe.com/PMS/login'>Click here to login PMS tool</a>"
							+ "<br><br>" + "Best Regards" + "<br>" + "Team HR");
					session.persist(empEmail);

					Employee secondLevelManagerDetails = CommonUtils
							.getCurrentEmployeeData(employee.getSecondLevelSuperiorEmpId());

					EmployeeEmails managerEmail = new EmployeeEmails();
					// managerEmail.setEmpCode(object.getEmpCode());
					managerEmail.setSendTo(secondLevelManagerDetails.getEmail());
					managerEmail.setSendFrom(PMSConstants.SEND_FORM);
					managerEmail.setAppraisalYearId(object.getAppraisalYearId());
					// managerEmail.setEmailType(PMSConstants.SECOND_LEVEL_MANAGER_APPRAISAL_APPROVAL);
					managerEmail.setCreatedOn(new Date());
					managerEmail.setEmployeeName(employee.getEmpName());
					managerEmail.setStatus(PMSConstants.STATUS_PENDING);
					managerEmail.setEmailSubject(messages.getString("email.mid.year.review.subject"));
					managerEmail
							.setEmailContent("Dear Manager" + "<br><br>" + "Your team member " + employee.getEmpName()
									+ " mid-term assessment has been approved by manager, please log into the PMS system to complete this action."
									+ "<br><br>"
									+ "URL <a href='https://pms-hfe.com/PMS/login'>Click here to login PMS tool</a>"
									+ "<br><br>" + "Best Regards" + "<br>" + "Team HR");
					session.persist(managerEmail);

				}
				if (object.getFinalYear() == 1) {
					object.setFirstManagerYearEndAssessmentApproval(PMSConstants.STATUS_ACTIVE);

					EmployeeEmails empEmail = new EmployeeEmails();
					// empEmail.setEmpCode(object.getEmpCode());
					empEmail.setSendTo(firstLevelManagerDetails.getEmail());
					empEmail.setSendFrom(PMSConstants.SEND_FORM);
					empEmail.setAppraisalYearId(object.getAppraisalYearId());
					// empEmail.setEmailType(PMSConstants.FIRST_LEVEL_MANAGER_APPRAISAL_APPROVAL);
					empEmail.setCreatedOn(new Date());
					empEmail.setEmployeeName(employee.getEmpName());
					empEmail.setStatus(PMSConstants.STATUS_PENDING);
					empEmail.setEmailSubject(messages.getString("email.final.year.review.subject"));
					empEmail.setEmailContent("Dear Manager" + "<br><br>" + "You have approved " + employee.getEmpName()
							+ " year-end assessment" + "<br><br>"
							+ "URL <a href='https://pms-hfe.com/PMS/login'>Click here to login PMS tool</a>"
							+ "<br><br>" + "Best Regards" + "<br>" + "Team HR");
					session.persist(empEmail);

					Employee secondLevelManagerDetails = CommonUtils
							.getCurrentEmployeeData(employee.getSecondLevelSuperiorEmpId());

					EmployeeEmails managerEmail = new EmployeeEmails();
					// managerEmail.setEmpCode(object.getEmpCode());
					managerEmail.setSendTo(secondLevelManagerDetails.getEmail());
					managerEmail.setSendFrom(PMSConstants.SEND_FORM);
					managerEmail.setAppraisalYearId(object.getAppraisalYearId());
					// managerEmail.setEmailType(PMSConstants.SECOND_LEVEL_MANAGER_APPRAISAL_APPROVAL);
					managerEmail.setCreatedOn(new Date());
					managerEmail.setEmployeeName(employee.getEmpName());
					managerEmail.setStatus(PMSConstants.STATUS_PENDING);
					managerEmail.setEmailSubject(messages.getString("email.final.year.review.subject"));
					managerEmail
							.setEmailContent("Dear Manager" + "<br><br>" + "Your team member " + employee.getEmpName()
									+ " year-end assessment has been approved by manager, please log into the PMS system to complete this action."
									+ "<br><br>"
									+ "URL <a href='https://pms-hfe.com/PMS/login'>Click here to login PMS tool</a>"
									+ "<br><br>" + "Best Regards" + "<br>" + "Team HR");
					session.persist(managerEmail);
				}
				responseObject.setString("Successfully send to manager");
			}

			if (userType.equals(PMSConstants.SECOND_LEVEL_MANAGER) && buttonType.equals(PMSConstants.BUTTON_APPROVED)) {
				object.setEmployeeIsvisible(PMSConstants.STATUS_DELETED);
				object.setFirstLevelIsvisible(PMSConstants.STATUS_DELETED);
				object.setSecondLevelIsvisible(PMSConstants.STATUS_DELETED);
				object.setSecondLevelIsvisibleCheck(PMSConstants.STATUS_ACTIVE);
				if (object.getInitializationYear() == 1) {
					EmployeeEmails empEmail = new EmployeeEmails();
					// empEmail.setEmpCode(object.getEmpCode());
					empEmail.setSendTo(employee.getEmail());
					empEmail.setSendFrom(PMSConstants.SEND_FORM);
					empEmail.setAppraisalYearId(object.getAppraisalYearId());
					// empEmail.setEmailType(PMSConstants.FINAL_EMPLOYEE_APPRAISAL_APPROVAL);
					empEmail.setCreatedOn(new Date());
					empEmail.setEmployeeName(employee.getEmpName());
					empEmail.setStatus(PMSConstants.STATUS_PENDING);
					empEmail.setEmailSubject(messages.getString("email.goal.setting.initialization.subject"));
					empEmail.setEmailContent("Dear " + employee.getEmpName() + "<br><br>"
							+ "Your goals are approved by manager " + "<br><br>"
							+ "URL <a href='https://pms-hfe.com/PMS/login'>Click here to login PMS tool</a>"
							+ "<br><br>" + "Best Regards" + "<br>" + "Team HR");
					session.persist(empEmail);
					Employee secondLevelManagerDetails = CommonUtils
							.getCurrentEmployeeData(employee.getSecondLevelSuperiorEmpId());

					EmployeeEmails managerEmail = new EmployeeEmails();
					// managerEmail.setEmpCode(object.getEmpCode());
					managerEmail.setSendTo(secondLevelManagerDetails.getEmail());
					managerEmail.setSendFrom(PMSConstants.SEND_FORM);
					managerEmail.setAppraisalYearId(object.getAppraisalYearId());
					// managerEmail.setEmailType(PMSConstants.FINAL_SECOND_LEVEL_MANAGER_APPRAISAL_APPROVAL);
					managerEmail.setCreatedOn(new Date());
					managerEmail.setEmployeeName(employee.getEmpName());
					managerEmail.setStatus(PMSConstants.STATUS_PENDING);
					managerEmail.setEmailSubject(messages.getString("email.goal.setting.initialization.subject"));
					managerEmail.setEmailContent("Dear Manager" + "<br><br>" + "You have approved "
							+ employee.getEmpName() + " goals" + "<br><br>"
							+ "URL <a href='https://pms-hfe.com/PMS/login'>Click here to login PMS tool</a>"
							+ "<br><br>" + "Best Regards" + "<br>" + "Team HR");
					session.persist(managerEmail);
				}
				if (object.getMidYear() == 1) {
					EmployeeEmails empEmail = new EmployeeEmails();
					// empEmail.setEmpCode(object.getEmpCode());
					empEmail.setSendTo(employee.getEmail());
					empEmail.setSendFrom(PMSConstants.SEND_FORM);
					empEmail.setAppraisalYearId(object.getAppraisalYearId());
					// empEmail.setEmailType(PMSConstants.FINAL_EMPLOYEE_APPRAISAL_APPROVAL);
					empEmail.setCreatedOn(new Date());
					empEmail.setEmployeeName(employee.getEmpName());
					empEmail.setStatus(PMSConstants.STATUS_PENDING);
					empEmail.setEmailSubject(messages.getString("email.mid.year.review.subject"));
					empEmail.setEmailContent("Dear " + employee.getEmpName() + "<br><br>"
							+ "Your mid-year review is completed by manager.Please log into PMS system to acknowledge rating with in 2 days. " + "<br>"
							+ "Process - Click PERFORMANCE APPRAISAL RATING - this will take you to a page where you can acknowledge performance rating."+ "<br><br>"
							+ "URL <a href='https://pms-hfe.com/PMS/login'>Click here to login PMS tool</a>"
							+ "<br><br>" + "Best Regards" + "<br>" + "Team HR");
					session.persist(empEmail);
					Employee secondLevelManagerDetails = CommonUtils
							.getCurrentEmployeeData(employee.getSecondLevelSuperiorEmpId());
					EmployeeEmails managerEmail = new EmployeeEmails();
					// managerEmail.setEmpCode(object.getEmpCode());
					managerEmail.setSendTo(secondLevelManagerDetails.getEmail());
					managerEmail.setSendFrom(PMSConstants.SEND_FORM);
					managerEmail.setAppraisalYearId(object.getAppraisalYearId());
					// managerEmail.setEmailType(PMSConstants.FINAL_SECOND_LEVEL_MANAGER_APPRAISAL_APPROVAL);
					managerEmail.setCreatedOn(new Date());
					managerEmail.setEmployeeName(employee.getEmpName());
					managerEmail.setStatus(PMSConstants.STATUS_PENDING);
					managerEmail.setEmailSubject(messages.getString("email.mid.year.review.subject"));
					managerEmail.setEmailContent("Dear Manager" + "<br><br>" + "You have approved "
							+ employee.getEmpName() + " mid-year. " + "<br><br>"
							+ "URL <a href='https://pms-hfe.com/PMS/login'>Click here to login PMS tool</a>"
							+ "<br><br>" + "Best Regards" + "<br>" + "Team HR");
					session.persist(managerEmail);
				}
				if (object.getFinalYear() == 1) {
					EmployeeEmails empEmail = new EmployeeEmails();
					// empEmail.setEmpCode(object.getEmpCode());
					empEmail.setSendTo(employee.getEmail());
					empEmail.setSendFrom(PMSConstants.SEND_FORM);
					empEmail.setAppraisalYearId(object.getAppraisalYearId());
					// empEmail.setEmailType(PMSConstants.FINAL_EMPLOYEE_APPRAISAL_APPROVAL);
					empEmail.setCreatedOn(new Date());
					empEmail.setEmployeeName(employee.getEmpName());
					empEmail.setStatus(PMSConstants.STATUS_PENDING);
					empEmail.setEmailSubject(messages.getString("email.final.year.review.subject"));
					empEmail.setEmailContent("Dear " + employee.getEmpName() + "<br><br>"
							+ "Your Year-end assessment is completed by manager.Please log into PMS system to acknowledge rating with in 2 days. " + "<br>"
							+ "Process - Click PERFORMANCE APPRAISAL RATING - this will take you to a page where you can acknowledge performance rating."+ "<br><br>"
							+ "URL <a href='https://pms-hfe.com/PMS/login'>Click here to login PMS tool</a>"
							+ "<br><br>" + "Best Regards" + "<br>" + "Team HR");
					session.persist(empEmail);
					Employee secondLevelManagerDetails = CommonUtils
							.getCurrentEmployeeData(employee.getSecondLevelSuperiorEmpId());
					EmployeeEmails managerEmail = new EmployeeEmails();
					// managerEmail.setEmpCode(object.getEmpCode());
					managerEmail.setSendTo(secondLevelManagerDetails.getEmail());
					managerEmail.setSendFrom(PMSConstants.SEND_FORM);
					managerEmail.setAppraisalYearId(object.getAppraisalYearId());
					// managerEmail.setEmailType(PMSConstants.FINAL_SECOND_LEVEL_MANAGER_APPRAISAL_APPROVAL);
					managerEmail.setCreatedOn(new Date());
					managerEmail.setEmployeeName(employee.getEmpName());
					managerEmail.setStatus(PMSConstants.STATUS_PENDING);
					managerEmail.setEmailSubject(messages.getString("email.final.year.review.subject"));
					managerEmail.setEmailContent("Dear Manager" + "<br><br>" + "You have approved "
							+ employee.getEmpName() + " Year-end assessment. " + "<br><br>"
							+ "URL <a href='https://pms-hfe.com/PMS/login'>Click here to login PMS tool</a>"
							+ "<br><br>" + "Best Regards" + "<br>" + "Team HR");
					session.persist(managerEmail);
				}
				responseObject.setString("Appraisal successfully approved");
			}
			if (userType.equals(PMSConstants.FIRST_LEVEL_MANAGER) && buttonType.equals(PMSConstants.BUTTON_REJECTED)) {
				checkStatus = true;
				object.setEmployeeIsvisible(PMSConstants.STATUS_ACTIVE);
				object.setFirstLevelIsvisible(PMSConstants.STATUS_DELETED);
				object.setSecondLevelIsvisible(PMSConstants.STATUS_DELETED);
				if (object.getInitializationYear() == 1) {
//					object.setFirstManagerGoalApproval(PMSConstants.STATUS_ACTIVE);
					EmployeeEmails empEmail = new EmployeeEmails();
					// empEmail.setEmpCode(object.getEmpCode());
					empEmail.setSendTo(employee.getEmail());
					empEmail.setSendFrom(PMSConstants.SEND_FORM);
					empEmail.setAppraisalYearId(object.getAppraisalYearId());
					// empEmail.setEmailType(PMSConstants.EMPLOYEE_APPRAISAL_REJECT);
					empEmail.setCreatedOn(new Date());
					empEmail.setEmployeeName(employee.getEmpName());
					empEmail.setStatus(PMSConstants.STATUS_PENDING);
					empEmail.setEmailSubject(messages.getString("email.goal.setting.initialization.subject"));
					empEmail.setEmailContent("Dear " + employee.getEmpName() + "<br><br>" + "Your goals has been rejected." + "<br><br>"
									+ "URL <a href='https://pms-hfe.com/PMS/login'>Click here to login PMS tool</a>"
									+ "<br><br>" + "Best Regards" + "<br>" + "Team HR");
					session.persist(empEmail);
					Employee firstLevelManagerDetails = CommonUtils
							.getCurrentEmployeeData(employee.getFirstLevelSuperiorEmpId());
					EmployeeEmails managerEmail = new EmployeeEmails();
					// empEmail.setEmpCode(object.getEmpCode());
					managerEmail.setSendTo(firstLevelManagerDetails.getEmail());
					managerEmail.setSendFrom(PMSConstants.SEND_FORM);
					managerEmail.setAppraisalYearId(object.getAppraisalYearId());
					// managerEmail.setEmailType(PMSConstants.MID_FIRST_LEVEL_MANAGER_APPRAISAL_REJECTED);
					managerEmail.setCreatedOn(new Date());
					managerEmail.setEmployeeName(employee.getEmpName());
					managerEmail.setStatus(PMSConstants.STATUS_PENDING);
					managerEmail.setEmailSubject(messages.getString("email.goal.setting.initialization.subject"));
					managerEmail.setEmailContent("Dear Manager" + "<br><br>" + "You have rejected "
							+ employee.getEmpName() + " goals." + "<br><br>"
							+ "URL <a href='https://pms-hfe.com/PMS/login'>Click here to login PMS tool</a>"
							+ "<br><br>" + "Best Regards" + "<br>" + "Team HR");
					session.persist(managerEmail);
				}
				if (object.getMidYear() == 1) {
//					object.setFirstManagerMidYearApproval(PMSConstants.STATUS_ACTIVE);
					EmployeeEmails empEmail = new EmployeeEmails();
					// empEmail.setEmpCode(object.getEmpCode());
					empEmail.setSendTo(employee.getEmail());
					empEmail.setSendFrom(PMSConstants.SEND_FORM);
					empEmail.setAppraisalYearId(object.getAppraisalYearId());
					// empEmail.setEmailType(PMSConstants.EMPLOYEE_APPRAISAL_REJECT);
					empEmail.setCreatedOn(new Date());
					empEmail.setEmployeeName(employee.getEmpName());
					empEmail.setStatus(PMSConstants.STATUS_PENDING);
					empEmail.setEmailSubject(messages.getString("email.mid.year.review.subject"));
					empEmail.setEmailContent("Dear " + employee.getEmpName() + "<br><br>"
							+ "Your mid year view has been rejected." + "<br><br>"
							+ "URL <a href='https://pms-hfe.com/PMS/login'>Click here to login PMS tool</a>"
							+ "<br><br>" + "Best Regards" + "<br>" + "Team HR");
					session.persist(empEmail);
					Employee firstLevelManagerDetails = CommonUtils
							.getCurrentEmployeeData(employee.getFirstLevelSuperiorEmpId());
					EmployeeEmails managerEmail = new EmployeeEmails();
					// empEmail.setEmpCode(object.getEmpCode());
					managerEmail.setSendTo(firstLevelManagerDetails.getEmail());
					managerEmail.setSendFrom(PMSConstants.SEND_FORM);
					managerEmail.setAppraisalYearId(object.getAppraisalYearId());
					// managerEmail.setEmailType(PMSConstants.MID_FIRST_LEVEL_MANAGER_APPRAISAL_REJECTED);
					managerEmail.setCreatedOn(new Date());
					managerEmail.setEmployeeName(employee.getEmpName());
					managerEmail.setStatus(PMSConstants.STATUS_PENDING);
					managerEmail.setEmailSubject(messages.getString("email.mid.year.review.subject"));
					managerEmail.setEmailContent("Dear Manager" + "<br><br>" + "You have rejected "
							+ employee.getEmpName() + " mid year review." + "<br><br>"
							+ "URL <a href='https://pms-hfe.com/PMS/login'>Click here to login PMS tool</a>"
							+ "<br><br>" + "Best Regards" + "<br>" + "Team HR");
					session.persist(managerEmail);
				}
				if (object.getFinalYear() == 1) {
//					object.setFirstManagerYearEndAssessmentApproval(PMSConstants.STATUS_ACTIVE);
					EmployeeEmails empEmail = new EmployeeEmails();
					// empEmail.setEmpCode(object.getEmpCode());
					empEmail.setSendTo(employee.getEmail());
					empEmail.setSendFrom(PMSConstants.SEND_FORM);
					empEmail.setAppraisalYearId(object.getAppraisalYearId());
					// empEmail.setEmailType(PMSConstants.EMPLOYEE_APPRAISAL_REJECT);
					empEmail.setCreatedOn(new Date());
					empEmail.setEmployeeName(employee.getEmpName());
					empEmail.setStatus(PMSConstants.STATUS_PENDING);
					empEmail.setEmailSubject(messages.getString("email.final.year.review.subject"));
					empEmail.setEmailContent("Dear " + employee.getEmpName() + "<br><br>"
							+ "Your Year-end assessment has been rejected." + "<br><br>"
							+ "URL <a href='https://pms-hfe.com/PMS/login'>Click here to login PMS tool</a>"
							+ "<br><br>" + "Best Regards" + "<br>" + "Team HR");
					session.persist(empEmail);
					Employee firstLevelManagerDetails = CommonUtils
							.getCurrentEmployeeData(employee.getFirstLevelSuperiorEmpId());
					EmployeeEmails managerEmail = new EmployeeEmails();
					// empEmail.setEmpCode(object.getEmpCode());
					managerEmail.setSendTo(firstLevelManagerDetails.getEmail());
					managerEmail.setSendFrom(PMSConstants.SEND_FORM);
					managerEmail.setAppraisalYearId(object.getAppraisalYearId());
					// managerEmail.setEmailType(PMSConstants.MID_FIRST_LEVEL_MANAGER_APPRAISAL_REJECTED);
					managerEmail.setCreatedOn(new Date());
					managerEmail.setEmployeeName(employee.getEmpName());
					managerEmail.setStatus(PMSConstants.STATUS_PENDING);
					managerEmail.setEmailSubject(messages.getString("email.final.year.review.subject"));
					managerEmail.setEmailContent("Dear Manager" + "<br><br>" + "You have rejected "
							+ employee.getEmpName() + " Year-end assessment." + "<br><br>"
							+ "URL <a href='https://pms-hfe.com/PMS/login'>Click here to login PMS tool</a>"
							+ "<br><br>" + "Best Regards" + "<br>" + "Team HR");
					session.persist(managerEmail);
				}
				responseObject.setString("Appraisal successfully rejected");
			}
			if (userType.equals(PMSConstants.SECOND_LEVEL_MANAGER) && buttonType.equals(PMSConstants.BUTTON_REJECTED)) {
				object.setEmployeeIsvisible(PMSConstants.STATUS_DELETED);
				object.setFirstLevelIsvisible(PMSConstants.STATUS_ACTIVE);
				object.setSecondLevelIsvisible(PMSConstants.STATUS_DELETED);
				object.setSecondLevelIsvisibleCheck(PMSConstants.STATUS_DELETED);
				checkStatus = true;
				Employee firstLevelManagerDetails = CommonUtils
						.getCurrentEmployeeData(employee.getFirstLevelSuperiorEmpId());
				if (object.getInitializationYear() == 1) {
					EmployeeEmails empEmail = new EmployeeEmails();
					// empEmail.setEmpCode(object.getEmpCode());
					empEmail.setSendTo(firstLevelManagerDetails.getEmail());
					empEmail.setSendFrom(PMSConstants.SEND_FORM);
					empEmail.setAppraisalYearId(object.getAppraisalYearId());
					empEmail.setEmailType(PMSConstants.FINAL_FIRST_LEVEL_MANAGER_APPRAISAL_REJECTED);
					empEmail.setCreatedOn(new Date());
					empEmail.setEmployeeName(employee.getEmpName());
					empEmail.setStatus(PMSConstants.STATUS_PENDING);
					empEmail.setEmailSubject(messages.getString("email.goal.setting.initialization.subject"));
					empEmail.setEmailContent("Dear Manager" + "<br><br>" + "Your team member " + employee.getEmpName()
							+ " goals has been rejected." + "<br><br>"
							+ "URL <a href='https://pms-hfe.com/PMS/login'>Click here to login PMS tool</a>"
							+ "<br><br>" + "Best Regards" + "<br>" + "Team HR");
					session.persist(empEmail);
					Employee secondLevelManagerDetails = CommonUtils
							.getCurrentEmployeeData(employee.getSecondLevelSuperiorEmpId());
					EmployeeEmails managerEmail = new EmployeeEmails();
					// managerEmail.setEmpCode(object.getEmpCode());
					managerEmail.setSendTo(secondLevelManagerDetails.getEmail());
					managerEmail.setSendFrom(PMSConstants.SEND_FORM);
					managerEmail.setAppraisalYearId(object.getAppraisalYearId());
					managerEmail.setEmailType(PMSConstants.FINAL_SECOND_LEVEL_MANAGER_APPRAISAL_REJECTED);
					managerEmail.setCreatedOn(new Date());
					managerEmail.setEmployeeName(employee.getEmpName());
					managerEmail.setStatus(PMSConstants.STATUS_PENDING);
					managerEmail.setEmailSubject(messages.getString("email.goal.setting.initialization.subject"));
					managerEmail.setEmailContent("Dear Manager" + "<br><br>" + "You have rejected "
							+ employee.getEmpName() + " goals." + "<br><br>"
							+ "URL <a href='https://pms-hfe.com/PMS/login'>Click here to login PMS tool</a>"
							+ "<br><br>" + "Best Regards" + "<br>" + "Team HR");
					session.persist(managerEmail);
				}
				if (object.getMidYear() == 1) {
					EmployeeEmails empEmail = new EmployeeEmails();
					// empEmail.setEmpCode(object.getEmpCode());
					empEmail.setSendTo(firstLevelManagerDetails.getEmail());
					empEmail.setSendFrom(PMSConstants.SEND_FORM);
					empEmail.setAppraisalYearId(object.getAppraisalYearId());
					empEmail.setEmailType(PMSConstants.FINAL_FIRST_LEVEL_MANAGER_APPRAISAL_REJECTED);
					empEmail.setCreatedOn(new Date());
					empEmail.setEmployeeName(employee.getEmpName());
					empEmail.setStatus(PMSConstants.STATUS_PENDING);
					empEmail.setEmailSubject(messages.getString("email.mid.year.review.subject"));
					empEmail.setEmailContent("Dear Manager" + "<br><br>" + "Your team member " + employee.getEmpName()
							+ " mid year review has been rejected." + "<br><br>"
							+ "URL <a href='https://pms-hfe.com/PMS/login'>Click here to login PMS tool</a>"
							+ "<br><br>" + "Best Regards" + "<br>" + "Team HR");
					session.persist(empEmail);
					Employee secondLevelManagerDetails = CommonUtils
							.getCurrentEmployeeData(employee.getSecondLevelSuperiorEmpId());
					EmployeeEmails managerEmail = new EmployeeEmails();
					// managerEmail.setEmpCode(object.getEmpCode());
					managerEmail.setSendTo(secondLevelManagerDetails.getEmail());
					managerEmail.setSendFrom(PMSConstants.SEND_FORM);
					managerEmail.setAppraisalYearId(object.getAppraisalYearId());
					managerEmail.setEmailType(PMSConstants.FINAL_SECOND_LEVEL_MANAGER_APPRAISAL_REJECTED);
					managerEmail.setCreatedOn(new Date());
					managerEmail.setEmployeeName(employee.getEmpName());
					managerEmail.setStatus(PMSConstants.STATUS_PENDING);
					managerEmail.setEmailSubject(messages.getString("email.mid.year.review.subject"));
					managerEmail.setEmailContent("Dear Manager" + "<br><br>" + "You have rejected "
							+ employee.getEmpName() + " mid year review." + "<br><br>"
							+ "URL <a href='https://pms-hfe.com/PMS/login'>Click here to login PMS tool</a>"
							+ "<br><br>" + "Best Regards" + "<br>" + "Team HR");
					session.persist(managerEmail);
				}
				if (object.getFinalYear() == 1) {
					EmployeeEmails empEmail = new EmployeeEmails();
					// empEmail.setEmpCode(object.getEmpCode());
					empEmail.setSendTo(firstLevelManagerDetails.getEmail());
					empEmail.setSendFrom(PMSConstants.SEND_FORM);
					empEmail.setAppraisalYearId(object.getAppraisalYearId());
					empEmail.setEmailType(PMSConstants.FINAL_FIRST_LEVEL_MANAGER_APPRAISAL_REJECTED);
					empEmail.setCreatedOn(new Date());
					empEmail.setEmployeeName(employee.getEmpName());
					empEmail.setStatus(PMSConstants.STATUS_PENDING);
					empEmail.setEmailSubject(messages.getString("email.final.year.review.subject"));
					empEmail.setEmailContent("Dear Manager" + "<br><br>" + "Your team member " + employee.getEmpName()
							+ " Year-end assessment has been rejected." + "<br><br>"
							+ "URL <a href='https://pms-hfe.com/PMS/login'>Click here to login PMS tool</a>"
							+ "<br><br>" + "Best Regards" + "<br>" + "Team HR");
					session.persist(empEmail);
					Employee secondLevelManagerDetails = CommonUtils
							.getCurrentEmployeeData(employee.getSecondLevelSuperiorEmpId());
					EmployeeEmails managerEmail = new EmployeeEmails();
					// managerEmail.setEmpCode(object.getEmpCode());
					managerEmail.setSendTo(secondLevelManagerDetails.getEmail());
					managerEmail.setSendFrom(PMSConstants.SEND_FORM);
					managerEmail.setAppraisalYearId(object.getAppraisalYearId());
					managerEmail.setEmailType(PMSConstants.FINAL_SECOND_LEVEL_MANAGER_APPRAISAL_REJECTED);
					managerEmail.setCreatedOn(new Date());
					managerEmail.setEmployeeName(employee.getEmpName());
					managerEmail.setStatus(PMSConstants.STATUS_PENDING);
					managerEmail.setEmailSubject(messages.getString("email.final.year.review.subject"));
					managerEmail.setEmailContent("Dear Manager" + "<br><br>" + "You have rejected "
							+ employee.getEmpName() + " Year-end assessment" + "<br><br>"
							+ "URL <a href='https://pms-hfe.com/PMS/login'>Click here to login PMS tool</a>"
							+ "<br><br>" + "Best Regards" + "<br>" + "Team HR");
					session.persist(managerEmail);
				}
				responseObject.setString("Appraisal successfully rejected");
			}
			object.setModifiedOn(new Date());
			object.setModifiedBy(empCode);
			session.update(object);
			tx.commit();
			if (checkStatus) {
				tx = session.beginTransaction();
				if (userType.equals(PMSConstants.FIRST_LEVEL_MANAGER)
						&& buttonType.equals(PMSConstants.BUTTON_REJECTED)) {
					List<EmployeeKRAData> kralist = DbUtils.getValidatedKRADetails(empCode, activeAppraisalYearID,
							PMSConstants.FIRST_LEVEL_MANAGER);
					List<Integer> kraNewlist = DbUtils.getValidatedNewKRADetails(empCode, activeAppraisalYearID,
							PMSConstants.FIRST_LEVEL_MANAGER);
					List<Integer> behaviouralCompetenceDetailsIdList = DbUtils.getValidatedBehaviouralCompetenceDetails(
							empCode, activeAppraisalYearID, PMSConstants.FIRST_LEVEL_MANAGER);
					List<Integer> extraOrdinaryDetailsIdList = DbUtils.getValidatedExtraOrdinaryDetails(empCode,
							activeAppraisalYearID, PMSConstants.FIRST_LEVEL_MANAGER);
					List<Integer> trainingNeedsDetailsIdList = DbUtils.getValidatedTrainingNeedsDetails(empCode,
							activeAppraisalYearID, PMSConstants.FIRST_LEVEL_MANAGER);
					if (object.getInitializationYear() == 1) {
					List<Integer> careerAspirationDetailsIdList = DbUtils.getValidatedCareerAspirationDetails(
								empCode, activeAppraisalYearID, PMSConstants.FIRST_LEVEL_MANAGER);
						for (int i = 0; i < careerAspirationDetailsIdList.size(); i++) {
							CareerAspirationsDetails cad = (CareerAspirationsDetails) session
									.get(CareerAspirationsDetails.class, careerAspirationDetailsIdList.get(i));
							cad.setIsValidatedByEmployee(PMSConstants.STATUS_DELETED);
							cad.setIsValidatedByFirstLevel(PMSConstants.STATUS_DELETED);
							session.update(cad);
						}
					}
					if (object.getMidYear() == 1) {
						List<Integer> careerAspirationDetailsIdList = DbUtils.getValidatedCareerAspirationDetails(
								empCode, activeAppraisalYearID, PMSConstants.FIRST_LEVEL_MANAGER);
						for (int i = 0; i < careerAspirationDetailsIdList.size(); i++) {
							CareerAspirationsDetails cad = (CareerAspirationsDetails) session
									.get(CareerAspirationsDetails.class, careerAspirationDetailsIdList.get(i));
							cad.setMidYearCommentsStatus(PMSConstants.STATUS_DELETED);
							cad.setMidYearCommentsStatusFirstLevel(PMSConstants.STATUS_DELETED);
							session.update(cad);
						}
					}
					if (object.getFinalYear() == 1) {
						List<Integer> careerAspirationDetailsIdList = DbUtils.getValidatedCareerAspirationDetails(
								empCode, activeAppraisalYearID, PMSConstants.FIRST_LEVEL_MANAGER);
						for (int i = 0; i < careerAspirationDetailsIdList.size(); i++) {
							CareerAspirationsDetails cad = (CareerAspirationsDetails) session
									.get(CareerAspirationsDetails.class, careerAspirationDetailsIdList.get(i));
							cad.setYearEndCommentsStatus(PMSConstants.STATUS_DELETED);
							cad.setYearEndCommentsStatusFirstLevel(PMSConstants.STATUS_DELETED);
							session.update(cad);
						}
					}
					for (int i = 0; i < extraOrdinaryDetailsIdList.size(); i++) {
						ExtraOrdinaryDetails ex = (ExtraOrdinaryDetails) session.get(ExtraOrdinaryDetails.class,
								extraOrdinaryDetailsIdList.get(i));
						ex.setIsValidatedByEmployee(PMSConstants.STATUS_DELETED);
						ex.setIsValidatedByFirstLevel(PMSConstants.STATUS_DELETED);
						session.update(ex);
					}
					for (int i = 0; i < behaviouralCompetenceDetailsIdList.size(); i++) {
						BehaviouralCompetenceDetails bcd = (BehaviouralCompetenceDetails) session
								.get(BehaviouralCompetenceDetails.class, behaviouralCompetenceDetailsIdList.get(i));
						bcd.setIsValidatedByEmployee(PMSConstants.STATUS_DELETED);
						bcd.setIsValidatedByFirstLevel(PMSConstants.STATUS_DELETED);
						session.update(bcd);
					}
					for (int i = 0; i < kralist.size(); i++) {
						KraDetails kra = (KraDetails) session.get(KraDetails.class, kralist.get(i).getId());
						kra.setIsValidatedByEmployee(PMSConstants.STATUS_DELETED);
						kra.setIsValidatedByFirstLevel(PMSConstants.STATUS_DELETED);
						session.update(kra);
					}
					for (int i = 0; i < kraNewlist.size(); i++) {
						KraNew kra = (KraNew) session.get(KraNew.class, kraNewlist.get(i));
						kra.setIsValidatedByEmployee(PMSConstants.STATUS_DELETED);
						kra.setIsValidatedByFirstLevel(PMSConstants.STATUS_DELETED);
						session.update(kra);
					}
					for (int i = 0; i < trainingNeedsDetailsIdList.size(); i++) {
						TrainingNeedsDetails tnd = (TrainingNeedsDetails) session.get(TrainingNeedsDetails.class,
								trainingNeedsDetailsIdList.get(i));
						tnd.setIsValidatedByEmployee(PMSConstants.STATUS_DELETED);
						tnd.setIsValidatedByFirstLevel(PMSConstants.STATUS_DELETED);
						session.update(tnd);
					}
				}
				if (userType.equals(PMSConstants.SECOND_LEVEL_MANAGER)
						&& buttonType.equals(PMSConstants.BUTTON_REJECTED)) {
					List<EmployeeKRAData> kralist = DbUtils.getValidatedKRADetails(empCode, activeAppraisalYearID,
							PMSConstants.SECOND_LEVEL_MANAGER);
//					List<Integer> kraNewlist = DbUtils.getValidatedNewKRADetails(empCode, activeAppraisalYearID,
//							PMSConstants.SECOND_LEVEL_MANAGER);
					for (int i = 0; i < kralist.size(); i++) {
						KraDetails kra = (KraDetails) session.get(KraDetails.class, kralist.get(i).getId());
						kra.setIsValidatedByFirstLevel(PMSConstants.STATUS_DELETED);
						kra.setIsValidatedBySecondLevel(PMSConstants.STATUS_DELETED);
						session.update(kra);
					}
//					for (int i = 0; i < kraNewlist.size(); i++) {
//						KraNew kra = (KraNew) session.get(KraNew.class, kraNewlist.get(i));
//						kra.setIsValidatedByFirstLevel(PMSConstants.STATUS_DELETED);
//						kra.setIsValidatedBySecondLevel(PMSConstants.STATUS_DELETED);
//						session.update(kra);
//					}
					if (object.getInitializationYear() == 1) {
						List<Integer> careerAspirationDetailsIdList = DbUtils.getValidatedCareerAspirationDetails(
								empCode, activeAppraisalYearID, PMSConstants.FIRST_LEVEL_MANAGER);
						for (int i = 0; i < careerAspirationDetailsIdList.size(); i++) {
							CareerAspirationsDetails cad = (CareerAspirationsDetails) session
									.get(CareerAspirationsDetails.class, careerAspirationDetailsIdList.get(i));
							cad.setIsValidatedByFirstLevel(PMSConstants.STATUS_DELETED);
							cad.setIsValidatedBySecondLevel(PMSConstants.STATUS_DELETED);
							session.update(cad);
						}
					}
					if (object.getMidYear() == 1) {
						List<Integer> careerAspirationDetailsIdList = DbUtils.getValidatedCareerAspirationDetails(
								empCode, activeAppraisalYearID, PMSConstants.FIRST_LEVEL_MANAGER);
						for (int i = 0; i < careerAspirationDetailsIdList.size(); i++) {
							CareerAspirationsDetails cad = (CareerAspirationsDetails) session
									.get(CareerAspirationsDetails.class, careerAspirationDetailsIdList.get(i));
							cad.setMidYearCommentsStatusFirstLevel(PMSConstants.STATUS_DELETED);
							cad.setMidYearCommentsStatusSecondLevel(PMSConstants.STATUS_DELETED);
							session.update(cad);
						}
					}
					if (object.getFinalYear() == 1) {
						List<Integer> careerAspirationDetailsIdList = DbUtils.getValidatedCareerAspirationDetails(
								empCode, activeAppraisalYearID, PMSConstants.FIRST_LEVEL_MANAGER);
						for (int i = 0; i < careerAspirationDetailsIdList.size(); i++) {
							CareerAspirationsDetails cad = (CareerAspirationsDetails) session
									.get(CareerAspirationsDetails.class, careerAspirationDetailsIdList.get(i));
							cad.setYearEndCommentsStatusSecondLevel(PMSConstants.STATUS_DELETED);
							cad.setYearEndCommentsStatusFirstLevel(PMSConstants.STATUS_DELETED);
							session.update(cad);
						}
					}
					List<Integer> behaviouralCompetenceDetailsIdList = DbUtils.getValidatedBehaviouralCompetenceDetails(
							empCode, activeAppraisalYearID, PMSConstants.SECOND_LEVEL_MANAGER);
					for (int i = 0; i < behaviouralCompetenceDetailsIdList.size(); i++) {
						BehaviouralCompetenceDetails bcd = (BehaviouralCompetenceDetails) session
								.get(BehaviouralCompetenceDetails.class, behaviouralCompetenceDetailsIdList.get(i));
						bcd.setIsValidatedByFirstLevel(PMSConstants.STATUS_DELETED);
						bcd.setIsValidatedBySecondLevel(PMSConstants.STATUS_DELETED);
						session.update(bcd);
					}
					List<Integer> extraOrdinaryDetailsIdList = DbUtils.getValidatedExtraOrdinaryDetails(empCode,
							activeAppraisalYearID, PMSConstants.SECOND_LEVEL_MANAGER);
					for (int i = 0; i < extraOrdinaryDetailsIdList.size(); i++) {
						ExtraOrdinaryDetails ex = (ExtraOrdinaryDetails) session.get(ExtraOrdinaryDetails.class,
								extraOrdinaryDetailsIdList.get(i));
						ex.setIsValidatedByFirstLevel(PMSConstants.STATUS_DELETED);
						ex.setIsValidatedBySecondLevel(PMSConstants.STATUS_DELETED);
						session.update(ex);
					}
					List<Integer> trainingNeedsDetailsIdList = DbUtils.getValidatedTrainingNeedsDetails(empCode,
							activeAppraisalYearID, PMSConstants.FIRST_LEVEL_MANAGER);
					for (int i = 0; i < trainingNeedsDetailsIdList.size(); i++) {
						TrainingNeedsDetails tnd = (TrainingNeedsDetails) session.get(TrainingNeedsDetails.class,
								trainingNeedsDetailsIdList.get(i));
						tnd.setIsValidatedByFirstLevel(PMSConstants.STATUS_DELETED);
						tnd.setIsValidatedBySecondLevel(PMSConstants.STATUS_DELETED);
						session.update(tnd);
					}
				}
				tx.commit();
			}
			responseObject.setObject(getUserAppraisalDetails(empCode, activeAppraisalYearID,"EMPLOYEE").getObject());
			responseObject.setInteger(PMSConstants.STATUS_SUCCESS);
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			logger.error("Exception while sending to first level manager in AppraisalYearDetails ... " + e);
			responseObject.setInteger(PMSConstants.STATUS_FAILED);
			responseObject.setString("Exception while sending to first level manager in AppraisalYearDetails ... " + e);
			responseObject.setObject(e);
		} finally {
			session.flush();
			session.close();
		}
		return responseObject;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseObject employeeAcknowledgement(String empCode, Integer appraisalYearId) {
		ResponseObject res = isEmployeeAppraisalStart(empCode, appraisalYearId);
		if (res.getInteger() != null) {
			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			List<AppraisalYearDetails> ayd = (List<AppraisalYearDetails>) res.getObject();
			try {
				AppraisalYearDetails object = (AppraisalYearDetails) session.get(AppraisalYearDetails.class,ayd.get(0).getId());
				object.setEmployeeIsvisible(PMSConstants.STATUS_ACTIVE);
				object.setAcknowledgementCheck(PMSConstants.STATUS_ACTIVE);
				session.update(object);
				tx.commit();
				res.setInteger(PMSConstants.STATUS_SUCCESS);
				res.setString("Acknowledgement Successfully.");
			} catch (HibernateException e) {
				if (tx != null)
					tx.rollback();
				e.printStackTrace();
				logger.error("Exception while activating appraisal process for employee ... " + e);
				res.setInteger(PMSConstants.STATUS_FAILED);
				res.setString("Exception while activating appraisal process for employee.");
			} finally {
				session.flush();
				session.close();
			}
		} else {
			res.setObject(null);
			res.setInteger(PMSConstants.STATUS_PENDING);
			res.setString("Appraisal not started please contact admin.");
		}
		return res;
	}

	private ResponseObject isEmployeeAppraisalStart(String empCode, Integer appraisalYearId) {
		Session session = this.sessionFactory.openSession();
		List<AppraisalYearDetails> list = new ArrayList<AppraisalYearDetails>();
		ResponseObject res = new ResponseObject();
		try {
			Criteria criteria = session.createCriteria(AppraisalYearDetails.class);
			criteria.add(Restrictions.eq("empCode", empCode));
			criteria.add(Restrictions.eq("appraisalYearId", appraisalYearId));
			list = criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception while isEmployeeAppraisalStart List... " + e);
		} finally {
			session.flush();
			session.close();
		}
		if (list.size() > 0) {
			res.setObject(list);
			res.setInteger(PMSConstants.STATUS_SUCCESS);
		}
		return res;
	}

	@Override
	public ResponseObject getAppraisalPendingEmployeeDetails(String empCode, Integer appraisalYearId,
			String managerType, String appraisalStage) {
		Session session = this.sessionFactory.openSession();
		ResponseObject res = new ResponseObject();
		try {
			String sql = "select emp.empName as empName, emp.empCode as empCode, des.name as designationName,dep.name as departmentName, "
					+ " ayd.initializationYear as initializationYear,ayd.midYear as midYear,ayd.finalYear as finalYear,ayd.employeeIsvisible as employeeIsvisible,"
					+ " ayd.firstLevelIsvisible as firstLevelIsvisible,ayd.secondLevelIsvisible as secondLevelIsvisible,ayd.secondLevelIsvisibleCheck as secondLevelIsvisibleCheck, "
					+ " ayd.firstManagerGoalApproval as firstManagerGoalApproval,ayd.firstManagerMidYearApproval as firstManagerMidYearApproval,ayd.firstManagerYearEndAssessmentApproval as firstManagerYearEndAssessmentApproval, "
					+ " ayd.employeeApproval as employeeApproval,ayd.employeeMidYearApproval as employeeMidYearApproval, ayd.acknowledgementCheck as acknowledgementCheck "
					+ " from Employee as emp,Designation as des, Department as dep , AppraisalYearDetails as ayd "
					+ " where emp.designationId = des.id and dep.id = emp.departmentId and ayd.appraisalYearId =:appraisalYearId and ayd.empCode = emp.empCode ";
			if (appraisalStage.equals(PMSConstants.GOAL_SETTING)
					&& managerType.equals(PMSConstants.FIRST_LEVEL_MANAGER)) {
				sql = sql
						+ " and ayd.initializationYear = 1 and  (ayd.employeeIsvisible = 1 OR ayd.employeeIsvisible = 0) and ayd.secondLevelIsvisibleCheck = 0"
						+ " and emp.firstLevelSuperiorEmpId =:firstLevelSuperiorEmpId";
			}
			if (appraisalStage.equals(PMSConstants.GOAL_APPROVAL) && managerType.equals(PMSConstants.FIRST_LEVEL_MANAGER)) {
				sql = sql
						+ " and ayd.initializationYear = 1 and (ayd.employeeIsvisible = 0 OR ayd.secondLevelIsvisibleCheck = 1) and ayd.acknowledgementCheck = 1"
						+ " and emp.firstLevelSuperiorEmpId =:firstLevelSuperiorEmpId";
			}
			if (appraisalStage.equals(PMSConstants.MID_TERM_REVIEW)
					&& managerType.equals(PMSConstants.FIRST_LEVEL_MANAGER)) {
//				sql = sql + " and ((ayd.initializationYear = 1 and emp.firstLevelSuperiorEmpId =:firstLevelSuperiorEmpId and ayd.secondLevelIsvisibleCheck = 1 ) OR (ayd.midYear = 1  and emp.firstLevelSuperiorEmpId =:firstLevelSuperiorEmpId and ayd.secondLevelIsvisibleCheck = 0))";
//				11-01-2019 Code Change
				sql = sql + " and ((ayd.midYear = 1  and emp.firstLevelSuperiorEmpId =:firstLevelSuperiorEmpId and ayd.secondLevelIsvisibleCheck = 0))";
			}
			if (appraisalStage.equals(PMSConstants.YEAR_END_ASSESSMENT)
					&& managerType.equals(PMSConstants.FIRST_LEVEL_MANAGER)) {
//				sql = sql + " and ((ayd.finalYear = 1 and ayd.secondLevelIsvisibleCheck = 0 and emp.firstLevelSuperiorEmpId =:firstLevelSuperiorEmpId ) "
//						+ "  OR (ayd.midYear = 1 and ayd.secondLevelIsvisibleCheck = 1 and emp.firstLevelSuperiorEmpId =:firstLevelSuperiorEmpId))";
//				11-01-2019 Code Change
				sql = sql + " and ((ayd.finalYear = 1 and ayd.secondLevelIsvisibleCheck = 0 and emp.firstLevelSuperiorEmpId =:firstLevelSuperiorEmpId ) "
						+ "  )";
			}
			if (appraisalStage.equals(PMSConstants.ASSESSMENT_APPROVAL)
					&& managerType.equals(PMSConstants.FIRST_LEVEL_MANAGER)) {
				sql = sql
						+ " and ayd.finalYear = 1  and ayd.secondLevelIsvisibleCheck = 1 and emp.firstLevelSuperiorEmpId =:firstLevelSuperiorEmpId";
			}
			if (appraisalStage.equals(PMSConstants.GOAL_SETTING)
					&& managerType.equals(PMSConstants.SECOND_LEVEL_MANAGER)) {
				sql = sql
						+ " and ayd.initializationYear = 1 and  (ayd.employeeIsvisible = 1 OR ayd.employeeIsvisible = 0) and ayd.secondLevelIsvisibleCheck = 0"
						+ " and emp.secondLevelSuperiorEmpId =:secondLevelSuperiorEmpId";
			}

			if (appraisalStage.equals(PMSConstants.GOAL_APPROVAL)
					&& managerType.equals(PMSConstants.SECOND_LEVEL_MANAGER)) {
				sql = sql
						+ " and ayd.initializationYear = 1 and (ayd.employeeIsvisible = 0 OR ayd.secondLevelIsvisibleCheck = 1) and ayd.acknowledgementCheck = 1"
						+ " and emp.secondLevelSuperiorEmpId =:secondLevelSuperiorEmpId";
			}
			if (appraisalStage.equals(PMSConstants.MID_TERM_REVIEW)
					&& managerType.equals(PMSConstants.SECOND_LEVEL_MANAGER)) {
//				sql = sql + " and ((ayd.initializationYear = 1 and emp.secondLevelSuperiorEmpId =:secondLevelSuperiorEmpId and ayd.secondLevelIsvisibleCheck = 1 ) OR (ayd.midYear = 1  and emp.secondLevelSuperiorEmpId =:secondLevelSuperiorEmpId  and ayd.secondLevelIsvisibleCheck = 0))";
//				11-01-2019 Code Change
				sql = sql + " and ((ayd.midYear = 1  and emp.secondLevelSuperiorEmpId =:secondLevelSuperiorEmpId  and ayd.secondLevelIsvisibleCheck = 0))";
			}
			if (appraisalStage.equals(PMSConstants.YEAR_END_ASSESSMENT)
					&& managerType.equals(PMSConstants.SECOND_LEVEL_MANAGER)) {
//				sql = sql + " and ((ayd.finalYear = 1 and ayd.secondLevelIsvisibleCheck = 0 and emp.secondLevelSuperiorEmpId =:secondLevelSuperiorEmpId ) "
//						+ "  OR (ayd.midYear = 1 and ayd.secondLevelIsvisibleCheck = 1 and emp.secondLevelSuperiorEmpId =:secondLevelSuperiorEmpId))";
//				11-01-2019 Code Change
				sql = sql + " and ((ayd.finalYear = 1 and ayd.secondLevelIsvisibleCheck = 0 and emp.secondLevelSuperiorEmpId =:secondLevelSuperiorEmpId ) "
						+ "  )";
			}
			if (appraisalStage.equals(PMSConstants.ASSESSMENT_APPROVAL)
					&& managerType.equals(PMSConstants.SECOND_LEVEL_MANAGER)) {
				sql = sql
						+ " and ayd.finalYear = 1  and ayd.secondLevelIsvisibleCheck = 1 and emp.secondLevelSuperiorEmpId =:secondLevelSuperiorEmpId";
			}
			Query query = session.createQuery(sql);
			query.setParameter("appraisalYearId", appraisalYearId);
			if (managerType.equals(PMSConstants.FIRST_LEVEL_MANAGER)) {
				query.setParameter("firstLevelSuperiorEmpId", empCode);
			}

			if (managerType.equals(PMSConstants.SECOND_LEVEL_MANAGER)) {
				query.setParameter("secondLevelSuperiorEmpId", empCode);
			}
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			res.setObject(query.list());
			res.setInteger(PMSConstants.STATUS_SUCCESS);
			res.setString("Success");

		} catch (HibernateException e) {
			e.printStackTrace();
			res.setObject(e);
			res.setInteger(PMSConstants.STATUS_FAILED);
			res.setString("Exception while  getAppraisalPendingEmployeeDetails List " + e);
			logger.error("Exception while getAppraisalPendingEmployeeDetails List" + e);
		} finally {
			session.flush();
			session.close();
		}
		return res;

	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getOverAllAppraisalCountDetails(Integer appraisalYearId, String appraisalStage) {
		Session session = this.sessionFactory.openSession();
		List list = new ArrayList();
		try {
			String sql = null;
			if (appraisalStage.equals(PMSConstants.IN_PLANNING)) {
				sql = "select COUNT(CASE WHEN ((ayd.EMPLOYEE_ISVISIBILE = 1 OR ayd.EMPLOYEE_ISVISIBILE = 0) and ayd.INITIALIZATION_YEAR = 1 and ayd.FIRSTLEVEL_ISVISIBILE = 0 and ayd.SECONDLEVEL_ISVISIBILE = 0 and ayd.SECONDLEVEL_ISVISIBILE_CHECK = 0) THEN 1 ELSE NULL END) AS goalSetting ,"
						+ " COUNT(CASE WHEN (ayd.EMPLOYEE_ISVISIBILE = 0 and (ayd.FIRSTLEVEL_ISVISIBILE = 1  OR ayd.SECONDLEVEL_ISVISIBILE = 1) and ayd.ACKNOWLEDGEMENT_CHECK = 1 and ayd.SECONDLEVEL_ISVISIBILE_CHECK = 0 and ayd.INITIALIZATION_YEAR = 1 ) THEN 1 ELSE NULL END) AS goalApproval "
						+ " from appraisal_year_details ayd, employee as emp where  emp.emp_code = ayd.emp_code "
						+ " and emp.STATUS =:STATUS and ayd.APPRAISAL_YEAR_ID =:APPRAISAL_YEAR_ID";
			}
			if (appraisalStage.equals(PMSConstants.IN_REVIEW)) {
//				sql = "select COUNT(CASE WHEN ((ayd.INITIALIZATION_YEAR = 1 and ayd.SECONDLEVEL_ISVISIBILE_CHECK = 1 ) OR (ayd.MID_YEAR = 1 and ayd.SECONDLEVEL_ISVISIBILE_CHECK = 0)) THEN 1 ELSE NULL END) AS inReview "
//						+ " from appraisal_year_details ayd, employee as emp where  emp.emp_code = ayd.emp_code "
//						+ " and emp.STATUS =:STATUS and ayd.APPRAISAL_YEAR_ID =:APPRAISAL_YEAR_ID";
//				11-01-2019 Code Change
				
				sql = "select COUNT(CASE WHEN ((ayd.MID_YEAR = 1 and ayd.SECONDLEVEL_ISVISIBILE_CHECK = 0)) THEN 1 ELSE NULL END) AS inReview "
						+ " from appraisal_year_details ayd, employee as emp where  emp.emp_code = ayd.emp_code "
						+ " and emp.STATUS =:STATUS and ayd.APPRAISAL_YEAR_ID =:APPRAISAL_YEAR_ID";
			}
			if (appraisalStage.equals(PMSConstants.IN_PROCESS)) {
//				sql = "select "
//						+ " COUNT(CASE WHEN ((ayd.FINAL_YEAR = 1 and ayd.SECONDLEVEL_ISVISIBILE_CHECK = 0) "
//						+ "  OR (ayd.MID_YEAR = 1 and ayd.SECONDLEVEL_ISVISIBILE_CHECK = 1))THEN 1 ELSE NULL END)  AS yearEndAssessment ,"
//						+ " COUNT(CASE WHEN (ayd.FINAL_YEAR = 1  and ayd.SECONDLEVEL_ISVISIBILE_CHECK = 1) THEN 1 ELSE NULL END) AS assessmentApproval "
//						11-01-2019 Code Change
				
						sql = "select "
								+ " COUNT(CASE WHEN ((ayd.FINAL_YEAR = 1 and ayd.SECONDLEVEL_ISVISIBILE_CHECK = 0) "
								+ " )THEN 1 ELSE NULL END)  AS yearEndAssessment ,"
								+ " COUNT(CASE WHEN (ayd.FINAL_YEAR = 1  and ayd.SECONDLEVEL_ISVISIBILE_CHECK = 1) THEN 1 ELSE NULL END) AS assessmentApproval "
						
						+ " from appraisal_year_details ayd, employee as emp where  emp.emp_code = ayd.emp_code "
						+ " and emp.STATUS =:STATUS and ayd.APPRAISAL_YEAR_ID =:APPRAISAL_YEAR_ID";
			}
			Query query = session.createSQLQuery(sql);
			query.setParameter("STATUS", PMSConstants.STATUS_ACTIVE);
			query.setParameter("APPRAISAL_YEAR_ID", appraisalYearId);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			list = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			System.out.println(e);
			logger.error("Exception while getting getOverAllAppraisalCountDetails" + e);
		} finally {
			session.flush();
			session.close();
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getOverAllDepartmentAppraisalCountDetails(Integer appraisalYearId, String appraisalStage) {
		Session session = this.sessionFactory.openSession();
		String sql = null;
		List<DepartmentName> departmentIdList = DbUtils.getAllDepartmentIdList();
		List mainList = new ArrayList();
		try {
			for (int i = 0; i < departmentIdList.size(); i++) {
				DepartmentFullAppraisalDetails departmentFullAppraisalDetails = new DepartmentFullAppraisalDetails();
				DepartmentInPlanningAppraisalDetails departmentAppraisalDetails = new DepartmentInPlanningAppraisalDetails();
				DepartmentInReviewAppraisalDetails departmentInReviewAppraisalDetails = new DepartmentInReviewAppraisalDetails();
				DepartmentInProcessAppraisalDetails departmentInProcessAppraisalDetails = new DepartmentInProcessAppraisalDetails();
				if (appraisalStage.equals(PMSConstants.IN_PLANNING)) {
					sql = "select dep.id as id, COUNT(CASE WHEN ((ayd.EMPLOYEE_ISVISIBILE = 1 OR ayd.EMPLOYEE_ISVISIBILE = 0) and ayd.INITIALIZATION_YEAR = 1 and ayd.FIRSTLEVEL_ISVISIBILE = 0 and ayd.SECONDLEVEL_ISVISIBILE = 0 and ayd.SECONDLEVEL_ISVISIBILE_CHECK = 0) THEN 1 ELSE NULL END) AS goalSetting ,"
							+ " COUNT(CASE WHEN (ayd.EMPLOYEE_ISVISIBILE = 0 and ayd.INITIALIZATION_YEAR = 1 and ayd.ACKNOWLEDGEMENT_CHECK = 1 "
							+ "  and (ayd.FIRSTLEVEL_ISVISIBILE = 1 OR ayd.SECONDLEVEL_ISVISIBILE = 1)  and ayd.SECONDLEVEL_ISVISIBILE_CHECK = 0) THEN 1 ELSE NULL END) AS goalApproval "
							+ " from appraisal_year_details as ayd , department as dep, employee as emp where "
							+ " emp.EMP_CODE = ayd.EMP_CODE and emp.DEPARTMENT_ID = dep.ID and dep.ID =:ID "
							+ " and ayd.APPRAISAL_YEAR_ID =:APPRAISAL_YEAR_ID and emp.STATUS =:STATUS and dep.STATUS =:STATUS";
					Query query = session.createSQLQuery(sql).addEntity(DepartmentInPlanningAppraisalDetails.class);
					query.setParameter("STATUS", PMSConstants.STATUS_ACTIVE);
					query.setParameter("ID", departmentIdList.get(i).getId());
					query.setParameter("APPRAISAL_YEAR_ID", appraisalYearId);
					departmentAppraisalDetails = (DepartmentInPlanningAppraisalDetails) query.uniqueResult();
					departmentFullAppraisalDetails.setId(departmentIdList.get(i).getId());
					departmentFullAppraisalDetails.setName(departmentIdList.get(i).getName());
					departmentFullAppraisalDetails.setGoalSetting(departmentAppraisalDetails.getGoalSetting());
					departmentFullAppraisalDetails.setGoalApproval(departmentAppraisalDetails.getGoalApproval());
					mainList.add(departmentFullAppraisalDetails);
				}
				if (appraisalStage.equals(PMSConstants.IN_REVIEW)) {
					sql = "select dep.id as id, COUNT(CASE WHEN ((ayd.INITIALIZATION_YEAR = 1 and ayd.SECONDLEVEL_ISVISIBILE_CHECK = 1 ) OR (ayd.MID_YEAR = 1 and ayd.SECONDLEVEL_ISVISIBILE_CHECK = 0)) THEN 1 ELSE NULL END) AS inReview "
							+ " from appraisal_year_details as ayd , department as dep, employee as emp where "
							+ " emp.EMP_CODE = ayd.EMP_CODE and emp.DEPARTMENT_ID = dep.ID and dep.ID =:ID "
							+ " and ayd.APPRAISAL_YEAR_ID =:APPRAISAL_YEAR_ID and emp.STATUS =:STATUS and dep.STATUS =:STATUS";
					Query query = session.createSQLQuery(sql).addEntity(DepartmentInReviewAppraisalDetails.class);
					query.setParameter("STATUS", PMSConstants.STATUS_ACTIVE);
					query.setParameter("ID", departmentIdList.get(i).getId());
					query.setParameter("APPRAISAL_YEAR_ID", appraisalYearId);
					departmentInReviewAppraisalDetails = (DepartmentInReviewAppraisalDetails) query.uniqueResult();
					departmentFullAppraisalDetails.setId(departmentIdList.get(i).getId());
					departmentFullAppraisalDetails.setName(departmentIdList.get(i).getName());
					departmentFullAppraisalDetails.setInReview(departmentInReviewAppraisalDetails.getInReview());

					mainList.add(departmentFullAppraisalDetails);
				}
				if (appraisalStage.equals(PMSConstants.IN_PROCESS)) {
					
					sql = "select dep.id as id,"
							
							+ " COUNT(CASE WHEN ((ayd.FINAL_YEAR = 1 and ayd.SECONDLEVEL_ISVISIBILE_CHECK = 0) "
							+ "  OR (ayd.MID_YEAR = 1 and ayd.SECONDLEVEL_ISVISIBILE_CHECK = 1))THEN 1 ELSE NULL END)  AS yearEndAssessment ,"
							+ " COUNT(CASE WHEN (ayd.FINAL_YEAR = 1  and ayd.SECONDLEVEL_ISVISIBILE_CHECK = 1) THEN 1 ELSE NULL END) AS assessmentApproval "
							
//							+ " COUNT(CASE WHEN ((ayd.FINAL_YEAR = 1) and ayd.SECONDLEVEL_ISVISIBILE_CHECK = 0) THEN 1 ELSE NULL END) AS yearEndAssessment ,"
//							+ " COUNT(CASE WHEN (ayd.FINAL_YEAR = 1 and  ayd.SECONDLEVEL_ISVISIBILE_CHECK = 1) THEN 1 ELSE NULL END) AS assessmentApproval "
							+ " from appraisal_year_details as ayd , department as dep, employee as emp where "
							+ " emp.EMP_CODE = ayd.EMP_CODE and emp.DEPARTMENT_ID = dep.ID and dep.ID =:ID "
							+ " and ayd.APPRAISAL_YEAR_ID =:APPRAISAL_YEAR_ID and emp.STATUS =:STATUS and dep.STATUS =:STATUS";
					Query query = session.createSQLQuery(sql).addEntity(DepartmentInProcessAppraisalDetails.class);
					query.setParameter("STATUS", PMSConstants.STATUS_ACTIVE);
					query.setParameter("ID", departmentIdList.get(i).getId());
					query.setParameter("APPRAISAL_YEAR_ID", appraisalYearId);
					departmentInProcessAppraisalDetails = (DepartmentInProcessAppraisalDetails) query.uniqueResult();
					departmentFullAppraisalDetails.setId(departmentIdList.get(i).getId());
					departmentFullAppraisalDetails.setName(departmentIdList.get(i).getName());
					departmentFullAppraisalDetails
							.setYearEndAssessment(departmentInProcessAppraisalDetails.getYearEndAssessment());
					departmentFullAppraisalDetails
							.setAssessmentApproval(departmentInProcessAppraisalDetails.getAssessmentApproval());
					mainList.add(departmentFullAppraisalDetails);
				}
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			System.out.println(e);
			logger.error("Exception while getting getOverAllAppraisalCountDetails" + e);
		} finally {
			session.flush();
			session.close();
		}
		return mainList;
	}

	@Override
	public ResponseObject getALLAppraisalPendingEmployeeDetails(Integer appraisalYearId, String type,
			Integer departmentId) {
		Session session = this.sessionFactory.openSession();
		ResponseObject res = new ResponseObject();
		try {
			String sql = "select emp.empName as empName, emp.empCode as empCode, des.name as designationName,dep.name as departmentName, "
					+ " ayd.initializationYear as initializationYear,ayd.midYear as midYear,ayd.finalYear as finalYear,ayd.employeeIsvisible as employeeIsvisible,"
					+ " ayd.firstLevelIsvisible as firstLevelIsvisible,ayd.secondLevelIsvisible as secondLevelIsvisible,ayd.secondLevelIsvisibleCheck as secondLevelIsvisibleCheck, "
					+ " ayd.firstManagerGoalApproval as firstManagerGoalApproval,ayd.firstManagerMidYearApproval as firstManagerMidYearApproval,ayd.firstManagerYearEndAssessmentApproval as firstManagerYearEndAssessmentApproval, "
					+ " ayd.employeeApproval as employeeApproval,ayd.employeeMidYearApproval as employeeMidYearApproval, ayd.acknowledgementCheck as acknowledgementCheck"
					+ " from Employee as emp,Designation as des, Department as dep , AppraisalYearDetails as ayd "
					+ " where emp.designationId = des.id and dep.id = emp.departmentId and ayd.appraisalYearId =:appraisalYearId and ayd.empCode = emp.empCode and emp.status='1' ";
			if (type.equals(PMSConstants.GOAL_SETTING)) {
				sql = sql
						+ " and ayd.initializationYear = 1 and  (ayd.employeeIsvisible = 1 OR ayd.employeeIsvisible = 0)  and ayd.firstLevelIsvisible = 0 and ayd.secondLevelIsvisible = 0 and ayd.secondLevelIsvisibleCheck = 0 ";
			}

			if (type.equals(PMSConstants.GOAL_APPROVAL)) {
				sql = sql
						+ " and ayd.initializationYear = 1 and ayd.employeeIsvisible = 0 and ayd.acknowledgementCheck = 1 and (ayd.firstLevelIsvisible = 1 OR ayd.secondLevelIsvisible = 1)  and ayd.secondLevelIsvisibleCheck = 0 ";
			}
			if (type.equals(PMSConstants.MID_TERM_REVIEW)) {
//				sql = sql + " and  ayd.midYear = 1";
//				sql = sql + " and ((ayd.initializationYear = 1 and ayd.secondLevelIsvisibleCheck = 1 ) OR (ayd.midYear = 1 and ayd.secondLevelIsvisibleCheck = 0))";
//				11-01-2019 Code Change
				sql = sql + " and ((ayd.midYear = 1 and ayd.secondLevelIsvisibleCheck = 0))";
			}
			if (type.equals(PMSConstants.YEAR_END_ASSESSMENT)) {
//				sql = sql + " and (ayd.finalYear = 1 OR ayd.midYear = 1) and ayd.secondLevelIsvisibleCheck = 0 ";
//				sql = sql + " and ((ayd.finalYear = 1 and ayd.secondLevelIsvisibleCheck = 0 ) OR (ayd.midYear = 1 and ayd.secondLevelIsvisibleCheck = 1))";
//				11-01-2019 Code Change
				sql = sql + " and ((ayd.finalYear = 1 and ayd.secondLevelIsvisibleCheck = 0 ))";
			}
			if (type.equals(PMSConstants.ASSESSMENT_APPROVAL)) {
				sql = sql + " and ayd.finalYear = 1  and ayd.secondLevelIsvisibleCheck = 1";
			}
			if (departmentId != null) {
				sql = sql + " and dep.id =:id";
			}
			Query query = session.createQuery(sql);
			if (departmentId != null) {
				query.setParameter("id", departmentId);
			}
			query.setParameter("appraisalYearId", appraisalYearId);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			res.setObject(query.list());
			res.setInteger(PMSConstants.STATUS_SUCCESS);
			res.setString("Success");
		} catch (HibernateException e) {
			e.printStackTrace();
			res.setObject(e);
			res.setInteger(PMSConstants.STATUS_FAILED);
			res.setString("Exception while  getAppraisalPendingEmployeeDetails List " + e);
			logger.error("Exception while getAppraisalPendingEmployeeDetails List" + e);
		} finally {
			session.flush();
			session.close();
		}
		return res;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getAllDepartmentHRDashBoardDetails(Integer appraisalYearId) {
		Session session = this.sessionFactory.openSession();
		List<DepartmentName> departmentIdList = DbUtils.getAllDepartmentIdList();
		List mainList = new ArrayList();
		try {
			for (int i = 0; i < departmentIdList.size(); i++) {
				DepartmentFullAppraisalDetails departmentFullAppraisalDetails = new DepartmentFullAppraisalDetails();
				DepartmentInPlanningAppraisalDetails departmentAppraisalDetails = new DepartmentInPlanningAppraisalDetails();
				String sql = "select dep.id as id, "
						+ " COUNT(CASE WHEN (ayd.EMPLOYEE_ISVISIBILE = 1) THEN 1 ELSE NULL END) AS pendingWithEmployee,"
						+ " COUNT(CASE WHEN ((ayd.FIRSTLEVEL_ISVISIBILE = 1 OR ayd.SECONDLEVEL_ISVISIBILE = 1)) THEN 1 ELSE NULL END) AS pendingWithManager,"
						+ " COUNT(CASE WHEN (ayd.SECONDLEVEL_ISVISIBILE_CHECK = 1 && ayd.EMPLOYEE_ISVISIBILE = 0 && ayd.FIRSTLEVEL_ISVISIBILE = 0  && ayd.SECONDLEVEL_ISVISIBILE = 0 ) THEN 1 ELSE NULL END) AS closed "
						+ " from appraisal_year_details as ayd , department as dep, employee as emp where "
						+ " emp.EMP_CODE = ayd.EMP_CODE and emp.DEPARTMENT_ID = dep.ID and dep.ID =:ID "
						+ " and ayd.APPRAISAL_YEAR_ID =:APPRAISAL_YEAR_ID and emp.STATUS =:STATUS and dep.STATUS =:STATUS";
				Query query = session.createSQLQuery(sql).addEntity(DepartmentInPlanningAppraisalDetails.class);
				query.setParameter("STATUS", PMSConstants.STATUS_ACTIVE);
				query.setParameter("ID", departmentIdList.get(i).getId());
				query.setParameter("APPRAISAL_YEAR_ID", appraisalYearId);
				departmentAppraisalDetails = (DepartmentInPlanningAppraisalDetails) query.uniqueResult();
				departmentFullAppraisalDetails.setId(departmentIdList.get(i).getId());
				departmentFullAppraisalDetails.setName(departmentIdList.get(i).getName());
				// departmentFullAppraisalDetails.setPendingWithEmployee(departmentAppraisalDetails.getPendingWithEmployee());
				// departmentFullAppraisalDetails.setPendingWithManager(departmentAppraisalDetails.getPendingWithManager());
				// departmentFullAppraisalDetails.setClosed(departmentAppraisalDetails.getClosed());
				mainList.add(departmentFullAppraisalDetails);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			logger.error("Exception while getting getOverAllAppraisalCountDetails" + e);
		} finally {
			session.flush();
			session.close();
		}
		return mainList;
	}

	@Override
	public ResponseObject getSelectedAppraisalYearDetails(String appraisalStage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer isUserAcknowledged(String empCode, Integer appraisalYearId) {
		Session session = this.sessionFactory.openSession();
		List<AppraisalYearDetails> list = new ArrayList<AppraisalYearDetails>();
		Integer isAcknowledged = 0;
		try {
			Criteria criteria = session.createCriteria(AppraisalYearDetails.class);
			criteria.add(Restrictions.eq("empCode", empCode));
			criteria.add(Restrictions.eq("appraisalYearId", appraisalYearId));
			criteria.add(Restrictions.eq("acknowledgementCheck", PMSConstants.STATUS_ACTIVE));
			list = criteria.list();
			if (list.size() > 0) {
				isAcknowledged = list.get(0).getAcknowledgementCheck();
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception while getting isUserAcknowledged List... " + e);
		} finally {
			session.flush();
			session.close();
		}
		return isAcknowledged;
	}

	@Override
	public ResponseObject changeEmployeeAppraisalStage(RequestObject res) {
		ResponseObject responseObject = new ResponseObject();
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			AppraisalYearDetails object = (AppraisalYearDetails) session.get(AppraisalYearDetails.class, res.getId());
			if (res.getStage().equals(PMSConstants.GOAL_SETTING)) {
				object.setEmployeeIsvisible(PMSConstants.STATUS_ACTIVE);
				object.setFirstLevelIsvisible(PMSConstants.STATUS_DELETED);
				object.setSecondLevelIsvisible(PMSConstants.STATUS_DELETED);
				object.setSecondLevelIsvisibleCheck(PMSConstants.STATUS_DELETED);
				object.setInitializationYear(PMSConstants.STATUS_ACTIVE);
				object.setMidYear(PMSConstants.STATUS_DELETED);
				object.setFinalYear(PMSConstants.STATUS_DELETED);
				object.setInitializationEndDate(res.getEndDate());
				object.setSecondLevelIsvisibleCheck(PMSConstants.STATUS_DELETED);

				List<EmployeeKRAData> kralist = DbUtils.getValidatedKRADetails(res.getEmpCode(), res.getAppraisalYearId(),
						PMSConstants.USER_ADMIN);
				// List<Integer> behaviouralCompetenceDetailsIdList =
				// DbUtils.getValidatedBehaviouralCompetenceDetails(
				// res.getEmpCode(), res.getAppraisalYearId(),
				// PMSConstants.USER_ADMIN);
				// List<Integer> extraOrdinaryDetailsIdList =
				// DbUtils.getValidatedExtraOrdinaryDetails(res.getEmpCode(),
				// res.getAppraisalYearId(), PMSConstants.USER_ADMIN);
				List<Integer> trainingNeedsDetailsIdList = DbUtils.getValidatedTrainingNeedsDetails(res.getEmpCode(),
						res.getAppraisalYearId(), PMSConstants.USER_ADMIN);

				List<Integer> careerAspirationDetailsIdList = DbUtils.getValidatedCareerAspirationDetails(
						res.getEmpCode(), res.getAppraisalYearId(), PMSConstants.USER_ADMIN);
				for (int i = 0; i < careerAspirationDetailsIdList.size(); i++) {
					CareerAspirationsDetails cad = (CareerAspirationsDetails) session
							.get(CareerAspirationsDetails.class, careerAspirationDetailsIdList.get(i));
					cad.setIsValidatedByEmployee(PMSConstants.STATUS_DELETED);
					cad.setIsValidatedByFirstLevel(PMSConstants.STATUS_DELETED);
					cad.setIsValidatedBySecondLevel(PMSConstants.STATUS_DELETED);
					session.update(cad);
				}

				// List<Integer> careerAspirationDetailsIdList =
				// DbUtils.getValidatedCareerAspirationDetails(
				// empCode, activeAppraisalYearID,
				// PMSConstants.FIRST_LEVEL_MANAGER);
				// for (int i = 0; i < careerAspirationDetailsIdList.size();
				// i++) {
				// CareerAspirationsDetails cad = (CareerAspirationsDetails)
				// session
				// .get(CareerAspirationsDetails.class,
				// careerAspirationDetailsIdList.get(i));
				// cad.setMidYearCommentsStatus(PMSConstants.STATUS_DELETED);
				// cad.setMidYearCommentsStatusFirstLevel(PMSConstants.STATUS_DELETED);
				// session.update(cad);
				// }

				// for (int i = 0; i < extraOrdinaryDetailsIdList.size(); i++) {
				// ExtraOrdinaryDetails ex = (ExtraOrdinaryDetails)
				// session.get(ExtraOrdinaryDetails.class,
				// extraOrdinaryDetailsIdList.get(i));
				// ex.setIsValidatedByEmployee(PMSConstants.STATUS_DELETED);
				// ex.setIsValidatedByFirstLevel(PMSConstants.STATUS_DELETED);
				// ex.setIsValidatedBySecondLevel(PMSConstants.STATUS_DELETED);
				// session.update(ex);
				// }
				// for (int i = 0; i <
				// behaviouralCompetenceDetailsIdList.size(); i++) {
				// BehaviouralCompetenceDetails bcd =
				// (BehaviouralCompetenceDetails) session
				// .get(BehaviouralCompetenceDetails.class,
				// behaviouralCompetenceDetailsIdList.get(i));
				// bcd.setIsValidatedByEmployee(PMSConstants.STATUS_DELETED);
				// bcd.setIsValidatedByFirstLevel(PMSConstants.STATUS_DELETED);
				// bcd.setIsValidatedBySecondLevel(PMSConstants.STATUS_DELETED);
				// session.update(bcd);
				// }
				for (int i = 0; i < kralist.size(); i++) {
					KraDetails kra = (KraDetails) session.get(KraDetails.class, kralist.get(i).getId());
					kra.setIsValidatedByEmployee(PMSConstants.STATUS_DELETED);
					kra.setIsValidatedByFirstLevel(PMSConstants.STATUS_DELETED);
					kra.setIsValidatedBySecondLevel(PMSConstants.STATUS_DELETED);
					kra.setMidYearHighlights(PMSConstants.STATUS_DELETED);
					kra.setFinalYearHighlights(PMSConstants.STATUS_DELETED);
					session.update(kra);
				}
				for (int i = 0; i < trainingNeedsDetailsIdList.size(); i++) {
					TrainingNeedsDetails tnd = (TrainingNeedsDetails) session.get(TrainingNeedsDetails.class,
							trainingNeedsDetailsIdList.get(i));
					tnd.setIsValidatedByEmployee(PMSConstants.STATUS_DELETED);
					tnd.setIsValidatedByFirstLevel(PMSConstants.STATUS_DELETED);
					tnd.setIsValidatedBySecondLevel(PMSConstants.STATUS_DELETED);
					session.update(tnd);
				}

			}

			if (res.getStage().equals(PMSConstants.GOAL_APPROVAL)) {
				object.setEmployeeIsvisible(PMSConstants.STATUS_DELETED);
				object.setFirstLevelIsvisible(PMSConstants.STATUS_ACTIVE);
				object.setSecondLevelIsvisible(PMSConstants.STATUS_DELETED);
				object.setSecondLevelIsvisibleCheck(PMSConstants.STATUS_DELETED);
				object.setInitializationYear(PMSConstants.STATUS_ACTIVE);
				object.setMidYear(PMSConstants.STATUS_DELETED);
				object.setFinalYear(PMSConstants.STATUS_DELETED);
				object.setInitializationEndDate(res.getEndDate());
				
				List<EmployeeKRAData> kralist = DbUtils.getValidatedKRADetails(res.getEmpCode(), res.getAppraisalYearId(),
						PMSConstants.USER_ADMIN);
				// List<Integer> behaviouralCompetenceDetailsIdList =
				// DbUtils.getValidatedBehaviouralCompetenceDetails(
				// res.getEmpCode(), res.getAppraisalYearId(),
				// PMSConstants.USER_ADMIN);
				// List<Integer> extraOrdinaryDetailsIdList =
				// DbUtils.getValidatedExtraOrdinaryDetails(res.getEmpCode(),
				// res.getAppraisalYearId(), PMSConstants.USER_ADMIN);
				List<Integer> trainingNeedsDetailsIdList = DbUtils.getValidatedTrainingNeedsDetails(res.getEmpCode(),
						res.getAppraisalYearId(), PMSConstants.USER_ADMIN);

				List<Integer> careerAspirationDetailsIdList = DbUtils.getValidatedCareerAspirationDetails(
						res.getEmpCode(), res.getAppraisalYearId(), PMSConstants.USER_ADMIN);
				for (int i = 0; i < careerAspirationDetailsIdList.size(); i++) {
					CareerAspirationsDetails cad = (CareerAspirationsDetails) session
							.get(CareerAspirationsDetails.class, careerAspirationDetailsIdList.get(i));
					cad.setIsValidatedByEmployee(PMSConstants.STATUS_ACTIVE);
					cad.setIsValidatedByFirstLevel(PMSConstants.STATUS_DELETED);
					cad.setIsValidatedBySecondLevel(PMSConstants.STATUS_DELETED);
					session.update(cad);
				}

				// List<Integer> careerAspirationDetailsIdList =
				// DbUtils.getValidatedCareerAspirationDetails(
				// empCode, activeAppraisalYearID,
				// PMSConstants.FIRST_LEVEL_MANAGER);
				// for (int i = 0; i < careerAspirationDetailsIdList.size();
				// i++) {
				// CareerAspirationsDetails cad = (CareerAspirationsDetails)
				// session
				// .get(CareerAspirationsDetails.class,
				// careerAspirationDetailsIdList.get(i));
				// cad.setMidYearCommentsStatus(PMSConstants.STATUS_DELETED);
				// cad.setMidYearCommentsStatusFirstLevel(PMSConstants.STATUS_DELETED);
				// session.update(cad);
				// }

				// for (int i = 0; i < extraOrdinaryDetailsIdList.size(); i++) {
				// ExtraOrdinaryDetails ex = (ExtraOrdinaryDetails)
				// session.get(ExtraOrdinaryDetails.class,
				// extraOrdinaryDetailsIdList.get(i));
				// ex.setIsValidatedByEmployee(PMSConstants.STATUS_ACTIVE);
				// ex.setIsValidatedByFirstLevel(PMSConstants.STATUS_DELETED);
				// ex.setIsValidatedBySecondLevel(PMSConstants.STATUS_DELETED);
				// session.update(ex);
				// }
				// for (int i = 0; i <
				// behaviouralCompetenceDetailsIdList.size(); i++) {
				// BehaviouralCompetenceDetails bcd =
				// (BehaviouralCompetenceDetails) session
				// .get(BehaviouralCompetenceDetails.class,
				// behaviouralCompetenceDetailsIdList.get(i));
				// bcd.setIsValidatedByEmployee(PMSConstants.STATUS_ACTIVE);
				// bcd.setIsValidatedByFirstLevel(PMSConstants.STATUS_DELETED);
				// bcd.setIsValidatedBySecondLevel(PMSConstants.STATUS_DELETED);
				// session.update(bcd);
				// }
				for (int i = 0; i < kralist.size(); i++) {
					KraDetails kra = (KraDetails) session.get(KraDetails.class, kralist.get(i).getId());
					kra.setIsValidatedByEmployee(PMSConstants.STATUS_ACTIVE);
					kra.setIsValidatedByFirstLevel(PMSConstants.STATUS_DELETED);
					kra.setIsValidatedBySecondLevel(PMSConstants.STATUS_DELETED);
					kra.setMidYearHighlights(PMSConstants.STATUS_DELETED);
					kra.setFinalYearHighlights(PMSConstants.STATUS_DELETED);
					session.update(kra);
				}
				for (int i = 0; i < trainingNeedsDetailsIdList.size(); i++) {
					TrainingNeedsDetails tnd = (TrainingNeedsDetails) session.get(TrainingNeedsDetails.class,
							trainingNeedsDetailsIdList.get(i));
					tnd.setIsValidatedByEmployee(PMSConstants.STATUS_ACTIVE);
					tnd.setIsValidatedByFirstLevel(PMSConstants.STATUS_DELETED);
					tnd.setIsValidatedBySecondLevel(PMSConstants.STATUS_DELETED);
					session.update(tnd);
				}
			}
			if (res.getStage().equals(PMSConstants.MID_TERM_REVIEW)) {
				object.setEmployeeIsvisible(PMSConstants.STATUS_ACTIVE);
				object.setFirstLevelIsvisible(PMSConstants.STATUS_DELETED);
				object.setSecondLevelIsvisible(PMSConstants.STATUS_DELETED);
				object.setSecondLevelIsvisibleCheck(PMSConstants.STATUS_DELETED);
				object.setInitializationYear(PMSConstants.STATUS_DELETED);
				object.setMidYear(PMSConstants.STATUS_ACTIVE);
				object.setFinalYear(PMSConstants.STATUS_DELETED);
				object.setMidYearEndDate(res.getEndDate());
				object.setEmployeeMidYearApproval(PMSConstants.STATUS_DELETED);
				List<EmployeeKRAData> kralist = DbUtils.getValidatedKRADetails(res.getEmpCode(), res.getAppraisalYearId(),
						PMSConstants.USER_ADMIN);
				List<Integer> behaviouralCompetenceDetailsIdList = DbUtils.getValidatedBehaviouralCompetenceDetails(
						res.getEmpCode(), res.getAppraisalYearId(), PMSConstants.USER_ADMIN);
				// List<Integer> extraOrdinaryDetailsIdList =
				// DbUtils.getValidatedExtraOrdinaryDetails(res.getEmpCode(),
				// res.getAppraisalYearId(), PMSConstants.USER_ADMIN);
				List<Integer> trainingNeedsDetailsIdList = DbUtils.getValidatedTrainingNeedsDetails(res.getEmpCode(),
						res.getAppraisalYearId(), PMSConstants.USER_ADMIN);

				List<Integer> careerAspirationDetailsIdList = DbUtils.getValidatedCareerAspirationDetails(
						res.getEmpCode(), res.getAppraisalYearId(), PMSConstants.USER_ADMIN);
				for (int i = 0; i < careerAspirationDetailsIdList.size(); i++) {
					CareerAspirationsDetails cad = (CareerAspirationsDetails) session
							.get(CareerAspirationsDetails.class, careerAspirationDetailsIdList.get(i));
					cad.setIsValidatedByEmployee(PMSConstants.STATUS_ACTIVE);
					cad.setIsValidatedByFirstLevel(PMSConstants.STATUS_ACTIVE);
					cad.setIsValidatedBySecondLevel(PMSConstants.STATUS_ACTIVE);
					cad.setMidYearCommentsStatus(PMSConstants.STATUS_DELETED);
					cad.setMidYearCommentsStatusFirstLevel(PMSConstants.STATUS_DELETED);
					cad.setMidYearCommentsStatusSecondLevel(PMSConstants.STATUS_DELETED);
					session.update(cad);
				}

				// List<Integer> careerAspirationDetailsIdList =
				// DbUtils.getValidatedCareerAspirationDetails(
				// empCode, activeAppraisalYearID,
				// PMSConstants.FIRST_LEVEL_MANAGER);
				// for (int i = 0; i < careerAspirationDetailsIdList.size();
				// i++) {
				// CareerAspirationsDetails cad = (CareerAspirationsDetails)
				// session
				// .get(CareerAspirationsDetails.class,
				// careerAspirationDetailsIdList.get(i));
				// cad.setMidYearCommentsStatus(PMSConstants.STATUS_DELETED);
				// cad.setMidYearCommentsStatusFirstLevel(PMSConstants.STATUS_DELETED);
				// session.update(cad);
				// }

				// for (int i = 0; i < extraOrdinaryDetailsIdList.size(); i++) {
				// ExtraOrdinaryDetails ex = (ExtraOrdinaryDetails)
				// session.get(ExtraOrdinaryDetails.class,
				// extraOrdinaryDetailsIdList.get(i));
				// ex.setIsValidatedByEmployee(PMSConstants.STATUS_DELETED);
				// ex.setIsValidatedByFirstLevel(PMSConstants.STATUS_DELETED);
				// ex.setIsValidatedBySecondLevel(PMSConstants.STATUS_DELETED);
				// session.update(ex);
				// }
				for (int i = 0; i < behaviouralCompetenceDetailsIdList.size(); i++) {
					BehaviouralCompetenceDetails bcd = (BehaviouralCompetenceDetails) session
							.get(BehaviouralCompetenceDetails.class, behaviouralCompetenceDetailsIdList.get(i));
					bcd.setIsValidatedByEmployee(PMSConstants.STATUS_DELETED);
					bcd.setIsValidatedByFirstLevel(PMSConstants.STATUS_DELETED);
					bcd.setIsValidatedBySecondLevel(PMSConstants.STATUS_DELETED);
					session.update(bcd);
				}
				for (int i = 0; i < kralist.size(); i++) {
					KraDetails kra = (KraDetails) session.get(KraDetails.class, kralist.get(i).getId());
					kra.setIsValidatedByEmployee(PMSConstants.STATUS_DELETED);
					kra.setIsValidatedByFirstLevel(PMSConstants.STATUS_DELETED);
					kra.setIsValidatedBySecondLevel(PMSConstants.STATUS_DELETED);
					kra.setMidYearHighlights(PMSConstants.STATUS_DELETED);
					kra.setFinalYearHighlights(PMSConstants.STATUS_DELETED);
					session.update(kra);
				}
				for (int i = 0; i < trainingNeedsDetailsIdList.size(); i++) {
					TrainingNeedsDetails tnd = (TrainingNeedsDetails) session.get(TrainingNeedsDetails.class,
							trainingNeedsDetailsIdList.get(i));
					tnd.setIsValidatedByEmployee(PMSConstants.STATUS_DELETED);
					tnd.setIsValidatedByFirstLevel(PMSConstants.STATUS_DELETED);
					tnd.setIsValidatedBySecondLevel(PMSConstants.STATUS_DELETED);
					session.update(tnd);
				}

			}
			if (res.getStage().equals(PMSConstants.YEAR_END_ASSESSMENT)) {
				object.setEmployeeIsvisible(PMSConstants.STATUS_ACTIVE);
				object.setFirstLevelIsvisible(PMSConstants.STATUS_DELETED);
				object.setSecondLevelIsvisible(PMSConstants.STATUS_DELETED);
				object.setSecondLevelIsvisibleCheck(PMSConstants.STATUS_DELETED);
				object.setInitializationYear(PMSConstants.STATUS_DELETED);
				object.setMidYear(PMSConstants.STATUS_DELETED);
				object.setFinalYear(PMSConstants.STATUS_ACTIVE);
				object.setFinalYearEndDate(res.getEndDate());

				List<EmployeeKRAData> kralist = DbUtils.getValidatedKRADetails(res.getEmpCode(), res.getAppraisalYearId(),
						PMSConstants.USER_ADMIN);
				List<Integer> behaviouralCompetenceDetailsIdList = DbUtils.getValidatedBehaviouralCompetenceDetails(
						res.getEmpCode(), res.getAppraisalYearId(), PMSConstants.USER_ADMIN);
				List<Integer> extraOrdinaryDetailsIdList = DbUtils.getValidatedExtraOrdinaryDetails(res.getEmpCode(),
						res.getAppraisalYearId(), PMSConstants.USER_ADMIN);
						// List<Integer> trainingNeedsDetailsIdList =
						// DbUtils.getValidatedTrainingNeedsDetails(res.getEmpCode(),
						// res.getAppraisalYearId(), PMSConstants.USER_ADMIN);
						//
						// List<Integer> careerAspirationDetailsIdList =
						// DbUtils.getValidatedCareerAspirationDetails(
						// res.getEmpCode(), res.getAppraisalYearId(),
						// PMSConstants.USER_ADMIN);
						// for (int i = 0; i <
						// careerAspirationDetailsIdList.size(); i++) {
						// CareerAspirationsDetails cad =
						// (CareerAspirationsDetails) session
						// .get(CareerAspirationsDetails.class,
						// careerAspirationDetailsIdList.get(i));
						// cad.setIsValidatedByEmployee(PMSConstants.STATUS_DELETED);
						// cad.setIsValidatedByFirstLevel(PMSConstants.STATUS_DELETED);
						// cad.setIsValidatedBySecondLevel(PMSConstants.STATUS_DELETED);
						// session.update(cad);
						// }
						// for (int i = 0; i <
						// trainingNeedsDetailsIdList.size(); i++) {
						// TrainingNeedsDetails tnd = (TrainingNeedsDetails)
						// session.get(TrainingNeedsDetails.class,
						// trainingNeedsDetailsIdList.get(i));
						// tnd.setIsValidatedByEmployee(PMSConstants.STATUS_DELETED);
						// tnd.setIsValidatedByFirstLevel(PMSConstants.STATUS_DELETED);
						// tnd.setIsValidatedBySecondLevel(PMSConstants.STATUS_DELETED);
						// session.update(tnd);
						// }

				// List<Integer> careerAspirationDetailsIdList =
				// DbUtils.getValidatedCareerAspirationDetails(
				// empCode, activeAppraisalYearID,
				// PMSConstants.FIRST_LEVEL_MANAGER);
				// for (int i = 0; i < careerAspirationDetailsIdList.size();
				// i++) {
				// CareerAspirationsDetails cad = (CareerAspirationsDetails)
				// session
				// .get(CareerAspirationsDetails.class,
				// careerAspirationDetailsIdList.get(i));
				// cad.setMidYearCommentsStatus(PMSConstants.STATUS_DELETED);
				// cad.setMidYearCommentsStatusFirstLevel(PMSConstants.STATUS_DELETED);
				// session.update(cad);
				// }

				List<Integer> careerAspirationDetailsIdList = DbUtils.getValidatedCareerAspirationDetails(
						res.getEmpCode(), res.getAppraisalYearId(), PMSConstants.USER_ADMIN);
				for (int i = 0; i < careerAspirationDetailsIdList.size(); i++) {
					CareerAspirationsDetails cad = (CareerAspirationsDetails) session
							.get(CareerAspirationsDetails.class, careerAspirationDetailsIdList.get(i));
					cad.setIsValidatedByEmployee(PMSConstants.STATUS_ACTIVE);
					cad.setIsValidatedByFirstLevel(PMSConstants.STATUS_ACTIVE);
					cad.setIsValidatedBySecondLevel(PMSConstants.STATUS_ACTIVE);
					cad.setYearEndCommentsStatus(PMSConstants.STATUS_DELETED);
					cad.setYearEndCommentsStatusFirstLevel(PMSConstants.STATUS_DELETED);
					cad.setYearEndCommentsStatusSecondLevel(PMSConstants.STATUS_DELETED);
					cad.setMidYearCommentsStatus(PMSConstants.STATUS_ACTIVE);
					cad.setMidYearCommentsStatusFirstLevel(PMSConstants.STATUS_ACTIVE);
					cad.setMidYearCommentsStatusSecondLevel(PMSConstants.STATUS_ACTIVE);
					session.update(cad);
				}

				for (int i = 0; i < extraOrdinaryDetailsIdList.size(); i++) {
					ExtraOrdinaryDetails ex = (ExtraOrdinaryDetails) session.get(ExtraOrdinaryDetails.class,
							extraOrdinaryDetailsIdList.get(i));
					ex.setIsValidatedByEmployee(PMSConstants.STATUS_DELETED);
					ex.setIsValidatedByFirstLevel(PMSConstants.STATUS_DELETED);
					ex.setIsValidatedBySecondLevel(PMSConstants.STATUS_DELETED);
					session.update(ex);
				}
				for (int i = 0; i < behaviouralCompetenceDetailsIdList.size(); i++) {
					BehaviouralCompetenceDetails bcd = (BehaviouralCompetenceDetails) session
							.get(BehaviouralCompetenceDetails.class, behaviouralCompetenceDetailsIdList.get(i));
					bcd.setIsValidatedByEmployee(PMSConstants.STATUS_DELETED);
					bcd.setIsValidatedByFirstLevel(PMSConstants.STATUS_DELETED);
					bcd.setIsValidatedBySecondLevel(PMSConstants.STATUS_DELETED);
					session.update(bcd);
				}
				for (int i = 0; i < kralist.size(); i++) {
					KraDetails kra = (KraDetails) session.get(KraDetails.class, kralist.get(i).getId());
					kra.setIsValidatedByEmployee(PMSConstants.STATUS_DELETED);
					kra.setIsValidatedByFirstLevel(PMSConstants.STATUS_DELETED);
					kra.setIsValidatedBySecondLevel(PMSConstants.STATUS_DELETED);
					kra.setFinalYearHighlights(PMSConstants.STATUS_DELETED);
					session.update(kra);
				}

			}
			if (res.getStage().equals(PMSConstants.ASSESSMENT_APPROVAL)) {
				object.setEmployeeIsvisible(PMSConstants.STATUS_DELETED);
				object.setFirstLevelIsvisible(PMSConstants.STATUS_DELETED);
				object.setSecondLevelIsvisible(PMSConstants.STATUS_DELETED);
				object.setSecondLevelIsvisibleCheck(PMSConstants.STATUS_ACTIVE);
				object.setInitializationYear(PMSConstants.STATUS_DELETED);
				object.setMidYear(PMSConstants.STATUS_DELETED);
				object.setFinalYear(PMSConstants.STATUS_ACTIVE);
				object.setFinalYearEndDate(res.getEndDate());

				List<EmployeeKRAData> kralist = DbUtils.getValidatedKRADetails(res.getEmpCode(), res.getAppraisalYearId(),
						PMSConstants.USER_ADMIN);
				List<Integer> behaviouralCompetenceDetailsIdList = DbUtils.getValidatedBehaviouralCompetenceDetails(
						res.getEmpCode(), res.getAppraisalYearId(), PMSConstants.USER_ADMIN);
				List<Integer> extraOrdinaryDetailsIdList = DbUtils.getValidatedExtraOrdinaryDetails(res.getEmpCode(),
						res.getAppraisalYearId(), PMSConstants.USER_ADMIN);
				List<Integer> trainingNeedsDetailsIdList = DbUtils.getValidatedTrainingNeedsDetails(res.getEmpCode(),
						res.getAppraisalYearId(), PMSConstants.USER_ADMIN);
				List<Integer> careerAspirationDetailsIdList = DbUtils.getValidatedCareerAspirationDetails(
						res.getEmpCode(), res.getAppraisalYearId(), PMSConstants.USER_ADMIN);
				for (int i = 0; i < careerAspirationDetailsIdList.size(); i++) {
					CareerAspirationsDetails cad = (CareerAspirationsDetails) session
							.get(CareerAspirationsDetails.class, careerAspirationDetailsIdList.get(i));
					cad.setIsValidatedByEmployee(PMSConstants.STATUS_ACTIVE);
					cad.setIsValidatedByFirstLevel(PMSConstants.STATUS_ACTIVE);
					cad.setIsValidatedBySecondLevel(PMSConstants.STATUS_ACTIVE);
					cad.setYearEndCommentsStatus(PMSConstants.STATUS_ACTIVE);
					cad.setYearEndCommentsStatusFirstLevel(PMSConstants.STATUS_ACTIVE);
					cad.setYearEndCommentsStatusSecondLevel(PMSConstants.STATUS_ACTIVE);
					cad.setMidYearCommentsStatus(PMSConstants.STATUS_ACTIVE);
					cad.setMidYearCommentsStatusFirstLevel(PMSConstants.STATUS_ACTIVE);
					cad.setMidYearCommentsStatusSecondLevel(PMSConstants.STATUS_ACTIVE);
					session.update(cad);
				}

				// List<Integer> careerAspirationDetailsIdList =
				// DbUtils.getValidatedCareerAspirationDetails(
				// empCode, activeAppraisalYearID,
				// PMSConstants.FIRST_LEVEL_MANAGER);
				// for (int i = 0; i < careerAspirationDetailsIdList.size();
				// i++) {
				// CareerAspirationsDetails cad = (CareerAspirationsDetails)
				// session
				// .get(CareerAspirationsDetails.class,
				// careerAspirationDetailsIdList.get(i));
				// cad.setMidYearCommentsStatus(PMSConstants.STATUS_DELETED);
				// cad.setMidYearCommentsStatusFirstLevel(PMSConstants.STATUS_DELETED);
				// session.update(cad);
				// }

				for (int i = 0; i < extraOrdinaryDetailsIdList.size(); i++) {
					ExtraOrdinaryDetails ex = (ExtraOrdinaryDetails) session.get(ExtraOrdinaryDetails.class,
							extraOrdinaryDetailsIdList.get(i));
					ex.setIsValidatedByEmployee(PMSConstants.STATUS_ACTIVE);
					ex.setIsValidatedByFirstLevel(PMSConstants.STATUS_ACTIVE);
					ex.setIsValidatedBySecondLevel(PMSConstants.STATUS_ACTIVE);
					session.update(ex);
				}
				for (int i = 0; i < behaviouralCompetenceDetailsIdList.size(); i++) {
					BehaviouralCompetenceDetails bcd = (BehaviouralCompetenceDetails) session
							.get(BehaviouralCompetenceDetails.class, behaviouralCompetenceDetailsIdList.get(i));
					bcd.setIsValidatedByEmployee(PMSConstants.STATUS_ACTIVE);
					bcd.setIsValidatedByFirstLevel(PMSConstants.STATUS_ACTIVE);
					bcd.setIsValidatedBySecondLevel(PMSConstants.STATUS_ACTIVE);
					session.update(bcd);
				}
				for (int i = 0; i < kralist.size(); i++) {
					KraDetails kra = (KraDetails) session.get(KraDetails.class, kralist.get(i).getId());
					kra.setIsValidatedByEmployee(PMSConstants.STATUS_ACTIVE);
					kra.setIsValidatedByFirstLevel(PMSConstants.STATUS_ACTIVE);
					kra.setIsValidatedBySecondLevel(PMSConstants.STATUS_ACTIVE);
					session.update(kra);
				}
				for (int i = 0; i < trainingNeedsDetailsIdList.size(); i++) {
					TrainingNeedsDetails tnd = (TrainingNeedsDetails) session.get(TrainingNeedsDetails.class,
							trainingNeedsDetailsIdList.get(i));
					tnd.setIsValidatedByEmployee(PMSConstants.STATUS_ACTIVE);
					tnd.setIsValidatedByFirstLevel(PMSConstants.STATUS_ACTIVE);
					tnd.setIsValidatedBySecondLevel(PMSConstants.STATUS_ACTIVE);
					session.update(tnd);
				}

			}
			session.update(object);
			tx.commit();
			responseObject.setInteger(PMSConstants.STATUS_SUCCESS);
			responseObject.setString("Successfully saved");
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			logger.error("Exception while changeEmployeeAppraisalStage ... " + e);
			responseObject.setInteger(PMSConstants.STATUS_FAILED);
			responseObject.setString("Exception while changeEmployeeAppraisalStage ... " + e);
		} finally {
			session.flush();
			session.close();
		}
		return responseObject;
	}

	@Override
	public ResponseObject carryForwardEmployeeKRADATAToCurrentYear(RequestObject res) {
		ResponseObject responseObject = new ResponseObject();
		Session session = sessionFactory.openSession();
		boolean checkTypeId = false;
		Transaction tx = session.beginTransaction();
		try {
			AppraisalYearDetails object = (AppraisalYearDetails) session.get(AppraisalYearDetails.class, res.getId());
			if (res.getTypeId() == 1) {
				object.setKraForwardCheck(PMSConstants.STATUS_ACTIVE);
				checkTypeId = true;
			}
			if (res.getTypeId() == 2) {
				object.setKraForwardCheck(PMSConstants.STATUS_DELETED);
			}
			session.update(object);
			tx.commit();
			if (checkTypeId) {
				responseObject = DbUtils.forwardEmployeeKRADATAToCurrentYear(res.getEmpCode(),
						res.getAppraisalYearId());
			}
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			logger.error("Exception while carryForwardEmployeeKRADATAToCurrentYear ... " + e);
			responseObject.setInteger(PMSConstants.STATUS_FAILED);
			responseObject.setString("Exception while carryForwardEmployeeKRADATAToCurrentYear ... " + e);
			responseObject.setObject(e);
		} finally {
			session.flush();
			session.close();
		}
		return responseObject;
	}

	@Override
	public ResponseObject employeeApprovalProcess(RequestObject res) {
		ResponseObject responseObject = new ResponseObject();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			AppraisalYearDetails object = (AppraisalYearDetails) session.get(AppraisalYearDetails.class, res.getId());
			if(res.getType().equals(PMSConstants.APPRAISAL_MID_YEAR))
			{		
				object.setEmployeeMidYearApproval(PMSConstants.STATUS_ACTIVE);
			}
			if(res.getType().equals(PMSConstants.APPRAISAL_FINAL_YEAR))
			{
//				OverAllCalculation overAllCalculation = new OverAllCalculation();
//				overAllCalculation.setAppraisalYearId(res.getAppraisalYearId());
//				overAllCalculation.setEmpCode(res.getEmpCode());
//				overAllCalculation.setKraTotalCalculation(res.getKraTotalCalculation());
//				overAllCalculation.setBehaviouralCompetenceTotalCalculation(res.getBehaviouralCompetenceTotalCalculation());
//				overAllCalculation.setExtraOrdinaryTotalCalculation(res.getExtraOrdinaryTotalCalculation());
//				overAllCalculation.setCreatedBy(res.getEmpCode());
//				overAllCalculation.setCreatedOn(new Date());
//				session.save(overAllCalculation);
				
				object.setEmployeeApproval(PMSConstants.STATUS_ACTIVE);
			}	
			session.update(object);
			tx.commit();
			if(res.getType().equals(PMSConstants.APPRAISAL_MID_YEAR))
			{
				responseObject = DbUtils.getUserAppraisalDetails(res.getEmpCode(), res.getAppraisalYearId(),"EMPLOYEE");
				responseObject.setString("Mid year acknowledge");
			}
			if(res.getType().equals(PMSConstants.APPRAISAL_FINAL_YEAR))
			{
				responseObject = DbUtils.getUserAppraisalDetails(res.getEmpCode(), res.getAppraisalYearId(),"EMPLOYEE");
				responseObject.setString("Year year acknowledge");	
			}
			
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			logger.error("Exception while employeeApprovalProcess ... " + e);
			responseObject.setInteger(PMSConstants.STATUS_FAILED);
			responseObject.setString("Exception while employeeApprovalProcess ... " + e);
			responseObject.setObject(e);
		} finally {
			session.flush();
			session.close();
		}
		return responseObject;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseObject teamRating(RequestObject object) {
		Session session = this.sessionFactory.openSession();
		ResponseObject res = new ResponseObject();
		List<EmployeeAppraisalDetails> employeeAppraisalDetails = new ArrayList<EmployeeAppraisalDetails>();
		List<EmployeeKRADetails> employeeKRADetails = new ArrayList<EmployeeKRADetails>();
		List<EmployeeBehaviouralCompetenceDetails> employeeBehaviouralCompetenceDetails = new ArrayList<EmployeeBehaviouralCompetenceDetails>();
		List<EmployeeExtraOrdinaryDetails> employeeExtraOrdinaryDetails = new ArrayList<EmployeeExtraOrdinaryDetails>();
		List<EmployeeOverAllCalculationDetails> employeeOverAllCalculationDetails = new ArrayList<EmployeeOverAllCalculationDetails>();
		boolean userCheck = CommonUtils.isCurrentUserManager();
		logger.info("<---------teamRating DAOImpl--------->");
		try {
			String sql ="select ay.NAME as sessionYear,emp.ID as empId,emp.STATUS as status,emp.MOBILE as mobile,emp.EMAIL as email,emp.EMP_CODE as empCode,emp.EMP_NAME as empName, emp.LOCATION as location,emp.DATE_OF_JOINING as dateOfJoining, "
					+ " ayd.APPRAISAL_YEAR_ID as appraisalYearId,dep.NAME as departmentName,desig.NAME as designationName,"
					+ " ayd.INITIALIZATION_YEAR as initializationYear,ayd.MID_YEAR as midYear,ayd.FINAL_YEAR as finalYear,"
					+ " ayd.SECONDLEVEL_ISVISIBILE_CHECK as secondLevelIsvisibleCheck,ayd.EMPLOYEE_APPROVAL as employeeApproval,"
					+ " ayd.EMPLOYEE_MID_YEAR_APPROVAL as employeeMidYearApproval,ayd.ACKNOWLEDGEMENT_CHECK as acknowledgementCheck, "
					+ " ayd.EMPLOYEE_ISVISIBILE as employeeIsvisible,ayd.FIRSTLEVEL_ISVISIBILE as firstLevelIsvisible,ayd.SECONDLEVEL_ISVISIBILE as secondLevelIsvisible "
					+ " from employee as emp,appraisal_year_details as ayd ,appraisal_year as ay,department as dep,designation as desig"
					+ " where ayd.APPRAISAL_YEAR_ID =:APPRAISAL_YEAR_ID and ayd.EMP_CODE = emp.EMP_CODE "
					+ " and dep.ID = emp.DEPARTMENT_ID and desig.ID = DESIGNATION_ID and ay.ID = ayd.APPRAISAL_YEAR_ID and emp.STATUS ='1'";
			if(userCheck)
			{
				sql = sql + " and  (emp.FIRST_LEVEL_SUPERIOR_EMP_ID =:FIRST_LEVEL_SUPERIOR_EMP_ID OR emp.SECOND_LEVEL_SUPERIOR_EMP_ID =:FIRST_LEVEL_SUPERIOR_EMP_ID)";
			}
			Query query = session.createSQLQuery(sql).addEntity(EmployeeAppraisalDetails.class);
			if(userCheck)
			{
			query.setParameter("FIRST_LEVEL_SUPERIOR_EMP_ID", object.getEmpCode());
//			query.setParameter("SECOND_LEVEL_SUPERIOR_EMP_ID", object.getEmpCode());
			}
			query.setParameter("APPRAISAL_YEAR_ID", object.getAppraisalYearId());
			employeeAppraisalDetails = query.list();	
			
			logger.info("<---------employeeAppraisalDetails--------->");
			
			for(int i=0;i<employeeAppraisalDetails.size();i++)
			{
				EmployeeOverAllCalculationDetails obj = new EmployeeOverAllCalculationDetails();
				obj.setEmpCode(employeeAppraisalDetails.get(i).getEmpCode());
				obj.setMobile(employeeAppraisalDetails.get(i).getMobile());
				obj.setEmail(employeeAppraisalDetails.get(i).getEmail());
				obj.setInitializationYear(employeeAppraisalDetails.get(i).getInitializationYear());
				obj.setMidYear(employeeAppraisalDetails.get(i).getMidYear());
				obj.setFinalYear(employeeAppraisalDetails.get(i).getFinalYear());
				obj.setEmployeeApproval(employeeAppraisalDetails.get(i).getEmployeeApproval());
				obj.setEmployeeMidYearApproval(employeeAppraisalDetails.get(i).getEmployeeMidYearApproval());
				obj.setEmpName(employeeAppraisalDetails.get(i).getEmpName());
				obj.setLocation(employeeAppraisalDetails.get(i).getLocation());
				obj.setDepartmentName(employeeAppraisalDetails.get(i).getDepartmentName());
				obj.setDesignationName(employeeAppraisalDetails.get(i).getDesignationName());
				obj.setDateOfJoining(employeeAppraisalDetails.get(i).getDateOfJoining());
				obj.setEmployeeIsvisible(employeeAppraisalDetails.get(i).getEmployeeIsvisible());
				obj.setSessionYear(employeeAppraisalDetails.get(i).getSessionYear());
				if(employeeAppraisalDetails.get(i).getEmployeeIsvisible() == 1)
				{
					obj.setPendingWithManager(DbUtils.getEmployeeName(employeeAppraisalDetails.get(i).getEmpCode()));
				}
				if(employeeAppraisalDetails.get(i).getFirstLevelIsvisible() ==1)
				{
					obj.setPendingWithManager(DbUtils.getEmployeeName(DbUtils.getFirstLevelManagerId(employeeAppraisalDetails.get(i).getEmpCode())));
				}
				if(employeeAppraisalDetails.get(i).getSecondLevelIsvisible() == 1)
				{
					obj.setPendingWithManager(DbUtils.getEmployeeName(DbUtils.getSecondLevelManagerId(employeeAppraisalDetails.get(i).getEmpCode())));
				}
				if(employeeAppraisalDetails.get(i).getSecondLevelIsvisibleCheck() == 1)
				{
					obj.setPendingWithManager(DbUtils.getEmployeeName(employeeAppraisalDetails.get(i).getEmpCode()));	
				}
				if(employeeAppraisalDetails.get(i).getInitializationYear() == 1 && employeeAppraisalDetails.get(i).getEmployeeIsvisible() == 0 
						&& employeeAppraisalDetails.get(i).getFirstLevelIsvisible() == 0 
						&& employeeAppraisalDetails.get(i).getSecondLevelIsvisible() == 0 && employeeAppraisalDetails.get(i).getSecondLevelIsvisibleCheck() == 0)
				{
					obj.setPendingWithManager(DbUtils.getEmployeeName(employeeAppraisalDetails.get(i).getEmpCode()));
				}
				obj.setFirstLevelIsvisible(employeeAppraisalDetails.get(i).getFirstLevelIsvisible());
				obj.setSecondLevelIsvisible(employeeAppraisalDetails.get(i).getSecondLevelIsvisible());
				obj.setSecondLevelIsvisibleCheck(employeeAppraisalDetails.get(i).getSecondLevelIsvisibleCheck());
				obj.setAcknowledgementCheck(employeeAppraisalDetails.get(i).getAcknowledgementCheck());
//				System.out.println("\n employeeAppraisalDetails----------->"+ employeeAppraisalDetails.get(i));
				employeeKRADetails = DbUtils.getEmployeeKRADetails(employeeAppraisalDetails.get(i).getEmpCode(),employeeAppraisalDetails.get(i).getAppraisalYearId());
				sectionWeightage = 0;
				calculationAverage = 0;
				for(int j=0;j<employeeKRADetails.size();j++)
				{
					System.out.println("\n obj----------->"+ obj);
					System.out.println("\n employeeKRADetails----------->"+ employeeKRADetails);
					if(employeeKRADetails.get(j).getWeightage()!=null && employeeKRADetails.get(j).getWeightage().length() >= 1)
					{
						sectionWeightage = sectionWeightage + Integer.parseInt(employeeKRADetails.get(j).getWeightage());
						if(employeeKRADetails.get(j).getMidYearAppraisarRating() != null && employeeKRADetails.get(j).getWeightage() !=null && employeeKRADetails.get(j).getFinalYearAppraisarRating() !=null)
						{
							calculationAverage = calculationAverage + ((((Double.parseDouble(employeeKRADetails.get(j).getWeightage()) * employeeKRADetails.get(j).getMidYearAppraisarRating())/100)  * 30) / 100)
									+ (((Double.parseDouble(employeeKRADetails.get(j).getWeightage()) * employeeKRADetails.get(j).getFinalYearAppraisarRating())/100) * 70 / 100);
						}
						if(employeeKRADetails.get(j).getMidYearAppraisarRating() == null && employeeKRADetails.get(j).getWeightage() !=null && employeeKRADetails.get(j).getFinalYearAppraisarRating() !=null)
						{
							calculationAverage = calculationAverage + ((Double.parseDouble(employeeKRADetails.get(j).getWeightage()) * employeeKRADetails.get(j).getFinalYearAppraisarRating())/100);
						}
					}
				}
				obj.setKra(calculationAverage);
				obj.setKraWeightage(sectionWeightage);
				obj.setKraCalculation((calculationAverage * 75) /100);
				
				employeeBehaviouralCompetenceDetails = DbUtils.getEmployeeBehaviouralCompetenceDetails(employeeAppraisalDetails.get(i).getEmpCode(),employeeAppraisalDetails.get(i).getAppraisalYearId());
				sectionWeightage = 0;
				calculationAverage = 0;
				for(int j=0;j<employeeBehaviouralCompetenceDetails.size();j++)
				{
					if(employeeBehaviouralCompetenceDetails.get(j).getWeightage()!=null && employeeBehaviouralCompetenceDetails.get(j).getWeightage().length() >=1)
					{
						sectionWeightage = sectionWeightage + Integer.parseInt(employeeBehaviouralCompetenceDetails.get(j).getWeightage());
						if(employeeBehaviouralCompetenceDetails.get(j).getMidYearAssessorRating() != null && employeeBehaviouralCompetenceDetails.get(j).getWeightage() !=null && employeeBehaviouralCompetenceDetails.get(j).getFinalYearAssessorRating() !=null)
						{
							calculationAverage = calculationAverage + (((Double.parseDouble(employeeBehaviouralCompetenceDetails.get(j).getWeightage()) * employeeBehaviouralCompetenceDetails.get(j).getMidYearAssessorRating())/100)  * 30 / 100)
									+ (((Double.parseDouble(employeeBehaviouralCompetenceDetails.get(j).getWeightage()) * employeeBehaviouralCompetenceDetails.get(j).getFinalYearAssessorRating())/100) * 70 / 100);
						}
						if(employeeBehaviouralCompetenceDetails.get(j).getMidYearAssessorRating() == null && employeeBehaviouralCompetenceDetails.get(j).getWeightage() !=null && employeeBehaviouralCompetenceDetails.get(j).getFinalYearAssessorRating() !=null)
						{
							calculationAverage = calculationAverage + (((Double.parseDouble(employeeBehaviouralCompetenceDetails.get(j).getWeightage()) * employeeBehaviouralCompetenceDetails.get(j).getFinalYearAssessorRating())/100));
						}
					}
				}
				obj.setBehaviouralCompetenceDetails(calculationAverage);
				obj.setBehaviouralCompetenceDetailsWeightage(sectionWeightage);
				obj.setBehaviouralCompetenceDetailsCalculation((calculationAverage * 15)/100);
//				System.out.println("\n employeeBehaviouralCompetenceDetails----------->"+ employeeBehaviouralCompetenceDetails);
				employeeExtraOrdinaryDetails = DbUtils.getEmployeeExtraOrdinaryDetails(employeeAppraisalDetails.get(i).getEmpCode(),employeeAppraisalDetails.get(i).getAppraisalYearId());
				sectionWeightage = 0;
				calculationAverage = 0;
				for(int j=0;j<employeeExtraOrdinaryDetails.size();j++)
				{
					if(employeeExtraOrdinaryDetails.get(j).getWeightage()!=null)
					{
						sectionWeightage = sectionWeightage + Integer.parseInt(employeeExtraOrdinaryDetails.get(j).getWeightage());
						if(employeeExtraOrdinaryDetails.get(j).getFinalYearAppraisarRating() != null && employeeExtraOrdinaryDetails.get(j).getWeightage() !=null)
						{
							calculationAverage = calculationAverage 
									+ ((Double.parseDouble(employeeExtraOrdinaryDetails.get(j).getWeightage()) * employeeExtraOrdinaryDetails.get(j).getFinalYearAppraisarRating())*10/100);
						}
					}
				}
				obj.setExtraOrdinary(calculationAverage);
				obj.setExtraOrdinaryCalculation((calculationAverage*10)/100);
				obj.setExtraOrdinaryWeightage(sectionWeightage);
				obj.setFinalRating(obj.getKraCalculation() + obj.getBehaviouralCompetenceDetailsCalculation() + obj.getExtraOrdinaryCalculation());
				employeeOverAllCalculationDetails.add(obj);
//				System.out.println("\n  employeeExtraOrdinaryDetails----------->"+ employeeExtraOrdinaryDetails);
			}
			res.setObject(employeeOverAllCalculationDetails);
			res.setInteger(PMSConstants.STATUS_SUCCESS);
			res.setString("Success");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			res.setObject(e);
			res.setInteger(PMSConstants.STATUS_FAILED);
			res.setString("Exception while getting teamRating List " + e);
			logger.error("Exception while getting teamRating List" + e);
		} finally {
			session.flush();
			session.close();
		}
		return res;
	}

}
