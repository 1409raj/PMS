package com.sampark.PMS.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AppraisalCountDetails {
	@Id
	private Integer id;
	private Integer pendingWithEmployee;
	private Integer pendingWithFirstManager;
	private Integer firstManagerClosed;
	private Integer pendingWithFirstLevel;
	private Integer pendingWithSecondManager;
	private Integer secondManagerClosed;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPendingWithEmployee() {
		return pendingWithEmployee;
	}
	public void setPendingWithEmployee(Integer pendingWithEmployee) {
		this.pendingWithEmployee = pendingWithEmployee;
	}
	public Integer getPendingWithFirstManager() {
		return pendingWithFirstManager;
	}
	public void setPendingWithFirstManager(Integer pendingWithFirstManager) {
		this.pendingWithFirstManager = pendingWithFirstManager;
	}
	public Integer getFirstManagerClosed() {
		return firstManagerClosed;
	}
	public void setFirstManagerClosed(Integer firstManagerClosed) {
		this.firstManagerClosed = firstManagerClosed;
	}
	public Integer getPendingWithFirstLevel() {
		return pendingWithFirstLevel;
	}
	public void setPendingWithFirstLevel(Integer pendingWithFirstLevel) {
		this.pendingWithFirstLevel = pendingWithFirstLevel;
	}
	public Integer getPendingWithSecondManager() {
		return pendingWithSecondManager;
	}
	public void setPendingWithSecondManager(Integer pendingWithSecondManager) {
		this.pendingWithSecondManager = pendingWithSecondManager;
	}
	public Integer getSecondManagerClosed() {
		return secondManagerClosed;
	}
	public void setSecondManagerClosed(Integer secondManagerClosed) {
		this.secondManagerClosed = secondManagerClosed;
	}
	
	
}
