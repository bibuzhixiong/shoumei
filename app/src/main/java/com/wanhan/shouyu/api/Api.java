package com.wanhan.shouyu.api;


import com.google.gson.JsonObject;
import com.wanhan.shouyu.bean.json.CodeBean;
import com.wanhan.shouyu.bean.json.HistoryRecordBean;
import com.wanhan.shouyu.bean.json.IdentifyCodeBean;
import com.wanhan.shouyu.bean.json.RecommendInformationBean;
import com.wanhan.shouyu.bean.json.RegisterBean;
import com.wanhan.shouyu.bean.json.TermsBean;
import com.wanhan.shouyu.bean.json.UserBean;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by lan on 2017/9/22.
 */
public class Api {

//    public final static String API_BASE_URL = "http://lsy.tunnel.echomod.cn/ShuaiMei/";
    public final static String API_BASE_URL = "http://q3.wanhanqianshun.com";
    public static Api instance;
    private ApiService service;
    //添加请求头
    Interceptor mInterceptor =new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
//            Response originalResponse=chain.proceed(chain.request());

            Request.Builder requestBuilder = original.newBuilder()
                    .addHeader("JSESSIONID", "11");
            Request request = requestBuilder.build();
            return chain.proceed(request);
        }
    };
    //配置网络参数
    public Api() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(20 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(20 * 1000, TimeUnit.MILLISECONDS)
//                .addInterceptor(mInterceptor)
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 添加Rx适配器
                .addConverterFactory(GsonConverterFactory.create()) // 添加Gson转换器
                .client(okHttpClient)
                .build();
        service = retrofit.create(ApiService.class);
    }

    //单例
    public static Api getInstance() {
        if (instance == null)
            instance = new Api();
        return instance;
    }
    //登录
    public Observable<UserBean> login(Map<String,String> map){
        return service.login(map);
    }
    //获取验证码
    public Observable<IdentifyCodeBean> getCode(Map<String,String> map){
        return service.getCode(map);
    }
    //注册
    public Observable<RegisterBean> register(Map<String,String> map){return service.register(map);}
    //瘦鱼服务条款
    public Observable<TermsBean> getTerms(){return service.getTerms();}
    //修改个人资料
    public Observable<CodeBean> updateUser(Map<String,String> map){return service.updateUser(map);}
    //意见反馈
    public Observable<CodeBean> suggestive(Map<String,String> map){return service.suggestive(map);}
    //修改密码
    public Observable<CodeBean> updatePassword(Map<String,String> map){return service.updatePassword(map);}
    //修改用户头像
    public Observable<ResponseBody> changeUserHead(MultipartBody.Part body){
        return service.changeUserHead(body);
    }

    //推荐信息
    public Observable<List<RecommendInformationBean>> recommendInformation(Map<String,String> map){
        return service.recommendInformation(map);}

        //添加历史记录
        public Observable<CodeBean> addHistoryRecord(Map<String,String> map){
            return service.addHistoryRecord(map);}
    //添加历史记录
    public Observable<List<HistoryRecordBean>> findHistoryRecord(Map<String,String> map){
        return service.findHistoryRecord(map);}
    //绑定微信
    public Observable<UserBean> bangWeixin(Map<String,String> map){
        return service.bangWeixin(map);}
    //绑定微信
    public Observable<CodeBean> unbundingWechat(Map<String,String> map){
        return service.unbundingWechat(map);}

    //绑定手机
    public Observable<CodeBean> bangPhone(Map<String,String> map){
        return service.bangPhone(map);}
    //忘记密码
    public Observable<CodeBean> forgetPassword(Map<String,String> map){
        return service.forgetPassword(map);}


}
