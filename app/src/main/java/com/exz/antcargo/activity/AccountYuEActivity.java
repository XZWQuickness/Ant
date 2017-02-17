package com.exz.antcargo.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.exz.antcargo.R;
import com.exz.antcargo.activity.bean.NewEntity;
import com.exz.antcargo.activity.utils.Constant;
import com.exz.antcargo.activity.utils.ConstantValue;
import com.exz.antcargo.activity.utils.SPutils;
import com.exz.antcargo.activity.utils.Utils;
import com.exz.antcargo.activity.utils.XUtilsApi;

import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by pc on 2016/8/26.
 * 账户余额
 */
@ContentView(R.layout.activity_account_yue)
public class AccountYuEActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.ll_back)
    private LinearLayout llBack;

    @ViewInject(R.id.ll_yue_detail)
    private LinearLayout ll_yue_detail;

    @ViewInject(R.id.ll_tixian)
    private LinearLayout ll_tixian;


    @ViewInject(R.id.tv_title)
    private TextView tv_title;

    @ViewInject(R.id.tv_pay)
    private TextView tv_pay;

    @ViewInject(R.id.myBalance)
    private TextView myBalance;


    private Context c = AccountYuEActivity.this;

    @Override
    public void initView() {
        tv_title.setText("账户余额");
        llBack.setOnClickListener(this);
        ll_yue_detail.setOnClickListener(this);
        ll_tixian.setOnClickListener(this);
        tv_pay.setOnClickListener(this);

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
                    myBalance.setText(ConstantValue.PRICE);
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
            case R.id.ll_yue_detail://余额记录
                Intent intent = new Intent(AccountYuEActivity.this, BalanceListActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_tixian://提现
                Utils.startActivityForResult(AccountYuEActivity.this, TiXianActivity.class);
                break;
            case R.id.tv_pay://余额充值
                Utils.startActivityForResult(AccountYuEActivity.this, BalanceActivity.class);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 100:
                initData();
                break;
        }

    }
}
