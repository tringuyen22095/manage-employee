package com.tri.nguyen.demo.models.entity;

import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.tri.nguyen.demo.models.entity.common.EnumCommon.ObjectStatus;

@MappedSuperclass
public class BaseObject {
	private Date createdDate;
	private Date modifiedDate;
	private int createdBy;
	private int modifiedBy;
	private ObjectStatus objectStatus;

	@PrePersist
	protected void prePersist() {
		this.createdDate = new Date();
		this.objectStatus = this.objectStatus == null ? ObjectStatus.ACTIVE : this.objectStatus;
	}

	@PreUpdate
	protected void preUpdate() {
		this.modifiedDate = new Date();
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public int getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public ObjectStatus getObjectStatus() {
		return objectStatus;
	}

	public void setObjectStatus(ObjectStatus objectStatus) {
		this.objectStatus = objectStatus;
	}

}
