package com.sampark.PMS.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EmployeeKRADetails {
	@Id
	private Integer kraId;
	private String weightage;
	private Integer midYearAppraisarRating;
	private Integer finalYearAppraisarRating;
	public Integer getKraId() {
		return kraId;
	}
	public void setKraId(Integer kraId) {
		this.kraId = kraId;
	}
	public String getWeightage() {
		return weightage;
	}
	public void setWeightage(String weightage) {
		this.weightage = weightage;
	}
	public Integer getMidYearAppraisarRating() {
		return midYearAppraisarRating;
	}
	public void setMidYearAppraisarRating(Integer midYearAppraisarRating) {
		this.midYearAppraisarRating = midYearAppraisarRating;
	}
	public Integer getFinalYearAppraisarRating() {
		return finalYearAppraisarRating;
	}
	public void setFinalYearAppraisarRating(Integer finalYearAppraisarRating) {
		this.finalYearAppraisarRating = finalYearAppraisarRating;
	}
	@Override
	public String toString() {
		return "EmployeeKRADetails [kraId=" + kraId + ", weightage=" + weightage + ", midYearAppraisarRating="
				+ midYearAppraisarRating + ", finalYearAppraisarRating=" + finalYearAppraisarRating + "]";
	}
	
}