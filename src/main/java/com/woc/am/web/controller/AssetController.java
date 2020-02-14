package com.woc.am.web.controller;

import com.woc.am.dto.AssetDTO;
import com.woc.am.dto.AssetTypeDTO;
import com.woc.am.dto.UserAssetAuditDTO;
import com.woc.am.service.AssetService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/asset")
public class AssetController {
    private static final Logger logger = LoggerFactory.getLogger(AssetController.class);

    @Autowired
    private AssetService assetService;

    @PostMapping(value = "/createAsset")
    public ModelAndView createAsset(HttpServletRequest request){
        logger.debug("createAsset api got triggered");
        String assetNo = request.getParameter("assetNo");
        String assetName = request.getParameter("assetName");
        String assetType = request.getParameter("assetType");

        AssetTypeDTO assetTypeDTO = assetService.getAssetByType(assetType);

        AssetDTO assetDTO = new AssetDTO();
        assetDTO.setAssetNo(assetNo);
        assetDTO.setAssetName(assetName);
        assetDTO.setAssetType(assetTypeDTO);
        Date now = new Date();
        assetDTO.setCreatedDate(now);
        assetDTO.setLastUpdatedDate(now);

        logger.debug("createAsset api input={}", assetDTO);

        AssetDTO savedAsset = null;
        try{
            savedAsset = assetService.createAsset(assetDTO);
        }catch (Exception e){
            logger.error("Unable to create asset", e);
            return new ModelAndView("redirect:./../../asset_creation_error.html");
        }

        return new ModelAndView("redirect:./../../manage_assets.html?assetNo="+savedAsset.getAssetNo());
    }

    @GetMapping(value = "/searchAsset")
    public ResponseEntity<?> searchAsset(@RequestParam("assetNo") String assetNo,
                                         @RequestParam("assetType") String assetType,
                                         @RequestParam("isAvailable") Boolean isAvailable
                                         ){
        logger.debug("searchAsset api got triggered for assetNo={}, assetType={}, isAvailable={}", assetNo, assetType, isAvailable);
        List<AssetDTO> assets = assetService.searchAssets(assetNo, assetType, isAvailable);
        return new ResponseEntity<>(assets, HttpStatus.OK);
    }

    @PostMapping(value = "/allocate")
    public ResponseEntity<?> allocateAsset(@RequestBody Map<String, Object> data){
        Integer assetId = Integer.valueOf(data.get("assetId").toString());
        String userId = (String) data.get("userId");
        logger.debug("allocateAsset api got triggered for assetId={}, userId={}", assetId, userId);
        try{
            assetService.allocateAsset(assetId, userId);
        }catch (Exception e){
            return new ResponseEntity<>(Map.of("errorMsg", e.getMessage()), HttpStatus.OK);
        }
        return new ResponseEntity<>(Map.of("errorMsg", StringUtils.EMPTY),HttpStatus.OK);
    }

    @PostMapping(value = "/deallocate")
    public ResponseEntity<?> deallocateAsset(@RequestBody Map<String, Object> data){
        Integer assetId = Integer.valueOf(data.get("assetId").toString());
        logger.debug("deallocateAsset api got triggered for assetId={}", assetId);
        try{
            assetService.deallocateAsset(assetId);
        }catch (Exception e){
            return new ResponseEntity<>(Map.of("errorMsg", e.getMessage()), HttpStatus.OK);
        }
        return new ResponseEntity<>(Map.of("errorMsg", StringUtils.EMPTY),HttpStatus.OK);
    }

    @GetMapping(value = "/audit")
    public ResponseEntity<?> getAssetAudit(@RequestParam("assetId") Integer assetId){
        logger.debug("getAssetAudit api got triggered for assetId={}", assetId);
        List<UserAssetAuditDTO> assetAuditDTOS = assetService.getAssetAudit(assetId);
        return new ResponseEntity<>(assetAuditDTOS, HttpStatus.OK);
    }

}
