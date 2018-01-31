package com.wanhan.shouyu.ui;

import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.wanhan.shouyu.R;
import com.wanhan.shouyu.base.BaseActivity;
import com.wanhan.shouyu.ui.login.activity.LoginActivity;
import com.wanhan.shouyu.ui.login.activity.PerfectInformationActivity;
import com.wanhan.shouyu.ui.login.activity.WelcomeActivity;
import com.wanhan.shouyu.utils.SharedPreferencesUtil;

/**
 * Created by Administrator on 2018/1/31.
 */

public class SplashActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);
        return R.layout.activity_splash;
    }

    @Override
    protected void initToolBar() {

    }

    @Override
    protected void initView() {
        View rootview = View.inflate(this, R.layout.activity_splash, null);
//        Log.i(ConfigInfo.TAG,"System.currentTimeMillis():"+System.currentTimeMillis());
        setContentView(rootview);

        //初始化渐变动画
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.activity_alpha);
        //设置动画监听器
        animation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //当监听到动画结束时，开始跳转到MainActivity中去
                if(SharedPreferencesUtil.getValue(SplashActivity.this,"USERID","")!=null){
                    if(SharedPreferencesUtil.getValue(SplashActivity.this,"HEIGHT","")!=null){
                        startActivity(MainActivity.class);
                    }else{
                        startActivity(PerfectInformationActivity.class);
                    }

                }else{
                    startActivity(WelcomeActivity.class);
                }
                finish_Activity(SplashActivity.this);

            }
        });

        //开始播放动画
        rootview.startAnimation(animation);
    }
}
