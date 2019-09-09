package com.sampark.PMS.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "career_aspirations_details")
public class CareerAspirationsDetails {
	@Id
	@GeneratedValue
	@Column(name="ID")
	private Integer id;
	@Column(name="INITIALIZATION_COMMENTS")
	private String initializationComments;
	@Column(name="INITIALIZATION_MANAGER_REVIEW")
	private String initializationManagerReview;
	@Column(name="MID_YEAR_COMMENTS")
	private String midYearComments;
	@Column(name="YEAR_END_COMMENTS")
	private String yearEndComments;
	@Column(name="MID_YEAR_COMMENTS_MANAGER_REVIEW")
	private String midYearCommentsManagerReview;
	@Column(name="YEAR_END_COMMENTS_MANAGER_REVIEW")
	private String yearEndCommentsManagerReview;
	@Column(name="EMP_CODE")
	private String empCode;
	@Column(name="APPRAISAL_YEAR_ID")
	private Integer appraisalYearId;
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
	@Column(name="MID_YEAR_COMMENTS_STATUS")
	private Integer midYearCommentsStatus;
	@Column(name="MID_YEAR_COMMENTS_STATUS_FIRST_LEVEL")
	private Integer midYearCommentsStatusFirstLevel;
	@Column(name="MID_YEAR_COMMENTS_STATUS_SECOND_LEVEL")
	private Integer midYearCommentsStatusSecondLevel;
	@Column(name="ISVALIDATEDBY_EMPLOYEE")
	private Integer isValidatedByEmployee;
	@Column(name="ISVALIDATEDBY_FIRSTLEVEL")
	private Integer isValidatedByFirstLevel;
	@Column(name="ISVALIDATEDBY_SECONDLEVEL")
	private Integer isValidatedBySecondLevel;
	@Column(name="YEAR_END_COMMENTS_STATUS")
	private Integer yearEndCommentsStatus;
	@Column(name="YEAR_END_COMMENTS_STATUS_FIRST_LEVEL")
	private Integer yearEndCommentsStatusFirstLevel;
	@Column(name="YEAR_END_COMMENTS_STATUS_SECOND_LEVEL")
	private Integer yearEndCommentsStatusSecondLevel;
	
	@Transient
	private String type;
	@Transient
	private String name;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getInitializationComments() {
		return initializationComments;
	}
	public void setInitializationComments(String initializationComments) {
		this.initializationComments = initializationComments;
	}
	public String getMidYearComments() {
		return midYearComments;
	}
	public void setMidYearComments(String midYearComments) {
		this.midYearComments = midYearComments;
	}
	public Integer getMidYearCommentsStatus() {
		return midYearCommentsStatus;
	}
	public void setMidYearCommentsStatus(Integer midYearCommentsStatus) {
		this.midYearCommentsStatus = midYearCommentsStatus;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getMidYearCommentsStatusFirstLevel() {
		return midYearCommentsStatusFirstLevel;
	}
	public void setMidYearCommentsStatusFirstLevel(Integer midYearCommentsStatusFirstLevel) {
		this.midYearCommentsStatusFirstLevel = midYearCommentsStatusFirstLevel;
	}
	public Integer getMidYearCommentsStatusSecondLevel() {
		return midYearCommentsStatusSecondLevel;
	}
	public void setMidYearCommentsStatusSecondLevel(Integer midYearCommentsStatusSecondLevel) {
		this.midYearCommentsStatusSecondLevel = midYearCommentsStatusSecondLevel;
	}
	public String getInitializationManagerReview() {
		return initializationManagerReview;
	}
	public void setInitializationManagerReview(String initializationManagerReview) {
		this.initializationManagerReview = initializationManagerReview;
	}
	public String getMidYearCommentsManagerReview() {
		return midYearCommentsManagerReview;
	}
	public void setMidYearCommentsManagerReview(String midYearCommentsManagerReview) {
		this.midYearCommentsManagerReview = midYearCommentsManagerReview;
	}
	public String getYearEndComments() {
		return yearEndComments;
	}
	public void setYearEndComments(String yearEndComments) {
		this.yearEndComments = yearEndComments;
	}
	public String getYearEndCommentsManagerReview() {
		return yearEndCommentsManagerReview;
	}
	public void setYearEndCommentsManagerReview(String yearEndCommentsManagerReview) {
		this.yearEndCommentsManagerReview = yearEndCommentsManagerReview;
	}
	public Integer getYearEndCommentsStatus() {
		return yearEndCommentsStatus;
	}
	public void setYearEndCommentsStatus(Integer yearEndCommentsStatus) {
		this.yearEndCommentsStatus = yearEndCommentsStatus;
	}
	public Integer getYearEndCommentsStatusFirstLevel() {
		return yearEndCommentsStatusFirstLevel;
	}
	public void setYearEndCommentsStatusFirstLevel(Integer yearEndCommentsStatusFirstLevel) {
		this.yearEndCommentsStatusFirstLevel = yearEndCommentsStatusFirstLevel;
	}
	public Integer getYearEndCommentsStatusSecondLevel() {
		return yearEndCommentsStatusSecondLevel;
	}
	public void setYearEndCommentsStatusSecondLevel(Integer yearEndCommentsStatusSecondLevel) {
		this.yearEndCommentsStatusSecondLevel = yearEndCommentsStatusSecondLevel;
	}
	@Override
	public String toString() {
		return "CareerAspirationsDetails [id=" + id + ", initializationComments=" + initializationComments
				+ ", initializationManagerReview=" + initializationManagerReview + ", midYearComments="
				+ midYearComments + ", yearEndComments=" + yearEndComments + ", midYearCommentsManagerReview="
				+ midYearCommentsManagerReview + ", yearEndCommentsManagerReview=" + yearEndCommentsManagerReview
				+ ", empCode=" + empCode + ", appraisalYearId=" + appraisalYearId + ", createdOn=" + createdOn
				+ ", modifiedOn=" + modifiedOn + ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy + ", status="
				+ status + ", midYearCommentsStatus=" + midYearCommentsStatus + ", midYearCommentsStatusFirstLevel="
				+ midYearCommentsStatusFirstLevel + ", midYearCommentsStatusSecondLevel="
				+ midYearCommentsStatusSecondLevel + ", isValidatedByEmployee=" + isValidatedByEmployee
				+ ", isValidatedByFirstLevel=" + isValidatedByFirstLevel + ", isValidatedBySecondLevel="
				+ isValidatedBySecondLevel + ", yearEndCommentsStatus=" + yearEndCommentsStatus
				+ ", yearEndCommentsStatusFirstLevel=" + yearEndCommentsStatusFirstLevel
				+ ", yearEndCommentsStatusSecondLevel=" + yearEndCommentsStatusSecondLevel + ", type=" + type
				+ ", name=" + name + "]";
	}
	
}
