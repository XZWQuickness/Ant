package com.exz.antcargo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.exz.antcargo.R;
import com.exz.antcargo.activity.bean.GoodsBaoZhaungBean;
import com.exz.antcargo.activity.bean.NewEntity;
import com.exz.antcargo.activity.utils.Constant;
import com.exz.antcargo.activity.utils.ConstantValue;
import com.exz.antcargo.activity.utils.XUtilsApi;

import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.picker.OptionPicker;

/**
 * Created by pc on 2016/8/25.
 * 货物信息
 */
@ContentView(R.layout.activity_goods_info)
public class GoodsInfoActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.ll_goods_baozhuang)
    private LinearLayout ll_goods_baozhuang;
    @ViewInject(R.id.tv_title)
    private TextView tv_tilte;
    @ViewInject(R.id.ll_back)
    private LinearLayout back;

    private ArrayList<String> goodsList;

    private List<GoodsBaoZhaungBean> list;

    private Context c = GoodsInfoActivity.this;


    @ViewInject(R.id.bt_sumbint)
    private Button bt_sumbint;


    @ViewInject(R.id.ed_goods_name)
    private EditText ed_goods_name;

    @ViewInject(R.id.ed_dun)
    private EditText ed_dun;

    @ViewInject(R.id.ed_fang)
    private EditText ed_fang;

    @ViewInject(R.id.ed_goods_num)
    private EditText ed_goods_num;

    @ViewInject(R.id.tv_goods_baozhaung)
    private TextView tv_goods_baozhaung;


    private String goodsName = "";
    private String goodsWeight = "";
    private String goodsVolume = "";
    private String goodsPackagingId = "";
    private String goodsCount = "";
    private GoodsBaoZhaungBean bean;

    private int position = 0;

    @Override
    public void initView() {
        tv_tilte.setText("货物信息");
        back.setOnClickListener(this);
        ll_goods_baozhuang.setOnClickListener(this);
        bt_sumbint.setOnClickListener(this);
    }

    @Override
    public void initData() {


        if (null != getIntent().getSerializableExtra("bean")) {
            bean = (GoodsBaoZhaungBean) getIntent().getSerializableExtra("bean");
            ed_goods_name.setText(bean.getGoodsName());
            ed_dun.setText(bean.getGoodsWeight());
            ed_fang.setText(bean.getGoodsVolume());
            ed_goods_num.setText(bean.getGoodsCount());
            position = Integer.parseInt(bean.getGoodsPackagingId());
            tv_goods_baozhaung.setText(bean.getName());
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;

            case R.id.ll_goods_baozhuang://货物包装

                XUtilsApi xUtilsApi = new XUtilsApi();
                RequestParams params = new RequestParams(Constant.PACKING);
                xUtilsApi.sendUrl(c, "post", params, true, new XUtilsApi.URLSuccessListenter() {

                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void OnSuccess(NewEntity content, JSONObject result) {
                        if (content.getResult().equals(ConstantValue.RESULT)) {
                            String info = result.optString("info");
                            list = JSON.parseArray(info, GoodsBaoZhaungBean.class);
                            goodsList = new ArrayList<String>();
                            for (GoodsBaoZhaungBean i : list) {
                                goodsList.add(i.getName());
                            }
                            OptionPicker optionPicker = new OptionPicker(GoodsInfoActivity.this, goodsList);
                            optionPicker.setOffset(2);//显示五条
                            optionPicker.setTextSize(14);
                            optionPicker.setSelectedIndex(position);
                            optionPicker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                                @Override
                                public void onOptionPicked(int position, String option) {
                                    tv_goods_baozhaung.setText(option);
                                    goodsPackagingId = list.get(position).getGoodspackId();

                                }
                            });
                            optionPicker.show();
                        } else {
                            Intent intent = new Intent(c, DiaLogActivity.class)
                                    .putExtra("message", content.getMessage());
                            startActivity(intent);
                        }
                    }
                });

                break;

            case R.id.bt_sumbint:
                bean = new GoodsBaoZhaungBean();
                goodsName = ed_goods_name.getText().toString().trim();
                goodsWeight = ed_dun.getText().toString().trim();
                goodsVolume = ed_fang.getText().toString().trim();
                goodsCount = ed_goods_num.getText().toString().trim();
                bean.setGoodsName(goodsName);
                bean.setGoodsWeight(goodsWeight);
                bean.setGoodsVolume(goodsVolume);
                bean.setGoodsPackagingId(goodsPackagingId);
                bean.setGoodsCount(goodsCount);
                bean.setName(tv_goods_baozhaung.getText().toString().trim());
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("bean", bean);
                intent.putExtras(bundle);
                setResult(222, intent);
                finish();

                break;

        }
    }
}
