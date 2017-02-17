package com.exz.antcargo.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.exz.antcargo.R;
import com.exz.antcargo.activity.adapter.XuNiBiListAdapter;
import com.exz.antcargo.activity.bean.NewEntity;
import com.exz.antcargo.activity.bean.XuNiBiListBean;
import com.exz.antcargo.activity.utils.Constant;
import com.exz.antcargo.activity.utils.ConstantValue;
import com.exz.antcargo.activity.utils.SPutils;
import com.exz.antcargo.activity.utils.XUtilsApi;

import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by pc on 2016/8/24.
 * 虚拟币记录
 */
@ContentView(R.layout.activity_xunibi)
public class XuNiBiActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.lv)
    private ListView lv;

    private XuNiBiListAdapter adapter;

    @ViewInject(R.id.tv_title)
    private TextView tvTitle;

    @ViewInject(R.id.ll_back)
    private LinearLayout ll_back;

    private Context c = XuNiBiActivity.this;

    @Override
    public void initView() {
        tvTitle.setText("虚拟币记录");
        adapter = new XuNiBiListAdapter(XuNiBiActivity.this);
        lv.setAdapter(adapter);
        ll_back.setOnClickListener(this);


    }

    public void initData() {


        XUtilsApi xUtilsApi = new XUtilsApi();
        RequestParams params = new RequestParams(Constant.VIRTUALCURRENCYRECORD);
        params.addBodyParameter("accountId", SPutils.getString(c, "accountId"));
        xUtilsApi.sendUrl(c, "post", params, false, new XUtilsApi.URLSuccessListenter() {

            @Override
            public void OnSuccess(NewEntity content, JSONObject result) {
                if (content.getResult().equals(ConstantValue.RESULT)) {
                    adapter.addendData(JSON.parseArray(result.optString("info"), XuNiBiListBean.class), true);
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
}
