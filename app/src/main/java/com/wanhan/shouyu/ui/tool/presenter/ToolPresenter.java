package com.wanhan.shouyu.ui.tool.presenter;

import android.util.Log;

import com.wanhan.shouyu.api.Api;
import com.wanhan.shouyu.base.rx.RxSubscriber;
import com.wanhan.shouyu.bean.json.CodeBean;
import com.wanhan.shouyu.bean.json.RecommendInformationBean;
import com.wanhan.shouyu.ui.tool.contract.ToolContract;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/2/8.
 */

public class ToolPresenter extends ToolContract.Presenter{

    @Override
    public void addHistoryRecord(Map<String, String> map) {
        addSubscrebe(Api.getInstance().addHistoryRecord(map),new RxSubscriber<CodeBean>(mContext,false){
            @Override
            protected void onSuccess(CodeBean info) {
                if(info.getCode().equals("0")){
                    mView.addHistoryRecordSuccess(info);
                }


            }
            @Override
            protected void onFailure(String message) {
                mView.loadFail(message);
            }
        });
    }
}
