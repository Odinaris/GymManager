package me.odinaris.gymmanager.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import me.odinaris.gymmanager.login.SignInActivity;
import me.odinaris.gymmanager.user.userBean;
import me.odinaris.gymmanager.R;

/**
 * Created by Odinaris on 2016/12/2.
 */

public class StartActivity extends Activity {
	@BindView(R.id.iv_start) ImageView iv_start;
	@BindView(R.id.container_start)RelativeLayout container;
	@BindView(R.id.test)TextView test;
	private Bitmap bmp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_start);
		Bmob.initialize(this, "899b5bc7d14343a022b2d59b35da55f5");
		ButterKnife.bind(this);
		initImage();
	}

	private void initImage() {
		bmp = readBitMap(R.drawable.bg_blue);
		iv_start.setImageBitmap(bmp);
		//设置缩放动画
		Animation animation = AnimationUtils.loadAnimation(StartActivity.this,R.anim.anim_start);
		animation.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				//可以在这里先进行某些操作
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				startActivity();
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});
		iv_start.startAnimation(animation);

	}

	private void startActivity() {
		userBean userInfo = BmobUser.getCurrentUser(userBean.class);//获取本地注册用户信息
		if(userInfo!=null){
			startActivity(new Intent(StartActivity.this, MainActivity.class));
			finish();
			overridePendingTransition(R.anim.scale_in, R.anim.scale_out);
		}else {
			startActivity(new Intent(StartActivity.this, SignInActivity.class));
			finish();
			overridePendingTransition(R.anim.scale_in, R.anim.scale_out);
		}
	}

	private Bitmap readBitMap(int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		InputStream is = getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}

	@Override
	protected void onStop() {
		super.onStop();
		bmp.recycle();
		System.gc();
	}

}
