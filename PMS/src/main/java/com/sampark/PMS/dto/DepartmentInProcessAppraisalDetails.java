package com.sampark.PMS.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DepartmentInProcessAppraisalDetails {
	@Id
	private Integer id;
	private Long yearEndAssessment;
	private Long assessmentApproval;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Long getYearEndAssessment() {
		return yearEndAssessment;
	}
	public void setYearEndAssessment(Long yearEndAssessment) {
		this.yearEndAssessment = yearEndAssessment;
	}
	public Long getAssessmentApproval() {
		return assessmentApproval;
	}
	public void setAssessmentApproval(Long assessmentApproval) {
		this.assessmentApproval = assessmentApproval;
	}
	@Override
	public String toString() {
		return "DepartmentInProcessAppraisalDetails [id=" + id + ", yearEndAssessment=" + yearEndAssessment
				+ ", assessmentApproval=" + assessmentApproval + "]";
	}
		
}
