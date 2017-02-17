package com.exz.antcargo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import cn.jpush.android.api.JPushInterface;

/**
 * Created by pc on 2016/8/22.
 * 登录
 */
@ContentView(R.layout.activity_loging)
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.ll_back)
    private LinearLayout llBack;

    @ViewInject(R.id.tv_title)
    private TextView tv_title;

    @ViewInject(R.id.tv_forget_password)
    private TextView tv_forget_password;

    @ViewInject(R.id.iv_back)
    private ImageView iv_back;


    @ViewInject(R.id.bt_register)
    private Button bt_register;
    @ViewInject(R.id.bt_login)
    private Button bt_login;

    @ViewInject(R.id.ed_phonet_num)
    private EditText ed_phonet_num;

    @ViewInject(R.id.ed_ed_password)
    private EditText ed_password;
    private Context c = LoginActivity.this;

    @ViewInject(R.id.tv_tel)
    private TextView tv_tel;

    @Override
    public void initView() {

        if (!TextUtils.isEmpty(SPutils.getString(c, "name"))) {
            ed_phonet_num.setText(SPutils.getString(c, "name"));
        }
        if (!TextUtils.isEmpty(SPutils.getString(c, "password"))) {
            ed_password.setText(SPutils.getString(c, "password"));
        }
        tv_title.setText("登录");
        llBack.setOnClickListener(this);
        iv_back.setVisibility(View.VISIBLE);
        bt_register.setOnClickListener(this);
        bt_login.setOnClickListener(this);
        tv_forget_password.setOnClickListener(this);
        tv_tel.setText("客服电话 "+ConstantValue.TEL);
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
            case R.id.bt_login://登录
                final String phone = ed_phonet_num.getText().toString().trim();
                final String password = ed_password.getText().toString().trim();
                if (Utils.textIsEmpty(phone) == false) {
                    PopTitleMes.showPopSingle(LoginActivity.this,"请输入手机号码!");
                    return;
                }

                if (Utils.textIsEmpty(password) == false) {
                    PopTitleMes.showPopSingle(LoginActivity.this,"请输入密码!");
                    return;
                }

                XUtilsApi xUtilsApi = new XUtilsApi();
                RequestParams params = new RequestParams(Constant.LOGIN);
                params.addBodyParameter("mobile", phone);
                params.addBodyParameter("password", password);
                params.addBodyParameter("deviceType", "1"); //1 Android 2 是 苹果
                params.addBodyParameter("registrationID", JPushInterface.getRegistrationID(this));
                xUtilsApi.sendUrl(c, "post", params, true, new XUtilsApi.URLSuccessListenter() {

                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void OnSuccess(NewEntity content, JSONObject result) {
                        Intent intent = new Intent(c, DiaLogActivity.class);
                        if (content.getResult().equals(ConstantValue.RESULT)) {
                            SPutils.save(c, "name", phone);
                            SPutils.save(c, "password", password);
                            JSONObject optJSONObject = result.optJSONObject("info");
                            SPutils.save(c, "accountId", optJSONObject.optString("accountId"));
                            SPutils.save(c, "accountType", optJSONObject.optString("accountType"));
                            ConstantValue.AccountType = optJSONObject.optString("accountType");
                            ConstantValue.AccountState = optJSONObject.optString("accountState");
                            switch (ConstantValue.AccountState) {
                                case "-1": //没有提交
                                    if (TextUtils.isEmpty(optJSONObject.optString("reason"))) {
                                        intent.putExtra("message", "亲~您没有提交资料哦!");
                                    } else {

                                        intent.putExtra("message", optJSONObject.optString("reason"));
                                    }
                                    startActivityForResult(intent, 210);
                                    break;
                                case "0":
                                case "1": //审核通过
                                    finishAffinity();
                                    JPushInterface.resumePush(LoginActivity.this);
                                    Utils.startActivity(LoginActivity.this, MainActivity.class);
                                    break;
                                case "2": //审核拒绝
                                    if (TextUtils.isEmpty(optJSONObject.optString("reason"))) {
                                        intent.putExtra("message", "亲~您审核被拒绝了!");
                                    } else {

                                        intent.putExtra("message", optJSONObject.optString("reason"));
                                    }

                                    startActivityForResult(intent, 210);
                                    break;
                                case "3": //审核禁用
                                    if (TextUtils.isEmpty(optJSONObject.optString("reason"))) {
                                        intent.putExtra("message", "亲~您账号被禁用了!");
                                    } else {
                                        intent.putExtra("message", optJSONObject.optString("reason"));
                                    }
                                    startActivity(intent);
                                    break;

                            }

                        } else {
                            intent.putExtra("message", content.getMessage());
                            startActivity(intent);
                        }
                    }
                });
                break;
            case R.id.bt_register://注册
                Utils.startActivity(LoginActivity.this, RegisterActivity.class);
                break;

            case R.id.tv_forget_password://忘记密码
                Utils.startActivity(LoginActivity.this, ForgetOfPasswordActivity.class);
                break;

        }
    }
 /*
    * 审核结果接口
    *
    * checkState'-1:未提交审核信息 0未审核(审核中) 1审核通过 2拒绝 3禁用
    * */

    private void isPassCheck(final String phone, final String password, String url) {

        XUtilsApi xUtilsApi = new XUtilsApi();
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("mobile", phone);
        params.addBodyParameter("password", password);
        xUtilsApi.sendUrl(c, "post", params, false, new XUtilsApi.URLSuccessListenter() {

            @Override
            public void OnSuccess(NewEntity content, JSONObject result) {
                if (content.getResult().equals(ConstantValue.RESULT)) {
                    JSONObject optJSONObject = result.optJSONObject("info");
                    Intent intent = new Intent(c, DiaLogActivity.class);
                    if (optJSONObject.optString("checkState").equals("2") || optJSONObject.optString("checkState").equals("-1")) {


                        switch (ConstantValue.AccountType) {// 1 是货主 2 车主
                            case "1":
                                Intent t = new Intent(c, ShippeAuditActivity.class);//完善信、信息
                                if (optJSONObject.optString("checkState").equals("2")) {

                                    t.putExtra("checkResult", optJSONObject.optString("checkResult"));
                                }
                                startActivity(t);
                                break;
                            case "2":
                                Intent t1 = new Intent(c, IdentityAuditActivity.class);//完善信、信息
                                if (optJSONObject.optString("checkState").equals("2")) {

                                    t1.putExtra("checkResult", optJSONObject.optString("checkResult"));
                                }
                                startActivity(t1);
                                break;
                        }
                    }

                } else {
                    Intent intent = new Intent(c, DiaLogActivity.class)
                            .putExtra("message", content.getMessage());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 210:
                switch (ConstantValue.AccountType) {// 1 是货主 2 车主
                    case "1":
                        isPassCheck(ed_phonet_num.getText().toString().trim(), ed_password.getText().toString().trim(), Constant.IS_PASS_CHECK);
                        break;
                    case "2":
                        isPassCheck(ed_phonet_num.getText().toString().trim(), ed_password.getText().toString().trim(), Constant.IS_PASS_CHECK_OWNER);
                        break;
                }
                break;
            case 200:
                Utils.startActivity(c, MainActivity.class);
                break;
        }

    }

}
