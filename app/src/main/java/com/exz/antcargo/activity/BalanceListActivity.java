package com.exz.antcargo.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.exz.antcargo.R;
import com.exz.antcargo.activity.adapter.BanlanceListAdapter;
import com.exz.antcargo.activity.bean.BalanceListBean;
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
 * 虚拟币记录
 */
@ContentView(R.layout.activity_bance_list)
public class BalanceListActivity extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    @ViewInject(R.id.lv)
    private ListView lv;

    private BanlanceListAdapter<BalanceListBean> adapter;

    @ViewInject(R.id.tv_title)
    private TextView tvTitle;

    @ViewInject(R.id.ll_back)
    private LinearLayout ll_back;

    private Context c = BalanceListActivity.this;

    private List<View> listLin = new ArrayList<View>();
    ;
    private int widthPixels;
    @ViewInject(R.id.v_01)
    private View v_01;

    @ViewInject(R.id.v_02)
    private View v_02;

    @ViewInject(R.id.v_03)
    private View v_03;

    @ViewInject(R.id.v_04)
    private View v_04;

    @ViewInject(R.id.rg)
    private RadioGroup rg;

    private String state = "1";

    @Override
    public void initView() {
        tvTitle.setText("余额记录");
        adapter = new BanlanceListAdapter<BalanceListBean>(BalanceListActivity.this);
        lv.setAdapter(adapter);
        ll_back.setOnClickListener(this);
        widthPixels = getResources().getDisplayMetrics().widthPixels;
        listLin.add(v_01);
        listLin.add(v_02);
        listLin.add(v_03);
        listLin.add(v_04);
        for (int i = 0; i < listLin.size(); i++) {
            changeWidthAndHeight(listLin.get(i));
        }
        rg.setOnCheckedChangeListener(this);
    }

    private void changeWidthAndHeight(View tv) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tv.getLayoutParams();
        int size = widthPixels / 4;
        layoutParams.width = size;
        tv.setLayoutParams(layoutParams);
    }

    public void initData() {


        banlanList();
    }

    private void banlanList() {
        XUtilsApi xUtilsApi = new XUtilsApi();
        RequestParams params = new RequestParams(Constant.RECORDLIST);
        params.addBodyParameter("accountId", SPutils.getString(c, "accountId"));
        params.addBodyParameter("type", state);
        xUtilsApi.sendUrl(c, "post", params, false, new XUtilsApi.URLSuccessListenter() {

            @Override
            public void OnSuccess(NewEntity content, JSONObject result) {
                if (content.getResult().equals(ConstantValue.RESULT)) {
                    adapter.addendData(JSON.parseArray(result.optString("info"), BalanceListBean.class), true,state);
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.rb_main_1:
                v_01.setVisibility(View.VISIBLE);
                v_02.setVisibility(View.INVISIBLE);
                v_03.setVisibility(View.INVISIBLE);
                v_04.setVisibility(View.INVISIBLE);
                state = "1";
                banlanList();
                break;

            case R.id.rb_main_2:
                v_02.setVisibility(View.VISIBLE);
                v_01.setVisibility(View.INVISIBLE);
                v_03.setVisibility(View.INVISIBLE);
                v_04.setVisibility(View.INVISIBLE);
                state = "2";
                banlanList();
                break;
            case R.id.rb_main_3:
                v_03.setVisibility(View.VISIBLE);
                v_01.setVisibility(View.INVISIBLE);
                v_02.setVisibility(View.INVISIBLE);
                v_04.setVisibility(View.INVISIBLE);
                state = "3";
                banlanList();
                break;
            case R.id.rb_main_4:
                v_04.setVisibility(View.VISIBLE);
                v_01.setVisibility(View.INVISIBLE);
                v_03.setVisibility(View.INVISIBLE);
                v_02.setVisibility(View.INVISIBLE);
                state = "4";
                banlanList();
                break;
        }
    }
}
