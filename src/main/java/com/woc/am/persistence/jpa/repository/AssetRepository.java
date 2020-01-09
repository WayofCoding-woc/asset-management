package com.woc.am.persistence.jpa.repository;

import com.woc.am.persistence.hibernate.model.Asset;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetRepository extends CrudRepository<Asset, Integer> {
    List<Asset> findByAssetName(String assetName);
    List<Asset> findByAssetNo(String assetNo);

    @Query("from Asset a where a.assetType.assetTypeCode=:assetTypeCode")
    List<Asset> getAllAssetsByType(@Param("assetTypeCode") String assetTypeCode);

    @Query("from Asset a where a.userId is null")
    List<Asset> getAllAvailableAssets();

    @Query("from Asset a where a.assetType.assetTypeCode=:assetTypeCode and a.userId is null")
    List<Asset> getAllAvailableAssetsByType(@Param("assetTypeCode") String assetTypeCode);

    @Query("from Asset a where a.assetType.assetTypeCode=:assetTypeCode and a.userId is not null")
    List<Asset> getAllAllocatedAssetsByType(@Param("assetTypeCode") String assetTypeCode);

    @Query("from Asset a where a.userId is not null")
    List<Asset> getAllAllocatedAssets();
}
