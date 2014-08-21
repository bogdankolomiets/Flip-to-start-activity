package com.example.actionbarlogotest;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;

@SuppressLint("NewApi")
public class MainActivity2 extends Activity {
	
    ImageView mEnglishList;
    ImageView mFrenchList;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mEnglishList = (ImageView) findViewById(R.id.list_en);
        mFrenchList = (ImageView) findViewById(R.id.list_fr);

        mFrenchList.setRotationY(-90f);
        
        mEnglishList.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				flipit();		
			}
		});
        
        mFrenchList.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {
        		flipit();				
        	}
        });
    }

    private Interpolator accelerator = new AccelerateInterpolator();
    private Interpolator decelerator = new DecelerateInterpolator();
    private void flipit() {
        final ImageView visibleList;
        final ImageView invisibleList;
        if (mEnglishList.getVisibility() == View.GONE) {
            visibleList = mFrenchList;
            invisibleList = mEnglishList;
        } else {
            invisibleList = mFrenchList;
            visibleList = mEnglishList;
        }
        ObjectAnimator visToInvis = ObjectAnimator.ofFloat(visibleList, "rotationY", 0f, 90f);
        visToInvis.setDuration(500);
        visToInvis.setInterpolator(accelerator);
        final ObjectAnimator invisToVis = ObjectAnimator.ofFloat(invisibleList, "rotationY",
                -90f, 0f);
        invisToVis.setDuration(500);
        invisToVis.setInterpolator(decelerator);
        visToInvis.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator anim) {
            	
//            	Log.d("Test", "x:" + visibleList.getX() + "; y:" + visibleList.getY());
//            	ActivityOptions opts = ActivityOptions.makeScaleUpAnimation(
//						visibleList, 0, 0, visibleList.getWidth(),
//						visibleList.getHeight());
//				// Request the activity be started, using the custom animation
//				// options.
//				startActivity(
//						new Intent(MainActivity2.this, MainActivity.class),
//						opts.toBundle());
            	
                visibleList.setVisibility(View.GONE);
                invisToVis.start();
                invisibleList.setVisibility(View.VISIBLE);
                
                Log.d("Test", "anim  visToInvis end");
                
                startActivity(
						new Intent(MainActivity2.this, MainActivity.class));
				overridePendingTransition(R.anim.center_scale, R.anim.hold);
            }
        });
        invisToVis.addListener(new AnimatorListenerAdapter() {
        	 @Override
			public void onAnimationEnd(Animator anim) {
				Log.d("Test", "anim  invisToVis end");
			}
		});
        
        visToInvis.start();
    }
}
