package com.wanhan.shouyu.ui;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kitnew.ble.QNApiManager;
import com.kitnew.ble.QNBleApi;
import com.kitnew.ble.QNBleDevice;
import com.kitnew.ble.QNBleScanCallback;
import com.kitnew.ble.QNUser;
import com.kitnew.ble.utils.QNLog;
import com.wanhan.shouyu.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 */

public class ScanActivity  extends AppCompatActivity implements View.OnClickListener {
    QNBleApi qnBleApi;

    RecyclerView recyclerView;
    Button scanBtn;
    RadioGroup sexRg;
    RadioGroup scanModeRg;
    RadioGroup steadyEnableRg;
    RadioGroup unitRg;
    EditText idEt;
    EditText heightEt;
    EditText birthdayEt;
    CheckBox storageModeCb;

    final List<QNBleDevice> devices = new ArrayList<>();
    DeviceListAdapter deviceListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scan);

//        initViews();
        initApi();

//        QNUser user = this.buildUser();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d");
        Date birthday = null;
        try {
            birthday = dateFormat.parse("1993-9-8");
        } catch (Exception e) {

        }
        QNUser user = new QNUser("1", 180, 1, birthday);
        QNBleDevice qnBleDevice = new QNBleDevice(BluetoothAdapter.getDefaultAdapter().getRemoteDevice("E2:43:E5:4D:1B:D7"));
        startActivity(ConnectActivity.getCallIntent(this, user, qnBleDevice));
    }


    void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        deviceListAdapter = new DeviceListAdapter();
        recyclerView.setAdapter(deviceListAdapter);

        scanBtn = (Button) findViewById(R.id.scanBtn);
        scanBtn.setOnClickListener(this);

        idEt = (EditText) findViewById(R.id.idEt);
        heightEt = (EditText) findViewById(R.id.heightEt);
        birthdayEt = (EditText) findViewById(R.id.birthdayEt);

        sexRg = (RadioGroup) findViewById(R.id.sexRG);
        scanModeRg = (RadioGroup) findViewById(R.id.scanMode);
        steadyEnableRg = (RadioGroup) findViewById(R.id.steadyEnable);

        unitRg = (RadioGroup) findViewById(R.id.measureUnit);

        storageModeCb = (CheckBox) findViewById(R.id.storageModeCb);

        int unit = QNApiManager.getApi(getBaseContext()).getWeightUnit();
        int resId = R.id.kg;
        switch (unit) {
            case QNBleApi.WEIGHT_UNIT_LB:
                resId = R.id.lb;
                break;
            case QNBleApi.WEIGHT_UNIT_JIN:
                resId = R.id.jin;
                break;
        }
        unitRg.check(resId);

        unitRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.kg:
                        QNApiManager.getApi(getBaseContext()).setWeightUnit(QNBleApi.WEIGHT_UNIT_KG);
                        break;
                    case R.id.lb:
                        QNApiManager.getApi(getBaseContext()).setWeightUnit(QNBleApi.WEIGHT_UNIT_LB);
                        break;
                    case R.id.jin:
                        QNApiManager.getApi(getBaseContext()).setWeightUnit(QNBleApi.WEIGHT_UNIT_JIN);
                        break;
                }
            }
        });
    }

    void initApi() {
        qnBleApi = QNApiManager.getApi(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scanBtn:
                if (qnBleApi.isScanning()) {
                    doStopScan();
                } else {
                    doStartScan();
                }
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        doStopScan();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        qnBleApi.disconnectAll();
    }

    void doStopScan() {
        if (!qnBleApi.isScanning()) {
            return;
        }
        scanBtn.setText("开始扫描");
        qnBleApi.stopScan();

    }

    void doStartScan() {
        if (qnBleApi.isScanning()) {
            return;
        }
        scanBtn.setText("停止扫描");
        devices.clear();
        deviceListAdapter.notifyDataSetChanged();
        int scanMode = scanModeRg.getCheckedRadioButtonId() == R.id.scanModeFirst ? QNBleApi.SCAN_MODE_FIRST : QNBleApi.SCAN_MODE_ALL;
        //设置扫描模式，如无特殊需要，不需要设置
        qnBleApi.setScanMode(scanMode);
        boolean isEnable = steadyEnableRg.getCheckedRadioButtonId() == R.id.steadyOpen ? true : false;
        qnBleApi.setSteadyBodyfat(isEnable);
        qnBleApi.startLeScan(null, null, new QNBleScanCallback() {
            @Override
            public void onCompete(int errorCode) {
                Log.i("hdr-ble", "执行结果:" + errorCode);
            }

            @Override
            public void onScan(QNBleDevice bleDevice) {
                QNLog.log("hdr-ble", "name:" + bleDevice.getDeviceName() + " mac:" + bleDevice.getMac()
                        + " model:" + bleDevice.getModel() + " 是否开机:" + bleDevice.getDeviceState());
                devices.add(bleDevice);
                deviceListAdapter.notifyItemInserted(devices.size() - 1);
            }
        });
        hideInput();

    }

    public void hideInput() { // 不显示键盘
        View v = this.getCurrentFocus();
        if (v != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0); // 强制隐藏键盘
        }
    }

    class DeviceListAdapter extends RecyclerView.Adapter<ScanActivity.DeviceViewHolder> {

        LayoutInflater inflater;

        DeviceListAdapter() {
            inflater = LayoutInflater.from(ScanActivity.this);
        }

        @Override
        public DeviceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = inflater.inflate(R.layout.device_list_item, parent, false);
            return new DeviceViewHolder(v);
        }

        @Override
        public void onBindViewHolder(DeviceViewHolder holder, int position) {
            holder.init(devices.get(position));
        }

        @Override
        public int getItemCount() {
            return devices.size();
        }
    }

    void doConnect(QNBleDevice qnBleDevice) {
        Log.e("ttt", "进来了");
        QNUser user = buildUser();
        if (user == null) {
            return;
        }
        startActivity(ConnectActivity.getCallIntent(this, user, qnBleDevice));
    }

    QNUser buildUser() {
        String id = idEt.getText().toString();
        String errorString = null;
        Date birthday = null;
        if (id.trim().equals("")) {
            errorString = "请填写有效的用户id";
        } else if (heightEt.getText().length() == 0) {
            errorString = "请填写有效的身高";
        } else {
            String birthdayString = birthdayEt.getText().toString();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d");
            try {
                birthday = dateFormat.parse(birthdayString);
            } catch (Exception e) {
                errorString = "请按照 yyyy-M-d 的格式输入生日";
            }
        }

        if (errorString != null) {
            Toast.makeText(this, errorString, Toast.LENGTH_SHORT).show();
            return null;
        }
        int height = Integer.parseInt(heightEt.getText().toString());
        int gender;
        if (sexRg.getCheckedRadioButtonId() == R.id.sexMan) {
            gender = 1;
        } else {
            gender = 0;
        }

        return new QNUser(id, height, gender, birthday);

    }

    class DeviceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nameTv;
        TextView macTv;
        Button connectBtn;

        public DeviceViewHolder(View itemView) {
            super(itemView);
            nameTv = (TextView) itemView.findViewById(R.id.nameTv);
            macTv = (TextView) itemView.findViewById(R.id.macTv);
            connectBtn = (Button) itemView.findViewById(R.id.connectBtn);
            connectBtn.setText("连接");
            connectBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int storageMode = storageModeCb.isChecked() ? QNBleApi.RECEIVE_STORAGE_DATA : QNBleApi.IGNORE_STORAGE_DATA;
            //设置是否接收存储数据，如无特殊需要，不需要设置
            qnBleApi.setReceiveOrIgnoreStorageData(storageMode);
            doStopScan();

            final QNBleDevice device = devices.get(getAdapterPosition());

            //停止扫描后，最后延时一会儿再做连接操作
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doConnect(device);
                }
            }, 150);
        }

        void init(QNBleDevice device) {
            nameTv.setText(device.getModel() + "  " + (device.getDeviceState() == QNBleDevice.DEVICE_STATE_ON ? "开机" : "关机"));
            macTv.setText(device.getMac());
        }
    }
}
