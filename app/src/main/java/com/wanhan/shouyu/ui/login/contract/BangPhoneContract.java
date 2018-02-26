package com.wanhan.shouyu.ui.login.contract;

import com.wanhan.shouyu.base.BasePresenter;
import com.wanhan.shouyu.base.BaseView;
import com.wanhan.shouyu.bean.json.CodeBean;
import com.wanhan.shouyu.bean.json.IdentifyCodeBean;
import com.wanhan.shouyu.bean.json.UserBean;

import java.util.Map;

/**
 * Created by Administrator on 2018/2/24.
 */

public interface BangPhoneContract {
    interface View extends BaseView {
        void loginSuccess(UserBean info);
        void bangPhoneSuccess(CodeBean bean);

//        void registerSuccess(RequestStatusBean info);

//        void findPwdSuccess(String info);

        void getCodeSuccess(IdentifyCodeBean info);
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

    abstract class Presenter extends BasePresenter<BangPhoneContract.View> {

        public abstract void login(Map<String, String> map);
        //        public abstract void register(Map<String, String> map);
        //        public abstract void findPwd(Map<String, String> map);
        public abstract void getCode(Map<String, String> map);
//        public abstract void loadWeChatData(String url);
        public abstract void bangPhone(Map<String,String> map);
//        public abstract void bindWeChat(Map<String,String> map);
//        public abstract void loadWeChatInfo(String url);


    }
}
