package com.wanhan.shouyu.ui.me.activity;

import android.content.DialogInterface;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wanhan.shouyu.AppConstant;
import com.wanhan.shouyu.R;
import com.wanhan.shouyu.base.BaseActivity;
import com.wanhan.shouyu.utils.CheckUtils;
import com.wanhan.shouyu.utils.RescourseUtil;
import com.wanhan.shouyu.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/2/5.
 */

public class RecommendActivity extends BaseActivity {
    @Bind(R.id.img_back)
    ImageView imgBack;
//    @Bind(R.id.webview)
    WebView webview;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.web_container)
    FrameLayout web_container;
    @Bind(R.id.img_close)
    ImageView imgClose;
    @Bind(R.id.progressBar1)
    ProgressBar progressBar1;
    List<String> list=new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_recommend;
    }

    @Override
    protected void initToolBar() {
    }

    @Override
    protected void initView() {
        Bundle bundle=getIntent().getExtras();
        String id=bundle.getString("id");
        String type=bundle.getString("type");
        tvTitle.setText(bundle.getString("title"));
        webview = new WebView(getApplicationContext());
        web_container.addView(webview);
        final WebSettings settings = webview.getSettings();
        settings.setAllowFileAccess(true);// 设置允许访问文件数据
//        settings.setSupportZoom(true);

        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
//        webview.setWebContentsDebuggingEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//优先使用网络
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAppCacheEnabled(true);            //设置webview可缓存
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);      //缓存模式
        settings.setAppCacheMaxSize(0); // 设置缓冲大小，8M
        settings.setAllowFileAccess(false);            // 设置使用缓存


        settings.setLoadWithOverviewMode(true);//设置webview加载的页面的模式
        settings.setDisplayZoomControls(false);//隐藏webview缩放按钮
        settings.setJavaScriptEnabled(true); // 设置支持javascript脚本
        settings.setAllowFileAccess(true); // 允许访问文件
        settings.setBuiltInZoomControls(false); // 设置显示缩放按钮
        settings.setSupportZoom(false); // 支持缩放
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        settings.setJavaScriptEnabled(true);


        ClipDrawable drawable = new ClipDrawable(new ColorDrawable(RescourseUtil.getColor(R.color.green)), Gravity.LEFT, ClipDrawable.HORIZONTAL);
        progressBar1.setProgressDrawable(drawable);
        progressBar1.setProgressDrawable(drawable);


        webview.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO 自动生成的方法存根

                if(newProgress==100){
                    progressBar1.setVisibility(View.GONE);//加载完网页进度条消失
                }
                else{
                    progressBar1.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    progressBar1.setProgress(newProgress);//设置进度值
                }

            }
            @Override
            public boolean onJsAlert(WebView view, String url,final String message, JsResult result) {
                Log.d("main", "onJsAlert:" + message);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showShortToast(message);
//                        new AlertDialog.Builder(RecommendActivity.this)
//                                .setTitle("提示")
//                                .setMessage(message)
//                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
////                                        mWebView.reload();//重写刷新页面
//                                    }
//                                })
//                                .setNegativeButton("取消", null)
//                                .show();

                    }
                });
                result.confirm();//这里必须调用，否则页面会阻塞造成假死
                return true;
            }
        });
        // 启用javascript
        webview.getSettings().setJavaScriptEnabled(true);

        webview.addJavascriptInterface(RecommendActivity.this,"hello");


        if(type.equals("test")){
            list.add(AppConstant.HEALTH_TEST_URL+id);
            webview.loadUrl(AppConstant.HEALTH_TEST_URL+id);
        }else if(type.equals("help")){
            list.add(AppConstant.USE_HELP_URL);
            webview.loadUrl(AppConstant.USE_HELP_URL);
        }else if(type.equals("about_us")){
            list.add(AppConstant.ABOUT_US_URL+ "V"+CheckUtils.getVersionCode(RecommendActivity.this));
            webview.loadUrl(AppConstant.ABOUT_US_URL+"V"+CheckUtils.getVersionCode(RecommendActivity.this));
        }else{
            list.add(AppConstant.INFORMATION_URL+id);
            webview.loadUrl(AppConstant.INFORMATION_URL+id);
        }

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list.size()==1){
                    finish_Activity(RecommendActivity.this);
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                Toast.makeText(MainActivity.this,"location",Toast.LENGTH_LONG).show();
                         list.remove(list.size()-1);
                            webview.loadUrl(list.get(list.size()-1));
                        }
                    });
                }

            }
        });
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish_Activity(RecommendActivity.this);
            }
        });
    }

        @JavascriptInterface
        public void showAndroid(final String url){
//            Toast.makeText(RecommendActivity.this,url,Toast.LENGTH_SHORT).show();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                Toast.makeText(MainActivity.this,"location",Toast.LENGTH_LONG).show();
                    list.add(AppConstant.BASE_URL+url);
                    webview.loadUrl(AppConstant.BASE_URL+url);
                }
            });
//            Toast.makeText(RecommendActivity.this,url,Toast.LENGTH_SHORT).show();
        }
    @JavascriptInterface
    public void update(){

       ToastUtil.showShortToast("已是最新版本");

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        webview.destroy();
        webview=null;
    }

}
