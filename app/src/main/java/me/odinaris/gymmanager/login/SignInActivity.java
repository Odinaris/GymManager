package me.odinaris.gymmanager.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import me.odinaris.gymmanager.main.MainActivity;
import me.odinaris.gymmanager.user.userBean;
import me.odinaris.gymmanager.R;
import me.odinaris.gymmanager.utils.AnimManager;

/**
 * Created by Odinaris on 2016/12/2.
 */

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{

	@BindView(R.id.signIn_container_ll)
	LinearLayout signInRootLayout;
	@BindView(R.id.signIn_ll_signIn)
	LinearLayout signInLinearLayout;
	@BindView(R.id.signIn_cv_logo)
	CardView logoCardView;
	@BindView(R.id.signIn_btn_sign_in)
	Button signInButton;
	@BindView(R.id.signIn_et_username)
	EditText userNameEditText;
	@BindView(R.id.signIn_et_password)
	EditText userPasswordEditText;
	@BindView(R.id.signIn_tv_register)
	TextView registerTextView;
	@BindView(R.id.signIn_tv_forget)
	TextView forgetTextView;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//隐藏标题栏
		supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
		//隐藏状态栏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.act_signin);
		ButterKnife.bind(this);
		List<View> testViews = getTestViews();
		List<Animation> testAnims = getTestAnims();
		AnimManager animManager = new AnimManager(testViews,testAnims);
		animManager.startAnimation();
		signInButton.setOnClickListener(this);
		registerTextView.setOnClickListener(this);
		forgetTextView.setOnClickListener(this);

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.signIn_btn_sign_in:
				userBean newUser = new userBean();
				newUser.setUsername(userNameEditText.getText().toString());
				newUser.setPassword(userPasswordEditText.getText().toString());
				newUser.login(new SaveListener<userBean>() {
					//注意：不能用save方法进行注册
					@Override
					public void done(userBean s, BmobException e) {
						if(e==null){
							Snackbar.make(signInRootLayout, "登录成功", Snackbar.LENGTH_LONG).setCallback(new Snackbar.Callback() {
								@Override
								public void onDismissed(Snackbar snackbar, int event) {
									super.onDismissed(snackbar, event);
									Intent intent = new Intent(SignInActivity.this,MainActivity.class);
									startActivity(intent);
								}
							}).show();

						}else{
							AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
							builder.setTitle("登录失败")
									.setMessage("错误信息码:"+e.getErrorCode()+"\n"+"错误信息:"+e.getMessage()+"\n")
									.setPositiveButton("知道了",null)
									.show();
						}
					}
				});
				break;
			case R.id.signIn_tv_register:
				Intent intent = new Intent(SignInActivity.this,RegisterActivity.class);
				startActivity(intent);
				break;

		}
	}

	private List<View> getTestViews(){
		List<View> testViews = new ArrayList<View>();
		testViews.add(logoCardView);//logo
		testViews.add(signInLinearLayout);
		/*testViews.add(userNameEditText);
		testViews.add(userIdEditText);
		testViews.add(userPasswordEditText);*/
		testViews.add(signInButton);
		testViews.add(registerTextView);
		for(View v:testViews){
			//将View设置为不可见
			v.setVisibility(View.INVISIBLE);
		}
		return testViews;
	}

	private List<Animation> getTestAnims(){
		List<Animation> testAnims = new ArrayList<Animation>();
		testAnims.add(AnimationUtils.loadAnimation(this,R.anim.scale_in));
		testAnims.add(AnimationUtils.loadAnimation(this,R.anim.translate_bottom_in));
/*		testAnims.add(AnimationUtils.loadAnimation(this,R.anim.translate_bottom_in));
		testAnims.add(AnimationUtils.loadAnimation(this,R.anim.translate_bottom_in));*/
		testAnims.add(AnimationUtils.loadAnimation(this,R.anim.scale_in));
		testAnims.add(AnimationUtils.loadAnimation(this,R.anim.translate_bottom_in));
		return  testAnims;
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
	@Override
	protected void onStop() {
		super.onStop();
		finish();
	}

}
