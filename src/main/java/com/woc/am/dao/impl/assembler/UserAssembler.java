package com.woc.am.dao.impl.assembler;

import com.woc.am.dto.AssetDTO;
import com.woc.am.dto.UserDTO;
import com.woc.am.persistence.hibernate.model.Asset;
import com.woc.am.persistence.hibernate.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class UserAssembler {
    @Autowired
    private AssetAssembler assetAssembler;

    public User toDomain(UserDTO dto){
        if(null == dto){
            return null;
        }

        User u = new User();
        if(dto.getId() != null){
            u.setId(dto.getId());
        }

        u.setLoginId(dto.getLoginId());
        u.setPassword(dto.getPassword());
        u.setUserName(dto.getUserName());
        u.setEmail(dto.getEmail());
        u.setMobile(dto.getMobile());
        u.setDateOfJoining(dto.getDateOfJoining());
        u.setCreatedDate(dto.getCreatedDate());
        u.setActive(dto.isActive());

        List<Asset> assets = assetAssembler.toDomain(dto.getAssets());
        u.setAssets(assets);
        return u;
    }

    public List<UserDTO> fromDomain(List<User> users){
        List<UserDTO> userDTOS = new LinkedList<>();
        for(User e : users){
            userDTOS.add(fromDomain(e));
        }
        return userDTOS;
    }

    public UserDTO fromDomain(User user){
        if(null == user){
            return null;
        }
        UserDTO dto = new UserDTO();
        if(user.getId() != null){
            dto.setId(user.getId());
        }

        dto.setLoginId(user.getLoginId());
        dto.setPassword(user.getPassword());
        dto.setUserName(user.getUserName());
        dto.setEmail(user.getEmail());
        dto.setMobile(user.getMobile());
        dto.setDateOfJoining(user.getDateOfJoining());
        dto.setCreatedDate(user.getCreatedDate());
        dto.setActive(user.isActive());

        List<AssetDTO> assets = assetAssembler.fromDomain(user.getAssets());
        dto.setAssets(assets);

        return dto;
    }
}
