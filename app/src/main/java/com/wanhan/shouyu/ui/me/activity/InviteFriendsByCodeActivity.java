package com.wanhan.shouyu.ui.me.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wanhan.shouyu.AppConstant;
import com.wanhan.shouyu.R;
import com.wanhan.shouyu.base.BaseActivity;
import com.wanhan.shouyu.base.BaseToolbar;
import com.wanhan.shouyu.utils.DensityUtil;
import com.wanhan.shouyu.utils.SharedPreferencesUtil;
import com.wanhan.shouyu.utils.ToastUtil;
import com.wanhan.shouyu.utils.WXShareManager;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/2/26.
 */

public class InviteFriendsByCodeActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    BaseToolbar toolbar;
    @Bind(R.id.img_code)
    ImageView imgCode;
    @Bind(R.id.bt_share)
    Button btShare;
    private IWXAPI api;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_invite_friends_by_code;
    }

    @Override
    protected void initToolBar() {
            toolbar.setLeftButtonOnClickLinster(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish_Activity(InviteFriendsByCodeActivity.this);
                }
            });
    }

    @Override
    protected void initView() {
//        api = WXAPIFactory.createWXAPI(InviteFriendsByCodeActivity.this, AppConstant.WEIXIN_APP_ID, false);
//        // 将该app注册到微信
//        api.registerApp(AppConstant.WEIXIN_APP_ID);
        int width= DensityUtil.getScreenWidth(InviteFriendsByCodeActivity.this)*8/10;
     Bitmap bitmap= EncodingUtils.createQRCode(AppConstant.BASE_URL+"/registration?userId="+ SharedPreferencesUtil.getValue(InviteFriendsByCodeActivity.this,"USERID","")+"",width,width,null);
            imgCode.setImageBitmap(bitmap);
            btShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.showShortToast("进来了");
                    WXShareManager mgr = WXShareManager.get();
                    WXShareManager.ShareResultListener listener = new WXShareManager.ShareResultListener() {
                        @Override
                        public void onShareResult(boolean result) {
                            Log.e("_share_", "onShareResult: " + result);
                        }
                    };
                    boolean result = false;
                    Bitmap bitmap= EncodingUtils.createQRCode(AppConstant.BASE_URL+"/registration?userId="+ SharedPreferencesUtil.getValue(InviteFriendsByCodeActivity.this,"USERID","")+"",150,150,null);

                    String name=SharedPreferencesUtil.getValue(InviteFriendsByCodeActivity.this,"NICKNAME","")+"";
//                    result=mgr.shareWebPage(AppConstant.BASE_URL+"/registration?userId="+ SharedPreferencesUtil.getValue(InviteFriendsByCodeActivity.this,"USERID","")+"","瘦鱼帮你健康减脂",name+"邀请您健康减脂，快来开启健康减脂之旅吧！", WXShareManager.ShareType.FRIENDS, listener);

                    result=mgr.shareImage(bitmap,null,null, WXShareManager.ShareType.FRIENDS, listener);

                }
            });

    }


}
