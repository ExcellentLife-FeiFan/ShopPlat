package com.ytxd.sppm.presenter;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;

import com.ytxd.sppm.util.AbStrUtil;
import com.ytxd.sppm.util.PrintUtils;
import com.ytxd.sppm.util.SPUtil;
import com.ytxd.sppm.view.IMainView;

import java.util.Set;


/**
 * 主界面presenter
 * Created by panl on 15/12/24.
 */
public class MainPresenter extends BasePresenter<IMainView>{


    public MainPresenter(Context context, IMainView iView) {
        super(context, iView);
    }

    @Override
    public void release() {
    }

    public void setBlueToothAdapter(Context context) {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (null != adapter) {
            if (!adapter.isEnabled()) {

            } else {
                Set<BluetoothDevice> devices = adapter.getBondedDevices();
                if (devices.size() > 0) {
                    String cB= SPUtil.getInstance().getString("bluetoothaddress");
                    if(!AbStrUtil.isEmpty(cB)){
                        for (BluetoothDevice device : devices) {
                            if (device.getAddress().equals(cB)) {
                                PrintUtils.bluetoothDevice=device;
                                break;
                            }
                        }
                    }

                }
            }
        }

    }



}
