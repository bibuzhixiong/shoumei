package com.wanhan.shouyu.ui.login.presenter;

import android.util.Log;

import com.wanhan.shouyu.api.Api;
import com.wanhan.shouyu.base.rx.RxSubscriber;
import com.wanhan.shouyu.bean.json.CodeBean;
import com.wanhan.shouyu.bean.json.IdentifyCodeBean;
import com.wanhan.shouyu.bean.json.RegisterBean;
import com.wanhan.shouyu.bean.json.UserBean;
import com.wanhan.shouyu.ui.login.contract.RegisterContract;

import java.util.Map;

/**
 * Created by Administrator on 2018/1/29.
 */

public class RegisterPresenter extends RegisterContract.Presenter{
    @Override
    public void register(Map<String, String> map) {
        addSubscrebe(Api.getInstance().register(map), new RxSubscriber<RegisterBean>(mContext,true) {
            @Override
            protected void onSuccess(RegisterBean requestStatusBean) {
                Log.e("ttt",requestStatusBean.toString());

                if(requestStatusBean.getCode().equals("0")){
                    mView.registerSuccess(requestStatusBean);
                }else if(requestStatusBean.getCode().equals("-3")){
                    mView.loadFail("验证码错误");
                }else if(requestStatusBean.getCode().equals("-4")){
                    mView.loadFail("注册手机号跟发送验证码的手机号不匹配");
                }else if(requestStatusBean.getCode().equals("-1")){
                    mView.loadFail("手机号已经存在");
                }else {
                    mView.loadFail("注册失败");
                }
            }
            @Override
            protected void onFailure(String message) {
                mView.loadFail(message);
            }
        });
    }

    @Override
    public void getCode(Map<String, String> map) {
        addSubscrebe(Api.getInstance().getCode(map), new RxSubscriber<IdentifyCodeBean>(mContext,true) {
            @Override
            protected void onSuccess(IdentifyCodeBean requestStatusBean) {
                Log.e("ttt",requestStatusBean.toString());

                if(requestStatusBean.getCode().equals("0")){
                    mView.getCodeSuccess(requestStatusBean);
                }else{
                    mView.loadFail("验证码发送已经超过5次");
                }

            }
            @Override
            protected void onFailure(String message) {
                mView.loadFail(message);
            }
        });
    }
}
