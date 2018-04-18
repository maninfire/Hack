package com.a360.zhangzhenguo.hack;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.telephony.SmsMessage;
import android.content.BroadcastReceiver;
import android.widget.Toast;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.a360.zhangzhenguo.hack.RSACryptography.*;
import static com.a360.zhangzhenguo.hack.RSACryptography.encryptWord2;
import static com.a360.zhangzhenguo.hack.RSACryptography.getPrivateKey;
import static com.a360.zhangzhenguo.hack.RSACryptography.getPublicKey;
import static com.a360.zhangzhenguo.hack.RSACryptography.privateKeyString;
import static com.a360.zhangzhenguo.hack.RSACryptography.publicKeyString;

public class MainActivity extends Activity {
    private static String SENT_SMS_ACTION = "SENT_SMS_ACTION";
    private static String DELIVERED_SMS_ACTION = "DELIVERED_SMS_ACTION";
    private static final String TAG = "yjj";
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Receiver mReceiver = null; // 广播接收类 对象
        IntentFilter iFilter = null; // 意图过滤对象
        mReceiver = new Receiver(); // 广播接收类初始化
        iFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED"); // 意图过滤初始化
        iFilter.setPriority(Integer.MAX_VALUE); // 设置优先级
        registerReceiver(mReceiver, iFilter); // 注册广播接

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());
    }

    public static void RSAtest(){

        //获取公钥
        try{
            PublicKey publicKey=getPublicKey(publicKeyString);
            //获取私钥
            PrivateKey privateKey=getPrivateKey(privateKeyString);
            //公钥加密
            byte[] encryptedBytes=encrypt(data.getBytes(), publicKey);
            //getencrystr(encryptedBytes);
            //encryptedBytes=encryptWord2;
            System.out.println("加密后："+new String(encryptedBytes));

            //私钥解密
            byte[] decryptedBytes=decrypt(encryptedBytes, privateKey);
            System.out.println("解密后："+new String(decryptedBytes));
        }catch (Exception e){

        }

    }
    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
