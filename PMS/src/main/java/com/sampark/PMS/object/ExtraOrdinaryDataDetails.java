package com.sampark.PMS.object;

import java.util.ArrayList;
import java.util.List;

import com.sampark.PMS.dto.ExtraOrdinary;

public class ExtraOrdinaryDataDetails {
	private String contributions;
	private String contributionDetails;
	private Integer finalYearSelfRating;
	private Integer finalYearAppraisarRating;
	private Integer finalScore;
	private String remarks;
	private List<ExtraOrdinary> child = new ArrayList<ExtraOrdinary>();
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
	public Integer getFinalScore() {
		return finalScore;
	}
	public void setFinalScore(Integer finalScore) {
		this.finalScore = finalScore;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public List<ExtraOrdinary> getChild() {
		return child;
	}
	public void setChild(List<ExtraOrdinary> child) {
		this.child = child;
	}
	
}
