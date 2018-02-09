package com.wanhan.shouyu.ui.me.presenter;

import android.util.Log;

import com.wanhan.shouyu.api.Api;
import com.wanhan.shouyu.base.rx.RxSubscriber;
import com.wanhan.shouyu.bean.json.RecommendInformationBean;
import com.wanhan.shouyu.ui.me.contract.MeContract;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018/2/5.
 */

public class MePresenter extends MeContract.Presenter {
    @Override
    public void recommentInformation(Map<String, String> map) {
        addSubscrebe(Api.getInstance().recommendInformation(map),new RxSubscriber<List<RecommendInformationBean>>(mContext,false){
            @Override
            protected void onSuccess(List<RecommendInformationBean> info) {

                    Log.e("EEE",info.toString());
                    mView.recommentInformationdSuccess(info);



            }
            @Override
            protected void onFailure(String message) {
                mView.loadFail(message);
            }
        });
    }
}
