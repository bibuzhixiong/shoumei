package com.wanhan.shouyu.base.rx;
import android.content.Context;


import com.wanhan.shouyu.App;
import com.wanhan.shouyu.R;
import com.wanhan.shouyu.dialog.LoadingDialog;
import com.wanhan.shouyu.utils.NetWorkUtil;
import com.wanhan.shouyu.utils.RescourseUtil;

import rx.Subscriber;

/**
 * Created by lan on 2016/7/4.
 */
public abstract class RxSubscriber<T> extends Subscriber<T> {
    private Context mContext;
    private String msg;
    private boolean showDialog=true;
    private LoadingDialog mLoadingDialog;
    public RxSubscriber(Context context, String msg, boolean showDialog){
        this.mContext = context;
        this.msg = msg;
        this.showDialog=showDialog;
    }
    public RxSubscriber(Context context) {
        this(context, App.getAppContext().getString(R.string.loading),true);
    }
    public RxSubscriber(Context context, boolean showDialog) {
        this(context, App.getAppContext().getString(R.string.loading),showDialog);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (showDialog) {
            try {
                mLoadingDialog=new LoadingDialog(mContext,msg,true);
                mLoadingDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCompleted(){
        if (showDialog)
            if(mLoadingDialog!=null){
                mLoadingDialog.cancelDialog();
                mLoadingDialog=null;
            }
    }
    @Override
    public void onError(Throwable e) {
        if (showDialog){
            if(mLoadingDialog!=null){
                mLoadingDialog.cancelDialog();
                mLoadingDialog=null;
            }
        }
        e.printStackTrace();
        //网络
        if (!NetWorkUtil.isNetConnected(App.getAppContext())) {
            onFailure(RescourseUtil.getString(R.string.no_net));
        }
        //服务器
        else{
            onFailure(e.getMessage());
        }

    }
    @Override
    public void onNext(T t) {
        onSuccess(t);

    }
    protected abstract void onSuccess(T t);

    protected abstract void onFailure(String message);
}
