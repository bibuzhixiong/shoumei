package com.wanhan.shouyu.ui.tool.fragment;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kitnew.ble.QNApiManager;
import com.kitnew.ble.QNBleApi;
import com.kitnew.ble.QNBleCallback;
import com.kitnew.ble.QNBleDevice;
import com.kitnew.ble.QNData;
import com.kitnew.ble.QNItemData;
import com.kitnew.ble.QNUser;
import com.wanhan.shouyu.AppConstant;
import com.wanhan.shouyu.R;
import com.wanhan.shouyu.base.BaseActivity;
import com.wanhan.shouyu.base.BaseFragment;
import com.wanhan.shouyu.bean.json.CodeBean;
import com.wanhan.shouyu.dialog.DialogInterface;
import com.wanhan.shouyu.dialog.NormalAlertDialog;
import com.wanhan.shouyu.ui.me.activity.RecommendActivity;
import com.wanhan.shouyu.ui.me.activity.SuggestionsActivity;
import com.wanhan.shouyu.ui.tool.activity.HistoryActivity;
import com.wanhan.shouyu.ui.tool.activity.MeasureResultActivity;
import com.wanhan.shouyu.ui.tool.contract.ToolContract;
import com.wanhan.shouyu.ui.tool.presenter.ToolPresenter;
import com.wanhan.shouyu.utils.MapUtil;
import com.wanhan.shouyu.utils.SharedPreferencesUtil;
import com.wanhan.shouyu.utils.ToastUtil;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import qiu.niorgai.StatusBarCompat;

/**
 * Created by Administrator on 2018/1/27.
 */

public class ToolFragment extends BaseFragment<ToolPresenter> implements QNBleCallback,ToolContract.View {


    QNBleApi bleApi;
    QNUser user;
    QNBleDevice device;
    BluetoothDevice device1;
    @Bind(R.id.rl1)
    RelativeLayout rl1;
    @Bind(R.id.tv_shangcheng)
    TextView tv_shangcheng;
    @Bind(R.id.ll_health_test)
    LinearLayout llHealthTest;
    @Bind(R.id.tv_weight_rank)
    TextView tvWeightRank;
    @Bind(R.id.tv_weight)
    TextView tvWeight;
    @Bind(R.id.tv_tizhilv)
    TextView tvTizhilv;
    private BluetoothAdapter mBluetoothAdapter;
    private static final int REQUEST_ENABLE = 1;
    private PopupWindow popupWindow;
    private ArrayList<BluetoothDevice> strArr;
    private BluetoothAdapter blueToothAdapter;
    private boolean isBond = false;
    private TextView tv_cheng_content;
    private boolean isHaveBlue = false;
    private boolean testing=false;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
//            StatusBarCompat.translucentStatusBar(getActivity(), true);
//            StatusBarCompat.setStatusBarColor(activity, Color.WHITE,125);

        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tool;
    }

    @Override
    protected void initView() {
        StatusBarCompat.translucentStatusBar(getActivity(), true);
        blueToothAdapter = BluetoothAdapter.getDefaultAdapter();
        strArr = new ArrayList<>();
        //注册广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        getActivity().registerReceiver(new BluetoothReceiver(), intentFilter);

        Log.e("TTT",SharedPreferencesUtil.getValue(getActivity(),"LAST_RESULT","")+"---进来了");
      if(!(SharedPreferencesUtil.getValue(getActivity(),"LAST_RESULT","")+"").equals("")) {
          String s = (SharedPreferencesUtil.getValue(getActivity(), "LAST_RESULT", "")) + "";
//          QNData qnData1 = new QNData();
          String str[] = s.split(",");
//          for (int i = 0; i < str.length; i++) {
//              QNItemData qnItemData = new QNItemData(i, Float.parseFloat(str[i]));
//              qnData1.addItemData(qnItemData);
//          }
          try {
              tvWeight.setText(Float.parseFloat(str[0]) * 2 + "");
              tvTizhilv.setText(Float.parseFloat(str[2])  + "");
          }catch (Exception e){

          }

      }

    }

    @OnClick({R.id.rl1, R.id.tv_shangcheng, R.id.ll_health_test,R.id.ll_history})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ll_history:
                startActivity(HistoryActivity.class);
                break;
            case R.id.rl1:
                startActivity(MeasureResultActivity.class);
                break;
            case R.id.tv_shangcheng:
                //获取本地蓝牙适配器
                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                //判断是否硬件支持蓝牙
                if (mBluetoothAdapter == null) {
                    Toast.makeText(getActivity(), "本地蓝牙不可用", Toast.LENGTH_SHORT).show();
                    //退出应用
                    return;
                }

                //判断是否打开蓝牙
                if (!mBluetoothAdapter.isEnabled()) {
                    //弹出对话框提示用户是后打开
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent, REQUEST_ENABLE);
                    return;
                    //不做提示，强行打开
                    // mBluetoothAdapter.enable();
                }

                performCodeWithPermission("获取蓝牙权限", new BaseActivity.PermissionCallback() {
                    @Override
                    public void hasPermission() {
                        connect();
                    }

                    @Override
                    public void noPermission() {
                    }
                }, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_CONTACTS);
                break;
            case R.id.ll_health_test:
                Bundle bundle = new Bundle();
//                Log.e("GGG","id:"+adapter.getData().get(position).getInformationId());
                bundle.putString("id", SharedPreferencesUtil.getValue(getActivity(), "USERID", "") + "");
                bundle.putString("title", "健康测试");
                bundle.putString("type", "test");
                startActivity(RecommendActivity.class, bundle);
                break;
        }

    }

    private void connect() {
        showPopuwindow();
        search();

    }

    private void connect1(String address) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d");
        Date birthday = null;
        try {
            String bir = SharedPreferencesUtil.getValue(getActivity(), "BIRTHDAY", "") + "";
            birthday = dateFormat.parse(bir);
        } catch (Exception e) {
            Log.e("TTT", "chucuol");
        }
        String height = SharedPreferencesUtil.getValue(getActivity(), "HEIGHT", "") + "";
        String sex = SharedPreferencesUtil.getValue(getActivity(), "SEX", "") + "";
        int height1 = Integer.parseInt(height);
        int sex1 = Integer.parseInt(sex);
        QNUser user = new QNUser("1", height1, sex1, birthday);
        QNBleDevice qnBleDevice = new QNBleDevice(BluetoothAdapter.getDefaultAdapter().getRemoteDevice(address));
        testConnect(getActivity(), user, qnBleDevice);
//        startActivity(ConnectActivity.getCallIntent(getActivity(), user, qnBleDevice));

    }

    private void showPopuwindow() {
        //把包裹ListView布局的XML文件转换为view对象
        View popview = LayoutInflater.from(getActivity()).inflate(R.layout.pupuwindow_shangcheng, null);
        tv_cheng_content = popview.findViewById(R.id.tv_cheng_content);
        ImageView img_close = popview.findViewById(R.id.img_close);
        //创建Popuwindow对象,参数1pupuwindow要显示的布局,参数2.3定义Popuwindow宽和高
        popupWindow = new PopupWindow(popview, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        //设置Popuwindow外部可以点击
        popupWindow.setOutsideTouchable(false);
        //设置Popuwindow里面的ListView有焦点
        popupWindow.setFocusable(true);
        //如果想让Popuwindow有动画效果,就必须有这段代码
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isBond = false;
                if (device != null) {
                    bleApi.disconnectDevice(device.getMac());
                }

                popupWindow.dismiss();
                testing=false;
            }
        });
        //设置Popuwindow结束时监听事件
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //设置TextView的颜色,把所有LinearLayout的文本颜色该为灰色
                isBond = false;
                if (device != null) {
                    bleApi.disconnectDevice(device.getMac());
                }
                testing=false;


            }
        });
        isBond = true;
        popupWindow.showAtLocation(tv_shangcheng, Gravity.CENTER, 0, 0);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_ENABLE:
                Log.e("TTT", "进来了流量");
                performCodeWithPermission("获取蓝牙权限", new BaseActivity.PermissionCallback() {
                    @Override
                    public void hasPermission() {
                        connect();
                    }

                    @Override
                    public void noPermission() {
                    }
                }, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_CONTACTS);


//                connect();
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void addHistoryRecordSuccess(CodeBean info) {

    }

    @Override
    public void loadFail(String msg) {

    }


    class BluetoothReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(BluetoothDevice.ACTION_FOUND)) {
                Log.e("TTT", "找到新设备了");
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                boolean addFlag = true;
               /* for (BluetoothDevice bluetoothDevice : strArr) {
                    if (device.getAddress().equals(bluetoothDevice.getAddress())) {
                        addFlag = false;
                    }
                }*/
//                addFlag = true;

                if (addFlag) {
                    Log.e("TTT", "正在连接");
                    strArr.add(device);
                    if (isBond == true) {
                        if (device.getName() != null) {
                            if (device.getName().startsWith("QN")) {
                                tv_cheng_content.setText("正在连接设备");
                                device1 = device;
                                if (device.getBondState() == BluetoothDevice.BOND_NONE) {
                                    bond(device);
                                } else if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
                                    connect1(device.getAddress());
//                            sendMessage(i);
                                }
                            }
                        }
                        Log.e(getActivity().getPackageName(), "名称" + device.getName());
                    }
//                    adapter.notifyDataSetChanged();
                }
            } else if (intent.getAction().equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                switch (device.getBondState()) {
                    case BluetoothDevice.BOND_NONE:
                        Log.e(getActivity().getPackageName(), "取消配对");
                        break;
                    case BluetoothDevice.BOND_BONDING:
                        Log.e("TTT", "配对中");
                        break;
                    case BluetoothDevice.BOND_BONDED:
                        Log.e("TTT", "配对成功");
                        tv_cheng_content.setText("正在导入数据");
                        connect1(device.getAddress());

                        break;
                }

            }
        }
    }

    public void search() {

        testing=true;
        Set<BluetoothDevice> bondedDevices = blueToothAdapter.getBondedDevices();

        for (BluetoothDevice b : bondedDevices) {
            if (b.getName() != null) {
                if (b.getName().startsWith("QN")) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                Toast.makeText(MainActivity.this,"location",Toast.LENGTH_LONG).show();
                            isHaveBlue = true;
                            tv_cheng_content.setText("正在连接设备");
                        }
                    });

                    device1 = b;
                    connect1(device1.getAddress());
                    Log.e("TTT", "正在连接设备");
                }
            }
        }
//        if(isHaveBlue==false){
        if (blueToothAdapter.isDiscovering())
            blueToothAdapter.cancelDiscovery();
        blueToothAdapter.startDiscovery();
        Log.e("TTT", "开始搜索");
//        }


    }

    private void bond(BluetoothDevice i) {
        try {
            Method method = BluetoothDevice.class.getMethod("createBond");
            Log.e("TTT", "开始配对");
            method.invoke(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void testConnect(Context context, QNUser user1, QNBleDevice device1) {
        bleApi = QNApiManager.getApi(getActivity());

        user = user1;
        device = device1;
        bleApi.connectDevice(device, user.getId(), user.getHeight(), user.getGender(), user.getBirthday(), this);
    }


    @Override
    public void onConnectStart(QNBleDevice qnBleDevice) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_cheng_content.setText("正在连接，请勿离开脂肪秤");
            }
        });
        Log.e("TTT", "连接成功");
    }

    @Override
    public void onConnected(QNBleDevice qnBleDevice) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_cheng_content.setText("连接成功，请勿离开脂肪秤");
            }
        });
    }

    @Override
    public void onDisconnected(QNBleDevice qnBleDevice, int i) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_cheng_content.setText("体脂秤蓝牙被占用");
            }
        });

//        Log.e("AAA", "连接不可用");
    }

    @Override
    public void onUnsteadyWeight(QNBleDevice qnBleDevice, float weight) {
        int unit = bleApi.getWeightUnit();
        String unitString = " kg";
        if (unit == QNBleApi.WEIGHT_UNIT_LB) {
            unitString = " lb";
        } else if (unit == QNBleApi.WEIGHT_UNIT_JIN) {
            unitString = " 斤";
        }
        Log.e("TTT", "重量：" + weight + unitString);
        final String unitString1 = unitString;
        final float weight1 = weight;
//        weightTv.setText(weight + unitString);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_cheng_content.setText("正在测量，请勿离开体重秤\n重量：" + weight1 + unitString1);
            }
        });
    }

    @Override
    public void onReceivedData(QNBleDevice qnBleDevice, QNData qnData) {
        if( testing==false){
            return;
        }
        AppConstant.qnData = null;
        AppConstant.qnData = qnData;
        QNItemData data = qnData.getAll().get(9);
        if (data != null) {
            Log.e("TTT", qnData.size() + "-----------------");
            if (data.type == QNData.TYPE_BODY_SHAPE) {
                Log.e("TTT", "完成" + data.name + data.valueStr);
            } else {
                Log.e("TTT", "完成" + data.name + data.value + "");
            }

        }
        for(int i=0;i<qnData.size();i++){
            QNItemData data1 = qnData.getAll().get(i);
            if (data.type == QNData.TYPE_BODY_SHAPE) {
                Log.e("TTT", i+"完成来" + data1.name + data1.valueStr);
            } else {
                Log.e("TTT", i+"完成" + data1.name + data1.value + "");
            }

        }


        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                popupWindow.dismiss();

            }
        });
        if (data.value==0.0F) {
                    new NormalAlertDialog.Builder(getActivity())
                .setBoolTitle(false)
                .setContentText("测量方法不对，正在开发中")
                .setContentTextColor(R.color.blue)
                .setSingleModel(true)
                .setSingleText("确认")
                .setWidth(0.75f)
                .setHeight(0.33f)
                .setSingleListener(new DialogInterface.OnSingleClickListener<NormalAlertDialog>() {
                    @Override
                    public void clickSingleButton(NormalAlertDialog dialog, View view) {
                     dialog.dismiss();
                    }
                }).build().show();
            if(bleApi!=null){
                bleApi.disconnectDevice(device.getMac());
            }
            popupWindow.dismiss();
            return;
        }
        Map<String,String> map=new HashMap<>();
        map.put("userId",SharedPreferencesUtil.getValue(getActivity(),"USERID","")+"");
        map.put("weight",qnData.getFloatValue(2)+"");
        map.put("bodyFatRate",qnData.getFloatValue(4)+"");
        map.put("fat",qnData.getFloatValue(2)*qnData.getFloatValue(4)/100+"");
        map.put("visceralFat",qnData.getFloatValue(6)+"");
        map.put("moisture",qnData.getFloatValue(7)+"");
        map.put("skeletalMuscle",qnData.getFloatValue(9)+"");
        map.put("bone","0");
        map.put("muscle","0");
        map.put("protein",qnData.getFloatValue(11)+"");
        map.put("healthIndex","90");
        map.put("physicalAge","0");
        map.put("metabolism",qnData.getFloatValue(12)+"");
        map.put("BMI",qnData.getFloatValue(3)+"");
        mPresenter.addHistoryRecord(MapUtil.getMap(map));
        if(bleApi!=null){
            bleApi.disconnectDevice(device.getMac());
        }


        tvWeight.setText(qnData.getAll().get(0).value*2+"");
        tvTizhilv.setText(qnData.getAll().get(2).value+"");
        if (qnData != null) {
            StringBuilder qnDataString=new StringBuilder("");
            for(int i=0;i<10;i++){
                qnDataString.append(qnData.getAll().get(i).value+",");
            }
            qnDataString.deleteCharAt(qnDataString.length()-1);
            Log.e("TTT","测量完了："+qnDataString.toString());
            SharedPreferencesUtil.putValue(getActivity(), "LAST_RESULT", qnDataString.toString());

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
//获取当前时间
            Date date = new Date(System.currentTimeMillis());
            SharedPreferencesUtil.putValue(getActivity(), "LAST_RESULT_TIME", simpleDateFormat.format(date));
            Bundle bundle = new Bundle();
            bundle.putString("type", "new");
            startActivity(MeasureResultActivity.class, bundle);
        }
    }

    @Override
    public void onReceivedStoreData(QNBleDevice qnBleDevice, List<QNData> list) {
            Log.e("TTT","添加历史记录成功");
    }

    @Override
    public void onDeviceModelUpdate(QNBleDevice qnBleDevice) {

    }

    @Override
    public void onLowPower() {
        ToastUtil.showShortToast("脂肪秤电量过低");
    }

    @Override
    public void onCompete(int i) {

    }

}
