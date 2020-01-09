package com.woc.am.persistence.hibernate.model;

import com.woc.am.constant.USER_ASSET_OPERATION;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "am_user_asset_audit")
public class UserAssetAudit implements Serializable {
    private static final long serialVersionUID = 3065254841766055235L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "asset_id", nullable = false)
    private Integer assetId;

    @Enumerated(EnumType.STRING)
    @Column(name = "operation", nullable = false)
    private USER_ASSET_OPERATION operation;

    @Column(name = "created_date", nullable = false)
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

    @Override
    public String toString() {
        return "UserAssetAudit{" +
                "id=" + id +
                ", userId=" + userId +
                ", assetId=" + assetId +
                ", operation=" + operation +
                ", createdDate=" + createdDate +
                '}';
    }
}
