package com.woc.am.dao.impl.assembler;

import com.woc.am.dto.AssetDTO;
import com.woc.am.persistence.hibernate.model.Asset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
public class AssetAssembler {

    @Autowired
    private AssetTypeAssembler assetTypeAssembler;

    public List<Asset> toDomain(List<AssetDTO> dtos) {
        if(null == dtos){
            return Collections.EMPTY_LIST;
        }
        List<Asset> assets = new LinkedList<>();
        for(AssetDTO dto : dtos){
            assets.add(toDomain(dto));
        }
        return assets;
    }

    public Asset toDomain(AssetDTO dto) {
        Asset asset = new Asset();
        if (dto.getId() != null) {
            asset.setId(dto.getId());
        }

        asset.setAssetNo(dto.getAssetNo());
        asset.setAssetName(dto.getAssetName());
        asset.setAssetType(assetTypeAssembler.toDomain(dto.getAssetType()));
        asset.setUserId(dto.getUserId());
        asset.setCreatedDate(dto.getCreatedDate());
        asset.setLastUpdatedDate(dto.getLastUpdatedDate());

        return asset;
    }

    public List<AssetDTO> fromDomain(List<Asset> assets) {
        if(null == assets){
            return Collections.emptyList();
        }
        List<AssetDTO> dtos = new LinkedList<>();
        for(Asset asset : assets){
            dtos.add(fromDomain(asset));
        }
        return dtos;
    }
    public AssetDTO fromDomain(Asset asset) {
        AssetDTO dto = new AssetDTO();
        if (asset.getId() != null) {
            dto.setId(asset.getId());
        }

        dto.setAssetNo(asset.getAssetNo());
        dto.setAssetName(asset.getAssetName());
        dto.setAssetType(assetTypeAssembler.fromDomain(asset.getAssetType()));
        dto.setUserId(asset.getUserId());
        dto.setCreatedDate(asset.getCreatedDate());
        dto.setLastUpdatedDate(asset.getLastUpdatedDate());

        return dto;
    }

}
