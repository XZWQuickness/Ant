package com.exz.antcargo.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.exz.antcargo.R;
import com.exz.antcargo.activity.DiaLogActivity;
import com.exz.antcargo.activity.bean.BalanceListBean;

import java.util.ArrayList;
import java.util.List;

import static com.exz.antcargo.R.id.iv_state;

/**
 * Created by pc on 2016/7/26.
 */

public class BanlanceListAdapter<T> extends BaseAdapter {
    private List<T> objects = new ArrayList<T>();
    private Context context;
    private String state;

    public BanlanceListAdapter(Context context) {
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
    public void addendData(List<T> alist, boolean isClearOld, String state) {

        this.state = state;

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
        final List<BalanceListBean> list = (List<BalanceListBean>) objects;
        ViewHodler viewHodler = null;
        if (convertView == null) {
            viewHodler = new ViewHodler();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_xunibi, null);
            viewHodler.iv_photo = (ImageView) convertView.findViewById(R.id.iv_photo);
            viewHodler.iv_state = (ImageView) convertView.findViewById(iv_state);
            viewHodler.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            viewHodler.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            viewHodler.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }
        switch (state) {
            case "1":
                viewHodler.iv_photo.setBackgroundResource(R.drawable.xunibijia);
                viewHodler.tv_money.setText("+" + list.get(position).getPrice());
                viewHodler.tv_title.setText("账户充值");
                viewHodler.iv_state.setVisibility(View.GONE);
                break;

            case "2":
                viewHodler.iv_photo.setBackgroundResource(R.drawable.xunibijian);
                viewHodler.iv_state.setVisibility(View.VISIBLE);
                viewHodler.tv_money.setText("-" + list.get(position).getPrice());
                viewHodler.tv_title.setText("账户提现");
                switch (list.get(position).getState()) {

                    case "0"://处理中
                        viewHodler.iv_state.setBackgroundResource(R.drawable.zhengzaishenhe);

                        break;
                    case "1"://处理成功
                        viewHodler.iv_state.setBackgroundResource(R.drawable.shenhtongguo);


                        break;
                    case "2"://处理失败
                        viewHodler.iv_state.setBackgroundResource(R.drawable.shenhebeiju);
                        break;
                }

                break;

            case "3":
                viewHodler.iv_photo.setBackgroundResource(R.drawable.xunibijian);
                viewHodler.tv_money.setText("-" + list.get(position).getPrice());
                viewHodler.tv_title.setText("信息扣费");
                viewHodler.iv_state.setVisibility(View.GONE);
                break;

            case "4":
                viewHodler.iv_photo.setBackgroundResource(R.drawable.xunibijia);
                viewHodler.tv_money.setText("+" + list.get(position).getPrice());
                viewHodler.tv_title.setText("保证金");
                viewHodler.iv_state.setVisibility(View.GONE);
                break;

        }

        viewHodler.tv_time.setText(list.get(position).getTime());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.get(position).getState().equals("2")) {
                    Intent intent = new Intent(context, DiaLogActivity.class);
                    intent.putExtra("message", list.get(position).getReason());
                    context.startActivity(intent);
                }
            }
        });

        return convertView;
    }

    class ViewHodler {

        ImageView iv_photo;
        ImageView iv_state;
        TextView tv_title;
        TextView tv_time;
        TextView tv_money;

    }
}