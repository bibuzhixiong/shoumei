package com.wanhan.shouyu.ui.me.contract;

import com.wanhan.shouyu.base.BasePresenter;
import com.wanhan.shouyu.base.BaseView;
import com.wanhan.shouyu.bean.json.CodeBean;
import com.wanhan.shouyu.bean.json.RecommendInformationBean;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;

/**
 * Created by Administrator on 2018/2/5.
 */

public interface MeContract {
    interface View extends BaseView {
        void recommentInformationdSuccess(List<RecommendInformationBean> info);

        void loadFail(String msg);

    }

    abstract class Presenter extends BasePresenter<MeContract.View> {

        public abstract void recommentInformation(Map<String, String> map);

    }
}
