package com.exz.antcargo.activity.bean;

/**
 * Created by pc on 2016/9/7.
 */

public class CarManageBean {

    /**
     * placeOfReceipt : 收货地
     * entruckingDate : 2016-8-30 12:00 - 13:00
     * margin : 保证金
     * destination : 目的地
     * shelvesDate : 几秒前、几分钟前、几小时前......
     * distanceAround : 接单区域
     * state : 1在线 2用户下架 3管理员下架
     * supplyCarsId : 车源编号
     * owner_vehicle : 箱式/2.0吨4.2米
     */
    private String placeOfReceipt;
    private String entruckingDate;
    private String margin;
    private String destination;
    private String shelvesDate;
    private String distanceAround;
    private String state;
    private String useCarType;
    private String supplyCarsId;
    private String owner_vehicle;
    private String isClick;

    public String getIsClick() {
        return isClick;
    }

    public void setIsClick(String isClick) {
        this.isClick = isClick;
    }

    public String getUseCarType() {
        return useCarType;
    }

    public void setUseCarType(String useCarType) {
        this.useCarType = useCarType;
    }

    public void setPlaceOfReceipt(String placeOfReceipt) {
        this.placeOfReceipt = placeOfReceipt;
    }

    public void setEntruckingDate(String entruckingDate) {
        this.entruckingDate = entruckingDate;
    }

    public void setMargin(String margin) {
        this.margin = margin;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setShelvesDate(String shelvesDate) {
        this.shelvesDate = shelvesDate;
    }

    public void setDistanceAround(String distanceAround) {
        this.distanceAround = distanceAround;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setSupplyCarsId(String supplyCarsId) {
        this.supplyCarsId = supplyCarsId;
    }

    public void setOwner_vehicle(String owner_vehicle) {
        this.owner_vehicle = owner_vehicle;
    }

    public String getPlaceOfReceipt() {
        return placeOfReceipt;
    }

    public String getEntruckingDate() {
        return entruckingDate;
    }

    public String getMargin() {
        return margin;
    }

    public String getDestination() {
        return destination;
    }

    public String getShelvesDate() {
        return shelvesDate;
    }

    public String getDistanceAround() {
        return distanceAround;
    }

    public String getState() {
        return state;
    }

    public String getSupplyCarsId() {
        return supplyCarsId;
    }

    public String getOwner_vehicle() {
        return owner_vehicle;
    }
}
