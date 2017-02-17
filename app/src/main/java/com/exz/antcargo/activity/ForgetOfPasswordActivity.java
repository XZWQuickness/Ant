package com.exz.antcargo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
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
import com.exz.antcargo.activity.utils.Utils;
import com.exz.antcargo.activity.utils.XUtilsApi;

import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by pc on 2016/8/23.
 * 忘记密码
 */
@ContentView(R.layout.activity_forget_of_password)
public class ForgetOfPasswordActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.ll_back)
    private LinearLayout llBack;

    @ViewInject(R.id.tv_title)
    private TextView tv_title;

    @ViewInject(R.id.tv_forget_password)
    private TextView tv_forget_password;

    @ViewInject(R.id.bt_code)
    private TextView bt_code;

    @ViewInject(R.id.ed_phone)
    private EditText ed_phone;

    @ViewInject(R.id.ed_code)
    private EditText ed_code;


    @ViewInject(R.id.ed_password)
    private EditText ed_password;

    @ViewInject(R.id.ed_new_password)
    private EditText ed_new_password;

    @ViewInject(R.id.bt_send_forget_password)
    private Button bt_send_forget_password;


    private Context c = ForgetOfPasswordActivity.this;

    private String getCode;

    private CountDownTimer countDownTimer;
    @Override
    public void initView() {
        tv_title.setText("忘记密码");
        llBack.setOnClickListener(this);
        bt_send_forget_password.setOnClickListener(this);
        bt_code.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;


            case R.id.bt_send_forget_password: //提交修改

                if (Utils.textIsEmpty(ed_phone.getText().toString().trim()) == false) {
                    Intent intent = new Intent(ForgetOfPasswordActivity.this, DiaLogActivity.class)
                            .putExtra("message", "请输入手机号码!");
                    startActivity(intent);
                    return;
                }

                if (ed_phone.getText().toString().trim().length() > 12 || ed_phone.getText().toString().trim().length() < 6) {
                    Intent intent = new Intent(ForgetOfPasswordActivity.this, DiaLogActivity.class)
                            .putExtra("message", "请填写正确的手机号码!");
                    startActivity(intent);
                    return;
                }

                if (Utils.textIsEmpty(ed_code.getText().toString().trim()) == false) {
                    Intent intent = new Intent(ForgetOfPasswordActivity.this, DiaLogActivity.class)
                            .putExtra("message", "请输入验证码!");
                    startActivity(intent);
                    return;
                }
                if (Utils.textIsEmpty(ed_password.getText().toString().trim()) == false) {
                    Intent intent = new Intent(ForgetOfPasswordActivity.this, DiaLogActivity.class)
                            .putExtra("message", "请输入密码!");
                    startActivity(intent);
                    return;
                }
                if (!ed_new_password.getText().toString().trim().equals(ed_password.getText().toString().trim())) {
                    Intent intent = new Intent(ForgetOfPasswordActivity.this, DiaLogActivity.class)
                            .putExtra("message", "两次输入的密码不一致!");
                    startActivity(intent);
                    return;
                }
                XUtilsApi xUtilsApi = new XUtilsApi();
                RequestParams params = new RequestParams(Constant.FORGET_PASSWORD);
                params.addBodyParameter("mobile", (ed_phone.getText().toString().trim().trim()));
                params.addBodyParameter("verifyCode", ed_code.getText().toString().trim()); //验证码
                params.addBodyParameter("password", ed_new_password.getText().toString().trim());
                xUtilsApi.sendUrl(c, "post", params, true, new XUtilsApi.URLSuccessListenter() {

                    @Override
                    public void OnSuccess(NewEntity content, JSONObject result) {
                        if (content.getResult().equals(ConstantValue.RESULT)) {
                            Intent intent = new Intent(c, DiaLogActivity.class)
                                    .putExtra("message", content.getMessage());
                            startActivity(intent);
                            SPutils.save(c, "name", ed_phone.getText().toString().trim().trim());
                            SPutils.save(c, "password", ed_new_password.getText().toString().trim());
                            finish();
                        } else {
                            Intent intent = new Intent(c, DiaLogActivity.class)
                                    .putExtra("message", content.getMessage());
                            startActivity(intent);
                        }
                    }
                });


                break;


            case R.id.bt_code: //发送验证码
                if (Utils.textIsEmpty(ed_phone.getText().toString().trim()) == false) {
                    Intent intent = new Intent(ForgetOfPasswordActivity.this, DiaLogActivity.class)
                            .putExtra("message", "请输入手机号码!");
                    startActivity(intent);
                    return;
                }

                if (ed_phone.getText().toString().trim().length() > 12 || ed_phone.getText().toString().trim().length() < 6) {
                    Intent intent = new Intent(ForgetOfPasswordActivity.this, DiaLogActivity.class)
                            .putExtra("message", "请填写正确的手机号码!");
                    startActivity(intent);
                    return;
                }
                countDownTimer=new CountDownTimer(120000, 1000) {

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
                senCode(ed_phone.getText().toString().trim());
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
        params.addBodyParameter("attribute", "2"); //1：用于注册   2：用于找回密码
        xUtilsApi.sendUrl(ForgetOfPasswordActivity.this, "post", params, true, new XUtilsApi.URLSuccessListenter() {

            @Override
            public void OnSuccess(NewEntity content, JSONObject result) {
                if (content.getResult().equals(ConstantValue.RESULT)) {
                    JSONObject optJSONObject = result.optJSONObject("info");
                    countDownTimer.start();
                    Intent intent = new Intent(ForgetOfPasswordActivity.this, DiaLogActivity.class)
                            .putExtra("message", content.getMessage());
                    startActivity(intent);
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
}
