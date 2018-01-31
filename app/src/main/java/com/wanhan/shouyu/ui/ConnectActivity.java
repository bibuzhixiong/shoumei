package com.wanhan.shouyu.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kitnew.ble.QNApiManager;
import com.kitnew.ble.QNBleApi;
import com.kitnew.ble.QNBleCallback;
import com.kitnew.ble.QNBleDevice;
import com.kitnew.ble.QNData;
import com.kitnew.ble.QNUser;
import com.wanhan.shouyu.R;

import java.util.List;

/**
 * Created by hdr on 15/12/11.
 */
public class ConnectActivity extends AppCompatActivity implements QNBleCallback {

    TextView nameTv;
    TextView macTv;
    Button connectBtn;

    TextView weightTv;
    TextView modelTv;
    TextView statusTv;
    RecyclerView reportRv;

    QNBleApi bleApi;

    QNUser user;
    QNBleDevice device;
    IndicatorAdapter indicatorAdapter;


    public static Intent getCallIntent(Context context, QNUser user, QNBleDevice device) {
        return new Intent(context, ConnectActivity.class).putExtra("user", user).putExtra("device", device);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        initViews();
        initData();
    }

    void initViews() {
        nameTv = (TextView) findViewById(R.id.nameTv);
        macTv = (TextView) findViewById(R.id.macTv);
        connectBtn = (Button) findViewById(R.id.connectBtn);

        weightTv = (TextView) findViewById(R.id.weightTv);
        modelTv = (TextView) findViewById(R.id.modelTv);
        statusTv = (TextView) findViewById(R.id.statusTv);

        reportRv = (RecyclerView) findViewById(R.id.reportRv);
        reportRv.setLayoutManager(new LinearLayoutManager(this));

        indicatorAdapter = new IndicatorAdapter(this);
        reportRv.setAdapter(indicatorAdapter);

        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connectBtn.getText().equals("连接")) {
                    doConnect();
                } else {
                    doDisconnect();
                }
            }
        });
    }

    void initData() {
        bleApi = QNApiManager.getApi(this);

        user = getIntent().getParcelableExtra("user");
        device = getIntent().getParcelableExtra("device");

        nameTv.setText(device.getDeviceName());
        macTv.setText(device.getMac());

        connectBtn.setText("连接");

        doConnect();
    }

    void doConnect() {
        bleApi.connectDevice(device, user.getId(), user.getHeight(), user.getGender(), user.getBirthday(), this);
    }

    void doDisconnect() {
        bleApi.disconnectDevice(device.getMac());
    }

    @Override
    public void onConnectStart(QNBleDevice bleDevice) {
        statusTv.setText("正在连接");
        connectBtn.setText("断开");
    }

    @Override
    public void onConnected(QNBleDevice bleDevice) {
        statusTv.setText("连接成功");
        modelTv.setText(bleDevice.getModel());
    }

    @Override
    public void onDisconnected(QNBleDevice bleDevice, int status) {
        statusTv.setText("连接已断开");
        connectBtn.setText("连接");
    }

    @Override
    public void onUnsteadyWeight(QNBleDevice bleDevice, float weight) {
        int unit = bleApi.getWeightUnit();
        String unitString = " kg";
        if (unit == QNBleApi.WEIGHT_UNIT_LB) {
            unitString = " lb";
        } else if (unit == QNBleApi.WEIGHT_UNIT_JIN) {
            unitString = " 斤";
        }
        weightTv.setText(weight + unitString);
    }

    @Override
    public void onReceivedData(QNBleDevice bleDevice, QNData data) {
        statusTv.setText("测量完成");
        indicatorAdapter.setQnData(data);
    }

    @Override
    public void onReceivedStoreData(QNBleDevice bleDevice, List<QNData> datas) {
    }

    @Override
    public void onDeviceModelUpdate(QNBleDevice bleDevice) {
        Log.d("hdr", "读取到了新的型号：" + bleDevice.getModel());
    }

    @Override
    public void onLowPower() {
        Log.d("hdr", "电池电量低");
    }


    @Override
    public void onCompete(int errorCode) {

    }
}
