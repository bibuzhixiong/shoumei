package com.wanhan.shouyu.ui.login.activity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.Resource;
import com.wanhan.shouyu.R;
import com.wanhan.shouyu.base.ActivityManager;
import com.wanhan.shouyu.base.BaseActivity;
import com.wanhan.shouyu.utils.RescourseUtil;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/27.
 */

public class WelcomeActivity extends BaseActivity {
    @Bind(R.id.banner)
    Banner banner;

    @Override
    protected int getLayoutId() {
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);
        return R.layout.activity_welcome;
    }

    @Override
    protected void initToolBar() {

    }

    @Override
    protected void initView() {
        banner.setImageLoader(new GlideImageLoader());
        List<Integer> list=new ArrayList<>();
        list.add(R.drawable.welcome1);
        list.add(R.drawable.welcome2);
        list.add(R.drawable.welcome3);
        banner.setImages(list);
//        banner.setBannerTitles(bannerTitle);
        banner.setDelayTime(3000);
        banner.start();

    }
    class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            //Glide 加载图片用法
            Glide.with(context).load(path).into(imageView);
        }
    }
    @OnClick({R.id.tv_register,R.id.tv_login})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_login:
                startActivity(LoginActivity.class);
                finish_Activity(WelcomeActivity.this);
                break;
            case R.id.tv_register:
                startActivity(RegisterActivity.class);
                finish_Activity(WelcomeActivity.this);
                break;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            ActivityManager.getActivityMar().exitApp(WelcomeActivity.this);
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }

    }
}
