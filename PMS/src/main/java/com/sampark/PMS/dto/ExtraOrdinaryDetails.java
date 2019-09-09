package com.sampark.PMS.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "extra_ordinary_details")
public class ExtraOrdinaryDetails {
	@Id
	@GeneratedValue
	@Column(name="ID")
	private Integer id;
	@Column(name="EMP_CODE")
	private String empCode;
	@Column(name="APPRAISAL_YEAR_ID")
	private Integer appraisalYearId;
	@Column(name="CONTRIBUTIONS")
	private String contributions;
	@Column(name="CONTRIBUTION_DETAILS")
	private String contributionDetails;
	@Column(name="WEIGHTAGE")
	private String weightage;
	@Column(name="FINAL_YEAR_SELF_RATING")
	private Integer finalYearSelfRating;
	@Column(name="FINAL_YEAR_APPRAISAR_RATING")
	private Integer finalYearAppraisarRating;
	@Column(name="REMARKS")
	private String remarks;
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
	@Column(name="FINAL_YEAR_HIGHLIGHTS")
	private Integer finalYearHighlights;
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
	public String getContributions() {
		return contributions;
	}
	public void setContributions(String contributions) {
		this.contributions = contributions;
	}
	public String getContributionDetails() {
		return contributionDetails;
	}
	public void setContributionDetails(String contributionDetails) {
		this.contributionDetails = contributionDetails;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	public Integer getFinalYearSelfRating() {
		return finalYearSelfRating;
	}
	public void setFinalYearSelfRating(Integer finalYearSelfRating) {
		this.finalYearSelfRating = finalYearSelfRating;
	}
	public Integer getFinalYearAppraisarRating() {
		return finalYearAppraisarRating;
	}
	public void setFinalYearAppraisarRating(Integer finalYearAppraisarRating) {
		this.finalYearAppraisarRating = finalYearAppraisarRating;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
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
	public String getWeightage() {
		return weightage;
	}
	public void setWeightage(String weightage) {
		this.weightage = weightage;
	}
	public Integer getFinalYearHighlights() {
		return finalYearHighlights;
	}
	public void setFinalYearHighlights(Integer finalYearHighlights) {
		this.finalYearHighlights = finalYearHighlights;
	}
	@Override
	public String toString() {
		return "ExtraOrdinaryDetails [id=" + id + ", empCode=" + empCode + ", appraisalYearId=" + appraisalYearId
				+ ", contributions=" + contributions + ", contributionDetails=" + contributionDetails + ", weightage="
				+ weightage + ", finalYearSelfRating=" + finalYearSelfRating + ", finalYearAppraisarRating="
				+ finalYearAppraisarRating + ", remarks=" + remarks + ", createdOn=" + createdOn + ", modifiedOn="
				+ modifiedOn + ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy + ", status=" + status
				+ ", isValidatedByEmployee=" + isValidatedByEmployee + ", isValidatedByFirstLevel="
				+ isValidatedByFirstLevel + ", isValidatedBySecondLevel=" + isValidatedBySecondLevel
				+ ", finalYearHighlights=" + finalYearHighlights + "]";
	}
	
}
