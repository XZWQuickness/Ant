package com.exz.antcargo.activity.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.exz.antcargo.R;
import com.exz.antcargo.activity.MapActivity;
import com.exz.antcargo.activity.bean.LogisticsDetailebean;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import static java.net.URLDecoder.decode;


/**
 * Created by pc on 2016/7/26.
 */

public class DestinationAdapter<T> extends BaseAdapter {
    private List<T> objects = new ArrayList<T>();
    private Activity context;
    String phoneNum = "";

    public DestinationAdapter(Activity context) {
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

    /**
     */
    public void Delete(int pos) {
        objects.remove(pos);
        updateAdapter();
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final List<LogisticsDetailebean.DestinationListEntity> list = (List<LogisticsDetailebean.DestinationListEntity>) objects;
        ViewHodler viewHodler = null;
        if (convertView == null) {
            viewHodler = new ViewHodler();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_origin, null);
            viewHodler.ll_phone = (LinearLayout) convertView.findViewById(R.id.ll_phone);
            viewHodler.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHodler.tv_photo_null = (TextView) convertView.findViewById(R.id.tv_photo_null);
            viewHodler.iv_location = (ImageView) convertView.findViewById(R.id.iv_location);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }
        try {
            viewHodler.tv_name.setText(decode(list.get(position).getName(),"utf-8"));
            viewHodler.tv_photo_null.setText(decode(list.get(position).getAddress(),"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        viewHodler.iv_location.setBackgroundResource(R.drawable.mudidi);

        viewHodler.ll_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    phoneNum = URLDecoder.decode(list.get(position).getPhone(),"utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                final AlertDialog dlgtwo = new AlertDialog.Builder(context)
                        .create();
                View viewtwo = LayoutInflater.from(context).inflate(
                        R.layout.pop_logistics_call, null);
                dlgtwo.setView(context.getLayoutInflater().inflate(
                        R.layout.pop_logistics_call, null));
                dlgtwo.show();
                dlgtwo.getWindow().setContentView(viewtwo);
                TextView queding = (TextView) viewtwo.findViewById(R.id.queding);
                TextView title = (TextView) viewtwo.findViewById(R.id.title);
                TextView quxiao = (TextView) viewtwo.findViewById(R.id.quxiao);
                title.setText("拨打" + phoneNum.trim());

                quxiao.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        dlgtwo.dismiss();

                    }
                });
                queding.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        dlgtwo.dismiss();

                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" +phoneNum));
                        context.startActivity(intent);

                    }
                });
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address="";
                String name="";
                String Latitude="";
                String Longitude="";
                try {
                    address=URLDecoder.decode(list.get(position).getAddress(),"utf-8");
                    name=URLDecoder.decode(list.get(position).getName(),"utf-8");
                    Latitude=URLDecoder.decode(list.get(position).getLatitude(),"utf-8");
                    Longitude=URLDecoder.decode(list.get(position).getLongitude(),"utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Intent intent=new Intent(context, MapActivity.class);
                intent.putExtra("Latitude",Latitude);
                intent.putExtra("Longitude",Longitude);
                intent.putExtra("address",address);
                intent.putExtra("name",name);
                context.startActivity(intent);

            }
        });


        return convertView;
        }

class ViewHodler {

    TextView tv_name;
    TextView tv_photo_null;
    ImageView iv_location;
    LinearLayout ll_phone;

}
}