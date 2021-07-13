package com.communication.curing.presenter;


import com.communication.curing.base.BasePresenter;
import com.communication.curing.entity.AccountEntity;
import com.communication.curing.http.CuringService;
import com.communication.curing.http.rxjavahelper.RxObserver;
import com.communication.curing.http.rxjavahelper.RxResultHelper;
import com.communication.curing.http.rxjavahelper.RxSchedulersHelper;
import com.communication.curing.view.PersonalInfoView;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by LG
 * on 2021/6/17  17:55
 * Description：
 */
public class PersonalInfoPresenter extends BasePresenter<PersonalInfoView> {

    /**
     *获取个人信息
     */
    public void getPersonalInfo(){

        CuringService.getAccount()
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<AccountEntity>() {
                    @Override
                    public void _onNext(AccountEntity accountEntity) {
                        getView().getPersonalInfoSuccess(accountEntity);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getPersonalInfoFail(errorMessage);
                    }

                });
    }

    /**
     *
     * @param company 公司名
     * @param id      个人信息编号
     * @param job     职位
     * @param name    姓名
     * @param sex     性别0：女 1：男
     */
    public void modifyPersonInfo(String company,String id,String job,String name,String sex){

        HashMap<String, String> params = new HashMap<>();

        params.put("company", company);
        params.put("id", id);
        params.put("job",job);
        params.put("name",name);
        params.put("sex",sex);

        JSONObject jsonObject = new JSONObject(params);


        CuringService.modifyPersonInfo(jsonObject)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<Object>() {
                    @Override
                    public void _onNext(Object s) {
                        getView().modifySuccess();
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().modifyFail(errorMessage);
                    }

                });


    }


}
