package com.exz.antcargo.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.exz.antcargo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016/7/26.
 */

public class RechargeAmountAdapter<T> extends BaseAdapter {
    private List<T> objects = new ArrayList<T>();
    private Context context;
    // 状态标志位
    private int clickTemp = -1;
    private boolean isClick = false;

    public void setSeclection(int position) {
        clickTemp = position;
    }

    public RechargeAmountAdapter(Context context) {
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
        final List<Integer> list = (List<Integer>) objects;
        ViewHodler viewHodler = null;
        if (convertView == null) {
            viewHodler = new ViewHodler();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_chongzhi_jine, null);
            viewHodler.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }
        viewHodler.tv_money.setText(list.get(position) + "");
        if (clickTemp == position) {
            viewHodler.tv_money.setBackgroundResource(R.drawable.baozhengjinyixuan);
        } else {
            viewHodler.tv_money.setBackgroundResource(R.drawable.baozhengjinweixuan);
        }

        return convertView;
    }

    class ViewHodler {

        TextView tv_money;

    }
}