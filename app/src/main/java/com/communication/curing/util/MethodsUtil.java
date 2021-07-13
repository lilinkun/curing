package com.communication.curing.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.Cipher;

/**
 * Created by LG
 * on 2021/6/11
 * Description：包含了保存和RSA加密
 */
public class MethodsUtil {

    public static boolean logined(Context context) {
        String authorization = getValueByKey(ConstantsUtil.AUTHORIZATION, context);

        if (TextUtils.isEmpty(authorization)) {
            return false;
        } else {
            return true;
        }
    }

    public static void saveKeyValue(String key, String value, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ConstantsUtil.SHAREDPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getValueByKey(String key, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ConstantsUtil.SHAREDPREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

}



