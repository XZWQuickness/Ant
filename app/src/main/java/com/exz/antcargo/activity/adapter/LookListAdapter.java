package com.exz.antcargo.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.exz.antcargo.R;
import com.exz.antcargo.activity.GoosdInfoDetailActivity;
import com.exz.antcargo.activity.bean.MyLookListBean;
import com.exz.antcargo.activity.utils.ConstantValue;

import java.util.ArrayList;
import java.util.List;

import static com.exz.antcargo.R.id.iv_01;

/**
 * Created by pc on 2016/8/22.
 */

public class LookListAdapter<T> extends BaseAdapter {
    private List<T> objects = new ArrayList<T>();
    private Context context;

    public LookListAdapter(Context context) {
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

        ViewHodler viewHodler = null;
        if (convertView == null) {
            viewHodler = new ViewHodler();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_look_list, null);
            viewHodler.rl_account_type = (RelativeLayout) convertView.findViewById(R.id.rl_account_type);
            viewHodler.ll_view = (LinearLayout) convertView.findViewById(R.id.ll_view);
            viewHodler.iv_01 = (ImageView) convertView.findViewById(iv_01);
            viewHodler.iv_02 = (ImageView) convertView.findViewById(R.id.iv_02);
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
        final List<MyLookListBean> list = (List<MyLookListBean>) objects;
        viewHodler.tv_createAddress.setText(list.get(position).getOrigin());//收货地
        viewHodler.tv_createDate.setText(list.get(position).getEntruckingDate());//创建时间
        viewHodler.tv_owner_vehicle.setText(list.get(position).getGoodsType());//箱式
        viewHodler.tv_destination.setText(list.get(position).getDestination());//目的地
        viewHodler.tv_shelvesDate.setText(list.get(position).getShelvesDate());//上架时间

        switch (ConstantValue.AccountType) {// 1 是货主 2 车主
            case "1":
                viewHodler.rl_account_type.setVisibility(View.VISIBLE);
                viewHodler.iv_01.setBackgroundResource(R.drawable.goods_lin);
                viewHodler.iv_02.setBackgroundResource(R.drawable.look_goods);

                break;

            case "2":

                viewHodler.iv_01.setBackgroundResource(R.drawable.goods_lin);
                viewHodler.iv_02.setBackgroundResource(R.drawable.look_goods);
                viewHodler.rl_account_type.setVisibility(View.GONE);


                break;

        }
        viewHodler.ll_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //货主详情

                Intent intent = new Intent(context, GoosdInfoDetailActivity.class);
                intent.putExtra("supplyGoodsId", list.get(position).getGoodsid());
                intent.putExtra("objectId", list.get(position).getSupplyCarsId());
                intent.putExtra("accountId", list.get(position).getAccountId());
                intent.putExtra("type", "1");
                intent.putExtra("history", "历史");
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
        RelativeLayout rl_account_type;
        LinearLayout ll_view;
        ImageView iv_01;
        ImageView iv_02;

    }
}
