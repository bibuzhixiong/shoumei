package com.wanhan.shouyu.ui.tool.contract;

import com.wanhan.shouyu.base.BasePresenter;
import com.wanhan.shouyu.base.BaseView;
import com.wanhan.shouyu.bean.json.CodeBean;
import com.wanhan.shouyu.bean.json.HistoryRecordBean;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/2/9.
 */

public interface HistoryContract {
    interface View extends BaseView {
        void findHistoryRecordSuccess(List<HistoryRecordBean> info);
        void deleteHistoryRecordSuccess(CodeBean info);

        void loadFail(String msg);


    }

    abstract class Presenter extends BasePresenter<HistoryContract.View> {

        public abstract void findHistoryRecord(Map<String, String> map);
        public abstract void deleteHistoryRecord(Map<String, String> map);


    }
}
