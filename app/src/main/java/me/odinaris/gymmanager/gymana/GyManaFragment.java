package me.odinaris.gymmanager.gymana;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.odinaris.gymmanager.R;

/**
 * Created by Odinaris on 2016/12/5.
 */

public class GyManaFragment extends Fragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frg_g,container,false);

		ButterKnife.bind(this,view);

		return view;
	}
}
