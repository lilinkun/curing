package com.communication.curing.callback;

import com.communication.curing.entity.BaseEntity;
import com.lzy.okgo.model.Response;

public interface RequestCallback {
    void onSuccess(Response response);
    void onFail(String str);
    void loading(boolean b);
}
