package me.odinaris.gymmanager.application;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;
import me.odinaris.gymmanager.R;
import me.odinaris.gymmanager.gym.equipmentInfo;
import me.odinaris.gymmanager.gym.gymInfo;
import me.odinaris.gymmanager.user.userBean;

/**
 * Created by Odinaris on 2016/12/6.
 */

public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.ViewHolder> implements View.OnClickListener{

	private Context mContext;
	private String applicationType;
	private String[] equipmentType = {"篮球", "羽毛球", "羽毛球拍", "乒乓球",
			"乒乓球拍", "网球", "网球拍", "排球"};
	private String[] equipmentObjectId = {"TGXB666L", "edHo222A", "FNEL000K", "Qhp9KKKi",
			"10i4IIIN", "CTcF222G", "IFAp666B", "9qTzJJJq"};
	private double[] equipmentUnitPrice = {2,0.5,2,0.2,1.5,0.5,2,2};

	private ArrayList<applicationInfo> mApplicationList = new ArrayList<applicationInfo>();

	public ApplicationAdapter(ArrayList<applicationInfo> applicationList, Context mContext, String applicationType){
		mApplicationList = applicationList;
		this.mContext = mContext;
		this.applicationType = applicationType;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
		View view = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.item_application,parent,false);
		ViewHolder holder = new ViewHolder(view);
		return holder;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, final int position){
		final applicationInfo application = mApplicationList.get(position);
		final String typeName = application.getType();
		final int typeId = application.getTypeId();
		final String useTime = application.getUseTime();
		final String useDate = application.getUseDate();
		final String bookTime = application.getUpdatedAt();
		final double cost = application.getApplicationCost();
		final String state = application.getApplicationState();
		final String typeObjectId = application.getTypeObjectId();
		final String equipName1 = application.getEquipmentName1();
		final String equipName2 = application.getEquipmentName2();
		final int equipNum1 = application.getEquipmentNum1();
		final int equipNum2 = application.getEquipmentNum2();
		holder.typeName.setText(typeId+"号"+typeName);
		holder.useTime.setText(useDate+useTime);
		holder.bookTime.setText(bookTime);
		holder.more.setVisibility(View.GONE);
		holder.cost.setText("合计: "+cost+" 元");
		switch (application.getType()){
			case "教室":
				holder.typeLogo.setColorFilter(Color.parseColor("#e14f00"));
				Glide.with(mContext).load(R.drawable.icon_luffy).into(holder.typeLogo);
				holder.cost.setText("");
				holder.topColor.setBackgroundColor(Color.parseColor("#e14f00"));
				break;
			case "篮球场":
				holder.typeLogo.setColorFilter(Color.parseColor("#e14f00"));
				Glide.with(mContext).load(R.drawable.icon_basketball).into(holder.typeLogo);
				holder.topColor.setBackgroundColor(Color.parseColor("#e14f00"));
				break;
			case "乒乓球场":
				holder.typeLogo.setColorFilter(Color.parseColor("#c4363b"));
				Glide.with(mContext).load(R.drawable.icon_tabletennis).into(holder.typeLogo);
				holder.topColor.setBackgroundColor(Color.parseColor("#c4363b"));
				break;
			case "羽毛球场":
				holder.typeLogo.setColorFilter(Color.parseColor("#adb1bc"));
				Glide.with(mContext).load(R.drawable.icon_badminton).into(holder.typeLogo);
				holder.topColor.setBackgroundColor(Color.parseColor("#adb1bc"));
				break;
			case "网球场":
				holder.typeLogo.setColorFilter(Color.parseColor("#6baf3b"));
				Glide.with(mContext).load(R.drawable.icon_tennis).into(holder.typeLogo);
				holder.topColor.setBackgroundColor(Color.parseColor("#6baf3b"));
				break;
			case "排球场":
				holder.typeLogo.setColorFilter(Color.parseColor("#91a0d1"));
				Glide.with(mContext).load(R.drawable.icon_volleyball).into(holder.typeLogo);
				holder.topColor.setBackgroundColor(Color.parseColor("#91a0d1"));
				break;
			default:
				Glide.with(mContext).load(R.drawable.icon_luffy).into(holder.typeLogo);
				break;
		}
		switch (state){
			case "已预约":
				holder.more.setOnClickListener(this);
				holder.cancel.setOnClickListener(this);
				break;
			case "申请中":
				holder.cancel.setText("取消申请");
				holder.more.setOnClickListener(this);
				holder.cancel.setOnClickListener(this);
				break;
			case "已结束":
				holder.cancel.setVisibility(View.GONE);
				holder.more.setOnClickListener(this);
				break;
		}
		holder.more.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

			}
		});
		holder.cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
				builder.setMessage("您确定要取消预约吗？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						gymInfo gymInfo = new gymInfo(typeName,typeId,"空闲",useTime,useDate,5);
						gymInfo.update(typeObjectId, new UpdateListener() {
							@Override
							public void done(BmobException e) {
								userBean updateUser = new userBean();
								userBean currentUser = BmobUser.getCurrentUser(userBean.class);
								updateUser.setUserBalance(currentUser.getUserBalance()+cost);
								updateUser.update(currentUser.getObjectId(), new UpdateListener() {
									@Override
									public void done(BmobException e) {
										applicationInfo application1 = new applicationInfo();
										application1.setObjectId(application.getObjectId());
										application1.delete(new UpdateListener() {
											@Override
											public void done(BmobException e) {
												int state = getUpdateState(equipName1,equipNum1,equipName2,equipNum2);
												switch (state){
													case -1:
														mApplicationList.remove(position);
														notifyItemRemoved(position);
														notifyItemRangeChanged(0,mApplicationList.size()-position);
														break;
													case 0:
														final int pos = getEquipmentPosition(equipName1);
														BmobQuery<equipmentInfo> query = new BmobQuery<equipmentInfo>();
														query.getObject(equipmentObjectId[pos], new QueryListener<equipmentInfo>() {
															@Override
															public void done(equipmentInfo equipmentInfo, BmobException e) {
																int freeNum = equipmentInfo.getFreeEquipment();
																freeNum = freeNum + equipNum1;
																equipmentInfo e1 = new equipmentInfo(equipName1,freeNum,equipmentUnitPrice[pos]);
																e1.update(equipmentObjectId[pos], new UpdateListener() {
																	@Override
																	public void done(BmobException e) {
																		mApplicationList.remove(position);
																		notifyItemRemoved(position);
																		notifyItemRangeChanged(0,mApplicationList.size()-position);
																	}
																});
															}
														});
														break;
													case 1:
														final int pos2 = getEquipmentPosition(equipName2);
														BmobQuery<equipmentInfo> query2 = new BmobQuery<equipmentInfo>();
														query2.getObject(equipmentObjectId[pos2], new QueryListener<equipmentInfo>() {
															@Override
															public void done(equipmentInfo equipmentInfo, BmobException e) {
																int freeNum = equipmentInfo.getFreeEquipment();
																freeNum = freeNum + equipNum2;
																equipmentInfo e2 = new equipmentInfo(equipName2,freeNum,equipmentUnitPrice[pos2]);
																e2.update(equipmentObjectId[pos2], new UpdateListener() {
																	@Override
																	public void done(BmobException e) {
																		mApplicationList.remove(position);
																		notifyItemRemoved(position);
																		notifyItemRangeChanged(0,mApplicationList.size()-position);
																	}
																});
															}
														});
														break;
													case 2:
														final int pos3 = getEquipmentPosition(equipName1);
														BmobQuery<equipmentInfo> query3 = new BmobQuery<equipmentInfo>();
														query3.getObject(equipmentObjectId[pos3], new QueryListener<equipmentInfo>() {
															@Override
															public void done(equipmentInfo equipmentInfo, BmobException e) {
																int freeNum = equipmentInfo.getFreeEquipment();
																freeNum = freeNum + equipNum1;
																equipmentInfo e2 = new equipmentInfo(equipName1,freeNum,equipmentUnitPrice[pos3]);
																e2.update(equipmentObjectId[pos3], new UpdateListener() {
																	@Override
																	public void done(BmobException e) {
																		final int pos4 = getEquipmentPosition(equipName2);
																		BmobQuery<equipmentInfo> query4 = new BmobQuery<equipmentInfo>();
																		query4.getObject(equipmentObjectId[pos4], new QueryListener<equipmentInfo>() {
																			@Override
																			public void done(equipmentInfo equipmentInfo, BmobException e) {
																				int freeNum1 = equipmentInfo.getFreeEquipment();
																				freeNum1 = freeNum1 + equipNum2;
																				equipmentInfo e2 = new equipmentInfo(equipName2,freeNum1,equipmentUnitPrice[pos4]);
																				e2.update(equipmentObjectId[pos4], new UpdateListener() {
																					@Override
																					public void done(BmobException e) {
																						mApplicationList.remove(position);
																						notifyItemRemoved(position);
																						notifyItemRangeChanged(0,mApplicationList.size()-position);
																					}
																				});
																			}
																		});
																	}
																});
															}
														});
														break;
												}
											}
										});
									}
								});
							}
						});
					}
				}).show();
			}
		});
	}

	private int getUpdateState(String equipName1, int equipNum1, String equipName2, int equipNum2) {
		int[] State = new int[]{-1,0,1,2};
		boolean e1,e2;
		e1 = e2 = false;
		for (String anEquipmentType : equipmentType) {
			if (equipName1.equals(anEquipmentType) && equipNum1 != 0) {
				e1 = true;
				break;
			}
		}
		for (String anEquipmentType : equipmentType) {
			if(equipName2!=null){
				if (equipName2.equals(anEquipmentType) && equipNum2 != 0) {
					e2 = true;
					break;
				}
			}

		}
		if(e1&&e2) return State[3];
		else if(e1) return State[1];
			 else if(e2) return State[2];
		          else return State[0];
	}

	private int getEquipmentPosition(String equipName1) {
		for(int i=0;i<equipmentType.length;i++){
			if(equipName1.equals(equipmentType[i])){
				return i;
			}
		}
		return -1;
	}

	@Override
	public int getItemCount(){
		return mApplicationList.size();
	}

	@Override
	public void onClick(View view) {


	}

	static class ViewHolder extends RecyclerView.ViewHolder{
		TextView typeName, useTime, bookTime,cost,more,cancel;
		ImageView typeLogo;
		LinearLayout topColor;
		ViewHolder(View itemView) {
			super(itemView);
			typeName = (TextView) itemView.findViewById(R.id.application_tv_name);
			useTime = (TextView) itemView.findViewById(R.id.application_tv_useTime);
			bookTime = (TextView) itemView.findViewById(R.id.application_tv_bookTime);
			cost = (TextView) itemView.findViewById(R.id.application_tv_cost);
			more = (TextView) itemView.findViewById(R.id.application_tv_more);
			cancel = (TextView) itemView.findViewById(R.id.application_tv_cancel);
			typeLogo = (ImageView) itemView.findViewById(R.id.application_iv_typeLogo);
			topColor = (LinearLayout) itemView.findViewById(R.id.application_ll_name);
		}
	}
}
