package com.wanhan.shouyu.ui.login.contract;

import com.wanhan.shouyu.base.BasePresenter;
import com.wanhan.shouyu.base.BaseView;
import com.wanhan.shouyu.bean.json.CodeBean;
import com.wanhan.shouyu.bean.json.IdentifyCodeBean;
import com.wanhan.shouyu.bean.json.RegisterBean;
import com.wanhan.shouyu.bean.json.UserBean;

import java.util.Map;

/**
 * Created by Administrator on 2018/1/29.
 */

public interface RegisterContract {
    interface View extends BaseView {
//        void loginSuccess(UserBean info);

        //        void weChatLoginSuccess(RequestStatusBean bean);
//
//
        void registerSuccess(RegisterBean info);
        //
//        void findPwdSuccess(String info);
//
        void getCodeSuccess(IdentifyCodeBean info);
        //
//        void loadWeChatData(WechatBean wechatBean);
//
        void loadFail(String msg);
//        void getCodeFail(String msg);

//        void weChatLogin(String info);
//
//        void bindWeChatSuccess(String info);
//        void loadWeChatInfo(WechatInfoBean bean);

        void showLoading();
        void hideLoading();

    }

    abstract class Presenter extends BasePresenter<RegisterContract.View> {

//        public abstract void login(Map<String, String> map);
                public abstract void register(Map<String, String> map);
        //        public abstract void findPwd(Map<String, String> map);
        public abstract void getCode(Map<String, String> map);
//        public abstract void loadWeChatData(String url);
//        public abstract void weChatLogin(Map<String,String> map);
//        public abstract void bindWeChat(Map<String,String> map);
//        public abstract void loadWeChatInfo(String url);


    }
}
