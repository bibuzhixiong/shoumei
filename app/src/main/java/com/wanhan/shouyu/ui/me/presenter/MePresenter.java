package com.wanhan.shouyu.ui.me.presenter;

import android.util.Log;

import com.wanhan.shouyu.api.Api;
import com.wanhan.shouyu.base.rx.RxSubscriber;
import com.wanhan.shouyu.ui.me.contract.MeContract;

import java.util.Map;

import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018/2/5.
 */

public class MePresenter extends MeContract.Presenter {
    @Override
    public void recommentInformation(Map<String, String> map) {
        addSubscrebe(Api.getInstance().recommendInformation(map),new RxSubscriber<ResponseBody>(mContext,true){
            @Override
            protected void onSuccess(ResponseBody info) {
                try {
                    Log.e("EEE",info.string());
                    mView.uploadHeadSuccess(info.string());

                }catch (Exception e){

                }

            }
            @Override
            protected void onFailure(String message) {
                mView.loadFail(message);
            }
        });
    }
}
