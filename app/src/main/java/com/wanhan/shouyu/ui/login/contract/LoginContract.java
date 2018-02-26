package com.wanhan.shouyu.ui.login.contract;

import com.wanhan.shouyu.base.BasePresenter;
import com.wanhan.shouyu.base.BaseView;
import com.wanhan.shouyu.bean.json.UserBean;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/26.
 */

public interface LoginContract {
    interface View extends BaseView {
        void loginSuccess(UserBean info);

                void weChatLoginSuccess(UserBean bean);
//
//
//        void registerSuccess(RequestStatusBean info);
        //
//        void findPwdSuccess(String info);
//
//        void getCodeSuccess(RequestStatusBean info);
        //
//        void loadWeChatData(WechatBean wechatBean);
//
        void loadFail(String msg);
//        void weChatLogin(String info);
//
//        void bindWeChatSuccess(String info);
//        void loadWeChatInfo(WechatInfoBean bean);

        void showLoading();
        void hideLoading();

    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void login(Map<String, String> map);
        //        public abstract void register(Map<String, String> map);
        //        public abstract void findPwd(Map<String, String> map);
//        public abstract void getCode(Map<String, String> map);
//        public abstract void loadWeChatData(String url);
        public abstract void weChatLogin(Map<String,String> map);
//        public abstract void bindWeChat(Map<String,String> map);
//        public abstract void loadWeChatInfo(String url);


    }
}
