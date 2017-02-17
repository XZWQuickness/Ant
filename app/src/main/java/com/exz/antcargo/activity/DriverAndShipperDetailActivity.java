package com.exz.antcargo.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.exz.antcargo.R;
import com.exz.antcargo.activity.adapter.CarInfoAdapter;
import com.exz.antcargo.activity.view.NoScrollListView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by pc on 2016/8/26.
 * 车主或者货主信息
 */
@ContentView(R.layout.activity_driver_and_shipper_detial)
public class DriverAndShipperDetailActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.ll_back)
    private LinearLayout llBack;


    @ViewInject(R.id.ll_tousu)
    private LinearLayout ll_tousu;



    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    private CarInfoAdapter adapter;


    @ViewInject(R.id.lv)
    private NoScrollListView lv;
    @Override
    public void initView() {
        tv_title.setText(getIntent().getStringExtra("name"));
        llBack.setOnClickListener(this);
        ll_tousu.setOnClickListener(this);
        adapter = new CarInfoAdapter(DriverAndShipperDetailActivity.this);
        lv.setAdapter(adapter);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_tousu://投诉
                Intent intent=new Intent(DriverAndShipperDetailActivity.this,TouSuActivity.class);
                intent.putExtra("name","投诉货主");
                intent.putExtra("objectId", "1");//1货源 2车源
                startActivity(intent);

                break;
        }
    }
}
