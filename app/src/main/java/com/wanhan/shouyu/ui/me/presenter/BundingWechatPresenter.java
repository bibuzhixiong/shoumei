package com.wanhan.shouyu.ui.me.presenter;

import com.wanhan.shouyu.api.Api;
import com.wanhan.shouyu.base.rx.RxSubscriber;
import com.wanhan.shouyu.bean.json.CodeBean;
import com.wanhan.shouyu.bean.json.UserBean;
import com.wanhan.shouyu.ui.me.contract.BundingWechatContract;

import java.util.Map;

/**
 * Created by Administrator on 2018/2/26.
 */

public class BundingWechatPresenter extends BundingWechatContract.Presenter {

    @Override
    public void wechatLogin(Map<String, String> map) {
        addSubscrebe(Api.getInstance().bangWeixin(map), new RxSubscriber<UserBean>(mContext,true) {
            @Override
            protected void onSuccess(UserBean requestStatusBean) {

                mView.wechatLoginSuccess(requestStatusBean);

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
    public void unbundingPhone(Map<String, String> map) {
        addSubscrebe(Api.getInstance().unbundingWechat(map), new RxSubscriber<CodeBean>(mContext,true) {
            @Override
            protected void onSuccess(CodeBean codeBean) {
                mView.unbundingPhoneSuccess(codeBean);

            }
            @Override
            protected void onFailure(String message) {
                mView.loadFail(message);
            }
        });
    }
}
