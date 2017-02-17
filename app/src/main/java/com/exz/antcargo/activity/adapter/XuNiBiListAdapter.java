package com.exz.antcargo.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.exz.antcargo.R;
import com.exz.antcargo.activity.bean.XuNiBiListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016/7/26.
 */

public class XuNiBiListAdapter<T> extends BaseAdapter {
    private List<T> objects = new ArrayList<T>();
    private Context context;

    public XuNiBiListAdapter(Context context) {
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
        final List<XuNiBiListBean> list = (List<XuNiBiListBean>) objects;
        ViewHodler viewHodler = null;
        if (convertView == null) {
            viewHodler = new ViewHodler();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_xunibi, null);
            viewHodler.iv_photo = (ImageView) convertView.findViewById(R.id.iv_photo);
            viewHodler.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            viewHodler.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            viewHodler.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }
        if(list.get(position).getType().equals("赠送")){
            viewHodler.iv_photo.setBackgroundResource(R.drawable.xunibijia);
            viewHodler.tv_money.setText("+"+list.get(position).getCount());
        }else if(list.get(position).getType().equals("花费")){
            viewHodler.iv_photo.setBackgroundResource(R.drawable.xunibijian);
            viewHodler.tv_money.setText("-"+list.get(position).getCount());
        }
        viewHodler.tv_title.setText(list.get(position).getType());
        viewHodler.tv_time.setText(list.get(position).getTime());


        return convertView;
    }

    class ViewHodler {

        ImageView iv_photo;
        TextView tv_title;
        TextView tv_time;
        TextView tv_money;

    }
}