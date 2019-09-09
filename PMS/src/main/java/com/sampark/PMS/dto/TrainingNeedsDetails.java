package com.sampark.PMS.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "training_needs_details")
public class TrainingNeedsDetails {
	@Id
	@GeneratedValue
	@Column(name="ID")
	private Integer id;
	@Column(name="EMP_CODE")
	private String empCode;
	@Column(name="APPRAISAL_YEAR_ID")
	private Integer appraisalYearId;
	@Column(name="TRAINING_TOPIC")
	private String trainingTopic;
	@Column(name="TRAINING_REASONS")
	private String trainingReasons;
	@Column(name="MAN_HOURS")
	private String manHours;
	@Column(name="APPROVED_REJECT")
	private String approvedReject;	
	@Column(name="REMARKS")
	private String remarks;
	@Transient
	private String departmentName;
	@Transient
	private String designationName;
	@Transient
	private String empName;
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
	@Column(name="CREATED_ON")
	private Date createdOn;
	@Column(name="MODIFIED_ON")
	private Date modifiedOn;
	@Column(name="CREATED_BY")
	private String createdBy;
	@Column(name="MODIFIED_BY")
	private String modifiedBy;
	@Column(name="STATUS")
	private Integer status;
	@Column(name="ISVALIDATEDBY_EMPLOYEE")
	private Integer isValidatedByEmployee;
	@Column(name="ISVALIDATEDBY_FIRSTLEVEL")
	private Integer isValidatedByFirstLevel;
	@Column(name="ISVALIDATEDBY_SECONDLEVEL")
	private Integer isValidatedBySecondLevel;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getAppraisalYearId() {
		return appraisalYearId;
	}
	public void setAppraisalYearId(Integer appraisalYearId) {
		this.appraisalYearId = appraisalYearId;
	}
	public String getTrainingTopic() {
		return trainingTopic;
	}
	public void setTrainingTopic(String trainingTopic) {
		this.trainingTopic = trainingTopic;
	}
	public String getTrainingReasons() {
		return trainingReasons;
	}
	public void setTrainingReasons(String trainingReasons) {
		this.trainingReasons = trainingReasons;
	}
	public String getManHours() {
		return manHours;
	}
	public void setManHours(String manHours) {
		this.manHours = manHours;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getApprovedReject() {
		return approvedReject;
	}
	public void setApprovedReject(String approvedReject) {
		this.approvedReject = approvedReject;
	}
	public Integer getIsValidatedByEmployee() {
		return isValidatedByEmployee;
	}
	public void setIsValidatedByEmployee(Integer isValidatedByEmployee) {
		this.isValidatedByEmployee = isValidatedByEmployee;
	}
	public Integer getIsValidatedByFirstLevel() {
		return isValidatedByFirstLevel;
	}
	public void setIsValidatedByFirstLevel(Integer isValidatedByFirstLevel) {
		this.isValidatedByFirstLevel = isValidatedByFirstLevel;
	}
	public Integer getIsValidatedBySecondLevel() {
		return isValidatedBySecondLevel;
	}
	public void setIsValidatedBySecondLevel(Integer isValidatedBySecondLevel) {
		this.isValidatedBySecondLevel = isValidatedBySecondLevel;
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
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	@Override
	public String toString() {
		return "TrainingNeedsDetails [id=" + id + ", empCode=" + empCode + ", appraisalYearId=" + appraisalYearId
				+ ", trainingTopic=" + trainingTopic + ", trainingReasons=" + trainingReasons + ", manHours=" + manHours
				+ ", approvedReject=" + approvedReject + ", remarks=" + remarks + ", departmentName=" + departmentName
				+ ", designationName=" + designationName + ", empName=" + empName + ", field1=" + field1 + ", field2="
				+ field2 + ", field3=" + field3 + ", field4=" + field4 + ", field5=" + field5 + ", createdOn="
				+ createdOn + ", modifiedOn=" + modifiedOn + ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy
				+ ", status=" + status + ", isValidatedByEmployee=" + isValidatedByEmployee
				+ ", isValidatedByFirstLevel=" + isValidatedByFirstLevel + ", isValidatedBySecondLevel="
				+ isValidatedBySecondLevel + "]";
	}
	
	
}
