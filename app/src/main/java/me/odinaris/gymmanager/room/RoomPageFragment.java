package me.odinaris.gymmanager.room;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.odinaris.gymmanager.R;

/**
 * Created by Odinaris on 2016/12/10.
 */

public class RoomPageFragment extends Fragment {

	private int roomType;
	private String roomTitle;
	@BindView(R.id.room_rv_list_bookable)
	RecyclerView recyclerView;
	private ArrayList<roomBean> mRoomList = new ArrayList<roomBean>();
	private ArrayList<roomBean> mBookableList = new ArrayList<roomBean>();

	public void setRoomType(int roomType) {
		this.roomType = roomType;
	}

	public void setRoomTitle(String roomTitle) {
		this.roomTitle = roomTitle;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frg_room_detail,container,false);
		ButterKnife.bind(this,view);
		initData();
		return view;
	}

	private void initData() {
		java.util.Random random=new java.util.Random();// 定义随机类
		for(int i=0;i<20;i++){
			mRoomList.add(new roomBean(i+1,random.nextInt(2)));
			if(mRoomList.get(i).getRoomState() == 0){
				mBookableList.add(new roomBean(i+1,0));
			}
		}
		LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
		recyclerView.setLayoutManager(layoutManager);
		RoomAdapter roomAdapter = new RoomAdapter(mBookableList);
		recyclerView.setAdapter(roomAdapter);
	}
}
