package me.odinaris.gymmanager.gym;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import me.odinaris.gymmanager.R;

/**
 * Created by Odinaris on 2016/12/6.
 */

public class GymAdapter extends RecyclerView.Adapter<GymAdapter.ViewHolder> {

	private Context mContext;
	private final String gymType;

	private List<gymInfo> mGymList = new ArrayList<gymInfo>();

	public GymAdapter(List<gymInfo> gymList, Context mContext, String gymType){
		mGymList = gymList;
		this.mContext = mContext;
		this.gymType = gymType;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
		View view = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.item_gym,parent,false);
		ViewHolder holder = new ViewHolder(view);
		return holder;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder,int position){
		holder.setIsRecyclable(false);
		final gymInfo gym = mGymList.get(position);
		final int gymId;
		final String gymDate,gymTime,objectId;
		final double gymRent;
		objectId = gym.getObjectId();
	    gymId = gym.getGymId();
		gymTime = gym.getGymTime();
		gymDate = gym.getGymDate();
		gymRent = gym.getGymRent();
		switch (gymType){
			case "篮球场":
				Glide.with(mContext).load(R.drawable.blue_basketball).into(holder.gymLogo);
				break;
			case "羽毛球场":
				Glide.with(mContext).load(R.drawable.blue_badminton).into(holder.gymLogo);
				break;
			case "网球场":
				Glide.with(mContext).load(R.drawable.blue_tennis).into(holder.gymLogo);
				break;
			case "乒乓球场":
				Glide.with(mContext).load(R.drawable.blue_tabletennis).into(holder.gymLogo);
				break;
			case "排球场":
				Glide.with(mContext).load(R.drawable.blue_volleyball).into(holder.gymLogo);
				break;
		}
		holder.gymId.setText(gymId+"号场馆");
		holder.bookCard.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				gym.setGymState("已预约");
				gym.update(objectId, new UpdateListener() {
					@Override
					public void done(BmobException e) {
						Intent intent = new Intent(mContext,ConfirmGymActivity.class);
						intent.putExtra("time",gymTime);
						intent.putExtra("date",gymDate);
						intent.putExtra("id",gymId+"");
						intent.putExtra("type",gymType);
						intent.putExtra("rent",gymRent+"");
						intent.putExtra("objectId",objectId);
						mContext.startActivity(intent);
					}
				});
			}
		});
	}

	@Override
	public int getItemCount(){
		return mGymList.size();
	}



	static class ViewHolder extends RecyclerView.ViewHolder{
		TextView gymId;
		CardView bookCard;
		ImageView gymLogo;
		public ViewHolder(View itemView) {
			super(itemView);
			gymId = (TextView) itemView.findViewById(R.id.gym_tv_gymId);
			bookCard = (CardView)itemView.findViewById(R.id.gym_cv_book);
			gymLogo = (ImageView)itemView.findViewById(R.id.gym_iv_gymLogo);
		}
	}
}
