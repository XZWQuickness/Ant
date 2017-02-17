package com.exz.antcargo.activity.bean;

import java.io.Serializable;

/**
 * Created by pc on 2016/9/9.
 * 货物包装
 */

public class GoodsBaoZhaungBean implements Serializable {

    private String goodsName;
    private String goodsWeight;
    private String goodsVolume;
    private String goodsPackagingId;
    private String goodsCount;
    private String goodspackId;
    private String name;

    public String getGoodspackId() {
        return goodspackId;
    }

    public void setGoodspackId(String goodspackId) {
        this.goodspackId = goodspackId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsWeight() {
        return goodsWeight;
    }

    public void setGoodsWeight(String goodsWeight) {
        this.goodsWeight = goodsWeight;
    }

    public String getGoodsVolume() {
        return goodsVolume;
    }

    public void setGoodsVolume(String goodsVolume) {
        this.goodsVolume = goodsVolume;
    }

    public String getGoodsPackagingId() {
        return goodsPackagingId;
    }

    public void setGoodsPackagingId(String goodsPackagingId) {
        this.goodsPackagingId = goodsPackagingId;
    }

    public String getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(String goodsCount) {
        this.goodsCount = goodsCount;
    }
}
