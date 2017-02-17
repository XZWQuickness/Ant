package com.exz.antcargo.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import org.greenrobot.eventbus.EventBus;
import org.xutils.x;


/**
 * Created by pc on 2016/7/14.
 */
public abstract class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initView();
        initData();

    }

    public abstract void initView();

    public abstract void initData();

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
