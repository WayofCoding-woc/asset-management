package com.woc.am.dao;

import com.woc.am.dto.AssetDTO;
import com.woc.am.dto.AssetTypeDTO;
import com.woc.am.dto.UserAssetAuditDTO;

import java.util.List;

public interface AssetDao {
    AssetDTO createAsset(AssetDTO asset);
    List<AssetDTO> searchAssets(String assetNo, String assetType, Boolean isAvailable);
    void allocateAsset(Integer assetId, Integer userId);
    void deallocateAsset(Integer assetId);
    AssetTypeDTO getAssetByType(String assetType);
    AssetTypeDTO createAssetByType(AssetTypeDTO assetTypeDTO);
    List<UserAssetAuditDTO> getAssetAudit(Integer assetId);
}
