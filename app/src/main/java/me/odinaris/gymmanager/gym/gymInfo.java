package me.odinaris.gymmanager.gym;

import cn.bmob.v3.BmobObject;

/**
 * Created by Odinaris on 2016/12/6.
 */

public class gymInfo extends BmobObject{
	private int gymId;
	private String gymType;
	private double gymRent;
	private String gymState;
	private String gymTime = "";
	private String gymDate = "";

	public gymInfo(){}

	public gymInfo(int gymId, String gymState){
		this.gymId = gymId;
		this.gymState = gymState;
	}

	public gymInfo(String gymType,int gymId, String gymState, String gymTime, String gymDate,double gymRent){
		this.gymType = gymType;
		this.gymId = gymId;
		this.gymState = gymState;
		this.gymTime = gymTime;
		this.gymDate = gymDate;
		this.gymRent = gymRent;
	}

	public int getGymId() {
		return gymId;
	}

	public void setGymId(int gymId) {
		this.gymId = gymId;
	}

	public String getGymType() {
		return gymType;
	}

	public void setGymType(String gymType) {
		this.gymType = gymType;
	}

	public String getGymState() {
		return gymState;
	}

	public void setGymState(String gymState) {
		this.gymState = gymState;
	}

	public String getGymTime() {
		return gymTime;
	}

	public void setGymTime(String gymTime) {
		this.gymTime = gymTime;
	}

	public String getGymDate() {
		return gymDate;
	}

	public void setGymDate(String gymDate) {
		this.gymDate = gymDate;
	}

	public double getGymRent() {
		return gymRent;
	}

	public void setGymRent(double gymRent) {
		this.gymRent = gymRent;
	}
}
