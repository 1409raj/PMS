package com.sampark.PMS.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EmployeeKRAData {
	@Id
	private Integer id;
	private String kraType;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getKraType() {
		return kraType;
	}
	public void setKraType(String kraType) {
		this.kraType = kraType;
	}
	@Override
	public String toString() {
		return "EmployeeKRAData [id=" + id + ", kraType=" + kraType + "]";
	}
	
	
}
