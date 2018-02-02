package com.wanhan.shouyu.ui.me.contract;

import com.wanhan.shouyu.base.BasePresenter;
import com.wanhan.shouyu.base.BaseView;
import com.wanhan.shouyu.bean.json.CodeBean;

import java.util.Map;

/**
 * Created by Administrator on 2018/2/2.
 */

public interface UpdatePasswordContract {
    interface View extends BaseView {
        void updatePasswordSuccess(CodeBean info);

        void loadFail(String msg);


    }

    abstract class Presenter extends BasePresenter<UpdatePasswordContract.View> {

        public abstract void updatePassword(Map<String, String> map);


    }
}
