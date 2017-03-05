package me.odinaris.gymmanager.gym;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListListener;
import me.odinaris.gymmanager.R;

/**
 * Created by Odinaris on 2016/12/10.
 */

public class GymPageFragment extends Fragment implements View.OnClickListener{


	@BindView(R.id.gym_rv_list_bookable) RecyclerView recyclerView;
	@BindView(R.id.gym_ll_timePicker) LinearLayout timePicker;
	@BindView(R.id.gym_ll_container) LinearLayout container;
	private String gymType,gymDate;
	private double gymRent;
	private List<gymInfo> mGymList = new ArrayList<gymInfo>();
	private List<gymInfo> mBookableList = new ArrayList<gymInfo>();
	private ArrayList<LinearLayout> timeLayouts = new ArrayList<>();
	private ArrayList<TextView> timeTextViews = new ArrayList<>();

	public void setGymType(String gymType) {
		this.gymType = gymType;
	}

	public void setGymDate(String gymDate) {
		this.gymDate = gymDate;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frg_gym_detail,container,false);
		ButterKnife.bind(this,view);
		gymRent = getRent(gymType);
		init();
		return view;
	}

	private void init() {
		for(int i=0;i<13;i++){
			final int num = i;
			LayoutInflater layoutInflater = LayoutInflater.from(getContext());
			final LinearLayout linearLayout = (LinearLayout)layoutInflater.inflate(R.layout.item_time,timePicker,false);
			final TextView textView = (TextView)linearLayout.findViewById(R.id.time_tv_content);
			String time = (8+i)+":00-"+(9+i)+":00";
			textView.setText(time);
			linearLayout.setBackgroundColor(Color.rgb(235,235,235));
			linearLayout.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					clearStyle();
					textView.setTextColor(Color.rgb(16,115,236));
					textView.setTextSize(16);
					queryGymData(num+1);
				}
			});
			textView.setTextColor(Color.rgb(128,128,128));
			isExceed(textView,linearLayout);
			timePicker.addView(linearLayout);
			timeLayouts.add(linearLayout);
			timeTextViews.add(textView);
		}
		clearStyle();
		setDefaultTime();
	}

	private void setDefaultTime() {
		for(int i=0;i<timeLayouts.size();i++){
			if(timeLayouts.get(i).getVisibility()!=View.GONE){
				timeTextViews.get(i).setTextColor(Color.rgb(16,115,236));
				timeTextViews.get(i).setTextSize(16);
				queryGymData(i+1);
				break;
			}
		}
		if(timeLayouts.get(12).getVisibility() == View.GONE){
			container.removeAllViews();
			LayoutInflater layoutInflater = LayoutInflater.from(getContext());
			LinearLayout linearLayout = (LinearLayout)layoutInflater.inflate(R.layout.tips_empty_gym,container,true);
		}
	}

	private double getRent(String gymType) {
		switch (gymType){
			case "篮球场":
				return 5.0;
			case "羽毛球场":
				return 3.0;
			case "网球场":
				return 4.0;
			case "乒乓球场":
				return 2.0;
			case "排球场":
				return 3.0;
		}
		return 0;
	}

	@Override
	public void onClick(View view) {}

	//更新体育馆当前体育馆占有情况
	private void queryGymData(final int time) {
		final String timeSlice = (time+7)+":00-" + (time+8) + ":00";
		mBookableList.clear();
		mGymList.clear();
		BmobQuery<gymInfo> query = new BmobQuery<>();
		query.addWhereEqualTo("gymType",gymType);
		query.addWhereEqualTo("gymDate",gymDate);
		query.addWhereEqualTo("gymTime",timeSlice);
		query.addWhereEqualTo("gymState","空闲");
		query.findObjects(new FindListener<gymInfo>() {
			@Override
			public void done(List<gymInfo> list, BmobException e) {
				if(e == null){
					if(list.size() == 0){
						List<BmobObject> bmobList = new ArrayList<BmobObject>();
						for(int i=1;i<21;i++){
							gymInfo gymInfo = new gymInfo(gymType,i,"空闲",timeSlice,gymDate,getRent(gymType));
							bmobList.add(gymInfo);
						}
						new BmobBatch().insertBatch(bmobList).doBatch(new QueryListListener<BatchResult>() {
							@Override
							public void done(List<BatchResult> list, BmobException e) {
								if(e == null){
									BmobQuery<gymInfo> query1 = new BmobQuery<>();
									query1.addWhereEqualTo("gymType",gymType);
									query1.addWhereEqualTo("gymDate",gymDate);
									query1.addWhereEqualTo("gymTime",timeSlice);
									query1.addWhereEqualTo("gymState","空闲");
									query1.findObjects(new FindListener<gymInfo>() {
										@Override
										public void done(List<gymInfo> list, BmobException e) {
											for(int i=0;i<list.size();i++){
												mBookableList.add(list.get(i));
											}
											LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
											GymAdapter gymAdapter = new GymAdapter(mBookableList, getContext(), gymType);
											recyclerView.setAdapter(gymAdapter);
											recyclerView.setLayoutManager(layoutManager);
										}
									});
								}
							}
						});
					}else {
						for(int i=0;i<list.size();i++){
							mBookableList.add(list.get(i));
						}
						LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
						GymAdapter gymAdapter = new GymAdapter(mBookableList, getContext(), gymType);
						recyclerView.setAdapter(gymAdapter);
						recyclerView.setLayoutManager(layoutManager);
					}
				}
			}
		});
	}

	//清除所有时间选项样式
	private void clearStyle(){
		for(int i=0;i<timeLayouts.size();i++){
			timeLayouts.get(i).setBackgroundColor(Color.rgb(235,235,235));
			TextView textView = (TextView)timeLayouts.get(i).findViewById(R.id.time_tv_content);
			textView.setTextColor(Color.rgb(128,128,128));
			textView.setTextSize(14);
		}
	}

	private void isExceed(TextView tv, LinearLayout linearLayout) {
		Calendar calendar = Calendar.getInstance();
		int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
		int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
		int time = Integer.parseInt(tv.getText().toString().split("-")[1].split(":")[0]);
		int gymDay = Integer.parseInt(gymDate.split("月")[1].split("日")[0]);
		if(time<=currentHour&&currentDay == gymDay){
			linearLayout.setVisibility(View.GONE);
		}
	}
}
