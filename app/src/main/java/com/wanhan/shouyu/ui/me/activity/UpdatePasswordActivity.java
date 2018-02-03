package com.wanhan.shouyu.ui.me.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wanhan.shouyu.R;
import com.wanhan.shouyu.base.BaseActivity;
import com.wanhan.shouyu.base.BaseToolbar;
import com.wanhan.shouyu.bean.json.CodeBean;
import com.wanhan.shouyu.dialog.DialogInterface;
import com.wanhan.shouyu.dialog.NormalAlertDialog;
import com.wanhan.shouyu.ui.me.contract.UpdatePasswordContract;
import com.wanhan.shouyu.ui.me.presenter.UpdatePasswordPresenter;
import com.wanhan.shouyu.utils.MapUtil;
import com.wanhan.shouyu.utils.SharedPreferencesUtil;
import com.wanhan.shouyu.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/2/2.
 */

public class UpdatePasswordActivity extends BaseActivity<UpdatePasswordPresenter> implements UpdatePasswordContract.View {
    @Bind(R.id.toolbar)
    BaseToolbar toolbar;
    @Bind(R.id.et_old_password)
    EditText etOldPassword;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.et_password1)
    EditText etPassword1;
    @Bind(R.id.bt_sure)
    Button btSure;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_password;
    }

    @Override
    protected void initToolBar() {
        toolbar.setLeftButtonOnClickLinster(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish_Activity(UpdatePasswordActivity.this);
            }
        });
    }

    @Override
    protected void initView() {

    }
    @OnClick({R.id.bt_sure})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.bt_sure:
                String oldPassword=etOldPassword.getText().toString().trim();
                if(oldPassword.equals("")){
                    ToastUtil.showShortToast("请输入旧密码");
                    return;
                }
                String password=etPassword.getText().toString().trim();
                String password1=etPassword1.getText().toString().trim();
                if(password.equals("")){
                    ToastUtil.showShortToast("请输入新密码");
                    return;
                }
                if(password1.equals("")){
                    ToastUtil.showShortToast("请确认密码");
                    return;
                }
                if(password.length()<6){
                    ToastUtil.showShortToast("密码至少6位数");
                    return;
                }
                if(!password.equals(password1)){
                    ToastUtil.showShortToast("密码不一致");
                    return;
                }
                Map map=new HashMap<>();
                map.put("oldPassWord",oldPassword);
                map.put("newPassWord",password);
                map.put("userId", SharedPreferencesUtil.getValue(UpdatePasswordActivity.this,"USERID",""));
                mPresenter.updatePassword(MapUtil.getMap(map));
                break;
        }
    }

    @Override
    public void updatePasswordSuccess(CodeBean info) {
        new NormalAlertDialog.Builder(this)
                .setBoolTitle(false)
                .setContentText("密码修改成功！")
                .setContentTextColor(R.color.blue)
                .setSingleModel(true)
                .setSingleText("确认")
                .setWidth(0.75f)
                .setHeight(0.33f)
                .setSingleListener(new DialogInterface.OnSingleClickListener<NormalAlertDialog>() {
                    @Override
                    public void clickSingleButton(NormalAlertDialog dialog, View view) {
                        finish_Activity(UpdatePasswordActivity.this);
                    }
                }).build().show();
    }

    @Override
    public void loadFail(String msg) {
        ToastUtil.showShortToast(msg);

    }
}
