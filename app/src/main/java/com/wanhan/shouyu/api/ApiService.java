package com.wanhan.shouyu.api;

import com.google.gson.JsonObject;
import com.wanhan.shouyu.bean.json.CodeBean;
import com.wanhan.shouyu.bean.json.HistoryRecordBean;
import com.wanhan.shouyu.bean.json.IdentifyCodeBean;
import com.wanhan.shouyu.bean.json.RecommendInformationBean;
import com.wanhan.shouyu.bean.json.RegisterBean;
import com.wanhan.shouyu.bean.json.TermsBean;
import com.wanhan.shouyu.bean.json.UserBean;
import com.wanhan.shouyu.ui.login.activity.TermsActivity;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    //修改用户头像
    @Multipart
    @POST("servlet/UploadFileServlet")
    Observable<ResponseBody> changeUserHead(@Part MultipartBody.Part file);
        //推荐信息
    @GET("information!findInformation.action")
    Observable<List<RecommendInformationBean>> recommendInformation(@QueryMap Map<String,String> map);
    //添加历史记录
    @GET("historicalRecord!addHistoricalRecord.action")
    Observable<CodeBean> addHistoryRecord(@QueryMap Map<String,String> map);
    //查找历史记录
    @GET("historicalRecord!findHistoricalRecord.action")
    Observable<List<HistoryRecordBean>> findHistoryRecord(@QueryMap Map<String,String> map);

//    微信登录
    @GET("user!doWeiXinLogin.action")
    Observable<UserBean> bangWeixin(@QueryMap Map<String,String> map);
//    绑定手机
    @GET("user!updateLoginInfo.action")
    Observable<CodeBean> bangPhone(@QueryMap Map<String,String> map);

//    忘记密码
    @GET("user!findUserPwd.action")
    Observable<CodeBean> forgetPassword(@QueryMap Map<String,String> map);
    //    忘记密码
    @GET("user!removeWeiXinBang.action")
    Observable<CodeBean> unbundingWechat(@QueryMap Map<String,String> map);

}
