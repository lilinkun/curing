package com.communication.curing.util;

import android.content.Intent;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.communication.curing.activity.LoginActivity;
import com.communication.curing.activity.WebviewActivity;

import static android.app.Activity.RESULT_OK;

public class AndroidJavascriptInterface {
    private WebviewActivity webviewActivity;

    public AndroidJavascriptInterface(WebviewActivity webviewActivity) {
        this.webviewActivity = webviewActivity;
    }

    @JavascriptInterface
    public void goToLogin() {
        if (webviewActivity != null) {
            MethodsUtil.saveKeyValue(ConstantsUtil.AUTHORIZATION, "", webviewActivity);
            Intent intent = new Intent(webviewActivity, LoginActivity.class);
            webviewActivity.startActivity(intent);
            ActivityUtil.finishAll();
        }
    }

    @JavascriptInterface
    public void goBack() {

        if (webviewActivity != null) {

            webviewActivity.runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    WebView webView = webviewActivity.getWebView();

                    if (webView != null) {
                        webviewActivity.setResult(RESULT_OK);
                        webviewActivity.finish();
                    }
                }
            });
        }
    }

    @JavascriptInterface
    public String getToken(){


        return  MethodsUtil.getValueByKey(ConstantsUtil.AUTHORIZATION,webviewActivity);


    }

}
