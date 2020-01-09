package com.woc.am.dao.impl.assembler;

import com.woc.am.dto.UserAssetAuditDTO;
import com.woc.am.persistence.hibernate.model.UserAssetAudit;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
public class UserAssetAuditAssembler {

    public List<UserAssetAudit> toDomain(List<UserAssetAuditDTO> dtos) {
        if(null == dtos){
            return Collections.EMPTY_LIST;
        }
        List<UserAssetAudit> userAssetAudits = new LinkedList<>();
        for(UserAssetAuditDTO dto : dtos){
            userAssetAudits.add(toDomain(dto));
        }
        return userAssetAudits;
    }

    public UserAssetAudit toDomain(UserAssetAuditDTO dto) {
        UserAssetAudit userAssetAudit = new UserAssetAudit();
        if (dto.getId() != null) {
            userAssetAudit.setId(dto.getId());
        }

        userAssetAudit.setUserId(dto.getUserId());
        userAssetAudit.setAssetId(dto.getAssetId());
        userAssetAudit.setOperation(dto.getOperation());
        userAssetAudit.setCreatedDate(dto.getCreatedDate());

        return userAssetAudit;
    }

    public List<UserAssetAuditDTO> fromDomain(List<UserAssetAudit> userAssetAudits) {
        List<UserAssetAuditDTO> dtos = new LinkedList<>();
        for(UserAssetAudit assetAudit : userAssetAudits){
            dtos.add(fromDomain(assetAudit));
        }
        return dtos;
    }

    public UserAssetAuditDTO fromDomain(UserAssetAudit userAssetAudit) {
        UserAssetAuditDTO dto = new UserAssetAuditDTO();
        if (userAssetAudit.getId() != null) {
            dto.setId(userAssetAudit.getId());
        }

        dto.setUserId(userAssetAudit.getUserId());
        dto.setAssetId(userAssetAudit.getAssetId());
        dto.setOperation(userAssetAudit.getOperation());
        dto.setCreatedDate(userAssetAudit.getCreatedDate());

        return dto;
    }

}
