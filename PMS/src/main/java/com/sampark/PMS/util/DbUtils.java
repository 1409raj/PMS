package com.sampark.PMS.util;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sampark.PMS.dto.AppraisalCountDetails;
import com.sampark.PMS.dto.AppraisalYear;
import com.sampark.PMS.dto.BehaviouralCompetence;
import com.sampark.PMS.dto.BehaviouralCompetenceDetails;
import com.sampark.PMS.dto.CareerAspirationsDetails;
import com.sampark.PMS.dto.Department;
import com.sampark.PMS.dto.DepartmentName;
import com.sampark.PMS.dto.Designation;
import com.sampark.PMS.dto.Employee;
import com.sampark.PMS.dto.EmployeeBasicDetails;
import com.sampark.PMS.dto.EmployeeBehaviouralCompetenceDetails;
import com.sampark.PMS.dto.EmployeeCodeEmail;
import com.sampark.PMS.dto.EmployeeDynamicFormDetails;
import com.sampark.PMS.dto.EmployeeEmails;
import com.sampark.PMS.dto.EmployeeExtraOrdinaryDetails;
import com.sampark.PMS.dto.EmployeeKRAData;
import com.sampark.PMS.dto.EmployeeKRADetails;
import com.sampark.PMS.dto.EmployeePromotions;
import com.sampark.PMS.dto.ExtraOrdinary;
import com.sampark.PMS.dto.ExtraOrdinaryDetails;
import com.sampark.PMS.dto.FinalReviewDetails;
import com.sampark.PMS.dto.OrganizationRoles;
import com.sampark.PMS.dto.Parameters;
import com.sampark.PMS.dto.Roles;
import com.sampark.PMS.dto.TrainingNeeds;
import com.sampark.PMS.dto.TrainingNeedsDetails;
import com.sampark.PMS.hibernate.AppraisalYearDAO;
import com.sampark.PMS.hibernate.AppraisalYearDetailsDAO;
import com.sampark.PMS.hibernate.BehaviouralCompetenceDAO;
import com.sampark.PMS.hibernate.CareerAspirationsDAO;
import com.sampark.PMS.hibernate.CommonDAO;
import com.sampark.PMS.hibernate.DepartmentDAO;
import com.sampark.PMS.hibernate.DesignationDAO;
import com.sampark.PMS.hibernate.EmployeeDAO;
import com.sampark.PMS.hibernate.EmployeeDynamicFormDetailsDAO;
import com.sampark.PMS.hibernate.EmployeeEmailDAO;
import com.sampark.PMS.hibernate.EmployeePromotionsDAO;
import com.sampark.PMS.hibernate.ExtraOrdinaryDAO;
import com.sampark.PMS.hibernate.FinalReviewDAO;
import com.sampark.PMS.hibernate.KraDetailsDAO;
import com.sampark.PMS.hibernate.OrganizationRolesDAO;
import com.sampark.PMS.hibernate.RolesDAO;
import com.sampark.PMS.hibernate.TrainingNeedsDAO;
import com.sampark.PMS.object.RequestObject;
import com.sampark.PMS.object.ResponseObject;

public class DbUtils {
	
	private static final ClassPathXmlApplicationContext  context = new ClassPathXmlApplicationContext("hibernate.xml");
	private static final CommonDAO commonDAO = context.getBean(CommonDAO.class);
	private static final EmployeeDAO employeeDAO = context.getBean(EmployeeDAO.class);
	private static final KraDetailsDAO kraDetailsDAO = context.getBean(KraDetailsDAO.class);
	private static final ExtraOrdinaryDAO extraOrdinaryDAO = context.getBean(ExtraOrdinaryDAO.class);
	private static final CareerAspirationsDAO careerAspirationsDAO = context.getBean(CareerAspirationsDAO.class);
	private static final TrainingNeedsDAO trainingNeedsDAO = context.getBean(TrainingNeedsDAO.class);
	private static final FinalReviewDAO finalReviewDAO = context.getBean(FinalReviewDAO.class);
	private static final BehaviouralCompetenceDAO behaviouralCompetenceDAO = context.getBean(BehaviouralCompetenceDAO.class);
	private static final AppraisalYearDetailsDAO appraisalYearDetailsDAO = context.getBean(AppraisalYearDetailsDAO.class);
	private static final AppraisalYearDAO appraisalYearDAO = context.getBean(AppraisalYearDAO.class);
	private static final DepartmentDAO departmentDAO = context.getBean(DepartmentDAO.class);
	private static final DesignationDAO designationDAO = context.getBean(DesignationDAO.class);
//	private static final QualificationDAO qualificationDAO = context.getBean(QualificationDAO.class);
	private static final OrganizationRolesDAO organizationRolesDAO = context.getBean(OrganizationRolesDAO.class);
	private static final RolesDAO rolesDAO = context.getBean(RolesDAO.class);
	private static final EmployeeDynamicFormDetailsDAO employeeDynamicFormDetailsDAO = context.getBean(EmployeeDynamicFormDetailsDAO.class);
	private static final EmployeeEmailDAO employeeEmailDAO = context.getBean(EmployeeEmailDAO.class);
	private static final EmployeePromotionsDAO employeePromotionsDAO = context.getBean(EmployeePromotionsDAO.class);
	
	public static List<Department> getAllDepartmentList() {
		return departmentDAO.getAllDepartmentList();
	}

	public static List<Designation> getAllDesignationList() {
		return designationDAO.getAllDesignationList();
	}

//	public static List<Qualification> getAllQualificationList() {
//		return qualificationDAO.getAllQualificationList();
//	}

	public static ResponseObject addEmployee(Employee object, Integer empId, String encryptedPassword, String userRandomPassword) {
		return employeeDAO.addEmployee(object,empId,encryptedPassword,userRandomPassword);
	}

	public static ResponseObject addKraDetails(RequestObject object, String empCode, String userType) {
		return kraDetailsDAO.addKraDetails(object,empCode,userType);
	}

	@SuppressWarnings("rawtypes")
	public static List getAllEmployeeCodeList() {
		return employeeDAO.getAllEmployeeCodeList();
	}
	
//	public static ResponseObject getLoggedUserDetails(String empCode) {
//		return commonDAO.getLoggedUserDetails(empCode);
//	}

	public static ResponseObject addExtraOrdinaryDetails(RequestObject object, String empCode, String userType) {
		return extraOrdinaryDAO.saveExtraOrdinaryDetails(object,empCode,userType);
	}

	public static ResponseObject addCareerAspirationsDetails(CareerAspirationsDetails object, String empCode, String managerType) {
		return careerAspirationsDAO.saveCareerAspirationsDetails(object,empCode,managerType);
	}

	public static ResponseObject addTrainingNeedsDetails(RequestObject object, String empCode, String managerType) {
		return trainingNeedsDAO.saveTrainingNeedsDetails(object,empCode,managerType);
	}

	public static ResponseObject addFinalReviewDetails(FinalReviewDetails object, String empId) {
		return finalReviewDAO.saveFinalReviewDetails(object,empId);
	}

	public static ResponseObject addBehavioralComptenceDetails(RequestObject object, String empCode, String userType) {
		return behaviouralCompetenceDAO.saveBehavioralComptenceDetails(object,empCode,userType);
	}

//	public static Long getCurrentUserSubEmployeeCount(String currentUserName) {
//		return commonDAO.getCurrentUserSubEmployeeCount(currentUserName);
//	}
	public static Long getCurrentUserSubEmployeeCount(String currentUserName, String superiorLevel) {
		return commonDAO.getCurrentUserSubEmployeeCount(currentUserName,superiorLevel);
	}
	public static ResponseObject getAllTeamMembers(String empCode, String type, Integer appraisalYearId) {
		return commonDAO.getAllTeamMembers(empCode,type,appraisalYearId);
	}

	public static ResponseObject getEmployeeAppraisalDetails(Integer appraisalYearId, String empCode) {
		return employeeDAO.getEmployeeAppraisalDetails(appraisalYearId,empCode);
	}

	public static ResponseObject getAppraisalYearDetails() {
		return appraisalYearDetailsDAO.getAppraisalYearDetails();
	}

//	public static ResponseObject appraisalYearKraSubmission(Integer appraisalYearId) {
//		return appraisalYearDetailsDAO.appraisalYearKraSubmission(appraisalYearId);
//	}

//	public static ResponseObject getAppraisalCycleDetails(Integer appraisalYearId) {
//		return appraisalYearDetailsDAO.getAppraisalCycleDetails(appraisalYearId);
//	}

//	public static ResponseObject appraisalYearMidYearSubmission(Integer appraisalYearId) {
//		return appraisalYearDetailsDAO.appraisalYearMidYearSubmission(appraisalYearId);
//	}

//	public static ResponseObject appraisalYearFinalYearSubmission(Integer appraisalYearId) {
//		return appraisalYearDetailsDAO.appraisalYearFinalYearSubmission(appraisalYearId);
//	}

	public static ResponseObject getUserAppraisalDetails(String empCode, Integer appraisalYearId, String type) {
		return appraisalYearDetailsDAO.getUserAppraisalDetails(empCode,appraisalYearId,type);
	}

   public static List<OrganizationRoles> getAllOrganizationRolesList(Integer departmentId) {
		return organizationRolesDAO.getAllOrganizationRolesList(departmentId);
	}

	public static List<Parameters> getAllParameters() {
		return commonDAO.getAllParameters();
	}

	public static ResponseObject getAllParametersData(Integer id) {
		return commonDAO.getAllParametersData(id);
	}
	public static List<BehaviouralCompetence> getCurrentBehavioralCompetenceList(Integer appraisalYearId, String type)
	 {
		return behaviouralCompetenceDAO.getCurrentBehavioralCompetenceList(appraisalYearId,type);	 
	 }

	public static ResponseObject saveDepartment(RequestObject object) {
		return departmentDAO.saveDepartment(object);
	}

	public static ResponseObject saveDesignation(RequestObject object) {
		return designationDAO.saveDesignation(object);
	}

//	public static ResponseObject saveQualification(RequestObject object) {
//		return qualificationDAO.saveQualification(object);
//	}

	public static ResponseObject saveOrganizationRoles(RequestObject object) {
		return organizationRolesDAO.saveOrganizationRoles(object);
	}

	public static List<Roles> getAllApplicationRolesList() {
		return rolesDAO.getAllApplicationRolesList();
	}

	public static ResponseObject saveApplicationRoles(RequestObject object) {
		return rolesDAO.saveApplicationRoles(object);
	}

	public static ResponseObject deleteDepartment(Integer departmentId) {
		return departmentDAO.deleteDepartment(departmentId);
	}

	public static ResponseObject deleteDesignation(Integer designationId) {
		return designationDAO.deleteDesignation(designationId);
	}

//	public static ResponseObject deleteQualification(Integer qualificationId) {
//		return qualificationDAO.deleteQualification(qualificationId);
//	}

	public static ResponseObject deleteOrganizationRoles(Integer organizationRolesId, Integer departmentId) {
		return organizationRolesDAO.deleteOrganizationRoles(organizationRolesId,departmentId);
	}

	public static ResponseObject deleteApplicationRoles(Integer rolesId) {
		return rolesDAO.deleteApplicationRoles(rolesId);
	}

	public static List<EmployeeDynamicFormDetails> getAllEmployeeDynamicFormList() {
		return employeeDynamicFormDetailsDAO.getAllEmployeeDynamicFormList();
	}

	public static ResponseObject deleteColumn(Integer id, Integer appraisalYearId) {
		return employeeDynamicFormDetailsDAO.deleteColumn(id,appraisalYearId);
	}

	public static ResponseObject saveColumn(String description, String name, Integer id, Integer appraisalYearId) {
		return employeeDynamicFormDetailsDAO.saveColumn(description,name,id,appraisalYearId);
	}
	public static ResponseObject activateColumn(Integer appraisalYearId, Integer id) {
		return employeeDynamicFormDetailsDAO.activateColumn(appraisalYearId,id);
	}
	public static List<EmployeeDynamicFormDetails> getCurrentEmployeeDynamicFormList(Integer appraisalYearId) {
		return employeeDynamicFormDetailsDAO.getCurrentEmployeeDynamicFormList(appraisalYearId);
	}

	public static ResponseObject getAllEmployeeList() {
		return employeeDAO.getAllEmployeeList();
	}
	
	public static ResponseObject getAllDetailsEmployeeList() {
		return employeeDAO.getAllDetailsEmployeeList();
	}
	
	public static ResponseObject getAllDetailsOfDeletedEmployeeList() {
		return employeeDAO.getAllDetailsOfDeletedEmployeeList();
	}

	public static ResponseObject saveBehavioralComptence(RequestObject object) {
		return behaviouralCompetenceDAO.saveBehavioralComptence(object);
	}

	public static ResponseObject deleteBehavioralComptence(Integer id, Integer appraisalYearId, String type) {
		return behaviouralCompetenceDAO.deleteBehavioralComptence(id,appraisalYearId,type);
	}

//	public static List<TrainingNeeds> getTrainingNeedsList() {
//		return trainingNeedsDAO.getTrainingNeedsList();
//	}
//
//	public static ResponseObject saveTrainingNeeds(RequestObject object) {
//		return trainingNeedsDAO.saveTrainingNeeds(object);
//	}
//
//	public static ResponseObject deleteTrainingNeeds(Integer id) {
//		return trainingNeedsDAO.deleteTrainingNeeds(id);
//	}

	public static List<ExtraOrdinary> getAllExtraOrdinary() {
		return extraOrdinaryDAO.getAllExtraOrdinary();
	}

	public static ResponseObject saveExtraOrdinary(RequestObject object) {
		return extraOrdinaryDAO.saveExtraOrdinary(object);
	}

	public static ResponseObject deleteExtraOrdinary(Integer id) {
		return extraOrdinaryDAO.deleteExtraOrdinary(id);
	}

	public static void addEmployeeBulk(List<Employee> object, HttpServletResponse response) throws IOException {
		 employeeDAO.addEmployeeBulk(object,response);
	}

	public static ResponseObject getCurrentEmployeeKRADetails(String empCode, Integer appraisalYearId, String kraScreen) {
		return kraDetailsDAO.getCurrentEmployeeKRADetails(empCode,appraisalYearId,kraScreen);
	}

	public static List<ExtraOrdinary> getCurrentExtraOrdinary(Integer appraisalYearId) {
		return extraOrdinaryDAO.getCurrentExtraOrdinary(appraisalYearId);
	}

	public static List<BehaviouralCompetenceDetails> getCurrentEmployeeBehavioralComptenceDetails(String empCode, Integer appraisalYearId) {
		return behaviouralCompetenceDAO.getCurrentEmployeeBehavioralComptenceDetails(empCode,appraisalYearId);
	}

	public static List<TrainingNeedsDetails> getCurrentEmployeeTrainingNeedsDetails(String empCode, Integer appraisalYearId) {
		return trainingNeedsDAO.getCurrentEmployeeTrainingNeedsDetails(empCode,appraisalYearId);
	}

	public static List<CareerAspirationsDetails> getCurrentEmployeeCareerAspirationDetails(String empCode, Integer appraisalYearId) {
		return careerAspirationsDAO.getCurrentEmployeeCareerAspirationDetails(empCode,appraisalYearId);
	}

	public static List<ExtraOrdinaryDetails> getCurrentEmployeeExtraOrdinaryDetails(Integer appraisalYearId, String empCode) {
		return extraOrdinaryDAO.getCurrentEmployeeExtraOrdinaryDetails(appraisalYearId,empCode);
	}

//	public static List<TrainingNeeds> getCurrentTrainingNeeds(Integer appraisalYearId) {
//		return trainingNeedsDAO.getCurrentTrainingNeeds(appraisalYearId);
//	}
//	public static ResponseObject activateTrainingNeeds(Integer id, Integer appraisalYearId) {
//		return trainingNeedsDAO.activateTrainingNeeds(id,appraisalYearId);
//	}
	public static ResponseObject behavioralDataCarryForward(Integer appraisalYearId, String type) {
		return behaviouralCompetenceDAO.behavioralDataCarryForward(appraisalYearId,type);
	}

	public static List<AppraisalYear> getAppraisalYearList() {
		return appraisalYearDAO.getAppraisalYearList();
	}

	public static ResponseObject saveAppraisalYear(RequestObject object) {
		return appraisalYearDAO.saveAppraisalYear(object);
	}

	public static ResponseObject deleteAppraisalYear(Integer id) {
		return appraisalYearDAO.deleteAppraisalYear(id);
	}

	public static ResponseObject updateApplicationAppraisalYear(Integer id, List<Integer> list) {
		return appraisalYearDAO.updateApplicationAppraisalYear(id,list);
	}

	public static ResponseObject activateExtraOrdinary(Integer id, Integer appraisalYearId) {
		return extraOrdinaryDAO.activateExtraOrdinary(id,appraisalYearId);
	}

	public static String getApplicationVersion() {
		return "1.0";
	}

	public static ResponseObject deleteEmployee(Integer id) {
		return employeeDAO.deleteEmployee(id);
	}

	public static ResponseObject getCurrentEmployeeDetails(Integer empId) {
		return employeeDAO.getCurrentEmployeeDetails(empId);
	}

	public static Integer getCurrentUserId(String empCode) {
		return commonDAO.getCurrentUserId(empCode);
	}

	public static ResponseObject appraisalCycleStart(RequestObject object) {
		return appraisalYearDetailsDAO.appraisalCycleStart(object);
	}

	public static List<EmployeeCodeEmail> getAllEmployeeCode(Date eligibilityDate, Integer appraisalYearId) {
		return employeeDAO.getAllEmployeeCode(eligibilityDate,appraisalYearId);
	}

	public static List<Integer> getAllEmployeeListId(Integer appraisalYearId, Date eligibilityDate) {
		return employeeDAO.getAllEmployeeListId(appraisalYearId,eligibilityDate);
	}

	public static Integer getActiveAppraisalYearId() {
		return appraisalYearDAO.getActiveAppraisalYearId();
	}

	public static ResponseObject isUserFirstTime(String empCode, Integer appraisalYearId) {
		return commonDAO.isUserFirstTime(empCode,appraisalYearId);
	}

	public static ResponseObject changeUserPassword(String currentPassword, String password) {
		return commonDAO.changeUserPassword(currentPassword,password);
	}

	public static ResponseObject getEmployeeAppraisalList(Integer appraisalYearId) {
		return appraisalYearDetailsDAO.getEmployeeAppraisalList(appraisalYearId);
	}

	public static ResponseObject updateEmployeeAppraisal(Integer status, Integer appraisalYearDetailsId, Integer appraisalYearId) {
		return appraisalYearDetailsDAO.updateEmployeeAppraisal(status,appraisalYearDetailsId,appraisalYearId);
	}

	public static ResponseObject forgetPassword(String email) {
		return commonDAO.forgetPassword(email);
	}

	public static ResponseObject resetPassword(String password, String token, String email)  {
		return commonDAO.resetPassword(password,token,email);
	}

	public static String getCurrentUserRoleId(String empCode) {
		return commonDAO.getCurrentUserRoleId(empCode);
	}

	public static ResponseObject sendToManager(String managerType,String buttonType, String empCode, Integer appraisalYearId) {
		return appraisalYearDetailsDAO.sendToManager(managerType,buttonType,empCode,appraisalYearId);
	}

	public static Integer getEmployeeAppraisalYearDetails(Integer activeAppraisalYearId, String empCode) {
		return commonDAO.getEmployeeAppraisalYearDetails(activeAppraisalYearId,empCode);
	}

	public static String getFirstLevelManagerId(String empCode) {
		return commonDAO.getFirstLevelManagerId(empCode);
	}

	public static List<EmployeeKRAData> getValidatedKRADetails(String empCode, Integer appraisalYearId, String managerType) {
		return kraDetailsDAO.getValidatedKRADetails(empCode,appraisalYearId,managerType);
	}

	public static List<Integer> getValidatedBehaviouralCompetenceDetails(String empCode, Integer appraisalYearId,String managerType) {
		return behaviouralCompetenceDAO.getValidatedBehaviouralCompetenceDetails(empCode,appraisalYearId,managerType);
	}

	public static List<Integer> getValidatedExtraOrdinaryDetails(String empCode, Integer appraisalYearId,String managerType) {
		return extraOrdinaryDAO.getValidatedExtraOrdinaryDetails(empCode,appraisalYearId,managerType);
	}

	public static ResponseObject getAllSubEmployeeList(String empCode, String type, Integer appraisalYearId) {
		return employeeDAO.getAllSubEmployeeList(empCode,type,appraisalYearId);
	}

	public static ResponseObject employeeAcknowledgement(String empCode, Integer appraisalYearId) {
		return appraisalYearDetailsDAO.employeeAcknowledgement(empCode,appraisalYearId);
	}

	public static List<Integer> getValidatedTrainingNeedsDetails(String empCode, Integer activeAppraisalYearID,
			String managerType) {
		return trainingNeedsDAO.getValidatedTrainingNeedsDetails(empCode,activeAppraisalYearID,managerType);
	}

	public static List<EmployeeEmails> getEmployeeEmailList() {
		return employeeEmailDAO.getEmployeeEmailList();
	}

	public static void updateEmployeeEmailDetails(EmployeeEmails employeeEmails) {
		 employeeEmailDAO.updateEmployeeEmailDetails(employeeEmails);
	}

	public static Employee getCurrentEmployeeData(String empCode) {
		return employeeDAO.getCurrentEmployeeData(empCode);
	}

	public static List<Integer> getValidatedCareerAspirationDetails(String empCode, Integer activeAppraisalYearID,
			String managerType) {
		return careerAspirationsDAO.getValidatedCareerAspirationDetails(empCode,activeAppraisalYearID,managerType);
	}

	public static List<EmployeeCodeEmail> getAllEmployeeAppraisalDetailsList(Integer appraisalYearId, Date eligibilityDate) {
		return employeeDAO.getAllEmployeeAppraisalDetailsList(appraisalYearId,eligibilityDate);
	}

	@SuppressWarnings("rawtypes")
	public static List getSubEmployeeAppraisalCountDetails(String empCode, Integer appraisalYearId, String appraisalStage, String stage) {
		return employeeDAO.getSubEmployeeAppraisalCountDetails(empCode,appraisalYearId,appraisalStage,stage);
	}

	public static ResponseObject getAppraisalPendingEmployeeDetails(String empCode, Integer appraisalYearId, String managerType, String appraisalStage) {
		return appraisalYearDetailsDAO.getAppraisalPendingEmployeeDetails(empCode,appraisalYearId,managerType,appraisalStage);
	}

	public static String getAppraiserName(String empCode) {
		return employeeDAO.getAppraiserName(empCode);
	}

	@SuppressWarnings("rawtypes")
	public static List getOverAllAppraisalCountDetails(Integer appraisalYearId, String appraisalStage) {
		return appraisalYearDetailsDAO.getOverAllAppraisalCountDetails(appraisalYearId,appraisalStage);
	}

	@SuppressWarnings("rawtypes")
	public static List getOverAllDepartmentAppraisalCountDetails(Integer appraisalYearId, String appraisalStage) {
		return appraisalYearDetailsDAO.getOverAllDepartmentAppraisalCountDetails(appraisalYearId,appraisalStage);
	}

	public static ResponseObject getALLAppraisalPendingEmployeeDetails(Integer appraisalYearId, String type, Integer  departmentId) {
		return appraisalYearDetailsDAO.getALLAppraisalPendingEmployeeDetails(appraisalYearId,type,departmentId);
	}

	@SuppressWarnings("rawtypes")
	public static List getAllDepartmentHRDashBoardDetails(Integer appraisalYearId) {
		return appraisalYearDetailsDAO.getAllDepartmentHRDashBoardDetails(appraisalYearId);
	}

	public static List<DepartmentName> getAllDepartmentIdList() {
		return departmentDAO.getAllDepartmentIdList();
	}

	public static ResponseObject getSelectedAppraisalYearDetails(String appraisalStage) {
		return appraisalYearDetailsDAO.getSelectedAppraisalYearDetails(appraisalStage);
	}

	public static ResponseObject saveEmployeePromotiondetails(EmployeePromotions object) {
		return employeePromotionsDAO.saveEmployeePromotiondetails(object);
	}

	public static String getSecondLevelManagerId(String empCode) {
		return commonDAO.getSecondLevelManagerId(empCode);
	}
	
	public static ResponseObject getEmployeePromotionDetails(Integer appraisalYearId, String empCode) {
		return employeeDAO.getEmployeePromotionDetails(appraisalYearId,empCode);
	}

	public static List<EmployeePromotions> getCurrentEmployeePromotionDetails(Integer appraisalYearId, String empCode) {
		return employeePromotionsDAO.getCurrentEmployeePromotionDetails(appraisalYearId,empCode);
	}

	public static ResponseObject rejectEmployeePromotiondetails(RequestObject object) {
		return employeePromotionsDAO.rejectEmployeePromotiondetails(object);
	}

	public static String getEmployeeType(String empCode) {
		return employeeDAO.getEmployeeType(empCode);
	}

	public static Integer isUserAcknowledged(String empCode, Integer appraisalYearId) {
		return appraisalYearDetailsDAO.isUserAcknowledged(empCode,appraisalYearId);
	}

	public static ResponseObject getOverAllRatingSubEmployeeList(String empCode, String type, Integer appraisalYearId) {
		return employeeDAO.getOverAllRatingSubEmployeeList(empCode,type,appraisalYearId);
	}

	public static ResponseObject getEmployeeOverAllRating(Integer appraisalYearId, String empCode) {
		return commonDAO.getEmployeeOverAllRating(appraisalYearId,empCode);
	}

	public static ResponseObject changeEmployeeAppraisalStage(RequestObject object) {
		return appraisalYearDetailsDAO.changeEmployeeAppraisalStage(object);
	}

	public static ResponseObject carryForwardEmployeeKRADATAToCurrentYear(RequestObject object) {
		return appraisalYearDetailsDAO.carryForwardEmployeeKRADATAToCurrentYear(object);
	}

	public static ResponseObject forwardEmployeeKRADATAToCurrentYear(String empCode, Integer appraisalYearId) {
		return kraDetailsDAO.forwardEmployeeKRADATAToCurrentYear(empCode,appraisalYearId);
		
	}

	public static Integer getPrevioueYearId() {
		return appraisalYearDAO.getPrevioueYearId();
	}

	public static ResponseObject employeeApprovalProcess(RequestObject object) {
		return appraisalYearDetailsDAO.employeeApprovalProcess(object);
	}

	public static String getEmployeeName(String currentUserName) {
		return commonDAO.getEmployeeName(currentUserName);
	}

	public static boolean isCurrentEmployeeHead(String currentUserName, String organizationRole) {
		return commonDAO.isCurrentEmployeeHead(currentUserName,organizationRole);
	}

	public static ResponseObject teamRating(RequestObject object) {
		return appraisalYearDetailsDAO.teamRating(object);
	}

	public static List<EmployeeKRADetails> getEmployeeKRADetails(String empCode, Integer appraisalYearId) {
		return kraDetailsDAO.getEmployeeKRADetails(empCode,appraisalYearId);
	}

	public static List<EmployeeBehaviouralCompetenceDetails> getEmployeeBehaviouralCompetenceDetails(String empCode,
			Integer appraisalYearId) {
		return behaviouralCompetenceDAO.getEmployeeBehaviouralCompetenceDetails(empCode,appraisalYearId);
	}

	public static List<EmployeeExtraOrdinaryDetails> getEmployeeExtraOrdinaryDetails(String empCode,
			Integer appraisalYearId) {
		return extraOrdinaryDAO.getEmployeeExtraOrdinaryDetails(empCode,appraisalYearId);
	}

	public static ResponseObject getAllEmployeeListData() {
			 return employeeDAO.getAllEmployeeListData();
	}
	public static ResponseObject getAllActiveEmployeeListData() {
		 return employeeDAO.getAllActiveEmployeeListData();
}
	public static ResponseObject getAllDeletedEmployeeList() {
		 return employeeDAO.getAllDeletedEmployeeList();
}
	
	
	public static ResponseObject getAllDepartmentWiseKRAList(Integer departmentId, Integer appraisalYearId, String empCode) {
		return kraDetailsDAO.getAllDepartmentWiseKRAList(departmentId,appraisalYearId,empCode);
	}

	public static List<EmployeeBasicDetails> getEmployeeBasicDetails(Integer departmentId, Integer appraisalYearId, String empCode) {
		return employeeDAO.getEmployeeBasicDetails(departmentId,appraisalYearId,empCode);
	}

//	public static ResponseObject getNewKRADetails(String empCode, Integer appraisalYearId) {
//		return kraDetailsDAO.getNewKRADetails(empCode,appraisalYearId);
//	}

	public static ResponseObject addNewKraDetails(RequestObject object, String empCode, String userType) {
		return kraDetailsDAO.addNewKraDetails(object,empCode,userType);
	}

	public static ResponseObject getCurrentEmployeeOldKRADetails(String empCode, Integer appraisalYearId) {
		return kraDetailsDAO.getCurrentEmployeeOldKRADetails(empCode,appraisalYearId);
	}

	public static ResponseObject getCurrentEmployeeNewKRADetails(String empCode, Integer appraisalYearId) {
		return kraDetailsDAO.getCurrentEmployeeNewKRADetails(empCode,appraisalYearId);
	}

	public static List<Integer> getValidatedNewKRADetails(String empCode, Integer appraisalYearId,
			String managerType) {
		return kraDetailsDAO.getValidatedNewKRADetails(empCode,appraisalYearId,managerType);
	}

	public static ResponseObject copyOldKRAtoNewKRA(RequestObject object) {
		return kraDetailsDAO.copyOldKRAtoNewKRA(object);
	}

	
}
