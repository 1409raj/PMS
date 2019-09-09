package com.sampark.PMS.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DepartmentInReviewAppraisalDetails {
	@Id
	private Integer id;
	private Long inReview;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Long getInReview() {
		return inReview;
	}
	public void setInReview(Long inReview) {
		this.inReview = inReview;
	}
	
	
}
