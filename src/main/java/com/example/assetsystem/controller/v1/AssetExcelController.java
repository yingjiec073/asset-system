package com.example.assetsystem.controller.v1;

import com.example.assetsystem.annotation.RequirePermission;
import com.example.assetsystem.dto.ApiResponse;
import com.example.assetsystem.entity.Asset;
import com.example.assetsystem.service.AssetService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/v1/assets/excel")
public class AssetExcelController {

    private final AssetService assetService;

    public AssetExcelController(AssetService assetService) {
        this.assetService = assetService;
    }

    @PostMapping("/import")
    @RequirePermission("asset:import")
    public ApiResponse<Map<String, Object>> importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        List<String> errors = new ArrayList<>();
        int success = 0;
        try (XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream())) {
            XSSFSheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;
                try {
                    Asset a = new Asset();
                    a.setName(HtmlUtils.htmlEscape(cell(row,0)));
                    a.setCategory(HtmlUtils.htmlEscape(cell(row,1)));
                    a.setSerialNumber(HtmlUtils.htmlEscape(cell(row,2)));
                    a.setStatus(StringUtils.hasText(cell(row,3)) ? cell(row,3) : "IN_STOCK");
                    a.setDepartmentId(Long.parseLong(cell(row,4)));
                    a.setPurchaseDate(LocalDate.parse(cell(row,5)));
                    a.setPurchasePrice(new BigDecimal(cell(row,6)));
                    if (!StringUtils.hasText(a.getName()) || !StringUtils.hasText(a.getCategory())) {
                        throw new IllegalArgumentException("name/category required");
                    }
                    assetService.save(a);
                    success++;
                } catch (Exception e) {
                    errors.add("row " + (i + 1) + ": " + e.getMessage());
                }
            }
        }
        return ApiResponse.ok(Map.of("success", success, "errors", errors));
    }

    @GetMapping("/export")
    @RequirePermission("asset:export")
    public void exportExcel(HttpServletResponse response) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("assets");
            Row header = sheet.createRow(0);
            String[] headers = {"name", "category", "serialNumber", "status", "departmentId", "purchaseDate", "purchasePrice"};
            for (int i = 0; i < headers.length; i++) header.createCell(i).setCellValue(headers[i]);
            List<Asset> assets = assetService.list();
            for (int i = 0; i < assets.size(); i++) {
                Asset a = assets.get(i);
                Row row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(Optional.ofNullable(a.getName()).orElse(""));
                row.createCell(1).setCellValue(Optional.ofNullable(a.getCategory()).orElse(""));
                row.createCell(2).setCellValue(Optional.ofNullable(a.getSerialNumber()).orElse(""));
                row.createCell(3).setCellValue(Optional.ofNullable(a.getStatus()).orElse(""));
                row.createCell(4).setCellValue(Optional.ofNullable(a.getDepartmentId()).orElse(0L));
                row.createCell(5).setCellValue(a.getPurchaseDate() == null ? "" : a.getPurchaseDate().toString());
                row.createCell(6).setCellValue(a.getPurchasePrice() == null ? "" : a.getPurchasePrice().toPlainString());
            }
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=assets.xlsx");
            workbook.write(response.getOutputStream());
        }
    }

    private String cell(Row row, int index) {
        return row.getCell(index) == null ? "" : row.getCell(index).toString().trim();
    }
}
