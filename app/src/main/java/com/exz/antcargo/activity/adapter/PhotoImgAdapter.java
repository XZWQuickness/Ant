package com.exz.antcargo.activity.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.exz.antcargo.R;
import com.exz.antcargo.activity.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by pc on 2016/7/26.
 */

public class PhotoImgAdapter<T> extends BaseAdapter {
    private List<T> objects = new ArrayList<T>();
    private Activity context;

    public PhotoImgAdapter(Activity context) {
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
        final List<String> list = (List<String>) objects;
        ViewHodler viewHodler = null;
        if (convertView == null) {
            viewHodler = new ViewHodler();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_photo, null);
            viewHodler.iv_photo = (ImageView) convertView.findViewById(R.id.iv_photo);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }
            ImageLoader.getInstance().displayImage(list.get(position),viewHodler.iv_photo);
        viewHodler.iv_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent=new Intent(context, PreviewImageActivity.class);
//                intent.putExtra("url",list.get(position));
//                context.startActivity(intent);
                Utils.showPriviewPhoto(context,list.get(position));
            }
        });

        return convertView;
    }

    class ViewHodler {

        ImageView iv_photo;

    }
}