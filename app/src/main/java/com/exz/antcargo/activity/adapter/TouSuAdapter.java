package com.exz.antcargo.activity.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.exz.antcargo.R;
import com.exz.antcargo.activity.bean.TouSuBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pc on 2016/7/26.
 */

public class TouSuAdapter<T> extends BaseAdapter {
    private List<T> objects = new ArrayList<T>();
    private Activity context;
    private Map<Integer, Boolean> isSelected;

    private String cid="";

    public TouSuAdapter(Activity context) {
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

        isSelected = new HashMap<Integer, Boolean>();
        for (int i = 0; i < alist.size(); i++) {
            isSelected.put(i, false);

        }
        objects.addAll(alist);


    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final List<TouSuBean> list = (List<TouSuBean>) objects;
        ViewHodler viewHodler = null;
        if (convertView == null) {
            viewHodler = new ViewHodler();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_tousu, null);
            viewHodler.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHodler.checkbox = (CheckBox) convertView.findViewById(R.id.checkbox);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }
        viewHodler.tv_name.setText(list.get(position).getName());

        viewHodler.checkbox.setOnClickListener(new View.OnClickListener() {
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
                cid=list.get(position).getCid();
//                EventBus.getDefault().post(new MainSendEvent("update",list.get(position).getCid()));

            }
        });
        viewHodler.tv_name.setOnClickListener(new View.OnClickListener() {
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
                cid=list.get(position).getCid();
//                EventBus.getDefault().post(new MainSendEvent("update",list.get(position).getCid()));
            }
        });
        viewHodler.checkbox.setChecked(isSelected.get(position));
        return convertView;
    }

  public  String getCid(){


      return cid;
  }


    class ViewHodler {

        TextView tv_name;
        CheckBox checkbox;

    }
}