package com.communication.curing.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;

import com.communication.curing.R;
import com.communication.curing.base.BaseActivity;
import com.communication.curing.base.BasePresenter;
import com.communication.curing.callback.JsonCallback;
import com.communication.curing.callback.LoginCallBack;
import com.communication.curing.entity.BaseEntity;
import com.communication.curing.entity.LoginEntity;
import com.communication.curing.entity.VerificationCodeEntity;
import com.communication.curing.http.CuringService;
import com.communication.curing.util.ConstantsKey;
import com.communication.curing.util.ConstantsUtil;
import com.communication.curing.util.CuringUtil;
import com.communication.curing.util.MethodsUtil;
import com.communication.curing.util.PermissUtil;
import com.communication.curing.util.RSAUtils;
import com.communication.curing.util.UToastUtil;
import com.gyf.immersionbar.ImmersionBar;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.iv_verification_code)
    AppCompatImageView ivVerificationCode;
    @BindView(R.id.et_verification_code)
    EditText etVerificationCode;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;

    private VerificationCodeEntity verificationCodeEntity;
    private LoginEntity loginEntity;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this).transparentStatusBar().init();
        OkGo.getInstance().getCommonHeaders().put(ConstantsUtil.AUTHORIZATION, "");

//        OkGo.get(CuringService.PREFIX + "/captchaImage").execute(new JsonCallback(VerificationCodeEntity.class, this));
        OkGo.get(CuringService.GETVCODE).execute(new JsonCallback(VerificationCodeEntity.class, this));


        PermissUtil.checkPermissions(this, PermissUtil.appNeedPermissions);


        String username = MethodsUtil.getValueByKey(ConstantsUtil.ACCOUNT, this);

        if (!TextUtils.isEmpty(username)){
            etUsername.setText(username);
        }

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    @Override
    public void onSuccess(Response response) {
        super.onSuccess(response);

        if (response != null){
            if (response.body() instanceof VerificationCodeEntity){
                verificationCodeEntity = (VerificationCodeEntity) response.body();

                CuringUtil.setImage(verificationCodeEntity.getImg(),ivVerificationCode);

            }else if (response.body() instanceof LoginEntity){
                loginEntity = (LoginEntity)response.body();

                if (loginEntity.getCode() == 200){
                    MethodsUtil.saveKeyValue(ConstantsUtil.AUTHORIZATION,loginEntity.getToken(), this);
                    MethodsUtil.saveKeyValue(ConstantsUtil.ACCOUNT,etUsername.getText().toString(), this);
                    OkGo.getInstance().getCommonHeaders().put(ConstantsUtil.AUTHORIZATION, loginEntity.getToken());
                    Intent intent = new Intent();
                    intent.setClass(this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }


            }
        }

    }

    @Override
    public void onFail(String base) {
        if (!TextUtils.isEmpty(base)){
            super.onFail(base);
            Log.v("LG",base);
            if (base.contains("验证码")){
                OkGo.get(CuringService.GETVCODE).execute(new JsonCallback(VerificationCodeEntity.class, this));
            }
            UToastUtil.show(this,base);
        }
    }

    @OnClick({R.id.iv_verification_code,R.id.btn_login})
    public void onClick(View view){
        if (view.getId() == R.id.iv_verification_code){
//            OkGo.get(CuringService.PREFIX + "/captchaImage").execute(new JsonCallback(VerificationCodeEntity.class, this));
            OkGo.get(CuringService.GETVCODE).execute(new JsonCallback(VerificationCodeEntity.class, this));
        }else if (view.getId() == R.id.btn_login){
            if (!CuringUtil.etIsnull(etUsername)){
                UToastUtil.show(this,"请输入用户名");
            }else if (!CuringUtil.etIsnull(etPassword)){
                UToastUtil.show(this,"请输入密码");
            }else if (!CuringUtil.etIsnull(etVerificationCode)){
                UToastUtil.show(this,"请输入验证码");
            }else {

                String password = etPassword.getText().toString();

                try {
                    String psd = Base64.encodeToString(RSAUtils.encryptByPublicKey(password,ConstantsKey.publicKey),Base64.NO_WRAP);
                    /*byte[] sy2=RSAUtils.decryptByPrivateKey(Base64.decode(psd,Base64.NO_WRAP), ConstantsKey.privateKey);
                    String outputpwd = new String(sy2);*/

                    HashMap<String, String> params = new HashMap<>();
                    params.put("clientType", "1");
                    params.put("code", etVerificationCode.getText().toString());
                    params.put("password", psd);
                    params.put("username", etUsername.getText().toString());
                    params.put("uuid", verificationCodeEntity.getUuid());
                    JSONObject jsonObject = new JSONObject(params);


//                    OkGo.post(CuringService.PREFIX + "/login")
                    OkGo.post(CuringService.LOGIN)
                            .upJson(jsonObject)
                            .execute(new LoginCallBack(LoginEntity.class, this));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissUtil.PERMISSON_REQUESTCODE){
            if (grantResults[0] == PackageManager.PERMISSION_DENIED){
                finish();
            }
        }
    }

}
