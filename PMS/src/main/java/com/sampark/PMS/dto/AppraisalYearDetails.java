package com.sampark.PMS.dto;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "appraisal_year_details")
public class AppraisalYearDetails {
	@Id
	@GeneratedValue
	@Column(name="ID")
	private Integer id;
	@Column(name="EMP_CODE")
	private String empCode;
	@Column(name="EMAIL")
	private String email;
	@Column(name="APPRAISAL_YEAR_ID")
	private Integer appraisalYearId;
	@Column(name="INITIALIZATION_YEAR")
	private Integer initializationYear;
	@Column(name="MID_YEAR")
	private Integer midYear;
	@Column(name="FINAL_YEAR")
	private Integer finalYear;
	@Column(name="EMPLOYEE_ISVISIBILE")
	private Integer employeeIsvisible;
	@Column(name="FIRSTLEVEL_ISVISIBILE")
	private Integer firstLevelIsvisible;
	@Column(name="SECONDLEVEL_ISVISIBILE")
	private Integer secondLevelIsvisible;
	@Column(name="SECONDLEVEL_ISVISIBILE_CHECK")
	private Integer secondLevelIsvisibleCheck;
	@Column(name="CREATED_ON")
	private Date createdOn;
	@Column(name="MODIFIED_ON")
	private Date modifiedOn;
	@Column(name="CREATED_BY")
	private String createdBy;
	@Column(name="MODIFIED_BY")
	private String modifiedBy;
	@Column(name="INITIALIZATION_START_DATE")
	private Date initializationStartDate;
	@Column(name="INITIALIZATION_END_DATE")
	private Date initializationEndDate;
	@Column(name="MID_YEAR_START_DATE")
	private Date midYearStartDate;
	@Column(name="MID_YEAR_END_DATE")
	private Date midYearEndDate;
	@Column(name="FINAL_YEAR_START_DATE")
	private Date finalYearStartDate;
	@Column(name="FINAL_YEAR_END_DATE")
	private Date finalYearEndDate;
	@Column(name="STATUS")
	private Integer status;
	@Column(name="ACKNOWLEDGEMENT_CHECK")  // For First Time ACKNOWLEDGEMENT_CHECK  
	private Integer acknowledgementCheck ;
	@Column(name="KRA_FORWARD_CHECK")  // For First Time KRA_FORWARD_CHECK
	private Integer kraForwardCheck ;
	@Column(name="FIRST_MANAGER_GOAL_APPROVAL")  
	private Integer firstManagerGoalApproval ;
	@Column(name="FIRST_MANAGER_MID_YEAR_APPROVAL")  
	private Integer firstManagerMidYearApproval ;
	@Column(name="FIRST_MANAGER_YEAR_END_ASSESSMENT_APPROVAL")  
	private Integer firstManagerYearEndAssessmentApproval ;
	@Column(name="EMPLOYEE_APPROVAL")  
	private Integer employeeApproval ;
	@Column(name="EMPLOYEE_MID_YEAR_APPROVAL")  
	private Integer employeeMidYearApproval ;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getAppraisalYearId() {
		return appraisalYearId;
	}
	public void setAppraisalYearId(Integer appraisalYearId) {
		this.appraisalYearId = appraisalYearId;
	}
	public Integer getInitializationYear() {
		return initializationYear;
	}
	public void setInitializationYear(Integer initializationYear) {
		this.initializationYear = initializationYear;
	}
	public Integer getMidYear() {
		return midYear;
	}
	public void setMidYear(Integer midYear) {
		this.midYear = midYear;
	}
	public Integer getFinalYear() {
		return finalYear;
	}
	public void setFinalYear(Integer finalYear) {
		this.finalYear = finalYear;
	}
	public Integer getEmployeeIsvisible() {
		return employeeIsvisible;
	}
	public void setEmployeeIsvisible(Integer employeeIsvisible) {
		this.employeeIsvisible = employeeIsvisible;
	}
	public Integer getFirstLevelIsvisible() {
		return firstLevelIsvisible;
	}
	public void setFirstLevelIsvisible(Integer firstLevelIsvisible) {
		this.firstLevelIsvisible = firstLevelIsvisible;
	}
	public Integer getSecondLevelIsvisible() {
		return secondLevelIsvisible;
	}
	public void setSecondLevelIsvisible(Integer secondLevelIsvisible) {
		this.secondLevelIsvisible = secondLevelIsvisible;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public Date getModifiedOn() {
		return modifiedOn;
	}
	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getInitializationStartDate() {
		return initializationStartDate;
	}
	public void setInitializationStartDate(Date initializationStartDate) {
		this.initializationStartDate = initializationStartDate;
	}
	public Date getInitializationEndDate() {
		return initializationEndDate;
	}
	public void setInitializationEndDate(Date initializationEndDate) {
		this.initializationEndDate = initializationEndDate;
	}
	public Date getMidYearStartDate() {
		return midYearStartDate;
	}
	public void setMidYearStartDate(Date midYearStartDate) {
		this.midYearStartDate = midYearStartDate;
	}
	public Date getMidYearEndDate() {
		return midYearEndDate;
	}
	public void setMidYearEndDate(Date midYearEndDate) {
		this.midYearEndDate = midYearEndDate;
	}
	public Date getFinalYearStartDate() {
		return finalYearStartDate;
	}
	public void setFinalYearStartDate(Date finalYearStartDate) {
		this.finalYearStartDate = finalYearStartDate;
	}
	public Date getFinalYearEndDate() {
		return finalYearEndDate;
	}
	public void setFinalYearEndDate(Date finalYearEndDate) {
		this.finalYearEndDate = finalYearEndDate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getSecondLevelIsvisibleCheck() {
		return secondLevelIsvisibleCheck;
	}
	public void setSecondLevelIsvisibleCheck(Integer secondLevelIsvisibleCheck) {
		this.secondLevelIsvisibleCheck = secondLevelIsvisibleCheck;
	}
	public Integer getAcknowledgementCheck() {
		return acknowledgementCheck;
	}
	public void setAcknowledgementCheck(Integer acknowledgementCheck) {
		this.acknowledgementCheck = acknowledgementCheck;
	}
	public Integer getKraForwardCheck() {
		return kraForwardCheck;
	}
	public void setKraForwardCheck(Integer kraForwardCheck) {
		this.kraForwardCheck = kraForwardCheck;
	}
	public Integer getFirstManagerGoalApproval() {
		return firstManagerGoalApproval;
	}
	public void setFirstManagerGoalApproval(Integer firstManagerGoalApproval) {
		this.firstManagerGoalApproval = firstManagerGoalApproval;
	}
	public Integer getFirstManagerMidYearApproval() {
		return firstManagerMidYearApproval;
	}
	public void setFirstManagerMidYearApproval(Integer firstManagerMidYearApproval) {
		this.firstManagerMidYearApproval = firstManagerMidYearApproval;
	}
	public Integer getFirstManagerYearEndAssessmentApproval() {
		return firstManagerYearEndAssessmentApproval;
	}
	public void setFirstManagerYearEndAssessmentApproval(Integer firstManagerYearEndAssessmentApproval) {
		this.firstManagerYearEndAssessmentApproval = firstManagerYearEndAssessmentApproval;
	}
	public Integer getEmployeeApproval() {
		return employeeApproval;
	}
	public void setEmployeeApproval(Integer employeeApproval) {
		this.employeeApproval = employeeApproval;
	}
	public Integer getEmployeeMidYearApproval() {
		return employeeMidYearApproval;
	}
	public void setEmployeeMidYearApproval(Integer employeeMidYearApproval) {
		this.employeeMidYearApproval = employeeMidYearApproval;
	}
	@Override
	public String toString() {
		return "AppraisalYearDetails [id=" + id + ", empCode=" + empCode + ", email=" + email + ", appraisalYearId="
				+ appraisalYearId + ", initializationYear=" + initializationYear + ", midYear=" + midYear
				+ ", finalYear=" + finalYear + ", employeeIsvisible=" + employeeIsvisible + ", firstLevelIsvisible="
				+ firstLevelIsvisible + ", secondLevelIsvisible=" + secondLevelIsvisible
				+ ", secondLevelIsvisibleCheck=" + secondLevelIsvisibleCheck + ", createdOn=" + createdOn
				+ ", modifiedOn=" + modifiedOn + ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy
				+ ", initializationStartDate=" + initializationStartDate + ", initializationEndDate="
				+ initializationEndDate + ", midYearStartDate=" + midYearStartDate + ", midYearEndDate="
				+ midYearEndDate + ", finalYearStartDate=" + finalYearStartDate + ", finalYearEndDate="
				+ finalYearEndDate + ", status=" + status + ", acknowledgementCheck=" + acknowledgementCheck
				+ ", kraForwardCheck=" + kraForwardCheck + ", firstManagerGoalApproval=" + firstManagerGoalApproval
				+ ", firstManagerMidYearApproval=" + firstManagerMidYearApproval
				+ ", firstManagerYearEndAssessmentApproval=" + firstManagerYearEndAssessmentApproval
				+ ", employeeApproval=" + employeeApproval + ", employeeMidYearApproval=" + employeeMidYearApproval
				+ "]";
	}
	
	}
