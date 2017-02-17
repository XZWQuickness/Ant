package com.exz.antcargo.activity.fragemt;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.exz.antcargo.R;
import com.exz.antcargo.activity.AfficheActivity;
import com.exz.antcargo.activity.CarInfoActivity;
import com.exz.antcargo.activity.CarInfoListActivity;
import com.exz.antcargo.activity.DiaLogActivity;
import com.exz.antcargo.activity.GoodsInfoListActivity;
import com.exz.antcargo.activity.IdentityAuditActivity;
import com.exz.antcargo.activity.IsuueCarActivity;
import com.exz.antcargo.activity.IsuueGoodsActivity;
import com.exz.antcargo.activity.LoginActivity;
import com.exz.antcargo.activity.ShippeAuditActivity;
import com.exz.antcargo.activity.WebViewActivity;
import com.exz.antcargo.activity.adapter.CarInfoAdapter;
import com.exz.antcargo.activity.adapter.GoodsInfoAdapter;
import com.exz.antcargo.activity.bean.BannerBean;
import com.exz.antcargo.activity.bean.NewEntity;
import com.exz.antcargo.activity.bean.NewestCarBena;
import com.exz.antcargo.activity.bean.NewestGoodsBean;
import com.exz.antcargo.activity.utils.CheckState;
import com.exz.antcargo.activity.utils.Constant;
import com.exz.antcargo.activity.utils.ConstantValue;
import com.exz.antcargo.activity.utils.MainSendEvent;
import com.exz.antcargo.activity.utils.PopTitleMes;
import com.exz.antcargo.activity.utils.SPutils;
import com.exz.antcargo.activity.utils.Utils;
import com.exz.antcargo.activity.utils.XUtilsApi;
import com.exz.antcargo.activity.view.MyScrollView;
import com.exz.antcargo.activity.view.NoScrollListView;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import razerdp.basepopup.BasePopupWindow;

import static com.exz.antcargo.activity.utils.ConstantValue.AccountType;
import static com.exz.antcargo.activity.utils.ConstantValue.CAR_NUMBER_STATE;
import static com.exz.antcargo.activity.utils.ConstantValue.CAR_STATE;
import static com.exz.antcargo.activity.utils.ConstantValue.GOODS_STATE;
import static com.exz.antcargo.activity.utils.ConstantValue.checkResult;
import static com.exz.antcargo.activity.utils.ConstantValue.checkResultMessage;


/**
 * Created by pc on 2016/7/14.
 *
 * @ContentView(R.layout.fragment_home)
 */
@ContentView(R.layout.fragment_home)
public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    @ViewInject(R.id.sw)
    private SwipeRefreshLayout mSwipeLayout;

    @ViewInject(R.id.sv)
    private MyScrollView sv;
    @ViewInject(R.id.tv_title)
    private TextView tvTiltle;

    @ViewInject(R.id.tv_left)
    private TextView tv_left;


    @ViewInject(R.id.iv_back)
    private ImageView iv_back;
    @ViewInject(R.id.ll_isuue)
    private LinearLayout ll_isuue;

    @ViewInject(R.id.car_info)
    private LinearLayout car_info;

    @ViewInject(R.id.ll_goods_info_list)
    private LinearLayout ll_goods_info_list;

    @ViewInject(R.id.ll_affiche)
    private LinearLayout ll_affiche;
    @ViewInject(R.id.ll_store)
    private LinearLayout ll_store;

    @ViewInject(R.id.iv_null_data)
    private ImageView iv_null_data;


    @ViewInject(R.id.rg)
    private RadioGroup rg;

    private CarInfoAdapter<NewestCarBena> carAdapter;
    private GoodsInfoAdapter<NewestGoodsBean> goodsAdapter;

    private boolean bootom = true;

    private BasePopupWindow basePopupWindow;

    @ViewInject(R.id.v_01)
    private View v_01;

    @ViewInject(R.id.v_02)
    private View v_02;

    @ViewInject(R.id.iv_location)
    private ImageView iv_location;

    @ViewInject(R.id.iv_right)
    private ImageView iv_right;

    @ViewInject(R.id.roll_view_pager)
    private RollPagerView mRollViewPager;


    @ViewInject(R.id.lv)
    private NoScrollListView lv;
    private List<View> list;
    private int widthPixels,heightPixels;
    List<String> mImages = new ArrayList<>();

    private TestNormalAdapter bannerAdapter;

    private List<BannerBean> listBanner;

    private String state = "1";


    @Override
    public void initView() {
        widthPixels = getResources().getDisplayMetrics().widthPixels;
        heightPixels = getResources().getDisplayMetrics().heightPixels;
        iv_back.setVisibility(View.INVISIBLE);
        iv_location.setVisibility(View.VISIBLE);
//        iv_right.setBackgroundResource(R.drawable.message_red);
        tvTiltle.setText("首页");
        tv_left.setText(ConstantValue.CityName);

        list = new ArrayList<View>();
        list.add(v_01);
        list.add(v_02);
        for (int i = 0; i < list.size(); i++) {
            changeWidthAndHeight(list.get(i));
        }
//        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mRollViewPager.getLayoutParams();
//        layoutParams.height = 320*(320/widthPixels);
//        mRollViewPager.setLayoutParams(layoutParams);


        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(R.color.yeelow,
                R.color.blueness,
                R.color.gary);
        carAdapter = new CarInfoAdapter(getActivity());
        lv.setAdapter(carAdapter);
        goodsAdapter = new GoodsInfoAdapter(getActivity());
        lv.setAdapter(goodsAdapter);
        rg.setOnCheckedChangeListener(this);
        ll_isuue.setOnClickListener(this);
        car_info.setOnClickListener(this);
        ll_goods_info_list.setOnClickListener(this);
        ll_store.setOnClickListener(this);
        ll_affiche.setOnClickListener(this);
        lv.setEmptyView(iv_null_data);
        switch (ConstantValue.AccountType) {// 1 是货主 2 车主
            case "1":
                isPassCheck(Constant.IS_PASS_CHECK);
                iv_null_data.setBackgroundResource(R.drawable.zanwucheyuan);
                EventBus.getDefault().post(new MainSendEvent("minGoodsFragment"));
                break;
            case "2":
                isPassCheck(Constant.IS_PASS_CHECK_OWNER);
                iv_null_data.setBackgroundResource(R.drawable.zanwuhuoyuan);
                EventBus.getDefault().post(new MainSendEvent("minCarFragment"));
                break;
        }

    }

    private void changeWidthAndHeight(View tv) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tv.getLayoutParams();
        int size = widthPixels / 2;
        layoutParams.width = size;
        tv.setLayoutParams(layoutParams);
    }


    @Override
    public void initData() {
        //设置播放时间间隔
        mRollViewPager.setPlayDelay(3000);
        //设置透明度
        mRollViewPager.setAnimationDurtion(500);
        getBanner();//获取banner图
        info(Constant.LATESTVEHICLELIST);

        mSwipeLayout.setEnabled(false);
    }

    private void info(String url) {
        XUtilsApi xUtilsApi = new XUtilsApi();
        RequestParams params = new RequestParams(url);
        if (state.equals("1")) {
            params.addBodyParameter("Longitude", ConstantValue.Longitude + "");
            params.addBodyParameter("Latitude", ConstantValue.Latitude + "");
            params.addBodyParameter("accountId", TextUtils.isEmpty(SPutils.getString(getActivity(), "accountId")) ? "0" : SPutils.getString(getActivity(), "accountId"));

        }

        xUtilsApi.sendUrl(getActivity(), "post", params, true, new XUtilsApi.URLSuccessListenter() {

            @Override
            public void OnSuccess(NewEntity content, JSONObject result) {
                if (content.getResult().equals(ConstantValue.RESULT)) {
                    if (content.getResult().equals(ConstantValue.RESULT)) {
                        String optJSONObject = result.optString("info");
                        if (state.equals("1")) {
                            carAdapter = new CarInfoAdapter(getActivity());
                            lv.setAdapter(carAdapter);
                            carAdapter.addendData(JSON.parseArray(optJSONObject, NewestCarBena.class), true);
                            carAdapter.updateAdapter();

                        } else if (state.equals("2")) {
                            goodsAdapter = new GoodsInfoAdapter(getActivity());
                            lv.setAdapter(goodsAdapter);
                            goodsAdapter.addendData(JSON.parseArray(optJSONObject, NewestGoodsBean.class), true, "1");
                            goodsAdapter.updateAdapter();

                        }
                    } else {
                        Intent intent = new Intent(getActivity(), DiaLogActivity.class)
                                .putExtra("message", content.getMessage());
                        startActivity(intent);
                    }

                } else {
                    Intent intent = new Intent(getActivity(), DiaLogActivity.class)
                            .putExtra("message", content.getMessage());
                    startActivity(intent);
                }
            }
        });

    }


    private void getBanner() {
        XUtilsApi xUtilsApi = new XUtilsApi();
        RequestParams params = new RequestParams(Constant.BANNER_LIST);
        xUtilsApi.sendUrl(getActivity(), "post", params, false, new XUtilsApi.URLSuccessListenter() {

            @Override
            public void OnSuccess(NewEntity content, JSONObject result) {
                if (content.getResult().equals(ConstantValue.RESULT)) {
                    if (content.getResult().equals(ConstantValue.RESULT)) {
                        String banner = result.optString("info");
                        list.clear();
                        listBanner = JSON.parseArray(banner, BannerBean.class);
                        bannerAdapter = new TestNormalAdapter(listBanner);
                        mRollViewPager.setAdapter(bannerAdapter);
                        //设置适配器
                        bannerAdapter.notifyDataSetChanged();
                    } else {
                        Intent intent = new Intent(getActivity(), DiaLogActivity.class)
                                .putExtra("message", content.getMessage());
                        startActivity(intent);
                    }

                } else {
                    Intent intent = new Intent(getActivity(), DiaLogActivity.class)
                            .putExtra("message", content.getMessage());
                    startActivity(intent);
                }
            }
        });

    }

    private class TestNormalAdapter extends StaticPagerAdapter {

        List<BannerBean> listBanner;

        public TestNormalAdapter(List<BannerBean> listBanner) {
            this.listBanner = listBanner;
        }

        @Override
        public View getView(ViewGroup container, final int position) {
            ImageView view = new ImageView(container.getContext());
            ImageLoader.getInstance().displayImage(listBanner.get(position).getImgUrl(), view);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), WebViewActivity.class);
                    intent.putExtra("name", "");
                    intent.putExtra("url", listBanner.get(position).getUrl());
                    startActivity(intent);
                }
            });

            return view;
        }


        @Override
        public int getCount() {
            return listBanner.size();
        }
    }


    @Override
    public void onRefresh() {
        getBanner();
        switch (ConstantValue.AccountType) {// 1 是货主 2 车主
            case "1":
                isPassCheck(Constant.IS_PASS_CHECK);

                break;
            case "2":
                isPassCheck(Constant.IS_PASS_CHECK_OWNER);

                break;
        }
        if (state.equals("1")) {

            info(Constant.LATESTVEHICLELIST);


        } else if (state.equals("2")) {
            info(Constant.LATESTGOODSLIST);
        }

        mSwipeLayout.setRefreshing(false);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.rb_main_1:
                v_01.setVisibility(View.VISIBLE);
                v_02.setVisibility(View.INVISIBLE);
                state = "1";


                info(Constant.LATESTVEHICLELIST);
                break;

            case R.id.rb_main_2:
                v_02.setVisibility(View.VISIBLE);
                v_01.setVisibility(View.INVISIBLE);
                state = "2";
                info(Constant.LATESTGOODSLIST);
                break;

        }
    }

    @Override
    public void onClick(View view) {

        CheckState.checkCarInfo(getActivity(), ConstantValue.AccountType);
        switch (view.getId()) {
            case R.id.ll_store:
                PopTitleMes.showPopSingle(getActivity(), "商城开发中!");
                break;

            case R.id.ll_isuue:

                if (TextUtils.isEmpty(SPutils.getString(getActivity(), "accountId"))) {

                    Utils.startActivity(getActivity(), LoginActivity.class);
                    return;
                }

                switch (SPutils.getString(getActivity(), "accountType")) {// 1 是货主 2 车主

                    case "1":
                        switch (GOODS_STATE) {

                            case "-1"://货主未提交资料
                                popCheck(checkResultMessage);
                                return;
                            case "0"://货主审核中
                                PopTitleMes.showPopSingle(getActivity(), checkResultMessage);
                                return;
                            case "1"://货主审核通过
//                                switch (COMPANY_STATE) {
//                                    case "-1"://公司未提交资料
//                                    case "2":
//                                        popCheck(checkResultMessage);
//                                        return;
//                                }
                                break;
                            case "2"://货主审核被拒
                                popCheck(checkResultMessage);
                                return;
                        }

                        Utils.startActivity(getActivity(), IsuueGoodsActivity.class); //发布货源
                        break;
                    case "2":
                        switch (CAR_STATE) {

                            case "-1"://车主未提交资料
                                popCheck(checkResultMessage);
                                return;
                            case "0"://车主审核中
                                PopTitleMes.showPopSingle(getActivity(), checkResultMessage);
                                return;
                            case "1"://车主审核通过
                                switch (CAR_NUMBER_STATE) {
                                    case "-1"://车辆资料
                                        popCheck(checkResultMessage);
                                        return;
                                }
                                break;
                            case "2"://车主审核被拒
                                popCheck(checkResultMessage);
                                return;
                        }
                        Utils.startActivity(getActivity(), IsuueCarActivity.class); //发布车源
                        break;
                }


                break;
            case R.id.car_info://车源信息
                if (TextUtils.isEmpty(SPutils.getString(getActivity(), "accountId"))) {

                    Utils.startActivity(getActivity(), LoginActivity.class);
                    return;
                }
                Utils.startActivity(getActivity(), CarInfoListActivity.class);
                break;
            case R.id.ll_goods_info_list://货源信息
                if (TextUtils.isEmpty(SPutils.getString(getActivity(), "accountId"))) {

                    Utils.startActivity(getActivity(), LoginActivity.class);
                    return;
                }

                Utils.startActivity(getActivity(), GoodsInfoListActivity.class);
                break;

            case R.id.ll_affiche:
                Utils.startActivity(getActivity(), AfficheActivity.class);
                break;
        }
    }

 /*
    * 审核结果接口
    *
    * checkState'-1:未提交审核信息 0未审核(审核中) 1审核通过 2拒绝 3禁用
    * */

    private void isPassCheck(String url) {

        XUtilsApi xUtilsApi = new XUtilsApi();
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("mobile", SPutils.getString(getActivity(), "name"));
        params.addBodyParameter("password", SPutils.getString(getActivity(), "password"));
        xUtilsApi.sendUrl(getActivity(), "post", params, false, new XUtilsApi.URLSuccessListenter() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void OnSuccess(NewEntity content, JSONObject result) {
                Intent intent = new Intent(getActivity(), DiaLogActivity.class);

                if (content.getResult().equals(ConstantValue.RESULT)) {
                    JSONObject optJSONObject = result.optJSONObject("info");

                    SPutils.save(getActivity(), "accountId", optJSONObject.optString("accountId"));

                    if (AccountType.equals("1")) {
                        GOODS_STATE = optJSONObject.optString("checkState");
                    } else {
                        CAR_STATE = optJSONObject.optString("checkState");

                    }
                    switch (optJSONObject.optString("checkState")) {//checkState'-1:未提交审核信息 0未审核(审核中) 1审核通过 2拒绝 3禁用
                        case "-1"://未提交审核信息
                            ConstantValue.checkResultMessage = "亲~您没有提交认证信息哦!";
                            popCheck(ConstantValue.checkResultMessage);
                            break;
                        case "0"://0未审核(审核中)
                            ConstantValue.checkResultMessage = "亲~正在审核中哦!";
                            intent.putExtra("message", optJSONObject.optJSONObject("checkResult").optString("message"));
                            startActivity(intent);
                            break;
                        case "1":
                            if (ConstantValue.AccountType.equals("1")) {
                                EventBus.getDefault().post(new MainSendEvent("minGoodsFragment"));

                            } else {
                                EventBus.getDefault().post(new MainSendEvent("minCarFragment"));
                            }
                            break;


                        case "2"://2拒绝
                            ConstantValue.checkResultMessage = "亲~您提交认证信息没有通过哦!";
                            checkResult = optJSONObject.optString("checkResult");
                            popCheck(checkResultMessage);
                            break;

                        case "3":// 3禁用
                            Utils.startActivity(getActivity(), LoginActivity.class);
                            intent.putExtra("message", optJSONObject.optJSONObject("checkResult").optString("message"));
                            startActivity(intent);
                            break;

                    }


                } else {

                    intent.putExtra("message", content.getMessage());
                    startActivity(intent);
                }
            }
        });
    }

    private void popCheck(String message) {

        final AlertDialog dlgtwo = new AlertDialog.Builder(getActivity())
                .create();
        View viewtwo = LayoutInflater.from(getActivity()).inflate(
                R.layout.pop_check, null);
        dlgtwo.setView(getActivity().getLayoutInflater().inflate(
                R.layout.pop_check, null));
        dlgtwo.show();
        dlgtwo.getWindow().setContentView(viewtwo);
        TextView queding = (TextView) viewtwo.findViewById(R.id.queding);
        TextView title = (TextView) viewtwo.findViewById(R.id.title);
        TextView quxiao = (TextView) viewtwo.findViewById(R.id.quxiao);
        title.setText(message);
        queding.setText("认证");
        quxiao.setText("暂不");
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
                switch (ConstantValue.AccountType) {// 1 是货主 2 车主
                    case "1":

                        switch (GOODS_STATE) {
                            case "-1":
                            case "2":
                                Intent t = new Intent(getActivity(), ShippeAuditActivity.class);//完善信、信息
                                t.putExtra("checkResult", checkResult);
                                startActivity(t);
                                break;
                            case "1":
//                                switch (COMPANY_STATE) {
//                                    case "-1":
//                                    case "2":
//                                        Intent gongsi = new Intent(getActivity(), GongSiRenZhengActivity.class);
//                                        gongsi.putExtra("state", "0");
//                                        startActivityForResult(gongsi, 100);
//                                        break;
//                                }
                                break;

                        }
                        break;
                    case "2":
                        switch (CAR_STATE) {
                            case "-1":
                            case "2":
                                Intent t1 = new Intent(getActivity(), IdentityAuditActivity.class);//完善信、信息
                                t1.putExtra("checkResult", checkResult);
                                startActivity(t1);
                                break;
                            case "1":
                                switch (CAR_NUMBER_STATE) {
                                    case "-1":
                                        Utils.startActivity(getActivity(), CarInfoActivity.class);
                                        break;
                                }
                                break;
                        }
                        break;
                }
            }
        });
    }

}
