package com.sampark.PMS.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TermClass {
	@Id
	private Integer id;
	private Integer initializationYear;
	private Integer midYear;
	private Integer finalYear;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getInitializationYear() {
		return initializationYear;
	}
	public void setInitializationYear(Integer initializationYear) {
		this.initializationYear = initializationYear;
	}
	public Integer getMidYear() {
		return midYear;
	}
	public void setMidYear(Integer midYear) {
		this.midYear = midYear;
	}
	public Integer getFinalYear() {
		return finalYear;
	}
	public void setFinalYear(Integer finalYear) {
		this.finalYear = finalYear;
	}

}
