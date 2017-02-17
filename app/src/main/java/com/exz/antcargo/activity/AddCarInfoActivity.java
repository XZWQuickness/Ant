package com.exz.antcargo.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import static com.exz.antcargo.activity.utils.ConstantValue.vehicleWidth;

/**
 * Created by pc on 2016/8/22.
 * 添加车辆信息
 */
@ContentView(R.layout.activity_add_info)
public class AddCarInfoActivity extends BaseActivity implements View.OnClickListener {

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
    @ViewInject(R.id.rl_car_long)
    private LinearLayout rl_car_long;

    @ViewInject(R.id.bt_sumbint)
    private Button bt_sumbint;

    @ViewInject(R.id.load)
    private EditText load;



    @Override
    public void initView() {
        tv_title.setText("车辆信息");
        llBack.setOnClickListener(this);
        bt_sumbint.setOnClickListener(this);
        rl_chexing.setOnClickListener(this);
        rl_car_long.setOnClickListener(this);
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
    }


    public void onClick(View view) {

        Bundle args = new Bundle();
        switch (view.getId()) {
            case R.id.ll_back:
                ConstantValue.modelsId = ""; // 车型ID
                ConstantValue.modelsName = ""; // 车型
                ConstantValue.vehicleLengthName = ""; // 车长的
                vehicleWidth = ""; // 车长的
                ConstantValue.deadweightTonnage = ""; // 载重
                finish();
                break;
            case R.id.rl_chexing://车型
                SelectCarXingIsCarLongDialog dialog = new SelectCarXingIsCarLongDialog();
                args.putString("name", "请选择车型");
                args.putString("state", "1");
                dialog.setArguments(args);
                dialog.show(getFragmentManager(), "EditNameDialog");
                break;

            case R.id.bt_sumbint:


                if(TextUtils.isEmpty(tv_chexing.getText().toString().trim())){
                    Toast.makeText(this, "请选择车型!", Toast.LENGTH_SHORT).show();
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
                if(TextUtils.isEmpty(load.getText().toString().trim())){
                    Toast.makeText(this, "请填写载重!", Toast.LENGTH_SHORT).show();
                    return;
                }

                ConstantValue.deadweightTonnage=load.getText().toString().trim();
                ConstantValue.vehicleLengthName=ed_chechang.getText().toString().trim();
                ConstantValue.vehicleWidth=ed_car_width.getText().toString().trim();
                setResult(100);
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
        ConstantValue.modelsName = ""; // 车型
        ConstantValue.vehicleLengthName = ""; // 车长的
        vehicleWidth = ""; // 车宽
        ConstantValue.deadweightTonnage = ""; // 载重
    }

    @Subscribe
    public void onEventMainThread(MainSendEvent event) {
        if (event != null&&event.getT()!=null) {
                tv_chexing.setText(ConstantValue.modelsName);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
