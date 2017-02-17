package com.exz.antcargo.activity.bean;

/**
 * Created by pc on 2016/9/20.
 */

public class CheckCarInfo {

    /**
     * result : success
     * message : 审核结果提示
     * info : {"accountId":"10","checkState":"2","checkResult":{"area":{"check":"1","value":"泉山区"},"models":{"check":"1","value":"平板"},"city":{"check":"1","value":"徐州市"},"idCard":{"check":"1","value":"http://myhd.xzsem.cn/owner/20160905023412073557803.jpg"},"vehicles":{"check":"2","value":"http://myhd.xzsem.cn/owner/20160905031510105126106.jpg"},"userName":{"check":"1","value":"xx"},"idCardPositive":{"check":"1","value":"http://myhd.xzsem.cn/owner/20160905023412057932501.jpg"},"message":"","idCardReverse":{"check":"1","value":"http://myhd.xzsem.cn/owner/20160905023412073557802.jpg"},"vehicleLength":{"check":"1","value":"1.0×1.0"},"province":{"check":"1","value":"江苏省"},"contact":{"check":"1","value":"13236027713"},"idCardNumber":{"check":"1","value":"320324199503202565"},"driverLicense":{"check":"1","value":"http://myhd.xzsem.cn/owner/20160905031510105126104.jpg"},"deadweightTonnage":{"check":"2","value":"1.00"},"vehicleLicense":{"check":"2","value":"http://myhd.xzsem.cn/owner/20160905031510105126105.jpg"}}}
     */
    private String result;
    private String message;

    private InfoEntity info;

    public void setResult(String result) {
        this.result = result;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setInfo(InfoEntity info) {
        this.info = info;
    }

    public String getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public InfoEntity getInfo() {
        return info;
    }

    public class InfoEntity {
        /**
         * accountId : 10
         * checkState : 2
         * checkResult : {"area":{"check":"1","value":"泉山区"},"models":{"check":"1","value":"平板"},"city":{"check":"1","value":"徐州市"},"idCard":{"check":"1","value":"http://myhd.xzsem.cn/owner/20160905023412073557803.jpg"},"vehicles":{"check":"2","value":"http://myhd.xzsem.cn/owner/20160905031510105126106.jpg"},"userName":{"check":"1","value":"xx"},"idCardPositive":{"check":"1","value":"http://myhd.xzsem.cn/owner/20160905023412057932501.jpg"},"message":"","idCardReverse":{"check":"1","value":"http://myhd.xzsem.cn/owner/20160905023412073557802.jpg"},"vehicleLength":{"check":"1","value":"1.0×1.0"},"province":{"check":"1","value":"江苏省"},"contact":{"check":"1","value":"13236027713"},"idCardNumber":{"check":"1","value":"320324199503202565"},"driverLicense":{"check":"1","value":"http://myhd.xzsem.cn/owner/20160905031510105126104.jpg"},"deadweightTonnage":{"check":"2","value":"1.00"},"vehicleLicense":{"check":"2","value":"http://myhd.xzsem.cn/owner/20160905031510105126105.jpg"}}
         */
        private String accountId;
        private String checkState;
        private String throughVehicle;
        private CheckResultEntity checkResult;

        public String getThroughVehicle() {
            return throughVehicle;
        }

        public void setThroughVehicle(String throughVehicle) {
            this.throughVehicle = throughVehicle;
        }

        public void setAccountId(String accountId) {
            this.accountId = accountId;
        }

        public void setCheckState(String checkState) {
            this.checkState = checkState;
        }

        public void setCheckResult(CheckResultEntity checkResult) {
            this.checkResult = checkResult;
        }

        public String getAccountId() {
            return accountId;
        }

        public String getCheckState() {
            return checkState;
        }

        public CheckResultEntity getCheckResult() {
            return checkResult;
        }

        public class CheckResultEntity {
            /**
             * area : {"check":"1","value":"泉山区"}
             * models : {"check":"1","value":"平板"}
             * city : {"check":"1","value":"徐州市"}
             * idCard : {"check":"1","value":"http://myhd.xzsem.cn/owner/20160905023412073557803.jpg"}
             * vehicles : {"check":"2","value":"http://myhd.xzsem.cn/owner/20160905031510105126106.jpg"}
             * userName : {"check":"1","value":"xx"}
             * idCardPositive : {"check":"1","value":"http://myhd.xzsem.cn/owner/20160905023412057932501.jpg"}
             * message :
             * idCardReverse : {"check":"1","value":"http://myhd.xzsem.cn/owner/20160905023412073557802.jpg"}
             * vehicleLength : {"check":"1","value":"1.0×1.0"}
             * province : {"check":"1","value":"江苏省"}
             * contact : {"check":"1","value":"13236027713"}
             * idCardNumber : {"check":"1","value":"320324199503202565"}
             * driverLicense : {"check":"1","value":"http://myhd.xzsem.cn/owner/20160905031510105126104.jpg"}
             * deadweightTonnage : {"check":"2","value":"1.00"}
             * vehicleLicense : {"check":"2","value":"http://myhd.xzsem.cn/owner/20160905031510105126105.jpg"}
             */
            private AreaEntity area;
            private ModelsEntity models;
            private CityEntity city;
            private IdCardEntity idCard;
            private VehiclesEntity vehicles;
            private UserNameEntity userName;
            private IdCardPositiveEntity idCardPositive;
            private String message;
            private IdCardReverseEntity idCardReverse;
            private VehicleLengthEntity vehicleLength;
            private ProvinceEntity province;
            private ContactEntity contact;
            private IdCardNumberEntity idCardNumber;
            private DriverLicenseEntity driverLicense;
            private DeadweightTonnageEntity deadweightTonnage;
            private VehicleLicenseEntity vehicleLicense;

            public void setArea(AreaEntity area) {
                this.area = area;
            }

            public void setModels(ModelsEntity models) {
                this.models = models;
            }

            public void setCity(CityEntity city) {
                this.city = city;
            }

            public void setIdCard(IdCardEntity idCard) {
                this.idCard = idCard;
            }

            public void setVehicles(VehiclesEntity vehicles) {
                this.vehicles = vehicles;
            }

            public void setUserName(UserNameEntity userName) {
                this.userName = userName;
            }

            public void setIdCardPositive(IdCardPositiveEntity idCardPositive) {
                this.idCardPositive = idCardPositive;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public void setIdCardReverse(IdCardReverseEntity idCardReverse) {
                this.idCardReverse = idCardReverse;
            }

            public void setVehicleLength(VehicleLengthEntity vehicleLength) {
                this.vehicleLength = vehicleLength;
            }

            public void setProvince(ProvinceEntity province) {
                this.province = province;
            }

            public void setContact(ContactEntity contact) {
                this.contact = contact;
            }

            public void setIdCardNumber(IdCardNumberEntity idCardNumber) {
                this.idCardNumber = idCardNumber;
            }

            public void setDriverLicense(DriverLicenseEntity driverLicense) {
                this.driverLicense = driverLicense;
            }

            public void setDeadweightTonnage(DeadweightTonnageEntity deadweightTonnage) {
                this.deadweightTonnage = deadweightTonnage;
            }

            public void setVehicleLicense(VehicleLicenseEntity vehicleLicense) {
                this.vehicleLicense = vehicleLicense;
            }

            public AreaEntity getArea() {
                return area;
            }

            public ModelsEntity getModels() {
                return models;
            }

            public CityEntity getCity() {
                return city;
            }

            public IdCardEntity getIdCard() {
                return idCard;
            }

            public VehiclesEntity getVehicles() {
                return vehicles;
            }

            public UserNameEntity getUserName() {
                return userName;
            }

            public IdCardPositiveEntity getIdCardPositive() {
                return idCardPositive;
            }

            public String getMessage() {
                return message;
            }

            public IdCardReverseEntity getIdCardReverse() {
                return idCardReverse;
            }

            public VehicleLengthEntity getVehicleLength() {
                return vehicleLength;
            }

            public ProvinceEntity getProvince() {
                return province;
            }

            public ContactEntity getContact() {
                return contact;
            }

            public IdCardNumberEntity getIdCardNumber() {
                return idCardNumber;
            }

            public DriverLicenseEntity getDriverLicense() {
                return driverLicense;
            }

            public DeadweightTonnageEntity getDeadweightTonnage() {
                return deadweightTonnage;
            }

            public VehicleLicenseEntity getVehicleLicense() {
                return vehicleLicense;
            }

            public class AreaEntity {
                /**
                 * check : 1
                 * value : 泉山区
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

            public class ModelsEntity {
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

            public class CityEntity {
                /**
                 * check : 1
                 * value : 徐州市
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

            public class IdCardEntity {
                /**
                 * check : 1
                 * value : http://myhd.xzsem.cn/owner/20160905023412073557803.jpg
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

            public class VehiclesEntity {
                /**
                 * check : 2
                 * value : http://myhd.xzsem.cn/owner/20160905031510105126106.jpg
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

            public class UserNameEntity {
                /**
                 * check : 1
                 * value : xx
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

            public class IdCardPositiveEntity {
                /**
                 * check : 1
                 * value : http://myhd.xzsem.cn/owner/20160905023412057932501.jpg
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

            public class IdCardReverseEntity {
                /**
                 * check : 1
                 * value : http://myhd.xzsem.cn/owner/20160905023412073557802.jpg
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

            public class VehicleLengthEntity {
                /**
                 * check : 1
                 * value : 1.0×1.0
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

            public class ProvinceEntity {
                /**
                 * check : 1
                 * value : 江苏省
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

            public class ContactEntity {
                /**
                 * check : 1
                 * value : 13236027713
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

            public class IdCardNumberEntity {
                /**
                 * check : 1
                 * value : 320324199503202565
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

            public class DriverLicenseEntity {
                /**
                 * check : 1
                 * value : http://myhd.xzsem.cn/owner/20160905031510105126104.jpg
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

            public class DeadweightTonnageEntity {
                /**
                 * check : 2
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

            public class VehicleLicenseEntity {
                /**
                 * check : 2
                 * value : http://myhd.xzsem.cn/owner/20160905031510105126105.jpg
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
}
