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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.exz.antcargo.R;
import com.exz.antcargo.activity.LogisticsDetaileActivity;
import com.exz.antcargo.activity.bean.LogisticsBean;
import com.exz.antcargo.activity.utils.XUtilsApi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016/7/26.
 */

public class WuLiuListAdapter<T> extends BaseAdapter {
    private List<T> objects = new ArrayList<T>();
    private Activity context;

    public WuLiuListAdapter(Activity context) {
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
        final List<LogisticsBean> list = (List<LogisticsBean>) objects;
        ViewHodler viewHodler = null;
        if (convertView == null) {
            viewHodler = new ViewHodler();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_wuliu_list, null);
            viewHodler.iv_wuliu = (ImageView) convertView.findViewById(R.id.iv_wuliu);
            viewHodler.rl_view = (RelativeLayout) convertView.findViewById(R.id.rl_view);
            viewHodler.iv_phone = (ImageView) convertView.findViewById(R.id.iv_phone);
            viewHodler.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHodler.tv_origin = (TextView) convertView.findViewById(R.id.tv_origin);
            viewHodler.tv_destination = (TextView) convertView.findViewById(R.id.tv_destination);
            viewHodler.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }

//        ImageLoader.getInstance().displayImage(list.get(position).getImg(), viewHodler.iv_wuliu);
        XUtilsApi.imageLoad( viewHodler.iv_wuliu,list.get(position).getImg(),R.drawable.wuliugongsimoren);
        viewHodler.tv_name.setText(list.get(position).getName());//物流公司
        viewHodler.tv_origin.setText(list.get(position).getOrigin());   //出发地
        viewHodler.tv_destination.setText(list.get(position).getDestination());//目的地
        viewHodler.rl_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, LogisticsDetaileActivity.class);
                intent.putExtra("lid",list.get(position).getLid());
                context.startActivity(intent);


            }
        });

        viewHodler.tv_content.setText(list.get(position).getAddress());
        viewHodler.iv_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                title.setText("拨打" + list.get(position).getCompany_contact().trim());
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

                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + list.get(position).getCompany_contact()));
                        context.startActivity(intent);

                    }
                });
            }
        });
        return convertView;
    }

    class ViewHodler {

        ImageView iv_wuliu;
        ImageView iv_phone;
        TextView tv_name;
        TextView tv_origin;
        TextView tv_destination;
        RelativeLayout rl_view;
        TextView tv_content;

    }
}