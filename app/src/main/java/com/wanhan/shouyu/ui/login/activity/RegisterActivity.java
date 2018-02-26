package com.wanhan.shouyu.ui.login.activity;

import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wanhan.shouyu.R;
import com.wanhan.shouyu.base.BaseActivity;
import com.wanhan.shouyu.bean.json.CodeBean;
import com.wanhan.shouyu.bean.json.IdentifyCodeBean;
import com.wanhan.shouyu.bean.json.RegisterBean;
import com.wanhan.shouyu.ui.login.contract.RegisterContract;
import com.wanhan.shouyu.ui.login.presenter.RegisterPresenter;
import com.wanhan.shouyu.utils.CheckUtils;
import com.wanhan.shouyu.utils.CountDownTimerUtil;
import com.wanhan.shouyu.utils.MapUtil;
import com.wanhan.shouyu.utils.SharedPreferencesUtil;
import com.wanhan.shouyu.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/27.
 */

public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View{
    @Bind(R.id.bt_register)
    Button btRegister;
    @Bind(R.id.tv_getcode)
    TextView tvGetcode;
    @Bind(R.id.et_code)
    EditText etCode;
    @Bind(R.id.et_phone)
    TextView etPhone;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.et_password1)
    EditText etPassword1;
    @Bind(R.id.tv_shouyu_terms)
    TextView tv_shouyu_terms;
    @Bind(R.id.cb_terms)
    CheckBox cb_terms;
    @Bind(R.id.img_back)
    ImageView img_back;

    private String verPhone="";
    private String verCode="";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initToolBar() {

    }

    @Override
    protected void initView() {

    }
    @OnClick({R.id.tv_getcode,R.id.bt_register,R.id.tv_shouyu_terms,R.id.img_back})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_shouyu_terms:
                startActivity(TermsActivity.class);
                break;
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
                map.put("sendType","1");
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
                String pwd=etPassword.getText().toString().trim();
                if (pwd == null || pwd.trim().equals("")) {
                    ToastUtil.showCenterShortToast("请输入您的密码");
                    return;
                }
                if(pwd.length()<6||pwd.length()>20){
                    ToastUtil.showCenterShortToast("请输入6~20位密码");
                    return;
                }
                String pwd1=etPassword1.getText().toString().trim();
                if (pwd == null || pwd.trim().equals("")) {
                    ToastUtil.showCenterShortToast("请确认你的密码");
                    return;
                }
                if(!pwd.equals(pwd1)){
                    ToastUtil.showCenterShortToast("两次密码不一致");
                    return;
                }
                if(!cb_terms.isChecked()){
                    ToastUtil.showCenterShortToast("请阅读并同意瘦鱼服务条款");
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
                map1.put("passWord",pwd);
                map1.put("phoneVerification",code);
                map1.put("notPhoneCode",code);
                mPresenter.register(MapUtil.getMap(map1));
                break;
            case R.id.img_back:
                startActivity(WelcomeActivity.class);
                finish_Activity(RegisterActivity.this);
                break;
        }
    }

    @Override
    public void registerSuccess(RegisterBean info) {
        SharedPreferencesUtil.putValue(RegisterActivity.this,"USERID",info.getUserId());
        ToastUtil.showShortToast("注册成功");
        startActivity(PerfectInformationActivity.class);
        finish_Activity(RegisterActivity.this);
    }

    @Override
    public void getCodeSuccess(IdentifyCodeBean info) {
            verCode=info.getPhoneVerification();
        verPhone=info.getValidationPhone_();
        CountDownTimerUtil mCountDownTimerUtils = new CountDownTimerUtil(tvGetcode, 60000, 1000);
        mCountDownTimerUtils.start();
        ToastUtil.showShortToast("发送成功");
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
   finish_Activity(RegisterActivity.this);
   return false;
  }else {
   return super.onKeyDown(keyCode, event);
  }

 }


}
