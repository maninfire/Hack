package com.a360.zhangzhenguo.hack;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
/**
 * Created by zhangzhenguo on 2018/4/20.
 */

public class DeviceManagerBC extends DeviceAdminReceiver {
    @Override
    public void onEnabled(Context context, Intent intent) {
        super.onEnabled(context, intent);
        System.out.println("已经注册成为系统组件");
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        super.onDisabled(context, intent);
        System.out.println("已经注销了系统组件");
    }
}
