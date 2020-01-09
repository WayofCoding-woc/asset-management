package com.woc.am.persistence.jpa.repository;

import com.woc.am.persistence.hibernate.model.AssetType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetTypeRepository extends CrudRepository<AssetType, Integer> {
    AssetType findByAssetTypeCode(String assetTypeCode);
}
