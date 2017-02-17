package com.exz.antcargo.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.exz.antcargo.R;
import com.exz.antcargo.activity.bean.NewEntity;
import com.exz.antcargo.activity.utils.Constant;
import com.exz.antcargo.activity.utils.ConstantValue;
import com.exz.antcargo.activity.utils.SPutils;
import com.exz.antcargo.activity.utils.XUtilsApi;

import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by pc on 2016/8/23.
 * 提现
 */
@ContentView(R.layout.activity_tixian)
public class TiXianActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.ll_back)
    private LinearLayout llBack;

    @ViewInject(R.id.tv_title)
    private TextView tv_title;

    @ViewInject(R.id.bt_sumbint)
    private Button bt_sumbint;


    @ViewInject(R.id.ed_cardUserName)
    private EditText ed_cardUserName;


    @ViewInject(R.id.ed_cardNumber)
    private EditText ed_cardNumber;


    @ViewInject(R.id.ed_bankName)
    private EditText ed_bankName;

    @ViewInject(R.id.ed_price)
    private EditText ed_price;


    @ViewInject(R.id.ed_mobile)
    private EditText ed_mobile;


    private Context c = TiXianActivity.this;


    @Override
    public void initView() {
        tv_title.setText("余额提现");
        llBack.setOnClickListener(this);
        bt_sumbint.setOnClickListener(this);
    }

    @Override
    public void initData() {
        XUtilsApi xUtilsApi = new XUtilsApi();
        RequestParams params = new RequestParams(Constant.GETOWNERPRICE);
        params.addBodyParameter("accountId", SPutils.getString(c, "accountId"));//账户id
        xUtilsApi.sendUrl(c, "post", params, false, new XUtilsApi.URLSuccessListenter() {

            @Override
            public void OnSuccess(NewEntity content, JSONObject result) {
                if (content.getResult().equals(ConstantValue.RESULT)) {

                    ConstantValue.MARGIN = result.optJSONObject("info").optString("margin");
                    ConstantValue.PRICE = result.optJSONObject("info").optString("price");
                    ed_price.setHint("可提现" + ConstantValue.PRICE + ",请输入提现金额");
                } else {
                    Intent intent = new Intent(c, DiaLogActivity.class)
                            .putExtra("message", content.getMessage());
                    startActivity(intent);
                }
            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;

            case R.id.bt_sumbint://

                String cardUserName = ed_cardUserName.getText().toString().trim();
                String bankName = ed_bankName.getText().toString().trim();
                String cardNumber = ed_cardNumber.getText().toString().trim();
                String price = ed_price.getText().toString().trim();
                String mobile = ed_mobile.getText().toString().trim();
                if (TextUtils.isEmpty(cardNumber) || TextUtils.isEmpty(bankName) || TextUtils.isEmpty(cardNumber) || TextUtils.isEmpty(price) || TextUtils.isEmpty(mobile)) {
                    Intent intent = new Intent(c, DiaLogActivity.class)
                            .putExtra("message","亲~请完善信息哦!");
                    startActivity(intent);
                    return;
                }






                XUtilsApi xUtilsApi = new XUtilsApi();
                RequestParams params = new RequestParams(Constant.WITHDRAWAL);
                params.addBodyParameter("accountId",SPutils.getString(c, "accountId"));//账户id
                params.addBodyParameter("cardUserName",cardUserName);//姓名
                params.addBodyParameter("cardNumber", bankName);//银行卡号
                params.addBodyParameter("bankName", cardNumber);//开户行
                params.addBodyParameter("price", price);//钱
                params.addBodyParameter("mobile",mobile);//手机号
                xUtilsApi.sendUrl(c, "post", params, true, new XUtilsApi.URLSuccessListenter() {

                    @Override
                    public void OnSuccess(NewEntity content, JSONObject result) {
                        if (content.getResult().equals(ConstantValue.RESULT)) {
                            initData();
                            Intent intent = new Intent(c, DiaLogActivity.class)
                                    .putExtra("message", content.getMessage());
                            startActivity(intent);
                            finish();

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
}
