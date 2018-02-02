package com.wanhan.shouyu.ui.me.contract;

import com.wanhan.shouyu.base.BasePresenter;
import com.wanhan.shouyu.base.BaseView;
import com.wanhan.shouyu.bean.json.CodeBean;
import com.wanhan.shouyu.bean.json.UserBean;
import com.wanhan.shouyu.ui.login.contract.LoginContract;

import java.util.Map;

/**
 * Created by Administrator on 2018/2/2.
 */

public interface SuggestionsContract {
    interface View extends BaseView {
        void suggestiveSuccess(CodeBean info);

        void loadFail(String msg);


    }

    abstract class Presenter extends BasePresenter<SuggestionsContract.View> {

        public abstract void suggestive(Map<String, String> map);


    }
}
