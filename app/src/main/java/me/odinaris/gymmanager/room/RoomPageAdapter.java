package me.odinaris.gymmanager.room;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import me.odinaris.gymmanager.room.RoomPageFragment;

/**
 * Created by Odinaris on 2016/12/10.
 */

public class RoomPageAdapter extends FragmentStatePagerAdapter {

	private List<String> mTitles;

	public RoomPageAdapter(FragmentManager fm, List<String> titles) {
		super(fm);
		this.mTitles = titles;
	}


	@Override
	public Fragment getItem(int position) {
		RoomPageFragment roomPageFragment = new RoomPageFragment();
		roomPageFragment.setRoomTitle(mTitles.get(position));
		roomPageFragment.setRoomType(position);
		return roomPageFragment;
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
