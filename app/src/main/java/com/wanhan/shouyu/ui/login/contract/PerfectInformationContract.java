package com.wanhan.shouyu.ui.login.contract;

import com.wanhan.shouyu.base.BasePresenter;
import com.wanhan.shouyu.base.BaseView;
import com.wanhan.shouyu.bean.json.CodeBean;
import com.wanhan.shouyu.bean.json.UserBean;

import java.util.Map;

/**
 * Created by Administrator on 2018/1/30.
 */

public interface PerfectInformationContract {
    interface View extends BaseView {
        void updateUserSuccess(CodeBean info);

        void loadFail(String msg);
        void showLoading();
        void hideLoading();

    }

    abstract class Presenter extends BasePresenter<PerfectInformationContract.View> {

        public abstract void updateUser(Map<String, String> map);
        //        public abstract void register(Map<String, String> map);
        //        public abstract void findPwd(Map<String, String> map);
//        public abstract void getCode(Map<String, String> map);
//        public abstract void loadWeChatData(String url);
//        public abstract void weChatLogin(Map<String,String> map);
//        public abstract void bindWeChat(Map<String,String> map);
//        public abstract void loadWeChatInfo(String url);


    }
}
