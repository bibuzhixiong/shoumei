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
import com.wanhan.shouyu.ui.login.contract.ForgetPasswordContract;
import com.wanhan.shouyu.ui.login.presenter.ForgetPasswordPresenter;
import com.wanhan.shouyu.utils.CheckUtils;
import com.wanhan.shouyu.utils.CountDownTimerUtil;
import com.wanhan.shouyu.utils.MapUtil;
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

public class ForgetPasswordActivity extends BaseActivity<ForgetPasswordPresenter> implements ForgetPasswordContract.View {
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.et_phone)
    ClearEditText etPhone;
    @Bind(R.id.tv_getcode)
    TextView tvGetcode;
    @Bind(R.id.et_code)
    ClearEditText etCode;
    @Bind(R.id.et_password)
    ClearEditText etPassword;
    @Bind(R.id.et_password1)
    ClearEditText etPassword1;
    @Bind(R.id.bt_register)
    Button btRegister;
    private String verPhone="";
    private String verCode="";
    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected void initToolBar() {
    }
    @Override
    protected void initView() {

    }
    @OnClick({R.id.tv_getcode,R.id.bt_register,R.id.img_back})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.img_back:
                finish_Activity(ForgetPasswordActivity.this);
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
                map.put("sendType","0");
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
                mPresenter.forgetPassword(MapUtil.getMap(map1));
                break;
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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
    public void forgetPassword(CodeBean info) {
        if(info.getCode().equals("0")){
            ToastUtil.showShortToast("修改成功");
            finish_Activity(ForgetPasswordActivity.this);
        }else if(info.getCode().equals("-1")){
            ToastUtil.showShortToast("修改失败");
        }else if(info.getCode().equals("-4")){
            ToastUtil.showShortToast("手机号跟发送验证码手机号不匹配");
        }
        else if(info.getCode().equals("-3")){
            ToastUtil.showShortToast("验证码错误");
        }else if(info.getCode().equals(" -2")){
            ToastUtil.showShortToast("用户手机不存在");
        }

    }

    @Override
    public void loadFail(String msg) {

    }
}
