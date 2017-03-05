package me.odinaris.gymmanager.utils;

import android.view.View;
import android.view.animation.Animation;
import android.widget.ListView;

import java.util.List;

/**
 * 实现多个View间的顺序执行动画
 * 1.AnimManager实现AnimationListener接口，并且提供接口输入Views和Anims。
 * 2.使用时，需在代码中初始化，然后传入Views和Anims，最后执行startAnimation()。
 * 3.startAnimation()中检测Views和Anims的大小并抛出异常，最后执行excute();
 * 4.excute()中获取当前position的View和Anim并设置AnimationListener，最后执行当前position的动画
 * 5.重写了onAnimationStart()和onAnimationEnd()
 * 其中onAnimationStart()将当前View的可见性设置为VISIBLE,需要在代码中将Views中所有View事先设置为不可见
 * 其中onAnimationEnd()将position加1后再次执行excute()，实现顺序执行
 */

public class AnimManager implements Animation.AnimationListener {

	private final List<View> views;
	private final List<Animation> anims;
	private boolean isLast;
	private int position;
	private View view;
	private Animation animation;

	//构造函数
	public AnimManager(List<View> views,List<Animation> anims){
		this.views = views;
		this.anims = anims;
	}


	@Override
	public void onAnimationStart(Animation animation) {
		if(view == null){
			throw new RuntimeException("第" + position + "个View是空的");
		}
		view.setVisibility(View.VISIBLE);
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		if(position == views.size() - 1){
			//动画执行结束
			return;
		}
		position++;
		excute();
	}

	@Override
	public void onAnimationRepeat(Animation animation) {

	}

	public void startAnimation(){
		if(views == null || views.size() < 0){
			throw new RuntimeException("views集合为空！无法开始动画！");
		}
		if(anims ==null || anims.size() < 0){
			throw new RuntimeException("anims集合为空！无法开始动画！");
		}

		excute();
	}

	//当前View执行动画
	private void excute() {
		view = views.get(position);
		animation = anims.get(position);
		animation.setAnimationListener(this);
		view.startAnimation(animation);
		//view.startAnimation(animation);
	}

	public int getPosition(){
		return position;
	}



}
