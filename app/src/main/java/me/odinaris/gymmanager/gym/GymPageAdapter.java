package me.odinaris.gymmanager.gym;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import me.odinaris.gymmanager.gym.GymPageFragment;

/**
 * Created by Odinaris on 2016/12/10.
 */

public class GymPageAdapter extends FragmentStatePagerAdapter {
	private List<String> mTitles;
	private String gymType;

	public GymPageAdapter(FragmentManager fm, List<String> titles,String gymType) {
		super(fm);
		this.mTitles = titles;
		this.gymType = gymType;
	}
	@Override
	public Fragment getItem(int position) {
		GymPageFragment gymPageFragment = new GymPageFragment();
		gymPageFragment.setGymDate(mTitles.get(position));
		gymPageFragment.setGymType(gymType);
		return gymPageFragment;
	}
	@Override
	public int getCount() {
		return mTitles.size();
	}
	@Override
	public CharSequence getPageTitle(int position){
		return mTitles.get(position);
	}
}