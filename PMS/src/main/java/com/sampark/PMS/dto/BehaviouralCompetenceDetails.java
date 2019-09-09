package com.sampark.PMS.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "behavioural_competence_details")
public class BehaviouralCompetenceDetails {
	@Id
	@GeneratedValue
	@Column(name="ID")
	private Integer id;
	@Column(name="EMP_CODE")
	private String empCode;
	@Column(name="APPRAISAL_YEAR_ID")
	private Integer appraisalYearId;
	@Column(name="BEHAVIOURAL_DETAILS_ID")
	private Integer behaviouralDetailsId;
	@Column(name="COMMENTS")
	private String comments;
	@Column(name="MID_YEAR_SELF_RATING")
	private Integer midYearSelfRating;
	@Column(name="MID_YEAR_ASSESSOR_RATING")
	private Integer midYearAssessorRating;
	@Column(name="FINAL_YEAR_SELF_RATING")
	private Integer finalYearSelfRating;
	@Column(name="FINAL_YEAR_ASSESSOR_RATING")
	private Integer finalYearAssessorRating;
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
	@Column(name="MID_YEAR_HIGHLIGHTS")
	private Integer midYearHighlights;
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
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Integer getMidYearSelfRating() {
		return midYearSelfRating;
	}
	public void setMidYearSelfRating(Integer midYearSelfRating) {
		this.midYearSelfRating = midYearSelfRating;
	}
	public Integer getMidYearAssessorRating() {
		return midYearAssessorRating;
	}
	public void setMidYearAssessorRating(Integer midYearAssessorRating) {
		this.midYearAssessorRating = midYearAssessorRating;
	}
	public Integer getFinalYearSelfRating() {
		return finalYearSelfRating;
	}
	public void setFinalYearSelfRating(Integer finalYearSelfRating) {
		this.finalYearSelfRating = finalYearSelfRating;
	}
	public Integer getFinalYearAssessorRating() {
		return finalYearAssessorRating;
	}
	public void setFinalYearAssessorRating(Integer finalYearAssessorRating) {
		this.finalYearAssessorRating = finalYearAssessorRating;
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
	public Integer getBehaviouralDetailsId() {
		return behaviouralDetailsId;
	}
	public void setBehaviouralDetailsId(Integer behaviouralDetailsId) {
		this.behaviouralDetailsId = behaviouralDetailsId;
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
	public Integer getMidYearHighlights() {
		return midYearHighlights;
	}
	public void setMidYearHighlights(Integer midYearHighlights) {
		this.midYearHighlights = midYearHighlights;
	}
	public Integer getFinalYearHighlights() {
		return finalYearHighlights;
	}
	public void setFinalYearHighlights(Integer finalYearHighlights) {
		this.finalYearHighlights = finalYearHighlights;
	}
	@Override
	public String toString() {
		return "BehaviouralCompetenceDetails [id=" + id + ", empCode=" + empCode + ", appraisalYearId="
				+ appraisalYearId + ", behaviouralDetailsId=" + behaviouralDetailsId + ", comments=" + comments
				+ ", midYearSelfRating=" + midYearSelfRating + ", midYearAssessorRating=" + midYearAssessorRating
				+ ", finalYearSelfRating=" + finalYearSelfRating + ", finalYearAssessorRating="
				+ finalYearAssessorRating + ", createdOn=" + createdOn + ", modifiedOn=" + modifiedOn + ", createdBy="
				+ createdBy + ", modifiedBy=" + modifiedBy + ", status=" + status + ", isValidatedByEmployee="
				+ isValidatedByEmployee + ", isValidatedByFirstLevel=" + isValidatedByFirstLevel
				+ ", isValidatedBySecondLevel=" + isValidatedBySecondLevel + ", midYearHighlights=" + midYearHighlights
				+ ", finalYearHighlights=" + finalYearHighlights + "]";
	}
	
	
}
