package com.wanhan.shouyu.ui;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.wanhan.shouyu.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2018/2/6.
 */

public class TsstBlueActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "MainActivity";
    // 本地蓝牙适配器
    private BluetoothAdapter mBluetoothAdapter;
    // 搜索按钮
    private Button btn_search;
    //搜索到蓝牙显示列表
    private ListView mylistview;
    // listview的adapter
    private ArrayAdapter<String> mylistAdapter;
    // 存储搜索到的蓝牙
    private List<String> bluetoothdeviceslist = new ArrayList<String>();

    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_blue);
        checkBluetoothPermission();
        initview();
        SearchBluetoot();

    }

    private void initview() {
        mylistview = (ListView) findViewById(R.id.mylistview);
        btn_search = (Button) findViewById(R.id.btn_search);

        // 获取本地蓝牙适配器
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBluetoothAdapter.startDiscovery();
                setTitle("正在搜索...");
                // 判断是否在搜索,如果在搜索，就取消搜索
                if (mBluetoothAdapter.isDiscovering()) {
                    mBluetoothAdapter.cancelDiscovery();
                }
                // 开始搜索
//                mBluetoothAdapter.startDiscovery();
            }
        });

    }

    public void SearchBluetoot() {
        // 判断有没有蓝牙硬件支持
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "设备不支持蓝牙", Toast.LENGTH_SHORT).show();
            finish();
        }

        // 判断是否打开了蓝牙
        if (!mBluetoothAdapter.isEnabled()) {
            // 我们通过startActivityForResult()方法发起的Intent将会在onActivityResult()回调方法中获取用户的选择，比如用户单击了Yes开启，
            // 那么将会收到RESULT_OK的结果，
            // 如果RESULT_CANCELED则代表用户不愿意开启蓝牙
            // 弹出对话框提示用户是否打开
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, 1);
            // 没有提示强制打开蓝牙(用enable()方法来开启，无需询问用户(实惠无声息的开启蓝牙设备),这时就需要用到android.permission.BLUETOOTH_ADMIN权限)
            // mBluetoothAdapter.enable();

            // 获的已经配对的设备
            Set<BluetoothDevice> myDevices = mBluetoothAdapter.getBondedDevices();
            /// 判断是否有配对过的设备
            if (myDevices.size() > 0) {
                for (BluetoothDevice device : myDevices) {
                    // 遍历到列表中
                    bluetoothdeviceslist.add(device.getName() + ":" + device.getAddress() + "\n");
                }
            }
// adapter
            mylistAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, bluetoothdeviceslist);
            mylistview.setAdapter(mylistAdapter);

            // 找到设备的广播
            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            // 注册广播
            registerReceiver(myreceiver, filter);
            // 搜索完成的广播
            filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
            // 注册广播
            registerReceiver(myreceiver, filter);
        }
    }

    // 广播接收器
    private final BroadcastReceiver myreceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 收到的广播类型
            String action = intent.getAction();
            // 发现设备的广播
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // 从intent中获取设备
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // 判断是否配对过
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    // 添加到列表
                    bluetoothdeviceslist.add(device.getName() + ":"+ device.getAddress() + "\n");
                    mylistAdapter.notifyDataSetChanged();
                }
                // 搜索完成
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                setTitle("搜索完成！");
            }
        }
    };

    /*
           校验蓝牙权限
          */
    private void checkBluetoothPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Android M Permission check
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},PERMISSION_REQUEST_COARSE_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}

