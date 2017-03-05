package me.odinaris.gymmanager.application;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListListener;
import cn.bmob.v3.listener.QueryListener;
import me.odinaris.gymmanager.R;
import me.odinaris.gymmanager.user.userBean;

/**
 * Created by Odinaris on 2016/12/10.
 */

public class ApplicationPageFragment extends Fragment {

	private String type;
	//超期的预约列表，需要将其修改为已结束
	private ArrayList<BmobObject> exceedTimeList = new ArrayList<BmobObject>();
	private ArrayList<applicationInfo> applicationList = new ArrayList<applicationInfo>();
	@BindView(R.id.application_rv_list) RecyclerView list;

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frg_application_detail,container,false);
		ButterKnife.bind(this,view);
		Bmob.initialize(getActivity(), "899b5bc7d14343a022b2d59b35da55f5");
		initData();
		initView();

		return view;
	}

	private void initView() {
	}

	private void initData() {
		userBean user = userBean.getCurrentUser(userBean.class);
		String userId = user.getUserId();
		BmobQuery<applicationInfo> query = new BmobQuery<>();
		query.addWhereEqualTo("userId",userId);
		query.addWhereEqualTo("applicationState",type);
		query.findObjects(new FindListener<applicationInfo>() {
			@Override
			public void done(List<applicationInfo> alist, BmobException e) {
				if(e == null) {
					for (applicationInfo app : alist) {
						if(idExceed(app,type)){
							app.setApplicationState("已结束");
							exceedTimeList.add(app);
						}else {
							applicationList.add(app);
						}
					}
					if(exceedTimeList.size()!=0){
						new BmobBatch().updateBatch(exceedTimeList).doBatch(new QueryListListener<BatchResult>() {
							@Override
							public void done(List<BatchResult> blist, BmobException e) {
								Collections.reverse(applicationList);
								ApplicationAdapter adapter = new ApplicationAdapter(applicationList,getContext(), type);
								LinearLayoutManager manager = new LinearLayoutManager(getActivity());
								list.setAdapter(adapter);
								list.setLayoutManager(manager);
							}
						});
					}else {
						Collections.reverse(applicationList);
						ApplicationAdapter adapter = new ApplicationAdapter(applicationList,getContext(), type);
						LinearLayoutManager manager = new LinearLayoutManager(getActivity());
						list.setAdapter(adapter);
						list.setLayoutManager(manager);
					}

				}else {
					Snackbar.make(list,e.toString(), Snackbar.LENGTH_LONG);
				}
			}

			private boolean idExceed(applicationInfo app, String type) {
				if(!type.equals("已结束")){
					Calendar calendar = Calendar.getInstance();
					int currentMonth = calendar.get(Calendar.MONTH)+1;
					int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
					int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
					//获取当前申请表中预定的时间段截止时间
					int appHour = Integer.parseInt(app.getUseTime().split("-")[1].split(":")[0]);
					int appMonth = Integer.parseInt(app.getUseDate().split("月")[0]);
					int appDay = Integer.parseInt(app.getUseDate().split("月")[1].split("日")[0]);
					if(currentMonth > appMonth){
						return true;
					}else if(currentMonth == appMonth){
						if(currentDay > appDay){
							return true;
						}else if(currentDay == appDay){
							if(currentHour >= appHour){
								return true;
							}
						}
					}else {
						return false;
					}
					return false;
				}else {
					return false;
				}
			}
		});
	}
}
