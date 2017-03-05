package me.odinaris.gymmanager.gym;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.View;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.odinaris.gymmanager.R;

/**
 * Created by Odinaris on 2016/12/5.
 */

public class GymFragment extends Fragment implements View.OnClickListener{
	@BindView(R.id.gym_cv_basketball) CardView basketballCardView;
	@BindView(R.id.gym_cv_badminton) CardView badmintonCardView;
	@BindView(R.id.gym_cv_tabletennis) CardView tabletennisCardView;
	@BindView(R.id.gym_cv_tennis) CardView tennisCardView;
	@BindView(R.id.gym_cv_volleyball) CardView volleyballCardView;
	@BindView(R.id.gym_iv_basketball) ImageView ivBasketball;
	@BindView(R.id.gym_iv_badminton) ImageView ivBadminton;
	@BindView(R.id.gym_iv_tabletennis) ImageView ivTabletennis;
	@BindView(R.id.gym_iv_tennis) ImageView ivTennis;
	@BindView(R.id.gym_iv_volleyball) ImageView ivVolleyball;
	Bitmap basketball,badminton,tabletennis,tennis,volleyball;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frg_gym,container,false);
		ButterKnife.bind(this,view);
		initClickListener();
		initView();
		return view;
	}

	private void initClickListener() {
		basketballCardView.setOnClickListener(this);
		badmintonCardView.setOnClickListener(this);
		tabletennisCardView.setOnClickListener(this);
		tennisCardView.setOnClickListener(this);
		volleyballCardView.setOnClickListener(this);
	}

	private void initView() {
		basketball = readBitMap(R.drawable.color_basketball);
		badminton = readBitMap(R.drawable.color_badminton);
		tabletennis = readBitMap(R.drawable.color_pingpong);
		tennis = readBitMap(R.drawable.color_tennis);
		volleyball = readBitMap(R.drawable.color_volleyball);
		ivBasketball.setImageBitmap(basketball);
		ivBadminton.setImageBitmap(badminton);
		ivTabletennis.setImageBitmap(tabletennis);
		ivTennis.setImageBitmap(tennis);
		ivVolleyball.setImageBitmap(volleyball);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.gym_cv_basketball:
				Intent basketballDetail = new Intent(getActivity(), GymDetailActivity.class);
				basketballDetail.putExtra("type","篮球场");
				startActivity(basketballDetail);
				break;
			case R.id.gym_cv_badminton:
				Intent badmintonDetail = new Intent(getActivity(), GymDetailActivity.class);
				badmintonDetail.putExtra("type","羽毛球场");
				startActivity(badmintonDetail);
				break;
			case R.id.gym_cv_tabletennis:
				Intent tabletennisDetail = new Intent(getActivity(), GymDetailActivity.class);
				tabletennisDetail.putExtra("type","乒乓球场");
				startActivity(tabletennisDetail);
				break;
			case R.id.gym_cv_tennis:
				Intent tennisDetail = new Intent(getActivity(), GymDetailActivity.class);
				tennisDetail.putExtra("type","网球场");
				startActivity(tennisDetail);
				break;
			case R.id.gym_cv_volleyball:
				Intent volleyballDetail = new Intent(getActivity(), GymDetailActivity.class);
				volleyballDetail.putExtra("type","排球场");
				startActivity(volleyballDetail);
				break;
			default:
				break;
		}
	}
	private Bitmap readBitMap(int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		InputStream is = getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}
	@Override
	public void onStop() {
		super.onStop();
		System.gc();
	}
}