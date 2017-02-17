package com.exz.antcargo.activity.fragemt;

import android.view.View;
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
@ContentView(R.layout.dialog_tainxie_address)
public class TianXieAddressDialog extends BaseDiaLogFragment {

    private CarDialogAdapter adapter;

    @ViewInject(R.id.name)
    private TextView name;

    @ViewInject(R.id.tv_queding)
    private TextView tv_queding;

    @ViewInject(R.id.tv_quxiao)
    private TextView tv_quxiao;

    @ViewInject(R.id.ed_input_address)
    private TextView ed_input_address;




    @Override
    public void initView() {
        tv_queding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    EventBus.getDefault().post(new MainSendEvent(ed_input_address.getText().toString()));
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
