package com.communication.curing.http;


import com.communication.curing.entity.AccountEntity;
import com.communication.curing.entity.ResponseData;
import com.communication.curing.entity.UpdateBean;
import com.communication.curing.entity.UpdateVersionBean;
import com.communication.curing.http.convert.JsonConvert;
import com.lzy.okgo.OkGo;
import com.lzy.okrx2.adapter.ObservableBody;

import org.json.JSONObject;

import java.util.HashMap;

import io.reactivex.Observable;

/**
 * author : liguo
 * date : 2021/2/26 11:33
 * description :
 */
public class CuringService {

//    public static final String PREFIX = "http://192.168.1.210:8070";  //凯
//    public static final String PREFIX = "http://192.168.1.124:8070";  //敏
    public static final String PREFIX = "http://192.168.1.205:8070";  //文
//    public static final String PREFIX = "http://192.168.40.86:8096";
    public static String EXITVENUEORDER = PREFIX + "/life-service/serviceVenueOrder/modifyOrderStatus";

    public static String GETVCODE = PREFIX + "/captchaImage";
//    public static String GETVCODE = PREFIX + "/prod-api/captchaImage";
    public static String LOGIN = PREFIX + "/login";
//    public static String LOGIN = PREFIX + "/prod-api/login";

    public static final String H5 = "http://192.168.1.205:8070/lwjc/indicators/statistics/mileage/areaDensity?type=1";
//    public static final String H5 = "http://192.168.1.204:8088";


    public static String UPDATEURL = "https://www.pgyer.com/apiv2/app/check";

//    public static String UPDATEVERSIONURL = "http://192.168.56.1:8080/liguo/liguo.html";
    public static String UPDATEVERSIONURL = PREFIX + "/app/version/check";
//    public static String UPDATEVERSIONURL = PREFIX + "/prod-api/app/version/check";


    /**
     * 获取登录用户信息
     */
    public static String GETACCOUNT = PREFIX + "/account";
    /**
     *修改个人信息
     */
    public static String MODIFYPERSONALINFO = PREFIX + "/persons";


    /**
     * 更新apk
     */
    public static Observable<ResponseData<UpdateBean>> updateApk(){

        return OkGo.<ResponseData<UpdateBean>>get(UPDATEURL)
                .params("_api_key", "ea6f638543a55aaef96b9abb39ab1c2e")
                .params("appKey", "d2db71f43bb6e9424a3cf3ac81a50dc6")
                .converter(new JsonConvert<ResponseData<UpdateBean>>() {
                })
                .adapt(new ObservableBody<ResponseData<UpdateBean>>());
    }

    /**
     * 更新apk
     */
    public static Observable<ResponseData<UpdateVersionBean>> updateVersionApk(){

        return OkGo.<ResponseData<UpdateVersionBean>>post(UPDATEVERSIONURL)
                .params("appName", "yh_android")
                .converter(new JsonConvert<ResponseData<UpdateVersionBean>>() {
                })
                .adapt(new ObservableBody<ResponseData<UpdateVersionBean>>());
    }

    /**
     * 获取登录用户信息
     */
    public static Observable<ResponseData<AccountEntity>> getAccount(){

        return OkGo.<ResponseData<AccountEntity>>get(GETACCOUNT)
                .converter(new JsonConvert<ResponseData<AccountEntity>>(){
                })
                .adapt(new ObservableBody<ResponseData<AccountEntity>>());
    }

    /**
     * 修改个人信息
     */
    public static Observable<ResponseData<Object>> modifyPersonInfo(JSONObject jsonObject){

        return OkGo.<ResponseData<Object>>put(MODIFYPERSONALINFO)
                .upJson(jsonObject)
                .converter(new JsonConvert<ResponseData<Object>>(){
                })
                .adapt(new ObservableBody<ResponseData<Object>>());

    }


}
