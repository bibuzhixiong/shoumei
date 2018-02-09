package com.wanhan.shouyu.ui.tool.presenter;

import com.wanhan.shouyu.api.Api;
import com.wanhan.shouyu.base.rx.RxSubscriber;
import com.wanhan.shouyu.bean.json.CodeBean;
import com.wanhan.shouyu.bean.json.HistoryRecordBean;
import com.wanhan.shouyu.ui.tool.contract.HistoryContract;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/2/9.
 */

public class HistoryPresenter extends HistoryContract.Presenter {
    @Override
    public void findHistoryRecord(Map<String, String> map) {
        addSubscrebe(Api.getInstance().findHistoryRecord(map),new RxSubscriber<List<HistoryRecordBean>>(mContext,false){
            @Override
            protected void onSuccess(List<HistoryRecordBean> info) {
              mView.findHistoryRecordSuccess(info);
            }
            @Override
            protected void onFailure(String message) {
                mView.loadFail(message);
            }
        });
    }
}
