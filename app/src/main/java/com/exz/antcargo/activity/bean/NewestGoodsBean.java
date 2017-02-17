package com.exz.antcargo.activity.bean;

/**
 * Created by pc on 2016/9/5.
 */

public class NewestGoodsBean {

    /**
     * entruckingDate : 2016-07-22 10:55
     * goodsid : 2
     * origin : 徐州市泉山区淮海西路120号
     * destination : 徐州市第一人民医院
     * shelvesDate : 5天前
     * goodsType : 100台电脑//
     */
    private String entruckingDate;
    private String goodsid;
    private String origin;
    private String destination;
    private String shelvesDate;
    private String goodsType;
    private String state;
    private String originLongitude;
    private String originLatitude;

    public String getOriginLongitude() {
        return originLongitude;
    }

    public void setOriginLongitude(String originLongitude) {
        this.originLongitude = originLongitude;
    }

    public String getOriginLatitude() {
        return originLatitude;
    }

    public void setOriginLatitude(String originLatitude) {
        this.originLatitude = originLatitude;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setEntruckingDate(String entruckingDate) {
        this.entruckingDate = entruckingDate;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setShelvesDate(String shelvesDate) {
        this.shelvesDate = shelvesDate;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getEntruckingDate() {
        return entruckingDate;
    }

    public String getGoodsid() {
        return goodsid;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getShelvesDate() {
        return shelvesDate;
    }

    public String getGoodsType() {
        return goodsType;
    }
}
