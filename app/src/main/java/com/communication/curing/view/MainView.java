package com.communication.curing.view;

import com.communication.curing.entity.UpdateBean;
import com.communication.curing.entity.UpdateVersionBean;

/**
 * Created by LG
 * on 2021/6/11  11:23
 * Description：
 */
public interface MainView {


    public void getUpdateVersionDataSuccess(UpdateVersionBean updateBean);
    public void getUpdateDataSuccess(UpdateBean updateBean);

    public void getDataFail(String msg);

}
