package com.exz.antcargo.activity;

import android.os.Handler;
import android.text.TextUtils;

import com.exz.antcargo.R;
import com.exz.antcargo.activity.utils.CheckState;
import com.exz.antcargo.activity.utils.ConstantValue;
import com.exz.antcargo.activity.utils.Utils;

import org.xutils.view.annotation.ContentView;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by pc on 2016/9/21.
 *
 */
@ContentView(R.layout.activity_guide_age)
public class GuidePageActivity extends BaseActivity {
    @Override
    public void initView() {

        if(!TextUtils.isEmpty( ConstantValue.AccountType)){

            CheckState.checkCarInfo(GuidePageActivity.this, ConstantValue.AccountType);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Utils.startActivity(GuidePageActivity.this,MainActivity.class);
            }
        },3000);
    }


    @Override
    public void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(GuidePageActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(GuidePageActivity.this);
    }



}
