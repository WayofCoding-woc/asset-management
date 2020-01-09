package com.woc.am.dto;

import com.woc.am.AMUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;

public class AssetDTO implements Serializable {
    private static final long serialVersionUID = -4325466587702555518L;

    private Integer id;
    private String assetNo;
    private String assetName;
    private AssetTypeDTO assetType;
    private Integer userId;
    private Date createdDate;
    private Date lastUpdatedDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAssetNo() {
        return assetNo;
    }

    public void setAssetNo(String assetNo) {
        this.assetNo = assetNo;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public AssetTypeDTO getAssetType() {
        return assetType;
    }

    public void setAssetType(AssetTypeDTO assetType) {
        this.assetType = assetType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserDisplayId(){
        if(null == this.getUserId()){
            return StringUtils.EMPTY;
        }
        return AMUtil.USER_ID_PREFIX + this.getUserId();
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getCreatedDateStr(){
        if(null == this.createdDate){
            return StringUtils.EMPTY;
        }
        return this.createdDate.toLocaleString();
    }

    public String getLastUpdatedDateStr(){
        if(null == this.lastUpdatedDate){
            return StringUtils.EMPTY;
        }
        return this.lastUpdatedDate.toLocaleString();
    }

    @Override
    public String toString() {
        return "AssetDTO{" +
                "id=" + id +
                ", assetNo='" + assetNo + '\'' +
                ", assetName='" + assetName + '\'' +
                ", assetType=" + assetType +
                ", userId=" + userId +
                ", createdDate=" + createdDate +
                ", lastUpdatedDate=" + lastUpdatedDate +
                '}';
    }
}
