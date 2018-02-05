package com.wanhan.shouyu.bean.json;

/**
 * Created by Administrator on 2018/2/5.
 */

public class RecommendInformationBean {
    private String niceName;
    private String content;
    private String title;
    private String iRecCount;
    private String informationNum;
    private String coverPic;
    private String pic;
    private String isRecomm;
    private String releaseTime;
    private String informationId;
    private String adminUserId;

    public String getNiceName() {
        return niceName;
    }

    public void setNiceName(String niceName) {
        this.niceName = niceName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getiRecCount() {
        return iRecCount;
    }

    public void setiRecCount(String iRecCount) {
        this.iRecCount = iRecCount;
    }

    public String getInformationNum() {
        return informationNum;
    }

    public void setInformationNum(String informationNum) {
        this.informationNum = informationNum;
    }

    public String getCoverPic() {
        return coverPic;
    }

    public void setCoverPic(String coverPic) {
        this.coverPic = coverPic;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getIsRecomm() {
        return isRecomm;
    }

    public void setIsRecomm(String isRecomm) {
        this.isRecomm = isRecomm;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getInformationId() {
        return informationId;
    }

    public void setInformationId(String informationId) {
        this.informationId = informationId;
    }

    public String getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(String adminUserId) {
        this.adminUserId = adminUserId;
    }

    @Override
    public String toString() {
        return "RecommendInformationBean{" +
                "niceName='" + niceName + '\'' +
                ", content='" + content + '\'' +
                ", title='" + title + '\'' +
                ", iRecCount='" + iRecCount + '\'' +
                ", informationNum='" + informationNum + '\'' +
                ", coverPic='" + coverPic + '\'' +
                ", pic='" + pic + '\'' +
                ", isRecomm='" + isRecomm + '\'' +
                ", releaseTime='" + releaseTime + '\'' +
                ", informationId='" + informationId + '\'' +
                ", adminUserId='" + adminUserId + '\'' +
                '}';
    }
}
