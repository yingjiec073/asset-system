package com.example.assetsystem.controller;

import com.example.assetsystem.dto.ApiResponse;
import com.example.assetsystem.dto.asset.AssetOperationRequest;
import com.example.assetsystem.service.AssetService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/scrap")
public class ScrapController {

    private final AssetService assetService;

    public ScrapController(AssetService assetService) {
        this.assetService = assetService;
    }

    @PostMapping
    public ApiResponse<Void> scrap(@Valid @RequestBody AssetOperationRequest request) {
        request.setType("SCRAP");
        assetService.processOperation(request);
        return ApiResponse.ok(null);
    }
}
