package com.wanhan.shouyu.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.bumptech.glide.util.Util;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXFileObject;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMusicObject;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXVideoObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wanhan.shouyu.AppConstant;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/2/26.
 */

public class WXShareManager {
    private final String APP_ID = AppConstant.WEIXIN_APP_ID;
    private IWXAPI api;
    private ShareResultListener listener;

    private final String TRANSACTION_TEXT = "text";
    private final String TRANSACTION_IMAGE = "image";
    private final String TRANSACTION_WEBPAGE = "webpage";
    private final String TRANSACTION_MUSIC = "music";
    private final String TRANSACTION_VIDEO = "video";
    private final String TRANSACTION_FILE = "file";

    private final int THUMBNAIL_SIZE = 150;

    //分享类型
    public enum ShareType {
        FRIENDS, FRIENDSCIRCLE, FAVOURITE
    }

    private WXShareManager() {
        //no instance
    }

    public static class InstanceHolder {
        private static WXShareManager INSTANCE = new WXShareManager();
    }

    public static WXShareManager get() {
        return InstanceHolder.INSTANCE;
    }

    /**
     * 初始化微信API 推荐在自定义的Application的onCreate方法中调用
     * @param appContext
     */
    public void init(Context appContext) {
        api = WXAPIFactory.createWXAPI(appContext, APP_ID, true);
        api.registerApp(APP_ID);
    }

    /**
     * @param shareText
     * @param title
     * @param description
     * @param type
     * @param listener
     * @return
     */
    public boolean shareText(@NonNull String shareText, @NonNull String title, @NonNull String description,
                             @NonNull ShareType type, @Nullable ShareResultListener listener) {
        this.listener = listener;
        WXTextObject obj = new WXTextObject(shareText);
        WXMediaMessage msg = buildMediaMesage(obj, title, description);
        BaseReq req = buildSendReq(msg, buildTransaction(TRANSACTION_TEXT), getWxShareType(type));
        return api.sendReq(req);
    }

    public boolean shareImage(@NonNull Bitmap bitmap, @NonNull String title, @NonNull String description,
                              @NonNull ShareType type, @Nullable ShareResultListener listener) {
        this.listener = listener;

        WXMediaMessage.IMediaObject obj = new WXImageObject(bitmap);
        WXMediaMessage msg = buildMediaMesage(obj, title, description);
        msg.setThumbImage(bitmap);
        BaseReq req = buildSendReq(msg, buildTransaction(TRANSACTION_IMAGE), getWxShareType(type));
        return api.sendReq(req);
    }

    public boolean shareImage(@NonNull final String pathOrUrl, @NonNull Bitmap thumbnail, @NonNull String title, @NonNull String description,
                              @NonNull ShareType type, ShareResultListener listener) {
        this.listener = listener;

        //分享url图片
        if(pathOrUrl.contains("://")) {
//            obj.imageUrl = pathOrUrl; //不能编译???
          /*  Observable.<byte[]>create(subscriber -> {
                subscriber.onNext(Util.getHtmlByteArray(pathOrUrl));
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(data -> {
                        shareImage(data, title, description, type, listener);
                    });
            return true;*/
        }

        //分享文件系统中的图片
        WXImageObject obj = new WXImageObject();
        obj.imagePath = pathOrUrl;
        WXMediaMessage msg = buildMediaMesage(obj, title, description);
        if(thumbnail != null) {
            msg.setThumbImage(thumbnail);
        } else {
            Bitmap thumbNail = getThumbnail(pathOrUrl, THUMBNAIL_SIZE);
            msg.setThumbImage(thumbNail);
            thumbNail.recycle();
        }
        BaseReq req = buildSendReq(msg, buildTransaction(TRANSACTION_IMAGE), getWxShareType(type));
        return api.sendReq(req);
    }

    public boolean shareImage(@NonNull byte[] imageData, @NonNull String title, @NonNull String description,
                              @NonNull ShareType type, @Nullable ShareResultListener listener) {
        this.listener = listener;

        WXMediaMessage.IMediaObject obj = new WXImageObject(imageData);
        WXMediaMessage msg = buildMediaMesage(obj, title, description);

        Bitmap thumbnail = getThumbnail(imageData, THUMBNAIL_SIZE);
        msg.setThumbImage(thumbnail);
        thumbnail.recycle();

        BaseReq req = buildSendReq(msg, buildTransaction(TRANSACTION_IMAGE), getWxShareType(type));
        return api.sendReq(req);
    }

    public boolean shareMusic(@NonNull String url, @NonNull String title, @NonNull String description,
                              @NonNull ShareType type, @Nullable ShareResultListener listener) {
        this.listener = listener;

        WXMusicObject obj = new WXMusicObject();
        obj.musicUrl = url;
        WXMediaMessage msg = buildMediaMesage(obj, title, description);
        BaseReq req = buildSendReq(msg, buildTransaction(TRANSACTION_MUSIC), getWxShareType(type));
        return api.sendReq(req);
    }

    public boolean shareVideo(@NonNull String url, @NonNull String title, @NonNull String description,
                              @NonNull ShareType type, @Nullable ShareResultListener listener) {
        this.listener = listener;

        WXVideoObject obj = new WXVideoObject();
        obj.videoUrl = url;
        WXMediaMessage msg = buildMediaMesage(obj, title, description);
        BaseReq req = buildSendReq(msg, buildTransaction(TRANSACTION_VIDEO), getWxShareType(type));
        return api.sendReq(req);
    }

    public boolean shareWebPage(@NonNull String weburl, @NonNull String title, @NonNull String description,
                                @NonNull ShareType type, @Nullable ShareResultListener listener) {
        this.listener = listener;

        WXWebpageObject obj = new WXWebpageObject(weburl);
        WXMediaMessage msg = buildMediaMesage(obj, title, description);
        BaseReq req = buildSendReq(msg, buildTransaction(TRANSACTION_WEBPAGE), getWxShareType(type));
        return api.sendReq(req);
    }

    public boolean shareFile(@NonNull String filepath, @NonNull String title, @NonNull String description,
                             @NonNull ShareType type, @Nullable ShareResultListener listener) {
        this.listener = listener;

        WXFileObject obj = new WXFileObject(filepath);
        WXMediaMessage msg = buildMediaMesage(obj, title, description);
        BaseReq req = buildSendReq(msg, buildTransaction(TRANSACTION_FILE), getWxShareType(type));
        return api.sendReq(req);
    }

    public void handleIntent(Intent intent, IWXAPIEventHandler handler) {
        api.handleIntent(intent, handler);
    }

    public boolean performShareResult(boolean result) {
        if(listener != null) {
            Log.e("_share_", "performShareResult: " + result);
            listener.onShareResult(result);
            listener = null;
            return true;
        }

        return false;
    }

    private int getWxShareType(ShareType type) {
        if(type == ShareType.FRIENDS) {
            return SendMessageToWX.Req.WXSceneSession;
        } else if(type == ShareType.FRIENDSCIRCLE) {
            return SendMessageToWX.Req.WXSceneTimeline;
        } else if(type == ShareType.FAVOURITE) {
            return SendMessageToWX.Req.WXSceneFavorite;
        }

        throw new IllegalArgumentException("非法参数: 不识别的ShareType -> " + type.name());
    }

    private Bitmap getThumbnail(@NonNull String path, int thumbnailSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        Log.e("_share_", "outWidth=" + options.outWidth + ", outHeight=" + options.outHeight);
        int sourceSize = Math.min(options.outWidth, options.outHeight);
        options.inJustDecodeBounds = false;
        options.inSampleSize = sourceSize / thumbnailSize;
        return BitmapFactory.decodeFile(path, options);
    }

    private Bitmap getThumbnail(@NonNull byte[] imageData, int thumbnailSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(imageData, 0, imageData.length, options);
        options.inJustDecodeBounds = false;
        Log.e("_share_", "outWidth=" + options.outWidth + ", outHeight=" + options.outHeight);
        int sourceSize = Math.min(options.outWidth, options.outHeight);
        options.inSampleSize = sourceSize / thumbnailSize;
        return BitmapFactory.decodeByteArray(imageData, 0, imageData.length, options);
    }

    private BaseReq buildSendReq(WXMediaMessage msg, String transaction, int scene) {
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.message = msg;
        req.transaction = transaction;
        req.scene = scene;
        return req;
    }

    private WXMediaMessage buildMediaMesage(WXMediaMessage.IMediaObject obj, String title, String description) {
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = obj;
        msg.title = title;
        msg.description = description;
        return msg;
    }

    /**
     * @param type text/image/webpage/music/video
     * @return
     */
    private String buildTransaction(String type) {
        return TextUtils.isEmpty(type) ? String.valueOf(System.currentTimeMillis()) : (type + System.currentTimeMillis());
    }

    /**
     * 分享结果回调
     */
    public interface ShareResultListener {
        void onShareResult(boolean result);
    }


}
