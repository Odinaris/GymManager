package me.odinaris.gymmanager.user;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;
import me.odinaris.gymmanager.R;

/**
 * Created by Odinaris on 2016/12/12.
 */

public class EditUserInfo extends AppCompatActivity {

	@BindView(R.id.edit_title)
	RelativeLayout titleConatainer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bmob.initialize(this, "899b5bc7d14343a022b2d59b35da55f5");
		//隐藏标题栏
		supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.act_edit_userinfo);
		ButterKnife.bind(this);
		initView();
		initData();
	}

	private void initData() {

	}

	private void initView() {
		TextView title = (TextView)titleConatainer.findViewById(R.id.tv_title);
		title.setText("修改信息");
	}
}
