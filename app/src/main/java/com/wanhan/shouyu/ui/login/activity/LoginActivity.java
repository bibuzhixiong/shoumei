package com.wanhan.shouyu.ui.login.activity;

import android.content.Intent;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wanhan.shouyu.R;
import com.wanhan.shouyu.base.BaseActivity;
import com.wanhan.shouyu.bean.json.UserBean;
import com.wanhan.shouyu.ui.MainActivity;
import com.wanhan.shouyu.ui.login.contract.LoginContract;
import com.wanhan.shouyu.ui.login.presenter.LoginPresenter;
import com.wanhan.shouyu.utils.CheckUtils;
import com.wanhan.shouyu.utils.MapUtil;
import com.wanhan.shouyu.utils.SharedPreferencesUtil;
import com.wanhan.shouyu.utils.ToastUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/26.
 */

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View{
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.account_login_et)
    EditText accountLoginEt;
    @Bind(R.id.pwd_login_et)
    EditText pwdLoginEt;
    @Bind(R.id.login_btn)
    Button loignBtn;
    @Bind(R.id.isShow_pwd_iv)
    ImageView mIvIsShowPwd;
    @Bind(R.id.findpwd_tv)
    TextView findpwdTv;
    private boolean isHidden = true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initToolBar() {

    }

    @Override
    protected void initView() {
        accountLoginEt.setText(   SharedPreferencesUtil.getValue(LoginActivity.this,"PHONE","")+"");

    }
    @OnClick({R.id.img_back,R.id.login_btn,R.id.isShow_pwd_iv,R.id.findpwd_tv})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {

            case R.id.findpwd_tv:
                ToastUtil.showShortToast("忘记密码");
                break;
            case R.id.img_back:
                    startActivity(WelcomeActivity.class);
                    finish_Activity(LoginActivity.this);
                break;
            case R.id.login_btn:
                String phone=accountLoginEt.getText().toString().trim();
                String password=pwdLoginEt.getText().toString().trim();
                if(phone==null||phone.equals("")){
                    ToastUtil.showShortToast("请输入账号");
                    return;
                }
                if(CheckUtils.isMobileNO(phone)==false){
                    ToastUtil.showShortToast("请输入正确的手机号");
                    return;
                }
                if(password==null||phone.equals("")){
                    ToastUtil.showShortToast("请输入密码");
                    return;
                }

                Map map=new HashMap();
                map.put("phone",phone);
                map.put("passWord",password);
                mPresenter.login(MapUtil.getMap(map));
                break;
            case R.id.isShow_pwd_iv:
                if (isHidden) {
                    mIvIsShowPwd.setImageResource(R.drawable.icon_view);
                    pwdLoginEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    mIvIsShowPwd.setImageResource(R.drawable.icon_close);
                    pwdLoginEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                pwdLoginEt.setSelection(pwdLoginEt.getText().length());
                isHidden = !isHidden;
                break;
        }
    }

    @Override
    public void loginSuccess(UserBean info) {
        SharedPreferencesUtil.putValue(LoginActivity.this,"HEADPATH",info.getIcon());
        SharedPreferencesUtil.putValue(LoginActivity.this,"USERID",info.getUserId());
        SharedPreferencesUtil.putValue(LoginActivity.this,"BIRTHDAY",info.getBirthday());
        SharedPreferencesUtil.putValue(LoginActivity.this,"SEX",info.getSex());
        SharedPreferencesUtil.putValue(LoginActivity.this,"HEIGHT",info.getHeight());
        SharedPreferencesUtil.putValue(LoginActivity.this,"NICKNAME",info.getNiceName());
        SharedPreferencesUtil.putValue(LoginActivity.this,"PHONE",accountLoginEt.getText().toString().trim());
        ToastUtil.showShortToast("登录成功");
        startActivity(MainActivity.class);
        finish_Activity(this);

    }

    @Override
    public void loadFail(String msg) {
        ToastUtil.showShortToast(msg);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
           startActivity(WelcomeActivity.class);
           finish_Activity(LoginActivity.this);
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }

    }
}
