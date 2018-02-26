package com.wanhan.shouyu.ui.login.presenter;

import android.util.Log;

import com.wanhan.shouyu.api.Api;
import com.wanhan.shouyu.base.rx.RxSubscriber;
import com.wanhan.shouyu.bean.json.CodeBean;
import com.wanhan.shouyu.bean.json.IdentifyCodeBean;
import com.wanhan.shouyu.bean.json.UserBean;
import com.wanhan.shouyu.ui.login.contract.BangPhoneContract;

import java.util.Map;

/**
 * Created by Administrator on 2018/2/24.
 */

public class BangPhonePresenter extends BangPhoneContract.Presenter {



    @Override
    public void getCode(Map<String, String> map) {
        addSubscrebe(Api.getInstance().getCode(map), new RxSubscriber<IdentifyCodeBean>(mContext,true) {
            @Override
            protected void onSuccess(IdentifyCodeBean requestStatusBean) {
                Log.e("ttt",requestStatusBean.toString());

                if(requestStatusBean.getCode().equals("0")){
                    mView.getCodeSuccess(requestStatusBean);
                }else if(requestStatusBean.getCode().equals("-1")){
                    mView.loadFail("该手机号已经存在");
                }else if(requestStatusBean.getCode().equals("-2")){
                    mView.loadFail("该手机号已经绑定微信");
                }
                else{
                    mView.loadFail("验证码发送已经超过5次");
                }

            }
            @Override
            protected void onFailure(String message) {
                mView.loadFail(message);
            }
        });
    }

    @Override
    public void bangPhone(Map<String, String> map) {
        addSubscrebe(Api.getInstance().bangPhone(map), new RxSubscriber<CodeBean>(mContext,true) {
            @Override
            protected void onSuccess(CodeBean codeBean) {
             mView.bangPhoneSuccess(codeBean);

            }
            @Override
            protected void onFailure(String message) {
                mView.loadFail(message);
            }
        });
    }
    @Override
    public void login(Map<String, String> map) {
        addSubscrebe(Api.getInstance().login(map), new RxSubscriber<UserBean>(mContext,true) {
            @Override
            protected void onSuccess(UserBean requestStatusBean) {

                if(requestStatusBean.getCode().equals("0")){
                    mView.loginSuccess(requestStatusBean);
                }else if(requestStatusBean.getCode().equals("2")){
                    mView.loadFail("用户不存在");
                }
                else{
                    mView.loadFail(requestStatusBean.getMsg());
                }

            }
            @Override
            protected void onFailure(String message) {
                mView.loadFail(message);
            }
        });
    }
}
