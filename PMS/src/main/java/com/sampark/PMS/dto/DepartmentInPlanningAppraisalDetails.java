package com.sampark.PMS.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DepartmentInPlanningAppraisalDetails {
	@Id
	private Integer id;
	private Long goalSetting;
	private Long goalApproval;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	
	
}
