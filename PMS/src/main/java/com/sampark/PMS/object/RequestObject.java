package com.sampark.PMS.object;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sampark.PMS.dto.BehaviouralCompetence;
import com.sampark.PMS.dto.ExtraOrdinaryDetails;
import com.sampark.PMS.dto.TrainingNeedsDetails;

public class RequestObject {
	private Integer id;
	private String type;
	private Integer typeId;
	private String name;
	// private boolean isAll;
	private Date eligibility;
	private Date startDate;
	private Date endDate;
	private String empCode;
	private String description;
	private Integer departmentId;
	private KraSections kraDetails;
	private KraNewSections kraNewDetails;
	private Integer appraisalYearId;
	private String weightage;
	private String password;
	private String email;
	private Integer status;
	private String token;
	private String stage;
	private String kraScreen;
	private Integer rejectionType;
	private String currentPassword;
	private String emailSubject;
	private String emailContent;
	private String appraisalYear;
	private String kraTotalCalculation;
	private String extraOrdinaryTotalCalculation;
	private String behaviouralCompetenceTotalCalculation;
	private List<Integer> list = new ArrayList<Integer>();
	// private List<Integer> designationList = new ArrayList<Integer>();
	// private List<Integer> departmentList = new ArrayList<Integer>();
	private List<ExtraOrdinaryDetails> extraOrdinaryDetails = new ArrayList<ExtraOrdinaryDetails>();
	private List<TrainingNeedsDetails> trainingNeedsDetails = new ArrayList<TrainingNeedsDetails>();
	private List<BehaviouralCompetence> behaviouralCompetence = new ArrayList<BehaviouralCompetence>();

	
	
	// public boolean isAll() {
	// return isAll;
	// }
	//
	// public void setAll(boolean isAll) {
	// this.isAll = isAll;
	// }
	
	public String getKraScreen() {
		return kraScreen;
	}

	public void setKraScreen(String kraScreen) {
		this.kraScreen = kraScreen;
	}

	public String getToken() {
		return token;
	}

	public Integer getRejectionType() {
		return rejectionType;
	}

	public void setRejectionType(Integer rejectionType) {
		this.rejectionType = rejectionType;
	}

	public KraNewSections getKraNewDetails() {
		return kraNewDetails;
	}

	public void setKraNewDetails(KraNewSections kraNewDetails) {
		this.kraNewDetails = kraNewDetails;
	}

	public String getAppraisalYear() {
		return appraisalYear;
	}

	public void setAppraisalYear(String appraisalYear) {
		this.appraisalYear = appraisalYear;
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

	public void setToken(String token) {
		this.token = token;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getPassword() {
		return password;
	}

	public List<ExtraOrdinaryDetails> getExtraOrdinaryDetails() {
		return extraOrdinaryDetails;
	}

	public void setExtraOrdinaryDetails(List<ExtraOrdinaryDetails> extraOrdinaryDetails) {
		this.extraOrdinaryDetails = extraOrdinaryDetails;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	// public List<Integer> getDesignationList() {
	// return designationList;
	// }
	//
	// public void setDesignationList(List<Integer> designationList) {
	// this.designationList = designationList;
	// }
	//
	// public List<Integer> getDepartmentList() {
	// return departmentList;
	// }
	//
	// public void setDepartmentList(List<Integer> departmentList) {
	// this.departmentList = departmentList;
	// }
	public Date getEligibility() {
		return eligibility;
	}

	public void setEligibility(Date eligibility) {
		this.eligibility = eligibility;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<Integer> getList() {
		return list;
	}

	public void setList(List<Integer> list) {
		this.list = list;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public KraSections getKraDetails() {
		return kraDetails;
	}

	public void setKraDetails(KraSections kraDetails) {
		this.kraDetails = kraDetails;
	}

	public Integer getAppraisalYearId() {
		return appraisalYearId;
	}

	public void setAppraisalYearId(Integer appraisalYearId) {
		this.appraisalYearId = appraisalYearId;
	}

	public List<BehaviouralCompetence> getBehaviouralCompetence() {
		return behaviouralCompetence;
	}

	public void setBehaviouralCompetence(List<BehaviouralCompetence> behaviouralCompetence) {
		this.behaviouralCompetence = behaviouralCompetence;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getWeightage() {
		return weightage;
	}

	public void setWeightage(String weightage) {
		this.weightage = weightage;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public List<TrainingNeedsDetails> getTrainingNeedsDetails() {
		return trainingNeedsDetails;
	}

	public void setTrainingNeedsDetails(List<TrainingNeedsDetails> trainingNeedsDetails) {
		this.trainingNeedsDetails = trainingNeedsDetails;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getKraTotalCalculation() {
		return kraTotalCalculation;
	}

	public void setKraTotalCalculation(String kraTotalCalculation) {
		this.kraTotalCalculation = kraTotalCalculation;
	}

	public String getExtraOrdinaryTotalCalculation() {
		return extraOrdinaryTotalCalculation;
	}

	public void setExtraOrdinaryTotalCalculation(String extraOrdinaryTotalCalculation) {
		this.extraOrdinaryTotalCalculation = extraOrdinaryTotalCalculation;
	}

	public String getBehaviouralCompetenceTotalCalculation() {
		return behaviouralCompetenceTotalCalculation;
	}

	public void setBehaviouralCompetenceTotalCalculation(String behaviouralCompetenceTotalCalculation) {
		this.behaviouralCompetenceTotalCalculation = behaviouralCompetenceTotalCalculation;
	}

}
