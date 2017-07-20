package com.ytxd.sppm.ui.activity.main;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.kennyc.view.MultiStateView;
import com.ytxd.sppm.R;
import com.ytxd.sppm.base.AppManager;
import com.ytxd.sppm.base.BaseActivity;
import com.ytxd.sppm.model.OrderM;
import com.ytxd.sppm.ui.adapter.ConnectBlueToothAdapterLV;
import com.ytxd.sppm.util.CommonUtils;
import com.ytxd.sppm.util.PrintUtils;
import com.ytxd.sppm.util.SPUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BlueToothPrintActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    ConnectBlueToothAdapterLV mAdapter;
    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.msv)
    MultiStateView msv;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    OrderM order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_tooth_print);
        ButterKnife.bind(this);
        getBar().initActionBar("选择打印设备", "", "设置蓝牙", R.drawable.ic_back_white, -1, this);
        order = (OrderM) getIntent().getSerializableExtra("data");
        swipeLayout.setOnRefreshListener(this);
        mAdapter = new ConnectBlueToothAdapterLV(new ArrayList<BluetoothDevice>(), this);
        lv.setAdapter(mAdapter);
        setData();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                PrintUtils.bluetoothDevice = mAdapter.getItem(position);
                mAdapter.notifyDataSetChanged();
                SPUtil.getInstance().putString("bluetoothaddress", PrintUtils.bluetoothDevice.getAddress());
                if (null != order) {
                    if (mAdapter.getItem(position).getBondState() == BluetoothDevice.BOND_BONDED) {
                        lv.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                CommonUtils.printOrder(mAdapter.getItem(position), activity, order);
                            }
                        }, 10);
                    } else {
                        showToast("连接失败");
                    }

                }

            }
        });
    }

    private void setData() {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (null != adapter) {
            if (!adapter.isEnabled()) {
                showToast("未开启蓝牙，请设置蓝牙");
                Intent mIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(mIntent, 1);
            } else {
                Set<BluetoothDevice> devices = adapter.getBondedDevices();
                if (devices.size() > 0) {
                    List<BluetoothDevice> deviceList = new ArrayList<>();
                    for (BluetoothDevice device : devices) {
                        deviceList.add(device);
                    }
                    mAdapter.addItems(deviceList, true);
                } else {
                    showToast("没有连接的设备");
                    Intent mIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivity(mIntent);
                }
            }
        } else {
            showToast("该设备不支持蓝牙");
        }
        swipeLayout.setRefreshing(false);
        setEmpty();
    }

    private void setEmpty() {
        if (mAdapter.getCount() > 0) {
            msv.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        } else {
            msv.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                AppManager.getInstance().killActivity(this);
                break;
            case R.id.tv_right:
                startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS));
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "蓝牙已经开启", Toast.LENGTH_SHORT).show();
                setData();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "不允许蓝牙开启", Toast.LENGTH_SHORT).show();
                AppManager.getInstance().killActivity(this);
            }
        }
    }

    @Override
    public void onRefresh() {
        setData();
    }
}
