# EAM 资产管理系统（Spring Boot + Vue3）

在原有车辆 CRUD 基础上升级为**统一资产平台**，支持多资产类型和资产全生命周期管理：

- 入库 → 在用（领用）→ 维修 → 调拨 → 报废 → 盘点
- 统一资产主数据 + 扩展表（vehicle）
- 操作日志可追溯
- Dashboard / 统计分析

## 技术栈

- 后端：Java 17 / Spring Boot 3 / MyBatis-Plus / MySQL / Maven
- 前端：Vue3 / Vite / Vue Router / Axios

---

## 一、后端结构

```text
src/main/java/com/example/assetsystem
├── controller
│   ├── AssetController.java
│   ├── AssetOperationController.java
│   ├── MaintenanceController.java
│   ├── ScrapController.java
│   ├── InventoryController.java
│   ├── VehicleController.java
│   └── GlobalExceptionHandler.java
├── entity
│   ├── Asset.java
│   ├── Vehicle.java
│   ├── AssetOperation.java
│   ├── MaintenanceRecord.java
│   ├── ScrapRecord.java
│   ├── InventoryTask.java
│   ├── InventoryRecord.java
│   └── AssetCategory.java
├── mapper
├── service
└── config
```

### 数据库脚本

执行：`sql/vehicle.sql`

包含表：

- 资产主表：`asset`
- 扩展表：`vehicle`（保留）
- 操作日志：`asset_operation`
- 维修：`maintenance_record`
- 报废：`scrap_record`
- 盘点任务：`inventory_task`
- 盘点结果：`inventory_record`
- 分类：`asset_category`

---

## 二、核心接口

### 资产管理
- `GET /api/assets`（分页/筛选/搜索）
- `GET /api/assets/{id}`
- `POST /api/assets`
- `PUT /api/assets/{id}`
- `DELETE /api/assets/{id}`
- `GET /api/assets/categories`
- `POST /api/assets/categories`
- `PUT /api/assets/categories/{id}`

### 仪表盘与统计
- `GET /api/assets/dashboard`
- `GET /api/assets/statistics`

### 资产操作流
- `POST /api/asset-operations/checkout`（领用：在库→在用）
- `POST /api/asset-operations/return/{assetId}`（归还：在用→在库）
- `POST /api/asset-operations/transfer`（调拨）
- `GET /api/asset-operations`

### 维修
- `GET /api/maintenance`
- `POST /api/maintenance`
- `PUT /api/maintenance/{id}/start`
- `PUT /api/maintenance/{id}/complete`

### 报废
- `GET /api/scrap`
- `POST /api/scrap`

### 盘点
- `GET /api/inventory/tasks`
- `POST /api/inventory/tasks`
- `POST /api/inventory/tasks/{taskId}/records`
- `GET /api/inventory/tasks/{taskId}/diffs`

---

## 三、前端结构

```text
frontend/src
├── api
│   ├── asset.js
│   ├── vehicle.js
│   └── operation.js
├── views
│   ├── dashboard/
│   ├── assets/
│   ├── vehicles/
│   ├── operations/
│   ├── inventory/
│   └── statistics/
├── components
│   ├── UnifiedTable.vue
│   ├── AssetFormModal.vue
│   └── StatusTag.vue
└── router
```

菜单已覆盖：仪表盘、资产列表（统一入口）、车辆、电脑预留、设备预留、家具预留、资产操作、盘点管理、统计分析。

---

## 四、运行方式

### 后端
1. 配置 `src/main/resources/application.yml` 中 MySQL 连接
2. 执行 `sql/vehicle.sql`
3. 启动：

```bash
mvn spring-boot:run
```

### 前端

```bash
cd frontend
npm install
npm run dev
```

前端默认 `5173`，已代理 `/api` 到 `8080`。

---

## 五、示例请求（资产领用）

```bash
curl -X POST http://localhost:8080/api/asset-operations/checkout \
  -H "Content-Type: application/json" \
  -d '{
    "assetId": 20001,
    "user": "张三",
    "purpose": "项目外勤"
  }'
```

返回：

```json
{
  "success": true,
  "message": "ok",
  "data": null
}
```
