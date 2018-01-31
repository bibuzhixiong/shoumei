package com.wanhan.shouyu.base;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;


import com.wanhan.shouyu.R;
import com.wanhan.shouyu.utils.RescourseUtil;
import com.wanhan.shouyu.utils.TUtil;
import com.wanhan.shouyu.utils.ToastUtil;

import butterknife.ButterKnife;
import qiu.niorgai.StatusBarCompat;

/**
 * Created by lan on 2017/9/22.
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity
        implements View.OnClickListener{
    protected P mPresenter;

    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doBeforeSetcontentView();
        this.setContentView(getLayoutId());
        StatusBarCompat.setStatusBarColor(this, RescourseUtil.getColor(R.color.colorAccent));

        mContext=this;
        //添加注解
        ButterKnife.bind(this);
         /*
        通过反射获得Presenter,设置View
         */
        mPresenter= TUtil.getT(this,0);
        if(mPresenter!=null){
            mPresenter.setV(this);
            mPresenter.mContext=this;
        }
        this.initToolBar();
        this.initView();
        //把Activity添加到集合，统一管理
        ActivityManager.getActivityMar().addActivity(this);
    }
    /**
     * 设置layout前配置
     */
    private void doBeforeSetcontentView() {
        // 无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
    /*********************子类实现*****************************/
    //获取布局文件
    protected abstract int getLayoutId();
    //初始化ActionBar
    protected abstract void initToolBar();
    //初始化view
    protected abstract void initView();

    /**
     * 通过Class跳转界面
     **/
    protected void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    protected void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    protected void startActivityForResult(Class<?> cls, Bundle bundle,
                                          int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    protected void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }
    /**
     * 关闭Activity
     * @param activity
     */
    protected void finish_Activity(Activity activity){
        activity.finish();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null){
            mPresenter.onDestroy();
        }
        ButterKnife.unbind(this);
        //Activity销毁时，从集合移除
        ActivityManager.getActivityMar().finishActivity(this);
    }

    @Override
    public void onClick(View v) {

    }

    public interface PermissionCallback{
        void hasPermission();
        void noPermission();
    }
    private int permissionRequestCode = 88;
    private PermissionCallback permissionRunnable ;
    /**
     * Android M运行时权限请求封装
     * @param permissionDes 权限描述
     * @param runnable 请求权限回调
     * @param permissions 请求的权限（数组类型），直接从Manifest中读取相应的值，比如Manifest.permission.WRITE_CONTACTS
     */
    public void performCodeWithPermission(@NonNull String permissionDes, PermissionCallback runnable, @NonNull String... permissions){
        if(permissions == null || permissions.length == 0)return;
//        this.permissionrequestCode = requestCode;
        this.permissionRunnable = runnable;
        if((Build.VERSION.SDK_INT < Build.VERSION_CODES.M) || checkPermissionGranted(permissions)){
            if(permissionRunnable!=null){
                permissionRunnable.hasPermission();
                permissionRunnable = null;
            }
        }else{
            //permission has not been granted.
            requestPermission(permissionDes,permissionRequestCode,permissions);
        }

    }
    private boolean checkPermissionGranted(String[] permissions){
        boolean flag = true;
        for(String p:permissions){
            if(ActivityCompat.checkSelfPermission(this, p) != PackageManager.PERMISSION_GRANTED){
                flag = false;
                break;
            }
        }
        return flag;
    }
    private void requestPermission(String permissionDes,final int requestCode,final String[] permissions){
        if(shouldShowRequestPermissionRationale(permissions)){
            //如果用户之前拒绝过此权限，再提示一次准备授权相关权限
            new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage(permissionDes)
                    .setPositiveButton("好", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(BaseActivity.this, permissions, requestCode);
                        }
                    }).show();

        }else{
            // Contact permissions have not been granted yet. Request them directly.
            ActivityCompat.requestPermissions(BaseActivity.this, permissions, requestCode);
        }
    }
    private boolean shouldShowRequestPermissionRationale(String[] permissions){
        boolean flag = false;
        for(String p:permissions){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,p)){
                flag = true;
                break;
            }
        }
        return flag;
    }
    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if(requestCode == permissionRequestCode){
            if(verifyPermissions(grantResults)){
                if(permissionRunnable!=null) {
                    permissionRunnable.hasPermission();
                    permissionRunnable = null;
                }
            }else{
                ToastUtil.showShortToast("暂无权限执行相关操作！");
                if(permissionRunnable!=null) {
                    permissionRunnable.noPermission();
                    permissionRunnable = null;
                }
            }
        }else{
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }
    public boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if(grantResults.length < 1){
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
}
