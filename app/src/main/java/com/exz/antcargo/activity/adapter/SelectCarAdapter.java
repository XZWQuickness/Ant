package com.exz.antcargo.activity.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.exz.antcargo.R;
import com.exz.antcargo.activity.bean.SelectCarBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pc on 2016/7/26.
 */

public class SelectCarAdapter<T> extends BaseAdapter {
    private List<T> objects = new ArrayList<T>();
    private Activity context;
    private ViewHodler viewHodler = null;

    public SelectCarAdapter(Activity context) {
        this.context = context;
    }

    private Map<Integer, Boolean> isSelected;

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
        isSelected = new HashMap<Integer, Boolean>();
        for (int i = 0; i < alist.size(); i++) {
            isSelected.put(i, false);

        }
        objects.addAll(alist);

    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final List<SelectCarBean> list = (List<SelectCarBean>) objects;

        if (convertView == null) {
            viewHodler = new ViewHodler();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_select_car, null);
            viewHodler.ll_view = (LinearLayout) convertView.findViewById(R.id.ll_view);
            viewHodler.tv_modelsName = (TextView) convertView.findViewById(R.id.tv_modelsName);
            viewHodler.tv_deadweightTonnage = (TextView) convertView.findViewById(R.id.tv_deadweightTonnage);
            viewHodler.tv_vehicleLength = (TextView) convertView.findViewById(R.id.tv_vehicleLength);
            viewHodler.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }
        viewHodler.tv_modelsName.setText(list.get(position).getModelsName());//车型
        viewHodler.tv_deadweightTonnage.setText(list.get(position).getDeadweightTonnage());//载重
        viewHodler.tv_vehicleLength.setText(list.get(position).getVehicleLength());//车长
        viewHodler.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 当前点击的CB
                boolean cu = !isSelected.get(position);
                // 先将所有的置为FALSE
                for (Integer p : isSelected.keySet()) {
                    isSelected.put(p, false);
                }
                // 再将当前选择CB的实际状态
                isSelected.put(position, cu);
                updateAdapter();
                Intent intent = new Intent();
                intent.putExtra("ownerVehicleCerId", list.get(position).getVehicleId());
                intent.putExtra("ownerVehicleCerName", list.get(position).getModelsName() + list.get(position).getDeadweightTonnage() + list.get(position).getVehicleLength());
                context.setResult(123, intent);
                context.finish();
            }
        });
        viewHodler.ll_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 当前点击的CB
                boolean cu = !isSelected.get(position);
                // 先将所有的置为FALSE
                for (Integer p : isSelected.keySet()) {
                    isSelected.put(p, false);
                }
                // 再将当前选择CB的实际状态
                isSelected.put(position, cu);
                updateAdapter();
                Intent intent = new Intent();
                intent.putExtra("ownerVehicleCerId", list.get(position).getVehicleId());
                intent.putExtra("ownerVehicleCerName", list.get(position).getModelsName() + list.get(position).getDeadweightTonnage() + list.get(position).getVehicleLength());
                context.setResult(123, intent);
                context.finish();
            }
        });
        viewHodler.checkBox.setChecked(isSelected.get(position));
        if(context.getIntent().getStringExtra("ownerVehicleCerId").equals(list.get(position).getVehicleId())){
            viewHodler.checkBox.setChecked(true);
        }
        return convertView;
    }

    class ViewHodler {

        TextView tv_modelsName;
        TextView tv_deadweightTonnage;
        TextView tv_vehicleLength;
        ImageView iv_audit;
        CheckBox checkBox;
        LinearLayout ll_view;

    }
}