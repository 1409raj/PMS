package com.sampark.PMS.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "kra_new")

public class KraNew {
	@Id
	@GeneratedValue
	@Column(name="ID")
	private Integer id;
	@Column(name="EMP_CODE")
	private String empCode;
	@Column(name="APPRAISAL_YEAR_ID")
	private Integer appraisalYearId;
	@Column(name="SECTION_NAME")
	private String sectionName;
	@Column(name="SMART_GOAL")
	private String smartGoal;
	@Column(name="TARGET")
	private String target;
	@Column(name="FILE_NAME")
	private String fileName;
	@Transient
	private String fileDummyName;
	@Transient
	private String departmentName;
	@Transient
	private String designationName;
	@Transient
	private String kraScreen;
	@Transient
	private String empName;
	@Column(name="ACHIEVEMENT_DATE")
	private Date achievementDate;
	@Column(name="WEIGHTAGE")
	private String weightage;
	@Column(name="MID_YEAR_ACHIEVEMENT")
	private String midYearAchievement;
	@Column(name="MID_YEAR_SELF_RATING")
	private Integer midYearSelfRating;
	@Column(name="MID_YEAR_APPRAISAR_RATING")
	private Integer midYearAppraisarRating;
	@Column(name="MID_YEAR_ASSESSMENT_REMARKS")
	private String midYearAssessmentRemarks;
	@Column(name="Final_YEAR_ACHIEVEMENT")
	private String finalYearAchievement;
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
	@Column(name="MID_YEAR_HIGHLIGHTS")
	private Integer midYearHighlights;
	@Column(name="FINAL_YEAR_HIGHLIGHTS")
	private Integer finalYearHighlights;
	@Column(name="KRA_TYPE")
	private String kraType;
	
	public String getKraScreen() {
		return kraScreen;
	}
	public void setKraScreen(String kraScreen) {
		this.kraScreen = kraScreen;
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
	public Integer getAppraisalYearId() {
		return appraisalYearId;
	}
	public void setAppraisalYearId(Integer appraisalYearId) {
		this.appraisalYearId = appraisalYearId;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public String getSmartGoal() {
		return smartGoal;
	}
	public void setSmartGoal(String smartGoal) {
		this.smartGoal = smartGoal;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getMidYearAchievement() {
		return midYearAchievement;
	}
	public void setMidYearAchievement(String midYearAchievement) {
		this.midYearAchievement = midYearAchievement;
	}
	public String getFinalYearAchievement() {
		return finalYearAchievement;
	}
	public void setFinalYearAchievement(String finalYearAchievement) {
		this.finalYearAchievement = finalYearAchievement;
	}
	public Date getAchievementDate() {
		return achievementDate;
	}
	public void setAchievementDate(Date achievementDate) {
		this.achievementDate = achievementDate;
	}
	public String getWeightage() {
		return weightage;
	}
	public void setWeightage(String weightage) {
		this.weightage = weightage;
	}
	public Integer getMidYearSelfRating() {
		return midYearSelfRating;
	}
	public void setMidYearSelfRating(Integer midYearSelfRating) {
		this.midYearSelfRating = midYearSelfRating;
	}
	public Integer getMidYearAppraisarRating() {
		return midYearAppraisarRating;
	}
	public void setMidYearAppraisarRating(Integer midYearAppraisarRating) {
		this.midYearAppraisarRating = midYearAppraisarRating;
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
	public String getMidYearAssessmentRemarks() {
		return midYearAssessmentRemarks;
	}
	public void setMidYearAssessmentRemarks(String midYearAssessmentRemarks) {
		this.midYearAssessmentRemarks = midYearAssessmentRemarks;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileDummyName() {
		return fileDummyName;
	}
	public void setFileDummyName(String fileDummyName) {
		this.fileDummyName = fileDummyName;
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
	public String getKraType() {
		return kraType;
	}
	public void setKraType(String kraType) {
		this.kraType = kraType;
	}
	@Override
	public String toString() {
		return "KraNew [id=" + id + ", empCode=" + empCode + ", appraisalYearId=" + appraisalYearId + ", sectionName="
				+ sectionName + ", smartGoal=" + smartGoal + ", target=" + target + ", fileName=" + fileName
				+ ", fileDummyName=" + fileDummyName + ", departmentName=" + departmentName + ", designationName="
				+ designationName + ", empName=" + empName + ", achievementDate=" + achievementDate + ", weightage="
				+ weightage + ", midYearAchievement=" + midYearAchievement + ", midYearSelfRating=" + midYearSelfRating
				+ ", midYearAppraisarRating=" + midYearAppraisarRating + ", midYearAssessmentRemarks="
				+ midYearAssessmentRemarks + ", finalYearAchievement=" + finalYearAchievement + ", finalYearSelfRating="
				+ finalYearSelfRating + ", finalYearAppraisarRating=" + finalYearAppraisarRating + ", remarks="
				+ remarks + ", createdOn=" + createdOn + ", modifiedOn=" + modifiedOn + ", createdBy=" + createdBy
				+ ", modifiedBy=" + modifiedBy + ", status=" + status + ", isValidatedByEmployee="
				+ isValidatedByEmployee + ", isValidatedByFirstLevel=" + isValidatedByFirstLevel
				+ ", isValidatedBySecondLevel=" + isValidatedBySecondLevel + ", midYearHighlights=" + midYearHighlights
				+ ", finalYearHighlights=" + finalYearHighlights + ", kraType=" + kraType + "]";
	}
	
}
