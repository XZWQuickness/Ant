package com.exz.antcargo.activity.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.exz.antcargo.R;
import com.exz.antcargo.activity.bean.SendPhoto;
import com.exz.antcargo.activity.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by pc on 2016/7/26.
 */

public class PhotoAdapter<T> extends BaseAdapter {
    private List<T> objects = new ArrayList<T>();
    private Context context;


    public PhotoAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        if (objects.size() == 5) {
            return 5;
        }
        return objects.size() + 1;
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

    /**
     */
    public void Delete(int pos) {
        objects.remove(pos);
        updateAdapter();
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        List<String> list= (List<String>) objects;
        ViewHodler viewHodler = null;
        if (convertView == null) {
            viewHodler = new ViewHodler();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_photo, null);
            viewHodler.iv_photo = (ImageButton) convertView.findViewById(R.id.iv_photo);
            viewHodler.iv_is_delete = (ImageView) convertView.findViewById(R.id.iv_is_delete);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }
        if (position == objects.size()) {
            viewHodler.iv_photo.setImageBitmap(BitmapFactory.decodeResource(context.
                    getResources(), R.mipmap.paizhao));
            if (position == 6) {
                viewHodler.iv_photo.setVisibility(View.GONE);
            }
            viewHodler.iv_is_delete.setVisibility(View.GONE);

        } else {
           if(!list.get(position).contains("http")){
                viewHodler.iv_photo.setImageBitmap(Utils.base64ToBitmap((String)objects.get(position)));
            } else {
                ImageLoader.getInstance().displayImage((String) objects.get(position), viewHodler.iv_photo);

            }
            viewHodler.iv_is_delete.setVisibility(View.VISIBLE);
        }

        viewHodler.iv_is_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EventBus.getDefault().post(new SendPhoto(objects.get(position), "delete", position));
                objects.remove(position);
            }
        });

        viewHodler.iv_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position == objects.size() && position != 6) {

                    EventBus.getDefault().post(new SendPhoto(position, "add"));
                }
            }
        });


        return convertView;
    }

    class ViewHodler {

        ImageButton iv_photo;
        ImageView iv_is_delete;

    }
}