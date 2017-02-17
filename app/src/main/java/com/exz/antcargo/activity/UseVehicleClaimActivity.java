package com.exz.antcargo.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.exz.antcargo.R;
import com.exz.antcargo.activity.fragemt.SelectCarXingIsCarLongDialog;
import com.exz.antcargo.activity.utils.ConstantValue;
import com.exz.antcargo.activity.utils.MainSendEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import static com.exz.antcargo.R.id.rb_01;
import static com.exz.antcargo.R.id.rb_02;
import static com.exz.antcargo.activity.utils.ConstantValue.vehicleWidth;

/**
 * Created by pc on 2016/8/22.
 * 用车要求
 */
@ContentView(R.layout.activity_add_info)
public class UseVehicleClaimActivity extends BaseActivity implements View.OnClickListener {

    @ViewInject(R.id.ll_back)
    private LinearLayout llBack;

    @ViewInject(R.id.tv_title)
    private TextView tv_title;

    @ViewInject(R.id.tv_chexing)
    private TextView tv_chexing;

    @ViewInject(R.id.ed_chechang)
    private EditText ed_chechang;

    @ViewInject(R.id.ed_car_width)
    private EditText ed_car_width;


    @ViewInject(R.id.rl_chexing)
    private RelativeLayout rl_chexing;

    @ViewInject(R.id.bt_sumbint)
    private Button bt_sumbint;

    @ViewInject(R.id.load)
    private EditText load;

    @ViewInject(R.id.rg)
    private RadioGroup rg;

    @Override
    public void initView() {
        tv_title.setText("用车要求");
        llBack.setOnClickListener(this);
        bt_sumbint.setOnClickListener(this);
        rl_chexing.setOnClickListener(this);
    }

    @Override
    public void initData() {
        tv_chexing.setText(ConstantValue.modelsName);
        ed_chechang.setText(ConstantValue.vehicleLengthName);
        ed_car_width.setText(ConstantValue.vehicleWidth);
        load.setText(ConstantValue.deadweightTonnage);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case rb_01:
                        ConstantValue.useCarTypeId = "1";
                        break;

                    case rb_02:
                        ConstantValue. useCarTypeId = "2";
                        break;

                }
            }
        });
        ((RadioButton) findViewById(rb_01)).setChecked(true);
    }


    public void onClick(View view) {
        SelectCarXingIsCarLongDialog dialog = new SelectCarXingIsCarLongDialog();
        Bundle args = new Bundle();
        switch (view.getId()) {
            case R.id.ll_back:
                ConstantValue.modelsId = ""; // 车型ID
                vehicleWidth = ""; // 车长的ID
                ConstantValue.modelsName = ""; // 车型
                ConstantValue.vehicleLengthName = ""; // 车长的
                ConstantValue.deadweightTonnage = ""; // 载重
                ConstantValue.useCarTypeId="";
                finish();
                break;
            case R.id.rl_chexing://车型
                args.putString("name", "请选择车型");
                args.putString("state", "1");
                dialog.setArguments(args);
                dialog.show(getFragmentManager(), "EditNameDialog");
                break;

            case R.id.bt_sumbint:
                if(TextUtils.isEmpty(ConstantValue.modelsId)){
                    Toast.makeText(this, "请选车型!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(ed_chechang.getText().toString().trim())){
                    Toast.makeText(this, "请输入车长!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(ed_car_width.getText().toString().trim())){
                    Toast.makeText(this, "请输入车宽!", Toast.LENGTH_SHORT).show();
                    return;
                }

                ConstantValue.vehicleLengthName = ed_chechang.getText().toString().trim();
                ConstantValue.vehicleWidth = ed_car_width.getText().toString().trim();
                ConstantValue.deadweightTonnage = load.getText().toString().trim();
                setResult(100);
                setResult(110);
                finish();
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ConstantValue.modelsId = ""; // 车型ID
        vehicleWidth = ""; // 车长的ID
        ConstantValue.modelsName = ""; // 车型
        ConstantValue.vehicleLengthName = ""; // 车长的
        ConstantValue.deadweightTonnage = ""; // 载重
        ConstantValue.useCarTypeId="";
    }

    @Subscribe
    public void onEventMainThread(MainSendEvent event) {
        if (event != null) {
            String state = (String) event.getStringMsgData();
            if (state.equals("1")) {
                tv_chexing.setText(ConstantValue.modelsName);
            }
        }
    }
}
