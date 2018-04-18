package com.a360.zhangzhenguo.hack;

import android.util.Base64;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;



import javax.crypto.Cipher;

/**
 * Created by zhangzhenguo on 2018/4/18.
 */

public class RSACryptography {
    public static String data="hello world";
    //public static String data="hack 360 samples by zhang034";
    public static String publicKeyString="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCISLP98M/56HexX/9FDM8iuIEQozy6kn2JMcbZS5/BhJ+U4PZIChJfggYlWnd8NWn4BYr2kxxyO8Qgvc8rpRZCkN0OSLqLgZGmNvoSlDw80UXq90ZsVHDTOHuSFHw8Bv//B4evUNJBB8g9tpVxr6P5EJ6FMoR/kY2dVFQCQM4+5QIDAQAB";
    public static String privateKeyString="MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIhIs/3wz/nod7Ff/0UMzyK4gRCjPLqSfYkxxtlLn8GEn5Tg9kgKEl+CBiVad3w1afgFivaTHHI7xCC9zyulFkKQ3Q5IuouBkaY2+hKUPDzRRer3RmxUcNM4e5IUfDwG//8Hh69Q0kEHyD22lXGvo/kQnoUyhH+RjZ1UVAJAzj7lAgMBAAECgYAVh26vsggY0Yl/Asw/qztZn837w93HF3cvYiaokxLErl/LVBJz5OtsHQ09f2IaxBFedfmy5CB9R0W/aly851JxrI8WAkx2W2FNllzhha01fmlNlOSumoiRF++JcbsAjDcrcIiR8eSVNuB6ymBCrx/FqhdX3+t/VUbSAFXYT9tsgQJBALsHurnovZS1qjCTl6pkNS0V5qio88SzYP7lzgq0eYGlvfupdlLX8/MrSdi4DherMTcutUcaTzgQU20uAI0EMyECQQC6il1Kdkw8Peeb0JZMHbs+cMCsbGATiAt4pfo1b/i9/BO0QnRgDqYcjt3J9Ux22dPYbDpDtMjMRNrAKFb4BJdFAkBMrdWTZOVc88IL2mcC98SJcII5wdL3YSeyOZto7icmzUH/zLFzM5CTsLq8/HDiqVArNJ4jwZia/q6Fg6e8KO2hAkB0EK1VLF/ox7e5GkK533Hmuu8XGWN6I5bHnbYd06qYQyTbbtHMBrFSaY4UH91Qwd3u9gAWqoCZoGnfT/o03V5lAkBqq8jZd2lHifey+9cf1hsHD5WQbjJKPPIb57CK08hn7vUlX5ePJ02Q8AhdZKETaW+EsqJWpNgsu5wPqsy2UynO";


    public static byte[] encryptWords={117,60,53,99,83,-6,-11,56,105,23,39,-71,10,-41,102,-14,35,-71,83,9,83,51,8,-107,10,-86,-65,39,96,76,64,-40,55,76,-22,123,21,-20,-118,-27,-106,107,61,103,23,100,53,126,5,52,-18,-96,33,126,39,-72,-117,26,28,-105,61,-56,117,0,-8,-88,-126,-1,-67,56,-89,-112,90,96,-81,53,-81,2,-25,-13,20,93,-92,37,-33,87,58,37,120,-19,127,33,-4,-84,-87,-101,94,99,127,-97,15,29,29,20,-90,115,-25,-42,23,-49,59,40,29,-36,109,-13,-17,24,3,-4,32,45,-107,-123,43,-17,-118,28,};
    public static byte[] encryptWord2={10,9,44,42,74,25,-35,-18,-112,-39,-11,127,-9,40,77,-128,72,47,31,-45,-17,21,33,45,-5,-123,-120,20,-113,-49,109,85,-16,42,50,-38,63,123,84,-80,-33,85,2,-86,-93,85,-128,94,0,104,-20,-115,88,-16,124,-54,-95,0,57,-116,-55,49,107,127,-31,-47,93,-112,76,2,82,-101,123,97,-31,-73,-97,70,-14,-54,61,-127,112,-74,48,-118,-111,6,-34,80,-66,-63,23,57,-123,-46,-36,57,85,-95,50,-105,9,69,110,35,-5,120,-66,77,120,19,3,-94,68,-7,-110,92,-74,85,-92,-13,-52,-40,-107,48,4,-121,};

    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub


        //获取公钥
        PublicKey publicKey=getPublicKey(publicKeyString);

        //获取私钥
        PrivateKey privateKey=getPrivateKey(privateKeyString);

        //公钥加密
        byte[] encryptedBytes=encrypt(data.getBytes(), publicKey);
        System.out.println("加密后："+new String(encryptedBytes));

        //私钥解密
        byte[] decryptedBytes=decrypt(encryptedBytes, privateKey);
        System.out.println("解密后："+new String(decryptedBytes));
    }

    //将base64编码后的公钥字符串转成PublicKey实例
    public static PublicKey getPublicKey(String publicKey) throws Exception{
        byte[ ] keyBytes= android.util.Base64.decode(publicKey.getBytes(),android.util.Base64.DEFAULT);
        X509EncodedKeySpec keySpec=new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory=KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    //将base64编码后的私钥字符串转成PrivateKey实例
    public static PrivateKey getPrivateKey(String privateKey) throws Exception{
        byte[ ] keyBytes=  android.util.Base64.decode(privateKey.getBytes(),android.util.Base64.DEFAULT);
        PKCS8EncodedKeySpec keySpec=new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory=KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    //公钥加密
    public static byte[] encrypt(byte[] content, PublicKey publicKey) throws Exception{
        Cipher cipher=Cipher.getInstance("RSA");//java默认"RSA"="RSA/ECB/PKCS1Padding"
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(content);
    }

    //私钥解密
    public static byte[] decrypt(byte[] content, PrivateKey privateKey) throws Exception{
        Cipher cipher=Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(content);
    }
    public static void generateKey() throws Exception {
        // TODO Auto-generated method stub

        KeyPair keyPair=genKeyPair(1024);

        //获取公钥，并以base64格式打印出来
        PublicKey publicKey=keyPair.getPublic();
        System.out.println("公钥："+new String(Base64.encode(publicKey.getEncoded(),Base64.DEFAULT)));

        //获取私钥，并以base64格式打印出来
        PrivateKey privateKey=keyPair.getPrivate();
        System.out.println("私钥："+new String(Base64.encode(privateKey.getEncoded(),Base64.DEFAULT)));

        //公钥加密
        byte[] encryptedBytes=encrypt(data.getBytes(), publicKey);
        System.out.println("加密后："+new String(encryptedBytes));

        //私钥解密
        byte[] decryptedBytes=decrypt(encryptedBytes, privateKey);
        System.out.println("解密后："+new String(decryptedBytes));
    }

    //生成密钥对
    public static KeyPair genKeyPair(int keyLength) throws Exception{
        KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        return keyPairGenerator.generateKeyPair();
    }


    public static void getencrystr(byte[] encrypteBytes){
        System.out.print("加密后：{");
        for(int i=0;i<encrypteBytes.length;i++){
            System.out.print(encrypteBytes[i]+",");
        }
        System.out.print("}");
    }
}
