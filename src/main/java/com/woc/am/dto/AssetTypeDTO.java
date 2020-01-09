package com.woc.am.dto;

import java.io.Serializable;

public class AssetTypeDTO implements Serializable {
    private static final long serialVersionUID = 2776135771063024217L;

    private Integer id;

    private String assetTypeCode;

    private String assetTypeDescription;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAssetTypeCode() {
        return assetTypeCode;
    }

    public void setAssetTypeCode(String assetTypeCode) {
        this.assetTypeCode = assetTypeCode;
    }

    public String getAssetTypeDescription() {
        return assetTypeDescription;
    }

    public void setAssetTypeDescription(String assetTypeDescription) {
        this.assetTypeDescription = assetTypeDescription;
    }

    @Override
    public String toString() {
        return "AssetTypeDTO{" +
                "id=" + id +
                ", assetTypeCode='" + assetTypeCode + '\'' +
                ", assetTypeDescription='" + assetTypeDescription + '\'' +
                '}';
    }
}
