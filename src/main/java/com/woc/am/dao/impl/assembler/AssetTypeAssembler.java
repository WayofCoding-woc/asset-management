package com.woc.am.dao.impl.assembler;

import com.woc.am.dto.AssetTypeDTO;
import com.woc.am.persistence.hibernate.model.AssetType;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
public class AssetTypeAssembler {

    public List<AssetType> toDomain(List<AssetTypeDTO> dtos) {
        if(null == dtos){
            return Collections.EMPTY_LIST;
        }
        List<AssetType> assetTypes = new LinkedList<>();
        for(AssetTypeDTO dto : dtos){
            assetTypes.add(toDomain(dto));
        }
        return assetTypes;
    }

    public AssetType toDomain(AssetTypeDTO dto) {
        AssetType assetType = new AssetType();
        if (dto.getId() != null) {
            assetType.setId(dto.getId());
        }

        assetType.setAssetTypeCode(dto.getAssetTypeCode());
        assetType.setAssetTypeDescription(dto.getAssetTypeDescription());

        return assetType;
    }

    public List<AssetTypeDTO> fromDomain(List<AssetType> assetTypes) {
        List<AssetTypeDTO> dtos = new LinkedList<>();
        for(AssetType assetType : assetTypes){
            dtos.add(fromDomain(assetType));
        }
        return dtos;
    }

    public AssetTypeDTO fromDomain(AssetType assetType) {
        AssetTypeDTO dto = new AssetTypeDTO();
        if (assetType.getId() != null) {
            dto.setId(assetType.getId());
        }

        dto.setAssetTypeCode(assetType.getAssetTypeCode());
        dto.setAssetTypeDescription(assetType.getAssetTypeDescription());

        return dto;
    }

}
