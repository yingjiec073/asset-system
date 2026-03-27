package com.example.assetsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.assetsystem.dto.ApiResponse;
import com.example.assetsystem.entity.Vehicle;
import com.example.assetsystem.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public ApiResponse<List<Vehicle>> list() {
        List<Vehicle> vehicles = vehicleService.list(
                new LambdaQueryWrapper<Vehicle>().orderByDesc(Vehicle::getCreatedAt)
        );
        return ApiResponse.ok(vehicles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Vehicle>> detail(@PathVariable Long id) {
        Vehicle vehicle = vehicleService.getById(id);
        if (vehicle == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.fail("车辆不存在"));
        }
        return ResponseEntity.ok(ApiResponse.ok(vehicle));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Vehicle>> create(@Valid @RequestBody Vehicle vehicle) {
        vehicle.setId(null);
        boolean saved = vehicleService.save(vehicle);
        if (!saved) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.fail("新增失败"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(vehicle));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Vehicle>> update(@PathVariable Long id, @Valid @RequestBody Vehicle vehicle) {
        if (!vehicleService.exists(new LambdaQueryWrapper<Vehicle>().eq(Vehicle::getId, id))) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.fail("车辆不存在"));
        }
        vehicle.setId(id);
        boolean updated = vehicleService.updateById(vehicle);
        if (!updated) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.fail("更新失败"));
        }
        return ResponseEntity.ok(ApiResponse.ok(vehicleService.getById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        boolean removed = vehicleService.removeById(id);
        if (!removed) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.fail("车辆不存在或已删除"));
        }
        return ResponseEntity.ok(ApiResponse.ok(null));
    }
}
