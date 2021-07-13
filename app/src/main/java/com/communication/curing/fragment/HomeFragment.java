package com.communication.curing.fragment;

import android.content.Intent;
import android.view.View;

import com.communication.curing.R;
import com.communication.curing.activity.WebviewActivity;
import com.communication.curing.base.BaseFragment;
import com.communication.curing.base.BasePresenter;
import com.communication.curing.util.UToastUtil;

import butterknife.OnClick;

/**
 * Created by LG
 * on 2021/6/10
 * Description：
 */
public class HomeFragment extends BaseFragment {

    @Override
    public int getlayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initEventAndData() {


    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @OnClick({R.id.tv_home_basic_data_road_network,R.id.tv_home_curing_plan,R.id.tv_home_curing_road_project,R.id.tv_home_data_road_network,R.id.tv_home_law_enforcement,
    R.id.tv_home_overload_control,R.id.tv_home_road_evaluate,R.id.tv_home_road_manager,R.id.tv_home_other})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_home_basic_data_road_network:

                Intent intent = new Intent(getActivity(), WebviewActivity.class);
                startActivity(intent);

                break;

            case R.id.tv_home_curing_plan:
            case R.id.tv_home_curing_road_project:
            case R.id.tv_home_data_road_network:
            case R.id.tv_home_law_enforcement:
            case R.id.tv_home_overload_control:
            case R.id.tv_home_road_evaluate:
            case R.id.tv_home_road_manager:
            case R.id.tv_home_other:

                UToastUtil.show(getActivity(),"计划开发中...");

                break;

            default:
                break;
        }
    }
}
