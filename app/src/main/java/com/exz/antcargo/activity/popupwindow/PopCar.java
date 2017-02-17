package com.exz.antcargo.activity.popupwindow;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.CycleInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.exz.antcargo.R;
import com.exz.antcargo.activity.adapter.PopCarAdapter;
import com.exz.antcargo.activity.bean.CarManageBean;
import com.exz.antcargo.activity.utils.MainSendEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import razerdp.basepopup.BasePopupWindow;

/**
 * Created by pc on 2016/9/14.
 */

public class PopCar extends BasePopupWindow {

    PopCarAdapter<CarManageBean> popGoodsAdapter;
    private ListView lv;

    private TextView tv_quxiao, tv_queding;

    private String supplyCarId = "";

    private TextView tv_title;


    public PopCar(Activity context, List<CarManageBean> list) {
        super(context);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        lv = (ListView) findViewById(R.id.lv);
        tv_quxiao = (TextView) findViewById(R.id.tv_quxiao);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_queding = (TextView) findViewById(R.id.tv_queding);
        popGoodsAdapter = new PopCarAdapter<CarManageBean>(context);
        popGoodsAdapter.addendData(list, true);
        lv.setAdapter(popGoodsAdapter);
        tv_title.setText("选择车源");
        int height = context.getResources().getDisplayMetrics().heightPixels >> 2;
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (list.size() > 1) {

            layoutParams.setMargins(0, height + (height >> 1), 0, 0);//4个参数按顺序分别是左上右下
        } else {
            layoutParams.setMargins(0, (int) (height*2.5), 0, 0);//4个参数按顺序分别是左上右下
        }
        tv_title.setLayoutParams(layoutParams);
        tv_title.setWidth(context.getResources().getDisplayMetrics().widthPixels);
//
    }


    @Subscribe
    public void update(MainSendEvent e) {
        if (e != null) {
            supplyCarId = e.getStringMsgData();
            dismiss();
        }
    }


    public PopCar(Activity context, int w, int h) {
        super(context, w, h);
    }

    @Override
    protected Animation getShowAnimation() {
        AnimationSet set = new AnimationSet(false);
        Animation shakeAnima = new RotateAnimation(0, 15, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        shakeAnima.setInterpolator(new CycleInterpolator(5));
        shakeAnima.setDuration(400);
        set.addAnimation(getDefaultAlphaAnimation());
        set.addAnimation(shakeAnima);
        return set;
    }

    @Override
    protected View getClickToDismissView() {
        return mPopupView;
    }

    @Override
    public View getPopupView() {
        return getPopupViewById(R.layout.pop_release);
    }

    @Override
    public View getAnimaView() {
        return null;
    }

}
