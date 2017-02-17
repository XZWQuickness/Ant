package com.exz.antcargo.activity.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import com.exz.antcargo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016/7/26.
 */

public class YanTuLuMingAdapter<T> extends BaseAdapter {
    private List<T> objects = new ArrayList<T>();
    private Context context;
    private LayoutInflater inflater;

    public YanTuLuMingAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return objects.size();
    }


    @Override
    public T getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
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
        objects = alist;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.adapter_yantu_luming, null);
            ViewHolder holder = new ViewHolder(convertView);
            holder.ivJian.setTag(position);
            class MyTextWatcher implements TextWatcher {
                public MyTextWatcher(ViewHolder holder) {
                    mHolder = holder;
                }

                private ViewHolder mHolder;

                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s != null && !"".equals(s.toString())) {
                        objects.set((int) mHolder.ivJian.getTag(), (T) s.toString());
                    }
                }
            }
            holder.edName.addTextChangedListener(new MyTextWatcher(holder));
            convertView.setTag(holder);
        }
        initializeViews(getItem(position), (ViewHolder) convertView.getTag(), position);
        return convertView;
    }

    private void initializeViews(T object, ViewHolder holder, int position) {
        holder.ivJian.setTag(position);
        holder.edName.setText(object.toString());
        holder.ivJian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                objects.remove((int)view.getTag());
                updateAdapter();
            }
        });
    }

    protected class ViewHolder {
        private ImageView ivJian;
        private EditText edName;

        public ViewHolder(View view) {
            ivJian = (ImageView) view.findViewById(R.id.iv_jian);
            edName = (EditText) view.findViewById(R.id.ed_name);
        }
    }

}