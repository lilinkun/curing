package com.communication.curing.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.EditText;
import android.widget.ImageView;

import com.communication.curing.activity.LoginActivity;

public class CuringUtil {

    //将返回的base64转换成图片
    public static void setImage(String imageStr, ImageView image) {
        byte[] decode = Base64.decode(imageStr, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decode, 0, decode.length);
        image.setImageBitmap(bitmap);
    }

    public static boolean etIsnull(EditText editText){
        if (editText.getText().toString().trim().length() > 0){
            return true;
        }
        return false;
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }


    public static void otherLogin(Context context,String msg){
        if (msg.contains("您的账号已在其他客户端登录")){
            MethodsUtil.saveKeyValue(ConstantsUtil.AUTHORIZATION, "", context);
            Intent intent = new Intent(context, LoginActivity.class);
            intent.putExtra(ConstantsUtil.ACCOUNT,MethodsUtil.getValueByKey(ConstantsUtil.ACCOUNT,context));
            context.startActivity(intent);
            ActivityUtil.finishAll();
        }
    }

}
