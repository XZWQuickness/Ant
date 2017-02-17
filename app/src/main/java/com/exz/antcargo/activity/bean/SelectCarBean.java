package com.exz.antcargo.activity.bean;

/**
 * Created by pc on 2016/9/6.
 */

public class SelectCarBean
{

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    /**
     * vehicleLength : 车长
     * modelsName : 车型
     * price : 起步价
     * deadweightTonnage : 载重吨位
     * id : 车辆编号
     * state : 0未审核 1审核通过 2拒绝 3禁用
     * mileage : 起步公里数
     */

    private String vehicleId;
    private String vehicleLength;
    private String modelsName;
    private String price;
    private String deadweightTonnage;
    private String id;
    private String state;
    private String mileage;

    public void setVehicleLength(String vehicleLength) {
        this.vehicleLength = vehicleLength;
    }

    public void setModelsName(String modelsName) {
        this.modelsName = modelsName;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDeadweightTonnage(String deadweightTonnage) {
        this.deadweightTonnage = deadweightTonnage;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getVehicleLength() {
        return vehicleLength;
    }

    public String getModelsName() {
        return modelsName;
    }

    public String getPrice() {
        return price;
    }

    public String getDeadweightTonnage() {
        return deadweightTonnage;
    }

    public String getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    public String getMileage() {
        return mileage;
    }
}
