package com.wanhan.shouyu.bean.json;

/**
 * Created by Administrator on 2018/1/25.
 */

public class UserBean extends CodeBean{
    private String loginInfold;
    private String userId;
    private String niceName;
    private String icon;
    private String sex;
    private String height;
    private String birthday;
    private String userLevel;
    private String integral;
    private String coachId;
    private String coachPhone;
    private String registerTime;
    private String phone;

    public String getLoginInfold() {
        return loginInfold;
    }

    public void setLoginInfold(String loginInfold) {
        this.loginInfold = loginInfold;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNiceName() {
        return niceName;
    }

    public void setNiceName(String niceName) {
        this.niceName = niceName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getCoachId() {
        return coachId;
    }

    public void setCoachId(String coachId) {
        this.coachId = coachId;
    }

    public String getCoachPhone() {
        return coachPhone;
    }

    public void setCoachPhone(String coachPhone) {
        this.coachPhone = coachPhone;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "loginInfold='" + loginInfold + '\'' +
                ", userId='" + userId + '\'' +
                ", niceName='" + niceName + '\'' +
                ", icon='" + icon + '\'' +
                ", sex='" + sex + '\'' +
                ", height='" + height + '\'' +
                ", birthday='" + birthday + '\'' +
                ", userLevel='" + userLevel + '\'' +
                ", integral='" + integral + '\'' +
                ", coachId='" + coachId + '\'' +
                ", coachPhone='" + coachPhone + '\'' +
                ", registerTime='" + registerTime + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
