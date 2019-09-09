package com.sampark.PMS.dto;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EmployeeAppraisalDetails {
	@Id
	private Integer empId;
	private Integer appraisalYearId;
	private String empCode;
	private String empName;
	private String sessionYear;
	private String location;
	private String email;
	private String mobile;
	private Date dateOfJoining;
	private String departmentName;
	private String designationName;
	private Integer initializationYear;
	private Integer midYear;
	private Integer finalYear;
	private Integer secondLevelIsvisibleCheck;
	private Integer employeeApproval;
	private Integer employeeMidYearApproval;
	private Integer employeeIsvisible;
	private Integer firstLevelIsvisible;
	private Integer secondLevelIsvisible;
	private Integer acknowledgementCheck;
	
	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public Integer getAppraisalYearId() {
		return appraisalYearId;
	}

	public void setAppraisalYearId(Integer appraisalYearId) {
		this.appraisalYearId = appraisalYearId;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDesignationName() {
		return designationName;
	}

	public void setDesignationName(String designationName) {
		this.designationName = designationName;
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

	public Integer getSecondLevelIsvisibleCheck() {
		return secondLevelIsvisibleCheck;
	}

	public void setSecondLevelIsvisibleCheck(Integer secondLevelIsvisibleCheck) {
		this.secondLevelIsvisibleCheck = secondLevelIsvisibleCheck;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getAcknowledgementCheck() {
		return acknowledgementCheck;
	}

	public void setAcknowledgementCheck(Integer acknowledgementCheck) {
		this.acknowledgementCheck = acknowledgementCheck;
	}

	public String getSessionYear() {
		return sessionYear;
	}

	public void setSessionYear(String sessionYear) {
		this.sessionYear = sessionYear;
	}

	@Override
	public String toString() {
		return "EmployeeAppraisalDetails [empId=" + empId + ", appraisalYearId=" + appraisalYearId + ", empCode="
				+ empCode + ", empName=" + empName + ", sessionYear=" + sessionYear + ", location=" + location
				+ ", email=" + email + ", mobile=" + mobile + ", dateOfJoining=" + dateOfJoining + ", departmentName="
				+ departmentName + ", designationName=" + designationName + ", initializationYear=" + initializationYear
				+ ", midYear=" + midYear + ", finalYear=" + finalYear + ", secondLevelIsvisibleCheck="
				+ secondLevelIsvisibleCheck + ", employeeApproval=" + employeeApproval + ", employeeMidYearApproval="
				+ employeeMidYearApproval + ", employeeIsvisible=" + employeeIsvisible + ", firstLevelIsvisible="
				+ firstLevelIsvisible + ", secondLevelIsvisible=" + secondLevelIsvisible + ", acknowledgementCheck="
				+ acknowledgementCheck + "]";
	}	
}