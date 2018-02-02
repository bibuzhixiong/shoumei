package com.wanhan.shouyu.ui.me.presenter;

import android.util.Log;

import com.wanhan.shouyu.api.Api;
import com.wanhan.shouyu.base.rx.RxSubscriber;
import com.wanhan.shouyu.bean.json.CodeBean;
import com.wanhan.shouyu.bean.json.IdentifyCodeBean;
import com.wanhan.shouyu.ui.me.contract.SuggestionsContract;

import java.util.Map;

/**
 * Created by Administrator on 2018/2/2.
 */

public class SuggestionsPresenter extends SuggestionsContract.Presenter{
    @Override
    public void suggestive(Map<String, String> map) {
        addSubscrebe(Api.getInstance().suggestive(map), new RxSubscriber<CodeBean>(mContext,true) {
            @Override
            protected void onSuccess(CodeBean requestStatusBean) {

                if(requestStatusBean.getCode().equals("0")){
                    mView.suggestiveSuccess(requestStatusBean);
                }else{
                    mView.loadFail("发送失败");
                }

            }
            @Override
            protected void onFailure(String message) {
                mView.loadFail(message);
            }
        });
    }
}
