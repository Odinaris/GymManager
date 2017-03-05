package me.odinaris.gymmanager.room;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import me.odinaris.gymmanager.R;

/**
 * Created by Odinaris on 2016/12/6.
 */

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {



	private ArrayList<roomBean> mGymList = new ArrayList<roomBean>();



	public RoomAdapter(ArrayList<roomBean> gymList){
		mGymList = gymList;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
		View view = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.item_room,parent,false);
		ViewHolder holder = new ViewHolder(view);
		return holder;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder,int position){
		roomBean room = mGymList.get(position);
		holder.gymId.setText(room.getRoomId()+"号教室");
		holder.book.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//Intent intent = new Intent(,ConfirmGymActivity.class);
				
			}
		});
	}

	@Override
	public int getItemCount(){
		return mGymList.size();
	}



	static class ViewHolder extends RecyclerView.ViewHolder{
		TextView gymId;
		TextView book;
		public ViewHolder(View itemView) {
			super(itemView);
			gymId = (TextView) itemView.findViewById(R.id.room_tv_roomId);
			book = (TextView) itemView.findViewById(R.id.room_tv_book);
		}
	}
}
