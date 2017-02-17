package com.exz.antcargo.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.exz.antcargo.R;
import com.exz.antcargo.activity.utils.Utils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by pc on 2016/8/22.
 * 选择身份
 */
@ContentView(R.layout.activity_checked_identtity)
public class CheckedIdentityActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.ll_back)
    private LinearLayout llBack;

    @ViewInject(R.id.tv_title)
    private TextView tv_title;

    @ViewInject(R.id.ll_owner)
    private LinearLayout ll_owner;

    @ViewInject(R.id.ll_shipper)
    private LinearLayout ll_shipper;



    @Override
    public void initView() {
        tv_title.setText("注册");
        llBack.setOnClickListener(this);
        ll_owner.setOnClickListener(this);
        ll_shipper.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_back:
                finish();
                break;

            case R.id.ll_owner://车主认证
                Utils.startActivity(CheckedIdentityActivity.this,IdentityAuditActivity.class);
                break;
            case R.id.ll_shipper://货主认证
                Utils.startActivity(CheckedIdentityActivity.this,ShippeAuditActivity.class);
                break;

        }
    }
}
