package com.communication.curing.activity;

import android.os.Build;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.communication.curing.R;
import com.communication.curing.base.BaseActivity;
import com.communication.curing.base.BasePresenter;
import com.communication.curing.http.CuringService;
import com.communication.curing.util.ActivityUtil;
import com.communication.curing.util.AndroidJavascriptInterface;

import okhttp3.Headers;


/**
 * webview
 */
public class WebviewActivity extends BaseActivity {
    private WebView webView;


    @Override
    public int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    public void initView() {
        ActivityUtil.addActivity(this);

//        String url = "http://192.168.1.236:8080/liguo";
//        String url = "http://www.baidu.com";
        String url = CuringService.H5;
//        String url = "https://echarts.apache.org/examples/zh/index.html";

        Log.i("OkGo", "" + url);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeSessionCookies(null);
        cookieManager.flush();
        cookieManager.setAcceptCookie(true);
        cookieManager.setCookie(url, "");

        webView = findViewById(R.id.wv);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.addJavascriptInterface(new AndroidJavascriptInterface(this), "Android");
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                view.setVisibility(View.GONE);
                finish();
            }
        });
        webView.loadUrl(url);

    }

    public WebView getWebView() {
        return webView;
    }

    @Override
    public void onBackPressed() {

        if (webView != null) {

            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                setResult(RESULT_OK);
                super.onBackPressed();
            }
        }
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
