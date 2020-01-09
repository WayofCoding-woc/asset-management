package com.woc.am.persistence.hibernate.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "am_asset_type")
public class AssetType implements Serializable {
    private static final long serialVersionUID = -7822415916223856652L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "asset_type_code")
    private String assetTypeCode;

    @Column(name = "asset_type_description")
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
        return "AssetType{" +
                "id=" + id +
                ", assetTypeCode='" + assetTypeCode + '\'' +
                ", assetTypeDescription='" + assetTypeDescription + '\'' +
                '}';
    }
}
