package me.odinaris.gymmanager.application;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by Odinaris on 2016/12/10.
 */

public class ApplicationPageAdapter extends FragmentStatePagerAdapter {

	static final String TAB_TAG = "applications";
	private List<String> mTitles;

	public ApplicationPageAdapter(FragmentManager fm,List<String> titles) {
		super(fm);
		this.mTitles = titles;
	}


	@Override
	public Fragment getItem(int position) {
		ApplicationPageFragment applicationsFragment = new ApplicationPageFragment();
		String[] title = mTitles.get(position).split(TAB_TAG);
		applicationsFragment.setType(title[0]);
		return applicationsFragment;
	}

	@Override
	public int getCount() {
		return mTitles.size();
	}

	@Override
	public CharSequence getPageTitle(int position){
		return mTitles.get(position).split(TAB_TAG)[0];
	}
}
