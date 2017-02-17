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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.exz.antcargo.R;
import com.exz.antcargo.activity.CarInfoActivity;
import com.exz.antcargo.activity.CarInfoDetailActivity;
import com.exz.antcargo.activity.DiaLogActivity;
import com.exz.antcargo.activity.IdentityAuditActivity;
import com.exz.antcargo.activity.IsuueCarActivity;
import com.exz.antcargo.activity.bean.CarManageBean;
import com.exz.antcargo.activity.bean.NewEntity;
import com.exz.antcargo.activity.utils.CheckState;
import com.exz.antcargo.activity.utils.Constant;
import com.exz.antcargo.activity.utils.ConstantValue;
import com.exz.antcargo.activity.utils.PopTitleMes;
import com.exz.antcargo.activity.utils.Utils;
import com.exz.antcargo.activity.utils.XUtilsApi;

import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import static com.exz.antcargo.activity.utils.ConstantValue.CAR_NUMBER_STATE;
import static com.exz.antcargo.activity.utils.ConstantValue.CAR_STATE;
import static com.exz.antcargo.activity.utils.ConstantValue.COMPANY_STATE;
import static com.exz.antcargo.activity.utils.ConstantValue.GOODS_STATE;
import static com.exz.antcargo.activity.utils.ConstantValue.checkResult;

/**
 * Created by pc on 2016/8/22.
 */

public class CarInfoManageAdapter<T> extends BaseAdapter {
    private List<T> objects = new ArrayList<T>();
    private Activity context;
    private String type;

    public CarInfoManageAdapter(Activity context) {
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
        if (alist == null) {
            return;
        }
        if (isClearOld) {
            objects.clear();
        }
        this.type = type;
        objects.addAll(alist);

    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final List<CarManageBean> list = (List<CarManageBean>) objects;
        ViewHodler viewHodler = null;
        if (convertView == null) {
            viewHodler = new ViewHodler();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_car_info_mannage, null);
            viewHodler.rl_order_detail = (RelativeLayout) convertView.findViewById(R.id.rl_order_detail);
            viewHodler.tv_placeOfReceipt = (TextView) convertView.findViewById(R.id.tv_placeOfReceipt);
            viewHodler.iv_01 = (ImageView) convertView.findViewById(R.id.iv_01);
            viewHodler.tv_entruckingDate = (TextView) convertView.findViewById(R.id.tv_entruckingDate);
            viewHodler.tv_owner_vehicle = (TextView) convertView.findViewById(R.id.tv_owner_vehicle);
            viewHodler.tv_destination = (TextView) convertView.findViewById(R.id.tv_destination);
            viewHodler.ll_View = (LinearLayout) convertView.findViewById(R.id.ll_View);
            viewHodler.ll_baozhengjin = (LinearLayout) convertView.findViewById(R.id.ll_baozhengjin);
            viewHodler.tv_margin_isnull = (TextView) convertView.findViewById(R.id.tv_margin_isnull);
            viewHodler.tv_distanceAround = (TextView) convertView.findViewById(R.id.tv_distanceAround);
            viewHodler.tv_shelvesDate = (TextView) convertView.findViewById(R.id.tv_shelvesDate);
            viewHodler.tv_01 = (TextView) convertView.findViewById(R.id.tv_01);
            viewHodler.tv_manege_state = (TextView) convertView.findViewById(R.id.tv_manege_state);
            viewHodler.tv_02 = (TextView) convertView.findViewById(R.id.tv_02);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }
        if (list.get(position).getUseCarType().equals("1")) {
            viewHodler.iv_01.setBackgroundResource(R.drawable.null_car_lin);
        } else {
            viewHodler.iv_01.setBackgroundResource(R.drawable.shunfeng_car);

        }
        viewHodler.rl_order_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckState.checkCarInfo(context, ConstantValue.AccountType);

                if (ConstantValue.AccountType.equals("1") && type.equals("1")) {
//                    if(list.get(position).getIsClick().equals("true")){
                    Intent intnent = new Intent(context, CarInfoDetailActivity.class);
                    intnent.putExtra("id", list.get(position).getSupplyCarsId());
                    intnent.putExtra("type", type);//1 C查看车主 2 编辑
                    intnent.putExtra("state", TextUtils.isEmpty(list.get(position).getState()) ? "" : list.get(position).getState());// "state":"1在线 2用户下架 3管理员下架"
                    context.startActivity(intnent);
//                    }else{
//                        PopTitleMes.showPopSingle(context, "超出接货范围，司机会得到提示，并主动联系您!");
//                    }
                } else if (ConstantValue.AccountType.equals("2") && type.equals("2")) {
                    Intent intnent = new Intent(context, CarInfoDetailActivity.class);
                    intnent.putExtra("id", list.get(position).getSupplyCarsId());
                    intnent.putExtra("type", type);//1 C查看车主 2 编辑
                    intnent.putExtra("state", TextUtils.isEmpty(list.get(position).getState()) ? "" : list.get(position).getState());// "state":"1在线 2用户下架 3管理员下架"
                    context.startActivity(intnent);
                } else if (ConstantValue.AccountType.equals("2")) {
                    PopTitleMes.showPopSingle(context, "您是车主不可以查看车主详情");
                }


            }
        });
        if (type.equals("1")) {
            viewHodler.ll_View.setVisibility(View.GONE);
            viewHodler.ll_baozhengjin.setVisibility(View.VISIBLE);
        } else {

            viewHodler.ll_baozhengjin.setVisibility(View.VISIBLE);
            viewHodler.ll_View.setVisibility(View.VISIBLE);

        }

        viewHodler.tv_placeOfReceipt.setText(list.get(position).getPlaceOfReceipt());//出发地
        viewHodler.tv_entruckingDate.setText(list.get(position).getEntruckingDate());//出发时间

        if(!TextUtils.isEmpty(list.get(position).getEntruckingDate())&&!list.get(position).getEntruckingDate().equals(" ")){
            viewHodler.tv_entruckingDate.setText(list.get(position).getEntruckingDate());//创建时间
        }else{
            viewHodler.tv_entruckingDate.setText("不限");
        }

        viewHodler.tv_owner_vehicle.setText(list.get(position).getOwner_vehicle());//箱式

        if(!TextUtils.isEmpty(list.get(position).getDestination())){

            viewHodler.tv_destination.setText(list.get(position).getDestination());
        }else{
            viewHodler.tv_destination.setText("不限");
        }

        if (TextUtils.isEmpty(list.get(position).getMargin())) {//失联保证金
            viewHodler.tv_margin_isnull.setText("暂无失联保证金");//目的地

        } else {
            viewHodler.tv_margin_isnull.setText("车主已缴纳" + list.get(position).getMargin() + "元失联保证金");//目的地
        }
        if (TextUtils.isEmpty(list.get(position).getDistanceAround())) {
            viewHodler.tv_distanceAround.setText("0" + "km");//接单区域
        } else {
            viewHodler.tv_distanceAround.setText(list.get(position).getDistanceAround() + "km");//接单区域
        }

        if(!TextUtils.isEmpty(list.get(position).getShelvesDate())){
            viewHodler.tv_shelvesDate.setText(list.get(position).getShelvesDate());//上架时间
        }else{
            viewHodler.tv_shelvesDate.setText("不限");//上架时间
        }

        if (type.equals("2")) {

            switch (list.get(position).getState()) {// "state":"1在线 2用户下架 3管理员下架"
                case "1":
                    viewHodler.tv_01.setText("编辑车源");
                    viewHodler.tv_02.setText("手动下架");
                    viewHodler.tv_manege_state.setText("");
                    break;
                case "2":
                    viewHodler.tv_01.setText("重新上架");
                    viewHodler.tv_02.setText("删除车源");
                    viewHodler.tv_manege_state.setText("");
                    break;

                case "3":
                    viewHodler.tv_01.setText("申请上架");
                    viewHodler.tv_02.setText("删除车源");
                    viewHodler.tv_manege_state.setText("您已被管理员下架!");
                    break;


            }
            viewHodler.tv_01.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (list.get(position).getState()) {// "state":"1在线 2用户下架 3管理员下架"
                        case "1"://编辑
                            Intent intent = new Intent(context, IsuueCarActivity.class);
                            intent.putExtra("editId", list.get(position).getSupplyCarsId());
                            intent.putExtra("edit", "edit");//编辑
                            context.startActivity(intent);
                            break;
                        case "2"://上架
                            editInfo(list.get(position).getSupplyCarsId(), "1", position);//状态：0删除 1上架  2:用户下架
                            break;
                        case "3":
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
                            editInfo(list.get(position).getSupplyCarsId(), "2", position);//状态：0删除 1上架  2:用户下架
                            break;
                        case "2"://删除
                            editInfo(list.get(position).getSupplyCarsId(), "0", position);//状态：0删除 1上架  2:用户下架
                            break;
                        case "3"://删除
                            editInfo(list.get(position).getSupplyCarsId(), "0", position);//状态：0删除 1上架  2:用户下架
                            break;
                    }

                }
            });
        }
        return convertView;
    }

    private void popCheck(String message) {

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
        title.setText(message);
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

                switch (type.equals("1") ? GOODS_STATE : CAR_STATE) {
                    case "-1":
                    case "2":
                        Intent t1 = new Intent(context, IdentityAuditActivity.class);//完善信、信息
                        t1.putExtra("checkResult", checkResult);
                        context.startActivity(t1);
                        break;
                    case "1":
                        switch (type.equals("1") ? COMPANY_STATE : CAR_NUMBER_STATE) {
                            case "-1":
                                Utils.startActivity(context, CarInfoActivity.class);
                                break;
                        }
                        break;
                }
            }
        });
    }

    private void editInfo(String editId, String editState, final int position) {
        XUtilsApi xUtilsApi = new XUtilsApi();
        RequestParams params = new RequestParams(Constant.EDITVEHICLEGOODS);
        params.addBodyParameter("editType", "2");//编辑类型【1货源 2车源】
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

        RelativeLayout rl_order_detail;
        LinearLayout ll_View;
        LinearLayout ll_baozhengjin;
        TextView tv_placeOfReceipt;
        TextView tv_entruckingDate;
        TextView tv_owner_vehicle;
        TextView tv_destination;
        TextView tv_margin_isnull;
        TextView tv_distanceAround;
        TextView tv_shelvesDate;
        TextView tv_manege_state;
        TextView tv_01;
        TextView tv_02;
        ImageView iv_01;

    }
}
