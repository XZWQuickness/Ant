package com.exz.antcargo.activity.fragemt;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.exz.antcargo.R;
import com.exz.antcargo.activity.DiaLogActivity;
import com.exz.antcargo.activity.adapter.WuLiuListAdapter;
import com.exz.antcargo.activity.bean.LogisticsBean;
import com.exz.antcargo.activity.bean.NewEntity;
import com.exz.antcargo.activity.utils.Constant;
import com.exz.antcargo.activity.utils.ConstantValue;
import com.exz.antcargo.activity.utils.SPutils;
import com.exz.antcargo.activity.utils.Utils;
import com.exz.antcargo.activity.utils.XUtilsApi;
import com.exz.antcargo.activity.view.DrawableCenterButton;
import com.exz.antcargo.activity.xlistView.XListView;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

import cn.qqtheme.framework.picker.AddressPicker;
import cn.qqtheme.framework.picker.DatePicker;

import static com.alibaba.fastjson.JSON.parseArray;


/**
 * Created by pc on 2016/7/14.
 * 物流
 */
@ContentView(R.layout.fragment_wuliu)
public class LogisticsFragment extends BaseFragment implements View.OnClickListener, XListView.IXListViewListener, AddressPicker.OnAddressPickListener, DialogInterface.OnDismissListener {
    @ViewInject(R.id.tv_chufadi)
    private DrawableCenterButton tv_chufadi;
    @ViewInject(R.id.tv_mudidi)
    private DrawableCenterButton tv_mudidi;
    @ViewInject(R.id.ed_keywords)
    private EditText ed_keywords;
    @ViewInject(R.id.lv)
    private XListView lv;
    private String originId = "", destinationId = "";
    private boolean isOrgin = true;
    private int page = 1;
    private WuLiuListAdapter adapter;
    private ArrayList<View> mViewArray = new ArrayList<View>();
    private boolean refresh = true;
    @ViewInject(R.id.iv_null_data)
    private ImageView iv_null_data;
    @ViewInject(R.id.bt_search)
    private Button bt_search;
    @ViewInject(R.id.ed_key_word)
    private EditText getEd_keywords;
    private String keywords = "";
    private AddressPicker chufapicker;
    private AddressPicker mudipicker;
    private AddressPicker.City area;
    private AddressPicker.County county;
    private ArrayList<AddressPicker.City> cityStr;
    private ArrayList<AddressPicker.County> counties;
    private ArrayList<AddressPicker.Province> data;

    @Override
    public void initView() {
        adapter = new WuLiuListAdapter(getActivity());
        lv.setAdapter(adapter);
        tv_chufadi.setOnClickListener(this);
        tv_mudidi.setOnClickListener(this);
        lv.setPullLoadEnable(true);
        lv.setPullRefreshEnable(true);
        lv.setXListViewListener(this);
        bt_search.setOnClickListener(this);
    }

    @Override
    public void initData() {
        infoList();
        lv.setEmptyView(iv_null_data);
        initAddressPicker();
    }

    private void initAddressPicker() {
        area = new AddressPicker.City();
        county = new AddressPicker.County();
        cityStr = new ArrayList<>();
        counties = new ArrayList<>();
        data = new ArrayList<>();
        area.setAreaName("不限");
        area.setAreaId("");
        county.setAreaName("不限");
        county.setAreaId("");
        counties.add(county);
        area.setCounties(counties);
        cityStr.add(area);
        RequestParams params = new RequestParams(Constant.WORFK_CITY);
        if (!TextUtils.isEmpty(SPutils.getString(getActivity(), "city"))) {
            initAddressData();
        } else {
            params.setMethod(HttpMethod.POST);
            x.http().post(params, new Callback.CommonCallback<JSONObject>() {
                @Override
                public void onSuccess(JSONObject result) {
                    JSONObject j = result.optJSONObject("info");
                    final String provinces = j.optString("provinces");
                    SPutils.save(getActivity(), "city", provinces);
                    initAddressData();
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                }

                @Override
                public void onCancelled(CancelledException cex) {
                }

                @Override
                public void onFinished() {
                }
            });
        }
    }

    private void initAddressData() {
        AddressPicker.Province province_ = new AddressPicker.Province();
        province_.setAreaName("不限");
        province_.setCities(cityStr);
        data.add(province_);
        for (AddressPicker.Province p : JSON.parseArray(SPutils.getString(getActivity(), "city"), AddressPicker.Province.class)) {
            AddressPicker.Province province = new AddressPicker.Province();
            province.setAreaId(p.getAreaId());
            province.setAreaName(p.getAreaName());
            AddressPicker.City city1 = new AddressPicker.City();
            city1.setAreaId("");
            city1.setAreaName("不限");
            p.getCities().add(0, city1);
            province.setCities(p.getCities());
            for (AddressPicker.City city : p.getCities()) {
                AddressPicker.County a = new AddressPicker.County();
                a.setAreaId("");
                a.setAreaName("不限");
                city.getCounties().add(0, a);
            }
            data.add(province);
        }
    }

    private void showPick() {
        if (data != null)
            if (isOrgin) {
                chufapicker = new AddressPicker(getActivity(), data);
                chufapicker.setCancelText("不限");
                chufapicker.setSelectedItem(ConstantValue.StartProvinceName, ConstantValue.StartCityName, ConstantValue.StartArea);
                chufapicker.setOnAddressPickListener(this);
                chufapicker.setOnDismissListener(this);
                chufapicker.show();
                chufapicker.setOnCancelListener(new DatePicker.OnCancelListener() {
                    @Override
                    public void cancel() {
                        setGaryOrBlue(tv_chufadi, true);
                        tv_chufadi.setText("出发地");
                        originId = "";
                        infoList();
                    }
                });
            } else {
                mudipicker = new AddressPicker(getActivity(), data);
                mudipicker.setCancelText("不限");
                mudipicker.setSelectedItem(ConstantValue.EndProvinceName, ConstantValue.EndCityName, ConstantValue.EndArea);
                mudipicker.setOnAddressPickListener(this);
                mudipicker.setOnDismissListener(this);
                mudipicker.show();
                mudipicker.setOnCancelListener(new DatePicker.OnCancelListener() {
                    @Override
                    public void cancel() {
                        setGaryOrBlue(tv_mudidi, true);
                        tv_mudidi.setText("目的地");
                        destinationId = "";
                        infoList();
                    }
                });

            }
        else {
            initAddressPicker();
            Toast.makeText(getContext(), "数据加载中请稍后", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAddressPicked(AddressPicker.Province province, AddressPicker.City city, AddressPicker.County county) {
        if (isOrgin) {
            tv_chufadi.setText(TextUtils.isEmpty(province.getAreaId()) ? "出发地" : TextUtils.isEmpty(city.getAreaId()) ? province.getAreaName() : TextUtils.isEmpty(county.getAreaId()) ? city.getAreaName() : county.getAreaName());
            originId = (TextUtils.isEmpty(province.getAreaId()) ? "" : TextUtils.isEmpty(city.getAreaId()) ? province.getAreaId() : TextUtils.isEmpty(county.getAreaId()) ? city.getAreaId() : county.getAreaId());
            ConstantValue.StartProvinceName = TextUtils.isEmpty(province.getAreaName()) ? "不限" : province.getAreaName();
            ConstantValue.StartCityName = TextUtils.isEmpty(province.getAreaName()) ? "不限" : city.getAreaName();
            ConstantValue.StartArea = TextUtils.isEmpty(province.getAreaName()) ? "不限" : county.getAreaName();
            if (TextUtils.isEmpty(originId)) {
                setGaryOrBlue(tv_chufadi, true);
            } else {
                setGaryOrBlue(tv_chufadi, false);
            }
        } else {
            tv_mudidi.setText(TextUtils.isEmpty(province.getAreaId()) ? "目的地" : TextUtils.isEmpty(city.getAreaId()) ? province.getAreaName() : TextUtils.isEmpty(county.getAreaId()) ? city.getAreaName() : county.getAreaName());
            destinationId = (TextUtils.isEmpty(province.getAreaId()) ? "" : TextUtils.isEmpty(city.getAreaId()) ? province.getAreaId() : TextUtils.isEmpty(county.getAreaId()) ? city.getAreaId() : county.getAreaId());
            ConstantValue.EndProvinceName = TextUtils.isEmpty(province.getAreaName()) ? "不限" : province.getAreaName();
            ConstantValue.EndCityName = TextUtils.isEmpty(province.getAreaName()) ? "不限" : city.getAreaName();
            ConstantValue.EndArea = TextUtils.isEmpty(province.getAreaName()) ? "不限" : county.getAreaName();
            if (TextUtils.isEmpty(destinationId)) {
                setGaryOrBlue(tv_mudidi, true);
            } else {
                setGaryOrBlue(tv_mudidi, false);
            }
        }
        infoList();
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        if (isOrgin) tv_chufadi.setChecked(false);
        else tv_mudidi.setChecked(false);
    }

    /**
     * originId	出发地 id
     * destinationId 目的地 id
     * page  第几页
     */
    private void infoList() {

        XUtilsApi xUtilsApi = new XUtilsApi();
        RequestParams params = new RequestParams(Constant.LOGISTICS_LIST);
        if (!TextUtils.isEmpty(originId)) {

            params.addBodyParameter("originId", originId);
        }
        if (!TextUtils.isEmpty(destinationId)) {

            params.addBodyParameter("destinationId", destinationId);
        }
        if (!TextUtils.isEmpty(keywords)) {

            params.addBodyParameter("keywords", keywords);
        }

        params.addBodyParameter("page", page + "");


        xUtilsApi.sendUrl(getActivity(), "post", params, true, new XUtilsApi.URLSuccessListenter() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void OnSuccess(NewEntity content, JSONObject result) {
                if (content.getResult().equals(ConstantValue.RESULT)) {
                    String info = result.optString("info");
                    adapter.addendData(parseArray(info, LogisticsBean.class), refresh);
                    adapter.updateAdapter();

                } else {
                    Intent intent = new Intent(getActivity(), DiaLogActivity.class)
                            .putExtra("message", content.getMessage());
                    startActivity(intent);
                }
            }
        });

        lv.stopRefresh();
        lv.stopLoadMore();
        lv.setRefreshTime(Utils.getCurrentHour());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_chufadi:
                isOrgin = true;
                showPick();
                break;
            case R.id.tv_mudidi:
                isOrgin = false;
                showPick();
                break;
            case R.id.bt_search:
                refresh = true;
                keywords = ed_keywords.getText().toString().trim();
                page = 1;
                infoList();
                break;
        }
    }

    /**
     * 设置灰色还是蓝色 箭头
     *
     * @param b true gary  ; false blue
     */
    private void setGaryOrBlue(RadioButton view, boolean b) {
        if (b) {
            view.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(getContext(), R.drawable.selector_gary_sanjiao), null);
            view.setTextColor(ContextCompat.getColor(getContext(), R.color.text_gray));
        } else {
            view.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(getContext(), R.drawable.selector_blue_sanjiao), null);
            view.setTextColor(ContextCompat.getColor(getContext(), R.color.blueness));
        }
    }

    @Override
    public void onRefresh() {
        page = 1;
        refresh = true;
        infoList();
    }

    @Override
    public void onLoadMore() {
        page++;
        refresh = false;
        infoList();
    }
}
