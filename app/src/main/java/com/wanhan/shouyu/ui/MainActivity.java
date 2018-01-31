package com.wanhan.shouyu.ui;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Toast;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.kitnew.ble.QNApiManager;
import com.kitnew.ble.QNBleApi;
import com.kitnew.ble.QNBleCallback;
import com.kitnew.ble.QNBleDevice;
import com.kitnew.ble.QNData;
import com.kitnew.ble.QNUser;
import com.wanhan.shouyu.AppConstant;
import com.wanhan.shouyu.R;
import com.wanhan.shouyu.base.BaseActivity;
import com.wanhan.shouyu.bean.MainTabEntity;
import com.wanhan.shouyu.ui.me.fragment.MeFragment;
import com.wanhan.shouyu.ui.tool.fragment.ToolFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;

public class MainActivity extends BaseActivity  {
    @Bind(R.id.tab_layout)
    CommonTabLayout tabLayout;

    private String[] mTitles = {"工具","我的",};
    private int[]  mIconSelectIds = {
            R.drawable.icon_tool,R.drawable.icon_me};
    private int[] mIconUnselectIds = {
            R.drawable.icon_tool_gray,R.drawable.icon_me_gray};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private ToolFragment toolFragment;
    private MeFragment meFragment;
    private int currentPosition;


    QNBleApi qnBleApi;

    QNBleApi bleApi;

    QNUser user;
    QNBleDevice device;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化frament
        initFragment(savedInstanceState);
        tabLayout.measure(0,0);

    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initToolBar() {

    }

    @Override
    protected void initView() {
       /* qnBleApi = QNApiManager.getApi(this);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d");
        Date birthday = null;
        try {
            birthday = dateFormat.parse("1993-9-8");
        } catch (Exception e) {

        }
        QNUser user = new QNUser("1", 180, 1, birthday);
        QNBleDevice qnBleDevice = new QNBleDevice(BluetoothAdapter.getDefaultAdapter().getRemoteDevice("E2:43:E5:4D:1B:D7"));
        startActivity(ConnectActivity.getCallIntent(this, user, qnBleDevice));*/
        initTab();
    }
    /**
     * 初始化tab
     */
    private void initTab() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new MainTabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        tabLayout.setTabData(mTabEntities);
        //点击监听
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                SwitchTo(position);
            }
            @Override
            public void onTabReselect(int position) {

            }
        });
    }
    /**
     * 初始化碎片
     */
    private void initFragment(Bundle savedInstanceState) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        int currentTabPosition = 0;
        if (savedInstanceState != null) {
            toolFragment = (ToolFragment) getSupportFragmentManager().findFragmentByTag("toolFragment");
            meFragment= (MeFragment) getSupportFragmentManager().findFragmentByTag("meFragment");

            currentTabPosition = savedInstanceState.getInt(AppConstant.HOME_CURRENT_TAB_POSITION);
        } else {
            toolFragment = new ToolFragment();
            meFragment = new MeFragment();
            transaction.add(R.id.fl_body, toolFragment, "toolFragment");
            transaction.add(R.id.fl_body, meFragment, "meFragment");
        }
        transaction.commit();
        SwitchTo(currentTabPosition);
        tabLayout.setCurrentTab(currentTabPosition);



    }


    /**
     * 切换
     */
    private void SwitchTo(int position) {
        currentPosition=position;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (position) {
            //工具
            case 0:
                transaction.show(toolFragment);
                transaction.hide(meFragment);
                transaction.commitAllowingStateLoss();
                break;
            //我的
            case 1:
                transaction.hide(toolFragment);
                transaction.show(meFragment);
                transaction.commitAllowingStateLoss();
                break;
           default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
