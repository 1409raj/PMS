package com.sampark.PMS.hibernate;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.sampark.PMS.PMSConstants;
import com.sampark.PMS.dto.AppraisalYearDetails;
import com.sampark.PMS.dto.Employee;
import com.sampark.PMS.dto.Parameters;
import com.sampark.PMS.dto.PasswordRest;
import com.sampark.PMS.dto.Users;
import com.sampark.PMS.object.ResponseObject;
import com.sampark.PMS.util.CommonUtils;
import com.sampark.PMS.util.DbUtils;
import com.sampark.PMS.util.EncryptionUtil;

public class CommonDAOImpl implements CommonDAO {

	private static final Logger logger = LoggerFactory.getLogger(CommonDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public String getApplicationVersion() {
		String version = "1.0.0";
		// Session session = sessionFactory.openSession();
		// Query query = session.createSQLQuery("select BIAPPVERSION from
		// BIDB.BI_VERSION");
		// try {
		// version = query.list().get(0).toString();
		// } catch (Exception e) {
		// logger.error("Error while reading application version... " + e);
		// }
		// session.close();
		return version;
	}

	@Override
	public Integer getNextId(String sequenceName) throws Exception {
		Integer id = 0;
		Session session = sessionFactory.openSession();
		Query query = session.createSQLQuery("select " + sequenceName + ".nextval from DUAL");
		id = ((BigDecimal) query.list().get(0)).intValue();
		session.flush();
		session.close();
		return id;
	}

//	@Override
//	public ResponseObject getLoggedUserDetails(String empCode) {
//		Session session = this.sessionFactory.openSession();
//		ResponseObject res = new ResponseObject();
//		try {
//			// emp.assessmentPeriod as assessmentPeriod,
//			String sql = "select (select emp1.empName from Employee as emp1 where emp1.empCode = emp.firstLevelSuperiorEmpId) as firstLevelSuperiorName, "
//					+ " (select emp2.empName from Employee as emp2 where emp2.empCode = emp.secondLevelSuperiorEmpId) as secondLevelSuperiorName,emp.empCode as empCode ,"
//					+ " emp.empName as empName,emp.mobile as mobile, emp.email as email,emp.dateOfJoining as dateOfJoining ,"
//					+ " emp.location as location,"
//					+ " dep.name as departmentName, des.name as designationName, qua.name as qualificationName"
//					+ " from Employee as emp,Department as dep,Designation as des,Qualification as qua"
//					+ " where emp.empCode =:empCode and emp.departmentId = dep.id"
//					+ " and emp.qualificationId = qua.id and emp.designationId = des.id and emp.status =:status";
//			Query query = session.createQuery(sql);
//			query.setParameter("empCode", empCode);
//			query.setParameter("status", PMSConstants.STATUS_ACTIVE);
//			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
//			res.setObject(query.uniqueResult());
//			res.setInteger(PMSConstants.STATUS_SUCCESS);
//			res.setString("Success");
//		} catch (HibernateException e) {
//			e.printStackTrace();
//			res.setObject(e);
//			res.setInteger(PMSConstants.STATUS_FAILED);
//			res.setString("Error while getting Logged User Details");
//		} finally {
//			session.close();
//		}
//		return res;
//	}

	// @Override
	// public Long getCurrentUserSubEmployeeCount(String currentUserName) {
	// Session session = this.sessionFactory.openSession();
	// Long count = null;
	// try {
	// String sql = "select count(empCode) from Employee where
	// firstLevelSuperiorEmpId =:firstLevelSuperiorEmpId";
	// Query query = session.createQuery(sql);
	// query.setParameter("firstLevelSuperiorEmpId", currentUserName);
	// count = (Long) query.uniqueResult();
	// } catch (HibernateException e) {
	// e.printStackTrace();
	// logger.error("Error while getting Current User SubEmploye Count");
	// } finally {
	// session.close();
	// }
	// return count;
	// }

	@Override
	public Long getCurrentUserSubEmployeeCount(String currentUserName, String superiorLevel) {
		Session session = this.sessionFactory.openSession();
		Long count = null;
		try {
			String sql = "select count(empCode) from Employee where ";

			if (superiorLevel.equalsIgnoreCase(PMSConstants.FIRST_LEVEL_EMPLOYEE)) {
				sql = sql + " firstLevelSuperiorEmpId =:firstLevelSuperiorEmpId ";

			}
			if (superiorLevel.equalsIgnoreCase(PMSConstants.SECOND_LEVEL_EMPLOYEE)) {
				sql = sql + " secondLevelSuperiorEmpId =:secondLevelSuperiorEmpId ";

			}
			Query query = session.createQuery(sql);
			if (superiorLevel.equalsIgnoreCase(PMSConstants.FIRST_LEVEL_EMPLOYEE)) {
				query.setParameter("firstLevelSuperiorEmpId", currentUserName);
			}
			if (superiorLevel.equalsIgnoreCase(PMSConstants.SECOND_LEVEL_EMPLOYEE)) {
				query.setParameter("secondLevelSuperiorEmpId", currentUserName);
			}
			count = (Long) query.uniqueResult();
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Error while getting Current User SubEmploye Count");
		} finally {
			session.flush();
			session.close();
		}
		return count;
	}

	@Override
	public ResponseObject getAllTeamMembers(String empCode, String type, Integer appraisalYearId) {
		Session session = this.sessionFactory.openSession();
		ResponseObject res = new ResponseObject();
		try {
			
//			String sql = "select emp.empName as empName, emp.empCode as empCode, des.name as designationName,dep.name as departmentName, "
//					+ " ayd.initializationYear as initializationYear,ayd.midYear as midYear,ayd.finalYear as finalYear,ayd.employeeIsvisible as employeeIsvisible,"
//					+ " ayd.firstLevelIsvisible as firstLevelIsvisible,ayd.secondLevelIsvisible as secondLevelIsvisible,ayd.secondLevelIsvisibleCheck as secondLevelIsvisibleCheck, "
//					+ " ayd.firstManagerGoalApproval as firstManagerGoalApproval,ayd.firstManagerMidYearApproval as firstManagerMidYearApproval,ayd.firstManagerYearEndAssessmentApproval as firstManagerYearEndAssessmentApproval, "
//					+ " ayd.employeeApproval as employeeApproval,ayd.employeeMidYearApproval as employeeMidYearApproval, ayd.acknowledgementCheck as acknowledgementCheck "
//					+ " from Employee as emp,Designation as des, Department as dep , AppraisalYearDetails as ayd "
//					+ " where emp.designationId = des.id and dep.id = emp.departmentId and ayd.appraisalYearId =:appraisalYearId and ayd.empCode = emp.empCode ";
			
			
			String sql = "select emp.empName as empName, emp.empCode as empCode,"
					+ " ayd.initializationYear as initializationYear,ayd.midYear as midYear,ayd.finalYear as finalYear,ayd.employeeIsvisible as employeeIsvisible,"
					+ " ayd.firstLevelIsvisible as firstLevelIsvisible,ayd.secondLevelIsvisible as secondLevelIsvisible,ayd.secondLevelIsvisibleCheck as secondLevelIsvisibleCheck, "
					+ " ayd.firstManagerGoalApproval as firstManagerGoalApproval,ayd.firstManagerMidYearApproval as firstManagerMidYearApproval,ayd.firstManagerYearEndAssessmentApproval as firstManagerYearEndAssessmentApproval, "
					+ " ayd.employeeApproval as employeeApproval,ayd.employeeMidYearApproval as employeeMidYearApproval, ayd.acknowledgementCheck as acknowledgementCheck, "
					+ " dep.name as departmentName, des.name as designationName"
					+ " from Employee as emp,Department as dep,Designation as des, AppraisalYearDetails ayd where emp.departmentId = dep.id"
					+ " and emp.designationId = des.id and emp.status =:status and ayd.empCode = emp.empCode and ayd.appraisalYearId =:appraisalYearId and ";
			if (type.equalsIgnoreCase(PMSConstants.FIRST_LEVEL_EMPLOYEE)) {
				sql = sql + "emp.firstLevelSuperiorEmpId =:firstLevelSuperiorEmpId ";
//				and ayd.firstLevelIsvisible =:firstLevelIsvisible
			}
			if (type.equalsIgnoreCase(PMSConstants.SECOND_LEVEL_EMPLOYEE)) {
				sql = sql + "emp.secondLevelSuperiorEmpId =:secondLevelSuperiorEmpId ";
//				and ayd.secondLevelIsvisible =:secondLevelIsvisible
			}
			Query query = session.createQuery(sql);
			if (type.equalsIgnoreCase(PMSConstants.FIRST_LEVEL_EMPLOYEE)) {
				query.setParameter("firstLevelSuperiorEmpId", empCode);
//				query.setParameter("firstLevelIsvisible", PMSConstants.STATUS_ACTIVE);
			}
			if (type.equalsIgnoreCase(PMSConstants.SECOND_LEVEL_EMPLOYEE)) {
				query.setParameter("secondLevelSuperiorEmpId", empCode);
//				query.setParameter("secondLevelIsvisible", PMSConstants.STATUS_ACTIVE);
			}
			query.setParameter("appraisalYearId", appraisalYearId);
			query.setParameter("status", PMSConstants.STATUS_ACTIVE);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			res.setObject(query.list());
			res.setInteger(PMSConstants.STATUS_SUCCESS);
			res.setString("Successfully saved");
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

	@Override
	public List<Parameters> getAllParameters() {
		Session session = this.sessionFactory.openSession();
		List<Parameters> list = new ArrayList<Parameters>();
		try {
			String sql = "from Parameters where status =:status";
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ResponseObject getAllParametersData(Integer id) {
		ResponseObject responseObject = new ResponseObject();
		switch (id) {
		case 1:
			responseObject.setObject(DbUtils.getAllDepartmentList());
			break;
		case 2:
			responseObject.setObject(DbUtils.getAllDesignationList());
			break;
//		case 3:
//			responseObject.setObject(DbUtils.getAllQualificationList());
//			break;
		case 4:
			Map map = new HashMap<>();
			map.put("departmentList", DbUtils.getAllDepartmentList());
			responseObject.setObject(map);
			break;
		case 5:
			responseObject.setObject(DbUtils.getAllApplicationRolesList());
			break;
		}
		return responseObject;
	}

	@Override
	public Integer getCurrentUserId(String empCode) {
		Session session = this.sessionFactory.openSession();
		List<Users> list = new ArrayList<Users>();
		try {
			Criteria criteria = session.createCriteria(Users.class);
			criteria.add(Restrictions.eq("userName", empCode));
			list = criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Error while getting Current User ID");
		} finally {
			session.flush();
			session.close();
		}
		return list.get(0).getId();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ResponseObject isUserFirstTime(String empCode,Integer appraisalYearId) {
		Session session = this.sessionFactory.openSession();
		List list = new ArrayList();
		ResponseObject responseObject = new ResponseObject();
		try {
			Criteria criteria = session.createCriteria(Users.class);
			criteria.add(Restrictions.eq("enabled", PMSConstants.STATUS_ACTIVE));
			criteria.add(Restrictions.eq("firstCheck", PMSConstants.STATUS_PENDING));
			criteria.add(Restrictions.eq("userName", empCode));
			list = criteria.list();
			if (list.size() > 0) {
				responseObject.setInteger(PMSConstants.STATUS_SUCCESS);
			}
			else
			{
				responseObject.setInteger(PMSConstants.STATUS_FAILED);
				responseObject.setObject(DbUtils.isUserAcknowledged(empCode,appraisalYearId));
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception while checking is User First Time Login... " + e);
		} finally {
			session.flush();
			session.close();
		}
		return responseObject;
	}

	@Override
	public ResponseObject changeUserPassword(String currentPassword, String password) {
		ResponseObject responseObject = new ResponseObject();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Users user = new Users();
			if (currentPassword != null && !currentPassword.isEmpty() && isValidPassword(currentPassword,CommonUtils.getCurrentUserName())) {
				user = (Users) session.get(Users.class, DbUtils.getCurrentUserId(CommonUtils.getCurrentUserName()));
				user.setModifiedOn(new Date());
				user.setModifiedBy(CommonUtils.getCurrentUserName());
				user.setPassword(password);
				user.setFirstCheck(PMSConstants.STATUS_ACTIVE);  // For First Time Password Change
				session.update(user);
				tx.commit();
				responseObject.setInteger(PMSConstants.STATUS_SUCCESS);
				responseObject.setString("Successfully saved");
			} else {
				responseObject.setInteger(PMSConstants.STATUS_FAILED);
				responseObject.setString("Invalid current password.");
			}
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			logger.error("Exception while updating new password ... " + e);
			responseObject.setInteger(PMSConstants.STATUS_FAILED);
			responseObject.setString("Exception while updating new password ... " + e);
			responseObject.setObject(e);
		} finally {
			session.flush();
			session.close();
		}
		return responseObject;
	}

	@SuppressWarnings("rawtypes")
	private boolean isValidPassword(String oldPassword,String userName) {
		Session session = this.sessionFactory.openSession();
		List list = new ArrayList();
		boolean isUserValidPassword = false;
		try {
			Criteria criteria = session.createCriteria(Users.class);
			criteria.add(Restrictions.eq("enabled", PMSConstants.STATUS_ACTIVE));
			criteria.add(Restrictions.eq("userName",userName));
			list = criteria.list();
			if (list.size() == 1) {
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				String existingPassword = oldPassword; // Password entered by
														// user
				String dbPassword = getCurrentUserPassword(); // Load hashed DB
																// password

				if (passwordEncoder.matches(existingPassword, dbPassword)) {
					isUserValidPassword = true;
					// Encode new password and store it
				}
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception while checking is User Password... " + e);
		} finally {
			session.flush();
			session.close();
		}
		return isUserValidPassword;
	}

	private String getCurrentUserPassword() {
		Session session = this.sessionFactory.openSession();
		List<Users> list = new ArrayList<Users>();
		try {
			Criteria criteria = session.createCriteria(Users.class);
			criteria.add(Restrictions.eq("userName", CommonUtils.getCurrentUserName()));
			list = criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Error while getting Current User Password");
		} finally {
			session.flush();
			session.close();
		}
		return list.get(0).getPassword();
	}

	@Override
	public ResponseObject forgetPassword(String email) {
		Session session = this.sessionFactory.openSession();
		List<Employee> list = new ArrayList<Employee>();
		ResponseObject object = new ResponseObject();
		try {
			Criteria criteria = session.createCriteria(Employee.class);
			criteria.add(Restrictions.eq("email", email));
			list = criteria.list();
			if (list.size() == 1) {
				Date date = new Date();
				SimpleDateFormat ft = new SimpleDateFormat("MMddyyhhmmssMs");
				String datetime = ft.format(date);
				String first = datetime.substring(0, datetime.length() / 2); //
				String second = datetime.substring(datetime.length() / 2); //
				String token = first + PMSConstants.SPLIT_TEXT + CommonUtils.getCurrentUserId(list.get(0).getEmpCode())
						+ PMSConstants.SPLIT_TEXT + second;
				String passwordEncoded = null;
				try {
					passwordEncoded = EncryptionUtil.encrypt(token);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("Error while creating encryption token for forget password");
					e.printStackTrace();
				}
				if(isUserPasswordSaved(passwordEncoded))
				{
					object.setString("Link has been send your email address.");
					object.setInteger(PMSConstants.STATUS_SUCCESS);
					object.setObject(passwordEncoded);
				}
//				System.out.println(datetime);
//				System.out.println("\n" + first);
//				System.out.println("\n" + second);
//				System.out.println("\n" + token);
			} else {
				object.setString("Invalid email address");
				object.setInteger(PMSConstants.STATUS_FAILED);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			object.setString("Error while getting Current User Email for forget password");
			object.setInteger(PMSConstants.STATUS_FAILED);
			logger.error("Error while getting Current User Email for forget password");
		} finally {
			session.flush();
			session.close();
		}
		return object;
	}

	private boolean isUserPasswordSaved(String token) {
		Session session = sessionFactory.openSession();
		Transaction tx =session.beginTransaction();
		boolean userPassword =false;
		try {
			PasswordRest object = new PasswordRest();
			object.setCreatedOn(new Date());
			object.setToken(token);
			session.persist(object);
			tx.commit();
			userPassword = true;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			logger.error("Method isUserPasswordSaved : Exception while adding new PaswwordRest Table ... " + e);
		}finally {
			session.flush();
			session.close();
		}
		return userPassword;
	}

	@Override
	public ResponseObject resetPassword(String password, String token, String email)  {
		Session session = this.sessionFactory.openSession();
		List<PasswordRest> listToken = new ArrayList<PasswordRest>();
		List<Employee> listEmail = new ArrayList<Employee>();
		Transaction tx =session.beginTransaction();
		ResponseObject object = new ResponseObject();
		try {
			Criteria criteriaToken = session.createCriteria(PasswordRest.class);
			String tempToken = token.replace(' ','+');
			criteriaToken.add(Restrictions.eq("token",tempToken ));
			listToken = criteriaToken.list(); 
			if (listToken.size() == 1) {
				Criteria criteriaEmail = session.createCriteria(Employee.class);
				criteriaEmail.add(Restrictions.eq("email", email));
				listEmail = criteriaEmail.list();
				if (listEmail.size() == 1) {
					       String userToken = null;
						try {
							userToken = EncryptionUtil.decrypt(tempToken);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						    String array[] = userToken.split(PMSConstants.SPLIT_TEXT);
						    Users user = (Users) session.get(Users.class,Integer.parseInt(array[1]));
							user.setModifiedOn(new Date());
							user.setModifiedBy(listEmail.get(0).getEmpCode());
							user.setPassword(password);
							session.update(user);
							tx.commit();
							if(deleteUserToken(listToken.get(0).getId()))
							{
							object.setInteger(PMSConstants.STATUS_SUCCESS);
							object.setString("Successfully saved");	
							}	
				} else {
					object.setString("Invalid email address");
					object.setInteger(PMSConstants.STATUS_FAILED);
				}
			} else {
				object.setString("Invalid Token");
				object.setInteger(PMSConstants.STATUS_FAILED);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null)
				tx.rollback();
			object.setString("Error while getting Current User Email for forget password");
			object.setInteger(PMSConstants.STATUS_FAILED);
			logger.error("Error while getting Current User Email for forget password");
		} finally {
			session.flush();
			session.close();
		}
		return object;
	}

	private boolean deleteUserToken(Integer id) {
		boolean isDataDeleted = false;
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			PasswordRest object = (PasswordRest) session.get(PasswordRest.class, id);
			session.delete(object);
			tx.commit();
			isDataDeleted = true;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			logger.error("Exception while deleting token entry in PasswordRest  ... " + e);
		} finally {
			session.flush();
			session.close();
		}
		return isDataDeleted;
	}

	@Override
	public String getCurrentUserRoleId(String empCode) {
		Session session = this.sessionFactory.openSession();
		List<String> list = new ArrayList<String>();
		try {
			String hql = "select empType as empType from Employee as emp where emp.empCode =:empCode";
			Query query = session.createQuery(hql);
			query.setParameter("empCode", empCode);
			list = (List<String>) query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Error while getting Current User SubEmploye Count");
		} finally {
			session.flush();
			session.close();
		}
		return list.get(0);
	}

	@Override
	public Integer getEmployeeAppraisalYearDetails(Integer activeAppraisalYearId, String empCode) {
		Session session = this.sessionFactory.openSession();
		List<AppraisalYearDetails> list = new ArrayList<AppraisalYearDetails>();
		try {
			Criteria criteria = session.createCriteria(AppraisalYearDetails.class);
			criteria.add(Restrictions.eq("empCode", empCode));
			criteria.add(Restrictions.eq("appraisalYearId", activeAppraisalYearId));
			list = criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Error while getting Current getEmployeeAppraisakYearDetails  ID");
		} finally {
			session.flush();
			session.close();
		}
		return list.get(0).getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getFirstLevelManagerId(String empCode) {
		Session session = this.sessionFactory.openSession();
		List<Employee> list = new ArrayList<Employee>();
		try {
			Criteria criteria = session.createCriteria(Employee.class);
			criteria.add(Restrictions.eq("empCode",empCode));
			list = criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Error while getting Current User FirstLevelManagerId");
		} finally {
			session.flush();
			session.close();
		}
		return list.get(0).getFirstLevelSuperiorEmpId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getSecondLevelManagerId(String empCode) {
		Session session = this.sessionFactory.openSession();
		List<Employee> list = new ArrayList<Employee>();
		try {
			Criteria criteria = session.createCriteria(Employee.class);
			criteria.add(Restrictions.eq("empCode",empCode));
			list = criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Error while getting Current User SecondLevelManagerId");
		} finally {
			session.flush();
			session.close();
		}
		return list.get(0).getSecondLevelSuperiorEmpId();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResponseObject getEmployeeOverAllRating(Integer appraisalYearId, String empCode) {
		Session session = this.sessionFactory.openSession();
		ResponseObject res = new ResponseObject();
		List list = new ArrayList();
		try {
			String sql = "select sectionName as sectionName, weightage as weightage, midYearSelfRating as midYearSelfRating,"
					+ " midYearAppraisarRating as midYearAppraisarRating, finalYearSelfRating as finalYearSelfRating, finalYearAppraisarRating as finalYearAppraisarRating"
					+ " from KraDetails where empCode =:empCode and appraisalYearId =:appraisalYearId and isValidatedBySecondLevel =:isValidatedBySecondLevel";
			Query query = session.createQuery(sql);
			query.setParameter("empCode", empCode);
			query.setParameter("appraisalYearId", appraisalYearId);
			query.setParameter("isValidatedBySecondLevel", PMSConstants.STATUS_ACTIVE);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
//			 KRA Details Fetched
			list.add(query.list());
//			ExtraOrdinary Details Fetch
			list.add(getCurrentEmployeeExtraOrdinaryDetails(appraisalYearId,empCode));
//			Behavioural Competence Details Fetch
			list.add(getCurrentEmployeeBehaviouralCompetenceDetails(appraisalYearId,empCode));
			res.setObject(list);
			res.setInteger(PMSConstants.STATUS_SUCCESS);
			res.setString("Successfully saved");
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
	private List getCurrentEmployeeBehaviouralCompetenceDetails(Integer appraisalYearId, String empCode) {
		Session session = this.sessionFactory.openSession();
		List list = new ArrayList();
		try {
			String sql = "select bc.weightage as weightage, bcd.midYearSelfRating as midYearSelfRating,"
					+ " bcd.midYearAssessorRating as midYearAssessorRating, bcd.finalYearSelfRating as finalYearSelfRating,"
					+ " bcd.finalYearAssessorRating as finalYearAssessorRating"
					+ " from BehaviouralCompetenceDetails as bcd,BehaviouralCompetence as bc where "
					+ " bcd.empCode =:empCode and bcd.appraisalYearId =:appraisalYearId and bc.appraisalYearId =:appraisalYearId "
					+ " and bcd.behaviouralDetailsId = bc.id and bcd.isValidatedBySecondLevel =:isValidatedBySecondLevel";
			Query query = session.createQuery(sql);
			query.setParameter("empCode", empCode);
			query.setParameter("appraisalYearId", appraisalYearId);
			query.setParameter("isValidatedBySecondLevel", PMSConstants.STATUS_ACTIVE);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			list = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
		return list;
	}

	@SuppressWarnings("rawtypes")
	private List getCurrentEmployeeExtraOrdinaryDetails(Integer appraisalYearId, String empCode) {
		Session session = this.sessionFactory.openSession();
		List list = new ArrayList();
		try {
			String sql = "select weightage as weightage, finalYearSelfRating as finalYearSelfRating,"
					+ " finalYearAppraisarRating as finalYearAppraisarRating "
					+ " from ExtraOrdinaryDetails where empCode =:empCode and appraisalYearId =:appraisalYearId"
					+ " and isValidatedBySecondLevel =:isValidatedBySecondLevel";
			Query query = session.createQuery(sql);
			query.setParameter("empCode", empCode);
			query.setParameter("appraisalYearId", appraisalYearId);
			query.setParameter("isValidatedBySecondLevel", PMSConstants.STATUS_ACTIVE);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
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
	public String getEmployeeName(String currentUserName) {
		Session session = this.sessionFactory.openSession();
		List<Employee> list = new ArrayList<Employee>();
		try {
			Criteria criteria = session.createCriteria(Employee.class);
			criteria.add(Restrictions.eq("empCode", currentUserName));
			list = criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Error while getting Current User Name");
		} finally {
			session.flush();
			session.close();
		}
		return list.get(0).getEmpName();
	}

	@Override
	public boolean isCurrentEmployeeHead(String empCode,String organizationRole) {
		Session session = this.sessionFactory.openSession();
		boolean isCurrentEmployeeHRHead = false;
		try {
			String sql = "select organi.id from Employee as emp,OrganizationRoles as organi where emp.empCode =:empCode and emp.organizationRoleId = organi.id "
					+ " and organi.name =:name";
			Query query = session.createQuery(sql);
			query.setParameter("empCode", empCode);
			query.setParameter("name",organizationRole );
			if(query.list().size() > 0)
			{
				isCurrentEmployeeHRHead = true;
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Error while checking isCurrentEmployeeHRHead");
		} finally {
			session.flush();
			session.close();
		}
		return isCurrentEmployeeHRHead;
	}

	
}
