package me.odinaris.gymmanager.room;

/**
 * Created by Odinaris on 2016/12/6.
 */

public class roomBean {
	private int roomId;
	private int roomState;
	private String roomDate;
	private String roomTime;

	public roomBean(){}

	public roomBean(int roomId, int gymState){
		this.roomId = roomId;
		this.roomState = gymState;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}


	public int getRoomState() {
		return roomState;
	}

	public void setRoomState(int gymState) {
		this.roomState = gymState;
	}

	public String getRoomDate() {
		return roomDate;
	}

	public void setRoomDate(String roomDate) {
		this.roomDate = roomDate;
	}

	public String getRoomTime() {
		return roomTime;
	}

	public void setRoomTime(String roomTime) {
		this.roomTime = roomTime;
	}
}