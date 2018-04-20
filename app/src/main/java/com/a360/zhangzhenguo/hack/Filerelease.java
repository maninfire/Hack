package com.a360.zhangzhenguo.hack;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by zhangzhenguo on 2018/4/19.
 */

public class Filerelease extends Application {

    private static final String appkey = "APPLICATION_CLASS_NAME";
    private String apkFileName;
    private String odexPath;
    private String libPath;
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        try {
            //创建两个文件夹payload_odex，payload_lib 私有的，可写的文件目录
            File odex = this.getDir("payload_odex", MODE_PRIVATE);
            File libs = this.getDir("payload_lib", MODE_PRIVATE);
            odexPath = odex.getAbsolutePath();
            libPath = libs.getAbsolutePath();
            apkFileName = Environment.getExternalStorageDirectory().getPath()+"/payload.apk";
            File dexFile = new File(apkFileName);
            Log.i("demo", "apk size:"+dexFile.length());
           // if (!dexFile.exists())
           // {
                //dexFile.createNewFile();  //在payload_odex文件夹内，创建payload.apk
                // 读取程序classes.dex文件
                byte[] apkdata = this.readDexFileFromApk();

                // 分离出解壳后的apk文件已用于动态加载
                this.decrypt(apkdata);
                writetodisk(dexFile,apkdata);
                //this.decrypt(dexdata);
                Log.v("yjj","decrypt");
           // }
        } catch (Exception e) {
            Log.i("demo", "error:"+Log.getStackTraceString(e));
            e.printStackTrace();
        }
    }
    public void writetodisk(File file,byte[] data){
        try{
            FileOutputStream fops = new FileOutputStream(file);
            fops.write(data);
            fops.flush();
            fops.close();
            System.out.println("文件释放位置"+file.getAbsolutePath());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private byte[] readDexFileFromApk() throws IOException {
        ByteArrayOutputStream dexByteArrayOutputStream = new ByteArrayOutputStream();
        ZipInputStream localZipInputStream = new ZipInputStream(
                new BufferedInputStream(new FileInputStream(
                        this.getApplicationInfo().sourceDir)));
        while (true) {
            ZipEntry localZipEntry = localZipInputStream.getNextEntry();
            if (localZipEntry == null) {
                localZipInputStream.close();
                break;
            }
            Log.v("yjj",localZipEntry.getName());
            //if (localZipEntry.getName().equals("lib/x86/libnative-lib.so")) {
            if (localZipEntry.getName().equals("lib/x86/new.so")) {
                Log.v("yjj","found target encrypted");
                byte[] arrayOfByte = new byte[1024];
                while (true) {
                    int i = localZipInputStream.read(arrayOfByte);
                    if (i == -1)
                        break;
                    dexByteArrayOutputStream.write(arrayOfByte, 0, i);
                }
            }
            localZipInputStream.closeEntry();
        }

        localZipInputStream.close();
        return dexByteArrayOutputStream.toByteArray();
    }
    private byte[] decrypt(byte[] srcdata) {
        for(int i=0;i<srcdata.length;i++){
            srcdata[i] = (byte)(0xFF ^ srcdata[i]);
        }
        return srcdata;
    }
}
