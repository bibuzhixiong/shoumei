package com.wanhan.shouyu.ui.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wanhan.shouyu.AppConstant;
import com.wanhan.shouyu.R;
import com.wanhan.shouyu.base.BaseActivity;
import com.wanhan.shouyu.base.rx.RxBus;
import com.wanhan.shouyu.bean.json.UserBean;
import com.wanhan.shouyu.event.WeChatEvent;
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
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

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


    private IWXAPI api;
    private Subscription subscription;
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
        api = WXAPIFactory.createWXAPI(LoginActivity.this, AppConstant.WEIXIN_APP_ID, false);
        // 将该app注册到微信
        api.registerApp(AppConstant.WEIXIN_APP_ID);
        event();
    }
    private void event(){
        subscription= RxBus.$().toObservable(WeChatEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WeChatEvent>() {
                    @Override
                    public void call(WeChatEvent event) {
//                        ToastUtil.showShortToast(event.getCode()+"");
                     Log.e("BBB","CODE:   "+event.getCode());
                     Map<String,String> map=new HashMap<>();
                     map.put("wxCode",event.getCode());
                     mPresenter.weChatLogin(MapUtil.getMap(map));
                    }
                });
    }
    @OnClick({R.id.img_back,R.id.login_btn,R.id.isShow_pwd_iv,R.id.findpwd_tv,R.id.tv_weixin})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_weixin:
                if (!api.isWXAppInstalled()) {
                    ToastUtil.showbottomShortToast("您还未安装微信客户端");
                    return;
                }
                final SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = "taosha_wx_login";
                api.sendReq(req);
                break;
            case R.id.findpwd_tv:
                startActivity(ForgetPasswordActivity.class);
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
                if(password==null||password.equals("")){
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
//        Log.e("LoginActivity",info.getBirthday()+"   birthday");
        if(info.getBirthday().equals("null")||info.getBirthday()==null){
            startActivity(PerfectInformationActivity.class);
            return;
        }
        SharedPreferencesUtil.putValue(LoginActivity.this,"HEADPATH",info.getIcon());
        SharedPreferencesUtil.putValue(LoginActivity.this,"USERID",info.getUserId());
        SharedPreferencesUtil.putValue(LoginActivity.this,"BIRTHDAY",info.getBirthday());
        SharedPreferencesUtil.putValue(LoginActivity.this,"SEX",info.getSex());
        SharedPreferencesUtil.putValue(LoginActivity.this,"HEIGHT",info.getHeight());
        SharedPreferencesUtil.putValue(LoginActivity.this,"NICKNAME",info.getNiceName());
        SharedPreferencesUtil.putValue(LoginActivity.this,"OPENID",info.getOpenId());
        SharedPreferencesUtil.putValue(LoginActivity.this,"PHONE",accountLoginEt.getText().toString().trim());
        ToastUtil.showShortToast("登录成功");
        startActivity(MainActivity.class);
        finish_Activity(this);

    }

    @Override
    public void weChatLoginSuccess(UserBean info) {
            if(info.getCode().equals("0")){
                if(info.getBirthday().equals("null")||info.getBirthday()==null){
                    startActivity(PerfectInformationActivity.class);
                    return;
                }
                SharedPreferencesUtil.putValue(LoginActivity.this,"HEADPATH",info.getIcon());
                SharedPreferencesUtil.putValue(LoginActivity.this,"USERID",info.getUserId());
                SharedPreferencesUtil.putValue(LoginActivity.this,"BIRTHDAY",info.getBirthday());
                SharedPreferencesUtil.putValue(LoginActivity.this,"SEX",info.getSex());
                SharedPreferencesUtil.putValue(LoginActivity.this,"HEIGHT",info.getHeight());
                SharedPreferencesUtil.putValue(LoginActivity.this,"NICKNAME",info.getNiceName());
                SharedPreferencesUtil.putValue(LoginActivity.this,"OPENID",info.getOpenId());
                SharedPreferencesUtil.putValue(LoginActivity.this,"PHONE",info.getPhone());
//                SharedPreferencesUtil.putValue(LoginActivity.this,"PHONE",accountLoginEt.getText().toString().trim());
                ToastUtil.showShortToast("登录成功");
                startActivity(MainActivity.class);
                finish_Activity(this);
                return;
            }else if(info.getCode().equals("-1")){
                ToastUtil.showShortToast("授权失败");
                return;
            }else if(info.getCode().equals("-2")){
                Bundle bundle =new Bundle();
                bundle.putString("openid",info.getOpenId());
                startActivity(BangPhoneActivity.class,bundle);
            }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null&&!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
