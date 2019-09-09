package com.sampark.PMS.dto;

public class DepartmentFullAppraisalDetails {
	private Integer id;
	private String name;
	private Long goalSetting;
	private Long goalApproval;
	private Long inReview;
	private Long yearEndAssessment;
	private Long assessmentApproval;
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
	public Long getGoalSetting() {
		return goalSetting;
	}
	public void setGoalSetting(Long goalSetting) {
		this.goalSetting = goalSetting;
	}
	public Long getGoalApproval() {
		return goalApproval;
	}
	public void setGoalApproval(Long goalApproval) {
		this.goalApproval = goalApproval;
	}
	public Long getInReview() {
		return inReview;
	}
	public void setInReview(Long inReview) {
		this.inReview = inReview;
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
	
	

}
