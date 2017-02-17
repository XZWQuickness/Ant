package com.exz.antcargo.activity.fragemt;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.exz.antcargo.R;
import com.exz.antcargo.activity.DiaLogActivity;
import com.exz.antcargo.activity.adapter.CarDialogAdapter;
import com.exz.antcargo.activity.bean.CarBean;
import com.exz.antcargo.activity.bean.NewEntity;
import com.exz.antcargo.activity.bean.VehicleBean;
import com.exz.antcargo.activity.utils.Constant;
import com.exz.antcargo.activity.utils.ConstantValue;
import com.exz.antcargo.activity.utils.MainSendEvent;
import com.exz.antcargo.activity.utils.XUtilsApi;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by pc on 2016/8/22.
 * 选择车型或者车长
 */
@ContentView(R.layout.dialog_car)
public class SelectCarXingIsCarLongDialog extends BaseDiaLogFragment {

    private CarDialogAdapter<? extends CarBean> adapter;

    @ViewInject(R.id.gv)
    private GridView gv;

    @ViewInject(R.id.name)
    private TextView name;


    private String state = "";
    private List<VehicleBean> list;

    @Override
    public void initView() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        String name = getArguments().getString("name");
        state = getArguments().getString("state");
        this.name.setText(name);
        adapter = new CarDialogAdapter(getActivity());
        gv.setAdapter(adapter);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                dismiss();
            }
        });

    }

    @Override
    public void initData() {
        if (state.equals("1")) {
            RequestParams p = new RequestParams(Constant.VEHICLEPARAMETERS);
            XUtilsApi xUtilsApi = new XUtilsApi();

            xUtilsApi.sendUrl(getActivity(), "post", p, false, new XUtilsApi.URLSuccessListenter() {

                @Override
                public void OnSuccess(NewEntity content, JSONObject result) {
                    if (content.getResult().equals(ConstantValue.RESULT)) {
                        JSONObject optJSONObject = result.optJSONObject("info");
                        list = JSON.parseArray(optJSONObject.optString("car"), (VehicleBean.class));
                        adapter.addendData(list, true);
                        adapter.updateAdapter();
                    } else {
                        Intent intent = new Intent(getActivity(), DiaLogActivity.class)
                                .putExtra("message", content.getMessage());
                        startActivity(intent);
                    }
                }
            });
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEventMainThread(MainSendEvent event) {
        if (event != null) {
            int pos = (int) event.getT();
            if (state.equals("1")) {
                ConstantValue.modelsId = list.get(pos).getTypeid();
                ConstantValue.modelsName = list.get(pos).getName();
                dismiss();
            }
        }
    }
}
