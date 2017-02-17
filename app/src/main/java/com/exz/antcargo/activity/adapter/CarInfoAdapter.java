package com.exz.antcargo.activity.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.exz.antcargo.R;
import com.exz.antcargo.activity.CarInfoActivity;
import com.exz.antcargo.activity.CarInfoDetailActivity;
import com.exz.antcargo.activity.IdentityAuditActivity;
import com.exz.antcargo.activity.LoginActivity;
import com.exz.antcargo.activity.bean.NewestCarBena;
import com.exz.antcargo.activity.utils.CheckState;
import com.exz.antcargo.activity.utils.ConstantValue;
import com.exz.antcargo.activity.utils.PopTitleMes;
import com.exz.antcargo.activity.utils.SPutils;
import com.exz.antcargo.activity.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.exz.antcargo.R.id.iv_01;
import static com.exz.antcargo.activity.utils.ConstantValue.COMPANY_STATE;
import static com.exz.antcargo.activity.utils.ConstantValue.GOODS_STATE;
import static com.exz.antcargo.activity.utils.ConstantValue.checkResult;

/**
 * Created by pc on 2016/7/26.
 */

public class CarInfoAdapter<T> extends BaseAdapter {
    private List<T> objects = new ArrayList<T>();
    private Activity context;

    public CarInfoAdapter(Activity context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return objects.size();
    }


    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return objects.size();
    }

    /**
     * 更新适配器数据
     */
    public void updateAdapter() {
        this.notifyDataSetChanged();
    }

    /**
     * 清空适配器数据
     */
    public void clearAdapter() {
        objects.clear();
    }

    /**
     * 返回适配器中的数据
     */
    public List<T> getAdapterData() {
        return objects;
    }

    /**
     * 添加多条记录
     *
     * @param alist      数据集合
     * @param isClearOld 是否清空原数据
     */
    public void addendData(List<T> alist, boolean isClearOld) {
        if (alist == null) {
            return;
        }
        if (isClearOld) {
            objects.clear();
        }
        objects.addAll(alist);

    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final List<NewestCarBena> list = (List<NewestCarBena>) objects;
        ViewHodler viewHodler = null;
        if (convertView == null) {
            viewHodler = new ViewHodler();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_car_goods_info, null);
            viewHodler.rl_newst_order = (RelativeLayout) convertView.findViewById(R.id.rl_newst_order);
            viewHodler.iv_01 = (ImageView) convertView.findViewById(iv_01);
            viewHodler.tv_createAddress = (TextView) convertView.findViewById(R.id.tv_createAddress);
            viewHodler.tv_createDate = (TextView) convertView.findViewById(R.id.tv_createDate);
            viewHodler.tv_owner_vehicle = (TextView) convertView.findViewById(R.id.tv_owner_vehicle);
            viewHodler.tv_destination = (TextView) convertView.findViewById(R.id.tv_destination);
            viewHodler.tv_margin_isnull = (TextView) convertView.findViewById(R.id.tv_margin_isnull);
            viewHodler.tv_distanceAround = (TextView) convertView.findViewById(R.id.tv_distanceAround);
            viewHodler.tv_shelvesDate = (TextView) convertView.findViewById(R.id.tv_shelvesDate);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }
        viewHodler.tv_createAddress.setText(list.get(position).getPlaceOfReceipt());//收货地
        if(!TextUtils.isEmpty(list.get(position).getEntruckingDate())&&!list.get(position).getEntruckingDate().equals(" ")){
            viewHodler.tv_createDate.setText(list.get(position).getEntruckingDate());//创建时间
        }else{
            viewHodler.tv_createDate.setText("不限");
        }

        viewHodler.tv_owner_vehicle.setText(list.get(position).getOwner_vehicle());//箱式
        if (list.get(position).getUseCarType().equals("1")) {
            viewHodler.iv_01.setBackgroundResource(R.drawable.null_car_lin);
        } else {

            viewHodler.iv_01.setBackgroundResource(R.drawable.shunfeng_car);
        }
        if (!TextUtils.isEmpty(list.get(position).getDestination())) {

            viewHodler.tv_destination.setText(list.get(position).getDestination());//目的地
        } else {
            viewHodler.tv_destination.setText("不限");//目的地
        }
        if (TextUtils.isEmpty(list.get(position).getMargin())) {//失联保证金
            viewHodler.tv_margin_isnull.setText("暂无失联保证金");//目的地

        } else {
            viewHodler.tv_margin_isnull.setText("车主已缴纳" + list.get(position).getMargin() + "元失联保证金");//目的地
        }
        if (TextUtils.isEmpty(list.get(position).getDistanceAround())) {
            viewHodler.tv_distanceAround.setText("0" + "km");//接单区域
        } else {
            viewHodler.tv_distanceAround.setText(list.get(position).getDistanceAround() + "km");//接单区域
        }

        if(!TextUtils.isEmpty(list.get(position).getShelvesDate())){
            viewHodler.tv_shelvesDate.setText(list.get(position).getShelvesDate() + "  ");//上架时间
        }else{
            viewHodler.tv_shelvesDate.setText("不限");//上架时间
        }

        viewHodler.rl_newst_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(SPutils.getString(context, "accountId"))) {

                    Utils.startActivity(context, LoginActivity.class);
                    return;
                }
                CheckState.checkCarInfo(context, ConstantValue.AccountType);

                if (ConstantValue.AccountType.equals("1")) {
//                    if(list.get(position).getIsClick().equals("true")){
                        Intent intent = new Intent(context, CarInfoDetailActivity.class);
                        intent.putExtra("id", list.get(position).getSupplyCarsId());
                        intent.putExtra("type", "1");
                        context.startActivity(intent);
//                    }else{
//                        PopTitleMes.showPopSingle(context, "超出接货范围，司机会得到提示，并主动联系您!");
//                    }

                } else {
                    PopTitleMes.showPopSingle(context, "您是车主不可以查看车主详情");
                }


            }
        });

        return convertView;
    }

    private void popCheck(String message) {

        final AlertDialog dlgtwo = new AlertDialog.Builder(context)
                .create();
        View viewtwo = LayoutInflater.from(context).inflate(
                R.layout.pop_check, null);
        dlgtwo.setView(context.getLayoutInflater().inflate(
                R.layout.pop_check, null));
        dlgtwo.show();
        dlgtwo.getWindow().setContentView(viewtwo);
        TextView queding = (TextView) viewtwo.findViewById(R.id.queding);
        TextView title = (TextView) viewtwo.findViewById(R.id.title);
        TextView quxiao = (TextView) viewtwo.findViewById(R.id.quxiao);
        title.setText(message);
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

                switch (GOODS_STATE) {
                    case "-1":
                    case "2":
                        Intent t1 = new Intent(context, IdentityAuditActivity.class);//完善信、信息
                        t1.putExtra("checkResult", checkResult);
                        context.startActivity(t1);
                        break;
                    case "1":
                        switch (COMPANY_STATE) {
                            case "-1":
                                Utils.startActivity(context, CarInfoActivity.class);
                                break;
                        }
                        break;
                }
            }
        });
    }

    class ViewHodler {

        TextView tv_createAddress;
        TextView tv_createDate;
        TextView tv_owner_vehicle;
        TextView tv_destination;
        TextView tv_margin_isnull;
        TextView tv_distanceAround;
        TextView tv_shelvesDate;
        RelativeLayout rl_newst_order;
        ImageView iv_01;

    }
}