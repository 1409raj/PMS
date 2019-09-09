package com.sampark.PMS.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "employee_emails")
public class EmployeeEmails {
	
	@Id
	@GeneratedValue
	@Column(name="ID")
	private Integer id;
	@Column(name="APPRAISAL_YEAR_ID")
	private Integer appraisalYearId;
	@Column(name="EMPLOYEE_NAME")
	private String employeeName;
	@Column(name="SEND_TO")
	private String sendTo;
	@Column(name="EMPLOYEE_PASSWORD")
	private String employeePassword;
	@Column(name="SEND_FROM")
	private String sendFrom;
	@Column(name="CREATED_ON")
	private Date createdOn;
	@Column(name="DELIVERY_STATUS")
	private String deliveryStatus;
	@Column(name="EMAIL_TYPE")
	private String emailType;
	@Column(name="STATUS")
	private Integer status;
	@Column(name="EMAIL_SUBJECT")
	private String emailSubject;
	@Column(name="EMAIL_CONTENT")
	private String emailContent;
	public String getEmployeePassword() {
		return employeePassword;
	}
	public void setEmployeePassword(String employeePassword) {
		this.employeePassword = employeePassword;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAppraisalYearId() {
		return appraisalYearId;
	}
	public void setAppraisalYearId(Integer appraisalYearId) {
		this.appraisalYearId = appraisalYearId;
	}
	
	public String getSendTo() {
		return sendTo;
	}
	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}
	public String getSendFrom() {
		return sendFrom;
	}
	public void setSendFrom(String sendFrom) {
		this.sendFrom = sendFrom;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public String getDeliveryStatus() {
		return deliveryStatus;
	}
	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getEmailType() {
		return emailType;
	}
	public void setEmailType(String emailType) {
		this.emailType = emailType;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getEmailSubject() {
		return emailSubject;
	}
	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}
	public String getEmailContent() {
		return emailContent;
	}
	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}
	@Override
	public String toString() {
		return "EmployeeEmails [id=" + id + ", appraisalYearId=" + appraisalYearId + ", employeeName=" + employeeName
				+ ", sendTo=" + sendTo + ", employeePassword=" + employeePassword + ", sendFrom=" + sendFrom
				+ ", createdOn=" + createdOn + ", deliveryStatus=" + deliveryStatus + ", emailType=" + emailType
				+ ", status=" + status + ", emailSubject=" + emailSubject + ", emailContent=" + emailContent + "]";
	}
	
}
