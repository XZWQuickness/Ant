package com.exz.antcargo.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.exz.antcargo.R;
import com.exz.antcargo.activity.WebViewActivity;
import com.exz.antcargo.activity.bean.AfficheBean;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by pc on 2016/7/26.
 */

public class AfficheAdapter<T> extends BaseAdapter {
    private List<T> objects = new ArrayList<T>();
    private Context context;

    public AfficheAdapter(Context context) {
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
        final List<AfficheBean> list = (List<AfficheBean>) objects;
        ViewHodler viewHodler = null;
        if (convertView == null) {
            viewHodler = new ViewHodler();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_affiche, null);
            viewHodler.rl_affiche = (RelativeLayout) convertView.findViewById(R.id.rl_affiche);
            viewHodler.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHodler.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }

        viewHodler.tv_name.setText(list.get(position).getTitle());
        viewHodler.tv_time.setText(list.get(position).getTime());
        viewHodler.rl_affiche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("name", "公告详情");
                intent.putExtra("url", list.get(position).getUrl());
                context.startActivity(intent
                );
            }
        });
        return convertView;
    }

    class ViewHodler {

        TextView tv_name;
        TextView tv_time;
        RelativeLayout rl_affiche;

    }
}