package com.wanhan.shouyu.ui.login.presenter;

import android.util.Log;

import com.wanhan.shouyu.api.Api;
import com.wanhan.shouyu.base.rx.RxSubscriber;
import com.wanhan.shouyu.bean.json.CodeBean;
import com.wanhan.shouyu.bean.json.TermsBean;
import com.wanhan.shouyu.ui.login.contract.TermsContract;

import java.util.Map;

/**
 * Created by Administrator on 2018/1/29.
 */

public class TermsPresenter extends TermsContract.Presenter {
    @Override
    public void getTerms() {
        addSubscrebe(Api.getInstance().getTerms(), new RxSubscriber<TermsBean>(mContext,true) {
            @Override
            protected void onSuccess(TermsBean requestStatusBean) {
                Log.e("ttt",requestStatusBean.getContent().toString());
                    mView.getTerms(requestStatusBean);


            }
            @Override
            protected void onFailure(String message) {
                mView.loadFail(message);
            }
        });
    }
}
