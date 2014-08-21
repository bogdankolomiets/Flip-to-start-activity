package com.example.actionbarlogotest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

public class MainActivity extends Activity implements
		OnClickListener {
	private ImageView imageview;
	private ViewGroup mContainer;
	/**
	 * 这个变量设置的是图片，如果是多张图片，那么可以用数组，如 private static final int IMAGE = new int[]{
	 * R.drawable.ro, R.drawable.icon }; 有多少图片就放多少，我这里做的只是一张图片的翻转
	 * 
	 */
	private static final int IMAGE = R.drawable.test;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		imageview = (ImageView) findViewById(R.id.image);
		mContainer = (ViewGroup) findViewById(R.id.container);
		/**
		 * 设置最新显示的图片 如果是数组，那么可以写成IMAGE[int]
		 * 
		 */
		imageview.setImageResource(IMAGE);
		/**
		 * 
		 * 设置ImageView的OnClickListener
		 * 
		 */
		imageview.setClickable(true);
		imageview.setFocusable(true);
		imageview.setOnClickListener(this);
	}

	private void applyRotation(int position, float start, float end) {
		// Find the center of the container
		final float centerX = mContainer.getWidth() / 2.0f;
		final float centerY = mContainer.getHeight() / 2.0f;
		final Rotate3d rotation = new Rotate3d(start, end, centerX, centerY,
				310.0f, true);
		rotation.setDuration(500);
		rotation.setFillAfter(true);
		rotation.setInterpolator(new AccelerateInterpolator());
		rotation.setAnimationListener(new DisplayNextView(position));
		mContainer.startAnimation(rotation);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		/**
		 * 
		 * 调用这个方法，就是翻转图片 参数很简单，大家都应该看得懂 简单说下，第一个是位置，第二是开始的角度，第三个是结束的角度
		 * 这里需要说明的是，如果是要回到上一张 把第一个参数设置成-1就行了
		 * 
		 */
		applyRotation(0, 0, 90);
	}

	private final class DisplayNextView implements Animation.AnimationListener {
		private final int mPosition;

		private DisplayNextView(int position) {
			mPosition = position;
		}

		public void onAnimationStart(Animation animation) {
		}

		public void onAnimationEnd(Animation animation) {
			mContainer.post(new SwapViews(mPosition));
		}

		public void onAnimationRepeat(Animation animation) {
		}
	}

	/**
	 * This class is responsible for swapping the views and start the second
	 * half of the animation.
	 */
	private final class SwapViews implements Runnable {
		private final int mPosition;

		public SwapViews(int position) {
			mPosition = position;
		}

		public void run() {
			final float centerX = mContainer.getWidth() / 2.0f;
			final float centerY = mContainer.getHeight() / 2.0f;
			Rotate3d rotation;
			if (mPosition > -1) {
				imageview.setVisibility(View.VISIBLE);
				imageview.requestFocus();
				rotation = new Rotate3d(90, 180, centerX, centerY, 310.0f,
						false);
			} else {
				imageview.setVisibility(View.GONE);
				rotation = new Rotate3d(90, 0, centerX, centerY, 310.0f, false);
			}
			rotation.setDuration(500);
			rotation.setFillAfter(true);
			rotation.setInterpolator(new DecelerateInterpolator());
			mContainer.startAnimation(rotation);
		}
	}
	
	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(-1, R.anim.center_scale2);
	}

}
