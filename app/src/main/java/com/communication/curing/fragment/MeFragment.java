package com.communication.curing.fragment;

import android.content.Intent;
import android.view.View;
import android.webkit.WebView;

import com.communication.curing.R;
import com.communication.curing.activity.LoginActivity;
import com.communication.curing.activity.PersonalInfoActivity;
import com.communication.curing.base.BaseFragment;
import com.communication.curing.base.BasePresenter;
import com.communication.curing.entity.AccountEntity;
import com.communication.curing.util.ConstantsUtil;
import com.communication.curing.util.MethodsUtil;
import com.communication.curing.util.UToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG
 * on 2021/6/10
 * Description：
 */
public class MeFragment extends BaseFragment {

    private AccountEntity accountEntity;
    private final int PERSONFORRESULT = 0x212;


    @Override
    public int getlayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    public void initEventAndData() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    @OnClick({R.id.tv_exit_login,R.id.tv_my_info,R.id.tv_change_psd,R.id.tv_about,R.id.tv_setting})
    public void onClick(View view){
        switch (view.getId()){


            case R.id.tv_exit_login:

                MethodsUtil.saveKeyValue(ConstantsUtil.AUTHORIZATION, "", getActivity());
                Intent intent1 = new Intent();
                intent1.setClass(getActivity(), LoginActivity.class);
                startActivity(intent1);
                getActivity().finish();

                break;

            case R.id.tv_my_info:


                Intent intent = new Intent();
                intent.putExtra(ConstantsUtil.PERSONAL,accountEntity);
                intent.setClass(getActivity(), PersonalInfoActivity.class);
                startActivityForResult(intent,PERSONFORRESULT);

                break;
            case R.id.tv_change_psd:
            case R.id.tv_about:
            case R.id.tv_setting:

                UToastUtil.show(getActivity(),"等待开发中...");

                break;

            default:

                break;
        }
    }

}
