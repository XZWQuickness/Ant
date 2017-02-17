package com.exz.antcargo.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.exz.antcargo.R;
import com.exz.antcargo.activity.adapter.YanTuLuMingAdapter;
import com.exz.antcargo.activity.view.NoScrollListView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016/8/31.
 * 沿途路名
 */
@ContentView(R.layout.activity_yantuluming)
public class YanTuLuMing extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.tv_title)
    private TextView tvTiltle;

    @ViewInject(R.id.tv_right)
    private TextView tv_right;

    @ViewInject(R.id.ed_input_name)
    private EditText ed_input_name;


    @ViewInject(R.id.ll_back)
    private LinearLayout ll_back;

    @ViewInject(R.id.lv)
    private NoScrollListView lv;

    @ViewInject(R.id.bt_sumbint)
    private Button bt_sumbint;

    private String yantu = "";


    private YanTuLuMingAdapter<String> adapter;//查看记录的adapter

    List<String> list = new ArrayList<String>();

    @Override
    public void initView() {

        tvTiltle.setText("编辑路名");
        ll_back.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        tv_right.setText("添加");
        adapter = new YanTuLuMingAdapter<String>(YanTuLuMing.this);
        lv.setAdapter(adapter);
        lv.setItemsCanFocus(true);
        if (!TextUtils.isEmpty(getIntent().getStringExtra("yantu"))) {
            String[] yantus = getIntent().getStringExtra("yantu").split(",");
            for (String i : yantus) {

                list.add(i);
            }

        }
        adapter.addendData(list, true);
        adapter.updateAdapter();
        bt_sumbint.setOnClickListener(this);

    }


    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                adapter.getAdapterData();
                if( adapter.getAdapterData().size()<5){
                    if(!TextUtils.isEmpty(ed_input_name.getText().toString().trim())){
                        yantu+=ed_input_name.getText().toString().trim()+",";
                    }
                    for(String i:adapter.getAdapterData()){
                        yantu += i + ",";
                    }

                }else if(adapter.getAdapterData().size()==5){
                    for(String i:adapter.getAdapterData()){
                        yantu += i + ",";
                    }
                }else if(adapter.getAdapterData().size()==0){
                    yantu=ed_input_name.getText().toString().trim();
                }
                setResult(0x1100, new Intent().putExtra("yantu", yantu.substring(0, yantu.length() - 1).equals("")?"": yantu.substring(0, yantu.length() - 1)));


                finish();
                break;

            case R.id.tv_right:
                if(list.size()>4){
                    Toast.makeText(this, "最多输入5个沿途路名!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(ed_input_name.getText().toString().trim())){
                    Toast.makeText(this, "输入项不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }

                list.add(ed_input_name.getText().toString().trim());
                adapter.updateAdapter();
                ed_input_name.setText("");
                break;

            case R.id.bt_sumbint:
                adapter.getAdapterData();
                if( adapter.getAdapterData().size()<5){
                    if(!TextUtils.isEmpty(ed_input_name.getText().toString().trim())){
                        yantu+=ed_input_name.getText().toString().trim()+",";
                    }
                    for(String i:adapter.getAdapterData()){
                        yantu += i + ",";
                    }

                }else if(adapter.getAdapterData().size()==5){
                    for(String i:adapter.getAdapterData()){
                        yantu += i + ",";
                    }
                }else if(adapter.getAdapterData().size()==0){
                    yantu=ed_input_name.getText().toString().trim();
                }
                setResult(0x1100, new Intent().putExtra("yantu", yantu.substring(0, yantu.length() - 1).equals("")?"": yantu.substring(0, yantu.length() - 1)));



                finish();
                break;


        }
    }


}
