package me.odinaris.gymmanager.user;

import cn.bmob.v3.BmobUser;

/**
 * Created by Odinaris on 2016/11/21.
 */

public class userBean extends BmobUser{
	private String userId;//用户Id(学号)
	private String userGender;//用户性别
	private double userBalance = 200;//用户当前余额


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserGender() {
		return userGender;
	}

	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}

	public double getUserBalance() {
		return userBalance;
	}

	public void setUserBalance(double userBalance) {
		this.userBalance = userBalance;
	}
}
