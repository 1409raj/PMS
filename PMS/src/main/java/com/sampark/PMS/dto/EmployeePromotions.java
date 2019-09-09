package com.sampark.PMS.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "employee_promotions")
public class EmployeePromotions {
	@Id
	@GeneratedValue
	@Column(name="ID")
	private Integer id;
	@Column(name="APPRAISAL_YEAR_ID")
	private Integer appraisalYearId;
	@Column(name="EMP_CODE")
	private String empCode;
	@Column(name="RECOMMENDED_DESIGNATION")
	private String recommendedDesignation;
	@Column(name="FIRST_LEVEL_MANAGER_ID")
	private String firstLevelManagerId;
	@Column(name="SECOND_LEVEL_MANAGER_ID")
	private String secondLevelManagerId;
	@Column(name="SPECIFIC_ACHIEVEMENTS")
	private String specificAchievements;	
	@Column(name="EXPECTATIONS")
	private String expectations;
	@Column(name="PROMOTION_IMPACT")
	private String promotionImpact;
	@Column(name="JOB_RESPONSIBILITY")
	private String jobResponsibility;
	@Column(name="DEPARTMENTAL_LEVEL")
	private String departmentLevel;
	@Column(name="ORGANISATIONAL_LEVEL")
	private String organisationLevel;
	@Column(name="ADDITONAL_TRAINING")
	private String additionalTraining;
	@Column(name="NEXT_FIVE_YEARS")
	private String nextFiveYears;
	@Column(name="FIRST_LEVEL_SUPERIOR_COMMENTS")
	private String firstLevelSuperiorComments;
	@Column(name="FIRST_LEVEL_SUPERIOR_COMMENTS_DATE")
	private Date firstLevelSuperiorCommentsDate;
	@Column(name="SECOND_LEVEL_SUPERIOR_COMMENTS")
	private String secondLevelSuperiorComments;
	@Column(name="SECOND_LEVEL_SUPERIOR_COMMENTS_DATE")
	private Date secondLevelSuperiorCommentsDate;
	@Column(name="HR_COMMENTS")
	private String hrComments;
	@Column(name="HR_COMMENTS_DATE")
	private String hrCommentsDate;	
	@Column(name="APPROVED_BY_COMMENTS")
	private String approvedByComments;
	@Column(name="APPROVED_BY_DATE")
	private Date approvedByDate;
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
	@Column(name="FIRST_LEVEl_CHECK")
	private Integer firstLevelCheck;
	@Column(name="SECOND_LEVEL_CHECK")
	private Integer secondLevelCheck;
	@Column(name="HR_CHECK")
	private Integer hrCheck;
	@Column(name="CEO_CHECK")
	private Integer ceoCheck;
	@Transient
	private String type;
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
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public String getRecommendedDesignation() {
		return recommendedDesignation;
	}
	public void setRecommendedDesignation(String recommendedDesignation) {
		this.recommendedDesignation = recommendedDesignation;
	}
	public String getFirstLevelManagerId() {
		return firstLevelManagerId;
	}
	public void setFirstLevelManagerId(String firstLevelManagerId) {
		this.firstLevelManagerId = firstLevelManagerId;
	}
	public String getSecondLevelManagerId() {
		return secondLevelManagerId;
	}
	public void setSecondLevelManagerId(String secondLevelManagerId) {
		this.secondLevelManagerId = secondLevelManagerId;
	}
	public String getSpecificAchievements() {
		return specificAchievements;
	}
	public void setSpecificAchievements(String specificAchievements) {
		this.specificAchievements = specificAchievements;
	}
	public String getExpectations() {
		return expectations;
	}
	public void setExpectations(String expectations) {
		this.expectations = expectations;
	}
	public String getPromotionImpact() {
		return promotionImpact;
	}
	public void setPromotionImpact(String promotionImpact) {
		this.promotionImpact = promotionImpact;
	}
	public String getJobResponsibility() {
		return jobResponsibility;
	}
	public void setJobResponsibility(String jobResponsibility) {
		this.jobResponsibility = jobResponsibility;
	}
	public String getDepartmentLevel() {
		return departmentLevel;
	}
	public void setDepartmentLevel(String departmentLevel) {
		this.departmentLevel = departmentLevel;
	}
	public String getOrganisationLevel() {
		return organisationLevel;
	}
	public void setOrganisationLevel(String organisationLevel) {
		this.organisationLevel = organisationLevel;
	}
	public String getAdditionalTraining() {
		return additionalTraining;
	}
	public void setAdditionalTraining(String additionalTraining) {
		this.additionalTraining = additionalTraining;
	}
	public String getNextFiveYears() {
		return nextFiveYears;
	}
	public void setNextFiveYears(String nextFiveYears) {
		this.nextFiveYears = nextFiveYears;
	}
	public String getFirstLevelSuperiorComments() {
		return firstLevelSuperiorComments;
	}
	public void setFirstLevelSuperiorComments(String firstLevelSuperiorComments) {
		this.firstLevelSuperiorComments = firstLevelSuperiorComments;
	}
	public Date getFirstLevelSuperiorCommentsDate() {
		return firstLevelSuperiorCommentsDate;
	}
	public void setFirstLevelSuperiorCommentsDate(Date firstLevelSuperiorCommentsDate) {
		this.firstLevelSuperiorCommentsDate = firstLevelSuperiorCommentsDate;
	}
	public String getSecondLevelSuperiorComments() {
		return secondLevelSuperiorComments;
	}
	public void setSecondLevelSuperiorComments(String secondLevelSuperiorComments) {
		this.secondLevelSuperiorComments = secondLevelSuperiorComments;
	}
	public Date getSecondLevelSuperiorCommentsDate() {
		return secondLevelSuperiorCommentsDate;
	}
	public void setSecondLevelSuperiorCommentsDate(Date secondLevelSuperiorCommentsDate) {
		this.secondLevelSuperiorCommentsDate = secondLevelSuperiorCommentsDate;
	}
	public String getHrComments() {
		return hrComments;
	}
	public void setHrComments(String hrComments) {
		this.hrComments = hrComments;
	}
	public String getHrCommentsDate() {
		return hrCommentsDate;
	}
	public void setHrCommentsDate(String hrCommentsDate) {
		this.hrCommentsDate = hrCommentsDate;
	}
	public String getApprovedByComments() {
		return approvedByComments;
	}
	public void setApprovedByComments(String approvedByComments) {
		this.approvedByComments = approvedByComments;
	}
	public Date getApprovedByDate() {
		return approvedByDate;
	}
	public void setApprovedByDate(Date approvedByDate) {
		this.approvedByDate = approvedByDate;
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
	public Integer getFirstLevelCheck() {
		return firstLevelCheck;
	}
	public void setFirstLevelCheck(Integer firstLevelCheck) {
		this.firstLevelCheck = firstLevelCheck;
	}
	public Integer getSecondLevelCheck() {
		return secondLevelCheck;
	}
	public void setSecondLevelCheck(Integer secondLevelCheck) {
		this.secondLevelCheck = secondLevelCheck;
	}
	public Integer getHrCheck() {
		return hrCheck;
	}
	public void setHrCheck(Integer hrCheck) {
		this.hrCheck = hrCheck;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getCeoCheck() {
		return ceoCheck;
	}
	public void setCeoCheck(Integer ceoCheck) {
		this.ceoCheck = ceoCheck;
	}
	@Override
	public String toString() {
		return "EmployeePromotions [id=" + id + ", appraisalYearId=" + appraisalYearId + ", empCode=" + empCode
				+ ", recommendedDesignation=" + recommendedDesignation + ", firstLevelManagerId=" + firstLevelManagerId
				+ ", secondLevelManagerId=" + secondLevelManagerId + ", specificAchievements=" + specificAchievements
				+ ", expectations=" + expectations + ", promotionImpact=" + promotionImpact + ", jobResponsibility="
				+ jobResponsibility + ", departmentLevel=" + departmentLevel + ", organisationLevel="
				+ organisationLevel + ", additionalTraining=" + additionalTraining + ", nextFiveYears=" + nextFiveYears
				+ ", firstLevelSuperiorComments=" + firstLevelSuperiorComments + ", firstLevelSuperiorCommentsDate="
				+ firstLevelSuperiorCommentsDate + ", secondLevelSuperiorComments=" + secondLevelSuperiorComments
				+ ", secondLevelSuperiorCommentsDate=" + secondLevelSuperiorCommentsDate + ", hrComments=" + hrComments
				+ ", hrCommentsDate=" + hrCommentsDate + ", approvedByComments=" + approvedByComments
				+ ", approvedByDate=" + approvedByDate + ", createdOn=" + createdOn + ", modifiedOn=" + modifiedOn
				+ ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy + ", status=" + status
				+ ", firstLevelCheck=" + firstLevelCheck + ", secondLevelCheck=" + secondLevelCheck + ", hrCheck="
				+ hrCheck + ", ceoCheck=" + ceoCheck + ", type=" + type + "]";
	}
	}
