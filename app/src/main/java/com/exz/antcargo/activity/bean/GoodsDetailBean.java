package com.exz.antcargo.activity.bean;

import java.io.Serializable;

/**
 * Created by pc on 2016/9/7.
 */

public class GoodsDetailBean implements Serializable{



    /**
     * goodsImg : ["http://myhd.xzsem.cn/shipper/20160925073449767243400.jpg","http://myhd.xzsem.cn/shipper/20160925073449767243401.jpg","http://myhd.xzsem.cn/shipper/20160925073449782869103.jpg","http://myhd.xzsem.cn/shipper/20160925073449782869105.jpg"]
     * note : 我是测试数据，请无视。
     * originLatitude : 34.263531
     * entruckingTime : 12:00-14:00
     * carWidth : 3.0
     * origin : 中国江苏省徐州市泉山区和平街道淮海西路
     * shelvesDate : 53分钟前
     * destination : 中国江苏省徐州市泉山区和平街道淮海西路
     * goodsWeight : 1
     * referencePrice : 15
     * destinationLatitude : 34.263115
     * goodsName : 棉被
     * entruckingDate : 2016-09-27
     * goodsVolume : 1
     * carManagement :
     * destinationLongitude : 117.173305
     * carLenght : 12.0
     * accountId : 48
     * goodsCount : 1
     * hopePrice : 12-19
     * carManagementId : 1
     * originLongitude : 117.173196
     * goodsPackagingName : 裸装
     * deadweightTonnage : 2.00
     * useCarTypeId : 1
     */
    private String note;
    private String originLatitude;
    private String entruckingTime;
    private String carWidth;
    private String origin;
    private String shelvesDate;
    private String destination;
    private String goodsWeight;
    private String referencePrice;
    private String destinationLatitude;
    private String goodsName;
    private String entruckingDate;
    private String goodsVolume;
    private String carManagement;
    private String destinationLongitude;
    private String carLenght;
    private String accountId;
    private String goodsCount;
    private String hopePrice;
    private String carManagementId;
    private String originLongitude;
    private String goodsPackagingName;
    private String setGoodsPackagingId;
    private String deadweightTonnage;
    private String useCarTypeId="0";
    private String goodsImg;
    private String deleteGoodsImg;

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public String getDeleteGoodsImg() {
        return deleteGoodsImg;
    }

    public void setDeleteGoodsImg(String deleteGoodsImg) {
        this.deleteGoodsImg = deleteGoodsImg;
    }

    public String getSetGoodsPackagingId() {
        return setGoodsPackagingId;
    }

    public void setSetGoodsPackagingId(String setGoodsPackagingId) {
        this.setGoodsPackagingId = setGoodsPackagingId;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setOriginLatitude(String originLatitude) {
        this.originLatitude = originLatitude;
    }

    public void setEntruckingTime(String entruckingTime) {
        this.entruckingTime = entruckingTime;
    }

    public void setCarWidth(String carWidth) {
        this.carWidth = carWidth;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setShelvesDate(String shelvesDate) {
        this.shelvesDate = shelvesDate;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setGoodsWeight(String goodsWeight) {
        this.goodsWeight = goodsWeight;
    }

    public void setReferencePrice(String referencePrice) {
        this.referencePrice = referencePrice;
    }

    public void setDestinationLatitude(String destinationLatitude) {
        this.destinationLatitude = destinationLatitude;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public void setEntruckingDate(String entruckingDate) {
        this.entruckingDate = entruckingDate;
    }

    public void setGoodsVolume(String goodsVolume) {
        this.goodsVolume = goodsVolume;
    }

    public void setCarManagement(String carManagement) {
        this.carManagement = carManagement;
    }

    public void setDestinationLongitude(String destinationLongitude) {
        this.destinationLongitude = destinationLongitude;
    }

    public void setCarLenght(String carLenght) {
        this.carLenght = carLenght;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setGoodsCount(String goodsCount) {
        this.goodsCount = goodsCount;
    }

    public void setHopePrice(String hopePrice) {
        this.hopePrice = hopePrice;
    }

    public void setCarManagementId(String carManagementId) {
        this.carManagementId = carManagementId;
    }

    public void setOriginLongitude(String originLongitude) {
        this.originLongitude = originLongitude;
    }

    public void setGoodsPackagingName(String goodsPackagingName) {
        this.goodsPackagingName = goodsPackagingName;
    }

    public void setDeadweightTonnage(String deadweightTonnage) {
        this.deadweightTonnage = deadweightTonnage;
    }

    public void setUseCarTypeId(String useCarTypeId) {
        this.useCarTypeId = useCarTypeId;
    }


    public String getNote() {
        return note;
    }

    public String getOriginLatitude() {
        return originLatitude;
    }

    public String getEntruckingTime() {
        return entruckingTime;
    }

    public String getCarWidth() {
        return carWidth;
    }

    public String getOrigin() {
        return origin;
    }

    public String getShelvesDate() {
        return shelvesDate;
    }

    public String getDestination() {
        return destination;
    }

    public String getGoodsWeight() {
        return goodsWeight;
    }

    public String getReferencePrice() {
        return referencePrice;
    }

    public String getDestinationLatitude() {
        return destinationLatitude;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public String getEntruckingDate() {
        return entruckingDate;
    }

    public String getGoodsVolume() {
        return goodsVolume;
    }

    public String getCarManagement() {
        return carManagement;
    }

    public String getDestinationLongitude() {
        return destinationLongitude;
    }

    public String getCarLenght() {
        return carLenght;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getGoodsCount() {
        return goodsCount;
    }

    public String getHopePrice() {
        return hopePrice;
    }

    public String getCarManagementId() {
        return carManagementId;
    }

    public String getOriginLongitude() {
        return originLongitude;
    }

    public String getGoodsPackagingName() {
        return goodsPackagingName;
    }

    public String getDeadweightTonnage() {
        return deadweightTonnage;
    }

    public String getUseCarTypeId() {
        return useCarTypeId;
    }
}
