package com.exz.antcargo.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.exz.antcargo.R;
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

/**
 * Created by pc on 2016/8/23.
 * 余额充值
 */
@ContentView(R.layout.activity_pay)
public class BalanceActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.ll_back)
    private LinearLayout llBack;

    @ViewInject(R.id.tv_title)
    private TextView tv_title;


    @ViewInject(R.id.bt_send_pay)
    private Button bt_send_pay;

    @ViewInject(R.id.rb_zhifu)
    private RadioButton rb_zhifu;

    @ViewInject(R.id.rb_weixin)
    private RadioButton rb_weixin;

    @ViewInject(R.id.ed_pay)
    private EditText ed_pay;

    private Context c = BalanceActivity.this;

    private final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);

    @Override
    public void initView() {
        tv_title.setText("余额充值");
        ed_pay.setHint("请输入充值金额(当前账户余额" + ConstantValue.PRICE + "￥)");

        llBack.setOnClickListener(this);
        bt_send_pay.setOnClickListener(this);
        rb_zhifu.setOnClickListener(this);
        rb_weixin.setOnClickListener(this);

    }

    @Override
    public void initData() {

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
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
                    PopTitleMes.showPopSingle(BalanceActivity.this,"支付取消");
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

        if (TextUtils.isEmpty(ed_pay.getText().toString().trim())) {
            Intent intent = new Intent(c, DiaLogActivity.class)
                    .putExtra("message", "输入金额不能为空!");
            startActivity(intent);
            return;
        }
        XUtilsApi xUtilsApi = new XUtilsApi();
        RequestParams params = new RequestParams(Constant.WEIXIN_PAY);
        params.addBodyParameter("accountId", SPutils.getString(c, "accountId"));//账户id
        params.addBodyParameter("payType", "1");//充值类型 1、账户余额 2、保证金
        params.addBodyParameter("payMoney", ed_pay.getText().toString().trim());//充值金额

        xUtilsApi.sendUrl(BalanceActivity.this, "post", params, true, new XUtilsApi.URLSuccessListenter() {

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
        if (TextUtils.isEmpty(ed_pay.getText().toString().trim())) {
            Intent intent = new Intent(c, DiaLogActivity.class)
                    .putExtra("message", "输入金额不能为空!");
            startActivity(intent);
            return;
        }
        XUtilsApi xUtilsApi = new XUtilsApi();
        RequestParams params = new RequestParams(Constant.AliPayment);
        params.addBodyParameter("accountId", SPutils.getString(c, "accountId"));//账户id
        params.addBodyParameter("payType", "1");//充值类型 1、账户余额 2、保证金
        params.addBodyParameter("payMoney", ed_pay.getText().toString().trim());//充值金额

        xUtilsApi.sendUrl(BalanceActivity.this, "post", params, true, new XUtilsApi.URLSuccessListenter() {

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
                        Intent intent = new Intent(BalanceActivity.this, DiaLogActivity.class)
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
                PayTask alipay = new PayTask(BalanceActivity.this);
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
