package com.wanhan.shouyu.ui.login.contract;

import com.wanhan.shouyu.base.BasePresenter;
import com.wanhan.shouyu.base.BaseView;
import com.wanhan.shouyu.bean.json.TermsBean;
import com.wanhan.shouyu.bean.json.UserBean;

import java.util.Map;

/**
 * Created by Administrator on 2018/1/29.
 */

public interface TermsContract {
    interface View extends BaseView {
        void getTerms(TermsBean info);
        void loadFail(String msg);

        void showLoading();
        void hideLoading();

    }

    abstract class Presenter extends BasePresenter<TermsContract.View> {

        public abstract void getTerms();



    }
}
