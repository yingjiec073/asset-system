# asset-system

资产管理系统后端（车辆资产模块），基于 **Java 17 + Spring Boot 3 + MyBatis-Plus + MySQL + Maven**。

## 1. 项目结构

```text
asset-system
├── pom.xml
├── sql
│   └── vehicle.sql
└── src
    └── main
        ├── java/com/example/assetsystem
        │   ├── AssetSystemApplication.java
        │   ├── config
        │   │   └── MybatisPlusMetaObjectHandler.java
        │   ├── controller
        │   │   ├── GlobalExceptionHandler.java
        │   │   └── VehicleController.java
        │   ├── dto
        │   │   └── ApiResponse.java
        │   ├── entity
        │   │   └── Vehicle.java
        │   ├── mapper
        │   │   └── VehicleMapper.java
        │   └── service
        │       ├── VehicleService.java
        │       └── impl
        │           └── VehicleServiceImpl.java
        └── resources
            └── application.yml
```

## 2. 数据库建表 SQL

执行：`sql/vehicle.sql`

```sql
CREATE DATABASE IF NOT EXISTS asset_system DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE asset_system;

CREATE TABLE IF NOT EXISTS vehicle (
    id BIGINT PRIMARY KEY,
    plate_number VARCHAR(20) NOT NULL COMMENT '车牌号',
    vin VARCHAR(50) NOT NULL COMMENT '车架号',
    brand VARCHAR(50) NOT NULL COMMENT '品牌',
    model VARCHAR(50) NOT NULL COMMENT '型号',
    purchase_date DATE NOT NULL COMMENT '购买日期',
    purchase_price DECIMAL(12,2) NOT NULL COMMENT '购买价格',
    status VARCHAR(20) NOT NULL COMMENT '状态（在用/维修/闲置/报废）',
    department_id BIGINT NOT NULL COMMENT '部门ID',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    UNIQUE KEY uk_plate_number (plate_number),
    UNIQUE KEY uk_vin (vin),
    KEY idx_department_id (department_id),
    KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='车辆资产表';
```

## 3. 启动方式

1. 修改数据库连接（`src/main/resources/application.yml`）：
   - `spring.datasource.url`
   - `spring.datasource.username`
   - `spring.datasource.password`
2. 创建数据库并执行建表 SQL。
3. 启动项目：

```bash
mvn spring-boot:run
```

默认端口：`8080`

## 4. RESTful 接口

### 4.1 查询列表
- **GET** `/api/vehicles`

示例请求：

```bash
curl -X GET http://localhost:8080/api/vehicles
```

示例返回：

```json
{
  "success": true,
  "message": "ok",
  "data": [
    {
      "id": 1912345678901234567,
      "plateNumber": "粤A12345",
      "vin": "LDC613P23A1305189",
      "brand": "Toyota",
      "model": "Corolla",
      "purchaseDate": "2024-05-10",
      "purchasePrice": 125000.00,
      "status": "在用",
      "departmentId": 1001,
      "createdAt": "2026-03-27T09:30:12"
    }
  ]
}
```

### 4.2 查询详情
- **GET** `/api/vehicles/{id}`

示例请求：

```bash
curl -X GET http://localhost:8080/api/vehicles/1912345678901234567
```

示例返回：

```json
{
  "success": true,
  "message": "ok",
  "data": {
    "id": 1912345678901234567,
    "plateNumber": "粤A12345",
    "vin": "LDC613P23A1305189",
    "brand": "Toyota",
    "model": "Corolla",
    "purchaseDate": "2024-05-10",
    "purchasePrice": 125000.00,
    "status": "在用",
    "departmentId": 1001,
    "createdAt": "2026-03-27T09:30:12"
  }
}
```

### 4.3 新增车辆
- **POST** `/api/vehicles`

示例请求：

```bash
curl -X POST http://localhost:8080/api/vehicles \
  -H "Content-Type: application/json" \
  -d '{
    "plateNumber": "沪B56789",
    "vin": "LSVAB1234F2187654",
    "brand": "BYD",
    "model": "秦PLUS",
    "purchaseDate": "2025-01-15",
    "purchasePrice": 109800.00,
    "status": "在用",
    "departmentId": 1002
  }'
```

示例返回：

```json
{
  "success": true,
  "message": "ok",
  "data": {
    "id": 1912345678901234568,
    "plateNumber": "沪B56789",
    "vin": "LSVAB1234F2187654",
    "brand": "BYD",
    "model": "秦PLUS",
    "purchaseDate": "2025-01-15",
    "purchasePrice": 109800.00,
    "status": "在用",
    "departmentId": 1002,
    "createdAt": "2026-03-27T10:01:08"
  }
}
```

### 4.4 修改车辆
- **PUT** `/api/vehicles/{id}`

示例请求：

```bash
curl -X PUT http://localhost:8080/api/vehicles/1912345678901234568 \
  -H "Content-Type: application/json" \
  -d '{
    "plateNumber": "沪B56789",
    "vin": "LSVAB1234F2187654",
    "brand": "BYD",
    "model": "秦PLUS DM-i",
    "purchaseDate": "2025-01-15",
    "purchasePrice": 112800.00,
    "status": "维修",
    "departmentId": 1002
  }'
```

示例返回：

```json
{
  "success": true,
  "message": "ok",
  "data": {
    "id": 1912345678901234568,
    "plateNumber": "沪B56789",
    "vin": "LSVAB1234F2187654",
    "brand": "BYD",
    "model": "秦PLUS DM-i",
    "purchaseDate": "2025-01-15",
    "purchasePrice": 112800.00,
    "status": "维修",
    "departmentId": 1002,
    "createdAt": "2026-03-27T10:01:08"
  }
}
```

### 4.5 删除车辆
- **DELETE** `/api/vehicles/{id}`

示例请求：

```bash
curl -X DELETE http://localhost:8080/api/vehicles/1912345678901234568
```

示例返回：

```json
{
  "success": true,
  "message": "ok",
  "data": null
}
```

## 5. created_at 自动填充说明

- `Vehicle.createdAt` 使用 `@TableField(fill = FieldFill.INSERT)` 标记。
- `MybatisPlusMetaObjectHandler` 在插入时自动填充 `LocalDateTime.now()`。

> 新增接口无需传 `createdAt` 字段，后端会自动赋值。
