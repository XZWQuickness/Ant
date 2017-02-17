package com.exz.antcargo.activity.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.exz.antcargo.R;
import com.exz.antcargo.activity.bean.NewestGoodsBean;
import com.exz.antcargo.activity.utils.MainSendEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by pc on 2016/7/26.
 */

public class PopGoodsAdapter<T> extends BaseAdapter {
    private List<T> objects = new ArrayList<T>();
    private Context context;
    private Map<Integer, Boolean> isSelected;

    public PopGoodsAdapter(Context context) {
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
        //type 1 查看货主 2 编辑货主
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
        final List<NewestGoodsBean> list = (List<NewestGoodsBean>) objects;
        ViewHodler viewHodler = null;
        viewHodler = new ViewHodler();
        convertView = LayoutInflater.from(context).inflate(R.layout.pop_item_goods, null);
        viewHodler.ll_click = (LinearLayout) convertView.findViewById(R.id.ll_click);
        viewHodler.tv_createAddress = (TextView) convertView.findViewById(R.id.tv_createAddress);
        viewHodler.tv_createDate = (TextView) convertView.findViewById(R.id.tv_createDate);
        viewHodler.tv_owner_vehicle = (TextView) convertView.findViewById(R.id.tv_owner_vehicle);
        viewHodler.tv_destination = (TextView) convertView.findViewById(R.id.tv_destination);
        viewHodler.tv_margin_isnull = (TextView) convertView.findViewById(R.id.tv_margin_isnull);
        viewHodler.tv_shelvesDate = (TextView) convertView.findViewById(R.id.tv_shelvesDate);
        viewHodler.tv_shelvesDate.setText(list.get(position).getShelvesDate());//上架时间
        viewHodler.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
        viewHodler.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
        viewHodler.shunfeng_car = (ImageView) convertView.findViewById(R.id.iv_01);
        viewHodler.iv_type = (ImageView) convertView.findViewById(R.id.iv_type);

        if (!TextUtils.isEmpty(list.get(position).getOrigin())) {
            viewHodler.tv_createAddress.setText(list.get(position).getOrigin());//收货地
        }
        if (!TextUtils.isEmpty(list.get(position).getEntruckingDate())) {

            viewHodler.tv_createDate.setText(list.get(position).getEntruckingDate());//创建时间
        }
        if (!TextUtils.isEmpty(list.get(position).getGoodsType())) {
            viewHodler.tv_owner_vehicle.setText(list.get(position).getGoodsType());//箱式
        }
        if (!TextUtils.isEmpty(list.get(position).getDestination())) {
            viewHodler.tv_destination.setText(list.get(position).getDestination());//目的地
        }

        viewHodler.ll_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new MainSendEvent(list.get(position).getGoodsid(),list.get(position).getGoodsType(),position));
            }
        });
        viewHodler.shunfeng_car.setBackgroundResource(R.drawable.goods_lin);
        viewHodler.iv_type.setBackgroundResource(R.drawable.huowuxinxi);
        return convertView;
    }

    /**
     * 清空适配器数据
     */
    public void clearFlagAdapter() {
        for (Integer p : isSelected.keySet()) {
            isSelected.put(p, false);
        }
        updateAdapter();
    }


    class ViewHodler {

        LinearLayout ll_click;
        TextView tv_createAddress;
        TextView tv_createDate;
        TextView tv_owner_vehicle;
        TextView tv_destination;
        TextView tv_margin_isnull;
        TextView tv_distanceAround;
        TextView tv_shelvesDate;
        ImageView shunfeng_car;
        ImageView iv_type;
        CheckBox checkBox;
    }
}