package com.exz.antcargo.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.exz.antcargo.R;
import com.exz.antcargo.activity.bean.SelectCarBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016/7/26.
 */

public class AddCarInfoAdapter<T> extends BaseAdapter {
    private List<T> objects = new ArrayList<T>();
    private Context context;

    private boolean isClick = false;

    private String checkState;

    public AddCarInfoAdapter(Context context) {
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
        final List<SelectCarBean> list = (List<SelectCarBean>) objects;
        ViewHodler viewHodler = null;
        if (convertView == null) {
            viewHodler = new ViewHodler();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_add_car_info, null);
            viewHodler.tv_modelsName = (TextView) convertView.findViewById(R.id.tv_modelsName);
            viewHodler.tv_deadweightTonnage = (TextView) convertView.findViewById(R.id.tv_deadweightTonnage);
            viewHodler.tv_vehicleLength = (TextView) convertView.findViewById(R.id.tv_vehicleLength);
            viewHodler.iv_audit = (ImageView) convertView.findViewById(R.id.iv_audit);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }
        viewHodler.tv_modelsName.setText(list.get(position).getModelsName());//车型
        viewHodler.tv_deadweightTonnage.setText(list.get(position).getDeadweightTonnage());//载重
        viewHodler.tv_vehicleLength.setText(list.get(position).getVehicleLength());//车长


        switch (list.get(position).getState()){////'-1:未提交审核信息 0未审核(审核中) 1审核通过 2拒绝 3禁用
            case "0":
                viewHodler.iv_audit.setBackgroundResource(R.drawable.zhengzaishenhe);
                break;
            case "1":
                viewHodler.iv_audit.setBackgroundResource(R.drawable.shenhtongguo);
                break;
            case "2":
                viewHodler.iv_audit.setBackgroundResource(R.drawable.shenhebeiju);
                break;
        }

        return convertView;
    }

    class ViewHodler {

        TextView tv_modelsName;
        TextView tv_deadweightTonnage;
        TextView tv_vehicleLength;
        ImageView iv_audit;

    }
}