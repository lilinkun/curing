package com.communication.curing.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;


import com.communication.curing.R;
import com.communication.curing.base.BaseActivity;
import com.communication.curing.entity.AccountEntity;
import com.communication.curing.presenter.PersonalInfoPresenter;
import com.communication.curing.util.ActivityUtil;
import com.communication.curing.util.ConstantsUtil;
import com.communication.curing.util.CuringUtil;
import com.communication.curing.util.UToastUtil;
import com.communication.curing.view.PersonalInfoView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG
 * on 2021/6/17  16:01
 * Description：个人信息
 */
public class PersonalInfoActivity extends BaseActivity<PersonalInfoView, PersonalInfoPresenter> implements PersonalInfoView {

    @BindView(R.id.tv_name_value)
    TextView tvNameValue;
    @BindView(R.id.tv_organization_unit_value)
    TextView tvOrganizationUnitValue;
    @BindView(R.id.tv_post_value)
    TextView tvPostValue;
    @BindView(R.id.tv_email_value)
    TextView tvEmailValue;
    @BindView(R.id.tv_sex_value)
    TextView tvSexValue;
    @BindView(R.id.tv_phone_value)
    TextView tvPhoneValue;

    private String organizationUnit,name,post;
    //0女 1男
    private int sex;
    private AccountEntity accountEntity;
    private final int JUMPPAGE = 0X123;
    private String[] sexArry = new String[]{"女", "男"};// 性别选择

    @Override
    public int getLayoutId() {
        return R.layout.activity_personal_info;
    }

    @Override
    public void initView() {

        ActivityUtil.addActivity(this);

        if(getIntent() != null && getIntent().getSerializableExtra(ConstantsUtil.PERSONAL) != null){
            accountEntity = (AccountEntity) getIntent().getSerializableExtra(ConstantsUtil.PERSONAL);
            getPersonalInfoSuccess(accountEntity);
        }else {
//            mPresenter.getPersonalInfo();
        }

    }

    @Override
    protected PersonalInfoPresenter createPresenter() {
        return new PersonalInfoPresenter();
    }

    @Override
    public void getPersonalInfoSuccess(AccountEntity accountEntity) {
        this.accountEntity = accountEntity;
        tvNameValue.setText(accountEntity.getAccountInfo().getName());
        tvOrganizationUnitValue.setText(accountEntity.getAccountInfo().getCompany());
        tvPostValue.setText(accountEntity.getAccountInfo().getJob());
        tvPhoneValue.setText(accountEntity.getAccountInfo().getPhone());
        tvEmailValue.setText(accountEntity.getAccountInfo().getEmail());

        if (accountEntity.getAccountInfo().getSex() == 0){
            tvSexValue.setText("女");
        }else if (accountEntity.getAccountInfo().getSex() == 1){
            tvSexValue.setText("男");
        }

    }

    @Override
    public void getPersonalInfoFail(String msg) {
        UToastUtil.show(this,msg);
        CuringUtil.otherLogin(this,msg);
    }

    @Override
    public void modifySuccess() {
        UToastUtil.show(this,"修改成功");
        onBackPressed();
    }

    @Override
    public void modifyFail(String msg) {
        UToastUtil.show(this,msg);
    }

    @OnClick({R.id.ll_back,R.id.tv_phone_value,R.id.iv_phone,R.id.tv_modify_setting,R.id.tv_email_value,R.id.iv_email,R.id.tv_sex_value})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ll_back:

                onBackPressed();

                break;
            case R.id.tv_phone_value:
            case R.id.iv_phone:
                break;

            case R.id.tv_email_value:
            case R.id.iv_email:
                break;

            case R.id.tv_modify_setting:

                organizationUnit = tvOrganizationUnitValue.getText().toString();

                name = tvNameValue.getText().toString();

                post = tvPostValue.getText().toString();


                mPresenter.modifyPersonInfo(organizationUnit,accountEntity.getAccountInfo().getId()+"",post,name,sex+"");

                break;

            case R.id.tv_sex_value:

                if (tvSexValue.getText().toString().equals("男")){
                    sex = 1;
                }else if(tvSexValue.getText().toString().equals("女")){
                    sex = 0;
                }else {
                    if (accountEntity != null && accountEntity.getAccountInfo() != null) {
                        sex = accountEntity.getAccountInfo().getSex();
                    }
                }

                AlertDialog.Builder builder3 = new AlertDialog.Builder(this);// 自定义对话框
                builder3.setSingleChoiceItems(sexArry, sex, new DialogInterface.OnClickListener() {// 2默认的选中

                    @Override
                    public void onClick(DialogInterface dialog, int which) {// which是被选中的位置
                        // showToast(which+"");
                        tvSexValue.setText(sexArry[which]);
                        sex = which;
                        dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
                    }
                });
                builder3.show();// 让弹出框显示
                break;

            default:

                break;

        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == JUMPPAGE && resultCode ==  RESULT_OK){
            mPresenter.getPersonalInfo();
        }
    }
}
