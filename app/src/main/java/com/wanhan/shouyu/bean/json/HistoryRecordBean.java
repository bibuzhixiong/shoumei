package com.wanhan.shouyu.bean.json;

/**
 * Created by Administrator on 2018/2/8.
 */

public class HistoryRecordBean {
    private String historicalRecordId;
    private String userId;
    private String weight;
    private String bodyFatRate;
    private String fat;
    private String visceralFat;
    private String protein;
    private String moisture;
    private String muscle;
    private String skeletalMuscle;
    private String bone;
    private String healthIndex;
    private String physicalAge;
    private String metabolism;
    private String BMI;
    private String weightLoss;
    private String reducedFat;
    private String recordTime;
    private String weightTotal;
    private String fatTotal;

    public String getHistoricalRecordId() {
        return historicalRecordId;
    }

    public void setHistoricalRecordId(String historicalRecordId) {
        this.historicalRecordId = historicalRecordId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBodyFatRate() {
        return bodyFatRate;
    }

    public void setBodyFatRate(String bodyFatRate) {
        this.bodyFatRate = bodyFatRate;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getVisceralFat() {
        return visceralFat;
    }

    public void setVisceralFat(String visceralFat) {
        this.visceralFat = visceralFat;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getMoisture() {
        return moisture;
    }

    public void setMoisture(String moisture) {
        this.moisture = moisture;
    }

    public String getMuscle() {
        return muscle;
    }

    public void setMuscle(String muscle) {
        this.muscle = muscle;
    }

    public String getSkeletalMuscle() {
        return skeletalMuscle;
    }

    public void setSkeletalMuscle(String skeletalMuscle) {
        this.skeletalMuscle = skeletalMuscle;
    }

    public String getBone() {
        return bone;
    }

    public void setBone(String bone) {
        this.bone = bone;
    }

    public String getHealthIndex() {
        return healthIndex;
    }

    public void setHealthIndex(String healthIndex) {
        this.healthIndex = healthIndex;
    }

    public String getPhysicalAge() {
        return physicalAge;
    }

    public void setPhysicalAge(String physicalAge) {
        this.physicalAge = physicalAge;
    }

    public String getMetabolism() {
        return metabolism;
    }

    public void setMetabolism(String metabolism) {
        this.metabolism = metabolism;
    }

    public String getBMI() {
        return BMI;
    }

    public void setBMI(String BMI) {
        this.BMI = BMI;
    }

    public String getWeightLoss() {
        return weightLoss;
    }

    public void setWeightLoss(String weightLoss) {
        this.weightLoss = weightLoss;
    }

    public String getReducedFat() {
        return reducedFat;
    }

    public void setReducedFat(String reducedFat) {
        this.reducedFat = reducedFat;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public String getWeightTotal() {
        return weightTotal;
    }

    public void setWeightTotal(String weightTotal) {
        this.weightTotal = weightTotal;
    }

    public String getFatTotal() {
        return fatTotal;
    }

    public void setFatTotal(String fatTotal) {
        this.fatTotal = fatTotal;
    }

    @Override
    public String toString() {
        return "HistoryRecordBean{" +
                "historicalRecordId='" + historicalRecordId + '\'' +
                ", userId='" + userId + '\'' +
                ", weight='" + weight + '\'' +
                ", bodyFatRate='" + bodyFatRate + '\'' +
                ", fat='" + fat + '\'' +
                ", visceralFat='" + visceralFat + '\'' +
                ", protein='" + protein + '\'' +
                ", moisture='" + moisture + '\'' +
                ", muscle='" + muscle + '\'' +
                ", skeletalMuscle='" + skeletalMuscle + '\'' +
                ", bone='" + bone + '\'' +
                ", healthIndex='" + healthIndex + '\'' +
                ", physicalAge='" + physicalAge + '\'' +
                ", metabolism='" + metabolism + '\'' +
                ", BMI='" + BMI + '\'' +
                ", weightLoss='" + weightLoss + '\'' +
                ", reducedFat='" + reducedFat + '\'' +
                ", recordTime='" + recordTime + '\'' +
                ", weightTotal='" + weightTotal + '\'' +
                ", fatTotal='" + fatTotal + '\'' +
                '}';
    }
}
