package com.wanhan.shouyu.ui.login.presenter;

import android.util.Log;

import com.wanhan.shouyu.api.Api;
import com.wanhan.shouyu.base.rx.RxSubscriber;
import com.wanhan.shouyu.bean.json.CodeBean;
import com.wanhan.shouyu.bean.json.UserBean;
import com.wanhan.shouyu.ui.login.contract.PerfectInformationContract;

import java.util.Map;

/**
 * Created by Administrator on 2018/1/30.
 */

public class PerfectInformationPresenter extends PerfectInformationContract.Presenter{

    @Override
    public void updateUser(Map<String, String> map) {
        addSubscrebe(Api.getInstance().updateUser(map), new RxSubscriber<CodeBean>(mContext, true) {
            @Override
            protected void onSuccess(CodeBean requestStatusBean) {
//                Log.e("ttt",requestStatusBean.toString());

                if (requestStatusBean.getCode().equals("0")) {
                    mView.updateUserSuccess(requestStatusBean);
                } else {
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
