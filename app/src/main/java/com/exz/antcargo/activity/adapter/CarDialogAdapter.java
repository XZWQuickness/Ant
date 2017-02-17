package com.exz.antcargo.activity.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.exz.antcargo.R;
import com.exz.antcargo.activity.bean.CarBean;
import com.exz.antcargo.activity.utils.ConstantValue;
import com.exz.antcargo.activity.utils.MainSendEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by pc on 2016/8/22.
 */

public class CarDialogAdapter<T> extends BaseAdapter {
    private List<T> objects = new ArrayList<T>();
    private Context context;

    public CarDialogAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return objects
                .size();
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
    public void addendData(List<? extends CarBean> alist, boolean isClearOld) {
        if (alist == null) {
            return;
        }
        if (isClearOld) {
            objects.clear();
        }
        objects.addAll((Collection<? extends T>) alist);

    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final List<? extends CarBean> list = (List<? extends CarBean>) objects;
        ViewHodler viewHodler = null;
        if (convertView == null) {
            viewHodler = new ViewHodler();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_car_dialog, null);
            viewHodler.name = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }
        if(!TextUtils.isEmpty(list.get(position).getName())){
            viewHodler.name.setText(list.get(position).getName());
            viewHodler.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ConstantValue.modelsId = list.get(position).getTypeid();
                    ConstantValue.modelsName = list.get(position).getName();
                    EventBus.getDefault().post(new MainSendEvent(position));
                }
            });
        }

        return convertView;
    }

    class ViewHodler {

        TextView name;

    }
}
