package com.exz.antcargo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
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
 * Created by pc on 2016/8/24.
 * 修改密码
 */
@ContentView(R.layout.activity_change_password)
public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.ll_back)
    private LinearLayout llBack;

    @ViewInject(R.id.tv_title)
    private TextView tv_title;


    @ViewInject(R.id.bt_change_password)
    private Button bt_change_password;

    @ViewInject(R.id.ed_password)
    private EditText ed_password;

    @ViewInject(R.id.ed_news_password_01)
    private EditText ed_news_password_01;

    @ViewInject(R.id.ed_news_password_02)
    private EditText ed_news_password_02;


    private Context c=ChangePasswordActivity.this;

    @Override
    public void initView() {
        tv_title.setText("修改密码");
        llBack.setOnClickListener(this);
        bt_change_password.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_back:
                finish();
                break;
            case R.id.bt_change_password://修改密码
                if(!ed_password.getText().toString().trim().equals(SPutils.getString(c,"password"))){
                    Intent intent = new Intent(c, DiaLogActivity.class)
                            .putExtra("message", "亲~您输入的密码于原密码不匹配!");
                    startActivity(intent);
                    return;
                }
                if(ed_news_password_01.getText().toString().trim().length()<6){
                    Intent intent = new Intent(c, DiaLogActivity.class)
                            .putExtra("message", "请输入大于六位数的密码!");
                    startActivity(intent);
                    return;
                }
                if(!ed_news_password_01.getText().toString().trim().equals(ed_news_password_02.getText().toString().trim())){
                    Intent intent = new Intent(c, DiaLogActivity.class)
                            .putExtra("message", "亲~两次输入的密码不一致!");
                    startActivity(intent);
                    return;
                }
                XUtilsApi xUtilsApi = new XUtilsApi();
                RequestParams params = new RequestParams(Constant.MODIFY_PASSWORD);
                params.addBodyParameter("accountId",SPutils.getString(c, "accountId"));
                params.addBodyParameter("password", ed_password.getText().toString().trim()); //旧密码
                params.addBodyParameter("newPassword", ed_news_password_02.getText().toString().trim());//新密码
                xUtilsApi.sendUrl(c, "post", params, true, new XUtilsApi.URLSuccessListenter() {

                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void OnSuccess(NewEntity content, JSONObject result) {
                        if (content.getResult().equals(ConstantValue.RESULT)) {
                            Intent intent = new Intent(c, DiaLogActivity.class)
                                    .putExtra("message", content.getMessage());
                            startActivity(intent);
                            SPutils.save(c, "password", "");
                            Utils.startActivity(c,LoginActivity.class);
                            finishAffinity();
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
