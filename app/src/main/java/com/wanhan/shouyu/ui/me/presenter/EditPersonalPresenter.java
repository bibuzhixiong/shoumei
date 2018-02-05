package com.wanhan.shouyu.ui.me.presenter;

import android.util.Log;

import com.google.gson.JsonObject;
import com.wanhan.shouyu.api.Api;
import com.wanhan.shouyu.base.rx.RxSubscriber;
import com.wanhan.shouyu.bean.json.CodeBean;
import com.wanhan.shouyu.ui.me.activity.EditPersonalActivity;
import com.wanhan.shouyu.ui.me.contract.EditPersonalContract;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Administrator on 2018/2/5.
 */

public class EditPersonalPresenter extends EditPersonalContract.Presenter{

    @Override
    public void uploadHead(MultipartBody.Part body) {

        addSubscrebe(Api.getInstance().changeUserHead(body),new RxSubscriber<ResponseBody>(mContext,true){
            @Override
            protected void onSuccess(ResponseBody info) {
                try {
                    mView.uploadHeadSuccess(info.string());
                }catch (Exception e){

                }

            }
            @Override
            protected void onFailure(String message) {
                mView.loadFail(message);
            }
        });

    }
    @Override
    public void updateUser(Map<String, String> map) {
        addSubscrebe(Api.getInstance().updateUser(map), new RxSubscriber<CodeBean>(mContext, true) {
            @Override
            protected void onSuccess(CodeBean requestStatusBean) {
//                Log.e("ttt",requestStatusBean.toString());

                if (requestStatusBean.getCode().equals("0")) {
                    mView.updateUserSuccess(requestStatusBean);
                } else {
                    mView.loadFail("修改失败");
                }

            }

            @Override
            protected void onFailure(String message) {
                mView.loadFail(message);
            }
        });
    }
}
