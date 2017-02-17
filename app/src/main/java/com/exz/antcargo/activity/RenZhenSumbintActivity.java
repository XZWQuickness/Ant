package com.exz.antcargo.activity;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.exz.antcargo.R;
import com.exz.antcargo.activity.utils.Utils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by pc on 2016/8/22.
 * 认证提交成功
 */
@ContentView(R.layout.activity_renzhen_sumbint)
public class RenZhenSumbintActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.ll_back)
    private LinearLayout llBack;

    @ViewInject(R.id.tv_title)
    private TextView tv_title;


    @ViewInject(R.id.bt_xianguangguang)
    private Button bt_xianguangguang;





    @Override
    public void initView() {
        tv_title.setText("认证提交成功");
        llBack.setOnClickListener(this);
        bt_xianguangguang.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_back:
                finish();
                break;
            case R.id.bt_xianguangguang:
                Utils.startActivity(RenZhenSumbintActivity.this,MainActivity.class);
                break;


        }
    }
}
