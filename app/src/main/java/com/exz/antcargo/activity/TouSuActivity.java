package com.exz.antcargo.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.exz.antcargo.R;
import com.exz.antcargo.activity.adapter.TouSuAdapter;
import com.exz.antcargo.activity.bean.NewEntity;
import com.exz.antcargo.activity.bean.TouSuBean;
import com.exz.antcargo.activity.utils.Constant;
import com.exz.antcargo.activity.utils.ConstantValue;
import com.exz.antcargo.activity.utils.SPutils;
import com.exz.antcargo.activity.utils.XUtilsApi;
import com.exz.antcargo.activity.view.NoScrollListView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by pc on 2016/8/25.
 * 投诉
 */
@ContentView(R.layout.activity_tousu)
public class TouSuActivity extends BaseActivity implements View.OnClickListener {

    @ViewInject(R.id.tv_title)
    private TextView tvTiltle;


    @ViewInject(R.id.ll_back)
    private LinearLayout ll_back;

    @ViewInject(R.id.bt_sumbint)
    private Button bt_sumbint;


    @ViewInject(R.id.lv)
    private NoScrollListView lv;


    private TouSuAdapter<TouSuBean> adapter;//查看记录的adapter

    private Context c = TouSuActivity.this;

    private String optionId = "";

    private List<TouSuBean> list;


    @Override
    public void initView() {
//        if (!EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().register(this);
//        }
        tvTiltle.setText(getIntent().getStringExtra("name"));
        ll_back.setOnClickListener(this);
        adapter = new TouSuAdapter<TouSuBean>(TouSuActivity.this);
        lv.setAdapter(adapter);
        bt_sumbint.setOnClickListener(this);

    }


    @Override
    public void initData() {

        //投诉
        XUtilsApi xUtilsApi = new XUtilsApi();
        RequestParams params = new RequestParams(Constant.COMPLAINT_OPTIONS);
//        params.addBodyParameter("objectId",getIntent().getStringExtra("objectId"));
        params.addBodyParameter("typeId",getIntent().getStringExtra("objectId"));
        xUtilsApi.sendUrl(c, "post", params, false, new XUtilsApi.URLSuccessListenter() {

            @Override
            public void OnSuccess(NewEntity content, JSONObject result) {
                if (content.getResult().equals(ConstantValue.RESULT)) {
                    String optJSONObject = result.optString("info");
                    list=JSON.parseArray(optJSONObject, TouSuBean.class);
                    adapter.addendData(list, true);
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

            case R.id.bt_sumbint:
                if (TextUtils.isEmpty(adapter.getCid())) {
                    Intent intent = new Intent(c, DiaLogActivity.class)
                            .putExtra("message", "请选择投诉原因!");
                    startActivity(intent);
                    return;
                }

                //提交投诉
                XUtilsApi xUtilsApi = new XUtilsApi();
                RequestParams params = new RequestParams(Constant.SUBMIT_OPTIONS);
                params.addBodyParameter("accountId", SPutils.getString(c, "accountId"));//    账户编号
                params.addBodyParameter("objectId", getIntent().getStringExtra("objectId"));//1货源 2车源
                params.addBodyParameter("fromId", getIntent().getStringExtra("id"));//    货源/车源 编号
                params.addBodyParameter("optionId", adapter.getCid());//投诉选项 编号
                xUtilsApi.sendUrl(c, "post", params, false, new XUtilsApi.URLSuccessListenter() {

                    @Override
                    public void OnSuccess(NewEntity content, JSONObject result) {
                        if (content.getResult().equals(ConstantValue.RESULT)) {
                            Intent intent = new Intent(c, DiaLogActivity.class)
                                    .putExtra("message", content.getMessage());
                            startActivity(intent);
                            TouSuActivity.this.finish();
                        } else {
                            Intent intent = new Intent(c, DiaLogActivity.class)
                                    .putExtra("message", content.getMessage());
                            startActivity(intent);
                        }
                    }
                });

                break;
        }
    }

//    @Subscribe
//    public void update(MainSendEvent event) {
//        if (event != null) {
//            optionId = event.getStringMsgData();
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
