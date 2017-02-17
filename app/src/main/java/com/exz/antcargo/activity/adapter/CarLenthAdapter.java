package com.exz.antcargo.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.exz.antcargo.R;
import com.exz.antcargo.activity.bean.PopuWindowCheXingBean;
import com.exz.antcargo.activity.utils.MainSendEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016/8/22.
 */

public class CarLenthAdapter<T> extends BaseAdapter {
    private List<T> objects = new ArrayList<T>();
    private Context context;
    private ViewHodler viewHodler = null;

    public CarLenthAdapter(Context context) {
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
        final List<PopuWindowCheXingBean> list = (List<PopuWindowCheXingBean>) objects;

        if (convertView == null) {
            viewHodler = new ViewHodler();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_pop_more, null);
            viewHodler.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }
        viewHodler.checkBox.setText(list.get(position).getName());

        if (list.get(position).isSelectState()) {
            viewHodler.checkBox.setChecked(true);
        } else {
            viewHodler.checkBox.setChecked(false);
        }
        viewHodler.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                EventBus.getDefault().post(new MainSendEvent(position, "3", b));

            }
        });

        return convertView;
    }

    class ViewHodler {

        CheckBox checkBox;
    }

    public void cleanColor() {

    }
}
