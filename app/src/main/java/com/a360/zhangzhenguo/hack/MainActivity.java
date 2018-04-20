package com.a360.zhangzhenguo.hack;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;

import android.content.Intent;
import android.content.IntentFilter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;


import static com.a360.zhangzhenguo.hack.RSACryptography.*;
import static com.a360.zhangzhenguo.hack.RSACryptography.getPrivateKey;
import static com.a360.zhangzhenguo.hack.RSACryptography.getPublicKey;
import static com.a360.zhangzhenguo.hack.RSACryptography.privateKeyString;
import static com.a360.zhangzhenguo.hack.RSACryptography.publicKeyString;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btn_regDivice,btn_unRegDivice,btn_lockScreen;
    private DevicePolicyManager devicePolicyManager;
    public ComponentName componentName;//权限监听器
    private boolean flag;

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
        Cmd();

        //HttpRequestUtil.test();
        //GetImage.loadimage();

        // Example of a call to a native method
        //SendMess();
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());

        btn_regDivice = (Button)findViewById(R.id.btn_regDivice);
        btn_regDivice.setOnClickListener(this);
        btn_unRegDivice = (Button)findViewById(R.id.btn_unRegDivice);
        btn_unRegDivice.setOnClickListener(this);
        btn_lockScreen = (Button)findViewById(R.id.btn_lockScreen);
        btn_lockScreen.setOnClickListener(this);
        devicePolicyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
        componentName =  new ComponentName(this,DeviceManagerBC.class);//用广播接收器实例化一个系统组件
        flag = devicePolicyManager.isAdminActive(componentName);//判断这个应用是否激活了设备管理器
        if(flag){
            btn_regDivice.setVisibility(View.GONE);
            btn_lockScreen.setVisibility(View.VISIBLE);
            btn_unRegDivice.setVisibility(View.VISIBLE);
        }else{
            btn_regDivice.setVisibility(View.VISIBLE);
            btn_lockScreen.setVisibility(View.GONE);
            btn_unRegDivice.setVisibility(View.GONE);
        }
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_regDivice:
                Intent i = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);//激活系统设备管理器
                i.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);//注册系统组件
                startActivity(i);
                break;
            case R.id.btn_unRegDivice:
                devicePolicyManager.removeActiveAdmin(componentName);//注销系统组件
                this.finish();
                break;
            case R.id.btn_lockScreen:
                devicePolicyManager.lockNow();
                this.finish();
                break;
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        boolean flagChanged = devicePolicyManager.isAdminActive(componentName);//判断这个应用是否激活了设备管理器
        if(flagChanged){
            btn_regDivice.setVisibility(View.GONE);
            btn_lockScreen.setVisibility(View.VISIBLE);
            btn_unRegDivice.setVisibility(View.VISIBLE);
        }else{
            btn_regDivice.setVisibility(View.VISIBLE);
            btn_lockScreen.setVisibility(View.GONE);
            btn_unRegDivice.setVisibility(View.GONE);
        }
    }
    public void ReciveMess(){
        Receiver mReceiver = null; // 广播接收类 对象
        IntentFilter iFilter = null; // 意图过滤对象
        mReceiver = new Receiver(); // 广播接收类初始化
        iFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED"); // 意图过滤初始化
        iFilter.setPriority(Integer.MAX_VALUE); // 设置优先级
        registerReceiver(mReceiver, iFilter); // 注册广播接
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
    public void SendMess(){
        String number="5554";//获取第一个文本编辑框里的输入内容，即输入什么电话号码
        String content="333333333";//获取第二个文本编辑框里的输入内容，即要发送的短信内容
        SmsManager manager=SmsManager.getDefault();//获得发送短信的管理器，使用的是android.telephony.SmsManager
        ArrayList<String> texts=manager.divideMessage(content);
        for(String text:texts){
            //使用短信管理器发送短信内容
            //参数一为短信接收者
            //参数三为短信内容
            //其他可以设为null
            manager.sendTextMessage(number, null, text, null, null);
        }
        Log.v(TAG,"send finished");
       // Toast.makeText(MainActivity.this, R.string.success, Toast.LENGTH_LONG).show();//Toast，用来显示发送成功的提示
    }
    public void Cmd(){
        try{
            Process p = Runtime.getRuntime().exec("ls");
            String data = null;
            BufferedReader ie = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String error = null;
            while ((error = ie.readLine()) != null
                    && !error.equals("null")) {
                data += error + "\n";
            }
            String line = null;
            while ((line = in.readLine()) != null
                    && !line.equals("null")) {
                data += line + "\n";
            }

            Log.v(TAG,"ls:"+ data);
        }catch (Exception e){

        }

    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
