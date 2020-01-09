package com.woc.am.persistence.jpa.repository;

import com.woc.am.persistence.hibernate.model.UserAssetAudit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAssetAuditRepository extends CrudRepository<UserAssetAudit, Integer> {
    List<UserAssetAudit> findByAssetId(Integer assetId);
}
