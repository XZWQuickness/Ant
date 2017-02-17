package com.exz.antcargo.activity.bean;

import java.util.List;

/**
 * Created by pc on 2016/9/2.
 */

public class VehicleBean extends CarBean {

    /**
     * carLenght : [{"name":"车长名称","id":"11"}]
     * name : 车型名称
     * id : 1
     */
    private List<CarLenghtEntity> carLenght;

    public List<CarLenghtEntity> getCarLenght() {
        return carLenght;
    }

    public void setCarLenght(List<CarLenghtEntity> carLenght) {
        this.carLenght = carLenght;
    }

    public class CarLenghtEntity extends CarBean{

    }
}
