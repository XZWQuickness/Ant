package com.exz.antcargo.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.exz.antcargo.R;
import com.exz.antcargo.activity.adapter.AlongRoadAdapter;
import com.exz.antcargo.activity.adapter.DestinationAdapter;
import com.exz.antcargo.activity.adapter.OriginAdapter;
import com.exz.antcargo.activity.bean.LogisticsDetailebean;
import com.exz.antcargo.activity.bean.NewEntity;
import com.exz.antcargo.activity.utils.Constant;
import com.exz.antcargo.activity.utils.ConstantValue;
import com.exz.antcargo.activity.utils.SPutils;
import com.exz.antcargo.activity.utils.XUtilsApi;
import com.exz.antcargo.activity.view.NoScrollListView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * Created by pc on 2016/9/26.
 * 物流详情
 */
@ContentView(R.layout.activity_loging_detaile)
public class LogisticsDetaileActivity extends BaseActivity implements View.OnClickListener {

    @ViewInject(R.id.ll_back)
    private LinearLayout ll_back;

    @ViewInject(R.id.tv_title)
    private TextView tv_title;

    @ViewInject(R.id.tv_right)
    private TextView tv_right;


    @ViewInject(R.id.tv_content)
    private TextView tv_content;


    @ViewInject(R.id.lv_origin)
    private NoScrollListView lv_origin;

    @ViewInject(R.id.lv_alongRoad)
    private NoScrollListView lv_alongRoad;

    @ViewInject(R.id.lv_destination)
    private NoScrollListView lv_destination;

    private String url = "";

    private List<LogisticsDetailebean> list;

    private OriginAdapter<LogisticsDetailebean.OriginListEntity> lv_originAdapter;
    private DestinationAdapter<LogisticsDetailebean.DestinationListEntity> detailebeanDestinationAdapter;
    private AlongRoadAdapter<LogisticsDetailebean.AlongRoadListEntity> alongRoadAdapter;


    @Override
    public void initView() {
        tv_right.setText("投诉");
        ll_back.setOnClickListener(this);
        lv_originAdapter = new OriginAdapter<LogisticsDetailebean.OriginListEntity>(LogisticsDetaileActivity.this);
        lv_origin.setAdapter(lv_originAdapter);
        detailebeanDestinationAdapter = new DestinationAdapter<LogisticsDetailebean.DestinationListEntity>(LogisticsDetaileActivity.this);
        lv_destination.setAdapter(detailebeanDestinationAdapter);

        alongRoadAdapter = new AlongRoadAdapter<LogisticsDetailebean.AlongRoadListEntity>(LogisticsDetaileActivity.this);
        lv_alongRoad.setAdapter(alongRoadAdapter);
        tv_content.setOnClickListener(this);
        tv_right.setOnClickListener(this);

    }

    @Override
    public void initData() {
        XUtilsApi xUtilsApi = new XUtilsApi();
        RequestParams params = new RequestParams(Constant.LOGISTICSINFOR);
        params.addBodyParameter("lid", getIntent().getStringExtra("lid"));


        xUtilsApi.sendUrl(LogisticsDetaileActivity.this, "post", params, true, new XUtilsApi.URLSuccessListenter() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void OnSuccess(NewEntity content, JSONObject result) {
                if (content.getResult().equals(ConstantValue.RESULT)) {
                    String info = result.optString("info");


                    LogisticsDetailebean logisticsDetailebean = JSON.parseObject(info, LogisticsDetailebean.class);
                    tv_title.setText(logisticsDetailebean.getName());
                    lv_originAdapter.addendData(logisticsDetailebean.getOriginList(), true);
                    lv_originAdapter.updateAdapter();

                    detailebeanDestinationAdapter.addendData(logisticsDetailebean.getDestinationList(), true);
                    detailebeanDestinationAdapter.updateAdapter();


                    alongRoadAdapter.addendData(logisticsDetailebean.getAlongRoadList(), true);
                    alongRoadAdapter.updateAdapter();

                    try {
                        url = URLDecoder.decode(result.optJSONObject("info").getString("content"), "utf-8");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }


                } else {
                    Intent intent = new Intent(LogisticsDetaileActivity.this, DiaLogActivity.class)
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

            case R.id.tv_content:
                Intent intent = new Intent(LogisticsDetaileActivity.this, WebViewActivity.class);
                intent.putExtra("name", "公司详情");
                intent.putExtra("url", url);
                startActivity(intent);
                break;

            case R.id.tv_right:

                popShowToSu();
                break;
        }
    }

    private void popShowToSu() {
        final AlertDialog dlgtwo = new AlertDialog.Builder(LogisticsDetaileActivity.this)
                .create();
        View viewtwo = LayoutInflater.from(LogisticsDetaileActivity.this).inflate(
                R.layout.pop_tousu, null);
        dlgtwo.setView(LogisticsDetaileActivity.this.getLayoutInflater().inflate(
                R.layout.pop_tousu, null));
        dlgtwo.show();
        dlgtwo.getWindow().setContentView(viewtwo);
        TextView queding = (TextView) viewtwo.findViewById(R.id.queding);
        final TextView title = (TextView) viewtwo.findViewById(R.id.title);
        TextView quxiao = (TextView) viewtwo.findViewById(R.id.quxiao);
        quxiao.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dlgtwo.dismiss();

            }
        });
        queding.setOnClickListener(new View.OnClickListener() {

                                       @Override
                                       public void onClick(View arg0) {
                                           dlgtwo.dismiss();

                                           sumbintTouSu(title.getText().toString().trim());
                                       }

                                   }

        );
    }

    /*
    *
    * 提交投诉内容
    * */
    private void sumbintTouSu(String content) {
        XUtilsApi xUtilsApi = new XUtilsApi();
        RequestParams params = new RequestParams(Constant.LOGISTICS_FEEDBACK);
        params.addBodyParameter("accountId", SPutils.getString(LogisticsDetaileActivity.this, "accountId"));//    账户编号
        params.addBodyParameter("objectId", getIntent().getStringExtra("lid"));//物流编号
        params.addBodyParameter("content", content);//投诉内容
        xUtilsApi.sendUrl(LogisticsDetaileActivity.this, "post", params, false, new XUtilsApi.URLSuccessListenter() {

            @Override
            public void OnSuccess(NewEntity content, JSONObject result) {
                if (content.getResult().equals(ConstantValue.RESULT)) {
                    Intent intent = new Intent(LogisticsDetaileActivity.this, DiaLogActivity.class)
                            .putExtra("message", content.getMessage());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(LogisticsDetaileActivity.this, DiaLogActivity.class)
                            .putExtra("message", content.getMessage());
                    startActivity(intent);
                }
            }
        });


    }
}
