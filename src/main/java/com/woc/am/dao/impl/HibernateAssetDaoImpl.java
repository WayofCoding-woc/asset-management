package com.woc.am.dao.impl;

import com.woc.am.AMUtil;
import com.woc.am.constant.USER_ASSET_OPERATION;
import com.woc.am.dao.AssetDao;
import com.woc.am.dao.impl.assembler.AssetAssembler;
import com.woc.am.dao.impl.assembler.AssetTypeAssembler;
import com.woc.am.dao.impl.assembler.UserAssetAuditAssembler;
import com.woc.am.dto.AssetDTO;
import com.woc.am.dto.AssetTypeDTO;
import com.woc.am.dto.UserAssetAuditDTO;
import com.woc.am.persistence.hibernate.model.Asset;
import com.woc.am.persistence.hibernate.model.AssetType;
import com.woc.am.persistence.hibernate.model.UserAssetAudit;
import com.woc.am.persistence.jpa.repository.AssetRepository;
import com.woc.am.persistence.jpa.repository.AssetTypeRepository;
import com.woc.am.persistence.jpa.repository.UserAssetAuditRepository;
import com.woc.am.persistence.jpa.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class HibernateAssetDaoImpl implements AssetDao {
    private static final Logger logger = LoggerFactory.getLogger(HibernateAssetDaoImpl.class);

    @Autowired
    private AssetRepository assetRepository;
    @Autowired
    private AssetAssembler assetAssembler;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAssetAuditRepository userAssetAuditRepository;
    @Autowired
    private AssetTypeRepository assetTypeRepository;
    @Autowired
    private AssetTypeAssembler assetTypeAssembler;
    @Autowired
    private UserAssetAuditAssembler userAssetAuditAssembler;

    @Transactional
    @Override
    public AssetDTO createAsset(AssetDTO assetDTO) {
        Asset asset = assetRepository.save(assetAssembler.toDomain(assetDTO));
        return assetAssembler.fromDomain(asset);
    }

    @Override
    public List<AssetDTO> searchAssets(String assetNo, String assetType, Boolean isAvailable) {
        if(!isBlank(assetNo)){
            List<Asset> assets = assetRepository.findByAssetNo(assetNo);
            return assetAssembler.fromDomain(assets);
        }

        if(isBlank(assetType) && null == isAvailable){
            return assetAssembler.fromDomain((List<Asset>)assetRepository.findAll());
        }

        if(isBlank(assetType) && Boolean.TRUE.equals(isAvailable)){
            return assetAssembler.fromDomain(assetRepository.getAllAvailableAssets());
        }

        if(!isBlank(assetType) && null == isAvailable){
            return assetAssembler.fromDomain(assetRepository.getAllAssetsByType(assetType));
        }

        if(!isBlank(assetType) && Boolean.TRUE.equals(isAvailable)){
            return assetAssembler.fromDomain(assetRepository.getAllAvailableAssetsByType(assetType));
        }

        if(!isBlank(assetType) && Boolean.FALSE.equals(isAvailable)){
            return assetAssembler.fromDomain(assetRepository.getAllAllocatedAssetsByType(assetType));
        }

        if(isBlank(assetType) && Boolean.FALSE.equals(isAvailable)){
            return assetAssembler.fromDomain(assetRepository.getAllAllocatedAssets());
        }

        return Collections.emptyList();
    }

    @Override
    @Transactional
    public void allocateAsset(Integer assetId, Integer userId) {
        if(!userRepository.existsById(userId)){
            throw new RuntimeException("there is no such user in system with userId = " + AMUtil.USER_ID_PREFIX+userId);
        }

        Asset asset = assetRepository.findById(assetId).get();
        if(null != asset.getUserId()){
            throw new RuntimeException("asset is already allocated to userId=" + AMUtil.USER_ID_PREFIX+userId);
        }
        asset.setUserId(userId);
        Date now = new Date();
        asset.setLastUpdatedDate(now);

        UserAssetAudit userAssetAudit = new UserAssetAudit();
        userAssetAudit.setUserId(userId);
        userAssetAudit.setAssetId(assetId);
        userAssetAudit.setOperation(USER_ASSET_OPERATION.ALLOCATE);
        userAssetAudit.setCreatedDate(now);
        userAssetAuditRepository.save(userAssetAudit);
    }

    @Override
    @Transactional
    public void deallocateAsset(Integer assetId) {
        Asset asset = assetRepository.findById(assetId).get();

        if(null == asset.getUserId()){
            throw new RuntimeException("there is no such asset in system with assetId = " + assetId);
        }
        Integer userId = asset.getUserId();
        asset.setUserId(null);
        Date now = new Date();
        asset.setLastUpdatedDate(now);

        UserAssetAudit userAssetAudit = new UserAssetAudit();
        userAssetAudit.setUserId(userId);
        userAssetAudit.setAssetId(assetId);
        userAssetAudit.setOperation(USER_ASSET_OPERATION.DEALLOCATE);
        userAssetAudit.setCreatedDate(now);
        userAssetAuditRepository.save(userAssetAudit);
    }

    @Override
    public AssetTypeDTO getAssetByType(String assetTypeCode) {
        AssetType assetType = assetTypeRepository.findByAssetTypeCode(assetTypeCode);
        return assetTypeAssembler.fromDomain(assetType);
    }

    @Override
    public List<UserAssetAuditDTO> getAssetAudit(Integer assetId) {
        List<UserAssetAudit> userAssetAudits = userAssetAuditRepository.findByAssetId(assetId);
        return userAssetAuditAssembler.fromDomain(userAssetAudits);
    }

    private boolean isBlank(String input){
        if(null == input || "undefined".equals(input) || input.trim().length() == 0){
            return true;
        }
        return false;
    }
}
