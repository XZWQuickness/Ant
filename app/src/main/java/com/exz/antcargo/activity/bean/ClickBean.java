package com.exz.antcargo.activity.bean;

/**
 * Created by pc on 2016/9/6.
 */

public class ClickBean {

    /**
     * vehicleHistoryId : 1
     * name : 叶**
     * time : 2016-8-30 12:00 - 13:00
     */
    private String vehicleHistoryId;
    private String name;
    private String time;
    private String supplyCarId;
    private String supplyGoodId;
    private String fromId;

    public String getSupplyCarId() {
        return supplyCarId;
    }

    public void setSupplyCarId(String supplyCarId) {
        this.supplyCarId = supplyCarId;
    }

    public String getSupplyGoodId() {
        return supplyGoodId;
    }

    public void setSupplyGoodId(String supplyGoodId) {
        this.supplyGoodId = supplyGoodId;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public void setVehicleHistoryId(String vehicleHistoryId) {
        this.vehicleHistoryId = vehicleHistoryId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVehicleHistoryId() {
        return vehicleHistoryId;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }
}
