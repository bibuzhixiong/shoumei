package com.wanhan.shouyu.ui.me.activity;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wanhan.shouyu.AppConstant;
import com.wanhan.shouyu.R;
import com.wanhan.shouyu.base.BaseActivity;
import com.wanhan.shouyu.base.BaseToolbar;
import com.wanhan.shouyu.ui.login.activity.LoginActivity;
import com.wanhan.shouyu.utils.SharedPreferencesUtil;
import com.wanhan.shouyu.utils.ToastUtil;
import com.wanhan.shouyu.utils.WXShareManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/2/2.
 */

public class InviteFriendsActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    BaseToolbar toolbar;
    @Bind(R.id.ll_link)
    LinearLayout llLink;
    @Bind(R.id.ll_code)
    LinearLayout llCode;
    private IWXAPI api;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_invite_friends;
    }

    @Override
    protected void initToolBar() {
        toolbar.setLeftButtonOnClickLinster(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish_Activity(InviteFriendsActivity.this);
            }
        });
    }

    @Override
    protected void initView() {
        api = WXAPIFactory.createWXAPI(InviteFriendsActivity.this, AppConstant.WEIXIN_APP_ID, false);
        // 将该app注册到微信
        api.registerApp(AppConstant.WEIXIN_APP_ID);
    }
    @OnClick({R.id.ll_link,R.id.ll_code})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        WXShareManager mgr = WXShareManager.get();
        WXShareManager.ShareResultListener listener = new WXShareManager.ShareResultListener() {
            @Override
            public void onShareResult(boolean result) {
                Log.e("_share_", "onShareResult: " + result);
            }
        };
        boolean result = false;


        switch (v.getId()){
            case R.id.ll_link:

                Drawable d = ActivityCompat.getDrawable(this, R.mipmap.ic_launcher);
                String name=SharedPreferencesUtil.getValue(InviteFriendsActivity.this,"NICKNAME","")+"";
                result=mgr.shareWebPage(AppConstant.BASE_URL+"/registration?userId="+ SharedPreferencesUtil.getValue(InviteFriendsActivity.this,"USERID","")+"","瘦鱼帮你健康减脂",name+"邀请您健康减脂，快来开启健康减脂之旅吧！", WXShareManager.ShareType.FRIENDS, listener);
//                result = mgr.shareImage(((BitmapDrawable)d).getBitmap(), "", "分享一张图片", WXShareManager.ShareType.FRIENDS, listener);
                Toast.makeText(this, "share result = " + result, Toast.LENGTH_SHORT).show();

                break;
            case R.id.ll_code:
                break;
        }
    }
    /**
     * @param type text/image/webpage/music/video
     * @return
     */
    private String buildTransaction(String type) {
        return TextUtils.isEmpty(type) ? String.valueOf(System.currentTimeMillis()) : (type + System.currentTimeMillis());
    }

}
