package com.exz.antcargo.activity.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.exz.antcargo.R;
import com.exz.antcargo.activity.bean.LogisticsDetailebean;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by pc on 2016/7/26.
 */

public class AlongRoadAdapter<T> extends BaseAdapter {
    private List<T> objects = new ArrayList<T>();
    private Activity context;


    public AlongRoadAdapter(Activity context) {
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
     *  @param alist      数据集合
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

    /**
     */
    public void Delete(int pos) {
        objects.remove(pos);
        updateAdapter();
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final List<LogisticsDetailebean.AlongRoadListEntity> list = (List<LogisticsDetailebean.AlongRoadListEntity>) objects;
        ViewHodler viewHodler = null;
        if (convertView == null) {
            viewHodler = new ViewHodler();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_alongroad, null);
            viewHodler.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHodler.tv_photo_null = (TextView) convertView.findViewById(R.id.tv_photo_null);
            viewHodler.iv_location = (ImageView) convertView.findViewById(R.id.iv_location);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }
        try {
            viewHodler.tv_name.setText(  URLDecoder.decode(list.get(position).getName(),"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        return convertView;
}

class ViewHodler {

    TextView tv_name;
    TextView tv_photo_null;
    ImageView iv_location;

}
}