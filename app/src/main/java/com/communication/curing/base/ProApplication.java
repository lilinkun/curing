package com.communication.curing.base;

import android.app.Application;

import com.communication.curing.http.interceptor.HttpInterceptor;
import com.communication.curing.util.ConstantsUtil;
import com.communication.curing.util.MethodsUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.DBCookieStore;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;

import okhttp3.OkHttpClient;

public class ProApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        HttpHeaders headers = new HttpHeaders();
        headers.put(ConstantsUtil.AUTHORIZATION, MethodsUtil.getValueByKey(ConstantsUtil.AUTHORIZATION, this));

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        HttpInterceptor loggingInterceptor = new HttpInterceptor("OkGo",this);
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        loggingInterceptor.setColorLevel(java.util.logging.Level.INFO);
        builder.addInterceptor(loggingInterceptor);

        builder.cookieJar(new CookieJarImpl(new DBCookieStore(this)));

        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory();
        builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);

        OkGo.getInstance().init(this)
                .setOkHttpClient(builder.build())
                .setRetryCount(3)
                .addCommonHeaders(headers);
    }
}
