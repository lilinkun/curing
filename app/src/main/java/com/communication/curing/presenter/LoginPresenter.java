package com.communication.curing.presenter;

import android.app.Activity;
import android.content.Context;

import com.communication.curing.base.BasePresenter;
import com.communication.curing.callback.JsonCallback;
import com.communication.curing.entity.VerificationCodeEntity;
import com.communication.curing.view.LoginView;
import com.lzy.okgo.OkGo;

public class LoginPresenter extends BasePresenter<LoginView>{


    public void getImage() {

//        OkGo.get("http://192.168.40.86:8096/prod-api/captchaImage").execute(new JsonCallback(VerificationCodeEntity.class, activity));
    }


}
