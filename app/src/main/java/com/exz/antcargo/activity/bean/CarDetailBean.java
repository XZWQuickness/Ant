package com.exz.antcargo.activity.bean;

import java.io.Serializable;

/**
 * Created by pc on 2016/9/7.
 */

public class CarDetailBean implements Serializable {
    /**
     * placeOfReceipt : 徐州市泉山区淮海西路120号
     * accountId : 10
     * note : 快快联系我
     * margin : 0.00
     * entruckingDate : 2016-7-22 10:55:00
     * alongRoad : 颖都大厦,二院
     * shelvesDate : 2016-8-31 17:16:43
     * destination : 北京北京市朝阳区淮海西路120号
     * owner_Vehicle_Cer : 平板/10.00/0-5米
     * distanceAround : 3
     * useCarType : 2
     */
    private String placeOfReceipt;
    private String accountId;

    public String getVehicleImg() {
        return vehicleImg;
    }

    public void setVehicleImg(String vehicleImg) {
        this.vehicleImg = vehicleImg;
    }

    private String note;
    private String margin;
    private String entruckingDate;

    public String getEntruckingTime() {
        return entruckingTime;
    }

    public void setEntruckingTime(String entruckingTime) {
        this.entruckingTime = entruckingTime;
    }

    private String entruckingTime;
    private String alongRoad;
    private String shelvesDate;
    private String destination;
    private String owner_Vehicle_Cer;
    private String vehicleImg;
    private String distanceAround;
    private String useCarType;
    private String placeOfReceiptLongitude;
    private String placeOfReceiptLatitude;
    private String destinationAddress;

    public String getPlaceOfReceiptLongitude() {
        return placeOfReceiptLongitude;
    }

    public void setPlaceOfReceiptLongitude(String placeOfReceiptLongitude) {
        this.placeOfReceiptLongitude = placeOfReceiptLongitude;
    }

    public String getPlaceOfReceiptLatitude() {
        return placeOfReceiptLatitude;
    }

    public void setPlaceOfReceiptLatitude(String placeOfReceiptLatitude) {
        this.placeOfReceiptLatitude = placeOfReceiptLatitude;
    }

    public String getOwner_Vehicle_CerId() {
        return owner_Vehicle_CerId;
    }

    public void setOwner_Vehicle_CerId(String owner_Vehicle_CerId) {
        this.owner_Vehicle_CerId = owner_Vehicle_CerId;
    }

    private String owner_Vehicle_CerId;


    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public void setPlaceOfReceipt(String placeOfReceipt) {
        this.placeOfReceipt = placeOfReceipt;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setMargin(String margin) {
        this.margin = margin;
    }

    public void setEntruckingDate(String entruckingDate) {
        this.entruckingDate = entruckingDate;
    }

    public void setAlongRoad(String alongRoad) {
        this.alongRoad = alongRoad;
    }

    public void setShelvesDate(String shelvesDate) {
        this.shelvesDate = shelvesDate;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setOwner_Vehicle_Cer(String owner_Vehicle_Cer) {
        this.owner_Vehicle_Cer = owner_Vehicle_Cer;
    }

    public void setDistanceAround(String distanceAround) {
        this.distanceAround = distanceAround;
    }

    public void setUseCarType(String useCarType) {
        this.useCarType = useCarType;
    }

    public String getPlaceOfReceipt() {
        return placeOfReceipt;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getNote() {
        return note;
    }

    public String getMargin() {
        return margin;
    }

    public String getEntruckingDate() {
        return entruckingDate;
    }

    public String getAlongRoad() {
        return alongRoad;
    }

    public String getShelvesDate() {
        return shelvesDate;
    }

    public String getDestination() {
        return destination;
    }

    public String getOwner_Vehicle_Cer() {
        return owner_Vehicle_Cer;
    }

    public String getDistanceAround() {
        return distanceAround;
    }

    public String getUseCarType() {
        return useCarType;
    }
}
