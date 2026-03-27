# asset-system

基于 **Spring Boot 3 + MyBatis-Plus + MySQL + Vue3 + Vite** 的简化版企业资产管理系统（EAM）。

> 在原有车辆资产 CRUD 的基础上扩展为多资产统一平台，覆盖资产全生命周期：**入库 → 使用 → 维修 → 调拨 → 报废 → 盘点**。

## 功能总览

### 后端（已实现）

- 统一资产台账：`/api/assets`（支持类型筛选、关键字搜索、分页）
- 分类管理：`/api/assets/categories`
- 仪表盘统计：`/api/assets/dashboard`
- 资产操作流：`/api/asset-operations`（领用/归还/调拨/维修/报废/盘点日志）
- 维修管理：`/api/maintenance`
- 报废管理：`/api/scrap`
- 盘点管理：`/api/inventory/tasks`、`/api/inventory/records`
- 兼容原车辆模块：`/api/vehicles`

### 前端（新增目录 `frontend/`）

- 菜单：仪表盘、资产列表、车辆管理、电脑/设备/家具预留、资产操作、盘点管理
- 页面目录：
  - `src/views/dashboard`
  - `src/views/assets`
  - `src/views/vehicles`
  - `src/views/operations`
  - `src/views/inventory`
- 通用组件：统一表格、表单弹窗、状态标签
- API 模块：`src/api/asset.js`、`src/api/vehicle.js`、`src/api/operation.js`

## 项目结构

```text
asset-system
├── sql/vehicle.sql
├── src/main/java/com/example/assetsystem
│   ├── common                 # 枚举定义
│   ├── config                 # MyBatis Plus 配置、自动填充
│   ├── controller             # 资产/操作/维修/盘点/报废/车辆接口
│   ├── dto                    # 通用响应 + 查询/请求 DTO
│   ├── entity                 # asset/operation/maintenance/inventory/vehicle 实体
│   ├── mapper
│   └── service
└── frontend
    ├── package.json
    ├── src
    │   ├── api
    │   ├── components
    │   └── views
    └── vite.config.js
```

## 数据库

执行 `sql/vehicle.sql`，包含：

- 保留 `vehicle` 表
- 新增 `asset` 主表
- 新增 `asset_category` 分类表
- 新增 `asset_operation` 操作日志表
- 新增 `maintenance_order` 维修工单表
- 新增 `inventory_task` / `inventory_record` 盘点表

## 启动

### 后端

1. 修改 `src/main/resources/application.yml` 数据库连接
2. 初始化数据库 SQL
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

## 生命周期状态约束（核心）

- `CHECKOUT`：`IN_STOCK -> IN_USE`
- `RETURN`：`IN_USE -> IN_STOCK`
- `TRANSFER`：跨部门变更 `departmentId`
- `MAINTENANCE`：变更状态为 `IN_MAINTENANCE`
- `SCRAP`：变更状态为 `SCRAPPED` 且后续禁止业务操作
- `INVENTORY`：记录盘点日志

## 注意

当前环境中 `mvn test` 受 Maven Central 403 限制，无法完成依赖下载。
