package me.odinaris.gymmanager.main;

import android.content.DialogInterface;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;
import me.odinaris.gymmanager.R;
import me.odinaris.gymmanager.application.ApplicationsFragment;
import me.odinaris.gymmanager.gymana.GyManaFragment;
import me.odinaris.gymmanager.gym.GymFragment;
import me.odinaris.gymmanager.room.RoomFragment;
import me.odinaris.gymmanager.user.UserFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

	@BindView(R.id.main_bnb_navigator)
	BottomNavigationBar bnbNavigator;
	@BindView(R.id.main_ll_container)
	LinearLayout llContainer;
	@BindView(R.id.main_tv_title)
	TextView tvTitle;

	private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
	private GymFragment gymFragment = new GymFragment();
	private RoomFragment roomFragment = new RoomFragment();
	private GyManaFragment gyManaFragment = new GyManaFragment();
	private ApplicationsFragment applicationsFragment = new ApplicationsFragment();
	private UserFragment userFragment = new UserFragment();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bmob.initialize(this, "899b5bc7d14343a022b2d59b35da55f5");
		//隐藏标题栏
		supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//此FLAG可使状态栏透明，且当前视图在绘制时，从屏幕顶端开始即top = 0开始绘制，这也是实现沉浸效果的基础
			this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//可不加
		}
		setContentView(R.layout.act_main);
		ButterKnife.bind(this);
		initView();
		initData();
		setDefaultFragment();

	}

	private void initView() {
		fragments.add(0,gymFragment);
		fragments.add(1,gyManaFragment);
		fragments.add(2,applicationsFragment);
		fragments.add(3,userFragment);
		bnbNavigator
				.addItem(
				new BottomNavigationItem(R.drawable.icon_gym,"场馆"))
				.addItem(
				new BottomNavigationItem(R.drawable.bnb_icon_g,"主页"))
				.addItem(
				new BottomNavigationItem(R.drawable.icon_applications,"预约"))
				.addItem(
				new BottomNavigationItem(R.drawable.icon_user,"用户"))
				.setFirstSelectedPosition(1).initialise();
		bnbNavigator.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
			@Override
			public void onTabSelected(int position) {
				FragmentManager fm = getSupportFragmentManager();
				FragmentTransaction transaction = fm.beginTransaction();
				transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				switch (position){
					case 0:
						if (gymFragment==null){
							gymFragment = new GymFragment();
						}

						hideAllFragments(transaction);
						if(!fragments.get(position).isAdded()){
							transaction.add(R.id.main_ll_container,gymFragment).show(gymFragment);
						}else{
							transaction.show(gymFragment);
						}
						tvTitle.setText("场馆");
						break;
					case 1:
						if (gyManaFragment ==null){
							gyManaFragment = new GyManaFragment();
						}
						hideAllFragments(transaction);
						if(!fragments.get(position).isAdded()){
							transaction.add(R.id.main_ll_container,gyManaFragment).show(gyManaFragment);
						}else{
							transaction.show(gyManaFragment);
						}
						tvTitle.setText("主页");
						break;
					case 2:
						if (applicationsFragment==null){
							applicationsFragment = new ApplicationsFragment();
						}
						hideAllFragments(transaction);
						if(!fragments.get(position).isAdded()){
							transaction.add(R.id.main_ll_container,applicationsFragment).show(applicationsFragment);
						}else{
							transaction.show(applicationsFragment);
						}
						tvTitle.setText("预约");
						break;
					case 3:
						if(userFragment==null){
							userFragment = new UserFragment();
						}
						hideAllFragments(transaction);
						if(!fragments.get(position).isAdded()){
							transaction.add(R.id.main_ll_container,userFragment).show(userFragment);
						}else{
							transaction.show(userFragment);
						}
						tvTitle.setText("用户");
						break;
				}
				transaction.commit();//提交事务
			}

			@Override
			public void onTabUnselected(int position) {

			}

			@Override
			public void onTabReselected(int position) {

			}
		});
	}

	private void setDefaultFragment() {
		FragmentManager mFragmentManager = getSupportFragmentManager();
		FragmentTransaction mFragmentTranscation = mFragmentManager.beginTransaction();
		mFragmentTranscation.add(R.id.main_ll_container, gyManaFragment);
		mFragmentTranscation.commit();
	}

	@Override
	public void onClick(View v){
		switch (v.getId()){
			default:
				break;
		}
	}

	private void initData() {


	}


	public void hideAllFragments(FragmentTransaction transaction){
		for(int i=0;i<fragments.size();i++){
			transaction.hide(fragments.get(i));
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_DOWN){
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			dialog.setTitle("您要退出G-Manager吗？").setNegativeButton("不，我再想想",null)
					.setPositiveButton("是", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface, int i) {
							finish();
						}
					}).show();
		}
		return false;
	}
}
