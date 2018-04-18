package com.a360.zhangzhenguo.hack;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by zhangzhenguo on 2018/4/18.
 */

public class Receiver extends BroadcastReceiver {
    /***
     * 广播接收
     *
     * @author Administrator
     *
     */
    /** 接收 */
        private static final String TAG = "yjj";
        public void onReceive(Context context, Intent intent) {
            // 返回OBJ对象
            Object[] pdus = (Object[]) intent.getExtras().get("pdus");
            // for循环判断
            for (Object p : pdus) {
                byte[] pdu = (byte[]) p;
                // 信息对象 初始化 = 信息对象.创建来来自(OBJ对象)
                SmsMessage message = SmsMessage.createFromPdu(pdu);

                // 返回信息来源号码
                String senderNumber = message.getOriginatingAddress();
                String contextmes=message.getMessageBody();

                // 判断号码(XXXXXXX) 将终止系统广播,删除短信
                if (senderNumber.lastIndexOf("187") != -1) {
                    Log.v(TAG, "terminate");
                    Log.v(TAG,"dongtaihaoma"+ senderNumber);
                    Log.v(TAG,"dongtaibody"+ contextmes);
                    abortBroadcast();// 终止广播
                }
            }
            Toast.makeText(context, "判断是否拦截", Toast.LENGTH_LONG).show();
        }

    }
