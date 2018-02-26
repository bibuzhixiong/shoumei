package com.wanhan.shouyu.ui.me.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wanhan.shouyu.AppConstant;
import com.wanhan.shouyu.R;
import com.wanhan.shouyu.base.BaseActivity;
import com.wanhan.shouyu.base.BaseToolbar;
import com.wanhan.shouyu.base.rx.RxBus;
import com.wanhan.shouyu.bean.json.CodeBean;
import com.wanhan.shouyu.bean.json.UserBean;
import com.wanhan.shouyu.event.WeChatEvent;
import com.wanhan.shouyu.ui.login.activity.LoginActivity;
import com.wanhan.shouyu.ui.me.contract.BundingWechatContract;
import com.wanhan.shouyu.ui.me.presenter.BundingWechatPresenter;
import com.wanhan.shouyu.utils.MapUtil;
import com.wanhan.shouyu.utils.SharedPreferencesUtil;
import com.wanhan.shouyu.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Administrator on 2018/2/26.
 */

public class BundingWechatActivity extends BaseActivity<BundingWechatPresenter> implements BundingWechatContract.View {
    @Bind(R.id.toolbar)
    BaseToolbar toolbar;
    @Bind(R.id.ll_jiebang)
    LinearLayout llJiebang;
    @Bind(R.id.tv_wechat)
    TextView tvWechat;
    private IWXAPI api;
    private Subscription subscription;
    private String openId="";
    @Override
    protected int getLayoutId() {
        return R.layout.activity_bang_weixin;
    }

    @Override
    protected void initToolBar() {
        toolbar.setLeftButtonOnClickLinster(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish_Activity(BundingWechatActivity.this);
            }
        });
    }

    @Override
    protected void initView() {
        api = WXAPIFactory.createWXAPI(BundingWechatActivity.this, AppConstant.WEIXIN_APP_ID, false);
        // 将该app注册到微信
        api.registerApp(AppConstant.WEIXIN_APP_ID);
        event();
        String openid= SharedPreferencesUtil.getValue(BundingWechatActivity.this,"OPENID","")+"";
        if(openid.equals("null")||openid==null||openid.equals("")){
            tvWechat.setText("绑定微信");
        }

        llJiebang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String openid= SharedPreferencesUtil.getValue(BundingWechatActivity.this,"OPENID","")+"";
                if(openid.equals("null")||openid==null||openid.equals("")){
                    if (!api.isWXAppInstalled()) {
                        ToastUtil.showbottomShortToast("您还未安装微信客户端");
                        return;
                    }
                    final SendAuth.Req req = new SendAuth.Req();
                    req.scope = "snsapi_userinfo";
                    req.state = "taosha_wx_login";
                    api.sendReq(req);
                }else {
                    Map<String,String> map=new HashMap<>();
                    map.put("userId",SharedPreferencesUtil.getValue(BundingWechatActivity.this,"USERID","")+"");
                    mPresenter.unbundingPhone(MapUtil.getMap(map));
                }
            }
        });
    }
    private void event(){
        subscription= RxBus.$().toObservable(WeChatEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WeChatEvent>() {
                    @Override
                    public void call(WeChatEvent event) {
//                        ToastUtil.showShortToast(event.getCode()+"");
//                        Log.e("BBB","CODE:   "+event.getCode());
                        Map<String,String> map=new HashMap<>();
                        map.put("wxCode",event.getCode());
                        mPresenter.wechatLogin(MapUtil.getMap(map));
                    }
                });
    }


    @Override
    public void wechatLoginSuccess(UserBean bean) {
        if(bean.getCode().equals("-2")){
            Map<String,String> map=new HashMap<>();
            openId=bean.getOpenId();
            map.put("openId",bean.getOpenId());
            map.put("phone",SharedPreferencesUtil.getValue(BundingWechatActivity.this,"PHONE","")+"");
            mPresenter.bangPhone(MapUtil.getMap(map));
        }else{
            ToastUtil.showShortToast("绑定失败");
        }
    }

    @Override
    public void bangPhoneSuccess(CodeBean bean) {
        if(bean.getCode().equals("0")){
            ToastUtil.showShortToast("绑定成功");
            tvWechat.setText("解绑微信");
            SharedPreferencesUtil.putValue(BundingWechatActivity.this,"OPENID",openId);
        }else{
            ToastUtil.showShortToast("绑定失败");
        }

    }

    @Override
    public void unbundingPhoneSuccess(CodeBean bean) {
        if(bean.getCode().equals("0")){
            SharedPreferencesUtil.putValue(BundingWechatActivity.this,"OPENID","");
            tvWechat.setText("绑定微信");
            ToastUtil.showShortToast("解绑成功");
        }else{
            ToastUtil.showShortToast("解绑失败");
        }

    }

    @Override
    public void loadFail(String msg) {
    ToastUtil.showShortToast(msg);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null&&!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
