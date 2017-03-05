package me.odinaris.gymmanager.application;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.odinaris.gymmanager.R;

/**
 * Created by Odinaris on 2016/12/5.
 */

public class ApplicationsFragment extends Fragment{

	@BindView(R.id.application_tab_type)
	TabLayout typeTabLayout;
	@BindView(R.id.application_vp_content)
	ViewPager contentViewPager;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frg_applications,container,false);
		ButterKnife.bind(this,view);
		initData();
		return view;
	}

	private void initData() {
		String[] titles = getResources().getStringArray(R.array.applicationType);
		ApplicationPageAdapter pageAdapter = new ApplicationPageAdapter(
				getChildFragmentManager(), Arrays.asList(titles));
		this.contentViewPager.setAdapter(pageAdapter);
		this.typeTabLayout.setupWithViewPager(contentViewPager);
	}
}
