package com.wanhan.shouyu.base;

import android.content.Context;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by lan on 2017/9/22.
 */

public abstract class BasePresenter<V> {
    public Context mContext;
    public V mView;
    protected CompositeSubscription subscription;
    //设置View
    public void setV(V v){
        this.mView=v;

    }
    //取消订阅
    protected void unSubscribe(){
        if(subscription!=null){
            subscription.unsubscribe();
        }
    }

    /**
     * @param observable
     * @param subscriber
     * 用CompositeSubscription管理网络请求
     */
    protected void addSubscrebe(Observable observable, Subscriber subscriber){
        if(subscription==null){
            subscription=new CompositeSubscription();
        }
        subscription.add(observable
        .subscribeOn(Schedulers.io())
        .unsubscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscriber));
    }

    public void onDestroy(){
        this.mView=null;
        //取消订阅和移除集合
        unSubscribe();
    }
}
