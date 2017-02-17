package com.exz.antcargo.activity.bean;

import java.io.Serializable;

/**
 * Created by pc on 2016/9/6.
 */

public class AuditCarInfoBean implements Serializable {


    /**
     * checkState : 2
     * vehicleId : 30
     * checkResult : {"vehicleLength":{"check":"1","value":"1.0"},"modelsName":{"check":"1","value":"平板"},"vehicleWidth":{"check":"1","value":"1.0"},"deadweightTonnage":{"check":"1","value":"1.00"},"vehicles":{"check":"2","value":"http://myhd.xzsem.cn/owner/20160913090745785334101.jpg"},"message":"","vehicleLicense":{"check":"2","value":"http://myhd.xzsem.cn/owner/20160913090745832210302.jpg"}}
     */
    private String checkState;
    private String vehicleId;
    private CheckResultEntity checkResult;

    public void setCheckState(String checkState) {
        this.checkState = checkState;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public void setCheckResult(CheckResultEntity checkResult) {
        this.checkResult = checkResult;
    }

    public String getCheckState() {
        return checkState;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public CheckResultEntity getCheckResult() {
        return checkResult;
    }

    public class CheckResultEntity implements Serializable {
        /**
         * vehicleLength : {"check":"1","value":"1.0"}
         * modelsName : {"check":"1","value":"平板"}
         * vehicleWidth : {"check":"1","value":"1.0"}
         * deadweightTonnage : {"check":"1","value":"1.00"}
         * vehicles : {"check":"2","value":"http://myhd.xzsem.cn/owner/20160913090745785334101.jpg"}
         * message :
         * vehicleLicense : {"check":"2","value":"http://myhd.xzsem.cn/owner/20160913090745832210302.jpg"}
         */
        private VehicleLengthEntity vehicleLength;
        private ModelsNameEntity modelsName;
        private VehicleWidthEntity vehicleWidth;
        private DeadweightTonnageEntity deadweightTonnage;
        private VehiclesEntity vehicles;
        private String message;
        private VehicleLicenseEntity vehicleLicense;

        public void setVehicleLength(VehicleLengthEntity vehicleLength) {
            this.vehicleLength = vehicleLength;
        }

        public void setModelsName(ModelsNameEntity modelsName) {
            this.modelsName = modelsName;
        }

        public void setVehicleWidth(VehicleWidthEntity vehicleWidth) {
            this.vehicleWidth = vehicleWidth;
        }

        public void setDeadweightTonnage(DeadweightTonnageEntity deadweightTonnage) {
            this.deadweightTonnage = deadweightTonnage;
        }

        public void setVehicles(VehiclesEntity vehicles) {
            this.vehicles = vehicles;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public void setVehicleLicense(VehicleLicenseEntity vehicleLicense) {
            this.vehicleLicense = vehicleLicense;
        }

        public VehicleLengthEntity getVehicleLength() {
            return vehicleLength;
        }

        public ModelsNameEntity getModelsName() {
            return modelsName;
        }

        public VehicleWidthEntity getVehicleWidth() {
            return vehicleWidth;
        }

        public DeadweightTonnageEntity getDeadweightTonnage() {
            return deadweightTonnage;
        }

        public VehiclesEntity getVehicles() {
            return vehicles;
        }

        public String getMessage() {
            return message;
        }

        public VehicleLicenseEntity getVehicleLicense() {
            return vehicleLicense;
        }

        public class VehicleLengthEntity implements Serializable {
            /**
             * check : 1
             * value : 1.0
             */
            private String check;
            private String value;

            public void setCheck(String check) {
                this.check = check;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getCheck() {
                return check;
            }

            public String getValue() {
                return value;
            }
        }

        public class ModelsNameEntity implements Serializable  {
            /**
             * check : 1
             * value : 平板
             */
            private String check;
            private String value;

            public void setCheck(String check) {
                this.check = check;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getCheck() {
                return check;
            }

            public String getValue() {
                return value;
            }
        }

            public class VehicleWidthEntity implements Serializable {
            /**
             * check : 1
             * value : 1.0
             */
            private String check;
            private String value;

            public void setCheck(String check) {
                this.check = check;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getCheck() {
                return check;
            }

            public String getValue() {
                return value;
            }
        }

        public class DeadweightTonnageEntity implements Serializable {
            /**
             * check : 1
             * value : 1.00
             */
            private String check;
            private String value;

            public void setCheck(String check) {
                this.check = check;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getCheck() {
                return check;
            }

            public String getValue() {
                return value;
            }
        }

        public class VehiclesEntity implements Serializable  {
            /**
             * check : 2
             * value : http://myhd.xzsem.cn/owner/20160913090745785334101.jpg
             */
            private String check;
            private String value;

            public void setCheck(String check) {
                this.check = check;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getCheck() {
                return check;
            }

            public String getValue() {
                return value;
            }
        }

        public class VehicleLicenseEntity implements Serializable {
            /**
             * check : 2
             * value : http://myhd.xzsem.cn/owner/20160913090745832210302.jpg
             */
            private String check;
            private String value;

            public void setCheck(String check) {
                this.check = check;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getCheck() {
                return check;
            }

            public String getValue() {
                return value;
            }
        }
    }
}
