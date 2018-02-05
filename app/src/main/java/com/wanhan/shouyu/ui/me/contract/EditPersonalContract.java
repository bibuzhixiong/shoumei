package com.wanhan.shouyu.ui.me.contract;

import com.wanhan.shouyu.base.BasePresenter;
import com.wanhan.shouyu.base.BaseView;
import com.wanhan.shouyu.bean.json.CodeBean;

import java.util.Map;

import okhttp3.MultipartBody;

/**
 * Created by Administrator on 2018/2/5.
 */

public interface EditPersonalContract {
    interface View extends BaseView {
        void uploadHeadSuccess(String info);
        void updateUserSuccess(CodeBean info);
        void loadFail(String msg);

    }

    abstract class Presenter extends BasePresenter<EditPersonalContract.View> {

        public abstract void updateUser(Map<String, String> map);
        public abstract void uploadHead(MultipartBody.Part body);


    }
}
