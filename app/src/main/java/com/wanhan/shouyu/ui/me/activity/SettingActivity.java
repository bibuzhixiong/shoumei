package com.wanhan.shouyu.ui.me.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.wanhan.shouyu.R;
import com.wanhan.shouyu.base.BaseActivity;
import com.wanhan.shouyu.base.BaseToolbar;
import com.wanhan.shouyu.ui.login.activity.WelcomeActivity;
import com.wanhan.shouyu.utils.SharedPreferencesUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/2/1.
 */

public class SettingActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    BaseToolbar toolbar;
    @Bind(R.id.ll_binding)
    LinearLayout llBinding;
    @Bind(R.id.ll_update_password)
    LinearLayout llUpdatePassword;
    @Bind(R.id.ll_about_us)
    LinearLayout llAboutUs;
    @Bind(R.id.ll_exit)
    LinearLayout llExit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initToolBar() {
        toolbar.setLeftButtonOnClickLinster(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish_Activity(SettingActivity.this);
            }
        });
    }

    @Override
    protected void initView() {

    }
    @OnClick({R.id.ll_exit,R.id.ll_update_password,R.id.ll_binding,R.id.ll_about_us})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.ll_exit:
                SharedPreferencesUtil.removeAll(SettingActivity.this);
                startActivity(WelcomeActivity.class);
                finish_Activity(SettingActivity.this);
                break;
            case R.id.ll_update_password:
                startActivity(UpdatePasswordActivity.class);
                break;

        }
    }
}
