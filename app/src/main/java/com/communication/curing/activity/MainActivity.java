package com.communication.curing.activity;

import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.communication.curing.R;
import com.communication.curing.adapter.PageAdapter;
import com.communication.curing.base.BaseActivity;
import com.communication.curing.base.BaseFragment;
import com.communication.curing.base.BasePresenter;
import com.communication.curing.entity.TabEntity;
import com.communication.curing.entity.UpdateBean;
import com.communication.curing.entity.UpdateVersionBean;
import com.communication.curing.fragment.HomeFragment;
import com.communication.curing.fragment.MeFragment;
import com.communication.curing.http.CuringService;
import com.communication.curing.presenter.MainPresenter;
import com.communication.curing.service.UpdateService;
import com.communication.curing.util.ActivityUtil;
import com.communication.curing.util.CuringUtil;
import com.communication.curing.util.UToastUtil;
import com.communication.curing.view.MainView;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainView, MainPresenter> implements MainView{

    @BindView(R.id.vp_main)
    ViewPager vpMain;
    @BindView(R.id.ctl_main)
    CommonTabLayout commonTablayout;

    private ArrayList<BaseFragment> fragmentList;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private int[] mIconUnselectIds = {
            R.drawable.ic_home_control_nor, R.drawable.ic_home_me_nor};
    private int[] mIconSelectIds = {
            R.drawable.ic_home_control_sel, R.drawable.ic_home_me_sel};

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {

        ActivityUtil.addActivity(this);

        fragmentList = new ArrayList<>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new MeFragment());

        PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(),fragmentList);
        vpMain.setAdapter(pageAdapter);

        for (int i = 0;i<fragmentList.size();i++){
            mTabEntities.add(new TabEntity(getResources().getStringArray(R.array.main_activity)[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }

        bindTab();

        mPresenter.checkVersionUpdate();

    }

    private void bindTab() {
        commonTablayout.setTabData(mTabEntities);
        commonTablayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                vpMain.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
                if (position == 0) {
                }
            }
        });

        vpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                commonTablayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        vpMain.setCurrentItem(0);
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    public void getUpdateVersionDataSuccess(UpdateVersionBean updateBean) {

        int rt = updateBean.getVersion().compareTo(CuringUtil.getVersion(this));

        if ( rt > 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this).setMessage("请升级更新app").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    /*mApkUrl = bean1.getInstall_url();
                    deleteApkFile();
                    downloadApkFile(dialog);*/

                    UpdateService.startAction(MainActivity.this,updateBean.getDownloadPath(),updateBean.getAppName());

                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
//            builder.create().setCanceledOnTouchOutside(false);
            //  builder.setCancelable(false);
            /*builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    finish();
                }
            });*/
            builder.show();
        }
    }

    @Override
    public void getUpdateDataSuccess(UpdateBean updateBean) {
        if (updateBean.buildVersion > Double.valueOf(CuringUtil.getVersion(this))){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this).setMessage("请升级更新app").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    /*mApkUrl = bean1.getInstall_url();
                    deleteApkFile();
                    downloadApkFile(dialog);*/

                    UpdateService.startAction(MainActivity.this,updateBean.downloadURL,updateBean.buildName);

                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
//            builder.create().setCanceledOnTouchOutside(false);
            //  builder.setCancelable(false);
            /*builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    finish();
                }
            });*/
            builder.show();
        }
    }

    @Override
    public void getDataFail(String msg) {
        UToastUtil.show(this,msg);
    }

    private long exitTime;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - exitTime < 2000) {
            super.onBackPressed();
        } else {
            exitTime = System.currentTimeMillis();
            Toast.makeText(this, getString(R.string.exit_prompt), Toast.LENGTH_SHORT).show();
        }
    }
}