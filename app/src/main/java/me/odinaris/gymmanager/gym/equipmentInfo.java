package me.odinaris.gymmanager.gym;

import cn.bmob.v3.BmobObject;

/**
 * Created by Odinaris on 2016/12/17.
 */

public class equipmentInfo extends BmobObject{
	private String equipmentType = "";
	private int freeEquipment;
	private double unitPrice;

	public equipmentInfo(String equipmentType,int freeEquipment,double unitPrice){
		this.freeEquipment = freeEquipment;
		this.equipmentType = equipmentType;
		this.unitPrice = unitPrice;
	}

	public equipmentInfo(){}

	public String getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}

	public int getFreeEquipment() {
		return freeEquipment;
	}

	public void setFreeEquipment(int freeEquipment) {
		this.freeEquipment = freeEquipment;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
}
