package com.wanhan.shouyu.ui.login.contract;

import com.wanhan.shouyu.base.BasePresenter;
import com.wanhan.shouyu.base.BaseView;
import com.wanhan.shouyu.bean.json.CodeBean;
import com.wanhan.shouyu.bean.json.IdentifyCodeBean;

import java.util.Map;

/**
 * Created by Administrator on 2018/2/24.
 */

public interface ForgetPasswordContract {
    interface View extends BaseView{
        void showLoading();
        void hideLoading();

        void getCodeSuccess(IdentifyCodeBean info);
//
        void forgetPassword(CodeBean info);
//
        void loadFail(String msg);
    }
    abstract class Presenter extends BasePresenter<ForgetPasswordContract.View> {

        public abstract void forgetPassword(Map<String, String> map);
        //        public abstract void register(Map<String, String> map);
        //        public abstract void findPwd(Map<String, String> map);
        public abstract void getCode(Map<String, String> map);
        //        public abstract void loadWeChatData(String url);
        public abstract void bangPhone(Map<String,String> map);
//        public abstract void bindWeChat(Map<String,String> map);
//        public abstract void loadWeChatInfo(String url);


    }
}
