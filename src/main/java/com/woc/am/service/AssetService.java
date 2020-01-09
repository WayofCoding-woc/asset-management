package com.woc.am.service;

import com.woc.am.AMUtil;
import com.woc.am.dao.AssetDao;
import com.woc.am.dto.AssetDTO;
import com.woc.am.dto.AssetTypeDTO;
import com.woc.am.dto.UserAssetAuditDTO;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetService {
    private static final Logger logger = LoggerFactory.getLogger(AssetService.class);

    @Autowired
    private AssetDao assetDao;

    public AssetDTO createAsset(AssetDTO asset){
        return assetDao.createAsset(asset);
    }

    public List<AssetDTO> searchAssets(String assetNo, String assetType, Boolean isAvailable){
        return assetDao.searchAssets(assetNo, assetType, isAvailable);
    }

    public void allocateAsset(Integer assetId, String userId) {
        if(userId.startsWith(AMUtil.USER_ID_PREFIX)){
            userId = userId.substring(AMUtil.USER_ID_PREFIX.length());
        }

        if(!NumberUtils.isDigits(userId)){
            throw new RuntimeException("UserId is not in correct format");
        }

        Integer userIdWithoutPrefix = Integer.valueOf(userId);

        assetDao.allocateAsset(assetId, userIdWithoutPrefix);
    }

    public void deallocateAsset(Integer assetId) {
        assetDao.deallocateAsset(assetId);
    }

    public AssetTypeDTO getAssetByType(String assetType) {
        return assetDao.getAssetByType(assetType);
    }

    public List<UserAssetAuditDTO> getAssetAudit(Integer assetId) {
        return assetDao.getAssetAudit(assetId);
    }
}
