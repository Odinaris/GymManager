package me.odinaris.gymmanager.room;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.odinaris.gymmanager.R;

/**
 * Created by Odinaris on 2016/12/5.
 */

public class RoomFragment extends Fragment{
	@BindView(R.id.room_tab_date)
	TabLayout typeTabLayout;
	@BindView(R.id.room_vp_content)
	ViewPager contentViewPager;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frg_room,container,false);
		ButterKnife.bind(this,view);
		initData();

		return view;
	}

	private void initData() {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("MM-dd");
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
		RoomPageAdapter pageAdapter = new RoomPageAdapter(
				getChildFragmentManager(), Arrays.asList(dateList));
		this.contentViewPager.setAdapter(pageAdapter);
		this.typeTabLayout.setupWithViewPager(contentViewPager);
	}
}
