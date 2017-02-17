package com.exz.antcargo.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.exz.antcargo.R;
import com.exz.antcargo.activity.adapter.AfficheAdapter;
import com.exz.antcargo.activity.bean.AfficheBean;
import com.exz.antcargo.activity.bean.NewEntity;
import com.exz.antcargo.activity.utils.Constant;
import com.exz.antcargo.activity.utils.ConstantValue;
import com.exz.antcargo.activity.utils.XUtilsApi;

import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by pc on 2016/9/10.
 * 公告
 */
@ContentView(R.layout.activity_affiche)
public class AfficheActivity extends BaseActivity implements View.OnClickListener {

    @ViewInject(R.id.ll_back)
    private LinearLayout ll_back;

    @ViewInject(R.id.tv_title)
    private TextView tv_title;

    @ViewInject(R.id.lv)
    private ListView lv;


    private AfficheAdapter<AfficheBean> adapter;

    private Context c = AfficheActivity.this;


    @Override
    public void initView() {
        ll_back.setOnClickListener(this);
        tv_title.setText("公告消息");
        adapter = new AfficheAdapter<AfficheBean>(AfficheActivity.this);
        lv.setAdapter(adapter);
    }


    @Override
    public void initData() {

        RequestParams params = new RequestParams(Constant.AFFICHE_LIST);
        XUtilsApi xUtilsApi = new XUtilsApi();
        xUtilsApi.sendUrl(c, "post", params, true, new XUtilsApi.URLSuccessListenter() {

            @Override
            public void OnSuccess(NewEntity content, JSONObject result) {
                if (content.getResult().equals(ConstantValue.RESULT)) {
                    String json = result.optString("info");
                    adapter.addendData(JSON.parseArray(json, AfficheBean.class), true);
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
