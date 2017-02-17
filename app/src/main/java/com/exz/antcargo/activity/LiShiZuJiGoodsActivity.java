package com.exz.antcargo.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.exz.antcargo.R;
import com.exz.antcargo.activity.adapter.ClickListAdapter;
import com.exz.antcargo.activity.adapter.LiShiGoodsInfoAdapter;
import com.exz.antcargo.activity.bean.ClickBean;
import com.exz.antcargo.activity.bean.LiShiGoodsBean;
import com.exz.antcargo.activity.bean.NewEntity;
import com.exz.antcargo.activity.utils.Constant;
import com.exz.antcargo.activity.utils.ConstantValue;
import com.exz.antcargo.activity.utils.SPutils;
import com.exz.antcargo.activity.utils.XUtilsApi;

import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016/8/24.
 * 历史足迹 货主
 */
@ContentView(R.layout.activity_lishizuju)
public class LiShiZuJiGoodsActivity extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

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

    @ViewInject(R.id.lv_01)
    private ListView lv_01;

    @ViewInject(R.id.lv_02)
    private ListView lv_02;

    @ViewInject(R.id.iv_null_data)
    private ImageView iv_null_data;

    @ViewInject(R.id.rg)
    private RadioGroup rg;
    private LiShiGoodsInfoAdapter<LiShiGoodsBean> lookListAdapter;//查看记录的adapter

    private ClickListAdapter<ClickBean> clickListAdapter;//我被点击的记录

    private Context c = LiShiZuJiGoodsActivity.this;


    @Override
    public void initView() {
        widthPixels = getResources().getDisplayMetrics().widthPixels;
        tvTiltle.setText("历史足迹");

        list = new ArrayList<View>();
        list.add(v_01);
        list.add(v_02);
        for (int i = 0; i < list.size(); i++) {
            changeWidthAndHeight(list.get(i));
        }
        ll_back.setOnClickListener(this);
        rg.setOnCheckedChangeListener(this);
        lookListAdapter = new LiShiGoodsInfoAdapter<LiShiGoodsBean>(LiShiZuJiGoodsActivity.this);
        lv_01.setAdapter(lookListAdapter);
        clickListAdapter = new ClickListAdapter<ClickBean>(LiShiZuJiGoodsActivity.this);
        lv_02.setAdapter(clickListAdapter);
        lv_01.setEmptyView(iv_null_data);
        lv_02.setEmptyView(iv_null_data);
        v_02.setVisibility(View.INVISIBLE);
        lv_02.setVisibility(View.GONE);
    }

    private void changeWidthAndHeight(View tv) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tv.getLayoutParams();
        int size = widthPixels / 2;
        layoutParams.width = size;
        tv.setLayoutParams(layoutParams);
    }

    @Override
    public void initData() {
        myLookList(Constant.SHIPPER_LOOKLIST);
        myClickList(Constant.LOOKLIST_GOODS);

    }

    private void myLookList(String url) {


        XUtilsApi xUtilsApi = new XUtilsApi();
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("AccountId", SPutils.getString(c, "accountId"));
        xUtilsApi.sendUrl(c, "post", params, true, new XUtilsApi.URLSuccessListenter() {

            @Override
            public void OnSuccess(NewEntity content, JSONObject result) {
                if (content.getResult().equals(ConstantValue.RESULT)) {
                    String optJSONObject = result.optString("info");

                    lookListAdapter.addendData(JSON.parseArray(optJSONObject, LiShiGoodsBean.class), true);
                    lv_01.setAdapter(lookListAdapter);

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
                lv_01.setVisibility(View.VISIBLE);
                lv_02.setVisibility(View.GONE);
                switch (ConstantValue.AccountType) {// 1 是货主 2 车主


                    case "1":
                        myLookList(Constant.SHIPPER_LOOKLIST);
                        break;
                    case "2":
                        break;
                }
                break;

            case R.id.rb_main_2:
                v_02.setVisibility(View.VISIBLE);
                v_01.setVisibility(View.INVISIBLE);
                lv_02.setVisibility(View.VISIBLE);
                lv_01.setVisibility(View.GONE);
                switch (ConstantValue.AccountType) {// 1 是货主 2 车主
                    case "1":
                        myClickList(Constant.LOOKLIST_GOODS);
                        break;
                    case "2":
                        break;
                }
                break;

        }
    }

    private void myClickList(String url) {
        XUtilsApi xUtilsApi = new XUtilsApi();
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("AccountId", SPutils.getString(c, "accountId"));
        xUtilsApi.sendUrl(c, "post", params, true, new XUtilsApi.URLSuccessListenter() {

            @Override
            public void OnSuccess(NewEntity content, JSONObject result) {
                if (content.getResult().equals(ConstantValue.RESULT)) {
                    String optJSONObject = result.optString("info");
                    clickListAdapter.addendData(JSON.parseArray(optJSONObject, ClickBean.class), true,"货主");
                    lv_02.setAdapter(clickListAdapter);

                } else {
                    Intent intent = new Intent(c, DiaLogActivity.class)
                            .putExtra("message", content.getMessage());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
        }
    }
}
