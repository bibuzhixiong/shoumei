package com.wanhan.shouyu.ui.tool.contract;

import com.wanhan.shouyu.base.BasePresenter;
import com.wanhan.shouyu.base.BaseView;
import com.wanhan.shouyu.bean.json.CodeBean;
import com.wanhan.shouyu.ui.me.contract.UpdatePasswordContract;

import java.util.Map;

/**
 * Created by Administrator on 2018/2/8.
 */

public interface ToolContract {
    interface View extends BaseView {
        void addHistoryRecordSuccess(CodeBean info);

        void loadFail(String msg);


    }

    abstract class Presenter extends BasePresenter<ToolContract.View> {

        public abstract void addHistoryRecord(Map<String, String> map);


    }
}
