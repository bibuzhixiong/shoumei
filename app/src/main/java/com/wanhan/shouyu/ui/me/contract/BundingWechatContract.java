package com.wanhan.shouyu.ui.me.contract;

import com.wanhan.shouyu.base.BasePresenter;
import com.wanhan.shouyu.base.BaseView;
import com.wanhan.shouyu.bean.json.CodeBean;
import com.wanhan.shouyu.bean.json.IdentifyCodeBean;
import com.wanhan.shouyu.bean.json.UserBean;
import com.wanhan.shouyu.ui.login.contract.BangPhoneContract;

import java.util.Map;

/**
 * Created by Administrator on 2018/2/26.
 */

public interface BundingWechatContract {
    interface View extends BaseView {

        void wechatLoginSuccess(UserBean bean);
        void bangPhoneSuccess(CodeBean bean);
        void unbundingPhoneSuccess(CodeBean bean);
        void loadFail(String msg);

        void showLoading();
        void hideLoading();

    }

    abstract class Presenter extends BasePresenter<BundingWechatContract.View> {

        public abstract void wechatLogin(Map<String, String> map);
        public abstract void bangPhone(Map<String, String> map);

        public abstract void unbundingPhone(Map<String,String> map);


    }
}
