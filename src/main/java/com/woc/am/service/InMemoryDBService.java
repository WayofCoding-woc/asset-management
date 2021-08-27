package com.woc.am.service;

import com.woc.am.dto.AssetDTO;
import com.woc.am.dto.AssetTypeDTO;
import com.woc.am.dto.UserDTO;
import java.util.Date;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class InMemoryDBService {
    private static final Logger logger = LoggerFactory.getLogger(InMemoryDBService.class);

    @Value("${spring.datasource.url:}")
    private String dbUrl;
    @Autowired private UserService userService;
    @Autowired private AssetService assetService;

    @PostConstruct
    private void init(){
        try{
            if(StringUtils.isBlank(dbUrl)){
                logger.info("*************going to create a dummy user with username=woc and password=pass inside the in-memory db***************");
                createDummyUser();
                AssetTypeDTO assetTypeLaptopDTOFromDB = createAssetType("Laptop");
                AssetTypeDTO assetTypeSeatDTOFromDB = createAssetType("Seat");

                createAsset(assetTypeLaptopDTOFromDB, "pqr-123", "lenovo E3490");
                createAsset(assetTypeLaptopDTOFromDB, "mnp-4334", "dell lattitude 5400");
                logger.info("************Congratulations! A dummy user has been created for you, Now you can login with credentials username=woc and password=pass");
            }
        }catch (Exception e){
            logger.error("Unable to init the user login data to in-memory database", e);
        }
    }

    private void createAsset(AssetTypeDTO assetTypeLaptopDTOFromDB, String assetNo, String assetName) {
        AssetDTO assetDTO = new AssetDTO();
        assetDTO.setAssetNo(assetNo);
        assetDTO.setAssetName(assetName);
        assetDTO.setAssetType(assetTypeLaptopDTOFromDB);
        Date now = new Date();
        assetDTO.setCreatedDate(now);
        assetDTO.setLastUpdatedDate(now);
        assetService.createAsset(assetDTO);
    }

    private AssetTypeDTO createAssetType(String type) {
        AssetTypeDTO assetTypeDTO = new AssetTypeDTO();
        assetTypeDTO.setAssetTypeCode(type);
        assetTypeDTO.setAssetTypeDescription(type);
        AssetTypeDTO assetTypeDTOFromDB = assetService.createAssetByType(assetTypeDTO);
        return assetTypeDTOFromDB;
    }

    private void createDummyUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setLoginId("woc");
        userDTO.setPassword("pass");
        userDTO.setUserName("WayOfCoding Java Training");
        userDTO.setEmail("wayofcoding@gmail.com");
        userDTO.setMobile(8095553563l);
        userDTO.setDateOfJoining(new Date());
        userDTO.setActive(true);
        userDTO.setRole("admin");
        userDTO.setCreatedDate(new Date());
        userService.createUser(userDTO);
    }


}
