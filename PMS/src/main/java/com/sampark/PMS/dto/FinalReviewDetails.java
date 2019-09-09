package com.sampark.PMS.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "final_review_details")
public class FinalReviewDetails {
	@Id
	@GeneratedValue
	@Column(name="ID")
	private Integer id;
	@Column(name="EMP_CODE")
	private Integer empCode;
	@Column(name="APPRAISAL_YEAR_ID")
	private Integer appraisalYearId;
	@Column(name="FIRST_LEVEL_SUPERIOR_COMMENTS")
	private String firstLevelSuperiorComments;
	@Column(name="SECOND_LEVEL_SUPERIOR_COMMENTS")
	private String secondLevelSuperiorComments;
	@Column(name="ASSESSEE_COMMENTS")
	private String assesseeComments;
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
	public String getFirstLevelSuperiorComments() {
		return firstLevelSuperiorComments;
	}
	public void setFirstLevelSuperiorComments(String firstLevelSuperiorComments) {
		this.firstLevelSuperiorComments = firstLevelSuperiorComments;
	}
	public String getSecondLevelSuperiorComments() {
		return secondLevelSuperiorComments;
	}
	public void setSecondLevelSuperiorComments(String secondLevelSuperiorComments) {
		this.secondLevelSuperiorComments = secondLevelSuperiorComments;
	}
	public String getAssesseeComments() {
		return assesseeComments;
	}
	public void setAssesseeComments(String assesseeComments) {
		this.assesseeComments = assesseeComments;
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
	public Integer getEmpCode() {
		return empCode;
	}
	public void setEmpCode(Integer empCode) {
		this.empCode = empCode;
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
	@Override
	public String toString() {
		return "FinalReviewDetails [id=" + id + ", empCode=" + empCode + ", appraisalYearId=" + appraisalYearId
				+ ", firstLevelSuperiorComments=" + firstLevelSuperiorComments + ", secondLevelSuperiorComments="
				+ secondLevelSuperiorComments + ", assesseeComments=" + assesseeComments + ", createdOn=" + createdOn
				+ ", modifiedOn=" + modifiedOn + ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy + ", status="
				+ status + "]";
	}
	
	
	
}
