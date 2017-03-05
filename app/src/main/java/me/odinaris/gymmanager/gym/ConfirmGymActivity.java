package me.odinaris.gymmanager.gym;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import me.odinaris.gymmanager.R;
import me.odinaris.gymmanager.application.applicationInfo;
import me.odinaris.gymmanager.main.MainActivity;
import me.odinaris.gymmanager.user.userBean;
import me.odinaris.gymmanager.utils.SelectImageView;


/**
 * Created by Odinaris on 2016/12/12.
 */

public class ConfirmGymActivity extends AppCompatActivity implements View.OnClickListener {

	@BindView(R.id.confirm_gym_cv_pay) CardView pay;
	@BindView(R.id.confirm_gym_tv_name) TextView gymName;
	@BindView(R.id.confirm_gym_tv_cost) TextView cost;
	@BindView(R.id.confirm_gym_tv_time) TextView useTime;
	@BindView(R.id.confirm_basketball) LinearLayout basketball;
	@BindView(R.id.confirm_badminton) LinearLayout badminton;
	@BindView(R.id.confirm_badmintonRacket) LinearLayout badmintonRacket;
	@BindView(R.id.confirm_tabletennis) LinearLayout tabletennis;
	@BindView(R.id.confirm_tabletennisRacket) LinearLayout tabletennisRacket;
	@BindView(R.id.confirm_tennis) LinearLayout tennis;
	@BindView(R.id.confirm_tennisRacket) LinearLayout tennisRacket;
	@BindView(R.id.confirm_volleyball) LinearLayout volleyball;
	private String gymType,gymTime,gymDate,objectId,typeObjectId;
	private int gymId;
	private double gymRent,balance;
	private DecimalFormat df = new DecimalFormat("#.00");
	public static final int FLAG_HOMEKEY_DISPATCHED = 0x80000000; //需要自己定义标志
	private String[] equipmentType = {"篮球", "羽毛球", "羽毛球拍", "乒乓球",
			"乒乓球拍", "网球", "网球拍", "排球"};
	private String[] equipmentObjectId = {"TGXB666L", "edHo222A", "FNEL000K", "Qhp9KKKi",
			"10i4IIIN", "CTcF222G", "IFAp666B", "9qTzJJJq"};
	private double[] equipmentUnitPrice = {2,0.5,2,0.2,1.5,0.5,2,2};
	private int[] equipmentFreeNum = {0,0,0,0,0,0,0,0};
	private int[] equipmentUseNum = {0,0,0,0,0,0,0,0};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(FLAG_HOMEKEY_DISPATCHED, FLAG_HOMEKEY_DISPATCHED);//关键代码
		Bmob.initialize(this, "899b5bc7d14343a022b2d59b35da55f5");
		//隐藏标题栏
		supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.act_confirm_gym_order);
		ButterKnife.bind(this);
		initData();
		initOnClickListener();
		initView();
	}

	private void initData() {
		gymType = getIntent().getStringExtra("type");
		gymTime = getIntent().getStringExtra("time");
		gymDate = getIntent().getStringExtra("date");
		gymId = Integer.parseInt(getIntent().getStringExtra("id"));
		gymRent = Double.parseDouble(getIntent().getStringExtra("rent"));
		objectId = getIntent().getStringExtra("objectId");
	}

	private void initOnClickListener() {
		basketball.setOnClickListener(this);
		badminton.setOnClickListener(this);
		badmintonRacket.setOnClickListener(this);
		tabletennis.setOnClickListener(this);
		tabletennisRacket.setOnClickListener(this);
		tennis.setOnClickListener(this);
		tennisRacket.setOnClickListener(this);
		volleyball.setOnClickListener(this);
		pay.setOnClickListener(this);
	}

	private void initView() {
		gymName.setText(gymId+"号"+gymType);
		useTime.setText(gymDate+gymTime);
		userBean userInfo = BmobUser.getCurrentUser(userBean.class);
		balance = userInfo.getUserBalance();
		cost.setText("￥"+gymRent);
		if(balance < gymRent){
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			dialog.setMessage("余额不足！请充值").show();
		}
		final String[] equipmentType = getEquipmentName(gymType);
		if(equipmentType != null){
			BmobQuery<equipmentInfo> query = new BmobQuery<>();
			query.addWhereContainedIn("equipmentType", Arrays.asList(equipmentType));
			query.findObjects(new FindListener<equipmentInfo>() {
				@Override
				public void done(List<equipmentInfo> list, BmobException e) {
					if(e == null){
						for( equipmentInfo type : list){
							int freeNum = type.getFreeEquipment();
							double unitPrice = type.getUnitPrice();
							String typeName = type.getEquipmentType();
							switch (type.getEquipmentType()){
								case "篮球":
									showEquipment(basketball,freeNum,typeName,unitPrice,0);
									break;
								case "羽毛球":
									showEquipment(badminton,freeNum,typeName, unitPrice, 1);
									break;
								case "羽毛球拍":
									showEquipment(badmintonRacket,freeNum,typeName, unitPrice, 2);
									break;
								case "乒乓球":
									showEquipment(tabletennis,freeNum,typeName, unitPrice, 3);
									break;
								case "乒乓球拍":
									showEquipment(tabletennisRacket,freeNum,typeName, unitPrice, 4);
									break;
								case "网球":
									showEquipment(tennis,freeNum,typeName, unitPrice, 5);
									break;
								case "网球拍":
									showEquipment(tennisRacket,freeNum,typeName, unitPrice, 6);
									break;
								case "排球":
									showEquipment(volleyball,freeNum,typeName, unitPrice, 7);
									break;}}}}});}
	}

	private void showEquipment(final LinearLayout equipment, final int freeNum, final String equipmentName,
	                           final double unitPrice, final int i) {
		final TextView equipmentType,freeEquipment,num;
		final ImageView subtract,add;
		final SelectImageView select;
		equipment.setVisibility(View.VISIBLE);
		equipmentType = (TextView) equipment.findViewById(R.id.equipment_tv_name);
		freeEquipment = (TextView) equipment.findViewById(R.id.equipment_tv_remain);
		select = (SelectImageView)equipment.findViewById(R.id.equipment_iv_select);
		subtract = (ImageView)equipment.findViewById(R.id.equipment_iv_subtract);
		add = (ImageView)equipment.findViewById(R.id.equipment_iv_add);
		num = (TextView)equipment.findViewById(R.id.equipment_tv_num);
		equipmentUseNum[i] = 0;
		equipmentFreeNum[i] = freeNum;
		equipmentType.setText(equipmentName);
		freeEquipment.setText("剩余"+freeNum);
		select.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(view == select){
					if(!select.flag){
						select.setImageBitmap(readBitMap(R.drawable.icon_selected));
						subtract.setVisibility(View.VISIBLE);
						num.setVisibility(View.VISIBLE);
						add.setVisibility(View.VISIBLE);
						freeEquipment.setVisibility(View.VISIBLE);
						select.flag = true;
					}else {
						double originCost = Double.parseDouble(cost.getText().toString().split("￥")[1]);
						originCost -= unitPrice * Integer.parseInt(num.getText().toString());
						equipmentFreeNum[i] = freeNum;
						num.setText("0");
						equipmentUseNum[i] = 0;
						freeEquipment.setText("剩余"+freeNum);
						select.setImageBitmap(readBitMap(R.drawable.icon_unselected));
						subtract.setVisibility(View.GONE);
						num.setVisibility(View.GONE);
						add.setVisibility(View.GONE);
						freeEquipment.setVisibility(View.GONE);
						cost.setText("￥"+df.format(originCost));
						select.flag = false;}}}});
		add.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				int freeNum = Integer.parseInt(freeEquipment.getText().toString().split("剩余")[1]);
				double currentCost = Double.parseDouble(cost.getText().toString().split("￥")[1]);
				if(freeNum!=0){
					if((currentCost + unitPrice) <= balance){
						equipmentFreeNum[i]--;
						equipmentUseNum[i]++;
						currentCost = currentCost + unitPrice;
						num.setText(""+equipmentUseNum[i]);
						freeEquipment.setText("剩余"+equipmentFreeNum[i]);
						cost.setText("￥"+df.format(currentCost));
					}else {
						AlertDialog.Builder dialog = new AlertDialog.Builder(ConfirmGymActivity.this);
						dialog.setMessage("余额不足!不能再添加器材了!").setPositiveButton("知道了",null).show();
					}
				}}});
		subtract.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				double currentCost = Double.parseDouble(cost.getText().toString().split("￥")[1]);
				if(equipmentUseNum[i]!=0){
					equipmentFreeNum[i]++;
					equipmentUseNum[i]--;
					currentCost = currentCost - unitPrice;
					num.setText(""+equipmentUseNum[i]);
					freeEquipment.setText("剩余"+equipmentFreeNum[i]);
					cost.setText("￥"+df.format(currentCost));
				}}});

	}

	private String[] getEquipmentName(String gymType) {
		String[] type;
		switch (gymType){
			case "篮球场":
				type = new String[]{equipmentType[0]};
				return type;
			case "羽毛球场":
				type = new String[]{equipmentType[1],equipmentType[2]};
				return type;
			case "乒乓球场":
				type = new String[]{equipmentType[3],equipmentType[4]};
				return type;
			case "网球场":
				type = new String[]{equipmentType[5],equipmentType[6]};
				return type;
			case "排球场":
				type = new String[]{equipmentType[7]};
				return type;
			default:
				return null;
		}
	}


	@Override
	public void onClick(View view) {
		switch(view.getId()){
			case R.id.confirm_gym_cv_pay:
				AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmGymActivity.this);
				builder.setMessage("预定成功!")
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						balance -= Double.parseDouble(cost.getText().toString().split("￥")[1]);
						final userBean updateUser = new userBean();
						updateUser.setUserBalance(balance);
						userBean currentUser = BmobUser.getCurrentUser(userBean.class);
						final String userName = currentUser.getUsername();
						final String userId = currentUser.getUserId();
						updateUser.update(currentUser.getObjectId(), new UpdateListener() {
							@Override
							public void done(BmobException e) {
								List<BmobObject> equipmentList = new ArrayList<BmobObject>();
								final applicationInfo newApplication = new applicationInfo();
								for(int i = 0; i< equipmentFreeNum.length; i++){
									if(equipmentFreeNum[i] != 0){
										equipmentInfo e1 = new equipmentInfo();
										e1.setObjectId(equipmentObjectId[i]);
										e1.setFreeEquipment(equipmentFreeNum[i]);
										e1.setEquipmentType(equipmentType[i]);
										e1.setUnitPrice(equipmentUnitPrice[i]);
										if(newApplication.getEquipmentNum1()==0){
											newApplication.setEquipmentName1(equipmentType[i]);
											newApplication.setEquipmentNum1(equipmentUseNum[i]);
										}else {
											newApplication.setEquipmentName2(equipmentType[i]);
											newApplication.setEquipmentNum2(equipmentUseNum[i]);
										}
										equipmentList.add(e1);
									}
								}
								new BmobBatch().updateBatch(equipmentList).doBatch(new QueryListListener<BatchResult>() {
									@Override
									public void done(List<BatchResult> list, BmobException e) {
										newApplication.setApplicationCost(
												Double.parseDouble(cost.getText().toString().split("￥")[1]));
										newApplication.setUserName(userName);
										newApplication.setUserId(userId);
										newApplication.setType(gymType);
										newApplication.setTypeId(gymId);
										newApplication.setUseDate(gymDate);
										newApplication.setUseTime(gymTime);
										newApplication.setApplicationState("已预约");
										newApplication.setTypeObjectId(objectId);
										newApplication.save(new SaveListener<String>() {
											@Override
											public void done(String s, BmobException e) {
												Intent intent = new Intent(ConfirmGymActivity.this,MainActivity.class).
														setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
												//Intent.FLAG_ACTIVITY_CLEAR_TOP
												//Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK
												startActivity(intent);
											}
										});
									}
								});
							}
						});
					}
				}).show();
				break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_DOWN){
			AlertDialog.Builder dialog = new AlertDialog.Builder(ConfirmGymActivity.this);
			dialog.setMessage("您真的要取消预约吗？").setNegativeButton("不，我再想想",null)
					.setPositiveButton("是的，我要取消", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface, int i) {
							gymInfo gymInfo = new gymInfo(gymType,gymId,"空闲",gymTime,gymDate,gymRent);
							gymInfo.update(objectId, new UpdateListener() {
								@Override
								public void done(BmobException e) {
									finish();
								}
							});
						}
					}).show();
		}
		return false;
	}

	private Bitmap readBitMap(int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		InputStream is = getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}
}
