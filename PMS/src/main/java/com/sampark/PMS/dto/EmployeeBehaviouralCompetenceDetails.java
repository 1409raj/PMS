package com.sampark.PMS.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EmployeeBehaviouralCompetenceDetails {
	@Id
	private Integer bcdId;
	private Integer midYearAssessorRating;
	private Integer finalYearAssessorRating;
	private String weightage;
	public Integer getMidYearAssessorRating() {
		return midYearAssessorRating;
	}
	public void setMidYearAssessorRating(Integer midYearAssessorRating) {
		this.midYearAssessorRating = midYearAssessorRating;
	}
	public Integer getFinalYearAssessorRating() {
		return finalYearAssessorRating;
	}
	public void setFinalYearAssessorRating(Integer finalYearAssessorRating) {
		this.finalYearAssessorRating = finalYearAssessorRating;
	}
	public Integer getBcdId() {
		return bcdId;
	}
	public void setBcdId(Integer bcdId) {
		this.bcdId = bcdId;
	}
	public String getWeightage() {
		return weightage;
	}
	public void setWeightage(String weightage) {
		this.weightage = weightage;
	}
	@Override
	public String toString() {
		return "EmployeeBehaviouralCompetenceDetails [bcdId=" + bcdId + ", midYearAssessorRating="
				+ midYearAssessorRating + ", finalYearAssessorRating=" + finalYearAssessorRating + ", weightage="
				+ weightage + "]";
	}
}