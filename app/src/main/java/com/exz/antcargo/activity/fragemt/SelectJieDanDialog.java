package com.exz.antcargo.activity.fragemt;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.exz.antcargo.R;
import com.exz.antcargo.activity.adapter.CarDialogAdapter;
import com.exz.antcargo.activity.utils.MainSendEvent;

import org.greenrobot.eventbus.EventBus;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by pc on 2016/8/22.
 * 选择车接单区域
 */
@ContentView(R.layout.dialog_jiedan)
public class SelectJieDanDialog extends BaseDiaLogFragment {

    private CarDialogAdapter adapter;

    @ViewInject(R.id.name)
    private TextView name;

    @ViewInject(R.id.tv_queding)
    private TextView tv_queding;

    @ViewInject(R.id.tv_quxiao)
    private TextView tv_quxiao;

    @ViewInject(R.id.input)
    private EditText input;


    @Override
    public void initView() {
        String name = getArguments().getString("distanceAround");

        input.setText(name);
        input.setSelection(name.length());
        tv_queding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new MainSendEvent(input.getText().toString().trim()));
                dismiss();
            }
        });
        tv_quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    @Override
    public void initData() {

    }
}
