package com.exz.antcargo.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.exz.antcargo.R;
import com.exz.antcargo.activity.adapter.GoodsInfoAdapter;
import com.exz.antcargo.activity.bean.NewEntity;
import com.exz.antcargo.activity.bean.NewestGoodsBean;
import com.exz.antcargo.activity.utils.Constant;
import com.exz.antcargo.activity.utils.ConstantValue;
import com.exz.antcargo.activity.utils.MainSendEvent;
import com.exz.antcargo.activity.utils.SPutils;
import com.exz.antcargo.activity.utils.XUtilsApi;
import com.exz.antcargo.activity.view.NoScrollListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016/8/24.
 * 车源信息管理
 */
@ContentView(R.layout.activity_car_info_mannage)
public class GoodsManageActivity extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, SwipeRefreshLayout.OnRefreshListener {
    @ViewInject(R.id.v_01)
    private View v_01;

    @ViewInject(R.id.v_02)
    private View v_02;

    @ViewInject(R.id.tv_title)
    private TextView tvTiltle;

    private List<View> list;
    private int widthPixels;

    @ViewInject(R.id.ll_back)
    private LinearLayout ll_back;

    @ViewInject(R.id.lv)
    private NoScrollListView lv;

    @ViewInject(R.id.rg)
    private RadioGroup rg;
    @ViewInject(R.id.sw)
    private SwipeRefreshLayout mSwipeLayout;


    private GoodsInfoAdapter<NewestGoodsBean> adapter;

    private Context c = GoodsManageActivity.this;

    private String typeId = "2";

    @ViewInject(R.id.iv_null_data)
    private ImageView iv_null_data;

    @Override
    public void initView() {
        widthPixels = getResources().getDisplayMetrics().widthPixels;
        tvTiltle.setText("货源信息管理");

        list = new ArrayList<View>();
        list.add(v_01);
        list.add(v_02);
        for (int i = 0; i < list.size(); i++) {
            changeWidthAndHeight(list.get(i));
        }
        ll_back.setOnClickListener(this);
        rg.setOnCheckedChangeListener(this);
        adapter = new GoodsInfoAdapter<NewestGoodsBean>(GoodsManageActivity.this);
        lv.setAdapter(adapter);

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        lv.setEmptyView(iv_null_data);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(R.color.yeelow,
                R.color.blueness,
                R.color.gary);
    }

    private void changeWidthAndHeight(View tv) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tv.getLayoutParams();
        int size = widthPixels / 2;
        layoutParams.width = size;
        tv.setLayoutParams(layoutParams);
    }

    @Override
    public void initData() {

        carManageInfo(typeId);

    }

    @Override
    protected void onResume() {
        super.onResume();
        carManageInfo(typeId);
    }

    /**
     * @param typeId 1在线 2下架
     */
    private void carManageInfo(String typeId) {
        XUtilsApi xUtilsApi = new XUtilsApi();
        RequestParams params = new RequestParams(Constant.GOODS_MYINFORMATIONLIST);
        params.addBodyParameter("AccountId", SPutils.getString(c, "accountId"));
        params.addBodyParameter("typeId", typeId);

        xUtilsApi.sendUrl(c, "post", params, true, new XUtilsApi.URLSuccessListenter() {

            @Override
            public void OnSuccess(NewEntity content, JSONObject result) {
                if (content.getResult().equals(ConstantValue.RESULT)) {
                    String json = result.optString("info");
                    adapter.addendData(JSON.parseArray(json, NewestGoodsBean.class), true, "2");
                    adapter.updateAdapter();


                } else {
                    Intent intent = new Intent(c, DiaLogActivity.class)
                            .putExtra("message", content.getMessage());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.rb_main_1:
                v_01.setVisibility(View.VISIBLE);
                v_02.setVisibility(View.INVISIBLE);
                typeId = "2";
                carManageInfo(typeId);
                break;

            case R.id.rb_main_2:
                v_02.setVisibility(View.VISIBLE);
                v_01.setVisibility(View.INVISIBLE);
                typeId = "1";
                carManageInfo(typeId);
                break;

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        carManageInfo(typeId);
    }

    @Subscribe
    public void update(MainSendEvent event) {
        if (event != null & event.getStringMsgData().equals("update")) {
            carManageInfo(typeId);

        }

    }

    @Override
    public void onRefresh() {
        carManageInfo(typeId);
        mSwipeLayout.setRefreshing(false);

    }
}
