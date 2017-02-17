package com.exz.antcargo.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.exz.antcargo.R;
import com.exz.antcargo.activity.adapter.CarInfoManageAdapter;
import com.exz.antcargo.activity.bean.CarManageBean;
import com.exz.antcargo.activity.bean.NewEntity;
import com.exz.antcargo.activity.utils.Constant;
import com.exz.antcargo.activity.utils.ConstantValue;
import com.exz.antcargo.activity.utils.SPutils;
import com.exz.antcargo.activity.utils.Utils;
import com.exz.antcargo.activity.utils.XUtilsApi;
import com.exz.antcargo.activity.xlistView.XListView;

import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016/9/26.
 * 货源 匹配的车源列表
 */
@ContentView(R.layout.activity_mathing_car_list)
public class MathingCarListActivity extends BaseActivity implements XListView.IXListViewListener, View.OnClickListener {

    @ViewInject(R.id.lv)
    private XListView lv;
    private CarInfoManageAdapter<CarManageBean> adapter;//查看记录的adapter
    private boolean refsh = true;

    private int page=1;
    private List<CarManageBean> listCar = new ArrayList<CarManageBean>();
    private Context c=MathingCarListActivity.this;
    @ViewInject(R.id.iv_null_data)
    private ImageView iv_null_data;

    @ViewInject(R.id.ll_back)
    private LinearLayout ll_back;

    @ViewInject(R.id.tv_title)
    private TextView tv_title;



    @Override
    public void initView() {
        adapter = new CarInfoManageAdapter<CarManageBean>(MathingCarListActivity.this);
        lv.setAdapter(adapter);
        lv.setPullRefreshEnable(true);
        lv.setPullLoadEnable(true);
        lv.setXListViewListener(this);
        ll_back.setOnClickListener(this);
        tv_title.setText("匹配到的车源列表");

    }

  private void   carInfoList(){
        XUtilsApi xUtilsApi = new XUtilsApi();
        RequestParams p = new RequestParams(Constant.RECVEHICLELIST);
        p.addBodyParameter("accountId", SPutils.getString(c, "accountId"));
        p.addBodyParameter("longitude",getIntent().getStringExtra("longitude"));
        p.addBodyParameter("latitude", getIntent().getStringExtra("latitude"));
        p.addBodyParameter("origin", getIntent().getStringExtra("origin"));
        p.addBodyParameter("page", page+"");
        xUtilsApi.sendUrl(c, "post", p, false, new XUtilsApi.URLSuccessListenter() {

            @Override
            public void OnSuccess(NewEntity content, JSONObject result) {
                Intent intent = new Intent(c, DiaLogActivity.class);
                if (content.getResult().equals(ConstantValue.RESULT)) {
                    String json = result.optString("info");
                    listCar = JSON.parseArray(json, CarManageBean.class);
                    adapter.addendData(listCar, refsh, "1");//1 查看车主 2 编辑
                    adapter.updateAdapter();

                } else {
                    intent.putExtra("message", content.getMessage());
                    startActivity(intent);
                }
            }
        });
      lv.setEmptyView(iv_null_data);
      lv.stopRefresh();
      lv.stopLoadMore();
      lv.setRefreshTime(Utils.getCurrentHour());
    }

    @Override
    public void initData() {
        carInfoList();
    }

    @Override
    public void onRefresh() {
        refsh=true;
        page=1;
        carInfoList();
    }

    @Override
    public void onLoadMore() {
        refsh=false;
        page++;
        carInfoList();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_back:
                finish();
                break;
        }
    }
}
