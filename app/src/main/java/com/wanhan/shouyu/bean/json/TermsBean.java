package com.wanhan.shouyu.bean.json;

/**
 * Created by Administrator on 2018/1/29.
 */

public class TermsBean {
    private String content;
    private String serviceTermsId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getServiceTermsId() {
        return serviceTermsId;
    }

    public void setServiceTermsId(String serviceTermsId) {
        this.serviceTermsId = serviceTermsId;
    }

    @Override
    public String toString() {
        return "TermsBean{" +
                "content='" + content + '\'' +
                ", serviceTermsId='" + serviceTermsId + '\'' +
                '}';
    }
}
