package com.exz.antcargo.activity.utils;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;

public class LocationUtils {
    /**
     * 定义接口
     */
    public interface LocationCallBack {
        //回调函数
        public void Location();

        public void LocationFail();
    }

    private static LocationCallBack locationCallBack;

    public void setLocationListener(LocationCallBack locationCallBack) {
        this.locationCallBack = locationCallBack;
    }

    /**
     * 通过高德地图来进行定位
     */
    public static void checkLocation(final Context context,
                                     LocationManagerProxy mLocationManager) {
        mLocationManager.requestLocationData(LocationProviderProxy.AMapNetwork,
                -1, 100, new AMapLocationListener() {

                    @Override
                    public void onStatusChanged(String provider, int status,
                                                Bundle extras) {
                    }

                    @Override
                    public void onProviderEnabled(String provider) {
                    }

                    @Override
                    public void onProviderDisabled(String provider) {
                    }

                    @Override
                    public void onLocationChanged(Location location) {
                    }

                    @Override
                    public void onLocationChanged(AMapLocation amapLocation) {
                        if (amapLocation != null
                                && amapLocation.getAMapException()
                                .getErrorCode() == 0) {
                            // 获取位置信息
//							Double lat = amapLocation.getLatitude();
//							Double lng = amapLocation.getLongitude();


                            ConstantValue.ProvinceName = amapLocation.getProvince();
                            ConstantValue.Address = amapLocation.getAddress();
                            ConstantValue.CityName = amapLocation.getCity();
                            ConstantValue.Area = amapLocation.getDistrict();
                            ConstantValue.Latitude = amapLocation.getLatitude();
                            ConstantValue.Longitude = amapLocation.getLongitude();


                            locationCallBack.Location();
//							// 详细地址
//							Bundle locBundle = amapLocation.getExtras();
//							if (locBundle != null) {
////								Const.desc = locBundle.getString("desc");
//							}
                        } else if (amapLocation.getAMapException()
                                .getErrorCode() == 23) {
//                            Toast.makeText(context, "连接超时,网络不稳定,请检查后重试",
//                                    Toast.LENGTH_SHORT).show();
                            if (locationCallBack != null) {
                                locationCallBack.LocationFail();
                            }
                        } else {
//                            Toast.makeText(context, "定位失败，请开启网络和定位!",
//                                    Toast.LENGTH_SHORT).show();
                            if (locationCallBack != null) {
                                locationCallBack.LocationFail();
                            }
                        }

                    }
                });
    }
}
