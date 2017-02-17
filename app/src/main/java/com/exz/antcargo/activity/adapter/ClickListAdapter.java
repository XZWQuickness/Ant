package com.exz.antcargo.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.exz.antcargo.R;
import com.exz.antcargo.activity.CarInfoDetailActivity;
import com.exz.antcargo.activity.GoosdInfoDetailActivity;
import com.exz.antcargo.activity.bean.ClickBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016/8/22.
 */

public class ClickListAdapter<T> extends BaseAdapter {
    private List<T> objects = new ArrayList<T>();
    private Context context;
    private String type;

    public ClickListAdapter(Context context) {
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
    public void addendData(List<T> alist, boolean isClearOld, String type) {
        this.type = type;
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
        final List<ClickBean> list = (List<ClickBean>) objects;
        ViewHodler viewHodler = null;
        if (convertView == null) {
            viewHodler = new ViewHodler();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_click_list, null);

            viewHodler.ll_view = (LinearLayout) convertView.findViewById(R.id.ll_view);
            viewHodler.tv_name_type = (TextView) convertView.findViewById(R.id.tv_name_type);
            viewHodler.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHodler.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            viewHodler.tv_look_name = (TextView) convertView.findViewById(R.id.tv_look_name);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }

        if (type.equals("车主")) {
            viewHodler.tv_name_type.setText("货主");
            viewHodler.tv_look_name.setText("查看车源");

        } else if (type.equals("货主")) {
            viewHodler.tv_name_type.setText("车主");
            viewHodler.tv_look_name.setText("查看货源");

        }
        viewHodler.tv_name.setText(list.get(position).getName());
        viewHodler.tv_time.setText(list.get(position).getTime());
        viewHodler.ll_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type.equals("车主")){
                    Intent intent = new Intent(context, GoosdInfoDetailActivity.class);
                    intent.putExtra("supplyGoodsId", list.get(position).getSupplyGoodId());
                    intent.putExtra("objectId", list.get(position).getSupplyCarId());
                    intent.putExtra("accountId", list.get(position).getFromId());
                    intent.putExtra("type", "1");//type 1 查看货主 2 编辑货主
                    intent.putExtra("history","历史");
                    context.startActivity(intent);
                }else {
                    Intent intent = new Intent(context, CarInfoDetailActivity.class);
                    intent.putExtra("id", list.get(position).getSupplyCarId());
                    intent.putExtra("objectId", list.get(position).getSupplyGoodId());
                    intent.putExtra("accountId", list.get(position).getFromId());
                    intent.putExtra("type", "1");//查车货源详情
                    intent.putExtra("history","历史");
                    context.startActivity(intent);
                }
            }
        });
        return convertView;
    }

    class ViewHodler {

        TextView tv_name_type;
        TextView tv_name;
        TextView tv_time;
        TextView tv_look_name;
        LinearLayout ll_view;

    }
}
