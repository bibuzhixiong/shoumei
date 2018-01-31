package com.wanhan.shouyu.ui.tool.fragment;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import com.kitnew.ble.QNApiManager;
import com.kitnew.ble.QNBleApi;
import com.kitnew.ble.QNBleCallback;
import com.kitnew.ble.QNBleDevice;
import com.kitnew.ble.QNData;
import com.kitnew.ble.QNUser;
import com.wanhan.shouyu.R;
import com.wanhan.shouyu.base.BaseFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import butterknife.Bind;

/**
 * Created by Administrator on 2018/1/27.
 */

public class ToolFragment extends BaseFragment  {
    @Bind(R.id.heartbeatViews)
    TextView tv_shangcheng;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tool;
    }

    @Override
    protected void initView() {


    }


}
