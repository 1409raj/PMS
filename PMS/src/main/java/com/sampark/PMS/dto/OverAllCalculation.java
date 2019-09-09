package com.sampark.PMS.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "over_all_calculation")
public class OverAllCalculation {
	@Id
	@GeneratedValue
	@Column(name="ID")
	private Integer id;
	@Column(name="APPRAISAL_YEAR_ID")
	private Integer appraisalYearId;
	@Column(name="EMP_CODE")
	private String empCode;
	@Column(name="KAR_TOTAL_CALCULATION")
	private String kraTotalCalculation;
	@Column(name="BEHAVIOURAL_COMPETENCE_TOTAL_CALCULATION")
	private String behaviouralCompetenceTotalCalculation;
	@Column(name="EXTRA_ORDINARY_TOTAL_CALCULATION")
	private String extraOrdinaryTotalCalculation;
	@Column(name="CREATED_ON")
	private Date createdOn;
	@Column(name="MODIFIED_ON")
	private Date modifiedOn;
	@Column(name="CREATED_BY")
	private String createdBy;
	@Column(name="MODIFIED_BY")
	private String modifiedBy;
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
	public String getKraTotalCalculation() {
		return kraTotalCalculation;
	}
	public void setKraTotalCalculation(String kraTotalCalculation) {
		this.kraTotalCalculation = kraTotalCalculation;
	}
	public String getBehaviouralCompetenceTotalCalculation() {
		return behaviouralCompetenceTotalCalculation;
	}
	public void setBehaviouralCompetenceTotalCalculation(String behaviouralCompetenceTotalCalculation) {
		this.behaviouralCompetenceTotalCalculation = behaviouralCompetenceTotalCalculation;
	}
	public String getExtraOrdinaryTotalCalculation() {
		return extraOrdinaryTotalCalculation;
	}
	public void setExtraOrdinaryTotalCalculation(String extraOrdinaryTotalCalculation) {
		this.extraOrdinaryTotalCalculation = extraOrdinaryTotalCalculation;
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
	@Override
	public String toString() {
		return "OverAllCalculation [id=" + id + ", appraisalYearId=" + appraisalYearId + ", empCode=" + empCode
				+ ", kraTotalCalculation=" + kraTotalCalculation + ", behaviouralCompetenceTotalCalculation="
				+ behaviouralCompetenceTotalCalculation + ", extraOrdinaryTotalCalculation="
				+ extraOrdinaryTotalCalculation + ", createdOn=" + createdOn + ", modifiedOn=" + modifiedOn
				+ ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy + "]";
	}
		
}