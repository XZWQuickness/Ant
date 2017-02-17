package com.exz.antcargo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.exz.antcargo.R;
import com.exz.antcargo.activity.bean.NewEntity;
import com.exz.antcargo.activity.utils.Constant;
import com.exz.antcargo.activity.utils.ConstantValue;
import com.exz.antcargo.activity.utils.PopTitleMes;
import com.exz.antcargo.activity.utils.SPutils;
import com.exz.antcargo.activity.utils.Utils;
import com.exz.antcargo.activity.utils.XUtilsApi;

import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by pc on 2016/8/22.
 * 注册
 */
@ContentView(R.layout.activity_regiter)
public class RegisterActivity extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    @ViewInject(R.id.ll_back)
    private LinearLayout llBack;

    @ViewInject(R.id.tv_title)
    private TextView tv_title;

    @ViewInject(R.id.bt_code)
    private TextView bt_code;

    @ViewInject(R.id.tv_user_xiyi)
    private TextView tv_user_xiyi;


    @ViewInject(R.id.bt_send_regiter)
    private Button bt_send_regiter;

    @ViewInject(R.id.checkBox)
    private CheckBox checkBox;


    @ViewInject(R.id.ed_code)
    private EditText ed_code;

    @ViewInject(R.id.ed_phone)
    private EditText ed_phone;


    @ViewInject(R.id.ed_password)
    private EditText ed_password;

    @ViewInject(R.id.ed_tuijianren)
    private EditText ed_tuijianren;


    @ViewInject(R.id.ed_new_password)
    private EditText ed_new_password;

    @ViewInject(R.id.ll_tuijiaren)
    private LinearLayout ll_tuijianrew;

    @ViewInject(R.id.rg)
    private RadioGroup rg;


    private String type ="";

    private String getCode;

    private Context c = RegisterActivity.this;

    private CountDownTimer countDownTimer;

    @Override
    public void initView() {
        tv_title.setText("注册");
        llBack.setOnClickListener(this);
        bt_send_regiter.setOnClickListener(this);
        tv_user_xiyi.setOnClickListener(this);
        bt_code.setOnClickListener(this);
        rg.setOnCheckedChangeListener(this);
    }

    @Override
    public void initData() {
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;

            case R.id.bt_code:
                String phone = ed_phone.getText().toString().trim();
                String code = ed_code.getText().toString().trim();
                if (Utils.textIsEmpty(phone) == false) {
                    Intent intent = new Intent(RegisterActivity.this, DiaLogActivity.class)
                            .putExtra("message", "请输入手机号码!");
                    startActivity(intent);
                    return;
                }

                if (phone.length() > 12 || phone.length() < 6) {
                    Intent intent = new Intent(RegisterActivity.this, DiaLogActivity.class)
                            .putExtra("message", "请填写正确的手机号码!");
                    startActivity(intent);
                    return;
                }
                 countDownTimer=  new CountDownTimer(120000, 1000) {

                    @Override
                    public void onTick(long l) {
                        bt_code.setClickable(false);
                        bt_code.setText(l / 1000 + "秒");
                    }

                    @Override
                    public void onFinish() {
                        bt_code.setClickable(true);
                        bt_code.setText("获取验证码");
                    }
                };
                senCode(phone);
                break;

            case R.id.bt_send_regiter://选择身份
                if (!checkBox.isChecked()) {
                    PopTitleMes.showPopSingle(RegisterActivity.this,"请同意蚂蚁用户服务协议!");
                    return;
                }
                if (TextUtils.isEmpty(type)) {
                    PopTitleMes.showPopSingle(RegisterActivity.this,"请选择注册身份!");
                    return;
                }


                if (Utils.textIsEmpty(ed_phone.getText().toString().trim()) == false) {
                    PopTitleMes.showPopSingle(RegisterActivity.this,"请输入手机号码!");
                    return;
                }

                if (ed_phone.getText().toString().trim().length() > 12 || ed_phone.getText().toString().trim().length() < 6) {
                    PopTitleMes.showPopSingle(RegisterActivity.this,"请填写正确的手机号码!");
                    return;
                }

                if (Utils.textIsEmpty(ed_code.getText().toString().trim()) == false) {

                    PopTitleMes.showPopSingle(RegisterActivity.this,"请输入验证码!");
                    return;
                }
                if (Utils.textIsEmpty(ed_password.getText().toString().trim()) == false) {
                    PopTitleMes.showPopSingle(RegisterActivity.this,"请输入密码!");
                    return;
                }
                if (ed_password.getText().toString().trim().length()<6) {
                    PopTitleMes.showPopSingle(RegisterActivity.this,"请输入六位数的密码!");
                    return;
                }
                if (!ed_new_password.getText().toString().trim().equals(ed_password.getText().toString().trim())) {
                    PopTitleMes.showPopSingle(RegisterActivity.this,"两次输入的密码不一致!");
                    return;
                }
                XUtilsApi xUtilsApi = new XUtilsApi();
                RequestParams params = new RequestParams(Constant.REGISTER);
                params.addBodyParameter("mobile", (ed_phone.getText().toString().trim().trim()));
                params.addBodyParameter("verifyCode", ed_code.getText().toString().trim()); //验证码
                params.addBodyParameter("parentmobile", ed_tuijianren.getText().toString().trim());
                params.addBodyParameter("password", ed_new_password.getText().toString().trim());
                params.addBodyParameter("accountType", type);
                xUtilsApi.sendUrl(c, "post", params, true, new XUtilsApi.URLSuccessListenter() {

                    @Override
                    public void OnSuccess(NewEntity content, JSONObject result) {
                        if (content.getResult().equals(ConstantValue.RESULT)) {
                            JSONObject optJSONObject = result.optJSONObject("info");

                            SPutils.save(c, "accountId", optJSONObject.optString("accountId"));
                            SPutils.save(c, "name", ed_phone.getText().toString().trim().trim());
                            SPutils.save(c, "password", ed_new_password.getText().toString().trim());
                            SPutils.save(c, "accountType", type);
                            ConstantValue.AccountType = type;
                            switch (type) {
                                case "":
                                    Intent intent = new Intent(RegisterActivity.this, DiaLogActivity.class);
                                    intent.putExtra("message", "亲~请选择身份进行认证!认证成功不可更改请谨慎选择哦");
                                    startActivity(intent);
                                    break;
                                case "1"://货主认证
                                    Utils.startActivity(RegisterActivity.this, ShippeAuditActivity.class);
                                    finish();
                                    break;
                                case "2"://车主认证
                                    Utils.startActivity(RegisterActivity.this, IdentityAuditActivity.class);
                                    finish();
                                    break;
                            }
                        } else {
                            Intent intent = new Intent(c, DiaLogActivity.class)
                                    .putExtra("message", content.getMessage());
                            startActivity(intent);
                        }
                    }
                });


                break;

            case R.id.tv_user_xiyi:
                Intent intent = new Intent(RegisterActivity.this, WebViewActivity.class);
                intent.putExtra("name", "用户协议");
                intent.putExtra("url", Constant.URL + "agreement.aspx");
                startActivity(intent);
                break;

        }
    }

    /*
    *
    * 发送验证码
    * */
    private void senCode(String phone) {
        XUtilsApi xUtilsApi = new XUtilsApi();
        RequestParams params = new RequestParams(Constant.SEND_CODE);
        params.addBodyParameter("mobile", phone);
        params.addBodyParameter("attribute", "1"); //1：用于注册   2：用于找回密码
        xUtilsApi.sendUrl(RegisterActivity.this, "post", params, true, new XUtilsApi.URLSuccessListenter() {

            @Override
            public void OnSuccess(NewEntity content, JSONObject result) {
                if (content.getResult().equals(ConstantValue.RESULT)) {

                    countDownTimer.start();
                    JSONObject optJSONObject = result.optJSONObject("info");
                    PopTitleMes.showPopSingle(RegisterActivity.this, content.getMessage());
                    getCode = optJSONObject.optString("verifyCode");
//                    ed_code.setText(getCode);
                } else {

                    Intent intent = new Intent(c, DiaLogActivity.class)
                            .putExtra("message", content.getMessage());
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.rb_01:
                type = "1";
                ll_tuijianrew.setVisibility(View.GONE);
                break;

            case R.id.rb_02:
                type = "2";
                ll_tuijianrew.setVisibility(View.VISIBLE);
                break;

        }
    }
}
