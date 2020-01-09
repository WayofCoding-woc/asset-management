package com.woc.am.dto;

import com.woc.am.AMUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class UserDTO implements Serializable {
    private static final long serialVersionUID = 6198435926462297136L;

    private Integer id;
    private String loginId;
    private String password;
    private String userName;
    private Long mobile;
    private String email;
    private Date dateOfJoining;
    private boolean isActive;
    private Date createdDate;
    private List<AssetDTO> assets;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserDisplayId(){
        return AMUtil.USER_ID_PREFIX + this.getId();
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getMobile() {
        return mobile;
    }

    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(Date dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public List<AssetDTO> getAssets() {
        return assets;
    }

    public void setAssets(List<AssetDTO> assets) {
        this.assets = assets;
    }

    public String getDateOfJoiningStr(){
        if(null == this.dateOfJoining){
            return StringUtils.EMPTY;
        }
        return AMUtil.formatToDatePartOnly(this.dateOfJoining);
    }

    public String getCreatedDateStr(){
        if(null == this.createdDate){
            return StringUtils.EMPTY;
        }
        return this.createdDate.toLocaleString();
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", loginId='" + loginId + '\'' +
                ", password='" + password + '\'' +
                ", userName='" + userName + '\'' +
                ", mobile=" + mobile +
                ", email='" + email + '\'' +
                ", dateOfJoining=" + dateOfJoining +
                ", isActive=" + isActive +
                ", createdDate=" + createdDate +
                ", assets=" + assets +
                '}';
    }
}
