package com.wanhan.shouyu.bean.json;

/**
 * Created by Administrator on 2018/1/30.
 */

public class RegisterBean extends CodeBean{
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "RegisterBean{" +
                "userId='" + userId + '\'' +
                '}';
    }
}
