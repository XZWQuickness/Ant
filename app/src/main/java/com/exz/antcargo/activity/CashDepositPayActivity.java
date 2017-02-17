package com.exz.antcargo.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.exz.antcargo.R;
import com.exz.antcargo.activity.adapter.RechargeAmountAdapter;
import com.exz.antcargo.activity.bean.NewEntity;
import com.exz.antcargo.activity.utils.Constant;
import com.exz.antcargo.activity.utils.ConstantValue;
import com.exz.antcargo.activity.utils.MainSendEvent;
import com.exz.antcargo.activity.utils.PayResult;
import com.exz.antcargo.activity.utils.PopTitleMes;
import com.exz.antcargo.activity.utils.SPutils;
import com.exz.antcargo.activity.utils.XUtilsApi;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016/8/24.
 * 保证金充值
 */
@ContentView(R.layout.activity_cash_depositpay)
public class CashDepositPayActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.ll_back)
    private LinearLayout llBack;

    @ViewInject(R.id.tv_title)
    private TextView tv_title;

    @ViewInject(R.id.tv_margin)
    private TextView tv_margin;

    @ViewInject(R.id.tv_pay_money)
    private TextView tv_pay_money;

    @ViewInject(R.id.ll_pay)
    private LinearLayout ll_pay;


    @ViewInject(R.id.bt_send_pay)
    private Button bt_send_pay;

    @ViewInject(R.id.rb_zhifu)
    private RadioButton rb_zhifu;

    @ViewInject(R.id.rb_weixin)
    private RadioButton rb_weixin;


    @ViewInject(R.id.gv)
    private GridView gv;

    private RechargeAmountAdapter adapter;

    private Context c = CashDepositPayActivity.this;

    private Double margin, price, payMoney;


    private int priceArray[] = {
            500, 1000, 2000, 3000, 4000, 5000, 10000, 15000, 20000
    };
    private List<Integer> listPrice = new ArrayList<Integer>();

    private final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);

    @Override
    public void initView() {
        tv_title.setText("失联保证金");
        llBack.setOnClickListener(this);
        bt_send_pay.setOnClickListener(this);
        rb_zhifu.setOnClickListener(this);
        rb_weixin.setOnClickListener(this);
        adapter = new RechargeAmountAdapter(CashDepositPayActivity.this);
        gv.setAdapter(adapter);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapter.setSeclection(i);
                adapter.notifyDataSetChanged();
                payMoney = listPrice.get(i) - margin;
                tv_pay_money.setText(payMoney + "");
            }
        });
    }

    @Override
    public void initData() {

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        getPrice();
    }


    private void getPrice() {

        XUtilsApi xUtilsApi = new XUtilsApi();
        RequestParams params = new RequestParams(Constant.GET_OWNER_PRICE);
        params.addBodyParameter("accountId", SPutils.getString(c, "accountId"));//账户id

        xUtilsApi.sendUrl(c, "post", params, true, new XUtilsApi.URLSuccessListenter() {

            public void OnSuccess(NewEntity content, JSONObject resul) {
                if (content.getResult().equals(ConstantValue.RESULT)) {
                    JSONObject info = resul.optJSONObject("info");
                    margin = Double.parseDouble(info.optString("margin"));
                    price = Double.parseDouble(info.optString("price"));
                    listPrice.clear();
                    tv_margin.setText(margin + "元");
                    for (int i : priceArray) {
                        if (i > margin) {
                            listPrice.add(i);
                        }

                    }
                    adapter.addendData(listPrice, true);
                    adapter.updateAdapter();

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

            case R.id.bt_send_pay:
                if (rb_zhifu.isChecked()) {
                    zhiFuBao();
                } else {
                    weiXIn();
                }


                break;

            case R.id.rb_zhifu:
                rb_zhifu.setChecked(true);
                rb_weixin.setChecked(false);
                break;

            case R.id.rb_weixin:
                rb_zhifu.setChecked(false);
                rb_weixin.setChecked(true);
                break;


        }
    }
    @Subscribe
    public void weiXinUpdate(MainSendEvent e) {
        if (e != null&&e.getT().equals("weixin")) {
            switch (e.getStringMsgData()){
                case "-1":
                    break;

                case "-2":
                    PopTitleMes.showPopSingle(CashDepositPayActivity.this,"支付取消");
                    break;

                case "0":
                    setResult(100);
                    break;

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }

    private void weiXIn() {

        if (TextUtils.isEmpty(tv_pay_money.getText().toString().trim()) || payMoney == null) {
            Intent intent = new Intent(c, DiaLogActivity.class)
                    .putExtra("message", "请选择保证金级别!");
            startActivity(intent);
            return;
        }
        XUtilsApi xUtilsApi = new XUtilsApi();
        RequestParams params = new RequestParams(Constant.WEIXIN_PAY);
        params.addBodyParameter("accountId", SPutils.getString(c, "accountId"));//账户id
        params.addBodyParameter("payType", "2");//充值类型 1、账户余额 2、保证金
        params.addBodyParameter("payMoney",new Double(payMoney).intValue() +"");//充值金额

        xUtilsApi.sendUrl(CashDepositPayActivity.this, "post", params, true, new XUtilsApi.URLSuccessListenter() {

            public void OnSuccess(NewEntity content, JSONObject resul) {
                if (content.getResult().equals(ConstantValue.RESULT)) {

                    JSONObject info = resul.optJSONObject("info");


                    String timestamp = info.optString("timestamp");
                    String appid = info.optString("appid");
                    String partnerid = info.optString("partnerid");
                    String prepayid = info.optString("prepayid");
                    String noncestr = info.optString("noncestr");
                    String packagestr = info.optString("package");
                    String sign = info.optString("sign");
                    PayReq req = new PayReq();
                    req.appId = appid;
                    ConstantValue.APP_ID = appid;
                    req.partnerId = partnerid;
                    req.prepayId = prepayid;
                    req.packageValue = packagestr;
                    req.nonceStr = noncestr;
                    req.timeStamp = timestamp;
                    req.sign = sign;

                    msgApi.registerApp(appid);
                    msgApi.sendReq(req);

                } else {
                    Intent intent = new Intent(c, DiaLogActivity.class)
                            .putExtra("message", content.getMessage());
                    startActivity(intent);
                }
            }

        });
    }
    private void zhiFuBao() {
        if (TextUtils.isEmpty(tv_pay_money.getText().toString().trim()) || payMoney == null) {
            Intent intent = new Intent(c, DiaLogActivity.class)
                    .putExtra("message", "请选择保证金级别!");
            startActivity(intent);
            return;
        }
        XUtilsApi xUtilsApi = new XUtilsApi();
        RequestParams params = new RequestParams(Constant.AliPayment);
        params.addBodyParameter("accountId", SPutils.getString(c, "accountId"));//账户id
        params.addBodyParameter("payType", "2");//充值类型 1、账户余额 2、保证金
        params.addBodyParameter("payMoney", new Double(payMoney).intValue()+"");//充值金额

        xUtilsApi.sendUrl(CashDepositPayActivity.this, "post", params, true, new XUtilsApi.URLSuccessListenter() {

            public void OnSuccess(NewEntity content, JSONObject resul) {
                if (content.getResult().equals(ConstantValue.RESULT)) {

                    JSONObject info = resul.optJSONObject("info");
                    String sign = info.optString("sign");
                    String paymentDescription = info
                            .optString("paymentDescription");
                    pay(paymentDescription, sign);

                } else {
                    Intent intent = new Intent(c, DiaLogActivity.class)
                            .putExtra("message", content.getMessage());
                    startActivity(intent);
                }
            }

        });

    }

    private static final int SDK_PAY_FLAG = 1;
    private Handler mHandler = new Handler() {
        @SuppressLint("InflateParams")
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String mess = "";
                    final String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        setResult(100);
                        mess = "支付成功";
                        Intent intent = new Intent(CashDepositPayActivity.this, DiaLogActivity.class)
                                .putExtra("message", "支付成功");
                        startActivity(intent);
                        finish();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            mess = "支付结果确认中";
                        } else if (TextUtils.equals(resultStatus, "6001")) {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            mess = "支付被取消";
                        } else if (TextUtils.equals(resultStatus, "6002")) {
                            mess = "网络连接出错";
                        } else {
                            mess = "支付失败";
                        }
                    }


                    break;
                }
            }
        }

        ;
    };

    private void pay(String orderInfo, String sign) {
        /**
         * 完整的符合支付宝参数规范的订单信息
         */
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
                + getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(CashDepositPayActivity.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();

    }

    /**
     * get the sign type we use. 获取签名方式
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }

}
