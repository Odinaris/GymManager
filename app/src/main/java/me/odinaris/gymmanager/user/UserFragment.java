package me.odinaris.gymmanager.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import me.odinaris.gymmanager.R;
import me.odinaris.gymmanager.login.SignInActivity;

/**
 * Created by Odinaris on 2016/12/5.
 */

public class UserFragment extends Fragment implements View.OnClickListener{

	@BindView(R.id.user_userName) TextView userName;
	@BindView(R.id.user_userBalance) TextView userBalance;
	@BindView(R.id.user_editUserInfo) CardView editUserInfo;
	@BindView(R.id.user_sign_out) CardView signOut;
	@BindView(R.id.user_logo) ImageView logo;
	private DecimalFormat df = new DecimalFormat("#.00");

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frg_user,container,false);
		ButterKnife.bind(this,view);
		initData();
		initView();
		return view;
	}


	@Override
	public void onHiddenChanged(boolean b){
		super.onHiddenChanged(b);
		if(!b){
			initData();
		}

	}

	private void initView() {
		signOut.setOnClickListener(this);
		editUserInfo.setOnClickListener(this);
		Glide.with(getActivity()).load(R.drawable.icon_luffy).into(logo);
	}

	private void initData() {
		userBean userInfo = userBean.getCurrentUser(userBean.class);
		BmobQuery<userBean> query = new BmobQuery<userBean>();
		query.addWhereEqualTo("objectId",userInfo.getObjectId());
		query.findObjects(new FindListener<userBean>() {
			@Override
			public void done(List<userBean> list, BmobException e) {
				userName.setText(list.get(0).getUsername());
				userBalance.setText("当前余额:"+df.format(list.get(0).getUserBalance()));
			}
		});

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.user_sign_out:
				BmobUser.logOut();   //清除缓存用户对象
				Intent loginOut = new Intent(getActivity(), SignInActivity.class);
				startActivity(loginOut);
				break;
			case R.id.user_editUserInfo:
				Intent edit = new Intent(getActivity(), EditUserInfo.class);
				startActivity(edit);
		}
	}
}