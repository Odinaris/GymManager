package me.odinaris.gymmanager.application;

import cn.bmob.v3.BmobObject;

/**
 * Created by Odinaris on 2016/12/18.
 */

public class applicationInfo extends BmobObject {
	private String userName;
	private String userId;
	private String type;
	private int typeId;
	private String useDate;
	private String useTime;
	private String equipmentName1;
	private int equipmentNum1;
	private String equipmentName2;
	private int equipmentNum2;
	private String applicationState;
	private String applicationReason;
	private double applicationCost;

	public String getTypeObjectId() {
		return typeObjectId;
	}

	public void setTypeObjectId(String typeObjectId) {
		this.typeObjectId = typeObjectId;
	}

	private String typeObjectId;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getUseDate() {
		return useDate;
	}

	public void setUseDate(String useDate) {
		this.useDate = useDate;
	}

	public String getUseTime() {
		return useTime;
	}

	public void setUseTime(String useTime) {
		this.useTime = useTime;
	}

	public String getEquipmentName1() {
		return equipmentName1;
	}

	public void setEquipmentName1(String equipmentName1) {
		this.equipmentName1 = equipmentName1;
	}

	public int getEquipmentNum1() {
		return equipmentNum1;
	}

	public void setEquipmentNum1(int equipmentNum1) {
		this.equipmentNum1 = equipmentNum1;
	}

	public String getEquipmentName2() {
		return equipmentName2;
	}

	public void setEquipmentName2(String equipmentName2) {
		this.equipmentName2 = equipmentName2;
	}

	public int getEquipmentNum2() {
		return equipmentNum2;
	}

	public void setEquipmentNum2(int equipmentNum2) {
		this.equipmentNum2 = equipmentNum2;
	}

	public String getApplicationState() {
		return applicationState;
	}

	public void setApplicationState(String applicationState) {
		this.applicationState = applicationState;
	}

	public String getApplicationReason() {
		return applicationReason;
	}

	public void setApplicationReason(String applicationReason) {
		this.applicationReason = applicationReason;
	}

	public double getApplicationCost() {
		return applicationCost;
	}

	public void setApplicationCost(double applicationCost) {
		this.applicationCost = applicationCost;
	}

}
