#include <jni.h>
#include <string.h>
#include "aes.h"
#include "base64.h"

const char *UNSIGNATURE = "UNSIGNATURE";

unsigned char *getKey() {
    int n = 0;
    char s[23];//"NMTIzNDU2Nzg5MGFiY2RlZg";

    s[n++] = 'N';
    s[n++] = 'M';
    s[n++] = 'T';
    s[n++] = 'I';
    s[n++] = 'z';
    s[n++] = 'N';
    s[n++] = 'D';
    s[n++] = 'U';
    s[n++] = '2';
    s[n++] = 'N';
    s[n++] = 'z';
    s[n++] = 'g';
    s[n++] = '5';
    s[n++] = 'M';
    s[n++] = 'G';
    s[n++] = 'F';
    s[n++] = 'i';
    s[n++] = 'Y';
    s[n++] = '2';
    s[n++] = 'R';
    s[n++] = 'l';
    s[n++] = 'Z';
    s[n++] = 'g';
    char *encode_str = s + 1;
    return b64_decode(encode_str, strlen(encode_str));

    //初版hidekey的方案
}

char* decodefun(char* str) {
    uint8_t *AES_KEY = (uint8_t *) getKey();

    char *desResult = AES_128_ECB_PKCS5Padding_Decrypt(str, AES_KEY);

    return desResult;
//不用系统自带的方法NewStringUTF是因为如果desResult是乱码,会抛出异常
//return charToJstring(env,desResult);
}
//extern "C"
JNIEXPORT jstring

JNICALL
Java_com_a360_zhangzhenguo_hack_MainActivity_stringFromJNI(
        JNIEnv *env,
        jstring str_) {
    char* hello = "Hello from C++";
    char* hack ="Ee8hYyEGcjIXbuEgGhEO++WvENp8q5/v2TVbjCYNqLY=";
    hello=decodefun(hack);
    return (*env)->NewStringUTF(env,hello);
}
JNIEXPORT jstring JNICALL
Java_com_a360_zhangzhenguo_hack_MainActivity_encode(JNIEnv *env, jobject instance, jobject context, jstring str_) {

    uint8_t *AES_KEY = (uint8_t *) getKey();
    const char *in ="test";//"native encode master hack";
     //       in=(*env)->GetStringUTFChars(env, str_, JNI_TRUE);
    if(in == NULL)
    {
        return NULL;
    }
    char *baseResult = AES_128_ECB_PKCS5Padding_Encrypt(in, AES_KEY);

    (*env)->ReleaseStringUTFChars(env,str_, in);
    return (*env)->NewStringUTF(env,baseResult);
}
JNIEXPORT jstring JNICALL
Java_com_a360_zhangzhenguo_hack_MainActivity_decode(JNIEnv *env, jobject instance, jobject context, jstring str_) {

    uint8_t *AES_KEY = (uint8_t *) getKey();
    const char *str = NULL;
            str=(*env)->GetStringUTFChars(env,str_, JNI_TRUE);
    if(str == NULL)
    {
        return NULL;
    }
    char *desResult = AES_128_ECB_PKCS5Padding_Decrypt(str, AES_KEY);
    (*env)->ReleaseStringUTFChars(env, str_, str);
    return (*env)->NewStringUTF(env,desResult);
    //不用系统自带的方法NewStringUTF是因为如果desResult是乱码,会抛出异常
    //return charToJstring(env,desResult);
}
