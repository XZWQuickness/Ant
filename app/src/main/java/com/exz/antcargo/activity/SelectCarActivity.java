package com.exz.antcargo.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.exz.antcargo.R;
import com.exz.antcargo.activity.adapter.SelectCarAdapter;
import com.exz.antcargo.activity.bean.NewEntity;
import com.exz.antcargo.activity.bean.SelectCarBean;
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

import static com.alibaba.fastjson.JSON.parseArray;

/**
 * Created by pc on 2016/8/25.
 * 选择车辆
 */
@ContentView(value = R.layout.activity_select_car)
public class SelectCarActivity extends BaseActivity implements View.OnClickListener {

    private Context c=SelectCarActivity.this;

    @ViewInject(R.id.tv_title)
    private TextView tvTiltle;


    @ViewInject(R.id.ll_back)
    private LinearLayout ll_back;

    @ViewInject(R.id.lv)
    private ListView lv;


    private SelectCarAdapter<SelectCarBean> adapter;//查看记录的adapter
    private List<SelectCarBean> list = new ArrayList<SelectCarBean>();


    @Override
    public void initView() {
        tvTiltle.setText("车辆选择");

        ll_back.setOnClickListener(this);
        adapter = new SelectCarAdapter<SelectCarBean>(SelectCarActivity.this);
        lv.setAdapter(adapter);
    }


    @Override
    public void initData() {
        XUtilsApi xUtilsApi = new XUtilsApi();
        RequestParams params = new RequestParams(Constant.VEHICLESELECTLIST);
        params.addBodyParameter("AccountId", SPutils.getString(c, "accountId"));
        params.addBodyParameter("type", "1");//- 1 所有 1认证过

        xUtilsApi.sendUrl(SelectCarActivity.this, "post", params, false, new XUtilsApi.URLSuccessListenter() {

            @Override
            public void OnSuccess(NewEntity content, JSONObject result) {
                if (content.getResult().equals(ConstantValue.RESULT)) {
                    String json = result.optString("info");
                    list = parseArray(json, SelectCarBean.class);

                    adapter.addendData(list, true);
                    adapter.updateAdapter();

                } else {
                    Intent intent = new Intent(SelectCarActivity.this, DiaLogActivity.class)
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
