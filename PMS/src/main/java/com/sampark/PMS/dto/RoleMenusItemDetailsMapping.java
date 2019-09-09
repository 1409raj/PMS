package com.sampark.PMS.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "role_menus_item_details_mapping")
public class RoleMenusItemDetailsMapping {
	@Id
	@GeneratedValue
	@Column(name="ID")
	private Integer id;
	@Column(name="ROLE_ID")
	private Integer roleId;
	@Column(name="MENUS_ITEM_DETAILS_ID")
	private Integer menusItemDetailsId;
	@Column(name="CAN_VIEW")
	private Integer canView;
	@Column(name="CAN_EDIT")
	private Integer canEdit;
	@Column(name="CAN_ADD")
	private Integer canAdd;
	@Column(name="CAN_APPROVE_AUTHENTICATION")
	private Integer canApproveAuthentication;
	@Column(name="CREATED_ON")
	private Date createdOn;
	@Column(name="MODIFIED_ON")
	private Date modifiedOn;
	@Column(name="CREATED_BY")
	private String createdBy;
	@Column(name="MODIFIED_BY")
	private String modifiedBy;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Integer getMenusItemDetailsId() {
		return menusItemDetailsId;
	}
	public void setMenusItemDetailsId(Integer menusItemDetailsId) {
		this.menusItemDetailsId = menusItemDetailsId;
	}
	public Integer getCanView() {
		return canView;
	}
	public void setCanView(Integer canView) {
		this.canView = canView;
	}
	public Integer getCanEdit() {
		return canEdit;
	}
	public void setCanEdit(Integer canEdit) {
		this.canEdit = canEdit;
	}
	public Integer getCanAdd() {
		return canAdd;
	}
	public void setCanAdd(Integer canAdd) {
		this.canAdd = canAdd;
	}
	public Integer getCanApproveAuthentication() {
		return canApproveAuthentication;
	}
	public void setCanApproveAuthentication(Integer canApproveAuthentication) {
		this.canApproveAuthentication = canApproveAuthentication;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public Date getModifiedOn() {
		return modifiedOn;
	}
	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	@Override
	public String toString() {
		return "RoleMenusItemDetailsMapping [id=" + id + ", roleId=" + roleId + ", menusItemDetailsId="
				+ menusItemDetailsId + ", canView=" + canView + ", canEdit=" + canEdit + ", canAdd=" + canAdd
				+ ", canApproveAuthentication=" + canApproveAuthentication + ", createdOn=" + createdOn
				+ ", modifiedOn=" + modifiedOn + ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy + "]";
	}
	
	
	
}
