package com.exz.antcargo.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.exz.antcargo.R;
import com.exz.antcargo.activity.utils.Constant;
import com.exz.antcargo.activity.utils.ConstantValue;
import com.exz.antcargo.activity.utils.SPutils;
import com.exz.antcargo.activity.utils.Utils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by pc on 2016/8/23.
 * 设置
 */
@ContentView(R.layout.activity_stting)
public class SttingActivity extends BaseActivity implements View.OnClickListener {

    @ViewInject(R.id.ll_back)
    private LinearLayout llBack;

    @ViewInject(R.id.tv_title)
    private TextView tv_title;


    @ViewInject(R.id.rl_change_password)
    private RelativeLayout rl_change_password;

    @ViewInject(R.id.rl_use_help)
    private RelativeLayout rl_use_help;


    @ViewInject(R.id.rl_tel)
    private RelativeLayout rl_tel;



    @ViewInject(R.id.rl_user_xieyi)
    private RelativeLayout rl_user_xieyi;

    @ViewInject(R.id.rl_guanyu)
    private RelativeLayout rl_guanyu;


    @ViewInject(R.id.bt_finishi)
    private Button bt_finishi;


    @Override
    public void initView() {
        tv_title.setText("设置");
        llBack.setOnClickListener(this);
        bt_finishi.setOnClickListener(this);
        rl_change_password.setOnClickListener(this);
        rl_use_help.setOnClickListener(this);
        rl_user_xieyi.setOnClickListener(this);
        rl_guanyu.setOnClickListener(this);
        rl_tel.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(SttingActivity.this,WebViewActivity.class);
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.rl_tel: //客服电话
                final AlertDialog dlgtwo = new AlertDialog.Builder(SttingActivity.this)
                        .create();
                View viewtwo = LayoutInflater.from(SttingActivity.this).inflate(
                        R.layout.pop_check, null);
                dlgtwo.setView(SttingActivity.this.getLayoutInflater().inflate(
                        R.layout.pop_check, null));
                dlgtwo.show();
                dlgtwo.getWindow().setContentView(viewtwo);
                TextView queding = (TextView) viewtwo.findViewById(R.id.queding);
                TextView title = (TextView) viewtwo.findViewById(R.id.title);
                TextView quxiao = (TextView) viewtwo.findViewById(R.id.quxiao);
                title.setText("拨打" + ConstantValue.TEL);
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

                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + ConstantValue.TEL));
                        startActivity(intent);

                    }
                });
                break;

            case R.id.rl_change_password://修改密码
                Utils.startActivity(SttingActivity.this, ChangePasswordActivity.class);
                break;

            case R.id.rl_use_help:
                intent.putExtra("name","使用帮助");
                intent.putExtra("url", Constant.URL+"help.aspx");
                startActivity(intent);
                break;

            case R.id.rl_user_xieyi:
                intent.putExtra("name","用户协议");
                intent.putExtra("url", Constant.URL+"agreement.aspx");
                startActivity(intent);
                break;


            case R.id.rl_guanyu:
                intent.putExtra("name","关于我们");
                intent.putExtra("url", Constant.URL+"about.aspx");
                startActivity(intent);
                break;



            case R.id.bt_finishi://bt_finishi
                finishAffinity();
                JPushInterface.stopPush(SttingActivity.this);
                Utils.startActivity(SttingActivity.this, LoginActivity.class);
                ConstantValue.AccountType="";
                SPutils.save(SttingActivity.this,"name","");
                SPutils.save(SttingActivity.this,"password","");
                SPutils.save(SttingActivity.this,"accountId","");
                SPutils.save(SttingActivity.this,"accountType","");
                break;


        }
    }
}
