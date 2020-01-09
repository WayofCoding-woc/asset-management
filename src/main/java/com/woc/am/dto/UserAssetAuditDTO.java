package com.woc.am.dto;

import com.woc.am.constant.USER_ASSET_OPERATION;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;

public class UserAssetAuditDTO implements Serializable {
    private static final long serialVersionUID = 3065254841766055235L;

    private Integer id;
    private Integer userId;
    private Integer assetId;
    private USER_ASSET_OPERATION operation;
    private Date createdDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public USER_ASSET_OPERATION getOperation() {
        return operation;
    }

    public void setOperation(USER_ASSET_OPERATION operation) {
        this.operation = operation;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedDateStr(){
        if(null == this.createdDate){
            return StringUtils.EMPTY;
        }
        return this.createdDate.toLocaleString();
    }

    @Override
    public String toString() {
        return "UserAssetAuditDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", assetId=" + assetId +
                ", operation=" + operation +
                ", createdDate=" + createdDate +
                '}';
    }
}
