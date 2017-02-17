package com.exz.antcargo.activity.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.exz.antcargo.R;
import com.exz.antcargo.activity.DiaLogActivity;
import com.exz.antcargo.activity.GoosdInfoDetailActivity;
import com.exz.antcargo.activity.IsuueGoodsActivity;
import com.exz.antcargo.activity.LoginActivity;
import com.exz.antcargo.activity.bean.NewEntity;
import com.exz.antcargo.activity.bean.NewestGoodsBean;
import com.exz.antcargo.activity.utils.CheckState;
import com.exz.antcargo.activity.utils.Constant;
import com.exz.antcargo.activity.utils.ConstantValue;
import com.exz.antcargo.activity.utils.PopTitleMes;
import com.exz.antcargo.activity.utils.SPutils;
import com.exz.antcargo.activity.utils.Utils;
import com.exz.antcargo.activity.utils.XUtilsApi;

import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016/7/26.
 */

public class GoodsInfoAdapter<T> extends BaseAdapter {
    private List<T> objects = new ArrayList<T>();
    private Activity context;
    private String type;

    public GoodsInfoAdapter(Activity context) {
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
    public void addendData(List<T> alist, boolean isClearOld, String type) {
        //type 1 查看货主 2 编辑货主
        this.type = type;
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
        final List<NewestGoodsBean> list = (List<NewestGoodsBean>) objects;
        ViewHodler viewHodler = null;
        if (convertView == null) {
            viewHodler = new ViewHodler();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_manager_goods, null);
            viewHodler.rl_newst_order = (RelativeLayout) convertView.findViewById(R.id.rl_newst_order);
            viewHodler.ll_edit_view = (LinearLayout) convertView.findViewById(R.id.ll_edit_view);
            viewHodler.tv_createAddress = (TextView) convertView.findViewById(R.id.tv_createAddress);
            viewHodler.tv_createDate = (TextView) convertView.findViewById(R.id.tv_createDate);
            viewHodler.tv_owner_vehicle = (TextView) convertView.findViewById(R.id.tv_owner_vehicle);
            viewHodler.tv_destination = (TextView) convertView.findViewById(R.id.tv_destination);
            viewHodler.tv_margin_isnull = (TextView) convertView.findViewById(R.id.tv_margin_isnull);
            viewHodler.tv_shelvesDate = (TextView) convertView.findViewById(R.id.tv_shelvesDate);
            viewHodler.tv_manege_state = (TextView) convertView.findViewById(R.id.tv_manege_state);
            viewHodler.tv_01 = (TextView) convertView.findViewById(R.id.tv_01);
            viewHodler.tv_02 = (TextView) convertView.findViewById(R.id.tv_02);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }
        viewHodler.tv_createAddress.setText(list.get(position).getOrigin());//收货地
        viewHodler.tv_createDate.setText(list.get(position).getEntruckingDate());//创建时间
        viewHodler.tv_owner_vehicle.setText(list.get(position).getGoodsType());//箱式
        viewHodler.tv_destination.setText(list.get(position).getDestination());//目的地
        viewHodler.tv_shelvesDate.setText("  " + list.get(position).getShelvesDate());//上架时间
        viewHodler.rl_newst_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckState.checkCarInfo(context, ConstantValue.AccountType);
                if (TextUtils.isEmpty(SPutils.getString(context, "accountId"))) {

                    Utils.startActivity(context, LoginActivity.class);
                    return;
                }
                CheckState.checkCarInfo(context, ConstantValue.AccountType);

//                if(list.get(position).get){
//
//                }
                if (ConstantValue.AccountType.equals("2") && type.equals("1")) {
                    Intent intent = new Intent(context, GoosdInfoDetailActivity.class);
                    intent.putExtra("supplyGoodsId", list.get(position).getGoodsid());
                    intent.putExtra("type", type);//type 1 查看货主 2 编辑货主
                    intent.putExtra("state", list.get(position).getState());// "state":"1在线 2用户下架 3管理员下架"
                    context.startActivity(intent);

                } else if (ConstantValue.AccountType.equals("1") && type.equals("2")) {

                    Intent intent = new Intent(context, GoosdInfoDetailActivity.class);
                    intent.putExtra("supplyGoodsId", list.get(position).getGoodsid());
                    intent.putExtra("type", type);//type 1 查看货主 2 编辑货主
                    intent.putExtra("state", list.get(position).getState());// "state":"1在线 2用户下架 3管理员下架"
                    context.startActivity(intent);
                } else if (ConstantValue.AccountType.equals("1") && type.equals("1")) {
                    PopTitleMes.showPopSingle(context, "您是货主不可以查看货主详情");
                }


            }
        });
        if (type.equals("2")) {
            viewHodler.ll_edit_view.setVisibility(View.VISIBLE);

            switch (list.get(position).getState()) {// "state":"1在线 2用户下架 3管理员下架"
                case "1":
                    viewHodler.tv_01.setText("编辑货源");
                    viewHodler.tv_02.setText("手动下架");
                    viewHodler.tv_manege_state.setText("");
                    break;
                case "2":
                    viewHodler.tv_01.setText("重新上架");
                    viewHodler.tv_02.setText("删除货源");
                    viewHodler.tv_manege_state.setText("");
                    break;

                case "3":
                    viewHodler.tv_01.setText("申请上架");
                    viewHodler.tv_02.setText("删除货源");
                    viewHodler.tv_manege_state.setText("已被管理员下架!");
            break;
        }
            viewHodler.tv_01.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (list.get(position).getState()) {// "state":"1在线 2用户下架 3管理员下架"
                        case "1"://编辑
                            Intent intent = new Intent(context, IsuueGoodsActivity.class);
                            intent.putExtra("editId", list.get(position).getGoodsid());
                            intent.putExtra("edit", "edit");//编辑
                            context.startActivity(intent);
                            break;
                        case "2"://上架
                            editInfo(list.get(position).getGoodsid(), "1", position);//状态：0删除 1上架  2:用户下架
                            break;
                        case "3"://联系客服
                            final AlertDialog dlgtwo = new AlertDialog.Builder(context)
                                    .create();
                            View viewtwo = LayoutInflater.from(context).inflate(
                                    R.layout.pop_check, null);
                            dlgtwo.setView(context.getLayoutInflater().inflate(
                                    R.layout.pop_check, null));
                            dlgtwo.show();
                            dlgtwo.getWindow().setContentView(viewtwo);
                            TextView queding = (TextView) viewtwo.findViewById(R.id.queding);
                            TextView title = (TextView) viewtwo.findViewById(R.id.title);
                            TextView quxiao = (TextView) viewtwo.findViewById(R.id.quxiao);
                            title.setText("拨打" + "8888");
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

                                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "8888"));
                                    context.startActivity(intent);

                                }
                            });
                            break;
                    }

                }
            });
            viewHodler.tv_02.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (list.get(position).getState()) {// "state":"1在线 2用户下架 3管理员下架"
                        case "1"://下架
                            editInfo(list.get(position).getGoodsid(), "2", position);//状态：0删除 1上架  2:用户下架
                            break;
                        case "2"://删除
                            editInfo(list.get(position).getGoodsid(), "0", position);//状态：0删除 1上架  2:用户下架
                            break;
                        case "3"://删除
                            editInfo(list.get(position).getGoodsid(), "0", position);//状态：0删除 1上架  2:用户下架
                            break;
                    }

                }
            });
        } else {
            viewHodler.ll_edit_view.setVisibility(View.GONE);
        }
        return convertView;
    }


    private void editInfo(String editId, String editState, final int position) {
        XUtilsApi xUtilsApi = new XUtilsApi();
        RequestParams params = new RequestParams(Constant.EDITVEHICLEGOODS);
        params.addBodyParameter("editType", "1");//编辑类型【1货源 2车源】
        params.addBodyParameter("editId", editId);
        params.addBodyParameter("editState", editState);

        xUtilsApi.sendUrl(context, "post", params, true, new XUtilsApi.URLSuccessListenter() {

            @Override
            public void OnSuccess(NewEntity content, JSONObject result) {
                if (content.getResult().equals(ConstantValue.RESULT)) {
                    objects.remove(position);
                    updateAdapter();
                    Intent intent = new Intent(context, DiaLogActivity.class)
                            .putExtra("message", content.getMessage());
                    context.startActivity(intent);


                } else {
                    Intent intent = new Intent(context, DiaLogActivity.class)
                            .putExtra("message", content.getMessage());
                    context.startActivity(intent);
                }
            }
        });
    }

    class ViewHodler {

        LinearLayout ll_edit_view;
        TextView tv_createAddress;
        TextView tv_createDate;
        TextView tv_owner_vehicle;
        TextView tv_destination;
        TextView tv_margin_isnull;
        TextView tv_distanceAround;
        TextView tv_manege_state;
        TextView tv_shelvesDate;
        RelativeLayout rl_newst_order;
        TextView tv_01;
        TextView tv_02;
    }
}