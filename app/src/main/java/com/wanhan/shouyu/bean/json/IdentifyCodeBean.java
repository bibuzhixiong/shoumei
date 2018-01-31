package com.wanhan.shouyu.bean.json;

/**
 * Created by Administrator on 2018/1/31.
 */

public class IdentifyCodeBean {
    private String code;
    private String phoneVerification;
    private String validationPhone_;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhoneVerification() {
        return phoneVerification;
    }

    public void setPhoneVerification(String phoneVerification) {
        this.phoneVerification = phoneVerification;
    }

    public String getValidationPhone_() {
        return validationPhone_;
    }

    public void setValidationPhone_(String validationPhone_) {
        this.validationPhone_ = validationPhone_;
    }

    @Override
    public String toString() {
        return "IdentifyCodeBean{" +
                "code='" + code + '\'' +
                ", phoneVerification='" + phoneVerification + '\'' +
                ", validationPhone_='" + validationPhone_ + '\'' +
                '}';
    }
}
