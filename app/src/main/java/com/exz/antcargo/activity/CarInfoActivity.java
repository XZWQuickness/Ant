package com.exz.antcargo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.exz.antcargo.R;
import com.exz.antcargo.activity.adapter.AddCarInfoAdapter;
import com.exz.antcargo.activity.bean.AuditCarInfoBean;
import com.exz.antcargo.activity.bean.NewEntity;
import com.exz.antcargo.activity.bean.SelectCarBean;
import com.exz.antcargo.activity.utils.Constant;
import com.exz.antcargo.activity.utils.ConstantValue;
import com.exz.antcargo.activity.utils.SPutils;
import com.exz.antcargo.activity.utils.Utils;
import com.exz.antcargo.activity.utils.XUtilsApi;
import com.exz.antcargo.activity.view.NoScrollListView;

import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import static com.alibaba.fastjson.JSON.parseArray;

/**
 * Created by pc on 2016/8/24.
 * 车辆信息
 */
@ContentView(R.layout.activity_car_info)
public class CarInfoActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.ll_back)
    private LinearLayout llBack;

    @ViewInject(R.id.tv_title)
    private TextView tv_title;

    @ViewInject(R.id.bt_add_car)
    private Button bt_add_car;

    @ViewInject(R.id.lv)
    private NoScrollListView lv;

    private AddCarInfoAdapter<SelectCarBean> adapter;
    private List<SelectCarBean> list = new ArrayList<SelectCarBean>();

    private Context c = CarInfoActivity.this;


    @Override
    public void initView() {
        tv_title.setText("车辆信息");
        llBack.setOnClickListener(this);
        bt_add_car.setOnClickListener(this);
        adapter = new AddCarInfoAdapter<SelectCarBean>(CarInfoActivity.this);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                isCarInfo(list.get(i).getVehicleId());
            }


        });
    }

    private void isCarInfo(String id) {
        XUtilsApi xUtilsApi = new XUtilsApi();
        RequestParams params = new RequestParams(Constant.CHECKVEHICLERESULT);//审核车辆信息
        params.addBodyParameter("vehicleId", id);
        xUtilsApi.sendUrl(c, "post", params, false, new XUtilsApi.URLSuccessListenter() {

            @Override
            public void OnSuccess(NewEntity content, JSONObject result) {
                if (content.getResult().equals(ConstantValue.RESULT)) {
                    Intent addCarActivityIntnet = new Intent(c, AddCarActivity.class);
                    Intent intent = new Intent(CarInfoActivity.this, DiaLogActivity.class);
                    Bundle mBundle = new Bundle();
                    if (content.getResult().equals(ConstantValue.RESULT)) {

                        String json = result.optString("info");
                        switch ( JSON.parseObject(json, AuditCarInfoBean.class).getCheckState()) {//'-1:未提交审核信息 0未审核(审核中) 1审核通过 2拒绝 3禁用
                            case "-1":
                                mBundle.putSerializable("bean",  JSON.parseObject(json, AuditCarInfoBean.class));
                                addCarActivityIntnet.putExtras(mBundle);
                                startActivityForResult(addCarActivityIntnet,100);
                                break;
                            case "0":
                                break;

                            case "2":
                                mBundle.putSerializable("bean",  JSON.parseObject(json, AuditCarInfoBean.class));
                                addCarActivityIntnet.putExtras(mBundle);
                                startActivityForResult(addCarActivityIntnet,100);
                                break;

                        }

                    }
                } else {
                    Intent intent = new Intent(c, DiaLogActivity.class)
                            .putExtra("message", content.getMessage());
                    startActivity(intent);
                }


            }
        });
    }

    @Override
    public void initData() {
        XUtilsApi xUtilsApi = new XUtilsApi();
        RequestParams params = new RequestParams(Constant.VEHICLESELECTLIST);
        params.addBodyParameter("AccountId", SPutils.getString(c, "accountId"));
        params.addBodyParameter("type", "-1");//- 1 所有 1认证过

        xUtilsApi.sendUrl(CarInfoActivity.this, "post", params, false, new XUtilsApi.URLSuccessListenter() {

            @Override
            public void OnSuccess(NewEntity content, JSONObject result) {
                if (content.getResult().equals(ConstantValue.RESULT)) {
                    String json = result.optString("info");
                    list = parseArray(json, SelectCarBean.class);
                    adapter.addendData(list, true);
                    adapter.updateAdapter();

                } else {
                    Intent intent = new Intent(CarInfoActivity.this, DiaLogActivity.class)
                            .putExtra("message", content.getMessage());
                    startActivity(intent);
                }
            }
        });

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;

            case R.id.bt_add_car://添加车辆
                Utils.startActivityForResult(CarInfoActivity.this, AddCarActivity.class);
                break;


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100:
                initData();
                break;
        }
    }

}
