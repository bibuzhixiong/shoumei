package com.wanhan.shouyu.ui.me.presenter;

import com.wanhan.shouyu.api.Api;
import com.wanhan.shouyu.base.rx.RxSubscriber;
import com.wanhan.shouyu.bean.json.CodeBean;
import com.wanhan.shouyu.ui.me.contract.UpdatePasswordContract;

import java.util.Map;

/**
 * Created by Administrator on 2018/2/2.
 */

public class UpdatePasswordPresenter extends UpdatePasswordContract.Presenter {
    @Override
    public void updatePassword(Map<String, String> map) {
        addSubscrebe(Api.getInstance().updatePassword(map), new RxSubscriber<CodeBean>(mContext,true) {
            @Override
            protected void onSuccess(CodeBean requestStatusBean) {

                if(requestStatusBean.getCode().equals("0")){
                    mView.updatePasswordSuccess(requestStatusBean);
                }else if(requestStatusBean.getCode().equals("-1")){
                    mView.loadFail("旧密码错误");
                }else {
                    mView.loadFail("修改失败");
                }

            }
            @Override
            protected void onFailure(String message) {
                mView.loadFail(message);
            }
        });
    }
}
