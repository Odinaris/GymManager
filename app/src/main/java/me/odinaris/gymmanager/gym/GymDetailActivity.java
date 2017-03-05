package me.odinaris.gymmanager.gym;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;
import me.odinaris.gymmanager.R;

/**
 * Created by Odinaris on 2016/12/6.
 */

public class GymDetailActivity extends AppCompatActivity implements View.OnClickListener{

	@BindView(R.id.gym_rl_title)
	RelativeLayout title_container;
	@BindView(R.id.gym_tab_date)
	TabLayout typeTabLayout;
	@BindView(R.id.gym_vp_content)
	ViewPager contentViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bmob.initialize(this, "899b5bc7d14343a022b2d59b35da55f5");
		//隐藏标题栏
		supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.act_gym_detail);
		ButterKnife.bind(this);
		initView();
		initData();
	}

	private void initData() {
		String gymType = getIntent().getStringExtra("type");
		SimpleDateFormat sDateFormat = new SimpleDateFormat("MM月dd日");
		Calendar calendar = Calendar.getInstance();
		String[] dateList = new String[3];
		String today = sDateFormat.format(calendar.getTime());
		calendar.roll(Calendar.DAY_OF_YEAR,1);
		String tomorrow = sDateFormat.format(calendar.getTime());
		calendar.roll(Calendar.DAY_OF_YEAR,1);
		String aftertomorrow = sDateFormat.format(calendar.getTime());
		dateList[0] = today;
		dateList[1] = tomorrow;
		dateList[2] = aftertomorrow;
		GymPageAdapter pageAdapter = new GymPageAdapter(
				getSupportFragmentManager(), Arrays.asList(dateList),gymType);
		this.contentViewPager.setAdapter(pageAdapter);
		contentViewPager.setCurrentItem(0);

		this.typeTabLayout.setupWithViewPager(contentViewPager);
	}

	private void initView() {
		TextView title = (TextView)title_container.findViewById(R.id.tv_title);
		title.setText("预定场地");
	}

	@Override
	public void onClick(View view) {

	}
}
