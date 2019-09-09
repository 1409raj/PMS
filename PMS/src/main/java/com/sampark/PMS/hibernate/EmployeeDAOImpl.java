package com.sampark.PMS.hibernate;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TimeZone;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sampark.PMS.PMSConstants;
import com.sampark.PMS.dto.BehaviouralCompetenceDetails;
import com.sampark.PMS.dto.CareerAspirationsDetails;
import com.sampark.PMS.dto.Department;
import com.sampark.PMS.dto.Employee;
import com.sampark.PMS.dto.EmployeeBasicDetails;
import com.sampark.PMS.dto.EmployeeCodeEmail;
import com.sampark.PMS.dto.EmployeeEmails;
import com.sampark.PMS.dto.EmployeeKRADetails;
import com.sampark.PMS.dto.FinalReviewDetails;
import com.sampark.PMS.dto.KraDetails;
import com.sampark.PMS.dto.TrainingNeeds;
import com.sampark.PMS.dto.Users;
import com.sampark.PMS.object.ResponseObject;
import com.sampark.PMS.util.CommonUtils;
import com.sampark.PMS.util.DbUtils;
import com.sampark.PMS.util.RandomPassword;

public class EmployeeDAOImpl implements EmployeeDAO {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeDAOImpl.class);
	private static ResourceBundle messages = ResourceBundle.getBundle("messages");
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public ResponseObject addEmployee(Employee object, Integer empId, String encryptedPassword,
			String userRandomPassword) {
		ResponseObject responseObject = new ResponseObject();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			if (empId == null) {
				if (isEmpCodeEmailExist(object.getEmpCode())) {
					object.setStatus(PMSConstants.STATUS_ACTIVE);
					object.setCreatedBy(CommonUtils.getCurrentUserName());
					object.setCreatedOn(new Date());
					Users user = new Users();
					user.setEnabled(PMSConstants.STATUS_ACTIVE);
					// For FirstTime Password Change
					user.setFirstCheck(PMSConstants.STATUS_PENDING);
					user.setPassword(encryptedPassword);
					user.setRoleId(object.getApplicationRoleId());
					user.setUserName(object.getEmpCode());
					user.setCreatedOn(new Date());
					user.setCreatedBy(CommonUtils.getCurrentUserName());

					EmployeeEmails empEmail = new EmployeeEmails();
					// empEmail.setEmpCode(object.getEmpCode());
					empEmail.setSendTo(object.getEmail());
					empEmail.setSendFrom(PMSConstants.SEND_FORM);
					empEmail.setAppraisalYearId(CommonUtils.getActiveAppraisalYearId());
					empEmail.setEmailType(PMSConstants.NEW_EMPLOYEE);
					empEmail.setCreatedOn(new Date());
					empEmail.setEmployeePassword(userRandomPassword);
					empEmail.setEmployeeName(object.getEmpName());
					empEmail.setStatus(PMSConstants.STATUS_PENDING);
					empEmail.setEmailSubject(messages.getString("email.new.employee.subject"));
					empEmail.setEmailContent("Dear " + object.getEmpName() + "<br><br>"
							+ "Your Performance management system account is ready." + "<br><br>"
//							+ "Your Account details" + "<br>" + messages.getString("email.new.employee.text")
							+ "Your Account details" + "<br>"+ "Username:" + object.getEmpCode() +"<br>" + messages.getString("email.new.employee.text")
							+ userRandomPassword + "<br>"
							+ "URL <a href='https://pms-hfe.com/PMS/login'>Click here to login PMS tool</a>"
							+ "<br><br>"
							+ "We encourage you to use the system on a regular basis which will help you perform better and have better interaction with your manager."
							+ "<br><br>" + "Best Regards" + "<br>" + "Team HR");

					session.persist(empEmail);
					session.persist(user);
					session.persist(object);
					responseObject.setInteger(PMSConstants.STATUS_SUCCESS);
					responseObject.setString("Successfully saved");
				} else {
					responseObject.setInteger(PMSConstants.STATUS_FAILED);
					responseObject.setString("User already Exists ... ");
				}
			} else {
				Employee emp = new Employee();
				emp = (Employee) session.get(Employee.class, empId);
				emp.setRoleId(object.getRoleId());
				emp.setEmpName(object.getEmpName());
				emp.setEmail(object.getEmail());
				emp.setJobDescription(object.getJobDescription());
				emp.setDepartmentId(object.getDepartmentId());
				emp.setOrganizationRoleId(object.getOrganizationRoleId());
				emp.setApplicationRoleId(object.getApplicationRoleId());
				emp.setDesignationId(object.getDesignationId());
				emp.setQualification(object.getQualification());
				emp.setMobile(object.getMobile());
				emp.setLocation(object.getLocation());
				emp.setCompany(object.getCompany());
				emp.setDateOfBirth(object.getDateOfBirth());
				emp.setFirstLevelSuperiorEmpId(object.getFirstLevelSuperiorEmpId());
				emp.setSecondLevelSuperiorEmpId(object.getSecondLevelSuperiorEmpId());
				// emp.setFirstLevelSuperiorName(object.getFirstLevelSuperiorName());
				// emp.setSecondLevelSuperiorName(object.getFirstLevelSuperiorName());
				emp.setDocument(object.getDocument());
				emp.setField1(object.getField1());
				emp.setField2(object.getField2());
				emp.setField3(object.getField3());
				emp.setField4(object.getField4());
				emp.setField5(object.getField5());
				emp.setField6(object.getField6());
				emp.setField7(object.getField7());
				emp.setField8(object.getField8());
				emp.setField9(object.getField9());
				emp.setField10(object.getField10());
				emp.setModifiedOn(new Date());
				emp.setModifiedBy(CommonUtils.getCurrentUserName());
				Users user = new Users();
				user = (Users) session.get(Users.class, CommonUtils.getCurrentUserId(emp.getEmpCode()));
				user.setRoleId(object.getApplicationRoleId());
				user.setModifiedBy(CommonUtils.getCurrentUserName());
				user.setModifiedOn(new Date());
				session.update(emp);
				session.update(user);
				responseObject.setInteger(PMSConstants.STATUS_SUCCESS);
				responseObject.setString("Successfully updated");
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			responseObject.setInteger(PMSConstants.STATUS_FAILED);
			responseObject.setString("Exception while adding new Employee ... ");
			logger.error("Exception while adding new User [" + object.getEmpName() + "]... " + e);
		} finally {
			session.flush();
			session.close();
		}

		return responseObject;
	}

	// private boolean addLoginDetails(Employee object, Users user) {
	// Session session = sessionFactory.openSession();
	// Transaction tx = session.beginTransaction();
	// boolean isLoginDetailsAdded = false;
	// try {
	// tx.begin();
	// session.persist(user);
	// session.persist(object);
	// tx.commit();
	// isLoginDetailsAdded = true;
	// } catch (HibernateException e) {
	// if (tx != null)
	// tx.rollback();
	// e.printStackTrace();
	// logger.error("Exception while adding new User [" + user.getUserName() +
	// "]... " + e);
	// } finally {
	// session.close();
	// }
	// return isLoginDetailsAdded;
	// }

	@SuppressWarnings("unchecked")
	private boolean isEmpCodeEmailExist(String empCode) {
		Session session = this.sessionFactory.openSession();
		List<Employee> list = new ArrayList<Employee>();
		Boolean employeeExist = true;
		try {
			Criteria criteria = session.createCriteria(Employee.class);
			criteria.add(Restrictions.eq("empCode", empCode));
			list = criteria.list();
			if (list.size() > 0) {
				employeeExist = false;
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception while isEmpCodeEmailExist... " + e);
		} finally {
			session.flush();
			session.close();
		}
		return employeeExist;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getAllEmployeeCodeList() {
		Session session = this.sessionFactory.openSession();
		List list = new ArrayList();
		try {
			String sql = "select concat(empCode,' - ', empName) AS text, empCode as id from Employee where status =:status and roleId =:roleId";
			Query query = session.createQuery(sql);
			query.setParameter("status", PMSConstants.STATUS_ACTIVE);
			query.setParameter("roleId", 3);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			list = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception while getting All EmployeeCode List" + e);
		} finally {
			session.flush();
			session.close();
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResponseObject getEmployeeAppraisalDetails(Integer appraisalYearId, String empCode) {
		Session session = this.sessionFactory.openSession();
		ResponseObject res = new ResponseObject();
		List list = new ArrayList();
		try {
			String sql = " select (select emp1.empName from Employee as emp1 where emp1.empCode = emp.firstLevelSuperiorEmpId) as firstLevelSuperiorName, "
					+ " (select emp2.empName from Employee as emp2 where emp2.empCode = emp.secondLevelSuperiorEmpId) as secondLevelSuperiorName, "
					+ " emp.mobile as mobile, emp.company as company,emp.dateOfBirth as dateOfBirth, emp.jobDescription as jobDescription, emp.id as id, emp.empCode as empCode ,"
					+ " emp.empName as empName,emp.email as email,emp.dateOfJoining as dateOfJoining ,"
					+ " emp.location as location, "
					+ " dep.name as departmentName, des.name as designationName, emp.qualification as qualificationName"
					+ " from Employee as emp,Department as dep,Designation as des"
					+ " where emp.empCode =:empCode and emp.departmentId = dep.id"
					+ " and emp.designationId = des.id and emp.status =:status";
			Query query = session.createQuery(sql);
			query.setParameter("empCode", empCode);
			query.setParameter("status", PMSConstants.STATUS_ACTIVE);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			list.add(query.uniqueResult());
			// list.add(getEmployeeKreDetails(empCode,appraisalYearId));
			// list.add(getEmployeeBehaviouralCompetenceDetails(empCode,appraisalYearId));
			// list.add(getEmployeeCareerAspirationsDetails(empCode,appraisalYearId));
			// list.add(getEmployeeTrainingNeedsDetails(empCode,appraisalYearId));
			// list.add(getEmployeeFinalReviewDetails(empCode,appraisalYearId));
			res.setObject(list);
			res.setInteger(PMSConstants.STATUS_SUCCESS);
			res.setString("Success");
		} catch (HibernateException e) {
			e.printStackTrace();
			res.setObject(e);
			res.setInteger(PMSConstants.STATUS_FAILED);
			res.setString("Exception while getting SubEmployee Details");
			logger.error("Exception while getting SubEmployee Details" + e);
		} finally {
			session.flush();
			session.close();
		}
		return res;
	}

	@SuppressWarnings("unused")
	private List<FinalReviewDetails> getEmployeeFinalReviewDetails(String empCode, Integer appraisalYearId) {
		Session session = this.sessionFactory.openSession();
		List<FinalReviewDetails> list = new ArrayList<FinalReviewDetails>();
		try {
			// Calendar lowCal = Calendar.getInstance();
			// lowCal.set(Calendar.DAY_OF_YEAR,
			// lowCal.getActualMinimum(Calendar.DAY_OF_YEAR));
			// Date lowDate = lowCal.getTime();
			// Calendar highCal = Calendar.getInstance();
			// lowCal.set(Calendar.DAY_OF_YEAR,
			// lowCal.getActualMaximum(Calendar.DAY_OF_YEAR));
			// Date highDate = highCal.getTime();
			// criteria.add(Restrictions.between("appraisalYear", lowDate,
			// highDate));
			Criteria criteria = session.createCriteria(FinalReviewDetails.class);
			criteria.add(Restrictions.eq("appraisalYearId", appraisalYearId));
			criteria.add(Restrictions.eq("empCode", empCode));
			list = (List<FinalReviewDetails>) criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception while getting SubEmployee Final Review Details" + e);
		} finally {
			session.flush();
			session.close();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	private List<TrainingNeeds> getEmployeeTrainingNeedsDetails(String empCode, Integer appraisalYearId) {
		Session session = this.sessionFactory.openSession();
		List<TrainingNeeds> list = new ArrayList<TrainingNeeds>();
		try {
			// Calendar lowCal = Calendar.getInstance();
			// lowCal.set(Calendar.DAY_OF_YEAR,
			// lowCal.getActualMinimum(Calendar.DAY_OF_YEAR));
			// Date lowDate = lowCal.getTime();
			// Calendar highCal = Calendar.getInstance();
			// lowCal.set(Calendar.DAY_OF_YEAR,
			// lowCal.getActualMaximum(Calendar.DAY_OF_YEAR));
			// Date highDate = highCal.getTime();
			// criteria.add(Restrictions.between("appraisalYear", lowDate,
			// highDate));
			Criteria criteria = session.createCriteria(TrainingNeeds.class);
			criteria.add(Restrictions.eq("appraisalYearId", appraisalYearId));
			criteria.add(Restrictions.eq("empCode", empCode));
			list = (List<TrainingNeeds>) criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception while getting SubEmployee Training Needs Details" + e);
		} finally {
			session.flush();
			session.close();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	private List<CareerAspirationsDetails> getEmployeeCareerAspirationsDetails(String empCode,
			Integer appraisalYearId) {
		Session session = this.sessionFactory.openSession();
		List<CareerAspirationsDetails> list = new ArrayList<CareerAspirationsDetails>();
		try {
			// Calendar lowCal = Calendar.getInstance();
			// lowCal.set(Calendar.DAY_OF_YEAR,
			// lowCal.getActualMinimum(Calendar.DAY_OF_YEAR));
			// Date lowDate = lowCal.getTime();
			// Calendar highCal = Calendar.getInstance();
			// lowCal.set(Calendar.DAY_OF_YEAR,
			// lowCal.getActualMaximum(Calendar.DAY_OF_YEAR));
			// Date highDate = highCal.getTime();
			// criteria.add(Restrictions.between("appraisalYear", lowDate,
			// highDate));
			Criteria criteria = session.createCriteria(CareerAspirationsDetails.class);
			criteria.add(Restrictions.eq("appraisalYearId", appraisalYearId));
			criteria.add(Restrictions.eq("empCode", empCode));
			list = (List<CareerAspirationsDetails>) criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception while getting SubEmployee Career Aspiration Details" + e);
		} finally {
			session.flush();
			session.close();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	private List<KraDetails> getEmployeeKreDetails(String empCode, Integer appraisalYearId) {
		Session session = this.sessionFactory.openSession();
		List<KraDetails> list = new ArrayList<KraDetails>();
		try {
			// Calendar lowCal = Calendar.getInstance();
			// lowCal.set(Calendar.DAY_OF_YEAR,
			// lowCal.getActualMinimum(Calendar.DAY_OF_YEAR));
			// Date lowDate = lowCal.getTime();
			// Calendar highCal = Calendar.getInstance();
			// lowCal.set(Calendar.DAY_OF_YEAR,
			// lowCal.getActualMaximum(Calendar.DAY_OF_YEAR));
			// Date highDate = highCal.getTime();
			// criteria.add(Restrictions.between("appraisalYear", lowDate,
			// highDate));
			Criteria criteria = session.createCriteria(KraDetails.class);
			criteria.add(Restrictions.eq("appraisalYearId", appraisalYearId));
			criteria.add(Restrictions.eq("empCode", empCode));
			list = (List<KraDetails>) criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception while getting SubEmployee KRA Details" + e);
		} finally {
			session.flush();
			session.close();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	private List<BehaviouralCompetenceDetails> getEmployeeBehaviouralCompetenceDetails(String empCode,
			Integer appraisalYearId) {
		Session session = this.sessionFactory.openSession();
		List<BehaviouralCompetenceDetails> list = new ArrayList<BehaviouralCompetenceDetails>();
		try {
			// Calendar lowCal = Calendar.getInstance();
			// lowCal.set(Calendar.DAY_OF_YEAR,
			// lowCal.getActualMinimum(Calendar.DAY_OF_YEAR));
			// Date lowDate = lowCal.getTime();
			// Calendar highCal = Calendar.getInstance();
			// lowCal.set(Calendar.DAY_OF_YEAR,
			// lowCal.getActualMaximum(Calendar.DAY_OF_YEAR));
			// Date highDate = highCal.getTime();
			// criteria.add(Restrictions.between("appraisalYear", lowDate,
			// highDate));
			Criteria criteria = session.createCriteria(BehaviouralCompetenceDetails.class);
			criteria.add(Restrictions.eq("appraisalYearId", appraisalYearId));
			criteria.add(Restrictions.eq("empCode", empCode));
			list = (List<BehaviouralCompetenceDetails>) criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception while getting SubEmployee Behavioural Competence Details" + e);
		} finally {
			session.flush();
			session.close();
		}
		return list;
	}

	@Override
	public ResponseObject getAllEmployeeList() {
		Session session = this.sessionFactory.openSession();
		ResponseObject res = new ResponseObject();
		try {
			String sql = "select (select emp1.empName from Employee as emp1 where emp1.empCode = emp.firstLevelSuperiorEmpId) as firstLevelSuperiorName,"
					+ " (select emp2.empName from Employee as emp2 where emp2.empCode = emp.secondLevelSuperiorEmpId) as secondLevelSuperiorName,"
					+ " emp.id as id,orgR.name as organizationRole,rol.name as applicationRole, emp.email as email,emp.location as location,emp.mobile as mobile, emp.empName as empName,emp.empCode as empCode,emp.status as status,"
					+ " dep.name as departmentName, des.name as designationName"
					+ " from Employee as emp,Department as dep,Designation as des,OrganizationRoles as orgR,Roles as rol where emp.departmentId = dep.id and orgR.id = emp.organizationRoleId and rol.id = emp.roleId and  emp.status =:status"
					+ " and emp.designationId = des.id";
			Query query = session.createQuery(sql);
			// and emp.status =:status
			// query.setParameter("status", PMSConstants.STATUS_ACTIVE);
			// query.setParameter("appraisalYearId", appraisalYearId);
			query.setParameter("status", PMSConstants.STATUS_ACTIVE);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			res.setObject(query.list());
			res.setInteger(PMSConstants.STATUS_SUCCESS);
			res.setString("Success");
		} catch (HibernateException e) {
			e.printStackTrace();
			res.setObject(e);
			res.setInteger(PMSConstants.STATUS_FAILED);
			res.setString("Exception while getting All Employee List " + e);
			logger.error("Exception while getting All Employee List" + e);
		} finally {
			session.flush();
			session.close();
		}
		return res;
	}

	@Override
	public ResponseObject deleteEmployee(Integer id) {
		ResponseObject responseObject = new ResponseObject();
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Employee object = (Employee) session.get(Employee.class, id);
			object.setStatus(PMSConstants.STATUS_DELETED);
			session.update(object);
			tx.commit();
			responseObject.setInteger(PMSConstants.STATUS_SUCCESS);
			responseObject.setObject(getAllEmployeeList().getObject());
			responseObject.setString("Successfully delete");
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			responseObject.setInteger(PMSConstants.STATUS_FAILED);
			responseObject.setString("Exception while deleting Employee ... " + e);
			responseObject.setObject(e);
			logger.error("Exception while deleting Employee. " + e);
		} finally {
			session.flush();
			session.close();
		}
		return responseObject;
	}

	@Override
	public ResponseObject getCurrentEmployeeDetails(Integer empId) {
		Session session = this.sessionFactory.openSession();
		ResponseObject responseObject = new ResponseObject();
		try {
			String sql = "select (select emp1.empName from Employee as emp1 where emp1.empCode = emp.firstLevelSuperiorEmpId) as firstLevelSuperiorName,"
					+ " (select emp2.empName from Employee as emp2 where emp2.empCode = emp.secondLevelSuperiorEmpId) as secondLevelSuperiorName,emp.empCode as empCode ,emp.firstLevelSuperiorEmpId as firstLevelSuperiorEmpId,"
					+ " emp.secondLevelSuperiorEmpId as secondLevelSuperiorEmpId, "
					+ " emp.empName as empName,emp.company as company,emp.dateOfBirth as dateOfBirth,emp.empType as empType, emp.mobile as mobile,emp.document as document, emp.email as email,emp.dateOfJoining as dateOfJoining ,"
					+ " emp.location as location,orgr.name as organizationalRoleName,emp.jobDescription as jobDescription,"
					+ " emp.field1 as field1,emp.field2 as field2,emp.field3 as field3,emp.field4 as field4,emp.field5 as field5,emp.field6 as field6,"
					+ " emp.field7 as field7,emp.field8 as field8,emp.field9 as field9,emp.field10 as field10, "
					+ " dep.name as departmentName, des.name as designationName, emp.qualification as qualificationName,rl.name as roleName"
					+ " from Employee as emp,Department as dep,Designation as des,Roles as rl,"
					+ " OrganizationRoles as orgr"
					+ " where  emp.departmentId = dep.id and orgr.id = emp.organizationRoleId and rl.id = emp.roleId"
					+ " and emp.designationId = des.id and emp.status =:status and emp.id =:id";
			Query query = session.createQuery(sql);
			query.setParameter("id", empId);
			query.setParameter("status", PMSConstants.STATUS_ACTIVE);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			responseObject.setInteger(PMSConstants.STATUS_SUCCESS);
			responseObject.setObject(query.uniqueResult());
			responseObject.setString("Successfully delete");
		} catch (HibernateException e) {
			e.printStackTrace();
			responseObject.setInteger(PMSConstants.STATUS_FAILED);
			responseObject.setString("Exception while getting current Employee details... " + e);
			responseObject.setObject(e);
			logger.error("Exception while getting current Employee details... " + e);
		} finally {
			session.flush();
			session.close();
		}
		return responseObject;
	}

	@Override
	public List<EmployeeCodeEmail> getAllEmployeeCode(Date eligibilityDate, Integer appraisalYearId) {
		Session session = this.sessionFactory.openSession();
		List<EmployeeCodeEmail> list = new ArrayList<EmployeeCodeEmail>();
		try {
			// emp.EMP_CODE !=:EMP_CODE and
			String sql = "select emp.id as id,emp.EMP_NAME as empName, emp.EMP_CODE as empCode, emp.email as email from employee as emp where emp.EMP_CODE NOT IN "
					+ "(select EMP_CODE from appraisal_year_details where APPRAISAL_YEAR_ID =:APPRAISAL_YEAR_ID) and emp.EMP_CODE !='DEFAULT_CODE' and emp.DATE_OF_JOINING <=:DATE_OF_JOINING and emp.STATUS =:STATUS";
			Query query = session.createSQLQuery(sql).addEntity(EmployeeCodeEmail.class);
			query.setParameter("STATUS", PMSConstants.STATUS_ACTIVE);
			// query.setParameter("EMP_CODE", "DEFAULT_CODE");
			query.setParameter("DATE_OF_JOINING", eligibilityDate);
			query.setParameter("APPRAISAL_YEAR_ID", appraisalYearId);
			list = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			System.out.println(e);
			logger.error("Exception while getting All EmployeeCode List" + e);
		} finally {
			session.flush();
			session.close();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getAllEmployeeListId(Integer appraisalYearId,Date eligibilityDate) {
		Session session = this.sessionFactory.openSession();
		List<Integer> list = new ArrayList<Integer>();
		try {
			String sql = "select ayd.id from AppraisalYearDetails as ayd,Employee as emp where ayd.status =:status and ayd.appraisalYearId=:appraisalYearId and "
					+ " emp.dateOfJoining <=:dateOfJoining and emp.empCode = ayd.empCode";
			Query query = session.createQuery(sql);
			query.setParameter("status", PMSConstants.STATUS_ACTIVE);
			query.setParameter("appraisalYearId", appraisalYearId);
			query.setParameter("dateOfJoining", eligibilityDate);
			list = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			System.out.println(e);
			logger.error("Exception while getting All AppraisalYearDetails ID List" + e);
		} finally {
			session.flush();
			session.close();
		}
		return list;
	}

	@Override
	public ResponseObject getAllSubEmployeeList(String empCode, String type, Integer appraisalYearId) {
		Session session = this.sessionFactory.openSession();
		ResponseObject res = new ResponseObject();
		try {
			String sql = null;
			if (type.equals(PMSConstants.FIRST_LEVEL_EMPLOYEE)) {
				sql = "select emp.empCode as empCode,emp.empName as empName,concat(emp.empCode,' - ', emp.empName) AS empCodeWithName "
						+ " from Employee as emp where "
						+ " emp.status =:status and emp.firstLevelSuperiorEmpId =:firstLevelSuperiorEmpId ";
			}
			if (type.equals(PMSConstants.SECOND_LEVEL_EMPLOYEE)) {
				sql = " select emp.empCode as empCode,emp.empName as empName, concat(emp.empCode,' - ', emp.empName) AS empCodeWithName  "
						+ " from Employee as emp, EmployeePromotions as empPro where emp.empCode = empPro.empCode and "
						+ " emp.status =:status and empPro.secondLevelManagerId =:secondLevelManagerId "
						+ " and empPro.appraisalYearId =:appraisalYearId and empPro.status =:status";
			}
			if (type.equals(PMSConstants.USER_ADMIN)) {
				sql = " select emp.empCode as empCode,emp.empName as empName,concat(emp.empCode,' - ', emp.empName) AS empCodeWithName  "
						+ " from Employee as emp, EmployeePromotions as empPro where emp.empCode = empPro.empCode and "
						+ " emp.status =:status and empPro.secondLevelCheck =:secondLevelCheck "
						+ " and empPro.appraisalYearId =:appraisalYearId";
			}
			Query query = session.createQuery(sql);
			query.setParameter("status", PMSConstants.STATUS_ACTIVE);
			if (type.equals(PMSConstants.FIRST_LEVEL_EMPLOYEE)) {
				query.setParameter("firstLevelSuperiorEmpId", empCode);
			}
			if (type.equals(PMSConstants.SECOND_LEVEL_EMPLOYEE)) {
				query.setParameter("secondLevelManagerId", empCode);
				query.setParameter("appraisalYearId", appraisalYearId);
			}
			if (type.equals(PMSConstants.USER_ADMIN)) {
				query.setParameter("secondLevelCheck", PMSConstants.STATUS_ACTIVE);
				query.setParameter("appraisalYearId", appraisalYearId);
			}
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			res.setObject(query.list());
			res.setInteger(PMSConstants.STATUS_SUCCESS);
			res.setString("Success");
		} catch (HibernateException e) {
			e.printStackTrace();
			res.setObject(e);
			res.setInteger(PMSConstants.STATUS_FAILED);
			res.setString("Exception while getting All Sub Employee List " + e);
			logger.error("Exception while getting All Sub Employee List" + e);
		} finally {
			session.flush();
			session.close();
		}
		return res;
	}

	@Override
	public Employee getCurrentEmployeeData(String empCode) {
		Session session = this.sessionFactory.openSession();
		List<Employee> list = new ArrayList<Employee>();
		try {
			Criteria criteria = session.createCriteria(Employee.class);
			criteria.add(Restrictions.eq("status", PMSConstants.STATUS_ACTIVE));
			criteria.add(Restrictions.eq("empCode", empCode));
			list = criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception while getting getCurrentEmployeeData List... " + e);
		} finally {
			session.flush();
			session.close();
		}
		return list.get(0);
	}

	@Override
	public void addEmployeeBulk(List<Employee> object, HttpServletResponse response) throws IOException {
		Iterator<Employee> it = object.iterator();
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		PrintWriter pWriter = response.getWriter();
		String fileContent = "EMP Code, Employee Name ,REMARKS\n";
		Boolean existedUser = false;
		ResponseObject res = new ResponseObject();
		try {
			while (it.hasNext()) {
				Employee emp = new Employee();
				emp = it.next();
				if (isEmpCodeEmailExist(emp.getEmpCode())) {
					emp.setCreatedBy(CommonUtils.getCurrentUserName());
					String userRandomPassword = RandomPassword.geek_Password(10).toString();
					PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
					String encryptedPassword = passwordEncoder.encode(userRandomPassword);
					Users user = new Users();
					user.setEnabled(PMSConstants.STATUS_ACTIVE);
					// For First Time Password Change
					user.setFirstCheck(PMSConstants.STATUS_PENDING);
					user.setPassword(encryptedPassword);
					user.setRoleId(emp.getRoleId());
					user.setUserName(emp.getEmpCode());
					user.setCreatedOn(new Date());
					user.setCreatedBy(CommonUtils.getCurrentUserName());

					EmployeeEmails empEmail = new EmployeeEmails();
					empEmail.setSendTo(emp.getEmail());
					empEmail.setSendFrom(PMSConstants.SEND_FORM);
					empEmail.setAppraisalYearId(CommonUtils.getActiveAppraisalYearId());
					empEmail.setEmailType(PMSConstants.NEW_EMPLOYEE);
					empEmail.setCreatedOn(new Date());
					empEmail.setEmployeePassword(userRandomPassword);
					empEmail.setEmployeeName(emp.getEmpName());
					empEmail.setStatus(PMSConstants.STATUS_PENDING);
					empEmail.setEmailSubject(messages.getString("email.new.employee.subject"));
					empEmail.setEmailContent("Dear " + emp.getEmpName() + "<br><br>"
							+ "Your Performance management system account is ready." + "<br><br>"
							+ "Your Account details" + "<br>"+ "Username:" + emp.getEmpCode() +"<br>" + messages.getString("email.new.employee.text")
							+ userRandomPassword + "<br>"
							+ "URL <a href='https://pms-hfe.com/PMS/login'>Click here to login PMS tool</a>"
							+ "<br><br>"
							+ "We encourage you to use the system on a regular basis which will help you perform better and have better interaction with your manager."
							+ "<br><br>" + "Best Regards" + "<br>" + "Team HR");
					session.persist(empEmail);
					session.persist(user);
					session.persist(emp);
				} else {
					fileContent += (String) emp.getEmpCode() + "," + (String) emp.getEmpCode() + "," + "Failure" + "\n";
					existedUser = true;

					logger.info("Data Download operation started");

				}

			}
			if (existedUser) {
				TimeZone.setDefault(TimeZone.getTimeZone("Asia/Calcutta"));
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat timeFormat = new SimpleDateFormat("HH-mm-ss");
				Date date = new Date();
				String stDateFormat = dateFormat.format(date);
				String stTimeFormat = timeFormat.format(date);
				String fileName = "RejectedEmployee" + "-" + stDateFormat + "_" + stTimeFormat
						+ ".csv".trim().toUpperCase();
				logger.info("File name created is " + fileName);
				response.setContentType("application/csv");
				response.setHeader("content-disposition", fileName);
				logger.info("Writing file Content in the file");
				pWriter.println(fileContent);
				pWriter.flush();
				pWriter.close();
			}
			if (!existedUser) {
				String responseToClient = "Successfully adding Employees in bulk.";

				response.setStatus(HttpServletResponse.SC_OK);
				response.getWriter().write(responseToClient);
				response.getWriter().flush();
				response.getWriter().close();
			}
			logger.info("Successfully adding Employees in bulk. ");
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			res.setInteger(PMSConstants.STATUS_FAILED);
			res.setString("Exception while adding Employee in bulk.");
			pWriter.print(res);
			pWriter.flush();
			pWriter.close();
			logger.error("Exception while adding Employee in bulk. " + e);
		} finally {
			session.flush();
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeCodeEmail> getAllEmployeeAppraisalDetailsList(Integer appraisalYearId, Date eligibilityDate) {
		Session session = this.sessionFactory.openSession();
		List<EmployeeCodeEmail> list = new ArrayList<EmployeeCodeEmail>();
		try {
			String sql = "select emp.ID as id,emp.EMP_NAME as empName, emp.EMP_CODE as empCode, emp.EMAIL as email from employee as emp,"
					+ " appraisal_year_details ayd where ayd.APPRAISAL_YEAR_ID =:APPRAISAL_YEAR_ID and ayd.EMP_CODE = emp.EMP_CODE and  emp.STATUS =:STATUS and"
					+ " emp.DATE_OF_JOINING <=:DATE_OF_JOINING ";
			Query query = session.createSQLQuery(sql).addEntity(EmployeeCodeEmail.class);
			query.setParameter("STATUS", PMSConstants.STATUS_ACTIVE);
			query.setParameter("DATE_OF_JOINING", new Date());
			query.setParameter("APPRAISAL_YEAR_ID", appraisalYearId);
			list = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			System.out.println(e);
			logger.error("Exception while getting AllEmployeeAppraisalDetailsList" + e);
		} finally {
			session.flush();
			session.close();
		}
		return list;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getSubEmployeeAppraisalCountDetails(String empCode, Integer appraisalYearId, String type,String stage) {
		Session session = this.sessionFactory.openSession();
		List list = new ArrayList();
		try {
			String sql = null;

			if (stage.equals(PMSConstants.IN_PLANNING) && type.equals(PMSConstants.FIRST_LEVEL_EMPLOYEE)) {
				sql = "select "
						+ " COUNT(CASE WHEN ((ayd.EMPLOYEE_ISVISIBILE = 1 OR ayd.EMPLOYEE_ISVISIBILE = 0) and ayd.INITIALIZATION_YEAR = 1 and emp.FIRST_LEVEL_SUPERIOR_EMP_ID =:FIRST_LEVEL_SUPERIOR_EMP_ID "
						+ "  and ayd.FIRSTLEVEL_ISVISIBILE = 0 and ayd.SECONDLEVEL_ISVISIBILE = 0 and ayd.SECONDLEVEL_ISVISIBILE_CHECK = 0 ) THEN 1 ELSE NULL END) AS goalSettingFirstManager , "
						+ " COUNT(CASE WHEN (ayd.EMPLOYEE_ISVISIBILE = 0 and ayd.INITIALIZATION_YEAR = 1 and ayd.ACKNOWLEDGEMENT_CHECK = 1 and emp.FIRST_LEVEL_SUPERIOR_EMP_ID =:FIRST_LEVEL_SUPERIOR_EMP_ID "
						+ "  and (ayd.FIRSTLEVEL_ISVISIBILE = 1 OR ayd.FIRSTLEVEL_ISVISIBILE = 0 OR ayd.SECONDLEVEL_ISVISIBILE = 1)  and ayd.SECONDLEVEL_ISVISIBILE_CHECK = 0 ) THEN 1 ELSE NULL END) AS goalApprovalFirstManager "
//						+ " COUNT(CASE WHEN ((ayd.EMPLOYEE_ISVISIBILE = 1 || ayd.EMPLOYEE_ISVISIBILE = 0 ) and ayd.INITIALIZATION_YEAR = 1 and emp.SECOND_LEVEL_SUPERIOR_EMP_ID =:SECOND_LEVEL_SUPERIOR_EMP_ID "
//						+ "  and ayd.FIRSTLEVEL_ISVISIBILE = 0 and ayd.SECONDLEVEL_ISVISIBILE = 0 and ayd.SECONDLEVEL_ISVISIBILE_CHECK = 0 ) THEN 1 ELSE NULL END) AS goalSettingSecondManager ,"
//						+ " COUNT(CASE WHEN (ayd.EMPLOYEE_ISVISIBILE = 0 and ayd.INITIALIZATION_YEAR = 1 and emp.SECOND_LEVEL_SUPERIOR_EMP_ID =:SECOND_LEVEL_SUPERIOR_EMP_ID"
//						+ "  and ayd.FIRSTLEVEL_ISVISIBILE = 0 and ayd.SECONDLEVEL_ISVISIBILE = 1  and ayd.SECONDLEVEL_ISVISIBILE_CHECK = 0 ) THEN 1 ELSE NULL END) AS goalApprovalSecondManager "
						+ " from appraisal_year_details ayd, employee as emp where  emp.emp_code = ayd.emp_code and "
						+ " emp.FIRST_LEVEL_SUPERIOR_EMP_ID =:FIRST_LEVEL_SUPERIOR_EMP_ID"
						+ " and emp.STATUS =:STATUS and ayd.APPRAISAL_YEAR_ID =:APPRAISAL_YEAR_ID";
			}
			if (stage.equals(PMSConstants.IN_PLANNING) && type.equals(PMSConstants.SECOND_LEVEL_EMPLOYEE)) {
				sql = "select "
//						+ " COUNT(CASE WHEN ((ayd.EMPLOYEE_ISVISIBILE = 1 || ayd.EMPLOYEE_ISVISIBILE = 0)  and ayd.INITIALIZATION_YEAR = 1 and emp.FIRST_LEVEL_SUPERIOR_EMP_ID =:FIRST_LEVEL_SUPERIOR_EMP_ID ) THEN 1 ELSE NULL END) AS goalSettingFirstManager ,"
//						+ " COUNT(CASE WHEN (ayd.EMPLOYEE_ISVISIBILE = 0 and ayd.INITIALIZATION_YEAR = 1 and emp.FIRST_LEVEL_SUPERIOR_EMP_ID =:FIRST_LEVEL_SUPERIOR_EMP_ID ) THEN 1 ELSE NULL END) AS goalApprovalFirstManager, "
						+ " COUNT(CASE WHEN ((ayd.EMPLOYEE_ISVISIBILE = 1 OR ayd.EMPLOYEE_ISVISIBILE = 0)  and ayd.INITIALIZATION_YEAR = 1 and emp.SECOND_LEVEL_SUPERIOR_EMP_ID =:SECOND_LEVEL_SUPERIOR_EMP_ID"
						+ "   and ayd.FIRSTLEVEL_ISVISIBILE = 0 and ayd.SECONDLEVEL_ISVISIBILE = 0 and ayd.SECONDLEVEL_ISVISIBILE_CHECK = 0) THEN 1 ELSE NULL END) AS goalSettingSecondManager ,"
						+ " COUNT(CASE WHEN (ayd.EMPLOYEE_ISVISIBILE = 0 and ayd.INITIALIZATION_YEAR = 1 and ayd.ACKNOWLEDGEMENT_CHECK = 1 and emp.SECOND_LEVEL_SUPERIOR_EMP_ID =:SECOND_LEVEL_SUPERIOR_EMP_ID"
						+ "  and (ayd.FIRSTLEVEL_ISVISIBILE = 1 OR ayd.FIRSTLEVEL_ISVISIBILE = 0 OR ayd.SECONDLEVEL_ISVISIBILE = 1)  and ayd.SECONDLEVEL_ISVISIBILE_CHECK = 0 ) THEN 1 ELSE NULL END) AS goalApprovalSecondManager "
						+ " from appraisal_year_details ayd, employee as emp where  emp.emp_code = ayd.emp_code and "
						+ " emp.SECOND_LEVEL_SUPERIOR_EMP_ID =:SECOND_LEVEL_SUPERIOR_EMP_ID"
						+ " and emp.STATUS =:STATUS and ayd.APPRAISAL_YEAR_ID =:APPRAISAL_YEAR_ID";
			}
			if (stage.equals(PMSConstants.IN_REVIEW) && type.equals(PMSConstants.FIRST_LEVEL_EMPLOYEE)) {
//				sql = "select COUNT(CASE WHEN ((ayd.INITIALIZATION_YEAR = 1 and emp.FIRST_LEVEL_SUPERIOR_EMP_ID =:FIRST_LEVEL_SUPERIOR_EMP_ID and ayd.SECONDLEVEL_ISVISIBILE_CHECK = 1 ) "
//						+ " OR (ayd.MID_YEAR = 1  and emp.FIRST_LEVEL_SUPERIOR_EMP_ID =:FIRST_LEVEL_SUPERIOR_EMP_ID and ayd.SECONDLEVEL_ISVISIBILE_CHECK = 0)) THEN 1 ELSE NULL END) AS inReviewFirstManager "
//				11-01-2018 Code Change
				sql = "select COUNT(CASE WHEN ((ayd.MID_YEAR = 1  and emp.FIRST_LEVEL_SUPERIOR_EMP_ID =:FIRST_LEVEL_SUPERIOR_EMP_ID and ayd.SECONDLEVEL_ISVISIBILE_CHECK = 0)) THEN 1 ELSE NULL END) AS inReviewFirstManager "
			
						+ " from appraisal_year_details ayd, employee as emp where  emp.emp_code = ayd.emp_code and "
						+ " emp.FIRST_LEVEL_SUPERIOR_EMP_ID =:FIRST_LEVEL_SUPERIOR_EMP_ID "
						+ " and emp.STATUS =:STATUS and ayd.APPRAISAL_YEAR_ID =:APPRAISAL_YEAR_ID";
			}
			if (stage.equals(PMSConstants.IN_REVIEW) && type.equals(PMSConstants.SECOND_LEVEL_EMPLOYEE)) {
//				sql = "select COUNT(CASE WHEN ((ayd.INITIALIZATION_YEAR = 1 and emp.SECOND_LEVEL_SUPERIOR_EMP_ID =:SECOND_LEVEL_SUPERIOR_EMP_ID and ayd.SECONDLEVEL_ISVISIBILE_CHECK = 1 ) "
//						+ " OR (ayd.MID_YEAR = 1  and emp.SECOND_LEVEL_SUPERIOR_EMP_ID =:SECOND_LEVEL_SUPERIOR_EMP_ID and ayd.SECONDLEVEL_ISVISIBILE_CHECK = 0)) THEN 1 ELSE NULL END) AS inReviewSecondManager "
//				 11-01-2018 Code Change
				sql = "select COUNT(CASE WHEN ((ayd.MID_YEAR = 1  and emp.SECOND_LEVEL_SUPERIOR_EMP_ID =:SECOND_LEVEL_SUPERIOR_EMP_ID and ayd.SECONDLEVEL_ISVISIBILE_CHECK = 0)) THEN 1 ELSE NULL END) AS inReviewSecondManager "						
						+ " from appraisal_year_details ayd, employee as emp where  emp.emp_code = ayd.emp_code and "
						+ " emp.SECOND_LEVEL_SUPERIOR_EMP_ID =:SECOND_LEVEL_SUPERIOR_EMP_ID "
						+ " and emp.STATUS =:STATUS and ayd.APPRAISAL_YEAR_ID =:APPRAISAL_YEAR_ID";
			}
			if (stage.equals(PMSConstants.IN_PROCESS) && type.equals(PMSConstants.FIRST_LEVEL_EMPLOYEE)) {
//				sql = "select COUNT(CASE WHEN ((ayd.FINAL_YEAR = 1 and ayd.SECONDLEVEL_ISVISIBILE_CHECK = 0 and emp.FIRST_LEVEL_SUPERIOR_EMP_ID =:FIRST_LEVEL_SUPERIOR_EMP_ID ) "
//						+ "  OR (ayd.MID_YEAR = 1 and ayd.SECONDLEVEL_ISVISIBILE_CHECK = 1 and emp.FIRST_LEVEL_SUPERIOR_EMP_ID =:FIRST_LEVEL_SUPERIOR_EMP_ID))THEN 1 ELSE NULL END)  AS yearEndAssessmentFirstManager ,"
//						+ " COUNT(CASE WHEN (ayd.FINAL_YEAR = 1  and ayd.SECONDLEVEL_ISVISIBILE_CHECK = 1 and emp.FIRST_LEVEL_SUPERIOR_EMP_ID =:FIRST_LEVEL_SUPERIOR_EMP_ID) THEN 1 ELSE NULL END) AS assessmentApprovalFirstManager "
//				 11-01-2018 Code Change		
				sql = "select COUNT(CASE WHEN ((ayd.FINAL_YEAR = 1 and ayd.SECONDLEVEL_ISVISIBILE_CHECK = 0 and emp.FIRST_LEVEL_SUPERIOR_EMP_ID =:FIRST_LEVEL_SUPERIOR_EMP_ID ) "
								+ " )THEN 1 ELSE NULL END)  AS yearEndAssessmentFirstManager ,"
								+ " COUNT(CASE WHEN (ayd.FINAL_YEAR = 1  and ayd.SECONDLEVEL_ISVISIBILE_CHECK = 1 and emp.FIRST_LEVEL_SUPERIOR_EMP_ID =:FIRST_LEVEL_SUPERIOR_EMP_ID) THEN 1 ELSE NULL END) AS assessmentApprovalFirstManager "
						+ " from appraisal_year_details ayd, employee as emp where  emp.emp_code = ayd.emp_code and "
						+ " emp.FIRST_LEVEL_SUPERIOR_EMP_ID =:FIRST_LEVEL_SUPERIOR_EMP_ID "
						+ " and emp.STATUS =:STATUS and ayd.APPRAISAL_YEAR_ID =:APPRAISAL_YEAR_ID";
			}
			if (stage.equals(PMSConstants.IN_PROCESS) && type.equals(PMSConstants.SECOND_LEVEL_EMPLOYEE)) {
//				sql = "select COUNT(CASE WHEN ((ayd.FINAL_YEAR = 1 and ayd.SECONDLEVEL_ISVISIBILE_CHECK = 0 and emp.SECOND_LEVEL_SUPERIOR_EMP_ID =:SECOND_LEVEL_SUPERIOR_EMP_ID ) "
//						+ "  OR (ayd.MID_YEAR = 1 and ayd.SECONDLEVEL_ISVISIBILE_CHECK = 1 and emp.SECOND_LEVEL_SUPERIOR_EMP_ID =:SECOND_LEVEL_SUPERIOR_EMP_ID))THEN 1 ELSE NULL END)  AS yearEndAssessmentFirstManager ,"							
//						+ " COUNT(CASE WHEN (ayd.FINAL_YEAR = 1  and ayd.SECONDLEVEL_ISVISIBILE_CHECK = 1 and emp.SECOND_LEVEL_SUPERIOR_EMP_ID =:SECOND_LEVEL_SUPERIOR_EMP_ID) THEN 1 ELSE NULL END) AS assessmentApprovalSecondManager"
//				11-01-2018 Code Change			
				sql = "select COUNT(CASE WHEN ((ayd.FINAL_YEAR = 1 and ayd.SECONDLEVEL_ISVISIBILE_CHECK = 0 and emp.SECOND_LEVEL_SUPERIOR_EMP_ID =:SECOND_LEVEL_SUPERIOR_EMP_ID ) "
								+ "  )THEN 1 ELSE NULL END)  AS yearEndAssessmentFirstManager ,"							
								+ " COUNT(CASE WHEN (ayd.FINAL_YEAR = 1  and ayd.SECONDLEVEL_ISVISIBILE_CHECK = 1 and emp.SECOND_LEVEL_SUPERIOR_EMP_ID =:SECOND_LEVEL_SUPERIOR_EMP_ID) THEN 1 ELSE NULL END) AS assessmentApprovalSecondManager"
						+ " from appraisal_year_details ayd, employee as emp where  emp.emp_code = ayd.emp_code and "
						+ " emp.SECOND_LEVEL_SUPERIOR_EMP_ID =:SECOND_LEVEL_SUPERIOR_EMP_ID "
						+ " and emp.STATUS =:STATUS and ayd.APPRAISAL_YEAR_ID =:APPRAISAL_YEAR_ID";
			}
			Query query = session.createSQLQuery(sql);
			query.setParameter("STATUS", PMSConstants.STATUS_ACTIVE);
			query.setParameter("APPRAISAL_YEAR_ID", appraisalYearId);
			if (type.equals(PMSConstants.SECOND_LEVEL_EMPLOYEE)) {
			query.setParameter("SECOND_LEVEL_SUPERIOR_EMP_ID", empCode);
			}
			if (type.equals(PMSConstants.FIRST_LEVEL_EMPLOYEE)) {
			query.setParameter("FIRST_LEVEL_SUPERIOR_EMP_ID", empCode);
			}
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

	@Override
	public String getAppraiserName(String empCode) {
		Session session = this.sessionFactory.openSession();
		String appraiserName = null;
		try {
			String sql = "select (select emp1.empName from Employee as emp1 where emp1.empCode = emp.firstLevelSuperiorEmpId) as firstLevelSuperiorName"
					+ " from Employee as emp" + " where emp.empCode =:empCode and emp.status =:status";
			Query query = session.createQuery(sql);
			query.setParameter("empCode", empCode);
			query.setParameter("status", PMSConstants.STATUS_ACTIVE);
			appraiserName = (String) query.list().get(0);
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception while getting AppraiserName" + e);
		} finally {
			session.flush();
			session.close();
		}
		return appraiserName;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResponseObject getEmployeePromotionDetails(Integer appraisalYearId, String empCode) {
		Session session = this.sessionFactory.openSession();
		ResponseObject res = new ResponseObject();
		List list = new ArrayList();
		try {
			String sql = " select (select emp1.empName from Employee as emp1 where emp1.empCode = emp.firstLevelSuperiorEmpId) as firstLevelSuperiorName, "
					+ " (select emp2.empName from Employee as emp2 where emp2.empCode = emp.secondLevelSuperiorEmpId) as secondLevelSuperiorName, "
					+ " emp.mobile as mobile, emp.id as id, emp.empCode as empCode ,"
					+ " emp.empName as empName,emp.email as email,emp.dateOfJoining as dateOfJoining ,"
					+ " emp.location as location, "
					+ " dep.name as departmentName, des.name as designationName, emp.qualification as qualificationName"
					+ " from Employee as emp,Department as dep,Designation as des "
					+ " where emp.empCode =:empCode and emp.departmentId = dep.id"
					+ " and emp.designationId = des.id and emp.status =:status";
			Query query = session.createQuery(sql);
			query.setParameter("empCode", empCode);
			query.setParameter("status", PMSConstants.STATUS_ACTIVE);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			list.add(query.uniqueResult());
			list.add(DbUtils.getCurrentEmployeePromotionDetails(appraisalYearId, empCode));
			res.setObject(list);
			res.setInteger(PMSConstants.STATUS_SUCCESS);
			res.setString("Success");
		} catch (HibernateException e) {
			e.printStackTrace();
			res.setObject(e);
			res.setInteger(PMSConstants.STATUS_FAILED);
			res.setString("Exception while getting SubEmployee Details");
			logger.error("Exception while getting SubEmployee Details" + e);
		} finally {
			session.flush();
			session.close();
		}
		return res;
	}

	@Override
	public String getEmployeeType(String empCode) {
		Session session = this.sessionFactory.openSession();
		List<Employee> list = new ArrayList<Employee>();
		try {
			Criteria criteria = session.createCriteria(Employee.class);
			criteria.add(Restrictions.eq("empCode", empCode));
			criteria.add(Restrictions.eq("status", PMSConstants.STATUS_ACTIVE));
			list = criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception while getting Employee Type... " + e);
		} finally {
			session.flush();
			session.close();
		}
		return list.get(0).getEmpType();
	}

	@Override
	public ResponseObject getOverAllRatingSubEmployeeList(String empCode, String type, Integer appraisalYearId) {
		Session session = this.sessionFactory.openSession();
		ResponseObject res = new ResponseObject();
		try {
			String sql = "select emp.empName as empName, emp.empCode as empCode,concat(emp.empCode,' - ', emp.empName) AS empCodeWithName"
					+ " from Employee as emp,AppraisalYearDetails ayd where emp.status =:status and ayd.empCode = emp.empCode and ayd.appraisalYearId =:appraisalYearId ";
			if (type.equalsIgnoreCase(PMSConstants.FIRST_LEVEL_EMPLOYEE)) {
				sql = sql
						+ "  and emp.firstLevelSuperiorEmpId =:firstLevelSuperiorEmpId and ayd.secondLevelIsvisibleCheck =:secondLevelIsvisibleCheck";
			}
			if (type.equalsIgnoreCase(PMSConstants.SECOND_LEVEL_EMPLOYEE)) {
				sql = sql
						+ "  and emp.secondLevelSuperiorEmpId =:secondLevelSuperiorEmpId  and ayd.secondLevelIsvisibleCheck =:secondLevelIsvisibleCheck";
			}
			Query query = session.createQuery(sql);
			if (type.equalsIgnoreCase(PMSConstants.FIRST_LEVEL_EMPLOYEE)) {
				query.setParameter("firstLevelSuperiorEmpId", empCode);
				query.setParameter("secondLevelIsvisibleCheck", PMSConstants.STATUS_ACTIVE);
			}
			if (type.equalsIgnoreCase(PMSConstants.SECOND_LEVEL_EMPLOYEE)) {
				query.setParameter("secondLevelSuperiorEmpId", empCode);
				query.setParameter("secondLevelIsvisibleCheck", PMSConstants.STATUS_ACTIVE);
			}
			query.setParameter("appraisalYearId", appraisalYearId);
			query.setParameter("status", PMSConstants.STATUS_ACTIVE);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			res.setObject(query.list());
			res.setInteger(PMSConstants.STATUS_SUCCESS);
			res.setString("Success");
		} catch (HibernateException e) {
			e.printStackTrace();
			res.setInteger(PMSConstants.STATUS_FAILED);
			res.setString("Error while getting All Team Members");
		} finally {
			session.flush();
			session.close();
		}
		return res;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ResponseObject getAllEmployeeListData() {
		ResponseObject res = new ResponseObject();
		Session session = this.sessionFactory.openSession();
		List list = new ArrayList();
		try {
			String sql = "select concat(empCode,' - ', empName) AS text, empCode as id,status as status from Employee where empCode !='DEFAULT_CODE'";
			Query query = session.createQuery(sql);
//			query.setParameter("status", PMSConstants.STATUS_ACTIVE);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			list = query.list();
			res.setInteger(PMSConstants.STATUS_SUCCESS);
			res.setString(PMSConstants.OK);
			res.setObject(list);
		} catch (HibernateException e) {
			e.printStackTrace();
			res.setInteger(PMSConstants.STATUS_FAILED);
			res.setString("NO result found");
			res.setObject(null);
			logger.error("Exception while getting getAllEmployeeListData " + e);
		} finally {
			session.flush();
			session.close();
		}
		return res;
	}

	@Override
	public List<EmployeeBasicDetails> getEmployeeBasicDetails(Integer departmentId,Integer appraisalYearId,String empCode) {
		Session session = this.sessionFactory.openSession();
		List<EmployeeBasicDetails> EmployeeBasicDetails = new ArrayList<EmployeeBasicDetails>();
		try {
			String sql ="select emp.ID as empId, emp.EMP_CODE as empCode,emp.EMP_NAME as empName,dep.NAME as departmentName,desig.NAME as designationName "
					+ " from employee as emp, department as dep,designation as desig where "
					+ " dep.ID = emp.DEPARTMENT_ID and desig.ID = emp.DESIGNATION_ID ";
			if(empCode == null)
			{
				sql = sql + " and dep.ID =:ID";
			}
			if(empCode != null)
			{
				sql = sql + " and emp.EMP_CODE =:EMP_CODE";
			}
			Query query = session.createSQLQuery(sql).addEntity(EmployeeBasicDetails.class);
			if(empCode != null)
			{	
				query.setParameter("EMP_CODE", empCode);
			}
			if(empCode == null)
			{	
			query.setParameter("ID", departmentId);
			}
			EmployeeBasicDetails = query.list();	
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception while getting getEmployeeBasicDetails List" + e);
		} finally {
			session.flush();
			session.close();
		}
		return EmployeeBasicDetails;
	}

	@Override
	public ResponseObject getAllDeletedEmployeeList() {
		ResponseObject res = new ResponseObject();
		Session session = this.sessionFactory.openSession();
		List list = new ArrayList();
		try {
			String sql = "select concat(empCode,' - ', empName) AS text, empCode as id,status as status from Employee where empCode !='DEFAULT_CODE' and status='0'";
			Query query = session.createQuery(sql);
//			query.setParameter("status", PMSConstants.STATUS_ACTIVE);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			list = query.list();
			res.setInteger(PMSConstants.STATUS_SUCCESS);
			res.setString(PMSConstants.OK);
			res.setObject(list);
		} catch (HibernateException e) {
			e.printStackTrace();
			res.setInteger(PMSConstants.STATUS_FAILED);
			res.setString("NO result found");
			res.setObject(null);
			logger.error("Exception while getting getAllDeletedEmployeeList " + e);
		} finally {
			session.flush();
			session.close();
		}
		return res;
	
	
	}

	@Override
	public ResponseObject getAllActiveEmployeeListData() {
		ResponseObject res = new ResponseObject();
		Session session = this.sessionFactory.openSession();
		List list = new ArrayList();
		try {
			String sql = "select concat(empCode,' - ', empName) AS text, empCode as id,status as status from Employee where empCode !='DEFAULT_CODE' and status='1'";
			Query query = session.createQuery(sql);
//			query.setParameter("status", PMSConstants.STATUS_ACTIVE);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			list = query.list();
			res.setInteger(PMSConstants.STATUS_SUCCESS);
			res.setString(PMSConstants.OK);
			res.setObject(list);
		} catch (HibernateException e) {
			e.printStackTrace();
			res.setInteger(PMSConstants.STATUS_FAILED);
			res.setString("NO result found");
			res.setObject(null);
			logger.error("Exception while getting getAllActiveEmployeeListData " + e);
		} finally {
			session.flush();
			session.close();
		}
		return res;
	
	
	}

	@Override
	public ResponseObject getAllDetailsOfDeletedEmployeeList() {
		Session session = this.sessionFactory.openSession();
		ResponseObject res = new ResponseObject();
		try {
			String sql = "select (select emp1.empName from Employee as emp1 where emp1.empCode = emp.firstLevelSuperiorEmpId) as firstLevelSuperiorName,"
					+ " (select emp2.empName from Employee as emp2 where emp2.empCode = emp.secondLevelSuperiorEmpId) as secondLevelSuperiorName,"
					+ " emp.id as id,orgR.name as organizationRole,rol.name as applicationRole, emp.email as email,emp.location as location,emp.mobile as mobile, emp.empName as empName,emp.empCode as empCode,emp.status as status,"
					+ " dep.name as departmentName, des.name as designationName"
					+ " from Employee as emp,Department as dep,Designation as des,OrganizationRoles as orgR,Roles as rol where emp.departmentId = dep.id and orgR.id = emp.organizationRoleId and rol.id = emp.roleId and  emp.status =:status"
					+ " and emp.designationId = des.id";
			Query query = session.createQuery(sql);
			// and emp.status =:status
			// query.setParameter("status", PMSConstants.STATUS_ACTIVE);
			// query.setParameter("appraisalYearId", appraisalYearId);
			query.setParameter("status", PMSConstants.STATUS_DELETED);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			res.setObject(query.list());
			res.setInteger(PMSConstants.STATUS_SUCCESS);
			res.setString("Success");
		} catch (HibernateException e) {
			e.printStackTrace();
			res.setObject(e);
			res.setInteger(PMSConstants.STATUS_FAILED);
			res.setString("Exception while getting All Employee List " + e);
			logger.error("Exception while getting All Employee List" + e);
		} finally {
			session.flush();
			session.close();
		}
		return res;
	}

	@Override
	public ResponseObject getAllDetailsEmployeeList() {
		Session session = this.sessionFactory.openSession();
		ResponseObject res = new ResponseObject();
		try {
			String sql = "select (select emp1.empName from Employee as emp1 where emp1.empCode = emp.firstLevelSuperiorEmpId) as firstLevelSuperiorName,"
					+ " (select emp2.empName from Employee as emp2 where emp2.empCode = emp.secondLevelSuperiorEmpId) as secondLevelSuperiorName,"
					+ " emp.id as id,orgR.name as organizationRole,rol.name as applicationRole, emp.email as email,emp.location as location,emp.mobile as mobile, emp.empName as empName,emp.empCode as empCode,emp.status as status,"
					+ " dep.name as departmentName, des.name as designationName"
					+ " from Employee as emp,Department as dep,Designation as des,OrganizationRoles as orgR,Roles as rol where emp.departmentId = dep.id and orgR.id = emp.organizationRoleId and rol.id = emp.roleId"
					+ " and emp.designationId = des.id";
			Query query = session.createQuery(sql);
			// and emp.status =:status
			// query.setParameter("status", PMSConstants.STATUS_ACTIVE);
			// query.setParameter("appraisalYearId", appraisalYearId);
		//	query.setParameter("status", PMSConstants.STATUS_ACTIVE);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			res.setObject(query.list());
			res.setInteger(PMSConstants.STATUS_SUCCESS);
			res.setString("Success");
		} catch (HibernateException e) {
			e.printStackTrace();
			res.setObject(e);
			res.setInteger(PMSConstants.STATUS_FAILED);
			res.setString("Exception while getting getAllDetailsEmployeeList " + e);
			logger.error("Exception while getting getAllDetailsEmployeeList" + e);
		} finally {
			session.flush();
			session.close();
		}
		return res;
	}



}
