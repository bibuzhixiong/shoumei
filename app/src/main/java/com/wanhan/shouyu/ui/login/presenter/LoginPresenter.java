package com.wanhan.shouyu.ui.login.presenter;

import android.util.Log;

import com.wanhan.shouyu.api.Api;
import com.wanhan.shouyu.base.rx.RxSubscriber;
import com.wanhan.shouyu.bean.json.UserBean;
import com.wanhan.shouyu.ui.login.contract.LoginContract;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/26.
 */

public class LoginPresenter extends LoginContract.Presenter{
    @Override
    public void login(Map<String, String> map) {
        addSubscrebe(Api.getInstance().login(map), new RxSubscriber<UserBean>(mContext,true) {
            @Override
            protected void onSuccess(UserBean requestStatusBean) {
                Log.e("ttt",requestStatusBean.toString());

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
