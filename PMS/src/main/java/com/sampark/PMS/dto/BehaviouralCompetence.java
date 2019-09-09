package com.sampark.PMS.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "behavioural_competence")
public class BehaviouralCompetence {
	
	@Id
	@GeneratedValue
	@Column(name="ID")
	private Integer id;	
	@Column(name="NAME")
	private String name;
	@Column(name="WEIGHTAGE")
	private String weightage;
	@Column(name="DESCRIPTION")
	private String description;
	@Column(name="APPRAISAL_YEAR_ID")
	private Integer appraisalYearId;
	@Column(name="TYPE")
	private String type;
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
	@Transient
	private String comments;
	@Transient
	private Integer behaviouralCompetenceDetailsId;
	@Transient
	private Integer midYearSelfRating;
	@Transient
	private Integer midYearAssessorRating;
	@Transient
	private Integer finalYearSelfRating;
	@Transient
	private Integer finalYearAssessorRating;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWeightage() {
		return weightage;
	}
	public void setWeightage(String weightage) {
		this.weightage = weightage;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getAppraisalYearId() {
		return appraisalYearId;
	}
	public void setAppraisalYearId(Integer appraisalYearId) {
		this.appraisalYearId = appraisalYearId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Integer getBehaviouralCompetenceDetailsId() {
		return behaviouralCompetenceDetailsId;
	}
	public void setBehaviouralCompetenceDetailsId(Integer behaviouralCompetenceDetailsId) {
		this.behaviouralCompetenceDetailsId = behaviouralCompetenceDetailsId;
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
	@Override
	public String toString() {
		return "BehaviouralCompetence [id=" + id + ", name=" + name + ", weightage=" + weightage + ", description="
				+ description + ", appraisalYearId=" + appraisalYearId + ", type=" + type + ", createdOn=" + createdOn
				+ ", modifiedOn=" + modifiedOn + ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy + ", status="
				+ status + ", comments=" + comments + ", behaviouralCompetenceDetailsId="
				+ behaviouralCompetenceDetailsId + ", midYearSelfRating=" + midYearSelfRating
				+ ", midYearAssessorRating=" + midYearAssessorRating + ", finalYearSelfRating=" + finalYearSelfRating
				+ ", finalYearAssessorRating=" + finalYearAssessorRating + "]";
	}
	
	
}