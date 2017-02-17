package com.exz.antcargo.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.exz.antcargo.R;
import com.exz.antcargo.activity.CarInfoDetailActivity;
import com.exz.antcargo.activity.bean.LiShiGoodsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016/7/26.
 */

public class LiShiGoodsInfoAdapter<T> extends BaseAdapter {
    private List<T> objects = new ArrayList<T>();
    private Context context;

    public LiShiGoodsInfoAdapter(Context context) {
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
        final List<LiShiGoodsBean> list = (List<LiShiGoodsBean>) objects;
        ViewHodler viewHodler = null;
        if (convertView == null) {
            viewHodler = new ViewHodler();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_car_info, null);
            viewHodler.rl_newst_order = (RelativeLayout) convertView.findViewById(R.id.rl_newst_order);
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
        viewHodler.tv_createDate.setText(list.get(position).getEntruckingDate());//创建时间
        viewHodler.tv_owner_vehicle.setText(list.get(position).getOwner_vehicle());//箱式
        if(!TextUtils.isEmpty(list.get(position).getDestination())){
            viewHodler.tv_destination.setText(list.get(position).getDestination());//目的地
        }else{
            viewHodler.tv_destination.setText("暂无");//目的地
        }
        viewHodler.tv_margin_isnull.setText("车主已缴纳" + list.get(position).getMargin() + "元失联保证金");
        viewHodler.tv_distanceAround.setText(list.get(position).getDistanceAround()+" km");
        viewHodler.tv_shelvesDate.setText(list.get(position).getShelvesDate());//上架时间
        viewHodler.rl_newst_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CarInfoDetailActivity.class);
                intent.putExtra("id", list.get(position).getSupplyCarsId());
                intent.putExtra("objectId", list.get(position).getSupplyGoodId());
                intent.putExtra("accountId", list.get(position).getAccountId());
                intent.putExtra("type", "1");//查看货源详情
                intent.putExtra("history","历史");
                context.startActivity(intent);
            }
        });
        return convertView;
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

    }
}