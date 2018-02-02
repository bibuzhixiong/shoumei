package com.wanhan.shouyu.api;

import com.wanhan.shouyu.bean.json.CodeBean;
import com.wanhan.shouyu.bean.json.IdentifyCodeBean;
import com.wanhan.shouyu.bean.json.RegisterBean;
import com.wanhan.shouyu.bean.json.TermsBean;
import com.wanhan.shouyu.bean.json.UserBean;
import com.wanhan.shouyu.ui.login.activity.TermsActivity;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by lan on 2017/9/22.
 */

public interface ApiService {
    //修改默认地址
    @GET("user!doLogin.action")
    Observable<UserBean> login(@QueryMap Map<String, String> param);

    // 获取验证码
    @GET("phoneVerification!getVerification.action")
    Observable<IdentifyCodeBean> getCode(@QueryMap Map<String,String> map);

    //注册
    @GET("user!addUser.action")
    Observable<RegisterBean> register(@QueryMap Map<String,String> map);

    //瘦鱼服务条款
    @GET("serviceTerms!findServiceTerms.action")
    Observable<TermsBean> getTerms();


    //修改资料
    @GET("user!updateUser.action")
    Observable<CodeBean> updateUser(@QueryMap Map<String,String> map);

    //意见反馈
    @GET("feedback!addFeedback.action")
    Observable<CodeBean> suggestive(@QueryMap Map<String,String> map);

    //修改密码
    @GET("user!updateUser.action")
    Observable<CodeBean> updatePassword(@QueryMap Map<String,String> map);



}
