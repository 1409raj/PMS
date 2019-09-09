package com.sampark.PMS.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EmployeeExtraOrdinaryDetails {
	@Id
	private Integer exId;
	private String weightage;
	private Integer finalYearAppraisarRating;
	public String getWeightage() {
		return weightage;
	}
	public void setWeightage(String weightage) {
		this.weightage = weightage;
	}
	public Integer getFinalYearAppraisarRating() {
		return finalYearAppraisarRating;
	}
	public void setFinalYearAppraisarRating(Integer finalYearAppraisarRating) {
		this.finalYearAppraisarRating = finalYearAppraisarRating;
	}
	public Integer getExId() {
		return exId;
	}
	public void setExId(Integer exId) {
		this.exId = exId;
	}
	@Override
	public String toString() {
		return "EmployeeExtraOrdinaryDetails [exId=" + exId + ", weightage=" + weightage + ", finalYearAppraisarRating="
				+ finalYearAppraisarRating + "]";
	}
	
}