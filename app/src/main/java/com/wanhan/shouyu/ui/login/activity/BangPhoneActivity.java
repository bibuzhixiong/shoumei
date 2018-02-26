package com.wanhan.shouyu.ui.login.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wanhan.shouyu.R;
import com.wanhan.shouyu.base.BaseActivity;
import com.wanhan.shouyu.bean.json.CodeBean;
import com.wanhan.shouyu.bean.json.IdentifyCodeBean;
import com.wanhan.shouyu.bean.json.UserBean;
import com.wanhan.shouyu.ui.MainActivity;
import com.wanhan.shouyu.ui.login.contract.BangPhoneContract;
import com.wanhan.shouyu.ui.login.presenter.BangPhonePresenter;
import com.wanhan.shouyu.utils.CheckUtils;
import com.wanhan.shouyu.utils.CountDownTimerUtil;
import com.wanhan.shouyu.utils.MapUtil;
import com.wanhan.shouyu.utils.SharedPreferencesUtil;
import com.wanhan.shouyu.utils.ToastUtil;
import com.wanhan.shouyu.widget.ClearEditText;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/2/24.
 */

public class BangPhoneActivity extends BaseActivity<BangPhonePresenter> implements BangPhoneContract.View {
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.et_phone)
    ClearEditText etPhone;
    @Bind(R.id.tv_getcode)
    TextView tvGetcode;
    @Bind(R.id.et_code)
    ClearEditText etCode;
    @Bind(R.id.bt_register)
    Button btRegister;

    private String verPhone="";
    private String verCode="";
    private String openid="";
    @Override
    protected int getLayoutId() {
        return R.layout.activity_bang_phone;
    }

    @Override
    protected void initToolBar() {

    }

    @Override
    protected void initView() {
        Bundle bundle=getIntent().getExtras();
        openid=bundle.getString("openid");
    }


    @Override
    public void loginSuccess(UserBean info) {
        if(info.getCode().equals("0")){
            if(info.getBirthday().equals("null")||info.getBirthday()==null){
                startActivity(PerfectInformationActivity.class);
                return;
            }
            SharedPreferencesUtil.putValue(BangPhoneActivity.this,"HEADPATH",info.getIcon());
            SharedPreferencesUtil.putValue(BangPhoneActivity.this,"USERID",info.getUserId());
            SharedPreferencesUtil.putValue(BangPhoneActivity.this,"BIRTHDAY",info.getBirthday());
            SharedPreferencesUtil.putValue(BangPhoneActivity.this,"SEX",info.getSex());
            SharedPreferencesUtil.putValue(BangPhoneActivity.this,"HEIGHT",info.getHeight());
            SharedPreferencesUtil.putValue(BangPhoneActivity.this,"NICKNAME",info.getNiceName());
            SharedPreferencesUtil.putValue(BangPhoneActivity.this,"OPENID",info.getOpenId());
                SharedPreferencesUtil.putValue(BangPhoneActivity.this,"PHONE",info.getPhone());
            ToastUtil.showShortToast("登录成功");
            startActivity(MainActivity.class);
            finish_Activity(this);
        }else if(info.getCode().equals("-1")){
            ToastUtil.showShortToast("授权失败");
        }
    }

    @Override
    public void bangPhoneSuccess(CodeBean bean) {
        if(bean.getCode().equals("0")){
            Map map=new HashMap();
            map.put("openId",openid);
            mPresenter.login(MapUtil.getMap(map));

        }else if(bean.getCode().equals("1")){
            ToastUtil.showShortToast("绑定失败");
        }
    }
    @Override
    public void getCodeSuccess(IdentifyCodeBean info) {
        verCode=info.getPhoneVerification();
        verPhone=info.getValidationPhone_();
        CountDownTimerUtil mCountDownTimerUtils = new CountDownTimerUtil(tvGetcode, 60000, 1000);
        mCountDownTimerUtils.start();
        ToastUtil.showShortToast("发送成功");
    }

    @OnClick({R.id.tv_getcode,R.id.bt_register,R.id.img_back})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){

            case R.id.tv_getcode:

                String phone=etPhone.getText().toString().trim();
                if(phone.equals("")){
                    ToastUtil.showShortToast("请输入电话号码");
                    return;
                }
                if(CheckUtils.isMobileNO(phone)==false){
                    ToastUtil.showShortToast("请输入正确的电话号码");
                    return;
                }
                Map map=new HashMap();
                map.put("phone",phone);
                map.put("sendType","2");
                mPresenter.getCode(MapUtil.getMap(map));
                break;
            case R.id.bt_register:
                String phone1=etPhone.getText().toString().trim();
                if (phone1 == null || phone1.trim().equals("")) {
                    ToastUtil.showCenterShortToast("请输入你的手机号码");
                    return;
                }
                if (!CheckUtils.isMobileNO(phone1)) {
                    ToastUtil.showCenterShortToast("请输入正确的手机号码");
                    return;
                }
                String code=etCode.getText().toString().trim();
                if (code == null || code.trim().equals("")) {
                    ToastUtil.showCenterShortToast("请输入验证码");
                    return;
                }

                if(!verPhone.equals(etPhone.getText().toString().trim())){
                    ToastUtil.showCenterShortToast("验证码不正确");
                    return;
                }
                if(!verCode.equals(code)){
                    ToastUtil.showCenterShortToast("验证码不正确");
                    return;
                }
                Map<String,String> map1=new HashMap<>();
                map1.put("phone",phone1);
                map1.put("openId",openid);
                mPresenter.bangPhone(MapUtil.getMap(map1));
//                mPresenter.register(MapUtil.getMap(map1));
                break;
            case R.id.img_back:

                finish_Activity(BangPhoneActivity.this);
                break;
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
}
