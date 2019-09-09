package com.sampark.PMS.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "employee")
public class Employee {
	@Id
	@GeneratedValue
	@Column(name="ID")
	private Integer id;
	@Transient
	private Integer applicationRoleId;
	@Column(name="EMP_CODE")
	private String empCode;
	@Column(name="ROLE_ID")
	private Integer roleId;
	@Column(name="EMP_NAME")
	private String empName;
	@Column(name="EMP_TYPE")
	private String empType;
	@Column(name="MOBILE")
	private String mobile;
	@Column(name="COMPANY")
	private String company;
	@Column(name="EMAIL")
	private String email;
	@Column(name="DATE_OF_JOINING")
	private Date dateOfJoining;
	@Column(name="DATE_OF_BIRTH")
	private Date dateOfBirth;
	@Column(name="DEPARTMENT_ID")
	private Integer departmentId;
	@Column(name="QUALIFICATION")
	private String qualification;
	@Column(name="DESIGNATION_ID")
	private Integer designationId;
	@Column(name="ORGANIZATION_ROLE_ID")
	private Integer organizationRoleId;
	@Column(name="LOCATION")
	private String location;
	@Column(name="FIRST_LEVEL_SUPERIOR_EMP_ID")
	private String firstLevelSuperiorEmpId;
//	@Column(name="FIRST_LEVEL_SUPERIOR_NAME")
//	private String firstLevelSuperiorName;
	@Column(name="SECOND_LEVEL_SUPERIOR_EMP_ID")
	private String secondLevelSuperiorEmpId;
//	@Column(name="SECOND_LEVEL_SUPERIOR_NAME")
//	private String secondLevelSuperiorName;
	@Column(name="JOB_DESCRIPTION")
	private String jobDescription;
//	@Column(name="APPRAISAL_YEAR_ID")
//	private Integer appraisalYearId;
	@Column(name="CREATED_ON")
	private Date createdOn;
	@Column(name="MODIFIED_ON")
	private Date modifiedOn;
	@Column(name="CREATED_BY")
	private String createdBy;
	@Column(name="MODIFIED_BY")
	private String modifiedBy;
	@Column(name="DOCUMENT")
	private String document;
	@Column(name="FIELD_1")
	private String field1;
	@Column(name="FIELD_2")
	private String field2;
	@Column(name="FIELD_3")
	private String field3;
	@Column(name="FIELD_4")
	private String field4;
	@Column(name="FIELD_5")
	private String field5;
	@Column(name="FIELD_6")
	private String field6;
	@Column(name="FIELD_7")
	private String field7;
	@Column(name="FIELD_8")
	private String field8;
	@Column(name="FIELD_9")
	private String field9;
	@Column(name="FIELD_10")
	private String field10;
	@Column(name="STATUS")
	private Integer status;
	
	
	public String getDocument() {
		return document;
	}
	public void setDocument(String document) {
		this.document = document;
	}
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
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getDateOfJoining() {
		return dateOfJoining;
	}
	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	
	public String getQualification() {
		return qualification;
	}
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	public Integer getDesignationId() {
		return designationId;
	}
	public void setDesignationId(Integer designationId) {
		this.designationId = designationId;
	}
	public Integer getOrganizationRoleId() {
		return organizationRoleId;
	}
	public void setOrganizationRoleId(Integer organizationRoleId) {
		this.organizationRoleId = organizationRoleId;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getFirstLevelSuperiorEmpId() {
		return firstLevelSuperiorEmpId;
	}
	public void setFirstLevelSuperiorEmpId(String firstLevelSuperiorEmpId) {
		this.firstLevelSuperiorEmpId = firstLevelSuperiorEmpId;
	}
	
	public String getSecondLevelSuperiorEmpId() {
		return secondLevelSuperiorEmpId;
	}
	public void setSecondLevelSuperiorEmpId(String secondLevelSuperiorEmpId) {
		this.secondLevelSuperiorEmpId = secondLevelSuperiorEmpId;
	}
	public String getJobDescription() {
		return jobDescription;
	}
	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
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
	public String getField1() {
		return field1;
	}
	public void setField1(String field1) {
		this.field1 = field1;
	}
	public String getField2() {
		return field2;
	}
	public void setField2(String field2) {
		this.field2 = field2;
	}
	public String getField3() {
		return field3;
	}
	public void setField3(String field3) {
		this.field3 = field3;
	}
	public String getField4() {
		return field4;
	}
	public void setField4(String field4) {
		this.field4 = field4;
	}
	public String getField5() {
		return field5;
	}
	public void setField5(String field5) {
		this.field5 = field5;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getApplicationRoleId() {
		return applicationRoleId;
	}
	public void setApplicationRoleId(Integer applicationRoleId) {
		this.applicationRoleId = applicationRoleId;
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
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getEmpType() {
		return empType;
	}
	public void setEmpType(String empType) {
		this.empType = empType;
	}
	public String getField6() {
		return field6;
	}
	public void setField6(String field6) {
		this.field6 = field6;
	}
	public String getField7() {
		return field7;
	}
	public void setField7(String field7) {
		this.field7 = field7;
	}
	public String getField8() {
		return field8;
	}
	public void setField8(String field8) {
		this.field8 = field8;
	}
	public String getField9() {
		return field9;
	}
	public void setField9(String field9) {
		this.field9 = field9;
	}
	public String getField10() {
		return field10;
	}
	public void setField10(String field10) {
		this.field10 = field10;
	}
	
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	@Override
	public String toString() {
		return "Employee [id=" + id + ", applicationRoleId=" + applicationRoleId + ", empCode=" + empCode + ", roleId="
				+ roleId + ", empName=" + empName + ", empType=" + empType + ", mobile=" + mobile + ", company="
				+ company + ", email=" + email + ", dateOfJoining=" + dateOfJoining + ", dateOfBirth=" + dateOfBirth
				+ ", departmentId=" + departmentId + ", qualification=" + qualification + ", designationId="
				+ designationId + ", organizationRoleId=" + organizationRoleId + ", location=" + location
				+ ", firstLevelSuperiorEmpId=" + firstLevelSuperiorEmpId + ", secondLevelSuperiorEmpId="
				+ secondLevelSuperiorEmpId + ", jobDescription=" + jobDescription + ", createdOn=" + createdOn
				+ ", modifiedOn=" + modifiedOn + ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy
				+ ", document=" + document + ", field1=" + field1 + ", field2=" + field2 + ", field3=" + field3
				+ ", field4=" + field4 + ", field5=" + field5 + ", field6=" + field6 + ", field7=" + field7
				+ ", field8=" + field8 + ", field9=" + field9 + ", field10=" + field10 + ", status=" + status + "]";
	}
				
}
